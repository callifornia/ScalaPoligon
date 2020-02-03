package checkAvro
import java.util.UUID

import Holder._
import cats.Monad
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import com.sksamuel
import com.sksamuel.avro4s.AvroSchema
import java.io.File

object Main {

  def main(args: Array[String]): Unit = {
    import com.sksamuel.avro4s.{AvroInputStream, AvroName, AvroOutputStream}

    import scala.io.Source
    case class Provider(a: Int, b: Option[String])

    @AvroName("Provider")
    case class Consumer(a: Int, b: Option[String], c: Option[String])

    val providerSchema = AvroSchema[Provider]
    println(providerSchema.toString)

    val os = AvroOutputStream.data[Provider].to(new File("pizzas.avro")).build(providerSchema)
    os.write(Provider(123, None))
    os.flush()
    os.close()



    val consumerSchema = AvroSchema[Consumer]
    val is = AvroInputStream.data[Consumer].from(new File("pizzas.avro")).build(consumerSchema)
    val pizzas = is.iterator.toSet
    println(s"asdasd: $pizzas")
    is.close()
  }
}


object Holder {
  class Service1 extends EitherOps {
    def foo1() = {
      val s2 = new Service2
      val s3 = new Service3
      s2.asEither()
      s3.asEither()
    }
  }

  class Service2 extends EitherOps {
    def foo2() = {
      val s1 = new Service1
      val s3 = new Service3
      s1.asEither()
      s3.asEither()
    }
  }
  class Service3
}