package cmsc125.mp1.algorithms.disk;

import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JTable;

import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.LineChartStage;

public class DiskSimulator {

	private class ChartUpdate {
		public Integer accessTime, cylinder;
		public String procName;

		public ChartUpdate(String procName, Integer accessTime, Integer cylinder) {
			this.procName = procName;
			this.accessTime = accessTime;
			this.cylinder = cylinder;
		}
	}
	public static int visualizationSpeed;
	private String cpuAlgo;
	private String diskAlgo;
	private JTable diskTable;
	private DiskScheduling ds;
	private LineChartStage lineChartStage;
	private int numProcess, numResource;
	private ChartUpdate prevUpdate = new ChartUpdate("", 0, 0);

	private ArrayList<Queue<Integer>> resultDiskQueue;

	public DiskSimulator(int numProcess, int numResource, String cpuAlgos, String diskAlgo, JTable diskTable,
			int visualizationSpeed) {
		this.numProcess = numProcess;
		this.numResource = numResource;
		this.cpuAlgo = cpuAlgos;
		this.diskAlgo = diskAlgo;
		this.diskTable = diskTable;
		DiskSimulator.visualizationSpeed = visualizationSpeed;
	}

	public void invokeChartUpdate(String procDisk, int accessTime, String procName) {
		int processNumber = Integer.parseInt(procName.substring(1));

		if (!prevUpdate.procName.equals(procName) && prevUpdate.accessTime != 0) {
			if (!resultDiskQueue.isEmpty() && !prevUpdate.procName.equals(""))
				lineChartStage.renewSeries(prevUpdate.procName);
			lineChartStage.updateChart(procName, prevUpdate.accessTime, prevUpdate.cylinder);
		}

		try {
			int cylinder = resultDiskQueue.get(processNumber).poll();
			lineChartStage.updateChart(procName, accessTime, cylinder);
			prevUpdate = new ChartUpdate(procName, accessTime, cylinder);
		} catch (Exception e) {
		}
	}

	private void prepareData(int start, int end) {
		String[][] diskData = ((ResourcesTableModel) diskTable.getModel()).getData();
		resultDiskQueue = new ArrayList<Queue<Integer>>();
		if (diskAlgo.equals("CLOOK"))
			ds = new CLOOK(start, end);
		else if (diskAlgo.equals("LOOK"))
			ds = new LOOK(start, end);
		else if (diskAlgo.equals("CSCAN"))
			ds = new CSCAN(start, end);
		else if (diskAlgo.equals("SCAN"))
			ds = new SCAN(start, end);
		else if (diskAlgo.equals("FCFS"))
			ds = new FCFS(start, end);
		else if (diskAlgo.equals("SSTF"))
			ds = new SSTF(start, end);

		System.out.println(diskAlgo);

		for (int i = 0; i < numProcess; i++) {
			for (int j = 0; j < numResource; j++) {
				try {
					ds.addPieces(Integer.parseInt(diskData[i][j]));
				} catch (NumberFormatException nfe) {
				}
			}
			ds.process();
			ds.printResult();
			resultDiskQueue.add(ds.getResult());
			ds.clear();
		}
	}

	public void setupSimulation() {
		prepareData(0, 100);

		lineChartStage = new LineChartStage(numProcess);
		if (cpuAlgo.contains("FCFS")) {
			lineChartStage.setTitle(diskAlgo + "Disk Visualization for FCFS CPU");
		} else if (cpuAlgo.contains("SJF")) {
			lineChartStage.setTitle(diskAlgo + "Disk Visualization for SJF CPU");
		} else if (cpuAlgo.contains("SRTF")) {
			lineChartStage.setTitle(diskAlgo + "Disk Visualization for SRTF CPU");
		} else if (cpuAlgo.contains("RR")) {
			lineChartStage.setTitle(diskAlgo + "Disk Visualization for RR CPU");
		} else if (cpuAlgo.contains("NP PRIO")) {
			lineChartStage.setTitle(diskAlgo + "Disk Visualization for NP PRIO CPU");
		} else if (cpuAlgo.contains("PRIO")) {
			lineChartStage.setTitle(diskAlgo + "Disk Visualization for PRIO CPU");
		}
	}
}
