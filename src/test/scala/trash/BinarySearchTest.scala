package trash

import org.scalatest.{FreeSpec, Matchers}
import search.BinarySearch

class BinarySearchTest extends FreeSpec with Matchers{


  "should return right array index if element was found" in {
    val array = Array(1,2,3,4, 22)
    BinarySearch.findElementInArray(22, array) shouldEqual 1
  }

  "should return -1 if element was not found" in {
    val expectedResult = -1
    val array = Array(1,2,3)
    BinarySearch.findElementInArray(99, array) shouldBe expectedResult
  }

  "should return -1 in empty array" in {
    val expectedResult = -1
    val array = Array.empty[Int]
    BinarySearch.findElementInArray(12, array) shouldBe expectedResult
  }

  "should return -1 in array with one element if element was not found" in {
    val expectedResult = -1
    val array = Array(2)
    BinarySearch.findElementInArray(12, array) shouldBe expectedResult
  }
}
