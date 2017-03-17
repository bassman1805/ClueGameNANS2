package clueGame;

public class Card {
	private String name;
	private cardType type;
	
	public Card(String name, cardType type){
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public cardType getType(){
		return type;
	}
}
