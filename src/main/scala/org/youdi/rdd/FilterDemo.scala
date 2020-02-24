package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object FilterDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val values: RDD[Int] = sc.makeRDD(1 to 10)
    val value: RDD[Int] = values.filter(_ % 2 == 0)
    value.collect.foreach(
      println
    )


  }
}
