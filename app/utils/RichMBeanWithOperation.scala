package utils

import fr.janalyse.jmx.RichMBean
import javax.management.MBeanOperationInfo


object RichMBeanWithOperation {

  implicit class RichMBeanWithOperation (x: RichMBean) {

      lazy val operationsSeq: Seq[RichMBeanOperation] = x.mbeanInfo.getOperations.map(arg => {
        new RichMBeanOperation( arg.getName, arg.getReturnType, arg.getSignature, arg.getDescription, _impact(arg.getImpact))
      })

      def operation(name: String): Option[RichMBeanOperation] = operationsSeq.find(_.name == name)

      def operations(): List[RichMBeanOperation] = operationsSeq.toList

      def _impact(i: Int): Impact = i match {
      case MBeanOperationInfo.ACTION => Impact.Action
      case MBeanOperationInfo.INFO => Impact.Info
      case MBeanOperationInfo.ACTION_INFO => Impact.ActionInfo
      case _ => Impact.Uknown
    }

  }
}
