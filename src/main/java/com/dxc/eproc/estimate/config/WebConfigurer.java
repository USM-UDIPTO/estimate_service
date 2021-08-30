package com.dxc.eproc.estimate.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.dxc.eproc.config.EProcProperties;

// TODO: Auto-generated Javadoc
/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

	/** The env. */
	private final Environment env;

	/** The e proc properties. */
	private final EProcProperties eProcProperties;

	/**
	 * Instantiates a new web configurer.
	 *
	 * @param env             the env
	 * @param eProcProperties the e proc properties
	 */
	public WebConfigurer(Environment env, EProcProperties eProcProperties) {
		this.env = env;
		this.eProcProperties = eProcProperties;
	}

	/**
	 * On startup.
	 *
	 * @param servletContext the servlet context
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		if (env.getActiveProfiles().length != 0) {
			log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
		}

		log.info("Web application fully configured");
	}

	/**
	 * Customize.
	 *
	 * @param server the server
	 */
	@Override
	public void customize(WebServerFactory server) {
		log.info("Web application customize");
	}

	/**
	 * Cors filter.
	 *
	 * @return the cors filter
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = eProcProperties.getCors();
		if (config != null && config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
			log.debug("Registering CORS filter");
			source.registerCorsConfiguration("/v1/api/**", config);
			source.registerCorsConfiguration("/management/**", config);
			source.registerCorsConfiguration("/v2/api-docs", config);
			source.registerCorsConfiguration("/*/api/**", config);
			source.registerCorsConfiguration("/services/*/api/**", config);
			source.registerCorsConfiguration("/*/management/**", config);
		}
		return new CorsFilter(source);
	}
}
