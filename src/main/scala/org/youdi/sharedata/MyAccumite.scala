package org.youdi.sharedata

import java.util

import org.apache.spark.rdd.RDD
import org.apache.spark.util.{AccumulatorV2, LongAccumulator}
import org.apache.spark.{SparkConf, SparkContext}

object MyAccumite {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)
    val rdd: RDD[String] = sc.makeRDD(List("one", "h", "hello", "m", "hi"))


    //TODO 创建累加器
    val wac = new WordAccumulator
    // 注册累加器
    sc.register(wac)

    rdd.foreach {
      case i => {
        wac.add(i)
      }
    }

    println(wac.value.toString{})

    sc.stop()

  }
}

// 声明累加器
// 继承父类
class WordAccumulator extends AccumulatorV2[String, util.ArrayList[String]] {
  val list = new util.ArrayList[String]()

  // 当前的累加器是否是初始化状态
  override def isZero: Boolean = {
    list.isEmpty
  }

  // 复制累加器
  override def copy(): AccumulatorV2[String, util.ArrayList[String]] = {
    new WordAccumulator()
  }

  // 重置累加器
  override def reset(): Unit = {
    list.clear()
  }

  // 向累加器中增加数据
  override def add(v: String): Unit = {
    if (v.contains("h")) {
      list.add(v)
    }
  }

  // 合并累加器
  override def merge(other: AccumulatorV2[String, util.ArrayList[String]]): Unit = {
    list.addAll(other.value)
  }

  // 获取累加器的结果
  override def value: util.ArrayList[String] = {
    list
  }
}
