package cmsc125.mp1.model;

import java.util.ArrayList;

public class ProcessesQueue {

	private ArrayList<Process> processes;

	public ProcessesQueue() {
		processes = new ArrayList<Process>();
	}

	public Process peek() {
		return processes.get(0);
	}

	public void enqueue(Process process) {
		processes.add(process);
	}

	public Process dequeue() {
		return processes.remove(0);
	}

	public int getSize() {
		return processes.size();
	}

	public boolean isEmpty() {
		return processes.isEmpty();
	}
	
	public void sortByBurstTime() {
		int size = processes.size();
		for (int i = 0; i < (size - 1); i++) {
			for (int j = 0; j < size - i - 1; j++) {
				if (processes.get(j).
						getBurstTime() > 
						processes.get(j + 1).
						getBurstTime()) {
					Process temp = processes.get(j);
					processes.set(j, 
							processes.get(j + 1));
					processes.set(j + 1, temp);
				}
			}
		}
	}
	
	public void sortByPriorityNum() {
		int size = processes.size();
		for (int i = 0; i < (size - 1); i++) {
			for (int j = 0; j < size - i - 1; j++) {
				if (processes.get(j).
						getPriorityNum() > 
						processes.get(j + 1).
						getPriorityNum()) {
					Process temp = processes.get(j);
					processes.set(j, 
							processes.get(j + 1));
					processes.set(j + 1, temp);
				}
			}
		}
	}
	
	public void sortByArrivalTime() {
		int size = processes.size();
		for (int i = 0; i < (size - 1); i++) {
			for (int j = 0; j < size - i - 1; j++) {
				if (processes.get(j).
						getArrivalTime() > 
						processes.get(j + 1).
						getArrivalTime()) {
					Process temp = processes.get(j);
					processes.set(j, 
							processes.get(j + 1));
					processes.set(j + 1, temp);
					System.out.print(processes.get(j + 1).getName() + ", ");
				}
			}
		}
		System.out.println();
	}
}
