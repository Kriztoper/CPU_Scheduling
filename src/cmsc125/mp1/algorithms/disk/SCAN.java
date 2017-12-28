package cmsc125.mp1.algorithms.disk;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class SCAN extends DiskScheduling{

	public SCAN(int currentPiece, int maxPiece) {
		super(currentPiece, maxPiece);
	}

	@Override
	public Queue<Integer> process() {
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		Stack<Integer> stack = new Stack<Integer>();
		LinkedList<Integer> pList = pieces;
		
		//Scan algorithm passes to zero
		pList.add(0);
		
		//Scan going to zero
		for (int num, i=0; i<pList.size(); i++){
			num = pList.get(i);
			if (num < currentPiece){
				pq.add(num);
				pList.remove(i);
			}
		}
		while(!pq.isEmpty())
			stack.push(pq.poll());
		while(!stack.isEmpty())
			result.add(stack.pop());
		
		//Sort remaining elements using Priority Queue, then add them to result list
		pq.addAll(pList);
		while(!pq.isEmpty())
			result.add(pq.poll());
		
		return result;
	}
}
