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
package org.bigtester.ate.test.model.casestep;

import org.bigtester.ate.model.casestep.AbstractBaseJavaCodedStep;
import org.bigtester.ate.model.casestep.IJavaCodedStep;
import org.bigtester.ate.model.casestep.IStepJumpingEnclosedContainer;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

// TODO: Auto-generated Javadoc
/**
 * This class JavaCodedStepTest defines ....
 * @author Peidong Hu
 *
 */
public class JavaCodedStepFilloutTextArea extends AbstractBaseJavaCodedStep implements IJavaCodedStep {

	/** The testvalue. */
	final public static String TESTVALUE = "ABCD";
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doStep(@Nullable IStepJumpingEnclosedContainer container) throws StepExecutionException,
			PageValidationException, RuntimeDataException {
		
		WebElement webE = getMyWebDriver().getWebDriverInstance().findElement(By.tagName("textarea"));
		webE.clear();
		webE.sendKeys(TESTVALUE);
	}

}
