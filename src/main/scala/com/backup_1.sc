import java.time.{LocalDate, LocalDateTime}
import java.time.LocalDate.parse

import scala.annotation.tailrec
case class Foo(a: Int, live: Boolean)


//Seq(1,2,3).filter(_ == 2)
//


//
//val s1 = List(Foo(1, true), Foo(2, true), Foo(3, true))
//val s2 = List(Foo(1, true), Foo(2, true), Foo(3, false))
//val s3 = List(Foo(1, true), Foo(2, false), Foo(3, false))
//val s4 = List(Foo(1, true), Foo(2, false), Foo(3, true))
//
//val s5 = List(Foo(1, false), Foo(2, false), Foo(3, false))
//val s6 = List(Foo(1, false), Foo(2, false), Foo(3, true), Foo(4, false))
//val s7 = List(Foo(1, false), Foo(2, true), Foo(3, true))
//val s8 = List(Foo(1, false), Foo(2, true), Foo(3, false))
//val s9 = List.empty
//
//
//def correctLiveFlag(seq: List[Foo], acc: List[Foo] = List.empty): List[Foo] = seq match {
//  case Nil => acc
//  case head :: tail if head.live =>
//    (acc :+ head) ::: tail.map(_.copy(live = head.live))
//  case head :: tail =>
//    correctLiveFlag(tail, acc :+ head)
//}
//
//
////def correctLiveFlag(seq: List[Foo], acc: List[Foo] = List.empty): List[Foo] = seq match {
////  case Nil => acc
////  case head :: tail if head.live =>
////    (acc :+ head) ::: tail.map(_.copy(live = head.live))
////  case head :: tail =>
////    correctLiveFlag(tail, acc :+ head)
////}
//
//
//correctLiveFlag(s1)
//correctLiveFlag(s2)
//correctLiveFlag(s3)
//correctLiveFlag(s4)
//correctLiveFlag(s5)
//correctLiveFlag(s6)
//correctLiveFlag(s7)
//correctLiveFlag(s8)
//correctLiveFlag(s9)










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

val dddd = Seq(
  ChangesTableEntry(parse("2020-07-07").atStartOfDay(), false, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2020-07-07").atStartOfDay(), true,  Some(Right(parse("2020-01-01")))),
  ChangesTableEntry(parse("2020-07-07").atStartOfDay(), false, Some(Right(parse("2020-07-07"))))
)


re



//******** W20 ****************************************************

//2020-01-01T00:00 to 2020-01-02T00:00
//CHANGES W20 1 record: W20 - false
val a1_false = Seq(
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), false, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), false, Some(Right(parse("2020-01-01")))))
//
////CHANGES W20 1 record W20 - true
val a2_true = Seq(
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), true, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), false, Some(Right(parse("2020-01-01")))))
//
//CHANGES W20 1 records W20 - true
val a3_true = Seq(
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), true, Some(Right(parse("2020-01-01")))),
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), true, Some(Right(parse("1970-01-01")))))

//CHANGES W20 1 record W20 true
val a4_true = Seq(
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), false, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2020-01-01").atStartOfDay(), true, Some(Right(parse("2020-01-01")))))
//
//
////******** ENDLESS ************************************************
//
////2019 - 03 - 02 T00: 00 to 2019 - 05 - 01 T23: 59
////CHANGES 1 record: endless - false
val a5_false = Seq(
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), false, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), false, Some(Right(parse("2020-01-01")))))

//CHANGES 1 record endless - true
val a6_true = Seq(
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), true, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), true, Some(Right(parse("2020-01-01")))))

//CHANGES 1 record endless - true
val a7_true = Seq(
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), true, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2019-04-01").atStartOfDay(), false, Some(Right(parse("2020-01-01")))))

//2019 - 03 - 02 T00: 00 to 2019 - 05 - 01 T23: 59
//CHANGES 1 record endless false
val a9_false = Seq(
  ChangesTableEntry(parse("2019-03-31").atStartOfDay(), false, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2019-03-31").atStartOfDay(), true, Some(Right(parse("2020-01-01")))))

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

val from = parse("2019-03-02")
val to = parse("2020-01-02")

implicit

def check2(entries: Seq[ChangesTableEntry]): Seq[ChangesTableEntry] = {
  entries
    .sorted
    .filter(_.validity.flatMap(_.toOption).exists(_.isBefore(to)))
    .toList match {
    case Nil => List.empty
    case l@List(a) => l
    case List(a, b) =>
      if (b.live) List(b)
      else List(b.copy(live = a.live))
    case l =>
      l.groupBy(_.date)
        .flatMap {
          case (_, entries) =>
            val e = entries.sortBy(_.date)(ChangesTableEntry.localDateOrdering2)
            e.foldLeft(e.headOption) {
              (acc, entry) =>
                if (entry.validity.flatMap(_.toOption).contains(entry.date.toLocalDate)) Some(entry)
                else acc }
        }.toList
  }
}

val a1 = check2(a11_false_true)
val a2 = check2(a12_true_false)
val a3 = check2(a13_true_true)
val a4 = check2(a14_false_true)

println(
  s"""
     | ***** 1 *******
     | false/true
     | ${a1.mkString("\n")}
     |
     | ***** 2 *******
     | true/false
     | ${a2.mkString("\n")}
     |
     | ***** 3 *******
     | true/true
     | ${a3.mkString("\n")}
     |
     | ***** 4 *******
     | false/true
     | ${a4.mkString("\n")}
   """.stripMargin)

//check2(a14_false_true).mkString("\n")
//println("***** 2 *******")
//check2(a13_true_true).mkString("\n")
//println("***** 3 *******")
//check2(a11_false_true).mkString("\n")


//println("***** 1 *******")
//check2(a1_false)
//println("***** 2 *******")
//check2(a2_true)
//println("***** 3 *******")
//check2(a3_true)
//println("***** 4 *******")
//check2(a4_true)
//println("***** 5 *******")

//def check(entries: Seq[ChangesTableEntry]) = {
//  import ChangesTableEntry._
//  entries match {
//    case xs =>
//      xs.groupBy(_.validity).flatMap {
//        case (_, entries) => entries.sortBy(_.date).foldLeft(entries.headOption) {
//          (acc, current) =>
//
//            println(
//              s"""
//                 | current: $current
//                 | acc: ${acc.mkString("\n")}
//           """.stripMargin)
//
//            if (current.validity.flatMap(_.toOption).contains(current.date.toLocalDate)) {
//              Some(current)
//            } else None
//        }
//      }.toList
//  }
//}
//
//println(
//  s"""
//     | bbb: ${check(a14_false_true)}
//   """.stripMargin)
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





