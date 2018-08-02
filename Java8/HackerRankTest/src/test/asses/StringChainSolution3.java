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

public class StringChainSolution3 {
	
	// Complete the longestChain function below.
    static int longestChain(List<String> words) {
    	
    	
    	ArrayList<String> wordsRef = new ArrayList<>(words);
    	Collections.sort(wordsRef);
    	
    	LinkedHashMap<Integer, List<List<Character>>> charArraysLinkedMap = new LinkedHashMap<>();
    	for (String string : wordsRef) {
    		int len = string.length();
    		charArraysLinkedMap.computeIfAbsent(len, k -> new ArrayList<>());
    		charArraysLinkedMap.get(len).add(getCharsList(string));
		}
    	
    	int maxLen = charArraysLinkedMap.keySet().stream().mapToInt( e -> e).max().getAsInt();
    	
    	List<List<String>> chains = new ArrayList<>();
    	
    	ArrayList<java.util.Map.Entry<Integer, List<List<Character>>>> charArraysEntriesList = new ArrayList<>(charArraysLinkedMap.entrySet());
    	Collections.sort(charArraysEntriesList, (e1,e2) -> e1.getKey() - e2.getKey());
    	
    	for (java.util.Map.Entry<Integer, List<List<Character>>> entry : charArraysEntriesList) {
    		findChain(charArraysLinkedMap, maxLen, chains, entry);
		}

    	return chains.stream()
    			.mapToInt(ls -> ls.size())
    			.max()
    			.orElseGet(() -> charArraysLinkedMap.keySet().stream().mapToInt( e -> e).min().getAsInt());

    }

	private static List<Character> getCharsList(String string) {
		return string.chars().mapToObj(i -> (char)i).collect(Collectors.toList());
	}

	private static void findChain(LinkedHashMap<Integer, List<List<Character>>> charArraysLinkedMap, int maxLen,
			List<List<String>> chains, java.util.Map.Entry<Integer, List<List<Character>>> entry) {
		int len = entry.getKey();

		List<List<Character>> charArraysList = charArraysLinkedMap.get(len);
		for (List<Character> chars : charArraysList) {
			ArrayList<String> chain = new ArrayList<>();

			findNextInChain(chain, chars, charArraysLinkedMap, chains, maxLen);

		}
	}

	private static void findNextInChain(ArrayList<String> chain, List<Character> chars, HashMap<Integer, List<List<Character>>> charArraysLinkedMap, List<List<String>> chains, int maxLen) {
		List<Character> charsRef = chars;

		if (!charArraysLinkedMap.containsKey(charsRef.size() + 1)) {
			addToChain(chain, charsRef);

			addChainInstanceToList(chains, chain);
		} else {
			List<List<Character>> nextLenList = charArraysLinkedMap.get(charsRef.size() + 1);
				for (List<Character> nextLenChars : nextLenList) {
				List<Character> nextLenCharsRef = new ArrayList<>(nextLenChars);
				removeAllChars(charsRef, nextLenCharsRef);

				if (nextLenCharsRef.size() == 1) {
					List<Character> nextLenCharsRef2 = new ArrayList<>(nextLenChars);
					removeAllChars(nextLenCharsRef, nextLenCharsRef2);

					if (getString(nextLenCharsRef2).equals(getString(charsRef))) {
						addToChain(chain, charsRef);

						charsRef = nextLenChars;

						addToChain(chain, nextLenChars);
						
						findNextInChain(chain, charsRef, charArraysLinkedMap, chains, maxLen);

						int nextLen = charsRef.size();

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

	private static void removeAllChars(List<Character> charsToRemove, List<Character> targetCharsList) {
		for (Character character : charsToRemove.toArray(new Character[charsToRemove.size()])) {
			targetCharsList.remove(character);
		}
	}

	private static void addChainInstanceToList(List<List<String>> chains, ArrayList<String> chain) {
		chains.add(new ArrayList<>(chain));
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
