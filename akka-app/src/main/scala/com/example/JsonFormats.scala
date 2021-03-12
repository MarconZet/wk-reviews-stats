package com.example

import com.example.RequestGuardian.{ProgressReport, UserReviews}
import spray.json.RootJsonFormat
import spray.json.DefaultJsonProtocol

object JsonFormats {

  import DefaultJsonProtocol._

  implicit val apiKeyJson: RootJsonFormat[ApiKey] = jsonFormat1(ApiKey)
  implicit val userReviewsJson: RootJsonFormat[UserReviews] = jsonFormat1(UserReviews)
  implicit val progressReportJson: RootJsonFormat[ProgressReport] = jsonFormat1(ProgressReport)
}
