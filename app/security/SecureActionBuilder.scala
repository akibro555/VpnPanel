package security

import javax.inject.{ Inject, Singleton }
import play.api.mvc._
import scala.concurrent.{ ExecutionContext, Future }
import play.api.mvc.Results._
import cats.implicits._
import Permissions._
import models.repo.UserRepo

@Singleton
class SecureActionBuilder(Role: Permissions)
  (implicit ec: ExecutionContext) extends ActionBuilder[Request] {
  private val user: UserRepo = ???

  def check(role: Permissions, id: java.util.UUID) = user.getType(id, role)

  def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]) = {
    request.session.get("UUID").map { id =>
      check(Role, java.util.UUID.fromString(id)) flatMap { r =>
        if(r) block(request)
        else Future.successful(Forbidden)
      }
    } getOrElse { Future.successful(Forbidden) }
  }
}


