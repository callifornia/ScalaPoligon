//package pdfReport
//import play.api.libs.json.{Json, Writes}
//
//
//object Model {
//
//
//
//  case class ReportData(a: String, b: Int, typex: ProjectPhaseType)
//
//
//  case class CostEstimationReport(a: String, b: Int, c: String)
//    extends PhasesReport[CostEstimationReport]
//  object CostEstimationReport {
//    implicit val w: Writes[CostEstimationReport] = Json.writes[CostEstimationReport]
//  }
//
//  case class OfferReport(a: String, b: String)
//    extends PhasesReport[OfferReport]
//  object OfferReport {
//    implicit val w: Writes[OfferReport] = Json.writes[OfferReport]
//  }
//
//
//  sealed trait ProjectPhaseType
//  case object Targeting extends ProjectPhaseType
//  case object Calculation extends ProjectPhaseType
//
//
//  sealed trait PhasesReport[A] { self: A =>
//    implicit val w: Writes[this.type] = Json.writes[this.type]
//  }
//}
