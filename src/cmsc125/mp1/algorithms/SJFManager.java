package cmsc125.mp1.algorithms; 
 
import javax.swing.JTable; 
 
import cmsc125.mp1.view.SimulationPanel; 
 
public class SJFManager extends Thread { 
 
  private SimulationPanel simulationPanel; 
  private JTable resourcesTable; 
  private JTable timeTable; 
   
  public SJFManager(SimulationPanel simulationPanel,  
      JTable resourcesTable, JTable timeTable) { 
    this.simulationPanel = simulationPanel; 
    this.resourcesTable = resourcesTable; 
    this.timeTable = timeTable; 
  } 
 
  public void startSimulation() { 
     
     
    start(); 
  } 
   
  @Override 
  public void run() { 
     
  } 
} 