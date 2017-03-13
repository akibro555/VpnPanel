package security

import play.api._
import play.api.mvc.Results._
import scala.concurrent.Future
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Singleton

import javax.inject.{ Singleton, Inject }

trait Secure {
  object UserSecureAction {
    def apply(a: String) =  new SecureActionBuilder(a)

    class SecureActionBuilder(a: String) extends ActionBuilder[Request] {
      def invokeBlock[A](
          request: Request[A],
          block: Request[A] => Future[Result]) = {
            block(request)
      }
    }
  }
}
