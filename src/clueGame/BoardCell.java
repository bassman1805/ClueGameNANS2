package clueGame;

public class BoardCell {
	
	private int row = 0;
	private int col = 0;
	private char initial = 'I';
	public BoardCell() {
		// TODO Auto-generated constructor stub
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
		return row;
	}


	public int getCol() {
		return col;
	}
	
	

}
