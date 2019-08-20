package polimorphism

import polimorphism.module.{Cat, Describer, Dog}

object Main {
  def main(args: Array[String]): Unit = {
    /*
    1) ad-hoc polymorphism
       - same methods name but different signature (arguments)
          ad_hoc("a")
          ad_hoc(2)

          def ad_hoc(a: Int): Int = a + 1
          def ad_hoc(a: Int, b: Int): Int = a + b
          def ad_hoc(a: String): String = a.toString

       Takes into account only Input general type (exclude inside type) and return type

       works                                                      at compile time
        def combine(l: List[Int]): Int = l.head                   List => Int
        def combine(l: List[String]): String = l.head             List => String

       does't work
        def combine(l: List[Int]): String = l.head.toString       List => String
        def combine(l: List[String]): String = l.head             List => String

        Another case:
          trait Animal
          case class Dog(v: String = "") extends Animal
          case class Cat(v: String = "") extends Animal

          def print(a: Animal) = println("animal")
          def print(a: Cat) = println("cat")
          def print(a: Dog) = println("dog")

          print(a)
          print(b)
          print(c)

          Output:
            animal
            animal
            cat

     2) parametric polymorphism
        generic functions OR data type
          - works independetly of type
          - provide type safety

          Example of container:
            case class Box[T](value: T) {
              val peek: T = value
            }

            val b: Box[Int]  = new Box(3)
            val r: Int = b.peek

            without typing
            case class Box(value: Any) {
              val peek: Any = value
            }

            val b: Box[Int]  = new Box(3)
            val r: Any = b.peek

            We loose type ^^


          Example of functions:
            def store[T](lists: List[T]): Option[T] = lists match {
              case x :: xs => Some(x)
              case Nil => None
            }

            val result: Option[Int] = store(List(1,2,3))

            going to be translated into:
            def store[Int](lists: List[Int]): Option[Int] = lists match {
              case x :: xs => Some(x)
              case Nil => None
            }


     3) inclusion polymorphism OR Subtyping.
        - Allows to use subtype S of T whenewere a T required

        trait Animal
        Cat <: Animal
        Dog <: Animal

        Invariance:
          trait Box[T](element: T) {
            def peek(): T = element
          }
          no relationship between Box[Animal] and Box[Cat]


        Covariant:
          trait Box[+T](element: T) {
            def peek(): T = element
          }

          Box[Animal] is a supper type for Box[Cat]
          Box[Animal] is a supper type for Box[Dog]
          Useful if we do have Box for read purpose. We are not going to insert any value inside already existed box.


        Contravariant:
          trait Box[-T] {
            def put(element: T): String
          }
          Useful when we need to put something into the Box.
          Also we can say is that we can put Dog into Box[Animal]
          Also we can say is that we can use Box[Animal] whenever we expect Box[Dog]


     4) Type Bounds
          [A <: B] - A must be a subtype of B
          [A >: B] - A must be a supertype of B

     5) Type classes
          5.1) Ordering implementation
              trait Ordering[T] {
                def compare(a: T, b: T): Int
              }

              case class Cat(age: Int, name: String)
              object Cat {
                implicit object sortingByAge extends Ordering[Cat] {
                  def compare(a: Cat, b: Cat): Int = a.age - b.age
                }
              }

              - have multiple/different implementation per type


          5.2) Describe implementation on type class

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

    */
  }
}








