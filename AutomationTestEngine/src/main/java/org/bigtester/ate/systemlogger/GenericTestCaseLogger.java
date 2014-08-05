/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2014, Montreal PROT, or individual contributors as
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
package org.bigtester.ate.systemlogger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.bigtester.ate.model.casestep.TestCase;
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.bigtester.ate.systemlogger.problems.StepExecutionProblem;
import org.bigtester.problomatic2.Problomatic;
import org.bigtester.problomatic2.handlers.SystemPrintlnHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

// TODO: Auto-generated Javadoc
/**
 * This class GenericSystemLogger defines ....
 * 
 * @author Peidong Hu
 * 
 */
@Aspect
public class GenericTestCaseLogger implements ApplicationContextAware {
	private ApplicationContext applicationContext = null;
	private TestCase currentTestCase;

	/**
	 * @return the currentTestCase
	 */
	public TestCase getCurrentTestCase() {
		return currentTestCase;
	}

	/**
	 * @param currentTestCase
	 *            the currentTestCase to set
	 */
	public void setCurrentTestCase(TestCase currentTestCase) {
		this.currentTestCase = currentTestCase;
	}

	@Pointcut("within(org.bigtester.ate..*)")
	private void selectAll() {
	}

	/**
	 * After throwing advice.
	 *
	 * @param joinPoint the join point
	 * @param error the error
	 * @throws InstantiationException the instantiation exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	@AfterThrowing(pointcut = "selectAll()", throwing = "error")
	public void afterThrowingAdvice(JoinPoint joinPoint, Throwable error)
			throws InstantiationException {
		if (error instanceof StepExecutionException
				&& joinPoint.getTarget() instanceof TestCase) {
			StepExecutionProblem sep = new StepExecutionProblem(
					joinPoint.getTarget(), (StepExecutionException) error);
			SystemPrintlnHandler sph = new SystemPrintlnHandler();
			Problomatic.addProblemHandlerForProblem(sep, sph);
			Problomatic.handleThrowable(joinPoint.getTarget(), error);

			System.out.println("There has been an exception: "
					+ error.getMessage() + "->"
					+ joinPoint.getTarget().toString() + "->"
					+ currentTestCase.getTestCaseName() + "->"
					+ currentTestCase.getCurrentTestStep().getStepName());

		}
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext) throws BeansException {
		System.out.println("setting context");
		this.applicationContext = applicationContext;
	}

}
