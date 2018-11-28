package com.itmo.lab2.threadpools;

import com.itmo.lab1.math.Matrix;
import com.itmo.lab2.tasksupplier.Task;

class Executor {
    private Task task;
    private Matrix result;
    private Boolean done = true;
    private Boolean isResultRead = true;

    Boolean isResultRead() {
        return isResultRead;
    }

    Matrix getResult() {
        isResultRead = true;
        return result;
    }

    Executor setTask(Task task) {
        this.task = task;
        done = false;
        isResultRead = false;
        return this;
    }

    Boolean isDone() {
        return done;
    }

    void start() {
        Thread thread = new Thread(() -> {
            result = task.execute();
            done = true;
        });
        thread.start();
    }
}
