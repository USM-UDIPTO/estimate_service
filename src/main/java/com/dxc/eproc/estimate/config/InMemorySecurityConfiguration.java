package com.dxc.eproc.estimate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

// TODO: Auto-generated Javadoc
/**
 * The Class InMemorySecurityConfiguration.
 */
@Configuration
@EnableWebSecurity
@Import(SecurityProblemSupport.class)
//@Profile(EProcConstants.PROFILE_INMEMORY)
@Order(1000)
public class InMemorySecurityConfiguration extends WebSecurityConfigurerAdapter {

	/** The problem support. */
	private final SecurityProblemSupport problemSupport;

	/**
	 * Instantiates a new in memory security configuration.
	 *
	 * @param problemSupport the problem support
	 */
	public InMemorySecurityConfiguration(SecurityProblemSupport problemSupport) {
		this.problemSupport = problemSupport;
	}

	/**
	 * Configure.
	 *
	 * @param http the http
	 * @throws Exception the exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().exceptionHandling().authenticationEntryPoint(problemSupport)
				.accessDeniedHandler(problemSupport).and().authorizeRequests().antMatchers("/v1/api/**").permitAll()
				.antMatchers("/v1/public/**").permitAll().antMatchers("/api/**").permitAll() // TODO
				.and().httpBasic();

	}

	/**
	 * Configure global.
	 *
	 * @param auth the auth
	 * @throws Exception the exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("sysadmin").password("sysadmin").roles("ADMIN", "SUPPLIER_USER");
		auth.inMemoryAuthentication().withUser("user").password("user").roles("SUPPLIER_USER");
	}

	/**
	 * Password encoder.
	 *
	 * @return the password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
