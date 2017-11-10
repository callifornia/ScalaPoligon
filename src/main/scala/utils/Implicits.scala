package utils

import scala.util.Random

object Implicits {
  implicit class ArrayImplicits[T](xs: Array[T]) {
    def isEmptyOrHasOneElement: Boolean = getMiddleIndex == 0
    def splitAtMiddle: (Array[T], Array[T]) = xs.splitAt(getMiddleIndex)
    def getRandomElement: T = xs(Random.nextInt(xs.length - 1))
    def getMiddleIndex: Int = xs.length / 2
    def getMiddleElement: T = xs(getMiddleIndex)
    def isArrayWithOneElementNotContain(el: Int): Boolean = xs.length == 1 && xs.head != el
  }

  implicit class IntImplicit(n: Int){
    def isGreaterThan(el: Int): Boolean = n > el
    def isLessThan(el: Int): Boolean = n < el
  }
}
