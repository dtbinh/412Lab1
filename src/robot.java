/*
*Version 0.8.1-beta
*/
import lejos.hardware.Button;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.EncoderMotor;
import lejos.utility.Delay;


public class robot {
	EncoderMotor left; 
	EncoderMotor right;
	
	double distancePerTick;
	double ticksPerRotation;
	double radiansPerTick;
	
	public robot(){

		this.left = new NXTMotor(MotorPort.A);
		this.right = new NXTMotor(MotorPort.D); 
		left.setPower(55);
	    right.setPower(55);

	    
	    distancePerTick = (Math.PI*0.054)/360;
	    ticksPerRotation = (Math.PI*0.6)/ distancePerTick;
	    radiansPerTick = (2*Math.PI)/ ticksPerRotation;
	}
	
	public void resetPower(){
		left.setPower(55);
	    right.setPower(55);
	}
	
	public void driveStraight(int delay){
		 left.resetTachoCount();
		 right.resetTachoCount();
	     left.forward();
	     right.forward();
	     Delay.msDelay(delay);
	     right.stop();
	     left.stop();
	     System.out.println("UM1: "+left.getTachoCount());
	     System.out.println("UM2: "+right.getTachoCount());
	}
	
	public void turnRight(){
		left.resetTachoCount();
		while(left.getTachoCount() < 200){
			left.forward();
			right.backward();
    	}
		 right.stop();
	     left.stop();
	}
	
	public void turnLeft(){
		right.resetTachoCount();
		while(left.getTachoCount() < 190){
			right.forward();
			left.backward();
    	}
		right.stop();
	    left.stop();
	}
	
	public void driveCircle(){
		left.resetTachoCount();
		right.setPower(0);
		while(left.getTachoCount() < 1700){
			left.forward();
		}
	    left.stop();
	    resetPower();
	}
	
	public void driveRectangle(){
		driveStraight(2000);
		turnRight();
		driveStraight(1000);
		turnRight();
		driveStraight(2000);
		turnRight();
		driveStraight(1000);
	}
	
	public void driveFigureEight(){
		left.resetTachoCount();
		right.setPower(0);
		while(left.getTachoCount() < 850){
			left.forward();
		}
	    left.stop();
	    
	    right.resetTachoCount();
	    right.setPower(55);
		left.setPower(0);
		while(right.getTachoCount() < 1700){
			right.forward();
		}
	    right.stop();
	    
	    left.resetTachoCount();
	    left.setPower(55);
		right.setPower(0);
		while(left.getTachoCount() < 850){
			left.forward();
		}
	    left.stop();
	    
	    resetPower();
	}
	
	public void deadReckoning(int [][] command){
		for(int i = 0; i<3; i++){
			left.setPower(command[i][0]);
			right.setPower(command[i][1]);
			left.resetTachoCount();
			right.resetTachoCount();
			right.forward();
			left.forward();
			
			long timer = System.currentTimeMillis();
			
			while(System.currentTimeMillis()-timer < command[i][2]*1000){
				
				
				
			}
			
			//Delay.msDelay(command[i][2]*1000);
			
			right.stop();
			left.stop();
			
		}
		
	}
	

	public void turnAngle(double angle){
		int ticks = (int) ((Math.toRadians(angle) * 2) / this.radiansPerTick); 
		
		left.resetTachoCount();
		while(left.getTachoCount() < ticks){
			//left.forward();
			//right.backward();
    	}
		//right.stop();
	    //left.stop();
		
		
	}
	

	public static void main(String[] args) {

		robot ev3 = new robot();
		
		//ev3.driveRectangle();
		
		//Delay.msDelay(500);
		
		//ev3.driveCircle();
		
		//Delay.msDelay(500);
		
		//ev3.driveFigureEight();
		

		
	}

	

}
