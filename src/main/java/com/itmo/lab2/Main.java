package com.itmo.lab2;

import com.itmo.lab1.math.Calculator;
import com.itmo.lab1.math.Matrix;
import com.itmo.lab2.tasksupplier.Task;
import com.itmo.lab2.tasksupplier.TaskSupplier;
import com.itmo.lab2.threadpools.LeastLoaded;
import com.itmo.lab2.threadpools.RoundRobin;
import com.itmo.lab2.threadpools.ThreadPoolInterface;
import com.itmo.lab2.threadpools.WeightedRoundRobin;

import java.util.ArrayList;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        TaskSupplier supplier = new TaskSupplier();
        Stack<Task> tasks = new Stack<>();
        tasks.addAll(supplier.getTasks());

        runWithThreadPool(new RoundRobin(2, tasks));
        tasks.addAll(supplier.getTasks());
        runWithThreadPool(new WeightedRoundRobin(2, tasks));
        tasks.addAll(supplier.getTasks());
        runWithThreadPool(new LeastLoaded(2, tasks));
    }

    private static void runWithThreadPool(ThreadPoolInterface input) {
        input.run();

        char[] animationChars = new char[]{'|', '/', '-', '\\'};
        int i = 0;

        while (!input.isDone()) {
            System.out.print("Processing: " + animationChars[i % 4]);
            i++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\r");

        }

        ArrayList<Matrix> result = input.getResult();
        Calculator calculator = new Calculator();
        System.out.println(result.stream().reduce(calculator::sum).get().getStringValues());

        System.out.println("Finish");
    }
}
