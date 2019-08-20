package functionalPatterns

object MainPatterns {
  def main(args: Array[String]): Unit = {
    /*


    links:
      https://hackernoon.com/functors-and-applicatives-b9af535b1440

    FUNCTOR
      It's like a box. With map method and 2 laws
      trait F[A]{
        def map[B](f: A => B): F[B]
      }

      1) IDENTITY law:
        functor.map(identity) == functor
        identity - is a function defined in scala.Predef.identity()
      2) COMPOSITION law:
        functor.map(f).map(g) == functor.map(f andThen g)

      Some example. We do have two implementation of our functor (list and option):
        def main(args: Array[String]): Unit = {
          listFunctor.fmap(List(1,2,3))(_+1).printlnFoo()
          optionFunctor.fmap(Some(1))(_+1).printlnFoo()
        }

        def listFunctor = new Functor[List] {
          override def fmap[A, B](a: List[A])(f: A => B) = a map f
        }

        def optionFunctor = new Functor[Option] {
          override def fmap[A, B](a: Option[A])(f: A => B) = a map f
        }

        trait Functor[F[_]] {
          def fmap[A, B](a: F[A])(f: A => B): F[B]
        }



    MONAD
       Monad is not a class or a trait; monad is a concept.
       Every “wrapper” that provides us with our two beloved operations,
        unit, and flatMap, is essentially a monad
        (well, it’s not really enough to just provide methods with those names,
        they, of course, have to follow certain laws, but we’ll get to that).
      trait F[A]{
        def unit(a: A): F[A]
        def flatMap[B](f: A => F[B]): F[B]
      }

      Monads come with two operations:
        - unit (also known as identity or return or pure)
          Unit performs the wrapping part. If we give it a value of type A,
          it will return a value of type F[A]
        - flatMap (also known as bind)

      1) IDENTITY law:
        - left identity:                    right identiy:
          unit(a).flatMap(f) == f(a)        m.flatMap(unit) == m
          List(1).flatMap(f) == f(1)

      2) Associativity law:
        m.flatMap(f).flatMap(g) == m.flatMap(e => f(e).flatMap(g))


    APPLICATIVE FUNCTOR
      1. way to define
        unit[A](a: A): F[A]
        apply[A, B](func: F[A])(f: F[A → B]): F[B]

        Example(apply method in Future is not defined, it's a pseudo Scala code):
          val f = (x: Int) => (y: Int) => x + y
          val r1: Future[Int => Int] = Future(42).map(f)
          Future(10).apply(r1) // results in Future(52)

        Example(by using scalaz library):
          val f1 = Future(12)
          val f2 = Future(2)
          val f3 = Future(3)

          val calculate: Int => Int => Int => Int = a => b => c => a + b + c

          val area: Future[Int] = f1.<*>(f2.<*>(f3.<*>(Future(calculate))))
          println(Await.result(area, 3.seconds))

      2. way to define
        unit the same
        map[A,B](func: F[A])(f: A => B): F[B] -> functor map method
        product[A, B](fa: F[A], fb: F[B]): F[(A, B)]

        Example: by using scalaz
          val f1 = Future(12)
          val f2 = Future(2)
          val f3 = Future(3)

          val calculate: (Int, Int, Int) => Int = (a, b, c) => a + b + c

          val area: Future[Int] = (f1 |@| f2 |@| f3)(calculate)
          println(Await.result(area, 3.seconds))

          // the same with zip:
          val area: Future[Int] = (f1.zip(f2).zip(f3)).map {
            case ((a, b), c) => calculate(a, b, c)
          }

          In Future scope we can say:
            - Applicative Functor concurrent execution,
            - Monada is sequences execution. Using the same context.

          Example monada (sequencial execution):
            lazy val f1 = Future{ Thread.sleep(1000); 1}
            lazy val f2 = Future{ Thread.sleep(3000); 1}
            lazy val f3 = Future{Thread.sleep(1000); 1}

            val result = for {
              a <- f1
              b <- f2
              c <- f3
            } yield a + b + c

            println(s"Result: ${Await.result(result, 5.seconds)}")


           Example applicative functor (paralel execution):
            lazy val f1 = Future{ Thread.sleep(1000); 1}
            lazy val f2 = Future{ Thread.sleep(3000); 1}
            lazy val f3 = Future{Thread.sleep(1000); 1}

            val result = (f1 |@| f2 |@| f3 )((a,b,c) => a + b + c)
            println(s"Result: ${Await.result(result, 5.seconds)}")


      MONOID
        Something that take two arguments with the same type and return result of operation
        with the same type

        monoid itself
          trait Monoid[A]{
            def empty: A
            def combine(a1: A, a2: A): A
          }


        Example (we are going to sum money):
          case class Money(value: Int)
          object Money {
            // implementation of summing money
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





















     */




  }
}
