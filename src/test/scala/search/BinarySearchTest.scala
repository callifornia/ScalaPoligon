package s2

import org.scalatest.{FreeSpec, Matchers}

class BinarySearchTest extends FreeSpec with Matchers{


  "should return right array index" - {
    val array = Array(1, 2, 3, 4, 5)

    "if element in zero array position" in {
      BinarySearch.findElementInArray(1, array) shouldEqual 0
    }

    "if element in first array position" in {
      BinarySearch.findElementInArray(2, array) shouldEqual 1
    }

    "if element in second array position" in {
      BinarySearch.findElementInArray(3, array) shouldEqual 2
    }

    "if element in third array position" in {
      BinarySearch.findElementInArray(4, array) shouldEqual 3
    }

    "if element in forth array position" in {
      BinarySearch.findElementInArray(5, array) shouldEqual 4
    }

  }

  "should return -1 if element was not found" in {
    val expectedResult = -1
    val array = Array(1, 2, 3)
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
