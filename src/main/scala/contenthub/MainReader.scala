package contenthub

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.CustomHeader
import akka.http.scaladsl.model.{HttpHeader, HttpMethods, HttpRequest, Uri}
import akka.stream.ActorMaterializer

import scala.io.Source
import settings._

import scala.util.Failure

object MainReader {
  def main(args: Array[String]): Unit = {
    implicit val ac = ActorSystem("foo")
    implicit val ex = ac.dispatcher

    Source
      .fromResource(fileName)
      .getLines
      .toSeq
      .filter(_.trim.nonEmpty)
      .printlnFoo(_.size + " LOL ")


//    val httpResponse = Http().singleRequest(HttpRequest(
//      uri = Uri.apply(url("0hhat3s")),
//      method = HttpMethods.GET).withHeaders(headers))(ActorMaterializer())
//
//    Thread.sleep(10000)
  }
}


object settings {

  val xApiKey = "YmE1MjBiMDYzNWU4ODE0ZTg3Y2RkZTNiMTg1YThiMGFmMDdjOGQ0NzA0Yjc5YzhlMzdjMDBhOWEwNmRjMTM1ZA=="
  val fileName = "contenthub-ekms.txt"

  val headers: HttpHeader = XApiKey(xApiKey)

  val url: String => String = mhid =>
    s"http://contenthub.staging.thomascook.io/" +
      "accommodation?pageSize=10&" +
      "referenceCodeType=MASTER_HOTEL_ID&" +
      s"referenceCodeValue=${mhid}"

  val urlSellingCode: String => String = id => s"" +
    s"http://contenthub.staging.thomascook.io/accommodation/" +
    s"$id" +
    s"/sellingCode"

  case class XApiKey(key: String) extends CustomHeader {
    override def name() = "X-API-KEY"
    override def value(): String = key
    override def renderInRequests(): Boolean = true
    override def renderInResponses(): Boolean = false
  }

  implicit class Foo[A](value: A) {
    def printlnFoo(f: A => Any): A = {
      println(
        s"""
           | ${Console.GREEN}
           | ${f(value)}
           | ${Console.RESET}
         """.stripMargin)
      value
    }
  }
}