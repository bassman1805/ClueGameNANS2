package clueGame;

import java.util.Set;

import com.sun.javafx.collections.MappingChange.Map;

public class Board {

	private int numRows;
	private int numColumns;
	public final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	public Board() {
		// TODO Auto-generated constructor stub
	}
	
	public Board getInstance()
	{
		return null;
	}
	
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
