package traits

import play.api.libs.json.{JsValue, Json}
import implementation._
import traits.sap.{Foo, Resident, SalesOrderToSAP}

object Main {
  val json: JsValue = Json.parse(
    """
    {
      "name" : "Watership Down",
      "location" : {
        "lat" : 51.235685,
        "long" : -1.309197
      },
      "residents" : [ {
        "name" : "Fiver",
        "age" : 4,
        "role" : null
      }, {
        "name" : "Bigwig",
        "age" : 6,
        "role" : "Owsla"
      } ]
    }
    """)

  val salesOrderEvent = SalesOrderEvent(
    "name_1",
    Seq(Resident("residentName_1", 1, Some("role"))),
    Seq("country_1", "country_2"))

  def main(args: Array[String]): Unit = {
    val service = new DefaultService(Configurator("configurator"))
    service.sendPOSTSalesOrder(salesOrderEvent)
  }
}

object implementation {

  case class SalesOrderEvent(name: String,
                             residents: Seq[Resident],
                             countries: Seq[String]) extends Foo[SalesOrderEvent] {

    def as[T](implicit f: SalesOrderEvent => T) = f(this)
  }

  object SalesOrderEvent {
    implicit val toSap = (s: SalesOrderEvent) => SalesOrderToSAP(s.name, "foo", s.residents)
    implicit val toPost = (s: SalesOrderEvent, ss: String) => SalesOrderToSAP(s.name + ss, "foo", s.residents)
  }

  case class ResidentCountry(residentName: String, country: String)


  class DefaultService(val config: Configurator)
    extends ServiceConfig {

    private def toConsole(msg: JsValue) = println(
      s"""
         | request:
         | $msg
         |""".stripMargin)


    def sendPOSTSalesOrder(salesOrderEvent: SalesOrderEvent) =
      toConsole(salesOrderEvent.convert[SalesOrderToSAP].toJson())

  }

  case class Configurator(value: String)

  trait ServiceConfig {
    def config(): Configurator

    lazy val p1 = config.value + "_p1"
    lazy val p2 = config.value + "_p2"
    lazy val p3 = config.value + "_p3"
    lazy val p4 = config.value + "_p4"
    lazy val p5 = config.value + "_p5"
  }
}

