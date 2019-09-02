package some

object Main {
  def main(args: Array[String]): Unit = {
    println(s"Hello world123")
    val lll: List[(Int, Int)] = List(
      Person("a", 2, 3),
      Person("a", 2, 2),
      Person("a", 3, 3),
      Person("a", 3, 3),
      Person("a", 4, 3)
    ).groupBy(_.age).toList.map{
      case (age, peoples) => (age, peoples.map(_.salary).sum)
    }
  }

  case class Person(name: String, age: Int, salary: Int)
}
