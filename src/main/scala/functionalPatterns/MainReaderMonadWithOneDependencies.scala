package functionalPatterns

object MainReaderMonadWithOneDependencies {
  def main(args: Array[String]): Unit = {
    val result = prependIntoName(1).run(UserRepoImpl)
    println(s"Result is: $result")
  }

  def prependIntoName(userId: Int): Reader[UserRepo, Option[User]] = {
    for {
      existedUser <- getUser(userId)
      _ <- existedUser.map(x => updateUser(x.copy(email = x.email + "_Foo"))).get
      _ <- existedUser.map(x => updateUser(x.copy(email = x.email + "_Foo"))).get
      updatedUser <- getUser(userId)
    } yield updatedUser
  }


  object repo {
    trait UserRepo {
      def get(userId: Int): Option[User]
      def find(email: String): Option[User]
      def update(user: User): Option[User]
    }

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
  }

  object services {
    object UserService {

      val userRepo = Reader[UserRepo, UserRepo](identity)

      def getUser(userId: Int): Reader[UserRepo, Option[User]] = userRepo map (_.get(userId))
      def findUser(email: String): Reader[UserRepo, Option[User]] = userRepo map (_.find(email))
      def updateUser(user: User): Reader[UserRepo, Option[User]] = userRepo map (_.update(user))
    }
  }

  object monads {
    case class Reader[A, B](run: A => B) {
      def apply(a: A) = run(a)
      def map[C]( f: B => C): Reader[A, C] = Reader(run andThen f)
      def flatMap[C](f: B => Reader[A, C]): Reader[A, C] = Reader(x => map(f)(x)(x))
    }
  }

  object models {
    case class User(id: Int, name: String, email: String)
  }
}
