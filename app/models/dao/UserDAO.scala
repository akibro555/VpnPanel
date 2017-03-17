package models.dao

import javax.inject.{ Inject, Singleton }
import scala.concurrent.Future
import slick.profile.RelationalProfile
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain.User

@Singleton
final private [models] class UserDAO @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
  ) extends HasDatabaseConfigProvider[RelationalProfile] {
  import slick.driver.MySQLDriver.api._

  protected class UsersTable(tag: Tag) extends Table[User](tag, "USERS") {
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    def username = column[String]("USERNAME", O.Length(255, true))
    def password = column[String]("PASSWORD", O.Length(255, true))
    def credits = column[Int]("CREDITS")
    def createdBy = column[String]("CREATED_BY")

    def * = (id.?, username, password, credits, createdBy.?) <> (User.tupled, User.unapply)
  }

  private [models] object query extends TableQuery(new UsersTable(_)) {
    @inline def apply(id: Int) = this.withFilter(r => r.id === id)
  }
}
