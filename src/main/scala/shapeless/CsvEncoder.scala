package shapeless
import shapeless._


case class Animal(a: String, b: Int)
case object Animal {

  import CsvEncoder._

  implicit val animal: CsvEncoder[Animal] =
    instance(animal => List(animal.a, animal.b.toString))

}
case class Person(a: String, b: Int)

trait CsvEncoder[A] {
  def encode(value: A): List[String]
}

object CsvEncoder {
  def instance[A](fun: A => List[String]): CsvEncoder[A]  =
    new CsvEncoder[A] {
      def encode(value: A): List[String] = fun(value)
    }
}
