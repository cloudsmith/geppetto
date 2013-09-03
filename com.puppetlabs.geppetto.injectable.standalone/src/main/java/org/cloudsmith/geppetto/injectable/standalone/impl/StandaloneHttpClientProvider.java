package com.puppetlabs.geppetto.injectable.standalone.impl;

import javax.inject.Named;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class StandaloneHttpClientProvider implements Provider<HttpClient> {

	@Inject(optional = true)
	@Named(CoreConnectionPNames.CONNECTION_TIMEOUT)
	private Integer connectonTimeout;

	@Inject(optional = true)
	@Named(CoreConnectionPNames.SO_TIMEOUT)
	private Integer soTimeout;

	@Inject
	private SSLSocketFactory sslSocketFactory;

	@Override
	public HttpClient get() {
		HttpParams params = new BasicHttpParams();
		if(connectonTimeout != null)
			HttpConnectionParams.setConnectionTimeout(params, connectonTimeout.intValue());
		if(soTimeout != null)
			HttpConnectionParams.setSoTimeout(params, soTimeout.intValue());

		DefaultHttpClient httpClient = new DefaultHttpClient(params);
		httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, sslSocketFactory));
		return httpClient;
	}
}
