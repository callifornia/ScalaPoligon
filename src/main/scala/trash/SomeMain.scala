package trash

object SomeMain {
  implicit class Arrayrepresentation[T](a: Array[T]) {
    def printArray(pref: String) = println(a.mkString(s"$pref(", ", ", ")"))
    def isEmptyOrHasOneElement: Boolean = a.length / 2 == 0
    def splitAtMiddle: (Array[T], Array[T]) = a.splitAt(a.length / 2)
  }

  def main(args: Array[String]): Unit = {

    val array = Array(1,3,4, -4, -3, -7)
    def merge(leftSide: Array[Int],
              rightSide: Array[Int]): Array[Int] = {
      println(s"leftSide: ${leftSide.printArray("left")}, rightSide: ${rightSide.printArray("right")} ")

      (leftSide, rightSide) match {
        case (left, right) if left.isEmpty => {
          println("empty guy")
          right
        }
        case (left, right) if right.isEmpty => {
          println("empty guy2")
          left
        }


        case (left, right) if left.head <= right.head => left.head +: merge(left.tail, right)
        case (left, right) if left.head > right.head => right.head +: merge(left, right.tail)

        case (left, right) => {
          println(s"left: ${left.printArray("left(case)")}, right: ${right.printArray("right(case)")}")
          Array.empty[Int]
        }
      }
    }

    def mergeSort(xs: Array[Int], marker: String): Array[Int] = {
      println(xs.printArray(marker))
      if (xs.isEmptyOrHasOneElement) xs
      else {
        val (leftSide, rightSide) = xs.splitAtMiddle
        merge( mergeSort(leftSide, "leftSide"), mergeSort(rightSide, "rightSide"))
        //    Array.empty
      }
    }


    mergeSort(array, "start").printArray("result")

  }
}