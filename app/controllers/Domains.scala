package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json._
import jajmx._
import fr.janalyse.jmx.{RichAttribute, RichMBean}

import utils.RichMBeanWithOperation._
import utils.RichMBeanOperation

object Domains extends Controller {

  val attributeWrites = new Writes[RichAttribute] {
    def writes(attr: RichAttribute) = Json.obj(
      "name" -> Json.toJson(attr.name) ,
      "description" -> Json.toJson(attr.desc)
    )
  }

  val operationWrites = new Writes[RichMBeanOperation] {
    def writes(opr: RichMBeanOperation) = Json.obj(
      "name" -> Json.toJson(opr.name) ,
      "description" -> Json.toJson(opr.desc)
    )
  }

  val mbeanWrites = new Writes[RichMBean] {
    def writes(m: RichMBean) = Json.obj(
      "name" -> Json.toJson(m.name),
      "domain" -> Json.toJson(m.domain),
      "attributes" -> Json.arr(m.attributes().map(attr => Json.toJson(attr)(attributeWrites))) ,
      "operations" -> Json.arr(m.operations().map(opr => Json.toJson(opr)(operationWrites)))
    )
  }



  /**
   * Returns an array of domains .
   */
  def list = Action {

    val jmx = JMX()
    val result = Json.toJson(jmx.mbeans().groupBy(m => m.domain).map(y => Json.obj("domain" -> y._1 ,
                                                                                   "mbeans" -> y._2.map(x => Json.toJson(x)(mbeanWrites)).toSeq)))
    Ok(result)

  }




}
