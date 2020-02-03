package traits
import play.api.libs.json._

object sap {

  trait Foo[A] { self: A =>
    def convert[T](implicit f : A => T): T = f(self)
  }

  trait JsonCreateOpts {
    implicit class JsonCreateOps[T](v: T) {
      def toJson()(implicit w: Writes[T]) = Json.toJson(v)
    }
  }

  trait PascaleCasePropertyName extends JsonCreateOpts {
    implicit val config = JsonConfiguration(JsonNaming.PascalCase)
  }

  case class SalesOrderToSAP(nameID: String,
                             incoterms_Location: String,
                             resident: Seq[Resident])

  object SalesOrderToSAP extends PascaleCasePropertyName {
    implicit val rootElementName: String = "salesOrder"
    implicit lazy val sorw: Writes[SalesOrderToSAP] = Json
      .writes[SalesOrderToSAP]
      .transform { ob: JsObject  =>
        Json.obj("SalesOasdrder" -> ob)
      }
  }

  case class Resident(nameBlob: String,
                      age: Int,
                      role: Option[String])

  object Resident extends PascaleCasePropertyName{
    implicit lazy val rcrw: OFormat[Resident] = Json.format[Resident]
  }
}
