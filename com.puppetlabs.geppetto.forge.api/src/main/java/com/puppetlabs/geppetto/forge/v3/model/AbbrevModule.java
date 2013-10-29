package com.puppetlabs.geppetto.forge.v3.model;

import java.net.URI;

import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.forge.model.Entity;

public class AbbrevModule extends Entity {
	@Expose
	private URI uri;

	@Expose
	private String name;

	@Expose
	private AbbrevUser owner;

	/**
	 * Returns the name. Please note that this name does not include
	 * the owner so it is not a full module identifier.
	 * 
	 * @return the name of this module
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the owner
	 */
	public AbbrevUser getOwner() {
		return owner;
	}

	/**
	 * @return the uri for this module
	 */
	public URI getUri() {
		return uri;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(AbbrevUser owner) {
		this.owner = owner;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}
}
