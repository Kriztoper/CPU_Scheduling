package cmsc125.mp1.algorithms.disk;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class CSCAN extends DiskScheduling {

	public CSCAN(int currentPiece, int maxPiece) {
		super(currentPiece, maxPiece);
	}

	@Override
	public Queue<Integer> process() {
		LinkedList<Integer> pList = (LinkedList<Integer>) pieces.clone();

		int startingPiece = pList.pollFirst();
		LinkedList<Integer> downwardList = new LinkedList<Integer>();
		LinkedList<Integer> upwardList = new LinkedList<Integer>();
		for (int i = 0; i < pList.size(); i++) {
			int currentPiece = pList.get(i);
			if (currentPiece <= startingPiece) {
				downwardList.add(currentPiece);
			} else {
				upwardList.add(currentPiece);
			}
		}

		Collections.sort(downwardList, Collections.reverseOrder());
		Collections.sort(upwardList, Collections.reverseOrder());

		result.add(startingPiece);
		for (int piece: downwardList) {
			result.add(piece);
		}
		result.add(0);
		result.add(maxPiece - 1);
		for (int piece: upwardList) {
			result.add(piece);
		}

		return result;
	}

}
