package com.puppetlabs.geppetto.forge.api.it;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.google.inject.AbstractModule;
import com.puppetlabs.geppetto.forge.client.ForgeHttpModule;
import com.puppetlabs.geppetto.forge.client.GsonModule;
import com.puppetlabs.geppetto.forge.client.OAuthModule;
import com.puppetlabs.geppetto.forge.v2.ForgeAPI;
import com.puppetlabs.geppetto.semver.Version;

public class ForgeAPITestBase {
	public static class TestModule extends AbstractModule {

		@Override
		protected void configure() {
			install(GsonModule.INSTANCE);
			install(new ForgeHttpModule() {

				@Override
				protected String getBaseURL() {
					return FORGE_STAGING_SERVICE_BASE_URL;
				}
			});
			install(new OAuthModule(FORGE_CLIENT_ID, FORGE_CLIENT_SECRET, TEST_USER, TEST_PASSWORD));
			bind(HttpClient.class).toInstance(new DefaultHttpClient());
		}
	}

	public static final String TEST_USER = "geppetto";

	public static final String TEST_PASSWORD = "geppetto tester";

	public static final String TEST_MODULE = "testmodule";

	public static final IPath TEST_GZIPPED_RELEASE = Path.fromPortableString("testData/geppetto-testmodule-0.1.0.tar.gz");

	public static final Version TEST_RELEASE_VERSION = Version.fromString("0.1.0");

	private static final String FORGE_CLIENT_ID = "cac18b1f07f13a244c47644548b29cbbe58048f3aaccdeefa7c0306467afda44";

	private static final String FORGE_CLIENT_SECRET = "2227c9a7392382f58b5e4d084b705827cb574673ff7d2a5905ef21685fd48e40";

	private static final String FORGE_STAGING_SERVICE_BASE_URL = "http://forge-staging-api.puppetlabs.com";

	private static ForgeAPI testUserForge;

	public static String[] getPuppetForgeClientIdentity() {
		return new String[] { FORGE_CLIENT_ID, FORGE_CLIENT_SECRET };
	}

	protected static ForgeAPI getTestUserForge() {
		if(testUserForge == null)
			testUserForge = new ForgeAPI(new TestModule());
		return testUserForge;
	}
}
