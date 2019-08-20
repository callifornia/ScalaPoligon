package polimorphism

object module {

  // type class
  trait Describable[T] {
    def describe(t: T): String
  }

  // interface to use
  object Describer {
    def apply[T](implicit d: Describable[T]): Describable[T] = d
  }

  // type class implementations
  implicit val catDescribed = new Describable[Cat] {
    def describe(t: Cat): String = s"Cat described: $t"
  }

  implicit val dogDescribed = new Describable[Dog] {
    def describe(t: Dog): String = s"Dog described: $t"
  }


  // usage
  Describer[Cat].describe(Cat(1, "cat"))
  Describer[Dog].describe(Dog(2, "dog"))

  case class Cat(age: Int, name: String)
  case class Dog(age: Int, name: String)

}
