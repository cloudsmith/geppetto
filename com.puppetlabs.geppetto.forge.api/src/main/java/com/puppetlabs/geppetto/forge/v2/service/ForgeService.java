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
package com.puppetlabs.geppetto.forge.v2.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpResponseException;
import com.puppetlabs.geppetto.forge.client.ForgeClient;
import com.puppetlabs.geppetto.forge.v2.model.HalLink;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.inject.Inject;

/**
 * Abstract base class for all services provided by the ForgeAPI API
 */
public abstract class ForgeService {
	@Inject
	private Gson gson;

	@Inject
	private ForgeClient forgeClient;

	/**
	 * Cleanly abort the currently executing request. This method does nothing if there is
	 * no executing request.
	 */
	public void abortCurrentRequest() {
		forgeClient.abortCurrentRequest();
	}

	ForgeClient getClient(boolean authenticated) throws IOException {
		if(authenticated)
			forgeClient.authenticate();
		return forgeClient;
	}

	/**
	 * Resolves a HAL link into a proper instance.
	 * 
	 * @param link
	 *            The link to resolve
	 * @param type
	 *            The type of the instance
	 * @return The resolved instance.
	 * @throws IOException
	 *             If the link could not be resolved.
	 */
	public <T> T resolveLink(HalLink link, Class<T> type) throws IOException {
		if(link == null)
			return null;

		String href = link.getHref();
		if(href == null)
			return null;

		if(!href.startsWith("/v2/"))
			throw new HttpResponseException(404, "Not found: " + href);

		return getClient(false).getV2(href.substring(4), null, type);
	}

	/**
	 * Converts a java bean into a hash map using the beans json representation.
	 * 
	 * @param bean
	 *            The bean to convert
	 * @return The resulting map
	 */
	protected Map<String, String> toQueryMap(Object bean) {
		Map<String, String> result = new HashMap<String, String>();
		if(bean != null) {
			JsonElement json = gson.toJsonTree(bean);
			if(json.isJsonObject()) {
				for(Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
					JsonElement element = entry.getValue();
					if(element.isJsonPrimitive())
						result.put(entry.getKey(), element.getAsString());
				}
			}
		}
		return result;
	}
}
