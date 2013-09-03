/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package org.cloudsmith.geppetto.forge.maven.plugin;

import org.slf4j.helpers.MarkerIgnoringBase;

@SuppressWarnings("serial")
public class NOPLogger extends MarkerIgnoringBase {

	final public void debug(String msg) {
	}

	final public void debug(String format, Object arg) {
	}

	public void debug(String format, Object... argArray) {
	}

	public void debug(String format, Object arg1, Object arg2) {
	}

	public void debug(String msg, Throwable t) {
	}

	public void error(String msg) {
	}

	public void error(String format, Object arg1) {
	}

	public void error(String format, Object... argArray) {
	}

	public void error(String format, Object arg1, Object arg2) {
	}

	public void error(String msg, Throwable t) {
	}

	@Override
	public String getName() {
		return "NOP";
	}

	public void info(String msg) {
	}

	public void info(String format, Object arg1) {
	}

	public void info(String format, Object... argArray) {
	}

	public void info(String format, Object arg1, Object arg2) {
	}

	public void info(String msg, Throwable t) {
	}

	public boolean isDebugEnabled() {
		return false;
	}

	public boolean isErrorEnabled() {
		return false;
	}

	public boolean isInfoEnabled() {
		return true;
	}

	public boolean isTraceEnabled() {
		return false;
	}

	public boolean isWarnEnabled() {
		return true;
	}

	public void trace(String msg) {
	}

	public void trace(String format, Object arg) {
	}

	public void trace(String format, Object... argArray) {
	}

	public void trace(String format, Object arg1, Object arg2) {
	}

	public void trace(String msg, Throwable t) {
	}

	public void warn(String msg) {
	}

	public void warn(String format, Object arg1) {
	}

	public void warn(String format, Object... argArray) {
	}

	public void warn(String format, Object arg1, Object arg2) {
	}

	public void warn(String msg, Throwable t) {
	}
}
