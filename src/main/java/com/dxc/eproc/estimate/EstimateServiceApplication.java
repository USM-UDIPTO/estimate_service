package com.dxc.eproc.estimate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.dxc.eproc.utils.Utility;

// TODO: Auto-generated Javadoc
/**
 * The Class EstimateServiceApplication.
 */
@SpringBootApplication
public class EstimateServiceApplication implements InitializingBean {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(EstimateServiceApplication.class);

	/** The Constant SPRING_PROFILE_DEFAULT. */
	public static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

	/** The Constant SPRING_PROFILE_ACTIVE. */
	private static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";

	/** The Constant SPRING_PROFILE_DEVELOPMENT. */
	public static final String SPRING_PROFILE_DEVELOPMENT = "dev";

	/** The Constant SPRING_PROFILE_PRODUCTION. */
	public static final String SPRING_PROFILE_PRODUCTION = "prod";

	/** The env. */
	public final Environment env;

	/**
	 * Instantiates a new supplier service application.
	 *
	 * @param env the env
	 */
	public EstimateServiceApplication(Environment env) {
		this.env = env;
	}

	/**
	 * After properties set.
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if (activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(SPRING_PROFILE_PRODUCTION)) {
			log.error("You have misconfigured your application! It should not run "
					+ "with both the 'dev' and 'prod' profiles at the same time.");
		}
		log.info("=======================================");
		log.info(env.getProperty("spring.datasource.url"));
		log.info("=======================================");
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EstimateServiceApplication.class);
		addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		logApplicationStartup(env);
	}

	/**
	 * Adds the default profile.
	 *
	 * @param app the app
	 */
	private static void addDefaultProfile(SpringApplication app) {
		Map<String, Object> defProperties = new HashMap<>();

		defProperties.put(SPRING_PROFILE_ACTIVE, SPRING_PROFILE_DEVELOPMENT);
		app.setDefaultProperties(defProperties);
	}

	/**
	 * Log application startup.
	 *
	 * @param env the env
	 */
	private static void logApplicationStartup(Environment env) {
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		String serverPort = env.getProperty("server.port");
		String contextPath = env.getProperty("server.servlet.context-path");
		if (!Utility.isValidString(contextPath)) {
			contextPath = "/";
		}
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}{}\n\t"
						+ "External: \t{}://{}:{}{}\n\t"
						+ "Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, serverPort, contextPath, protocol, hostAddress,
				serverPort, contextPath, env.getActiveProfiles());

		String configServerStatus = env.getProperty("configserver.status");
		if (configServerStatus == null) {
			configServerStatus = "Not found or not setup for this application";
		}
		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Config Server: \t{}\n----------------------------------------------------------",
				configServerStatus);

	}

}
