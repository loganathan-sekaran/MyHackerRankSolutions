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

public class StringChainSolution2 {
	
	// Complete the longestChain function below.
    static int longestChain(List<String> words) {
    	
    	
    	ArrayList<String> wordsRef = new ArrayList<>(words);
    	Collections.sort(wordsRef);
    	
    	LinkedHashMap<Integer, List<List<Character>>> charArraysLinkedMap = new LinkedHashMap<>();
    	for (String string : wordsRef) {
    		int len = string.length();
    		charArraysLinkedMap.computeIfAbsent(len, k -> new ArrayList<>());
    		charArraysLinkedMap.get(len).add(string.chars().mapToObj(i -> (char)i).collect(Collectors.toList()));
		}
    	
    	int maxLen = charArraysLinkedMap.keySet().stream().mapToInt( e -> e).max().getAsInt();
    	
    	List<List<String>> chains = new ArrayList<>();
    	
    	for (java.util.Map.Entry<Integer, List<List<Character>>> entry : charArraysLinkedMap.entrySet()) {
    		int len = entry.getKey();
    		List<List<Character>> charArraysList = charArraysLinkedMap.get(len);
    		for (List<Character> chars : charArraysList) {
    			ArrayList<String> chain = new ArrayList<>();
    			
    			List<Character> charsRef = chars;
    			outer:
    			for(int i = len; i < maxLen;  ) {
    			
	        		if(!charArraysLinkedMap.containsKey(i + 1)) {
	        			addToChain(chain, charsRef);
	        			
	            		chains.add((List<String>) chain.clone());
	        			break outer;
	        		} else {
	        			List<List<Character>> nextLenList = charArraysLinkedMap.get(i + 1);
	        			
	        			inner:
	        			for (List<Character> nextLenChars : nextLenList) {
	        				List<Character> nextLenCharsRef = new ArrayList<>(nextLenChars);
	        				nextLenCharsRef.removeAll(charsRef);
	        				
	        				if(nextLenCharsRef.size() == 1) {
		        				List<Character> nextLenCharsRef2 = new ArrayList<>(nextLenChars);
		        				nextLenCharsRef2.removeAll(nextLenCharsRef);

	        					
	        					if(getString(nextLenCharsRef2).equals(getString(charsRef))) {
		    	        			addToChain(chain, charsRef);
		    	        			
		    	        			charsRef = nextLenChars;
		    	        			
		    	        			addToChain(chain, nextLenChars);
		    	        			
		    	        			i = charsRef.size();
		    	        			
		    	        			if(i == maxLen) {
		    	        				chains.add((List<String>) chain.clone());
		    	        			}
		    	        			
		    	        			continue outer;
	        					}
	        				} else {
	        					addToChain(chain, chars);
	    	        			chains.add((List<String>) chain.clone());
	    	        			break outer;
	        				}
						}
	        		}
        		
    			}
        		
			}
    		
		}

    	return chains.stream()
    			.mapToInt(ls -> ls.size())
    			.max()
    			.orElseGet(() -> charArraysLinkedMap.keySet().stream().mapToInt( e -> e).min().getAsInt());

    }

	private static void addToChain(ArrayList<String> chain, List<Character> nextLenChars) {
		String strNext = getString(nextLenChars);
		if(!chain.contains(strNext)) {
			chain.add(strNext);
		}
	}

	private static String getString(List<Character> chars) {
		StringBuilder buf = new StringBuilder();
		chars.forEach(buf::append);
		return buf.toString();
	}
    
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getenv("INPUT_PATH"))));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int wordsCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> words = new ArrayList<>();

        IntStream.range(0, wordsCount).forEach(i -> {
            try {
                words.add(bufferedReader.readLine());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int res = longestChain(words);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }

}
