package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json._
import jajmx._
import fr.janalyse.jmx.RichMBean

object Domains extends Controller {

  /**
   * Returns an array of servers .
   */
  def get = Action {

    val jmx = JMX()
        val domains = jmx.domains
        val res = jmx.mbeans("java.lang:type=OperatingSystem")
        System.out.println(res)
        Ok(Json.toJson(domains))

  }

  def jsonify(mbean : RichMBean): JsValue = {

    val res = JsObject(mbean.attributesNames.map(attr => (attr, Json.toJson(mbean.getString(attr).getOrElse("")))).toSeq)
    res
  }


  def converToJson () {

  }
}
