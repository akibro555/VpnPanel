package models.domain

import play.api.libs.json._

case class User(
    firstName: String,
    lastName: String,
    address: String,
    gender: String,
    position: String,
    credits: Int,
    createdBy: String,
    foreignKey: Option[Int] = None,
    id: Option[Int]) {
  def toJson: JsObject = User.Implicits.userImplicits.writes(this).as[JsObject]
}

object User {
  object Implicits {
    implicit val userImplicits = new Writes[User] {
      def writes(user: User): JsValue = Json.obj(
        "first_name" -> user.firstName,
        "last_name" -> user.lastName,
        "address" -> user.address,
        "gender" -> user.gender,
        "position" -> user.position,
        "credits" -> user.credits,
        "created_by" -> user.createdBy,
        "id" -> user.id.get)
    }
  }
}
