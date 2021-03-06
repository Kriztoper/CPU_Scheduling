package cmsc125.mp1.algorithms.disk;

import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JTable;

import cmsc125.mp1.constants.ScreenConstants;
import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.LineChartStage;

public class DiskSimulator {

	public static int visualizationSpeed;
	private String cpuAlgo;
	private String diskAlgo;
	private JTable diskTable;
	private DiskScheduling ds;
	private LineChartStage lineChartStage;
	private int numProcess;
	private int delay = 0;

	private ArrayList<Queue<Integer>> resultDiskQueue;

	public DiskSimulator(int numProcess, String cpuAlgos, String diskAlgo, JTable diskTable,
			int visualizationSpeed) {
		this.numProcess = numProcess;
		this.cpuAlgo = cpuAlgos;
		this.diskAlgo = diskAlgo;
		this.diskTable = diskTable;
		DiskSimulator.visualizationSpeed = visualizationSpeed;
	}

	public void invokeChartUpdate(String procDisk, int accessTime, String procName) {
		int processNumber = Integer.parseInt(procName.substring(1, procName.length()));

		try {
			int cylinder = resultDiskQueue.get(processNumber).poll();

			if (diskAlgo.equals("CSCAN") && cylinder == 0) {
				lineChartStage.updateChart(procName, accessTime + delay, cylinder);
				cylinder = resultDiskQueue.get(processNumber).poll();
				lineChartStage.updateChart(procName, accessTime + delay, cylinder);
				delay += 1;
				cylinder = resultDiskQueue.get(processNumber).poll();
			} else if (diskAlgo.equals("SCAN") && cylinder == 0) {
				lineChartStage.updateChart(procName, accessTime + delay, cylinder);
				delay += 1;
				cylinder = resultDiskQueue.get(processNumber).poll();
			}

			lineChartStage.updateChart(procName, accessTime + delay, cylinder);
		} catch (Exception e) {
			System.out.println("Error in DiskSimulator invokeChartUpdate method");
			e.printStackTrace();
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
			for (int j = 0; j < 10; j++) {
				if (!diskData[i][j].equals("-")) {
					try {
						ds.addPieces(Integer.parseInt(diskData[i][j]));
					} catch (NumberFormatException nfe) {
						System.out.println("Error in prepareData method of DiskSimulator");
						nfe.printStackTrace();
					}
				} else {
					break;
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
			lineChartStage.setHeight(ScreenConstants.HEIGHT/2 - 10);
			lineChartStage.setWidth(ScreenConstants.WIDTH/3 - 10);
			lineChartStage.setX(0);
			lineChartStage.setY(0);
			lineChartStage.setTitle(diskAlgo + "Disk Visualization for FCFS CPU");
		} else if (cpuAlgo.contains("SJF")) {
			lineChartStage.setHeight(ScreenConstants.HEIGHT/2 - 10);
			lineChartStage.setWidth(ScreenConstants.WIDTH/3);
			lineChartStage.setX(ScreenConstants.WIDTH*2/3);
			lineChartStage.setY(0);
			lineChartStage.setTitle(diskAlgo + "Disk Visualization for SJF CPU");
		} else if (cpuAlgo.contains("SRTF")) {
			lineChartStage.setHeight(ScreenConstants.HEIGHT/2 - 10);
			lineChartStage.setWidth(ScreenConstants.WIDTH/3);
			lineChartStage.setX(ScreenConstants.WIDTH/3);
			lineChartStage.setY(0);
			lineChartStage.setTitle(diskAlgo + "Disk Visualization for SRTF CPU");
		} else if (cpuAlgo.contains("RR")) {
			lineChartStage.setHeight(ScreenConstants.HEIGHT/2 - 10);
			lineChartStage.setWidth(ScreenConstants.WIDTH/3);
			lineChartStage.setX(ScreenConstants.WIDTH*2/3);
			lineChartStage.setY(ScreenConstants.HEIGHT/2 - 10);
			lineChartStage.setTitle(diskAlgo + "Disk Visualization for RR CPU");
		} else if (cpuAlgo.contains("NP PRIO")) {
			lineChartStage.setHeight(ScreenConstants.HEIGHT/2 - 10);
			lineChartStage.setWidth(ScreenConstants.WIDTH/3);
			lineChartStage.setX(ScreenConstants.WIDTH/3);
			lineChartStage.setY(ScreenConstants.HEIGHT/2 - 10);
			lineChartStage.setTitle(diskAlgo + "Disk Visualization for NP PRIO CPU");
		} else if (cpuAlgo.contains("PRIO")) {
			lineChartStage.setHeight(ScreenConstants.HEIGHT/2 - 10);
			lineChartStage.setWidth(ScreenConstants.WIDTH/3 - 10);
			lineChartStage.setX(0);
			lineChartStage.setY(ScreenConstants.HEIGHT/2 - 10);
			lineChartStage.setTitle(diskAlgo + "Disk Visualization for PRIO CPU");
		}
	}

	public void hide() {
		lineChartStage.hide();
	}
}
