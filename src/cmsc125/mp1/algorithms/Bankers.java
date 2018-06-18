package cmsc125.mp1.algorithms;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;

import cmsc125.mp1.model.Process;
import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;

public class Bankers {

	private int[][] allocatedTableData;
	private int[][] maximumTableData;
	private int[][] needTableData;
	private int[][] availableTableData;
	private String[][] statsTableData;
	private int[] currentAvailableTableData;
	private int[][] need;
	private ProcessesQueue jobQueue;
	private int currentJobQueueIndex;
	private int currentJobQueueSize;
	private int numProcesses;
	private int numResources;
	private double avgCompletionTime;
	private double avgTurnaroundTime;
	private double avgWaitingTime;
	private double avgResponseTime;

	public Bankers(JTable allocatedTable, JTable maximumTable,
			JTable availableTable) {
		initTableData(allocatedTable, maximumTable, availableTable);
		numProcesses = allocatedTableData.length;
		numResources = allocatedTableData[0].length;
		initCurrentAvailableTableData();
		jobQueue = new ProcessesQueue();
		currentJobQueueIndex = 0;
		currentJobQueueSize = 0;
	}

	public void initCurrentAvailableTableData() {
		currentAvailableTableData = new int[availableTableData[0].length];
		for (int i = 0; i < availableTableData[0].length; i++) {
			currentAvailableTableData[i] = availableTableData[0][i];
		}
	}

	private void calcNeed() {
		for (int i = 0; i < numProcesses; i++) {
			for (int j = 0; j < numResources; j++) {
				// calculating need matrix
				need[i][j] = maximumTableData[i][j] - allocatedTableData[i][j];
			}
		}
	}

	private boolean checkAllocatable(int i) {
		// checking if all resources for ith process can be allocated
		for (int j = 0; j < numResources; j++) {
			if (availableTableData[0][j] < need[i][j]) {
				return false;
			}
		}

		return true;
	}

	public boolean isSafeState() {
		calcNeed();
		boolean done[] = new boolean[numProcesses];
		int j = 0;

		while (j < numProcesses) { // until all process allocated
			boolean allocated = false;
			for (int i = 0; i < numProcesses; i++) {
				if (!done[i] && checkAllocatable(i)) { // trying to allocate

					for (int k = 0; k < numResources; k++) {
						availableTableData[0][k] = availableTableData[0][k] - need[i][k] + maximumTableData[i][k];
					}
					System.out.println("Allocated process : " + i);
					allocated = done[i] = true;
					j++;
				}
			}
			if (!allocated) { // no allocation
				break;
			}
		}
		if (j == numProcesses) { // all processes are allocated
			System.out.println("\nSAFE STATE!");
			return true;
		} else {
			System.out.println("DEADLOCK!");
			return false;
		}
	}

	public void initTableData(JTable allocatedTable,
			JTable maximumTable, JTable availableTable) {
		String[][] allocatedData = ((ResourcesTableModel)
				allocatedTable.getModel()).getData();
		allocatedTableData = new int[allocatedData.length][allocatedData[0].length];

		String[][] maximumData = ((ResourcesTableModel)
				maximumTable.getModel()).getData();
		maximumTableData = new int[maximumData.length][maximumData[0].length];

		String[][] availableData = ((ResourcesTableModel)
				availableTable.getModel()).getData();
		availableTableData = new int[availableData.length][availableData[0].length];

		needTableData = new int[allocatedData.length][allocatedData[0].length];
		need = new int[allocatedData.length][allocatedData[0].length];

		statsTableData = new String[maximumData.length + 2][4];


		for (int i = 0; i < allocatedData.length; i++) {
			for (int j = 0; j < allocatedData[0].length; j++) {
				allocatedTableData[i][j] =
						Integer.parseInt(allocatedData[i][j]);
				maximumTableData[i][j] =
						Integer.parseInt(maximumData[i][j]);
				if (i == 0) {
					availableTableData[i][j] =
							Integer.parseInt(availableData[i][j]);
				}
				if (j < 4) {
					statsTableData[i][j] = "";
				}
			}
		}
	}

	// insert process to job queue if time t = Arrival Time of job queue
	public ProcessesQueue updateJobQueue(int t, ProcessesQueue processesQueue) {
		ArrayList<Process> tempProcessesQueue = new ArrayList<Process>();

		System.out.print("Processes Queue: ");
		printAllProcessesInQueue(processesQueue);
		for (int i = 0; i < processesQueue.getSize(); i++) {
			System.out.println("Arrival time of " + processesQueue.get(i).getName() + " is " + processesQueue.get(i).getArrivalTime() + ", b = " + processesQueue.get(i).getBurstTime()); // to check if the job queue carries the appropriate processes in the job queue
			if (processesQueue.get(i).getArrivalTime() == t) {
				jobQueue.enqueue(processesQueue.get(i));
				currentJobQueueSize++;

				tempProcessesQueue.add(processesQueue.get(i));
			}
		}

		processesQueue.removeAll(tempProcessesQueue);

		System.out.print("Job Queue: ");
		printAllProcessesInQueue(jobQueue);
		System.out.print("Processes Queue: ");
		printAllProcessesInQueue(processesQueue);
		System.out.print("Available Resources: ");
		printAvailableResources();

		return processesQueue;
	}

	private void printAllProcessesInQueue(ProcessesQueue queue) {
		for (int i = 0; i < queue.getSize(); i++) {
			System.out.print(queue.get(i).getName() + ", ");
		}
		System.out.println();
	}

	private void printAvailableResources() {
		System.out.print("Current Available Data = ");
		for (int i = 0; i < currentAvailableTableData.length; i++) {
			System.out.print(currentAvailableTableData[i] + ", ");
		}
		System.out.println();
	}

	public void releaseResourcesForProcess(Process currentProcess) {
		System.out.println("Releasing resources = [");
		int processIndex = currentProcess.getProcessNumber();
		for (int i = 0; i < numResources; i++) {
			System.out.println("n[" + i + "] = " + needTableData[processIndex][i] + " -> " + currentAvailableTableData[i]);
			currentAvailableTableData[i] += maximumTableData[processIndex][i];
			allocatedTableData[processIndex][i] = 0;
//			System.out.print(currentAvailableTableData[i] + ", ");
		}
		System.out.println("]\n");
	}

	public void returnProcessToJobQueue(Process process) {
//		for (int i = 0; i < jobQueue.getSize(); i++) {
//			if (process.getName().equals(jobQueue.get(i).getName())) {
//				jobQueue.get(i).setAllocated(false);
//			}
//		}
		process.setAllocated(false);
	}

	public boolean hasAllProcessesInJobQueueAllocated() {
		for (int i = 0; i < jobQueue.getSize(); i++) {
			if (!jobQueue.get(i).isAllocated()) {
				return false;
			}
		}

		return true;
	}

	// inserts current process to ready queue if request for resources are granted
	public ProcessesQueue requestResources(int t, ProcessesQueue readyQueue) {
		// if need <= avail then
		// 		set alloc = alloc + need
		//		avail = avail - need
		//		insert process to ready queue
		// else
		while (currentJobQueueIndex < currentJobQueueSize) {
			System.out.println("currentJobQueueIndex = " + currentJobQueueIndex + " currentJobQueueSize = " + currentJobQueueSize);

//			if (jobQueue.get(currentJobQueueIndex).getArrivalTime() == t) {
				boolean canAllocate = true;

				int processIndex = jobQueue.get(currentJobQueueIndex).getProcessNumber();

				if (!jobQueue.get(currentJobQueueIndex).isAllocated()) {
					int[] neededData = new int[numResources];
					for (int i = 0; i < numResources; i++) {
						neededData[i] = maximumTableData[processIndex][i] - allocatedTableData[processIndex][i];
						System.out.println("n[" + i + "] = " + neededData[i] + " a[" + i + "] = " + currentAvailableTableData[i]);
						if (neededData[i] > currentAvailableTableData[i]) {
							System.out.println("Can't allocate");
							canAllocate = false;
							break;
						}
					}
					System.out.println();

					if (canAllocate) {
						for (int i = 0; i < numResources; i++) {
							needTableData[processIndex][i] = maximumTableData[processIndex][i] - allocatedTableData[processIndex][i];
							currentAvailableTableData[i] -= needTableData[processIndex][i];
							allocatedTableData[processIndex][i] += needTableData[processIndex][i];
						}
						readyQueue.enqueue(jobQueue.get(currentJobQueueIndex));
						jobQueue.get(currentJobQueueIndex).setAllocated(true);

						/* I will print all the necessary informations in this part */
						/*******************************************/
						System.out.print(jobQueue.get(currentJobQueueIndex).getName() + ", isAllocated = " + jobQueue.get(currentJobQueueIndex).isAllocated() + " currAvailable = [");
						for (int i = 0; i < numResources; i++) {
							System.out.print(currentAvailableTableData[i] + ", ");
						}
						System.out.print("]\nJob Queue = ");
						printAllProcessesInQueue(jobQueue);
						System.out.println();
						/*******************************************/
					}
				}

				// set penalty if last of job queue is not allocated then if it is encountered again as not allocated then it is in a deadlock
//				if (currentJobQueueIndex == currentJobQueueSize - 1 && !hasAllProcessesInJobQueueAllocated() && penaltyForDeadlock) {
//					System.err.println("Penalty 2: DEADLOCK!");
//					System.exit(0);
//				} else if (currentJobQueueIndex == currentJobQueueSize - 1 && !hasAllProcessesInJobQueueAllocated() && !penaltyForDeadlock) {
//					System.err.println("Penalty 1");
//					penaltyForDeadlock = true;
//				}

//			}

			currentJobQueueIndex++;
		}

		if (currentJobQueueIndex >= currentJobQueueSize) {
			currentJobQueueIndex = 0;
		}

		System.out.print("Ready Queue: ");
		printAllProcessesInQueue(readyQueue);
		System.out.println();

		return readyQueue;
	}

	/*public void allocateResource(int t) {
		while (index < arrivalTimes.length) {
			if (arrivalTimes[index] == t) {
				boolean canAllocate = true;
				int[] neededData = new int[maximumTableData[index].length];
				for (int i = 0; i < maximumTableData[index].length; i++) {
					neededData[i] = maximumTableData[index][i] - allocatedTableData[index][i];
					if (neededData[i] > currentAvailableTableData[i]) {
						canAllocate = false;
						break;
					}
				}
				if (canAllocate) {
					for (int i = 0; i < currentAvailableTableData.length; i++) {
						currentAvailableTableData[i] -= neededData[i];
						allocatedTableData[index][i] += neededData[i];
					}
					jobQueue.enqueue(new Process(arrivalTimes[index], priorityNum[index], allocatedTableData[index][0], allocatedTableData[index], ("P" + processCount), ColorConstants.getColor(processCount)));
				}
			}

			if (index == arrivalTimes.length - 1) {
				index = -1;
			}

			index++;
		}
	}*/

	public ProcessesQueue getJobQueue() {
		return jobQueue;
	}

	public double getAvgCompletionTime() {
		return avgCompletionTime;
	}

	public void setAvgCompletionTime(double avgCompletionTime) {
		this.avgCompletionTime = avgCompletionTime;
	}

	public double getAvgTurnaroundTime() {
		return avgTurnaroundTime;
	}

	public void setAvgTurnaroundTime(double avgTurnaroundTime) {
		this.avgTurnaroundTime = avgTurnaroundTime;
	}

	public double getAvgWaitingTime() {
		return avgWaitingTime;
	}

	public void setAvgWaitingTime(double avgWaitingTime) {
		this.avgWaitingTime = avgWaitingTime;
	}

	public double getAvgResponseTime() {
		return avgResponseTime;
	}

	public void setAvgResponseTime(double avgResponseTime) {
		this.avgResponseTime = avgResponseTime;
	}

	public int[] getCurrentAvailableTableData() {
		return currentAvailableTableData;
	}

	public int[][] getMaximumTableData() {
		return maximumTableData;
	}

	public int[][] getAllocatedTableData() {
		return allocatedTableData;
	}

	public String computePartialStats(Vector<Process> processesVector) {
		setAvgCompletionTime(0.0);
		setAvgTurnaroundTime(0.0);
		setAvgWaitingTime(0.0);
		setAvgResponseTime(0.0);
		int size = processesVector.size();
		int numExecutedProcesses = 0;
		for (int i = 0; i < size; i++) {
			Process process = processesVector.get(i);

			if (process.getFirstResponseTime() != -1) {
				numExecutedProcesses++;
				setAvgCompletionTime(getAvgCompletionTime() + (process.getCompletionTime()));
				setAvgTurnaroundTime(getAvgTurnaroundTime() + (process.getTurnaroundTime()));
				setAvgWaitingTime(getAvgWaitingTime() + (process.getWaitingTime()));
				setAvgResponseTime(getAvgResponseTime() + (process.getResponseTime()));
			}
		}

		setAvgCompletionTime(getAvgCompletionTime() / numExecutedProcesses);
		setAvgTurnaroundTime(getAvgTurnaroundTime() / numExecutedProcesses);
		setAvgWaitingTime(getAvgWaitingTime() / numExecutedProcesses);
		setAvgResponseTime(getAvgResponseTime() / numExecutedProcesses);

		return String.format("Avg CT = %.5f\nAvg TAT = %.5f\nAvg WT = %.5f\nAvg RT = %.5f", getAvgCompletionTime(), getAvgTurnaroundTime(), getAvgWaitingTime(), getAvgResponseTime());
	}

	public String[][] computeStats(Vector<Process> processesVector) {
		setAvgCompletionTime(0.0);
		setAvgTurnaroundTime(0.0);
		setAvgWaitingTime(0.0);
		setAvgResponseTime(0.0);
		int size = processesVector.size();
		for (int i = 0; i < size; i++) {
			Process process = processesVector.get(i);

			statsTableData[i][0] = process.getCompletionTime() + "";
			statsTableData[i][1] = process.getTurnaroundTime() + "";
			statsTableData[i][2] = process.getWaitingTime() + "";
			statsTableData[i][3] = process.getResponseTime() + "";

			System.out.println(process.getName() + " CT=" + process.getCompletionTime() + ", TAT=" + process.getTurnaroundTime() + ", WT=" + process.getWaitingTime() + ", RT=" + process.getResponseTime());
			setAvgCompletionTime(getAvgCompletionTime() + process.getCompletionTime());
			setAvgTurnaroundTime(getAvgTurnaroundTime() + process.getTurnaroundTime());
			setAvgWaitingTime(getAvgWaitingTime() + process.getWaitingTime());
			setAvgResponseTime(getAvgResponseTime() + process.getResponseTime());
		}

		setAvgCompletionTime(getAvgCompletionTime() / processesVector.size());
		setAvgTurnaroundTime(getAvgTurnaroundTime() / processesVector.size());
		setAvgWaitingTime(getAvgWaitingTime() / processesVector.size());
		setAvgResponseTime(getAvgResponseTime() / processesVector.size());

		// insert to statsTableData Avg data labels and values
		size = statsTableData.length;
		statsTableData[size-2][0] = "Avg CT";
		statsTableData[size-2][1] = "Avg TAT";
		statsTableData[size-2][2] = "Avg WT";
		statsTableData[size-2][3] = "Avg RT";
		statsTableData[size-1][0] = String.format("%.5f", getAvgCompletionTime());
		statsTableData[size-1][1] = String.format("%.5f", getAvgTurnaroundTime());
		statsTableData[size-1][2] = String.format("%.5f", getAvgWaitingTime());
		statsTableData[size-1][3] = String.format("%.5f", getAvgResponseTime());

		System.out.printf("Avg CT = %.5f, Avg TAT = %.5f, Avg WT = %.5f, Avg RT = %.5f \n", getAvgCompletionTime(), getAvgTurnaroundTime(), getAvgWaitingTime(), getAvgResponseTime());

		return statsTableData;
	}
}
