package clueGame;

public class BoardCell {
	
	private int row = 0;
	private int col = 0;
	private boolean isDoorway;
	private DoorDirection doorDirection;
	private char initial;
	
	public BoardCell() {
		// TODO Auto-generated constructor stub
		this.isDoorway = false;
		this.initial = '0';
		this.doorDirection = doorDirection.NONE;
	}
	
	public boolean isWalkway(){
		
		return false;
	}

	public boolean isRoom(){
		
		return false;
	}
	
	public boolean isDoorway(){
		
		return false;
	}
	
	public BoardCell(int row, int col) {
		super();
		this.row =row;
		this.col = col;
		
	}


	public int getRow() {
		return this.row;
	}


	public int getCol() {
		return this.col;
	}
	
	public DoorDirection getDoorDirection()
	{
		return this.doorDirection;
	}
	
	public char getInitial()
	{
		return this.initial;
	}
	
	

}
