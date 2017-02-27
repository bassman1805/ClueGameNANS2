package experiment;

import java.util.Arrays;
import java.util.Set;

import com.sun.javafx.collections.MappingChange.Map;

public class IntBoard {
	
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private BoardCell[][] grid;



	public IntBoard(int rows, int cols) {
		// TODO Auto-generated constructor stub
		grid = new BoardCell[rows][cols]; 
		
		System.out.println("Board populated with cells:");
		
		for (int i = 0; i < rows; i++){
			for (int k = 0; k < cols; k++){
				grid[i][k] = new BoardCell(i,k);
				System.out.print("[" + grid[i][k].getRow() +","+ grid[i][k].getCol()  + "]");
				System.out.println();
			}
		}
		
	}
	
	private void calcAdjacencies()
	{
		
	}

	public void calcTargets(BoardCell startCel, int pathLength)
	{
		
	}
	
	public Set<BoardCell> getTargets()
	{
		
		return null;
	}
	
	public Set<BoardCell> getAdjList( BoardCell cell)
	{
		return null;
	}
	public BoardCell getCell (int a, int b){
		
		
		return grid[a][b];
	}

	
	
	
}
