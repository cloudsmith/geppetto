package org.cloudsmith.geppetto.forge.api.tests;

import org.cloudsmith.geppetto.forge.v2.Forge;
import org.cloudsmith.geppetto.forge.v2.client.ForgeHttpModule;
import org.cloudsmith.geppetto.forge.v2.client.ForgePreferences;

import com.google.inject.Guice;

public class ForgeAPITestBase {
	public static class TestModule extends ForgeHttpModule {
		@Override
		protected ForgePreferences getForgePreferences() {
			return new TestUsersPreferences();
		}
	}

	public static class TestUsersPreferences extends ForgeTestPreferences {
		@Override
		public String getLogin() {
			return ForgeTests.TEST_USER;
		}

		@Override
		public String getPassword() {
			return ForgeTests.TEST_PASSWORD;
		}
	}

	private static Forge testUserForge;

	protected static Forge getTestUserForge() {
		if(testUserForge == null)
			testUserForge = new Forge(Guice.createInjector(new TestModule()));
		return testUserForge;
	}
}
