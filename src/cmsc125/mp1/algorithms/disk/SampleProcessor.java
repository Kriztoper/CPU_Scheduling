package cmsc125.mp1.algorithms.disk;

public class SampleProcessor {

	public static void main(String[] args) {
		System.out.println("Disk Scheduling Algorithms");
		System.out.println("----------------------------");
		
		FCFS fcfs = new FCFS(53, 199);
		fcfs.addPieces(98,183,37,122,14,124,65,67);
		fcfs.process();
		fcfs.printResult();
		System.out.println("Total FCFS Head Movement: " + fcfs.getTotalHeadMovement());
		System.out.println();		
		
		//ISSUES: result of Scan Head Movement is Different from that of Powerpoint
		SCAN scan = new SCAN(53,199);
		scan.addPieces(98,183,37,122,14,124,65,67);
		scan.process();
		scan.printResult();
		System.out.println("Total SCAN Head Movement: " + scan.getTotalHeadMovement());
		System.out.println();
		
		//ISSUE: need to confirm if backtracking i.e. 199 to zero is counted in head movement tally
		CSCAN cscan = new CSCAN(53,199);
		cscan.addPieces(98,183,37,122,14,124,65,67);
		cscan.process();
		cscan.printResult();
		System.out.println("Total C-SCAN Head Movement: " + cscan.getTotalHeadMovement());
		System.out.println();
		
		LOOK look = new LOOK(53,199);
		look.addPieces(98,183,37,122,14,124,65,67);
		look.process();
		look.printResult();
		System.out.println("Total LOOK Head Movement: " + look.getTotalHeadMovement());
		System.out.println();
		
		CLOOK clook = new CLOOK(53,199);
		clook.addPieces(98,183,37,122,14,124,65,67);
		clook.process();
		clook.printResult();
		System.out.println("Total C-LOOK Head Movement: " + clook.getTotalHeadMovement());
		System.out.println();
		
		SSTF sstf = new SSTF(53,199);
		sstf.addPieces(98,183,37,122,14,124,65,67);
		sstf.process();
		sstf.printResult();
		System.out.println("Total SSTF Head Movement: "+sstf.getTotalHeadMovement());
		System.out.println();
		
	}

}
