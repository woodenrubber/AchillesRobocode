package training.adv.robocode.impl.zhangrenyue;

import java.awt.geom.Point2D;


import robocode.HitRobotEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class AttackStrategy extends Strategy{
	int lastScannedX = Integer.MIN_VALUE;
	int lastScannedY = Integer.MIN_VALUE;
	double predictedX = Integer.MIN_VALUE;
	double predictedY = Integer.MIN_VALUE;
	
	int noTargetTurns = 0;
	int AttackTurns = 0;
	int shotlessTurns = 0;
	
	public AttackStrategy() {
		setType(TYPE_AIM + TYPE_SCAN);
	}
	
	@Override
	public void onTick( Achilles me )
	{
		noTargetTurns++;
		AttackTurns++;
		
		// Initial radar movement
		
		double radaMovementRadians = (Math.PI/10);
		
//		1/15 * Math.PI;
		
		me.turnRadarLeftRadians(radaMovementRadians);
		
		if ( noTargetTurns > 5 ) {
			radaMovementRadians = -(Math.PI/10);
		}
		if ( noTargetTurns > 10 ) {
			radaMovementRadians = (Math.PI/10);
		}
		
		super.onTick(me);
	}
	
	@Override
	public void onScannedRobot( Achilles me, ScannedRobotEvent e )
	{
		
		noTargetTurns = 0;
//		Get fire power based on remaining energy,if energy is full enough, use max, if is too low, use min
		double firePower = Math.min( Rules.MAX_BULLET_POWER, Math.max( Rules.MIN_BULLET_POWER, me.getEnergy() / 30 ) );
//		Calculate bullet speed
		double bulletSpeed  = 20 - 3 * firePower;
//		Calculate the angle to the enemy
		double angleOffset = (me.getHeadingRadians() + e.getBearingRadians())% (2 * Math.PI);
		 
		
//		Calculate the coordination of the enemy
		lastScannedX = (int)(me.getX() + Math.sin(angleOffset) * e.getDistance());
		lastScannedY = (int)(me.getY() + Math.cos(angleOffset) * e.getDistance());
		
		double myX = me.getX();
		double myY = me.getY();
		double direction = me.getHeadingRadians() + e.getBearingRadians();
		double enemyHeading = e.getHeadingRadians();
		double enemyVelocity = e.getVelocity();
		
		double time = 0;
		double battleFieldHeight = me.getBattleFieldHeight();
		double battleFieldWidth = me.getBattleFieldWidth();
		
		predictedX = lastScannedX;
		predictedY = lastScannedY;
	
		
		
		
				while(time++ * bulletSpeed < Point2D.Double.distance(myX, myY, predictedX, predictedY)) {
					
//					the possible x, y for enemy in the next time
					predictedX = predictedX + Math.sin(enemyHeading) * enemyVelocity;
					predictedY = predictedY + Math.cos(enemyHeading) * enemyVelocity;
				} 


				
				double theta = Utils.normalAbsoluteAngle( Math.atan2( predictedX - me.getX(), predictedY - me.getY() ) );
				me.setTurnGunRightRadians( Utils.normalRelativeAngle( theta - me.getGunHeadingRadians() ) );
				me.setTurnRadarRightRadians( Utils.normalRelativeAngle( direction - me.getRadarHeadingRadians() ) );
				
//				System.out.println("The battlefield's width is " + battleFieldWidth + " and height is " + battleFieldHeight);	
				
//				if it is close enough, try every time to shot at max power
				if(e.getDistance() < 100 && me.getGunTurnRemainingRadians() < Math.PI/12 && me.getGunHeat() == 0) {
					firePower = Math.min(250/e.getDistance(), Rules.MAX_BULLET_POWER);
					me.setFire(firePower);
					shotlessTurns = 0;
					System.out.println("too close to the enemy! For the Alliance! Fire is " + firePower);
					System.out.println("Enemy distance is " + e.getDistance());
					if(e.getEnergy() < 10 && me.getEnergy() > e.getEnergy()) {
						me.setTurnRightRadians(e.getBearingRadians());
						me.setAhead(e.getDistance());
					}
				}else {
					shotlessTurns++;
				}
//				System.out.println("X is " + me.getX() + " Y is " + me.getY());
				
//				if it is the last battle
//				if(me.getEnergy() < 15 && e.getEnergy() < 15 && e.getDistance() > 100 && e.getDistance() < 300 && me.getGunTurnRemainingRadians() < Math.PI/15  && me.getGunHeat() == 0) {
//					firePower = Rules.MAX_BULLET_POWER;
//					me.setFire(firePower);
//					shotlessTurns = 0;
//					System.out.println("The last battle! Kill it!");
//				}else {
//					shotlessTurns++;
//				}
				
//				if my robot is losing too much, fire max to try to fight back
				if(e.getEnergy() - me.getEnergy() > 30 && me.getGunTurnRemainingRadians() < Math.PI/30 && me.getGunHeat()==0 && e.getDistance() < 200) {
					firePower = Rules.MAX_BULLET_POWER;
					me.setFire(firePower);
					shotlessTurns = 0;
					System.out.println("I am losing in the battle! fire immediately! fire is " +  firePower);
				}
				
//				if my robot energy is too low, try every time to fight back
				if(e.getDistance() >= 100 && me.getGunTurnRemainingRadians() < Math.PI/30 && me.getGunHeat()==0 && me.getEnergy() <= 15 ) {
//					firePower = Math.min( Rules.MAX_BULLET_POWER, Math.max( Rules.MIN_BULLET_POWER, me.getEnergy() / 5 ) );
					firePower = Rules.MAX_BULLET_POWER;
					me.setFire(firePower);
					shotlessTurns = 0;
					System.out.println("danger! fire back! the power is " + firePower);
				}else {
					shotlessTurns++; 
				}
				
//				if my robot energy is 15 to 30
				if(e.getDistance() < 300 && e.getDistance() >= 100 && me.getGunTurnRemainingRadians() < Math.PI/30 && me.getGunHeat()==0 && me.getEnergy() <= 30 && me.getEnergy() > 15) {
					firePower = Math.min( Rules.MAX_BULLET_POWER, Math.max( Rules.MIN_BULLET_POWER, me.getEnergy() / 15 ) );
					me.setFire(firePower);
					shotlessTurns = 0;
					System.out.println("2nd fire! the power is " + firePower);
				}else {
					shotlessTurns++; 
				}
				
				
//				if my robot energy is above 30
				if(e.getDistance() < 300 && e.getDistance() >= 100 &&  me.getGunTurnRemainingRadians() < Math.PI/30 && me.getGunHeat()==0 && me.getEnergy() > 30) {
					firePower = Math.min( Rules.MAX_BULLET_POWER, Math.max( Rules.MIN_BULLET_POWER, me.getEnergy() / 30 ) );
					me.setFire(firePower);
					shotlessTurns = 0;
					System.out.println("1st fire! the power is " + firePower);
				}else {
					shotlessTurns++; 
				}
				
				
				
		super.onScannedRobot( me, e );
	}
	
//	剩下这两个有余力再写
	
	/*
	@Override
	public void onHitByBullet(Achilles me, HitByBulletEvent event) {
		// TODO Auto-generated method stub
		super.onHitByBullet(me, event);
	}
	*/ 
	@Override
	public void onHitRobot(Achilles me, HitRobotEvent event) {
		
		me.setTurnGunRightRadians(event.getBearingRadians());

		if(me.getEnergy() > event.getEnergy()) {
			me.setAhead(50);
			System.out.println("Make our tank great again!");
		}
		double firePower = Rules.MAX_BULLET_POWER;
		if(me.getGunHeat() == 0 && me.getGunTurnRemaining() < Math.PI/12) {
			me.setFire(firePower);
			super.onHitRobot(me, event);
			System.out.println("For the Alliance! Destroying the Horde! Fire is " + firePower);
		}
		
	}
	
	
}
