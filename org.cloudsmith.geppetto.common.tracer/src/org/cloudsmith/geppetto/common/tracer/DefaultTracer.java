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
package org.cloudsmith.geppetto.common.tracer;

/**
 * A default tracer that prints to stdout.
 */
public class DefaultTracer extends AbstractTracer implements ITracer {

	/**
	 * @param option
	 */
	public DefaultTracer(String option) {
		super(option);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.common.tracer.AbstractTracer#getStringProvider()
	 */
	@Override
	public IStringProvider getStringProvider() {
		IStringProvider s = super.getStringProvider();
		if(s == null)
			setStringProvider(new DefaultStringProvider());
		return super.getStringProvider();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.common.tracer.AbstractTracer#trace(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void trace(String message, Object... objects) {
		if(!isTracing())
			return;
		IStringProvider str = getStringProvider();
		System.out.print(message);
		for(int i = 0; i < objects.length; i++)
			System.out.print(str.doToString(objects[i]));
		System.out.println("");
	}
}
