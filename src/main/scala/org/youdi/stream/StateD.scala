package org.youdi.stream

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

/**
 * 有状态数据统计
 */
object StateD {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("state")
    val streamContext = new StreamingContext(conf, Seconds(3))

    // 从指定端口中采集数据
    val rdstream: ReceiverInputDStream[String] = streamContext.socketTextStream("127.0.0.1", 9999)

    val mapRDD: DStream[(String, Int)] = rdstream.flatMap(_.split(" ")).map((_, 1))

    // 空指针 Option, value和null 注意， bufffer可能为空
    val state: DStream[(String, Int)] = mapRDD.updateStateByKey {
      case (seq, buffer) => {
        val sum = buffer.getOrElse(0) + seq.sum
        Option(sum)
      }
    }

    // 需要设置检查点
    streamContext.sparkContext.setCheckpointDir("cp")


    state.print()
    streamContext.start()
    streamContext.awaitTermination()

  }
}
