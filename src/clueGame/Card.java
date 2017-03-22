package clueGame;

public class Card{
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
	
	@Override
	public boolean equals(Object o){
		// If the object is compared with itself then return true  
        if (o == this) {
            return true;
        }
 
        /* Check if o is an instance of Card or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Card)) {
            return false;
        }
        
        Card other = (Card) o;
        
		return this.name.equals(other.getName());
	}

	@Override
	public String toString() {
		return "Card [name=" + name + "]";
	}
}
