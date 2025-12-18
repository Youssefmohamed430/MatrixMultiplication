package com.mycompany.matrixmultiplication2;

public class MatrixBenchMark {
    Matrix result;
    public long run(MatrixMultiplier mm, Matrix A, Matrix B) {
        long start = System.currentTimeMillis();
        result = mm.multiply(A, B);
        return System.currentTimeMillis() - start;
    }
    public Matrix GetMultiplyResult()
    {
        return result;
    }
}
