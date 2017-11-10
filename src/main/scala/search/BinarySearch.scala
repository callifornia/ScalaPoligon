package search

import utils.Implicits._


object BinarySearch {


  def findElementInArray(n: Int, xs: Array[Int]): Int = {

    def search(n: Int, xs: Array[Int], index: Int): Int = {
      xs match {
        case xs if xs.isEmpty | xs.isArrayWithOneElementNotContain(n) => -1
        case xs => {
          xs.getMiddleElement match {
            case middleElement if middleElement.equals(n) => index + xs.getMiddleIndex
            case middleElement if n.isLessThan(middleElement) => search(n, xs.splitAtMiddle._1, 0)
            case middleElement if n.isGreaterThan(middleElement) => search(n, xs.splitAtMiddle._2, index + xs.getMiddleIndex)
          }
        }
      }
    }
    search(n, xs, 0)
  }
}
