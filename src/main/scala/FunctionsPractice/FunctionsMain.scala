package FunctionsPractice

object FunctionsMain {
  def main(args: Array[String]): Unit = {
    case class A(a: Int)
    case class B(a: Int)
    case class C(a: Int)
    case class D(a: Int)
    case class E(a: Int)
    case class F(a: Int)
    case class G(a: Int)

    val mapConcept:       A => C        = a => C(a.a)
    val mapKsps:          (C, B) => C        = (a, b) => C(a.a + b.a)


    // A -> B -> C
    mapConcept andThen mapKsps.curried apply A(2) apply(B(3))
    mapConcept andThen mapKsps.curried
    type Input = (A, B)

    val mapConcept1:       Input => C        = (input: Input) => {println("1"); C(1) }
    val mapKsps1:          D => F        = (a) => {println("2"); F(3) }
    val mapKsps2:          F => G        = (a) => {println("3"); G(3) }


//    Function.chain(Seq(mapKsps1, mapKsps2))
  }
}
