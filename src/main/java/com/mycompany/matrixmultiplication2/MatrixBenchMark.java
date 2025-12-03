package com.mycompany.matrixmultiplication2;

public class MatrixBenchMark {
    public static long run(MatrixMultiplier mm, Matrix A, Matrix B) {
        long start = System.currentTimeMillis();
        mm.multiply(A, B);
        return System.currentTimeMillis() - start;
    }
}
