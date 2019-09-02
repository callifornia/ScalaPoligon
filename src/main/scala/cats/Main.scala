package cats

import cats.effect.{ContextShift, IO}
import cats.effect.Async

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object Main {
  def main(args: Array[String]): Unit = {
    implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)
    println(s"Inside Future: ${Thread.currentThread()}")
    lazy val fromFuture = Future{
      println(s"Inside Future: ${Thread.currentThread()}")
      "Some string from future "
    }

    val result = IO.fromFuture(IO(fromFuture)).map(_+123123)
    println(s"${result.unsafeRunSync()}")

  }

  def asyncExample = {
    println(s"Inside Future: ${Thread.currentThread()}")
    lazy val fromFuture = Future{
      println(s"Inside Future: ${Thread.currentThread()}")
      "Some string from future "
    }
    lazy val result = Async[IO].async[String]{ cb =>
      println(s"Inside Async: ${Thread.currentThread()}")
      fromFuture.onComplete{
        case Success(value) => cb(Right(value + 123))
        case Failure(ex) => cb(Left(ex))
      }
    }.unsafeRunSync()
    println(s"r: $result")
  }

  def readFromConsolePart = {
    def readLine: IO[String] = IO(scala.io.StdIn.readLine)
    def printLine(s: String): IO[Unit] = IO(println(s))
    val r = for {
      _ <- printLine("What is your name?")
      line <- readLine
      _ <- printLine(s"Hi $line")
    }  yield ()

    r.unsafeRunSync()
  }

  def futureWithLazyValBecomesReferencialTransparency = {

    println(s"Future declarations: [${Thread.currentThread()}]")
    lazy val f1 = Future {
      println(s"f1: ${Thread.currentThread()}")
      1
    }
    lazy val f2 = Future {
      println(s"f2: ${Thread.currentThread()}")
      2
    }
    println(s"End future declarations: [${Thread.currentThread()}]")
    Thread.sleep(3000)
    println(s"Going to start for comprehension: [${Thread.currentThread()}]")
    val result: Future[Int] = for {
      r1 <- f1
      r2 <- f2
    } yield r1 + r2
    println(s"Finish for comprehension: [${Thread.currentThread()}]")
    val toPrint = Await.result(result, 6.seconds)
    println(s"Result: $toPrint")
  }
}
