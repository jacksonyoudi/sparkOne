package org.youdi.sql

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}


object SparkSqlTranform {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("sql")
    val spark: SparkSession = SparkSession.builder().appName("SparkSessionExample").config(conf).getOrCreate()
    // 上下文

    // 创建rdd
    val rdd: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(List((1, "hello", 20), (2, "name", 30)))
    // 引入隐式转换规则
    import spark.implicits._
    val frame: DataFrame = rdd.toDF("id", "name", "age")

    // ds
    val ds: Dataset[User] = frame.as[User]

    val frame1: DataFrame = ds.toDF()
    val rdd1: RDD[Row] = frame1.rdd
    rdd1.foreach(row => {
      println(
        // 索引访问数据
        row.get(
          1
        ))
    })


    spark.stop()
  }
}

case class User(id: Int, name: String, age: Int)