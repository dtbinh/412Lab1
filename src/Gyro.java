import lejos.hardware.sensor.*;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;;

public class Gyro {
	public static void main(String[] args) {
		SensorMode angleProvider;
		Port port = LocalEV3.get().getPort("S2");
		EV3GyroSensor gyro = new EV3GyroSensor(port);
		angleProvider = (SensorMode) gyro.getAngleMode();
		gyro.reset();
		float[] angle = {0};
		for (int i = 0; i < 8; i++) {
			angleProvider.fetchSample(angle, 0);
			System.out.println(angle[0]);
		}
		Button.waitForAnyPress();
	}
}
