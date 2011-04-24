package org.cloudsmith.geppetto.pp.dsl.pptp;

import org.cloudsmith.geppetto.ruby.resource.PptpRubyResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.generic.GenericResourceServiceProvider;

/**
 * An IResourceServiceProvider for PPTP Ruby.
 * This implementation optimizes which .rb instances which will be visited by restricing {@link #canHandle(URI)} to only operate on the paths where
 * PPTP contributions can be made.
 */
public class PptpRubyResourceServiceProvider extends GenericResourceServiceProvider {

	/**
	 * Returns true for .rb files that make a contribution to PPTP.
	 * 
	 * This is the only difference from the default...
	 */
	@Override
	public boolean canHandle(URI uri) {
		if(super.canHandle(uri) && PptpRubyResource.detectLoadType(uri) != PptpRubyResource.LoadType.IGNORED)
			return true;
		return false;
	}

}
