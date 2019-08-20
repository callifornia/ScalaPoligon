/*

  0 1 1 2 3 5 8 13
  1 2 3 4 5 6 7 8

 */

def methodImplementation(count: Int): Int = {
  def calcNumber(start: Int, current: Int, acc: Int): Int = {
    if (acc <= 1) start
    else calcNumber(current, start + current, acc - 1)
  }
  calcNumber(0, 1, count)
}

val funImpl = (count: Int) => (1 to count)
  .foldLeft[(Int, Int)]((0, 1)){ (acc, _) =>
    (acc._1 + acc._2, acc._1)
  }._2

(1 to 7).map(funImpl)







