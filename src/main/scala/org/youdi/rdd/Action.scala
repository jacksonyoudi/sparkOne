package org.youdi.rdd

import org.apache.spark.{SparkConf, SparkContext}


/**
 * action
 *  runJob()
 *
 *
 * reduce(func)
 * 通过func函数聚集RDD中的所有元素，先聚合分区内的数据，再聚合分区间的数据
 *
 * collect()案例
 * 在驱动程序中，以数组的形式返回数据集的所有元素
 *
 * count()
 *
 * first() 返回第一元素
 *
 * take() 取前几个
 *
 * taskOrdered(3) 排序取前3
 *
 * aggregate 初始值， 分区内计算公式 分区间计算公式 注意： 初始值对分区间计算是有影响的
 *
 * fold()() 是分区间和分区内的计算一样的  初始值 计算公式
 *
 * saveAsTextFile(path) 将数据集的元素以textfile的形式保存到HDFS文件系统或者其他支持的文件系统中
 * 对于每个元素，spark将会调用toString方法，将它转换成文本
 *
 * saveAsSequenceFile(path)
 *
 * saveAsObjectFile(path)
 *
 *
 * countByKey() 返回map
 *
 * foreach(func) rdd中func   array是放在driver中的内存
 *
 *
 *
 *
 */
object Action {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)
  }
}
