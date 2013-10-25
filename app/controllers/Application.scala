package controllers

import play.api.mvc.{RequestHeader, Action, Controller}
import views.html

/**
 * Created with IntelliJ IDEA.
 * User: ahenrick
 * Date: 10/24/13
 * Time: 9:29 PM
 * To change this template use File | Settings | File Templates.
 */
object Application extends Controller {

  /**
   * Home page with a session containing UUID that might be reused to see an ongoing process
   */
  def dashboard = Action {
    request => {
      val (session, generatedUuid) = uuid(request)
      Ok(html.dashboard()).withSession(session)
    }
  }


  def uuid(request: RequestHeader) = {
    var session = request.session
    val uuid = session.get("uuid").getOrElse({
      val newUuid = java.util.UUID.randomUUID().toString()
      session = session.+("uuid", newUuid)
      newUuid
    })
    (session, uuid)
  }

}
