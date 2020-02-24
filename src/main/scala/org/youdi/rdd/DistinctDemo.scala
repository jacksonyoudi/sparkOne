package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 数据 shuffle 洗牌
 *
 * spark中所有的转换算子中没有shuffle的算子，性能比较快  不用等待
 *
 * distinct(partion)
 */

object DistinctDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(List(1, 2, 1, 2, 3, 4, 2, 1))
    // 使用distinct算子对数据进行去重，但是因为去重后会导致数据减少，所以可以改变默认的分区的数量
    value.distinct().collect().foreach(println)


  }
}
