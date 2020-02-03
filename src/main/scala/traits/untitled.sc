import java.time.OffsetDateTime

case class Foo(a: Int, date: OffsetDateTime)



Seq(
  Foo(1, OffsetDateTime.now().plusYears(2)),
  Foo(2, OffsetDateTime.now().plusYears(1)),
  Foo(3, OffsetDateTime.now().plusYears(3)),
  Foo(4, OffsetDateTime.now()))
  .max