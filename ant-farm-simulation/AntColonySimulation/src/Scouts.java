/* Scouts are responsible for enlarging the area available to the foragers. */
import java.util.Random;

public class Scouts extends Ant {
	
	// No-argument default constructor for Scout Ant 
	public Scouts() {
		
	}
	
/* Method declaration section */
	
	// Purpose: Move from previous coordinate to a new coordinate position.
	// Input: 	1)  Colony Array (The database) 
	//			2)  nodeViewArray (The screen)
	//          3)  Previous x coordinate
	//			4)  Previous y coordinate
	//			5)  New x coordinate 
	//			6)  New y coordinate
	//			7)  Food probability for node
	//			8)  Random food amount between 500 and 1000
	// Method: 	1.  If the ant wants to move to the queenspot, don't let it.
	//			2.  Change the ants X and Y values.
	//			3.  At previous position, reduce Scount count by 1 & hide logo if scoutWorkCtr is = 0
	//			4.  At new postion, increase Scout count and show icon. 
	//			5.  if scout moves to a closed node, reveal that node.
	//          5.1 generate random food amount for newly revealed node  
	
	public void move(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, int prevX, int prevY, int x, int y, int foodChance, int randFood) {
		
		
		// 1. If the ant wants to move to the queenspot, don't let it.
		if ( x == 13 && y == 13) {
			return;
		}
			
		// 2. Change the ants X and Y values.
		this.setAntX(x);
		this.setAntY(y);
		
		//  3. At previous position, reduce Scount count by 1 & hide logo if scoutWorkCtr is = 0.
		int workScoutCtr = colonyArray[prevX][prevY].getScoutCount();
		if(workScoutCtr != 0){
			workScoutCtr--;
			colonyArray[prevX][prevY].setScoutCount(workScoutCtr);
			nodeViewArray[prevX][prevY].setScoutCount(workScoutCtr);
			if( workScoutCtr == 0 ) {
				nodeViewArray[prevX][prevY].hideScoutIcon();
			}
		}
		
		//  4. At new postion, increase Scout count and show icon. 	
		int newScoutCtr = colonyArray[x][y].getScoutCount();
		newScoutCtr++;
		colonyArray[x][y].setScoutCount(newScoutCtr);
		nodeViewArray[x][y].setScoutCount(newScoutCtr);
		nodeViewArray[x][y].showScoutIcon();

		// 5. if scout moves to a closed node, reveal that node.
		if (!(colonyArray[x][y].getExplored())) {
			colonyArray[x][y].setExplored(true);
			nodeViewArray[x][y].showNode();
			// 5.1 generate random food amount for newly revealed node 
			if (foodChance < 26) { // 0 - 25 %
				colonyArray[x][y].getFoodAmount();
				colonyArray[x][y].setFoodAmount(randFood);
				nodeViewArray[x][y].setFoodAmount(colonyArray[x][y].getFoodAmount());
			}	
		}
	} // End of move function.

/************************************************************************************************************************************/
	
	
	// Purpose: Decide which direction the Scout should move.
	public int decide(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray) {
		
		Random randomDecide = new Random();
		int rand = randomDecide.nextInt(8);
		int foodChance = randomDecide.nextInt(100) + 1;
		int maxValue = 1000;
		int minValue = 500;
		int randFood = randomDecide.nextInt(maxValue - minValue + 1) + 500;
		boolean isOutOfBounds = false; // flag representing if (x,y) are out of bounds. 
		

		int prevX = this.getAntX();
		int prevY = this.getAntY();
		
		// given a random number between 0-7, move in a direction
		switch(rand) {
		
		// represents a move to the top left.
		case 0:
			isOutOfBounds = this.isOutOfBounds(prevX - 1, prevY - 1);
			if (!isOutOfBounds) {
				this.move(colonyArray, nodeViewArray, prevX, prevY, prevX - 1, prevY - 1, foodChance, randFood);
			}
			break;
			
		// represents a move to the top middle 	
		case 1:
			isOutOfBounds = this.isOutOfBounds(prevX, prevY - 1);
			if (!isOutOfBounds) {
				this.move(colonyArray, nodeViewArray, prevX, prevY, prevX, prevY - 1, foodChance, randFood);
			}
			break;
			
		// represents a move to the top right	
		case 2:
			isOutOfBounds = this.isOutOfBounds(prevX + 1, prevY - 1);
			if (!isOutOfBounds) {
				this.move(colonyArray, nodeViewArray, prevX, prevY, prevX + 1, prevY - 1, foodChance, randFood);
			}
			break;
			
		// represents a move to the left	
		case 3:
			isOutOfBounds = this.isOutOfBounds(prevX - 1, prevY);
			if (!isOutOfBounds) {
				this.move(colonyArray, nodeViewArray, prevX, prevY, prevX - 1, prevY, foodChance, randFood);
			}
			break;
		
		// represents a move to the right
		case 4:
			isOutOfBounds = this.isOutOfBounds(prevX + 1, prevY);
			if (!isOutOfBounds) {
				this.move(colonyArray, nodeViewArray, prevX, prevY, prevX + 1, prevY, foodChance, randFood);
			}
			break;
			
		// represents a move to the bottom left 	
		case 5:
			isOutOfBounds = this.isOutOfBounds(prevX - 1, prevY + 1);
			if (!isOutOfBounds) {
				this.move(colonyArray, nodeViewArray, prevX, prevY, prevX - 1, prevY + 1, foodChance, randFood);
			}
			break;
			
		// represents a move to the bottom middle 
		case 6:
			isOutOfBounds = this.isOutOfBounds(prevX, prevY + 1);
			if (!isOutOfBounds) {
				this.move(colonyArray, nodeViewArray, prevX, prevY, prevX, prevY + 1, foodChance, randFood);
			}
			break;
			
		// represents a move to the bottom right.	
		case 7:
			isOutOfBounds = this.isOutOfBounds(prevX + 1, prevY + 1);
			if (!isOutOfBounds) {
				this.move(colonyArray, nodeViewArray, prevX, prevY, prevX + 1, prevY + 1, foodChance, randFood);
			}
			break;
			
		default:
			// this should never execute 
		}
		return 0;
	} // end decide method 
		
} // end Scout class 
	
