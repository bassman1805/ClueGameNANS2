package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, Color playerColor, int row, int column) {
		super(name, playerColor, row, column);
		// TODO Auto-generated constructor stub
	}
	
	private BoardCell choose(Set<BoardCell> options){
		
		//first, go through and try to enter a new room
		for(BoardCell cell : options){
			if(cell.isDoorway() && cell.getInitial() != this.lastRoom){
				return cell;
			}
		}
		
		//if that didn't work, then choose an option at random
		Random rand = new Random();
		int num = rand.nextInt(options.size());
		int i=0;
		
		for(BoardCell cell : options){
			if(i == num){
				return cell;
			}
			i++;
		}
		
		return null;
	}

	@Override
	public void selectTarget(Set<BoardCell> options) {
		BoardCell target = this.choose(options);
		
		this.setLocation(target.getRow(), target.getCol());		
	}
	
	

}
