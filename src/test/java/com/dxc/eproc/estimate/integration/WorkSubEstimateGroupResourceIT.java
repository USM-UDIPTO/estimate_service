package com.dxc.eproc.estimate.integration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.IntegrationTest;
import com.dxc.eproc.estimate.controller.WorkSubEstimateGroupController;
import com.dxc.eproc.estimate.enumeration.ValueType;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupLumpsumService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupOverheadService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupService;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupLumpsumDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupOverheadDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * Integration tests for the {@link WorkSubEstimateGroupController} REST
 * controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class WorkSubEstimateGroupResourceIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupResourceIT.class);

	/** The work estimate DTO. */
	private static WorkEstimateDTO workEstimateDTO;

	/** The work estimate tendered DTO. */
	private static WorkEstimateDTO workEstimateTenderedDTO;

	/** The sub estimate DTO. */
	private static SubEstimateDTO subEstimateDTO;

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

	/** The work sub estimate group lumpsum service. */
	@Autowired
	private WorkSubEstimateGroupLumpsumService workSubEstimateGroupLumpsumService;

	/** The work sub estimate group overhead service. */
	@Autowired
	private WorkSubEstimateGroupOverheadService workSubEstimateGroupOverheadService;

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
		workEstimateTenderedDTO.setWorkEstimateNumber("testET g");
		workEstimateTenderedDTO.setStatus(WorkEstimateStatus.TENDERED);
		workEstimateTenderedDTO = workEstimateService.save(workEstimateTenderedDTO);

		workEstimateDTO = getWorkEstimate();
		workEstimateDTO = workEstimateService.save(workEstimateDTO);
	}

	/**
	 * Gets the work estimate.
	 *
	 * @return the work estimate
	 */
	public WorkEstimateDTO getWorkEstimate() {
		WorkEstimateDTO workEstimateDTO = new WorkEstimateDTO();
		workEstimateDTO.setWorkEstimateNumber("testEN g");
		workEstimateDTO.setStatus(WorkEstimateStatus.INITIAL);
		workEstimateDTO.setDeptId(1L);
		workEstimateDTO.setLocationId(1L);
		workEstimateDTO.setFileNumber("testFN o");
		workEstimateDTO.setName("testN o");
		workEstimateDTO.setEstimateTypeId(1L);
		workEstimateDTO.setWorkTypeId(1L);
		workEstimateDTO.setWorkCategoryId(1L);
		workEstimateDTO.setApprovedBudgetYn(true);
		workEstimateDTO.setProvisionalAmount(BigDecimal.valueOf(10000.0000));
		workEstimateDTO.setGrantAllocatedAmount(BigDecimal.valueOf(10000.0000));
		workEstimateDTO.setGroupOverheadTotal(BigDecimal.TEN);
		workEstimateDTO.setGroupLumpsumTotal(BigDecimal.TEN);
		return workEstimateDTO;
	}

	/**
	 * Gets the group.
	 *
	 * @return the group
	 * @throws Exception the exception
	 */
	public WorkSubEstimateGroupDTO getGroup() throws Exception {
		subEstimateDTO = new SubEstimateDTO();
		subEstimateDTO.setEstimateTotal(new BigDecimal(200));
		subEstimateDTO.setSubEstimateName("testNSE g");
		subEstimateDTO.setWorkEstimateId(workEstimateDTO.getId());
		subEstimateDTO = subEstimateService.save(subEstimateDTO);

		List<Long> subEstimateIds = new ArrayList<>();
		subEstimateIds.add(subEstimateDTO.getId());
		WorkSubEstimateGroupDTO workSubEstimateGroupDTO = new WorkSubEstimateGroupDTO();
		workSubEstimateGroupDTO.setWorkEstimateId(workEstimateDTO.getId());
		workSubEstimateGroupDTO.setDescription("test desc g");
		workSubEstimateGroupDTO.setSubEstimateIds(subEstimateIds);
		return workSubEstimateGroupDTO;
	}

	/**
	 * Gets the saved group.
	 *
	 * @return the saved group
	 * @throws Exception the exception
	 */
	public WorkSubEstimateGroupDTO getSavedGroup() throws Exception {
		subEstimateDTO = new SubEstimateDTO();
		subEstimateDTO.setEstimateTotal(new BigDecimal(200));
		subEstimateDTO.setSubEstimateName("testNSE g");
		subEstimateDTO.setWorkEstimateId(workEstimateDTO.getId());
		subEstimateDTO = subEstimateService.save(subEstimateDTO);

		List<Long> subEstimateIds = new ArrayList<>();
		subEstimateIds.add(subEstimateDTO.getId());
		WorkSubEstimateGroupDTO workSubEstimateGroupDTO = new WorkSubEstimateGroupDTO();
		workSubEstimateGroupDTO.setWorkEstimateId(workEstimateDTO.getId());
		workSubEstimateGroupDTO.setDescription("test desc g");
		workSubEstimateGroupDTO.setSubEstimateIds(subEstimateIds);
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workSubEstimateGroupDTO))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		workSubEstimateGroupDTO = objectMapper.readValue(content, WorkSubEstimateGroupDTO.class);
		return workSubEstimateGroupDTO;
	}

	/**
	 * Save lumpsum.
	 *
	 * @param groupId the group id
	 */
	public void saveLumpsum(Long groupId) {
		WorkSubEstimateGroupLumpsumDTO lumpsumDTO = new WorkSubEstimateGroupLumpsumDTO();
		lumpsumDTO.setName("lumpName g");
		lumpsumDTO.setWorkSubEstimateGroupId(groupId);
		lumpsumDTO.setApproxRate(BigDecimal.ZERO);
		workSubEstimateGroupLumpsumService.save(lumpsumDTO);
	}

	/**
	 * Save added overhead.
	 *
	 * @param groupId the group id
	 */
	public void saveAddedOverhead(Long groupId) {
		WorkSubEstimateGroupOverheadDTO overheadDTO = new WorkSubEstimateGroupOverheadDTO();
		overheadDTO.setDescription("testD o");
		overheadDTO.setEnteredValue(BigDecimal.ZERO);
		overheadDTO.setCode("B");
		overheadDTO.setFinalYn(true);
		overheadDTO.setValueFixedYn(true);
		overheadDTO.setValueType(ValueType.ADDED);
		overheadDTO.setWorkSubEstimateGroupId(groupId);
		overheadDTO.setOverheadValue(BigDecimal.ZERO);
		workSubEstimateGroupOverheadService.save(overheadDTO);
	}

	/**
	 * Creates the work sub estimate group success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroup_SuccessTest() throws Exception {
		WorkSubEstimateGroupDTO groupDTO = getGroup();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated());
		log.info("createWorkSubEstimateGroup_SuccessTest successful!!");
	}

	/**
	 * Creates the work sub estimate group invalid work estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroup_invalidWorkEstimateIdTest() throws Exception {
		WorkSubEstimateGroupDTO groupDTO = getGroup();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, -1L).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(groupDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("createWorkSubEstimateGroup_invalidWorkEstimateIdTest successful!!");
	}

	/**
	 * Creates the work sub estimate group incorrect status test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroup_incorrectStatusTest() throws Exception {
		WorkSubEstimateGroupDTO groupDTO = getGroup();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateTenderedDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("createWorkSubEstimateGroup_invalidWorkEstimateIdTest successful!!");
	}

	/**
	 * Creates the work sub estimate group no sub estimates selected test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroup_noSubEstimatesSelectedTest() throws Exception {
		WorkSubEstimateGroupDTO groupDTO = getGroup();
		groupDTO.setSubEstimateIds(null);
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("createWorkSubEstimateGroup_invalidWorkEstimateIdTest successful!!");
	}

	/**
	 * Creates the work sub estimate group invalid sub estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroup_invalidSubEstimateIdTest() throws Exception {
		WorkSubEstimateGroupDTO groupDTO = getGroup();
		List<Long> subEstimateIds = new ArrayList<>();
		subEstimateIds.add(-1L);
		groupDTO.setSubEstimateIds(subEstimateIds);
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("createWorkSubEstimateGroup_invalidSubEstimateIdTest successful!!");
	}

	/**
	 * Update work sub estimate group success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroup_successTest() throws Exception {
		WorkSubEstimateGroupDTO groupDTO = getSavedGroup();
		groupDTO.setDescription("Updated desc g");
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, workEstimateDTO.getId(), groupDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("updateWorkSubEstimateGroup_successTest Successful!!");
	}

	/**
	 * Update work sub estimate group invalid work estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroup_invalidWorkEstimateIdTest() throws Exception {
		WorkSubEstimateGroupDTO groupDTO = getSavedGroup();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, -1L, groupDTO.getId()).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(groupDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("updateWorkSubEstimateGroup_invalidWorkEstimateIdTest Successful!!");
	}

	/**
	 * Update work sub estimate group incorrect status test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroup_incorrectStatusTest() throws Exception {
		WorkSubEstimateGroupDTO groupDTO = getSavedGroup();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, workEstimateTenderedDTO.getId(), groupDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateWorkSubEstimateGroup_incorrectStatusTest Successful!!");
	}

	/**
	 * Update work sub estimate group invalid group id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroup_invalidGroupIdTest() throws Exception {
		WorkSubEstimateGroupDTO groupDTO = getSavedGroup();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, workEstimateDTO.getId(), -1L)
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("updateWorkSubEstimateGroup_invalidGroupIdTest Successful!!");
	}

	/**
	 * Gets the all work sub estimate groups success test.
	 *
	 * @return the all work sub estimate groups success test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkSubEstimateGroups_successTest() throws Exception {
		getSavedGroup();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-groups";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getAllWorkSubEstimateGroups_successTest Successful!!");
	}

	/**
	 * Gets the all work sub estimate groups invalid work estimate id test.
	 *
	 * @return the all work sub estimate groups invalid work estimate id test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkSubEstimateGroups_invalidWorkEstimateIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-groups";
		mvc.perform(MockMvcRequestBuilders.get(uri, -1L)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllWorkSubEstimateGroups_invalidWorkEstimateIdTest Successful!!");
	}

	/**
	 * Gets the all work sub estimate groups no groups present test.
	 *
	 * @return the all work sub estimate groups no groups present test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkSubEstimateGroups_noGroupsPresentTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-groups";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateTenderedDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllWorkSubEstimateGroups_noGroupsPresentTest Successful!!");
	}

	/**
	 * Gets the work sub estimate group success test.
	 *
	 * @return the work sub estimate group success test
	 * @throws Exception the exception
	 */
	@Test
	public void getWorkSubEstimateGroup_successTest() throws Exception {
		WorkSubEstimateGroupDTO groupDTO = getSavedGroup();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), groupDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getWorkSubEstimateGroup_successTest Successful!!");
	}

	/**
	 * Gets the work sub estimate group invalid work estimate id test.
	 *
	 * @return the work sub estimate group invalid work estimate id test
	 * @throws Exception the exception
	 */
	@Test
	public void getWorkSubEstimateGroup_invalidWorkEstimateIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, -1L, -1L)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getWorkSubEstimateGroup_invalidWorkEstimateIdTest Successful!!");
	}

	/**
	 * Gets the work sub estimate group invalid group id test.
	 *
	 * @return the work sub estimate group invalid group id test
	 * @throws Exception the exception
	 */
	@Test
	public void getWorkSubEstimateGroup_invalidGroupIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getWorkSubEstimateGroup_invalidGroupIdTest Successful!!");
	}

	/**
	 * Delete work sub estimate group success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroup_successTest() throws Exception {
		WorkSubEstimateGroupDTO groupDTO = getSavedGroup();
		WorkSubEstimateGroupDTO groupDTOToDelete = new WorkSubEstimateGroupDTO();
		groupDTOToDelete.setId(groupDTO.getId());
		groupDTOToDelete.setOverheadTotal(BigDecimal.TEN);
		groupDTOToDelete.setLumpsumTotal(BigDecimal.TEN);
		workSubEstimateGroupService.partialUpdate(groupDTOToDelete);

		saveLumpsum(groupDTO.getId());

		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, workEstimateDTO.getId(), groupDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
		log.info("deleteWorkSubEstimateGroup_successTest Successful!!");
	}

	/**
	 * Delete work sub estimate group invalid work estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroup_invalidWorkEstimateIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, -1L, -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("deleteWorkSubEstimateGroup_invalidWorkEstimateIdTest Successful!!");
	}

	/**
	 * Delete work sub estimate group incorrect status test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroup_incorrectStatusTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, workEstimateTenderedDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("deleteWorkSubEstimateGroup_incorrectStatusTest Successful!!");
	}

	/**
	 * Delete work sub estimate group invalid group id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroup_invalidGroupIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, workEstimateDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("deleteWorkSubEstimateGroup_invalidGroupIdTest Successful!!");
	}

	/**
	 * Verify group overhead or lumpsum success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void verifyGroupOverheadOrLumpsum_successTest() throws Exception {
		WorkSubEstimateGroupDTO groupDTO = getSavedGroup();
		saveAddedOverhead(groupDTO.getId());
		saveLumpsum(groupDTO.getId());
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}/verify";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), groupDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("verifyGroupOverheadOrLumpsum_successTest Successful!!");
	}

	/**
	 * Verify group overhead or lumpsum invalid work estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void verifyGroupOverheadOrLumpsum_invalidWorkEstimateIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}/verify";
		mvc.perform(MockMvcRequestBuilders.get(uri, -1L, -1L)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("verifyGroupOverheadOrLumpsum_invalidWorkEstimateIdTest Successful!!");
	}

	/**
	 * Verify group overhead or lumpsum invalid group id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void verifyGroupOverheadOrLumpsum_invalidGroupIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}/verify";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("verifyGroupOverheadOrLumpsum_invalidGroupIdTest Successful!!");
	}

	/**
	 * Verify group overhead or lumpsum no group contents test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void verifyGroupOverheadOrLumpsum_noGroupContentsTest() throws Exception {
		WorkSubEstimateGroupDTO groupDTO = getSavedGroup();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}/verify";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), groupDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("verifyGroupOverheadOrLumpsum_noGroupContentsTest Successful!!");
	}
}
