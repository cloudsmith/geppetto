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
package com.puppetlabs.geppetto.ruby;

/**
 * Generates settings. TODO: Should not be part of the runtime PPTP package.
 * TODO: This is part of PPTP generator logic. Does not need to be loaded in
 * runtime.
 * 
 */
public class SettingsData {

	public static class Setting {
		public final String name;
		public final String documentation;
		public final String defaultInfo;
		public final boolean deprecated;

		public Setting(String name, String documentation, String defaultInfo, boolean deprecated) {
			this.name = name;
			this.documentation = documentation;
			this.defaultInfo = defaultInfo;
			this.deprecated = deprecated;
		}
	}

	public static Setting setting(String name, String documentation, String defaultInfo) {
		return new Setting(name, documentation, defaultInfo, false);
	}

	public static Setting setting(String name, String documentation, String defaultInfo, boolean deprecated) {
		return new Setting(name, documentation, defaultInfo, deprecated);
	}

	public final Setting[] settings = new Setting[] {
			setting("allow_duplicate_certs", "Whether to allow a new certificate request to overwrite an existing certificate.",
					"Default: false"), //

			setting("archive_file_server",
					"During an inspect run, the file bucket server to archive files to if archive_files is set.",
					"Default: $server"), //

			setting("archive_files",
					"During an inspect run, whether to archive files whose contents are audited to a file bucket.",
					"Default: false"), //

			setting("async_storeconfigs",
					"Whether to use a queueing system to provide asynchronous database integration. Requires that puppetqd be running and that ‘PSON’ support for ruby be installed.",
					"Default: false"), //
			setting("authconfig",
					"The configuration file that defines the rights to the different namespaces and methods. This can be used as a coarse-grained authorization system for both puppet agent and puppet master.",
					"Default: $confdir/namespaceauth.conf"), //

			setting("autoflush", "Whether log files should always flush to disk.", "Default: false"), //
			setting("autosign",
					"Whether to enable autosign. Valid values are true (which autosigns any key request, and is a very bad idea), false (which never autosigns any key request), and the path to a file, which uses that configuration file to determine which keys to sign.",
					"Default: $confdir/autosign.conf"), //
			setting("bindaddress", "The address a listening server should bind to.",
					"Mongrel servers default to 127.0.0.1 and WEBrick defaults to 0.0.0.0."), //
			setting("bucketdir", "Where FileBucket files are stored.", "Default: $vardir/bucket"), //

			setting("ca", "Wether the master should function as a certificate authority.", "Default: true"), //

			setting("ca_days", "How long a certificate should be valid. This parameter is deprecated, use ca_ttl instead.", "",
					true), //

			setting("ca_md", "The type of hash used in certificates.", "Default: md5"), //

			setting("ca_name", "The name to use the Certificate Authority certificate.", "Default: Puppet CA: $certname"), //

			setting("ca_port", "The port to use for the certificate authority.", "Default: $masterport"), //

			setting("ca_server",
					"The server to use for certificate authority requests. It’s a separate server because it cannot and does not need to horizontally scale.",
					"Default: $server"), //

			setting("ca_ttl",
					"The default TTL for new certificates; valid values must be an integer, optionally followed by one of the units ‘y’ (years of 365 days), ‘d’ (days), ‘h’ (hours), or ‘s’ (seconds). The unit defaults to seconds. If this parameter is set, ca_days is ignored. Examples are ‘3600’ (one hour) and ‘1825d’, which is the same as ‘5y’ (5 years).",
					"Default: 5y"), //

			setting("cacert", "The CA certificate.", "Default: $cadir/ca_crt.pem"), //

			setting("cacrl", "The certificate revocation list (CRL) for the CA. Will be used if present but otherwise ignored.",
					"Default: $cadir/ca_crl.pem"), //

			setting("cadir", "The root directory for the certificate authority.", "Default: $ssldir/ca"), //

			setting("cakey", "The CA private key.", "Default: $cadir/ca_key.pem"), //

			setting("capass", "Where the CA stores the password for the private key", "Default: $caprivatedir/ca.pass"), //

			setting("caprivatedir", "Where the CA stores private certificate information.", "Default: $cadir/private"), //

			setting("capub", "The CA public key.", "Default: $cadir/ca_pub.pem"), //

			setting("catalog_format",
					"(Deprecated for ‘preferred_serialization_format’) What format to use to dump the catalog. Only supports ‘marshal’ and ‘yaml’. Only matters on the client, since it asks the server for a specific format.",
					"", true), //

			setting("catalog_terminus",
					"Where to get node catalogs. This is useful to change if, for instance, you’d like to pre-compile catalogs and store them in memcached or some other easily-accessed store.",
					"Default: compiler"), //
			setting("cert_inventory", "A Complete listing of all certificates", "Default: $cadir/inventory.txt"), //
			setting("certdir", "The certificate directory.", "Default: $ssldir/certs"), //
			setting("certdnsnames",
					"The DNS names on the Server certificate as a colon-separated list. If it’s anything other than an empty string, it will be used as an alias in the created certificate. By default, only the server gets an alias set up, and only for ‘puppet’.",
					""), //

			setting("certificate_revocation",
					"Whether certificate revocation should be supported by downloading a Certificate Revocation List (CRL) to all clients. If enabled, CA chaining will almost definitely not work.",
					"Default: true"), //

			setting("certname", "The name to use when handling certificates. Defaults to the fully qualified domain name.",
					"Default: magpie.puppetlabs.lan"), //
			setting("classfile",

					"The file in which puppet agent stores a list of the classes associated with the retrieved configuration. Can be loaded in the separate puppet executable using the --loadclasses option.",
					"Default: $statedir/classes.txt"), //
			setting("client_datadir", "The directory in which serialized data is stored on the client.",
					"Default: $vardir/client_data"), //
			setting("clientbucketdir", "Where FileBucket files are stored locally.", "Default: $vardir/clientbucket"), //
			setting("clientyamldir", "The directory in which client-side YAML data is stored.", "Default: $vardir/client_yaml"), //
			setting("code",
					"Code to parse directly. This is essentially only used by puppet, and should only be set if you’re writing your own Puppet executable.",
					""),
			setting("color",
					"Whether to use colors when logging to the console. Valid values are ansi (equivalent to true), html (mostly used during testing with TextMate), and false, which produces no color.",
					"Default: ansi"), //
			setting("confdir",
					"The main Puppet configuration directory. The default for this parameter is calculated based on the user. If the process is running as root or the user that Puppet is supposed to run as, it defaults to a system directory, but if it’s running as any other user, it defaults to being in the user’s home directory.",
					"Default: /etc/puppet"), //

			setting("config", "The configuration file for doc.", "Default: $confdir/puppet.conf"), //
			setting("config_version",
					"How to determine the configuration version. By default, it will be the time that the configuration is parsed, but you can provide a shell script to override how the version is determined. The output of this script will be added to every log message in the reports, allowing you to correlate changes on your hosts to the source version on the server.",
					""), //

			setting("configprint",
					"Print the value of a specific configuration parameter. If a parameter is provided for this, then the value is printed and puppet exits. Comma-separate multiple values. For a list of all values, specify ‘all’. This feature is only available in Puppet versions higher than 0.18.4.",
					""), //

			setting("configtimeout",
					"How long the client should wait for the configuration to be retrieved before considering it a failure. This can help reduce flapping if too many clients contact the server at one time.",
					"Default: 120"), //

			setting("couchdb_url", "The url where the puppet couchdb database will be created",
					"Default: http://127.0.0.1:5984/puppet"), //
			setting("csrdir", "Where the CA stores certificate requests", "Default: $cadir/requests"), //
			setting("daemonize", "Send the process into the background. This is the default.", "Default: true"), //
			setting("dbadapter", "The type of database to use.", "Default: sqlite3"), //
			setting("dbconnections",
					"The number of database connections for networked databases. Will be ignored unless the value is a positive integer.",
					""), //
			setting("dblocation", "The database cache for client configurations. Used for querying within the language.",
					"Default: $statedir/clientconfigs.sqlite3"), //

			setting("dbmigrate", "Whether to automatically migrate the database.", "Default: false"), //

			setting("dbname", "The name of the database to use.", "Default: puppet"), //

			setting("dbpassword", "The database password for caching. Only used when networked databases are used.",
					"Default: puppet"), //

			setting("dbport", "The database password for caching. Only used when networked databases are used.", ""), //

			setting("dbserver", "The database server for caching. Only used when networked databases are used.",
					"Default: localhost"), //

			setting("dbsocket",
					"The database socket location. Only used when networked databases are used. Will be ignored if the value is an empty string.",
					""), //
			setting("dbuser", "The database user for caching. Only used when networked databases are used.", "Default: puppet"), //
			setting("deviceconfig", "Path to the device config file for puppet device", "Default: $confdir/device.conf"), //
			setting("devicedir", "The root directory of devices’ $vardir", "Default: $vardir/devices"), //
			setting("diff", "Which diff command to use when printing differences between files.", "Default: diff"), //
			setting("diff_args", "Which arguments to pass to the diff command when printing differences between files.",
					"Default: -u"), //
			setting("document_all", "Document all resources", "Default: false"), //
			setting("downcasefacts", "Whether facts should be made all lowercase when sent to the server.", "Default: false"), //
			setting("dynamicfacts",
					"Facts that are dynamic; these facts will be ignored when deciding whether changed facts should result in a recompile. Multiple facts should be comma-separated.",
					"Default: memorysize,memoryfree,swapsize,swapfree"), //
			setting("environment",
					"The environment Puppet is running in. For clients (e.g., puppet agent) this determines the environment itself, which is used to find modules and much more. For servers (i.e., puppet master) this provides the default environment for nodes we know nothing about.",
					"Default: production"), //
			setting("evaltrace",
					"Whether each resource should log when it is being evaluated. This allows you to interactively see exactly what is being done.",
					"Default: false"), //
			setting("external_nodes",
					"An external command that can produce node information. The output must be a YAML dump of a hash, and that hash must have one or both of classes and parameters, where classes is an array and parameters is a hash. For unknown nodes, the commands should exit with a non-zero exit code. This command makes it straightforward to store your node mapping information in other data sources like databases.",
					"Default: none"), //
			setting("factdest", "Where Puppet should store facts that it pulls down from the central server.",
					"Default: $vardir/facts/"), //
			setting("factpath",
					"Where Puppet should look for facts. Multiple directories should be colon-separated, like normal PATH variables.",
					"Default: $vardir/lib/facter:$vardir/facts"), //
			setting("facts_terminus", "The node facts terminus.", "Default: facter"), //
			setting("factsignore", "What files to ignore when pulling down facts.", "Default: .svn CVS"), //
			setting("factsource",
					"From where to retrieve facts. The standard Puppet file type is used for retrieval, so anything that is a valid file source can be used here.",
					"Default: puppet://$server/facts/"), //
			setting("factsync", "Whether facts should be synced with the central server.", "Default: false"), //
			setting("fileserverconfig", "Where the fileserver configuration is stored.", "Default: $confdir/fileserver.conf"), //
			setting("filetimeout",
					"The minimum time to wait (in seconds) between checking for updates in configuration files. This timeout determines how quickly Puppet checks whether a file (such as manifests or templates) has changed on disk.",
					"Default: 15"), //
			setting("freeze_main",
					"Freezes the ‘main’ class, disallowing any code to be added to it. This essentially means that you can’t have any code outside of a node, class, or definition other than in the site manifest.",
					"Default: false"), //
			setting("genconfig",
					"Whether to just print a configuration to stdout and exit. Only makes sense when used interactively. Takes into account arguments specified on the CLI.",
					"Default: false"), //
			setting("genmanifest",
					"Whether to just print a manifest to stdout and exit. Only makes sense when used interactively. Takes into account arguments specified on the CLI.",
					"Default: false"), //
			setting("graph",
					"Whether to create dot graph files for the different configuration graphs. These dot files can be interpreted by tools like OmniGraffle or dot (which is part of ImageMagick).",
					"Default: false"), //
			setting("graphdir", "Where to store dot-outputted graphs.", "Default: $statedir/graphs"), //
			setting("group", "The group puppet master should run as.", "Default: puppet"), //
			setting("hostcert", "Where individual hosts store and look for their certificates.", "Default: $certdir/$certname.pem"), //
			setting("hostcrl",
					"Where the host’s certificate revocation list can be found. This is distinct from the certificate authority’s CRL.",
					"Default: $ssldir/crl.pem"), //
			setting("hostcsr", "Where individual hosts store and look for their certificate requests.",
					"Default: $ssldir/csr_$certname.pem"), //
			setting("hostprivkey", "Where individual hosts store and look for their private key.",
					"Default: $privatekeydir/$certname.pem"), //
			setting("hostpubkey", "Where individual hosts store and look for their public key.",
					"Default: $publickeydir/$certname.pem"), //
			setting("http_compression",
					"Allow http compression in REST communication with the master. This setting might improve performance for agent -> master communications over slow WANs. Your puppet master needs to support compression (usually by activating some settings in a reverse-proxy in front of the puppet master, which rules out webrick). It is harmless to activate this settings if your master doesn’t support compression, but if it supports it, this setting might reduce performance on high-speed LANs.",
					"Default: false"), //
			setting("http_proxy_host",
					"The HTTP proxy host to use for outgoing connections. Note: You may need to use a FQDN for the server hostname when using a proxy.",
					"Default: none"), //
			setting("http_proxy_port", "The HTTP proxy port to use for outgoing connections", "Default: 3128"), //
			setting("httplog", "Where the puppet agent web server logs.", "Default: $logdir/http.log"), //
			setting("ignorecache",
					"Ignore cache and always recompile the configuration. This is useful for testing new configurations, where the local cache may in fact be stale even if the timestamps are up to date - if the facts change or if the server changes.",
					"Default: false"), //
			setting("ignoreimport",
					"A parameter that can be used in commit hooks, since it enables you to parse-check a single file rather than requiring that all files exist.",
					"Default: false"), //
			setting("ignoreschedules",
					"Boolean; whether puppet agent should ignore schedules. This is useful for initial puppet agent runs.",
					"Default: false"), //
			setting("inventory_port", "The port to communicate with the inventory_server.", "Default: $masterport"), //
			setting("inventory_server", "The server to send facts to.", "Default: $server"), //
			setting("inventory_terminus", "Should usually be the same as the facts terminus", "Default: $facts_terminus"), //
			setting("keylength", "The bit length of keys.", "Default: 1024"), //
			setting("lastrunfile", "Where puppet agent stores the last run report summary in yaml format.",
					"Default: $statedir/last_run_summary.yaml"), //
			setting("lastrunreport", "Where puppet agent stores the last run report in yaml format.",
					"Default: $statedir/last_run_report.yaml"), //
			setting("ldapattrs",
					"The LDAP attributes to include when querying LDAP for nodes. All returned attributes are set as variables in the top-level scope. Multiple values should be comma-separated. The value ‘all’ returns all attributes.",
					"Default: all"), //
			setting("ldapbase",
					"The search base for LDAP searches. It’s impossible to provide a meaningful default here, although the LDAP libraries might have one already set. Generally, it should be the ‘ou=Hosts’ branch under your main directory.",
					""), //
			setting("ldapclassattrs", "The LDAP attributes to use to define Puppet classes. Values should be comma-separated.",
					"Default: puppetclass"), //
			setting("ldapnodes",
					"Whether to search for node configurations in LDAP. See http://projects.puppetlabs.com/projects/puppet/wiki/LDAP_Nodes for more information.",
					"Default: false"), //
			setting("ldapparentattr", "The attribute to use to define the parent node.", "Default: parentnode"), //
			setting("ldappassword", "The password to use to connect to LDAP.", ""), //
			setting("ldapport", "The LDAP port. Only used if ldapnodes is enabled.", "Default: 389"), //
			setting("ldapserver", "The LDAP server. Only used if ldapnodes is enabled.", "Default: ldap"), //
			setting("ldapssl",
					"Whether SSL should be used when searching for nodes. Defaults to false because SSL usually requires certificates to be set up on the client side.",
					"Default: false"), //
			setting("ldapstackedattrs",
					"The LDAP attributes that should be stacked to arrays by adding the values in all hierarchy elements of the tree. Values should be comma-separated.",
					"Default: puppetvar"), //
			setting("ldapstring", "The search string used to find an LDAP node.", "Default: (&(objectclass=puppetClient)(cn=%s))"), //
			setting("ldaptls",
					"Whether TLS should be used when searching for nodes. Defaults to false because TLS usually requires certificates to be set up on the client side.",
					"Default: false"), //
			setting("ldapuser", "The user to use to connect to LDAP. Must be specified as a full DN.", ""), //
			setting("lexical", "Whether to use lexical scoping (vs. dynamic).", "Default: false"), //
			setting("libdir",
					"An extra search path for Puppet. This is only useful for those files that Puppet will load on demand, and is only guaranteed to work for those cases. In fact, the autoload mechanism is responsible for making sure this directory is in Ruby’s search path",
					"Default: $vardir/lib"), //
			setting("listen",
					"Whether puppet agent should listen for connections. If this is true, then puppet agent will accept incoming REST API requests, subject to the default ACLs and the ACLs set in the rest_authconfig file. Puppet agent can respond usefully to requests on the run, facts, certificate, and resource endpoints.",
					"Default: false"), //
			setting("localcacert", "Where each client stores the CA certificate.", "Default: $certdir/ca.pem"), //
			setting("localconfig",
					"Where puppet agent caches the local configuration. An extension indicating the cache format is added automatically.",
					"Default: $statedir/localconfig"), //
			setting("logdir", "The Puppet log directory.", "Default: $vardir/log"), //
			setting("manage_internal_file_permissions",
					"Whether Puppet should manage the owner, group, and mode of files it uses internally", "Default: true"), //
			setting("manifest", "The entry-point manifest for puppet master.", "Default: $manifestdir/site.pp"), //
			setting("manifestdir", "Where puppet master looks for its manifests.", "Default: $confdir/manifests"), //
			setting("masterhttplog", "Where the puppet master web server logs.", "Default: $logdir/masterhttp.log"), //
			setting("masterlog",
					"Where puppet master logs. This is generally not used, since syslog is the default log destination.",
					"Default: $logdir/puppetmaster.log"), //
			setting("masterport", "Which port puppet master listens on.", "Default: 8140"), //
			setting("maximum_uid",
					"The maximum allowed UID. Some platforms use negative UIDs but then ship with tools that do not know how to handle signed ints, so the UIDs show up as huge numbers that can then not be fed back into the system. This is a hackish way to fail in a slightly more useful way when that happens.",
					"Default: 4294967290"), //
			setting("mkusers", "Whether to create the necessary user and group that puppet agent will run as.", "Default: false"), //
			setting("modulepath", "The search path for modules as a list of directories separated by the ‘:’ character.",
					"Default: $confdir/modules:/usr/share/puppet/modules"), //
			setting("name",
					"The name of the application, if we are running as one. The default is essentially $0 without the path or .rb.",
					"Default: doc"), //
			setting("node_name",
					"How the puppet master determines the client’s identity and sets the ‘hostname’, ‘fqdn’ and ‘domain’ facts for use in the manifest, in particular for determining which ‘node’ statement applies to the client. Possible values are ‘cert’ (use the subject’s CN in the client’s certificate) and ‘facter’ (use the hostname that the client reported in its facts)",
					"Default: cert"), //
			setting("node_name_fact",
					"The fact name used to determine the node name used for all requests the agent makes to the master. WARNING: This setting is mutually exclusive with node_name_value. Changing this setting also requires changes to the default auth.conf configuration on the Puppet Master. Please see http://links.puppetlabs.com/node_name_fact for more information.",
					""), //
			setting("node_name_value",
					"The explicit value used for the node name for all requests the agent makes to the master. WARNING: This setting is mutually exclusive with node_name_fact. Changing this setting also requires changes to the default auth.conf configuration on the Puppet Master. Please see http://links.puppetlabs.com/node_name_value for more information.",
					"Default: $certname"), //
			setting("node_terminus", "Where to find information about nodes.", "Default: plain"), //
			setting("noop", "Whether puppet agent should be run in noop mode.", "Default: false"), //
			setting("onetime",
					"Run the configuration once, rather than as a long-running daemon. This is useful for interactively running puppetd.",
					"Default: false"), //
			setting("passfile", "Where puppet agent stores the password for its private key. Generally unused.",
					"Default: $privatedir/password"), //
			setting("path", "The shell search path. Defaults to whatever is inherited from the parent process.", "Default: none"), //
			setting("pidfile", "The pid file", "Default: $rundir/$name.pid"), //
			setting("plugindest", "Where Puppet should store plugins that it pulls down from the central server.",
					"Default: $libdir"), //
			setting("pluginsignore", "What files to ignore when pulling down plugins.", "Default: .svn CVS .git"), //
			setting("pluginsource",
					"From where to retrieve plugins. The standard Puppet file type is used for retrieval, so anything that is a valid file source can be used here.",
					"Default: puppet://$server/plugins"), //
			setting("pluginsync", "Whether plugins should be synced with the central server.", "Default: false"), //
			setting("postrun_command",
					"A command to run after every agent run. If this command returns a non-zero return code, the entire Puppet run will be considered to have failed, even though it might have performed work during the normal run.",
					""), //
			setting("preferred_serialization_format",
					"The preferred means of serializing ruby instances for passing over the wire. This won’t guarantee that all instances will be serialized using this method, since not all classes can be guaranteed to support this format, but it will be used for all classes that support it.",
					"Default: pson"), //
			setting("prerun_command",
					"A command to run before every agent run. If this command returns a non-zero return code, the entire Puppet run will fail.",
					""), //
			setting("privatedir", "Where the client stores private certificate information.", "Default: $ssldir/private"), //
			setting("privatekeydir", "The private key directory.", "Default: $ssldir/private_keys"), //
			setting("publickeydir", "The public key directory.", "Default: $ssldir/public_keys"), //
			setting("puppetdlockfile", "A lock file to temporarily stop puppet agent from doing anything.",
					"Default: $statedir/puppetdlock"), //
			setting("puppetdlog", "The log file for puppet agent. This is generally not used.", "Default: $logdir/puppetd.log"), //
			setting("puppetport", "Which port puppet agent listens on.", "Default: 8139"), //
			setting("queue_source",
					"Which type of queue to use for asynchronous processing. If your stomp server requires authentication, you can include it in the URI as long as your stomp client library is at least 1.1.1",
					"Default: stomp://localhost:61613/"), //
			setting("queue_type", "Which type of queue to use for asynchronous processing.", "Default: stomp"), //
			setting("rails_loglevel",
					"The log level for Rails connections. The value must be a valid log level within Rails. Production environments normally use info and other environments normally use debug.",
					"Default: info"), //
			setting("railslog", "Where Rails-specific logs are sent", "Default: $logdir/rails.log"), //
			setting("report", "Whether to send reports after every transaction.", "Default: true"), //
			setting("report_port", "The port to communicate with the report_server.", "Default: $masterport"), //
			setting("report_server", "The server to send transaction reports to.", "Default: $server"), //
			setting("reportdir",
					"The directory in which to store reports received from the client. Each client gets a separate subdirectory.",
					"Default: $vardir/reports"), //
			setting("reportfrom", "The ‘from’ email address for the reports.", "Default: report@magpie.puppetlabs.lan"), //
			setting("reports",
					"The list of reports to generate. All reports are looked for in puppet/reports/name.rb, and multiple report names should be comma-separated (whitespace is okay).",
					"Default: store"), //
			setting("reportserver", "(Deprecated for ‘report_server’) The server to which to send transaction reports.",
					"Default: $server", true), //
			setting("reporturl", "The URL used by the http reports processor to send reports",
					"Default: http://localhost:3000/reports/upload"), //
			setting("req_bits", "The bit length of the certificates.", "Default: 2048"), //
			setting("requestdir", "Where host certificate requests are stored.", "Default: $ssldir/certificate_requests"), //
			setting("resourcefile",
					"The file in which puppet agent stores a list of the resources associated with the retrieved configuration.",
					"Default: $statedir/resources.txt"), //
			setting("rest_authconfig",
					"The configuration file that defines the rights to the different rest indirections. This can be used as a fine-grained authorization system for puppet master.",
					"Default: $confdir/auth.conf"), //
			setting("route_file", "The YAML file containing indirector route configuration.", "Default: $confdir/routes.yaml"), //
			setting("rrddir",
					"The directory where RRD database files are stored. Directories for each reporting host will be created under this directory.",
					"Default: $vardir/rrd"), //
			setting("rrdinterval",
					"How often RRD should expect data. This should match how often the hosts report back to the server.",
					"Default: $runinterval"), //
			setting("run_mode", "The effective ‘run mode’ of the application: master, agent, or user.", "Default: master"), //
			setting("rundir", "Where Puppet PID files are kept.", "Default: $vardir/run"), //
			setting("runinterval",
					"How often puppet agent applies the client configuration; in seconds. Note that a runinterval of 0 means “run continuously” rather than “never run.” If you want puppet agent to never run, you should start it with the --no-client option.",
					"Default: 1800"), //
			setting("sendmail", "Where to find the sendmail binary with which to send email.", "Default: /usr/sbin/sendmail"), //
			setting("serial", "Where the serial number for certificates is stored.", "Default: $cadir/serial"), //
			setting("server", "The server to which server puppet agent should connect", "Default: puppet"), //
			setting("server_datadir", "The directory in which serialized data is stored, usually in a subdirectory.",
					"Default: $vardir/server_data"), //
			setting("servertype",
					"The type of server to use. Currently supported options are webrick and mongrel. If you use mongrel, you will need a proxy in front of the process or processes, since Mongrel cannot speak SSL.",
					"Default: webrick"), //
			setting("show_diff",
					"Whether to print a contextual diff when files are being replaced. The diff is printed on stdout, so this option is meaningless unless you are running Puppet interactively. This feature currently requires the diff/lcs Ruby library.",
					"Default: false"), //
			setting("signeddir", "Where the CA stores signed certificates.", "Default: $cadir/signed"), //
			setting("smtpserver", "The server through which to send email reports.", "Default: none"), //
			setting("splay", "Whether to sleep for a pseudo-random (but consistent) amount of time before a run.", "Default: false"), //
			setting("splaylimit", "The maximum time to delay before runs. Defaults to being the same as the run interval.",
					"Default: $runinterval"), //
			setting("ssl_client_header",
					"The header containing an authenticated client’s SSL DN. Only used with Mongrel. This header must be set by the proxy to the authenticated client’s SSL DN (e.g., /CN=puppet.puppetlabs.com). See http://projects.puppetlabs.com/projects/puppet/wiki/Using_Mongrel for more information.",
					"Default: HTTP_X_CLIENT_DN"), //
			setting("ssl_client_verify_header",
					"The header containing the status message of the client verification. Only used with Mongrel. This header must be set by the proxy to ‘SUCCESS’ if the client successfully authenticated, and anything else otherwise. See http://projects.puppetlabs.com/projects/puppet/wiki/Using_Mongrel for more information.",
					"Default: HTTP_X_CLIENT_VERIFY"), //
			setting("ssldir", "Where SSL certificates are kept.", "Default: $confdir/ssl"), //
			setting("statedir",
					"The directory where Puppet state is stored. Generally, this directory can be removed without causing harm (although it might result in spurious service restarts).",
					"Default: $vardir/state"), //
			setting("statefile",
					"Where puppet agent and puppet master store state associated with the running configuration. In the case of puppet master, this file reflects the state discovered through interacting with clients.",
					"Default: $statedir/state.yaml"), //
			setting("storeconfigs",
					"Whether to store each client’s configuration, including catalogs, facts, and related data. This also enables the import and export of resources in the Puppet language - a mechanism for exchange resources between nodes. By default this uses ActiveRecord and an SQL database to store and query the data; this, in turn, will depend on Rails being available. You can adjust the backend using the storeconfigs_backend setting.",
					"Default: false"), //
			setting("storeconfigs_backend",
					"Configure the backend terminus used for StoreConfigs. By default, this uses the ActiveRecord store, which directly talks to the database from within the Puppet Master process.",
					"Default: active_record"), //
			setting("strict_hostname_checking",
					"Whether to only search for the complete hostname as it is in the certificate when searching for node information in the catalogs.",
					"Default: false"), //
			setting("summarize", "Whether to print a transaction summary.", "Default: false"), //
			setting("syslogfacility",
					"What syslog facility to use when logging to syslog. Syslog has a fixed list of valid facilities, and you must choose one of those; you cannot just make one up.",
					"Default: daemon"), //
			setting("tagmap", "The mapping between reporting tags and email addresses.", "Default: $confdir/tagmail.conf"), //
			setting("tags",
					"Tags to use to find resources. If this is set, then only resources tagged with the specified tags will be applied. Values must be comma-separated.",
					""), //
			setting("templatedir", "Where Puppet looks for template files. Can be a list of colon-seperated directories.",
					"Default: $vardir/templates"), //
			setting("thin_storeconfigs",
					"Boolean; wether storeconfigs store in the database only the facts and exported resources. If true, then storeconfigs performance will be higher and still allow exported/collected resources, but other usage external to Puppet might not work",
					"Default: false"), //
			setting("trace", "Whether to print stack traces on some errors", "Default: false"), //
			setting("use_cached_catalog",
					"Whether to only use the cached catalog rather than compiling a new catalog on every run. Puppet can be run with this enabled by default and then selectively disabled when a recompile is desired.",
					"Default: false"), //
			setting("usecacheonfailure",
					"Whether to use the cached configuration when the remote configuration will not compile. This option is useful for testing new configurations, where you want to fix the broken configuration rather than reverting to a known-good one.",
					"Default: true"), //
			setting("user", "The user puppet master should run as.", "Default: puppet"), //
			setting("vardir",
					"Where Puppet stores dynamic and growing data. The default for this parameter is calculated specially, like confdir_.",
					"Default: /var/lib/puppet"), //
			setting("yamldir", "The directory in which YAML data is stored, usually in a subdirectory.", "Default: $vardir/yaml"), //
			setting("zlib", "Boolean; whether to use the zlib library", "Default: true") //
	};
}
