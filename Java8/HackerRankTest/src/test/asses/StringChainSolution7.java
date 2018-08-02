package test.asses;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class StringChainSolution7 {
	
	// Complete the longestChain function below.
    static int longestChain(List<String> words) {
    	
    	
    	ArrayList<String> wordsRef = new ArrayList<>(words);
    	Collections.sort(wordsRef);
    	
    	LinkedHashMap<Integer, List<String>> charArraysLinkedMap = new LinkedHashMap<>();
    	
    	for (String string : wordsRef) {
    		int len = string.length();
    		charArraysLinkedMap.computeIfAbsent(len, k -> new ArrayList<>());
    		charArraysLinkedMap.get(len).add(string);
		}
    	
    	int maxLen = charArraysLinkedMap.keySet().parallelStream().mapToInt( e -> e).max().getAsInt();
    	
    	java.util.concurrent.atomic.AtomicInteger maxChainsCount = new java.util.concurrent.atomic.AtomicInteger(0);
    	
    	ArrayList<java.util.Map.Entry<Integer, List<String>>> charArraysEntriesList = new ArrayList<>(charArraysLinkedMap.entrySet());
    	Collections.sort(charArraysEntriesList, (e1,e2) -> e1.getKey() - e2.getKey());
    	
    	charArraysEntriesList.parallelStream().forEach(entry -> findChain(charArraysLinkedMap, maxLen, maxChainsCount, entry));
    	

    	return maxChainsCount.get();

    }

	private static void findChain(LinkedHashMap<Integer, List<String>> charArraysLinkedMap, int maxLen,
			java.util.concurrent.atomic.AtomicInteger maxChainsCount, java.util.Map.Entry<Integer, List<String>> entry) {
		int len = entry.getKey();

		List<String> charArraysList = charArraysLinkedMap.get(len);
		
		charArraysList.parallelStream().forEach(chars -> findNextInChain(new ArrayList<>(), chars, charArraysLinkedMap, maxChainsCount, maxLen));
	}

	private static void findNextInChain(ArrayList<String> chain, String chars, HashMap<Integer, List<String>> charArraysLinkedMap, java.util.concurrent.atomic.AtomicInteger maxChainsCount, int maxLen) {
		String charsRef = chars;

		if (!charArraysLinkedMap.containsKey(charsRef.length() + 1)) {
			addToChain(chain, charsRef);

			handleChainEnding(maxChainsCount, chain);
		} else {
			List<String> nextLenList = charArraysLinkedMap.get(charsRef.length() + 1);
				for (String nextLenChars : nextLenList) {
				List<Character> nextLenCharsRef = new LinkedList<>(getCharsList(nextLenChars));
				removeAllChars(charsRef, nextLenCharsRef);

				if (nextLenCharsRef.size() == 1) {
					List<Character> nextLenCharsRef2 = new LinkedList<>(getCharsList(nextLenChars));
					removeAllChars(getString(nextLenCharsRef), nextLenCharsRef2);

					if (getString(nextLenCharsRef2).equals(charsRef)) {
						addToChain(chain, charsRef);

						charsRef = nextLenChars;

						addToChain(chain, nextLenChars);
						
						findNextInChain(chain, charsRef, charArraysLinkedMap, maxChainsCount, maxLen);

						int nextLen = charsRef.length();

						if (nextLen == maxLen) {
							handleChainEnding(maxChainsCount, chain);
						}

					} else {
						addToChain(chain, chars);
						handleChainEnding(maxChainsCount, chain);
					}

				} else {
					addToChain(chain, chars);
					handleChainEnding(maxChainsCount, chain);
				}
			}
		}
	}
	
	private static List<Character> getCharsList(String string) {
		return string.chars().parallel().mapToObj(i -> (char)i).collect(Collectors.toList());
	}

	private static void removeAllChars(String charsToRemove, List<Character> targetCharsList) {
		charsToRemove.chars().mapToObj(i -> (char)i).forEach(targetCharsList::remove);
	}

	private static void handleChainEnding(java.util.concurrent.atomic.AtomicInteger maxChainsCount, ArrayList<String> chain) {
		if(chain.size() > maxChainsCount.get()) {
			maxChainsCount.set(chain.size());
		}
	}

	private static void addToChain(ArrayList<String> chain, String strNext) {
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
