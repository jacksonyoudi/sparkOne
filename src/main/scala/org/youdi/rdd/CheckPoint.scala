package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 检查点  不会失效
 *
 *
 * (8) ShuffledRDD[2] at reduceByKey at CheckPoint.scala:31 []
 * +-(8) MapPartitionsRDD[1] at map at CheckPoint.scala:27 []
 * |  ParallelCollectionRDD[0] at makeRDD at CheckPoint.scala:26 []
 *
 *
 * (8) ShuffledRDD[2] at reduceByKey at CheckPoint.scala:31 []
 * +-(8) MapPartitionsRDD[1] at map at CheckPoint.scala:27 []
 * |  ReliableCheckpointRDD[3] at foreach at CheckPoint.scala:32 []
 */

object CheckPoint {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    val mapRDD: RDD[(Int, Int)] = rdd.map((_, 1))
    // 设置检查点的目录
    sc.setCheckpointDir("cp")
    mapRDD.checkpoint()
    val reduceRDD: RDD[(Int, Int)] = mapRDD.reduceByKey(_ + _)
    reduceRDD.foreach(println)
    println(reduceRDD.toDebugString)

    sc.stop()
  }
}
