// Soldier ants are responsible for fighting bala ants

//import java.util.HashMap;
import java.util.Random;
import java.util.LinkedList;

public class Soldiers extends Ant{
	
	boolean attackMode = false;
	
/* No- arg default constructor*/
	Soldiers(){
		
	}
	
/* Methods */	
	
	/* The SoldierMove method:
	 * 	Move into one of the adjacent squares if the bala is in it 
	 *	If no balas nearby, move randomly 
	 *
	 * */
	public void SoldierMove(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, int prevX, int prevY, int x, int y) {
		
		// 1. If the ant wants to move to the queenspot, don't let it.
		if ( x == 13 && y == 13) {
			return;
		}
		
		// 2. Change the ants X and Y values.
		this.setAntX(x);
		this.setAntY(y);
		
		//  3. At previous position, reduce Soldier count by 1 & hide logo if workSoldierCtr is = 0.
		int workSoldierCtr = colonyArray[prevX][prevY].getSoldierCount();
		if(workSoldierCtr != 0){
			workSoldierCtr--;
			colonyArray[prevX][prevY].setSoldierCount(workSoldierCtr);
			nodeViewArray[prevX][prevY].setSoldierCount(workSoldierCtr);
			if( workSoldierCtr == 0 ) {
				nodeViewArray[prevX][prevY].hideSoldierIcon();
			}
		}
		
		//  4. At new postion, increase Soldier count and show icon. 	
		int newSoldierCtr = colonyArray[x][y].getSoldierCount();
		newSoldierCtr++;
		colonyArray[x][y].setSoldierCount(newSoldierCtr);
		nodeViewArray[x][y].setSoldierCount(newSoldierCtr);
		nodeViewArray[x][y].showSoldierIcon();
	
	} // End of move function.
	
	
	/* ****************************************************************************************************************************************************************************/	
	
	/* The Soldier Mover method:
	 * 	Call the SoldierDecide method to decide to move at random
	 * 	Call SoldierMove to do the moving action between nodes 
	 * */
	public void SoldierDeciderMain(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, LinkedList<Ant> antList) {
			
		int prevX = this.getAntX();
		int prevY = this.getAntY();
		int[] coords = new int[2];
		int limit = 7;
		int ctr = 0;
		boolean positiveNext = false;
		boolean goFlag = true; // Give it another go.
		
		// Find a good square to move to.
		// Take 7 tries at finding a 
		while ( goFlag = true  && ( ctr <= limit ) ) {
			
			coords = this.SoldierDecide(colonyArray, nodeViewArray);
				
			if (colonyArray[ coords[0] ] [ coords[1] ].getExplored()  == false ) {
				goFlag = true; // Give this loop another go.
				
			} else {
				goFlag = false; 
				positiveNext = true; 
				break;
			}
			ctr += 1;
		}
		
		
		if ( positiveNext == true ) {
			// Move
			boolean isOutOfBounds = this.isOutOfBounds(coords[0], coords[1]);
			if (!isOutOfBounds) {
				
				// If in AttackMode
				if ( this.attackMode == true ) {
					this.SoldierMove(colonyArray, nodeViewArray, prevX, prevY, coords[0], coords[1]);
					this.Fight(colonyArray, nodeViewArray, antList);
					
				} else {
					this.SoldierMove(colonyArray, nodeViewArray, prevX, prevY, coords[0], coords[1]);
					
				}
			} // End of checking for in bounds. 
		}  // End of if positive next is true.
	} // End of SoilderDeciderMethod.
	
/*****************************************************************************************************************************/	
	
	/* The SoldierDecide method:
	 * 	Decide which direction the Soldier should move - in scout mode
	 * 
	 * */
public int[] SoldierDecide(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray) {
		
		Random randomDecide = new Random();
		int rand = randomDecide.nextInt(8);
		int[] returnCoords = new int[2];
		boolean isOutOfBounds = false; // flag representing if array coordiantes are out of bounds.
		int sum = 0;
		

		int prevX = this.getAntX();
		int prevY = this.getAntY();
		
		// Determing if any of the surrounding cells have a Bala ant.
		
		//Top left
		if ( ( prevX > 0) && ( prevY > 0) ) {
			
			sum = colonyArray[prevX- 1][prevY- 1].getBalaCount();
			if ( sum > 0 ) {
				// Move there.
				returnCoords[0] = prevX - 1;
				returnCoords[1] = prevY - 1;
				this.attackMode = true;
				return returnCoords;
			}
		}
		
		
		// Top mid 
		if ( prevY> 0 ) {
			sum = colonyArray[prevX][prevY- 1].getBalaCount();
			if ( sum > 0 ) {
				// Move there.
				returnCoords[0] = prevX;
				returnCoords[1] = prevY - 1;
				this.attackMode = true;
				return returnCoords;
			}
		}
		
		// Top right
		if ( ( prevX < 26 ) && ( prevY> 0) ) {
			sum = colonyArray[prevX + 1][prevY- 1].getBalaCount();
			if ( sum > 0 ) {
				// Move there.
				returnCoords[0] = prevX + 1;
				returnCoords[1] = prevY - 1;
				this.attackMode = true;
				return returnCoords;
			}
		}
		
		// Top left
		if ( prevX > 0 ) {
			sum = colonyArray[prevX - 1][prevY].getBalaCount();
			if ( sum > 0 ) {
				// Move there.
				returnCoords[0] = prevX - 1;
				returnCoords[1] = prevY;
				this.attackMode = true;
				return returnCoords;
			}
		}
		
		// Right
		if ( prevX < 26 ) {
			sum = colonyArray[prevX + 1][prevY].getBalaCount();
			if ( sum > 0 ) {
				// Move there.
				returnCoords[0] = prevX + 1;
				returnCoords[1] = prevY;
				this.attackMode = true;
				return returnCoords;
			}
		}
		
		// Bottom left corner
		if ( (prevX > 0) && (prevY< 26) ) {
			sum = colonyArray[prevX - 1][prevY+ 1].getBalaCount();
			if ( sum > 0 ) {
				// Move there.
				returnCoords[0] = prevX - 1;
				returnCoords[1] = prevY + 1;
				this.attackMode = true;
				return returnCoords;
			}
		}
		
		// Bottom middle
		if ( prevY< 26 ) {
			sum = colonyArray[prevX][prevY+ 1].getBalaCount();
			if ( sum > 0 ) {
				// Move there.
				returnCoords[0] = prevX;
				returnCoords[1] = prevY + 1;
				this.attackMode = true;
				return returnCoords;
			}
		}
		
		// Bottom right corner
		if( (prevX < 26 ) && ( prevY< 26) ) {
			sum = colonyArray[prevX + 1][prevY+ 1].getBalaCount();
			if ( sum > 0 ) {
				// Move there.
				returnCoords[0] = prevX + 1;
				returnCoords[1] = prevY + 1;
				this.attackMode = true;
				return returnCoords;
			}
		}
		// End of check-bala-nearby
		
		// If all surrounding cells have no ants, move at random. 
		switch(rand) {

		// Top left
		case 0:
			isOutOfBounds = this.isOutOfBounds(prevX - 1, prevY - 1);
			if (!isOutOfBounds) {
				returnCoords[0] = prevX - 1;
				returnCoords[1] = prevY - 1;
				this.attackMode = false;
			}
			break;
			
		// Top middle
		case 1:
			isOutOfBounds = this.isOutOfBounds(prevX, prevY - 1);
			if (!isOutOfBounds) {
				returnCoords[0] = prevX;
				returnCoords[1] = prevY - 1;
				this.attackMode = false;
			}
			break;
			
		// Top right
		case 2:
			isOutOfBounds = this.isOutOfBounds(prevX + 1, prevY - 1);
			if (!isOutOfBounds) {
				returnCoords[0] = prevX + 1;
				returnCoords[1] = prevY - 1;
				this.attackMode = false;
			}
			break;
			
		// Left
		case 3: 
			isOutOfBounds = this.isOutOfBounds(prevX - 1, prevY);
			if (!isOutOfBounds) {
				returnCoords[0] = prevX - 1;
				returnCoords[1] = prevY;
				this.attackMode = false;
			}
			break;
			
		// Right
		case 4:
			isOutOfBounds = this.isOutOfBounds(prevX + 1, prevY);
			if (!isOutOfBounds) {
				returnCoords[0] = prevX + 1;
				returnCoords[1] = prevY;
				this.attackMode = false;
			}
			break;
			
		// Bottom left
		case 5:
			isOutOfBounds = this.isOutOfBounds(prevX - 1, prevY + 1);
			if (!isOutOfBounds) {
				returnCoords[0] = prevX - 1;
				returnCoords[1] = prevY + 1;
				this.attackMode = false;
			}
			break;
			
		// bottom middle 
		case 6:
			isOutOfBounds = this.isOutOfBounds(prevX, prevY + 1);
			if (!isOutOfBounds) {
				returnCoords[0] = prevX;
				returnCoords[1] = prevY + 1;
				this.attackMode = false;
			}
			
			break;
			
		// Bottom right
		case 7:
			isOutOfBounds = this.isOutOfBounds(prevX + 1, prevY + 1);
			if (!isOutOfBounds) {
				returnCoords[0] = prevX + 1;
				returnCoords[1] = prevY + 1;
				this.attackMode = false;
			}
			break;
			
		} // End of switch statement.
		return returnCoords;
	} // end of soldier decide 
	
/*****************************************************************************************************************************/	
	
	/* The Fight method: 
	 *	When the current node contains a bala ant - in attack mode 
	 *	50% chance the soldier kills the bala ant 
	 *  Assume there is a Bala in the same node as this ant.
	 * */
	public void Fight(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, LinkedList<Ant> antList) {
		// Generate the odds.
		Random randomDecide = new Random();
		int rand = randomDecide.nextInt(100) + 1;
		String antTypeStr = " ";
			
		if ( rand < 50 ) {
			// Soilder wins
			
			// Reference antList to find a Bala that is in the same node as this Soldier ant.
			for (int i = 0; i < antList.size(); i++) {
				
				// Get the class name (ant type) for the ant in Ant List
				Ant workAnt = antList.get(i);
				var antType = workAnt.getClass();
				antTypeStr = String.valueOf(antType);

				
				// If the ant is a Bala
				if ( antTypeStr == "class Balas" ) {
					
					Balas workBala = ( (Balas) workAnt);
					
					// If the work, bala is in the node list. 
					if ( ( workBala.getAntX() == this.getAntX() ) && ( workBala.getAntY() == this.getAntY() ) ) {
						this.die(colonyArray, nodeViewArray, antList, this.getID(), this.getAntX(), this.getAntY(), "class Balas");
						
					} // End if coorinates match.
				} // End of if class = Balas.
			} // End of for loop.
			
		} else {
			
			// Soldier looses
			this.die(colonyArray, nodeViewArray, antList, this.getID(), this.getAntX(), this.getAntY(), "class Soldiers");
		}
	}
	
	
} // end Soldier class

	
