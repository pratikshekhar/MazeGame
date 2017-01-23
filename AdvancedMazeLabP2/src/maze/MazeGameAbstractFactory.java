/*
 * SimpleMazeGame.java
 * Copyright (c) 2008, Drexel University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Drexel University nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DREXEL UNIVERSITY ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DREXEL UNIVERSITY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package maze;

import maze.ui.MazeViewer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
//import java.io.File;

/**
 * 
 * @author Sunny
 * @version 1.0
 * @since 1.0
 */
public class MazeGameAbstractFactory
{
	/**
	 * Creates a small maze.
	 */
	public Maze createMaze(){
//	
		Maze maze = MakeMaze();
		Room r1 = MakeRoom(0);
		Room r2 = MakeRoom(1);
		Door theDoor= MakeDoor(r1,r2);
		maze.addRoom(r1);
		maze.addRoom(r2);
		
		r1.setSide(Direction.North, MakeWall());
		r1.setSide(Direction.East, theDoor);
		r1.setSide(Direction.South, MakeWall());
		r1.setSide(Direction.West, MakeWall());
		
		r2.setSide(Direction.North, MakeWall());
		r2.setSide(Direction.East, MakeWall());
		r2.setSide(Direction.South, MakeWall());
		r2.setSide(Direction.West, theDoor);
		
		return maze;
		

	}

	public  Maze loadMaze(final String path, MazeFactory factory) throws IOException
	{
		Maze maze = factory.MakeMaze();
		
		BufferedReader br = null;
		HashMap<Integer, Room> room_ = new HashMap<>();
		HashMap<String, Door> door_ = new HashMap<>();
		String sCurrentLine;
		br = new BufferedReader(new FileReader(path)); 
		while ((sCurrentLine = br.readLine()) != null) {
			String[] parts = sCurrentLine.split(" ");
		        if (parts[0].equals("room")){
		        	Integer roomno = Integer.parseInt( parts[1]);
		            Room room = factory.MakeRoom(roomno);
		            room_.put(roomno, room);
		            maze.addRoom(room);
		       } 
		       else if (parts[0].equals("door")) {
		            	String key = parts[1];
		            	int r1 =Integer.parseInt( parts[2]);
		            	int r2 = Integer.parseInt( parts[3]);
		            	Door d0 = factory.MakeDoor(maze.getRoom(r1), maze.getRoom(r2));
			            	if(parts[4].equals("open")){
			            		d0.setOpen(true);
			            	}
			            	else{
			            		d0.setOpen(false);
			            	}
			            door_.put(key, d0);
		        }
			}	
		
		BufferedReader br2 = null;
		String sCurrentLine1;
		br2 = new BufferedReader(new FileReader(path)); 
		while ((sCurrentLine1 = br2.readLine()) != null) {
		String[] parts2 = sCurrentLine1.split(" ");
	        if (parts2[0].equals("room")){
	    	    String first = parts2[2];
				String second = parts2[3];
				String third = parts2[4];
				String fourth = parts2[5];
				
		        if (parts2[0].equals("room")){
		        		//first
			        	if(first.equals("wall")){	
							room_.get(Integer.parseInt(parts2[1])).setSide(Direction.North, factory.MakeWall());
						}
						else if(first.charAt(0)=='d'){
							room_.get(Integer.parseInt(parts2[1])).setSide(Direction.North, door_.get(first));
						}
						else {
							room_.get(Integer.parseInt(parts2[1])).setSide(Direction.North, room_.get(Integer.parseInt(first)));
						}
			        	
						//second
			        	if(second.equals("wall")){	
							room_.get(Integer.parseInt(parts2[1])).setSide(Direction.South, factory.MakeWall());
						}
						else if(second.charAt(0)=='d'){
							room_.get(Integer.parseInt(parts2[1])).setSide(Direction.South, door_.get(second));
						}
						else {
							room_.get(Integer.parseInt(parts2[1])).setSide(Direction.South, room_.get(Integer.parseInt(second)));
						}
			        	
			        	//third
						if(third.equals("wall")){	
							room_.get(Integer.parseInt(parts2[1])).setSide(Direction.East, factory.MakeWall());
						}
						else if(third.charAt(0)=='d'){
							room_.get(Integer.parseInt(parts2[1])).setSide(Direction.East, door_.get(third));
						}
						else {
							room_.get(Integer.parseInt(parts2[1])).setSide(Direction.East, room_.get(Integer.parseInt(third)));
						}
			        	
			        	//fourth
						if(fourth.equals("wall")){	
							room_.get(Integer.parseInt(parts2[1])).setSide(Direction.West, factory.MakeWall());
						}
						else if(fourth.charAt(0)=='d'){
							room_.get(Integer.parseInt(parts2[1])).setSide(Direction.West, door_.get(fourth));
						}
						else {
							room_.get(Integer.parseInt(parts2[1])).setSide(Direction.West, room_.get(Integer.parseInt(fourth)));
						}
			            
			       } 		
			}       
	        }
		br.close();
		br2.close();
		maze.setCurrentRoom(room_.get(0));
		return maze;
	}

	public  Maze MakeMaze(){ 
		return new Maze();
	}
	
	public Wall MakeWall(){
		return new Wall();
	}
	
	public Room MakeRoom(int id){
		return new Room(id);
	}
	
	public Door MakeDoor(Room r1, Room r2){
		return new Door(r1,r2);
	}
	
	public static void main(String[] args) throws IOException
	{ 
		
		if(args.length == 2) {
			if(args[0].equals("blue")){
		
				MazeGameAbstractFactory game = new MazeGameAbstractFactory();
				
				MazeFactory	blue = new BlueMazeFactory();
				Maze maze = game.loadMaze(args[1],blue);
			    MazeViewer viewer = new MazeViewer(maze);
			    viewer.run();
			    
			  
		
			}
			else if(args[0].equals("red") ){
				MazeGameAbstractFactory game = new MazeGameAbstractFactory();
				MazeFactory	red = new RedMazeFactory();
				Maze maze = game.loadMaze(args[1],red);
			    MazeViewer viewer = new MazeViewer(maze);
			    viewer.run();
				
//				
			}
			else if(args[1].equals("red")){
				MazeGameAbstractFactory game = new MazeGameAbstractFactory();
				MazeFactory	red = new RedMazeFactory();
				Maze maze = game.loadMaze(args[1],red);
			    MazeViewer viewer = new MazeViewer(maze);
			    viewer.run();
				
//				
			}
			else if(args[1].equals("blue")){
				MazeGameAbstractFactory game = new MazeGameAbstractFactory();
				MazeFactory	blue = new BlueMazeFactory();
				Maze maze = game.loadMaze(args[1],blue);
			    MazeViewer viewer = new MazeViewer(maze);
			    viewer.run();
				
//				
			}
			else{
				System.out.println("Please provide the correct input in order to load or create a maze game");
			}
		}
		
		
		// This part just creates a basic maze game without colors and when no path is provided, which means when no input is provided 
		else {
			MazeGameAbstractFactory game = new MazeGameAbstractFactory();
			Maze maze = game.createMaze();
			MazeViewer viewer = new MazeViewer(maze);
			viewer.run();
			
			
			
		}
	}
	
}
