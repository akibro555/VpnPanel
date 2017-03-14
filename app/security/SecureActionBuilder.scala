package security

import javax.inject.{ Inject, Singleton }
import play.api.mvc._
import scala.concurrent.Future
import play.api.mvc.Results._
import cats.implicits._

class SecureActionBuilder(UserRole: Permissions.Value, DBRole: String)
  extends ActionBuilder[Request] {
  def invokeBlock[A](
      request: Request[A],
      block: Request[A] => Future[Result]) = {
        if(UserRole.toString == DBRole) block(request)
        else Future.successful(Forbidden)
  }
}
