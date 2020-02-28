package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import scala.util.parsing.json

object Json {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val value: RDD[String] = sc.textFile("input")
    val result: RDD[Option[Any]] = value.map(json.JSON.parseFull)
    result.foreach(println)
    sc.stop()
  }
}
