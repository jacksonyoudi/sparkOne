package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 分组， 按照传入函数的返回值进行分组，将相同的key对应的值放入一个迭代器中
 *
 * group by返回值
 *
 *
 */
object GroupByDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    // 生成数据
    val values: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7))

    // 按函数的返回值的结果进行分组， 分组后的数据形成对偶元祖，values是一个可以迭代的序列
    val value: RDD[(Int, Iterable[Int])] = values.groupBy(_ % 2)

    value.collect().foreach(println)

  }
}
