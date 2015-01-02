// Programmer: Max Downs
// masc0437
// Red ID: 814291745
// Programming Project 2
// Class: CS310 Data Structures
// Instructor: Alan Riggins

package maze;
import data_structures.*;

public class MazeSolver {

	// Here is our grid
	private MazeGrid grid;
	
	// Our stack we will use to mark each of the Gridcells
	private Stack<GridCell> stack;
	
	// Our queue we will use to keep track of our moves
	private Queue<GridCell> queue;
	
	// This is the dimension of the grid which we pass to the constructor
	private int dimension;
	
	// constructor
	public MazeSolver(int dimension){
	
		this.dimension = dimension;
		stack = new Stack<GridCell>();
		queue = new Queue<GridCell>();
		
		grid = new MazeGrid(this, dimension);
	}
	
	// the move function will determine the shortest route through the maze
	public boolean move(){
		
		// create a cell and assign it to the last cell in the maze
		// we will use this as the starting point the find the shortest way through the maze
		GridCell last = grid.getCell(dimension-1, dimension-1);
		
		int distance = last.getDistance();
	
		// determines if there is no shortest route through he maze
		if (distance == -1)
			return false;
		
		// push the last grid cell  object onto the stack
		stack.push(last);
		
		// while we have not reached the end of the maze
		while (distance != 0)
		{	
			// move right methodology
			// test to see if the move is valid and does the next move decrease the distance to the origin
			// this will be repeated for the move left, move up, and move down
			GridCell next = grid.getCell(last.getX()+1, last.getY());
			if (grid.isValidMove(next) && next.getDistance() < distance)
			{
				distance = next.getDistance();
				
				// last tracks shortest path
				last = next;
				
				// push the shortest path onto the stack
				stack.push(last);
			}
			
			// move left methodology
			next = grid.getCell(last.getX()-1, last.getY());
			if (grid.isValidMove(next) && next.getDistance() < distance)
			{
				distance = next.getDistance();
				
				// last tracks shortest path
				// by updated we keep track of the next cell distance
				last = next;
				
				// push the shortest path onto the stack
				stack.push(last);
			}
			
			// move up methodology
			next = grid.getCell(last.getX(), last.getY()+1);
			if (grid.isValidMove(next) && next.getDistance() < distance)
			{
				distance = next.getDistance();
				
				last = next;
				
				stack.push(last);
			}
			
			// move down methodology
			next = grid.getCell(last.getX(), last.getY()-1);
			if (grid.isValidMove(next) && next.getDistance() < distance)
			{
				distance = next.getDistance();
				
				last = next;
				
				stack.push(last);
			}
		}
		
		// if we reach a dead end
		while (!stack.isEmpty())
		{
			// we pop the marked moves off the stack
			grid.markMove(stack.pop());	
		}
		
		return true;
	}
	
	// the mark method will mark all the cells with their distances
	public void mark(){
		
		// we start at the beginning
		// as opposed to the move() we start at the beginning instead of the end
		GridCell first = grid.getCell(0, 0);
		first.setDistance(0);
		
		// save the first GridCell onto the queue
		queue.enqueue(first);
		
		// start while loop to systematically check and mark cells
		//  while the queue is not empty 
		while (!queue.isEmpty())
		{
			int x, y, distance;
			
			// we need a second GridCell object
			GridCell current = queue.dequeue();
			 
			x = current.getX();
			y = current.getY();
			distance = current.getDistance();
			
			// the next four if statements moves forward and backward along the x-y coordinates
			// we determine if the move is valid and hasn't already been visited
			// the distance is set, marked, and put on the queue
			// move right methodology
			GridCell next = grid.getCell(x+1,y);
			if (grid.isValidMove(next) && !next.wasVisited())
			{
				next.setDistance(distance+1);
				grid.markDistance(next);
				queue.enqueue(next);
			}
			
			// move left methodology
			next = grid.getCell(x-1,y);
			if (grid.isValidMove(next) && !next.wasVisited())
			{
				next.setDistance(distance+1);
				grid.markDistance(next);
				queue.enqueue(next);
			}
			
			// move up methodology
			next = grid.getCell(x,y+1);
			if (grid.isValidMove(next) && !next.wasVisited())
			{
				next.setDistance(distance+1);
				grid.markDistance(next);
				queue.enqueue(next);
			}
			
			// move down methodology
			next = grid.getCell(x,y-1);
			if (grid.isValidMove(next) && !next.wasVisited())
			{
				next.setDistance(distance+1);
				grid.markDistance(next);
				queue.enqueue(next);
			}
		}
	}
	
	// clears stack and queue
	public void reset(){
		stack.makeEmpty();
		queue.makeEmpty();
	}


	// the main method
	public static void main (String[]args){
		new MazeSolver(30);
	}
	
}