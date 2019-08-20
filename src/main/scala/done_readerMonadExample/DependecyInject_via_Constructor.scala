package readerMonadExample

import java.util.concurrent.ConcurrentHashMap
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

// https://softwaremill.com/reader-monad-constructor-dependency-injection-friend-or-foe/
object DependecyInject_via_Constructor {
  def main(args: Array[String]): Unit = {
    // Dependencies injections via CONSTRUCTOR.
    // We put instance into constructor when object is going to be created
    lazy val storage = new FoodKV_DAO
    lazy val logger = new LogerImplFirst("some user name")
    lazy val fridge  = new FridgeSERVICE(storage, logger)
    lazy val cooker  = new CookerLOGIC(fridge)

    // run shit()
    val cookedStuff = for {
      _ <- init(fridge).map{ e => println(s"Init complete...."); e }
      sauceQuantity <- cooker.cookSauce(5)
      pastaQuantity <- cooker.cookPasta(5)
    } yield s"Coocked sauce: 95 and pasta: 92 <- expected\nCoocked sauce: $sauceQuantity and pasta: $pastaQuantity"

    val cookedResult = Await.result(cookedStuff, 2.seconds)
    println(cookedResult)
  }

  lazy val init: FridgeSERVICE => Future[Unit] = fridge => for {
    _ <- fridge.addFoodIntoStorage(Tomato, 100)
    _ <- fridge.addFoodIntoStorage(Basilic, 100)
    _ <- fridge.addFoodIntoStorage(BlankoOil, 100)
  } yield ()

  type FoodName = Food
  type FoodQuantity = Int
  type FoodKV_DAO = KVStore_DAO[FoodName, FoodQuantity]

  sealed trait Food
  case object Tomato extends Food
  case object Basilic extends Food
  case object Souce extends Food
  case object BlankoOil extends Food


  // Logger

  trait SomeLoger{
    def notify(msg: String): Future[Unit]
  }

  class LogerImplFirst(user: String) extends SomeLoger {
    def notify(msg: String): Future[Unit] = Future.successful{
      println(s"note: $msg")
    }
  }

  // DAO. Just a storage nothing else
  class KVStore_DAO[K, V] {
    var store = new ConcurrentHashMap[K, V]()

    def create(k: K, v: V): Future[Unit] = Future.successful(store.put(k, v))
    def read(k: K): Future[Option[V]] = Future.successful(Option(store.get(k)))
    def update(k: K, v: V): Future[Unit] = Future.successful(store.put(k, v))
    def delete(k: K): Future[Unit] = Future.successful(store.remove(k))
  }

  // Know how to COOK and nothing else.
  class CookerLOGIC(fridge: FridgeSERVICE) {
    def cookSauce(quantity: FoodQuantity): Future[FoodQuantity] = for {
      tomatoQuantity <- fridge.getFoodFromStorage(Tomato, quantity) // from storage
      basilikQuantity <- fridge.getFoodFromStorage(Basilic, quantity) // from storage
      souceQuantity = (tomatoQuantity + basilikQuantity) / 2
      _ <- fridge.addFoodIntoStorage(Souce, souceQuantity) // save to storage just to be able retrieve than
    } yield souceQuantity

    def cookPasta(quantity: FoodQuantity): Future[FoodQuantity] = for {
      tomatoQuantity <- fridge.getFoodFromStorage(BlankoOil, quantity) // from storage
      basilikQuantity <- fridge.getFoodFromStorage(Basilic, quantity) // from storage
      souceQuantity = (tomatoQuantity + basilikQuantity) / 2
      _ <- fridge.addFoodIntoStorage(Souce, souceQuantity) // save to storage just to be able retrieve than
    } yield souceQuantity
  }

 // Service, like Service -> DAO. Service has dependecy on "FoodKV" because need to have in constructor link to the DAO
 // Service has dependency on DAO via putting into constructor.
 class FridgeSERVICE(fc: FoodKV_DAO, logger: SomeLoger) {
    def addFoodIntoStorage(foodName: FoodName, foodQuantity: FoodQuantity): Future[Unit] = for {
      current <- fc.read(foodName)
      updated = current.map(_ + foodQuantity).getOrElse(foodQuantity)
      _ <- fc.update(foodName, updated)
      _ <- logger.notify(s"Added food. ($foodName, $current) -> $updated")
    } yield ()

    def getFoodFromStorage(foodName: FoodName, foodQuantity: FoodQuantity): Future[FoodQuantity] = for {
      currentAmountOfFood <- fc.read(foodName)
      aa = currentAmountOfFood.map { current =>
        if (current - foodQuantity < 0) {
          fc.delete(foodName)
          current
        }
        else {
          fc.update(foodName, current - foodQuantity)
          current - foodQuantity
        }
      }.getOrElse(0)
      _ <- logger.notify(s"Get food. ($foodName, $currentAmountOfFood, $foodQuantity) -> $aa")
    } yield aa
  }

}
