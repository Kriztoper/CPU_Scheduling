package cmsc125.mp1.algorithms;

import javax.swing.JTable;
import cmsc125.mp1.algorithms.disk.DiskSimulator;
import cmsc125.mp1.model.Process;
import cmsc125.mp1.view.GanttChartStage;

public class FCFSManager extends AlgoManager {

	public FCFSManager(JTable allocatedTable, JTable maximumTable, JTable availableTable, JTable timeTable, GanttChartStage ganttChart, DiskSimulator ds) {
		super(allocatedTable, maximumTable, availableTable, timeTable, ganttChart, ds);
	}

	@Override
	public void run() {
		bankers = new Bankers(allocatedTable, maximumTable, availableTable, getArrivalTimes(), getPriorityNumbers());
		Process currentProcess = null;
		int currentBurstTime = 0;
		int t = 0;

//		ganttChart.setTotalResourcesUsed(allocatedTable, bankers.getCurrentAvailableTableData());
		if (bankers.isSafeState()) {
			while (true) {
				System.out.println("At time " + t);
				ganttChart.displayTimeAndAvailableData(t, bankers.getCurrentAvailableTableData());
				bankers.updateJobQueue(t, processesQueue);
				ganttChart.displayUpdatedJobQueue(bankers.getJobQueue());
				bankers.requestResources(t, readyQueue);
				ganttChart.displayUpdatedJobQueue(bankers.getJobQueue());
				ganttChart.displayUpdatedReadyQueue(readyQueue);
				
//				bankers.allocateResource(t);
	//			bankers.getJobQueue().sortByArrivalTime();
	//			jobQueue = bankers.getJobQueue();
	
				// If ready queue is empty and there is no current process running
				if (processesQueue.isEmpty() && readyQueue.isEmpty() && currentProcess == null) {
					System.out.println("Ready Queue is empty!");
					break;
				} else if (!readyQueue.isEmpty() && currentProcess == null && readyQueue.peek().getArrivalTime() <= t) {
					// there is a process waiting in ready queue available for execution and there
					// is no current process running
	
//					requestResources();
					currentProcess = readyQueue.dequeue();
					currentBurstTime++;
					// System.out.println("processNum increased to "
					// +processNum);
	
//					ganttChart.displayUpdatedReadyQueue(readyQueue);
					ganttChart.updateGantt(t, currentProcess.getName());
					ds.invokeChartUpdate("FCFS", t, currentProcess.getName());
	
					System.out.println(currentProcess.getName() + "[" + currentBurstTime + "]\n");
				} else if (currentProcess != null && currentBurstTime < currentProcess.getBurstTime()) {
					// there is still a current process executing
	
					currentBurstTime++;
	
//					ganttChart.displayUpdatedReadyQueue(readyQueue);
					ganttChart.updateGantt(t, currentProcess.getName());
					ds.invokeChartUpdate("FCFS", t, currentProcess.getName());
	
					System.out.println(currentProcess.getName() + "[" + currentBurstTime + "]");
				}
	
				// there is still a current process running but it has completed executing
				if (currentProcess != null && currentBurstTime == currentProcess.getBurstTime()) {
					currentProcess.setCompletionTime(t + 1);
					currentProcess.setTurnaroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
					currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
					bankers.releaseResourcesForProcess(currentProcess);
					currentProcess = null;
					currentBurstTime = 0;
				}
	
				try {
					this.sleep(AlgoSimulator.visualizationSpeed); // delay
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				t++;
				ganttChart.displayTimeAndAvailableData(t, bankers.getCurrentAvailableTableData());
				ganttChart.displayUpdatedJobQueue(bankers.getJobQueue());
				ganttChart.displayUpdatedReadyQueue(readyQueue);
			}
			System.out.println("Done executing FCFS!");
			
			sortProcessesVectorByProcessNumber();
			displayStats();
//			bankers.displayStats(statsTable, processesVector);
//			bankers.setAvgCompletionTime(0.0);
//			bankers.setAvgTurnaroundTime(0.0);
//			bankers.setAvgWaitingTime(0.0);
//			for (int i = 0; i < processesVector.size(); i++) {
//				Process process = processesVector.get(i);
//				System.out.println(process.getName() + " CT=" + process.getCompletionTime() + ", TAT=" + process.getTurnaroundTime() + ", WT=" + process.getWaitingTime());
//				bankers.setAvgCompletionTime(bankers.getAvgCompletionTime() + ((double) process.getCompletionTime()));
//				bankers.setAvgTurnaroundTime(bankers.getAvgTurnaroundTime() + ((double) process.getTurnaroundTime()));
//				bankers.setAvgWaitingTime(bankers.getAvgWaitingTime() + ((double) process.getWaitingTime()));
//			}
//			
//			bankers.setAvgCompletionTime((bankers.getAvgCompletionTime()) / ((double) processesVector.size()));
//			bankers.setAvgTurnaroundTime(bankers.getAvgTurnaroundTime() / ((double) processesVector.size()));
//			bankers.setAvgWaitingTime(bankers.getAvgWaitingTime() / ((double) processesVector.size()));
//			
//			System.out.printf("Avg CT = %.5f, Avg TAT = %.5f, Avg WT = %.5f \n", bankers.getAvgCompletionTime(), bankers.getAvgTurnaroundTime(), bankers.getAvgWaitingTime());
		} else {
			System.exit(0);
		}
	}

	public void displayStats() {
		System.out.println("Hi!");
//		ganttChart.displayTotalResourcesUsed();
	}
}
