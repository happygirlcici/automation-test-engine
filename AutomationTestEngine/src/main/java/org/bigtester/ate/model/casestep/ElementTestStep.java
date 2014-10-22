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
package org.bigtester.ate.model.casestep;

import java.util.ArrayList;
import java.util.List;

import org.bigtester.ate.annotation.StepLoggable;
import org.bigtester.ate.constant.ExceptionErrorCode;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.constant.TestCaseConstants;
import org.bigtester.ate.model.asserter.IExpectedResultAsserter;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.exception.StepExecutionException2;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.openqa.selenium.NoSuchElementException;

// TODO: Auto-generated Javadoc
/**
 * The Class TestStep defines ....
 * 
 * @author Peidong Hu
 */
public class ElementTestStep extends BaseTestStep implements ITestStep {
	// TOTO add pageObject as another member.

	/**
	 * Instantiates a new test step.
	 * 
	 * @param myWe
	 *            the my we
	 */
	public ElementTestStep(final MyWebElement myWe) {
		super();
		setMyWebElement(myWe);
	}

	/**
	 * {@inheritDoc}
	 * @throws PageValidationException 
	 */
	@StepLoggable
	public void doStep() throws StepExecutionException2, PageValidationException2 {
		try {
			getMyWebElement().doAction();
//			if (getExpectedResultAsserter() != null) {
//				for (int i=0; i < getExpectedResultAsserter().size(); i++) {
//					getExpectedResultAsserter().get(i).assertER();
//				}
//			}
		} catch (NoSuchElementException e) {
			StepExecutionException2 pve = new StepExecutionException2(
					ExceptionMessage.MSG_WEBELEMENT_NOTFOUND
							+ ExceptionMessage.MSG_SEPERATOR + e.getMessage(),
					ExceptionErrorCode.WEBELEMENT_NOTFOUND,
					this.getMyWebElement(),
					this.getMyWebDriver(),
					(TestCase) getApplicationContext().getBean(TestCaseConstants.BEANID_TESTCASE));
			pve.initCause(e);
			throw pve;
		}
		if (getExpectedResultAsserter() != null) {
			boolean flagThrowE = false;
			List<IExpectedResultAsserter> listAsserters = new ArrayList<IExpectedResultAsserter>();
			for (int i=0; i < getExpectedResultAsserter().size(); i++) {
				getExpectedResultAsserter().get(i).assertER2();
				if (getExpectedResultAsserter().get(i).getExecResult().isFlagFailCase()) {
					flagThrowE = true;
				}
				listAsserters.add(getExpectedResultAsserter().get(i));
			}
			if (flagThrowE) {
				PageValidationException2 pve = new PageValidationException2(
						ExceptionMessage.MSG_PAGE_VALIDATION_ERROR_HIGH,
						ExceptionErrorCode.PAGEVALIDATION_HIGH,
						listAsserters, getExpectedResultAsserter().get(0).getResultPage().getMyWd(),
						(TestCase) getApplicationContext().getBean(
								TestCaseConstants.BEANID_TESTCASE));
				throw pve;
			}
			
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMyWebDriver getMyWebDriver() {
		// TODO Auto-generated method stub
		return super.getMyWebElement().getMyWd();
	}
}
