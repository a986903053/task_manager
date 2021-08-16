package com.task.bean;

import java.util.function.Consumer;

public class RunTimeToDo<T> {
    T t;
    Consumer<T> consumer;

    public RunTimeToDo() {
    }
    public RunTimeToDo(Consumer<T> consumer, T t) {
        this.consumer = consumer;
        this.t = t;
    }



    public Consumer<T> getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
