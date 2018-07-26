package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Generated;

import static org.junit.Assert.*;
import org.junit.Test;

@Generated(value = "org.junit-tools-1.0.6")
public class PermutationsTest {

	private Permutations createTestSubject() {
		return new Permutations();
	}

	@Test
	public void testFindPermutations1() throws Exception {
		String w = "a";
		String[] result;

		// default test
		result = Permutations.findPermutations(w);
		
		assertEquals(w, result[0]);
		
	}
	
	@Test
	public void testFindPermutations2() throws Exception {
		String w = "ab";
		String[] result;

		// default test
		result = Permutations.findPermutations(w);
		
		assertEquals("ba", result[0]);
		
	}
	
	@Test
	public void testFindPermutations3() throws Exception {
		String w = "abc";
		List<String> result;

		// default test
		result = Arrays.asList(Permutations.findPermutations(w));
		
		String[] expected = {"acb", "bac", "bca", "cab", "cba"};
		System.out.println(result);
		Stream.of(expected).forEach(s -> assertTrue(result.contains(s)));
		
	}
	
	@Test
	public void testFindPermutations4() throws Exception {
		String w = "abcd";
		List<String> result;

		// default test
		result = Arrays.asList(Permutations.findPermutations(w));
		
		result.stream().distinct().forEach(System.out::println);
		
	}
}