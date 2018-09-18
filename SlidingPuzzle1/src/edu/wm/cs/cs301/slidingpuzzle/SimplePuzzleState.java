package edu.wm.cs.cs301.slidingpuzzle;

import java.util.Random;


public class SimplePuzzleState implements PuzzleState {
	
	private int[][] grid;
	private int pathLength;
	private int emptySlots;
	private PuzzleState parent;
	private Operation op;
	private int emptySquares;
	
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
		this.grid = null;
		this.parent = null;
		this.op = null;
		this.pathLength = 0;
		this.parent = null;
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
		
		// different operations that first check to see there is a empty space in operation call and then update all the
		// variables so that the puzzles state is being followed properly
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
					
					if (startRow - endRow == -1) {
						
						newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEDOWN);
						
					}else if (startRow - endRow == 1) {
						
						newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEUP);
						
					}else if (startColumn - endColumn == -1) {
						
						newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVERIGHT);
						
					}else if (startColumn - endColumn == 1) {
						
						newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVELEFT);
						
					}
					
					return newgrid;		
					
					
			// conditions that need to be true in order for drag to occur when there are two empty slots
			// two if statements inside each condition to check the two possible routes that may occur
			}if (((startRow - endRow == -2) && (startColumn - endColumn == 0)) ||
				((startRow - endRow == 2) && (startColumn - endColumn == 0)) ||
				((startRow - endRow == 0) && (startColumn - endColumn == -2)) ||
				((startRow - endRow == 0) && (startColumn - endColumn == 2)) ||
				
				((startRow - endRow == -1) && (startColumn - endColumn == -1)) || 
				((startRow - endRow == -1) && (startColumn - endColumn == 1)) ||
				((startRow - endRow == 1) && (startColumn - endColumn == -1)) ||
				((startRow - endRow == 1) && (startColumn - endColumn == 1))) {
					
					// creating separate puzzle states in between to show that the drag is a result of individual movements
					// newgrid is overriden  to provide parent classes
					if ((startRow - endRow == -2) && (startColumn - endColumn == 0)) {
						newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEDOWN).move(startRow + 1, startColumn, Operation.MOVEDOWN);
						
					}if ((startRow - endRow == 2) && (startColumn - endColumn == 0)) {
						newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEUP).move(startRow - 1, startColumn, Operation.MOVEUP);
						
					}if ((startRow - endRow == 0) && (startColumn - endColumn == -2)) {
						newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVERIGHT).move(startRow, startColumn + 1, Operation.MOVERIGHT);
						
					}if ((startRow - endRow == 0) && (startColumn - endColumn == 2)) {
						newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVELEFT).move(startRow, startColumn - 1, Operation.MOVELEFT);
						
					}
					// for each checking two possible paths that can be taken to see which path was taken
					if ((startRow - endRow == -1) && (startColumn - endColumn == -1)) {
						if (this.grid[startRow + 1][startColumn] == 0) {
							newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEDOWN).move(startRow + 1, startColumn, Operation.MOVERIGHT);
						}else if (this.grid[startRow][startColumn + 1] == 0) {
							newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVERIGHT).move(startRow, startColumn + 1, Operation.MOVEDOWN);
						}

					}if ((startRow - endRow == -1) && (startColumn - endColumn == 1)) {
						if (this.grid[startRow + 1][startColumn] == 0) {
							newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEDOWN).move(startRow + 1, startColumn, Operation.MOVELEFT);
						}else if (this.grid[startRow][startColumn - 1] == 0) {
							newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVELEFT).move(startRow, startColumn - 1, Operation.MOVEDOWN);
						}

					}if ((startRow - endRow == 1) && (startColumn - endColumn == -1)) {
						if (this.grid[startRow - 1][startColumn] == 0) {
							newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEUP).move(startRow - 1, startColumn, Operation.MOVERIGHT);
						}else if (this.grid[startRow][startColumn + 1] == 0) {
							newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVERIGHT).move(startRow, startColumn + 1, Operation.MOVEUP);
						}

					}if ((startRow - endRow == 1) && (startColumn - endColumn == 1)) {
						if (this.grid[startRow - 1][startColumn] == 0) {
							newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEUP).move(startRow - 1, startColumn, Operation.MOVELEFT);
						}else if (this.grid[startRow][startColumn - 1] == 0) {
							newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVELEFT).move(startRow, startColumn - 1, Operation.MOVEUP);
						}
					}
				
					return newgrid;	
			
					
			// conditions needed to be true in order to drag with 3 empty spaces
			// 3 different ways of making the move therefore need to check each condition to see which move is being processed
			}if (((startRow - endRow == -3) && (startColumn - endColumn == 0)) || //
					((startRow - endRow == 3) && (startColumn - endColumn == 0)) || //
					((startRow - endRow == 0) && (startColumn - endColumn == -3)) || //
					((startRow - endRow == 0) && (startColumn - endColumn == 3)) || //
					
					((startRow - endRow == -2) && (startColumn - endColumn == -1)) || //
					((startRow - endRow == -2) && (startColumn - endColumn == 1)) || // 
					((startRow - endRow == 2) && (startColumn - endColumn == -1)) || //
					((startRow - endRow == 2) && (startColumn - endColumn == 1)) ||
					
					((startRow - endRow == -1) && (startColumn - endColumn == -2)) || //
					((startRow - endRow == -1) && (startColumn - endColumn == 2)) || //
					((startRow - endRow == 1) && (startColumn - endColumn == -2)) || // 
					((startRow - endRow == 1) && (startColumn - endColumn == 2))) { //

						if ((startRow - endRow == -3) && (startColumn - endColumn == 0)) {
							newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEDOWN).move(startRow + 1, startColumn, Operation.MOVEDOWN).move(startRow + 2, startColumn, Operation.MOVEDOWN);
							
						}if ((startRow - endRow == 3) && (startColumn - endColumn == 0)) {
							newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEUP).move(startRow - 1, startColumn, Operation.MOVEUP).move(startRow - 2, startColumn, Operation.MOVEUP);
							
						}if ((startRow - endRow == 0) && (startColumn - endColumn == -3)) {
							newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVERIGHT).move(startRow, startColumn + 1, Operation.MOVERIGHT).move(startRow, startColumn + 2, Operation.MOVERIGHT);
							
						}if ((startRow - endRow == 0) && (startColumn - endColumn == 3)) {
							newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVELEFT).move(startRow, startColumn - 1, Operation.MOVELEFT).move(startRow, startColumn - 2, Operation.MOVELEFT);
							
						}
						
						// second four (out of order this is third group)
						
						if ((startRow - endRow == -1) && (startColumn - endColumn == -2)) {
							if ((this.grid[startRow + 1][startColumn] == 0) && (this.grid[startRow+1][startColumn + 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEDOWN).move(startRow + 1, startColumn, Operation.MOVERIGHT).move(startRow + 1, startColumn + 1, Operation.MOVERIGHT);
							}else if ((this.grid[startRow][startColumn + 1] == 0) && (this.grid[startRow ][startColumn + 2] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVERIGHT).move(startRow, startColumn + 1, Operation.MOVERIGHT).move(startRow, startColumn + 2, Operation.MOVEDOWN);
							}else if ((this.grid[startRow][startColumn + 1] == 0) && (this.grid[startRow + 1][startColumn + 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVERIGHT).move(startRow, startColumn + 1, Operation.MOVEDOWN).move(startRow + 1, startColumn + 1, Operation.MOVERIGHT);
							}
							
							
						}if ((startRow - endRow == 1) && (startColumn - endColumn == -2)) {
							if ((this.grid[startRow - 1][startColumn] == 0) && (this.grid[startRow - 1][startColumn + 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEUP).move(startRow - 1, startColumn, Operation.MOVERIGHT).move(startRow - 1, startColumn + 1, Operation.MOVERIGHT);
							}else if ((this.grid[startRow][startColumn + 1] == 0) && (this.grid[startRow ][startColumn + 2] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVERIGHT).move(startRow, startColumn + 1, Operation.MOVERIGHT).move(startRow, startColumn + 2, Operation.MOVEUP);
							}else if ((this.grid[startRow][startColumn + 1] == 0) && (this.grid[startRow - 1][startColumn + 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVERIGHT).move(startRow, startColumn + 1, Operation.MOVEUP).move(startRow - 1, startColumn + 1, Operation.MOVERIGHT);
							}
							
						
						}if ((startRow - endRow == -1) && (startColumn - endColumn == 2)) {
							if ((this.grid[startRow + 1][startColumn] == 0) && (this.grid[startRow+1][startColumn - 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEDOWN).move(startRow + 1, startColumn, Operation.MOVELEFT).move(startRow + 1, startColumn - 1, Operation.MOVELEFT);
							}else if ((this.grid[startRow][startColumn - 1] == 0) && (this.grid[startRow ][startColumn - 2] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVELEFT).move(startRow, startColumn - 1, Operation.MOVELEFT).move(startRow, startColumn - 2, Operation.MOVEDOWN);
							}else if ((this.grid[startRow][startColumn - 1] == 0) && (this.grid[startRow + 1][startColumn - 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVELEFT).move(startRow, startColumn - 1, Operation.MOVEDOWN).move(startRow + 1, startColumn - 1, Operation.MOVELEFT);
							}
							
							
						}if ((startRow - endRow == 1) && (startColumn - endColumn == 2)) {
							if ((this.grid[startRow - 1][startColumn] == 0) && (this.grid[startRow - 1][startColumn - 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEUP).move(startRow - 1, startColumn, Operation.MOVELEFT).move(startRow - 1, startColumn - 1, Operation.MOVELEFT);
							}else if ((this.grid[startRow][startColumn - 1] == 0) && (this.grid[startRow ][startColumn - 2] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVELEFT).move(startRow, startColumn - 1, Operation.MOVELEFT).move(startRow, startColumn - 2, Operation.MOVEUP);
							}else if ((this.grid[startRow][startColumn - 1] == 0) && (this.grid[startRow - 1][startColumn - 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVELEFT).move(startRow, startColumn - 1, Operation.MOVEUP).move(startRow - 1, startColumn - 1, Operation.MOVELEFT);
							}
						}
						
						// third four (out of order this is second group)
						
						if ((startRow - endRow == -2) && (startColumn - endColumn == -1)) {
							if ((this.grid[startRow + 1][startColumn] == 0) && (this.grid[startRow + 2][startColumn] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEDOWN).move(startRow + 1, startColumn, Operation.MOVEDOWN).move(startRow + 2, startColumn, Operation.MOVERIGHT);
							}else if((this.grid[startRow + 1][startColumn] == 0) && (this.grid[startRow + 1][startColumn + 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEDOWN).move(startRow + 1, startColumn, Operation.MOVERIGHT).move(startRow + 1, startColumn + 1, Operation.MOVEDOWN);
							}else if ((this.grid[startRow][startColumn + 1] == 0) && (this.grid[startRow + 1][startColumn + 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVERIGHT).move(startRow, startColumn + 1, Operation.MOVEDOWN).move(startRow + 1, startColumn + 1, Operation.MOVEDOWN);
							}
							
							
						}if ((startRow - endRow == -2) && (startColumn - endColumn == 1)) {
							if ((this.grid[startRow + 1][startColumn] == 0) && (this.grid[startRow + 2][startColumn] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEDOWN).move(startRow + 1, startColumn, Operation.MOVEDOWN).move(startRow + 2, startColumn, Operation.MOVELEFT);
							}else if((this.grid[startRow + 1][startColumn] == 0) && (this.grid[startRow + 1][startColumn - 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEDOWN).move(startRow + 1, startColumn, Operation.MOVELEFT).move(startRow + 1, startColumn - 1, Operation.MOVEDOWN);
							}else if ((this.grid[startRow][startColumn - 1] == 0) && (this.grid[startRow + 1][startColumn - 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVELEFT).move(startRow, startColumn - 1, Operation.MOVEDOWN).move(startRow + 1, startColumn - 1, Operation.MOVEDOWN);
							}
							
							
							
						}if ((startRow - endRow == 2) && (startColumn - endColumn == -1)) {
							if ((this.grid[startRow - 1][startColumn] == 0) && (this.grid[startRow - 2][startColumn] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEUP).move(startRow - 1, startColumn, Operation.MOVEUP).move(startRow - 2, startColumn, Operation.MOVERIGHT);
							}else if((this.grid[startRow - 1][startColumn] == 0) && (this.grid[startRow - 1][startColumn + 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEUP).move(startRow - 1, startColumn, Operation.MOVERIGHT).move(startRow - 1, startColumn + 1, Operation.MOVEUP);
							}else if ((this.grid[startRow][startColumn + 1] == 0) && (this.grid[startRow - 1][startColumn + 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVERIGHT).move(startRow, startColumn + 1, Operation.MOVEUP).move(startRow - 1, startColumn + 1, Operation.MOVEUP);
							}
							
							
						}if ((startRow - endRow == 2) && (startColumn - endColumn == 1)) {
							if ((this.grid[startRow - 1][startColumn] == 0) && (this.grid[startRow - 2][startColumn] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEUP).move(startRow - 1, startColumn, Operation.MOVEUP).move(startRow - 2, startColumn, Operation.MOVELEFT);
							}else if((this.grid[startRow - 1][startColumn] == 0) && (this.grid[startRow - 1][startColumn - 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVEUP).move(startRow - 1, startColumn, Operation.MOVELEFT).move(startRow - 1, startColumn - 1, Operation.MOVEUP);
							}else if ((this.grid[startRow][startColumn - 1] == 0) && (this.grid[startRow - 1][startColumn - 1] == 0)) {
								newgrid = (SimplePuzzleState) this.move(startRow, startColumn, Operation.MOVELEFT).move(startRow, startColumn - 1, Operation.MOVEUP).move(startRow - 1, startColumn - 1, Operation.MOVEUP);
							}
						}
						
						return newgrid;			
			}
		}
		return null;
	}

	@Override
	public PuzzleState shuffleBoard(int pathLength) {
		
		// create a copy of the parent grid in order to have the work be done to it
		// emptySquares is to keep track of how many this.emptySlots is
		emptySquares = 0;
		SimplePuzzleState newgrid = new SimplePuzzleState();
		newgrid.grid = new int[this.grid.length][this.grid.length];
		for (int i = 0; i < this.grid.length; i++) {
			for(int j = 0; j < this.grid.length; j++) {
				System.out.println(this.grid[i][j]);
				newgrid.grid[i][j] = this.grid[i][j];
				if (this.grid[i][j] == 0) {
					emptySquares += 1;
				}
			}
		}
		this.emptySlots = emptySquares;
		
		// populate an array with the cordinates of the empty slots
		// keep track of where in the array it is located with local variable posEmp which should equal the number of empty slots
		int[][] emptySpots = new int[emptySquares][2];
		int posEmp = 0;
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid.length; j++) {
				if (this.grid[i][j] == 0) {
					emptySpots[posEmp][0] = i;
					emptySpots[posEmp][1] = j;
					posEmp += 1;
				}
			}
		}posEmp = 0;
		// variable count will keep track of how many operations have taken place to only allow the set number that is passed through
		Random rand = new Random();
		int count = 0;
		while (count < pathLength) {
			// these two local variables keep track of randomizing the operation occuring and which empty space will be changed
			int randint1 = rand.nextInt(4); // the separate movements are chosen at random
			int randint2 = rand.nextInt(emptySquares); // the random empty slot is chosen
			
			
			// check to see that there is not an empty tile at each of the possible operations
			// check bound statement to see the move is possible
			if (randint1 == 0) {
				if (emptySpots[randint2][0] > 0 && newgrid.isEmpty(emptySpots[randint2][0] - 1, emptySpots[randint2][1]) == false) {
					newgrid = (SimplePuzzleState) newgrid.move(emptySpots[randint2][0] - 1, emptySpots[randint2][1], Operation.MOVEDOWN);
					count += 1;
					emptySpots[randint2][0] = emptySpots[randint2][0] - 1;
				}
				
			}else if (randint1 == 1 ) {
				if (emptySpots[randint2][0] < (this.grid.length - 1) && newgrid.isEmpty(emptySpots[randint2][0] + 1, emptySpots[randint2][1]) == false) {
					newgrid = (SimplePuzzleState) newgrid.move(emptySpots[randint2][0] + 1, emptySpots[randint2][1], Operation.MOVEUP);
					count += 1;
					emptySpots[randint2][0] = emptySpots[randint2][0] + 1;
				}
			}else if (randint1 == 2) {
				if (emptySpots[randint2][1] < (this.grid.length - 1) && newgrid.isEmpty(emptySpots[randint2][0], emptySpots[randint2][1] + 1) == false) {
					newgrid = (SimplePuzzleState) newgrid.move(emptySpots[randint2][0], emptySpots[randint2][1] + 1, Operation.MOVELEFT);
					count += 1;
					emptySpots[randint2][1] = emptySpots[randint2][1] + 1;
				}
			
			}else if (randint1 == 3) {
				if (emptySpots[randint2][1] > 0 && newgrid.isEmpty(emptySpots[randint2][0], emptySpots[randint2][1] - 1) == false) {
					newgrid = (SimplePuzzleState) newgrid.move(emptySpots[randint2][0], emptySpots[randint2][1] - 1, Operation.MOVERIGHT);
					count += 1;
					emptySpots[randint2][1] = emptySpots[randint2][1] - 1;
				}
			}
			
		}emptySpots = null;
		return newgrid;
		
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
		
		if (this == ps) {
			return true;
		}
		
		if (ps == null) {
			
			return false;
			
		}if (this.getClass()!= ps.getClass()) {
			
			return false;
		}
		
		
		// make the object a sure puzzle in order to test to see that the contents being compared in each grid are the same
		// length is checked to see that the dimensions are the same meaning that they are grids of same size
		SimplePuzzleState state = (SimplePuzzleState) ps;
		if (this == state) {
			return true;
		} 
		
		// checks to see if one or both of the puzzle states have not been populated yet and are still null
		if (this.grid == null && state.grid == null) {
			return true;
			
		}else if (this.grid == null && state.grid != null) {
			return false;
			
		}else if (this.grid != null && state.grid == null) {
			return false;
			
		}
		
		for (int i = 0; i < this.grid.length; i++) {
			for(int j = 0; j < this.grid.length; j++) {
				
				if(this.grid[i][j] != state.grid[i][j] || this.grid.length != state.grid.length) {
					
					return false;
					
				}
			}
		}
		return true;
	}
	
	@Override
	// create unique hash method based on what is given in the puzzle state
	public int hashCode() {
		
		int temp = (this.grid.length + ((this.grid.length + 3)/4));
		int hash = this.grid.length + (temp*temp);
		return hash;
	}
	
	
}
