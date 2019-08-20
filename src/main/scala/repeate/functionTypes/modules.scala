package functionTypes

object modules {

  trait Animal
  case object Cat extends Animal
  case object Dog extends Animal

  trait Printer[-T] {
    def print(a: T)
  }

  object Printer {
    implicit object CatPrinter extends Printer[Cat.type] {
      def print(a: Cat.type): Unit = println("This is a Cat printer")
    }
    implicit object DogPrinter extends Printer[Dog.type] {
      def print(a: Dog.type): Unit = println("This is a Dog printer")
    }
    implicit object AnimalPrinter extends Printer[Animal] {
      def print(a: Animal): Unit = println("This is an Animal printer")
    }
  }
}
