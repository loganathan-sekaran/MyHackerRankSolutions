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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HuffmanDecoderSoulution {
	
	// Complete the decode function below.
    static String decode(List<String> codes, String encoded) {

    	Map<String, String> codesPairs = codes.stream()
    		.map(str -> str.split("\t"))
    		.filter(arr -> arr.length == 2)
    		.collect(Collectors.toMap(arr -> arr[1], arr -> arr[0].equals("[newline]") ? "\n" : arr[0]));
    	
    	Set<String> codeKeys = codesPairs.keySet();
    	
    	StringBuilder encodedStr = new StringBuilder(encoded);
    	StringBuilder result = new StringBuilder();
    	
    	while(encodedStr.length() > 0) {
	    	for (String string : codeKeys) {
	    		if(encodedStr.indexOf(string) == 0) {
	    			result.append(codesPairs.get(string));
	    			encodedStr.delete(0, string.length());
	        	}
			}
    	}
    	
    	return result.toString();
    }
	
   	public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getenv("INPUT_PATH"))));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int codesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> codes = new ArrayList<>();

        IntStream.range(0, codesCount).forEach(i -> {
            try {
                codes.add(bufferedReader.readLine());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        String encoded = bufferedReader.readLine();

        String res = decode(codes, encoded);

        bufferedWriter.write(res);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }

}
