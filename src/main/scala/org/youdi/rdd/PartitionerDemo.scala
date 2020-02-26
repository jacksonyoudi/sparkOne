package org.youdi.rdd

import org.apache.spark.{Partitioner, SparkConf, SparkContext}

object PartitionerDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)
    //    sc.makeRDD(List(1, 2, 1, 5, 6, 7, 4, 9, 1))
    sc.makeRDD(List((1, "a"), (2, "b"))).partitionBy(new MyParttioner(2)).glom().foreach(a => println(a.mkString(",")))
  }
}

class MyParttioner(partions: Int) extends Partitioner {
  override def numPartitions: Int = {
    partions
  }

  override def getPartition(key: Any): Int = {
    1
  }
}
