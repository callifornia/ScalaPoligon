package book.functorAndOther

object Main {
  def main(args: Array[String]): Unit = {
    listFunctor.fmap(List(1,2,3))(_+1).printlnFoo()
  }

  def listFunctor = new Functor[List] {
    override def fmap[A, B](a: List[A])(f: A => B) = a map f
  }

  implicit class Foo[A](value: A) {
    def printlnFoo(msg: String = ""): A = {
      println(s"$value")
      value
    }
  }
}
trait Functor[F[_]] {
  def fmap[A, B](a: F[A])(f: A => B): F[B]
}
