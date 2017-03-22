package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
		Set<BoardCell> options;
		Set<BoardCell> targets = new HashSet<BoardCell>();
		
		//if no rooms in list, select randomly.
		board.calcTargets(4, 6, 1); //from this point, if you only move 1 space, you only get to other walkways		
		options = board.getTargets();		
		for(int i=0; i < 100; i++){
			p.setLocation(4,6); 
			p.selectTarget(options);
			targets.add(board.getBoardCell(p)); //After 100 tries, we should have ended up at each option at least once
		}
		assertEquals(targets.size(), 4);
		assertTrue(targets.contains(board.getCellAt(3,6)));
		assertTrue(targets.contains(board.getCellAt(5,6)));
		assertTrue(targets.contains(board.getCellAt(4,5)));
		assertTrue(targets.contains(board.getCellAt(4,7)));
		
		targets.clear();
		
		//if room in list that was not just visited, must select it
		board.calcTargets(4, 3, 1); //from this point, if you only move 1 space, you are in range of a room
		options = board.getTargets();
		for(int i=0; i < 100; i++){
			p.setLocation(4,3); //from this point, if you only move 1 space, you are in range of a room
			p.setLastRoom('*'); //no rooms have this char, so this will let the player enter any room
			p.selectTarget(options);
			targets.add(board.getBoardCell(p));
		}
		assertEquals(targets.size(), 1); //should get the same result every time
		assertTrue(targets.contains(board.getCellAt(3,3)));
		
		targets.clear();
		
		//if room just visited is in list, each target (including room) selected randomly
		for(int i=0; i < 100; i++){
			p.setLocation(4,3); //same place as last test
			p.setLastRoom('D'); //make sure this is the last visited room
			p.selectTarget(options);
			targets.add(board.getBoardCell(p));
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
		Player p0 = board.getPlayer(0);
		Player p1 = board.getPlayer(1);
		Player p2 = board.getPlayer(2);
		board.clearAllHands();
		board.dealStackedDeck();

		Card playerOne = new Card("Lydia", cardType.PERSON);
		Card locationTwo = new Card("Alik'r", cardType.ROOM);
		Card weaponTwo = new Card("Mehrune's Razor", cardType.WEAPON);

		Suggestion guess = new Suggestion(playerOne, locationTwo, weaponTwo);

		Set<Card> results = new HashSet<Card>();
		ArrayList<Card> resultArray = new ArrayList<Card>();
		
		//System.out.println(p1.getCards());
		//System.out.println(p2.getCards());

		//Testing hand that has no cards relating to suggestion
		for(int i = 0; i < 100; i++){
			results.add(p0.disproveSuggestion(guess));
		}
		assertEquals(results.size(), 1);
		assertTrue(results.contains(null));
		results.clear();

		//Testing hand that has only one card within suggestion
		for(int i = 0; i < 100; i++){
			results.add(p1.disproveSuggestion(guess));
		}
		for (Card c : results){
			resultArray.add(c);
		}
		assertEquals(resultArray.size(), 1);
		assertTrue(resultArray.get(0).equals(playerOne));
		results.clear();
		resultArray.clear();

		//Testing hand that has more than one card in suggestion
		for(int i = 0; i < 100; i++){
			results.add(p2.disproveSuggestion(guess));
		}
		for (Card c : results){
			resultArray.add(c);
		}
		assertEquals(resultArray.size(), 2);
		// need to test for alternate cases
		boolean opt1 = resultArray.get(0).equals(weaponTwo);
		opt1 = opt1 && resultArray.get(1).equals(locationTwo);
		
		boolean opt2 = resultArray.get(0).equals(locationTwo);
		opt2 = resultArray.get(1).equals(weaponTwo);
		
		assertTrue(opt1 || opt2);
		
	}
	
	@Test
	public void testSuggestionsBoard() {
		//Make sure that all players disprove suggestions properly
		//revealing only when they must, going through players in the proper order, etc.
		fail("Not yet implemented");
	}
}
