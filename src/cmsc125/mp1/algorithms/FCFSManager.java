package cmsc125.mp1.algorithms;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import cmsc125.mp1.algorithms.disk.DiskSimulator;
import cmsc125.mp1.model.Process;
import cmsc125.mp1.view.GanttChartStage;
import javafx.application.Platform;

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
					currentProcess = readyQueue.get(0);

					// set 1st response time
					if (currentProcess.getFirstResponseTime() == -1) {
						currentProcess.setFirstResponseTime(t);
					}
					currentBurstTime++;
					// System.out.println("processNum increased to "
					// +processNum);

//					ganttChart.displayUpdatedReadyQueue(readyQueue);
					ganttChart.updateGantt(t, currentProcess.getName());
					ds.invokeChartUpdate("FCFS", t, currentProcess.getName());

					System.out.println(currentProcess.getName() + "[" + currentBurstTime + "]\n");
				} else if (currentProcess != null && currentBurstTime < currentProcess.getBurstTime()) {
					// there is still a current process executing

					// set 1st response time
					if (currentProcess.getFirstResponseTime() == -1) {
						currentProcess.setFirstResponseTime(t);
					}
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
					currentProcess.setResponseTime(Math.abs(currentProcess.getFirstResponseTime() - currentProcess.getArrivalTime()));
					bankers.releaseResourcesForProcess(currentProcess);
					readyQueue.dequeue();
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
			String[][] statsTableData = bankers.computeStats(processesVector);
			ganttChart.displayStats(statsTableData);
		} else {
			Runnable statsGUI = new Runnable() {

				@Override
				public void run() {
					// Hide CPU vis panel and Disk vis panel
					Platform.runLater(
						() -> {
							ganttChart.hide();
							ds.hide();
						}
					);
					// Show error dialog announcing a DEADLOCK! occured
					if (!isDeadlock) {
						isDeadlock = true;
						JOptionPane.showMessageDialog(new JPanel(), "DEADLOCK!", "Error", JOptionPane.ERROR_MESSAGE);
						isDeadlock = false;
					}
				}
			};

			SwingUtilities.invokeLater(statsGUI);
		}
	}
}
