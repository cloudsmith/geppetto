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
package com.puppetlabs.geppetto.graph;

public enum GraphHrefType {
	JS {
		@Override
		public Class<? extends IHrefProducer> getHrefProducerClass() {
			return JavascriptHrefProducer.class;
		}
	},
	GITHUB {
		@Override
		public Class<? extends IHrefProducer> getHrefProducerClass() {
			return GithubURLHrefProducer.class;
		}
	},
	RELATIVE_FILE {
		@Override
		public Class<? extends IHrefProducer> getHrefProducerClass() {
			return RelativeFileHrefProducer.class;
		}
	},
	ABSOLUTE_FILE {
		@Override
		public Class<? extends IHrefProducer> getHrefProducerClass() {
			return AbsoluteFileHrefProducer.class;
		}
	},
	NONE {
		@Override
		public Class<? extends IHrefProducer> getHrefProducerClass() {
			return EmptyStringHrefProducer.class;
		}
	};

	public abstract Class<? extends IHrefProducer> getHrefProducerClass();
}
