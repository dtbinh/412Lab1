import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

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
	
	
	public void powerAgressive(float left, float right){
		
		float ratio = left/right;
		float diff = right - left;
		
		
		if(diff > 0){
			powerR -= 0.1;
			powerL += 0.1;
			//powerL = (int) (powerR + powerR * ratio);
		}else{
			powerR += 0.1;
			powerL -= 0.1;	
			//powerR = (int) (powerL + powerL * ratio);
		}
		
	}
	
	public void aggressive(){
		
		smL = left.getAmbientMode();
		smR = right.getAmbientMode();
		
		while(true){
			
			
			smL.fetchSample(arraySM, 0);
			smR.fetchSample(arraySM, 1);
			
			//powerAgressive(arraySM[0],arraySM[1]);
			
			leftMotor.setPower((int) (arraySM[1]*100));
			rightMotor.setPower((int) (arraySM[0]*100));
			
			leftMotor.forward();
			rightMotor.forward();
			
			System.out.println("left: " + arraySM[0]);
			System.out.println("right: " + arraySM[1]);
			//System.out.println(powerR);
			//Delay.msDelay(10);
		}
	}
	
	public void coward(){
		smL = left.getAmbientMode();
		smR = right.getAmbientMode();
		
		while(true){
			
			smL.fetchSample(arraySM, 0);
			smR.fetchSample(arraySM, 1);
			
			//powerAgressive(arraySM[0],arraySM[1]);
			
			leftMotor.setPower((int) (arraySM[0]*100));
			rightMotor.setPower((int) (arraySM[1]*100));
			
			leftMotor.forward();
			rightMotor.forward();
			
			System.out.println("left: " + arraySM[0]);
			System.out.println("right: " + arraySM[1]);
		}
	}
	
	public void love(){
		smL = left.getAmbientMode();
		smR = right.getAmbientMode();
		
		while(true){
			
			smL.fetchSample(arraySM, 0);
			smR.fetchSample(arraySM, 1);
			
			//powerAgressive(arraySM[0],arraySM[1]);
			
			leftMotor.setPower((int) (((1/arraySM[0])*100))%100);
			rightMotor.setPower((int) (((1/arraySM[1])*100))%100);
			
			leftMotor.forward();
			rightMotor.forward();
			
			System.out.println("left: " + arraySM[0]);
			System.out.println("right: " + arraySM[1]);
		}
	}
	
	public void explore(){
		smL = left.getAmbientMode();
		smR = right.getAmbientMode();
		
		while(true){
			
			smL.fetchSample(arraySM, 0);
			smR.fetchSample(arraySM, 1);
			
			//powerAgressive(arraySM[0],arraySM[1]);
			
			leftMotor.setPower((int) (((1/arraySM[1])*100))%100);
			rightMotor.setPower((int) (((1/arraySM[0])*100))%100);
			
			leftMotor.forward();
			rightMotor.forward();
			
			System.out.println("left: " + arraySM[0]);
			System.out.println("right: " + arraySM[1]);
		}
		
	}
	

}
