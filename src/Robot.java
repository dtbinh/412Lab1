import lejos.hardware.Button;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class Robot {
	
	RobotMotor left; 
	RobotMotor right;
	
	double distancePerTick;
	double ticksPerRotation;
	double radiansPerTick;
	double heading, x, y;
	int power;
	
	public Robot(int power, double heading, double x, double y){
		this.left = new RobotMotor (MotorPort.A);
		this.right = new RobotMotor (MotorPort.D);	
		
		this.power = power;
		
		left.setPower(power);
	    right.setPower(power);
	    
	    distancePerTick = (Math.PI*0.056)/360;
	    ticksPerRotation = (2*Math.PI*0.05875)/ distancePerTick;
	    radiansPerTick = (2*Math.PI)/ ticksPerRotation;
	    this.heading = Math.toRadians(heading);
	    this.x = x;
	    this.y = y;
	}
	
	
	
	public void driveStraight(int delay){
		 left.resetTachoCount();
		 right.resetTachoCount();
		 left.setPower(power);
		 right.setPower(power);
	     left.forward();
	     right.forward();
	     Delay.msDelay(delay);
	     right.stop();
	     left.stop();
	     System.out.println("Left: "+left.getTachoCount());
	     System.out.println("Right: "+right.getTachoCount());
	}
	
	public void turnRight(){	
		System.out.println("Turning right");
		left.setPower(power);
		right.setPower(power);
		left.resetTachoCount();
		right.resetTachoCount();
		while(left.getTachoCount() < 200){
			left.forward();
			right.backward();
    	}
		right.stop();
	    left.stop();
	}
	
	public void driveCircle(){
		
		this.sweepAngle(360);
		/*
		left.resetTachoCount();
		//System.out.println("Left tacho count: " + left.getTachoCount());
		left.setPower(power);
		right.setPower(0);
		while(left.getTachoCount() < 1700){
			left.forward();
		}
	    left.stop();
	    */
	}
	
	public void driveRectangle(){
		driveStraight(2000);
		turnAngle(90);
		//turnRight();
		driveStraight(1000);
		turnAngle(90);
		//turnRight();
		driveStraight(2000);
		turnAngle(90);
		//turnRight();
		driveStraight(1000);
		turnAngle(90);
		//turnRight();
	}
	
	public void driveFigureEight() {	
		
		this.sweepAngle(355);
		this.sweepAngle(-355);
		
	}
	
	public void deadReckoning(int [][] command){
		for(int i = 0; i<3; i++){
			
			double deltaX, deltaY, deltaH, distance;
			
			left.setPower(command[i][0]);
			right.setPower(command[i][1]);
			left.resetTachoCount();
			right.resetTachoCount();
			
			left.forward();
			right.forward();
			
			long timer = System.currentTimeMillis();
			
			while(System.currentTimeMillis()-timer < command[i][2]*1000){
				distance = distancePerTick*(left.getTachoCount()+right.getTachoCount())/2;
				
				deltaH = (right.getTachoCount() - left.getTachoCount())
											*this.radiansPerTick / 2;
				
				deltaX = distance * Math.cos(this.heading);
				deltaY = distance * Math.sin(this.heading);
				
				this.x += deltaX;
				this.y += deltaY;
				this.heading += deltaH;
				
				if(this.heading < 0){
					this.heading += 2*Math.PI;
				}else if(this.heading > 2*Math.PI){
					this.heading -= 2*Math.PI;
				}
				
				left.resetTachoCount();
				right.resetTachoCount();
				Delay.msDelay(10);
			}
			right.stop();
			left.stop();
		}
		System.out.println("X pos(cm): " + Math.round(this.x*100));
		System.out.println("Y pos(cm): " + Math.round(this.y*100));
		System.out.println("Heading: " + Math.round(Math.toDegrees(this.heading)));
		Delay.msDelay(2000);
		System.out.println("Press button to continue");
		Button.waitForAnyPress();
		
	}
	
	public void sweepAngle(double angle){
		System.out.println("Turning: " + angle + " degrees");
		int ticks = (int) ((Math.toRadians(Math.abs(angle)) * 2) / this.radiansPerTick);

		
		
		if(angle < 0){
			right.resetTachoCount();
			while( right.getTachoCount() <= ticks){
				left.setPower(0);
				right.setPower(power);
				right.forward();
	    	}
		}else{
			left.resetTachoCount();
			while( left.getTachoCount() <= ticks){
				right.setPower(0);
				left.setPower(power);
				left.forward();
	    	}	
		}
		right.stop();
	    left.stop();
	}
	
	/**
	 * Turns robot passed angle
	 * 
	 * Positive is clockwise , negative is counter clockwise
	 */
	public void turnAngle(double angle){
		System.out.println("Turning: " + angle + " degrees");
		int ticks = (int) ((Math.toRadians(Math.abs(angle)) * 2) / this.radiansPerTick);

		
		
		if(angle < 0){
			right.resetTachoCount();
			while( right.getTachoCount() <= ticks/2){
				//left.setPower(0);
				right.setPower(power);
				left.setPower(power);
				right.forward();
				left.backward();
	    	}
		}else{
			left.resetTachoCount();
			while( left.getTachoCount() <= ticks/2){
				//left.setPower(power);
				//right.setPower(0);
				left.setPower(power);
				right.setPower(power);
				left.forward();
				right.backward();
	    	}	
		}
		right.stop();
	    left.stop();
	}
	
	public void driveDistance(double distance){
		double ticks = distance/this.distancePerTick;
		
		left.setPower(power);
		right.setPower(power);
		left.resetTachoCount();
		right.resetTachoCount();
		while((left.getTachoCount() < ticks) || (right.getTachoCount() < ticks)){
			left.forward();
			right.forward();
		}
		
		left.stop();
		right.stop();
	}
	
	

}

