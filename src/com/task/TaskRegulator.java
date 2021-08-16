package com.task;

import com.task.bean.RunTimeToDo;
import com.task.exception.TaskRunTimeException;
import com.task.run.Task;
import com.task.run.TaskRunWay;

import java.util.*;
import java.util.function.Consumer;

/**
 * 任务管理类
 * @Time 2021/7/21 16:17
 * @Aythor 韩国峰
 */
public class TaskRegulator<K,V,T> {

    private final Map<K,Task<V>> preMap= new HashMap<>();
    private final Map<K,LinkedList<V>> taskMap= new HashMap<>();
    /**
     * 这个map用于运行时对于设置异常处理到达一定数量后执行容灾机制,的异常数量
     */
    private final Map<K, RunTimeToDo<T>> taskRunTimeMaxExceptionToDoMap= new HashMap<>();
    /**
     * 这个map用于运行时对于设置异常处理到达一定数量后执行容灾机制,的异常处理方式
     */
    private final Map<K,Integer> taskRunTimeMaxExceptionCountMap= new HashMap<>();


    private TaskRunWay taskRunWay;

    /**
     * 批量插入任务消耗对象
     * @param key 任务名称
     * @param vals 任务消耗品
     */
    public void putTaskList(K key, List<? extends V> vals){
        for (V val:vals) { putTask(key,val); }
    }
    /**
     * 插入任务消耗对象
     * @param key 任务名称
     * @param val 任务消耗品
     */
    public void putTask(K key,V val){
        if (taskMap.containsKey(key)){
            taskMap.get(key).addLast(val);
        }else{
            taskMap.put(key,new LinkedList<>());
            putTask(key,val);
        }
    }

    /**
     * 插入任务执行方法
     * @param key 任务名称
     * @param pred 任务执行方法
     */
    public void putPredicate(K key, Task<V> pred){
        preMap.put(key,pred);
    }

    /**
     * 消耗掉第一个任务
     * @param key 任务名称
     * @return
     * @throws TaskRunTimeException
     */
    public boolean consumerFirstTask(K key) throws TaskRunTimeException {
        LinkedList<V> vs = taskMap.get(key);
        Task<V> task = preMap.get(key);
        if (vs.size()<1){ return false; }
        V first = vs.getFirst();
        if (vs.getFirst().equals(first) && taskRunWay.runTask(first,task)){
            vs.removeFirst();
        }
        return true;
    }

    /**
     * 消耗掉所有的任务
     * @param key 任务名称
     * @throws TaskRunTimeException 任务执行中异常
     */
    public void consumerAllTask(K key) throws TaskRunTimeException {
        LinkedList<V> vs = taskMap.get(key);
        Task<V> task = preMap.get(key);
        taskRunWay.runAllTask(vs,task);
    }





    public Map<K, Task<V>> getPreMap() {
        return preMap;
    }
    public TaskRunWay getTaskRunWay() {
        return taskRunWay;
    }
    public Map<K, LinkedList<V>> getTaskMap() {
        return taskMap;
    }
    public Integer getTaskListSize(K key){
        return taskMap.get(key).size();
    }
}
