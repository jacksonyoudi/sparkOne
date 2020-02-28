package org.youdi.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 依赖关系 每个RDD都有依赖关系
 * lineage:
 *  1. 容错
 *
 * toDebugString
 *
 *
 * 窄依赖: 一对一 NarrowDependency
 *
 *
 * 宽依赖：除了NarrowDependency 就是宽依赖 shuffle
 *
 * 对于窄依赖，partition的转换处理在stage中完成计算，对于宽依赖，由于有shuffle的存在，只能在parent RDD处理
 * 完成后，才能开始接下来的计算，因此宽依赖是划分stage的依据。
 *
 *
 *
 * RDD任务划分中间分为: Application， JOb， Stage 和 Task
 * 1. Application: 初始化一个sparkContext即生成一个Appplication
 * 2. Job 一个Action算子就会生成一个Job  activejob runjob
 * 3. Stage： 根据RDD之间的依赖关系的不同将job划分成不同的stage， 遇到一个宽依赖则划分一个stage。
 * 4. task： stage是一个taskset， 将stage划分的结果发送到不同的excutor执行即为一个task。
 *
 * application -> job -> stage -> task 每一层都是一对多
 */

object Lineage {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("makeRdd")
    // 上下文
    var sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8))
    //    rdd.dependencies

  }
}
