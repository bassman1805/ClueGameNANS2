package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public abstract class Player {
	String name;
	Color playerColor;
	int row;
	int column;
	char lastRoom;
	
	ArrayList<Card> hand;
	
	public Player(String name, Color playerColor, int row, int column){
		this.name = name;
		this.playerColor = playerColor;
		this.row = row;
		this.column = column;
		hand = new ArrayList<Card>();
		lastRoom = '*'; //no room has this char. It's essentially a "blank"
	}
	
	public void emptyHand(){
		hand.clear();
	}
	
	public void addCard(Card newCard){
		hand.add(newCard);
	}
	
	public int numCards() {
		return hand.size();
	}

	public ArrayList<Card> getCards() {
		return hand;
	}

	public Color getColor() {
		return playerColor;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return column;
	}

	public string getName() {
		return name;
	}

	public void setLocation(int i, int j) {
		this.row = i;
		this.column = j;
	}

	public abstract void selectTarget(Set<BoardCell> options);
	
	public void setLastRoom(char c) {
		this.lastRoom = c;
	}
	

}
