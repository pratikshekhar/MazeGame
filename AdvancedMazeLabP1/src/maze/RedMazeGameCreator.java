package maze;

public class RedMazeGameCreator extends MazeGameCreator {

	public Wall MakeWall(){
		return new RedWall();
	}
	
	public Room MakeRoom(int id){
		return new RedRoom(id);
	}
	public Door MakeDoor(Room r1, Room r2){
		return new Door(r1,r2);
	}
}
