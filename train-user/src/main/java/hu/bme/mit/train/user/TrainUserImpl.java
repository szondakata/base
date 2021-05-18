package hu.bme.mit.train.user;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainUserImpl implements TrainUser {

	private final TrainController controller;
	private int joystickPosition;

	private boolean alarmed;

	public TrainUserImpl(TrainController controller) {
		this.controller = controller;
		alarmed = false;
	}

	@Override
	public int getJoystickPosition() {
		return joystickPosition;
	}

	@Override
	public void overrideJoystickPosition(int joystickPosition) {
		this.joystickPosition = joystickPosition;
		alarmed = !controller.setJoystickPosition(joystickPosition);
	}

	@Override
	public void setAlarmState(boolean alarm) {
		this.alarmed = alarm;
	}

	@Override
	public boolean getAlarmState() {
		return alarmed;
	}
}
