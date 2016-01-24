import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.hardware.sensor.SensorMode;

public class BraitenbergVehicle {
	
	NXTLightSensor left; 
	NXTLightSensor right; 
	SensorMode smL;
	SensorMode smR;
	float[] arraySM = {0,0};
	RobotMotor leftMotor; 
	RobotMotor rightMotor;
	int powerR = 50;
	int powerL = 50;
	
	
	public BraitenbergVehicle() {
		left = new NXTLightSensor(SensorPort.S1);
		right = new NXTLightSensor(SensorPort.S4);
		
		this.leftMotor = new RobotMotor (MotorPort.A);
		this.rightMotor = new RobotMotor (MotorPort.D);	
		
		leftMotor.setPower(powerL);
	    rightMotor.setPower(powerR);
		
	}
	
	
	// Aggressive behaviour. Right sensor powers left motor, left sensor powers right motor.
	public void aggressive(){
		
		smL = left.getAmbientMode();
		smR = right.getAmbientMode();
		
		while(true){
			
			
			smL.fetchSample(arraySM, 0);
			smR.fetchSample(arraySM, 1);
			
			
			leftMotor.setPower((int) (arraySM[1]*100));
			rightMotor.setPower((int) (arraySM[0]*100));
			
			leftMotor.forward();
			rightMotor.forward();
			
			System.out.println("left: " + arraySM[0]);
			System.out.println("right: " + arraySM[1]);
		}
	}
	
	// Coward behaviour. Left sensor powers left motor, right sensor powers right motor.
	public void coward(){
		smL = left.getAmbientMode();
		smR = right.getAmbientMode();
		
		while(true){
			
			smL.fetchSample(arraySM, 0);
			smR.fetchSample(arraySM, 1);
			
			
			leftMotor.setPower((int) (arraySM[0]*100));
			rightMotor.setPower((int) (arraySM[1]*100));
			
			leftMotor.forward();
			rightMotor.forward();
			
			System.out.println("left: " + arraySM[0]);
			System.out.println("right: " + arraySM[1]);
		}
	}
	
	// Love behaviour. Inhibitory feedback. Left sensor powers left motor, right sensor powers right motor.
	public void love(){
		smL = left.getAmbientMode();
		smR = right.getAmbientMode();
		
		while(true){
			
			smL.fetchSample(arraySM, 0);
			smR.fetchSample(arraySM, 1);
			
			
			leftMotor.setPower((int) (((1/arraySM[0])*100))%100);
			rightMotor.setPower((int) (((1/arraySM[1])*100))%100);
			
			leftMotor.forward();
			rightMotor.forward();
			
			System.out.println("left: " + arraySM[0]);
			System.out.println("right: " + arraySM[1]);
		}
	}
	
	// Explore behaviour. Inhibitory feedback. Left sensor powers right motor, right sensor powers left motor.
	public void explore(){
		smL = left.getAmbientMode();
		smR = right.getAmbientMode();
		
		while(true){
			
			smL.fetchSample(arraySM, 0);
			smR.fetchSample(arraySM, 1);
			
			
			leftMotor.setPower((int) (((1/arraySM[1])*100))%100);
			rightMotor.setPower((int) (((1/arraySM[0])*100))%100);
			
			leftMotor.forward();
			rightMotor.forward();
			
			System.out.println("left: " + arraySM[0]);
			System.out.println("right: " + arraySM[1]);
		}
		
	}
	

}
