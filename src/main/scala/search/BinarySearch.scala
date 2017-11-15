package s2

import utils.Implicits._


object BinarySearch {


  /**
    * Binary search takes O(log N) time.
    *
    * @param n number which should be found
    * @param xs array to search in
    * @return index of n position if element was found, -1 otherwise
    */
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
