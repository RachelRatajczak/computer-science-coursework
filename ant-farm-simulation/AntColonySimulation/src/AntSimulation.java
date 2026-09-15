/* AntSimulation class */

/* Only 2D arrays are allowed for the grid environment for the colony */

import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;



public class AntSimulation implements SimulationEventListener {

/* Variable Declaration */
	private int turn = 0; // ants perform one action per turn 
	final int QUEEN_COUNT = 1;
	final int SCOUT_COUNT = 4;
	final int FORAGER_COUNT = 50;
	final int SOLDIER_COUNT = 10;
	final int BALA_COUNT = 1;
	int workId = 1;
	private Timer timer;
	private int turnCounter = 0;
	
	
	// Create an instance of ColonyView - Container for nodes
	ColonyView colonyView = new ColonyView(27,27);
	
	// GUI window 
	AntSimGUI antSimGUI = new AntSimGUI();
	
	// 2D array for ColonyNodeView objects - The view (GUI)
	ColonyNodeView[][] nodeViewArray = new ColonyNodeView[27][27];
	
	// 2D array for Node objects that contains colony data - The model 
	Node[][] colonyArray = new Node[27][27];
	
	// Create a list for the Ants
	LinkedList<Ant> antList = new LinkedList<>();  
	
	
/* Constructor of AntSimulation */
	public AntSimulation() {
		antSimGUI.initGUI(colonyView);
		antSimGUI.addSimulationEventListener(this);
	}
	
/**********************************************************************************************************************/
	
	// Initialize the colony Nodes 
	public void initColonyNodes() {
		
		// For loop to populate the 2D arrays - the view & the data arrays
		for(int i = 0; i < 27; i++) {
			for(int j = 0; j < 27; j++) {
				colonyArray[i][j] = new Node(i,j);
				nodeViewArray[i][j] = new ColonyNodeView();
				nodeViewArray[i][j].setID(i + " , " + j);
				colonyView.addColonyNodeView(nodeViewArray[i][j], i, j);
				
				// Fill nodes with zero food, pheromones, and ants 
				colonyArray[i][j].setFoodAmount(0);
				nodeViewArray[i][j].setFoodAmount(0);
				colonyArray[i][j].setPheromoneLevel(0);
				nodeViewArray[i][j].setPheromoneLevel(0);
				colonyArray[i][j].setSoldierCount(0);
				nodeViewArray[i][j].setSoldierCount(0);
				colonyArray[i][j].setForagerCount(0);
				nodeViewArray[i][j].setForagerCount(0);
				colonyArray[i][j].setScoutCount(0);
				nodeViewArray[i][j].setScoutCount(0);
				colonyArray[i][j].setBalaCount(0);
				nodeViewArray[i][j].setBalaCount(0);
						
				// Show the inital setup nodes 
				if ((i == 12) && (j >= 12 && j <= 14)) {
					nodeViewArray[i][j].showNode();
					colonyArray[i][j].setExplored(true);
				}
				if ((i == 13) && (j >= 12 && j <= 14)) {
					nodeViewArray[i][j].showNode();
					colonyArray[i][j].setExplored(true);
				}
				if ((i == 14) && (j >= 12 && j <= 14)) {
					nodeViewArray[i][j].showNode();
					colonyArray[i][j].setExplored(true);
				}
					
				// Fill the center node with queen & food 
				if ((i == 13) && (j == 13)) {
					colonyArray[i][j].setFoodAmount(1000);
					nodeViewArray[i][j].setFoodAmount(colonyArray[i][j].getFoodAmount());
					colonyArray[i][j].setPheromoneLevel(0);
					nodeViewArray[i][j].setPheromoneLevel(0);
					colonyArray[i][j].setExplored(true);
						
				// Fill the center node with the starting ants
				this.createInitialAnts();
				nodeViewArray[i][j].showQueenIcon();
				}		
			}
		} // end of initilization loops
	}
	
/**********************************************************************************************************************/
	
	public void createInitialAnts() {
		
		Queen queen = new Queen();
		queen.setID(workId);
		workId++;
		antList.add(queen);
		colonyArray[13][13].setQueen(true);
		nodeViewArray[13][13].setQueen(true);
		colonyArray[13][13].setQueenCount(1);
		queen.setAntLifeSpan(73000);
		
		for (int i = 0; i < BALA_COUNT; i++) {
			Balas bala = new Balas();
			bala.setID(workId);
			bala.setAntX(0);
			bala.setAntY(0);
			workId++;
			antList.add(bala);
					
			colonyArray[0][0].setBalaCount(i + 1);
			nodeViewArray[0][0].setBalaCount(i + 1);
			nodeViewArray[0][0].showBalaIcon();
			bala.setAntLifeSpan(3650);
		}
		
		for (int i = 0; i < SCOUT_COUNT; i++) {
			Scouts scout = new Scouts();
			scout.setID(workId);
			workId++;
			antList.add(scout);
			colonyArray[13][13].setScoutCount(i + 1);
			nodeViewArray[13][13].setScoutCount(i + 1);
			scout.setAntLifeSpan(3650);
		}
		
		for (int i = 0; i < FORAGER_COUNT; i++) {
			Foragers forager = new Foragers();
			forager.setID(workId);
			workId++;
			antList.add(forager);
			colonyArray[13][13].setForagerCount(i + 1);
			nodeViewArray[13][13].setForagerCount(i + 1);
			forager.setAntLifeSpan(3650);
		}
		
		for (int i = 0; i < SOLDIER_COUNT; i++) {
			Soldiers soldier = new Soldiers();
			soldier.setID(workId);
			workId++;
			antList.add(soldier);
			colonyArray[13][13].setSoldierCount(i + 1);
			nodeViewArray[13][13].setSoldierCount(i + 1);
			soldier.setAntLifeSpan(3650);
		}
	}
	
/**********************************************************************************************************************/
	
	// Method for Ant Turn
	// 1. Loop through the Ants in the AntList and let them all have a turn.
	// 2. 3% change of a Bala spawning at the periphery.
	// 3. Queen hatches a new ant on the start of every new day (10 turns)
	public void antTurn() {
		
		turn = turn + 1;
		String antTypeStr = " ";
		
		// 1. Loop through the Ants in the AntList and let them all have a turn.
		for (int i = 0; i < antList.size(); i++) {	
			
			// Find class name for each ant in list, and perform assigned actions 
			Ant workAnt = antList.get(i);
		
			var antType = workAnt.getClass();
			antTypeStr = String.valueOf(antType);
				
			switch (antTypeStr) {
			
				case "class Queen":
					Queen workQueenAnt = ((Queen) antList.get(i));
					workQueenAnt.queenTurn(colonyArray, nodeViewArray, i);
					workQueenAnt.checkLifeSpan(i, colonyArray, nodeViewArray, antList, i, i, i, antTypeStr);
					break;
					
				case "class Scouts":
					Scouts wkScout= ((Scouts) antList.get(i));
					wkScout.decide(colonyArray, nodeViewArray);
					wkScout.checkLifeSpan(i, colonyArray, nodeViewArray, antList, i, i, i, antTypeStr);
					break;
					
				case "class Foragers":
					Foragers wkForager = ((Foragers) antList.get(i));
					wkForager.goForager(colonyArray, nodeViewArray, antList);
					wkForager.checkLifeSpan(i, colonyArray, nodeViewArray, antList, i, i, i, antTypeStr);
					break;
					
				case "class Soldiers":
					Soldiers wkSoldier = ((Soldiers) antList.get(i));
					wkSoldier.SoldierDeciderMain(colonyArray, nodeViewArray, antList);
					wkSoldier.checkLifeSpan(i, colonyArray, nodeViewArray, antList, i, i, i, antTypeStr);
					break;
					
				case "class Balas":
					Balas wkBala = ((Balas) antList.get(i));
					wkBala.BalaTurn(colonyArray, nodeViewArray, antList);
					wkBala.checkLifeSpan(i, colonyArray, nodeViewArray, antList, i, i, i, antTypeStr);
					break;
				default:
					break;
				
			} // End of switch statement
		} // End of for loop
		
		
		// 2. 3% chance of a Bala spawning at the periphery.
		Random randomBalaOdds = new Random();
		int n = randomBalaOdds.nextInt(100);
	
		if (n < 3) {
			// Spawn an new Bala at (0, 0).
			Balas bala = new Balas();
			bala.setID(workId);
			bala.setAntX(0);
			bala.setAntY(0);
			workId++;
			antList.add(bala);
			
			int ctBalas = colonyArray[0][0].getBalaCount();
			ctBalas++;
					
			colonyArray[0][0].setBalaCount(ctBalas);
			nodeViewArray[0][0].setBalaCount(ctBalas);
			nodeViewArray[0][0].showBalaIcon();
		}
		
		
		
		// 3. Hatch a new ant at the start of new day
		if ( turn % 10 == 0) {
			Queen workQueen = ((Queen) antList.get(0));
			workId = workQueen.hatchAnt(colonyArray, nodeViewArray, turn, antList, workId);
			for ( int i = 0; i < 27; i++) {
				for( int j = 0; j < 27; j++) {
					int workPhero = colonyArray[i][j].getPheromoneLevel();
					workPhero = workPhero / 2;
					colonyArray[i][j].setPheromoneLevel(workPhero);
					nodeViewArray[i][j].setPheromoneLevel(colonyArray[i][j].getPheromoneLevel());
					
					if (workPhero == 0) {
						workPhero = 0;
					}
				}
			}
		}		
	} // end of antTurn method 
	
/**********************************************************************************************************************/	
		
	// GUI buttons 
	
	@Override
	public void simulationEventOccurred(SimulationEvent simEvent) {
		
		if (simEvent.getEventType() == SimulationEvent.NORMAL_SETUP_EVENT) {
			// set up the simulation for normal operation
			initColonyNodes();			
			
		} else if (simEvent.getEventType() == SimulationEvent.QUEEN_TEST_EVENT){
		 // set up simulation for testing the queen ant 

			
		} else if (simEvent.getEventType() == SimulationEvent.SCOUT_TEST_EVENT){
			// set up simulation for testing the scout ant }
	
				
		} else if (simEvent.getEventType() == SimulationEvent.FORAGER_TEST_EVENT) {
			 // set up simulation for testing the forager ant }

			
		} else if (simEvent.getEventType() == SimulationEvent.SOLDIER_TEST_EVENT) {
			 // set up simulation for testing the Soldier ant } 


		}else if (simEvent.getEventType() == SimulationEvent.RUN_EVENT) {
			// run the simulation continuously 
			
			this.AntSimulationTime();
			this.startSimulation();
 			
		} else if (simEvent.getEventType() == SimulationEvent.STEP_EVENT) {
			// run the next turn of the simulation 
			
			antTurn();
			
		} else {
			// invalid event occurred - probably will never happen }
		
		}

	} // End of events
	
/**********************************************************************************************************************/
	
	// Methods needs to run the simulation continously 
	
	public void AntSimulationTime() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform simulation logic here
                antTurn();
                turnCounter++;
                if (turnCounter >= 73000) {
                    timer.stop(); // Stop the timer when desired number of turns is reached
                }
            }
        });
    }

	
    public void startSimulation() {
        timer.start();
    }
	
}

