package shapelessBook

import shapeless.{HList, ::, HNil}


trait Converter[A] {
  type R
  def convert(a: A): R
}

object Converter {

  import shapeless.Generic

//  def pure[A, B](f : A* => B): Converter[A] = {
//    new Converter[A] {
//      type R = B
//      def convert(a: A): R = f(a)
//    }
//  }


  def pure2[MainClass, AdditionalParams <: HList, ReturnType](
                                                               f: (MainClass, AdditionalParams) => ReturnType): Generic[MainClass] = {

    ???
  }


//  implicit val toSales2 =
//    pure2 { (event: EventMessage, uuid: String) =>
//      RequestCpi(event.a, uuid)
//    }
//
//  implicit val toSales =
//    pure2 { (event: EventMessage, uuid: String, age: Int) =>
//      RequestCpi(event.a, uuid)
//    }




//  implicit val toSpiRequest: Converter[EventMessage] =
//    pure(message => RequestCpi(message.a, "converted"))
}


trait CsvEncoder[A] {
  def encode(value: A): List[String]
}

object CsvEncoder {

  import shapeless.Generic

  def pure[A](func: A => List[String]): CsvEncoder[A] =
    new CsvEncoder[A] {
      def encode(value: A): List[String] = func(value)
    }


  implicit val stringEncoder: CsvEncoder[String] =
    pure(str => List(str))

  implicit val intEncoder: CsvEncoder[Int] =
    pure(num => List(num.toString))

  implicit val booleanEncoder: CsvEncoder[Boolean] =
    pure(bool => List(if (bool) "yes" else "no"))

  implicit val hEmpty: CsvEncoder[HNil] =
    pure(_ => Nil)

  implicit def hTail[H, T <: HList](
                                     implicit
                                     h: CsvEncoder[H],
                                     t: CsvEncoder[T]
                                   ): CsvEncoder[H :: T] =
    pure {
      case head :: tail =>
        h.encode(head) ++ t.encode(tail)
    }


  // general implicit guy
  implicit def genEncoder[A, L](
                                 implicit
                                 gen: Generic[A] {type Repr = L},
                                 enc: CsvEncoder[L]
                               ): CsvEncoder[A] = {
    pure { value =>
      enc.encode(gen.to(value))
    }

  }
}

case class EventMessage(a: String)
case class RequestCpi(a: String, b: String)

case class Employee(name: String)
case class IceCream(name: String)