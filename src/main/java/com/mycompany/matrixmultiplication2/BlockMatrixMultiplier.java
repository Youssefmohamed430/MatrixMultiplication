/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.matrixmultiplication2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class BlockMatrixMultiplier implements MatrixMultiplier {
    private final ForkJoinPool pool = new ForkJoinPool();
    private final int blockSize;

    public BlockMatrixMultiplier(int blockSize) {
        this.blockSize = Math.max(8, blockSize);
    }

    public Matrix multiply(Matrix A, Matrix B) {
        if (A.getColCount() != B.getRowCount()) {
            throw new IllegalArgumentException("Incompatible dimensions");
        } else {
            Matrix C = new Matrix(A.getColCount(), B.getRowCount());
            BlockTask root = new BlockTask(A, B, C, 0, A.getRowCount(), 0, B.getColCount(), this.blockSize);
            this.pool.invoke(root);
            return C;
        }
    }

    private static class BlockTask extends RecursiveAction {
        private final Matrix A;
        private final Matrix B;
        private final Matrix C;
        private final int rowStart;
        private final int rowEnd;
        private final int colStart;
        private final int colEnd;
        private final int blockSize;

        BlockTask(Matrix A, Matrix B, Matrix C, int rowStart, int rowEnd, int colStart, int colEnd, int blockSize) {
            this.A = A;
            this.B = B;
            this.C = C;
            this.rowStart = rowStart;
            this.rowEnd = rowEnd;
            this.colStart = colStart;
            this.colEnd = colEnd;
            this.blockSize = blockSize;
        }

        protected void compute() {
            int rows = this.rowEnd - this.rowStart;
            int cols = this.colEnd - this.colStart;
            if (rows <= this.blockSize && cols <= this.blockSize) {
                this.multiplyBlock();
            } else {
                int rowMid = this.rowStart + rows / 2;
                int colMid = this.colStart + cols / 2;
                invokeAll(new ForkJoinTask[]{new BlockTask(this.A, this.B, this.C, this.rowStart, rowMid, this.colStart, colMid, this.blockSize), new BlockTask(this.A, this.B, this.C, this.rowStart, rowMid, colMid, this.colEnd, this.blockSize), new BlockTask(this.A, this.B, this.C, rowMid, this.rowEnd, this.colStart, colMid, this.blockSize), new BlockTask(this.A, this.B, this.C, rowMid, this.rowEnd, colMid, this.colEnd, this.blockSize)});
            }
        }

        private void multiplyBlock() {
            for(int i = this.rowStart; i < this.rowEnd; ++i) {
                for(int k = 0; k < this.A.getColCount(); ++k) {
                    double aik = this.A.get(i, k);

                    for(int j = this.colStart; j < this.colEnd; ++j) {
                        this.C.set(i, j, this.C.get(i, j) + aik * this.B.get(k, j));
                    }
                }
            }

        }
    }
}
