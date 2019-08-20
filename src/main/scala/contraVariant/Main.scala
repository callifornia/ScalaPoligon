package functionTypes
import functionTypes.modules.Printer.{AnimalPrinter, CatPrinter, DogPrinter}
import modules._
object Main {
  def main(args: Array[String]): Unit = {
    check(CatPrinter)
    check(AnimalPrinter)
  }

  def check(cat: Printer[Cat.type]) = {
    cat.print(Cat)
  }
}
