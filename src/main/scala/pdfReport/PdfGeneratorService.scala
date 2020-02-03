//package pdfReport
//
//import play.api.libs.json.Writes
//import play.api.libs.json.Json
//
//trait PdfGeneratorService {
////  def toPdf[A <: PhasesReport](a: A): Unit
//
//  //  def toPdf[A](a: A)(implicit w: Writes[A]): Unit
//}
//
//
//class DefaultPdfGeneratorService extends PdfGeneratorService {
//
//  def toPdf[A](a: A): Unit = {
//    println(s"Generating report for: $a")
//    val asJson = Json.toJson[A](a)
//    println(s"Going to send data: $asJson")
//  }
//
//  //  def toPdf[A](a: A)(implicit w: Writes[A]): Unit = {
//  //    println(s"Generating report for: $a")
//  //    val asJson = Json.toJson[A](a)
//  //    println(s"Going to send data: $asJson")
//  //  }
//
//}
