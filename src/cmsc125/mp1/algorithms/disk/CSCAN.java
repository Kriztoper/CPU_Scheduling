package cmsc125.mp1.algorithms.disk;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class CSCAN extends DiskScheduling {
	
	public CSCAN(int currentPiece, int maxPiece) {
		super(currentPiece, maxPiece);
	}

	@Override
	public Queue<Integer> process() {
		PriorityQueue<Integer> pq2 = new PriorityQueue<Integer>();
		PriorityQueue<Integer> pq1 = new PriorityQueue<Integer>();
		@SuppressWarnings("unchecked")
		LinkedList<Integer> pList = (LinkedList<Integer>) pieces.clone();
		
		//Scan algorithm passes to zero
		pList.add(0);
		pList.add(maxPiece);
		
		//Scan going to zero
		for (int num, i=0; i<pList.size(); i++){
			num = pList.get(i);
			if (num < currentPiece){
				pq2.add(num);
				pList.remove(i);
			}
		}
		
		//Sort remaining elements using Priority Queue, then add them to result list
		pq1.addAll(pList);
		while(!pq1.isEmpty())
			result.add(pq1.poll());
		
		while(!pq2.isEmpty())
			result.add(pq2.poll());
		
		return result;
	}

}
