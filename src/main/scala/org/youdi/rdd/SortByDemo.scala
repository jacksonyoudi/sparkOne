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
    //    rdd.foldByKey() 底层和aggregateByKey 分区内核分区间  算法相同

    // combineByKey
    /*
        combineByKeyWithClassTag[U]((v: V) =>
        cleanedSeqOp(createZero(), v),  分区内 (初始值，value)
      cleanedSeqOp, 分区内
       combOp, 分区间
        partitioner)
     */
    // combineByKeyWithClassTag(createCombiner, mergeValue, mergeCombiners)(null)
    rdd.combineByKey(
      (_, 1),
      (acc: (Int, Int), v) => (acc._1 + v, acc._2 + 1),
      (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
    )

    // sortByKey(ascending: Boolean = true, numPartitions: Int = self.partitions.length)
//    rdd.sortByKey()

// mapValues() 只对value操作
    // join 可以相同 性能比较低
    // cogroup  类似left join

  }
}
