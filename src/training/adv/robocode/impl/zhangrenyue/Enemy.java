package training.adv.robocode.impl.zhangrenyue;


import java.awt.Event;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;



public class Enemy {
	/*
	 * This class is about the enemy robot
	 * author: Jason Zhang
	 */	
//	enemy's Point2D
	public Point2D.Double position;
	
//	enemy's ScannedRobotEvent
	public ScannedRobotEvent scannedEvent;
	
//	my robot
	public Achilles me;
	
//	enemy's coordination
	public double x, y;
	
//	enemy's name
	public String name;
	
//	enemy's energy
	public double energy; 
	
//	heading radians
	public double headingRadians;
	
//	bearing radians
	public double bearingRadians;
	
//	enemy's distance
	public double distance;
	
//	enemy's velocity
	public double volocity; 
	
//	the direction of the enemy towards me
	public double direction;
	
//	the time when scan enemy, notice that the time is the current turn of the battle round 
	public long scanTime;
	
}
