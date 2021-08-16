package com.task.run;

import com.task.exception.TaskRunTimeException;

import java.util.LinkedList;

/**
 * 任务执行器
 *  使用了单线程,即当前线程执行任务,是完全串行运行的
 */
public class RunTaskBySingleThread implements TaskRunWay{

    @Override
    public <V> boolean runTask(V v, Task<V> task) {
        task.runTask(v);
        return false;
    }

    @Override
    public <V> void runAllTask(LinkedList<V> vs, Task<V> task) throws TaskRunTimeException {
        for (V v:vs) {
          runTask(v,task);
        }
    }
}
