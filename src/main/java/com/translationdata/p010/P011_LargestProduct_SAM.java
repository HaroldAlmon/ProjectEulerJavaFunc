package com.translationdata.p010;

/** Strategy: Brute Force, Single Abstract Method Interfaces, Tail Recursion */
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import static java.lang.Math.max;

import JUnitTests.FastTest;

interface MatrixProduct {
	public int apply(int[][] matrix, int row, int col);
}

@Category(FastTest.class)
public class P011_LargestProduct_SAM {
	MatrixProduct columnProduct = new MatrixProduct() {
		@Override
		public int apply(int[][] matrix, int row, int col) {
			return    matrix[row][col] 
					* matrix[row][col + 1] 
					* matrix[row][col + 2] 
					* matrix[row][col + 3];
		}
	};
	
	MatrixProduct fallingDiagonalProduct = new MatrixProduct() {
		@Override
		public int apply(int[][] matrix, int row, int col) {
			return    matrix[row]    [col] 
					* matrix[row + 1][col + 1] 
					* matrix[row + 2][col + 2] 
					* matrix[row + 3][col + 3];
		}
	};
	
	MatrixProduct rowProduct = new MatrixProduct() {
		@Override
		public int apply(int[][] matrix, int row, int col) {
			return    matrix[row]    [col] 
					* matrix[row + 1][col] 
					* matrix[row + 2][col] 
					* matrix[row + 3][col];
		}
	};
	
	MatrixProduct risingDiagonalProduct = new MatrixProduct() {
		@Override
		public int apply(int[][] matrix, int row, int col) {
			return    matrix[row + 3][col] 
					* matrix[row + 2][col + 1] 
					* matrix[row + 1][col + 2] 
					* matrix[row]    [col + 3];
		}
	};
	
	public int largestProduct() {
		return max(ColumnProduct.columnProduct(matrix, columnProduct, matrix.length), 
				 max(ColumnProduct.columnProduct(matrix, fallingDiagonalProduct, matrix.length - 4),
				   max(ColumnProduct.columnProduct(matrix, risingDiagonalProduct, matrix.length - 4),
					 RowProduct.rowProduct(matrix, rowProduct) )));
	}
	
	static class FourCellProduct {
		public static int cellProduct(int[][] matrix, MatrixProduct cellProductLambda, int rowBuffer, int columnBuffer) {
			return cellProductImpl(0, 0, matrix, cellProductLambda, rowBuffer, columnBuffer);
		}
		
		private static int cellProductImpl(int row, int product, int[][] matrix, MatrixProduct matrixProduct, int rowBuffer, int columnBuffer) {
			if (row >= matrix.length - rowBuffer) 
				return product;
			return cellProductImpl(row + 1, max(product, caculateProduct(row, matrix, matrixProduct, columnBuffer)), matrix, matrixProduct, rowBuffer, columnBuffer);
		}
		
		
		private static int caculateProduct(int row, int[][] matrix, MatrixProduct matrixProduct, int columnBuffer) {
			return cacluateProductImpl(row, 0, matrix,  0, matrixProduct, columnBuffer);
		}
		
		private static int cacluateProductImpl(int row,  int col, int[][] matrix,int previousProduct, MatrixProduct matrixProduct, int columnBuffer) {
			if (col > matrix[0].length - columnBuffer)
				return previousProduct;
			
			final int product =  matrixProduct.apply(matrix, row, col);
			return cacluateProductImpl(row, col + 1, matrix,  max(previousProduct, product), matrixProduct, columnBuffer);
		}	
	}
	
	static class RowProduct {
		public static int rowProduct(int[][] matrix, MatrixProduct matrixProduct) {
			return rowProductImpl(0, 0, matrix, matrixProduct);
		}

		private static int rowProductImpl(int col, int product, int[][] matrix, MatrixProduct matrixProduct) {
			if (col >=  matrix[0].length) 
				return product;
			return rowProductImpl( col + 1, max(product, getRowProd(col, matrix, matrixProduct)), matrix, matrixProduct);
		}
		

		private static int getRowProd(int col, int[][] matrix, MatrixProduct matrixProduct) {
			return getRowProdImpl(col, 0, matrix,  0, matrixProduct);
		}
		
		private static int getRowProdImpl(int col, int row, int[][] matrix, int previousProduct, MatrixProduct matrixProduct) {
			if (row > matrix.length - 4)
				return previousProduct;
			
			final int product = matrixProduct.apply(matrix, row, col);
			return getRowProdImpl(col, row + 1, matrix, max(previousProduct, product), matrixProduct);
		}
	}

	static class ColumnProduct {
		public static int columnProduct(int[][] matrix, MatrixProduct matrixProduct, int upperLimit) {
			return columnProductImpl(0, 0, matrix, matrixProduct, upperLimit);
		}
		
		private static int columnProductImpl(int row, int product, int[][] matrix, MatrixProduct matrixProduct, int upperLimit) {
			if (row >= upperLimit) 
				return product;
			return columnProductImpl( row + 1, max(product, getColProd(row, matrix, matrixProduct)), matrix, matrixProduct, upperLimit);
		}
		
		
		private static int getColProd(int row, int[][] matrix, MatrixProduct matrixProduct) {
			return getColProdImpl(row, 0, matrix,  0, matrixProduct);
		}
		
		private static int getColProdImpl(int row,  int col, int[][] matrix,int previousProduct, MatrixProduct matrixProduct) {
			if (col > matrix[0].length - 4)
				return previousProduct;
			
			final int product =  matrixProduct.apply(matrix, row, col);
			return getColProdImpl(row, col + 1, matrix,  max(previousProduct, product), matrixProduct);
		}	
	}

	@Test(timeout = 1_000)
	public void LargestProduct() {
		final int maximumProduct = largestProduct();
		System.out.printf("largestProduct() = %d%n", maximumProduct);
		assertEquals("Incorrect product", 70600674, maximumProduct);
	}
	
	private final static int[][] matrix = {
		{8,   2, 22, 97, 38, 15,  0, 40,  0, 75,  4,  5,  7, 78, 52, 12, 50, 77, 91, 8},
		{49, 49, 99, 40, 17, 81, 18, 57, 60, 87, 17, 40, 98, 43, 69, 48,  4, 56, 62, 0},
		{81, 49, 31, 73, 55, 79, 14, 29, 93, 71, 40, 67, 53, 88, 30,  3, 49, 13, 36, 65},
		{52, 70, 95, 23,  4, 60, 11, 42, 69, 24, 68, 56,  1, 32, 56, 71, 37,  2, 36, 91},
		{22, 31, 16, 71, 51, 67, 63, 89, 41, 92, 36, 54, 22, 40, 40, 28, 66, 33, 13, 80},
		{24, 47, 32, 60, 99, 3, 45, 2, 44, 75, 33, 53, 78, 36, 84, 20, 35, 17, 12, 50},
		{32, 98, 81, 28, 64, 23, 67, 10, 26, 38, 40, 67, 59, 54, 70, 66, 18, 38, 64, 70},
		{67, 26, 20, 68, 2, 62, 12, 20, 95, 63, 94, 39, 63, 8, 40, 91, 66, 49, 94, 21},
		{24, 55, 58, 5, 66, 73, 99, 26, 97, 17, 78, 78, 96, 83, 14, 88, 34, 89, 63, 72},
		{21, 36, 23, 9, 75, 0, 76, 44, 20, 45, 35, 14, 0, 61, 33, 97, 34, 31, 33, 95},
		{78, 17, 53, 28, 22, 75, 31, 67, 15, 94, 3, 80, 4, 62, 16, 14, 9, 53, 56, 92},
		{16, 39, 5, 42, 96, 35, 31, 47, 55, 58, 88, 24, 0, 17, 54, 24, 36, 29, 85, 57},
		{86, 56, 0, 48, 35, 71, 89, 7, 5, 44, 44, 37, 44, 60, 21, 58, 51, 54, 17, 58},
		{19, 80, 81, 68, 5, 94, 47, 69, 28, 73, 92, 13, 86, 52, 17, 77, 4, 89, 55, 40},
		{4, 52, 8, 83, 97, 35, 99, 16, 7, 97, 57, 32, 16, 26, 26, 79, 33, 27, 98, 66},
		{88, 36, 68, 87, 57, 62, 20, 72, 3, 46, 33, 67, 46, 55, 12, 32, 63, 93, 53, 69},
		{4, 42, 16, 73, 38, 25, 39, 11, 24, 94, 72, 18, 8, 46, 29, 32, 40, 62, 76, 36},
		{20, 69, 36, 41, 72, 30, 23, 88, 34, 62, 99, 69, 82, 67, 59, 85, 74, 4, 36, 16},
		{20, 73, 35, 29, 78, 31, 90, 1, 74, 31, 49, 71, 48, 86, 81, 16, 23, 57, 5, 54},
		{1, 70, 54, 71, 83, 51, 54, 69, 16, 92, 33, 48, 61, 43, 52, 1, 89, 19, 67, 48} 
	};
}