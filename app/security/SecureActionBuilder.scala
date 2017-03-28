package security

import javax.inject.{ Inject, Singleton }
import play.api.mvc._
import scala.concurrent.Future
import play.api.mvc.Results._
import cats.implicits._
import models.repo.UserRepo

@Singleton
class SecureActionBuilder(checking: Boolean) extends ActionBuilder[Request] {
  def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]) = {
    if(checking) block(request)
    else Future.successful(Forbidden)
  }
}
