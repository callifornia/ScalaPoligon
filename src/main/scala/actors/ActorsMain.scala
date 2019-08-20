package actors

import akka.actor.{Actor, ActorSystem, Props}

object ActorsMain {
  def main(args: Array[String]): Unit = {
    val as = ActorSystem("foo")
    val fooActor = as.actorOf(Foo("Hey there"))
    fooActor ! "Hey there"

  }
}

class FooActor(name: String) extends Actor {
  override def receive: Receive = {
    case a: String => println(s"[$name] got a String: $a")
    case b: Int => println(s"[$name] got an int: $b")
    case _ =>  println("[$name] got an SHIT...")
  }
}

trait FooState {
  val name: String
  lazy val fooActor = Props(classOf[FooActor], name)
}

case class Foo(name: String) extends FooState
case object Foo {
  def apply(name: String): Props = new Foo(name).fooActor
}