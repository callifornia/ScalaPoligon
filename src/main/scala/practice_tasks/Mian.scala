package practice_tasks

import cats.Applicative
import org.scalactic.{Bad, Good, Or}

import scala.Option
import scala.Either
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Mian {
  def main(args: Array[String]): Unit = {

    lazy val someFunction: Int => Option[String] = e => if (e > 2) Some(e.toString) else None
    val tr2 = traverseOption(List(1,2,3))(someFunction)
    println(tr2)

    lazy val someFunctionEither: Int => Either[String, String] = i =>
      if(i > 12) Left(s"Error. $i > 2 was found")
      else Right(i.toString)

    val tr3 = traverseEither(List(1,2,3))(someFunctionEither)
    println(tr3)


    val addTwoNumbers: Int => Int => Int => Int = a => b => c => a + b + c
    val addTwoNumbers2: Int => Int => Int = a => b  => a + b

    val result: Future[Int => Int] = Future(42).map(e => addTwoNumbers2(e))
    val result2: Future[Int => Int => Int] = Future(42).map(e => addTwoNumbers(e))

    println(addTwoNumbers(3)(2))

  }

  def traverseOption[A, B](l: List[A])(f: A => Option[B]): Option[List[B]] = {
    l.foldRight(Option(List.empty[B])){ (e, acc) =>
      (f(e), acc) match {
        case (Some(functionResult), Some(accumulatedList)) => Some(functionResult :: accumulatedList)
        case _ => None
      }
    }
  }

  def traverseEither[E, A, B](l: List[A])(f: A => Either[E, B]): Either[E, List[B]] = {
    l.foldRight[Either[E, List[B]]](Right(List.empty[B])){ (e, acc) =>
      (f(e), acc) match {
        case (Right(functionResult), Right(accumulatedList)) => Right(functionResult :: accumulatedList)
        case (Left(functionResult), _) => Left(functionResult)
        case (_, Left(functionResult)) => Left(functionResult)
      }
    }
  }

//  import cats.Applicative
//  def traverseList[F[_]: Applicative, A, B](l: List[A])(f: A => F[B]): F[List[B]] = {
//    l.foldRight(pure[F](List.empty[B])){ (el, acc) =>
//      f(el).zip(acc).map{
//        case (calculated, accummulator) => calculated :: accummulator
//      }
//    }
//    ???
//  }

}
