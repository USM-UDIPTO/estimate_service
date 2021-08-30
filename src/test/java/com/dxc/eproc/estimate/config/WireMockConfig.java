package com.dxc.eproc.estimate.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.github.tomakehurst.wiremock.WireMockServer;

// TODO: Auto-generated Javadoc
/**
 * The Class WireMockConfig.
 */
@TestConfiguration
public class WireMockConfig {

	/**
	 * Mock books service.
	 *
	 * @return the wire mock server
	 */
	@Bean(initMethod = "start", destroyMethod = "stop")
	public WireMockServer mockBooksService() {
		return new WireMockServer(9561);
	}

}
