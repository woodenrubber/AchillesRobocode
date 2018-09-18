package training.adv.robocode.impl.zhangrenyue;

import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

public class BasicMovementStrategy extends Strategy{
	
//	the direction of my robot
	int direction = 1;
	
//	the preferred distance between me and enemy
	int defaultDistance = 10;
	
	int side = 1;
	
	ScannedRobotEvent event;
	
	public BasicMovementStrategy() {
		setType(TYPE_MOVE);
	}
	
	public void onScannedRobot(Achilles me, ScannedRobotEvent e) {
		if(event == null) {
			event = e;
			return;
		}
		
//		decide if my robot have to move away
//		if(e.getDistance() > defaultDistance)
//			side = 1;
//		else
//			side = -1;
//		
		
		
//		if my robot reach to the end, change the direction and move
//		it is the set of move
		if (me.getDistanceRemaining() == 0) {
			direction = direction *  -1; 	
			
//			keep some distance, but not too long
			me.setAhead((e.getDistance()) * direction );	//	对付苟哥的robot需要保持一定的距离，但是对付其他robot这种贴脸好像有奇效
		}
		
//		it is the set of angle
//		
		me.setTurnRightRadians( e.getBearingRadians() + Math.PI/2 - 0.5 * direction);
//		System.out.println(Math.PI);
		event = e;
	}
	
	public void onHitWall(Achilles me, HitWallEvent e) {
		direction = direction * -1;
		me.setTurnLeftRadians(Math.PI/2);
		me.setAhead(100 * direction);
		System.out.println("Hitting walls!");
	}
}
