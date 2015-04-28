/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2015, Montreal PROT, or individual contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Montreal PROT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.bigtester.ate.model.casestep;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.RepeatStepRefreshable;
import org.bigtester.ate.annotation.RepeatStepRefreshable.RefreshDataType;
import org.bigtester.ate.model.data.IOnTheFlyData;
import org.bigtester.ate.model.data.IRepeatIncrementalIndex;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

// TODO: Auto-generated Javadoc
/**
 * This class StepDataLogger defines ....
 * 
 * @author Peidong Hu
 *
 */
@Aspect
public class StepDataLogger implements
		ApplicationListener<RepeatStepInOutEvent> {

	/** The on the flies. */
	final private Map<ITestStep, List<IOnTheFlyData<?>>> onTheFlies = new ConcurrentHashMap<ITestStep, List<IOnTheFlyData<?>>>(); // NOPMD

	/** The repeat step on the flies. */
	final private Map<RepeatStep, List<IOnTheFlyData<?>>> repeatStepOnTheFlies = new ConcurrentHashMap<RepeatStep, List<IOnTheFlyData<?>>>(); // NOPMD

	/** The repeat step logger. */
	@Nullable
	@Autowired
	private IRepeatStepExecutionLogger repeatStepLogger;

	/** The current execution step. */
	@Nullable
	private transient ITestStep currentExecutionStep;

	/**
	 * Log data.
	 *
	 * @param data
	 *            the data
	 */
	public void logData(IOnTheFlyData<?> data) {
		if (null == onTheFlies.get(currentExecutionStep)) {
			onTheFlies.put(currentExecutionStep,
					new ArrayList<IOnTheFlyData<?>>());
			onTheFlies.get(currentExecutionStep).add(data);
		} else if (!onTheFlies.get(currentExecutionStep).contains(data)) {
			onTheFlies.get(currentExecutionStep).add(data);
		}

	}

	private boolean isAlreadyLoggedInRepeatStep(IOnTheFlyData<?> data) {
		boolean alreadyLoggedInRepeatStep = false; //NOPMD

		for (Map.Entry<RepeatStep, List<IOnTheFlyData<?>>> entry : repeatStepOnTheFlies
				.entrySet()) {
			if (entry.getValue().contains(data)) {
				alreadyLoggedInRepeatStep = true;
				break;
			} 
			
		}
		return alreadyLoggedInRepeatStep;
	}

	private void logRepeatStepData(IOnTheFlyData<?> data, RepeatStep liveRepeat) {

		if (!isAlreadyLoggedInRepeatStep(data)) {
			if (repeatStepOnTheFlies.containsKey(liveRepeat)) {
				if (!repeatStepOnTheFlies.get(liveRepeat).contains(data))
					repeatStepOnTheFlies.get(liveRepeat).add(data);
			} else {
				repeatStepOnTheFlies.put(liveRepeat,
						new ArrayList<IOnTheFlyData<?>>());
				repeatStepOnTheFlies.get(liveRepeat).add(data);
			}

		}
	}

	/**
	 * @return the onTheFlies
	 */
	public Map<ITestStep, List<IOnTheFlyData<?>>> getOnTheFlies() {
		return onTheFlies;
	}

	/**
	 * @return the currentExecutionStep
	 */
	public ITestStep getCurrentExecutionStep() {
		final ITestStep currentExecutionStep2 = currentExecutionStep;
		if (currentExecutionStep2 == null) {
			throw GlobalUtils
					.createNotInitializedException("currentExecutionStep");
		} else {
			return currentExecutionStep2;
		}
	}

	/**
	 * @param currentExecutionStep
	 *            the currentExecutionStep to set
	 */
	public void setCurrentExecutionStep(ITestStep currentExecutionStep) {
		this.currentExecutionStep = currentExecutionStep;
	}

	/**
	 * Log.
	 *
	 * @param joinPoint_p
	 *            the join point_p
	 */
	@SuppressWarnings("unchecked")
	@Before("@annotation(org.bigtester.ate.annotation.StepLoggable)")
	public void log(final JoinPoint joinPoint_p) {

		ITestStep bts = (ITestStep) joinPoint_p.getTarget();
		if (bts == null)
			throw GlobalUtils
					.createInternalError("stepresultmaker log function.");
		currentExecutionStep = bts;
	}

	/**
	 * Process data log.
	 *
	 * @param jPoint
	 *            the j point
	 * @return true, if successful
	 */
	@After("@annotation(org.bigtester.ate.annotation.RepeatStepRefreshable)")
	// @Around("execution(public * org.bigtester.ate.annotation.RepeatStepRefreshable+.*(..))")
	public boolean processDataLog(JoinPoint jPoint) {
		if (getDataType(jPoint) == RefreshDataType.ONTHEFLY) {
			Object targ = jPoint.getTarget();
			if (null == targ)
				throw GlobalUtils
						.createInternalError("RepeatStepRefreshable pointcut error");
			if (targ instanceof IOnTheFlyData<?>) {
				logData((IOnTheFlyData<?>) targ);
			}
		}
		return true;
	}

	private RefreshDataType getDataType(JoinPoint thisJoinPoint) {

		MethodSignature methodSignature = (MethodSignature) thisJoinPoint
				.getSignature();
		String methodName = methodSignature.getMethod().getName();
		Class<?>[] parameterTypes = methodSignature.getMethod()
				.getParameterTypes();
		Method targetMethod;
		try {
			targetMethod = thisJoinPoint.getTarget().getClass()
					.getMethod(methodName, parameterTypes);
			return ((RepeatStepRefreshable) targetMethod
					.getAnnotation(RepeatStepRefreshable.class)).dataType();
		} catch (NoSuchMethodException | SecurityException e) {
			throw GlobalUtils.createInternalError("jvm class method error", e);
		}
		// targetMethod = methodSignature.getMethod();

	}

	/**
	 * @return the repeatStepLogger
	 */
	public IRepeatStepExecutionLogger getRepeatStepLogger() {
		final IRepeatStepExecutionLogger repeatStepLogger2 = repeatStepLogger;
		if (repeatStepLogger2 == null) {
			throw GlobalUtils
					.createNotInitializedException("repeat step logger in stepdatalogger");
		} else {
			return repeatStepLogger2;
		}
	}

	/**
	 * @param repeatStepLogger
	 *            the repeatStepLogger to set
	 */
	public void setRepeatStepLogger(RepeatStepExecutionLogger repeatStepLogger) {
		this.repeatStepLogger = repeatStepLogger;
	}

	/**
	 * @return the repeatStepOnTheFlies
	 */
	public Map<RepeatStep, List<IOnTheFlyData<?>>> getRepeatStepOnTheFlies() {
		return repeatStepOnTheFlies;
	}

	private void resetRepeatIncrementalIndex(RepeatStep rStep) {
		List<IOnTheFlyData<?>> onTheFlies = getRepeatStepOnTheFlies()
				.get(rStep);
		if (null == onTheFlies)
			return;
		for (IOnTheFlyData<?> data : onTheFlies) {
			if (data instanceof IRepeatIncrementalIndex) {
				((IRepeatIncrementalIndex) data).resetIndex();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onApplicationEvent(@Nullable RepeatStepInOutEvent event) {
		if (null == event)
			throw GlobalUtils.createInternalError("spring event");
		if (event.getInOutFlag() == RepeatStepInOutEvent.RepeatStepInOut.IN) {
			RepeatStep liveRepeatStep = GlobalUtils.getTargetObject(event
					.getSource());
			for (ITestStep step : liveRepeatStep.getRepeatingSteps()) {
				List<IOnTheFlyData<?>> dataList = onTheFlies.get(GlobalUtils
						.getTargetObject(step));
				if (null != dataList) {
					for (IOnTheFlyData<?> data : dataList)
						if (data != null)
							logRepeatStepData(data, liveRepeatStep);
				}
			}

		} else if (event.getInOutFlag() == RepeatStepInOutEvent.RepeatStepInOut.OUT) {
			resetRepeatIncrementalIndex((RepeatStep) GlobalUtils
					.getTargetObject(event.getSource()));
		}

	}
}