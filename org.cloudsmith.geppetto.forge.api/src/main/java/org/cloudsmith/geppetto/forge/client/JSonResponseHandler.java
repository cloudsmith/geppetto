/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.forge.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;

/**
 * A response handler that expects JSON content.
 * 
 * @param <V>
 */
public class JSonResponseHandler<V> implements ResponseHandler<V> {
	public static class ErrorResponse {
		@Expose
		private List<String> errors;

		public List<String> getErrors() {
			return errors;
		}

		@Override
		public String toString() {
			if(errors == null || errors.isEmpty())
				return "unknown reason";

			int top = errors.size();
			if(top == 1)
				return errors.get(0);

			StringBuilder bld = new StringBuilder();
			bld.append("Multiple errors [");
			bld.append(errors.get(0));
			for(int idx = 1; idx < top; ++idx) {
				bld.append(", ");
				bld.append(errors.get(idx));
			}
			bld.append(']');
			return bld.toString();
		}
	}

	protected static <T> T parseJson(Gson gson, InputStream stream, Type type) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, ForgeHttpClient.UTF_8), 2048);
		StringBuilder bld = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null) {
			bld.append(line);
			bld.append('\n');
		}
		try {
			return gson.fromJson(bld.toString(), type);
		}
		catch(JsonSyntaxException jpe) {
			throw new ForgeException("Parse exception converting JSON to object", jpe); //$NON-NLS-1$
		}
		finally {
			try {
				reader.close();
			}
			catch(IOException ignored) {
				// Ignored
			}
		}
	}

	private final Gson gson;

	private final Type type;

	public JSonResponseHandler(Gson gson, Type type) {
		this.gson = gson;
		this.type = type;
	}

	/**
	 * Create exception from response
	 * 
	 * @param response
	 * @param code
	 * @param status
	 * @return non-null newly {@link IOException}
	 */
	protected HttpResponseException createException(InputStream response, int code, String status) {
		String message;
		if(status != null && status.length() > 0)
			message = status;
		else
			message = "Unknown error occurred";
		return new HttpResponseException(code, message);
	}

	@Override
	public V handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		StatusLine statusLine = response.getStatusLine();
		int code = statusLine.getStatusCode();
		if(code >= 300) {
			String msg;
			try {
				ErrorResponse errors = parseJson(
					gson, ForgeHttpClient.getStream(response.getEntity()), ErrorResponse.class);
				if(errors == null)
					msg = statusLine.getReasonPhrase();
				else {
					msg = statusLine.getReasonPhrase() + ": " + errors;
				}
			}
			catch(Exception e) {
				// Just skip
				msg = statusLine.getReasonPhrase();
			}
			throw new HttpResponseException(statusLine.getStatusCode(), msg);
		}

		HttpEntity entity = response.getEntity();
		if(isOk(code)) {
			if(type == null)
				return null;
			return parseJson(ForgeHttpClient.getStream(entity), type);
		}
		throw createException(ForgeHttpClient.getStream(entity), code, statusLine.getReasonPhrase());
	}

	/**
	 * Does status code denote a non-error response?
	 * 
	 * @param code
	 * @return true if okay, false otherwise
	 */
	protected boolean isOk(final int code) {
		switch(code) {
			case HttpStatus.SC_OK:
			case HttpStatus.SC_CREATED:
			case HttpStatus.SC_ACCEPTED:
			case HttpStatus.SC_NO_CONTENT: // weird, but returned by DELETE calls
				return true;
			default:
				return false;
		}
	}

	/**
	 * Parse JSON to specified type
	 * 
	 * @param <V>
	 * @param stream
	 * @param type
	 * @return parsed type
	 * @throws IOException
	 */
	protected V parseJson(InputStream stream, Type type) throws IOException {
		return parseJson(gson, stream, type);
	}
}
