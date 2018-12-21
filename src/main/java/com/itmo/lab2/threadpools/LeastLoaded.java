package com.itmo.lab2.threadpools;

import com.itmo.lab1.math.Calculator;
import com.itmo.lab1.math.Matrix;
import com.itmo.lab2.tasksupplier.Task;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class LeastLoaded implements ThreadPoolInterface {

    private Stack<Task> tasks;
    private ArrayList<Executor> executors;
    private ArrayList<Matrix> result;

    public LeastLoaded(int threadsNumber, Stack<Task> tasks) {
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
                int pendingRequestCount = 9999999;
                Executor currentExecutor = executors.get(0);
                for (Executor executor : executors) {
                    if (pendingRequestCount > executor.getPendingRequestCount()) {
                        pendingRequestCount = executor.getPendingRequestCount();
                        currentExecutor = executor;
                    }
                }

                if (!tasks.empty())
                    currentExecutor.addTask(tasks.pop()).start();

            }

            stopAll();

        });
        thread.start();
    }

    public Boolean isDone() {

        for (Executor executor : executors) {
            if (!executor.isDone()) return false;
        }
        return tasks.empty();

    }


    private void stopAll() {
        for (Executor executor : executors) {
            result.add(executor.getResult());
        }
    }

    class Executor {
        private Queue<Task> tasks = new LinkedBlockingQueue<>();
        private Matrix result;
        private Boolean isDone = false;
        private Calculator calculator = new Calculator();


        public Boolean isDone() {
            return isDone;
        }

        public int getPendingRequestCount() {
            return tasks.size();
        }

        Matrix getResult() {
            return result;
        }

        Executor addTask(Task task) {
            tasks.add(task);
            return this;
        }

        void start() {
            isDone = false;
            Thread thread = new Thread(() -> {
                while (!isDone) {
                    if (tasks.isEmpty()) {
                        isDone = true;
                        break;
                    }

                    Task peek = tasks.poll();
                    if (result != null)
                        result = calculator.sum(result, peek.execute());
                    else
                        result = peek.execute();
                }
            });
            thread.start();
        }

    }

}
