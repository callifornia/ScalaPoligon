import java.time.LocalDate.parse
import java.time.{LocalDate, LocalDateTime}

case class ChangesTableEntry(date: LocalDateTime,
                             live: Boolean,
                             validity: Option[Either[String, LocalDate]]) {

  val isChangeDateBelongToVariant: Boolean = validity.flatMap(_.toOption).contains(date.toLocalDate)
}

object ChangesTableEntry {
  implicit val localDateOrdering: Ordering[Option[Either[String, LocalDate]]] =
    Ordering.by(_.flatMap(_.toOption).map(_.toEpochDay).getOrElse(LocalDate.now().toEpochDay))



  implicit val localDateOrdering2: Ordering[LocalDateTime] = Ordering.by(_.toLocalDate.toEpochDay)

  implicit val sortByValidity: Ordering[ChangesTableEntry] = Ordering.by(_.validity)
}

val false_true_false = Seq(
  ChangesTableEntry(parse("2020-07-07").atStartOfDay(), false, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2020-07-07").atStartOfDay(), true, Some(Right(parse("2020-01-01")))),
  ChangesTableEntry(parse("2020-07-07").atStartOfDay(), false, Some(Right(parse("2020-07-07")))))


val true_false_false = Seq(
  ChangesTableEntry(parse("2019-07-07").atStartOfDay(), true, Some(Right(parse("1970-01-01")))),
  ChangesTableEntry(parse("2019-07-07").atStartOfDay(), false, Some(Right(parse("2020-01-01")))),
  ChangesTableEntry(parse("2019-07-07").atStartOfDay(), false, Some(Right(parse("2020-07-07")))))


def check2(entryToCheck: Seq[ChangesTableEntry]) = {
  val sortedEntries = entryToCheck.sorted
  sortedEntries.foldLeft(sortedEntries.headOption){ (acc, e) => e match {
    case e if e.isChangeDateBelongToVariant && e.live => Some(e)
    case e if e.isChangeDateBelongToVariant => acc.map(a => e.copy(live = a.live))
    case e if acc.map(_.live).getOrElse(false) =>
    case _ => acc
  }
  }

}

check2(false_true_false)
//
//
//
//
//def check(entryToCheck: Seq[ChangesTableEntry]) = {
//  val sortedEntries = entryToCheck.sorted
//  sortedEntries
//    .find(_.isChangeDateBelongToVariant)
//    .orElse(sortedEntries.headOption)
//    .flatMap { entry =>
//      if (entry.live) Some(entry)
//      else {
//        sortedEntries
//          .takeWhile(_ != entry)
//          .find(_.live)
//          .map(e => entry.copy(live = e.live))
//          .orElse(Some(entry))
//      }
//    }
//}
//
//check(false_true_false)


