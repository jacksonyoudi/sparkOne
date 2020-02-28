package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 缓存
 * persist
 * cache
 *
 * 在血缘中加了 一层缓存的
 */
object Cache {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)
    val rdd: RDD[String] = sc.makeRDD(Array("hello"))
//    rdd.map(_.toString).cache()
    rdd.map(_.toString).persist()

  }
}
