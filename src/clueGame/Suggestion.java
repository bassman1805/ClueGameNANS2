package clueGame;

public class Suggestion{
	Card person;
	Card room;
	@Override
	public String toString() {
		return "Suggestion [person=" + person + ", room=" + room + ", weapon=" + weapon + "]";
	}

	Card weapon;
	
	public Suggestion(Card person, Card room, Card weapon){
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
