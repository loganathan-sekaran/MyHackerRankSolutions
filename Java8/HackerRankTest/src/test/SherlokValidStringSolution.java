package test;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class SherlokValidStringSolution {

    // Complete the isValid function below.
    static String isValid(String s) {
    	Map<Character, Long> charCounts = s.chars().parallel().mapToObj(i -> (char)i).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    	
    	long maxCount = getCharCounts(charCounts).max().getAsLong();
    	
    	long maxCountCount = getCharCounts(charCounts).filter(e -> e == maxCount).count();
    	
    	boolean valid = false; 
		if(maxCountCount == 1) {
			valid = getCharCounts(charCounts).filter(e -> e != maxCount).allMatch(e -> e == maxCount - 1);
		} else if (maxCountCount == (charCounts.size() - 1)) {
			valid = getCharCounts(charCounts).filter(e -> e != maxCount).filter(e -> e == 1).count() == 1;
		} else if(maxCountCount == charCounts.size()) {
			valid=true;
		}

    	return valid ? "YES" : "NO";

    }

	private static LongStream getCharCounts(Map<Character, Long> charCounts) {
		return charCounts.values().stream().mapToLong(e -> e);
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

        String s = scanner.nextLine();

        String result = isValid(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}