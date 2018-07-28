package test;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class MatrixRotationSolution {

    // Complete the matrixRotation function below.
    static void matrixRotation(int[][] matrix, int r) {
    	
    	int[][] prestine = matrix.clone();
    	int[][] result = null; 
    	for (int i = 0; i < r; i++) {
    		result = new int[matrix.length][matrix[0].length];
			rotate(prestine, result);  		
			prestine = result;
		}
		print(result);
    	
    }

    private static void rotate(int[][] prestine, int[][] result) {
    	int rowCount = prestine.length;
    	
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			int[] row = prestine[rowIndex];
			int halfRowCount = rowCount / 2;
			
			int columnCount = row.length;
		
			
			for (int colIndex = 0; colIndex < columnCount; colIndex++) {
				int halfColumnCount = columnCount / 2;
				
				//System.out.println();
				//print(result);
				
				int elem = row[colIndex];
				
				if(colIndex <  halfColumnCount) {
					
					if(rowIndex < halfRowCount) {
						int lowestColumn = Math.min(rowIndex, colIndex);

						if(colIndex == lowestColumn) {
							moveDown(result, rowIndex, colIndex, elem);
						} else {
							moveLeft(result, rowIndex, colIndex, elem);
						}
					} else {
						int highestRowIndex = rowCount - Math.min(rowCount - rowIndex  - 1, colIndex) - 1;
						if(rowIndex == highestRowIndex) {
							moveRight(result, rowIndex, colIndex, elem);
						} else {
							moveDown(result, rowIndex, colIndex, elem);
						}
					}
				} else {
					
					int highestColumnIndex;
					if(rowIndex > halfRowCount) {
						highestColumnIndex = columnCount - 1 - (Math.min(rowCount - rowIndex - 1, columnCount - colIndex - 1));
						if(colIndex == highestColumnIndex) {
							moveUp(result, rowIndex, colIndex, elem);
						} else {
							moveRight(result, rowIndex, colIndex, elem);
						}
					} else {
						highestColumnIndex = columnCount - 1 - (Math.min(rowIndex, columnCount - colIndex - 1));
						int lowestRowIndex = columnCount - colIndex - 1;

						if(colIndex == highestColumnIndex) {
							if(rowIndex == lowestRowIndex) {
								moveLeft(result, rowIndex, colIndex, elem);
							} else {
								moveUp(result, rowIndex, colIndex, elem);
								
							}
						} else {
							moveLeft(result, rowIndex, colIndex, elem);
						}
					}
				}
				
			}
			
		}
	}

	private static void moveUp(int[][] result, int rowIndex, int colIndex, int elem) {
		int targetRow = rowIndex - 1;
		int targetColumn = colIndex;
		
		result[targetRow][targetColumn] = elem;
	}

	private static void moveRight(int[][] result, int rowIndex, int colIndex, int elem) {
		int targetRow = rowIndex;
		int targetColumn = colIndex + 1;
		
		result[targetRow][targetColumn] = elem;
	}

	private static void moveLeft(int[][] result, int rowIndex, int colIndex, int elem) {
		int targetRow = rowIndex;
		int targetColumn = colIndex - 1;
		
		result[targetRow][targetColumn] = elem;
	}

	private static void moveDown(int[][] result, int rowIndex, int colIndex, int elem) {
		int targetRow = rowIndex + 1;
		int targetColumn = colIndex;
		
		result[targetRow][targetColumn] = elem;
	}

	private static void print(int[][] result) {
    	for (int[] row : result) {
			for (int i = 0; i < row.length; i++) {
				int e = row[i];
				System.out.print(e);
				if(i < row.length - 1) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}

    private static final Scanner scanner = getScanner();

   	private static Scanner getScanner()  {
   		try {
   			return new Scanner(new FileInputStream(System.getenv("INPUT_PATH")));
   		} catch (FileNotFoundException e) {
   			return new Scanner(System.in);
   		}
   	}

    public static void main(String[] args) {
        String[] mnr = scanner.nextLine().split(" ");

        int m = Integer.parseInt(mnr[0]);

        int n = Integer.parseInt(mnr[1]);

        int r = Integer.parseInt(mnr[2]);

        int[][] matrix = new int[m][n];

        for (int i = 0; i < m; i++) {
            String[] matrixRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < n; j++) {
                int matrixItem = Integer.parseInt(matrixRowItems[j]);
                matrix[i][j] = matrixItem;
            }
        }

        matrixRotation(matrix, r);

        scanner.close();
    }
}
