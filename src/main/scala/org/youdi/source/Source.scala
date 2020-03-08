package org.youdi.source


/**
 * Driver:
 *  spark的驱动节点，用于执行spark的main方法，负责实际代码的执行工作，driver在spark作业执行时主要负责
 *   1. 将用户程序转化为作业(job)
 *   2. 在executor之间调度任务(task)
 *   3. 跟踪excutor的执行情况
 *   4. 通过UI展示运行情况
 *
 *
 * Executor
 *  spark excutor节点是一个计算对象， backend，负责在spark作业中运行具体任务，任务彼此之间相互独立，spark应用启动时，
 *  executoe节点被同时启动，并且始终伴随着整个spark应用的生命周期而存在，如果有executor节点发生故障或崩溃，spark应用也可以继续执行，会将
 *  出错节点的任务调度到其他executor节点上继续运行。
 *
 *  executor有两个核心功能：
 *   1. 负责运行组成spark应用的任务，并将结果返回给驱动器进程
 *   2. 他们通过自身的块管理器(block Manaager)为用户程序中要求缓存的RDD
 *
 */
object Source {

}
