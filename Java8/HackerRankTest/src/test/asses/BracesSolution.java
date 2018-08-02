package test.asses;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class BracesSolution {
	
	
	// Complete the braces function below.
    static String[] braces(String[] values) {
    	String[] result = new String[values.length];
    	for (int i = 0; i < values.length; i++) {
			String string = values[i];
			result[i] = isBalanced(string) ? "YES" : "NO";
		}
    	
    	return result;
    }
    
    private static final Map<Character, Character> braces = new HashMap<>();
    static {
    	braces.put('}', '{');
    	braces.put(']', '[');
    	braces.put(')', '(');
    }
    
    private static final Set<Character> closeBraces = braces.keySet();
    
    private static final Set<Character> openBraces = new HashSet<>(braces.values());

	private static boolean isBalanced(String string) {
		Stack<Character> stack = new Stack<Character>();
		char[] charArray = string.toCharArray();
		boolean balanced = true;
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if(openBraces.contains(c)) {
				stack.push(c);
			} else if(closeBraces.contains(c)) {
				if(stack.size() > 0 && stack.peek().equals(braces.get(c))) {
					stack.pop();
				} else {
					return false;
				}
			}
		}
		
		return balanced && stack.isEmpty();
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

        int valuesCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String[] values = new String[valuesCount];

        for (int i = 0; i < valuesCount; i++) {
            String valuesItem = scanner.nextLine();
            values[i] = valuesItem;
        }

        String[] res = braces(values);

        for (int i = 0; i < res.length; i++) {
            bufferedWriter.write(res[i]);

            if (i != res.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}

