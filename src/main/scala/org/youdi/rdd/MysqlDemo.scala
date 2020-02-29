package org.youdi.rdd
//
//import java.sql.{Connection, PreparedStatement}
//
//import org.apache.spark.rdd.{JdbcRDD, RDD}
//import org.apache.spark.{SparkConf, SparkContext}
//
//
///**
// * 一个excute一个connect 一个分区
// */
//
//object MysqlDemo {
//  def main(args: Array[String]): Unit = {
//    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
//    // 上下文
//    var sc: SparkContext = new SparkContext(conf)
//    val driver = "com.mysql.jdbc.Driver"
//    val url = "jdbc:mysql://127.0.0.1:3306/ireadweek"
//    val user = "root"
//    val pass = ""
//    val dataRDD: RDD[(Int, Int)] = sc.makeRDD(List((1, 2), (1, 2)))
//
//    //    // 创建jdbcRDD，方法数据库
//    //    val sql = "select * from ireadweek where id >= ? and id <= ?"
//    //    val jdbcRDD = new JdbcRDD(sc, () => {
//    //      // 获取数据库对象
//    //      Class.forName(driver)
//    //      java.sql.DriverManager.getConnection(url, user, pass)
//    //    },
//    //      sql
//    //      , 1
//    //      , 10
//    //      , 2,
//    //      (rs) => {
//    //        rs.toString
//    //      }
//    //    )
//    //
//    //    jdbcRDD.collect().foreach(println)
//
//
//    //
//    //    dataRDD.foreachPartition(datas => {
//    //      Class.forName(driver)
//    //      val connection: Connection = java.sql.DriverManager.getConnection(url, user, pass)
//    //      val sql = "inii (？，？)"
//    //      datas.foreach(
//    //      case (user, age) => {
//    //        val statement: PreparedStatement = connection.prepareStatement(sql)
//    //        statement.setInt(1, user)
//    //        statement.setInt(2, age)
//    //        statement.executeUpdate()
//    //        statement.close()
//    //      }
//    //    }
//    //
//    //  }
//
//
////    dataRDD.foreachPartition(
////      datas => {
////        Class.forName(driver)
////        val connection: Connection = java.sql.DriverManager.getConnection(url, user, pass)
////        val sql = "inii (？，？)"
////        datas.foreach(
////          case (user , age) => {
////          val statement: PreparedStatement = connection.prepareStatement(sql)
////          statement.setInt(1, user)
////          statement.setInt(2, age)
////          statement.executeUpdate()
////          statement.close()
////        }
////        )
////      }
//    )
//  }
//}
