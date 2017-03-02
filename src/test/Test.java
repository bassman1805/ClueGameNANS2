package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class Test {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;

	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayoutCSV.csv", "Legends.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	@org.junit.Test
	public void test() {
		fail("Not yet implemented");
	}
	
	
	// This test will make sure the Legend.txt file is loaded correctly
	@org.junit.Test
	public void testLegend()
	{
		// Get the map of the initial legend
		HashMap<Character, String> legend = board.getLegend(); 
		
		// Make sure the correct number of rooms is read
		assertEquals(LEGEND_SIZE, legend.size());
		
		// Test each key to make sure the keys are mapped to the correct rooms - tests every room/key combination
		assertEquals("Library", legend.get('L'));
		assertEquals("Brown H", legend.get('B'));
		assertEquals("Rec Center", legend.get('R'));
		assertEquals("Elm", legend.get('E'));
		assertEquals("Trads", legend.get('T'));
		assertEquals("Safeway", legend.get('S'));
		assertEquals("CTLM", legend.get('C'));
		assertEquals("Green Center", legend.get('G'));
		assertEquals("Mines", legend.get('M'));
		assertEquals("Xcloset", legend.get('X'));
		assertEquals("Ywalkway", legend.get('Y'));
	}
	
	// This test will ensure the correct number of rows and columns have been read
	@org.junit.Test
	public void testRowsAndColumns()
	{
		// Checks to see if the correct number of rows and colums are read in
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	// This test will ensure the doorways are behaving as expected
	@org.junit.Test
	public void testDoorways()
	{
		// Tests a right facing doorway
		// location 2,2 on the board is a door facing right
		clueGame.BoardCell room = board.getCellAt(2, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		// Tests a left facing doorway
		// location 10,6 is a door facing left
		room = board.getCellAt(10, 6);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		
		// Tests a upward facing doorway
		// location 12,16 is a doorway facing up
		room = board.getCellAt(12, 16);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		
		// Tests a down facing doorway
		// location 9,22 is a doorway facing down
		room = board.getCellAt(9, 22);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		
		// Tests a room is not a doorway
		// location 0,0 is a room cell
		room = board.getCellAt(0, 0);
		assertFalse(room.isDoorway());
		
		// Tests a walkway is not a doorway
		// location 6,0 is a walkway
		room = board.getCellAt(6, 0);
		assertFalse(room.isDoorway());
	}
	
	// This will test that the correct number of doors are loaded from the file
	@org.junit.Test
	public void testNumDoors()
	{
		int numDoors = 0;
		
		// Loops through the entire board counting the number of doors encountered
		for(int r = 0; r < NUM_ROWS; r++)
		{
			for(int c = 0; c < NUM_COLUMNS; c++)
			{
				BoardCell currCell = board.getCellAt(r, c);
				if(currCell.isDoorway())
				{
					numDoors++;
				}
			}
		}
		
		// Tests to see if the counted number of doors equals the expected number of doors (17)
		assertEquals(17, numDoors); 
	}
	
	// This will test that the correct initials are assigned to a room cell
	@org.junit.Test
	public void testRoomInitials()
	{
		// Test first cell in a room
		// Location 0,0 should have initial = 'B'
		assertEquals('B', board.getCellAt(0, 0).getInitial());
		// Location 9,6 should have initial = 'M'
		assertEquals('M', board.getCellAt(9, 6).getInitial());
		// Location 15,20 should have initial = 'S'
		assertEquals('S', board.getCellAt(15, 20).getInitial());
		
		// Test last cell in a room
		// Location 21,12 should have initial = 'G'
		assertEquals('G', board.getCellAt(21, 12).getInitial());
		// Location 6,9 should have initial = 'E'
		assertEquals('E', board.getCellAt(6, 9).getInitial());
		// Location 6,16 should have initial = 'L'
		assertEquals('L', board.getCellAt(6, 16).getInitial());
		
		// Test a walkway
		// Location 0,3 should have initial = 'W'
		assertEquals('W', board.getCellAt(0, 3).getInitial());
		
		// Test a closet
		// Location 1,4 should have initial = 'X'
		assertEquals('X', board.getCellAt(1, 4));
	}

}
