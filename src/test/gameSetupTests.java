package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;
import clueGame.cardType;

import java.awt.Color;

public class gameSetupTests {

	private static Board board;

	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("map.csv", "legend.txt", "weapons.txt", "players.txt");		
		board.initialize();
	}
	
	@Before
	public void setupCards(){
		board.dealCards();
	}
	
	@Test
	public void testPeopleCards() {
		//These test first that the size of the player legend is 6, and that they contain the
		//first and last in the list
		assertEquals(6, board.getPlayerCards().size());
		assertTrue(board.getPlayerCards().contains("Cicero"));
		assertTrue(board.getPlayerCards().contains("Agmaer"));
	}
	
	@Test
	public void testWeaponCards() {
		//These test first that the size of the weapon legend is 6, and that they contain the
		//first and last in the list
		assertEquals(6, board.getWeaponCards().size());
		assertTrue(board.getWeaponCards().contains("Dawnbreaker"));
		assertTrue(board.getWeaponCards().contains("Wabbajack"));
	}
	
	@Test
	public void testRoomCards() {
		//These test first that the size of the room card legend is 9, and that they contain the
		//first and last in the list
		assertEquals(9, board.getRoomCards().size());
		assertTrue(board.getRoomCards().contains("Helstrom"));
		assertTrue(board.getRoomCards().contains("Falinesti"));
	}
	
	//If the above 3 tests pass, all cards were loaded properly
	
	@Test
	public void testSolution(){
		// Test the solution deck to make sure it contains exactly one each of player, weapon, room
		Card room, person, weapon;
		person = board.getSolution().getPerson();
		room = board.getSolution().getRoom();
		weapon = board.getSolution().getWeapon();
		
		assertFalse(room == null);
		assertFalse(person == null);
		assertFalse(weapon == null);
		
		assertEquals(room.getType(), cardType.ROOM);
		assertEquals(person.getType(), cardType.PERSON);
		assertEquals(weapon.getType(), cardType.WEAPON);
	}
	
	@Test
	public void testPlayerHands(){
		//Check to make sure everyone's hand is about the same size (within one card)
		//Also check to make sure all cards are dealt and no card dealt twice
		ArrayList<Player> players = board.getPlayers();
		int numCards = 0;
		int handSize = players.get(0).numCards(); //How many cards player 0 has (In the event of non-even deals, player 0 will always have the most cards)
		System.out.println(handSize);
		Set<Card> dealtCards = new HashSet<Card>();
		for(Player p: players){
			for(Card c:p.getCards()){
				//Check that this card hasn't been dealt already
				assertFalse(dealtCards.contains(c));
				dealtCards.add(c);
				numCards++;
			}
			//make sure everyone has the right number of cards
			assertTrue(handSize - p.numCards() <= 1);
			
		}
		//make sure that all cards were dealt at some point
		//There's 21 cards total, minus 3 that are in the solution
		assertEquals(numCards, 18);
	}
	
	//If the above 2 tests pass, then the cards were dealt properly
	
	@Test
	public void testPlayerColors(){
		//make sure that all player colors are correct
		ArrayList<Player> players = board.getPlayers();
		
		
		assertEquals(players.get(0).getColor(), Color.MAGENTA);
		assertEquals(players.get(1).getColor(), Color.BLUE);
		assertEquals(players.get(2).getColor(), Color.GREEN);
		assertEquals(players.get(3).getColor(), Color.YELLOW);
		assertEquals(players.get(4).getColor(), Color.ORANGE);
		assertEquals(players.get(5).getColor(), Color.RED);
	}
	
	@Test
	public void testPlayerLocations(){
		//make sure all players spawn in the right place
		ArrayList<Player> players = board.getPlayers();
		
		assertEquals(players.get(0).getRow(), 4);
		assertEquals(players.get(0).getCol(), 4);
		
		assertEquals(players.get(1).getRow(), 9);
		assertEquals(players.get(1).getCol(), 6);
		
		assertEquals(players.get(2).getRow(), 4);
		assertEquals(players.get(2).getCol(), 11);
		
		assertEquals(players.get(3).getRow(), 15);
		assertEquals(players.get(3).getCol(), 8);
		
		assertEquals(players.get(4).getRow(), 10);
		assertEquals(players.get(4).getCol(), 20);
		
		assertEquals(players.get(5).getRow(), 1);
		assertEquals(players.get(5).getCol(), 7);
	}
	
	@Test
	public void testPlayerNames(){
		//make sure the player object got the right names
		//different from making sure the player cards were loaded correctly
		ArrayList<Player> players = board.getPlayers();
		
		assertEquals(players.get(0).getName(), "Cicero");
		assertEquals(players.get(1).getName(), "Erandur");
		assertEquals(players.get(2).getName(), "Lydia");
		assertEquals(players.get(3).getName(), "J'zargo");
		assertEquals(players.get(4).getName(), "Farkas");
		assertEquals(players.get(5).getName(), "Agmaer");
	}
	
	//If the above 3 tests pass, then players were loaded correctly

}
