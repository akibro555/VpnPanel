package security

import play.api._
import play.api.mvc.Results._
import scala.concurrent.Future
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Singleton

import play.api.mvc.Request

import javax.inject.{ Singleton, Inject }

@Singleton
class SecuredRequest {
  def invokeSecuredBlock[A](role: String)
    (request: Request[A], block: Request[A] => Future[Result]) = {
    if(role == "Admin") {
       block(request)
    } else {
      Future.successful(Forbidden)
    }
  }
}

trait Secure {
  object UserAction extends ActionBuilder[Request] {
    def apply[A](role: String)(request: Request[A], )
    def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]) = {
      new SecuredRequest().invokeSecuredBlock("Admin")(request, block)
    }
  }
}
