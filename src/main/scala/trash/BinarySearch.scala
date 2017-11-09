package trash
import utils.Implicits._


object BinarySearch {


  def findElementInArray(n: Int, xs: Array[Int]): Int = {

    xs match {
      case xs if xs.isEmpty | xs.isArrayWithOneElementNotContain(n) => -1
      case xs => {
        xs.getMiddleElement match {
          case middleElement if middleElement.equals(n) => xs.getMiddleIndex
          case middleElement if n.isLessThan(middleElement) => findElementInArray(n, xs.splitAtMiddle._1)
          case middleElement if n.isGreaterThan(middleElement) => findElementInArray(n, xs.splitAtMiddle._2)
        }
      }
    }
  }
}
