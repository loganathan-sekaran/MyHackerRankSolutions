package test;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Stream;

public class SolutionBiggerIsGreater {

    // Complete the biggerIsGreater function below.
    static String biggerIsGreater(String w) {
        char[] chars = w.toCharArray();
        boolean changed = false;
        outer:
        for (int i = chars.length - 1; i >= 0; i--) {
			char c = chars[i];
			inner:
			for (int j = i - 1; j >= 0; j--) {
				if(c > chars[j]) {
					chars[i] = chars[j];
					chars[j] = c;
					
					List<Character> restChars = new ArrayList<>();
					for (int k = j+ 1; k < chars.length; k++) {
						restChars.add(chars[k]);
					}
					
					Collections.sort(restChars);
					
					for (int k = 0; k < restChars.size(); k++) {
						chars[j + k + 1] = restChars.get(k);
					}
					
					changed = true;
					break outer;
				}
			}
		}
        
        if(changed) {
        	String string = String.valueOf(chars);
			System.out.println(string);
        	return string;
        }
        
        return "no answer";
        
       
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

        int T = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int TItr = 0; TItr < T; TItr++) {
            String w = scanner.nextLine();

            String result = biggerIsGreater(w);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
