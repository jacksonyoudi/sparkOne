package org.youdi.file

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * json是按行读取的
 *
 * parquet
 */

object Write {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("udaf")
    val spark: SparkSession = SparkSession.builder().appName("SparkSessionExample").config(conf).getOrCreate()


    val df: DataFrame = spark.read.format("json").load("")
    val fs: Unit = df.write.format("json").mode("append").save("")

    val jdbcDf: DataFrame = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://hadoop:3306/rdd")
      .option("dbtable", "test.name")
      .option("user", "root")
      .option("password", "hello")
      .load()

    //    jdbcDf.write.mode("append").jdbc("
    jdbcDf.createTempView("tt")


    spark.

      spark.stop()
  }
}
