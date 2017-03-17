package clueGame;

public class Solution {
	Card person;
	Card room;
	Card weapon;
	
	public Solution(Card person, Card room, Card weapon){
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public Card getPerson(){
		return person;
	}
	
	public Card getRoom(){
		return room;
	}
	
	public Card getWeapon(){
		return weapon;
	}
}
