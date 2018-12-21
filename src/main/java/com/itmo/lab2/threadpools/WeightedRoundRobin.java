package com.itmo.lab2.threadpools;

import com.itmo.lab1.math.Matrix;
import com.itmo.lab2.tasksupplier.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class WeightedRoundRobin implements ThreadPoolInterface {

    private Stack<Task> tasks;
    private Map<Integer, Executor> executors;
    private ArrayList<Matrix> result;

    private Integer counter = 0;
    private Integer maxWeight = 0;

    public WeightedRoundRobin(int threadsNumber, Stack<Task> tasks) {
        this.tasks = tasks;

        result = new ArrayList<>();
        executors = new HashMap<>();
        maxWeight = threadsNumber;
        for (int i = 0; i < threadsNumber; i++) {
            executors.put(i, new Executor());
        }
    }

    public ArrayList<Matrix> getResult() {
        return result;
    }

    public void run() {
        Thread thread = new Thread(() -> {

            while (!isDone()) {
                counter++;
                for (int i = 0; counter + i < maxWeight; i++) {
                    Executor executor = executors.get(counter + i);

                    if (executor.isDone()) {
                        if (!executor.isResultRead()) {
                            result.add(executor.getResult());
                        }

                        if (!tasks.empty()) {
                            executor.setTask(tasks.pop()).start();
                        }
                    }
                }
                if (counter > maxWeight) counter = 0;
            }

        });
        thread.start();
    }


    public Boolean isDone() {

        for (Executor executor : executors.values()) {
            if (!executor.isDone()) return false;
            if (!executor.isResultRead()) return false;
        }

        return tasks.empty();

    }

    private class Executor {
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
}
