package com.dxc.eproc.estimate.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.IntegrationTest;
import com.dxc.eproc.estimate.enumeration.SchemeType;
import com.dxc.eproc.estimate.model.SchemeCode;
import com.dxc.eproc.estimate.repository.SchemeCodeRepository;
import com.dxc.eproc.estimate.service.dto.SchemeCodeDTO;
import com.dxc.eproc.estimate.service.mapper.SchemeCodeMapper;

/**
 * Integration tests for the {@link DeptHeadOfAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
public class SchemeCodeResourceIT extends AbstractTestNGSpringContextTests {

	private final Logger log = LoggerFactory.getLogger(SchemeCodeResourceIT.class);

	@Autowired
	private SchemeCodeRepository schemeCodeRepository;

	@Autowired
	private SchemeCodeMapper schemeCodeMapper;

	@Autowired
	private MockMvc mvc;

	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
		// schemeCode = new SchemeCode();
		createSchemeEntity();

	}

	public SchemeCodeDTO getschemeCodeDTO() throws Exception {

		SchemeCodeDTO schemeCodeDTO = new SchemeCodeDTO();
		schemeCodeDTO.setLocationId(1L);
		schemeCodeDTO.setSchemeCode("_Test-c");
		// schemeCodeDTO.setSchemeStatus("Test-b");
		schemeCodeDTO.setSchemeType(SchemeType.MRC);
		schemeCodeDTO.setActiveYn(false);
		return schemeCodeDTO;

	}

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static SchemeCode createSchemeEntity() {
		SchemeCode schemeCode = new SchemeCode();
		schemeCode.setLocationId(1L);
		schemeCode.setSchemeCode("Test-c");
		// schemeCode.setSchemeStatus("Test-b");
		schemeCode.setSchemeType(SchemeType.MRC);
		schemeCode.setActiveYn(false);
		return schemeCode;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	@Test
	public void createSchemeCode_SuccessTest() throws Exception {
		SchemeCodeDTO schemeCodeDTO = getschemeCodeDTO();

		String uri = "/v1/api/scheme-codes";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(schemeCodeDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated());

		log.info("createSchemeCode_SuccessTest() successful!!");
	}

	@Test
	public void createSchemeCode_IdExists() throws Exception {
		SchemeCodeDTO schemeCodeDTO = getschemeCodeDTO();
		schemeCodeDTO.setId(1L);
		String uri = "/v1/api/scheme-codes";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(schemeCodeDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("createSchemeCode_IdExists() successful!!");
	}

	@Test
	public void updateSchemeCode_SuccessTest() throws Exception {
		SchemeCode schemeCode = createSchemeEntity();
		schemeCodeRepository.save(schemeCode);

		SchemeCodeDTO schemeCodeDTO = schemeCodeMapper.toDto(schemeCode);
		schemeCodeDTO.setActiveYn(true);
		schemeCodeDTO.setSchemeStatus("Test");

		String uri = "/v1/api/scheme-codes/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, schemeCodeDTO.getId()).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(schemeCodeDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isOk());

		log.info("updateSchemeCode_SuccessTest() successful!!");
	}

	@Test
	public void updateSchemeCode_IdExistsTest() throws Exception {
		SchemeCodeDTO schemeCodeDTO = getschemeCodeDTO();
		schemeCodeDTO.setId(Long.MAX_VALUE);

		String uri = "/v1/api/scheme-codes/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, schemeCodeDTO.getId()).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(schemeCodeDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateSchemeCode_IdExistsTest successful");
	}

	@Test
	public void partialUpdateSchemeCode_SuccessTest() throws Exception {
		SchemeCode schemeCode = createSchemeEntity();
		schemeCodeRepository.save(schemeCode);

		SchemeCodeDTO schemeCodeDTO = schemeCodeMapper.toDto(schemeCode);
		schemeCodeDTO.setActiveYn(true);
		schemeCodeDTO.setSchemeCode("Test-02");

		String uri = "/v1/api/scheme-codes/{id}";

		RequestBuilder req1 = MockMvcRequestBuilders.patch(uri, schemeCodeDTO.getId())
				.accept(MediaType.APPLICATION_JSON).contentType("application/merge-patch+json")
				.content(TestUtil.convertObjectToJsonBytes(schemeCode));
		mvc.perform(req1).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("partialUpdateSchemeCode_SuccessTest()!!");
	}

	@Test
	public void partialUpdateScheme_IdExistsTest() throws Exception {
		SchemeCodeDTO schemeCodeDTO = getschemeCodeDTO();
		schemeCodeDTO.setId(Long.MAX_VALUE);
		SchemeCode schemeCode = createSchemeEntity();
		String uri = "/v1/api/scheme-codes/{id}";
		RequestBuilder req1 = MockMvcRequestBuilders.patch(uri, schemeCodeDTO.getId())
				.accept(MediaType.APPLICATION_JSON).contentType("application/merge-patch+json")
				.content(TestUtil.convertObjectToJsonBytes(schemeCode));
		mvc.perform(req1).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("partialUpdateScheme_IdExistsTest() successful!!");
	}

	@Test
	public void getAllSchemeCode_successTest() throws Exception {
		SchemeCodeDTO schemeCodeDTO = getschemeCodeDTO();
		String uri = "/v1/api/scheme-codes";
		mvc.perform(MockMvcRequestBuilders.get(uri, schemeCodeDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());

		log.info("getAllscheemCode_successTest successful");
	}

	@Test
	public void getAllActiveSchemeCode_successTest() throws Exception {
		SchemeCodeDTO schemeCodeDTO = getschemeCodeDTO();
		String uri = "/v1/api/scheme-codes-active";
		mvc.perform(MockMvcRequestBuilders.get(uri, schemeCodeDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());

		log.info("getAllActiveschemeCode_successTest successful");
	}

	@Test
	public void getSchemeCode_successTest() throws Exception {
		SchemeCode schemeCode = createSchemeEntity();
		schemeCodeRepository.save(schemeCode);

		String uri = "/v1/api/scheme-codes/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, schemeCode.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getDeptHeadOfAccount_successTest Successful!!");
	}

	@Test
	public void deleteSchemeCode_SuccessTest() throws Exception {
		SchemeCode schemeCode = createSchemeEntity();
		schemeCodeRepository.save(schemeCode);

		SchemeCodeDTO schemeCodeDTO = schemeCodeMapper.toDto(schemeCode);
		schemeCodeDTO.setActiveYn(true);
		schemeCodeDTO.setSchemeCode("Test-A");

		String uri = "/v1/api/scheme-codes/{id}";
		RequestBuilder req = MockMvcRequestBuilders.delete(uri, schemeCodeDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schemeCodeDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNoContent());

		log.info("deleteDeptHeadOfAccount_SuccessTest() successful!!");
	}

	@Test
	public void getAllBySchemeTypeAndSchemeCode_successTest() throws Exception {
		SchemeCode schemeCode = createSchemeEntity();
		schemeCodeRepository.save(schemeCode);
		String uri = "/v1/api/search-scheme-codes";
		mvc.perform(MockMvcRequestBuilders.get(uri).param("location-id", "1").param("scheme-type", "MRC")
				.param("scheme-code", "Test-c")).andExpect(MockMvcResultMatchers.status().isOk());

		log.info("getAllActiveschemeCode_successTest successful");
	}

	@Test
	public void getAllBySchemeTypeAndSchemeCode_notsuccessTest() throws Exception {
		String uri = "/v1/api/search-scheme-codes";
		mvc.perform(MockMvcRequestBuilders.get(uri).param("location-id", "3").param("scheme-type", "MRC")
				.param("scheme-code", "Test-a")).andExpect(MockMvcResultMatchers.status().isBadRequest());

		log.info("getAllActiveschemeCode_successTest successful");
	}

	@Test
	public void deleteSchemeCode_IdExistsTest() throws Exception {
		SchemeCodeDTO schemeCodeDTO = getschemeCodeDTO();
		schemeCodeDTO.setId(Long.MAX_VALUE);

		String uri = "/v1/api/scheme-codes/{id}";
		RequestBuilder req = MockMvcRequestBuilders.delete(uri, schemeCodeDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schemeCodeDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("deleteDeptHeadOfAccount_IdExistsTest successful");
	}
}
