package traits

import play.api.libs.json.{Json, OFormat}
import traits.sap.{Resident, SalesOrderToSAP}

trait JsonImplicits {
  implicit lazy val sorw: OFormat[SalesOrderToSAP] = Json.format[SalesOrderToSAP]
  implicit lazy val rcrw: OFormat[Resident] = Json.format[Resident]
}
