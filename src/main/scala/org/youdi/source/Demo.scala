package org.youdi.source

import org.apache.spark.deploy.SparkSubmit
import org.apache.spark.deploy.yarn.YarnClusterApplication
import org.apache.spark.executor.CoarseGrainedExecutorBackend

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
 *
 */


/**
 * ApplicationMaster
 *    -- main
 *    -- new ApplicationMasterArguments(args)
 *    -- new ApplicationMaster
 *    -- master.run
 *       cluster
 *        runDriver
 *          // 启动用户的应用
 *          -- startUserApplication
 *
 *          // 获取用户应用的类的main方法
 *          -- userClassLoader.loadClass(args.userClass).getMethod("main",classOf[Array[String]))
 *
 *          // 启动driver线程，调用用户的main方法
 *          -- new Thread.start().setName("driver")
 *
 *          // 注册AM
 *          registerAM
 *              // 获取yarn资源
 *              -- client.register(host, port, yarnConf, _sparkConf, uiAddress, historyAddress)
 *
 *              // 分配资源
 *              -- allocator.allocateResources()
 *              -- handleAllocatedContainers()
 *                -- runAllocatedContainers()
 *                    -- new ExecutorRunnable().run()  --> NM
 *                      -- startContainer
 *                         -- prepareCommand
 *                              org.apache.spark.executor.CoarseGrainedExecutorBackend
 *
 *
 *
 * * 3. Excuter
 * *    CoarseGrainedExecutorBackend
 *      -- main
 *         -- onstart
 *            -- ref.ask[Boolean](RegisterExecutor)
 *         -- receive
 *            -- case RegisteredExecutor
 *               -- new Executor
 *
 *
 *
 *
 */


object Demo {
  def main(args: Array[String]): Unit = {
  }
}
