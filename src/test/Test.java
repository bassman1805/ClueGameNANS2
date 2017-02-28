package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;

import clueGame.Board;

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
		board.setConfigFiles("ClueLayout.csv", "Legend.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	@org.junit.Test
	public void test() {
		fail("Not yet implemented");
	}
	
	
	// This test will make sure the Legend.txt file is loaded correctly
	@Test
	public void testLegend()
	{
		Map<Character, String> legend = board.getLegend(); // Need to add the getLegend() class
	}

}
