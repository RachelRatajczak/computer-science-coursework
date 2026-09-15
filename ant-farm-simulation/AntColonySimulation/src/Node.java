/* Node class to define the data of each node square */

/* In the AntSimulation,create a 2D array of node objects to represent the data for each square in the colony */

public class Node {
	
	/* Variable declaration */
	private boolean explored;
	private boolean queenPresent;
	private int foragerCount;
	private int scoutCount;
	private int soldierCount;
	private int balaCount;
	private int queenCount;
	private int food;
	private int pheromones;
	private int x;
	private int y;
	
	/* Node Constructor */
	public Node(int xPoint, int yPoint) {
		explored = false;
		queenPresent = false;
		foragerCount = 0;
		scoutCount = 0;
		soldierCount = 0;
		balaCount = 0;
		food = 0;
		pheromones = 0;
		queenCount = 0;
		xPoint = x;
		yPoint = y;
	}
	
	/* Methods - setters and getters */	
	
	// Location of the Node
	public void setX(int xSpot) {
		x = xSpot;
	}
	public int getX() {
		return x;
	}
	public void setY(int ySpot) {
		y = ySpot;
	}
	public int getY() {
		return y;
	}
	
	
	// Colony data for each Node object
	public void setExplored(boolean revealed) {
		 explored = revealed;
	}
	public boolean getExplored() {
		return explored;
	}
	
	public void setQueen(boolean queen) {
		queenPresent = queen;
	}
	public boolean getQueen() {
		return queenPresent;
	}
	
	public void setForagerCount(int foragers) {
		foragerCount = foragers;
	}
	public int getForagerCount() {
		return foragerCount;
	}
	
	public void setScoutCount(int scouts) {
		scoutCount = scouts;
	}
	public int getScoutCount() {
		return scoutCount;
	}
	
	public void setSoldierCount(int soldiers) {
		soldierCount = soldiers;
	}
	public int getSoldierCount() {
		return soldierCount;
	}
	
	public void setBalaCount(int balas) {
		balaCount = balas;
	}
	public int getBalaCount() {
		return balaCount;
	}
	
	public void setFoodAmount(int foodCount) {
		food = foodCount;
	}
	public int getFoodAmount() {
		return food;
	}
	
	public void setPheromoneLevel(int pheromoneCount) {
		pheromones = pheromoneCount;
	}
	public int getPheromoneLevel() {
		return pheromones;
	}
	public void setQueenCount(int queen) {
		queenCount = queen;
	}
	public int getQueenCount() {
		return queenCount;
	}
	
	
} // End Node class bracket
