package test;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class MorganAndStringSolution {

    // Complete the morganAndString function below.
    static String morganAndString(String a, String b) {
    	char[] aChars = a.toCharArray();
    	char[] bChars = b.toCharArray();
    	
    	char[] res = new char[aChars.length + bChars.length];
    	
    	int resIndex = 0;
    	
    	for (int i = 0, j = 0; !(i == aChars.length && j == bChars.length);) {
			
			if(i == aChars.length) {
				char c2 = bChars[j];
				res[resIndex++] = c2;
				j++;
				continue;
			} else if(j == bChars.length) {
				char c1 = aChars[i];
				res[resIndex++] = c1;
				i++;
				continue;
			}
			
			
			char c1 = aChars[i];
			char c2 = bChars[j];
			
			if (c1 < c2) {
				res[resIndex++] = c1;
				i++;
			} else if (c1 > c2){
				res[resIndex++] = c2;
				j++;
			} else if(c1 == c2) {
				if(String.valueOf(Arrays.copyOfRange(aChars, i, aChars.length - 1))
						.compareTo(String.valueOf(Arrays.copyOfRange(bChars, j, bChars.length - 1)))  <= 0) {
					res[resIndex++] = c1;
					i++;
				} else {
					res[resIndex++] = c2;
					j++;
				}
			}
			//System.out.println("i=" + i + " j=" + j + ": ci=" + c1 + " cj=" + c2 + " : " + String.valueOf(res));
		}
    	
    	return String.valueOf(res);

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

        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            String a = scanner.nextLine();

            String b = scanner.nextLine();

            String result = morganAndString(a, b);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
