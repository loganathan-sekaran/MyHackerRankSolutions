package test.asses;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class MinimumMovesSolution {
	
	// Complete the minimumMoves function below.
    static int minimumMoves(List<Integer> a, List<Integer> m) {
    	
    	List<List<Integer>> aDigitsList = getDigitLists(a);
    	List<List<Integer>> mDigitsList = getDigitLists(m);
    	
    	int totalMinMoves = 0;
    		
    	for (int i = 0; i < aDigitsList.size(); i++) {
			List<Integer> aDigits = aDigitsList.get(i);
			List<Integer> mDigits = mDigitsList.get(i);
			
			for (int j = 0; j < aDigits.size(); j++) {
				int aDigit = aDigits.get(j);
				int mDigit = mDigits.get(j);
				totalMinMoves += Math.abs(aDigit - mDigit);
			}
			
		}

    	return totalMinMoves;

    }

	private static List<List<Integer>> getDigitLists(List<Integer> a) {
		return a.stream()
    		.map(String::valueOf)
    		.map(s -> s.chars()
    					.mapToObj(i -> (char) i)
    					.map(c -> Integer.valueOf(String.valueOf(c)))
    					.collect(Collectors.toList())
    				).collect(Collectors.toList());
	}
    
   	public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getenv("INPUT_PATH"))));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int aCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> aTemp = new ArrayList<>();

        IntStream.range(0, aCount).forEach(i -> {
            try {
                aTemp.add(bufferedReader.readLine().replaceAll("\\s+$", ""));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> a = aTemp.stream()
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        int mCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> mTemp = new ArrayList<>();

        IntStream.range(0, mCount).forEach(i -> {
            try {
                mTemp.add(bufferedReader.readLine().replaceAll("\\s+$", ""));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> m = mTemp.stream()
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        int res = minimumMoves(a, m);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }

}
