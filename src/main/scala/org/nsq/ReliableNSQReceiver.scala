package org.nsq

import com.github.brainlag.nsq.callbacks.NSQMessageCallback
import com.github.brainlag.nsq.lookup.DefaultNSQLookup
import com.github.brainlag.nsq.{NSQConsumer, NSQMessage}
import org.apache.spark.internal.Logging
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver


class MessageCallbacks(store_fun:String => Unit) extends NSQMessageCallback with Logging {

  def message(message: NSQMessage): Unit ={

    val s = new String(message.getMessage())
    store_fun(s)
    message.finished()
  }
}
/* 自定义连接器 */
class ReliableNSQReceiver(host: String, port: Int, topic: String, channel: String)
  extends Receiver[String](StorageLevel.MEMORY_AND_DISK_2) with Logging {

  var consumer: NSQConsumer = null

  def onStart() {
    // 启动通过连接接收数据的线程
    new Thread("Socket Receiver") {
      override def run() { receive() }
    }.start()
  }

  def onStop() {
    logInfo("Stopped receiving")
    consumer.close
  }

  /** 接收数据 */
  private def receive() {

    try {
      val lookup = new DefaultNSQLookup
      lookup.addLookupAddress(host, port)
      consumer = new NSQConsumer(lookup, topic, channel, new MessageCallbacks(store))
      consumer.start
    } catch {
      case e: java.net.ConnectException =>
        restart("Error connecting to " + host + ":" + port, e)
      case t: Throwable =>
        restart("Error receiving data", t)
    }
  }

}
