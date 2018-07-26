package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class Permutations {
	
	public static String[] findPermutations(String w) {
		ArrayList<String> permutations = new ArrayList<>();
		findPermutations(w, chars1 -> permutations.add(String.valueOf(chars1)));
		return permutations.toArray(new String[permutations.size()]);
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
				temp[0] = c2;
				temp[1] = c1;
				
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
								_refChars[m] = tmp2;
								_restChars[k] = tmp1;
								
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

}
