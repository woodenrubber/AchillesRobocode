package training.adv.robocode.impl.zhangrenyue;


import robocode.ScannedRobotEvent;

public class ScanStrategy extends Strategy{
	public int ticks = 0;
	
	public ScanStrategy() {
		setType(TYPE_SCAN);
	}
	
//	add a new AttackStrategy every time when first turning is over
	public void onTick(Achilles me) {
		if(ticks == 0) {
			me.setTurnGunLeftRadians(2 * Math.PI);
		}
		
		if(ticks > 0 && me.getRadarTurnRemainingRadians() == 0) {
			me.addStrategy(new AttackStrategy());
		} 
		 
		me.scan();
		
		ticks++;
		super.onTick(me);
	}
	
	@Override
	public void onScannedRobot(Achilles me, ScannedRobotEvent enemy) {
		super.onScannedRobot(me, enemy);
	}

}
