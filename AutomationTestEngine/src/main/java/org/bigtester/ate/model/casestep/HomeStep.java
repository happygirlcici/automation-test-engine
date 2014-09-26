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

import org.bigtester.ate.annotation.StepLoggable;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.page.IHomepage;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeStep defines ....
 * 
 * @author Peidong Hu
 */
public class HomeStep extends BaseTestStep implements ITestStep{
	
	
	/** The homepg. */
	private IHomepage homepg;

	/**
	 * Gets the homepg.
	 * 
	 * @return the homepg
	 */
	public IHomepage getHomepg() {
		return homepg;
	}

	/**
	 * Sets the homepg.
	 * 
	 * @param homepg
	 *            the new homepg
	 */
	public void setHomepg(final IHomepage homepg) {
		this.homepg = homepg;
	}

	
	/* (non-Javadoc)
	 * @see org.bigtester.ate.model.casestep.ITestStep#doStep()
	 */
	/**
	 * {@inheritDoc}
	 * @throws PageValidationException 
	 */
	@StepLoggable
	public void doStep() throws PageValidationException{
		homepg.startHomepage();
		if (getExpectedResultAsserter() != null) {
			getExpectedResultAsserter().assertER();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMyWebDriver getMyWebDriver() {
		// TODO Auto-generated method stub
		return homepg.getMyWd();
	}

}
