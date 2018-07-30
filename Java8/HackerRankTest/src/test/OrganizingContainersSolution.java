package test;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrganizingContainersSolution {
	
	static class Box<T,R> {
		T val1;
		R val2;
		
		public Box(T t, R r) {
			super();
			this.val1 = t;
			this.val2 = r;
		}

		@Override
		public String toString() {
			return "Box [t=" + val1 + ", r=" + val2 + "]";
		}
		
		
	}

    // Complete the organizingContainers function below.
    static String organizingContainers(int[][] container) {
//    	AtomicInteger index1 = new AtomicInteger(0);
//    	 Map<Integer, Integer> containerwiseCount = Stream.of(container)
//    	 .map(val -> new Box<>(index1.getAndIncrement(), val))
//    	 .collect(Collectors.toMap(b -> b.val1, b -> Arrays.stream(b.val2).sum()));
//    	 
// 		int containerCount =  containerwiseCount.values().iterator().next();
//		boolean possible = containerwiseCount.values().stream().reduce(Boolean.TRUE, (b, val) -> b && val == containerCount, (b1, b2) -> b1 && b2);
//
//		if(possible) {
    	
    	boolean possible;
    	
    	 AtomicInteger index2 = new AtomicInteger(0);
    	 Map<Integer, Map<Integer, Integer> > containerwiseMap = Stream.of(container)
    	 .map(val -> new Box<>(index2.getAndIncrement(), val))
    	 .collect(Collectors.toMap(b -> b.val1, 
    			 b -> {
    				 AtomicInteger index3 = new AtomicInteger(0);
					return Arrays.stream(b.val2).mapToObj(val -> new Box<>(index3.getAndIncrement(), val))
							.collect(Collectors.toMap(b1 -> b1.val1, b1 -> b1.val2));
				}
    		));
    	 
    	  	Function<int[], ? extends Map<Integer, Long>> rowToMap = (Function<int[], ? extends Map<Integer, Long>>) (int[] intArr) -> {
			AtomicInteger index = new AtomicInteger(0);
			return Arrays.stream(intArr)
							.mapToObj(val -> new Box<>(index.getAndIncrement(), val))
							.collect(Collectors.groupingBy(b -> b.val1, 
									() -> new LinkedHashMap<Integer, Long>(), 
									Collectors.mapping(b1 -> b1.val2, 
											Collectors.summingLong(e -> e))
									)
						);
		};
			Map<Integer, Long> typesCount = Stream.of(container)
        		.collect(Collectors.reducing(new LinkedHashMap<Integer, Long>(), 
        						rowToMap,
        						(map1, map2) -> {
        							map1.forEach((k, v) -> {
        								map2.merge(k, v, (v1, v2) -> v1 + v2);
        							});
    								return map2;
    							}
        					)
        				);
    	 
    	 possible = containerwiseMap.entrySet()
    			 					.stream()
    			 					.allMatch(containerEntry -> 
    			 						containerEntry.getValue()
											.entrySet()
											.stream()
											.allMatch(cEntry -> {
												long typeCountInRestContainer = containerwiseMap.entrySet()
		    		    			 					.stream()
		    		    			 					.filter(entry -> !entry.getKey().equals(containerEntry.getKey()))
		    		    			 					.map(entry -> entry.getValue())
		    		    			 					.map(map -> map.get(cEntry.getKey()))
		    		    			 					.mapToLong( e -> e)
		    		    			 					.sum();
												long totalCountOfType = typesCount.get(cEntry.getKey());
												long typeCountInContainer = cEntry.getValue();
												boolean restContainerContainsRestofTypeCount = typeCountInRestContainer > 0 && typeCountInRestContainer == (totalCountOfType - typeCountInContainer);
												
												long thisContainerRestTypeCount = containerwiseMap.get(containerEntry.getKey())
														.entrySet()
														.stream()
														.filter(entry -> !entry.getKey().equals(cEntry.getKey()))
														.map(entry -> entry.getValue())
														.mapToLong(e -> e)
														.sum();
													
												boolean thisContainerContainsOtherCountsEqualsToRestOfTypeCount = (typeCountInContainer  + thisContainerRestTypeCount) >= typeCountInRestContainer;
												return restContainerContainsRestofTypeCount
														&& 
														thisContainerContainsOtherCountsEqualsToRestOfTypeCount;
											}));
    	 
	//	}
    
    	
  
		
			
		//int typeCount =  containerwiseCount.values().iterator().next();
		//possible = possible && typesCount.values().stream().reduce(Boolean.TRUE, (b, val) -> b && val == typeCount, (b1, b2) -> b1 && b2);
	
		
    	return possible ? "Possible" : "Impossible";

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

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[][] container = new int[n][n];

            for (int i = 0; i < n; i++) {
                String[] containerRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < n; j++) {
                    int containerItem = Integer.parseInt(containerRowItems[j]);
                    container[i][j] = containerItem;
                }
            }

            String result = organizingContainers(container);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
