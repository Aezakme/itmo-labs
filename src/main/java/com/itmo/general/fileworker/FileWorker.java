package com.itmo.general.fileworker;

import com.itmo.lab1.math.Matrix;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FileWorker {

    private static final String FILE_NAME = "temp.txt";
    private static final Integer MATRIX_COUNT = 500_000;
    private static final Integer VALUES_BOUND = 1000;

    public void writeTestData() {

        try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
            for (int i = 0; i < MATRIX_COUNT; i++) {
                for (int j = 0; j < 4; j++) {

                    fileWriter.write(new Random().nextInt(VALUES_BOUND) + " "
                            + new Random().nextInt(VALUES_BOUND) + " "
                            + new Random().nextInt(VALUES_BOUND) + " "
                            + new Random().nextInt(VALUES_BOUND) + "\n");
                }
                fileWriter.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Matrix> readTestData() {
        ArrayList<Matrix> matrices = new ArrayList<>();
        try (FileReader fileReader = new FileReader(FILE_NAME)) {
            Scanner scanner = new Scanner(fileReader);
            for (int i = 0; i < MATRIX_COUNT; i++) {

                Integer[][] values = new Integer[4][4];
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 4; k++) {
                        values[j][k] = Integer.valueOf(scanner.next());
                    }
                }
                matrices.add(new Matrix(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matrices;
    }
}