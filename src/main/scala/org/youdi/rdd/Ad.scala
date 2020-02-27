package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Ad {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[(String, String)] = sc.makeRDD(Array(
      ("hb", "ad1"), ("hb", "ad1"), ("hb", "ad1"), ("hb", "ad2"),
      ("hb", "ad2"), ("hb", "ad2"), ("hb", "ad2"), ("hb", "ad2"),
      ("hb", "ad1"), ("hb", "ad1"), ("hb", "ad3"), ("gx", "ad1"),
      ("gx", "ad1"), ("gx", "ad4"), ("hb", "ad3"), ("hb", "ad2"),
      ("hb", "ad1"), ("gx", "ad3"), ("hb", "ad4"), ("hb", "ad2"),
      ("gd", "ad1"), ("gx", "ad1"), ("gx", "ad5"), ("gx", "ad1"),
      ("gd", "ad1"), ("ah", "ad9"), ("hb", "ad6"), ("hb", "ad2"),
      ("gd", "ad1"), ("ah", "ad1"), ("hb", "ad7"), ("gx", "ad2"),
      ("gd", "ad1"), ("hn", "ad1"), ("hb", "ad8"), ("hb", "ad2"),
      ("gd", "ad1"), ("hn", "ad9"), ("hb", "ad9"), ("hb", "ad2"),
      ("gd", "ad1"), ("hn", "ad9"), ("hb", "ad9"), ("hb", "ad9")
    ))

    val value: RDD[(String, Iterable[Int])] = rdd.map((a) => (a._1 + a._2, 1)).reduceByKey(_ + _).groupByKey()
  }
}
