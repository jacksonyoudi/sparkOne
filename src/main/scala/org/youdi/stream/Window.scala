package org.youdi.stream

object Window {
  def main(args: Array[String]): Unit = {
    val ints = List(1, 2, 3, 4, 5, 6)
    // 滑动 步长
    val iterator: Iterator[List[Int]] = ints.sliding(2, 2)
    for (elem <- iterator) {
      println(elem.mkString(","))
    }
  }
}
