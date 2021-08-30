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
import com.dxc.eproc.estimate.model.DeptHeadOfAccount;
import com.dxc.eproc.estimate.repository.DeptHeadOfAccountRepository;
import com.dxc.eproc.estimate.service.dto.DeptHeadOfAccountDTO;
import com.dxc.eproc.estimate.service.mapper.DeptHeadOfAccountMapper;

/**
 * Integration tests for the {@link DeptHeadOfAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
public class DeptHeadOfAccountResourceIT extends AbstractTestNGSpringContextTests {

	private final Logger log = LoggerFactory.getLogger(DeptHeadOfAccountResourceIT.class);

	@Autowired
	private DeptHeadOfAccountRepository deptHeadOfAccountRepository;

	@Autowired
	private DeptHeadOfAccountMapper deptHeadOfAccountMapper;

	@Autowired
	private MockMvc mvc;

	private DeptHeadOfAccount deptHeadOfAccount;

	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
		deptHeadOfAccount = new DeptHeadOfAccount();

	}

	public DeptHeadOfAccountDTO getdeptHeadOfAccountDTO() throws Exception {

		DeptHeadOfAccountDTO deptHeadOfAccountDTO = new DeptHeadOfAccountDTO();
		deptHeadOfAccountDTO.setDeptId(1L);
		deptHeadOfAccountDTO.setHeadOfAccount("Test-A");
		deptHeadOfAccountDTO.setHoaDescription("Test-D");
		deptHeadOfAccountDTO.setActiveYn(false);
		return deptHeadOfAccountDTO;

	}

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static DeptHeadOfAccount createEntity() {
		DeptHeadOfAccount deptHeadOfAccount = new DeptHeadOfAccount();
		deptHeadOfAccount.setDeptId(1L);
		deptHeadOfAccount.setHeadOfAccount("test");
		deptHeadOfAccount.setHoaDescription("Test");
		deptHeadOfAccount.setActiveYn(false);
		return deptHeadOfAccount;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	@Test
	public void createDeptHeadOfAccount_SuccessTest() throws Exception {
		DeptHeadOfAccountDTO deptHeadOfAccountDTO = getdeptHeadOfAccountDTO();
		String uri = "/v1/api/dept-head-of-accounts";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(deptHeadOfAccountDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated());
		log.info("createDeptHeadOfAccount_SuccessTest() successful!!");
	}

	@Test
	public void createDeptHeadOfAccount_IdExists() throws Exception {
		DeptHeadOfAccountDTO deptHeadOfAccountDTO = getdeptHeadOfAccountDTO();
		deptHeadOfAccountDTO.setId(1L);
		String uri = "/v1/api/dept-head-of-accounts";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(deptHeadOfAccountDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("createDeptHeadOfAccount_IdExists() successful!!");
	}

	@Test
	public void updateDeptHeadOfAccount_SuccessTest() throws Exception {
		DeptHeadOfAccount deptHeadOfAccount = createEntity();
		deptHeadOfAccountRepository.save(deptHeadOfAccount);

		DeptHeadOfAccountDTO deptHeadOfAccountDTO = deptHeadOfAccountMapper.toDto(deptHeadOfAccount);
		deptHeadOfAccountDTO.setActiveYn(true);
		deptHeadOfAccountDTO.setHeadOfAccount("test-head-of-account");

		String uri = "/v1/api/dept-head-of-accounts/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, deptHeadOfAccountDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deptHeadOfAccountDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isOk());

		log.info("updateDeptHeadOfAccount_SuccessTest() successful!!");
	}

	@Test
	public void updateDeptHeadOfAccount_IdExistsTest() throws Exception {
		DeptHeadOfAccountDTO deptHeadOfAccountDTO = getdeptHeadOfAccountDTO();
		deptHeadOfAccountDTO.setId(Long.MAX_VALUE);

		String uri = "/v1/api/dept-head-of-accounts/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, deptHeadOfAccountDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deptHeadOfAccountDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateDeptHeadOfAccount_IdExistsTest successful");
	}

	@Test
	public void partialUpdateDeptHeadOfAccount_SuccessTest() throws Exception {
		DeptHeadOfAccount deptHeadOfAccount = createEntity();
		deptHeadOfAccountRepository.save(deptHeadOfAccount);

		DeptHeadOfAccountDTO deptHeadOfAccountDTO = deptHeadOfAccountMapper.toDto(deptHeadOfAccount);
		deptHeadOfAccountDTO.setActiveYn(true);
		deptHeadOfAccountDTO.setHeadOfAccount("test-head-of-account");

		String uri = "/v1/api/dept-head-of-accounts/{id}";
		/*
		 * RequestBuilder req = MockMvcRequestBuilders.patch(uri,
		 * deptHeadOfAccountDTO.getId()).accept(MediaType.APPLICATION_JSON)
		 * .content(TestUtil.convertObjectToJsonBytes(deptHeadOfAccountDTO))
		 * .contentType(MediaType.APPLICATION_JSON);
		 * mvc.perform(req).andExpect(MockMvcResultMatchers.status().isOk());
		 */

		RequestBuilder req1 = MockMvcRequestBuilders.patch(uri, deptHeadOfAccountDTO.getId())
				.accept(MediaType.APPLICATION_JSON).contentType("application/merge-patch+json")
				.content(TestUtil.convertObjectToJsonBytes(deptHeadOfAccount));
		mvc.perform(req1).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("partialUpdateDeptHeadOfAccount_SuccessTest()!!");
	}

	@Test
	public void partialUpdateDeptHeadOfAccount_IdExistsTest() throws Exception {
		DeptHeadOfAccountDTO deptHeadOfAccountDTO = getdeptHeadOfAccountDTO();
		deptHeadOfAccountDTO.setId(Long.MAX_VALUE);

		String uri = "/v1/api/dept-head-of-accounts/{id}";
		RequestBuilder req1 = MockMvcRequestBuilders.patch(uri, deptHeadOfAccountDTO.getId())
				.accept(MediaType.APPLICATION_JSON).contentType("application/merge-patch+json")
				.content(TestUtil.convertObjectToJsonBytes(deptHeadOfAccount));
		mvc.perform(req1).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateDeptHeadOfAccount_SuccessTest() successful!!");
	}

	@Test
	public void getAllDeptHeadOfAccounts_successTest() throws Exception {
		DeptHeadOfAccountDTO deptHeadOfAccountDTO = getdeptHeadOfAccountDTO();
		String uri = "/v1/api/dept-head-of-accounts";
		mvc.perform(MockMvcRequestBuilders.get(uri, deptHeadOfAccountDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());

		log.info("getAllDeptHeadOfAccounts_successTest successful");
	}

	@Test
	public void getAllActiveDeptHeadOfAccounts_successTest() throws Exception {
		DeptHeadOfAccountDTO deptHeadOfAccountDTO = getdeptHeadOfAccountDTO();
		String uri = "/v1/api/dept-head-of-accounts-active";
		mvc.perform(MockMvcRequestBuilders.get(uri, deptHeadOfAccountDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());

		log.info("getAllActiveDeptHeadOfAccounts_successTest successful");
	}

	@Test
	public void getDeptHeadOfAccount_successTest() throws Exception {
		DeptHeadOfAccount deptHeadOfAccount = createEntity();
		deptHeadOfAccountRepository.save(deptHeadOfAccount);

		String uri = "/v1/api/dept-head-of-accounts/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, deptHeadOfAccount.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getDeptHeadOfAccount_successTest Successful!!");
	}

	@Test
	public void getAllActiveDeptHeadOfAccountsList_successTest() throws Exception {
		DeptHeadOfAccount deptHeadOfAccount = createEntity();
		deptHeadOfAccountRepository.save(deptHeadOfAccount);

		String uri = "/v1/api/dept-master/{deptId}/active-head-of-accounts";
		mvc.perform(MockMvcRequestBuilders.get(uri, deptHeadOfAccount.getDeptId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getDeptHeadOfAccount_successTest Successful!!");
	}

	@Test
	public void deleteDeptHeadOfAccount_SuccessTest() throws Exception {
		DeptHeadOfAccount deptHeadOfAccount = createEntity();
		deptHeadOfAccountRepository.save(deptHeadOfAccount);

		DeptHeadOfAccountDTO deptHeadOfAccountDTO = deptHeadOfAccountMapper.toDto(deptHeadOfAccount);
		deptHeadOfAccountDTO.setActiveYn(true);
		deptHeadOfAccountDTO.setHeadOfAccount("test-head-of-account");

		String uri = "/v1/api/dept-head-of-accounts/{id}";
		RequestBuilder req = MockMvcRequestBuilders.delete(uri, deptHeadOfAccountDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deptHeadOfAccountDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNoContent());

		log.info("deleteDeptHeadOfAccount_SuccessTest() successful!!");
	}

	@Test
	public void deleteDeptHeadOfAccount_IdExistsTest() throws Exception {
		DeptHeadOfAccountDTO deptHeadOfAccountDTO = getdeptHeadOfAccountDTO();
		deptHeadOfAccountDTO.setId(Long.MAX_VALUE);

		String uri = "/v1/api/dept-head-of-accounts/{id}";
		RequestBuilder req = MockMvcRequestBuilders.delete(uri, deptHeadOfAccountDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deptHeadOfAccountDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("deleteDeptHeadOfAccount_IdExistsTest successful");
	}
}
