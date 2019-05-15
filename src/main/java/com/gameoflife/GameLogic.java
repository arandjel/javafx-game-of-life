package com.gameoflife;

/**
 * Rules:
 * 1. Any live cell with fewer than two live neighbours dies, as if by underpopulation.
 * 2. Any live cell with two or three live neighbours lives on to the next generation.
 * 3. Any live cell with more than three live neighbours dies, as if by overpopulation.
 * 4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction
 * 
 * @author Milos Randjelovic
 */
public class GameLogic {

	private int rowNum;
	private int colNum;

	private boolean[][] grid;

	public GameLogic(int rowNum, int colNum) {
		this.rowNum = rowNum;
		this.colNum = colNum;

		this.grid = new boolean[rowNum][colNum];
	}

	/**
	 * Randomly populate grid of cell population.
	 * 
	 * @param chance Chance that cell is alive. Must be between 0.0 and 1.0
	 * @throws IllegalArgumentException Error thrown if chance of cell being alive is less than 0.0 or greater than 1.0
	 */
	public void randomPopulation(double chance) throws IllegalArgumentException {
		if (chance < 0 || chance > 1) {
			throw new IllegalArgumentException("Chance must be between 0.0 and 1.0, current value is: " + chance);
		}
		
		for (int row = 0; row < rowNum; row++) {
			for (int col = 0; col < colNum; col++) {
				// chance that cell is born initially
				grid[row][col] = Math.random() < chance;
			}
		}
	}

	/**
	 * Get current grid of cell population.
	 * @return Current grid of cell population.
	 */
	public boolean[][] getGrid() {
		return grid;
	}

	/**
	 * Generate next population based on previous grid.
	 * @return New population grid.
	 */
	public boolean[][] next() {
		boolean[][] previousGrid = this.clone(this.grid);

		for (int row = 0; row < rowNum; row++) {
			for (int col = 0; col < colNum; col++) {
				grid[row][col] = isAlive(row, col, previousGrid);
			}
		}

		return grid;
	}

	/**
	 * Check if cell should be alive in next population iteration. Cell is located at given row and column position.
	 * 
	 * @param row Row position.
	 * @param col Column position.
	 * @param previousGrid Cell grid with previous population.
	 * @return Boolean saying if cell should be alive or not.
	 */
	private boolean isAlive(int row, int col, boolean[][] previousGrid) {
		int numberOfNeighbours = countNeighbours(previousGrid, row, col);

		// Cell dies due to overpopulation or underpopulation
		if ((numberOfNeighbours < 2) || (numberOfNeighbours > 3)) {
			return false;
		}
		// Cell remains unchanged
		if (numberOfNeighbours == 2) {
			return previousGrid[row][col];
		}
		// Cell stays alive, or a new cell is born
		// numberOfNeighbours == 3

		return true;
	}

	/**
	 * Count neighbours of cell located at given row and column position.
	 *  
	 * @param previousGrid Cell grid with previous population.
	 * @param row Row position.
	 * @param col Column position.
	 * 
	 * @return Number of neighbours.
	 */
	private int countNeighbours(final boolean[][] previousGrid, final int row, final int col) {
		int numberOfNeighbours = 0;

		// top left
		if ((row - 1 >= 0) && (col - 1 >= 0) && previousGrid[row - 1][col - 1]) {
			numberOfNeighbours++;
		}
		// left
		if ((row >= 0) && (col - 1 >= 0) && previousGrid[row][col - 1]) {
			numberOfNeighbours++;
		}
		// bottom left
		if ((row + 1 < previousGrid.length) && (col - 1 >= 0) && previousGrid[row + 1][col - 1]) {
			numberOfNeighbours++;
		}
		// bottom
		if ((row + 1 < previousGrid.length) && (col < previousGrid[0].length) && previousGrid[row + 1][col]) {
			numberOfNeighbours++;
		}
		// bottom right
		if ((row + 1 < previousGrid.length) && (col + 1 < previousGrid[0].length) && previousGrid[row + 1][col + 1]) {
			numberOfNeighbours++;
		}
		// right
		if ((row < previousGrid.length) && (col + 1 < previousGrid[0].length) && previousGrid[row][col + 1]) {
			numberOfNeighbours++;
		}
		// top right
		if ((row - 1 >= 0) && (col + 1 < previousGrid[0].length) && previousGrid[row - 1][col + 1]) {
			numberOfNeighbours++;
		}
		// top
		if ((row - 1 >= 0) && (col < previousGrid[0].length) && previousGrid[row - 1][col]) {
			numberOfNeighbours++;
		}

		return numberOfNeighbours;
	}

	/**
	 * Clone grid of cell population.
	 * 
	 * @param source Source cell grid.
	 * @return Copied cell grid.
	 */
	private boolean[][] clone(boolean[][] source) {
		int length = source.length;
		boolean[][] target = new boolean[length][source[0].length];
		for (int i = 0; i < length; i++) {
			System.arraycopy(source[i], 0, target[i], 0, source[i].length);
		}
		return target;
	}
}
