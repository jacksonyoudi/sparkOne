package org.youdi.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Encoders, SparkSession, TypedColumn}

object UfafClass {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("udaf")
    val spark: SparkSession = SparkSession.builder().appName("SparkSessionExample").config(conf).getOrCreate()


    // 创建聚合函数
    val udaf = new MyAgeAvgClassFuction
    // 将聚合函数转换为查询列
    val avgCol: TypedColumn[UserBean, Double] = udaf.toColumn.name("avgAge")

    import spark.implicits._
    val frame: DataFrame = spark.read.json("input/user.json")
    // Cannot up cast `age` from bigint to int as it may truncate
    val ds: Dataset[UserBean] = frame.as[UserBean]
    // 应用函数
    ds.select(avgCol).show()


    spark.stop()
  }
}

case class UserBean(user: String, age: BigInt)

case class AvgBuffer(var sum: BigInt, var count: Int)


//声明用户自定义类聚合函数(强类型)
//继承Aggregator
//实现
class MyAgeAvgClassFuction extends Aggregator[UserBean, AvgBuffer, Double] {

  //初始化
  override def zero: AvgBuffer = {
    AvgBuffer(0, 0)
  }

  /**
   * 聚合数据
   *
   * @param b
   * @param a
   * @return
   */
  override def reduce(b: AvgBuffer, a: UserBean): AvgBuffer = {
    b.sum += a.age
    b.count += 1
    b
  }

  /**
   * 分区间合并
   *
   * @param b1
   * @param b2
   * @return
   */
  override def merge(b1: AvgBuffer, b2: AvgBuffer): AvgBuffer = {
    b1.sum += b2.sum
    b1.count += b2.count
    b1
  }

  /**
   *
   * @param reduction
   * @return
   */
  override def finish(reduction: AvgBuffer): Double = {
    reduction.sum.toDouble / reduction.count
  }

  override def bufferEncoder: Encoder[AvgBuffer] = Encoders.product

  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}
