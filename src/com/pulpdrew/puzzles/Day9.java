import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day9 {

	private static int mod(int a, int b) {
		int mod = a % b;
		if (mod < 0) mod += b;
		return mod;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner s = new Scanner(new File("input.txt"));
		String[] line = s.nextLine().split(" ");
		s.close();
		
		int numberOfPlayers = Integer.parseInt(line[0]);
		int numberOfMarbles = Integer.parseInt(line[6]);
		
		// Maps player number in range [0, numberOfPlayers - 1] to player score.
		Map<Integer, Integer> playerScores = new HashMap<>();
		for (int player = 0; player < numberOfPlayers; player++) {
			playerScores.put(player, 0);
		}
		
		// The circle of marbles 
		List<Integer> circle = new ArrayList<>();
		circle.add(0);
//		System.out.println("[-] (0)");
		circle.add(1);
//		System.out.println("[-]  0  (1)");
		
		int indexOfCurrentMarble = 1;
		int currentPlayer = 1;
		for (int marble = 2; marble <= numberOfMarbles; marble++) {
			
			// Place a marble
			if (marble % 23 == 0) {
				
				indexOfCurrentMarble = mod(indexOfCurrentMarble - 7, circle.size());
				int removed = circle.remove(indexOfCurrentMarble);
				if (indexOfCurrentMarble >= circle.size()) indexOfCurrentMarble = 0;
				
				int score = playerScores.get(currentPlayer) + marble + removed;
				playerScores.replace(currentPlayer, score);
				
			} else {
				
				indexOfCurrentMarble = mod((indexOfCurrentMarble + 2), circle.size());
				if (indexOfCurrentMarble == 0) indexOfCurrentMarble = circle.size();
				circle.add(indexOfCurrentMarble, marble);
				
			}
			
			// Debug output
//			System.out.print("[" + currentPlayer + "] ");
//			for (int i = 0; i < circle.size(); i++) {
//				if (i == indexOfCurrentMarble) {
//					System.out.print("(" + circle.get(i) + ") ");
//				} else {
//					System.out.print(" " + circle.get(i) + "  ");
//				}
//			}
//			System.out.println();
			
			// Increment the player
			currentPlayer = (currentPlayer + 1) % numberOfPlayers;
			
		}
		
		// Find the highest score
		int highestScore = 0;
		for (int player = 0; player < numberOfPlayers; player++) {
			if (playerScores.get(player) > highestScore) highestScore = playerScores.get(player);
		}
		
		System.out.println("\nHighest Score: " + highestScore);

	}

}
