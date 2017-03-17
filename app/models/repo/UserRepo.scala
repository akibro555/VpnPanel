package models.repo

import javax.inject.{ Singleton, Inject }
import slick.profile.RelationalProfile
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }

@Singleton
class UserRepo @Inject()(
    userDAO: models.dao.UserDAO,
    protected val dbConfigProvider: DatabaseConfigProvider
  ) extends HasDatabaseConfigProvider[RelationalProfile] {
  import slick.driver.MySQLDriver.api._

  def getAll: scala.concurrent.Future[Seq[models.domain.User]] = db.run(userDAO.query.result)
}
