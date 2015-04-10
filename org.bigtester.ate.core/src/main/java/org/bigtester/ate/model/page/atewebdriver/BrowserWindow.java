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

import org.openqa.selenium.WebDriver;


// TODO: Auto-generated Javadoc
/**
 * This class BrowserWindow defines ....
 * @author Peidong Hu
 *
 */
public class BrowserWindow {
	
	/** The window handle. */
	final private String windowHandle;
	
	/** The my wd. */
	final private WebDriver myWd;
	
	/**
	 * Instantiates a new browser window.
	 *
	 * @param winHandle the win handle
	 * @param myWd the my wd
	 */
	public BrowserWindow(String winHandle, WebDriver myWd) {
		this.windowHandle = winHandle;
		this.myWd = myWd;
	}
	
	/**
	 * Maximize.
	 */
	public void maximize() {
		obtainFocus();
		myWd.manage().window().maximize();
	}
	
	/**
	 * Close.
	 */
	public void close() {
		obtainFocus();
		myWd.close();
	}
	
	/**
	 * Obtain focus.
	 */
	public void obtainFocus() {
		myWd.switchTo().window(getWindowHandle());
	}
	/**
	 * @return the windowHandle
	 */
	public String getWindowHandle() {
		return windowHandle;
	}
	/**
	 * @return the myWd
	 */
	public WebDriver getMyWd() {
		return myWd;
	}
}
