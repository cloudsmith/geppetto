package org.cloudsmith.geppetto.injectable.eclipse.impl;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.cloudsmith.geppetto.injectable.eclipse.Activator;
import org.eclipse.core.net.proxy.IProxyData;

import com.google.inject.Provider;

public class EclipseHttpClientProvider implements Provider<HttpClient> {

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
		httpClient.setRoutePlanner(new ProxiedRoutePlanner(schemeRegistry));
		for(IProxyData proxyData : Activator.getInstance().getProxyService().getProxyData()) {
			String user = proxyData.getUserId();
			String pwd = proxyData.getPassword();
			if(user != null || pwd != null)
				httpClient.getCredentialsProvider().setCredentials(
					new AuthScope(proxyData.getHost(), proxyData.getPort()), new UsernamePasswordCredentials(user, pwd));
		}
		return httpClient;
	}
}
