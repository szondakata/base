package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class TrainControllerImpl implements TrainController {
	private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private int step = 0;
	private int referenceSpeed = 130;
	private int speedLimit;

	final ScheduledFuture<?> speedFollowerHandle;

	public TrainControllerImpl() {
		final Runnable speedFollower = () -> {
			logger.log(Level.INFO, "run");
			followSpeed();
		};

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		speedFollowerHandle = scheduler.scheduleAtFixedRate(speedFollower, 0, 10, TimeUnit.MILLISECONDS );
	}

	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
		    if(referenceSpeed + step > 0) {
                referenceSpeed += step;
            } else {
		        referenceSpeed = 0;
            }
		}
		enforceSpeedLimit();
		logger.log(Level.INFO, String.valueOf(referenceSpeed));
	}

	@Override
	public int getReferenceSpeed() {
		System.out.println(referenceSpeed);
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public boolean setJoystickPosition(int joystickPosition) {
		if (joystickPosition > 0 && referenceSpeed == speedLimit){
			return false;
		}
		this.step = joystickPosition;
		return true;
	}
}
