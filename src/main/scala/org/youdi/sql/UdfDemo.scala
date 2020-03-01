package org.youdi.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}


object UdfDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("udaf")
    val spark: SparkSession = SparkSession.builder().appName("SparkSessionExample").config(conf).getOrCreate()

    //  创建自定义的函数对象
    spark.udf.register("addName", (x: String) => ("name:" + x))


    val dataFrame: DataFrame = spark.read.json("input/user.json")
    //    dataFrame.show()
    dataFrame.createOrReplaceTempView("user")
    spark.sql("select addName(user),age from user").show()

    spark.stop()
  }
}
