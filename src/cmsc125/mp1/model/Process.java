package cmsc125.mp1.model;

public class Process {

	private String name;
	private int arrivalTime;
	private int[] resources;
	
	public Process(int arrivalTime, int[] resources,
			String name) {
		setArrivalTime(arrivalTime);
		setResources(resources);
		setName(name);
	}
	
	@Override
	public String toString() {
		return "Arrival Time: " + getArrivalTime();
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
}
