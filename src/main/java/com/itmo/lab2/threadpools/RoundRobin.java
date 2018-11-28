package com.itmo.lab2.threadpools;

import com.itmo.lab1.math.Matrix;
import com.itmo.lab2.tasksupplier.Task;

import java.util.ArrayList;
import java.util.Stack;

public class RoundRobin {

    private Stack<Task> tasks;
    private ArrayList<Executor> executors;
    private ArrayList<Matrix> result;

    public RoundRobin(int threadsNumber, Stack<Task> tasks) {
        this.tasks = tasks;

        result = new ArrayList<>();
        executors = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            executors.add(new Executor());
        }
    }

    public ArrayList<Matrix> getResult() {
        return result;
    }

    public void run() {
        Thread thread = new Thread(() -> {

            while (!isDone()) {
                for (Executor executor : executors) {
                    if (executor.isDone()) {
                        if (!executor.isResultRead()) {
                            result.add(executor.getResult());
                        }

                        if (!tasks.empty()) {
                            executor.setTask(tasks.pop()).start();
                        }
                    }
                }
            }

        });
        thread.start();
    }


    public Boolean isDone() {


        for (Executor executor : executors) {
            if (!executor.isDone()) return false;
            if (!executor.isResultRead()) return false;
        }

        return tasks.empty();

    }

}
