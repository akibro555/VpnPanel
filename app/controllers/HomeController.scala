package controllers

import javax.inject._
import scala.concurrent.Future
import play.api._
import play.api.mvc._
import play.api.i18n.{ I18nSupport, MessagesApi }
import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }
import security.Permissions._
import play.api.libs.json._
import models.domain.User.Implicits._

@Singleton
class HomeController @Inject() (
  userRepo: models.repo.UserRepo,
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  implicit val wja: WebJarAssets,
  implicit val ec: scala.concurrent.ExecutionContext
) extends Controller with I18nSupport with PageMetaSupport with security.Secure {

  def index = UserSecureAction(ADMIN).async { implicit requests =>
    userRepo.all map {
      r => Ok(Json.obj("users" -> r.map(_.toJson)))
    }
  }

  def loginPage = Action.async { implicit requests =>
    Future.successful(Ok(views.html.loginPage()))
  }

  def loginAction = Action.async {
    Future.successful(Ok("Test"))
  }
}
