import java.time.{LocalDate, LocalDateTime}
import java.time.LocalDate.parse

//case class ChangesTableEntry(accommodationId: String,
//                             brand: String,
//                             language: String,
//                             date: LocalDateTime,
//                             mhid: String,
//                             ekms: String,
//                             origin: String,
//                             catalogs: List[String],
//                             name: String,
//                             live: Boolean,
//                             nurvisCodes: List[String],
//                             validity: Option[Either[String, LocalDate]])

case class ChangesTableEntry(date: LocalDateTime,
                             live: Boolean,
                             validity: Option[Either[String, LocalDate]])

object ChangesTableEntry {
  implicit val localDateOrdering: Ordering[Option[Either[String, LocalDate]]] =
    Ordering.by(_.flatMap(_.toOption).map(_.toEpochDay).getOrElse(LocalDate.now().toEpochDay))

  implicit val localDateOrdering2: Ordering[LocalDateTime] = Ordering.by(_.toLocalDate.toEpochDay)

  implicit val sortByValidity: Ordering[ChangesTableEntry] = Ordering.by(_.validity)
}


//******** W20 ****************************************************

//020-01-01T00:00 to 2020-01-02T00:00
//CHANGES W20 1 record: W20 - false
//val a1_false = Seq(
//  ChangesTableEntry("2020-01-01", false, Some(Right("1970-01-01"))),
//  ChangesTableEntry("2020-01-01", false, Some(Right("2020-01-01"))))
//
////CHANGES W20 1 record W20 - true
//val a2_true = Seq(
//  ChangesTableEntry("2020-01-01", true, Some(Right("1970-01-01"))),
//  ChangesTableEntry("2020-01-01", false, Some(Right("2020-01-01"))))
//
////CHANGES W20 1 records W20 - true
//val a3_true = Seq(
//  ChangesTableEntry("2020-01-01", true, Some(Right("2020-01-01"))),
//  ChangesTableEntry("2020-01-01", true, Some(Right("1970-01-01"))))
//
////CHANGES W20 1 record W20 true
//val a4_true = Seq(
//  ChangesTableEntry("2020-01-01", false, Some(Right("1970-01-01"))),
//  ChangesTableEntry("2020-01-01", true, Some(Right("2020-01-01"))))
//
//
////******** ENDLESS ************************************************
//
////2019 - 03 - 02 T00: 00 to 2019 - 05 - 01 T23: 59
////CHANGES 1 record: endless - false
//val a5_false = Seq(
//  ChangesTableEntry("2019-04-01", false, Some(Right("1970-01-01"))),
//  ChangesTableEntry("2019-04-01", false, Some(Right("2020-01-01"))))
//
////CHANGES 1 record endless - true
//val a6_true = Seq(
//  ChangesTableEntry("2019-04-01", true, Some(Right("1970-01-01"))),
//  ChangesTableEntry("2019-04-01", true, Some(Right("2020-01-01"))))
//
////CHANGES 1 record endless - true
//val a7_true = Seq(
//  ChangesTableEntry("2019-04-01", true, Some(Right("1970-01-01"))),
//  ChangesTableEntry("2019-04-01", false, Some(Right("2020-01-01"))))
//
////2019 - 03 - 02 T00: 00 to 2019 - 05 - 01 T23: 59
////CHANGES 1 record endless false
//val a9_false = Seq(
//  ChangesTableEntry("2019-03-31", false, Some(Right("1970-01-01"))),
//  ChangesTableEntry("2019-03-31", true, Some(Right("2020-01-01"))))
//
//
////******** ENDLESS +W20 *******************************************
//
////2019 - 03 - 02 T00: 00 to 2020 - 01 - 02 T00: 00
////CHANGES 2 records endless - false, W20 - true
val dateTimeOfChange = LocalDateTime.of(2018, 7, 22, 11, 22, 33)
val a11_false_true = Seq(
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), false, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), true, Some(Right(parse("2020-01-01")))),
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), false, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), true, Some(Right(parse("2020-01-01")))))
//
////CHANGES 2 records: endless - true, W20 - false
val a12_true_false = Seq(
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), true, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), false, Some(Right(parse("2020-01-01")))),
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), true, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), false, Some(Right(parse("2020-01-01")))))
//
////CHANGES 2 records endless - true, W20 - true
val a13_true_true = Seq(
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), true, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), true, Some(Right(parse("2020-01-01")))),
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), true, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), true, Some(Right(parse("2020-01-01")))))

//CHANGES 2 records endless - false, W20 - true
val a14_false_true = Seq(
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), false, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), true, Some(Right(parse("2020-01-01")))),
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), false, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), true, Some(Right(parse("2020-01-01")))))

//b.validity.flatMap(_.toOption).contains(b.date.toLocalDate)
import ChangesTableEntry._


def check2(entries: Seq[ChangesTableEntry]) = {
  entries.sorted.filter{ e =>
    e.
  }
  sorted filtered by variant match {
    case 1 => return 1
    case List(a, b) =>
      if (b.live) b
      else b.copy(live = a.live)
  }
}

def check(entries: Seq[ChangesTableEntry]) = {
  import ChangesTableEntry._
  entries match {
    case xs =>
      xs.groupBy(_.validity).flatMap {
        case (_, entries) => entries.sortBy(_.date).foldLeft(entries.headOption) {
          (acc, current) =>

            println(
              s"""
                 | current: $current
                 | acc: ${acc.mkString("\n")}
           """.stripMargin)

            if (current.validity.flatMap(_.toOption).contains(current.date.toLocalDate)) {
              Some(current)
            } else None
        }
      }.toList
  }
}

println(
  s"""
     | bbb: ${check(a14_false_true)}
   """.stripMargin)
//println(
//  s"""
//     |
//     |
//     | ${seq.sorted}
//     |
//   """.stripMargin)
//
//
//def checkMethod(entries: Seq[ChangesTableEntry]): Seq[ChangesTableEntry] = {
//  println(
//    s"""
//       |before after grouping
//       |${entries.mkString("\n")}
//             """.stripMargin)
//
//  entries match {
//    case Seq() => {
//      println("asdqwezxc")
//      Seq.empty
//    }
//    case one@Seq(_) => one
//    case twoElements@Seq(_, _) => twoElements sorted match {
//
//      case Seq(a, b) if (a.live == false && b.live == true) =>
//        if (b.validity.flatMap(_.toOption).contains(b.date.toLocalDate)) Seq(b)
//        else List(a)
//      case Seq(a)
//
//      case Seq(a, b) => (a.live, b.live) match {
//        case (false, true) =>
//          println(
//            s"""
//               | if (b.validity.flatMap(_.toOption).contains(b.date.toLocalDate)) List(b)
//               | b.validity.flatMap(_.toOption): ${b.validity.flatMap(_.toOption)}
//               | b.date.toLocalDate: ${b.date.toLocalDate}
//               |
//                         | ${if (b.validity.flatMap(_.toOption).contains(b.date.toLocalDate)) Seq(b)}
//                       """.stripMargin)
//          if (b.validity.flatMap(_.toOption).contains(b.date.toLocalDate)) Seq(b)
//          else List(a)
//        case (_, _) => Seq(a)
//      }
//    }
//    case xs =>
//      xs.groupBy(_.validity).flatMap {
//        case (_, entities) => entities.headOption
//      }.toSeq
//  }
//}
//val result = checkMethod(Seq.empty)
//println(
//  s"""
//     | RESULT
//     |$result
//     |
//   """.stripMargin)
////
////
////def checkMethod(entries: Seq[ChangesTableEntry]): Seq[ChangesTableEntry] = {
////  entries match {
////    case Nil => List.empty
////    case one :: Nil => List(one)
////    case twoElements @ _ :: _ :: Nil => twoElements sorted match {
////      case List(a, b) => (a.live, b.live) match {
////        case (false, true) =>
////          if (b.validity.flatMap(_.toOption).contains(b.changed)) List(b)
////          else List(a)
////        case (_, _) => List(a)
////      }
////    }
////    case xs =>
////      xs.groupBy(_.validity).flatMap{
////        case (_, entities) => entities.headOption
////      }.toList
////  }
////}
//
////
////checkMethod(a11_false_true)
////checkMethod(a12_true_false)
////
////checkMethod(a13_true_true)
////checkMethod(a14_false_true)
////checkMethod(a1_false)
////checkMethod(a2_true)
//////
////checkMethod(a3_true)
////checkMethod(a4_true) // false
////
////checkMethod(a5_false)
////checkMethod(a6_true)
//////
////checkMethod(a7_true)
////checkMethod(a9_false)
//
//
////CHANGES W20 1 record W20 true
////val a4_true = Seq(
////  ChangesTableEntry("2020-01-01", false, Some(Right("1970-01-01"))),
////  ChangesTableEntry("2020-01-01", true, Some(Right("2020-01-01"))))
//
////2020 - 01 - 01 T00: 00 to 2020 - 01 - 02 T00: 00
////CHANGES W20 1 record W20 true
////val a10_true = Seq(
////  ChangesTableEntry("2020-01-01", false, Some(Right("1970-01-01"))),
////  ChangesTableEntry("2020-01-01", true, Some(Right("2020-01-01"))))
//
//
////  // HEAD option
////  2019-03-02T00:00 to 2019-05-01T23:59
////  CHANGES 1 record endless false
////  ChangesTableEntry(2019-03-31T23:40,false,Some(Right(1970-01-01)))
////  ChangesTableEntry(2019-03-31T23:40,true,Some(Right(2020-01-01)))
////
////
////  CHANGES W20 1 record W20 true
////  ChangesTableEntry(2020-01-01T00:00,false,Some(Right(1970-01-01)))
////  ChangesTableEntry(2020-01-01T00:00,true,Some(Right(2020-01-01)))
//
//





