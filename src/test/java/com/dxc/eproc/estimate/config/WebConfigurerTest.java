package com.dxc.eproc.estimate.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.config.EProcProperties;
import com.dxc.eproc.estimate.EstimateServiceApplication;

// TODO: Auto-generated Javadoc
/**
 * Unit tests for the {@link WebConfigurer} class.
 */
public class WebConfigurerTest {

	/** The web configurer. */
	private WebConfigurer webConfigurer;

	/** The servlet context. */
	private MockServletContext servletContext;

	/** The env. */
	private MockEnvironment env;

	/** The props. */
	private EProcProperties props;

	/**
	 * Setup.
	 */
	@BeforeMethod
	public void setup() {
		servletContext = Mockito.spy(new MockServletContext());
		Mockito.doReturn(Mockito.mock(FilterRegistration.Dynamic.class)).when(servletContext)
				.addFilter(ArgumentMatchers.anyString(), ArgumentMatchers.any(Filter.class));
		Mockito.doReturn(Mockito.mock(ServletRegistration.Dynamic.class)).when(servletContext)
				.addServlet(ArgumentMatchers.anyString(), ArgumentMatchers.any(Servlet.class));

		env = new MockEnvironment();
		props = new EProcProperties();

		webConfigurer = new WebConfigurer(env, props);
	}

	/**
	 * Test start up prod servlet context.
	 *
	 * @throws ServletException the servlet exception
	 */
	@Test
	public void testStartUpProdServletContext() throws ServletException {
		env.setActiveProfiles(EstimateServiceApplication.SPRING_PROFILE_PRODUCTION);
		webConfigurer.onStartup(servletContext);
	}

	/**
	 * Test start up dev servlet context.
	 *
	 * @throws ServletException the servlet exception
	 */
	@Test
	public void testStartUpDevServletContext() throws ServletException {
		env.setActiveProfiles(EstimateServiceApplication.SPRING_PROFILE_DEVELOPMENT);
		webConfigurer.onStartup(servletContext);
	}

	/**
	 * Test cors filter on api path.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCorsFilterOnApiPath() throws Exception {
		props.getCors().setAllowedOrigins(Collections.singletonList("*"));
		props.getCors().setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		props.getCors().setAllowedHeaders(Collections.singletonList("*"));
		props.getCors().setMaxAge(1800L);
		props.getCors().setAllowCredentials(false);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerTestController())
				.addFilters(webConfigurer.corsFilter()).build();

		int test = mockMvc
				.perform(MockMvcRequestBuilders.options("/api/test-cors").header(HttpHeaders.ORIGIN, "other.domain.com")
						.header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST"))
				.andReturn().getResponse().getStatus();

		System.out.println("test - " + test);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/test-cors").header(HttpHeaders.ORIGIN, "other.domain.com"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/**
	 * Test cors filter on other path.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCorsFilterOnOtherPath() throws Exception {
		props.getCors().setAllowedOrigins(Collections.singletonList("*"));
		props.getCors().setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		props.getCors().setAllowedHeaders(Collections.singletonList("*"));
		props.getCors().setMaxAge(1800L);
		props.getCors().setAllowCredentials(true);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerTestController())
				.addFilters(webConfigurer.corsFilter()).build();

		mockMvc.perform(MockMvcRequestBuilders.get("/test/test-cors").header(HttpHeaders.ORIGIN, "other.domain.com"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
	}

	/**
	 * Test cors filter deactivated.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCorsFilterDeactivated() throws Exception {
		props.getCors().setAllowedOrigins(null);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerTestController())
				.addFilters(webConfigurer.corsFilter()).build();

		mockMvc.perform(MockMvcRequestBuilders.get("/api/test-cors").header(HttpHeaders.ORIGIN, "other.domain.com"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
	}

	/**
	 * Test cors filter deactivated 2.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCorsFilterDeactivated2() throws Exception {
		props.getCors().setAllowedOrigins(new ArrayList<>());

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerTestController())
				.addFilters(webConfigurer.corsFilter()).build();

		mockMvc.perform(MockMvcRequestBuilders.get("/api/test-cors").header(HttpHeaders.ORIGIN, "other.domain.com"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
	}
}
