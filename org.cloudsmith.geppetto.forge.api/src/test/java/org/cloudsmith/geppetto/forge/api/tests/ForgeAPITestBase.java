package org.cloudsmith.geppetto.forge.api.tests;

import org.cloudsmith.geppetto.forge.v2.Forge;
import org.cloudsmith.geppetto.forge.v2.client.ForgeHttpModule;
import org.cloudsmith.geppetto.forge.v2.client.ForgePreferences;

import com.google.inject.Guice;

public class ForgeAPITestBase {
	public static class BobsPreferences extends ForgeTestPreferences {
		@Override
		public String getLogin() {
			return "bob";
		}

		@Override
		public String getOAuthClientId() {
			return "5017fd0247c2c027c8000001";
		}

		@Override
		public String getOAuthClientSecret() {
			return "4142f3b56ac369f974267be05bd9d1e90927e940b5cac2b3f431d8a4a2ffd2e7";
		}

		@Override
		public String getPassword() {
			return "bobbobbob";
		}

	}

	public static class BobsTestModule extends ForgeHttpModule {
		@Override
		protected ForgePreferences getForgePreferences() {
			return new BobsPreferences();
		}
	}

	public static class TestUsersPreferences extends ForgeTestPreferences {
		@Override
		public String getLogin() {
			return ForgeTests.TEST_USER;
		}

		@Override
		public String getOAuthClientId() {
			return ForgeTests.PUPPET_FORGE_CLIENT_ID;
		}

		@Override
		public String getOAuthClientSecret() {
			return ForgeTests.PUPPET_FORGE_CLIENT_SECRET;
		}

		@Override
		public String getPassword() {
			return ForgeTests.TEST_PASSWORD;
		}
	}

	private static Forge bobUserForge;

	protected static Forge getBobUserForge() {
		if(bobUserForge == null)
			bobUserForge = new Forge(Guice.createInjector(new BobsTestModule()));
		return bobUserForge;
	}
}
