
import org.scalactic.{Bad, Good, Or, Many, One, Every}
import scala.Option

val good1: Or[String, Int] = Good("a")
val good2: Or[String, Int] = Good("b")
val good3: Or[String, Int] = Good("c")
val b1: Or[String, Int] = Bad(2)
val b2: Or[String, Int] = Bad(2)
val b3: Or[String, Int] = Bad(3)

