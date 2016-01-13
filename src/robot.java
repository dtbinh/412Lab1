/*
*Version 0.9.1-beta
*/
import lejos.hardware.Button;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lejos.hardware.motor.UnregulatedMotor;


public class robot {
	UnregulatedMotor left; 
	UnregulatedMotor right;
	
	public robot(){
		this.left = new UnregulatedMotor(MotorPort.A);
		this.right = new UnregulatedMotor(MotorPort.C); 
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
	
	public void circle(){
		left.resetTachoCount();
		right.setPower(0);
		while(left.getTachoCount() < 1500){
			left.forward();
		}
	    left.stop();
	}
	
	public static void main(String[] args) {

		robot ev3 = new robot();
        
		///ev3.driveStraight(2000);
        
		//ev3.turnRight();
		/*
		ev3.driveStraight(1000);
		
		ev3.turnRight();
		
		ev3.driveStraight(2000);
		
		ev3.turnRight();
		
		ev3.driveStraight(1000);
		
    	Button.waitForAnyPress();
    	*/
		
		ev3.circle();
		
	}

}
