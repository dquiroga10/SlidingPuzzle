package edu.wm.cs.cs301.slidingpuzzle;


public class SimplePuzzleState implements PuzzleState {
	
	private int[][] grid;
	private int pathLength;
	private int emptySlots;
	private PuzzleState parent;
	private Operation op;
	
	public SimplePuzzleState() {

		this.grid = null;
		this.parent = null;
		this.op = null;
		this.emptySlots = 0;
		this.pathLength = 0;
		
	}	
	
	@Override
	public void setToInitialState(int dimension, int numberOfEmptySlots) {
		// TODO set parent and operation to NULL
		
		// setting up to populate grid and set how many empty slots are needed
		int displayNumber = 1;
		this.emptySlots = numberOfEmptySlots;
		int total = dimension * dimension - this.emptySlots;
		this.grid = new int[dimension][dimension];
		
		// for loops to iterate through the 2-D array to populate grid by incrementing the displayNumber variable 
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (displayNumber < total + 1){
					this.grid[i][j] = displayNumber;
					displayNumber = displayNumber +1;
					
				// sets the remaining spaces in grid to 0 in order to create empty slots
				}else if (displayNumber >= total) {
					this.grid[i][j] = 0;
					displayNumber = displayNumber +1;
				}
			}
		}
	}
	
	@Override
	public int getValue(int row, int column) {
		
		return this.grid[row][column];
	}

	@Override
	public PuzzleState getParent() {

		return this.parent;
	}

	@Override
	public Operation getOperation() {

		return this.op;
	}

	@Override
	public int getPathLength() {

		return this.pathLength;
	}

	@Override
	public PuzzleState move(int row, int column, Operation op) {
		
		// create a newgrid that will represent the the next state after the movement
		// copying the values from the parent grid to the newgrid where the newgrid is where the changes will be made
		SimplePuzzleState newgrid = new SimplePuzzleState();
		newgrid.grid = new int[grid.length][grid.length];
		for (int i = 0; i < this.grid.length; i++) {
			for(int j = 0; j < this.grid.length; j++) {
				newgrid.grid[i][j] = this.grid[i][j];
			}
		}
		
		switch(op) {

			case MOVERIGHT: { 
				if (isEmpty(row, column + 1)) {
					newgrid.op = Operation.MOVERIGHT;
					newgrid.parent = this;
					int temp = newgrid.grid[row][column];
					newgrid.grid[row][column] = 0;
					newgrid.grid[row][column+1] = temp;
					newgrid.pathLength = this.pathLength + 1;
					return newgrid;
				}
			}
			case MOVELEFT: {
				if (isEmpty(row, column - 1)) {
					newgrid.op = Operation.MOVELEFT;
					newgrid.parent = this;
					int temp = newgrid.grid[row][column];
					newgrid.grid[row][column] = 0;
					newgrid.grid[row][column-1] = temp;
					newgrid.pathLength = this.pathLength + 1;
					return newgrid;
				}
			}
			case MOVEUP: {
				if (isEmpty(row - 1, column)) {
					newgrid.op = Operation.MOVEUP;
					newgrid.parent = this;
					int temp = newgrid.grid[row][column];
					newgrid.grid[row][column] = 0;
					newgrid.grid[row - 1][column] = temp;
					newgrid.pathLength = this.pathLength + 1;
					return newgrid;
				}
			}
			case MOVEDOWN: {
				if (isEmpty(row + 1, column)) {
					newgrid.op = Operation.MOVEDOWN;
					newgrid.parent = this;
					int temp = newgrid.grid[row][column];
					newgrid.grid[row][column] = 0;
					newgrid.grid[row + 1][column] = temp;
					newgrid.pathLength = this.pathLength + 1;
					return newgrid;
				}
			}
		}
		return null;
	}

	@Override
	public PuzzleState drag(int startRow, int startColumn, int endRow, int endColumn) {
		// TODO Auto-generated method stub
		// check PuzzleState for directions
		// first for one empty slot
		// then for two empty slot
		// then for three empty slots
		
		
		// same as move, create newgrid which will represent the puzzle after the drag event
		SimplePuzzleState newgrid = new SimplePuzzleState();
		newgrid.grid = new int[grid.length][grid.length];
		for (int i = 0; i < this.grid.length; i++) {
			for(int j = 0; j < this.grid.length; j++) {
				newgrid.grid[i][j] = this.grid[i][j];
			}
		}

		
		if (isEmpty(endRow, endColumn)) {
			
			
			// this is the condition that will satisfy whenever there is only on empty slot and the drag is in effect
			if (((startRow - endRow == -1) && (startColumn - endColumn == 0)) || 
				((startRow - endRow == 1) && (startColumn - endColumn == 0)) || 
				((startColumn - endColumn == -1) && (startRow - endRow == 0)) ||
				((startColumn - endColumn == 1)) && (startRow - endRow == 0)) {

					int temp = newgrid.grid[startRow][startColumn];
					newgrid.grid[startRow][startColumn] = 0;
					newgrid.grid[endRow][endColumn] = temp;
					
					if (startRow - endRow == -1) {
						
						this.move(startRow, startColumn, Operation.MOVEUP);
						
					}else if (startRow - endRow == 1) {
						
						this.move(startRow, startColumn, Operation.MOVEDOWN);
						
					}else if (startColumn - endColumn == -1) {
						
						this.move(startRow, startColumn, Operation.MOVERIGHT);
						
					}else if (startColumn - endColumn == 1) {
						
						this.move(startRow, startColumn, Operation.MOVELEFT);
						
					}
					
					return newgrid;		
					
					
			// conditions that need to be true in order for drag to occur when there are two empty slots
			}if (((startRow - endRow == -2) && (startColumn - endColumn == 0)) ||
				((startRow - endRow == 2) && (startColumn - endColumn == 0)) ||
				((startRow - endRow == 0) && (startColumn - endColumn == -2)) ||
				((startRow - endRow == 0) && (startColumn - endColumn == 2)) ||
				
				((startRow - endRow == -1) && (startColumn - endColumn == -1)) || 
				((startRow - endRow == -1) && (startColumn - endColumn == 1)) ||
				((startRow - endRow == 1) && (startColumn - endColumn == -1)) ||
				((startRow - endRow == 1) && (startColumn - endColumn == 1))) {
				
					
					int temp = newgrid.grid[startRow][startColumn];
					newgrid.grid[startRow][startColumn] = 0;
					newgrid.grid[endRow][endColumn] = temp;
					
					// creating separate puzzle states in between to show that the drag is a result of individual movements
					// newgrid is overriden  to provide parent classes
					if ((startRow - endRow == -2) && (startColumn - endColumn == 0)) {
						newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEUP).move(startRow + 1, startColumn, Operation.MOVEUP);
					}
					return newgrid;	
			
					
			// conditions needed to be true in order to drag with 3 empty spaces
			}if (((startRow - endRow == -3) && (startColumn - endColumn == 0)) ||
					((startRow - endRow == 3) && (startColumn - endColumn == 0)) ||
					((startRow - endRow == 0) && (startColumn - endColumn == -3)) ||
					((startRow - endRow == 0) && (startColumn - endColumn == 3)) ||
					
					((startRow - endRow == -2) && (startColumn - endColumn == -1)) || 
					((startRow - endRow == -2) && (startColumn - endColumn == 1)) ||
					((startRow - endRow == 2) && (startColumn - endColumn == -1)) ||
					((startRow - endRow == 2) && (startColumn - endColumn == 1)) ||
					
					((startRow - endRow == -1) && (startColumn - endColumn == -2)) || 
					((startRow - endRow == -1) && (startColumn - endColumn == 2)) ||
					((startRow - endRow == 1) && (startColumn - endColumn == -2)) ||
					((startRow - endRow == 1) && (startColumn - endColumn == 2))) {

						int temp = newgrid.grid[startRow][startColumn];
						newgrid.grid[startRow][startColumn] = 0;
						newgrid.grid[endRow][endColumn] = temp;
						newgrid.pathLength = this.pathLength + 1;
						return newgrid;	
						
			}
		}
		
		
		return null;
	}

	@Override
	public PuzzleState shuffleBoard(int pathLength) {
		// TODO Auto-generated method stub
		// check PuzzleState for directions
		return null;
	}

	@Override
	public boolean isEmpty(int row, int column) {
		
		// checks that index is possible in the grid and not out of bounds
		if (row >= grid.length || column >= grid.length || row < 0 || column < 0) {
			return false;
		}
		
		// making sure that there is a position empty 
		if (getValue(row,column) == 0) {
			
			return true;
		}
		return false;
	}

	@Override
	public PuzzleState getStateWithShortestPath() {
		// TODO Auto-generated method stub
		// check PuzzleState for directions
		return null;
	}
	
	
	@Override
	public boolean equals(Object ps) {
		
		if (ps == null) {
			
			return false;
			
		}if (getClass()!= ps.getClass()) {
			
			return false;
		
		// make the object a sure puzzle in order to test to see that the contents being compared in each grid are the same
		// length is checked to see that the dimensions are the same meaning that they are grids of same size
		}SimplePuzzleState state = (SimplePuzzleState) ps;
		for (int i = 0; i < this.grid.length; i++) {
			for(int j = 0; j < this.grid.length; j++) {
				if(this.grid[i][j] != state.grid[i][j] || this.grid.length != state.grid.length) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
}
