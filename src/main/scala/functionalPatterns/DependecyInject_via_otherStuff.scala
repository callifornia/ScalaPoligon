package functionalPatterns

import java.util.concurrent.ConcurrentHashMap

import cats.data.ReaderT
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

// https://softwaremill.com/reader-monad-constructor-dependency-injection-friend-or-foe/
object DependecyInject_via_otherStuff {
  def main(args: Array[String]): Unit = {
    // Dependencies injections via CONSTRUCTOR.
    // We put instance into constructor when object is going to be created
    lazy val dao = new FoodKV_DAO
    lazy val logger = new LogerImplFirst("some user name")
    lazy val fridge  = new FridgeSERVICE(logger)
    lazy val cooker  = new CookerLOGIC(fridge)

    lazy val init = for {
      _ <- fridge.addFoodIntoStorage(Tomato, 100)
      _ <- fridge.addFoodIntoStorage(Basilic, 100)
      _ <- fridge.addFoodIntoStorage(BlankoOil, 100)
    } yield ()

    // run shit()
    val cookedStuff = for {
      _ <- init
      sauceQuantity <- cooker.cookSauce(5)
      pastaQuantity <- cooker.cookPasta(5)
    } yield s"Coocked sauce: 95 and pasta: 92 <- expected\nCoocked sauce: $sauceQuantity and pasta: $pastaQuantity"

    val cookedResult = Await.result(cookedStuff(dao), 2.seconds)
    println(cookedResult)
  }


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
    def cookSauce(quantity: FoodQuantity): ReaderT[Future, FoodKV_DAO, FoodQuantity] = for {
      tomatoQuantity <- fridge.getFoodFromStorage(Tomato, quantity) // from servie
      basilikQuantity <- fridge.getFoodFromStorage(Basilic, quantity) // from storage
      souceQuantity = (tomatoQuantity + basilikQuantity) / 2
      _ <- fridge.addFoodIntoStorage(Souce, souceQuantity)// save to storage just to be able retrieve than
    } yield souceQuantity

    def cookPasta(quantity: FoodQuantity):ReaderT[Future, FoodKV_DAO, FoodQuantity] = for {
      tomatoQuantity <- fridge.getFoodFromStorage(BlankoOil, quantity) // from storage
      basilikQuantity <- fridge.getFoodFromStorage(Basilic, quantity) // from storage
      souceQuantity = (tomatoQuantity + basilikQuantity) / 2
      _ <- fridge.addFoodIntoStorage(Souce, souceQuantity)// save to storage just to be able retrieve than
    } yield souceQuantity
  }


  // Service, like Service -> DAO.
  // Service DOES NOT HAVE A DEPENDECY. RETURN function wrapped into class. To execute that function
  // you need to have those dependecy
  // In current case it's a reader monad
  // In another words SERVICE told explicitly about dependecies.
 class FridgeSERVICE(logger: SomeLoger) {
    def addFoodIntoStorage(foodName: FoodName, foodQuantity: FoodQuantity): ReaderT[Future, FoodKV_DAO, Unit] =
      ReaderT {
        fc => for {
          current <- fc.read(foodName)
          updated = current.map(_ + foodQuantity).getOrElse(foodQuantity)
          _ <- fc.update(foodName, updated)
          _ <- logger.notify(s"Added food. ($foodName, $current) -> $updated")
        } yield ()
      }

    def getFoodFromStorage(foodName: FoodName, foodQuantity: FoodQuantity): ReaderT[Future, FoodKV_DAO, FoodQuantity] =
      ReaderT {
        fc => for {
          currentAmountOfFood <- fc.read (foodName)
          aa = currentAmountOfFood.map {current =>
            if (current - foodQuantity < 0) {
              fc.delete (foodName)
              current}
            else {
             fc.update (foodName, current - foodQuantity)
             current - foodQuantity}
          }.getOrElse (0)
          _ <- logger.notify (s"Get food. ($foodName, $currentAmountOfFood, $foodQuantity) -> $aa ")
        } yield aa
    }
  }
}
