package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * RDD的创建
 * 弹性分布式数据集
 *
 * 1. 从集合中创建
 * 2. 从外部存储中
 * 3. 其他RDD转换的
 *
 *
 * 广播变量： 分布式只读共享变量
 * 累加器: 分布式只写共享变量
 */

object Demo {
  def main(args: Array[String]): Unit = {
    // 创建RDD
    // 1) 从内存中创建
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    // 从内存中创建
    // makeRDD 并行度
    /**
     * override def defaultParallelism(): Int = {
     *     conf.getInt("spark.default.parallelism", math.max(totalCoreCount.get(), 2))
     * }
     *
     * totalCoreCount
     *
     * 自定义分区
     */
    val listRdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9), 2)

    listRdd.collect().foreach(println)
    val value: RDD[Int] = sc.parallelize(Array(1, 2, 3, 4, 5, 6))

    // 从外部存储中获取
    // 默认情况下可以使用项目路径， 也可以使用其他路径
    /**
     * def textFile(
     * path: String,
     * minPartitions: Int = defaultMinPartitions): RDD[String] = withScope {
     * assertNotStopped()
     * hadoopFile(path, classOf[TextInputFormat], classOf[LongWritable], classOf[Text],
     * minPartitions).map(pair => pair._2.toString).setName(path)
     * }
     *
     * 使用 hadoop 文件分区的概念  最小分区数 取决于hadoop的分区规则
     * def defaultMinPartitions: Int = math.min(defaultParallelism, 2)
     */
    val fileRdd: RDD[String] = sc.textFile("/Users/youdi/Project/javaProject/sparkOne/input")
    fileRdd.collect().foreach(print)

    // 将rdd保存到文件中
    listRdd.saveAsTextFile("out")

  }
}
