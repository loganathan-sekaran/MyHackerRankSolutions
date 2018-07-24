package test;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.stream.*;
import java.util.stream.Collector.Characteristics;
import java.util.concurrent.*;
import java.util.regex.*;

public class SolutionClimbLeaderBoard {

	private static class Player implements Comparable<Player> {
		private String name;
		private int score;
		private int rank;

		public Player(String name, int score) {
			super();
			this.name = name;
			this.score = score;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public int getRank() {
			return rank;
		}

		public void setRank(int rank) {
			this.rank = rank;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + score;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Player other = (Player) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (score != other.score)
				return false;
			return true;
		}

		@Override
		public int compareTo(Player o) {
			int res = o.score - this.score;
			
			if(res == 0) {
				res =  o.getName().compareToIgnoreCase(this.getName());
			}
			
			return res;
		}

		@Override
		public String toString() {
			return "Player [name=" + name + ", score=" + score + ", rank=" + rank + "]";
		}
		
		

	}

	// Complete the climbingLeaderboard function below.
	static int[] climbingLeaderboard(int[] scores, int[] alice) {
		SortedSet<Player> scoreBoard = new TreeSet<Player>() {
			@Override
			public boolean add(Player e) {
				boolean res = super.add(e);
				Player last = this.lower(e);
				if (last != null) {
					if (e.getScore() == last.getScore()) {
						e.setRank(last.getRank());
					} else {
						e.setRank(last.getRank() + 1);
					}
				} else {
					e.setRank(1);
				}
				return res;
			}
		};
		

		IntStream.of(scores).boxed().map(score -> new Player("Unknown", score)).forEach(scoreBoard::add);
		
		Player lastAliceObj = null;
		List<Integer> aliceRanks = new ArrayList<>(alice.length);

		for (int j = 0; j < alice.length; j++) {
			int score = alice[j];
			if(j > 0 && score != lastAliceObj.getScore()) {
				scoreBoard.remove(lastAliceObj);
			}
			Player aliceNew = new Player("Alice", score);
			scoreBoard.add(aliceNew);
			lastAliceObj = aliceNew;

			aliceRanks.add(aliceNew.getRank());
		}

		return aliceRanks.stream().mapToInt(e -> e).toArray();

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

        int scoresCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] scores = new int[scoresCount];

        String[] scoresItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < scoresCount; i++) {
            int scoresItem = Integer.parseInt(scoresItems[i]);
            scores[i] = scoresItem;
        }

        int aliceCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] alice = new int[aliceCount];

        String[] aliceItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < aliceCount; i++) {
            int aliceItem = Integer.parseInt(aliceItems[i]);
            alice[i] = aliceItem;
        }

        int[] result = climbingLeaderboard(scores, alice);

        for (int i = 0; i < result.length; i++) {
            bufferedWriter.write(String.valueOf(result[i]));

            if (i != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
