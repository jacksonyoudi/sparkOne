package org.youdi.source

import org.apache.spark.deploy.SparkSubmit
import org.apache.spark.deploy.yarn.YarnClusterApplication

/**
 * 1. SparkSubmit
 * 启动进程
 * main
 * SparkSubmitArguments 封装参数
 *      handle
 *  提交
 *  submit(appArgs, uninitLog)
 *
 *  // 准备环境
 *  prepareSubmitEnvironment
 *    -- childMainClass = YARN_CLUSTER_SUBMIT_CLASS
 *    private[deploy] val YARN_CLUSTER_SUBMIT_CLASS =
 *        "org.apache.spark.deploy.yarn.YarnClusterApplication"
 *   -- childMainClass = args.mainClass
 *
 *  //
 *  doRunMain -> runMain -> loader(类加载器)
 *      反射加载类
 *      Utils.classForName(childMainClass)
 *      // 查找main方法
 *      val mainMethod = klass.getMethod("main", new Array[String](0).getClass)
 *      // 调用main方法
 *      mainMethod.invoke(null, args)
 *
 *
 *
 *2. Client
 *    main
 *      new ClientArguments(argStrings)
 *      new Client
 *      -- client.run   submitApplication
 *
 *          //封装指令   command= bin/java org.apache.spark.deploy.yarn.ApplicationMaster(Cluster)
 *                      command= bin/java org.apache.spark.deploy.yarn.ExecutorLauncher(Client)
 *          val containerContext = createContainerLaunchContext(newAppResponse)
 *          val appContext = createApplicationSubmissionContext(newApp, containerContext)
 *
 *          // 提交指令
 *          yarnClient.submitApplication(appContext)
 *
 *
 *
 */
object Demo {
  def main(args: Array[String]): Unit = {
    SparkSubmit.main()
  }
}
