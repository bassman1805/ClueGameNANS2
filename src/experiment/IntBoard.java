package experiment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.sun.javafx.collections.MappingChange.Map;

import test.IntBoardTest;

public class IntBoard {
	
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private BoardCell[][] grid;
	private int rows = 4;
	private int cols = 4;


	public IntBoard() {
		// TODO Auto-generated constructor stub
		
		grid = new BoardCell[rows][cols]; 
		
		for (int i = 0; i < rows; i++){
			for (int k = 0; k < cols; k++){
				grid[i][k] = new BoardCell(i,k);
				/*System.out.print("[" + grid[i][k].getRow() +","+ grid[i][k].getCol()  + "]");
				System.out.println();*/
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
		
		Set<BoardCell> adjList = new HashSet<BoardCell>();
		System.out.println("AdjList for " + cell.getRow()+","+ cell.getCol() + " populated with cells:");
		if (cell.getCol() - 1 > -1){ //if left piece is not out of bounds add to set
			adjList.add(grid[cell.getRow()][cell.getCol()-1]);
			System.out.println(" Left");
		}
		
	
		if ( cell.getCol() + 1 < cols){ //if right piece is not out of bounds add to set
			adjList.add(grid[cell.getRow()][cell.getCol()+1]);
			System.out.println(" Right");
		}
	
		if (cell.getRow() - 1 > -1){ //if upper piece is not out of bounds add to set
			adjList.add(grid[cell.getRow() - 1][cell.getCol()]);
			System.out.println(" Up");
		}
	
		if (cell.getRow() + 1 < rows){ //if down piece is not out of bounds add to set
			adjList.add(grid[cell.getRow() + 1][cell.getCol()]);
			System.out.println(" Down");
		}
		
		return adjList;
	}
	

	public BoardCell getCell (int a, int b){
		return grid[a][b];
	}

	
	
	
}
