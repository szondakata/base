package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private static int speedLimit;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	final ScheduledFuture<?> speedFollowerHandle;

	public TrainControllerImpl() {
		final Runnable speedFollower = () -> {
			System.out.print("run");
			followSpeed();
		};

		speedFollowerHandle = scheduler.scheduleAtFixedRate(speedFollower, 0, 10, TimeUnit.MILLISECONDS );
	}

	public boolean isAlarm() {
		return alarm;
	}

	private boolean alarm = false;

	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
		    if(referenceSpeed+step > 0) {
                referenceSpeed += step;
            } else {
		        referenceSpeed = 0;
            }
		}

		enforceSpeedLimit();
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		TrainControllerImpl.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
			raiseAlarm();
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		if (joystickPosition > 0 && referenceSpeed == speedLimit){
			raiseAlarm();
		}
		this.step = joystickPosition;
	}

	private void raiseAlarm(){
		alarm = true;
	}
}
