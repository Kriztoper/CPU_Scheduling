package cmsc125.mp1.algorithms.disk;

import java.util.Queue;

public class FCFS extends DiskScheduling{

	public FCFS(int currentPiece, int maxPiece) {
		super(currentPiece, maxPiece);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Queue<Integer> process() {
		result = (Queue<Integer>) pieces.clone();
		return result;
	}
}
