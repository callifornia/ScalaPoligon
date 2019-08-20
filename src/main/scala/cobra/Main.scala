//package cobra
//
//import akka.actor.ActorSystem
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.model._
//import akka.stream.ActorMaterializer
//
//import scala.io.Source
//import scala.util.Failure
//
//object Main {
//  val files = "cobra_ekms.txt"
//
//  def main(args: Array[String]): Unit = {
//    import java.time.{LocalDate, LocalDateTime}
//    import java.time.LocalDate.parse
//
//
//    case class ChangesTableEntry(changed: String,
//                                 live: Boolean,
//                                 validity: Option[Either[String, String]])
//
//    object ChangesTableEntry {
//      implicit val localDateOrdering: Ordering[Option[Either[String, String]]] =
//        Ordering.by(_.flatMap(_.toOption).map(e => parse(e).toEpochDay).getOrElse(LocalDate.now().toEpochDay))
//
//      implicit val sortByValidity: Ordering[ChangesTableEntry] =
//        Ordering.by(_.validity)
//    }
//    //******** W20 ****************************************************
//
//    //020-01-01T00:00 to 2020-01-02T00:00
//    //CHANGES W20 1 record: W20 - false
//    val a1_false = Seq(
//      ChangesTableEntry("2020-01-01", false, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2020-01-01", false, Some(Right("2020-01-01"))))
//
//    //CHANGES W20 1 record W20 - true
//    val a2_true = Seq(
//      ChangesTableEntry("2020-01-01", true, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2020-01-01", false, Some(Right("2020-01-01"))))
//
//    //CHANGES W20 1 records W20 - true
//    val a3_true = Seq(
//      ChangesTableEntry("2020-01-01", true, Some(Right("2020-01-01"))),
//      ChangesTableEntry("2020-01-01", true, Some(Right("1970-01-01"))))
//
//    //CHANGES W20 1 record W20 true
//    val a4_true = Seq(
//      ChangesTableEntry("2020-01-01", false, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2020-01-01", true, Some(Right("2020-01-01"))))
//
//
//    //******** ENDLESS ************************************************
//
//    //2019 - 03 - 02 T00: 00 to 2019 - 05 - 01 T23: 59
//    //CHANGES 1 record: endless - false
//    val a5_false = Seq(
//      ChangesTableEntry("2019-04-01", false, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2019-04-01", false, Some(Right("2020-01-01"))))
//
//    //CHANGES 1 record endless - true
//    val a6_true = Seq(
//      ChangesTableEntry("2019-04-01", true, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2019-04-01", true, Some(Right("2020-01-01"))))
//
//    //CHANGES 1 record endless - true
//    val a7_true = Seq(
//      ChangesTableEntry("2019-04-01", true, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2019-04-01", false, Some(Right("2020-01-01"))))
//
//    //2019 - 03 - 02 T00: 00 to 2019 - 05 - 01 T23: 59
//    //CHANGES 1 record endless false
//    val a9_false = Seq(
//      ChangesTableEntry("2019-03-31", false, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2019-03-31", true, Some(Right("2020-01-01"))))
//
//
//    //******** ENDLESS +W20 *******************************************
//
//    //2019 - 03 - 02 T00: 00 to 2020 - 01 - 02 T00: 00
//    //CHANGES 2 records endless - false, W20 - true
//    val a11_false_true = Seq(
//      ChangesTableEntry("2020-01-01", false, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2019-04-01", false, Some(Right("1970-01-01"))),
//
//      ChangesTableEntry("2020-01-01", true, Some(Right("2020-01-01"))),
//      ChangesTableEntry("2019-04-01", true, Some(Right("2020-01-01"))))
//
//    //CHANGES 2 records: endless - true, W20 - false
//    val a12_true_false = Seq(
//      ChangesTableEntry("2020-01-01", true, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2019-04-01", true, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2020-01-01", false, Some(Right("2020-01-01"))),
//      ChangesTableEntry("2019-04-01", false, Some(Right("2020-01-01"))))
//
//    //CHANGES 2 records endless - true, W20 - true
//    val a13_true_true = Seq(
//      ChangesTableEntry("2020-01-01", true, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2019-04-01", true, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2020-01-01", true, Some(Right("2020-01-01"))),
//      ChangesTableEntry("2019-04-01", true, Some(Right("2020-01-01"))))
//
//    //CHANGES 2 records endless - false, W20 - true
//    val a14_false_true = Seq(
//      ChangesTableEntry("2020-01-01", false, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2019-04-01", false, Some(Right("1970-01-01"))),
//      ChangesTableEntry("2020-01-01", true, Some(Right("2020-01-01"))),
//      ChangesTableEntry("2019-04-01", true, Some(Right("2020-01-01"))))
//
//    def checkMethod(entries: Seq[ChangesTableEntry]): Seq[ChangesTableEntry] = {
//      entries match {
//        case Nil => List.empty
//        case one :: Nil => List(one)
//        case twoElements @ _ :: _ :: Nil => twoElements sorted match {
//          case List(a, b) => (a.live, b.live) match {
//            case (false, true) =>
//              if (b.validity.flatMap(_.toOption).contains(b.changed)) List(b)
//              else List(a)
//            case (_, _) => List(a)
//          }
//        }
//        case xs =>
//          println("Long case")
//          List.empty /*group by variant headOption for each part*/
//      }
//    }
//
//    //checkMethod(a1_false)
//    //checkMethod(a2_true)
//    //
//    //checkMethod(a3_true)
//    checkMethod(a4_true) // false
//
//    //checkMethod(a5_false)
//    //checkMethod(a6_true)
//    //
//    //checkMethod(a7_true)
//    //checkMethod(a9_false)
//
//
//
//    //CHANGES W20 1 record W20 true
//    //val a4_true = Seq(
//    //  ChangesTableEntry("2020-01-01", false, Some(Right("1970-01-01"))),
//    //  ChangesTableEntry("2020-01-01", true, Some(Right("2020-01-01"))))
//
//    //2020 - 01 - 01 T00: 00 to 2020 - 01 - 02 T00: 00
//    //CHANGES W20 1 record W20 true
//    //val a10_true = Seq(
//    //  ChangesTableEntry("2020-01-01", false, Some(Right("1970-01-01"))),
//    //  ChangesTableEntry("2020-01-01", true, Some(Right("2020-01-01"))))
//
//
//
//
//
//
//
//
//
//    //  // HEAD option
//    //  2019-03-02T00:00 to 2019-05-01T23:59
//    //  CHANGES 1 record endless false
//    //  ChangesTableEntry(2019-03-31T23:40,false,Some(Right(1970-01-01)))
//    //  ChangesTableEntry(2019-03-31T23:40,true,Some(Right(2020-01-01)))
//    //
//    //
//    //  CHANGES W20 1 record W20 true
//    //  ChangesTableEntry(2020-01-01T00:00,false,Some(Right(1970-01-01)))
//    //  ChangesTableEntry(2020-01-01T00:00,true,Some(Right(2020-01-01)))
//
//
//
//
//
//
//
//
//  }
////  def main(args: Array[String]) = {
////    val result = Source
////      .fromResource(files)
////      .getLines
////      .toSeq
////      .flatMap(e => List(
////        s"cp ../done/ProductPackage_NVN_blank_${e}*S19*.xml.gz .",
////        s"cp ../done/ProductPackage_NVN_blank_${e}*W20*.xml.gz .",
////        s"cp ../done/ProductPackage_NVN_YNVN_${e}*S19*.xml.gz .",
////        s"cp ../done/ProductPackage_NVN_YNVN_${e}*W20*.xml.gz ."))
////
////    println(
////      s"""
////         | size: ${result.size}
////         | ${result.mkString("\n")}
////       """.stripMargin)
////  }
//}