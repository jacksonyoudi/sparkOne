package org.youdi.stream

import java.io.{BufferedReader, InputStreamReader}

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.receiver.Receiver

object MyReceiver {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("streaming myreciver")
    val streamingContext = new StreamingContext(conf, Seconds(4))

    val ds: ReceiverInputDStream[String] = streamingContext.receiverStream(new YdReceiver("127.0.0.1", 9999))
    ds.flatMap(_.split(" ")).map((_, 1)).reduceByKey((_ + _)).print()
    streamingContext.start()
    streamingContext.awaitTermination()
  }
}

// 声明采集器
class YdReceiver(host: String, port: Int) extends Receiver[String](StorageLevel.MEMORY_ONLY) {
  var socket: java.net.Socket = null

  def receive(): Unit = {
    socket = new java.net.Socket(host, port)
    val reader = new BufferedReader(new InputStreamReader(socket.getInputStream, "UTF-8"))
    var line: String = null
    // socket没有null
    while ((line = reader.readLine()) != null) {
      // 将采集的数据存储到采集器的内部进行转换
      if ("END".equals(line)) {
        return
      }
      this.store(line)


    }
  }

  override def onStart(): Unit = {
    new Thread() {
      override def run() = {
        receive()
      }
    }.start()
  }

  override def onStop(): Unit = {
    if (socket != null) {
      socket.close()
      socket = null
    }

  }
}
