package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * driver：
 * 创建spark上下文对象的应用程序称为driver
 *
 *
 * 发送任务
 *
 *
 * Execute:
 * 执行器用于接收任务并执行任务
 * 任务执行情况反馈
 *
 * 所有driver中的RDD计算功能都是excute进行的
 *
 * 序列化
 *
 * driver和execute上执行 进行
 *
 *
 *
 * flatMap(func)
 * 类似map, 但是每一个输入元素可以被映射为0或多个输出元素 所以func应该返回一个序列，而不是一个单一元素
 */
object Driver {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val ListRDD: RDD[List[Int]] = sc.makeRDD(Array(List(1, 2), List(3, 4)))

    // flatmap
    val value: RDD[Int] = ListRDD.flatMap(datas => datas)
    value.collect().foreach(println)

  }
}
