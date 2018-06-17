package cmsc125.mp1.model;

import java.awt.Color;

public class Process {

	private String name;
	private int arrivalTime;
	private int priorityNum;
	private int burstTime;
	private int[] resources;
	private Color color;
	private boolean allocated;
	private boolean arrived;
	private int completionTime;
	private int turnaroundTime;
	private int waitingTime;
	private int firstResponseTime;
	private int responseTime;
	private int totalCylinders;

	public Process(int arrivalTime, int priorityNum, int burstTime, int[] resources,
			String name, Color color) {
		setArrivalTime(arrivalTime);
		setPriorityNum(priorityNum);
		setBurstTime(burstTime);
		setResources(resources);
		setName(name);
		setColor(color);
		setAllocated(false);
		setArrived(false);
		setFirstResponseTime(-1);
	}

	@Override
	public String toString() {
		return "Arrival Time: " + getArrivalTime();
	}

	public int getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	public void decBurstTime() {
		this.burstTime--;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int[] getResources() {
		return resources;
	}

	public void setResources(int[] resources) {
		this.resources = resources;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProcessNumber() {
		return Integer.parseInt(name.substring(1,name.length()));
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getPriorityNum() {
		return priorityNum;
	}

	public void setPriorityNum(int priorityNum) {
		this.priorityNum = priorityNum;
	}

	public boolean isAllocated() {
		return allocated;
	}

	public void setAllocated(boolean allocated) {
		this.allocated = allocated;
	}

	public boolean isArrived() {
		return arrived;
	}

	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}

	public int getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(int completionTime) {
		this.completionTime = completionTime;
	}

	public int getTurnaroundTime() {
		return turnaroundTime;
	}

	public void setTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getFirstResponseTime() {
		return firstResponseTime;
	}

	public void setFirstResponseTime(int firstResponseTime) {
		this.firstResponseTime = firstResponseTime;
	}

	public int getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}

	public int getTotalCylinders() {
		return totalCylinders;
	}

	public void setTotalCylinders(int totalCylinders) {
		this.totalCylinders = totalCylinders;
	}
}
