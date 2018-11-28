package com.itmo.lab2.tasksupplier;

import com.itmo.lab1.math.Calculator;
import com.itmo.lab1.math.Matrix;

public class Task {

    private Matrix matrix1;
    private Matrix matrix2;
    private int number;

    Task(Matrix matrix1, Matrix matrix2, int number) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Matrix getMatrix1() {
        return matrix1;
    }

    public Matrix getMatrix2() {
        return matrix2;
    }

    public Matrix execute() {
        return new Calculator().multiply(matrix1, matrix2);
    }
}
