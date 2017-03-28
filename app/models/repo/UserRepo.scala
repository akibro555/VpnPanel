package models.repo

import javax.inject.{ Singleton, Inject }
import scala.concurrent.Future
import slick.profile.RelationalProfile
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain.User
import security.Permissions._

@Singleton
class UserRepo @Inject()(
    dao: models.dao.UserDAO,
    protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[RelationalProfile] {
  import slick.driver.MySQLDriver.api._

  def all: Future[Seq[User]] = db.run(dao.query.result)

  def get(username: String, password: String): Future[Option[User]] =
    db.run(dao.query(username, password).result.headOption)

  def add(user: User): Future[Int] = db.run(dao.query += user)

  def getType(id: java.util.UUID, `type`: Value): Future[Boolean] =
    db.run(dao.query.filter(r => r.id === id && r.`type` === `type`).exists.result)
}
