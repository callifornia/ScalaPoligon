import java.time.LocalDate

val today = LocalDate.now()
val yesturday = LocalDate.now().minusDays(1)
val tommorrow = LocalDate.now().plusDays(1)


val isFuture = (e: LocalDate) =>
  e.isAfter(LocalDate.now()) /*&& !e.isEqual(LocalDate.now())*/

val isFuture2 = (e: LocalDate) =>
  e.isAfter(LocalDate.now())

println(s"""
           | isFuture(yesturday): ${isFuture(yesturday)}
           | isFuture(today): ${isFuture(today)}
           | isFuture(tommorrow): ${isFuture(tommorrow)}
           |
           |""".stripMargin)