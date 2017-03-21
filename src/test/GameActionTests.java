package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
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
	public void testPlayerTargets() {
		//Ensure that computer players select new spaces properly
		fail("Not yet implemented");
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
