package org.cloudsmith.geppetto.forge.api.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.cloudsmith.geppetto.forge.v2.ForgeAPI;
import org.cloudsmith.geppetto.forge.client.ForgeHttpModule;
import org.cloudsmith.geppetto.semver.Version;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.google.inject.Guice;

public class ForgeAPITestBase {
	public static class TestModule extends ForgeHttpModule {
		public TestModule() {
			super(new TestUsersPreferences());
		}
	}

	public static class TestUsersPreferences extends ForgeTestPreferences {
		@Override
		public String getLogin() {
			return TEST_USER;
		}

		@Override
		public String getPassword() {
			return TEST_PASSWORD;
		}
	}

	public static final String TEST_USER = "bob";

	public static final String TEST_PASSWORD = "bobbobbob";

	public static final String TEST_MODULE = "java";

	public static final String TEST_GZIPPED_RELEASE = "puppetlabs-java-0.1.6.tar.gz";

	public static final Version TEST_RELEASE_VERSION = Version.create("0.1.6");

	private static String PUPPET_FORGE_CLIENT_ID;

	private static String PUPPET_FORGE_CLIENT_SECRET;

	private static ForgeAPI testUserForge;

	public static String[] getPuppetForgeClientIdentity() {
		if(PUPPET_FORGE_CLIENT_ID == null) {

			File devDB = new File(System.getProperty("user.home") + "/git/puppet-forge-api/db/development.sqlite3");
			if(!devDB.isFile())
				fail("Unable to find server development database at " + devDB.getAbsolutePath());

			SQLiteConnection connection = new SQLiteConnection(devDB);
			try {
				connection.open(false);
				SQLiteStatement statement = connection.prepare("SELECT id, secret FROM clients WHERE display_name = ?");
				try {
					statement.bind(1, "sample_sinatra_client");
					assertTrue(statement.step());
					PUPPET_FORGE_CLIENT_ID = statement.columnString(0);
					assertNotNull(PUPPET_FORGE_CLIENT_ID);
					PUPPET_FORGE_CLIENT_SECRET = statement.columnString(1);
					assertNotNull(PUPPET_FORGE_CLIENT_SECRET);
				}
				finally {
					statement.dispose();
				}
			}
			catch(SQLiteException e) {
				fail(e.getMessage());
			}
			finally {
				connection.dispose();
			}
		}
		return new String[] { PUPPET_FORGE_CLIENT_ID, PUPPET_FORGE_CLIENT_SECRET };
	}

	public static File getTestData(String path) throws IOException {
		URL url = ForgeIT.class.getResource(path);
		if(url == null)
			throw new RuntimeException("Unable to find \"" + path + "\" resource");
		return toFile(url);
	}

	protected static ForgeAPI getTestUserForge() {
		if(testUserForge == null)
			testUserForge = new ForgeAPI(Guice.createInjector(new TestModule()));
		return testUserForge;
	}

	public static File toFile(URL url) throws IOException {
		try {
			return new File(url.toURI());
		}
		catch(URISyntaxException e) {
			File temp = File.createTempFile("test-", ".tmp");
			temp.deleteOnExit();
			OutputStream output = new FileOutputStream(temp);
			InputStream input = url.openStream();
			try {
				byte[] buffer = new byte[4096];
				int cnt;
				while((cnt = input.read(buffer)) > 0)
					output.write(buffer, 0, cnt);
			}
			finally {
				input.close();
				output.close();
			}
			return temp;
		}
	}

}
