package training.adv.robocode.impl.zhangrenyue;

import java.awt.Color;
import java.util.Random;
import java.util.Stack;

import robocode.*;


//import java.awt.Color;

//API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
* Achilles - a robot by Jason Zhang
*/

public class Achilles extends AdvancedRobot {
	/**
	 * run: Achilles's default behavior
	 */
	
	Stack<Strategy> strategies = new Stack<Strategy>();
	
	//set the object of the enemy robot
//	Enemy enemy = new Enemy(me, );
	
	//set the direction for our tank, 1 is the positive direction, -1 is the negative direction
	Integer myDirection = 1;
	
	//set the fire power
	Double firePower = null;
	
	
	public void run() {

		//set the gun and radar independent from the robot 
//		setAdjustGunForRobotTurn(true);
//		setAdjustRadarForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
//		this.setColors(Color.blue, Color.white, Color.white);
		
		
//		Initially we have 2 strategies in the stack, AttackStrategy will be added in one case of scanStrategy
		strategies.push(new ScanStrategy());
		strategies.push(new BasicMovementStrategy());
		
		while(true) {
			for(int i = 0; i < strategies.size(); i++) {
				strategies.get(i).onTick(this);
			}
			setBodyColor(new Color((int)Math.random() * 255, (int)Math.random() * 255, (int)Math.random() * 255));
			setGunColor(new Color((int)Math.random() * 255, (int)Math.random() * 255, (int)Math.random() * 255));
			setRadarColor(new Color((int)Math.random() * 255, (int)Math.random() * 255, (int)Math.random() * 255));
		}
	}
	
	public void addStrategy(Strategy strategy) {
//		System.out.println("adding strategy " + strategy.toString());
		
		for(int i = 0; i < strategies.size(); i++) {
			if(strategies.get(i).collidesWithType(strategy.getType())) {
//				System.out.println("Strategy colliding! Remove colliding Strategy" + strategies.get(i).toString());
				strategies.remove(i);
				i--;
			}
		}
		strategies.push(strategy);
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		for(int i = 0; i < strategies.size(); i++) {
			strategies.get(i).onScannedRobot(this, e);
		}
	}
	
	public void onHitWall( HitWallEvent e )
	{
		for ( int i = 0; i < strategies.size() ; i++ ) {
			strategies.get( i ).onHitWall( this, e );
		}
	}
	
	public void onHitRobot(HitRobotEvent e) {
		for ( int i = 0; i < strategies.size() ; i++ ) {
			strategies.get( i ).onHitRobot( this, e );
		}
	}
	
	

}
