package com.task.run;

import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * 任务执行器
 * @author 韩国峰
 * @time 2021/08/01 12:01
 * 多线程方式实现运行
 */
public class RunTaskByThreadPool implements TaskRunWay{
    private int corePoolSize = 10;
    private int maximumPoolSize = 100;
    private long keepAliveTime = 1000;
    private TimeUnit unit = TimeUnit.MINUTES;
    private BlockingQueue<Runnable> workQueue;
    private ThreadFactory threadFactory;
    private RejectedExecutionHandler handler;
    /**
     * 多线程共用的线程池,线程池为对象私有
     */
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 运行一个任务
     * @param <V> 任务类型
     * @param v 任务需要消耗的对象
     * @param task 任务执行器
     * @return
     */
    @Override
    public <V> boolean runTask(V v, Task<V> task) {
        getThreadPool().execute(()-> task.runTask(v));
        return false;
    }

    /**
     * 将所有的任务放到线程池中执行
     * @param vs 任务队列
     * @param task 任务执行过程
     * @param <V> 任务类型
     */
    @Override
    public <V> void runAllTask(LinkedList<V> vs, Task<V> task) {
        for (V v:vs) {
            runTask(v,task);
        }
    }

    /**
     * 获取当前线程池
     * @return
     */
    private ThreadPoolExecutor getThreadPool(){
        if (Objects.isNull(threadPoolExecutor)){
            synchronized (this){
                if (Objects.isNull(threadPoolExecutor)){
                    workQueue = Objects.isNull(workQueue)?new ArrayBlockingQueue<>(Integer.MAX_VALUE):workQueue;
                    threadFactory = Objects.isNull(threadFactory)?Executors.defaultThreadFactory():threadFactory;
                    handler = Objects.isNull(handler)? new ThreadPoolExecutor.AbortPolicy() :handler;
                    threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,threadFactory,handler);
                }
            }
        }
        return threadPoolExecutor;
    }
}
