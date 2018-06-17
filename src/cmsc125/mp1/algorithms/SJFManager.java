package cmsc125.mp1.algorithms;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import cmsc125.mp1.algorithms.disk.DiskSimulator;
import cmsc125.mp1.model.Process;
import cmsc125.mp1.view.GanttChartStage;
import javafx.application.Platform;

public class SJFManager extends AlgoManager {

	public SJFManager(JTable allocatedTable, JTable maximumTable, JTable availableTable, JTable timeTable,
			GanttChartStage ganttChart, DiskSimulator ds) {
		super(allocatedTable, maximumTable, availableTable, timeTable, ganttChart, ds);
	}

	@Override
	public void run() {
		bankers = new Bankers(allocatedTable, maximumTable, availableTable);
		Process currentProcess = null;
		int currentBurstTime = 0;
		int t = 0;

		// ganttChart.setTotalResourcesUsed(allocatedTable,
		// bankers.getCurrentAvailableTableData());
		if (bankers.isSafeState()) {
			while (true) {
				System.out.println("At time " + t);
				ganttChart.displayTimeAndAvailableData(t, bankers.getCurrentAvailableTableData());
				bankers.updateJobQueue(t, processesQueue);
				ganttChart.displayUpdatedJobQueue(bankers.getJobQueue());
				bankers.requestResources(t, readyQueue);
				ganttChart.displayUpdatedJobQueue(bankers.getJobQueue());
				ganttChart.displayUpdatedReadyQueue(readyQueue);

				// fillReadyQueue(t);
				sortReadyQueue();
				// If ready queue is empty and there is no current process running
				if (processesQueue.isEmpty() && readyQueue.isEmpty() && currentProcess == null) {
					System.out.println("Ready Queue is empty!");
					break;
				} else if (!readyQueue.isEmpty() && currentProcess == null && readyQueue.peek().getArrivalTime() <= t) {
					// there is a process waiting in ready queue available for execution and there
					// is no current process running

					currentProcess = readyQueue.get(0);

					// set 1st response time
					if (currentProcess.getFirstResponseTime() == -1) {
						currentProcess.setFirstResponseTime(t);
					}
					currentBurstTime++;

					ganttChart.displayUpdatedReadyQueue(readyQueue);
					ganttChart.updateGantt(t, currentProcess.getName());
					ds.invokeChartUpdate("SJF", t, currentProcess.getName());

					System.out.println(currentProcess.getName() + "[" + currentBurstTime + "]");
				} else if (currentProcess != null && currentBurstTime < currentProcess.getBurstTime()) {
					// there is still a current process executing

					// set 1st response time
					if (currentProcess.getFirstResponseTime() == -1) {
						currentProcess.setFirstResponseTime(t);
					}
					currentBurstTime++;

					ganttChart.displayUpdatedReadyQueue(readyQueue);
					ganttChart.updateGantt(t, currentProcess.getName());
					ds.invokeChartUpdate("SJF", t, currentProcess.getName());

					System.out.println(currentProcess.getName() + "[" + currentBurstTime + "]");
				}

				if (currentProcess != null && currentBurstTime == currentProcess.getBurstTime()) {
					currentProcess.setCompletionTime(t + 1);
					currentProcess
							.setTurnaroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
					currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
					currentProcess.setResponseTime(Math.abs(currentProcess.getFirstResponseTime() - currentProcess.getArrivalTime()));
					bankers.releaseResourcesForProcess(currentProcess);
					removeProcessFromReadyQueue(currentProcess);
					currentProcess = null;
					currentBurstTime = 0;
				}

				try {
					this.sleep(AlgoSimulator.visualizationSpeed); // delay
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				t++;
				ganttChart.displayPartialStats(bankers.computePartialStats(processesVector));
				ganttChart.displayTimeAndAvailableData(t, bankers.getCurrentAvailableTableData());
				ganttChart.displayUpdatedJobQueue(bankers.getJobQueue());
				ganttChart.displayUpdatedReadyQueue(readyQueue);
			}
			System.out.println("Done executing SJF!");

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

	public void removeProcessFromReadyQueue(Process currentProcess) {
		int size = readyQueue.getSize();
		for (int i = 0; i < size; i++) {
			if (readyQueue.get(i) == currentProcess) {
				readyQueue.remove(i);
				break;
			}
		}
	}

	public void sortReadyQueue() {
		int size = readyQueue.getSize();
		for (int i = 0; i < (size - 1); i++) {
			for (int j = 0; j < size - i - 1; j++) {
				if (readyQueue.get(j).getBurstTime() > readyQueue.get(j + 1).getBurstTime()) {
					Process temp = readyQueue.get(j);
					readyQueue.set(j, readyQueue.get(j + 1));
					readyQueue.set(j + 1, temp);
				}
			}
		}
	}
}
