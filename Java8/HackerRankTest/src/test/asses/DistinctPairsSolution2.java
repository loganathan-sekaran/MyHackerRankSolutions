package test.asses;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DistinctPairsSolution2 {
	
	// Complete the numberOfPairs function below.
    static int numberOfPairs(int[] a, long k) {
    	int count = 0;
    	Set<String> existingPair = new HashSet<>();
    	for (int i = 0; i < a.length; i++) {
			int p = a[i];
			for (int j = 0; j < a.length; j++) {
				if(i != j) {
					int q = a[j];
					if(p + q == k) {
						int[] arr = {p,q};
						Arrays.sort(arr);
						String key = arr[0] + ":" + arr[1];
						if(!existingPair.contains(key)) {
							existingPair.add(key);
							count++;
						}
					}
				}
			}
		}
    	
    	return count;

    }
	
	private static final Scanner scanner = getScanner();

   	private static Scanner getScanner()  {
   		try {
   			return new Scanner(new FileInputStream(System.getenv("INPUT_PATH")));
   		} catch (FileNotFoundException e) {
   			return new Scanner(System.in);
   		}
   	}

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int aCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] a = new int[aCount];

        for (int i = 0; i < aCount; i++) {
            int aItem = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            a[i] = aItem;
        }

        long k = scanner.nextLong();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int res = numberOfPairs(a, k);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }

}
