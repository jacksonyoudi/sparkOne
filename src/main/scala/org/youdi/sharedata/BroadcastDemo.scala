package org.youdi.sharedata

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object BroadcastDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)
    val rdd1: RDD[(Int, Int)] = sc.makeRDD(List((1, 2), (2, 2), (3, 3)))

    val list = List((1, 2), (2, 2), (3, 3))
    //  广播变量， 调优
    val broadcast: Broadcast[List[(Int, Int)]] = sc.broadcast(list)

    val value1: RDD[(Int, (Int, Any))] = rdd1.map {
      case (key, value) => {
        var v2: Any = null
        for (elem <- broadcast.value) {
          if (key == elem._1) {
            v2 = elem._2
          }
        }
        (key, (value, v2))
      }
    }
    value1.foreach(println)


    sc.stop()
  }
}
