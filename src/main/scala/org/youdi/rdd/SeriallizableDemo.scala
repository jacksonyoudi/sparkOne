package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SeriallizableDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[String] = sc.makeRDD(Array("one", "two", "three", "four"))
    val search = new Search("o")
    // Task not serializable object not serializable
    val rdd1: RDD[String] = search.getMatch1(rdd)
    rdd1.collect().foreach(println)
  }
}


class Search(query: String) extends java.io.Serializable {
  //class Search(query: String) {
  // 过滤出包含字符串的数据
  def isMatch(s: String): Boolean = {
    s.contains(query)
  }

  def getMatch1(rdd: RDD[String]): RDD[String] = {
    rdd.filter(isMatch)
  }

  def getMatch2(rdd: RDD[String]): RDD[String] = {
    // 使用局部变量可以执行  q = query 字符串可以在网络中传输
    rdd.filter(x => x.contains(query))
  }


}