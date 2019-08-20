
//import java.util.{ Date, Calendar }
//import scala.util.{ Try, Success, Failure }

def today = java.util.Calendar.getInstance.getTime
//type Amount = BigDecimal
//
//case class Balance(amount: Amount = 0)
//case class Account(no: String,
//                   name: String,
//                   dateOfOpening: Date,
//                   balance: Balance = Balance())

//trait AccountService {
//  def debit(a: Account, amount: Amount): Try[Account] = {
//    if (a.balance.amount < amount) {
//      Failure(new Exception("Insufficient balance in account"))
//    }
//    else {
//      Success(
//        a.copy(
//          balance = Balance(
//            a.balance.amount - amount)))
//    }
//  }
//
//  def credit(a: Account, amount: Amount): Try[Account] =
//    Success(a.copy(balance = Balance(a.balance.amount + amount)))
//}
//
//object AccountService extends AccountService
//import AccountService._
//
//val a = Account("a1", "John", today)
//a.balance == Balance(0)
//val b = credit(a, 1000)