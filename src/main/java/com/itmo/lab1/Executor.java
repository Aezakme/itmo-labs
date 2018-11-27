package com.itmo.lab1;

import com.itmo.lab1.math.Calculator;
import com.itmo.lab1.math.Matrix;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Executor implements Callable<Matrix> {

    private int threadNumber;
    private int step;
    private Calculator calculator;

    private ArrayList<Matrix> input;
    private ArrayList<Matrix> output;

    Executor(int threadNumber, int step, ArrayList<Matrix> input) {
        this.threadNumber = threadNumber;
        this.step = step;
        this.input = input;

        calculator = new Calculator();
        output = new ArrayList<>();
    }

    @Override
    public Matrix call() throws Exception {
        int matrixCount = input.size();

        for (int i = threadNumber; i < matrixCount - 1; i += step + 1) {
            output.add(calculator.multiply(input.get(i), input.get(i + 1)));

        }

        return output.stream().reduce((matrix, matrix2) -> calculator.sum(matrix, matrix2)).orElseThrow(RuntimeException::new);
    }
}
