case class Labeling(a: Int)
case class Concept(a: Int)
case class D(a: Int)
case class E(a: Int)
case class F(a: Int)


//val mapConcept:       Labeling => Concept        = a => Concept(a.a)
val mapKsps:          (Concept, Labeling) => Concept        = (a, b) => Concept(a.a + b.a)
val mapConcept:       (Concept, Labeling) => Concept        = (a, b) => Concept(a.a + b.a)



mapConcept andThen mapKsps




//mapKsps.tupled(Concept(1), Labeling(2))
//
//mapKsps.curried

/*

// A -> B -> C
mapConcept andThen mapKsps.curried apply A(2) apply(B(3))
mapConcept andThen mapKsps.curried
type Input = (A, B)

val mapConcept1:       Input => C        = (input: Input) => {println("FIRST"); ??? }
val mapKsps1:          (D, E) => F        = (a, b) => {println("SECOND"); ??? }


//Function.chain(Seq(mapConcept1))
*/




