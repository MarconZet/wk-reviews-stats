package com.example

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route

import scala.concurrent.Future
import com.example.RequestGuardian._
import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.AskPattern._
import akka.util.Timeout

class UserRoutes(requestGuardian: ActorRef[RequestGuardian.Command])(implicit val system: ActorSystem[_]) {

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import JsonFormats._

  private implicit val timeout: Timeout = Timeout.create(system.settings.config.getDuration("my-app.routes.ask-timeout"))

  def getUserReviews(apiKey: ApiKey): Future[UserReviews] =
    requestGuardian.ask(GetAllUserReviews(apiKey, _))

  def getCurrentProgress(apiKey: ApiKey): Future[ProgressReport] =
    requestGuardian.ask(GetCurrentProgress(apiKey, _))

  val userRoutes: Route = {
    concat(
      pathPrefix("reviews") {
        pathEnd {
          post {
            entity(as[ApiKey]) {
              apiKey =>
                complete(getUserReviews(apiKey))
            }
          }
        }
      },
      pathPrefix("progress") {
        pathEnd {
          post {
            entity(as[ApiKey]) {
              apiKey =>
                complete(getCurrentProgress(apiKey))
            }
          }
        }
      },
    )
  }
}
