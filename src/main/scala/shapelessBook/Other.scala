package shapelessBook
import shapeless.{Generic, HNil, LabelledGeneric}
//import Implicits._
import shapeless.HList

object Other {
  type AdditionalParams


  case class Event(someName: String, a: Int)
  case class RequestCpi(someName: String, a: Int)


  trait Migrator[A, B] {
    def migrate(a: A): B
  }

  object Migrator {

    // String => Migrator[Event, RequestCpi]
    implicit def EventMigrator(uuid: String) = new Migrator[Event, RequestCpi] {
      def migrate(a: Event): RequestCpi = RequestCpi(a.someName + uuid, a.a)
    }
  }

  implicit class MigrateOps[A](value: A) {
    def migrate[R]()(implicit conv: Migrator[A, R]) =
      conv.migrate(value)
  }




//
//  implicit val toRequest: Eve => (String, Int) => Result =
//    (event: Eve) =>
//      (uuid: String, age: Int) =>
//        Result(event.a, uuid)
//
//
//  implicit val toRequest2: (Eve, String, Int) => Result =
//    (event, uuid, age) => Result(event.a, uuid)
//
//
//  implicit val toRequest23: PartialFunction[(Eve, String, Int), Result] = {
//    case (event, uuid, age) => Result(event.a, uuid)
//  }
//
//
//  implicit val toRequest3: (Eve, String) => Result =
//    (event, uuid) => Result(event.a, uuid)
//
//
//  implicit class ConOps[A](value: A) {
////    def as[R](a: Any*)(implicit vv: A => Any => R): Any => R = vv(value)
//    def as[R]()(implicit vv: A => Any => R): Any => R = vv(value)
//  }
//
//  case class Eve(a: String, b: Int)
//  case class Result(a: String, b: String)

}
