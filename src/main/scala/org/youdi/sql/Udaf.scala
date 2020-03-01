package org.youdi.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, LongType, StructType}

object Udaf {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("udaf")
    val spark: SparkSession = SparkSession.builder().appName("SparkSessionExample").config(conf).getOrCreate()

    //  创建自定义的函数对象
    val ageAvgFunction = new MyAgeAvgFunction
    spark.udf.register("avgAge", ageAvgFunction)


    val dataFrame: DataFrame = spark.read.json("input/user.json")
    //    dataFrame.show()
    dataFrame.createOrReplaceTempView("user")
    spark.sql("select avgAge(age) from user").show()

    spark.stop()

  }
}

// 声明用户自定义函数
/**
 * 1 继承
 * 2. 实现方法
 */
class MyAgeAvgFunction extends UserDefinedAggregateFunction {
  // 输入的数据结构
  override def inputSchema: StructType = {
    new StructType().add("age", LongType)
  }

  // 计算时的数据结构
  override def bufferSchema: StructType = {
    new StructType().add("sum", LongType).add("count", LongType)
  }

  // 函数返回的数据类型
  override def dataType: DataType = DoubleType

  // 是否稳定
  override def deterministic: Boolean = true

  // 计算前， 缓冲区 初始化
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    // sum count
    buffer(0) = 0L
    buffer(1) = 0L
  }

  // 根据查询结果进行 更新数据
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer.getLong(0) + input.getLong(0)
    buffer(1) = buffer.getLong(1) + 1
  }

  // 将多个节点的缓冲区数据合并
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }

  // 计算 最终结果
  override def evaluate(buffer: Row): Any = {
    buffer.getLong(0).toDouble / buffer.getLong(1)
  }
}
