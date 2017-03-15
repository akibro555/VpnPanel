package controllers

import javax.inject._
import scala.concurrent.Future
import play.api._
import play.api.mvc._
import play.api.i18n.{ I18nSupport, MessagesApi }
import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }
import security.Permissions._

@Singleton
class HomeController @Inject() (
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport with security.Secure {

  def index = UserSecureAction(Admin).async { implicit requests =>
    Future.successful(Ok(views.html.loginPage()))
  }

  def login = UserSecureAction(Admin).async { implicit requests =>
    Future.successful(Ok(views.html.loginPage()))
  }
}
