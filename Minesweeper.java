
public class Minesweeper {

	public static Cell [][] gameboard = new Cell [10][10];

	public static void main (String args[]) {	
		Gameboard gameBoard = new Gameboard(gameboard);					
		gameBoard.run();
	}	
}
