package test;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class AdjTests {
	// This private variable is used as the board for all the tests in this class. It is set up in the initialize call in the setUp function.
	private static Board board;

	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayoutCSV.csv", "Legends.txt");		
		board.initialize();
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

	// These tests ensure that the player cannot move around inside a room (ie. the adjacency list is 0)
	// On the spreadsheet, the cells tested are highlighted orange
	@Test
	public void testAdjInsideRoom()
	{
		// Test the corner of a room
		// Cell (0,21)
		Set<BoardCell> testList = board.getAdjList(0, 21);
		assertEquals(0, testList.size());

		// Test a cell in a room that has a walkway above it
		// Cell (14,6)
		testList = board.getAdjList(14, 6);
		assertEquals(0, testList.size());

		// Test a cell in a room that has a walkway underneath
		// Cell (6,8)
		testList = board.getAdjList(6, 8);
		assertEquals(0, testList.size());

		// Test a cell in a room that is in the middle of a room
		// Cell (18,1)
		testList = board.getAdjList(18, 1);
		assertEquals(0, testList.size());

		// Test a cell in a room that is beside a door
		// Cell (13,16)
		testList = board.getAdjList(13, 16);
		assertEquals(0, testList.size());

		// Test a cell in a room that is in the corner of a room
		// Cell (20,22)
		testList = board.getAdjList(20, 22);
		assertEquals(0, testList.size());
		
		// Test a cell that is in the upper left corner of the board
		// Cell (0,0)
		testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
	}

	// This will test that a the adj list for a door will only have the walkway cell next to the door opening
	// The adj list size should be 1
	// These tests are purple on the excel sheet
	@Test
	public void testAdjRoomExit()
	{
		// Tests right facing doorway
		// Door Cell (18,12)
		// Check that adj list contains walkway cell (18, 13)
		Set<BoardCell> testList = board.getAdjList(18, 12);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(18, 13)));

		// Tests left facing doorway
		// Door Cell (17,6)
		// Check that adj list contains walkway cell (17, 5)
		testList = board.getAdjList(17, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(17, 5)));

		// Tests up facing doorway
		// Door Cell (15, 21)
		// Check that adj list contains walkway cell (14, 21)
		testList = board.getAdjList(15, 21);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 21)));

		// Tests down facing doorway
		// Door Cell (9, 22)
		// Check that adj list contains walkway cell (10, 22)
		testList = board.getAdjList(9, 22);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(10, 22)));

		// Tests left facing doorway with a walkway below
		// Door Cell (6, 14)
		// Check that adj list contains only the walkway cell (6, 13)
		testList = board.getAdjList(6, 14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 13)));
	}

	// This tests the adj list at the entries of rooms
	// The cells are highlighted green on the excel spread sheet
	@Test
	public void testAdjDoorwayEntries()
	{
		// Tests beside a right facing doorway
		// Cell (14,3)
		// Target cells the doorway (14,2), and walkways (13,3), (15,3), (14,4)
		// Adj list should be size 4
		Set<BoardCell> testList = board.getAdjList(14, 3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 2)));
		assertTrue(testList.contains(board.getCellAt(13, 3)));
		assertTrue(testList.contains(board.getCellAt(15, 3)));
		assertTrue(testList.contains(board.getCellAt(14, 4)));

		// Tests beside a left facing doorway
		// Cell (6, 13)
		// Target cells the doorway (6,14), and walkways (7,13), (6,12)
		// Adj list should be size 3
		testList = board.getAdjList(6, 13);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 14)));
		assertTrue(testList.contains(board.getCellAt(7, 13)));
		assertTrue(testList.contains(board.getCellAt(6, 12)));

		// Tests beside an upward facing doorway
		// Cell (13,10)
		// Target cells the doorway (14,10), and walkways (13,9), (12,10), (13,11)
		// Adj list should be size 4
		testList = board.getAdjList(13, 10);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 10)));
		assertTrue(testList.contains(board.getCellAt(13, 9)));
		assertTrue(testList.contains(board.getCellAt(12, 10)));
		assertTrue(testList.contains(board.getCellAt(13, 11)));

		// Tests beside a downward facing doorway
		// Cell (10,22)
		// Target cells the doorway (9,22), and walkways (10,21), (11,22), (10,23)
		// Adj list should be size 4
		testList = board.getAdjList(10, 22);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(9, 22)));
		assertTrue(testList.contains(board.getCellAt(10, 21)));
		assertTrue(testList.contains(board.getCellAt(11, 22)));
		assertTrue(testList.contains(board.getCellAt(10, 23)));

	}
	
	// This tests a variety of different walkway scenarios
	// The tested cells are highlighted pink on the spreadsheet
	@Test
	public void adjListWalkways()
	{
		// Tests a cell at the top of the board with 3 adj walkway pieces
		// Cell (0,8)
		// Expected Targets: (0,7), (2,8), (1,9)
		// Adj list size should be 3
		Set<BoardCell> testList = board.getAdjList(0, 8);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(0, 7)));
		assertTrue(testList.contains(board.getCellAt(1, 8)));
		assertTrue(testList.contains(board.getCellAt(0, 9)));
		
		// Tests a cell with just one adj walkway piece
		// Cell (0,15)
		// Expected Targets: (0,16)
		// Adj list size should be 1
		testList = board.getAdjList(0, 15);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(0, 16)));
		
		// Tests a cell between two rooms, walkway above and below the cell
		// Cell (19,19)
		// Expected Targets: (18,19), (20,19)
		testList = board.getAdjList(19, 19);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(18, 19)));
		assertTrue(testList.contains(board.getCellAt(20, 19)));
		
		// Tests a cell surrounded by 4 walkways
		// Cell (8,18)
		// Expected Targets: (8,17), (7,18), (8,19), (9,18)
		// Adj list size should be 4
		testList = board.getAdjList(8, 18);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 17)));
		assertTrue(testList.contains(board.getCellAt(7, 18)));
		assertTrue(testList.contains(board.getCellAt(8, 19)));
		assertTrue(testList.contains(board.getCellAt(9, 18)));
		
		// Tests a cell at the bottom of the board
		// Cell (21,4)
		// Expected Targets: (21,3), (20,4), (21,5)
		// Adj list size should be 3
		testList = board.getAdjList(21, 4);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(21, 3)));
		assertTrue(testList.contains(board.getCellAt(20, 4)));
		assertTrue(testList.contains(board.getCellAt(21, 5)));
		
		// Tests a cell at the right side of the board with a room on the left
		// Cell (18,23)
		// Expected Targets: (17,23), (19,23)
		// Adj list size should be 2
		testList = board.getAdjList(18, 23);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(17, 23)));
		assertTrue(testList.contains(board.getCellAt(19, 23)));
		
		// Tests a walkway next to a door that is not the needed direction (actual door direction is left)
		// Cell (7,14)
		// Expected targets: (7,13), (8,14), (8,15)
		// Adj list size should be 3
		testList = board.getAdjList(7, 14);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(7, 13)));
		assertTrue(testList.contains(board.getCellAt(8, 14)));
		assertTrue(testList.contains(board.getCellAt(7, 15)));
	}
	
	// This will test targets 1 step away and at the edge of the board
	// Since each edge of the board has alredy been tested, this will only test two different locations
	// These are light blue on the spreadsheet
	@Test
	public void testOneStepTarget()
	{
		// Testing a cell at the top of the board not next to a door
		// Cell (0,3)
		// Expected to have 1 target: (1,3)
		board.calcTargets(0, 3, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 3)));
		
		// Testing a cell at the right edge of the board
		// Cell (0,23)
		// Expected to have 1 target: (1,23)
		board.calcTargets(0, 23, 1);
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 23)));
	}
	
	// This will test walkway targets with two steps. 
	// It will only contain walkway cells, no doors or rooms
	// These are highlighted light blue on the spreadsheet
	@Test
	public void testTwoStepTargets()
	{
		// Testing a walkway cell with two steps
		// Cell (0,23)
		// It is expected to have 1 target: (2,23)
		board.calcTargets(0, 23, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 23)));
		
		// Testing a walkway cell with two steps
		// Cell (15,19)
		// It is expected to have 3 targets: (17,19), (13,19), (14,20)
		board.calcTargets(15, 19, 2);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 19)));
		assertTrue(targets.contains(board.getCellAt(13, 19)));
		assertTrue(targets.contains(board.getCellAt(14, 20)));
	}
	
	// This tests walkway targets that are 4 steps away
	// These are highlighted light blue on the spreadsheet
	public void testFourStepTargets()
	{
		// Testing a walkway targets with four steps
		// Cell (0,23)
		// It is expected to have 1 target: (4,23)
		board.calcTargets(0, 23, 4);
		Set<BoardCell> targets = board.getTargets();
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 23)));
	}
	
	// Testing getting into a room where the room is exactly the number of steps away from the current cell.
	// The set of targets will contain doorways and walkways
	// These are highlighted light blue on the spreadsheet
	@Test
	public void testTargetsIntoRoomEqualSteps()
	{
		// One room is exactly 2 cells away
		// Cell (18,4)
		// Expected 7 targets: door (18,6), walkways (16,4), (20,4), (17,3), (19,3), (17,5), (19,5)
		board.calcTargets(18, 4, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 6)));
		assertTrue(targets.contains(board.getCellAt(16, 4)));
		assertTrue(targets.contains(board.getCellAt(20, 4)));
		assertTrue(targets.contains(board.getCellAt(17, 3)));
		assertTrue(targets.contains(board.getCellAt(19, 3)));
		assertTrue(targets.contains(board.getCellAt(17, 5)));
		assertTrue(targets.contains(board.getCellAt(19, 5)));
	}
	
	// Testing getting into a room when the room is less than the number of steps away from the current cell
	// The set of targets will contain walkways and doorways
	// These are highlighted in light blue on the spreadsheet
	@Test
	public void testTargetsIntoRoomLessSteps()
	{
		// Tests a cell right next to a doorway but with two steps
		// Cell (1,7)
		// Expected 3 targets: doorway (2,7), walkways (0,8), (1,9)
		board.calcTargets(1, 7, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 7)));
		assertTrue(targets.contains(board.getCellAt(0, 8)));
		assertTrue(targets.contains(board.getCellAt(1, 9)));
	}
	
	// Testing the targets calculated when getting out of a room
	// These are highlighted blue on the spreadsheet
	@Test 
	public void testTargetsLeavingRoom()
	{
		// Testing exiting a room with 3 steps
		// Cell (18,20)
		// Expected 2 targets: (16,19), (20,19)
		board.calcTargets(18, 20, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 19)));
		assertTrue(targets.contains(board.getCellAt(20, 19)));
		
		// Testing exiting a room with 5 steps where a target includes another room
		// Cell (21,18)
		// Expected 2 targets: doorway (18,20), walkway (17,19)
		board.calcTargets(21, 18, 5);
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 20)));
		assertTrue(targets.contains(board.getCellAt(17, 19)));
	}
}
