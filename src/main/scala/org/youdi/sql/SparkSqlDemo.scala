package org.youdi.sql

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object SparkSqlDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    val session: SparkSession = SparkSession.builder().appName("SparkSessionExample").config(conf).getOrCreate()
    // 上下文

    val dataFrame: DataFrame = session.read.json("input/user.json")
    //    dataFrame.show()
    // 采用sql语法访问
    // 将dataframe转换成表
    dataFrame.createOrReplaceTempView("user")
    session.sql("select * from user").show()
    //
    session.stop()
  }
}
