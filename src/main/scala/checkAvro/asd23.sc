import java.time.LocalDate
import cats.implicits._

case class D(instoreDate: Option[LocalDate])
case class UpdateSalesOrder(deliveryInformation: Option[D])
case object Error123
import cats.syntax.all._
import cats.data._
import cats.implicits._
import cats.Comparison._


val inFuture = (e: LocalDate) => {
  e.isAfter(LocalDate.now()) && !e.isEqual(LocalDate.now())

}

val instoreDateInFuture = (u: UpdateSalesOrder) => u.deliveryInformation
  .flatMap(_.instoreDate)
  .filter(inFuture)
  .toRight(Error123)
  .map(_ => "Goood")


instoreDateInFuture(UpdateSalesOrder(D(LocalDate.now().minusDays(3).some).some))
//println("***")
instoreDateInFuture(UpdateSalesOrder(D(LocalDate.now().some).some))
//println("***")
instoreDateInFuture(UpdateSalesOrder(D(LocalDate.now().plusDays(13).some).some))
