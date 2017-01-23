package maze;

public class BlueMazeGameCreator extends MazeGameCreator {
	public Wall MakeWall(){
		return new BlueWall();
	}
	
	public Room MakeRoom(int id){
		return new GreenRoom(id);
	}
	public Door MakeDoor(Room r1, Room r2){
		return new BrownDoor(r1,r2);
	}
}
