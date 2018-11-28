package com.itmo.lab2.tasksupplier;

import com.itmo.general.fileworker.FileWorker;
import com.itmo.lab1.math.Matrix;

import java.util.ArrayList;
import java.util.List;

public class TaskSupplier {

    private ArrayList<Task> tasks = new ArrayList<>();

    public TaskSupplier() {
        taskInit();
    }

    private void taskInit() {
        FileWorker fileWorker = new FileWorker();
        List<Matrix> matrices = fileWorker.readTestData();

        for (int i = 0; i < matrices.size(); i += 2) {
            Task task = new Task(matrices.get(i), matrices.get(i + 1), i);
            tasks.add(task);
        }

    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}