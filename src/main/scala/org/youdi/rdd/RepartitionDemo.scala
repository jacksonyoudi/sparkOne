package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
 * 重新分区
 *
 *
 * coalesce 重新分区，可以选择是否进行shuffle过程
 * def coalesce(numPartitions: Int, shuffle: Boolean = false,
 * partitionCoalescer: Option[PartitionCoalescer] = Option.empty)
 *
 *
 * def repartition(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T] = withScope {
 * coalesce(numPartitions, shuffle = true)
 * }
 *
 * repartition 一定会shuffle
 *
 *
 * union
 * zip 拉链 注意分区中数据要一一对应
 *    rdd1.zip(rdd2)
 *
 *
 * 分区器
 * partitionerBy()
 */
object RepartitionDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.parallelize(1 to 16, 4)
    println(value.partitions.size)

    val value1: RDD[Int] = value.repartition(2)
    println(value1.partitions.size)


    val value2: RDD[Int] = value.union(value1)

    val value3: RDD[(Int, String)] = sc.parallelize(Array((1, "a"), (2, "b"), (3, "c")))
    value3.partitionBy(new HashPartitioner(2))

  }
}
