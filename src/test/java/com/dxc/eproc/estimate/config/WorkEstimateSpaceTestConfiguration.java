package com.dxc.eproc.estimate.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.dxc.eproc.estimate.document.WorkEstimateSpace;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateSpaceTestConfiguration.
 */
@Profile("test")
@Configuration
public class WorkEstimateSpaceTestConfiguration {

	/**
	 * Save supplier document.
	 *
	 * @return the SupplierSpace
	 */
	@Bean
	@Primary
	public WorkEstimateSpace saveEstimateDocument() {
		return Mockito.mock(WorkEstimateSpace.class);
	}
}
