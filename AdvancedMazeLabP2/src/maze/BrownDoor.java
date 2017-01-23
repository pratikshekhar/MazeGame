package maze;

import java.awt.Color;

public class BrownDoor extends Door{
	public BrownDoor(Room r1, Room r2) {
		super(r1, r2);
		// TODO Auto-generated constructor stub
	}

	public Color getColor()
	{
		return new Color(156,93,82);
	}
}
