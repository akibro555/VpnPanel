package security

import play.api._

trait Secure {
  object UserSecureAction {
    def apply(Role: Permissions.Value) = invokeSecuredBlock(Role)

    def invokeSecuredBlock(Role: Permissions.Value) = {
      new SecureActionBuilder(Role, "Admin")
    }
  }
}
