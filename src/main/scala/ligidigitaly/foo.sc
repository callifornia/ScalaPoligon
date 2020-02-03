
sealed trait Json
  case class JsObject(get: Map[String, Json]) extends Json
  case class JsString(get: String) extends Json
  case class JsNumber(get: Double) extends Json
  case object JsNull extends Json


trait JsonWriter[A] {
  def write(value: A): Json
}
object JsonWriter {
  implicit object StringWriter extends JsonWriter[String] {
    override def write(value: String): Json = JsString(s"json { $value }")
  }
}


object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json = w.write(value)
}

Json.toJson("foo")



















//
//import java.time.OffsetDateTime
//import java.time.format.DateTimeFormatter
//val now = OffsetDateTime.now()
//
//// LN_POX20_NESP0001
//val seq = Seq(
//  "LN_POX20_NESP0001",
//  "LN_POX20_NESP0004",
//  "LN_POX20_NESP0002",
//  "LN_POX20_NESP0003",
//  "LN_POX20_NES0001",
//  "LN_POX20_NESPO0001",
//  "LN_POX10_NESP0001")
//val numberToSearch = "LN_POX20_NESP"
//val regex = s"^$numberToSearch[0-9]{4}".r
//
//seq
//  .filter(_.matches(regex.regex))
//  .sorted
//  .map(_.stripPrefix(numberToSearch))
//
//seq
//  .filter(_.matches(regex.regex))
//  .map(_.stripPrefix(numberToSearch))
//  .map(_.toInt + 1)
////  .map(n => f"$n%04d")
////  .map(_.prependedAll(numberToSearch))
//
//
//
//
//
//
//
//
//foundedNumber.stripPrefix(numberToSearch)
//
//
//
////now.format(DateTimeFormatter.ofPattern("uu"))
////"asd"
//
//// solution for NUMBERS
////val str = "EX3000_1568813040894"
////val str = "E"
////
////val numberFromDb = "0001"
////val actualNumber = numberFromDb.toInt + 9999
////val result = f"$actualNumber%05d"
////
////
////
////implicit class IntOps(value: String) {
////  def increase() = value + 1
////  def interpolate(sumbol: Char, len: Int): String = ???
////}
