package org.cloudsmith.geppetto.onetimeinstall;

import static org.eclipse.equinox.p2.engine.IProfile.PROP_PROFILE_ROOT_IU;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.internal.p2.director.ProfileChangeRequest;
import org.eclipse.equinox.internal.provisional.p2.director.IDirector;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.equinox.p2.engine.IProfileRegistry;
import org.eclipse.equinox.p2.engine.ProvisioningContext;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.planner.IProfileChangeRequest;
import org.eclipse.equinox.p2.query.IQuery;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	private static final String PROP_INCLUSION_RULES = "org.eclipse.equinox.p2.internal.inclusion.rules";

	private static final String INCLUSION_RULE_STRICT = "STRICT";

	private static void error(ILog log, String message) {
		log(log, message, IStatus.ERROR);
	}

	private static void info(ILog log, String message) {
		log(log, message, IStatus.INFO);
	}

	private static void log(ILog log, String message, int severity) {
		log.log(new Status(severity, "org.cloudsmith.geppetto.onetimeinstall", message));
	}

	public void start(BundleContext context) throws Exception {
		ILog log = Platform.getLog(context.getBundle());

		IProvisioningAgent agent = null;
		ServiceReference<IProvisioningAgent> agentRef = context.getServiceReference(IProvisioningAgent.class);
		if(agentRef != null)
			agent = context.getService(agentRef);

		if(agent == null) {
			error(log, "Unable to obtain provisioning agent");
			return;
		}

		IProfileRegistry profileRegistry = (IProfileRegistry) agent.getService(IProfileRegistry.SERVICE_NAME);
		IProfile profile = profileRegistry.getProfile(IProfileRegistry.SELF);

		boolean propertyUpdatesNeeded = false;

		List<String> ids = new ArrayList<String>();
		ids.add("org.cloudsmith.geppetto.ide2");
		ids.add("org.eclipse.team.svn.feature.group");
		ids.add("org.polarion.eclipse.team.svn.connector.feature.group");
		ids.add("org.polarion.eclipse.team.svn.connector.svnkit17.feature.group");
		ids.add("org.eclipse.egit.feature.group");
		ids.add("org.eclipse.jgit.feature.group");

		IQuery<IInstallableUnit> query = QueryUtil.createMatchQuery("$0.exists(x | x == id)", ids);
		IProfileChangeRequest changeRequest = new ProfileChangeRequest(profile);
		for(IInstallableUnit iu : profile.query(query, null).toUnmodifiableSet()) {
			if(profile.getInstallableUnitProperty(iu, PROP_INCLUSION_RULES) == null) {
				changeRequest.setInstallableUnitInclusionRules(iu, INCLUSION_RULE_STRICT);
				info(log, iu.getId() + " needs STRICT inclusion rule");
				propertyUpdatesNeeded = true;
			}
			String isRoot = profile.getInstallableUnitProperty(iu, PROP_PROFILE_ROOT_IU);
			if(!Boolean.valueOf(isRoot)) {
				changeRequest.setInstallableUnitProfileProperty(iu, PROP_PROFILE_ROOT_IU, "true");
				info(log, iu.getId() + " needs to become root");
				propertyUpdatesNeeded = true;
			}
		}

		query = QueryUtil.createIUQuery("org.cloudsmith.geppetto.ide");
		Set<IInstallableUnit> iusToUninstall = profile.query(query, null).toUnmodifiableSet();

		if(iusToUninstall.isEmpty() && !propertyUpdatesNeeded) {
			info(log, "No updates were needed");
			return;
		}

		IDirector director = (IDirector) agent.getService(IDirector.SERVICE_NAME);
		ProvisioningContext provisioningContext = new ProvisioningContext(agent);

		if(propertyUpdatesNeeded) {
			info(log, "Performing property updates");
			director.provision(changeRequest, provisioningContext, null);
			info(log, "Property update complete");
		}

		if(!iusToUninstall.isEmpty()) {
			changeRequest = new ProfileChangeRequest(profile);
			changeRequest.removeAll(iusToUninstall);
			info(log, "Removing obsolete IU's");
			director.provision(changeRequest, provisioningContext, null);
			info(log, "Removal complete");
		}
	}

	public void stop(BundleContext bundleContext) throws Exception {
	}
}
