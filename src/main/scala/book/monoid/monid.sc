trait Monoid[A]{
  def empty: A
  def combine(a1: A, a2: A): A
}
case class Money(value: Int)
object Money {
  implicit val sumMoney: Monoid[Money] = new Monoid[Money] {
    override def empty: Money = Money(0)
    override def combine(a1: Money, a2: Money): Money = {
      Money(a1.value + a2.value)
    }
  }
}



def totalMoney(xs: List[Money])(implicit m: Monoid[Money]) = {
  xs.foldLeft(m.empty){(acc, el) => m.combine(acc, el)}
}

lazy val list = List(Money(1), Money(2), Money(3), Money(4), Money(5))

lazy val result = totalMoney(list)
println(result)















