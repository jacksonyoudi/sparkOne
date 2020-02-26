package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * groupbykey
 *
 * reducebyKey() 按照key聚合，在shuffle之前有一个combine预聚合操作，返回结果是RDD[k,v]
 *
 * 如果在shuffle之前有预聚合 combine，可以提高效率
 *
 * aggregateByKey  根据
 *
 * def aggregateByKey[U: ClassTag](zeroValue: U)(seqOp: (U, V) => U,
 * combOp: (U, U) => U): RDD[(K, U)] = self.withScope {
 * aggregateByKey(zeroValue, defaultPartitioner(self))(seqOp, combOp)
 * }
 *
 *
 */


object SortByDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val keyRDD: RDD[(Int, String)] = sc.makeRDD(List((1, "a"), (2, "b")))
    keyRDD.aggregateByKey()

    val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("a", 2), ("c", 3), ("b", 4), ("c", 2), ("b", 5)))
    rdd.aggregateByKey(0)(math.max(_, _), _ + _) // 分区内取最大值， 分区间相加
  }
}
