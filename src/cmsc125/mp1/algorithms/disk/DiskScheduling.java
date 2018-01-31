package cmsc125.mp1.algorithms.disk;

import java.util.LinkedList;
import java.util.Queue;

public abstract class DiskScheduling {
	protected LinkedList<Integer> pieces;
	protected Queue<Integer> result;
	protected final int currentPiece;
	protected int maxPiece = 0;
	protected int headMovement = 0;
	
	/**
	 * Initializes Disk Scheduling algorithm by identifying current piece
	 * pointed by the disk, and the maximum number of pieces.
	 * It is assumed that disk starts with 0 and ends with maxPiece.
	 * 
	 * @param currentPiece the piece at which disk is currently pointing
	 * @param maxPiece the maximum number of pieces in disk
	 */
	public DiskScheduling(int currentPiece, int maxPiece){
		this.currentPiece = currentPiece;
		this.maxPiece = maxPiece;
		pieces = new LinkedList<Integer>();
		result = new LinkedList<Integer>();
	}
	
	/**
	 * This method will depend on what type of Disk Scheduling algorithm is concerned
	 * @return the queue of traversals of the disk scheduling algorithm
	 */
	public abstract Queue<Integer> process();
	
	/**
	 * Add pieces to the queue
	 * @param n is/are the numbers to be added onto the queue
	 */
	public void addPieces(int ...n){
		for (int a: n){
			pieces.add(a);
		}
	}
	
	/**
	 * Clears all the listed pieces in the queue.
	 */
	public void clear(){
		pieces.clear();
		result = new LinkedList<Integer>();	
	}
	
	/**
	 * Returns the result queue. It will return an empty queue if process() has not been executed.
	 * @return the queue containing the order of disk traversal
	 */
	public Queue<Integer> getResult(){
		return result;
	}
	
	public int getTotalHeadMovement(){
		LinkedList<Integer> resultLL = new LinkedList<Integer>();
		
		//clone to LinkedList
		Queue<Integer> copy = new LinkedList<Integer>();
		copy.addAll(this.result);
		while(!copy.isEmpty())
			resultLL.add(copy.poll());
		
		//loop result to calculate headMovement
		headMovement += Math.abs(currentPiece - resultLL.get(0));
		for (int i=1; i<resultLL.size(); i++){
			headMovement += Math.abs(resultLL.get(i) - resultLL.get(i-1));
		}
		return headMovement;
	}
	
	public void printResult(){
		Queue<Integer> resultLL = new LinkedList<Integer>();
		resultLL.addAll(this.result);
		System.out.print(getClass().getSimpleName()+": ");
		while(!resultLL.isEmpty()){
			System.out.print(resultLL.poll()+", ");
		}
		System.out.println();
	}
}
