package com.dxc.eproc.estimate.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The Class DatabaseConfiguration.
 */
@Configuration
@EntityScan(basePackages = { "com.dxc.eproc.estimate.model" })
@EnableJpaRepositories(basePackages = { "com.dxc.eproc.estimate.repository" })
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
