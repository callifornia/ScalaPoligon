package utils

import scala.util.Random

object Implicits {
  implicit class ArrayImplicits[T](xs: Array[T]) {
    def isEmptyOrHasOneElement: Boolean = xs.length / 2 == 0
    def splitAtMiddle: (Array[T], Array[T]) = xs.splitAt(xs.length / 2)
    def getRandomElement: T = xs(Random.nextInt(xs.length - 1))
    def getMiddleIndex: Int = xs.length / 2
    def getMiddleElement: T = xs(xs.length / 2)
    def isArrayWithOneElementNotContain(el: Int): Boolean = xs.length == 1 && xs.head != el
  }

  implicit class IntImplicit(n: Int){
    def isGreaterThan(el: Int): Boolean = n > el
    def isLessThan(el: Int): Boolean = n < el
  }
}
