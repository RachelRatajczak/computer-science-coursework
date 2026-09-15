import java.util.LinkedList;
import java.util.Random;
import java.util.HashMap;

public class Balas extends Ant {
	
	boolean insideColony = false;
	boolean attackMode = false;
	
	// Default constructor 
	Balas(){
		
	}
	
	
/***********************************************************************************************************************/	
	
	/* The BalaTurn method:
	 * */
	public void BalaTurn(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, LinkedList<Ant> antList) {

		if (colonyArray[this.getAntX()][this.getAntY()].getExplored() == true) {
			insideColony = true;
		}
		
		// Check if there are any friendly ants in current node 
		if (colonyArray[this.getAntX()][this.getAntY()].getScoutCount() > 0 ||
			colonyArray[this.getAntX()][this.getAntY()].getForagerCount() > 0	||
			colonyArray[this.getAntX()][this.getAntY()].getSoldierCount() > 0 ||
			colonyArray[this.getAntX()][this.getAntY()].getQueen() == true) {

			this.Attack(colonyArray, nodeViewArray, antList, this.getAntX(), this.getAntY());
			return;
		}
				
		this.BalaDecide(colonyArray, nodeViewArray, antList);
		
	} // end of BalaTurn method

/***********************************************************************************************************************/	
	
	/* The BalaMove method:
	 * 
	 * */
	public void BalaMove(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, int prevX, int prevY, int x, int y, LinkedList<Ant> antList) {
		
		// 1. Change the ants X and Y values.
		this.setAntX(x);
		this.setAntY(y);
		
		//  2. At previous position, reduce Bala count by 1 & hide logo if BalaCtr is = 0.
		int BalaCtr = colonyArray[prevX][prevY].getBalaCount();
		
		if(BalaCtr != 0){
			BalaCtr--;
			colonyArray[prevX][prevY].setBalaCount(BalaCtr);
			nodeViewArray[prevX][prevY].setBalaCount(BalaCtr);
			if( BalaCtr == 0 ) {
				nodeViewArray[prevX][prevY].hideBalaIcon();
			}
		}	
			
		//  3. At new postion, increase Bala count and show icon. 	
		int newBalaCtr = colonyArray[x][y].getBalaCount();
		newBalaCtr++;
		colonyArray[x][y].setBalaCount(newBalaCtr);
		nodeViewArray[x][y].setBalaCount(newBalaCtr);
		nodeViewArray[x][y].showBalaIcon();
				
} // end of BalaMove method
	
	
/***********************************************************************************************************************/
	
	/* The BalaDecide method:
	 * */
	public int BalaDecide(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, LinkedList<Ant> antList) {
		
		
		// Variable declaration section.
		int x = this.getAntX();
		int y = this.getAntY();
		int workSum = 0;	// Used in for loop over hashmap.
		String highestDirection = " ";
		int highestCount = 0;
		int pointOfRef = 0;
		boolean allSame = true;
		int[] returnCoords = new int[2];
		boolean isOutOfBounds = false; // flag representing if array coordiantes are out of bounds. 
		
		/* Look at adjacent nodes, and find ant counts. Save the sum to one variable which will serve as 
		 * the value to the key representing that direction in the HashMap: Find Ant.
		 */
		
		// findAnt represents a HashMap of (key , value) pairs - direction (key) , ant count at that node (value)
		HashMap<String, Integer> findAnt = new HashMap<>();
		
		//Top left
		if ( ( x > 0) && ( y > 0) ) {
			
			// If the ant is inside the colony,
			if ((insideColony == true))  {
				// The next cell also needs to be explored. 
				if(colonyArray[x-1][y-1].getExplored()) {
					int topLeftSum = colonyArray[x- 1][y - 1].getSoldierCount() + colonyArray[x- 1][y - 1].getForagerCount() +
							 colonyArray[x- 1][y - 1].getScoutCount() + colonyArray[x- 1][y - 1].getQueenCount();
					findAnt.put("topLeft", topLeftSum);
					pointOfRef = topLeftSum;
				}
			
			// If the ant is not inside the colony, then there is no special instructions
			} else {
				int topLeftSum = colonyArray[x- 1][y - 1].getSoldierCount() + colonyArray[x- 1][y - 1].getForagerCount() +
						 colonyArray[x- 1][y - 1].getScoutCount() + colonyArray[x- 1][y - 1].getQueenCount();
				findAnt.put("topLeft", topLeftSum);
				pointOfRef = topLeftSum;
				
			}
		}
		
		
		// Top mid 
		if ( y > 0 ) {
			
			if ((insideColony == true))  {
				if(colonyArray[x][y-1].getExplored()) {
					int topMidSum = colonyArray[x][y - 1].getSoldierCount() + colonyArray[x][y - 1].getForagerCount() +
							 colonyArray[x][y - 1].getScoutCount() + colonyArray[x][y - 1].getQueenCount();
					findAnt.put("topMiddle", topMidSum);
				}
				
			} else {
			int topMidSum = colonyArray[x][y - 1].getSoldierCount() + colonyArray[x][y - 1].getForagerCount() +
					 colonyArray[x][y - 1].getScoutCount() + colonyArray[x][y - 1].getQueenCount();
			findAnt.put("topMiddle", topMidSum);
			}
		}
		
		// Top right
		if ( ( x < 26 ) && ( y > 0) ) {
			
			if ((insideColony == true))  {
				if(colonyArray[x+1][y-1].getExplored()) {
					int topRightSum = colonyArray[x + 1][y - 1].getSoldierCount() + colonyArray[x + 1][y - 1].getForagerCount() +
							 colonyArray[x + 1][y - 1].getScoutCount() + colonyArray[x + 1][y - 1].getQueenCount();
					findAnt.put("topRight", topRightSum);
					pointOfRef = topRightSum; // loading this value in all the corner probes ensures it's always loaded.
				}
			} else {
				int topRightSum = colonyArray[x + 1][y - 1].getSoldierCount() + colonyArray[x + 1][y - 1].getForagerCount() +
						 colonyArray[x + 1][y - 1].getScoutCount() + colonyArray[x + 1][y - 1].getQueenCount();
				findAnt.put("topRight", topRightSum);
				pointOfRef = topRightSum; // loading this value in all the corner probes ensures it's always loaded.
			}
			
		}
		
		// Top left
		if ( x > 0 ) {
			if ((insideColony == true))  {
				if(colonyArray[x-1][y].getExplored()) {
					int leftSum = colonyArray[x - 1][y].getSoldierCount() + colonyArray[x - 1][y].getForagerCount() +
							 colonyArray[x - 1][y].getScoutCount() + colonyArray[x - 1][y].getQueenCount();
					findAnt.put("Left", leftSum);
				}
			} else {
				int leftSum = colonyArray[x - 1][y].getSoldierCount() + colonyArray[x - 1][y].getForagerCount() +
						 colonyArray[x - 1][y].getScoutCount() + colonyArray[x - 1][y].getQueenCount();
				findAnt.put("Left", leftSum);
			}

		}
		
		// Right
		if ( x < 26 ) {
			if ((insideColony == true))  {
				if(colonyArray[x+1][y].getExplored()) {
					int rightSum = colonyArray[x + 1][y].getSoldierCount() + colonyArray[x + 1][y].getForagerCount() +
							 colonyArray[x + 1][y].getScoutCount() + colonyArray[x + 1][y].getQueenCount();
					findAnt.put("Right", rightSum);
				}
			} else {
				int rightSum = colonyArray[x + 1][y].getSoldierCount() + colonyArray[x + 1][y].getForagerCount() +
						 colonyArray[x + 1][y].getScoutCount() + colonyArray[x + 1][y].getQueenCount();
				findAnt.put("Right", rightSum);
			}
		}
		
		// Bottom left corner
		if ( (x > 0) && (y < 26) ) {
			
			if ((insideColony == true))  {
				if(colonyArray[x-1][y+1].getExplored()) {
					int bottomLeftSum = colonyArray[x - 1][y + 1].getSoldierCount() + colonyArray[x - 1][y + 1].getForagerCount() +
							 colonyArray[x - 1][y + 1].getScoutCount() + colonyArray[x - 1][y + 1].getQueenCount();
					findAnt.put("bottomLeft", bottomLeftSum);
					pointOfRef = bottomLeftSum; 
				}
			} else {
				int bottomLeftSum = colonyArray[x - 1][y + 1].getSoldierCount() + colonyArray[x - 1][y + 1].getForagerCount() +
						 colonyArray[x - 1][y + 1].getScoutCount() + colonyArray[x - 1][y + 1].getQueenCount();
				findAnt.put("bottomLeft", bottomLeftSum);
				pointOfRef = bottomLeftSum; 
			}
			
			
		}
		
		// Bottom middle
		if ( y < 26 ) {
			if ((insideColony == true))  {
				if(colonyArray[x][y+1].getExplored()) {
					int bottomMidSum = colonyArray[x][y + 1].getSoldierCount() + colonyArray[x][y + 1].getForagerCount() +
							 colonyArray[x][y + 1].getScoutCount() + colonyArray[x][y + 1].getQueenCount();
					findAnt.put("bottomMid", bottomMidSum);

				}
			} else {
				int bottomMidSum = colonyArray[x][y + 1].getSoldierCount() + colonyArray[x][y + 1].getForagerCount() +
						 colonyArray[x][y + 1].getScoutCount() + colonyArray[x][y + 1].getQueenCount();
				findAnt.put("bottomMid", bottomMidSum);

			}
		}
		
		// Bottom right corner
		if( (x < 26 ) && ( y < 26) ) {
			if ((insideColony == true))  {
				if(colonyArray[x+1][y+1].getExplored()) {
					int bottomRightSum = colonyArray[x + 1][y + 1].getSoldierCount() + colonyArray[x + 1][y + 1].getForagerCount() +
							 colonyArray[x + 1][y + 1].getScoutCount() + colonyArray[x + 1][y + 1].getQueenCount();
					findAnt.put("bottomRight", bottomRightSum);
					pointOfRef = bottomRightSum; 

				}
			} else {
				int bottomRightSum = colonyArray[x + 1][y + 1].getSoldierCount() + colonyArray[x + 1][y + 1].getForagerCount() +
						 colonyArray[x + 1][y + 1].getScoutCount() + colonyArray[x + 1][y + 1].getQueenCount();
				findAnt.put("bottomRight", bottomRightSum);
				pointOfRef = bottomRightSum; 

			}
		}
		
		// Now the Bala ant moves on any ants that are next to it. 
		
		
		// Loop though the dictionary to see who has the highest sum.
		for (String direction : findAnt.keySet()) {
			
			workSum = findAnt.get(direction);
			
			// Set the highest pheromone and direction.
			if ( workSum > highestCount ) {
			
				highestCount = workSum;
				highestDirection = direction;
			}

			// The idea here is if all values are the same, the work sums won't deviate from
			// a point of reference taken from any of the cells probed in the 1st turn. 
			if ( pointOfRef != workSum ) {
				allSame = false;
			}
		}
		

		// If there are no ants in the adjacent nodes, move randomly  
		if (allSame == true) {
			Random randomDecide = new Random();
			int rand = randomDecide.nextInt(8);
			
			// given a random number between 0-7, move in a direction
			switch(rand) {
			
			// represents a move to the top left. 
			// BalaMove(node array, view array, before coordinates, after coordiantes)
			case 0:
				isOutOfBounds = this.isOutOfBounds(x - 1, y - 1);
				if ( !isOutOfBounds ) {
					this.BalaMove(colonyArray, nodeViewArray, x, y, x - 1, y - 1, antList);
				}
				break;
				
			// represents a move to the top middle 	
			case 1:
				isOutOfBounds = this.isOutOfBounds(x, y - 1);
				if ( !isOutOfBounds) {
					this.BalaMove(colonyArray, nodeViewArray, x, y, x, y - 1, antList);
				}
				break;
				
			// represents a move to the top right	
			case 2:
				isOutOfBounds = this.isOutOfBounds(x + 1, y - 1);
				if ( !isOutOfBounds) {
					this.BalaMove(colonyArray, nodeViewArray, x, y, x + 1, y - 1, antList);
				}
				break;
				
			// represents a move to the left	
			case 3:
				isOutOfBounds = this.isOutOfBounds(x - 1, y);
				if ( !isOutOfBounds) {
					this.BalaMove(colonyArray, nodeViewArray, x, y, x - 1, y, antList);
				}
				break;
			
			// represents a move to the right
			case 4:
				isOutOfBounds = this.isOutOfBounds(x + 1, y);
				if ( !isOutOfBounds) {
					this.BalaMove(colonyArray, nodeViewArray, x, y, x + 1, y, antList);
				}
				break;
				
			// represents a move to the bottom left 	
			case 5:
				isOutOfBounds = this.isOutOfBounds(x - 1, y + 1);
				if ( !isOutOfBounds) {
					this.BalaMove(colonyArray, nodeViewArray, x, y, x - 1, y + 1, antList);
				}
				break;
				
			// represents a move to the bottom middle 
			case 6:
				isOutOfBounds = this.isOutOfBounds(x, y + 1);
				if ( !isOutOfBounds) {
					this.BalaMove(colonyArray, nodeViewArray, x, y, x, y + 1, antList);
				}
				break;
				
			// represents a move to the bottom right.	
			case 7:
				isOutOfBounds = this.isOutOfBounds(x + 1, y + 1);
				if ( !isOutOfBounds) {
					this.BalaMove(colonyArray, nodeViewArray, x, y, x + 1, y + 1, antList);
				}
				
				break;
				
			default:
				// this should never execute 
			}
			
			
		// There are ants in the adjacent nodes, go in the direction of the highest count
		} else {
				
			switch (highestDirection) {		
			
			
				// Top left
				case "topLeft":
					isOutOfBounds = this.isOutOfBounds(x - 1, y - 1);
					returnCoords[0] = x - 1;
					returnCoords[1] = y - 1;
					break;
				// Top middle
				case "topMiddle":
					isOutOfBounds = this.isOutOfBounds(x, y - 1);
					returnCoords[0] = x;
					returnCoords[1] = y - 1;
					break;
				// Top right
				case "topRight":
					isOutOfBounds = this.isOutOfBounds(x + 1, y - 1);
					returnCoords[0] = x + 1;
					returnCoords[1] = y - 1;
					break;
				// Left
				case "left": 
					isOutOfBounds = this.isOutOfBounds(x - 1, y);
					returnCoords[0] = x - 1;
					returnCoords[1] = y;
					break;
				// Right
				case "right":
					isOutOfBounds = this.isOutOfBounds(x + 1, y);
					returnCoords[0] = x + 1;
					returnCoords[1] = y;
					break;
				// Bottom left
				case "bottomLeft":
					isOutOfBounds = this.isOutOfBounds(x - 1, y + 1);
					returnCoords[0] = x - 1;
					returnCoords[1] = y + 1;
					break;
				// bottom middle 
				case "bottomMid":
					isOutOfBounds = this.isOutOfBounds(x, y + 1);
					returnCoords[0] = x;
					returnCoords[1] = y + 1;
					break;
				// Bottom right
				case "bottomRight":
					isOutOfBounds = this.isOutOfBounds(x + 1, y + 1);
					returnCoords[0] = x + 1;
					returnCoords[1] = y + 1;
					break;
				} // End of switch statement.
			
			if (insideColony == true) {

				if (colonyArray[returnCoords[0]][returnCoords[1]].getExplored() == true) {
					
					// If the ant treid to move out of bounds, the ant just would not get a turn.
					if ( !isOutOfBounds) {
						this.BalaMove(colonyArray, nodeViewArray, x, y, returnCoords[0], returnCoords[1], antList);
					}
				}
				
			} // End of if-inside-colony-true
			
		} // end of if statement.
		
	return 0;
				
} // end of Bala decide 
	
/***********************************************************************************************************************/
	
	/* The Attack method:
	 * 
	 * 1. Add the ants that are in the same node as Bala into a new LinkedList called antsInBalaNode
	 * 2. If the LinkedList, antsInBalaNode is not empty then randomly attack one of the ants if there is more than one 
	 * 
	 * */
	public void Attack(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, LinkedList<Ant> antList, int x, int y) {
	
		LinkedList<Ant> antsInBalasNode = new LinkedList<>();
		String antTypeStr = " ";
		String finalOppAntType = " ";
		int loosingAntID = 0;
		
		// For each ant, determine it's class name, and use that add to antsInBalasNode if at Bala's at x and y.
		for (int i = 0; i < antList.size(); i++) {
			
			Ant workAnt = antList.get(i);
			
			// If an ant in antList is in the same coordinate as the Bala ant who is attacking.
			if ( ( workAnt.getAntX() == x ) && ( workAnt.getAntY() == y) ) {
				
				var antType = workAnt.getClass();
				antTypeStr = String.valueOf(antType);
				
									
				// Add it to the new linked list.
				switch (antTypeStr) {
				
					case "class Queen":
						Queen workQueenAnt = ((Queen) antList.get(i));
						antsInBalasNode.add(workQueenAnt);
						finalOppAntType = "class Queen";
						break;
						
					case "class Scouts":
						Scouts wkScout= ((Scouts) antList.get(i));
						antsInBalasNode.add(wkScout);
						finalOppAntType = "class Scouts";
						break;
						
					case "class Foragers":
						Foragers wkForager = ((Foragers) antList.get(i));
						antsInBalasNode.add(wkForager);
						finalOppAntType = "class Foragers";break;
						
					case "class Soldiers":
						Soldiers wkSoldier = ((Soldiers) antList.get(i));
						antsInBalasNode.add(wkSoldier);
						finalOppAntType = "class Soldiers";
						break;
						
					default:
						break;
					
				} // End of switch statement
			} // End of for loop		
		} // End of if statement - If ant is in the right coordinate.
		
		
		Random pickAnt = new Random();
		
		if (!antsInBalasNode.isEmpty() ) {
			
			int randomAnt = pickAnt.nextInt(antsInBalasNode.size() );
			// Go pick a random ant that is in the node occupied by the Bala.
			Ant opponentAnt = antsInBalasNode.get(randomAnt);
		
			// 50% chance the bala wins the attack
			Random winProbability = new Random();
			int rand = winProbability.nextInt(100) + 1;
			 
			if (rand < 50) {
				// Bala wins
				loosingAntID = opponentAnt.getID();
				opponentAnt.die(colonyArray, nodeViewArray, antList, loosingAntID, x, y, finalOppAntType);
				
			} else {
				// Opponent Ant Wins
				this.die(colonyArray, nodeViewArray, antList, this.getID(), x, y, "class Balas");
			}
		} // end if statement to make sure antsInBalasNode is not empty - prevents: java.lang.IllegalArgumentException: bound must be positive
		
	} // end attack method 
	
} // end of Bala class 
