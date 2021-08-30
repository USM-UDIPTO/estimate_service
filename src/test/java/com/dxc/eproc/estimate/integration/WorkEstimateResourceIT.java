package com.dxc.eproc.estimate.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.repository.SubEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.dxc.eproc.estimate.service.WorkCategoryService;
import com.dxc.eproc.estimate.service.dto.WorkCategoryDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateSearchDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateMapper;
import com.dxc.eproc.utils.Utility;

// TODO: Auto-generated Javadoc
/**
 * Integration tests for the {@link WorkEstimateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
public class WorkEstimateResourceIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WorkEstimateResourceIT.class);

	/** The Constant DEFAULT_WORK_ESTIMATE_NUMBER. */
	private static final String DEFAULT_WORK_ESTIMATE_NUMBER = "AAAAA1234/2021-22/AAAAA334/WORK_INDENT1";

	/** The Constant UPDATED_WORK_ESTIMATE_NUMBER. */
	private static final String UPDATED_WORK_ESTIMATE_NUMBER = "BBBBB1234/2021-22/BBBB1234/WORK_INDENT2";

	/** The Constant DEFAULT_STATUS. */
	private static final WorkEstimateStatus DEFAULT_STATUS = WorkEstimateStatus.DRAFT;

	/** The Constant UPDATED_STATUS. */
	private static final WorkEstimateStatus UPDATED_STATUS = WorkEstimateStatus.INITIAL;

	/** The Constant DEFAULT_WORK_CATEGORY_CODE. */
	private static final String DEFAULT_WORK_CATEGORY_CODE = "AAAAA334";

	/** The Constant DEFAULT_DEPT_CODE. */
	private static final String DEFAULT_DEPT_CODE = "AAAAA1234";

	/** The Constant DEFAULT_DEPT_ID. */
	private static final Long DEFAULT_DEPT_ID = 1L;

	/** The Constant UPDATED_DEPT_ID. */
	private static final Long UPDATED_DEPT_ID = 2L;

	/** The Constant DEFAULT_LOCATION_ID. */
	private static final Long DEFAULT_LOCATION_ID = 1L;

	/** The Constant UPDATED_LOCATION_ID. */
	private static final Long UPDATED_LOCATION_ID = 2L;

	/** The Constant DEFAULT_FILE_NUMBER. */
	private static final String DEFAULT_FILE_NUMBER = "AAAAAAAAAA";

	/** The Constant UPDATED_FILE_NUMBER. */
	private static final String UPDATED_FILE_NUMBER = "BBBBBBBBBB";

	/** The Constant DEFAULT_NAME. */
	private static final String DEFAULT_NAME = "AAAAAAAAAA";

	/** The Constant UPDATED_NAME. */
	private static final String UPDATED_NAME = "BBBBBBBBBB";

	/** The Constant DEFAULT_DESCRIPTION. */
	private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";

	/** The Constant UPDATED_DESCRIPTION. */
	private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

	/** The Constant DEFAULT_ESTIMATE_TYPE_ID. */
	private static final Long DEFAULT_ESTIMATE_TYPE_ID = 1L;

	/** The Constant UPDATED_ESTIMATE_TYPE_ID. */
	private static final Long UPDATED_ESTIMATE_TYPE_ID = 2L;

	/** The Constant DEFAULT_WOTK_TYPE_ID. */
	private static final Long DEFAULT_WOTK_TYPE_ID = 1L;

	/** The Constant UPDATED_WOTK_TYPE_ID. */
	private static final Long UPDATED_WOTK_TYPE_ID = 2L;

	/** The Constant DEFAULT_WORK_CATEGORY_ID. */
	private static final Long DEFAULT_WORK_CATEGORY_ID = 1L;

	/** The Constant UPDATED_WORK_CATEGORY_ID. */
	private static final Long UPDATED_WORK_CATEGORY_ID = 2L;

	/** The Constant DEFAULT_WORK_CATEGORY_ATTRIBUTE. */
	private static final Long DEFAULT_WORK_CATEGORY_ATTRIBUTE = 1L;

	/** The Constant UPDATED_WORK_CATEGORY_ATTRIBUTE. */
	private static final Long UPDATED_WORK_CATEGORY_ATTRIBUTE = 2L;

	/** The Constant DEFAULT_WORK_CATEGORY_ATTRIBUTE_VALUE. */
	private static final BigDecimal DEFAULT_WORK_CATEGORY_ATTRIBUTE_VALUE = new BigDecimal("10.0000");

	/** The Constant UPDATED_WORK_CATEGORY_ATTRIBUTE_VALUE. */
	private static final BigDecimal UPDATED_WORK_CATEGORY_ATTRIBUTE_VALUE = new BigDecimal("20.0000");

	/** The Constant DEFAULT_ADMIN_SANCTION_ACCORDED_YN. */
	private static final Boolean DEFAULT_ADMIN_SANCTION_ACCORDED_YN = false;

	/** The Constant UPDATED_ADMIN_SANCTION_ACCORDED_YN. */
	private static final Boolean UPDATED_ADMIN_SANCTION_ACCORDED_YN = true;

	/** The Constant DEFAULT_TECH_SANCTION_ACCORDED_YN. */
	private static final Boolean DEFAULT_TECH_SANCTION_ACCORDED_YN = false;

	/** The Constant UPDATED_TECH_SANCTION_ACCORDED_YN. */
	private static final Boolean UPDATED_TECH_SANCTION_ACCORDED_YN = true;

	/** The Constant DEFAULT_LINE_ESTIMATE_TOTAL. */
	private static final BigDecimal DEFAULT_LINE_ESTIMATE_TOTAL = new BigDecimal("10.0000");

	/** The Constant UPDATED_LINE_ESTIMATE_TOTAL. */
	private static final BigDecimal UPDATED_LINE_ESTIMATE_TOTAL = new BigDecimal("20.0000");

	/** The Constant DEFAULT_ESTIMATE_TOTAL. */
	private static final BigDecimal DEFAULT_ESTIMATE_TOTAL = new BigDecimal("10.0000");

	/** The Constant UPDATED_ESTIMATE_TOTAL. */
	private static final BigDecimal UPDATED_ESTIMATE_TOTAL = new BigDecimal("20.0000");

	/** The Constant DEFAULT_LUMPSUM_TOTAL. */
	private static final BigDecimal DEFAULT_LUMPSUM_TOTAL = new BigDecimal("10.0000");

	/** The Constant UPDATED_LUMPSUM_TOTAL. */
	private static final BigDecimal UPDATED_LUMPSUM_TOTAL = new BigDecimal("20.0000");

	/** The Constant DEFAULT_GROUP_OVERHEAD_TOTAL. */
	private static final BigDecimal DEFAULT_GROUP_OVERHEAD_TOTAL = new BigDecimal("21.0000");

	/** The Constant UPDATED_GROUP_OVERHEAD_TOTAL. */
	private static final BigDecimal UPDATED_GROUP_OVERHEAD_TOTAL = new BigDecimal("20.0000");

	/** The Constant DEFAULT_ADMIN_SANCTION_REF_NUMBER. */
	private static final String DEFAULT_ADMIN_SANCTION_REF_NUMBER = "AAAAAAAAAA";

	/** The Constant UPDATED_ADMIN_SANCTION_REF_NUMBER. */
	private static final String UPDATED_ADMIN_SANCTION_REF_NUMBER = "BBBBBBBBBB";

	/** The Constant DEFAULT_ADMIN_SANCTION_REF_DATE. */
	private static final ZonedDateTime DEFAULT_ADMIN_SANCTION_REF_DATE = ZonedDateTime
			.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);

	/** The Constant UPDATED_ADMIN_SANCTION_REF_DATE. */
	private static final ZonedDateTime UPDATED_ADMIN_SANCTION_REF_DATE = ZonedDateTime.now(ZoneId.systemDefault())
			.withNano(0);

	/** The Constant DEFAULT_ADMIN_SANCTIONED_DATE. */
	private static final ZonedDateTime DEFAULT_ADMIN_SANCTIONED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L),
			ZoneOffset.UTC);

	/** The Constant UPDATED_ADMIN_SANCTIONED_DATE. */
	private static final ZonedDateTime UPDATED_ADMIN_SANCTIONED_DATE = ZonedDateTime.now(ZoneId.systemDefault())
			.withNano(0);

	/** The Constant DEFAULT_TECH_SANCTION_REF_NUMBER. */
	private static final String DEFAULT_TECH_SANCTION_REF_NUMBER = "AAAAAAAAAA";

	/** The Constant UPDATED_TECH_SANCTION_REF_NUMBER. */
	private static final String UPDATED_TECH_SANCTION_REF_NUMBER = "BBBBBBBBBB";

	/** The Constant DEFAULT_TECH_SANCTIONED_DATE. */
	private static final ZonedDateTime DEFAULT_TECH_SANCTIONED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L),
			ZoneOffset.UTC);

	/** The Constant UPDATED_TECH_SANCTIONED_DATE. */
	private static final ZonedDateTime UPDATED_TECH_SANCTIONED_DATE = ZonedDateTime.now(ZoneId.systemDefault())
			.withNano(0);

	/** The Constant DEFAULT_APPROVED_BY. */
	private static final String DEFAULT_APPROVED_BY = "AAAAAAAAAA";

	/** The Constant UPDATED_APPROVED_BY. */
	private static final String UPDATED_APPROVED_BY = "BBBBBBBBBB";

	/** The Constant DEFAULT_APPROVED_TS. */
	private static final ZonedDateTime DEFAULT_APPROVED_TS = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L),
			ZoneOffset.UTC);

	/** The Constant UPDATED_APPROVED_TS. */
	private static final ZonedDateTime UPDATED_APPROVED_TS = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

	/** The Constant DEFAULT_HKRDB_FUNDED_YN. */
	private static final Boolean DEFAULT_HKRDB_FUNDED_YN = false;

	/** The Constant UPDATED_HKRDB_FUNDED_YN. */
	private static final Boolean UPDATED_HKRDB_FUNDED_YN = true;

	/** The Constant DEFAULT_SCHEME_ID. */
	private static final Long DEFAULT_SCHEME_ID = 1L;

	/** The Constant UPDATED_SCHEME_ID. */
	private static final Long UPDATED_SCHEME_ID = 2L;

	/** The Constant DEFAULT_APPROVED_BUDGET_YN. */
	private static final Boolean DEFAULT_APPROVED_BUDGET_YN = false;

	/** The Constant UPDATED_APPROVED_BUDGET_YN. */
	private static final Boolean UPDATED_APPROVED_BUDGET_YN = true;

	/** The Constant DEFAULT_GRANT_ALLOCATED_AMOUNT. */
	private static final BigDecimal DEFAULT_GRANT_ALLOCATED_AMOUNT = new BigDecimal("10.0000");

	/** The Constant UPDATED_GRANT_ALLOCATED_AMOUNT. */
	private static final BigDecimal UPDATED_GRANT_ALLOCATED_AMOUNT = new BigDecimal("20.0000");

	/** The Constant DEFAULT_DOCUMENT_REFERENCE. */
	private static final String DEFAULT_DOCUMENT_REFERENCE = "AAAAAAAAAA";

	/** The Constant UPDATED_DOCUMENT_REFERENCE. */
	private static final String UPDATED_DOCUMENT_REFERENCE = "BBBBBBBBBB";

	/** The Constant DEFAULT_PROVISIONAL_AMOUNT. */
	private static final BigDecimal DEFAULT_PROVISIONAL_AMOUNT = new BigDecimal("60.0000");

	/** The Constant UPDATED_PROVISIONAL_AMOUNT. */
	private static final BigDecimal UPDATED_PROVISIONAL_AMOUNT = new BigDecimal("70.0000");

	/** The Constant DEFAULT_HEAD_OF_ACCOUNT. */
	private static final String DEFAULT_HEAD_OF_ACCOUNT = "AAAAAAAAAA";

	/** The Constant UPDATED_HEAD_OF_ACCOUNT. */
	private static final String UPDATED_HEAD_OF_ACCOUNT = "BBBBBBBBBB";

	/** The Constant ENTITY_API_URL. */
	private static final String ENTITY_API_URL = "/v1/api/work-estimate";

	/** The Constant ENTITY_API_URL_ID. */
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

	/** The Constant ENTITY_API_URL_ID_SUBESTIMATE. */
	private static final String ENTITY_API_URL_ID_SUBESTIMATE = ENTITY_API_URL_ID + "/validate-subestimates";

	/** The random. */
	private static Random random = new Random();

	/** The count. */
	private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

	/** The work estimate repository. */
	@Autowired
	private WorkEstimateRepository workEstimateRepository;

	/** The sub estimate repository. */
	@Autowired
	private SubEstimateRepository subEstimateRepository;

	/** The work estimate mapper. */
	@Autowired
	private WorkEstimateMapper workEstimateMapper;

	/** The em. */
	@Autowired
	private EntityManager em;

	/** The rest work estimate mock mvc. */
	@Autowired
	private MockMvc restWorkEstimateMockMvc;

	/** The work estimate. */
	private WorkEstimate workEstimate;

	/** The sub estimate. */
	private SubEstimate subEstimate;

	/** The work category service. */
	@Autowired
	private WorkCategoryService workCategoryService;

	/**
	 * Create an entity for this test. This is a static method, as tests for other
	 * entities might also need it, if they test an entity which requires the
	 * current entity.
	 *
	 * @param em the em
	 * @return the work estimate
	 */
	public WorkEstimate createEntity(EntityManager em) {
		WorkEstimate workEstimate = new WorkEstimate().workEstimateNumber(DEFAULT_WORK_ESTIMATE_NUMBER)
				.status(DEFAULT_STATUS).deptId(DEFAULT_DEPT_ID).locationId(DEFAULT_LOCATION_ID)
				.fileNumber(DEFAULT_FILE_NUMBER).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION)
				.estimateTypeId(DEFAULT_ESTIMATE_TYPE_ID).workTypeId(DEFAULT_WOTK_TYPE_ID)
				.workCategoryId(DEFAULT_WORK_CATEGORY_ID).workCategoryAttribute(DEFAULT_WORK_CATEGORY_ATTRIBUTE)
				.workCategoryAttributeValue(DEFAULT_WORK_CATEGORY_ATTRIBUTE_VALUE)
				.adminSanctionAccordedYn(DEFAULT_ADMIN_SANCTION_ACCORDED_YN)
				.techSanctionAccordedYn(DEFAULT_TECH_SANCTION_ACCORDED_YN)
				.lineEstimateTotal(DEFAULT_LINE_ESTIMATE_TOTAL).estimateTotal(DEFAULT_ESTIMATE_TOTAL)
				.groupLumpsumTotal(DEFAULT_LUMPSUM_TOTAL).groupOverheadTotal(DEFAULT_GROUP_OVERHEAD_TOTAL)
				.adminSanctionRefNumber(DEFAULT_ADMIN_SANCTION_REF_NUMBER)
				.adminSanctionRefDate(DEFAULT_ADMIN_SANCTION_REF_DATE)
				.adminSanctionedDate(DEFAULT_ADMIN_SANCTIONED_DATE)
				.techSanctionRefNumber(DEFAULT_TECH_SANCTION_REF_NUMBER)
				.techSanctionedDate(DEFAULT_TECH_SANCTIONED_DATE).approvedBy(DEFAULT_APPROVED_BY)
				.approvedTs(DEFAULT_APPROVED_TS).hkrdbFundedYn(DEFAULT_HKRDB_FUNDED_YN).schemeId(DEFAULT_SCHEME_ID)
				.approvedBudgetYn(DEFAULT_APPROVED_BUDGET_YN).grantAllocatedAmount(DEFAULT_GRANT_ALLOCATED_AMOUNT)
				.documentReference(DEFAULT_DOCUMENT_REFERENCE).provisionalAmount(DEFAULT_PROVISIONAL_AMOUNT)
				.headOfAccount(DEFAULT_HEAD_OF_ACCOUNT);
		return workEstimate;
	}

	/**
	 * Create an updated entity for this test. This is a static method, as tests for
	 * other entities might also need it, if they test an entity which requires the
	 * current entity.
	 *
	 * @return the work estimate
	 */
	public WorkEstimate createUpdatedEntity() {
		WorkEstimate workEstimate = new WorkEstimate().workEstimateNumber(UPDATED_WORK_ESTIMATE_NUMBER)
				.status(UPDATED_STATUS).deptId(UPDATED_DEPT_ID).locationId(UPDATED_LOCATION_ID)
				.fileNumber(UPDATED_FILE_NUMBER).name(UPDATED_NAME).description(UPDATED_DESCRIPTION)
				.estimateTypeId(UPDATED_ESTIMATE_TYPE_ID).workTypeId(UPDATED_WOTK_TYPE_ID)
				.workCategoryId(UPDATED_WORK_CATEGORY_ID).workCategoryAttribute(UPDATED_WORK_CATEGORY_ATTRIBUTE)
				.workCategoryAttributeValue(UPDATED_WORK_CATEGORY_ATTRIBUTE_VALUE)
				.adminSanctionAccordedYn(UPDATED_ADMIN_SANCTION_ACCORDED_YN)
				.techSanctionAccordedYn(UPDATED_TECH_SANCTION_ACCORDED_YN)
				.lineEstimateTotal(UPDATED_LINE_ESTIMATE_TOTAL).estimateTotal(UPDATED_ESTIMATE_TOTAL)
				.groupLumpsumTotal(UPDATED_LUMPSUM_TOTAL).groupOverheadTotal(UPDATED_GROUP_OVERHEAD_TOTAL)
				.adminSanctionRefNumber(UPDATED_ADMIN_SANCTION_REF_NUMBER)
				.adminSanctionRefDate(UPDATED_ADMIN_SANCTION_REF_DATE)
				.adminSanctionedDate(UPDATED_ADMIN_SANCTIONED_DATE)
				.techSanctionRefNumber(UPDATED_TECH_SANCTION_REF_NUMBER)
				.techSanctionedDate(UPDATED_TECH_SANCTIONED_DATE).approvedBy(UPDATED_APPROVED_BY)
				.approvedTs(UPDATED_APPROVED_TS).hkrdbFundedYn(UPDATED_HKRDB_FUNDED_YN).schemeId(UPDATED_SCHEME_ID)
				.approvedBudgetYn(UPDATED_APPROVED_BUDGET_YN).grantAllocatedAmount(UPDATED_GRANT_ALLOCATED_AMOUNT)
				.documentReference(UPDATED_DOCUMENT_REFERENCE).provisionalAmount(UPDATED_PROVISIONAL_AMOUNT)
				.headOfAccount(UPDATED_HEAD_OF_ACCOUNT);
		return workEstimate;
	}

	/**
	 * Creates the work estimate entity.
	 *
	 * @return the work estimate
	 */
	private WorkEstimate createWorkEstimateEntity() {
		WorkEstimate workEstimate = new WorkEstimate().id(1001L).workEstimateNumber("1111")
				.status(WorkEstimateStatus.DRAFT).deptId(DEFAULT_DEPT_ID).locationId(1L).fileNumber(DEFAULT_FILE_NUMBER)
				.name("Road Repair").description("Road Repair work estimate.").estimateTypeId(1L).workTypeId(1L)
				.workCategoryId(1L).workCategoryAttribute(10L).workCategoryAttributeValue(BigDecimal.valueOf(20))
				.adminSanctionAccordedYn(false).techSanctionAccordedYn(false).hkrdbFundedYn(false)
				.approvedBudgetYn(true).provisionalAmount(BigDecimal.valueOf(70.0000))
				.grantAllocatedAmount(BigDecimal.valueOf(10.0000)).headOfAccount("SSA");

		return workEstimate;
	}

	/**
	 * Creates the sub estimate entity.
	 *
	 * @return the sub estimate
	 */
	private SubEstimate createSubEstimateEntity() {
		SubEstimate subEstimate = new SubEstimate().id(1L).workEstimateId(1L).sorWorCategoryId(1L)
				.subEstimateName("Repair Work").estimateTotal(BigDecimal.valueOf(1000000.0000)).areaWeightageCircle(1L)
				.areaWeightageDescription("Area weightage Description.").completedYn(false);

		return subEstimate;
	}

	/**
	 * Creates the work category DTO.
	 *
	 * @return the work category DTO
	 */
	private WorkCategoryDTO createWorkCategoryDTO() {
		WorkCategoryDTO workCategoryDTO = new WorkCategoryDTO();
		workCategoryDTO.setCategoryName("test");
		workCategoryDTO.setCategoryCode(DEFAULT_WORK_CATEGORY_CODE);
		workCategoryDTO.setActiveYn(true);
		return workCategoryDTO;
	}

	/**
	 * Sets the up.
	 */
	@BeforeClass
	public void setUp() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - setUp");

		workEstimate = createEntity(em);
		workEstimate = createUpdatedEntity();
		subEstimate = createSubEstimateEntity();

		WorkCategoryDTO workCategoryDTO = workCategoryService.save(createWorkCategoryDTO());
		workEstimate.setWorkCategoryId(workCategoryDTO.getId());

		// Initializing work estimate.
		workEstimateRepository.saveAndFlush(workEstimate);
	}

	/**
	 * Inits the test.
	 */
	@BeforeMethod
	public void initTest() {
		workEstimate = createEntity(em);

	}

	/**
	 * Creates the work estimate.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	@Transactional
	void createWorkEstimate() throws Exception {

		int databaseSizeBeforeCreate = workEstimateRepository.findAll().size();
		// Create the WorkEstimate
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setWorkEstimateNumber("AAAAA1234/2021-22/AAAAA334/WORK_INDENT600");
		workEstimateDTO.setFileNumber(Utility.getRandomAlphaNumric(10));
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);

		restWorkEstimateMockMvc.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO))).andExpect(status().isCreated());

		// Validate the WorkEstimate in the database
		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeCreate + 1);
		WorkEstimate testWorkEstimate = workEstimateList.get(workEstimateList.size() - 1);
		assertThat(testWorkEstimate.getWorkEstimateNumber()).isEqualTo(DEFAULT_WORK_ESTIMATE_NUMBER);
		assertThat(testWorkEstimate.getStatus()).isEqualTo(DEFAULT_STATUS);
		assertThat(testWorkEstimate.getDeptId()).isEqualTo(DEFAULT_DEPT_ID);
		assertThat(testWorkEstimate.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
		// assertThat(testWorkEstimate.getFileNumber()).isEqualTo(DEFAULT_FILE_NUMBER);
		assertThat(testWorkEstimate.getName()).isEqualTo(DEFAULT_NAME);
		assertThat(testWorkEstimate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testWorkEstimate.getEstimateTypeId()).isEqualTo(DEFAULT_ESTIMATE_TYPE_ID);
		assertThat(testWorkEstimate.getWorkTypeId()).isEqualTo(DEFAULT_WOTK_TYPE_ID);
		assertThat(testWorkEstimate.getWorkCategoryId()).isEqualTo(DEFAULT_WORK_CATEGORY_ID);
		assertThat(testWorkEstimate.getWorkCategoryAttribute()).isEqualTo(DEFAULT_WORK_CATEGORY_ATTRIBUTE);
		assertThat(testWorkEstimate.getWorkCategoryAttributeValue())
				.isEqualByComparingTo(DEFAULT_WORK_CATEGORY_ATTRIBUTE_VALUE);
		assertThat(testWorkEstimate.getAdminSanctionAccordedYn()).isEqualTo(DEFAULT_ADMIN_SANCTION_ACCORDED_YN);
		assertThat(testWorkEstimate.getTechSanctionAccordedYn()).isEqualTo(DEFAULT_TECH_SANCTION_ACCORDED_YN);
		assertThat(testWorkEstimate.getLineEstimateTotal()).isEqualByComparingTo(DEFAULT_LINE_ESTIMATE_TOTAL);
		assertThat(testWorkEstimate.getEstimateTotal()).isEqualByComparingTo(DEFAULT_ESTIMATE_TOTAL);
		assertThat(testWorkEstimate.getGroupLumpsumTotal()).isEqualByComparingTo(DEFAULT_LUMPSUM_TOTAL);
		assertThat(testWorkEstimate.getGroupOverheadTotal()).isEqualByComparingTo(DEFAULT_GROUP_OVERHEAD_TOTAL);
		assertThat(testWorkEstimate.getAdminSanctionRefNumber()).isEqualTo(DEFAULT_ADMIN_SANCTION_REF_NUMBER);
		assertThat(testWorkEstimate.getAdminSanctionRefDate()).isEqualTo(DEFAULT_ADMIN_SANCTION_REF_DATE);
		assertThat(testWorkEstimate.getAdminSanctionedDate()).isEqualTo(DEFAULT_ADMIN_SANCTIONED_DATE);
		assertThat(testWorkEstimate.getTechSanctionRefNumber()).isEqualTo(DEFAULT_TECH_SANCTION_REF_NUMBER);
		assertThat(testWorkEstimate.getTechSanctionedDate()).isEqualTo(DEFAULT_TECH_SANCTIONED_DATE);
		assertThat(testWorkEstimate.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
		assertThat(testWorkEstimate.getApprovedTs()).isEqualTo(DEFAULT_APPROVED_TS);
		assertThat(testWorkEstimate.getHkrdbFundedYn()).isEqualTo(DEFAULT_HKRDB_FUNDED_YN);
		assertThat(testWorkEstimate.getSchemeId()).isEqualTo(DEFAULT_SCHEME_ID);
		assertThat(testWorkEstimate.getApprovedBudgetYn()).isEqualTo(DEFAULT_APPROVED_BUDGET_YN);
		assertThat(testWorkEstimate.getGrantAllocatedAmount()).isEqualByComparingTo(DEFAULT_GRANT_ALLOCATED_AMOUNT);
		assertThat(testWorkEstimate.getDocumentReference()).isEqualTo(DEFAULT_DOCUMENT_REFERENCE);
		assertThat(testWorkEstimate.getProvisionalAmount()).isEqualByComparingTo(DEFAULT_PROVISIONAL_AMOUNT);
		assertThat(testWorkEstimate.getHeadOfAccount()).isEqualTo(DEFAULT_HEAD_OF_ACCOUNT);
	}

	/**
	 * Creates the work estimate with existing id.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 2)
	@Transactional
	void createWorkEstimateWithExistingId() throws Exception {
		// Create the WorkEstimate with an existing ID
		workEstimate.setId(1L);

		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);
		int databaseSizeBeforeCreate = workEstimateRepository.findAll().size();

		// An entity with an existing ID cannot be created, so this API call must fail
		restWorkEstimateMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimate in the database
		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeCreate);
	}

	/**
	 * Post work estimate not found.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 3)
	@Transactional
	void postWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postWorkEstimateNotFound - Start");

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setWorkEstimateNumber("AAAAA1234/2021-22/AAAAA334/WORK_INDENT601");
		workEstimateDTO.setFileNumber(Utility.getRandomAlphaNumric(10));
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);

		int databaseSizeBeforeCreate = workEstimateRepository.findAll().size();

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateMockMvc
				.perform(post(ENTITY_API_URL, WORKESTIMATEID).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		// Validate the workEstimateList in the database
		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	/**
	 * Check work estimate number is required.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 4)
	@Transactional
	void checkWorkEstimateNumberIsRequired() throws Exception {

		int databaseSizeBeforeTest = workEstimateRepository.findAll().size();
		// set the field null
		workEstimate.setWorkEstimateNumber(null);
		workEstimate.setFileNumber(Utility.getRandomAlphaNumric(10));

		// Create the WorkEstimate, which fails.
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		// workEstimateDTO.setWorkEstimateNumber("AAAAA1234/2021-22/AAAAA334/WORK_INDENT600");
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);
		restWorkEstimateMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check status is required.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 5)
	@Transactional
	void checkStatusIsRequired() throws Exception {

		int databaseSizeBeforeTest = workEstimateRepository.findAll().size();
		// set the field null
		workEstimate.setStatus(null);

		// Create the WorkEstimate, which fails.
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);
		workEstimateDTO.setFileNumber(Utility.getRandomAlphaNumric(10));

		restWorkEstimateMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check dept id is required.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 6)
	@Transactional
	void checkDeptIdIsRequired() throws Exception {
		int databaseSizeBeforeTest = workEstimateRepository.findAll().size();
		// set the field null
		workEstimate.setDeptId(null);

		// Create the WorkEstimate, which fails.
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);

		restWorkEstimateMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check location id is required.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 7)
	@Transactional
	void checkLocationIdIsRequired() throws Exception {
		int databaseSizeBeforeTest = workEstimateRepository.findAll().size();
		// set the field null
		workEstimate.setLocationId(null);

		// Create the WorkEstimate, which fails.
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);
		restWorkEstimateMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check file number is required.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 8)
	@Transactional
	void checkFileNumberIsRequired() throws Exception {
		int databaseSizeBeforeTest = workEstimateRepository.findAll().size();
		// set the field null
		workEstimate.setFileNumber(null);

		// Create the WorkEstimate, which fails.
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);
		restWorkEstimateMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check name is required.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 9)
	@Transactional
	void checkNameIsRequired() throws Exception {
		int databaseSizeBeforeTest = workEstimateRepository.findAll().size();
		// set the field null
		workEstimate.setName(null);

		// Create the WorkEstimate, which fails.
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);
		restWorkEstimateMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check estimate type id is required.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 10)
	@Transactional
	void checkEstimateTypeIdIsRequired() throws Exception {
		int databaseSizeBeforeTest = workEstimateRepository.findAll().size();
		// set the field null
		workEstimate.setEstimateTypeId(null);

		// Create the WorkEstimate, which fails.
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);

		restWorkEstimateMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check work type id is required.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 11)
	@Transactional
	void checkWorkTypeIdIsRequired() throws Exception {
		int databaseSizeBeforeTest = workEstimateRepository.findAll().size();
		// set the field null
		workEstimate.setWorkTypeId(null);

		// Create the WorkEstimate, which fails.
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);
		restWorkEstimateMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check work category id is required.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 12)
	@Transactional
	void checkWorkCategoryIdIsRequired() throws Exception {
		int databaseSizeBeforeTest = workEstimateRepository.findAll().size();
		// set the field null
		workEstimate.setWorkCategoryId(null);

		// Create the WorkEstimate, which fails.
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);
		restWorkEstimateMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check approved budget yn is required.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 13)
	@Transactional
	void checkApprovedBudgetYnIsRequired() throws Exception {
		int databaseSizeBeforeTest = workEstimateRepository.findAll().size();
		// set the field null
		workEstimate.setApprovedBudgetYn(null);

		// Create the WorkEstimate, which fails.
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);
		restWorkEstimateMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Gets the non existing work estimate.
	 *
	 * @return the non existing work estimate
	 * @throws Exception the exception
	 */
	@Test(priority = 14)
	@Transactional
	void getNonExistingWorkEstimate() throws Exception {
		// Get the workEstimate
		restWorkEstimateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	/**
	 * Gets the work estimate.
	 *
	 * @return the work estimate
	 * @throws Exception the exception
	 */
	@Test(priority = 15)
	@Transactional
	void getWorkEstimate() throws Exception {

		// Initialize the database

		// Initialize the database
		List<WorkEstimate> findAll = workEstimateRepository.findAll();

		workEstimate = findAll.get(0);

		// Get the workEstimate
		restWorkEstimateMockMvc.perform(get(ENTITY_API_URL_ID, workEstimate.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(workEstimate.getId().intValue()));

	}

	/**
	 * Gets the validate sub estimates.
	 *
	 * @return the validate sub estimates
	 * @throws Exception the exception
	 */
	@Test(priority = 16)
	@Transactional
	void getValidateSubEstimates() throws Exception {

		workEstimate = createEntity(em);
		workEstimate.setFileNumber(Utility.getRandomAlphaNumric(10));
		workEstimate.setWorkEstimateNumber(Utility.getRandomAlphaNumric(10));
		workEstimate = workEstimateRepository.save(workEstimate);

		subEstimate = createSubEstimateEntity();
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimate.setCompletedYn(true);
		subEstimate = subEstimateRepository.saveAndFlush(subEstimate);

		// Get the workEstimate
		restWorkEstimateMockMvc.perform(get(ENTITY_API_URL_ID_SUBESTIMATE, workEstimate.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	/**
	 * Gets the validate sub estimate not found.
	 *
	 * @return the validate sub estimate not found
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 17)
	@Transactional
	void getValidateSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkEstimateNotFound - Start");

		workEstimate.setId(1L);
		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateMockMvc.perform(get(ENTITY_API_URL_ID_SUBESTIMATE, WORKESTIMATEID, workEstimate.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - getWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	/**
	 * Put new work estimate.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 18)
	@Transactional
	void putNewWorkEstimate() throws Exception {

		workEstimate = createEntity(em);
		workEstimate.setFileNumber(Utility.getRandomAlphaNumric(10));
		workEstimate.setWorkEstimateNumber(Utility.getRandomAlphaNumric(10));
		workEstimate = workEstimateRepository.save(workEstimate);
		workEstimate.status(UPDATED_STATUS).deptId(UPDATED_DEPT_ID).fileNumber(Utility.getRandomAlphaNumric(10))
				.locationId(UPDATED_LOCATION_ID).name(UPDATED_NAME).description(UPDATED_DESCRIPTION)
				.estimateTypeId(UPDATED_ESTIMATE_TYPE_ID).workTypeId(UPDATED_WOTK_TYPE_ID)
				.workCategoryId(UPDATED_WORK_CATEGORY_ID).workCategoryAttribute(UPDATED_WORK_CATEGORY_ATTRIBUTE)
				.workCategoryAttributeValue(UPDATED_WORK_CATEGORY_ATTRIBUTE_VALUE)
				.hkrdbFundedYn(UPDATED_HKRDB_FUNDED_YN).schemeId(25L).approvedBudgetYn(UPDATED_APPROVED_BUDGET_YN)
				.grantAllocatedAmount(UPDATED_GRANT_ALLOCATED_AMOUNT).documentReference(UPDATED_DOCUMENT_REFERENCE)
				.provisionalAmount(UPDATED_PROVISIONAL_AMOUNT).headOfAccount(UPDATED_HEAD_OF_ACCOUNT);
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);

		restWorkEstimateMockMvc.perform(put(ENTITY_API_URL_ID, workEstimateDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isOk());

		// Validate the WorkEstimate in the database
		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		WorkEstimate testWorkEstimate = workEstimateList.get(workEstimateList.size() - 1);

		assertThat(testWorkEstimate.getStatus()).isEqualTo(DEFAULT_STATUS);
		assertThat(testWorkEstimate.getDeptId()).isEqualTo(DEFAULT_DEPT_ID);
		assertThat(testWorkEstimate.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
	}

	/**
	 * Put non existing work estimate.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 19)
	@Transactional
	void putNonExistingWorkEstimate() throws Exception {
		int databaseSizeBeforeUpdate = workEstimateRepository.findAll().size();
		workEstimate.setId(count.incrementAndGet());

		// Create the WorkEstimate
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restWorkEstimateMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimateDTO.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimate in the database
		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeUpdate);
	}

	/**
	 * Put with id mismatch work estimate.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 20)
	@Transactional
	void putWithIdMismatchWorkEstimate() throws Exception {
		int databaseSizeBeforeUpdate = workEstimateRepository.findAll().size();
		workEstimate.setId(count.incrementAndGet());

		// Create the WorkEstimate
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restWorkEstimateMockMvc
				.perform(put(ENTITY_API_URL_ID, count.incrementAndGet()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimate in the database
		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeUpdate);
	}

	/**
	 * Put with missing id path param work estimate.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 21)
	@Transactional
	void putWithMissingIdPathParamWorkEstimate() throws Exception {
		int databaseSizeBeforeUpdate = workEstimateRepository.findAll().size();
		workEstimate.setId(count.incrementAndGet());

		// Create the WorkEstimate
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restWorkEstimateMockMvc
				.perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isMethodNotAllowed());

		// Validate the WorkEstimate in the database
		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeUpdate);
	}

	/**
	 * Put work estimate bad request.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 22)
	@Transactional
	void putWorkEstimateBadRequest() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putWorkEstimateBadRequest - Start");
		int databaseSizeBeforeUpdate = workEstimateRepository.findAll().size();
		// Initialize the database
		workEstimate.setId(count.incrementAndGet());
		// setting wrong id here

		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateMockMvc.perform(put(ENTITY_API_URL_ID, workEstimateDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimate in the database
		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeUpdate);

		log.info("Test - putWorkEstimateBadRequest - End");
		log.info("==========================================================================");
	}

	/**
	 * Put invalid status bad request.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 23)
	@Transactional
	void putInvalidStatusBadRequest() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putInvalidStatusBadRequest - Start");

		workEstimate.setId(1L);
		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(workEstimate);

		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);

		int databaseSizeBeforeCreate = workEstimateRepository.findAll().size();

		restWorkEstimateMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimateDTO.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimate in the database
		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeCreate);

		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - putInvalidStatusBadRequest - End");
		log.info("==========================================================================");
	}

	/**
	 * Partial update work estimate with patch.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 24)
	@Transactional
	void partialUpdateWorkEstimateWithPatch() throws Exception {
		// Initialize the database

		workEstimate = createUpdatedEntity();
		workEstimate.setId(1L);

		// Update the workEstimate using partial update
		WorkEstimate partialUpdatedWorkEstimate = new WorkEstimate();
		partialUpdatedWorkEstimate.setId(workEstimate.getId());

		partialUpdatedWorkEstimate.deptId(UPDATED_DEPT_ID).locationId(UPDATED_LOCATION_ID).name(UPDATED_NAME)
				.description(UPDATED_DESCRIPTION).workCategoryAttribute(UPDATED_WORK_CATEGORY_ATTRIBUTE)
				.workCategoryAttributeValue(UPDATED_WORK_CATEGORY_ATTRIBUTE_VALUE).schemeId(UPDATED_SCHEME_ID)
				.approvedBudgetYn(UPDATED_APPROVED_BUDGET_YN).documentReference(UPDATED_DOCUMENT_REFERENCE)
				.headOfAccount(UPDATED_HEAD_OF_ACCOUNT);

		restWorkEstimateMockMvc
				.perform(patch(ENTITY_API_URL_ID, partialUpdatedWorkEstimate.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimate)))
				.andExpect(status().isOk());
	}

	/**
	 * Full update work estimate with patch.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 25)
	@Transactional
	void fullUpdateWorkEstimateWithPatch() throws Exception {
		// Initialize the database
		workEstimate = createUpdatedEntity();
		workEstimate.setId(1L);

		// Update the workEstimate using partial update
		WorkEstimate partialUpdatedWorkEstimate = new WorkEstimate();
		partialUpdatedWorkEstimate.setId(workEstimate.getId());

		partialUpdatedWorkEstimate.workEstimateNumber(DEFAULT_WORK_ESTIMATE_NUMBER).status(UPDATED_STATUS)
				.deptId(UPDATED_DEPT_ID).locationId(UPDATED_LOCATION_ID).fileNumber(UPDATED_FILE_NUMBER)
				.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).estimateTypeId(UPDATED_ESTIMATE_TYPE_ID)
				.workTypeId(UPDATED_WOTK_TYPE_ID).workCategoryId(UPDATED_WORK_CATEGORY_ID)
				.workCategoryAttribute(UPDATED_WORK_CATEGORY_ATTRIBUTE)
				.workCategoryAttributeValue(UPDATED_WORK_CATEGORY_ATTRIBUTE_VALUE)
				.hkrdbFundedYn(UPDATED_HKRDB_FUNDED_YN).schemeId(UPDATED_SCHEME_ID)
				.approvedBudgetYn(UPDATED_APPROVED_BUDGET_YN).grantAllocatedAmount(UPDATED_GRANT_ALLOCATED_AMOUNT)
				.documentReference(UPDATED_DOCUMENT_REFERENCE).provisionalAmount(UPDATED_PROVISIONAL_AMOUNT)
				.headOfAccount(UPDATED_HEAD_OF_ACCOUNT);

		restWorkEstimateMockMvc
				.perform(patch(ENTITY_API_URL_ID, partialUpdatedWorkEstimate.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimate)))
				.andExpect(status().isOk());
	}

	/**
	 * Patch non existing work estimate.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 26)
	@Transactional
	void patchNonExistingWorkEstimate() throws Exception {
		int databaseSizeBeforeUpdate = workEstimateRepository.findAll().size();
		workEstimate.setId(count.incrementAndGet());

		// Create the WorkEstimate
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restWorkEstimateMockMvc
				.perform(patch(ENTITY_API_URL_ID, workEstimateDTO.getId()).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isNotFound());
		// Validate the WorkEstimate in the database
		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeUpdate);
	}

	/**
	 * Partial invalid status bad request.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 27)
	@Transactional
	void partialInvalidStatusBadRequest() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putInvalidStatusBadRequest - Start");

		workEstimate.setId(1L);
		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(workEstimate);

		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);

		int databaseSizeBeforeCreate = workEstimateRepository.findAll().size();

		restWorkEstimateMockMvc
				.perform(patch(ENTITY_API_URL_ID, workEstimateDTO.getId()).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimate in the database
		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeCreate);

		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.INITIAL);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - putInvalidStatusBadRequest - End");
		log.info("==========================================================================");
	}

	/**
	 * Patch with id mismatch work estimate.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 28)
	@Transactional
	void patchWithIdMismatchWorkEstimate() throws Exception {
		int databaseSizeBeforeUpdate = workEstimateRepository.findAll().size();
		workEstimate.setId(count.incrementAndGet());

		// Create the WorkEstimate
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restWorkEstimateMockMvc
				.perform(patch(ENTITY_API_URL_ID, count.incrementAndGet()).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimate in the database
		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeUpdate);
	}

	/**
	 * Patch with missing id path param work estimate.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 29)
	@Transactional
	void patchWithMissingIdPathParamWorkEstimate() throws Exception {
		int databaseSizeBeforeUpdate = workEstimateRepository.findAll().size();
		workEstimate.setId(count.incrementAndGet());

		// Create the WorkEstimate
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restWorkEstimateMockMvc
				.perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isMethodNotAllowed());

		// Validate the WorkEstimate in the database
		List<WorkEstimate> workEstimateList = workEstimateRepository.findAll();
		assertThat(workEstimateList).hasSize(databaseSizeBeforeUpdate);
	}

	/**
	 * Check updated file number is required.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 30)
	@Transactional
	void checkUpdatedFileNumberIsRequired() throws Exception {

		WorkEstimate workEstimate2 = createWorkEstimateEntity();
		workEstimate2.setId(null);
		workEstimate2.setWorkEstimateNumber(Utility.getRandomAlphaNumric(10));
		workEstimate2.setFileNumber(Utility.getRandomAlphaNumric(10));
		workEstimate2.setSchemeId(workEstimate.getSchemeId());
		workEstimate2 = workEstimateRepository.saveAndFlush(workEstimate2);

		// Create the WorkEstimate, which fails.
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate2);
		workEstimateDTO.setDeptCode(DEFAULT_DEPT_CODE);
		workEstimateDTO.setWorkCategoryCode(DEFAULT_WORK_CATEGORY_CODE);
		workEstimateDTO.setFileNumber(null);

		restWorkEstimateMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimateDTO.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateDTO)))
				.andExpect(status().isBadRequest());

	}

	/**
	 * Gets the validate sub estimate bad request.
	 *
	 * @return the validate sub estimate bad request
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 31)
	@Transactional
	void getValidateSubEstimateBadRequest() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getValidateSubEstimateBadRequest - Start");

		workEstimate.setId(1L);
		subEstimate = createSubEstimateEntity();
		subEstimate.setCompletedYn(false);
		subEstimateRepository.saveAndFlush(subEstimate);
		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateMockMvc.perform(get(ENTITY_API_URL_ID_SUBESTIMATE, workEstimate.getId()))
				.andExpect(status().isBadRequest());

		log.info("Test - getValidateSubEstimateBadRequest - End");
		log.info("==========================================================================");
	}

	/**
	 * Gets the calculated check bad request.
	 *
	 * @return the calculated check bad request
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 32)
	@Transactional
	void getCalculatedCheckBadRequest() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getCalculatedCheckBadRequest - Start");

		workEstimate.setId(1L);
		subEstimate = createSubEstimateEntity();
		subEstimate.setCompletedYn(true);
		subEstimateRepository.saveAndFlush(subEstimate);
		WorkEstimateDTO workEstimateDTO = workEstimateMapper.toDto(workEstimate);
		workEstimateDTO.setProvisionalAmount(DEFAULT_PROVISIONAL_AMOUNT);

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateMockMvc.perform(get(ENTITY_API_URL_ID_SUBESTIMATE, workEstimate.getId()))
				.andExpect(status().isBadRequest());

		log.info("Test - getCalculatedCheckBadRequest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 33)
	@Transactional
	void searchWorkEstimate() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - searchWorkEstimate - Start");

		String createdBy = "system";

		WorkEstimate workEsimate = createEntity(em);
		workEsimate.setWorkEstimateNumber("aaq11");

		workEstimateRepository.saveAndFlush(workEsimate);

		WorkEstimateSearchDTO workEstimateSearchDTO = new WorkEstimateSearchDTO();
		workEstimateSearchDTO.setFileNumber(workEsimate.getFileNumber());
		workEstimateSearchDTO.setName(workEsimate.getName());
		workEstimateSearchDTO.setWorkEstimateNumber(workEsimate.getWorkEstimateNumber());

		restWorkEstimateMockMvc.perform(post("/v1/api/work-estimate/search-work-estimate")
				.queryParam("createdBy", createdBy).queryParam("page", "0").queryParam("size", "5")
				.queryParam("workEstimateSearch", "RECENT_ESTIMATES").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(workEstimateSearchDTO))).andExpect(status().isOk());

		log.info("==========================================================================");
		log.info("Test - searchWorkEstimate - End");

	}

	@Test(priority = 33)
	@Transactional
	void searchWorkEstimateSearchEstimates() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - searchWorkEstimateSearchEstimates - Start");

		String createdBy = "system";

		WorkEstimate workEsimate = createEntity(em);
		workEsimate.setWorkEstimateNumber("zaq11");
		workEstimateRepository.saveAndFlush(workEsimate);

		List<WorkEstimateStatus> statusList = new ArrayList<>();
		statusList.add(WorkEstimateStatus.GRANT_ALLOCATED);

		WorkEstimateSearchDTO workEstimateSearchDTO = new WorkEstimateSearchDTO();
		workEstimateSearchDTO.setFileNumber(workEsimate.getFileNumber());
		workEstimateSearchDTO.setName(workEsimate.getName());
		workEstimateSearchDTO.setWorkEstimateNumber(workEsimate.getWorkEstimateNumber());
		workEstimateSearchDTO.setWorkEstimateStatusList(statusList);

		restWorkEstimateMockMvc.perform(post("/v1/api/work-estimate/search-work-estimate")
				.queryParam("createdBy", createdBy).queryParam("page", "0").queryParam("size", "5")
				.queryParam("workEstimateSearch", "SEARCH_ESTIMATES").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(workEstimateSearchDTO))).andExpect(status().isOk());

		log.info("==========================================================================");
		log.info("Test - searchWorkEstimateSearchEstimates - End");

	}

}
