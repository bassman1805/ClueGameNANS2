package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public class HumanPlayer extends Player{

	public HumanPlayer(String name, Color playerColor, int row, int column) {
		super(name, playerColor, row, column);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void selectTarget(Set<BoardCell> options) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Suggestion suggest(Card room) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void seeCards(Card retval) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unseeWeapons(ArrayList weapons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unseePeople(ArrayList people) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unseeRooms(ArrayList rooms) {
		// TODO Auto-generated method stub
		
	}

}
