package com.itmo.lab2;

import com.itmo.lab1.math.Calculator;
import com.itmo.lab1.math.Matrix;
import com.itmo.lab2.tasksupplier.Task;
import com.itmo.lab2.tasksupplier.TaskSupplier;
import com.itmo.lab2.threadpools.RoundRobin;

import java.util.ArrayList;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        TaskSupplier supplier = new TaskSupplier();
        Stack<Task> tasks = new Stack<>();
        tasks.addAll(supplier.getTasks());

        RoundRobin roundRobin = new RoundRobin(2, tasks);
        roundRobin.run();

        char[] animationChars = new char[]{'|', '/', '-', '\\'};
        int i = 0;

        while (!roundRobin.isDone()) {
            System.out.print("Processing: " + animationChars[i % 4]);
            i++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\r");

        }

        ArrayList<Matrix> result = roundRobin.getResult();
        Calculator calculator = new Calculator();
        System.out.println(result.stream().reduce(calculator::sum).get().getStringValues());

        System.out.println("Finish");
    }
}
