//package Option
//
//object OptionImplementtion {
//  def main(args: Array[String]): Unit = {
//
//    val b: Option[Int] = Some(1)
//    val b1: Option[Int] = None
//
//    val a: aOption[Int] = aSome(1)
//    val a2: aOption[Int] = aNone
//    println(
//      s"""
//         | ${a.toList}
//         | ${a2.toList}
//       """.stripMargin)
//  }
//}
//
//
//case object aNone extends aOption[Nothing] {
//  override def get(): Nothing = throw new NoSuchElementException("aNone contains no value")
//  override def isDefined() = false
//  override def isEmpty(): Boolean = !isDefined()
//}
//case class aSome[+T](value: T) extends aOption[T] {
//  override def get(): T = value
//  override def isDefined(): Boolean = true
//  override def isEmpty(): Boolean = !isDefined()
//}
//
//sealed trait aOption[+T] {
//  def get(): T
//  def isDefined: Boolean
//  def isEmpty: Boolean
//
//  def map[R >: T](f: T => R): aOption[R] = if (isDefined) aSome(f(this.get())) else aNone
////  def getOrElse[R >: T](value: => R): R = if (isEmpty) value else this.get()
////  def orElse[R >: T](value: => R): aOption[R] = if (isEmpty) aSome(value) else this
////  def filter(f: T => Boolean): aOption[T] =
////    if (isEmpty) aNone
////    else if (f(this.get())) this else aNone
////  def toList: List[T] = if (isEmpty) List.empty[T] else List(this.get())
//}