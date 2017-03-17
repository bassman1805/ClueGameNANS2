package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	String name;
	Color playerColor;
	int row;
	int column;
	
	ArrayList<Card> hand;
	
	public Player(String name, Color playerColor, int row, int column){
		this.name = name;
		this.playerColor = playerColor;
		this.row = row;
		this.column = column;
		hand = new ArrayList<Card>();
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

	public Object getRow() {
		return row;
	}

	public Object getCol() {
		return column;
	}

	public Object getName() {
		return name;
	}

}
