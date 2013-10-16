package utils

import javax.management.MBeanParameterInfo

case class RichMBeanOperation(val name: String,
                              val returnType: String,
                              val signature: Seq[MBeanParameterInfo],
                              val desc: String,
                              val impact: Impact) {

  override def toString = s"[RichMBeanOperation name=$name]"
}
