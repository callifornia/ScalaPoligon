package implicitsRepeat

object some_module {

  sealed trait Converter[T] {
    def convert(a: T): ConvertResult
  }


  case class Person(name: String)
  object Person {
    implicit object personConverter extends Converter[Person]{
      def convert(a: Person) = PersonConverted(a)
    }
  }

  trait ConvertResult
  case class PersonConverted(person: Person) extends ConvertResult

  case class Location(name: String)

  implicit class PrintFoo[A](value: A){
    def printThatShit(msg: String = "") = {
      println(s"$msg\nvalues: \n$value")
      value
    }
  }
}
