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


import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.XsdBeanDefinitionParser;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.exception.StepExecutionException2;
import org.bigtester.ate.xmlschema.BaseTestStepBeanDefinitionParser;
import org.bigtester.ate.xmlschema.IXsdBeanDefinitionParser;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * This class JavaCodeStep defines ....
 * @author Peidong Hu
 *
 */

abstract public class AbstractBaseJavaCodedStep extends BaseTestStep{
	@Autowired
	@Nullable
	private IMyWebDriver myWebDriver;
	
	
	public IMyWebDriver getMyWebDriver() {
		final IMyWebDriver myWebDriver2 = myWebDriver;
		if (myWebDriver2== null) {
			throw GlobalUtils.createNotInitializedException("my web driver");
		} else {
			return myWebDriver2;
		}
	}
		
	
	/**
	 * Sets the my web driver.
	 *
	 * @param myD the new my web driver
	 */
	public void setMyWebDriver(IMyWebDriver myD) {
		myWebDriver = myD;
	}

	
}
