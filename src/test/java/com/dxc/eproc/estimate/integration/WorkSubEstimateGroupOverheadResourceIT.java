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
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.IntegrationTest;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupService;
import com.dxc.eproc.estimate.service.dto.AggregateOverheadsDTO;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupOverheadDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkSubEstimateGroupOverheadControllerTestIT.
 */
@IntegrationTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class WorkSubEstimateGroupOverheadResourceIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupOverheadResourceIT.class);

	/** The work estimate DTO. */
	private static WorkEstimateDTO workEstimateDTO;

	/** The work estimate tendered DTO. */
	private static WorkEstimateDTO workEstimateTenderedDTO;

	/** The sub estimate DTO. */
	private static SubEstimateDTO subEstimateDTO;

	/** The work sub estimate group DTO. */
	private static WorkSubEstimateGroupDTO workSubEstimateGroupDTO;

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
		workEstimateTenderedDTO.setWorkEstimateNumber("testENT o");
		workEstimateTenderedDTO.setStatus(WorkEstimateStatus.TENDERED);
		workEstimateTenderedDTO = workEstimateService.save(workEstimateTenderedDTO);

		workEstimateDTO = getWorkEstimate();
		workEstimateDTO = workEstimateService.save(workEstimateDTO);

		subEstimateDTO = new SubEstimateDTO();
		subEstimateDTO.setEstimateTotal(new BigDecimal(200));
		subEstimateDTO.setSubEstimateName("testN o");
		subEstimateDTO.setWorkEstimateId(workEstimateDTO.getId());
		subEstimateDTO = subEstimateService.save(subEstimateDTO);

		List<Long> subEstimateIds = new ArrayList<>();
		subEstimateIds.add(subEstimateDTO.getId());
		workSubEstimateGroupDTO = new WorkSubEstimateGroupDTO();
		workSubEstimateGroupDTO.setWorkEstimateId(workEstimateDTO.getId());
		workSubEstimateGroupDTO.setDescription("test desc o");
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
		workEstimateDTO.setWorkEstimateNumber("testEN o");
		workEstimateDTO.setStatus(WorkEstimateStatus.INITIAL);
		workEstimateDTO.setDeptId(1L);
		workEstimateDTO.setLocationId(1L);
		workEstimateDTO.setFileNumber("testFN o");
		workEstimateDTO.setName("testN o");
		workEstimateDTO.setEstimateTypeId(1L);
		workEstimateDTO.setWorkTypeId(1L);
		workEstimateDTO.setWorkCategoryId(1L);
		workEstimateDTO.setApprovedBudgetYn(true);
		return workEstimateDTO;
	}

	/**
	 * Gets the overhead DTO.
	 *
	 * @return the overhead DTO
	 */
	public WorkSubEstimateGroupOverheadDTO getOverheadDTO() {
		WorkSubEstimateGroupOverheadDTO overheadDTO = new WorkSubEstimateGroupOverheadDTO();
		overheadDTO.setDescription("testD o");
		overheadDTO.setEnteredValue(new BigDecimal(100));
		overheadDTO.setFinalYn(true);
		overheadDTO.setValueFixedYn(true);
		return overheadDTO;
	}

	/**
	 * Creates the work sub estimate group overhead fixed success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroupOverhead_FixedSuccessTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated());
		log.info("createWorkSubEstimateGroupOverhead_FixedSuccessTest successful!!");
	}

	/**
	 * Creates the work sub estimate group overhead percentage success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroupOverhead_PercentageSuccessTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		AggregateOverheadsDTO aggregateOverheadsDTO = objectMapper.readValue(content, AggregateOverheadsDTO.class);

		List<Long> selectedOverheads = new ArrayList<>();
		aggregateOverheadsDTO.getOverheads().stream().forEach(overhead -> {
			selectedOverheads.add(overhead.getId());
		});

		overheadDTO.setId(null);
		overheadDTO.setValueFixedYn(false);
		overheadDTO.setSelectedOverheads(selectedOverheads);
		req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated());
		log.info("createWorkSubEstimateGroupOverhead_PercentageSuccessTest successful!!");
	}

	/**
	 * Creates the work sub estimate group overhead code AN success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroupOverhead_CodeANSuccessTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		for (int i = 0; i < 25; i++) {
			mvc.perform(req);
		}
		String content = mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
				.getResponse().getContentAsString();
		AggregateOverheadsDTO finalValue = objectMapper.readValue(content, AggregateOverheadsDTO.class);
		log.info("=======================>" + finalValue.getOverheads().size());
		Assert.assertTrue(finalValue.getOverheads().size() > 0);
		log.info("createWorkSubEstimateGroupOverhead_CodeANSuccessTest successful!!");
	}

	/**
	 * Creates the work sub estimate group overhead invalid work estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroupOverhead_invalidWorkEstimateIdTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, -1L, workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("createWorkSubEstimateGroupOverhead_invalidWorkEstimateIdTest successful!!");
	}

	/**
	 * Creates the work sub estimate group overhead incorrect status test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroupOverhead_incorrectStatusTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders
				.post(uri, workEstimateTenderedDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("createWorkSubEstimateGroupOverhead_incorrectStatusTest successful!!");
	}

	/**
	 * Creates the work sub estimate group overhead invalid group id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroupOverhead_invalidGroupIdTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), -1L)
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("createWorkSubEstimateGroupOverhead_invalidGroupIdTest successful!!");
	}

	/**
	 * Creates the work sub estimate group overhead custom validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroupOverhead_customValidationTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		overheadDTO.setEnteredValue(BigDecimal.valueOf(-1));
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("createWorkSubEstimateGroupOverhead_customValidationTest successful!!");
	}

	/**
	 * Creates the work sub estimate group overhead no overhead selected test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroupOverhead_noOverheadSelectedTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		overheadDTO.setValueFixedYn(false);
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("createWorkSubEstimateGroupOverhead_noOverheadSelectedTest successful!!");
	}

	/**
	 * Creates the work sub estimate group overhead invalid overheads test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkSubEstimateGroupOverhead_invalidOverheadsTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		List<Long> selectedOverheads = new ArrayList<>();
		selectedOverheads.add(-1L);
		overheadDTO.setValueFixedYn(false);
		overheadDTO.setSelectedOverheads(selectedOverheads);
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("createWorkSubEstimateGroupOverhead_invalidOverheadsTest successful!!");
	}

	/**
	 * Update work sub estimate group overhead fixed success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupOverhead_FixedSuccessTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		String content = mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
				.getResponse().getContentAsString();
		AggregateOverheadsDTO aggregateOverheadsDTO = objectMapper.readValue(content, AggregateOverheadsDTO.class);

		WorkSubEstimateGroupOverheadDTO savedValue = aggregateOverheadsDTO.getOverheads()
				.get(aggregateOverheadsDTO.getOverheads().size() - 1);
		savedValue.setDescription("updatedtestF o");
		uri += "/{id}";
		req = MockMvcRequestBuilders
				.put(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), savedValue.getId())
				.content(TestUtil.convertObjectToJsonBytes(savedValue)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("updateWorkSubEstimateGroupOverhead_FixedSuccessTest successful!!");
	}

	/**
	 * Update work sub estimate group overhead percentage success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupOverhead_PercentageSuccessTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		String content = mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
				.getResponse().getContentAsString();
		AggregateOverheadsDTO aggregateOverheadsDTO = objectMapper.readValue(content, AggregateOverheadsDTO.class);

		overheadDTO = aggregateOverheadsDTO.getOverheads().get(aggregateOverheadsDTO.getOverheads().size() - 1);
		List<Long> selectedOverheads = new ArrayList<>();
		selectedOverheads.add(overheadDTO.getId());
		overheadDTO = getOverheadDTO();
		req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		content = mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn().getResponse()
				.getContentAsString();
		AggregateOverheadsDTO aggregateOverheadsDTOLinked = objectMapper.readValue(content,
				AggregateOverheadsDTO.class);

		WorkSubEstimateGroupOverheadDTO savedValue = aggregateOverheadsDTOLinked.getOverheads()
				.get(aggregateOverheadsDTOLinked.getOverheads().size() - 1);
		savedValue.setDescription("updatedtestF o");
		savedValue.setValueFixedYn(false);
		savedValue.setSelectedOverheads(selectedOverheads);
		uri += "/{id}";
		req = MockMvcRequestBuilders
				.put(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), savedValue.getId())
				.content(TestUtil.convertObjectToJsonBytes(savedValue)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("updateWorkSubEstimateGroupOverhead_PercentageSuccessTest successful!!");
	}

	/**
	 * Update work sub estimate group overhead invalid work estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupOverhead_invalidWorkEstimateIdTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, -1L, workSubEstimateGroupDTO.getId(), -1L)
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("updateWorkSubEstimateGroupOverhead_invalidWorkEstimateIdTest successful!!");
	}

	/**
	 * Update work sub estimate group overhead incorrect status test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupOverhead_incorrectStatusTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}";
		RequestBuilder req = MockMvcRequestBuilders
				.put(uri, workEstimateTenderedDTO.getId(), workSubEstimateGroupDTO.getId(), -1L)
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateWorkSubEstimateGroupOverhead_incorrectStatusTest successful!!");
	}

	/**
	 * Update work sub estimate group overhead invalid group id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupOverhead_invalidGroupIdTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, workEstimateDTO.getId(), -1L, 1L)
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("updateWorkSubEstimateGroupOverhead_invalidGroupIdTest successful!!");
	}

	/**
	 * Update work sub estimate group overhead custom validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupOverhead_customValidationTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		overheadDTO.setEnteredValue(BigDecimal.valueOf(-1));
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}";
		RequestBuilder req = MockMvcRequestBuilders
				.put(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), 1L)
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateWorkSubEstimateGroupOverhead_customValidationTest successful!!");
	}

	/**
	 * Update work sub estimate group overhead invalid overhead id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupOverhead_invalidOverheadIdTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}";
		RequestBuilder req = MockMvcRequestBuilders
				.put(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), 1L)
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("updateWorkSubEstimateGroupOverhead_invalidGroupIdTest successful!!");
	}

	/**
	 * Update work sub estimate group overhead overhead linked test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupOverhead_overheadLinkedTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		String content = mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
				.getResponse().getContentAsString();
		AggregateOverheadsDTO aggregateOverheadsDTO = objectMapper.readValue(content, AggregateOverheadsDTO.class);

		overheadDTO = aggregateOverheadsDTO.getOverheads().get(aggregateOverheadsDTO.getOverheads().size() - 1);
		List<Long> selectedOverheads = new ArrayList<>();
		selectedOverheads.add(overheadDTO.getId());
		overheadDTO = getOverheadDTO();
		overheadDTO.setValueFixedYn(false);
		overheadDTO.setSelectedOverheads(selectedOverheads);
		req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated());
		uri += "/{id}";
		req = MockMvcRequestBuilders
				.put(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), selectedOverheads.get(0))
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateWorkSubEstimateGroupOverhead_overheadLinkedTest successful!!");
	}

	/**
	 * Update work sub estimate group overhead no overhead selected test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupOverhead_noOverheadSelectedTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		String content = mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
				.getResponse().getContentAsString();
		AggregateOverheadsDTO aggregateOverheadsDTO = objectMapper.readValue(content, AggregateOverheadsDTO.class);

		overheadDTO = aggregateOverheadsDTO.getOverheads().get(aggregateOverheadsDTO.getOverheads().size() - 1);
		overheadDTO.setValueFixedYn(false);
		uri += "/{id}";
		req = MockMvcRequestBuilders
				.put(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), overheadDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateWorkSubEstimateGroupOverhead_noOverheadSelectedTest successful!!");
	}

	/**
	 * Update work sub estimate group overhead invalid overheads test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkSubEstimateGroupOverhead_invalidOverheadsTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		String content = mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
				.getResponse().getContentAsString();
		AggregateOverheadsDTO aggregateOverheadsDTO = objectMapper.readValue(content, AggregateOverheadsDTO.class);

		overheadDTO = aggregateOverheadsDTO.getOverheads().get(aggregateOverheadsDTO.getOverheads().size() - 1);
		List<Long> selectedOverheads = new ArrayList<>();
		selectedOverheads.add(-1L);
		overheadDTO.setValueFixedYn(false);
		overheadDTO.setSelectedOverheads(selectedOverheads);
		uri += "/{id}";
		req = MockMvcRequestBuilders
				.put(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), overheadDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateWorkSubEstimateGroupOverhead_invalidOverheadsTest successful!!");
	}

	/**
	 * Gets the all work sub estimate group overheads success test.
	 *
	 * @return the all work sub estimate group overheads success test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkSubEstimateGroupOverheads_successTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated());
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getAllWorkSubEstimateGroupOverheads_successTest successful!!");
	}

	/**
	 * Gets the all work sub estimate group overheads invalid work estimate id test.
	 *
	 * @return the all work sub estimate group overheads invalid work estimate id
	 *         test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkSubEstimateGroupOverheads_invalidWorkEstimateIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		mvc.perform(MockMvcRequestBuilders.get(uri, -1L, workSubEstimateGroupDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllWorkSubEstimateGroupOverheads_invalidWorkEstimateIdTest successful!!");
	}

	/**
	 * Gets the all work sub estimate group overheads invalid group id test.
	 *
	 * @return the all work sub estimate group overheads invalid group id test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkSubEstimateGroupOverheads_invalidGroupIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllWorkSubEstimateGroupOverheads_invalidGroupIdTest successful!!");
	}

	/**
	 * Gets the all work sub estimate group overheads no overheads present test.
	 *
	 * @return the all work sub estimate group overheads no overheads present test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkSubEstimateGroupOverheads_noOverheadsPresentTest() throws Exception {
		WorkEstimateDTO workEstimateDTO = getWorkEstimate();
		workEstimateDTO.setWorkEstimateNumber("testENT oga");
		workEstimateDTO = workEstimateService.save(workEstimateDTO);

		WorkSubEstimateGroupDTO workSubEstimateGroupDTO = new WorkSubEstimateGroupDTO();
		workSubEstimateGroupDTO.setWorkEstimateId(workEstimateDTO.getId());
		workSubEstimateGroupDTO.setDescription("test desc o");
		workSubEstimateGroupDTO = workSubEstimateGroupService.save(workSubEstimateGroupDTO);

		SubEstimateDTO subEstimateDTO = new SubEstimateDTO();
		subEstimateDTO.setEstimateTotal(new BigDecimal(200));
		subEstimateDTO.setSubEstimateName("testN o");
		subEstimateDTO.setWorkEstimateId(workEstimateDTO.getId());
		subEstimateDTO.setWorkSubEstimateGroupId(workSubEstimateGroupDTO.getId());
		subEstimateService.save(subEstimateDTO);
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllWorkSubEstimateGroupOverheads_noOverheadsPresentTest successful!!");
	}

	/**
	 * Gets the all work sub estimate group overheads null added overhead test.
	 *
	 * @return the all work sub estimate group overheads null added overhead test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkSubEstimateGroupOverheads_nullAddedOverheadTest() throws Exception {
		SubEstimateDTO subEstimateDTO = new SubEstimateDTO();
		subEstimateDTO.setEstimateTotal(new BigDecimal(200));
		subEstimateDTO.setSubEstimateName("tstN o");
		subEstimateDTO.setWorkEstimateId(workEstimateDTO.getId());
		subEstimateDTO = subEstimateService.save(subEstimateDTO);
		List<Long> subEstimateIds = new ArrayList<>();
		subEstimateIds.add(subEstimateDTO.getId());

		WorkSubEstimateGroupDTO workSubEstimateGroupDTO = new WorkSubEstimateGroupDTO();
		workSubEstimateGroupDTO.setDescription("tst");
		workSubEstimateGroupDTO.setWorkEstimateId(workEstimateDTO.getId());
		workSubEstimateGroupDTO.setSubEstimateIds(subEstimateIds);
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId())
				.accept(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workSubEstimateGroupDTO))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		workSubEstimateGroupDTO = objectMapper.readValue(content, WorkSubEstimateGroupDTO.class);

		uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getAllWorkSubEstimateGroupOverheads_nullAddedOverheadTest successful!!");
	}

	/**
	 * Gets the work sub estimate group overhead success test.
	 *
	 * @return the work sub estimate group overhead success test
	 * @throws Exception the exception
	 */
	@Test
	public void getWorkSubEstimateGroupOverhead_successTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		String content = mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
				.getResponse().getContentAsString();
		AggregateOverheadsDTO aggregateOverheadsDTO = objectMapper.readValue(content, AggregateOverheadsDTO.class);

		List<Long> selectedOverheads = new ArrayList<>();
		aggregateOverheadsDTO.getOverheads().stream().forEach(overhead -> {
			selectedOverheads.add(overhead.getId());
		});
		uri += "/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(),
				selectedOverheads.get(selectedOverheads.size() - 1))).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getWorkSubEstimateGroupOverhead_successTest successful!!");
	}

	/**
	 * Gets the work sub estimate group overhead invalid work estimate id test.
	 *
	 * @return the work sub estimate group overhead invalid work estimate id test
	 * @throws Exception the exception
	 */
	@Test
	public void getWorkSubEstimateGroupOverhead_invalidWorkEstimateIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, -1L, workSubEstimateGroupDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getWorkSubEstimateGroupOverhead_invalidWorkEstimateIdTest successful!!");
	}

	/**
	 * Gets the work sub estimate group overhead invalid group id test.
	 *
	 * @return the work sub estimate group overhead invalid group id test
	 * @throws Exception the exception
	 */
	@Test
	public void getWorkSubEstimateGroupOverhead_invalidGroupIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), -1L, -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getWorkSubEstimateGroupOverhead_invalidGroupIdTest successful!!");
	}

	/**
	 * Gets the work sub estimate group overhead invalid overhead id test.
	 *
	 * @return the work sub estimate group overhead invalid overhead id test
	 * @throws Exception the exception
	 */
	@Test
	public void getWorkSubEstimateGroupOverhead_invalidOverheadIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getWorkSubEstimateGroupOverhead_invalidOverheadIdTest successful!!");
	}

	/**
	 * Delete work sub estimate group overhead success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroupOverhead_successTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		String content = mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
				.getResponse().getContentAsString();
		AggregateOverheadsDTO aggregateOverheadsDTO = objectMapper.readValue(content, AggregateOverheadsDTO.class);

		List<Long> selectedOverheads = new ArrayList<>();
		aggregateOverheadsDTO.getOverheads().stream().forEach(overhead -> {
			selectedOverheads.add(overhead.getId());
		});
		uri += "/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(),
				selectedOverheads.get(selectedOverheads.size() - 1))).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("deleteWorkSubEstimateGroupOverhead_successTest successful!!");
	}

	/**
	 * Delete work sub estimate group overhead invalid work estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroupOverhead_invalidWorkEstimateIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, -1L, workSubEstimateGroupDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("deleteWorkSubEstimateGroupOverhead_invalidWorkEstimateIdTest successful!!");
	}

	/**
	 * Delete work sub estimate group overhead incorrect status test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroupOverhead_incorrectStatusTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, workEstimateTenderedDTO.getId(), -1L, -1L))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("deleteWorkSubEstimateGroupOverhead_incorrectStatusTest successful!!");
	}

	/**
	 * Delete work sub estimate group overhead invalid group id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroupOverhead_invalidGroupIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, workEstimateDTO.getId(), -1L, -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("deleteWorkSubEstimateGroupOverhead_invalidGroupIdTest successful!!");
	}

	/**
	 * Delete work sub estimate group overhead invalid overhead id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroupOverhead_invalidOverheadIdTest() throws Exception {
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(), -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("deleteWorkSubEstimateGroupOverhead_invalidOverheadIdTest successful!!");
	}

	/**
	 * Delete work sub estimate group overhead overhead linked test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkSubEstimateGroupOverhead_overheadLinkedTest() throws Exception {
		WorkSubEstimateGroupOverheadDTO overheadDTO = getOverheadDTO();
		String uri = "/v1/api/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		String content = mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
				.getResponse().getContentAsString();
		AggregateOverheadsDTO aggregateOverheadsDTO = objectMapper.readValue(content, AggregateOverheadsDTO.class);

		List<Long> selectedOverheads = new ArrayList<>();
		aggregateOverheadsDTO.getOverheads().stream().forEach(overhead -> {
			selectedOverheads.add(overhead.getId());
		});
		overheadDTO = getOverheadDTO();
		overheadDTO.setValueFixedYn(false);
		overheadDTO.setSelectedOverheads(selectedOverheads);
		req = MockMvcRequestBuilders.post(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(overheadDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated());
		uri += "/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, workEstimateDTO.getId(), workSubEstimateGroupDTO.getId(),
				selectedOverheads.get(selectedOverheads.size() - 1)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("deleteWorkSubEstimateGroupOverhead_overheadLinkedTest successful!!");
	}
}
