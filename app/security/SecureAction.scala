package security

import play.api._
import models.repo.UserRepo
import scala.concurrent.ExecutionContext

trait Secure {
  protected val userRepo: UserRepo

  object UserSecureAction {
    def apply(Role: Permissions.Value)(implicit ec: ExecutionContext) = {
      invokeSecuredBlock(Role)
    }

    def invokeSecuredBlock(Role: Permissions.Value)(implicit ec: ExecutionContext) = {
      new SecureActionBuilder(Role, userRepo)
    }
  }
}
