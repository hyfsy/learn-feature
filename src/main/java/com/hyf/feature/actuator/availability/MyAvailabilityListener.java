package com.hyf.feature.actuator.availability;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author baB_hyf
 * @date 2020/10/20
 */
@Component
public class MyAvailabilityListener {

	@EventListener
	public void onStateChange(AvailabilityChangeEvent<? extends AvailabilityState> event) {
		if (event.getState() instanceof LivenessState) {
			onLiveStateChange((AvailabilityChangeEvent<LivenessState>) event);
		}
		else if (event.getState() instanceof ReadinessState) {
			onReadStateChange((AvailabilityChangeEvent<ReadinessState>) event);
		}
	}

	public void onLiveStateChange(AvailabilityChangeEvent<LivenessState> event) {
		switch (event.getState()) {
			case CORRECT:
				System.out.println("The application is running and its internal state is correct.");
				break;
			case BROKEN:
				System.out.println("The application is running but its internal state is broken.");
				break;
		}
	}

	public void onReadStateChange(AvailabilityChangeEvent<ReadinessState> event) {
		switch (event.getState()) {
			case ACCEPTING_TRAFFIC:
				System.out.println("The application is ready to receive traffic.");
				break;
			case REFUSING_TRAFFIC:
				System.out.println("The application is not willing to receive traffic.");
				break;
		}
	}
}
