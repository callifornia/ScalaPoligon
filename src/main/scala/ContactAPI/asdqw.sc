import java.time.LocalDateTime

import play.api.libs.json.{Json, Writes}


case class Foo(a: String, b: LocalDateTime, c: Int)
object Foo {
  implicit val w: Writes[Foo] = Json.writes[Foo]
}



val f = Foo("asd", LocalDateTime.now(), 123)
Json.toJson(f)





