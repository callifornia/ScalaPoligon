package functionalPatterns

import functionalPatterns.monad.Reader
import functionalPatterns.repos._

object Main {
  def main(args: Array[String]): Unit = {

  }
}
object monad {
  case class Reader[A, B](run: A => B) {
    def apply(a: A) = run(a)
    def map[C](f: B => C): Reader[A, C] = Reader( run andThen f)
    def flatMap[C](f: B => Reader[A, C]): Reader[A, C] = Reader(a => map(f)(a)(a))
  }
}


object repos {
  trait Repositories {
    def userRepository: UserRepo
    def addressRepository: AddressRepo
  }

  object Repositories {
    val repositories: Reader[Repositories, Repositories]  = Reader[Repositories, Repositories](identity)

    val userRepo:     Reader[Repositories, UserRepo]      = repositories map (_.userRepository)
    val addressRepo:  Reader[Repositories, AddressRepo]   = repositories map (_.addressRepository)
  }

  trait UserRepo {
    def get(userId: Int): Option[User]
    def find(email: String): Option[User]
    def update(user: User): Option[User]
  }

  object UserRepo {
    import Repositories.userRepo
    def getUser(userId: Int): Reader[Repositories, Option[User]] = userRepo.map(_.get(userId))
    def findUserByMail(email: String): Reader[Repositories, Option[User]] = userRepo.map(_.find(email))
  }

  trait AddressRepo {
    def get(userId: Int): Option[Address]
    def find(email: String): Option[Address]
    def update(user: Address): Option[Address]
  }

  object AddressRepo {
    import Repositories.addressRepo
    def getUser(userId: Int): Reader[Repositories, Option[Address]] = addressRepo.map(_.get(userId))
    def findUserByMail(email: String): Reader[Repositories, Option[Address]] = addressRepo.map(_.find(email))
  }
}

object repositoryImpl {
  object UserRepoImpl extends UserRepo {
    private var storage: Map[Int, User] = Map(
      1 -> User(1, "name_1", "email_1"),
      2 -> User(2, "name_2", "email_2"),
      3 -> User(3, "name_3", "email_3"))

    override def get(userId: Int) = storage.get(userId)
    override def find(email: String) = storage.values.find(_.email == email)
    override def update(user: User) = {
      storage = storage + (user.id -> user)
      Some(user)
    }
  }
  object AddressRepoImpl extends AddressRepo {
    private var storage: Map[Int, Address] = Map(
      1 -> Address(1, "name_1"),
      2 -> Address(2, "name_2"),
      3 -> Address(3, "name_3"))

    override def get(userId: Int) = storage.get(userId)
    override def find(name: String) = storage.values.find(_.name == name)
    override def update(user: Address) = {
      storage = storage + (user.id -> user)
      Some(user)
    }
  }
}

case class User(id: Int, name: String, email: String)
case class Address(id: Int, name: String)
