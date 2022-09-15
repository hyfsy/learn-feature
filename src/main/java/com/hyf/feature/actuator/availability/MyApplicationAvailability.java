package com.hyf.feature.actuator.availability;

import com.hyf.fsfts.util.spring.BeanUtil;
import org.springframework.boot.actuate.availability.AvailabilityStateHealthIndicator;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.stereotype.Component;

/**
 * 指示当前的应用状态
 *
 * @author baB_hyf
 * @date 2020/10/20
 * @see AvailabilityStateHealthIndicator
 */
@Component
public class MyApplicationAvailability implements ApplicationAvailability {

	@Override
	public <S extends AvailabilityState> S getState(Class<S> stateType, S defaultState) {

		System.out.println("enter getState(Class, S)");

		System.out.println(stateType.getName());
		System.out.println(defaultState);

		return defaultState;
	}

	@Override
	public <S extends AvailabilityState> S getState(Class<S> stateType) {

		System.out.println("enter getState(Class)");

		System.out.println(stateType.getName());
		S state = BeanUtil.instantiateClass(stateType);
		System.out.println(state);

		return state;
	}

	@Override
	public <S extends AvailabilityState> AvailabilityChangeEvent<S> getLastChangeEvent(Class<S> stateType) {

		System.out.println("enter getLastChangeEvent(Class)");

		System.out.println(stateType.getName());

		String s = "availability state test";

		S state = BeanUtil.instantiateClass(stateType);

		AvailabilityChangeEvent<S> availabilityStateAvailabilityChangeEvent = new AvailabilityChangeEvent<>(s, state);

		return availabilityStateAvailabilityChangeEvent;
	}
}
