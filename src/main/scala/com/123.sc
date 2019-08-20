import java.time.{LocalDate, LocalDateTime}
import java.time.LocalDate.parse
case class ChangesTableEntry(changed: LocalDate,
                             live: Boolean,
                             validity: Option[Either[String, LocalDate]])









/// asdasdasdasd ////

val ddd =parse("2020-01-01")
def sort(seq: Seq[])

seq match {
  case Nil => List.empty
  case one :: Nil => List(one)
  case l @ _ :: _ :: Nil => l sorted match {
    case List(a, b) => (a, b) match {
      case ("a", "b") => "1"
      case ("b", "a") => "2"
    }
  }
  case xs => group by variant headOption for each part
}

//List("b", "a", "c") match {
//  case l @ _ :: _ :: Nil => l match {
//    case List(a, b) => (a, b) match {
//      case ("a", "b") => "1"
//      case ("b", "a") => "2"
//    }
//  }
//  case _ => "---"
//}

