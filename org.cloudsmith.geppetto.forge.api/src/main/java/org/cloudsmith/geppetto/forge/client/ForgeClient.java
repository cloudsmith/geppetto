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
package org.cloudsmith.geppetto.forge.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * The generic communication client. Typically implemented using HTTP. Other clients can be added for
 * specific purposes (like unit tests).
 */
public interface ForgeClient {
	/**
	 * Cleanly abort the currently executing request. This method does nothing if there is
	 * no executing request.
	 */
	void abortCurrentRequest();

	/**
	 * Authenticate, i.e. ask the server for the OAuth credentials unless we already have them
	 * 
	 * @throws IOException
	 *             if the authentication was unsuccessful
	 */
	void authenticate() throws IOException;

	/**
	 * Send DELETE request to URI
	 */
	void delete(String uri) throws IOException;

	/**
	 * Performs a GET command and writes the response to <code>output</code>.
	 * 
	 * @param uri
	 * @param params
	 * @param output
	 * @throws IOException
	 */
	void download(String uri, Map<String, String> params, OutputStream output) throws IOException;

	/**
	 * Executes a HTTP GET request. The http response is expected to be a JSON representation of
	 * an object of the specified <code>type</code>. The object is parsed and returned.
	 * 
	 * @param urlStr
	 *            The URL of the request
	 * @param params
	 *            Parameters to include in the URL
	 * @param type
	 *            The expected type of the result
	 * @return An object of the expected type
	 * @throws IOException
	 *             if the request could not be completed
	 */
	<V> V get(String urlStr, Map<String, String> params, Type type) throws IOException;

	/**
	 * Executes a HTTP GET request usign the legacy v1 API. The http response is expected to be a JSON representation of
	 * an object of the specified <code>type</code>. The object is parsed and returned.
	 * 
	 * @param urlStr
	 *            The URL of the request
	 * @param params
	 *            Parameters to include in the URL
	 * @param type
	 *            The expected type of the result
	 * @return An object of the expected type
	 * @throws IOException
	 *             if the request could not be completed
	 */
	<V> V getV1(String urlStr, Map<String, String> params, Type type) throws IOException;

	/**
	 * Patch data to URI
	 * 
	 * @param userPath
	 * @param params
	 * @param class1
	 * @return
	 */
	<V> V patch(String userPath, Object params, Class<V> type) throws IOException;

	/**
	 * Post to URI
	 * 
	 * @param uri
	 * @throws IOException
	 */
	void post(String uri) throws IOException;

	/**
	 * Post data to URI
	 * 
	 * @param <V>
	 * @param uri
	 * @param params
	 * @param type
	 * @return response
	 * @throws IOException
	 */
	<V> V postJSON(String uri, Object params, Class<V> type) throws IOException;

	/**
	 * Post using a MultiPart entity
	 * 
	 * @param uri
	 *            The URI to post the data to (relative to the client base URI)
	 * @param stringParts
	 *            Optional String parts to include in the post
	 * @param in
	 *            The stream from which data will be read
	 * @param mimeType
	 *            The mime type of the data
	 * @param fileName
	 *            The name of the data file
	 * @param fileSize
	 *            The size of the file in bytes
	 * @param type
	 *            The type of the expected return value
	 * @return
	 * @throws IOException
	 */
	<V> V postUpload(String uri, Map<String, String> stringParts, InputStream in, String mimeType, String fileName,
			long fileSize, Class<V> type) throws IOException;

	/**
	 * Put to URI
	 * 
	 * @param uri
	 * @throws IOException
	 */
	void put(String uri) throws IOException;

	/**
	 * Put data to URI
	 * 
	 * @param <V>
	 * @param uri
	 * @param params
	 * @param type
	 * @return response
	 * @throws IOException
	 */
	<V> V put(String uri, Object params, Class<V> type) throws IOException;

	/**
	 * Set the user agent to use for all subsequent requests performed by this client.
	 * 
	 * @param agent
	 *            The agent or <code>null</code> to use the default agent.
	 * @return this client
	 */
	ForgeClient setUserAgent(String agent);
}
