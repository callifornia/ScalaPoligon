import scala.util.Random

def getrandomArray = {
  val arraySize = Random.nextInt(100)
  Array.fill(arraySize)(Random.nextInt(10000))
}

getrandomArray.sorted



