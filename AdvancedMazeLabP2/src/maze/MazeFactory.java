package maze;

public interface MazeFactory {

		  public Maze MakeMaze();

		  public Wall MakeWall();
		  

		  public Room MakeRoom(int roomNumber);
		   

		  public Door MakeDoor(Room r1, Room r2);

		

		

		

		
		    
}
