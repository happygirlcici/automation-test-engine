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
package org.bigtester.ate.model.page.atewebdriver;

import java.util.ArrayList;
import java.util.List;

import org.bigtester.ate.GlobalUtils;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class ChromeFeatureProfile defines ....
 * @author Peidong Hu
 *
 */
public class ChromeFeatureProfile extends AbstractBrowserFeatureProfile {

	/** The Constant defaultUserDataDir. */
	private static final String DEFAULTUSERDATADIR = "tmpUserDataDir";
	
	/** The start arguments. */
	private List<String> startArguments = new ArrayList<String>();
	/** The user data dir. */
	@Nullable
	 private String testCaseChromeUserDataDir;

	/**
	 * Instantiates a new chrome feature profile.
	 */
	public ChromeFeatureProfile() {
		super();
		setTestCaseChromeUserDataDir(getWebDriverFeatureProfilePath() + DEFAULTUSERDATADIR);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getBrowserTypeString() {
		return MyChromeDriver.BROWSERTYPENAME;
	}

	/**
	 * @return the testCaseChromeUserDataDir
	 */
	public String getTestCaseChromeUserDataDir() {
		final String testCaseChromeUserDataDir2 = testCaseChromeUserDataDir;
		if (testCaseChromeUserDataDir2 == null) {
			throw GlobalUtils.createNotInitializedException("test case chrome user data dir");
		} else {
			return testCaseChromeUserDataDir2;
		}
	}

	/**
	 * @param testCaseChromeUserDataDir the testCaseChromeUserDataDir to set
	 */
	public void setTestCaseChromeUserDataDir(String testCaseChromeUserDataDir) {
		this.testCaseChromeUserDataDir = testCaseChromeUserDataDir;
	}

	/**
	 * @return the defaultuserdatadir
	 */
	public static String getDefaultuserdatadir() {
		return DEFAULTUSERDATADIR;
	}
	/**
	 * @return the startArguments
	 */
	public List<String> getStartArguments() {
		return startArguments;
	}
	/**
	 * @param startArguments the startArguments to set
	 */
	public void setStartArguments(List<String> startArguments) {
		this.startArguments = startArguments;
	}

	

}
