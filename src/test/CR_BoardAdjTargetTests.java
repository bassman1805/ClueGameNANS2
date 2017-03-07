package test;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class CR_BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt");		
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(5, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(15, 20);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(18, 9);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(18, 7);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(14, 12);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(10, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(10, 5)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(6, 14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 13)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(9, 22);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(10, 22)));
		//TEST DOORWAY UP
		testList = board.getAdjList(14, 10);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 10)));
		//TEST DOORWAY RIGHT, WHERE THERE'S A WALKWAY BELOW
		testList = board.getAdjList(2, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(2, 3)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(2, 3);
		assertTrue(testList.contains(board.getCellAt(2, 2)));
		assertTrue(testList.contains(board.getCellAt(1, 3)));
		assertTrue(testList.contains(board.getCellAt(3, 3)));
		assertEquals(3, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(10, 22);
		assertTrue(testList.contains(board.getCellAt(9, 22)));
		assertTrue(testList.contains(board.getCellAt(11, 22)));
		assertTrue(testList.contains(board.getCellAt(10, 21)));
		assertTrue(testList.contains(board.getCellAt(10, 23)));
		assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(7, 14);
		assertTrue(testList.contains(board.getCellAt(7, 15)));
		assertTrue(testList.contains(board.getCellAt(7, 13)));
		assertTrue(testList.contains(board.getCellAt(8, 14)));
		assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(13, 10);
		assertTrue(testList.contains(board.getCellAt(13, 9)));
		assertTrue(testList.contains(board.getCellAt(13, 11)));
		assertTrue(testList.contains(board.getCellAt(12, 10)));
		assertTrue(testList.contains(board.getCellAt(14, 10)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(0, 4);
		assertTrue(testList.contains(board.getCellAt(0, 5)));
		assertEquals(1, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(7, 0);
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(8, 0)));
		assertTrue(testList.contains(board.getCellAt(7, 1)));
		assertEquals(3, testList.size());

		// Test between two rooms, walkways up and down
		testList = board.getAdjList(19, 19);
		assertTrue(testList.contains(board.getCellAt(20, 19)));
		assertTrue(testList.contains(board.getCellAt(18, 19)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(8,18);
		assertTrue(testList.contains(board.getCellAt(8, 19)));
		assertTrue(testList.contains(board.getCellAt(8, 17)));
		assertTrue(testList.contains(board.getCellAt(7, 18)));
		assertTrue(testList.contains(board.getCellAt(9, 18)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(21, 5);
		assertTrue(testList.contains(board.getCellAt(21, 4)));
		assertTrue(testList.contains(board.getCellAt(20, 5)));
		assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(14, 22);
		assertTrue(testList.contains(board.getCellAt(14, 21)));
		assertTrue(testList.contains(board.getCellAt(13, 22)));
		assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(9, 23);
		assertTrue(testList.contains(board.getCellAt(10, 23)));
		assertTrue(testList.contains(board.getCellAt(8, 23)));
		assertEquals(2, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(15, 19, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 19)));
		assertTrue(targets.contains(board.getCellAt(16, 19)));	
		
		board.calcTargets(1, 7, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 7)));
		assertTrue(targets.contains(board.getCellAt(2, 7)));	
		assertTrue(targets.contains(board.getCellAt(1, 8)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(0, 3, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 3)));
		
		board.calcTargets(21, 5, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 5)));
		assertTrue(targets.contains(board.getCellAt(21, 4)));	
		assertTrue(targets.contains(board.getCellAt(21, 3)));			
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(0, 23, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 23)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(1, 7, 4);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 10)));
		assertTrue(targets.contains(board.getCellAt(1, 9)));	
		assertTrue(targets.contains(board.getCellAt(1, 11)));	
		assertTrue(targets.contains(board.getCellAt(2, 10)));	
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(21, 5, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(10, targets.size());
		//every other row going up from 21
		assertTrue(targets.contains(board.getCellAt(21, 5)));
		assertTrue(targets.contains(board.getCellAt(19, 5)));	
		assertTrue(targets.contains(board.getCellAt(17, 5)));	
		assertTrue(targets.contains(board.getCellAt(15, 5)));
		// two left + every other column going up from 21
		assertTrue(targets.contains(board.getCellAt(17, 3)));	
		assertTrue(targets.contains(board.getCellAt(19, 3)));	
		assertTrue(targets.contains(board.getCellAt(21, 3)));
		// one right + every other column up from 20 
		assertTrue(targets.contains(board.getCellAt(16, 4)));	
		assertTrue(targets.contains(board.getCellAt(18, 4)));	
		assertTrue(targets.contains(board.getCellAt(20, 4)));	
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 3 away
		board.calcTargets(0, 3, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		//down into the room 
		assertTrue(targets.contains(board.getCellAt(2, 2)));
		// directly down
		assertTrue(targets.contains(board.getCellAt(3, 3)));
	
	}
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(21, 5, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(12, targets.size());
		//free to go up wall on right side two cells on left side
		// directly up
		assertTrue(targets.contains(board.getCellAt(16, 5)));
		assertTrue(targets.contains(board.getCellAt(18, 5)));
		assertTrue(targets.contains(board.getCellAt(20, 5)));
		// column 4 
		assertTrue(targets.contains(board.getCellAt(21, 4)));
		assertTrue(targets.contains(board.getCellAt(19, 4)));
		assertTrue(targets.contains(board.getCellAt(17, 4)));
		// column 3
		assertTrue(targets.contains(board.getCellAt(20, 3)));
		assertTrue(targets.contains(board.getCellAt(18, 3)));
		// into the rooms
		assertTrue(targets.contains(board.getCellAt(17, 6)));
		assertTrue(targets.contains(board.getCellAt(18, 6)));
	
				
		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(18, 20, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 19)));
		// Take two steps
		board.calcTargets(18, 20, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 19)));
		assertTrue(targets.contains(board.getCellAt(19, 19)));
		
	}

}
