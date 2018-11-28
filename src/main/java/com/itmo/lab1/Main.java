package com.itmo.lab1;

import com.itmo.general.fileworker.FileWorker;
import com.itmo.lab1.math.Calculator;
import com.itmo.lab1.math.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    private static boolean isDone = false;

    public static void main(String[] args) throws Exception {

        int threadsNumber = 4;
        if (args.length > 0 && Integer.parseInt(args[0]) > 0) {
            threadsNumber = Integer.parseInt(args[0]);
        }

        ArrayList<Matrix> result = new ArrayList<>();
        ArrayList<Future<Matrix>> executors = new ArrayList<>();

        Thread progressBar = new Thread(() -> {

            isDone = false;

            char[] animationChars = new char[]{'|', '/', '-', '\\'};
            int i = 0;
            while (!isDone) {
                System.out.print("Processing: " + animationChars[i % 4]);
                i++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("\r");

            }
        });

        System.out.print("Reading start");
        FileWorker fileWorker = new FileWorker();
        List<Matrix> matrices = fileWorker.readTestData();
        System.out.println("\rReading complete");


        System.out.println("Started: " + threadsNumber + " threads");
        progressBar.start();

        long startExec = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);

        for (int i = 0; i < threadsNumber; i++) {
            Callable<Matrix> executor = new Executor(i * 2, 1 + (threadsNumber - 1) * 2, matrices);
            Future<Matrix> submit = executorService.submit(executor);
            executors.add(submit);
        }

        executors.forEach(future -> {
            try {
                result.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        isDone = true;
        Calculator calculator = new Calculator();

        System.out.println("\rComputation complete");
        System.out.println("\nResult of computation:");
        System.out.println(result.stream().reduce(calculator::sum).get().getStringValues());
        System.out.println("Time of computation: " + (System.currentTimeMillis() - startExec) + " ms");

        executorService.shutdown();
    }
}
