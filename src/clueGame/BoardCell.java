package clueGame;

public class BoardCell {
	
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + ", initial=" + initial + "]";
	}

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
	
	public BoardCell(int row, int col, char initial, boolean isDoorway)
	{
		this.row = row;
		this.col = col;
		this.initial = initial;
		this.isDoorway = isDoorway;
	}
	
	public boolean isWalkway(){
		if(initial == 'W')
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isRoom(){
		if(initial != 'W' && initial != 'X' && !isDoorway)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isCloset()
	{
		if(initial == 'X')
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public boolean isDoorway(){
		
		return isDoorway;
	}
	
	public void setIsDoorway()
	{
		this.isDoorway = true;
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
	
	public void setDoorDirection(char dir)
	{
		if(dir == 'R')
		{
			this.doorDirection = DoorDirection.RIGHT;
		}
		else if (dir == 'L')
		{
			this.doorDirection = DoorDirection.LEFT;
		}
		else if (dir == 'U')
		{
			this.doorDirection = DoorDirection.UP;
		}
		else if (dir == 'D')
		{
			this.doorDirection = DoorDirection.DOWN;
		}
		else
		{
			this.doorDirection = DoorDirection.NONE;
		}
	}
	
	public char getInitial()
	{
		return this.initial;
	}
	
	

}
