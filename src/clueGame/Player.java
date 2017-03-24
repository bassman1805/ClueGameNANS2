//Nick Attarian
package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	String name;
	Color playerColor;
	int row;
	int column;
	char lastRoom;
	
	ArrayList<Card> unseenRooms;
	public ArrayList<Card> unseenWeapons;
	ArrayList<Card> unseenPeople;
	
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

	public String getName() {
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

	public Card disproveSuggestion(Suggestion suggestion){
		Random rand = new Random();
		ArrayList<Card> contain = new ArrayList<Card>();
		
		for (Card c : hand){
			if (c.equals(suggestion.getPerson()))	contain.add(c);
			if (c.equals(suggestion.getRoom()))		contain.add(c);
			if (c.equals(suggestion.getWeapon()))	contain.add(c);
		}
		
		if(contain.size() == 0){
			return null;
		}else{
			int num = rand.nextInt(contain.size());
			return contain.get(num);
		}
	}
	
	public abstract void unseeWeapons(ArrayList weapons);
	public abstract void unseePeople(ArrayList people);
	public abstract void unseeRooms(ArrayList rooms);

	public abstract void seeCards(Card retval);

	public abstract Suggestion suggest(Card room);
}
