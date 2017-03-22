package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, Color playerColor, int row, int column) {
		super(name, playerColor, row, column);
		
		unseenRooms = new ArrayList<Card>();
		unseenWeapons = new ArrayList<Card>();
		unseenPeople = new ArrayList<Card>();
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
	
	public void unseeRooms(ArrayList rooms){
		unseenRooms.addAll(rooms);
	}
	
	public void unseePeople(ArrayList people){
		unseenPeople.addAll(people);
	}
	
	public void unseeWeapons(ArrayList weapons){
		unseenWeapons.addAll(weapons);
	}

	@Override
	public void selectTarget(Set<BoardCell> options) {
		BoardCell target = this.choose(options);
		
		this.setLocation(target.getRow(), target.getCol());		
	}

	@Override
	public Suggestion suggest(Card room) {
		Random rand = new Random();
		int x, z;
		x = rand.nextInt(unseenPeople.size());
		z = rand.nextInt(unseenWeapons.size());
		return new Suggestion(unseenPeople.get(x), room, unseenWeapons.get(z));
	}

	@Override
	public void seeCards(Card retval) {
		if (retval.getType() == cardType.PERSON){
			unseenPeople.remove(retval);
		}

		if (retval.getType() == cardType.ROOM){
			unseenRooms.remove(retval);
		}

		if (retval.getType() == cardType.WEAPON){
			unseenWeapons.remove(retval);
		}
	}



}
