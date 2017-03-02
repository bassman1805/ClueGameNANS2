package clueGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;
import java.util.HashMap;
import java.util.Scanner;
import com.sun.javafx.collections.MappingChange.Map;

public class Board {

	private int numRows;
	private int numColumns;
	public final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private HashMap<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
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

	}

	public void calcAdjacencies()
	{

	}

	public void calcTargets(BoardCell cell, int pathLength)
	{

	}

}
