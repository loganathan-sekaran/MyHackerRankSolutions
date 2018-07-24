package test;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SolutionMagicCube {

    // Complete the formingMagicSquare function below.
    static int formingMagicSquare(int[][] s) {
        int[] edgesRef1 =  new int[]{6,1,8,3,4,9,2,7};

        int[] edgesRef2 =  new int[]{8,1,6,7,2,9,4,3};
        
        int[] edgeFromInput = new int[] {s[0][0], s[0][1], s[0][2], s[1][2], s[2][2], s[2][1], s[2][0], s[1][0]};
        
        int centreElem = s[1][1];
        
        int minCost = Integer.MAX_VALUE;
                
        minCost = getMinCostForEdgeRef(edgesRef1, edgeFromInput, minCost, centreElem);
        
        minCost = getMinCostForEdgeRef(edgesRef2, edgeFromInput, minCost, centreElem);
        
        
        return minCost;


    }

	private static int getMinCostForEdgeRef(int[] edgesRef1, int[] edgeFromInput, final int minCost, int centreElem) {
		ArrayList<Integer> ref1 = new ArrayList<>(IntStream.of(edgesRef1)
				.boxed()
				.collect(Collectors.toList()));
        int i = 0;
        int minimumCost = minCost;
        do {
			int cost = getCost(ref1, edgeFromInput, centreElem);
			System.out.println("Cost of " + ref1 + ": " + cost);
			if(cost < minimumCost) {
				minimumCost = cost;
				System.out.println("Last Min Cost " + ref1 + ": " + cost);
			}
			
			if(i < 3) {
				Collections.rotate(ref1, 2);
			}
			
		} while (i++ < 3);
        
        
        return minimumCost;
	}

    private static int getCost(ArrayList<Integer> ref1, int[] edgeFromInput, int centreElem) {
    	int cost = 0;
    	if(ref1.size() == edgeFromInput.length) {
    		for (int i = 0; i < edgeFromInput.length; i++) {
    			cost += Math.abs(ref1.get(i) - edgeFromInput[i]);
			}
    	}
    	
	  if(centreElem != 5) {
		  cost += Math.abs(5 - centreElem);
      }
    	  
		return cost;
	}


    public static void main(String[] args) throws IOException {
    	//int s[][] = {{4,9,2},{3,5,7},{8,1,5}};
    	//int s[][] = {{4,5,8},{2,4,1},{1,9,7}};
    	int s[][] = {{7,6,5},{7,2,8},{5,3,4}};
    	int result = formingMagicSquare(s);
    	System.out.println(result);
    }
}
