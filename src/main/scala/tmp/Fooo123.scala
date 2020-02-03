package tmp

object Fooo123 {
  implicit val w: String = "asd"
}


object someObject {
  import Fooo123._
  val w123: Int = 3

  def check()(implicit ex: String) = {
    println(ex)
  }


  check()
}