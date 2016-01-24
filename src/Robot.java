import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lejos.hardware.sensor.*;
/*
 * Robot class implements shape drawing, and dead reckoning with Lejos EV3
 */
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
	
	// Draws a rectangle, circle, and figure-eight.
	public void shapes() {
		this.driveRectangle();
		Button.waitForAnyPress();
		this.driveCircle();
		Button.waitForAnyPress();
		this.driveFigureEight();
		Button.waitForAnyPress();
	}
	
	public void errorStraight() {
		int command[][] = {{90,90,2}};
		this.deadReckoning(command);
	}
	
	public void errorRotating() {
		this.sweepAngle(45);
	}
	
	// Measures horizontal deviation from a straight line using a gyroscope.
	public void errorGyro() {
		EV3GyroSensor gyro = new EV3GyroSensor(LocalEV3.get().getPort("S2"));
		SensorMode angleProvider = (SensorMode) gyro.getAngleMode();
		gyro.reset();
		float[] angle = {0};
		
		left.resetTachoCount();
		right.resetTachoCount();
		
		long timeNow = System.currentTimeMillis();
		left.setPower(70);
		right.setPower(70);
		while (System.currentTimeMillis() - timeNow < 2000) {
			left.forward();
			right.forward();
			angleProvider.fetchSample(angle, 0);
			System.out.println(angle[0]);
		}
		left.stop();
		right.stop();
		System.out.println(left.getTachoCount());
		System.out.println(right.getTachoCount());
		Button.waitForAnyPress();
		
	}
	
	
	public void gyroMeasure(){
		EV3GyroSensor gyro = new EV3GyroSensor(LocalEV3.get().getPort("S2"));
		SensorMode angleProvider = (SensorMode) gyro.getAngleMode();
		gyro.reset();
		float[] angle = {0};
		for (int i = 0; i < 8; i++){
			angleProvider.fetchSample(angle, 0);
			System.out.println(angle[0]);
		}
		
	}
	
	public void driveCircle(){
		this.sweepAngle(360);
	}
	
	public void driveRectangle(){
		driveStraight(2000);
		turnAngle(90);
		driveStraight(1000);
		turnAngle(90);
		driveStraight(2000);
		turnAngle(90);
		driveStraight(1000);
		turnAngle(90);
	}
	
	public void driveFigureEight() {	
		this.sweepAngle(355);
		this.sweepAngle(-355);
	}
	
	/*
	 *  Deadreckoning. Figures out x and y coordinates and heading, while following
	 *  given command.
	 */
	public void deadReckoning(int [][] command){
		for(int i = 0; i<command.length; i++){
			
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
				
				//bounds heading between 0 and 2*Pi
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
	
	// Rotates wheel in opposite directions to turn the supplied angle.
	public void turnAngle(double angle){
		System.out.println("Turning: " + angle + " degrees");
		int ticks = (int) ((Math.toRadians(Math.abs(angle)) * 2) / this.radiansPerTick);

		
		
		if(angle < 0){
			right.resetTachoCount();
			while( right.getTachoCount() <= ticks/2){
				right.setPower(power);
				left.setPower(power);
				right.forward();
				left.backward();
	    	}
		}else{
			left.resetTachoCount();
			while( left.getTachoCount() <= ticks/2){
				left.setPower(power);
				right.setPower(power);
				left.forward();
				right.backward();
	    	}	
		}
		right.stop();
	    left.stop();
	}
	
	// Sweeps angle. Using one wheel as point around which to rotate.
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
	
	// Drives the supplied distance. Using the distancePerTick calculation to turn wheels correct amount.
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

