package test.asses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AnagramDifferenceSolution {
	
	// Complete the getMinimumDifference function below.
    static int[] getMinimumDifference(String[] a, String[] b) {
    	
    	int[] res = new int[a.length];
    	
    	for (int i = 0; i < a.length; i++) {
			String aStr = a[i];
			String bStr = b[i];
			
			if(aStr.length() != bStr.length()) {
				res[i] = -1;
			} else {
				ArrayList<Character> aChars = new ArrayList<>();
				for (char c : aStr.toCharArray()) {
					aChars.add(c);
				}
				
				Collections.sort(aChars);
				
				ArrayList<Character> bChars = new ArrayList<>();
				for (char c : bStr.toCharArray()) {
					bChars.add(c);
				}
				
				Collections.sort(bChars);
				
				if(aChars.equals(bChars)) {
					res[i] = 0;
				} else {
					
					ArrayList<Character>  aRef = (ArrayList<Character>) aChars.clone();
					ArrayList<Character>  bRef = (ArrayList<Character>) bChars.clone();
					
					for (Character ch : aChars) {
						if(bRef.contains(ch)) {
							bRef.remove(ch);
						}
					}
					
					
					for (Character ch : bChars) {
						if(aRef.contains(ch)) {
							aRef.remove(ch);
						}
					}
					
					
					res[i] = (aRef.size() + bRef.size()) / 2;
					
				}
				
			}
			
			
			
		}

    	return res;

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

        String[] a = new String[aCount];

        for (int i = 0; i < aCount; i++) {
            String aItem = scanner.nextLine();
            a[i] = aItem;
        }

        int bCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String[] b = new String[bCount];

        for (int i = 0; i < bCount; i++) {
            String bItem = scanner.nextLine();
            b[i] = bItem;
        }

        int[] res = getMinimumDifference(a, b);

        for (int i = 0; i < res.length; i++) {
            bufferedWriter.write(String.valueOf(res[i]));

            if (i != res.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    } 

}
