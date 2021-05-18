package hu.bme.mit.train.interfaces;

public interface TrainUser {

	int getJoystickPosition();

	void overrideJoystickPosition(int joystickPosition);

	void setAlarmState(boolean alarm);

	boolean getAlarmState();
}
