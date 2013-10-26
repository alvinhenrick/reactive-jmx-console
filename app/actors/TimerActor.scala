package actors

import play.api.libs.json._
import play.api.libs.json.Json._

import akka.actor.Actor

import play.api.libs.iteratee.{Concurrent, Enumerator}

import play.api.libs.iteratee.Concurrent.Channel
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.duration._

class TimerActor extends Actor {

  // crate a scheduler to send a message to this actor every socket
  val cancellable = context.system.scheduler.schedule(0 second, 1 second, self, UpdateCharts())

  case class UserChannel(userId: String, var channelsCount: Int, enumerator: Enumerator[JsValue], channel: Channel[JsValue])

  lazy val log = Logger("application." + this.getClass.getName)

  // this map relate every user with his UserChannel
  var webSockets = Map[String, UserChannel]()

  override def receive = {

    case StartSocket(userId) =>

      log.debug(s"start new socket for user $userId")

      // get or create the touple (Enumerator[JsValue], Channel[JsValue]) for current user
      // Channel is very useful class, it allows to write data inside its related 
      // enumerator, that allow to create WebSocket or Streams around that enumerator and
      // write data iside that using its related Channel
      val userChannel: UserChannel = webSockets.get(userId) getOrElse {
        val broadcast: (Enumerator[JsValue], Channel[JsValue]) = Concurrent.broadcast[JsValue]
        UserChannel(userId, 0, broadcast._1, broadcast._2)
      }

      // if user open more then one connection, increment just a counter instead of create
      // another touple (Enumerator, Channel), and return current enumerator,
      // in that way when we write in the channel,
      // all opened WebSocket of that user receive the same data
      userChannel.channelsCount = userChannel.channelsCount + 1
      webSockets += (userId -> userChannel)

      log debug s"channel for user : $userId count : ${userChannel.channelsCount}"
      log debug s"channel count : ${webSockets.size}"

      // return the enumerator related to the user channel,
      // this will be used for create the WebSocket
      sender ! userChannel.enumerator

    case UpdateCharts() =>

      // increase the current time for every user,
      // and send current time to the user,
      webSockets.foreach {
        case (userId, userChannel) =>
          val json = Map("data" -> toJson(1))
          // writing data to tha channel,
          // will send data to all WebSocket opend form every user
          userChannel.channel push Json.toJson(json)
      }


    case SocketClosed(userId) =>

      log debug s"closed socket for $userId"

      val userChannel = webSockets.get(userId).get

      if (userChannel.channelsCount > 1) {
        userChannel.channelsCount = userChannel.channelsCount - 1
        webSockets += (userId -> userChannel)
        log debug s"channel for user : $userId count : ${userChannel.channelsCount}"
      } else {
        removeUserChannel(userId)
        log debug s"removed channel and timer for $userId"
      }

  }

  def removeUserChannel(userId: String) = webSockets -= userId

}


sealed trait SocketMessage

case class StartSocket(userId: String) extends SocketMessage

case class SocketClosed(userId: String) extends SocketMessage

case class UpdateCharts() extends SocketMessage


