package com.itmo.lab1.math;

public class Matrix {

    private Integer[][] values = new Integer[4][4];

    public Matrix(Integer[][] values) {
        this.values = values;
    }

    public Integer[][] getValues() {
        return values;
    }

    public void setValues(Integer[][] values) {
        this.values = values;
    }


    public String getStringValues() {
        String result = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result += values[i][j] + " ";
            }
            result += "\n";
        }
        return result;
    }
}
