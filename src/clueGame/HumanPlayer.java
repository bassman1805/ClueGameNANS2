package clueGame;

import java.awt.Color;
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

}
