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

public class StringChainSolution4 {
	
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
    	
    	int maxLen = charArraysLinkedMap.keySet().stream().mapToInt( e -> e).max().getAsInt();
    	
    	List<List<String>> chains = new ArrayList<>();
    	
    	ArrayList<java.util.Map.Entry<Integer, List<String>>> charArraysEntriesList = new ArrayList<>(charArraysLinkedMap.entrySet());
    	Collections.sort(charArraysEntriesList, (e1,e2) -> e1.getKey() - e2.getKey());
    	
    	for (java.util.Map.Entry<Integer, List<String>> entry : charArraysEntriesList) {
    		findChain(charArraysLinkedMap, maxLen, chains, entry);
		}

    	return chains.stream()
    			.mapToInt(ls -> ls.size())
    			.max()
    			.orElseGet(() -> charArraysLinkedMap.keySet().stream().mapToInt( e -> e).min().getAsInt());

    }

	private static void findChain(LinkedHashMap<Integer, List<String>> charArraysLinkedMap, int maxLen,
			List<List<String>> chains, java.util.Map.Entry<Integer, List<String>> entry) {
		int len = entry.getKey();

		List<String> charArraysList = charArraysLinkedMap.get(len);
		for (String chars : charArraysList) {
			ArrayList<String> chain = new ArrayList<>();

			findNextInChain(chain, chars, charArraysLinkedMap, chains, maxLen);

		}
	}

	private static void findNextInChain(ArrayList<String> chain, String chars, HashMap<Integer, List<String>> charArraysLinkedMap, List<List<String>> chains, int maxLen) {
		String charsRef = chars;

		if (!charArraysLinkedMap.containsKey(charsRef.length() + 1)) {
			addToChain(chain, charsRef);

			addChainInstanceToList(chains, chain);
		} else {
			List<String> nextLenList = charArraysLinkedMap.get(charsRef.length() + 1);
				for (String nextLenChars : nextLenList) {
				List<Character> nextLenCharsRef = new ArrayList<>(getCharsList(nextLenChars));
				removeAllChars(charsRef, nextLenCharsRef);

				if (nextLenCharsRef.size() == 1) {
					List<Character> nextLenCharsRef2 = new ArrayList<>(getCharsList(nextLenChars));
					removeAllChars(getString(nextLenCharsRef), nextLenCharsRef2);

					if (getString(nextLenCharsRef2).equals(charsRef)) {
						addToChain(chain, charsRef);

						charsRef = nextLenChars;

						addToChain(chain, nextLenChars);
						
						findNextInChain(chain, charsRef, charArraysLinkedMap, chains, maxLen);

						int nextLen = charsRef.length();

						if (nextLen == maxLen) {
							addChainInstanceToList(chains, chain);
						}

					} else {
						addToChain(chain, chars);
						addChainInstanceToList(chains, chain);
					}

				} else {
					addToChain(chain, chars);
					addChainInstanceToList(chains, chain);
				}
			}
		}
	}
	
	private static List<Character> getCharsList(String string) {
		return string.chars().mapToObj(i -> (char)i).collect(Collectors.toList());
	}

	private static void removeAllChars(String charsToRemove, List<Character> targetCharsList) {
		for (Character character : charsToRemove.toCharArray()) {
			targetCharsList.remove(character);
		}
	}

	private static void addChainInstanceToList(List<List<String>> chains, ArrayList<String> chain) {
		chains.add(new ArrayList<>(chain));
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
