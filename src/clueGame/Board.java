
package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

//for random choice
import java.util.Random;

public class Board {

	private int numRows = 22;
	private int numColumns = 24;
	public final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private HashMap<Character, String> legend;
	
	private HashMap<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private Set<BoardCell> visitedTargets;
	
	private String boardConfigFile;
	private String roomConfigFile;
	private String weaponConfigFile;
	private String personConfigFile;
	
	private ArrayList<Player> players;
	
	private ArrayList<String> playerCards;
	private ArrayList<String> weaponCards;
	private ArrayList<String> roomCards;
	private Stack<Card> deck;
	
	private Suggestion solution;


	/////////////////// CONSTRUCTOR \\\\\\\\\\\\\\\\\\\\\\\
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {
		playerCards = new ArrayList<String>();
		weaponCards = new ArrayList<String>();
		roomCards = new ArrayList<String>();
		players = new ArrayList<Player>();
		deck = new Stack<Card>();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/////////////////// SETTERS \\\\\\\\\\\\\\\\\\\\\\\
	public void setConfigFiles(String boardConfigFile, String legendFile)
	{
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = legendFile;
	}
	
	public void setConfigFiles(String boardConfigFile, String legendFile, String weaponFile, String personFile)
	{
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = legendFile;
		this.weaponConfigFile = weaponFile;
		this.personConfigFile = personFile;
	}

	/////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\
	public HashMap<Character, String> getLegend()
	{
		return this.legend;
	}

	public int getNumRows()
	{
		return numRows;
	}

	public int getNumColumns()
	{
		return numColumns;
	}

	public BoardCell getCellAt(int row, int col)
	{
		return board[row][col];
	}

	public Set<BoardCell> getAdjList(int row, int col)
	{
		return adjMatrix.get(board[row][col]);
	}

	/////////////////// HELPER FUNCTIONS \\\\\\\\\\\\\\\\\\\\\\\
	public void initialize() 
	{
		loadRoomConfig();
		loadBoardConfig();
		loadPersonConfig();
		loadWeaponConfig();
		calcAdjacencies();
	}
	
	public void loadRoomConfig()
	{
		// Create the legend map
		this.legend = new HashMap<Character, String>();

		try {
			FileReader reader = new FileReader(roomConfigFile);
			Scanner in = new Scanner(reader);

			String line;
			String[] splitLine;
			String type, name;
			while(in.hasNextLine())
			{
				line = in.nextLine();
				splitLine = line.split(", ");
				Character initial = splitLine[0].charAt(0);
				name = splitLine[1];
				type = splitLine[2];
				
				legend.put(initial, name);
				if (type.equalsIgnoreCase("card")){
					roomCards.add(name);
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println(roomConfigFile + " was not found");
		}
	}

	public void loadBoardConfig()
	{
		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(reader);
			int row = 0;

			while(in.hasNext())
			{
				String line = in.next();
				String[] lineArr = line.split(",");
				for(int i = 0; i < lineArr.length; i++)
				{
					String initial = lineArr[i];
					BoardCell newCell = new BoardCell(row, i, initial.charAt(0), false);
					board[row][i] = newCell;

					if(initial.length() > 1 && initial.charAt(1) != 'N')
					{
						board[row][i].setIsDoorway();
						board[row][i].setDoorDirection(initial.charAt(1));
					}
				}

				row++;
				// Subtract 1 from the number of columns because it is zero indexed
				this.numColumns = lineArr.length;
			}
			// Subtract 1 from the number of rows because it is zero indexed
			this.numRows = row;

			/*for (int i = 0; i < 23; i++){
				for (int k = 0; k < 24; k++){
					System.out.print("[" + board[i][k].getRow() +","+ board[i][k].getCol()  + "]");

				}
				System.out.println();
			}*/
		}catch(FileNotFoundException e)
		{
			System.out.println(boardConfigFile + " was not found");
		}
	}
	
	public void loadWeaponConfig(){
		//load in the weapon file
		try {
			FileReader reader = new FileReader(weaponConfigFile);
			Scanner in = new Scanner(reader);
			//read weapons into arraylist
			while(in.hasNextLine())
			{
				String line = in.nextLine();
				weaponCards.add(line);
			}

		} catch (FileNotFoundException e) {
			System.out.println(weaponConfigFile + " was not found");
		}
	}
	
	//convert a String to Color
	private Color convertColor(String strColor) {
	    Color color; 
	    try {     
	        // We can use reflection to convert the string to a color
	        Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
	        color = (Color)field.get(null); 
	    } catch (Exception e) {  
	        color = null; // Not defined  
	    }
	    return color;
	}

	
	public void loadPersonConfig(){
		try {
			FileReader reader = new FileReader(personConfigFile);
			Scanner in = new Scanner(reader);

			String line;
			String[] splitLine;
			String name;
			Field field;
			Color color;
			int row, column;
			Player p;
			while(in.hasNextLine())
			{
				line = in.nextLine();
				splitLine = line.split(", ");
				name = splitLine[0];
				
				color = convertColor(splitLine[1]);
				
				row = Integer.parseInt(splitLine[2]);
				column = Integer.parseInt(splitLine[3]);

				playerCards.add(name);
				p = new Player(name, color, row, column);
				players.add(p);
			}

		} catch (FileNotFoundException e) {
			System.out.println(personConfigFile + " was not found");
		}
	}

	public void calcAdjacencies()
	{
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();

		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numColumns; j++)
			{
				HashSet<BoardCell> adjList = new HashSet<BoardCell>();

				// Adding walkways to adjList if they are within the board
				if((j-1 > -1) && board[i][j-1].isWalkway() && !board[i][j].isDoorway() && !board[i][j].isRoom() && !board[i][j].isCloset())
				{
					adjList.add(board[i][j-1]);
				}

				if((j+1 < numColumns) && board[i][j+1].isWalkway() && !board[i][j].isDoorway() && !board[i][j].isRoom() && !board[i][j].isCloset())
				{
					adjList.add(board[i][j+1]);
				}

				if((i+1 < numRows) && board[i+1][j].isWalkway() && !board[i][j].isDoorway() && !board[i][j].isRoom() && !board[i][j].isCloset()) 
				{
					adjList.add(board[i+1][j]);
				}

				if((i-1 > -1) && board[i-1][j].isWalkway() && !board[i][j].isDoorway() && !board[i][j].isRoom() && !board[i][j].isCloset()) 
				{
					adjList.add(board[i-1][j]);
				}

				// Add doorway to adjList if it has the correct direction
				if((j-1 > -1)  &&  board[i][j-1].getDoorDirection() == DoorDirection.RIGHT)
				{
					adjList.add(board[i][j-1]);
				}
				if((j+1<numColumns) && board[i][j+1].getDoorDirection() == DoorDirection.LEFT)
				{
					adjList.add(board[i][j+1]);
				}
				if((i+1<numRows) && board[i+1][j].getDoorDirection() == DoorDirection.UP)
				{
					adjList.add(board[i+1][j]);
				}
				if((i-1>-1) && board[i-1][j].getDoorDirection() == DoorDirection.DOWN)
				{
					adjList.add(board[i-1][j]);
				}

				// Add adjList for doorways based on door direction
				if(board[i][j].isDoorway())
				{
					DoorDirection direction = board[i][j].getDoorDirection();
					if(direction == DoorDirection.LEFT)
					{
						adjList.add(board[i][j-1]);
					}
					else if(direction == DoorDirection.RIGHT)
					{
						adjList.add(board[i][j+1]);
					}
					else if(direction == DoorDirection.UP)
					{
						adjList.add(board[i-1][j]);
					}
					else if(direction == DoorDirection.DOWN)
					{
						adjList.add(board[i+1][j]);
					}
				}


				adjMatrix.put(board[i][j], adjList);

			}
		}
	}
	
	private void shuffle(){
		Stack<Card> half1 = new Stack<Card>();
		Stack<Card> half2 = new Stack<Card>();
		
		//Split the deck into 2 half-decks
		Card tempCard;
		for(int i=0; i < deck.size()/2; i++){
			tempCard = deck.pop();
			half1.push(tempCard);
		}
		for(int i=0; i < deck.size(); i++){
			tempCard = deck.pop();
			half2.push(tempCard);
		}
		
		//now, riffle shuffle the half-decks by alternately adding a random (1-3)
		//number of cards back to the main deck
		Random rand = new Random();
		int num;
		while(!(half1.isEmpty() && half2.isEmpty())){
			num = rand.nextInt(3)+1;
			if(num > half1.size()){
				num = half1.size();
			}
			for(int i=0; i < num; i++){
				tempCard = half1.pop();
				deck.push(tempCard);
			}
			
			num = rand.nextInt(3)+1;
			if(num > half2.size()){
				num = half2.size();
			}
			for(int i=0; i < num; i++){
				tempCard = half2.pop();
				deck.push(tempCard);
			}
		}
	}
	
	// this has been altered. look back over
	public void dealCards() {
		//make sure every player has an empty hand to start out with
		//also empty the deck, if it has anything for some reason
		for(Player p:players) p.emptyHand();
		deck.clear();
		
		//declare cards and decks
		Card c;
		ArrayList<Card> roomDeck = new ArrayList<Card>();
		ArrayList<Card> personDeck = new ArrayList<Card>();
		ArrayList<Card> weaponDeck = new ArrayList<Card>();
		
		//populate mini-decks
		for (String s : roomCards){
			c = new Card(s, cardType.ROOM);
			roomDeck.add(c);
		}
		for (String s : playerCards){
			c = new Card(s, cardType.PERSON);
			personDeck.add(c);
		}
		for (String s : weaponCards){
			c = new Card(s, cardType.WEAPON);
			weaponDeck.add(c);
		}
		
		//pick a solution (using random number)
		Random rand = new Random();
		int i;
		Card tempRoom, tempPerson, tempWeapon;
		
		i = rand.nextInt(roomDeck.size());
		tempRoom = roomDeck.get(i);
		roomDeck.remove(i);
		
		i = rand.nextInt(personDeck.size());
		tempPerson = personDeck.get(i);
		personDeck.remove(i);
		
		i = rand.nextInt(weaponDeck.size());
		tempWeapon = weaponDeck.get(i);
		weaponDeck.remove(i);
		
		solution = new Suggestion(tempPerson, tempRoom, tempWeapon);
		
		//combine mini-decks into big deck
		for(Card card : roomDeck){
			deck.push(card);
		}
		for(Card card : personDeck){
			deck.push(card);
		}
		for(Card card : weaponDeck){
			deck.push(card);
		}
		
		//shuffle big deck using the above shuffle method. 5 shuffles seems good.
		for(i=0; i < 5; i++){
			this.shuffle();
		}
		
		//deal cards
		i=0;
		while(!deck.isEmpty()){
			players.get(i).addCard(deck.pop());
			
			//go to the next player
			i++;
			//if there is no next player, go back to player 0
			if(i >= players.size()) i = 0;
		}
	}
	
	//for(Player p:players) p.emptyHand();
	//ArrayList<Card> roomDeck;
	//ArrayList<Card> personDeck;
	//ArrayList<Card> weaponDeck;
	

	public void calcTargets(int row, int col, int pathLength)
	{
		
		visitedTargets = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visitedTargets.add(board[row][col]);
		calcTargetsRecursive(row, col, pathLength);
	}
	
	/* GIVEN PSUEDOCODE
	 * Parameters: thisCell and numSteps

	for each adjCell in adjacentCells 
	-- if already in visited list, skip rest of this

	-- add adjCell to visited list 
	-- if numSteps == 1, add adjCell to Targets
	-- else call findAllTargets with adjCell, numSteps-1

	-- remove adjCell from visited list
	 */
	public void calcTargetsRecursive(int row, int col, int pathLength)
	{
		Set<BoardCell> adjList = getAdjList(row,col);

		for(BoardCell bc : adjList)
		{
			if(!visitedTargets.contains(bc)){
				visitedTargets.add(bc);

				if(pathLength == 1 || bc.isDoorway())
				{
					targets.add(bc);
				}
				else
				{
					calcTargetsRecursive(bc.getRow(), bc.getCol(), pathLength - 1);
				}

				visitedTargets.remove(bc);
			}
		}
	}

	public Set<BoardCell> getTargets()
	{
		return targets;
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public ArrayList<String> getPlayerCards() {
		return playerCards;
	}
	
	public ArrayList<String> getWeaponCards() {
		return weaponCards;
	}
	
	public ArrayList<String> getRoomCards() {
		return roomCards;
	}
	
	public Suggestion getSolution() {
		return solution;
	}
	
	public void setSolution(Suggestion solution){
		this.solution = solution;
	}
	
	public boolean makeAccusation(Suggestion suggestion){
		if (suggestion.getPerson() == solution.getPerson() && suggestion.getRoom() == solution.getRoom() && suggestion.getWeapon() == solution.getWeapon())
			return true;
		return false;
	}
	public static Player getPlayer(int i) {
		// TODO Auto-generated method stub
		return null;
	}

}
