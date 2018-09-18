package training.adv.robocode.impl.zhangrenyue;


import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

public class Strategy {
	static int TYPE_MOVE = 1;
	static int TYPE_AIM = 3;
	static int TYPE_SCAN = 5;
	
//	the default type
	protected int defaultType = 0;
	
	public Strategy() {
		
	}
	
	public boolean collidesWithType(int type) {
		if((defaultType & type)>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public void setType(int type) {
		defaultType = type;
	} 
	
	public int getType() {
		return defaultType;
	}
	
	public void onTick(Achilles me) {
		
	}
	
	public void onScannedRobot(Achilles me, ScannedRobotEvent event){
		
	}
	
	public void onHitWall(Achilles me, HitWallEvent event) {
		
	}
	
	public void onHitRobot(Achilles me, HitRobotEvent event) {
		
	}

}
