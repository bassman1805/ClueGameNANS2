package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class IntBoard {
	
	private HashMap<BoardCell, Set<BoardCell>> adjMtx;
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
		calcAdjacencies();
	}
	
	
	private void calcAdjacencies()
	{
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		
		for(int i = 0; i < rows; i++) // Outer loop loops through each row of the board
		{
			for(int j = 0; j < cols; j++) // Inner loop loops through each column of the board
			{
				
				HashSet<BoardCell> adjList = new HashSet<BoardCell>();
				//System.out.println("AdjList for " + cell.getRow()+","+ cell.getCol() + " populated with cells:");
				if (j - 1 > -1){ //if left piece is not out of bounds add to set
					adjList.add(grid[i][j-1]);
					//System.out.println(" Left");
				}
				
			
				if ( j + 1 < cols){ //if right piece is not out of bounds add to set
					adjList.add(grid[i][j+1]);
					//System.out.println(" Right");
				}
			
				if (i - 1 > -1){ //if upper piece is not out of bounds add to set
					adjList.add(grid[i - 1][j]);
					//System.out.println(" Up");
				}
			
				if (i + 1 < rows){ //if down piece is not out of bounds add to set
					adjList.add(grid[i + 1][j]);
					//System.out.println(" Down");
				}
				
				// Add the adjList to the adjMtx
				adjMtx.put(grid[i][j], adjList);
			}
		}
	}

	// This function is necessary to initialize the visited and targets arrays to zero so they will not be reset every recursive call
	public void calcTargetsInitialize(BoardCell startCell, int pathLength)
	{
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		calcTargets(startCell, pathLength);
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
	public void calcTargets(BoardCell startCell, int pathLength)
	{
		Set<BoardCell> adj = getAdjList(startCell);
		
		for(BoardCell bc: adj){
			if(!visited.contains(bc)){
				visited.add(bc);
				if(pathLength == 1)
				{
					targets.add(bc);
				}
				else
				{
					calcTargets(bc, pathLength - 1);
				}
	
				visited.remove(bc);
			}
		}
	}
	
	public Set<BoardCell> getTargets()
	{
		return targets;
	}
	
	public Set<BoardCell> getAdjList( BoardCell cell)
	{
		return adjMtx.get(cell);
	}
	

	public BoardCell getCell (int a, int b){
		return grid[a][b];
	}

	
	
	
}
