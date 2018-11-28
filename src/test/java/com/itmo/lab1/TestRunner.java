package com.itmo.lab1;

import com.itmo.general.fileworker.FileWorker;
import com.itmo.lab1.math.Calculator;
import com.itmo.lab1.math.Matrix;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    private FileWorker fileWorker = new FileWorker();
    private Calculator calculator = new Calculator();

    @Test
    public void writeTestData() {
        FileWorker f = new FileWorker();
        f.writeTestData();
    }


    @Test
    public void oneExecutor() {

        List<Matrix> matrices = fileWorker.readTestData();
        ArrayList<Matrix> result = new ArrayList<>();

        for (int i = 0; i < matrices.size() - 1; i += 2) {
            result.add(calculator.multiply(matrices.get(i), matrices.get(i + 1)));
        }

        System.out.println(result.stream().reduce((matrix, matrix2) -> calculator.sum(matrix, matrix2)).get().getStringValues());
    }
}
