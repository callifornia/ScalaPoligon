package checkAvro

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import scala.reflect.{ClassTag, classTag}
import shapeless.syntax.typeable._
import scala.reflect.runtime.universe._
import scala.reflect.ClassTag

trait EitherOps extends CustomLogger {
  implicit class Foo[T](a: T)  {
    def asEither() = {
      log.info(s"****** boo 1".stripMargin)
    }
  }
}

trait CustomLogger {
  val log = Logger(this.getClass)
}
