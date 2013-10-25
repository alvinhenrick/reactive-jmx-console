package controllers

import play.api.mvc._
import play.api.libs.json.{Json, JsValue}
import play.api.libs.iteratee._
import play.api.libs.concurrent.Akka
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits._

import akka.pattern.ask
import akka.util.Timeout
import akka.actor._
import actors.{SocketClosed, StartSocket, TimerActor}

import scala.concurrent.Future
import scala.concurrent.duration._


object Metrics extends Controller {

  implicit val timeout = Timeout(3 second)
  val timerActor = Akka.system.actorOf(Props[TimerActor])

  def indexWS = WebSocket.async[JsValue] { request =>

    def errorFuture = {
      // Just consume and ignore the input
      val in = Iteratee.ignore[JsValue]
      // Send a single 'Hello!' message and close
      val out = Enumerator(Json.toJson("not authorized")).andThen(Enumerator.eof)
       Future { (in, out) }
    }


    extractUuid(request) match {
      case None => errorFuture
      case Some(userId) =>
        (timerActor ? StartSocket(userId)) map {
          enumerator =>

            (Iteratee.ignore[JsValue] map {
              _ =>
                timerActor ! SocketClosed(userId)
            }, enumerator.asInstanceOf[Enumerator[JsValue]])
        }
    }
  }

  def extractUuid(request: RequestHeader) = {
    request.session.get("uuid")
  }


}


