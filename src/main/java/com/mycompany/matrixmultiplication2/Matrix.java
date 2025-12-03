//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mycompany.matrixmultiplication2;

import java.util.Arrays;
import java.util.Random;

public class Matrix {
    private final int rows;
    private final int cols;
    final double[][] data;

    public Matrix(int rows, int cols) {
        if (rows >= 1 && cols >= 1) {
            this.rows = rows;
            this.cols = cols;
            this.data = new double[rows][cols];
        } else {
            throw new IllegalArgumentException("Matrix dimensions must be positive");
        }
    }

    public Matrix(double[][] data) {
        this.rows = data.length;
        this.cols = this.rows == 0 ? 0 : data[0].length;
        this.data = new double[this.rows][this.cols];

        for(int i = 0; i < this.rows; ++i) {
            if (data[i].length != this.cols) {
                throw new IllegalArgumentException("Jagged array is not allowed");
            }

            System.arraycopy(data[i], 0, this.data[i], 0, this.cols);
        }

    }

    public int getRowCount() {
        return this.rows;
    }

    public int getColCount() {
        return this.cols;
    }

    public double get(int r, int c) {
        return this.data[r][c];
    }

    public void set(int r, int c, double value) {
        this.data[r][c] = value;
    }

    public static Matrix random(int r, int c, long seed) {
        Matrix m = new Matrix(r, c);
        Random rnd = new Random(seed);

        for(int i = 0; i < r; ++i) {
            for(int j = 0; j < c; ++j) {
                m.data[i][j] = rnd.nextDouble();
            }
        }

        return m;
    }

    public static Matrix random(int r, int c) {
        return random(r, c, System.nanoTime());
    }

    public boolean equalsWithTolerance(Matrix other, double tol) {
        if (other == null) {
            return false;
        } else if (this.rows == other.getRowCount() && this.cols == other.getColCount()) {
            for(int i = 0; i < this.rows; ++i) {
                for(int j = 0; j < this.cols; ++j) {
                    if (Math.abs(this.get(i, j) - other.get(i, j)) > tol) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Matrix " + this.rows + "x" + this.cols + ":\n");
        int r = Math.min(this.rows, 10);
        int c = Math.min(this.cols, 10);

        for(int i = 0; i < r; ++i) {
            sb.append(Arrays.toString(Arrays.copyOf(this.data[i], c)));
            if (c < this.cols) {
                sb.append(" ...");
            }

            sb.append("\n");
        }

        if (r < this.rows) {
            sb.append("... (truncated)\n");
        }

        return sb.toString();
    }
}
