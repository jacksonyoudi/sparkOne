package org.youdi.stream

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object KDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("kafka")
    val streamingContext = new StreamingContext(conf, Seconds(5))

    val kDstream: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(streamingContext, "127.0.0.1:2181", "youdi", Map("youdi" -> 3))
    kDstream.flatMap(_._2.split(" ")).map((_, 1)).reduceByKey(_ + _).print()

    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
