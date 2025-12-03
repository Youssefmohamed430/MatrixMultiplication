/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.matrixmultiplication2;

public class SequentialMatrixMultiplier implements MatrixMultiplier {
    public Matrix multiply(Matrix a, Matrix b) {
        if (a.getColCount() != b.getRowCount()) {
            int var10002 = a.getRowCount();
            throw new IllegalArgumentException("Incompatible dimensions: " + var10002 + "x" + a.getColCount() + " * " + b.getRowCount() + "x" + b.getColCount());
        } else {
            int m = a.getRowCount();
            int n = a.getColCount();
            int p = b.getColCount();
            Matrix c = new Matrix(m, p);

            for(int i = 0; i < m; ++i) {
                for(int k = 0; k < n; ++k) {
                    double aik = a.get(i, k);

                    for(int j = 0; j < p; ++j) {
                        c.set(i, j, c.get(i, j) + aik * b.get(k, j));
                    }
                }
            }

            return c;
        }
    }
}