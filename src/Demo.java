import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class Demo {

	public Demo() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		Robot robot = new Robot(55, 90, 0, 0);
		
		//NXTLightSensor left = new NXTLightSensor(SensorPort.S1); 
		//NXTLightSensor right = new NXTLightSensor(SensorPort.S4); 
		//SensorMode smL;
		//SensorMode smR;
		//float[] smArray = {0,0};
		
		/*
		while(true){
			smL = left.getAmbientMode();
			smR = right.getAmbientMode();
			
			smL.fetchSample(smArray, 0);
			smR.fetchSample(smArray, 1);
			
			System.out.println("Left: "+smArray[0]);
			System.out.println("Right: "+smArray[1]);
			Delay.msDelay(100);
		}
		*/
		//BraitenbergVehicle bv = new BraitenbergVehicle();
		
		//bv.aggressive();
		//bv.coward();
		//bv.love();
		//bv.explore();
		
		
		
		int[][] command = {
			      { 80, 60, 2},
			      { 60, 60, 1},
			      {-50, 80, 2}
			    };
		
		//robot.deadReckoning(command);
		//robot.driveRectangle();
		
		//robot.turnAngle(270);
		
		//robot.driveFigureEight();
		//robot.driveFigureEight();
		//robot.driveFigureEight();
		//robot.driveDistance(0.3);
		
		//robot.driveCircle();
		
		
	}
	
}
