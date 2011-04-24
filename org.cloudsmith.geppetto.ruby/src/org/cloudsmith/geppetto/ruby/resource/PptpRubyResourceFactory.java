/**
 * Copyright (c) 2006-2009, Cloudsmith Inc.
 * The code, documentation and other materials contained herein have been
 * licensed under the Eclipse Public License - v 1.0 by the copyright holder
 * listed above, as the Initial Contributor under such license. The text of
 * such license is available at www.eclipse.org.
 */
package org.cloudsmith.geppetto.ruby.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;

/**
 * Factory for a Ruby (.rb) resource for translation of puppet specific ruby code
 * to puppet "target platform" model.
 *
 */
public class PptpRubyResourceFactory implements Factory {

	@Override
	public Resource createResource(URI uri) {
		return new PptpRubyResource(uri);
	}

}
