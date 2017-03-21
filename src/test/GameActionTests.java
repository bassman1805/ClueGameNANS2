package test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.Player;
import clueGame.Suggestion;
import clueGame.cardType;

public class GameActionTests {
	
	private static Board board;

	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("map.csv", "legend.txt", "weapons.txt", "players.txt");		
		board.initialize();
	}
	
	@Test
	public void testPlayerTargets() {
		//Ensure that computer players select new spaces properly
		
		Player p = Board.getPlayer(1);
		Set<BoardCell> targets = new HashSet<BoardCell>();
		
		//if no rooms in list, select randomly
		for(int i=0; i < 100; i++){
			p.setLocation(4,6); //from this point, if you only move 1 space, you only get to other walkways
			p.selectTarget(1);
			targets.add(p.getBoardCell()); //After 100 tries, we will probably have ended up at each option at least once
		}
		assertEquals(targets.size(), 4);
		assertTrue(targets.contains(board.getCellAt(3,6)));
		assertTrue(targets.contains(board.getCellAt(5,6)));
		assertTrue(targets.contains(board.getCellAt(4,5)));
		assertTrue(targets.contains(board.getCellAt(4,7)));
		
		
		//if room in list that was not just visited, must select it
		for(int i=0; i < 100; i++){
			p.setLocation(4,3); //from this point, if you only move 1 space, you are in range of a room
			p.setLastRoom('*'); //no rooms have this char, so this will let the player enter any room
			p.selectTarget(1);
			targets.add(p.getBoardCell());
		}
		assertEquals(targets.size(), 1); //should get the same result every time
		assertTrue(targets.contains(board.getCellAt(3,3)));
		
		
		//if room just visited is in list, each target (including room) selected randomly
		for(int i=0; i < 100; i++){
			p.setLocation(4,3); //from this point, if you only move 1 space, you are in range of a room
			p.setLastRoom('D'); //make sure this is the last visited room
			p.selectTarget(1);
			targets.add(p.getBoardCell());
		}
		assertEquals(targets.size(), 4); //should have 4 results
		assertTrue(targets.contains(board.getCellAt(5,3)));
		assertTrue(targets.contains(board.getCellAt(3,3)));
		assertTrue(targets.contains(board.getCellAt(4,2)));
		assertTrue(targets.contains(board.getCellAt(4,4)));
	}
	
	@Test
	public void testAccusations() {
		// need to recreate the cards for the solution/accusation
		Card playerSolution = new Card("Cicero", cardType.PERSON);
		Card playerFalse = new Card("Lydia", cardType.PERSON);
		
		Card locationSolution = new Card("Alinor", cardType.ROOM);
		Card locationFalse = new Card("Torval", cardType.ROOM);
		
		Card weaponSolution = new Card("Sanguine Rose", cardType.WEAPON);
		Card weaponFalse = new Card("Dawnbreaker", cardType.WEAPON);
		
		
		Suggestion solution = new Suggestion(playerSolution, locationSolution, weaponSolution);
		Suggestion falseName = new Suggestion(playerFalse, locationSolution, weaponSolution);
		Suggestion falseWeapon = new Suggestion(playerSolution, locationSolution, weaponFalse);
		Suggestion falseLocation = new Suggestion(playerSolution, locationFalse, weaponSolution);
		board.setSolution(solution);
		
		assertTrue(board.makeAccusation(solution));
		assertFalse(board.makeAccusation(falseName));
		assertFalse(board.makeAccusation(falseWeapon));
		assertFalse(board.makeAccusation(falseLocation));
		
	}
	
	@Test
	public void testSuggestionsCreation() {
		//make sure CPU players make proper suggestions
		fail("Not yet implemented");
	}
	
	@Test
	public void testSuggestionDisprovePlayer() {
		//Make sure suggestions are disproved properly by individual CPU players
		fail("Not yet implemented");
	}
	
	@Test
	public void testSuggestionsBoard() {
		//Make sure that all players disprove suggestions properly
		//revealing only when they must, going through players in the proper order, etc.
		fail("Not yet implemented");
	}

}
