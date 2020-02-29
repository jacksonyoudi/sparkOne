package org.youdi.sharedata

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

object ShareDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    // 2个分区
    val dataRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    //    val i: Int = dataRDD.reduce(_ + _)
    //    print(i)
    //    var sum = 0
    // sum在excute中执行，没有回传到driver

    // 创建累加器共享变量 excute中计算完以后，将结果回传给driver
    val sl: LongAccumulator = sc.longAccumulator


    //    dataRDD.foreach(i => {
    //      sum += i
    //    })

    dataRDD.foreach {
      case i => {
        // 执行累加的功能
        sl.add(i)
      }
    }

    // 获取累加器值
    println(sl.toString)


    sc.stop()

  }
}
