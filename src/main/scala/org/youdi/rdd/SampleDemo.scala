package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * sample(withReplacement, fraction, seed)
 * withReplacement: 数据是否放回 true， false
 * seed用于指定随机数生成器种子
 *
 * 从指定的数据集合中进行抽样处理，根据不同的算法进行抽样
 */

object SampleDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(1 to 100)
    val value1: RDD[Int] = value.sample(false, 0.4, 1)
    value1.collect.foreach(println)

  }
}
