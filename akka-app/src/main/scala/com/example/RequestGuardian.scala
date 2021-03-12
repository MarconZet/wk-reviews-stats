package com.example


import akka.actor.typed.{ActorRef, Behavior, scaladsl}
import akka.actor.typed.scaladsl.Behaviors

import scala.collection.immutable

final case class ApiKey(key: String)

object RequestGuardian {

  sealed trait Command

  final case class GetAllUserReviews(apiKey: ApiKey, replyTo: ActorRef[UserReviews]) extends Command

  final case class GetCurrentProgress(apiKey: ApiKey, replyTo: ActorRef[ProgressReport]) extends Command

  final case class UserReviews(reviews: List[String])

  final case class ProgressReport(progress: Int)

  def apply(): Behavior[Command] = Behaviors.receive {
    (context, message) => {
      print(message)
      Behaviors.same
    }

  }
}
