package org.youdi.stream

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

object Transform {
  def main(args: Array[String]): Unit = {
    // conf
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wordcount")
    //采集周期
    val streamContext = new StreamingContext(conf, Seconds(3))

    // 从指定端口中采集数据
    val rdstream: ReceiverInputDStream[String] = streamContext.socketTextStream("127.0.0.1", 9999)
    // 转换 driver
    // val a = 1
    rdstream.map {
      case x => {
        // excutor
        // val a = 1
        x
      }
    }
    // driver
    // val
    rdstream.transform {
      case rdd => {
        // driver (m=采集周期) 每个周期都会执行的
        rdd.map {
          case x => {
            // Executor
            x
          }
        }
      }


    }


    // 按行的数据
    // 将采集的数据进行分解 (扁平化)
    val wordDstream: DStream[String] = rdstream.flatMap(_.split(" "))
    //将数据进行结构转换方便统计分析
    val mapDstream: DStream[(String, Int)] = wordDstream.map((_, 1))
    // 将转换的数据进行聚合处理
    val wordSumDstream: DStream[(String, Int)] = mapDstream.reduceByKey(_ + _)

    //打印结果
    wordSumDstream.print()

    // 不能停止采集程序
    //    streamContext
    //    streamContext.stop(true, true)

    // 启动采集器
    streamContext.start()
    // driver等待采集器停止
    streamContext.awaitTermination()
  }
}
