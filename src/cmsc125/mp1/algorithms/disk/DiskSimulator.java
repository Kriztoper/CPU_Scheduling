package cmsc125.mp1.algorithms.disk;

import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JTable;

import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.LineChartStage;

public class DiskSimulator {

	private ArrayList<String> algos;
	private int numProcess, numResource;
	private JTable diskTable;
	public static int visualizationSpeed;
	private LineChartStage CSCANdisk, CLOOKdisk, FCFSdisk, LOOKdisk, SCANdisk, SSTFdisk;
	private ArrayList<Queue> resultCSCAN, resultCLOOK, resultFCFS, resultLOOK, resultSCAN, resultSSTF;

	public DiskSimulator(int numProcess, int numResource, ArrayList<String> algos, JTable diskTable, int visualizationSpeed) {
		this.numProcess = numProcess;
		this.numResource = numResource;
		this.algos = algos;
		this.diskTable = diskTable;
		DiskSimulator.visualizationSpeed = visualizationSpeed;
	}

	public void startSimulation() {
		
		if (algos.contains("CSCAN")) {
			resultCSCAN = prepareData("CSCAN");
			CSCANdisk = new LineChartStage(numProcess);
			CSCANdisk.setTitle("CSCAN");
			
			for (int accessTime=0; accessTime<numResource+2 ; accessTime++) {
				for(int i=1; i<=resultCSCAN.size(); i++){
					try {
						CSCANdisk.updateChart("P"+Integer.toString(i), accessTime, (int) resultCSCAN.get(i-1).poll());
					} catch (Exception e) {
					}
				}
			}
		}
		if (algos.contains("SCAN")) {
			resultSCAN = prepareData("SCAN");
			SCANdisk = new LineChartStage(numProcess);
			SCANdisk.setTitle("SCAN");
			
			for (int accessTime=0; accessTime<numResource+2 ; accessTime++) {
				for(int i=1; i<=resultSCAN.size(); i++){
					try {
						SCANdisk.updateChart("P"+Integer.toString(i), accessTime, (int) resultSCAN.get(i-1).poll());
					} catch (Exception e) {
					}
				}
			}
		}
		if (algos.contains("LOOK")) {
			resultLOOK = prepareData("LOOK");
			
			LOOKdisk = new LineChartStage(numProcess);
			LOOKdisk.setTitle("LOOK");
			
			for (int accessTime=0; accessTime<numResource ; accessTime++) {
				for(int i=1; i<=resultLOOK.size(); i++){
					LOOKdisk.updateChart("P"+Integer.toString(i), accessTime, (int) resultLOOK.get(i-1).poll());
				}
			}
		}
		if (algos.contains("CLOOK")) {
			resultCLOOK = prepareData("CLOOK");
			
			CLOOKdisk = new LineChartStage(numProcess);
			CLOOKdisk.setTitle("CLOOK");
			
			for (int accessTime=0; accessTime<numResource ; accessTime++) {
				for(int i=1; i<=resultCLOOK.size(); i++){
					CLOOKdisk.updateChart("P"+Integer.toString(i), accessTime, (int) resultCLOOK.get(i-1).poll());
				}
			}
		}
		if (algos.contains("FCFS")) {
			resultFCFS = prepareData("FCFS");
			
			FCFSdisk = new LineChartStage(numProcess);
			FCFSdisk.setTitle("FCFS");
			
			for (int accessTime=0; accessTime<numResource ; accessTime++) {
				for(int i=1; i<=resultFCFS.size(); i++){
					FCFSdisk.updateChart("P"+Integer.toString(i), accessTime, (int) resultFCFS.get(i-1).poll());
				}
			}
		}
		if (algos.contains("SSTF")) {
			resultSSTF = prepareData("SSTF");
			
			SSTFdisk = new LineChartStage(numProcess);
			SSTFdisk.setTitle("SSTF");
			
			for (int accessTime=0; accessTime<numResource ; accessTime++) {
				for(int i=1; i<=resultSSTF.size(); i++){
					SSTFdisk.updateChart("P"+Integer.toString(i), accessTime, (int) resultSSTF.get(i-1).poll());
				}
			}
		}
		
	}
	
	private ArrayList<Queue> prepareData(String algoType) {
		String[][] diskData = ((ResourcesTableModel) diskTable.getModel()).getData();
		ArrayList<Queue> result = new ArrayList<Queue>();
		
		DiskScheduling ds = null;
		if (algoType == "CSCAN")
			ds = new CSCAN(0,100);
		else if (algoType == "SCAN")
			ds = new SCAN(0,100);
		else if (algoType == "LOOK")
			ds = new LOOK(0,100);
		else if (algoType == "CLOOK")
			ds = new CLOOK(0,100);
		else if (algoType == "FCFS")
			ds = new FCFS(0,100);
		else if (algoType == "SSTF")
			ds = new SSTF(0,100);
		
		for (int i=0; i<numProcess; i++) {
			//loop cylinders needed per process
			for (int j = 0; j < numResource; j++) {
				ds.addPieces(Integer.parseInt(diskData[i][j]));
			}
			ds.process();
			result.add(ds.getResult());
			ds.printResult();
			ds.clear();
		}
		return result;
	}
}
