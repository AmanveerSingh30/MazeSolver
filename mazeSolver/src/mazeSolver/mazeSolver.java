package mazeSolver;
import java.util.Scanner;



public class mazeSolver {
	static Maze mainMaze;

	
	public static void main(String[] args) {
	
		Scanner console = new Scanner(System.in);
	
		
		System.out.println("What is the height of your maze?");
		int height = Integer.parseInt(console.nextLine());
		
		System.out.println("What is the width of your maze?");
		int width = Integer.parseInt(console.nextLine());
		
		String[][] myMaze = new String[height][width];
		System.out.println("Please enter your maze!\n\n"
				+ "	When entering your maze please remember that each cell is represented by 4 binary digits.\n"
				+ "	0 represents an open wall and 1 represents a closed wall. \n"
				+ " 		1st digit - top wall \n"
				+ "		2nd digit - left wall \n "
				+ "		3rd digit - bottom wall \n "
				+ "		4th digit - right wall \n"
				+ "	Please leave 1 space in between each binary cell and press enter at the end. \n\n"
				+ "	For example... \n"
				+ "1110 1010 1010 1000 1010 1011\n"
				+ "1010 1000 1001 0101 1100 1001\n"
				+ "1100 0011 0101 0110 0011 0101\n"
				+ "0101 1101 0110 1001 1110 0000\n"
				+ "0110 0011 1110 0010 1010 0011\n");
		
		
		System.out.println("Enter below this line.");
		
		int substringCounter = 0;
		for(int i=0; i<height; i++) {
			String line = console.nextLine();
			substringCounter = 0;
			for(int j=0; j<(width); j++) {
				myMaze[i][j] = line.substring(substringCounter, (substringCounter + 4));
				substringCounter = substringCounter + 5;;
			}
		}
		
		setup(myMaze);
		
		System.out.println("Enter 1 to find out if there are 2 or more gates \nEnter 2 for the solution to the maze");
		int line = Integer.parseInt(console.nextLine());
		
		if(line == 1) {
			enoughGate();
		}
		else if(line == 2) {
			
			System.out.println("Please enter the row for entrance gate");
			int rowENT = (Integer.parseInt(console.nextLine()))-1;
			
			System.out.println("Please enter the column for entrance gate");
			int columnsENT = (Integer.parseInt(console.nextLine()))-1;
			
			
			String mazeOutput = findPath(rowENT,columnsENT);
			System.out.println("The solution to your maze is (row,column):");
			System.out.println(mazeOutput);
		}
		else {
			System.out.println("Please enter from the given options of 1 and 2.");
		}
		
	
}//end of main
	
	
	
	
	/**
	 * This method sets up the maze using the given input argument
	 * 
	 * @param maze is a maze that is used to construct the mainMaze
	 */
	public static void setup(String[][] maze) {
		
		mainMaze = new Maze(maze);
	}

	
	
	
	/**
	 * This method returns true if the number of gates in mainMaze >= 2.
	 * 
	 * @return it returns true, if enough gate exists (at least 2), otherwise false.
	 */
	public static void enoughGate() {
		

		/*
		 * to solve the method the code splits the maze into four arrays with the top,
		 * left, bottom, and right sides in seperate arrays.
		 * 
		 * than 4 different methods are called to recursively check how many gates occur
		 * in each array(side of maze) and add the values together to determine the
		 * total number of gates in the maze.
		 */

		String[][] tempMaze = new String[mainMaze.getMaze().length][mainMaze.getMaze()[0].length]; // create a temporary
																									// maze to store the
																									// maze used so we
																									// dont repeatedly
																									// call the getter
																									// to use the maze
		tempMaze = mainMaze.getMaze();

		// split the four sides of the maze, the edges, into four different arrays
		String[] topRow = new String[tempMaze[0].length]; // the top and bottom row respectively
		String[] bottomRow = new String[tempMaze[0].length];
		String[] leftCol = new String[tempMaze.length]; // the left and right side respectively
		String[] rightCol = new String[tempMaze.length];

		// store the respective sides of the maze in the respective arrays
		for (int i = 0; i < topRow.length; i++)
			topRow[i] = tempMaze[0][i]; // top row of the maze, row stays same(first row, column increases
		for (int i = 0; i < bottomRow.length; i++)
			bottomRow[i] = tempMaze[tempMaze.length - 1][i]; // bottom row, row stays same (last row), column increases
		for (int i = 0; i < leftCol.length; i++)
			leftCol[i] = tempMaze[i][0]; // left column, column stays same (first column), row increases
		for (int i = 0; i < rightCol.length; i++)
			rightCol[i] = tempMaze[i][tempMaze[0].length - 1]; // right column, column stays same (last column), row
																// increases

		// call 4 methods to check how many gates are in the respective sides and add
		// together all the returns
		int totalGates = checkTopRow(topRow, 0) + checkBottomRow(bottomRow, 0) + checkLeftCol(leftCol, 0)
				+ checkRightCol(rightCol, 0);

		if (totalGates >= 2)
			System.out.println("true"); // if all the returns totaled is greater or equal than 2 that means there are
							// enough gates
		else
			System.out.println("false");

	}

	/**
	 * This method determines the number of gates in the top row of the maze which
	 * is represented by the string array of topRow
	 * 
	 * @param topRow is the String array that contains the set of binary digits that
	 *               make the top row of the maze
	 * @param i      is the index of the current cell that is being checked to have
	 *               a gate or not.
	 * @return it returns a integer of the number of gates that appear in the array
	 *         which represents the top side of the maze.
	 */
	private static int checkTopRow(String[] topRow, int i) {

		if (i == topRow.length)
			return 0; // base case to check if the cell to be checked is out of bounds
		else if (topRow[i].charAt(0) == '0')
			return 1 + checkTopRow(topRow, i + 1); // if there is a 0 at first digit of binary code that means the top
													// of cell is open and it is a gate
		else
			return checkTopRow(topRow, i + 1); // if not 0 than continue checking next cell in the row

	}

	/**
	 * This method determines the number of gates in the bottom row of the maze
	 * which is represented by the string array of bottomRow
	 * 
	 * @param bottomRow is the String array that contains the set of binary digits
	 *                  that make the bottom row of the maze
	 * @param i         is the index of the current cell that is being checked to
	 *                  have a gate or not.
	 * @return it returns a integer of the number of gates that appear in the array
	 *         which represents the bottom side of the maze.
	 */
	private static int checkBottomRow(String[] bottomRow, int i) {

		if (i == bottomRow.length)
			return 0; // base case to check if the cell to be checked is out of bounds
		else if (bottomRow[i].charAt(2) == '0')
			return 1 + checkBottomRow(bottomRow, i + 1); // if there is a 0 at third digit of binary code that means the
															// bottom of cell is open and it is a gate
		else
			return checkBottomRow(bottomRow, i + 1); // if not 0 than continue checking next cell in the row

	}

	/**
	 * This method determines the number of gates in the left column of the maze
	 * which is represented by the string array of leftCol
	 * 
	 * @param leftCol is the String array that contains the set of binary digits
	 *                that make the left Col (first col) of the maze
	 * @param i       is the index of the current cell that is being checked to have
	 *                a gate or not.
	 * @return it returns a integer of the number of gates that appear in the array
	 *         which represents the left side of the maze.
	 */
	private static int checkLeftCol(String[] leftCol, int i) {

		if (i == leftCol.length)
			return 0; // base case to check if the cell to be checked is out of bounds
		else if (leftCol[i].charAt(1) == '0')
			return 1 + checkLeftCol(leftCol, i + 1); // if there is a 0 at second digit of binary code that means the
														// left side of cell is open and it is a gate
		else
			return checkLeftCol(leftCol, i + 1); // if not 0 than continue checking next cell in the row

	}

	/**
	 * This method determines the number of gates in the right column of the maze
	 * which is represented by the string array of rightCol
	 * 
	 * @param rightCol is the String array that contains the set of binary digits
	 *                 that make the right Col (last col) of the maze
	 * @param i        is the index of the current cell that is being checked to
	 *                 have a gate or not.
	 * @return it returns a integer of the number of gates that appear in the array
	 *         which represents the right side of the maze.
	 */
	private static int checkRightCol(String[] rightCol, int i) {

		if (i == rightCol.length)
			return 0; // base case to check if the cell to be checked is out of bounds
		else if (rightCol[i].charAt(3) == '0')
			return 1 + checkRightCol(rightCol, i + 1); // if there is a 0 at last digit of binary code that means the
														// right side of cell is open and it is a gate
		else
			return checkRightCol(rightCol, i + 1); // if not 0 than continue checking next cell in the row

	}

	
	
	
	
	
	
	
	
	/**
	 * This method finds a path from the entrance gate to the exit gate.
	 * 
	 * @param row    is the index of the row, where the entrance is.
	 * @param column is the index of the column, where the entrance is.
	 * @return it returns a string that contains the path from the start to the end.
	 *         The return value should have a pattern like this (i,j)(k,l),... The
	 *         first pair of the output must show the entrance given as the input
	 *         parameter (i.e. (row,column) No whitespace is allowed in the output.
	 */
	public static String findPath(int row, int column) {
		

		/*
		 * to find the path the code creates a visited 2d boolean array to track if the
		 * cell has already been visited.
		 * 
		 * the first cell the code is done manually as it is linked with a gate so if
		 * included in the main recursive code it will be mistaken for the end gate
		 * 
		 * the method of traverse is called after the first cell code to find the path
		 * and it returns a string which are the coordinates of the path to follow and
		 * the first cell coordinates are added to the beggining of that string and
		 * returned as the final answer
		 */

		String[][] tempMaze = new String[mainMaze.getMaze().length][mainMaze.getMaze()[0].length]; // create a temporary
		tempMaze = mainMaze.getMaze();																// maze to store the
																									// maze used so we
																									// dont repeatedly
																									// call the getter
																									// to use the maze
		

		boolean[][] visited = new boolean[tempMaze.length][tempMaze[0].length]; // an array to identify if we have
																				// visited parallel cell in the dogMaze
																				// maze
		for (int i = 0; i < visited.length; i++) // at first store false in all cells and convert to true 
			for (int j = 0; j < visited[i].length; j++)
				visited[i][j] = false;

		// for the first starter cell
		visited[row][column] = true; // sets the visited boolean to true for the indexes to convey that the cell has
										// been visited
		String returnedStatement = "";

		if (tempMaze[row][column].charAt(0) == '0') // checks if the top of the cell is open, if open path can travel in
													// that direction
		{
			if (cellInBound(tempMaze, row - 1, column)) // checks if the cell is a valid cell in the array
			{
				returnedStatement = traverse(tempMaze, row - 1, column, visited); // calls traverse with the next cell
																					// in path so traverse can find the
																					// next cell of the path and so on

				if ((returnedStatement == "visited") || (returnedStatement == "deadend")) // if the returned statement
																						  // from traverse is
				{ 											// visited (means the next prompted cell is already visited)
					returnedStatement = ""; // or deadend (means there is no further cell the path can move forward on)
											
				} //so if true do nothing because direction is wrong and move onto check the next sides pf the cell
				else // otherwise ordered coordinates for the path are returned and add current index to the path
				{
					return "(" + row + "," + column + ")" + returnedStatement; // the updated ordered path is returned
																				// to the cell before to be added to
																				// path
				}
			}
		}

		if (tempMaze[row][column].charAt(1) == '0') // checks if the left side of the cell is open
		{
			if (cellInBound(tempMaze, row, column - 1)) {
				returnedStatement = traverse(tempMaze, row, column - 1, visited);

				if ((returnedStatement == "visited") || (returnedStatement == "deadend")) {
					returnedStatement = "";
				} else {
					return "(" + row + "," + column + ")" + returnedStatement;
				}
			}
		}

		if (tempMaze[row][column].charAt(2) == '0') // checks if the bottom of the cell is open
		{
			if (cellInBound(tempMaze, row + 1, column)) {
				returnedStatement = traverse(tempMaze, row + 1, column, visited);

				if ((returnedStatement == "visited") || (returnedStatement == "deadend")) {
					returnedStatement = "";
				} else {
					return "(" + row + "," + column + ")" + returnedStatement;
				}
			}
		}

		if (tempMaze[row][column].charAt(3) == '0') // checks if the right side of the cell is open
		{
			if (cellInBound(tempMaze, row, column + 1)) {
				returnedStatement = traverse(tempMaze, row, column + 1, visited);

				if ((returnedStatement == "visited") || (returnedStatement == "deadend")) {
					returnedStatement = "";
				} else {
					return "(" + row + "," + column + ")" + returnedStatement;
				}
			}
		}

		return "maze cannot be solved"; // if none of the directions are valid at the started cell there is no solution
	}// end of findPath

	
	
	
	
	/**
	 * This method finds the path to the second gate and when it finds the second
	 * gate by recursively calling itself, it returns the coordinates of the cells
	 * it took.
	 * 
	 * @param tempMaze is the 2d string array of the main maze with the binary
	 *                 digits
	 * @param row      is the index of the row of the current cell.
	 * @param column   is the index of the colunm of the current cell.
	 * @param visited  is the 2d boolean array that tracks if the cell has been
	 *                 visited or not
	 * @return it returns a String containing the path of ordered coordinates to the
	 *         second gate (exit of maze. The string does not contain the first
	 *         given coordinate.
	 */
	private static String traverse(String[][] tempMaze, int row, int column, boolean[][] visited) {

		String returnedStatement = "";

		if (cellInBound(tempMaze, row, column)) // check if the cell is a valid cell in the array
		{
			if (visited[row][column])
				return "visited"; // check if cell has already been visited, if true than return visited to indicate
			else
				visited = cellVisited(visited, row, column); // if not, set the cell in visited array to true
		} else
			return ""; // if not inbound but because there was a open gate leading to the cell that
						// means it is the end gate
						// the base case "" to which the coordinates are added to as recursive pops off
						// the stack

		if (tempMaze[row][column].charAt(0) == '0') // check if the top side is open(0) or closed(1)
		{

			returnedStatement = traverse(tempMaze, row - 1, column, visited); // if its open, call the cell in that
																				// direction and store the return from
																				// the method
																				// calls traverse with the next cell in
																				// path so traverse can find the next
																				// cell of the path and so on

			if ((returnedStatement == "visited") || (returnedStatement == "deadend")) // if the returned statement form
																						// traverse is
			{ 												// visited (means the next prompted cell is already visited)
				returnedStatement = ""; // or deadend (means no directions prove a valid path so wrong way), 
										
			} 							// so if true do nothing because direction is wrong and move onto check the next
			else // otherwise ordered coordinates for the path are returned and add current index to the path
			{
				return "(" + row + "," + column + ")" + returnedStatement; // the updated ordered path is returned to
																			// the cell before to be added to path
			}
		}

		if (tempMaze[row][column].charAt(1) == '0') // check if the left side is open(0) or closed(1)
		{
			// this means we call traverse and up
			returnedStatement = traverse(tempMaze, row, column - 1, visited);

			if ((returnedStatement == "visited") || (returnedStatement == "deadend")) {
				returnedStatement = "";
			} else {
				return "(" + row + "," + column + ")" + returnedStatement;
			}
		}

		if (tempMaze[row][column].charAt(2) == '0') // check if the bottom side is open(0) or closed(1)
		{
			// this means we call traverse and up
			returnedStatement = traverse(tempMaze, row + 1, column, visited);

			if ((returnedStatement == "visited") || (returnedStatement == "deadend")) {
				returnedStatement = "";
			} else {
				return "(" + row + "," + column + ")" + returnedStatement;
			}
		}

		if (tempMaze[row][column].charAt(3) == '0') // check if the right side is open(0) or closed(1)
		{
			// this means we call traverse and up
			returnedStatement = traverse(tempMaze, row, column + 1, visited);

			if ((returnedStatement == "visited") || (returnedStatement == "deadend")) {
				returnedStatement = "";
			} else {
				return "(" + row + "," + column + ")" + returnedStatement;
			}
		}

		return "deadend"; // none of the directions return a valid path so that means deadend

	}// traverse end

	
	
	/**
	 * This method sets the current cell to true in the 2d boolean visited array so
	 * the cell is not checked more than once
	 * 
	 * @param visited is the 2d boolean array that tracks if the cell has been
	 *                visited or not
	 * @param row     is the index of the row of the current cell.
	 * @param column  is the index of the colunm of the current cell.
	 * @return it returns the updated 2d boolean array of visited with the required
	 *         cell set to true (as in visited)
	 */
	private static boolean[][] cellVisited(boolean[][] visited, int row, int column) {
		visited[row][column] = true; // set the cell to visited
		return visited;
	}

	/**
	 * This method checks if the cell is in bound of the maze
	 * 
	 * @param tempMaze is the maze and its dimensions are used
	 * @param row      is the index of the row of the current cell.
	 * @param column   is the index of the column of the current cell.
	 * @return it returns a boolean depdning on if the cell in in bound the maze or
	 *         not
	 */
	private static boolean cellInBound(String[][] tempMaze, int row, int column) {
		if (row >= 0 && row < tempMaze.length && column >= 0 && column < tempMaze[0].length)
			return true; // return true is cell is within the dimensions of the maze
		else
			return false;
	}

} // end of class mazeSolver










class Maze {

	private String[][] maze;

	/**
	 * This constructor makes the maze.
	 * 
	 * @param maze is a 2D array that contains information on how each cell of the
	 *             array looks like.
	 */
	public Maze(String[][] maze) {
		/*
		 * complete the constructor so that the maze is a deep copy of the input
		 * parameter.
		 */
		this.maze = new String[maze.length][maze[0].length]; // init the dimensions for the maze
		for (int i = 0; i < maze.length; i++) // Create a deep copy of input parameter
			for (int j = 0; j < maze[i].length; j++)
				this.maze[i][j] = maze[i][j];
	}

	/**
	 * This accessor (getter) method returns a 2D array that represents the maze
	 * 
	 * @return it returns a reference to the maze
	 */
	public String[][] getMaze() {
		

		String[][] mazeClone = new String[this.maze.length][this.maze[0].length]; // created a clone for the main maze
																					// and returned that clone
		for (int i = 0; i < this.maze.length; i++)
			for (int j = 0; j < this.maze[i].length; j++)
				mazeClone[i][j] = this.maze[i][j];

		return mazeClone;
	}

	@Override
	public String toString() {
		
		String output = "";

		for (int i = 0; i < this.maze.length; i++) // for loop to loop through the rows
		{
			output += "["; // opening bracket for each row

			for (int j = 0; j < this.maze[i].length; j++) // for loop to loop through each column of the row
			{
				if (j == (this.maze[i].length - 1))
					output += this.maze[i][j]; // if the last number in row than add to output without space after
				else
					output += this.maze[i][j] + " "; // otherwise not the last number in row, than add space after it
			}
			output += "]" + "\n"; // add closing bracket to the row and than create new line
		}

		return output; // return the string containing the maze output
	}

}// end of class Maze

