package search

import org.scalatest.{FreeSpec, Matchers}

import scala.util.Random

class MergeSortTest extends FreeSpec with Matchers{

  def runTimes(n: Int)(fun: => Unit): Unit = (0 to n) foreach(_ => fun)

  "should correct sort array" in {
    runTimes(Random.nextInt(1000)){
      val unsortedArray = Array.fill(Random.nextInt(1000))(Random.nextInt(1000))
      MergeSort.sortInt(unsortedArray) shouldEqual unsortedArray.sorted
    }
  }

  "should behaves as expected in an empty array" in {
    MergeSort.sortInt(Array.empty) shouldEqual Array.empty
  }

  "should behaves as expected in an array with one element" in {
    MergeSort.sortInt(Array(1)) shouldEqual Array(1)
  }
}
