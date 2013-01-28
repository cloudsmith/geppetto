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
package org.cloudsmith.geppetto.forge.api.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

// @fmtOff
@SuiteClasses({
	ModuleTestCreate.class,
	ReleaseTestCreate.class,
	UserTests.class,
	ModuleTests.class,
	ReleaseTests.class,
	ReleaseTestDelete.class,
	ModuleTestDelete.class,
})
// @fmtOn
@RunWith(Suite.class)
public class ForgeTests extends ForgeAPITestBase {
	public static final String TEST_USER = "bob";

	public static final String TEST_PASSWORD = "bobbobbob";

	public static final String TEST_MODULE = "java";

	public static final String TEST_GZIPPED_RELEASE = "puppetlabs-java-0.1.6.tar.gz";

	public static final String TEST_RELEASE_VERSION = "0.1.6";

	private static String PUPPET_FORGE_CLIENT_ID;

	private static String PUPPET_FORGE_CLIENT_SECRET;

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
}
