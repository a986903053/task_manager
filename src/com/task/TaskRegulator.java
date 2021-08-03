package com.task;

import java.util.*;
import java.util.function.Predicate;

/**
 * 任务管理类
 * @Time 2021/7/21 16:17
 * @Aythor 韩国峰
 */
public class TaskRegulator<K,V> {

    private final Map<K,LinkedList<V>> taskMap= new HashMap<>();
    private final Map<K,Predicate<V>> preMap= new HashMap<>();

    public void putTaskList(K key, List<? extends V> vals){
        for (V val:vals) { putTask(key,val); }
    }

    public void putTask(K key,V val){
        if (taskMap.containsKey(key)){
            taskMap.get(key).addLast(val);
        }else{
            taskMap.put(key,new LinkedList<>());
            putTask(key,val);
        }
    }
    public void putPredicate(K key,Predicate<V> pred){
        preMap.put(key,pred);
    }

    public int consumerFirstTask(K key){
        LinkedList<V> vs = taskMap.get(key);
        Predicate<V> vPredicate = preMap.get(key);
        if (vs.size()<1){
            return -1;
        }
        V first = vs.getFirst();
        boolean test = vPredicate.test(first);
        if (test && vs.getFirst().equals(first)){
            vs.removeFirst();
            return 1;
        }
        return 1;
    }

    public void consumerAllTask(K key){
        LinkedList<V> vs = taskMap.get(key);
        int sum = vs.size();
        int excepitonSum = 0;
        while(sum>0){
            try {
                consumerFirstTask(key);
            }catch (Exception e){
                e.printStackTrace();
                excepitonSum++;
                System.err.println(excepitonSum);
            }
            sum = vs.size();
        }
    }

    public Integer getTaskListSize(K key){
        return taskMap.get(key).size();
    }
}
