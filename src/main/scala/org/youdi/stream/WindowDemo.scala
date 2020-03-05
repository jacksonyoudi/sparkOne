package org.youdi.stream

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

object WindowDemo {
  def main(args: Array[String]): Unit = {
    // conf
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    //采集周期
    val streamContext = new StreamingContext(conf, Seconds(3))

    // 从指定端口中采集数据
    val rdstream: ReceiverInputDStream[String] = streamContext.socketTextStream("127.0.0.1", 9999)

    // 窗口 大小是采集周期的整数倍
    // window(windowDuration, this.slideDuration)
    // 滑动步长
    // 2，3，1 => 2,5,6,4,1
    val windowStream: DStream[String] = rdstream.window(Seconds(9), Seconds(3))
    // 按行的数据
    // 将采集的数据进行分解 (扁平化)
    val wordDstream: DStream[String] = windowStream.flatMap(_.split(" "))
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
