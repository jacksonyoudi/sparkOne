package org.ip

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}


object ParseIp {
  def main(args: Array[String]): Unit = {
    // 生成时间
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Sip")
    val spark = SparkSession.builder().config(sparkConf).appName("Sip").getOrCreate()

    spark.read.parquet("./input").createOrReplaceTempView("udp")
    val df: DataFrame = spark.sql("select stat_time,app,ip,src_ip from udp limit 100")

    import spark.implicits._
    val dataFrame: DataFrame = df.map {
      case Row(stat_time: BigInt, ap: String, ip: BigInt, src_ip: String)
      => {
        val srcIpInfo: IPInfo = IP.findIp(src_ip)
        val dstIpInfo: IPInfo = IP.findIntIp(ip)
        (stat_time, ap, ip, src_ip)
        (stat_time, ap, ip, dstIpInfo.country, dstIpInfo.region, dstIpInfo.city, dstIpInfo.isp, src_ip, srcIpInfo.country, srcIpInfo.region, srcIpInfo.city, srcIpInfo.isp)
      }
    }.toDF("stat_time", "app", "ip", "src_ip")
    }.toDF("stat_time", "app", "ip", "dst_country", "dst_region", "dst_city", "dst_isp", "src_ip", "src_country", "src_region", "src_city", "src_isp")


  val dataFrame: DataFrame = df.map(
    (row) => {
      val stat_time: BigInt = row.getLong(0)
      val ap: String = row.getString(1)
      val ip: BigInt = row.getLong(2)
      val src_ip: String = row.getString(3)

      val srcIpInfo: IPInfo = IP.findIp(src_ip)
      val dstIpInfo: IPInfo = IP.findIntIp(ip)


      (stat_time, ap, ip, dstIpInfo.country, dstIpInfo.region, dstIpInfo.city, dstIpInfo.isp, src_ip, srcIpInfo.country, srcIpInfo.region, srcIpInfo.city, srcIpInfo.isp)

    }
  ).toDF("stat_time", "app", "ip", "dst_country", "dst_region", "dst_city", "dst_isp", "src_ip", "src_country", "src_region", "src_city", "src_isp")

  dataFrame.show(100)
  spark.stop()
}

}
