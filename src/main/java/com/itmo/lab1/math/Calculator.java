package com.itmo.lab1.math;

public class Calculator {

    public Matrix multiply(Matrix m1, Matrix m2) {
        Integer[][] resultValues = new Integer[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                resultValues[j][i] = m1.getValues()[j][0] * m2.getValues()[0][i]
                        + m1.getValues()[j][1] * m2.getValues()[1][i]
                        + m1.getValues()[j][2] * m2.getValues()[2][i]
                        + m1.getValues()[j][3] * m2.getValues()[3][i];
            }
        }

        return new Matrix(resultValues);
    }

    public Matrix sum(Matrix m1, Matrix m2) {
        Integer[][] resultValues = new Integer[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                resultValues[i][j] = m1.getValues()[i][j] + m2.getValues()[i][j];
            }
        }

        return new Matrix(resultValues);

    }
}
