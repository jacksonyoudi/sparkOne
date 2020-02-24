package org.youdi.rdd

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object GlomDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val values: RDD[Int] = sc.makeRDD(1 to 16, 4)
    // 将一个分区的数据放到一个数组中
    val value: RDD[Array[Int]] = values.glom()
    value.foreach(arr => println(arr.mkString(",")))

  }
}
