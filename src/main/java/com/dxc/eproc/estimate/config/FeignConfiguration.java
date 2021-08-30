package com.dxc.eproc.estimate.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The Class FeignConfiguration.
 */
@Configuration
@EnableFeignClients(basePackages = "com.dxc.eproc.client")
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

	/**
	 * Set the Feign specific log level to log client REST requests.
	 *
	 * @return the feign. logger. level
	 */
	@Bean(name = "feignLoggerLevel")
	feign.Logger.Level feignLoggerLevel() {
		return feign.Logger.Level.BASIC;
	}

//	/**
//	 * Error decoder.
//	 *
//	 * @return the error decoder
//	 */
//	@Bean
//	public ErrorDecoder errorDecoder() {
//		return new CustomErrorDecoder();
//	}
}
