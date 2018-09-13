package edu.wm.cs.cs301.slidingpuzzle;

public class SimplePuzzleState implements PuzzleState {
	
	public int[][] grid = new int[4][4];
	
	

	@Override
	public void setToInitialState(int dimension, int numberOfEmptySlots) {
		// TODO Auto-generated method stub
		int displayNumber = 1;
		int total = dimension * dimension - numberOfEmptySlots;
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
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				System.out.println(grid[i][j]);
			}
		}
	}
	@Override
	public int getValue(int row, int column) {
		
		// TODO Auto-generated method stub)
		if (grid[row][column] != 0) {
			return grid[row][column];
		}
		return 0;
	}

	@Override
	public PuzzleState getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Operation getOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PuzzleState move(int row, int column, Operation op) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuzzleState drag(int startRow, int startColumn, int endRow, int endColumn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuzzleState shuffleBoard(int pathLength) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty(int row, int column) {//done
		// TODO Auto-generated method stub
		int val = getValue(row, column);
		if (val == 0) {
			return true;
		}
		return false;
	}

	@Override
	public PuzzleState getStateWithShortestPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
