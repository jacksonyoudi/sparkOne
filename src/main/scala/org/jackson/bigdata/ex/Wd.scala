package org.jackson.bigdata.ex

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * java IO
 * input output
 * byte char(reader writer) 读取内容
 * 文件输入流
 * InputStream in = new FileInputStream("xxxx")
 * 缓冲流
 * InputStream bufferIn = new BufferedInputStream(new FileInputStream("xxxx"))
 *
 * 使用字符流读取一行数据
 * Reader reader = new BufferReader(new InputStreamReader(in, "UTF-8"))
 *
 *
 *
 * RDD: 装饰者模式，将数据进行逻辑转换  控制抽象
 * 弹性分布式数据集是spark中最基本的数据抽象，代码中一个抽象类，它代表一个不可变，可分区 里面的元素可并行计算的集合。
 * 不可变:原数据不能修改数据，为了回溯
 * 可分区: 并行计算
 *
 *
 * * Internally, each RDD is characterized by five main properties:
 * *
 * *  - A list of partitions
 * *  - A function for computing each split
 * *  - A list of dependencies on other RDDs
 * *  - Optionally, a Partitioner for key-value RDDs (e.g. to say that the RDD is hash-partitioned)
 * *  - Optionally, a list of preferred locations to compute each split on (e.g. block locations for
 * *    an HDFS file)
 *
 * RDD属性
 * 一组分区(partition) 即数据集的基本组成单位
 * 一个计算每个分区的函数
 * RDD之间的依赖的函数 血缘 血统  DAG
 * 一个parttioner，即RDD的分片函数
 * 一个列表，存储存取每个partition的优先位置(preferred location)
 *
 *
 * 优先位置：
 * 移动数据不如移动计算  网络IO  距离
 *
 *
 * RDD特点:
 * 1. 不可变
 * 2. 存在依赖
 * 3. 血缘关系延时计算，可以使用持久化进行
 *
 *
 *
 * 1. 只读
 * 2. 算子 从认知心理学角度，解决问题，其实就是将问题的初始状态，通过一系列的操作对问题的状态的转换，然后达到完成(解决) 状态 Operate 方法
 *      算子功能不同， 1. 转化算子  2. 执行算子 action
 *      spark中的所有RDD方法都是算子， 但是分为两大类 转化算子，行动算子
 * 3. 依赖
 * 4.缓存 防止血缘中断
 * 5. checkpoint
 */


object Wd {
  def main(args: Array[String]): Unit = {
    // 配置， 运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")

    // 上下文对象
    val sc = new SparkContext(sparkConf)

    // 读取文件，将文件内容一行一行的读取出来

    // 如果从本地中使用 file://
    val lines: RDD[String] = sc.textFile("file://Users/youdi/Project/javaProject/sparkOne/input")

    val words: RDD[String] = lines.flatMap(_.split(" "))
    val wordToOne: RDD[(String, Int)] = words.map((_, 1)) // tuple 2

    // 分组聚合
    val wordSum: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)

    val array: Array[(String, Int)] = wordSum.collect()
    array.foreach(println)
  }

}
