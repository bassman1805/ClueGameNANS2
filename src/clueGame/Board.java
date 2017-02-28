package clueGame;

import java.util.Set;

import com.sun.javafx.collections.MappingChange.Map;

public class Board {

	private int numRows;
	private int numColumns;
	public final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private String legendFile;
	
	
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
		this.legendFile = legendFile;
	}
	
	/////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\
	public Map<Character, String> getLegend()
	{
		return this.legend;
	}
	
	public int getNumRows()
	{
		return numRows;
	}
	
	public int getNumColumns()
	{
		return numColumns;
	}
	
	public BoardCell getCellAt(int row, int col)
	{
		return board[row][col];
	}
	
	/////////////////// HELPER FUNCTIONS \\\\\\\\\\\\\\\\\\\\\\\
	public void initialize()
	{
		
	}
	
	public void loadRoomConfig()
	{
		
	}
	
	public void loadBoardConfig()
	{
		
	}
	
	public void calcAdjacencies()
	{
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength)
	{
		
	}

}
