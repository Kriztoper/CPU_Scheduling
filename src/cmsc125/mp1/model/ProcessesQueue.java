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
}
