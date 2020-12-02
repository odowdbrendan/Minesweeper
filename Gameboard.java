import java.util.Scanner;
import java.util.Random;
import java.util.Stack;

public class Gameboard {

	public Cell[][] currentBoard = new Cell[10][10];

	Stack expandStack_ToDo = new Stack();
	Stack expandStack_Done = new Stack();

	// Constructor - defines a game board with random mines placed
	public Gameboard() {
		setGameboard();
	}

	private void setGameboard() {
		Random generator = new Random();
		int mineCount = 0;

		// Initialize each cell on 10X10 grid with a dash
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[i].length; j++) {
				currentBoard[i][j] = new Cell("-", false, i, j);
			}
		}

		while (mineCount < 10) {
			int randomRow;
			int randowmColumn;
			Cell mineCell;

			randomRow = generator.nextInt(8) + 1; 
			
			randowmColumn = generator.nextInt(8) + 1;

			mineCell = currentBoard[randomRow][randowmColumn];

			if (!mineCell.getMineStatus()) {
				currentBoard[randomRow][randowmColumn].setCharacter("M");
				currentBoard[randomRow][randowmColumn].setMineStatus(true);
				mineCount++;
			}

		}

	}

	private boolean checkWinner() {
		int unknownCellsCount = 0;

		// game over if all cells revealed
		for (int i = 1; i < currentBoard.length - 1; i++) {
			for (int j = 1; j < currentBoard[i].length - 1; j++) {
				if (currentBoard[i][j].getMineCharacter().equals("-")) {
					unknownCellsCount++;
				}
			}
		}

		return (unknownCellsCount == 0 ? true : false);
	}

	private void checkExpansionNeighbors(Cell expandCell) {
		Cell pushCell;

		// add this cells neighbors to the stack for expansion
		if ((expandCell.getMineRow() - 1) > 0 && (expandCell.getMineColumn() - 1) > 0) {
			pushCell = currentBoard[expandCell.getMineRow() - 1][expandCell.getMineColumn() - 1];
			if (!expandStack_ToDo.contains(pushCell) && !expandStack_Done.contains(pushCell)) {
				expandStack_ToDo.push(pushCell);
			}
		}

		if ((expandCell.getMineRow() - 1) > 0) {
			pushCell = currentBoard[expandCell.getMineRow() - 1][expandCell.getMineColumn()];
			if (!expandStack_ToDo.contains(pushCell) && !expandStack_Done.contains(pushCell)) {
				expandStack_ToDo.push(pushCell);
			}
		}

		if ((expandCell.getMineRow() - 1) > 0 && (expandCell.getMineColumn() + 1) <= 8) {
			pushCell = currentBoard[expandCell.getMineRow() - 1][expandCell.getMineColumn() + 1];
			if (!expandStack_ToDo.contains(pushCell) && !expandStack_Done.contains(pushCell)) {
				expandStack_ToDo.push(pushCell);
			}
		}

		if ((expandCell.getMineColumn() - 1) > 0) {
			pushCell = currentBoard[expandCell.getMineRow()][expandCell.getMineColumn() - 1];
			if (!expandStack_ToDo.contains(pushCell) && !expandStack_Done.contains(pushCell)) {
				expandStack_ToDo.push(pushCell);
			}
		}

		if ((expandCell.getMineColumn() + 1) <= 8) {
			pushCell = currentBoard[expandCell.getMineRow()][expandCell.getMineColumn() + 1];
			if (!expandStack_ToDo.contains(pushCell) && !expandStack_Done.contains(pushCell)) {
				expandStack_ToDo.push(pushCell);
			}
		}

		if ((expandCell.getMineRow() + 1) <= 8 && (expandCell.getMineColumn() - 1) > 0) {
			pushCell = currentBoard[expandCell.getMineRow() + 1][expandCell.getMineColumn() - 1];
			if (!expandStack_ToDo.contains(pushCell) && !expandStack_Done.contains(pushCell)) {
				expandStack_ToDo.push(pushCell);
			}
		}

		if ((expandCell.getMineRow() + 1) <= 8) {
			pushCell = currentBoard[expandCell.getMineRow() + 1][expandCell.getMineColumn()];
			if (!expandStack_ToDo.contains(pushCell) && !expandStack_Done.contains(pushCell)) {
				expandStack_ToDo.push(pushCell);
			}
		}

		if ((expandCell.getMineRow() + 1) <= 8 && (expandCell.getMineColumn() + 1) <= 8) {
			pushCell = currentBoard[expandCell.getMineRow() + 1][expandCell.getMineColumn() + 1];
			if (!expandStack_ToDo.contains(pushCell) && !expandStack_Done.contains(pushCell)) {
				expandStack_ToDo.push(pushCell);
			}
		}

	}

	private void expand(Cell expandCell) {

		expandStack_Done.clear(); // new iteration so clear out this stack

		checkExpansionNeighbors(expandCell);

		while (!expandStack_ToDo.empty()) {
			Cell popCell = (Cell) expandStack_ToDo.pop();

			if (!popCell.getMineStatus()) {
				// process cells that done have a mine
				int neighborCount = countNeighbors(popCell.getMineRow(), popCell.getMineColumn());
				popCell.setCharacter(String.valueOf(neighborCount));
				if (neighborCount == 0) {
					checkExpansionNeighbors(popCell); // keep goin as no neighbor is a mine or adjacant to one
				}

			}
			expandStack_Done.push(popCell); // tag this cell as done
		}

	}

	public void run() {

		boolean gameOn = true;

		printBoard(currentBoard, false);

		peekMines();
		int userRow = getUserInput("row", 1, 8);
		int userColumn = getUserInput("column", 1, 8);
		String mineGuess = getUserMineGuess();

		while (gameOn) {

			if (mineGuess.equalsIgnoreCase("yes") || (mineGuess.equalsIgnoreCase("y"))
					&& currentBoard[userRow][userColumn].getMineStatus() == false) {
				System.out.println("The guess was incorrect, you lose. There was not a mine located here.");
				gameOn = false;

			} else if (mineGuess.equalsIgnoreCase("no")
					|| (mineGuess.equalsIgnoreCase("n")) && currentBoard[userRow][userColumn].getMineStatus() == true) {
				System.out.println("The guess was incorrect, you lose. There was a mine located here.");
				gameOn = false;

			} else if (mineGuess.equalsIgnoreCase("yes")
					|| (mineGuess.equalsIgnoreCase("y")) && currentBoard[userRow][userColumn].getMineStatus() == true) {
				System.out.println("The guess was correct. There was a mine located here.");

			} else if (mineGuess.equalsIgnoreCase("no") || (mineGuess.equalsIgnoreCase("n"))
					&& currentBoard[userRow][userColumn].getMineStatus() == false) {
				System.out.println("The guess was correct. There was not a mine located here.");
				int neighborCount = countNeighbors(userRow, userColumn);
				currentBoard[userRow][userColumn].setCharacter(String.valueOf(neighborCount));
				if (neighborCount == 0) {
					expand(currentBoard[userRow][userColumn]); // expand the board
				}
			}

			if (gameOn && checkWinner()) {
				gameOn = false;
				System.out.println("You have WON the world Minesweeper championship!! Congratulations!!!!!.");
			}

			if (gameOn) {
				printBoard(currentBoard, false);
				peekMines();
				userRow = getUserInput("row", 1, 8);
				userColumn = getUserInput("column", 1, 8);
				mineGuess = getUserMineGuess();
			}
		}

	}

	// Prints the entire game board minus the outer grid, showing location of all
	// mines
	public void printBoard(Cell[][] board, boolean peek) {

		for (int i = 1; i < board.length - 1; i++) {
			for (int j = 1; j < board[i].length - 1; j++) {
				Cell printCell = board[i][j];

				if (!peek && printCell.getMineStatus()) {
					System.out.print("-");
				} else
					System.out.print(printCell);

				if (j < board[i].length - 1)
					System.out.print(" ");
			}
			System.out.println();
		}
	}

	private int countNeighbors(int row, int column) {
		int counter = 0;

		if (currentBoard[row - 1][column - 1].getMineStatus())
			counter++;
		if (currentBoard[row - 1][column].getMineStatus())
			counter++;
		if (currentBoard[row - 1][column + 1].getMineStatus())
			counter++;
		if (currentBoard[row][column - 1].getMineStatus())
			counter++;
		if (currentBoard[row][column + 1].getMineStatus())
			counter++;
		if (currentBoard[row + 1][column - 1].getMineStatus())
			counter++;
		if (currentBoard[row + 1][column].getMineStatus())
			counter++;
		if (currentBoard[row + 1][column + 1].getMineStatus())
			counter++;

		return counter;
	}

	// Gets the user input for row
	public int getUserInput(String msg, int minNumber, int maxNumber) {
		Scanner scan = new Scanner(System.in);
		int row = 0;
		System.out.println("Please enter " + msg + " between " + minNumber + " and " + maxNumber + " : ");

		try {
			row = scan.nextInt();
			if (row < minNumber || row > maxNumber)
				throw new Exception("Invalid number");

		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			getUserInput(msg, minNumber, maxNumber);
		}

		return row;
	}

	// Gets the user input for mine guess
	private String getUserMineGuess() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Does this cell contain a mine? (y/n) ");
		String userMineGuess = scan.nextLine();

		if (!userMineGuess.matches("(?i)yes|y|no|n")) {
			System.out.println("Invalid input. Please try again.");
			getUserMineGuess();
		}

		return userMineGuess;
	}

	// If user says yes, prints the current game board with location of mines
	private void peekMines() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Would you like to peek the mines? (y/n)");
		String userResponse = scan.nextLine();

		if (userResponse.matches("(?i)yes|y")) {
			printBoard(currentBoard, true);
		} else if (!userResponse.matches("(?i)no|n")) {
			System.out.println("Invalid input. Please try again.");
			peekMines();
		}
	}

}
