package implicits

import implicits.some_module.Converter
import implicits.some_module._

object ImplicitsMain {
  def main(args: Array[String]): Unit = {
    (0 to 20)
      .map(createPerson)
      .map(check(_))
      .mkString("\n")
      .printThatShit()
  }

  def check[A : Converter](a: A) = {
    val ex = implicitly[Converter[A]]
    ex.convert(a)
  }

  lazy val createPerson = (a: Int) => Person(a.toString)
}


