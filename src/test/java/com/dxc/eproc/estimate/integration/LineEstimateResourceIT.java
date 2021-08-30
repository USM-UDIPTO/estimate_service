package com.dxc.eproc.estimate.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.IntegrationTest;
import com.dxc.eproc.estimate.controller.LineEstimateController;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.LineEstimate;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.repository.LineEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.dxc.eproc.estimate.service.dto.LineEstimateDTO;
import com.dxc.eproc.estimate.service.mapper.LineEstimateMapper;

// TODO: Auto-generated Javadoc
/**
 * Integration tests for the {@link LineEstimateController} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class LineEstimateResourceIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(LineEstimateResourceIT.class);

	/** The Constant DEFAULT_WORK_ESTIMATE_ID. */
	private static final Long DEFAULT_WORK_ESTIMATE_ID = 1L;

	/** The Constant UPDATED_WORK_ESTIMATE_ID. */
	private static final Long UPDATED_WORK_ESTIMATE_ID = 2L;

	/** The Constant DEFAULT_NAME. */
	private static final String DEFAULT_NAME = "AAAAAAAAAA";

	/** The Constant UPDATED_NAME. */
	private static final String UPDATED_NAME = "BBBBBBBBBB";

	/** The Constant DEFAULT_APPROX_RATE. */
	private static final BigDecimal DEFAULT_APPROX_RATE = new BigDecimal(1);

	/** The Constant UPDATED_APPROX_RATE. */
	private static final BigDecimal UPDATED_APPROX_RATE = new BigDecimal(2);

	/** The Constant ENTITY_API_URL. */
	// private static final String ENTITY_API_URL = "/api/line-estimates";

	private static final String ENTITY_API_URL = "/v1/api/work-estimate/{workEstimateId}/line-estimate";

	/** The Constant ENTITY_API_URL_ID. */
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

	/** The random. */
	private static Random random = new Random();

	/** The count. */
	private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

	/** The line estimate repository. */
	@Autowired
	private LineEstimateRepository lineEstimateRepository;

	/** The work estimate repository. */
	@Autowired
	private WorkEstimateRepository workEstimateRepository;

	/** The line estimate mapper. */
	@Autowired
	private LineEstimateMapper lineEstimateMapper;

	/** The em. */
	@Autowired
	private EntityManager em;

	/** The rest line estimate mock mvc. */
	@Autowired
	private MockMvc restLineEstimateMockMvc;

	/** The line estimate. */
	private LineEstimate lineEstimate;

	/** The work estimate. */
	private WorkEstimate workEstimate;

	/**
	 * Creates the work estimate entity.
	 *
	 * @param em the em
	 * @return the work estimate
	 */
	public static WorkEstimate createWorkEstimateEntity(EntityManager em) {
		System.err.println("=============createWorkEstimateEntity================");

		WorkEstimate workEstimate = new WorkEstimate().workEstimateNumber("AAAAAAAAAA").status(WorkEstimateStatus.DRAFT)
				.deptId(1L).locationId(1L).fileNumber("AAAAAAAAAA").name("AAAAAAAAAA").description("AAAAAAAAAA")
				.estimateTypeId(1L).workTypeId(1L).workCategoryId(1L).workCategoryAttribute(1L)
				.workCategoryAttributeValue(new BigDecimal(1)).adminSanctionAccordedYn(false)
				.techSanctionAccordedYn(false).lineEstimateTotal(new BigDecimal(1)).estimateTotal(new BigDecimal(1))
				.groupLumpsumTotal(new BigDecimal(1)).groupOverheadTotal(new BigDecimal(1))
				.adminSanctionRefNumber("AAAAAAAAAA")
				.adminSanctionRefDate(ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC))
				.adminSanctionedDate(ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC))
				.techSanctionRefNumber("AAAAAAAAAA")
				.techSanctionedDate(ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC))
				.approvedBy("AAAAAAAAAA").approvedTs(ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC))
				.hkrdbFundedYn(false).schemeId(1L).approvedBudgetYn(false).grantAllocatedAmount(new BigDecimal(1))
				.documentReference("AAAAAAAAAA").provisionalAmount(new BigDecimal(1)).headOfAccount("AAAAAAAAAA");
		return workEstimate;
	}

	/**
	 * Create an entity for this test.
	 * 
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 *
	 * @param em the em
	 * @return the line estimate
	 */
	public static LineEstimate createEntity(EntityManager em) {
		LineEstimate lineEstimate = new LineEstimate().workEstimateId(DEFAULT_WORK_ESTIMATE_ID).name(DEFAULT_NAME)
				.approxRate(DEFAULT_APPROX_RATE);
		return lineEstimate;
	}

	/**
	 * Create an updated entity for this test.
	 * 
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 *
	 * @param em the em
	 * @return the line estimate
	 */
	public static LineEstimate createUpdatedEntity(EntityManager em) {
		LineEstimate lineEstimate = new LineEstimate().workEstimateId(UPDATED_WORK_ESTIMATE_ID).name(UPDATED_NAME)
				.approxRate(UPDATED_APPROX_RATE);
		return lineEstimate;
	}

	/**
	 * Inits the test.
	 */
	@BeforeClass
	public void init() {
		workEstimate = createWorkEstimateEntity(em);
		workEstimate = workEstimateRepository.save(workEstimate);
		lineEstimate = createEntity(em);
	}

	/**
	 * Creates the line estimate.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void createLineEstimate_Test() throws Exception {
		log.info("==========  createLineEstimate_Test  Start !!==========");
		int databaseSizeBeforeCreate = lineEstimateRepository.findAll().size();
		// Create the LineEstimate
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1.setWorkEstimateNumber("ppp");
		workEstimate1 = workEstimateRepository.save(workEstimate1);
		lineEstimate.setWorkEstimateId(workEstimate1.getId());
		LineEstimateDTO lineEstimateDTO = lineEstimateMapper.toDto(lineEstimate);
		restLineEstimateMockMvc.perform(post(ENTITY_API_URL, workEstimate1.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lineEstimateDTO)))
				.andExpect(status().isCreated());

		// Validate the LineEstimate in the database
		List<LineEstimate> lineEstimateList = lineEstimateRepository.findAll();
		assertThat(lineEstimateList).hasSize(databaseSizeBeforeCreate + 1);
		log.info("==========  createLineEstimate_Test  Success !!==========");
	}

	/**
	 * Creates the line estimate work estimate id validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void createLineEstimate_RecordNotFound_Validation_Test() throws Exception {

		log.info("==========  createLineEstimate_RecordNotFound_Validation_Test  Start !!==========");
		Long workEstimateId = Long.MAX_VALUE;
		LineEstimateDTO lineEstimateDTO = lineEstimateMapper.toDto(lineEstimate);

		int databaseSizeBeforeCreate = lineEstimateRepository.findAll().size();

		// An entity with an existing ID cannot be created, so this API call must fail
		restLineEstimateMockMvc.perform(post(ENTITY_API_URL, workEstimateId).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(lineEstimateDTO))).andExpect(status().isNotFound());

		// Validate the LineEstimate in the database
		List<LineEstimate> lineEstimateList = lineEstimateRepository.findAll();
		assertThat(lineEstimateList).hasSize(databaseSizeBeforeCreate);
		log.info("==========  createLineEstimate_RecordNotFound_Validation_Test  Success !!==========");
	}

	/**
	 * Creates the line estimate permission validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void createLineEstimate_Permission_validation_Test() throws Exception {
		log.info("==========  createLineEstimate_Permission_validation_Test  Start !!==========");
		workEstimate.setApprovedBudgetYn(true);
		WorkEstimate workEstimate1 = workEstimateRepository.save(workEstimate);
		// Create the LineEstimate
		LineEstimateDTO lineEstimateDTO = lineEstimateMapper.toDto(lineEstimate);
		restLineEstimateMockMvc
				.perform(post(ENTITY_API_URL, workEstimate1.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(lineEstimateDTO)))
				.andExpect(status().isBadRequest());
		log.info("==========  createLineEstimate_Permission_validation_Test  Success !!==========");
	}

	/**
	 * Creates the line estimate methodagrument validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void createLineEstimate_Methodagrument_validation_Test() throws Exception {
		log.info("==========  createLineEstimate_Methodagrument_validation_Test  Start !!==========");
		// Create the LineEstimate
		lineEstimateRepository.saveAndFlush(lineEstimate);
		LineEstimateDTO lineEstimateDTO = lineEstimateMapper.toDto(lineEstimate);
		lineEstimateDTO.setApproxRate(BigDecimal.valueOf(12.98765));
		restLineEstimateMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(lineEstimateDTO)))
				.andExpect(status().isBadRequest());
		log.info("==========  createLineEstimate_Methodagrument_validation_Test  Success !!==========");
	}

	/**
	 * Partial update line estimate test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void partialUpdateLineEstimate_Test() throws Exception {

		log.info("==========  partialUpdateLineEstimate_Test  Start !!==========");
		// Initialize the database
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1.setWorkEstimateNumber("RR");
		workEstimate1 = workEstimateRepository.save(workEstimate1);

		LineEstimate lineEstimate1 = createEntity(em);

		lineEstimate1.setWorkEstimateId(workEstimate1.getId());
		lineEstimate1.setName("Name1");
		lineEstimateRepository.saveAndFlush(lineEstimate1);

		int databaseSizeBeforeUpdate = lineEstimateRepository.findAll().size();

		// Update the lineEstimate using partial update
		LineEstimate partialUpdatedLineEstimate = new LineEstimate();
		partialUpdatedLineEstimate.setId(lineEstimate1.getId());

		partialUpdatedLineEstimate.workEstimateId(UPDATED_WORK_ESTIMATE_ID).name(UPDATED_NAME)
				.approxRate(UPDATED_APPROX_RATE);

		restLineEstimateMockMvc
				.perform(patch(ENTITY_API_URL_ID, workEstimate1.getId(), lineEstimate1.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedLineEstimate)))
				.andExpect(status().isOk());

		// Validate the LineEstimate in the database
		List<LineEstimate> lineEstimateList = lineEstimateRepository.findAll();
		assertThat(lineEstimateList).hasSize(databaseSizeBeforeUpdate);
		log.info("==========  partialUpdateLineEstimate_Test  Success !!==========");
	}

	/**
	 * Update line estimate record not found validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void partialUpdateLineEstimate_RecordNotFound_Validation_Test() throws Exception {

		log.info("==========  partialUpdateLineEstimate_RecordNotFound_Validation_Test  Start !!==========");

		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1.setWorkEstimateNumber("Reee");
		workEstimate1 = workEstimateRepository.save(workEstimate1);
		LineEstimate lineEstimate1 = createEntity(em);

		lineEstimate1.setWorkEstimateId(workEstimate1.getId());
		lineEstimate1.setName("Name1");
		lineEstimateRepository.saveAndFlush(lineEstimate1);
		// Initialize the database
		lineEstimateRepository.saveAndFlush(lineEstimate1);
		Long workEstimateId = Long.MAX_VALUE;

		// Update the lineEstimate using partial update
		LineEstimate partialUpdatedLineEstimate = new LineEstimate();
		partialUpdatedLineEstimate.setId(lineEstimate1.getId());

		partialUpdatedLineEstimate.workEstimateId(UPDATED_WORK_ESTIMATE_ID).name(UPDATED_NAME)
				.approxRate(UPDATED_APPROX_RATE);

		restLineEstimateMockMvc
				.perform(patch(ENTITY_API_URL_ID, workEstimateId, lineEstimate1.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedLineEstimate)))
				.andExpect(status().isNotFound());

		log.info("==========  partialUpdateLineEstimate_RecordNotFound_Validation_Test  Success !!==========");
	}

	/**
	 * Update line estimate permission validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void partialUpdateLineEstimate_Permission_Validation_Test() throws Exception {

		log.info("==========  partialUpdateLineEstimate_Permission_Validation_Test  Start !!==========");
		// Initialize the database
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("Aaa");
		workEstimate1.setApprovedBudgetYn(true);
		workEstimate1 = workEstimateRepository.save(workEstimate1);
		lineEstimate.setWorkEstimateId(workEstimate1.getId());
		lineEstimateRepository.saveAndFlush(lineEstimate);

		// Update the lineEstimate using partial update
		LineEstimate partialUpdatedLineEstimate = new LineEstimate();
		partialUpdatedLineEstimate.setId(lineEstimate.getId());

		partialUpdatedLineEstimate.workEstimateId(UPDATED_WORK_ESTIMATE_ID).name(UPDATED_NAME)
				.approxRate(UPDATED_APPROX_RATE);

		restLineEstimateMockMvc
				.perform(patch(ENTITY_API_URL_ID, workEstimate1.getId(), lineEstimate.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedLineEstimate)))
				.andExpect(status().isBadRequest());

		log.info("==========  partialUpdateLineEstimate_Permission_Validation_Test  Success !!==========");
	}

	/**
	 * Partial update line estimate line estimate id validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void partialUpdateLineEstimate_lineEstimateId_Validation_Test() throws Exception {

		log.info("==========  partialUpdateLineEstimate_lineEstimateId_Validation_Test  Start !!==========");
		// Initialize the database
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1.setWorkEstimateNumber("Zzzzz");
		workEstimate1 = workEstimateRepository.save(workEstimate1);

		LineEstimate lineEstimate1 = createEntity(em);

		lineEstimate1.setWorkEstimateId(workEstimate1.getId());
		lineEstimate1.setName("Name2");
		lineEstimateRepository.saveAndFlush(lineEstimate1);

		LineEstimateDTO lineEstimateDTO = lineEstimateMapper.toDto(lineEstimate1);
		lineEstimateDTO.setId(null);
		restLineEstimateMockMvc
				.perform(patch(ENTITY_API_URL_ID, workEstimate1.getId(), lineEstimate1.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(lineEstimateDTO)))
				.andExpect(status().isBadRequest());

		log.info("==========  partialUpdateLineEstimate_lineEstimateId_Validation_Test  Success !!==========");
	}

	/**
	 * Update line estimate test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void updateLineEstimate_Test() throws Exception {

		log.info("==========  updateLineEstimate_Test  Start !!==========");
		// Initialize the database
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1.setWorkEstimateNumber("QWERTY");
		workEstimate1 = workEstimateRepository.save(workEstimate1);

		LineEstimate lineEstimate1 = createEntity(em);

		lineEstimate1.setWorkEstimateId(workEstimate1.getId());
		lineEstimate1.setName("Name3");
		lineEstimateRepository.saveAndFlush(lineEstimate1);

		int databaseSizeBeforeUpdate = lineEstimateRepository.findAll().size();

		// Update the lineEstimate using partial update
		LineEstimate partialUpdatedLineEstimate = new LineEstimate();
		partialUpdatedLineEstimate.setId(lineEstimate1.getId());

		partialUpdatedLineEstimate.workEstimateId(UPDATED_WORK_ESTIMATE_ID).name(UPDATED_NAME)
				.approxRate(UPDATED_APPROX_RATE);

		restLineEstimateMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimate1.getId(), lineEstimate1.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedLineEstimate)))
				.andExpect(status().isOk());

		// Validate the LineEstimate in the database
		List<LineEstimate> lineEstimateList = lineEstimateRepository.findAll();
		assertThat(lineEstimateList).hasSize(databaseSizeBeforeUpdate);
		log.info("==========  updateLineEstimate_Test  Success !!==========");
	}

	/**
	 * Update line estimate record not found validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void updateLineEstimate_RecordNotFound_Validation_Test() throws Exception {

		log.info("==========  updateLineEstimate_RecordNotFound_Validation_Test  Start !!==========");
		Long workEstimateId = Long.MAX_VALUE;

		// Update the lineEstimate using partial update
		LineEstimate partialUpdatedLineEstimate = new LineEstimate();
		partialUpdatedLineEstimate.setId(lineEstimate.getId());

		partialUpdatedLineEstimate.workEstimateId(UPDATED_WORK_ESTIMATE_ID).name(UPDATED_NAME)
				.approxRate(UPDATED_APPROX_RATE);

		restLineEstimateMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimateId, lineEstimate.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedLineEstimate)))
				.andExpect(status().isNotFound());

		log.info("==========  updateLineEstimate_RecordNotFound_Validation_Test  Success !!==========");
	}

	/**
	 * Update line estimate permission validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void updateLineEstimate_Permission_Validation_Test() throws Exception {

		log.info("==========  updateLineEstimate_Permission_Validation_Test  Start !!==========");
		// Initialize the database
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("Baaw");
		workEstimate1.setApprovedBudgetYn(true);
		workEstimate1 = workEstimateRepository.save(workEstimate1);
		LineEstimate lineEstimate1 = createEntity(em);
		lineEstimate1.setName("Name-1");
		lineEstimate1.setWorkEstimateId(workEstimate1.getId());
		lineEstimateRepository.saveAndFlush(lineEstimate1);

		// Update the lineEstimate using partial update
		LineEstimate partialUpdatedLineEstimate = new LineEstimate();
		partialUpdatedLineEstimate.setId(lineEstimate1.getId());

		partialUpdatedLineEstimate.workEstimateId(UPDATED_WORK_ESTIMATE_ID).name(UPDATED_NAME)
				.approxRate(UPDATED_APPROX_RATE);

		restLineEstimateMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimate1.getId(), lineEstimate1.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedLineEstimate)))
				.andExpect(status().isBadRequest());

		log.info("==========  updateLineEstimate_Permission_Validation_Test  Success !!==========");
	}

	/**
	 * Update line estimate norecord found validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void updateLineEstimate_NorecordFound_Validation_Test() throws Exception {

		log.info("==========  updateLineEstimate_NorecordFound_Validation_Test  Start !!==========");
		// Initialize the database
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("Baa");
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1 = workEstimateRepository.save(workEstimate1);
		LineEstimate lineEstimate1 = createEntity(em);
		lineEstimate1.setName("Name-1");
		lineEstimate1.setWorkEstimateId(workEstimate1.getId());
		lineEstimateRepository.saveAndFlush(lineEstimate1);

		// Update the lineEstimate using partial update
		LineEstimate partialUpdatedLineEstimate = new LineEstimate();
		partialUpdatedLineEstimate.setId(lineEstimate1.getId());

		partialUpdatedLineEstimate.workEstimateId(UPDATED_WORK_ESTIMATE_ID).name(UPDATED_NAME)
				.approxRate(UPDATED_APPROX_RATE);

		restLineEstimateMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimate1.getId(), lineEstimate.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedLineEstimate)))
				.andExpect(status().isNotFound());

		log.info("==========  updateLineEstimate_NorecordFound_Validation_Test  Success !!==========");
	}

	/**
	 * Update line estimate methodagrument validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void updateLineEstimate_Methodagrument_validation_Test() throws Exception {
		log.info("==========  updateLineEstimate_Methodagrument_validation_Test  Start !!==========");
		// Create the LineEstimate
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("Qwerty1");
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1 = workEstimateRepository.save(workEstimate1);

		LineEstimate lineEstimate1 = createEntity(em);
		lineEstimate1.setName("Name-102");
		lineEstimate1.setWorkEstimateId(workEstimate1.getId());
		lineEstimateRepository.saveAndFlush(lineEstimate1);
		LineEstimateDTO lineEstimateDTO = lineEstimateMapper.toDto(lineEstimate1);
		lineEstimateDTO.setApproxRate(BigDecimal.valueOf(12.98765));

		restLineEstimateMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimate1.getId(), lineEstimate1.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(lineEstimateDTO)))
				.andExpect(status().isBadRequest());
		log.info("==========  updateLineEstimate_Methodagrument_validation_Test  Success !!==========");
	}

	/**
	 * Gets the all line estimates.
	 *
	 * @return the all line estimates
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void getAllLineEstimates_Test() throws Exception {
		log.info("==========  getAllLineEstimates_Test  Start !!==========");
		// Initialize the database
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("Xyz");
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1 = workEstimateRepository.save(workEstimate1);
		LineEstimate lineEstimate1 = createEntity(em);
		lineEstimate1.setName("Name-5");
		lineEstimate1.setWorkEstimateId(workEstimate1.getId());
		lineEstimateRepository.saveAndFlush(lineEstimate1);

		// Get all the lineEstimateList
		restLineEstimateMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/line-estimates", workEstimate1.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		log.info("==========  getAllLineEstimates_Test  Success !!==========");
	}

	/**
	 * Gets the all line estimates work estimate id validation test.
	 *
	 * @return the all line estimates work estimate id validation test
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void getAllLineEstimates_WorkEstimateId_Validation_Test() throws Exception {
		log.info("==========  getAllLineEstimates_WorkEstimateId_Validation_Test  Start !!==========");
		// Get all the lineEstimateList
		restLineEstimateMockMvc.perform(get("/v1/api/work-estimate/{workEstimateId}/line-estimates", Long.MAX_VALUE)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
		log.info("==========  getAllLineEstimates_WorkEstimateId_Validation_Test  Success !!==========");
	}

	/**
	 * Gets the all line estimates no record found validation test.
	 *
	 * @return the all line estimates no record found validation test
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void getAllLineEstimates_NoRecordFound_Validation_Test() throws Exception {
		log.info("==========  getAllLineEstimates_NoRecordFound_Validation_Test  Start !!==========");
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("Xyz1");
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1 = workEstimateRepository.save(workEstimate1);
		// Get all the lineEstimateList
		restLineEstimateMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/line-estimates", workEstimate1.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		log.info("==========  getAllLineEstimates_NoRecordFound_Validation_Test  Success !!==========");
	}

	/**
	 * Gets the line estimates test.
	 *
	 * @return the line estimates test
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void getLineEstimates_Test() throws Exception {
		log.info("==========  getLineEstimates_Test  Start !!==========");
		// Initialize the database
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("Xyz3");
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1 = workEstimateRepository.save(workEstimate1);
		LineEstimate lineEstimate1 = createEntity(em);
		lineEstimate1.setName("Name-6");
		lineEstimate1.setWorkEstimateId(workEstimate1.getId());
		lineEstimateRepository.saveAndFlush(lineEstimate1);

		// Get all the lineEstimateList
		restLineEstimateMockMvc.perform(get("/v1/api/work-estimate/{workEstimateId}/line-estimate/{id}",
				workEstimate1.getId(), lineEstimate1.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		log.info("==========  getLineEstimates_Test  Success !!==========");
	}

	/**
	 * Gets the all line estimates work estimate id validation test.
	 *
	 * @return the all line estimates work estimate id validation test
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void getLineEstimates_WorkEstimateId_Validation_Test() throws Exception {
		log.info("==========  getLineEstimates_WorkEstimateId_Validation_Test  Start !!==========");
		// Get all the lineEstimateList
		restLineEstimateMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/line-estimate/{id}", Long.MAX_VALUE, 1l)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		log.info("==========  getLineEstimates_WorkEstimateId_Validation_Test  Success !!==========");
	}

	/**
	 * Gets the all line estimates no record found validation test.
	 *
	 * @return the all line estimates no record found validation test
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void getLineEstimates_NoRecordFound_Validation_Test() throws Exception {
		log.info("==========  getLineEstimates_NoRecordFound_Validation_Test  Start !!==========");
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("Xyz2");
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1 = workEstimateRepository.save(workEstimate1);
		// Get all the lineEstimateList
		restLineEstimateMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/line-estimate/{id}", workEstimate1.getId(), 1L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		log.info("==========  getLineEstimates_NoRecordFound_Validation_Test  Success !!==========");
	}

	/**
	 * Delete line estimate.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void deleteLineEstimate() throws Exception {
		log.info("==========  deleteLineEstimate  Start !!==========");
		// Initialize the database
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("Xyz5");
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1 = workEstimateRepository.save(workEstimate1);
		LineEstimate lineEstimate1 = createEntity(em);
		lineEstimate1.setName("Name-8");
		lineEstimate1.setWorkEstimateId(workEstimate1.getId());
		lineEstimateRepository.saveAndFlush(lineEstimate1);

		int databaseSizeBeforeDelete = lineEstimateRepository.findAll().size();

		// Delete the lineEstimate
		restLineEstimateMockMvc.perform(delete("/v1/api/work-estimate/{workEstimateId}/line-estimate/{id}",
				workEstimate1.getId(), lineEstimate1.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// Validate the database contains one less item
		List<LineEstimate> lineEstimateList = lineEstimateRepository.findAll();
		assertThat(lineEstimateList).hasSize(databaseSizeBeforeDelete - 1);
		log.info("==========  deleteLineEstimate  Success !!==========");
	}

	/**
	 * Delete line estimate work estimate id validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void deleteLineEstimate_WorkEstimateId_Validation_Test() throws Exception {
		log.info("==========  deleteLineEstimate_WorkEstimateId_Validation_Test  Start !!==========");
		// Delete the lineEstimate
		restLineEstimateMockMvc
				.perform(delete("/v1/api/work-estimate/{workEstimateId}/line-estimate/{id}", Long.MAX_VALUE, 1L)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		log.info("==========  deleteLineEstimate_WorkEstimateId_Validation_Test  Success !!==========");

	}

	/**
	 * Delete line estimate line estimate id validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void deleteLineEstimate_LineEstimateId_Validation_Test() throws Exception {
		log.info("==========  deleteLineEstimate_LineEstimateId_Validation_Test  Start !!==========");
		// Initialize the database
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("Xyz9");
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1 = workEstimateRepository.save(workEstimate1);

		// Delete the lineEstimate
		restLineEstimateMockMvc
				.perform(delete("/v1/api/work-estimate/{workEstimateId}/line-estimate/{id}", workEstimate1.getId(), 1L)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		log.info("==========  deleteLineEstimate_LineEstimateId_Validation_Test  Success !!==========");
	}

	/**
	 * Validaet line estimates test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void validaetLineEstimates_Test() throws Exception {
		log.info("==========  validaetLineEstimates_Test  Start !!==========");
		// Initialize the database
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("Xyz10");
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1 = workEstimateRepository.save(workEstimate1);
		LineEstimate lineEstimate1 = createEntity(em);
		lineEstimate1.setName("Name-16");
		lineEstimate1.setWorkEstimateId(workEstimate1.getId());
		lineEstimateRepository.saveAndFlush(lineEstimate1);

		// Get all the lineEstimateList
		restLineEstimateMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/validate-line-estimates", workEstimate1.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		log.info("==========  validaetLineEstimates_Test  Success !!==========");
	}

	/**
	 * Validaet line estimates work estimate id validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void validaetLineEstimates_workEstimateId_Validation_Test() throws Exception {
		log.info("==========  validaetLineEstimates_workEstimateId_Validation_Test  Start !!==========");
		// Get all the lineEstimateList
		restLineEstimateMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/validate-line-estimates", Long.MAX_VALUE)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		log.info("==========  validaetLineEstimates_workEstimateId_Validation_Test  Success !!==========");
	}

	/**
	 * Validaet line estimates line estimate data validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	void validaetLineEstimates_LineEstimateData_Validation_Test() throws Exception {
		log.info("==========  validaetLineEstimates_LineEstimateData_Validation_Test  Start !!==========");
		// Initialize the database
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("Xyz11");
		workEstimate1.setApprovedBudgetYn(false);
		workEstimate1 = workEstimateRepository.save(workEstimate1);
		// Get all the lineEstimateList
		restLineEstimateMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/validate-line-estimates", workEstimate1.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		log.info("==========  validaetLineEstimates_LineEstimateData_Validation_Test  Success !!==========");
	}
}
