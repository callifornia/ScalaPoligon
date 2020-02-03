import java.time.format.DateTimeFormatter
import java.time.{LocalTime, OffsetDateTime, ZoneOffset}

(1 to 2).map{ e =>
  if (e % 2 == 0) "a"
  else "b"
}


// 2020-01-29T17:23:57.773Z
// 2020-01-30T12:00
// 2020-01-30T12:00
// 2020-01-30T12:00Z
// 2020-01-30T12:00:00Z
val blo = Some(OffsetDateTime.now().plusDays(1).toLocalDate)
  .map(date => OffsetDateTime.of(date, LocalTime.NOON, ZoneOffset.UTC))

OffsetDateTime.now().plusDays(1)
  .toLocalDate
  .atTime(LocalTime.MIN.atOffset(ZoneOffset.UTC))

val a = DateTimeFormatter.ISO_OFFSET_DATE
val b = DateTimeFormatter.ISO_OFFSET_DATE_TIME
val c = DateTimeFormatter.ISO_OFFSET_TIME
OffsetDateTime.now().plusDays(1).toLocalDate
  .atTime(LocalTime.NOON)
  .atOffset(ZoneOffset.UTC)
  .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)



