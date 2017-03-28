package models.domain

import play.api.libs.json._
import security.Permissions._

case class User(
    id: Option[java.util.UUID],
    userName: String,
    password: String,
    `type`: Permissions,
    credits: Int,
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
        "type" -> user.credits,
        "created_by" -> user.createdBy)
    }
  }
}
