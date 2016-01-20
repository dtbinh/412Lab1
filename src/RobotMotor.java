import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.Port;
import lejos.hardware.port.TachoMotorPort;

/**
 * 
 * RobotMotor extends NXTMotor and fixes resetTachoCount bug
 *
 */
public class RobotMotor extends NXTMotor {

	int zeroTachoCount;
	
	public RobotMotor(Port port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	public RobotMotor(TachoMotorPort port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	public RobotMotor(Port port, int PWMMode) {
		super(port, PWMMode);
		// TODO Auto-generated constructor stub
	}

	public RobotMotor(TachoMotorPort mport, int PWMMode) {
		super(mport, PWMMode);
		// TODO Auto-generated constructor stub
	}

	public int getTachoCount(){
		return (super.getTachoCount() - this.zeroTachoCount);
	}

	public void resetTachoCount(){
		this.zeroTachoCount = super.getTachoCount();
	}
	

}
