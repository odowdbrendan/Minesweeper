import java.util.Scanner;
import java.util.Random;

public class Gameboard {

	public Cell[][] gameboard = new Cell[10][10];
	public Cell[][] currentBoard = new Cell[10][10];

	// Constructor - defines a game board with random mines placed
	public Gameboard(Cell gameboard[][]) {
		this.gameboard = gameboard;
		setGameboard();
	}
	
	private void setGameboard() {
		Random generator = new Random();
		int randomNum;
		int mineCount = 0;

		// Loop through the game board and place mines at random locations within the
		// 8X8 grid, creates an outer layer of blanks
		for (int i = 0; i < gameboard.length; i++) {
			for (int j = 0; j < gameboard[i].length; j++) {
				randomNum = generator.nextInt(7);
				if ((randomNum != 1 && mineCount < 10) || i == 0 || i == 9 || j == 0 || j == 9) {
					gameboard[i][j] = new Cell("-", false);
					currentBoard[i][j] = new Cell("-", false);
				} else {
					gameboard[i][j] = new Cell("M", true);
					currentBoard[i][j] = new Cell("-", true);
				}
			}
		}
	}
	
	public void run() {

		int gameStatus = 0;

		printCurrentGameboard(currentBoard);
		peekMines();
		int userRow = getUserRow();
		int userColumn = getUserColumn();
		String mineGuess = getUserMineGuess();

		while (gameStatus == 0) {

			if (mineGuess.equals("yes") && gameboard[userRow][userColumn].getMineStatus() == false) {
				System.out.println("The guess was incorrect, you lose. There was not a mine located here.");
				gameStatus = 1;

			} else if (mineGuess.equals("no") && gameboard[userRow][userColumn].getMineStatus() == true) {
				System.out.println("The guess was incorrect, you lose. There was a mine located here.");
				gameStatus = 1;

			} else if (mineGuess.equals("yes") && gameboard[userRow][userColumn].getMineStatus() == true) {
				System.out.println("The guess was correct. There was a mine located here.");
				currentBoard[userRow][userColumn].setCharacter("M");

			} else if (mineGuess.equals("no") && gameboard[userRow][userColumn].getMineStatus() == false) {
				System.out.println("The guess was correct. There was not a mine located here.");
				gameboard[userRow][userColumn].setCharacter(countNeighbors(userRow, userColumn));
				currentBoard[userRow][userColumn].setCharacter(countNeighbors(userRow, userColumn));
			}

			if (gameStatus == 0) {
			printCurrentGameboard(currentBoard);
			peekMines();
			userRow = getUserRow();
			userColumn = getUserColumn();
			mineGuess = getUserMineGuess();
			} else {
				playAgain();
			}
		}

	}

	// Prints the entire game board minus the outer grid, showing location of all mines
	public void printGameboard(Cell[][] board) {
		this.gameboard = board;

		for (int i = 1; i < board.length - 1; i++) {
			for (int j = 1; j < board[i].length - 1; j++) {
				System.out.print(board[i][j]);
				if (j < board[i].length - 1)
					System.out.print(" ");
			}
			System.out.println();
		}
	}

	// Prints the game board based on what the user has guessed
	public void printCurrentGameboard(Cell[][] board) {
		this.currentBoard = board;

		for (int i = 1; i < board.length - 1; i++) {
			for (int j = 1; j < board[i].length - 1; j++) {
				System.out.print(board[i][j]);
				if (j < board[i].length - 1)
					System.out.print(" ");
			}
			System.out.println();
		}
	}

	private String countNeighbors(int row, int column) {
		int counter = 0;

		if (gameboard[row - 1][column - 1].mineStatus == true)
			counter++;
		if (gameboard[row - 1][column].mineStatus == true)
			counter++;
		if (gameboard[row - 1][column + 1].mineStatus == true)
			counter++;
		if (gameboard[row][column - 1].mineStatus == true)
			counter++;
		if (gameboard[row][column + 1].mineStatus == true)
			counter++;
		if (gameboard[row + 1][column - 1].mineStatus == true)
			counter++;
		if (gameboard[row + 1][column].mineStatus == true)
			counter++;
		if (gameboard[row + 1][column + 1].mineStatus == true)
			counter++;

		return Integer.toString(counter);
	}

	// Gets the user input for row
	public int getUserRow() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the row location between 1 and 8: ");
		int userRow = scan.nextInt();

		return userRow;
	}

	// Gets the user input for column
	private int getUserColumn() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the column location between 1 and 8: ");
		int userColumn = scan.nextInt();

		return userColumn;
	}

	// Gets the user input for mine guess
	private String getUserMineGuess() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Does this cell contain a mine?");
		String userMineGuess = scan.nextLine();

		return userMineGuess;
	}

	// If user says yes, prints the current game board with location of mines
	private void peekMines() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Would you like to peek the mines?");
		String userResponse = scan.nextLine();

		if (userResponse.equals("yes"))
			printGameboard(gameboard);
	}

	private void playAgain() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Would you like to play again?");
		String userResponse = scan.nextLine();

		if (userResponse.equals("yes"))
			setGameboard();
			run();
	}
		
}
