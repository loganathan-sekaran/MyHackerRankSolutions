package test;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SolutionQueensAttack0 {
	
	static class Table {
		private int size;
		
		private Map<String, Position> positions = new HashMap<>();
		
		public Table(int size) {
			this.size = size;
		}

		public Optional<Position> getNextWest(Position position) {
			return Optional.ofNullable(positions.get(position.getRow() + ":" +  (position.getColumn() - 1)));
		}
		
		public Optional<Position> getNextEast(Position position) {
			return Optional.ofNullable(positions.get(position.getRow() + ":" +  (position.getColumn() + 1)));
		}
		
		public Optional<Position> getNextNorth(Position position) {
			return Optional.ofNullable(positions.get((position.getRow() + 1) + ":" +  (position.getColumn())));
		}
		
		public Optional<Position> getNextSouth(Position position) {
			return Optional.ofNullable(positions.get((position.getRow() - 1) + ":" +  (position.getColumn())));
		}
		
		
		public Optional<Position> getNextNorthEast(Position position) {
			return Optional.ofNullable(positions.get((position.getRow() + 1) + ":" +  (position.getColumn() + 1)));
		}
		
		public Optional<Position> getNextNorthWest(Position position) {
			return Optional.ofNullable(positions.get((position.getRow() + 1) + ":" +  (position.getColumn() - 1)));
		}
		
		public Optional<Position> getNextSouthEast(Position position) {
			return Optional.ofNullable(positions.get((position.getRow() - 1) + ":" +  (position.getColumn() + 1)));
		}
		
		public Optional<Position> getNextSouthWest(Position position) {
			return Optional.ofNullable(positions.get((position.getRow() - 1) + ":" +  (position.getColumn() - 1)));
		}
		
		public int getNextPossibleMovementsCount(Position position, Function<Position, Optional<Position>> direction) {
			int count = 0;
			Optional<Position> nextPosition = Optional.of(position);
			while(nextPosition.isPresent()) {
				nextPosition = direction.apply(nextPosition.get());
				if(nextPosition.isPresent() && !nextPosition.get().isOccupied()) {
					count++;
				} else {
					break;
				}
			} 
			return count;
		}
		
		
		public Position getPosition(int row, int column) {
			return positions.get(row + ":" +  column);
		}
		
		public Position getOrCreatePosition(int row, int column) {
			Position position = getPosition(row, column);
			if( position == null ) {
				position = new Position(row, column);
				positions.put((row + 1) + ":" + (column + 1), position);
			}
			return position;
			
			
		}
		
		
	}
	
	static class Position {
		private int row;
		private int column;
		
		private String coin;
		
		public Position(int row, int column) {
			super();
			this.row = row;
			this.column = column;
		}

		public String getCoin() {
			return coin;
		}

		public void setCoin(String coin) {
			this.coin = coin;
		}
		
		public boolean isOccupied() {
			return coin != null;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getColumn() {
			return column;
		}

		public void setColumn(int column) {
			this.column = column;
		}
		
		
	}

    // Complete the queensAttack function below.
    static int queensAttack(int n, int k, int r_q, int c_q, int[][] obstacles) {
    	Table table = new Table(n);
    	Position queenPosition = table.getPosition(r_q, c_q);
    	queenPosition.setCoin("Queen");
    	
    	for(int i = 0; i < obstacles.length; i++) {
    		int[] row = obstacles[i];
    		Position obsPosition = table.getPosition(row[0], row[1]);
    		obsPosition.setCoin("Obstacle" + (i + 1));
    	}
    	
    	int totalCount = 0;
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextEast);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextWest);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextNorth);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextSouth);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextNorthEast);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextNorthWest);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextSouthEast);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextSouthWest);
    	
    	return totalCount;
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

        String[] nk = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nk[0]);

        int k = Integer.parseInt(nk[1]);

        String[] r_qC_q = scanner.nextLine().split(" ");

        int r_q = Integer.parseInt(r_qC_q[0]);

        int c_q = Integer.parseInt(r_qC_q[1]);

        int[][] obstacles = new int[k][2];

        for (int i = 0; i < k; i++) {
            String[] obstaclesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 2; j++) {
                int obstaclesItem = Integer.parseInt(obstaclesRowItems[j]);
                obstacles[i][j] = obstaclesItem;
            }
        }

        int result = queensAttack(n, k, r_q, c_q, obstacles);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
