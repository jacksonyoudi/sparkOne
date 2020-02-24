package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 缩减分区,可以简单的理解为合并分区
 */
object Coalesce {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(1 to 16, 4)

    println(value.partitions.length)
    println("----------------")
    val value1: RDD[Int] = value.coalesce(2)
    println(value1.partitions.length)
    sc.stop()
  }
}
