package cmsc125.mp1.algorithms.disk;

import java.util.LinkedList;
import java.util.Queue;

public class SSTF extends DiskScheduling{
	
	public SSTF(int currentPiece, int maxPiece) {
		super(currentPiece, maxPiece);
	}

	@Override
	public Queue<Integer> process() {
		LinkedList<Integer> pList = pieces;
		
		int previousPiece = currentPiece;
		int shortestNext = -1;
		int index_SN = -1;
		int checkPiece;
		int checkCPdistance;
		
		while(pList.size()!=0){
			int shortestDistance = Integer.MAX_VALUE;
			for (int i=0; i<pList.size(); i++){
				checkPiece = pList.get(i);
				checkCPdistance = Math.abs(previousPiece - checkPiece);
				
				if (checkCPdistance < shortestDistance){
					shortestNext = checkPiece;
					shortestDistance = checkCPdistance;
					index_SN = i;
				}
			}
			result.add(shortestNext);
			pList.remove(index_SN);
			previousPiece = shortestNext;
		}
		
		return result;		
	}

}
