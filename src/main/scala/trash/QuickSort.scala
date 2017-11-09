package trash
import utils.Implicits._
object QuickSort {

  def sortInt(xs: Array[Int]): Array[Int] = {
    if (xs.isEmptyOrHasOneElement) xs
    else {
      val pivot = xs.getRandomElement
      Array.concat(
        sortInt(xs.filter(_ < pivot)),
        xs.filter(_ == pivot),
        sortInt(xs.filter(_ > pivot))
      )
    }
  }
}
