# Minesweeper
Recreation of the classic game minesweeper.

Cell class:

defines a cell on the gameboard. Made up of a character and a mine status. A cell will show a hyphen (-) if it has not been selected. If the user selects a cell guessing it is a not a mine, the cell will be replaced with a number representing how many neighboring cells are mines. If the user gueses it is a mine the cell will show a ("M") representing a mine. Incorrectly guessing a cell as not a mine or vice versa results in the end of the game.

Gameboard Class:

defines a 2D array of cells and initalizes the gameboard with random mines. One gameboard will be the one the player sees, unless they opt to cheat and see the location of all the mines. This class defines the logic of the game and loops until the user has made an incorrect guess.

Minesweeper Class:

creates an instance of the Gameboard and callls its run method.
