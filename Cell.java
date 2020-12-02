public class Cell {

	private String character;
	private boolean mineStatus;
	private int row;
	private int column;

	// Constructor - defines what a Cell is
	public Cell(String character, boolean mineStatus, int row, int column) {
		this.character = character;
		this.mineStatus = mineStatus;
		this.row = row;
		this.column = column;
	}

	// Getter method to retrieve the mine status of the cell
	public boolean getMineStatus() {
		return mineStatus;
	}

	// Getter method to retrieve the row of the cell
	public int getMineRow() {
		return row;
	}

	// Getter method to retrieve the column of the cell
	public int getMineColumn() {
		return column;
	}

	public String getMineCharacter() {
		return character;
	}


	// Setter method to set the value of the cell (will be used when the user
	public void setCharacter(String newCharacter) {
		this.character = newCharacter;
	}

	public void setMineStatus(boolean mineStatus) {
		this.mineStatus = mineStatus;
	}

	// This is important to print the value of the cell as a readable String, rather
	public String toString() {
		return this.character;
	}

}
