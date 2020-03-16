package org.nsq

import com.google.gson.JsonParser
import org.apache.spark.SparkConf
import org.apache.spark.internal.Logging
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/*
* 在定义一个 context 之后,您必须执行以下操作.

* 通过创建输入 DStreams 来定义输入源.
* 通过应用转换和输出操作 DStreams 定义流计算（streaming computations）.
* 开始接收输入并且使用 streamingContext.start() 来处理数据.
* 使用 streamingContext.awaitTermination() 等待处理被终止（手动或者由于任何错误）.
* 使用 streamingContext.stop() 来手动的停止处理.
 */

object NsqStreaming extends Logging {

  def main(args: Array[String]): Unit = {

    if (args.length < 4) {
      System.err.println("Usage: ELKStreaming <hostname> <port> <batchDuration> <to table>")
      System.exit(1)
    }
    logInfo("start ===========>")
    val sparkConf = new SparkConf().setAppName("ELKStreaming").setMaster("yarn").set("hive.metastore.uris", "thrift://hadoop15.bigdata.org:9083")

    // 创建一个批次间隔为10
    val ssc = new StreamingContext(sparkConf, Seconds(args(2).toInt))
    // 使用自定义的NSQReceiver
    val lines = ssc.receiverStream(new ReliableNSQReceiver(args(0), args(1).toInt, "log", "scalatest"))
    val hiveStream: DStream[(String, String)] = lines.map(line => prefix_exit(line))

    // 将计算后的数据保存到hive中
    hiveStream.foreachRDD(rdd => {
      // 利用SparkConf来初始化SparkSession。
      val sparkSession: SparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()

      // 导入隐式转换来将RDD
      import sparkSession.implicits._
      // 将RDD转换成DF
      val df: DataFrame = rdd.toDF("str", "ymd")
      // 取出表中的字段
      logInfo("df count ===========>" + df.count)

      df.createOrReplaceTempView("spark_logs")

      sparkSession.sql("insert into " + args(3) + " partition (ymd) select str,ymd from spark_logs")
    })

    ssc.start()
    ssc.awaitTermination()
  }

  def prefix_exit(line: String): (String, String) = {
    // 对数据进行清洗计算
    val obj = new JsonParser().parse(line).getAsJsonObject
    val data_str1 = obj.get("recv_timestamp").toString().split("T|Z|\"")
    val data_str2 = data_str1(1).split('-')
    val data_str3 = data_str2(1) + "/" + data_str2(2) + "/" + data_str2(0) + " " + data_str1(2) + " [I] " + obj.get("index_type").toString().split("\"")(1) + " " + line
    val data_str4 = data_str2(0) + data_str2(1) + data_str2(2)
    (data_str3.toString(), data_str4.toString())
  }
}
