package org.cloudsmith.geppetto.injectable.standalone.impl;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.inject.Provider;

public class StandaloneHttpClientProvider implements Provider<HttpClient> {

	@Override
	public HttpClient get() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		final SchemeRegistry schemeRegistry = httpClient.getConnectionManager().getSchemeRegistry();
		try {
			schemeRegistry.register(new Scheme("https", 443, new SSLSocketFactory(new TrustSelfSignedStrategy())));
		}
		catch(Exception e) {
			// let's try without that ...
		}
		return httpClient;
	}
}
