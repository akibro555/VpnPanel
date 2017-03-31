package controllers

import javax.inject._
import scala.concurrent.Future
import play.api._
import play.api.mvc._
import play.api.i18n.{ I18nSupport, MessagesApi }
import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }
import play.api.data.Forms._
import play.api.data._
import security.Permissions._
import play.api.libs.json._
import models.domain.User
import security.{ Secure }

@Singleton
class HomeController @Inject() (
  protected val userRepo: models.repo.UserRepo,
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  implicit val wja: WebJarAssets,
  implicit val ec: scala.concurrent.ExecutionContext
) extends Controller with I18nSupport with PageMetaSupport with Secure {
  import User.Implicits._
  private val lgnForm = Form(tuple(
    "username" -> nonEmptyText(minLength=1),
    "password" -> nonEmptyText(minLength=4)))

  def index = UserSecureAction(ADMIN).async { implicit requests =>
    userRepo.all map {
      r => Ok(Json.obj("users" -> r.map(_.toJson)))
    }
  }

  def loginPage = Action.async { implicit requests =>
    Future.successful(Ok(views.html.loginPage()))
  }

  def loginAction = Action.async { implicit requests =>
    lgnForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(formWithErrors.errorsAsJson)),
      { case (username, password) =>
          userRepo.get(username, password).map(user => Ok(Json.obj("user" -> user.map(_.toJson))))
      })
  }
}
