package test.asses;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DistinctPairsSolution {
	
	static class Box<T,R> {
		T val1;
		R val2;
		
		public Box(T t, R r) {
			super();
			this.val1 = t;
			this.val2 = r;
		}

		@Override
		public String toString() {
			return "Box [t=" + val1 + ", r=" + val2 + "]";
		}
		
	}
	
	// Complete the numberOfPairs function below.
    static int numberOfPairs(int[] a, long k) {
    	java.util.concurrent.atomic.AtomicInteger i = new java.util.concurrent.atomic.AtomicInteger();
    	java.util.concurrent.atomic.AtomicInteger j = new java.util.concurrent.atomic.AtomicInteger();
    	Set<String> existingPair = Collections.synchronizedSet(new HashSet<>());
    	java.util.concurrent.atomic.AtomicInteger count = new java.util.concurrent.atomic.AtomicInteger();
    	java.util.stream.IntStream.of(a)
    		.parallel()
    		.mapToObj(val -> new Box<>(i.getAndIncrement(), val))
    		.forEach(b1 -> {
	    		int p = b1.val2;
	    		java.util.stream.IntStream.of(a)
	    			.parallel()
	    			.mapToObj(val -> new Box<>(j.getAndIncrement(), val))
	    			.filter(b2 -> b1.val1 != b2.val1)
	    			.forEach(b2 -> {
	    				int q = b2.val2;
						if(p != q && p + q == k) {
							int[] arr = {p,q};
							Arrays.sort(arr);
							String key = arr[0] + ":" + arr[1];
							synchronized (existingPair) {
								if(!existingPair.contains(key)) {
									existingPair.add(key);
									count.incrementAndGet();
								}
							}
						}
	        	});
    	});
    	
    	return count.get();

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
