package org.youdi.stream

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object FileDataSource {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("streaming filesource")
    val streamingContext = new StreamingContext(conf, Seconds(5))

    val ds: DStream[String] = streamingContext.textFileStream("input")
    ds.flatMap(_.split(" ")).map((_, 1)).reduceByKey((_ + _)).print()
    streamingContext.start()
    streamingContext.awaitTermination()


  }
}
