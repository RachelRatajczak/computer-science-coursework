import java.util.LinkedList;
import java.util.Random;

public class Queen extends Ant {

	/* Variable Declaration */
	final int QUEEN_ID = 0; // Queen ID is always 0
	final int QUEEN_X = 13;	// Center Node
	final int QUEEN_Y = 13;	// Center Node
	final int queenLifeSpan = 73000; //  20 years * 365 days * 10 turns 

	
	/* Constructor for Queen Ant */
	public void queenAnt() {
		this.setID(QUEEN_ID);
		this.setAntLifeSpan(queenLifeSpan);
		this.setAntX(QUEEN_X);
		this.setAntY(QUEEN_Y);
		
	}
	
	/* Methods */
	
	// queen consumes 1 unit of food each turn
	public int consumeFood(int food, Node[][] colonyArray, ColonyNodeView[][] nodeViewArray) {
		
		int amount = colonyArray[13][13].getFoodAmount();
		
		if ( amount > 0 ) {
			// consume one food unit
			amount = amount - 1;
			
			colonyArray[13][13].setFoodAmount(amount);
			nodeViewArray[13][13].setFoodAmount(colonyArray[13][13].getFoodAmount());
			
		} else {
			
			// death by starvation
			this.die(colonyArray, nodeViewArray, null, amount, amount, amount, null);
		}
		
		return amount;
	}
	
/************************************************************************************************************************************/	
	
	
	// queen hatches new ants at 1 ant/day (day = 10 turns)
	public int hatchAnt(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, int firstTurn, LinkedList<Ant> antList, int workId) {
		Random randomHatch = new Random();
		int n = randomHatch.nextInt(100);
	

		
		if (n < 50) { // 0-49
			
			// hatch a forager ant 
			Foragers workForager = new Foragers();
			workForager.setID(workId);
			workId++;
			antList.add(workForager);
			
			// Update the colonyArray & nodeViewArray
			int foragerCtr = colonyArray[13][13].getForagerCount();
			foragerCtr++;
			colonyArray[13][13].setForagerCount(foragerCtr);
			nodeViewArray[13][13].setForagerCount(colonyArray[13][13].getForagerCount());
			
			workForager.setAntLifeSpan(3650);
			
			
		} else if (n < 75) { // 50 - 74
			
			// hatch scout ants 
			Scouts workScout = new Scouts();
			workScout.setID(workId);
			workId++;
			antList.add(workScout);
			
			
			// Update the colonyArray & nodeViewArray
			int scoutCtr = colonyArray[13][13].getScoutCount(); 
			scoutCtr++;
			colonyArray[13][13].setScoutCount(scoutCtr);
			nodeViewArray[13][13].setScoutCount(colonyArray[13][13].getScoutCount());
			
			workScout.setAntLifeSpan(3650);
			
			
		} else {// 75 - 100
		
			// hatch soldier ants 
			Soldiers workSoldier = new Soldiers();
			workSoldier.setID(workId);
			workId++;
		
			antList.add(workSoldier);
			
			// Update the colonyArray & nodeViewArray
			int soldierCtr = colonyArray[13][13].getSoldierCount(); 
			soldierCtr++;
			colonyArray[13][13].setSoldierCount(soldierCtr);
			nodeViewArray[13][13].setSoldierCount(colonyArray[13][13].getSoldierCount());
			
			workSoldier.setAntLifeSpan(3650);
		}
		return workId;
	} // end of hatch method.
	
	
/************************************************************************************************************************************/
	
	//  On her turn, she can hatch an ant and consume food.
	public void queenTurn(Node[][] colonyArray, ColonyNodeView[][] nodeViewArray, int turn) {
	
		this.consumeFood(1, colonyArray, nodeViewArray);	
	}
	
} // Queen class bracket close


