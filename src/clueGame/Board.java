package clueGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import com.sun.javafx.collections.MappingChange.Map;

public class Board {

	private int numRows = 22;
	private int numColumns = 24;
	public final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private HashMap<Character, String> legend;
	private HashMap<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private Set<BoardCell> visitedTargets;
	private String boardConfigFile;
	private String roomConfigFile;


	/////////////////// CONSTRUCTOR \\\\\\\\\\\\\\\\\\\\\\\
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/////////////////// SETTERS \\\\\\\\\\\\\\\\\\\\\\\
	public void setConfigFiles(String boardConfigFile, String legendFile)
	{
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = legendFile;
	}

	/////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\
	public HashMap<Character, String> getLegend()
	{
		return this.legend;
	}

	public int getNumRows()
	{
		//System.out.println(board.length);
		return numRows;
	}

	public int getNumColumns()
	{
		//System.out.println(board[0].length);
		return numColumns;
	}

	public BoardCell getCellAt(int row, int col)
	{
		return board[row][col];
	}

	public Set<BoardCell> getAdjList(int row, int col)
	{
		return adjMatrix.get(board[row][col]);
	}

	/////////////////// HELPER FUNCTIONS \\\\\\\\\\\\\\\\\\\\\\\
	public void initialize() 
	{
		loadRoomConfig();
		loadBoardConfig();
		calcAdjacencies();
	}

	public void loadRoomConfig()
	{
		// Create the legend map
		this.legend = new HashMap<Character, String>();

		try {
			FileReader reader = new FileReader(roomConfigFile);
			Scanner in = new Scanner(reader);

			while(in.hasNextLine())
			{
				String line = in.nextLine();
				String initialAsString = line.substring(0,1);
				Character initial = initialAsString.charAt(0);
				String name = line.substring(3, line.lastIndexOf(','));
				legend.put(initial, name);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(roomConfigFile + " was not found");
		}
	}

	public void loadBoardConfig()
	{
		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(reader);
			int row = 0;

			while(in.hasNext())
			{
				String line = in.next();
				String[] lineArr = line.split(",");
				for(int i = 0; i < lineArr.length; i++)
				{
					String initial = lineArr[i];
					BoardCell newCell = new BoardCell(row, i, initial.charAt(0), false);
					board[row][i] = newCell;

					if(initial.length() > 1 && initial.charAt(1) != 'N')
					{
						board[row][i].setIsDoorway();
						board[row][i].setDoorDirection(initial.charAt(1));
					}
				}

				row++;
				// Subtract 1 from the number of columns because it is zero indexed
				this.numColumns = lineArr.length;
			}
			// Subtract 1 from the number of rows because it is zero indexed
			this.numRows = row;

			/*for (int i = 0; i < 23; i++){
				for (int k = 0; k < 24; k++){
					System.out.print("[" + board[i][k].getRow() +","+ board[i][k].getCol()  + "]");

				}
				System.out.println();
			}*/
		}catch(FileNotFoundException e)
		{
			System.out.println(boardConfigFile + " was not found");
		}
	}

	public void calcAdjacencies()
	{
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();

		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numColumns; j++)
			{
				HashSet<BoardCell> adjList = new HashSet<BoardCell>();

				// Adding walkways to adjList if they are within the board
				if((j-1 > -1) && board[i][j-1].isWalkway() && !board[i][j].isDoorway() && !board[i][j].isRoom() && !board[i][j].isCloset())
				{
					adjList.add(board[i][j-1]);
				}

				if((j+1 < numColumns) && board[i][j+1].isWalkway() && !board[i][j].isDoorway() && !board[i][j].isRoom() && !board[i][j].isCloset())
				{
					adjList.add(board[i][j+1]);
				}

				if((i+1 < numRows) && board[i+1][j].isWalkway() && !board[i][j].isDoorway() && !board[i][j].isRoom() && !board[i][j].isCloset()) 
				{
					adjList.add(board[i+1][j]);
				}

				if((i-1 > -1) && board[i-1][j].isWalkway() && !board[i][j].isDoorway() && !board[i][j].isRoom() && !board[i][j].isCloset()) 
				{
					adjList.add(board[i-1][j]);
				}

				// Add doorway to adjList if it has the correct direction
				if((j-1 > -1)  &&  board[i][j-1].getDoorDirection() == DoorDirection.RIGHT)
				{
					adjList.add(board[i][j-1]);
				}
				if((j+1<numColumns) && board[i][j+1].getDoorDirection() == DoorDirection.LEFT)
				{
					adjList.add(board[i][j+1]);
				}
				if((i+1<numRows) && board[i+1][j].getDoorDirection() == DoorDirection.UP)
				{
					adjList.add(board[i+1][j]);
				}
				if((i-1>-1) && board[i-1][j].getDoorDirection() == DoorDirection.DOWN)
				{
					adjList.add(board[i-1][j]);
				}

				// Add adjList for doorways based on door direction
				if(board[i][j].isDoorway())
				{
					DoorDirection direction = board[i][j].getDoorDirection();
					if(direction == DoorDirection.LEFT)
					{
						adjList.add(board[i][j-1]);
					}
					else if(direction == DoorDirection.RIGHT)
					{
						adjList.add(board[i][j+1]);
					}
					else if(direction == DoorDirection.UP)
					{
						adjList.add(board[i-1][j]);
					}
					else if(direction == DoorDirection.DOWN)
					{
						adjList.add(board[i+1][j]);
					}
				}


				adjMatrix.put(board[i][j], adjList);

			}
		}
	}

	public void calcTargets(int row, int col, int pathLength)
	{
		
		visitedTargets = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visitedTargets.add(board[row][col]);
		calcTargetsRecursive(row, col, pathLength);
	}
	
	/* GIVEN PSUEDOCODE
	 * Parameters: thisCell and numSteps

	for each adjCell in adjacentCells 
	-- if already in visited list, skip rest of this

	-- add adjCell to visited list 
	-- if numSteps == 1, add adjCell to Targets
	-- else call findAllTargets with adjCell, numSteps-1

	-- remove adjCell from visited list
	 */
	public void calcTargetsRecursive(int row, int col, int pathLength)
	{
		Set<BoardCell> adjList = getAdjList(row,col);

		for(BoardCell bc : adjList)
		{
			if(!visitedTargets.contains(bc)){
				visitedTargets.add(bc);

				if(pathLength == 1 || bc.isDoorway())
				{
					targets.add(bc);
				}
				else
				{
					calcTargetsRecursive(bc.getRow(), bc.getCol(), pathLength - 1);
				}

				visitedTargets.remove(bc);
			}
		}
	}

	public Set<BoardCell> getTargets()
	{
		return targets;
	}

}
