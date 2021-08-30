package com.dxc.eproc.estimate.integration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.IntegrationTest;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupService;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupLumpsumDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkSubEstimateGroupLumpsumResourceIT.
 */
@IntegrationTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")

public class WorkSubEstimateGroupLumpsumResourceIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupLumpsumResourceIT.class);
	/** The work estimate DTO. */
	private static WorkEstimateDTO workEstimateDTO;
	/** The sub estimate DTO. */
	private static SubEstimateDTO subEstimateDTO;
	/** The work sub estimate group DTO. */
	private static WorkSubEstimateGroupDTO workSubEstimateGroupDTO;

	/** The work estimate tendered DTO. */
	private static WorkEstimateDTO workEstimateTenderedDTO;
	/** The object mapper. */
	@Autowired
	private ObjectMapper objectMapper;

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/** The sub estimate service. */
	@Autowired
	private SubEstimateService subEstimateService;

	/** The work sub estimate group service. */
	@Autowired
	private WorkSubEstimateGroupService workSubEstimateGroupService;

	/** The mvc. */
	@Autowired
	private MockMvc mvc;

	/**
	 * Inits the.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public void init() throws Exception {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");

		System.setProperty("spring.profiles.active", "test");

		workEstimateTenderedDTO = getWorkEstimate();
		workEstimateTenderedDTO.setWorkEstimateNumber("testENT_lp");
		workEstimateTenderedDTO.setStatus(WorkEstimateStatus.TENDERED);
		workEstimateTenderedDTO = workEstimateService.save(workEstimateTenderedDTO);

		workEstimateDTO = getWorkEstimate();
		workEstimateDTO = workEstimateService.save(workEstimateDTO);

		subEstimateDTO = new SubEstimateDTO();
		subEstimateDTO.setEstimateTotal(new BigDecimal(100));
		subEstimateDTO.setSubEstimateName("testNo l");
		subEstimateDTO.setWorkEstimateId(workEstimateDTO.getId());
		subEstimateDTO = subEstimateService.save(subEstimateDTO);

		List<Long> subEstimateIds = new ArrayList<>();
		subEstimateIds.add(subEstimateDTO.getId());
		workSubEstimateGroupDTO = new WorkSubEstimateGroupDTO();
		workSubEstimateGroupDTO.setWorkEstimateId(workEstimateDTO.getId());
		workSubEstimateGroupDTO.setDescription("test1_desc_lp");
		workSubEstimateGroupDTO.setSubEstimateIds(subEstimateIds);
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workSubEstimateGroupDTO))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		workSubEstimateGroupDTO = objectMapper.readValue(content, WorkSubEstimateGroupDTO.class);

		Long subEstimateId = subEstimateDTO.getId();
		subEstimateDTO = new SubEstimateDTO();
		subEstimateDTO.setId(subEstimateId);
		subEstimateDTO.setWorkSubEstimateGroupId(workSubEstimateGroupDTO.getId());
		subEstimateDTO = subEstimateService.partialUpdate(subEstimateDTO).get();
	}

	/**
	 * Gets the work estimate.
	 *
	 * @return the work estimate
	 */
	public WorkEstimateDTO getWorkEstimate() {
		WorkEstimateDTO workEstimateDTO = new WorkEstimateDTO();
		workEstimateDTO.setWorkEstimateNumber("testEN_l");
		workEstimateDTO.setStatus(WorkEstimateStatus.DRAFT);
		workEstimateDTO.setDeptId(1L);
		workEstimateDTO.setLocationId(1L);
		workEstimateDTO.setFileNumber("testFN_l");
		workEstimateDTO.setName("testN_l");
		workEstimateDTO.setEstimateTypeId(1L);
		workEstimateDTO.setWorkTypeId(1L);
		workEstimateDTO.setWorkCategoryId(1L);
		workEstimateDTO.setApprovedBudgetYn(true);
		return workEstimateDTO;
	}

	/**
	 * Gets the lumpsum DTO.
	 *
	 * @return the lumpsum DTO
	 */
	public WorkSubEstimateGroupLumpsumDTO getLumpsumDTO() {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = new WorkSubEstimateGroupLumpsumDTO();
		lumpsumDTO.setId(1L);
		lumpsumDTO.setName("TEST");
		lumpsumDTO.setWorkSubEstimateGroupId(1L);
		lumpsumDTO.setApproxRate(new BigDecimal(100));

		return lumpsumDTO;
	}

	/**
	 * Creates the work sub estimate group Lumpsum fixed success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroupLumpsum_FixedSuccessTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated());
		log.info("createWorkSubEstimateGroupLumpsum_FixedSuccessTest successful!!");
	}

	/**
	 * Creates the work sub estimate group Lumpsum invalid work estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroupLumpsum_invalidWorkEstimateIdTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, -1L, workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("createWorkSubEstimateGroupLumpsum_invalidWorkEstimateIdTest successful!!");
	}

	/**
	 * Creates the work sub estimate group Lumpsum incorrect status test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroupLumpsum_incorrectStatusTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum";
		RequestBuilder req = MockMvcRequestBuilders
				.post(uri, workEstimateTenderedDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("createWorkSubEstimateGroupLumpsum_incorrectStatusTest successful!!");
	}

	/**
	 * Creates the work sub estimate group Lumpsum invalid group id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroupLumpsum_invalidGroupIdTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), -1L)
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("createWorkSubEstimateGroupLumpsum_invalidGroupIdTest successful!!");
	}

	/**
	 * Validate approx rate test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void validateApproxRateTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		lumpsumDTO.setApproxRate(BigDecimal.valueOf(-1));
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("validateApproxRateTest successful!!");
	}

	/**
	 * Validate lumpsum name test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void validateLumpsumNameTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		lumpsumDTO.setName("Test");
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}";
		RequestBuilder req = MockMvcRequestBuilders
				.put(uri, workEstimateDTO.getName(), workSubEstimateGroupDTO.getId(), 1L)
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("validateLumpsumNameTest successful!!");
	}

	/**
	 * Update work sub estimate group Lumpsum fixed success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupLumpsum_FixedSuccessTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		String content = mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
				.getResponse().getContentAsString();
		WorkSubEstimateGroupLumpsumDTO savedValue = objectMapper.readValue(content,
				WorkSubEstimateGroupLumpsumDTO.class);
		savedValue.setName("updatedtestF+o");
		uri += "/{id}";
		req = MockMvcRequestBuilders
				.put(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), savedValue.getId())
				.content(TestUtil.convertObjectToJsonBytes(savedValue)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("updateWorkSubEstimateGroupLumpsum_FixedSuccessTest successful!!");
	}

	/**
	 * Update work sub estimate group Lumpsum invalid work estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupLumpsum_invalidWorkEstimateIdTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, -1L, workSubEstimateGroupDTO.getId(), -1L)
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("updateWorkSubEstimateGroupLumpsum_invalidWorkEstimateIdTest successful!!");
	}

	/**
	 * Update work sub estimate group Lumpsum incorrect status test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupLumpsum_incorrectStatusTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}";
		RequestBuilder req = MockMvcRequestBuilders
				.put(uri, workEstimateTenderedDTO.getId(), workSubEstimateGroupDTO.getId(), -1L)
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateWorkSubEstimateGroupLumpsum_incorrectStatusTest successful!!");
	}

	/**
	 * Update work sub estimate group Lumpsum invalid group id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupLumpsum_invalidGroupIdTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, workEstimateDTO.getId(), -1L, 1L)
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("updateWorkSubEstimateGroupLumpsum_invalidGroupIdTest successful!!");
	}

	/**
	 * Update work sub estimate group lumpsum invalid id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupLumpsum_invalidIdTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}";
		RequestBuilder req = MockMvcRequestBuilders
				.put(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), -1L)
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("updateWorkSubEstimateGroupLumpsum_invalidGroupIdTest successful!!");
	}

	/**
	 * Gets the all work sub estimate group lumpsums success test.
	 *
	 * @return the all work sub estimate group lumpsums success test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkSubEstimateGroupLumpsums_successTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated());
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getAllWorkSubEstimateGroupLumpsums_successTest successful!!");
	}

	/**
	 * Gets the all work sub estimate group Lumpsum invalid work estimate id test.
	 *
	 * @return the all work sub estimate group Lumpsum invalid work estimate id test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkSubEstimateGroupLumpsums_invalidWorkEstimateIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum";
		mvc.perform(MockMvcRequestBuilders.get(uri, -1L, workSubEstimateGroupDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllWorkSubEstimateGroupLumpsums_invalidWorkEstimateIdTest successful!!");
	}

	/**
	 * Gets the all work sub estimate group Lumpsum invalid group id test.
	 *
	 * @return the all work sub estimate group Lumpsum invalid group id test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkSubEstimateGroupLumpsums_invalidGroupIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllWorkSubEstimateGroupLumpsums_invalidGroupIdTest successful!!");
	}

	/**
	 * Gets the all work sub estimate group Lumpsums no Lumpsum present test.
	 *
	 * @return the all work sub estimate group lumpsums no Lumpsum present test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkSubEstimateGroupLumpsums_noOverheadsPresentTest() throws Exception {
		WorkSubEstimateGroupDTO workSubEstimateGroupDTO = new WorkSubEstimateGroupDTO();
		workSubEstimateGroupDTO.setWorkEstimateId(workEstimateDTO.getId());
		workSubEstimateGroupDTO.setDescription("test_desc_o");
		workSubEstimateGroupDTO = workSubEstimateGroupService.save(workSubEstimateGroupDTO);

		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllWorkSubEstimateGroupLumpsums_noOverheadsPresentTest successful!!");
	}

	/**
	 * Gets the work sub estimate group Lumpsum invalid work estimate id test.
	 *
	 * @return the work sub estimate group Lumpsum invalid work estimate id test
	 * @throws Exception the exception
	 */
	@Test
	public void getWorkSubEstimateGroupLumpsum_invalidWorkEstimateIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, -1L, workSubEstimateGroupDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getWorkSubEstimateGroupLumpsum_invalidWorkEstimateIdTest successful!!");
	}

	/**
	 * Gets the work sub estimate group lumpsum success test.
	 *
	 * @return the work sub estimate group lumpsum success test
	 * @throws Exception the exception
	 */
	@Test
	public void getWorkSubEstimateGroupLumpsum_successTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		String content = mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
				.getResponse().getContentAsString();
		lumpsumDTO = objectMapper.readValue(content, WorkSubEstimateGroupLumpsumDTO.class);
		uri += "/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(),
				lumpsumDTO.getId())).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getWorkSubEstimateGroupLumpsum_successTest successful!!");
	}

	/**
	 * Gets the work sub estimate group Lumpsum invalid group id test.
	 *
	 * @return the work sub estimate group Lumpsum invalid group id test
	 * @throws Exception the exception
	 */
	@Test
	public void getWorkSubEstimateGroupLumpsum_invalidGroupIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), -1L, -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getWorkSubEstimateGroupLumpsum_invalidGroupIdTest successful!!");
	}

	/**
	 * Gets the work sub estimate group Lumpsum invalid overhead id test.
	 *
	 * @return the work sub estimate group Lumpsum invalid overhead id test
	 * @throws Exception the exception
	 */
	@Test
	public void getWorkSubEstimateGroupLumpsum_invalidOverheadIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getWorkSubEstimateGroupLumpsum_invalidOverheadIdTest successful!!");
	}

	/**
	 * Delete work sub estimate group Lumpsum success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroupLumpsum_successTest() throws Exception {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = getLumpsumDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(lumpsumDTO)).contentType(MediaType.APPLICATION_JSON);
		String content = mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
				.getResponse().getContentAsString();
		lumpsumDTO = objectMapper.readValue(content, WorkSubEstimateGroupLumpsumDTO.class);
		uri += "/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(),
				lumpsumDTO.getId())).andExpect(MockMvcResultMatchers.status().isNoContent());
		log.info("deleteWorkSubEstimateGroupLumpsum_successTest successful!!");
	}

	/**
	 * Delete work sub estimate group Lumpsum invalid work estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroupLumpsum_invalidWorkEstimateIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, -1L, workSubEstimateGroupDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("deleteWorkSubEstimateGroupLumpsum_invalidWorkEstimateIdTest successful!!");
	}

	/**
	 * Delete work sub estimate group Lumpsum incorrect status test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroupLumpsum_incorrectStatusTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, workEstimateTenderedDTO.getId(), -1L, -1L))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("deleteWorkSubEstimateGroupLumpsum_incorrectStatusTest successful!!");
	}

	/**
	 * Delete work sub estimate group Lumpsum invalid group id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroupLumpsum_invalidGroupIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, workEstimateDTO.getId(), -1L, -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("deleteWorkSubEstimateGroupLumpsum_invalidGroupIdTest successful!!");
	}

	/**
	 * Delete work sub estimate group lumpsum invalid test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroupLumpsum_invalidTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("deleteWorkSubEstimateGroupLumpsum_invalidGroupIdTest successful!!");
	}

	/**
	 * Delete work sub estimate group Lumpsum invalid overhead id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroupLumpsum_invalidLumpsumIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("deleteWorkSubEstimateGroupLumpsum_invalidLumpsumIdTest successful!!");
	}
}
