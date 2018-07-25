package test;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NonDivisibleSubsetSolution {

    private static class Combine {
        private long operand1;
        private long operand2;
        
        private long sum;
        
        public Combine(long operand1, long operand2) {
            super();
            this.operand1 = operand1;
            this.operand2 = operand2;
            this.sum = operand1 + operand2;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Combine other = (Combine) obj;
    
            
            if(this.operand1 == other.operand1 && this.operand2 == other.operand2) {
                return true;
            }
            
            if(this.operand1 == other.operand2 && this.operand2 == other.operand1) {
                return true;
            }
            
            return false;

        }
        
        @Override
        public String toString() {
            return "Combine [operand1=" + operand1 + ", operand2=" + operand2 + ", sum=" + sum + "]";
        }
        
        
        
        
    }
    
    // Complete the nonDivisibleSubset function below.
    static int nonDivisibleSubset(int k, long[] S) {
        Set<Combine> list = new HashSet<>();
        for (int i = 0; i < S.length; i++) {
            long val1 = S[i];
            for (int j = 0; j < S.length; j++) {
                long val2 = S[j];
                Combine combine = new Combine(val1, val2);
                list.add(combine);
            }
        }
        
       List<Long> distinct = list.stream()
				   			.filter(e -> (e.sum % (long)k )!= 0)
				   			.peek(System.out::println)
				   			.flatMap(e -> Stream.of(e.operand1, e.operand2))
				   			.distinct()
				   			.collect(Collectors.toList());
     System.out.println(distinct);
	return (int) distinct
    		   			.size();

    }
    
	static int nonDivisibleSubset1(int k, int[] arr) {
		int reminders[] = new int[k];
		for (int i = 0; i < arr.length; i++) {
			int num = arr[i];
			int index = num % k;
			reminders[index]++;
		}
		int size = 0;
		for (int i = 1; i <= k / 2; i++) {
			if (i * 2 == k) {
				size++;
			} else {
				size += Math.max(reminders[i], reminders[k - i]);
			}
		}
		return reminders[0] != 0 ? size + 1 : size;
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

        String[] nk = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nk[0]);

        int k = Integer.parseInt(nk[1]);

        long[] S = new long[n];

        String[] SItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int SItem = Integer.parseInt(SItems[i]);
            S[i] = SItem;
        }

        int result = nonDivisibleSubset(k, S);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
