package com.task.run;

import com.task.exception.TaskRunTimeException;

import java.util.LinkedList;

public interface TaskRunWay {

    <V> boolean runTask(V vs, Task<V> task) throws TaskRunTimeException;

    <V> void runAllTask(LinkedList<V> vs,Task<V> task) throws TaskRunTimeException;
}
