package org.jackson.bigdata.ex

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object Wd {
  def main(args: Array[String]): Unit = {
    // 配置， 运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

    // 读取文件，将文件内容一行一行的读取出来
    val lines = sc.textFile("/Users/youdi/Project/javaProject/sparkOne/input")

    val words = lines.flatMap(_.split(" "))
    val wordToOne: RDD[(String, Int)] = words.map((_, 1)) // tuple 2

    // 分组聚合
    val wordSum: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)

    val array: Array[(String, Int)] = wordSum.collect()
    array.foreach(println)


  }

}
