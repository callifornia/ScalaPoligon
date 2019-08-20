package book.monoid

object Main {
  def main(args: Array[String]): Unit = {
    val list = List(Money(1), Money(2), Money(3), Money(4), Money(5))
    val result = totalMoney(list)
    println(result)
  }

  def totalMoney(xs: List[Money])(implicit m: Monoid[Money]) = xs.foldLeft(m.empty)(m.combine)
}

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




