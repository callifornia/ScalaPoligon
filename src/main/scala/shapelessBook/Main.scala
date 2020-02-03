package shapelessBook

object Main {

  import shapeless.Generic
  import shapelessBook.Other.Event

  val employee = Employee("Bill")
  val employee2 = Employee("Employee")
  val iceCream = IceCream("inceCream")
  val evenMessage = EventMessage("inceCream")

  val uuid = "some uuid"
  val age = 2
  val year = 999
  val event = Event("hello there", 123)


  def main(args: Array[String]): Unit = {



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


    //    01.20 - input
    //    01.20              02.20
    //    e.isAfter(LocalDate.now()) && !e.isEqual(LocalDate.now())


//    val event = Event("some event", 123)
//    event.migrate[RequestCpi]("foo")
//
//
//
//
//
//
//    val result = ???
//    println(s"result is: " + result)
  }

  def check[A](v: A)(implicit gen: Generic[A]): A = {
    val converted = Generic[A].from(gen.to(v))
    converted
  }
}
