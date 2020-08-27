
public class Cell {

	String character;
	boolean mineStatus;

	//Constructor - defines what a Cell is 
	public Cell(String character, boolean mineStatus) {
		this.character = character;
		this.mineStatus = mineStatus;
	}

	// Getter method to retrieve the mine status of the cell
	public boolean getMineStatus() {
		return mineStatus;
	}

	// Setter method to set the value of the cell (will be used when the user
	public void setCharacter(String newCharacter) {
		this.character = newCharacter;
	}

	// This is important to print the value of the cell as a readable String, rather
	public String toString() {
		return this.character;
	}

}

