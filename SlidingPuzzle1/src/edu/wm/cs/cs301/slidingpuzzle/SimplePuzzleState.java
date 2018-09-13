package edu.wm.cs.cs301.slidingpuzzle;

public class SimplePuzzleState implements PuzzleState {
	
	public int[][] grid = new int[4][4];
	
	@Override
	public void setToInitialState(int dimension, int numberOfEmptySlots) {
		// TODO Auto-generated method stub
		//2-D array that represents the grid, populate it beginning with 1 all the way up to the 16-numberOfEmptySlots
		//after, populate the rest with 0 which signifies empty positions
		// TODO set parent and operation to NULL
		int displayNumber = 1;
		int total = dimension * dimension - numberOfEmptySlots;
		//int[][] grid = new int[dimension][dimension];
		System.out.println("emp:"+ numberOfEmptySlots);
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (displayNumber < total + 1){
					grid[i][j] = displayNumber;
					displayNumber = displayNumber +1;
				}else if (displayNumber >= total) {
					grid[i][j] = 0;
					displayNumber = displayNumber +1;
				}
			}
		}
		for (int i = 0; i < dimension; i++) {//just printing out the array to see populated array is expected
			for (int j = 0; j < dimension; j++) {
				System.out.println(grid[i][j]);
			}
		}
	}
	@Override
	public int getValue(int row, int column) {
		// TODO Auto-generated method stub)
		// Use public grid to check to see if the value is a number other than 0, then return that value in the location
		if (grid[row][column] != 0) {
			System.out.println(grid[row][column]);//not necessary just checking location
			return grid[row][column];
		}
		return 0;
	}

	@Override
	public PuzzleState getParent() {
		// TODO Auto-generated method stub
		// get the parent (previous) move that caused the current state to be achieved
		// initial state does not have a parent
		return null;
	}

	@Override
	public Operation getOperation() {
		// TODO Auto-generated method stub
		//the operation which allowed for the current state to be shown
		//is set(called on) when applying move or drag operations
		// initial state has no operation
		return null;
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
		return 0;
	}

	@Override
	public PuzzleState move(int row, int column, Operation op) {
		// TODO Auto-generated method stub
		// check PuzzleState for directions
		return null;
	}

	@Override
	public PuzzleState drag(int startRow, int startColumn, int endRow, int endColumn) {
		// TODO Auto-generated method stub
		// // check PuzzleState for directions
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
		
		int val = getValue(row, column);
		if (val == 0) {
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

}
