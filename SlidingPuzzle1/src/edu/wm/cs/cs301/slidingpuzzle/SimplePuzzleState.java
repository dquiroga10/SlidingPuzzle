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
		// TODO Auto-generated method stub
		//2-D array that represents the grid, populate it beginning with 1 all the way up to the 16-numberOfEmptySlots
		//after, populate the rest with 0 which signifies empty positions
		// TODO set parent and operation to NULL
		int displayNumber = 1;
		this.emptySlots = numberOfEmptySlots;
		int total = dimension * dimension - this.emptySlots;
		this.grid = new int[dimension][dimension];
		//System.out.println("emp:"+ numberOfEmptySlots);
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (displayNumber < total + 1){
					this.grid[i][j] = displayNumber;
					displayNumber = displayNumber +1;
				}else if (displayNumber >= total) {
					this.grid[i][j] = 0;
					displayNumber = displayNumber +1;
				}
			}
		}//PuzzleState state = grid;
		
		/*for (int i = 0; i < dimension; i++) {//just printing out the array to see populated array is expected
			for (int j = 0; j < dimension; j++) {
				System.out.println(grid[i][j]);
			}
		}*/
	}
	
	@Override
	public int getValue(int row, int column) {
		// TODO Auto-generated method stub)
		// Use public grid to check to see if the value is a number other than 0, then return that value in the location
		return this.grid[row][column];
		/*if (this.grid[row][column] != 0) {
			//System.out.println(this.grid[row][column]);//not necessary just checking location
			return this.grid[row][column];
		}
		return 0;*/
	}

	@Override
	public PuzzleState getParent() {
		// TODO Auto-generated method stub
		// get the parent (previous) move that caused the current state to be achieved
		// initial state does not have a parent
		return this.parent;
	}

	@Override
	public Operation getOperation() {
		// TODO Auto-generated method stub
		//the operation which allowed for the current state to be shown
		//is set(called on) when applying move or drag operations
		// initial state has no operation
		return this.op;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		// initial state has path length 0 every operation after that increments it by 1
		// drag operation is the number of equivalent sequence of move operations 
		
		//Note that any move operation increases the path length even if the player
		//gets closer to the solution. So the path length is not the distance
		//between initial state and current state but the number of puzzle states
		//that are linked together in a sequence with the getParent() function.
		return this.pathLength;
	}

	@Override
	public PuzzleState move(int row, int column, Operation op) {
		// TODO Auto-generated method stub
		// check PuzzleState for directions
		
		
		SimplePuzzleState newgrid = new SimplePuzzleState();
		newgrid.grid = new int[grid.length][grid.length];
		for (int i = 0; i < this.grid.length; i++) {
			for(int j = 0; j < this.grid.length; j++) {
				newgrid.grid[i][j] = this.grid[i][j];
			}
		}
		//newgrid.grid = this.grid;
		
		switch(op) {

			case MOVERIGHT: { //MOVERIGHT
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
		SimplePuzzleState newgrid = new SimplePuzzleState();
		newgrid.grid = new int[grid.length][grid.length];
		newgrid.grid = this.grid;

			
		if (isEmpty(endRow, endColumn)) {
			
			if (((startRow - endRow == -1) && (startColumn - endColumn == 0)) || 
				((startRow - endRow == 1) && (startColumn - endColumn == 0)) || 
				((startColumn - endColumn == -1) && (startRow - endRow == 0)) ||
				((startColumn - endColumn == 1)) && (startRow - endRow == 0)) {

					int temp = newgrid.grid[startRow][startColumn];
					newgrid.grid[startRow][startColumn] = 0;
					newgrid.grid[endRow][endColumn] = temp;
					newgrid.pathLength = this.pathLength + 1;
					
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
					newgrid.pathLength = this.pathLength + 1;
					
					
					if ((startRow - endRow == -2) && (startColumn - endColumn == 0)) {
						newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEUP).move(startRow + 1, startColumn, Operation.MOVEUP);
					}
					return newgrid;	
					
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
		//use getValue method to see if the value at the mouse click is a 0, if so return true.
		
		if (row >= grid.length || column >= grid.length || row < 0 || column < 0) {
			return false;
		}
		
		
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
