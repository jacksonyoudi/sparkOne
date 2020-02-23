package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * RDD有两种类型 value和 key-value类型
 * 关心map中数据
 * map 转换 一个参数， 返回一个值
 *
 *
 * 整个分区 关心分区
 * mapPatitions(fun)
 * 类似RDD，但独立在每个分片(分区)上运行，因此在类型为T的RDD上运行时，func的函数类型必须是Iterator[T] => Iterator[U]
 * 效率更高， 减少了发送到执行器执行交互次数
 * 可能会出现  OOM
 *
 *
 * mapPartitionIndex()
 *
 *
 * def mapPartitions[U: ClassTag](
 * f: Iterator[T] => Iterator[U],
 * preservesPartitioning: Boolean = false): RDD[U] = withScope {
 * val cleanedF = sc.clean(f)
 * new MapPartitionsRDD(
 * this,
 * (context: TaskContext, index: Int, iter: Iterator[T]) => cleanedF(iter),
 * preservesPartitioning)
 * }
 * 不同分区做不同处理，和task关联
 *
 *
 */
object RDDMap {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RddValue")
    val sc: SparkContext = new SparkContext(conf)

    // map算子
    val listOne: RDD[Int] = sc.makeRDD(1 to 10, 2)
    val mapRdd: RDD[Int] = listOne.map(x => {
      x * 2
    })

    mapRdd.collect().foreach(println)

    // 可以直接对一个RDD中所有分区中进行操作
    val mapPartitionRDD: RDD[Int] = mapRdd.mapPartitions(datas => {
      // scala计算
      datas.map(2 * _)

    })
    mapPartitionRDD.collect().foreach(println)

    val indexRDD: RDD[(Int, Int)] = listOne.mapPartitionsWithIndex {
      // 使用模式匹配
      case (num, datas) => {
        datas.map((num, _))
      }
    }

    indexRDD.collect().foreach(println)

  }
}
