package models.domain

import play.api.libs.json._

case class User(
    userName: String,
    password: String,
    credits: String,
    createdBy: Option[String] = None) {
  def toJson: JsObject = User.Implicits.userImplicits.writes(this).as[JsObject]
}

object User {
  val tupled = (apply _).tupled
  object Implicits {
    implicit val userImplicits = new Writes[User] {
      def writes(user: User): JsValue = Json.obj(
        "user_name" -> user.userName,
        "password" -> user.password,
        "credits" -> user.credits,
        "created_by" -> user.createdBy)
    }
  }
}
