/* Forager ants are responsible for bringing food to queen */

import java.util.Random;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class Foragers extends Ant { 
	
/* Variable declaration */
	boolean forageMode = true; // true = forage mode.  false = nest mode. 
	boolean carryingFood = false; // false = not carrying food. true = carrying food. 
	Stack <int[]> pheroTrail = new Stack<int[]>();
	
	
/* // No-argument default constructor for Forager Ant */
	public Foragers() {
		this.pheroTrail.push(new int[]{13, 13});
		
	}
	
/* Method declaration section */
	
	/* The goForage method:
	 * 	Decide what mode the ant should be in
	 * */
	public void goForager(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, LinkedList<Ant> antList) {
		 if (this.forageMode == true ) {
			 this.Forage(colonyArray, nodeViewArray);
		 } else {
			 this.returnToNest(colonyArray, nodeViewArray, antList);
		 }
	}
	
/* ****************************************************************************************************************************************************************************/	
	
	/* The Forage method:
	 * 	HashMap used to store key (Direction) , value ( movement)
	 * 	Call the forageDecide method to decide to move at random or follow pheromone trail 
	 * 	Call moveForager to do the moving action between nodes, checks whether food is present 
	 * */
	public void Forage(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray) {
		
		// Variable declaration section.
		int highestPhero = 0;
		String highestDirection = " ";
		int probeValue = 0;
		int workPheroLevel = 0;
		
		// Examine the 7 surrounding cells to determine pheromone levels. 
		// Only load this hash map with coordnates that are in bounds
		HashMap<String, Integer> pheroMap = new HashMap<>();
		
		
		boolean isOutOfBounds = this.isOutOfBounds(this.getAntX() - 1, this.getAntY() - 1);
		if (!isOutOfBounds) {
			workPheroLevel = colonyArray[this.getAntX() - 1][this.getAntY() - 1].getPheromoneLevel(); 
			pheroMap.put("topLeft", workPheroLevel);
			probeValue = workPheroLevel;
		}
		
		isOutOfBounds = this.isOutOfBounds(this.getAntX(),this.getAntY() - 1);
		if (!isOutOfBounds) {
			workPheroLevel = colonyArray[this.getAntX()][this.getAntY() - 1].getPheromoneLevel();
			pheroMap.put("topMiddle", workPheroLevel);
			probeValue = workPheroLevel;
		}
		
		isOutOfBounds = this.isOutOfBounds(this.getAntX() + 1, this.getAntY() - 1);
		if (!isOutOfBounds) {
			workPheroLevel = colonyArray[this.getAntX() + 1][this.getAntY() - 1].getPheromoneLevel();
			pheroMap.put("topRight", workPheroLevel);
			probeValue = workPheroLevel;
		}
		
		isOutOfBounds = this.isOutOfBounds(this.getAntX() - 1, this.getAntY());
		if (!isOutOfBounds) {
			workPheroLevel = colonyArray[this.getAntX() - 1][this.getAntY()].getPheromoneLevel();
			pheroMap.put("Left", workPheroLevel);
			probeValue = workPheroLevel;
		}
		
		isOutOfBounds = this.isOutOfBounds(this.getAntX() + 1, this.getAntY());
		if (!isOutOfBounds) {
			workPheroLevel = colonyArray[this.getAntX() + 1][this.getAntY()].getPheromoneLevel();
			pheroMap.put("Right", workPheroLevel);
			probeValue = workPheroLevel;
		}
		
		isOutOfBounds = this.isOutOfBounds(this.getAntX() - 1, this.getAntY() + 1);
		if (!isOutOfBounds) {
			workPheroLevel = colonyArray[this.getAntX() - 1][this.getAntY() + 1].getPheromoneLevel();
			pheroMap.put("bottomLeft", workPheroLevel);
			probeValue = workPheroLevel;
		}
		
		isOutOfBounds = this.isOutOfBounds(this.getAntX(), this.getAntY() + 1);
		if (!isOutOfBounds) {
			workPheroLevel = colonyArray[this.getAntX()][this.getAntY() + 1].getPheromoneLevel();
			pheroMap.put("bottomMid", workPheroLevel);
			probeValue = workPheroLevel;
		}
		
		isOutOfBounds = this.isOutOfBounds(this.getAntX() + 1, this.getAntY() + 1);
		if (!isOutOfBounds) {
			workPheroLevel = colonyArray[this.getAntX() + 1][this.getAntY() + 1].getPheromoneLevel();
			pheroMap.put("bottomRight", workPheroLevel);
			probeValue = workPheroLevel;
		}
			
		int prevX = this.getAntX();
		int prevY = this.getAntY();
		int[] coords = new int[2];
		int limit = 7;
		int ctr = 0;
		boolean positiveNext = false;
		boolean goFlag = true;
		
		// Find a good square to move to - 7 trys 
		while ( goFlag = true  && ( ctr <= limit ) ) {
			
			coords = this.forageDecide(colonyArray, nodeViewArray, prevX, prevY, highestDirection, pheroMap, probeValue);
			
			// If it's out of bounds, break out of this iteration of the loop. 
			isOutOfBounds = this.isOutOfBounds(coords[0], coords[1]);
			if (isOutOfBounds) {
				ctr += 1;
				continue;
			}
			
			
			if ( (colonyArray[ coords[0] ] [ coords[1] ].getExplored()  == false )) {
				goFlag = true;
				
			} else {
				goFlag = false;
				positiveNext = true;
				break;
			}
			
			ctr += 1;
		}
		
		if ( positiveNext == true ) {
			// Move
			this.MoveForager(colonyArray, nodeViewArray, prevX, prevY, coords[0], coords[1]);
		}
	} // End of forage method.
	
/* ****************************************************************************************************************************************************************************/
	
	
	/* The forageDecide method:
	 * 	no pheromone trail? move at random 
	 * 	follow highest pheromone trail if present 
	 * */
	public int[] forageDecide(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, int prevX, int prevY, String highestDirection, HashMap<String, Integer> pheroMap, int probeValue) {
		
		int[] returnCoords = new int[2];
		int workValue = 0;
		int highestPhero = 0;
		int firstValue = probeValue;
		boolean allSame = true; // true until proven false.
		
		// 1. Which adjacent node has the highest pheromone level
		for (String direction : pheroMap.keySet()) {

			workValue = pheroMap.get(direction);
			
			// Set the highest pheromone and direction.
			if ( workValue > highestPhero ) {
			
				highestPhero = workValue;
				highestDirection = direction;
			}

			// Check to see if adjancent nodes have same levels. 
			if ( firstValue != workValue ) {
				allSame = false;
			}
		}
		
		
		// 2. If adjacent nodes have the same pheromone levels, then move in random direction.
		if( allSame == true) {
			
			// Get a random number between 0-7.
			Random randMove = new Random();
			int move = randMove.nextInt(8);
			
			switch (move) {
			
			// Top left
			case 0:
				returnCoords[0] = prevX - 1;
				returnCoords[1] = prevY - 1;
				break;
			// Top middle
			case 1:
				returnCoords[0] = prevX;
				returnCoords[1] = prevY - 1;
				break;
			// Top right
			case 2:
				returnCoords[0] = prevX + 1;
				returnCoords[1] = prevY - 1;
				break;
			// Left
			case 3: 
				returnCoords[0] = prevX - 1;
				returnCoords[1] = prevY;
				break;
			// Right
			case 4:
				returnCoords[0] = prevX + 1;
				returnCoords[1] = prevY;
				break;
			// Bottom left
			case 5:
				returnCoords[0] = prevX - 1;
				returnCoords[1] = prevY + 1;
				break;
			// bottom middle 
			case 6:
				returnCoords[0] = prevX;
				returnCoords[1] = prevY + 1;
				break;
			// Bottom right
			case 7:
				returnCoords[0] = prevX + 1;
				returnCoords[1] = prevY + 1;
				break;
			} // End of switch statement.	
			
		
		// 3. If one node has highest pheromone count, go to that node 
		} else {
			
			switch (highestDirection) {
			
			// Top left
			case "topLeft":
				returnCoords[0] = prevX - 1;
				returnCoords[1] = prevY - 1;
				break;
			// Top middle
			case "topMiddle":
				returnCoords[0] = prevX;
				returnCoords[1] = prevY - 1;
				break;
			// Top right
			case "topRight":
				returnCoords[0] = prevX + 1;
				returnCoords[1] = prevY - 1;
				break;
			// Left
			case "left": 
				returnCoords[0] = prevX - 1;
				returnCoords[1] = prevY;
				break;
			// Right
			case "right":
				returnCoords[0] = prevX + 1;
				returnCoords[1] = prevY;
				break;
			// Bottom left
			case "bottomLeft":
				returnCoords[0] = prevX - 1;
				returnCoords[1] = prevY + 1;
				break;
			// bottom middle 
			case "bottomMid":
				returnCoords[0] = prevX;
				returnCoords[1] = prevY + 1;
				break;
			// Bottom right
			case "bottomRight":
				returnCoords[0] = prevX + 1;
				returnCoords[1] = prevY + 1;
				break;
			} // End of switch statement.
			
		}// End of following highest pheromone direction
		
		return returnCoords;
	}
	
/* ****************************************************************************************************************************************************************************/
	
	/* The MoveForager method:
	 * 	The move action from previous node to a new node 
	 * 	This method checks if there is any food present in the node 
	 * */
	public void MoveForager(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, int prevX, int prevY, int x, int y) {
		
		
		// When the ant is looking for food. 
		if (forageMode == true ) {
			// 1. If the ant wants to move to the queenspot, don't let it.
			if ( x == 13 && y == 13) {
				return;
			}
			
			// Add the next position to the stack.
			pheroTrail.push(new int[]{x, y});
			
			// 2. Change the ants X and Y values.
			this.setAntX(x);
			this.setAntY(y);
			
			
			//  3. At previous position, reduce Forager count by 1 & hide logo if foragerCtr is = 0.
			int foragerCtr = colonyArray[prevX][prevY].getForagerCount();
			if(foragerCtr != 0){
				foragerCtr--;
				colonyArray[prevX][prevY].setForagerCount(foragerCtr);
				nodeViewArray[prevX][prevY].setForagerCount(foragerCtr);
				if( foragerCtr == 0 ) {
					nodeViewArray[prevX][prevY].hideForagerIcon();
				}
			}
			
			//  4. At new postion, increase Forager count and show icon. 	
			int newForagerCtr = colonyArray[x][y].getForagerCount();
			newForagerCtr++;
			colonyArray[x][y].setForagerCount(newForagerCtr);
			nodeViewArray[x][y].setForagerCount(newForagerCtr);
			nodeViewArray[x][y].showForagerIcon();
			
			
			// If new position has food, pick it up and activate return-to-nest-mode. 
			if (colonyArray[x][y].getFoodAmount() > 0) {
				int foodAtSquare = colonyArray[x][y].getFoodAmount();
			
				// Lower amount at square by 1.
				colonyArray[x][y].setFoodAmount(foodAtSquare - 1);
				nodeViewArray[x][y].setFoodAmount(foodAtSquare - 1);
				
				// Increase the amount the ant is carrying. 
				carryingFood = true;
				forageMode = false;
			}
			
		} 		
		
} // End of MoveForager
	
/* ****************************************************************************************************************************************************************************/	

	/* The returnToNest mode:
	 * 	The ant is now carrying food
	 * 	Deposit 10 units of pheromone into nodes as it goes back to the nest 
	 * 	Deposit unit of food to the queen nest
	 * */
	public void returnToNest(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, LinkedList<Ant> antList) {
		
		int prevX = this.getAntX();
		int prevY = this.getAntY();
		
		// Peek before you pop.
		if ( pheroTrail.peek()[0] == 13 && pheroTrail.peek()[1] == 13) {
			// Increase food by 1.
			// Turn on Forage Mode.
			this.forageMode = true;
			this.carryingFood = false;
			
			// Queen food goes up by 1.
			colonyArray[13][13].setFoodAmount( colonyArray[13][13].getFoodAmount() + 1 );
			nodeViewArray[13][13].setFoodAmount( colonyArray[13][13].getFoodAmount() + 1 );
	
			// call moveForager
			this.MoveForager(colonyArray, nodeViewArray, prevX, prevY, prevX, prevY);
			
			return;
		}
		
		// Pop - find way back to nest from popping off hashmap
		int[] workCoords = new int[2];
		workCoords = pheroTrail.pop();
		int x = workCoords[0];  // The new X and Y for the ant.
		int y = workCoords[1];
		
		// 2. Change the ants X and Y values - move the ant to this new coordinate.
		this.setAntX(x);
		this.setAntY(y);
		
		//  3. At previous position, reduce Forager count by 1 & hide logo if foragerCtr is = 0.
		int foragerCtr = colonyArray[prevX][prevY].getForagerCount();
		
		if(foragerCtr != 0){
			foragerCtr--;
			colonyArray[prevX][prevY].setForagerCount(foragerCtr);
			nodeViewArray[prevX][prevY].setForagerCount(foragerCtr);
		} 
		if (colonyArray[prevX][prevY].getForagerCount() == 0) {
			nodeViewArray[prevX][prevY].hideForagerIcon();
		}
		
		// Increase pheromone levels by 1.
		int currentPL = colonyArray[prevX][prevY].getPheromoneLevel();
		currentPL = currentPL + 10;
		colonyArray[prevX][prevY].setPheromoneLevel(currentPL);
		nodeViewArray[prevX][prevY].setPheromoneLevel(currentPL);
		
		//  4. At new postion, increase Forager count and show icon. 	
		int newForagerCtr = colonyArray[x][y].getForagerCount();
		newForagerCtr++;
		colonyArray[x][y].setForagerCount(newForagerCtr);
		nodeViewArray[x][y].setForagerCount(newForagerCtr);
		nodeViewArray[x][y].showForagerIcon();
			
	}
	
}
