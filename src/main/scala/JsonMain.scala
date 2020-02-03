import java.util.UUID

import play.api.libs.json.{Format, Json, JsonConfiguration, JsonNaming}
import cats.syntax.either._
import cats.syntax.try_._

import scala.util.Try

object JsonMain {
  def main(args: Array[String]): Unit = {
    val body2 = Json.parse(
      """{
                             "SalesOrderCollection": {
                                 "SalesOrder": {
                                     "ProjectReference": "LN_POS19_X21079",
                                     "ObjectID": "00163E5557D11EEA8BB4FDF710F26820",
                                     "ID": "1586",
                                     "SalesOrderItem": {
                                         "SalesOrderItem": [
                                             {
                                                 "Description": "one more free 2",
                                                 "ID": "10",
                                                 "UUID": "00163e55-57d1-1eea-8bb4-fdf710f28820"
                                             }
                                         ]
                                     },
                                     "UUID": "00163e55-57d1-1eea-8bb4-fdf710f26820"
                                 }
                             }
                         }""")
    lazy val body3 = Json.parse(
      """{
                             "SalesOrderCollection": {
                                 "SalesOrder": 123
                             }
                         }""")
    val res = Try(body2.as[CreateSalesOrderResponse]).toEither.leftMap{ err =>
      println(s"Failed to parse: $body3")
      err
    }
//    println(s"result is: $res")
  }
}




trait PascaleCasePropertyName {
  implicit val config = JsonConfiguration(JsonNaming.PascalCase)
}
case class SalesOrderItem(description: String,
                          ID: String,
                          UUID: UUID)

object SalesOrderItem extends PascaleCasePropertyName {
  implicit val r: Format[SalesOrderItem] = Json.format[SalesOrderItem]
}

case class SalesOrderItems(salesOrderItem: Seq[SalesOrderItem])

object SalesOrderItems extends PascaleCasePropertyName {
  implicit val r: Format[SalesOrderItems] = Json.format[SalesOrderItems]
}

case class SalesOrder(projectReference: String,
                      objectID: String,
                      ID: String,
                      salesOrderItem: SalesOrderItems,
                      UUID: UUID)

object SalesOrder extends PascaleCasePropertyName {
  implicit val r: Format[SalesOrder] = Json.format[SalesOrder]
}

case class SalesOrderCollection(salesOrder: SalesOrder)

object SalesOrderCollection extends PascaleCasePropertyName {
  implicit val r: Format[SalesOrderCollection] = Json.format[SalesOrderCollection]
}

case class CreateSalesOrderResponse(salesOrderCollection: SalesOrderCollection)

object CreateSalesOrderResponse extends PascaleCasePropertyName {
  implicit val r: Format[CreateSalesOrderResponse] = Json.format[CreateSalesOrderResponse]
}