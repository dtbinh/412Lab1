import lejos.hardware.Button;

public class Demo {

	public Demo() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		Robot robot = new Robot(55, 90, 0, 0);
		
		for (int i = 0; i < 3; i++) {
			robot.shapes();
		}
		
		int[][] command = {
			      { 80, 60, 2},
			      { 60, 60, 1},
			      {-50, 80, 2}
			    };
		
		robot.deadReckoning(command);
		
		robot.left.close();
		robot.right.close();
		
		BraitenbergVehicle bv = new BraitenbergVehicle();
		
		Button.waitForAnyPress();
		bv.aggressive();
		Button.waitForAnyPress();
		bv.coward();
		Button.waitForAnyPress();
		bv.love();
		Button.waitForAnyPress();
		bv.explore();
		Button.waitForAnyPress();
	}
	
}
