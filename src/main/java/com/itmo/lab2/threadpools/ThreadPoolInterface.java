package com.itmo.lab2.threadpools;

import com.itmo.lab1.math.Matrix;

import java.util.ArrayList;

public interface ThreadPoolInterface {

    void run();

    Boolean isDone();

    ArrayList<Matrix> getResult();
}
