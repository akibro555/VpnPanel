package security

import slick.driver.MySQLDriver.api._

object Permissions extends Enumeration  {
  implicit val PermissionsMapper = MappedColumnType.base[Permissions, String](
    e => e.toString,
    s => Permissions.withName(s)
  )

  type Permissions = Value
  val ADMIN = Value("admin")
  val RESELER = Value("reseller")
  val `SUB-RESELLER` = Value("sub-reseller")
  val CLIENT = Value("client")
}
