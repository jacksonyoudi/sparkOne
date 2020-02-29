package org.youdi.rdd

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase
import org.apache.spark.{SparkConf, SparkContext}

object HbaseDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val hconf: Configuration = HBaseConfiguration.create()
    hconf.set("hbase.zookeeper.quorum", "sss")
    // 读表
//    hconf.set()

//    sc.newAPIHadoopRDD(conf, classOf[TableInputFormat])


    sc.stop()
  }
}
