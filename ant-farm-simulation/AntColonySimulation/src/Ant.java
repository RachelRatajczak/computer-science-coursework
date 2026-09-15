/* The Ant class 
 * 
 */

import java.util.LinkedList;

public class Ant {
	
/* Variable Declaration */
	boolean isAlive;
	int antLifeSpan = 3650;  // 1 year : 365 days * 10 turns 
	int queenLifeSpan = 73000; //  20 years * 365 days * 10 turns 
	final int QUEEN_SPOT = 13;
	private int antTurn;
	private int antID;
	boolean nodeRevealed;
	private int moveNode; // Ants move one node per turn 
	private int antX;
	private int antY;
	
	
/* Constructor for Ant */
	public Ant() {
		isAlive = true;
		antLifeSpan = 3650; // 1 year ~ 365 days * 10 turns = 3650 
		antTurn = 0;
		antX = QUEEN_SPOT;
		antY = QUEEN_SPOT;
		moveNode = 1;
		
	}
	
/**********************************************************************************************************************/
	
/* Methods - setters and getters */
	
	// New ant is alive for the simulation
	public void setAlive(boolean newAnt) {
		isAlive = newAnt;
	}
	public boolean getAlive() {
		return isAlive;
	}
	
	// New ant gets a lifeSpan for the simulation
	public void setAntLifeSpan(int newAnt) {
		antLifeSpan = newAnt;
	}
	public int getAntLifeSpan() {
		return antLifeSpan;
	}
	
	// Ants get to take a turn and perform actions on those turns
	public void setAntTurn(int turn) {
		antTurn = turn;
	}
	public int getAntTurn() {
		return antTurn;
	}
	
	// New ants will hatch at the Queen's location (13,13)
	public void setAntX(int hatchSpot) {
		antX = hatchSpot;
	}
	public int getAntX() {
		return antX;
	}
	public void setAntY(int hatchSpot) {
		antY = hatchSpot;
	}
	public int getAntY() {
		return antY;
	}
	
	// New ants have the ability to move 1 node in any direction 
	public void setMoveNode(int move) {
		moveNode = move;
	}
	public int getMoveNode() {
		return moveNode;
	}
	
	public void setID(int idNum) {
		antID = idNum;
	}
	public int getID() {
		return antID;
		
	}
	
/**********************************************************************************************************************/
	
	public boolean isOutOfBounds(int x, int y) {
		
		// If any of these conditions are true, then the input coordinates are out of bounds.
		if ( ( x > 26 ) || ( x < 0 ) || ( y > 26 ) || ( y < 0) ) {
			return true;
		} else {
			return false;
		}
	}

/**********************************************************************************************************************/
	

	public void die(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, LinkedList<Ant> antList, int AntID, int x, int y, String antType) {
		
		int workCtr = 0;
	
		if ( antType == "class Soldiers") {
			workCtr = colonyArray[x][y].getSoldierCount();
			workCtr--;
			if (workCtr <= 0) {
				workCtr = 0;
			}
			colonyArray[x][y].setSoldierCount(workCtr);
			nodeViewArray[x][y].setSoldierCount(workCtr);
			
			if ( workCtr == 0) {
				nodeViewArray[x][y].hideSoldierIcon();
			}
			
			// Removing the ant from antList by ID.
			for ( int i = 0; i < antList.size(); i++) {
				if ( antList.get(i).getID() == AntID ) {
					antList.remove(i);
				}
			}
		} 
		
		if ( antType == "class Scouts") {
			workCtr = colonyArray[x][y].getScoutCount();
			workCtr--;
			if (workCtr <= 0) {
				workCtr = 0;
			}
			colonyArray[x][y].setScoutCount(workCtr);
			nodeViewArray[x][y].setScoutCount(workCtr);
			
			if ( workCtr == 0) {
				nodeViewArray[x][y].hideScoutIcon();
			}
			
			// Removing the ant from antList by ID.
			for ( int i = 0; i < antList.size(); i++) {
				if ( antList.get(i).getID() == AntID ) {
					antList.remove(i);
				}
			}
		} 
		
		if ( antType == "class Foragers") {
			workCtr = colonyArray[x][y].getForagerCount();
			workCtr--;
			if (workCtr <= 0) {
				workCtr = 0;
			}
			colonyArray[x][y].setForagerCount(workCtr);
			nodeViewArray[x][y].setForagerCount(workCtr);
			
			if ( workCtr == 0) {
				nodeViewArray[x][y].hideForagerIcon();
			}
			
			// Removing the ant from antList by ID.
			for ( int i = 0; i < antList.size(); i++) {
				if ( antList.get(i).getID() == AntID ) {
					antList.remove(i);
				}
			}
		} 
		
		if ( antType == "class Balas") {
			workCtr = colonyArray[x][y].getBalaCount();
			workCtr--;
			if (workCtr <= 0) {
				workCtr = 0;
			}
			colonyArray[x][y].setBalaCount(workCtr);
			nodeViewArray[x][y].setBalaCount(workCtr);
			
			if ( workCtr == 0) {
				nodeViewArray[x][y].hideBalaIcon();
			}
			
			// Removing the ant from antList by ID.
			for ( int i = 0; i < antList.size(); i++) {
				if ( antList.get(i).getID() == AntID ) {
					antList.remove(i); 
				}
			}
		} 
		
		if ( antType == "class Queen") {
			
			// Game ends.
			System.out.println("The queen has died. Game over.");
			System.exit(0);
		}	
		
	} // end die method 
	
/**********************************************************************************************************************/
	
	public void checkLifeSpan(int turn, Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, LinkedList<Ant> antList, int antID, int x, int y, String antType) {
		
		int workLifeSpan = this.antLifeSpan;
		
		workLifeSpan--;
		
		var type = this.getClass();
		String antTypeStr = String.valueOf(type);
		
		if (workLifeSpan <= 0) {
			
			int workCtr = 0;
			if ( antTypeStr.equals("class Soldiers")) {
				workCtr = colonyArray[x][y].getSoldierCount();
				workCtr--;
				if (workCtr <= 0) {
					workCtr = 0;
				}
				colonyArray[x][y].setSoldierCount(workCtr);
				nodeViewArray[x][y].setSoldierCount(workCtr);
				
				if ( workCtr == 0) {
					nodeViewArray[x][y].hideSoldierIcon();
				}
				
				// Removing the ant from antList by ID.
				for ( int i = 0; i < antList.size(); i++) {
					if ( antList.get(i).getID() == this.getID() ) {
						antList.remove(i);
					}
				}
			} 
			
			if ( antTypeStr.equals("class Scouts")) {
				workCtr = colonyArray[x][y].getScoutCount();
				workCtr--;
				if (workCtr <= 0) {
					workCtr = 0;
				}
				colonyArray[x][y].setScoutCount(workCtr);
				nodeViewArray[x][y].setScoutCount(workCtr);
				
				if ( workCtr == 0) {
					nodeViewArray[x][y].hideScoutIcon();
				}
				
				// Removing the ant from antList by ID.
				for ( int i = 0; i < antList.size(); i++) {
					if ( antList.get(i).getID() == this.getID() ) {
						antList.remove(i);
					}
				}
			} 
			
			if ( antTypeStr.equals("class Foragers")) {
				workCtr = colonyArray[x][y].getForagerCount();
				workCtr--;
				if (workCtr <= 0) {
					workCtr = 0;
				}
				colonyArray[x][y].setForagerCount(workCtr);
				nodeViewArray[x][y].setForagerCount(workCtr);
				
				if ( workCtr == 0) {
					nodeViewArray[x][y].hideForagerIcon();
				}
				
				// Removing the ant from antList by ID.
				for ( int i = 0; i < antList.size(); i++) {
					if ( antList.get(i).getID() == this.getID() ) {
						antList.remove(i);
					}
				}
			} 
			
			if ( antTypeStr.equals("class Balas")) {
				workCtr = colonyArray[x][y].getBalaCount();
				workCtr--;
				if (workCtr <= 0) {
					workCtr = 0;
				}
				colonyArray[x][y].setBalaCount(workCtr);
				nodeViewArray[x][y].setBalaCount(workCtr);
				
				if ( workCtr == 0) {
					nodeViewArray[x][y].hideBalaIcon();
				}
				
				// Removing the ant from antList by ID.
				for ( int i = 0; i < antList.size(); i++) {
					if ( antList.get(i).getID() == this.getID() ) {
						antList.remove(i); 
					}
				}
			} 
			
			if ( antTypeStr.equals("class Queen")) {
				
				// Game ends.
				System.out.println("The queen has died. Game over.");
				System.exit(0);
			}	
		}
		
		this.antLifeSpan = workLifeSpan;
		
	} // end checkLifeSpan
	
} // end Ant class
