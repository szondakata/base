package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainSensorImpl implements TrainSensor {

	private final TrainController controller;
	private final TrainUser user;
	private int speedLimit = 130;

	public TrainSensorImpl(TrainController controller, TrainUser user) {
		this.controller = controller;
		this.user = user;
	}

	private boolean alarm(int speedLimit){
		user.setAlarmState(speedLimit > 500 || speedLimit < 0 || speedLimit < controller.getReferenceSpeed() * 0.5);
		return (speedLimit > 500 || speedLimit < 0 || speedLimit < controller.getReferenceSpeed() * 0.5);
	}

	@Override
	public int getSpeedLimit() {
		return speedLimit;
	}

	@Override
	public void overrideSpeedLimit(int speedLimit) {
		if(alarm(speedLimit))
			return;
		this.speedLimit = speedLimit;
		controller.setSpeedLimit(speedLimit);
	}
}
