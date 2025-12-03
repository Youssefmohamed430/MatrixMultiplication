package com.mycompany.matrixmultiplication2;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinMatrixMultiplier implements MatrixMultiplier {
    private final ForkJoinPool pool = new ForkJoinPool();
    private final int thresholdRows;

    public ForkJoinMatrixMultiplier(int thresholdRows) {
        this.thresholdRows = Math.max(1, thresholdRows);
    }

    public Matrix multiply(Matrix a, Matrix b) {
        if (a.getColCount() != b.getRowCount()) {
            throw new IllegalArgumentException("Incompatible dimensions");
        } else {
            int m = a.getRowCount();
            int p = b.getColCount();
            Matrix c = new Matrix(m, p);
            MultiplyTask root = new MultiplyTask(a, b, c, 0, m, this.thresholdRows);
            this.pool.invoke(root);
            return c;
        }
    }
}
