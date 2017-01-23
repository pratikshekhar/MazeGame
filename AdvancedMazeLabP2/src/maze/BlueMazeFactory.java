package maze;

public  class BlueMazeFactory implements MazeFactory {
	public Wall MakeWall(){
		return new BlueWall();
	}
	
	public Room MakeRoom(int id){
		return new GreenRoom(id);
	}
	public Door MakeDoor(Room r1, Room r2){
		return new BrownDoor(r1,r2);
	}

	@Override
	public Maze MakeMaze() {
		// TODO Auto-generated method stub
		return new Maze();
	}

	


	
}
