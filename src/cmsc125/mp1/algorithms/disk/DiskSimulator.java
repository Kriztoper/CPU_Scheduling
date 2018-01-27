package cmsc125.mp1.algorithms.disk;

import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JTable;

import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.LineChartStage;

public class DiskSimulator {

	private ArrayList<String> cpuAlgos;
	private String diskAlgo;
	private int numProcess, numResource;
	private JTable diskTable;
	public static int visualizationSpeed;
	private LineChartStage FCFSProcDisk, SRTFProcDisk, RRProcDisk, PRIOProcDisk, NPPRIOProcDisk, SJFProcDisk;
	private ArrayList<Queue<Integer>> resultDiskQueue, FCFSqueue, SRTFqueue, RRqueue, PRIOqueue, NPPRIOqueue, SJFqueue;
	private DiskScheduling ds;

	public DiskSimulator(int numProcess, int numResource, ArrayList<String> cpuAlgos, String diskAlgo, JTable diskTable, int visualizationSpeed) {
		this.numProcess = numProcess;
		this.numResource = numResource;
		this.cpuAlgos = cpuAlgos;
		this.diskAlgo = diskAlgo;
		this.diskTable = diskTable;
		DiskSimulator.visualizationSpeed = visualizationSpeed;
	}

	public void invokeChartUpdate(String procDisk, int accessTime, String name) {
		int processNumber = Integer.parseInt(name.substring(1));
		
		try {
			if (procDisk == "FCFS")
				FCFSProcDisk.updateChart("P"+Integer.toString(processNumber), accessTime, (int) FCFSqueue.get(processNumber-1).poll());
			else if (procDisk == "SRTF")
				SRTFProcDisk.updateChart("P"+Integer.toString(processNumber), accessTime, (int) SRTFqueue.get(processNumber-1).poll());
			else if (procDisk == "RR")
				RRProcDisk.updateChart("P"+Integer.toString(processNumber), accessTime, (int) RRqueue.get(processNumber-1).poll());
			else if (procDisk == "PRIO")
				PRIOProcDisk.updateChart("P"+Integer.toString(processNumber), accessTime, (int) PRIOqueue.get(processNumber-1).poll());
			else if (procDisk == "NPPRIO")
				NPPRIOProcDisk.updateChart("P"+Integer.toString(processNumber), accessTime, (int) NPPRIOqueue.get(processNumber-1).poll());
			else if (procDisk == "SJF")
				SJFProcDisk.updateChart("P"+Integer.toString(processNumber), accessTime, (int) SJFqueue.get(processNumber-1).poll());
		} catch (Exception e) {}
	}
	
	@SuppressWarnings("unchecked")
	public void setupSimulation() {
		prepareData(0,100);
		
		if (cpuAlgos.contains("FCFS")) {
			FCFSProcDisk = new LineChartStage(numProcess);
			FCFSProcDisk.setTitle(diskAlgo+" Visualization for FCFS");
			FCFSqueue = (ArrayList<Queue<Integer>>) resultDiskQueue.clone();
		}
		if (cpuAlgos.contains("SJF")) {
			SJFProcDisk = new LineChartStage(numProcess);
			SJFProcDisk.setTitle(diskAlgo+" Visualization for SJF");
			SJFqueue = (ArrayList<Queue<Integer>>) resultDiskQueue.clone();
		}
		if (cpuAlgos.contains("SRTF")) {
			SRTFProcDisk = new LineChartStage(numProcess);
			SRTFProcDisk.setTitle(diskAlgo+" Visualization for SRTF");
			SRTFqueue = (ArrayList<Queue<Integer>>) resultDiskQueue.clone();
		}
		if (cpuAlgos.contains("RR")) {
			RRProcDisk = new LineChartStage(numProcess);
			RRProcDisk.setTitle(diskAlgo+" Visualization for RR");
			RRqueue = (ArrayList<Queue<Integer>>) resultDiskQueue.clone();
		}
		if (cpuAlgos.contains("PRIO")) {
			PRIOProcDisk = new LineChartStage(numProcess);
			PRIOProcDisk.setTitle(diskAlgo+" Visualization for PRIO");
			PRIOqueue = (ArrayList<Queue<Integer>>) resultDiskQueue.clone();
		}
		if (cpuAlgos.contains("NPPRIO")) {
			NPPRIOProcDisk = new LineChartStage(numProcess);
			NPPRIOProcDisk.setTitle(diskAlgo+" Visualization for NPPRIO");
			NPPRIOqueue = (ArrayList<Queue<Integer>>) resultDiskQueue.clone();
		}
	}
	
	private void prepareData(int start, int end) {
		String[][] diskData = ((ResourcesTableModel) diskTable.getModel()).getData();
		resultDiskQueue = new ArrayList<Queue<Integer>>();
		if (diskAlgo.equals("CLOOK"))
			ds = new CLOOK(start,end);
		else if (diskAlgo.equals("LOOK"))
			ds = new LOOK(start,end);
		else if (diskAlgo.equals("CSCAN"))
			ds = new CSCAN(start,end);
		else if (diskAlgo.equals("SCAN"))
			ds = new SCAN(start,end);
		else if (diskAlgo.equals("FCFS"))
			ds = new FCFS(start,end);
		else if (diskAlgo.equals("SSTF"))
			ds = new SSTF(start,end);
		
		System.out.println(diskAlgo);
		
		for (int i=0; i<numProcess; i++) {
			for (int j = 0; j < numResource; j++) {
				ds.addPieces(Integer.parseInt(diskData[i][j]));
			}
			ds.process();
			ds.printResult();
			resultDiskQueue.add(ds.getResult());
			ds.clear();
		}
	}
}
