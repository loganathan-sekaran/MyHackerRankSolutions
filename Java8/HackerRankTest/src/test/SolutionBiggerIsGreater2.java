package test;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.regex.*;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Stream;

public class SolutionBiggerIsGreater2 {

    // Complete the biggerIsGreater function below.
    static String biggerIsGreater(String w) {
    	boolean[] foundABigger  = new boolean[1];
    	String[] currentBigger = new String[1];
    	
    	Consumer<char[]> charsConsumer = chars -> {
    		String currentCombination = String.valueOf(chars);
    		if(currentCombination.compareTo(w) > 0) {
    			if(!foundABigger[0]) {
    				currentBigger[0] = currentCombination;
    				foundABigger[0] = true;
    			} else {
    				if(currentBigger[0].compareTo(currentCombination) > 0) {
    					currentBigger[0] = currentCombination;
    				}
    			}
    			
    		}
    	};
    	
		findPermutations(w, charsConsumer);
		
		if(!foundABigger[0]) {
			return "no answer";
		} else {
			return currentBigger[0];
		}
        
       
    }
    
    public static void findPermutations(String w, Consumer<char[]> charsConsumer) {
		char[] chars = w.toCharArray();
		if(chars.length <= 1) {
			charsConsumer.accept(chars);
			return;
		}
		
		findPermutations(chars, new char[0], chars, charsConsumer);
	}

	private static void findPermutations(char[] allChars, char[] refChars, char[] restChars, Consumer<char[]> charsConsumer) {
			
			if(restChars.length == 2) {
				char[] temp = Arrays.copyOf(restChars, restChars.length);
				char c1 = temp[0];
				char c2 = temp[1];
				if(c2 > c1) {
					temp[0] = c2;
					temp[1] = c1;
				}
				
				char[] res = combineArrays(refChars, temp);
				
				charsConsumer.accept(res);
			} else {
				int k = 0;
				char[] _refChars = new char[0];
				char[] _restChars = new char[0];
				int m = 0;
				
				int i = 1;
				char[] tmp = restChars;
				char[] __refChars = Arrays.copyOfRange(tmp, 0, i);
				char[] __restChars = Arrays.copyOfRange(tmp, i, tmp.length);
				
				do {
					
					if(i < tmp.length) {
					
						
						if(m < i) {
							_refChars = combineArrays(refChars, __refChars);
							_restChars = __restChars;
						} 
						
						char[] existing = combineArrays(_refChars, _restChars);
						charsConsumer.accept(existing);
						
						findPermutations(allChars, _refChars, _restChars, charsConsumer);
						
						if(_refChars.length >= 1) {
							if(k < _restChars.length && m < _refChars.length) {
								combineArrays(refChars, __refChars);
								_refChars = combineArrays(refChars, __refChars);;
								_restChars = Arrays.copyOf(__restChars, __restChars.length);
								char tmp1 = _refChars[m];
								char tmp2 = _restChars[k];
								//if(tmp2 > tmp1) {
									_refChars[m] = tmp2;
									_restChars[k] = tmp1;
								//}
								
							}
						}
						
						if(m == _refChars.length) {
							++k;
							m = -1;
						}
						m++;
					}
					
				} while (k <= _restChars.length);
			}
		
	}

	private static char[] combineArrays(char[] refChars, char[] restChars) {
		char[] res = new char[refChars.length + restChars.length];
		System.arraycopy(refChars, 0, res, 0, refChars.length);
		System.arraycopy(restChars, 0, res, refChars.length, restChars.length);
		return res;
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
