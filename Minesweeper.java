import java.util.Scanner;

public class Minesweeper {

	private static boolean playAgain() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Would you like to play again?");
		String userResponse = scan.nextLine();
		boolean playAgain = false;

		if (userResponse.matches("(?i)yes|y")) {
			playAgain = true;
		} else if (!userResponse.matches("(?i)no|n")) {
			System.out.println("Invalid input. Please try again.");
			playAgain = playAgain();
		} 

		return playAgain;
	}

	public static void main(String args[]) {
		boolean playGame = true;

		while (playGame) {
			Gameboard gameBoard = new Gameboard();
			gameBoard.run();
			playGame = playAgain();
		}
	}
}
