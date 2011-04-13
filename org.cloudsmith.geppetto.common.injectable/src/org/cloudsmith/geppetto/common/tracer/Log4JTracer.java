/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.common.tracer;

import org.apache.log4j.Logger;

/**
 * A tracer that traces to a log4j logger using the tracing option string as the
 * key to obtain a logger. The option is transformed to a logger name by replacing "/" with "." to
 * create a log4j hierarchical name.
 * 
 */
public class Log4JTracer extends AbstractTracer implements ITracer {

	private Logger log = null;

	public static final String B3_GLOBAL_DEBUG_OPTION = "org.eclipse.b3.backend/debug";

	/**
	 * Checks if platform is debugging, and if so, gets the debug option setting
	 * on the form "org.someorg.somebundle.etc./debug", "o.s.s.etc/debug/suboption".
	 * The options is also used to obtain a logger via log4j. The logger will have its level set to debug.
	 * The logger name is constructed by replacing "/" with "." to enable hierarchical
	 * configuration of the logger.
	 * 
	 * To activate a tracer, an ".options" file is needed in a bundle root where a line on the form: <br/>
	 * org.someorg.somebundle.etc./debug=false</br>
	 * Defines the default setting of the trace option. This also makes the option appear in an eclipse
	 * launch configuration for convenient setting in the launch dialog.
	 * 
	 * @param option
	 */
	public Log4JTracer(String option) {
		super(option);
		if(isTracing()) {
			log = Logger.getLogger(option.replace("/", "."));
			log.setLevel(org.apache.log4j.Level.DEBUG);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.common.tracer.ITracer#trace(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void trace(String message, Object... objects) {
		// in case code did not check and just traced
		if(!tracing)
			return;
		StringBuffer buf = new StringBuffer(message.length() + objects.length * 10);
		buf.append(message);
		for(int i = 0; i < objects.length; i++)
			buf.append(getStringProvider().doToString(objects[i]));
		log.debug(buf.toString());

	}

}
