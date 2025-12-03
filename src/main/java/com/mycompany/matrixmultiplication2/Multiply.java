package com.mycompany.matrixmultiplication2;
import java.util.concurrent.RecursiveAction;

class MultiplyTask extends RecursiveAction {
    private final Matrix a;
    private final Matrix b;
    private final Matrix c;
    private final int rowStart;
    private final int rowEnd;
    private final int threshold;

    MultiplyTask(Matrix a, Matrix b, Matrix c, int rowStart, int rowEnd, int threshold) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
        this.threshold = threshold;
    }

    protected void compute() {
        int rows = this.rowEnd - this.rowStart;
        if (rows <= this.threshold) {
            this.multiplyRange();
        } else {
            int mid = this.rowStart + rows / 2;
            MultiplyTask left = new MultiplyTask(this.a, this.b, this.c, this.rowStart, mid, this.threshold);
            MultiplyTask right = new MultiplyTask(this.a, this.b, this.c, mid, this.rowEnd, this.threshold);
            left.fork();
            right.compute();
            left.join();
        }

    }

    private void multiplyRange() {
        int n = this.a.getColCount();
        int p = this.b.getColCount();

        for(int i = this.rowStart; i < this.rowEnd; ++i) {
            for(int k = 0; k < n; ++k) {
                double aik = this.a.get(i, k);

                for(int j = 0; j < p; ++j) {
                    this.c.set(i, j, this.c.get(i, j) + aik * this.b.get(k, j));
                }
            }
        }

    }
}

