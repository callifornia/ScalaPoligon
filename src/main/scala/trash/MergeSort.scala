package trash

import scala.util.Random

object MergeSort {

  def sortInt(xs: Array[Int]): Array[Int] = {
    if (xs.isEmptyOrHasOneElement) xs
    else {
      val (leftSide, rightSide) = xs.splitAtMiddle
      merge( sortInt(leftSide), sortInt(rightSide))
    }
  }

  private def merge(leftSide: Array[Int],
                    rightSide: Array[Int]): Array[Int] = {

    (leftSide, rightSide) match {
      case (left, right) if left.isEmpty => right
      case (left, right) if right.isEmpty => left
      case (left, right) if left.head <= right.head => left.head +: merge(left.tail, right)
      case (left, right) if left.head > right.head => right.head +: merge(left, right.tail)
      case _ =>  Array.empty[Int]
    }
  }

  implicit class ArrayImplicits[T](xs: Array[T]) {
    def isEmptyOrHasOneElement: Boolean = xs.length / 2 == 0
    def splitAtMiddle: (Array[T], Array[T]) = xs.splitAt(xs.length / 2)
    def getRandomElement: T = xs(Random.nextInt(xs.length - 1))
  }
}
