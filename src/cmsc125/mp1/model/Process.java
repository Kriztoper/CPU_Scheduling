package cmsc125.mp1.model;

import java.awt.Color;

public class Process {

	private String name;
	private int arrivalTime;
	private int priorityNum;
	private int[] resources;
	private Color color;
	
	public Process(int arrivalTime, int priorityNum, int[] resources,
			String name, Color color) {
		setArrivalTime(arrivalTime);
		setPriorityNum(priorityNum);
		setResources(resources);
		setName(name);
		setColor(color);
	}
	
	@Override
	public String toString() {
		return "Arrival Time: " + getArrivalTime();
	}

	public int getBurstTime() {
		return resources[0];
	}
	
	public void decBurstTime() {
		resources[0] = resources[0] - 1;
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
}
