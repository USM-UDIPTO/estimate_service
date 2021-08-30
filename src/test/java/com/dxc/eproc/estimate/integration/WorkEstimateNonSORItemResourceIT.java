package com.dxc.eproc.estimate.integration;

import static com.dxc.eproc.estimate.integration.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.EstimateServiceApplication;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.model.WorkEstimateItem;
import com.dxc.eproc.estimate.model.WorkEstimateItemLBD;
import com.dxc.eproc.estimate.repository.SubEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateItemLBDRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateItemRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.dxc.eproc.estimate.response.dto.SubEstimateResponseDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateItemMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration tests for the {@link WorkEstimateItemResource} REST controller.
 */
@SpringBootTest(classes = EstimateServiceApplication.class)
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class WorkEstimateNonSORItemResourceIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(WorkEstimateNonSORItemResourceIT.class);

	private static final Long DEFAULT_SUB_ESTIMATE_ID = 1L;
	private static final Long UPDATED_SUB_ESTIMATE_ID = 2L;

	private static final Long DEFAULT_CAT_WORK_SOR_ITEM_ID = 1L;
	private static final Long UPDATED_CAT_WORK_SOR_ITEM_ID = 2L;

	private static final Long DEFAULT_WORK_CATEGORY_ID = 1L;
	private static final Long UPDATED_WORK_CATEGORY_ID = 2L;

	private static final Integer DEFAULT_ENTRY_ORDER = 1;
	private static final Integer UPDATED_ENTRY_ORDER = 2;

	private static final String DEFAULT_ITEM_CODE = "AAAAAAAAAA";
	private static final String UPDATED_ITEM_CODE = "BBBBBBBBBB";

	private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
	private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

	private static final Long DEFAULT_UOM_ID = 1L;
	private static final Long UPDATED_UOM_ID = 2L;
	
	private static final String DEFAULT_UOM_NAME = "AAAAAAAAAA";
	private static final String UPDATED_UOM_NAME = "BBBBBBBBBB";

	private static final BigDecimal DEFAULT_BASE_RATE = new BigDecimal(1);
	private static final BigDecimal UPDATED_BASE_RATE = new BigDecimal(2);

	private static final BigDecimal DEFAULT_FINAL_RATE = new BigDecimal(1);
	private static final BigDecimal UPDATED_FINAL_RATE = new BigDecimal(2);

	private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
	private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

	private static final Integer DEFAULT_FLOOR_NUMBER = 1;
	private static final Integer UPDATED_FLOOR_NUMBER = 2;

	private static final BigDecimal DEFAULT_LABOUR_RATE = new BigDecimal(1);
	private static final BigDecimal UPDATED_LABOUR_RATE = new BigDecimal(2);

	private static final Boolean DEFAULT_LBD_PERFORMED_YN = false;
	private static final Boolean UPDATED_LBD_PERFORMED_YN = true;

	private static final Boolean DEFAULT_RA_PERFORMED_YN = false;
	private static final Boolean UPDATED_RA_PERFORMED_YN = true;

	private static final String ENTITY_API_URL = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items";
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
	private static final String UPLOAD_FILE_URL = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/upload-non-sor-items";
	private static final String SAVE_UPLOADED_FILE_URL = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/save-uploaded-non-sor-items";

	private static Random random = new Random();
	private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

	@Autowired
	private WorkEstimateItemRepository workEstimateItemRepository;
	@Autowired
	private WorkEstimateRepository workEstimateRepository;
	@Autowired
	private SubEstimateRepository subEstimateRepository;
	/** The work estimate item LBD repository. */
	@Autowired
	private WorkEstimateItemLBDRepository workEstimateItemLBDRepository;

	@Autowired
	private WorkEstimateItemMapper workEstimateItemMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restWorkEstimateItemMockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	private WorkEstimate workEstimate;

	private SubEstimate subEstimate;

	private WorkEstimateItem workEstimateItem;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static WorkEstimateItem createEntity(EntityManager em) {
		WorkEstimateItem workEstimateItem = new WorkEstimateItem().subEstimateId(DEFAULT_SUB_ESTIMATE_ID)
				.catWorkSorItemId(DEFAULT_CAT_WORK_SOR_ITEM_ID).categoryId(DEFAULT_WORK_CATEGORY_ID)
				.entryOrder(DEFAULT_ENTRY_ORDER).itemCode(DEFAULT_ITEM_CODE).description(DEFAULT_DESCRIPTION)
				.uomId(DEFAULT_UOM_ID).uomName(DEFAULT_UOM_NAME).baseRate(DEFAULT_BASE_RATE).finalRate(DEFAULT_FINAL_RATE)
				.quantity(DEFAULT_QUANTITY).floorNumber(DEFAULT_FLOOR_NUMBER).labourRate(DEFAULT_LABOUR_RATE)
				.lbdPerformedYn(DEFAULT_LBD_PERFORMED_YN).raPerformedYn(DEFAULT_RA_PERFORMED_YN);
		return workEstimateItem;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static WorkEstimateItem createUpdatedEntity(EntityManager em) {
		WorkEstimateItem workEstimateItem = new WorkEstimateItem().subEstimateId(UPDATED_SUB_ESTIMATE_ID)
				.catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID).categoryId(UPDATED_WORK_CATEGORY_ID)
				.entryOrder(UPDATED_ENTRY_ORDER).itemCode(UPDATED_ITEM_CODE).description(UPDATED_DESCRIPTION)
				.uomId(UPDATED_UOM_ID).uomName(UPDATED_UOM_NAME).baseRate(UPDATED_BASE_RATE).finalRate(UPDATED_FINAL_RATE)
				.quantity(UPDATED_QUANTITY).floorNumber(UPDATED_FLOOR_NUMBER).labourRate(UPDATED_LABOUR_RATE)
				.lbdPerformedYn(UPDATED_LBD_PERFORMED_YN).raPerformedYn(UPDATED_RA_PERFORMED_YN);
		return workEstimateItem;
	}

	private WorkEstimate createWorkEstimateEntity() {
		WorkEstimate workEstimate = new WorkEstimate().workEstimateNumber("88888").status(WorkEstimateStatus.DRAFT)
				.deptId(1L).locationId(1L).fileNumber("1234").name("Road Repair")
				.description("Road Repair work estimate.").estimateTypeId(1L).workTypeId(1L).workCategoryId(1L)
				.workCategoryAttribute(10L).workCategoryAttributeValue(BigDecimal.valueOf(20))
				.adminSanctionAccordedYn(false).techSanctionAccordedYn(false).approvedBudgetYn(false);

		return workEstimate;
	}

	private SubEstimate createSubEstimateEntity() {
		SubEstimate subEstimate = new SubEstimate().workEstimateId(1L).sorWorCategoryId(1L)
				.subEstimateName("Repair Work").estimateTotal(BigDecimal.valueOf(1000000.0000)).areaWeightageCircle(1L)
				.areaWeightageDescription("Area weightage Description.").completedYn(false);

		return subEstimate;
	}

	@BeforeClass
	public void setUp() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - setUp");

		workEstimate = createWorkEstimateEntity();
		subEstimate = createSubEstimateEntity();

		// Initializing work estimate.
		workEstimateRepository.saveAndFlush(workEstimate);

		// Initializing sub estimate.
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);
	}

	@BeforeMethod
	public void initTest() {
		log.info("==================================================================================");
		log.info("This is executed before each Test - initTest");

		workEstimateItem = createEntity(em);
	}

	@Test
	@Transactional
	void createWorkEstimateItem() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkEstimateItem - Start");

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();
		// Create the WorkEstimateItem
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isCreated());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate + 1);
		WorkEstimateItem testWorkEstimateItem = workEstimateItemList.get(workEstimateItemList.size() - 1);
		// assertThat(testWorkEstimateItem.getSubEstimateId()).isEqualTo(DEFAULT_SUB_ESTIMATE_ID);
		assertThat(testWorkEstimateItem.getCatWorkSorItemId()).isEqualTo(null);
		assertThat(testWorkEstimateItem.getCategoryId()).isEqualTo(null);
		assertThat(testWorkEstimateItem.getEntryOrder()).isEqualTo(DEFAULT_ENTRY_ORDER);
		assertThat(testWorkEstimateItem.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
		assertThat(testWorkEstimateItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testWorkEstimateItem.getUomId()).isEqualTo(DEFAULT_UOM_ID);
		assertThat(testWorkEstimateItem.getBaseRate()).isEqualByComparingTo(DEFAULT_BASE_RATE);
		assertThat(testWorkEstimateItem.getFinalRate()).isEqualByComparingTo(DEFAULT_FINAL_RATE);
		assertThat(testWorkEstimateItem.getQuantity()).isEqualByComparingTo(DEFAULT_QUANTITY);
		assertThat(testWorkEstimateItem.getFloorNumber()).isEqualTo(DEFAULT_FLOOR_NUMBER);
		assertThat(testWorkEstimateItem.getLabourRate()).isEqualByComparingTo(DEFAULT_LABOUR_RATE);
		assertThat(testWorkEstimateItem.getLbdPerformedYn()).isEqualTo(DEFAULT_LBD_PERFORMED_YN);
		assertThat(testWorkEstimateItem.getRaPerformedYn()).isEqualTo(DEFAULT_RA_PERFORMED_YN);

		log.info("Test - createWorkEstimateItem - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void updateWorkEstimateItemWithExistingId() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkEstimateItemWithExistingId - Start");

		// Create the WorkEstimateItem with an existing ID
		workEstimateItem.setId(1L);
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// An entity with an existing ID cannot be created, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isCreated());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - createWorkEstimateItemWithExistingId - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void postWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postWorkEstimateNotFound - Start");

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, WORKESTIMATEID, subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void postSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postSubEstimateNotFound - Start");

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), SUBESTIMATEID)
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void postInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postInvalidWorkEstimateStatus - Start");

		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(workEstimate);

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - postInvalidWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void checkItemCodeIsRequired() throws Exception {
		log.info("==========================================================================");
		log.info("Test - checkItemCodeIsRequired - Start");

		int databaseSizeBeforeTest = workEstimateItemRepository.findAll().size();
		// set the field null
		workEstimateItem.setItemCode(null);

		// Create the WorkEstimateItem, which fails.
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeTest);

		log.info("Test - checkItemCodeIsRequired - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void checkDescriptionIsRequired() throws Exception {
		log.info("==========================================================================");
		log.info("Test - checkDescriptionIsRequired - Start");

		int databaseSizeBeforeTest = workEstimateItemRepository.findAll().size();
		// set the field null
		workEstimateItem.setDescription(null);

		// Create the WorkEstimateItem, which fails.
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeTest);

		log.info("Test - checkDescriptionIsRequired - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void checkUomIdIsRequired() throws Exception {
		log.info("==========================================================================");
		log.info("Test - checkUomIdIsRequired - Start");

		int databaseSizeBeforeTest = workEstimateItemRepository.findAll().size();
		// set the field null
		workEstimateItem.setUomId(null);

		// Create the WorkEstimateItem, which fails.
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeTest);

		log.info("Test - checkUomIdIsRequired - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void checkBaseRateIsRequired() throws Exception {
		log.info("==========================================================================");
		log.info("Test - checkBaseRateIsRequired - Start");

		int databaseSizeBeforeTest = workEstimateItemRepository.findAll().size();
		// set the field null
		workEstimateItem.setBaseRate(null);

		// Create the WorkEstimateItem, which fails.
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeTest);

		log.info("Test - checkBaseRateIsRequired - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllWorkEstimateItemsWithPagination() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkEstimateItemsWithPagination - Start");

		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// Get all the workEstimateItemList
		MvcResult mvcResult = restWorkEstimateItemMockMvc
				.perform(get(ENTITY_API_URL + "-with-pagination", workEstimate.getId(), subEstimate.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		SubEstimateResponseDTO responseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				SubEstimateResponseDTO.class);

		List<WorkEstimateItemDTO> workEstimateItemDTOs = responseDTO.getItems();
		WorkEstimateItemDTO testWorkEstimateItem = workEstimateItemDTOs.get(0);

		// assertThat(testWorkEstimateItem.getSubEstimateId()).isEqualTo(DEFAULT_SUB_ESTIMATE_ID);
		assertThat(testWorkEstimateItem.getCatWorkSorItemId()).isEqualTo(null);
		assertThat(testWorkEstimateItem.getCategoryId()).isEqualTo(null);
		assertThat(testWorkEstimateItem.getEntryOrder()).isEqualTo(DEFAULT_ENTRY_ORDER);
		assertThat(testWorkEstimateItem.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
		assertThat(testWorkEstimateItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testWorkEstimateItem.getUomId()).isEqualTo(DEFAULT_UOM_ID);
		assertThat(testWorkEstimateItem.getBaseRate()).isEqualByComparingTo(DEFAULT_BASE_RATE);
		assertThat(testWorkEstimateItem.getFinalRate()).isEqualByComparingTo(DEFAULT_FINAL_RATE);
		assertThat(testWorkEstimateItem.getQuantity()).isEqualByComparingTo(DEFAULT_QUANTITY);
		assertThat(testWorkEstimateItem.getFloorNumber()).isEqualTo(DEFAULT_FLOOR_NUMBER);
		assertThat(testWorkEstimateItem.getLabourRate()).isEqualByComparingTo(DEFAULT_LABOUR_RATE);
		assertThat(testWorkEstimateItem.getLbdPerformedYn()).isEqualTo(DEFAULT_LBD_PERFORMED_YN);
		assertThat(testWorkEstimateItem.getRaPerformedYn()).isEqualTo(DEFAULT_RA_PERFORMED_YN);

		log.info("Test - getAllWorkEstimateItemsWithPagination - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllWorkEstimateNotFoundWithPagination() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkEstimateNotFoundWithPagination - Start");

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(get(ENTITY_API_URL + "-with-pagination", WORKESTIMATEID, subEstimate.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - getAllWorkEstimateNotFoundWithPagination - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllSubEstimateNotFoundWithPagination() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getAllSubEstimateNotFoundWithPagination - Start");

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(get(ENTITY_API_URL + "-with-pagination?sort=id,desc", workEstimate.getId(), SUBESTIMATEID))
				.andExpect(status().isNotFound());

		log.info("Test - getAllSubEstimateNotFoundWithPagination - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllWorkEstimateItems() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkEstimateItems - Start");

		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// Get all the workEstimateItemList
		MvcResult mvcResult = restWorkEstimateItemMockMvc
				.perform(get(ENTITY_API_URL + "?sort=id,desc", workEstimate.getId(), subEstimate.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		SubEstimateResponseDTO responseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				SubEstimateResponseDTO.class);

		List<WorkEstimateItemDTO> workEstimateItemDTOs = responseDTO.getItems();
		WorkEstimateItemDTO testWorkEstimateItem = workEstimateItemDTOs.get(0);

		// assertThat(testWorkEstimateItem.getSubEstimateId()).isEqualTo(DEFAULT_SUB_ESTIMATE_ID);
		assertThat(testWorkEstimateItem.getCatWorkSorItemId()).isEqualTo(null);
		assertThat(testWorkEstimateItem.getCategoryId()).isEqualTo(null);
		assertThat(testWorkEstimateItem.getEntryOrder()).isEqualTo(DEFAULT_ENTRY_ORDER);
		assertThat(testWorkEstimateItem.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
		assertThat(testWorkEstimateItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testWorkEstimateItem.getUomId()).isEqualTo(DEFAULT_UOM_ID);
		assertThat(testWorkEstimateItem.getBaseRate()).isEqualByComparingTo(DEFAULT_BASE_RATE);
		assertThat(testWorkEstimateItem.getFinalRate()).isEqualByComparingTo(DEFAULT_FINAL_RATE);
		assertThat(testWorkEstimateItem.getQuantity()).isEqualByComparingTo(DEFAULT_QUANTITY);
		assertThat(testWorkEstimateItem.getFloorNumber()).isEqualTo(DEFAULT_FLOOR_NUMBER);
		assertThat(testWorkEstimateItem.getLabourRate()).isEqualByComparingTo(DEFAULT_LABOUR_RATE);
		assertThat(testWorkEstimateItem.getLbdPerformedYn()).isEqualTo(DEFAULT_LBD_PERFORMED_YN);
		assertThat(testWorkEstimateItem.getRaPerformedYn()).isEqualTo(DEFAULT_RA_PERFORMED_YN);

		log.info("Test - getAllWorkEstimateItems - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkEstimateNotFound - Start");

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc", WORKESTIMATEID, subEstimate.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - getAllWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getAllSubEstimateNotFound - Start");

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc", workEstimate.getId(), SUBESTIMATEID))
				.andExpect(status().isNotFound());

		log.info("Test - getAllSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getWorkEstimateItem() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkEstimateItem - Start");

		final Long SUBESTIMATEID = subEstimate.getId();

		workEstimateItem.setSubEstimateId(subEstimate.getId());
		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// Get the workEstimateItem
		restWorkEstimateItemMockMvc
				.perform(get(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(workEstimateItem.getId().intValue()))
				.andExpect(jsonPath("$.subEstimateId").value(SUBESTIMATEID))
				.andExpect(jsonPath("$.catWorkSorItemId").value(DEFAULT_CAT_WORK_SOR_ITEM_ID.intValue()))
				.andExpect(jsonPath("$.categoryId").value(DEFAULT_WORK_CATEGORY_ID.intValue()))
				.andExpect(jsonPath("$.entryOrder").value(DEFAULT_ENTRY_ORDER))
				.andExpect(jsonPath("$.itemCode").value(DEFAULT_ITEM_CODE))
				.andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
				.andExpect(jsonPath("$.uomId").value(DEFAULT_UOM_ID.intValue()))
				.andExpect(jsonPath("$.baseRate").value(sameNumber(DEFAULT_BASE_RATE)))
				.andExpect(jsonPath("$.finalRate").value(sameNumber(DEFAULT_FINAL_RATE)))
				.andExpect(jsonPath("$.quantity").value(sameNumber(DEFAULT_QUANTITY)))
				.andExpect(jsonPath("$.floorNumber").value(DEFAULT_FLOOR_NUMBER))
				.andExpect(jsonPath("$.labourRate").value(sameNumber(DEFAULT_LABOUR_RATE)))
				.andExpect(jsonPath("$.lbdPerformedYn").value(DEFAULT_LBD_PERFORMED_YN.booleanValue()))
				.andExpect(jsonPath("$.raPerformedYn").value(DEFAULT_RA_PERFORMED_YN.booleanValue()));

		log.info("Test - getWorkEstimateItem - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getNonExistingWorkEstimateItem() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getNonExistingWorkEstimateItem - Start");

		// Get the workEstimateItem
		restWorkEstimateItemMockMvc
				.perform(get(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), Long.MAX_VALUE))
				.andExpect(status().isNotFound());

		log.info("Test - getNonExistingWorkEstimateItem - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkEstimateNotFound - Start");

		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(get(ENTITY_API_URL_ID, WORKESTIMATEID, subEstimate.getId(), workEstimateItem.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - getWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getSubEstimateNotFound - Start");

		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(get(ENTITY_API_URL_ID, workEstimate.getId(), SUBESTIMATEID, workEstimateItem.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - getSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putNewWorkEstimateItem() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putNewWorkEstimateItem - Start");

		workEstimateItem.setSubEstimateId(subEstimate.getId());
		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		int databaseSizeBeforeUpdate = workEstimateItemRepository.findAll().size();

		// Update the workEstimateItem
		WorkEstimateItem updatedWorkEstimateItem = workEstimateItemRepository.findById(workEstimateItem.getId()).get();
		// Disconnect from session so that the updates on updatedWorkEstimateItem are
		// not directly saved in db
		em.detach(updatedWorkEstimateItem);
		updatedWorkEstimateItem.catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID).categoryId(UPDATED_WORK_CATEGORY_ID)
				.entryOrder(UPDATED_ENTRY_ORDER).itemCode(UPDATED_ITEM_CODE).description(UPDATED_DESCRIPTION)
				.uomId(UPDATED_UOM_ID).baseRate(UPDATED_BASE_RATE).finalRate(UPDATED_FINAL_RATE)
				.quantity(UPDATED_QUANTITY).floorNumber(UPDATED_FLOOR_NUMBER).labourRate(UPDATED_LABOUR_RATE)
				.lbdPerformedYn(UPDATED_LBD_PERFORMED_YN).raPerformedYn(UPDATED_RA_PERFORMED_YN);
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(updatedWorkEstimateItem);

		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isOk());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeUpdate);
		WorkEstimateItem testWorkEstimateItem = workEstimateItemList.get(workEstimateItemList.size() - 1);

		assertThat(testWorkEstimateItem.getCatWorkSorItemId()).isEqualTo(DEFAULT_CAT_WORK_SOR_ITEM_ID);
		assertThat(testWorkEstimateItem.getCategoryId()).isEqualTo(DEFAULT_WORK_CATEGORY_ID);
		assertThat(testWorkEstimateItem.getEntryOrder()).isEqualTo(UPDATED_ENTRY_ORDER);
		assertThat(testWorkEstimateItem.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
		assertThat(testWorkEstimateItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
		assertThat(testWorkEstimateItem.getUomId()).isEqualTo(UPDATED_UOM_ID);
		assertThat(testWorkEstimateItem.getBaseRate()).isEqualTo(String.format("%.4f", UPDATED_BASE_RATE));
		assertThat(testWorkEstimateItem.getFinalRate()).isEqualTo(String.format("%.4f", UPDATED_FINAL_RATE));
		assertThat(testWorkEstimateItem.getQuantity()).isEqualTo(String.format("%.4f", UPDATED_QUANTITY));
		assertThat(testWorkEstimateItem.getFloorNumber()).isEqualTo(UPDATED_FLOOR_NUMBER);
		assertThat(testWorkEstimateItem.getLabourRate()).isEqualTo(String.format("%.4f", UPDATED_LABOUR_RATE));
		assertThat(testWorkEstimateItem.getLbdPerformedYn()).isEqualTo(UPDATED_LBD_PERFORMED_YN);
		assertThat(testWorkEstimateItem.getRaPerformedYn()).isEqualTo(UPDATED_RA_PERFORMED_YN);

		log.info("Test - putNewWorkEstimateItem - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putNonExistingWorkEstimateItem() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putNonExistingWorkEstimateItem - Start");

		int databaseSizeBeforeUpdate = workEstimateItemRepository.findAll().size();
		workEstimateItem.setId(count.incrementAndGet());

		// Create the WorkEstimateItem
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeUpdate);

		log.info("Test - putNonExistingWorkEstimateItem - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putWorkEstimateNotFound - Start");

		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL_ID, WORKESTIMATEID, subEstimate.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putSubEstimateNotFound - Start");

		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimate.getId(), SUBESTIMATEID, workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - putSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putInvalidWorkEstimateStatus - Start");

		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(workEstimate);

		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - putInvalidWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putNewWorkEstimateItemError() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putNewWorkEstimateItemError - Start");

		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		int databaseSizeBeforeUpdate = workEstimateItemRepository.findAll().size();

		// Create the WorkEstimateItem
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		workEstimateItemDTO.setQuantity(null);
		workEstimateItemDTO.setItemCode(null);

		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeUpdate);

		log.info("Test - putNewWorkEstimateItemError - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void partialUpdateWorkEstimateItemWithPatch() throws Exception {
		log.info("==========================================================================");
		log.info("Test - partialUpdateWorkEstimateItemWithPatch - Start");

		workEstimateItem.setSubEstimateId(subEstimate.getId());
		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		int databaseSizeBeforeUpdate = workEstimateItemRepository.findAll().size();

		// Update the workEstimateItem using partial update
		WorkEstimateItem partialUpdatedWorkEstimateItem = new WorkEstimateItem();
		partialUpdatedWorkEstimateItem.setId(workEstimateItem.getId());

		partialUpdatedWorkEstimateItem.subEstimateId(UPDATED_SUB_ESTIMATE_ID)
				.catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID).entryOrder(UPDATED_ENTRY_ORDER).uomId(UPDATED_UOM_ID)
				.baseRate(UPDATED_BASE_RATE).quantity(UPDATED_QUANTITY).labourRate(UPDATED_LABOUR_RATE);

		restWorkEstimateItemMockMvc
				.perform(patch(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(),
						partialUpdatedWorkEstimateItem.getId()).contentType("application/merge-patch+json")
								.content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimateItem)))
				.andExpect(status().isOk());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeUpdate);
		WorkEstimateItem testWorkEstimateItem = workEstimateItemList.get(workEstimateItemList.size() - 1);
		assertThat(testWorkEstimateItem.getSubEstimateId()).isEqualTo(UPDATED_SUB_ESTIMATE_ID);
		assertThat(testWorkEstimateItem.getCatWorkSorItemId()).isEqualTo(UPDATED_CAT_WORK_SOR_ITEM_ID);
		assertThat(testWorkEstimateItem.getCategoryId()).isEqualTo(DEFAULT_WORK_CATEGORY_ID);
		assertThat(testWorkEstimateItem.getEntryOrder()).isEqualTo(UPDATED_ENTRY_ORDER);
		assertThat(testWorkEstimateItem.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
		assertThat(testWorkEstimateItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testWorkEstimateItem.getUomId()).isEqualTo(UPDATED_UOM_ID);
		assertThat(testWorkEstimateItem.getBaseRate()).isEqualByComparingTo(UPDATED_BASE_RATE);
		assertThat(testWorkEstimateItem.getFinalRate()).isEqualByComparingTo(DEFAULT_FINAL_RATE);
		assertThat(testWorkEstimateItem.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
		assertThat(testWorkEstimateItem.getFloorNumber()).isEqualTo(DEFAULT_FLOOR_NUMBER);
		assertThat(testWorkEstimateItem.getLabourRate()).isEqualByComparingTo(UPDATED_LABOUR_RATE);
		assertThat(testWorkEstimateItem.getLbdPerformedYn()).isEqualTo(DEFAULT_LBD_PERFORMED_YN);
		assertThat(testWorkEstimateItem.getRaPerformedYn()).isEqualTo(DEFAULT_RA_PERFORMED_YN);

		log.info("Test - partialUpdateWorkEstimateItemWithPatch - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void fullUpdateWorkEstimateItemWithPatch() throws Exception {
		log.info("==========================================================================");
		log.info("Test - fullUpdateWorkEstimateItemWithPatch - Start");

		workEstimateItem.setSubEstimateId(subEstimate.getId());
		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		int databaseSizeBeforeUpdate = workEstimateItemRepository.findAll().size();

		// Update the workEstimateItem using partial update
		WorkEstimateItem partialUpdatedWorkEstimateItem = new WorkEstimateItem();
		partialUpdatedWorkEstimateItem.setId(workEstimateItem.getId());

		partialUpdatedWorkEstimateItem.subEstimateId(UPDATED_SUB_ESTIMATE_ID)
				.catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID).categoryId(UPDATED_WORK_CATEGORY_ID)
				.entryOrder(UPDATED_ENTRY_ORDER).itemCode(UPDATED_ITEM_CODE).description(UPDATED_DESCRIPTION)
				.uomId(UPDATED_UOM_ID).baseRate(UPDATED_BASE_RATE).finalRate(UPDATED_FINAL_RATE)
				.quantity(UPDATED_QUANTITY).floorNumber(UPDATED_FLOOR_NUMBER).labourRate(UPDATED_LABOUR_RATE)
				.lbdPerformedYn(UPDATED_LBD_PERFORMED_YN).raPerformedYn(UPDATED_RA_PERFORMED_YN);

		restWorkEstimateItemMockMvc
				.perform(patch(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(),
						partialUpdatedWorkEstimateItem.getId()).contentType("application/merge-patch+json")
								.content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimateItem)))
				.andExpect(status().isOk());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeUpdate);
		WorkEstimateItem testWorkEstimateItem = workEstimateItemList.get(workEstimateItemList.size() - 1);
		assertThat(testWorkEstimateItem.getSubEstimateId()).isEqualTo(UPDATED_SUB_ESTIMATE_ID);
		assertThat(testWorkEstimateItem.getCatWorkSorItemId()).isEqualTo(UPDATED_CAT_WORK_SOR_ITEM_ID);
		assertThat(testWorkEstimateItem.getCategoryId()).isEqualTo(UPDATED_WORK_CATEGORY_ID);
		assertThat(testWorkEstimateItem.getEntryOrder()).isEqualTo(UPDATED_ENTRY_ORDER);
		assertThat(testWorkEstimateItem.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
		assertThat(testWorkEstimateItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
		assertThat(testWorkEstimateItem.getUomId()).isEqualTo(UPDATED_UOM_ID);
		assertThat(testWorkEstimateItem.getBaseRate()).isEqualByComparingTo(UPDATED_BASE_RATE);
		assertThat(testWorkEstimateItem.getFinalRate()).isEqualByComparingTo(UPDATED_FINAL_RATE);
		assertThat(testWorkEstimateItem.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
		assertThat(testWorkEstimateItem.getFloorNumber()).isEqualTo(UPDATED_FLOOR_NUMBER);
		assertThat(testWorkEstimateItem.getLabourRate()).isEqualByComparingTo(UPDATED_LABOUR_RATE);
		assertThat(testWorkEstimateItem.getLbdPerformedYn()).isEqualTo(UPDATED_LBD_PERFORMED_YN);
		assertThat(testWorkEstimateItem.getRaPerformedYn()).isEqualTo(UPDATED_RA_PERFORMED_YN);

		log.info("Test - fullUpdateWorkEstimateItemWithPatch - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void patchNonExistingWorkEstimateItem() throws Exception {
		log.info("==========================================================================");
		log.info("Test - patchNonExistingWorkEstimateItem - Start");

		int databaseSizeBeforeUpdate = workEstimateItemRepository.findAll().size();
		workEstimateItem.setId(count.incrementAndGet());

		// Create the WorkEstimateItem
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restWorkEstimateItemMockMvc
				.perform(
						patch(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItemDTO.getId())
								.contentType("application/merge-patch+json")
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeUpdate);

		log.info("Test - patchNonExistingWorkEstimateItem - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void patchWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - patchWorkEstimateNotFound - Start");

		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(patch(ENTITY_API_URL_ID, WORKESTIMATEID, subEstimate.getId(), workEstimateItemDTO.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - patchWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void patchSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - patchSubEstimateNotFound - Start");

		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(patch(ENTITY_API_URL_ID, workEstimate.getId(), SUBESTIMATEID, workEstimateItemDTO.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - patchSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void patchInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - patchInvalidWorkEstimateStatus - Start");

		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(workEstimate);

		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		restWorkEstimateItemMockMvc
				.perform(
						patch(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItemDTO.getId())
								.contentType("application/merge-patch+json")
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - patchInvalidWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void deleteWorkEstimateItem() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateItem - Start");

		workEstimateItem.setSubEstimateId(subEstimate.getId());
		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		int databaseSizeBeforeDelete = workEstimateItemRepository.findAll().size();

		// Delete the workEstimateItem
		restWorkEstimateItemMockMvc
				.perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// Validate the database contains one less item
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeDelete - 1);

		log.info("Test - deleteWorkEstimateItem - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void deleteWorkEstimateNonSORItemLBDPerformed() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateNonSORItemLBDPerformed - Start");

		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItem.setLbdPerformedYn(true);

		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		WorkEstimateItemLBD workEstimateItemLBD = new WorkEstimateItemLBD();
		workEstimateItemLBD.setLbdLength(BigDecimal.valueOf(2.0000));
		workEstimateItemLBD.setLbdBredth(BigDecimal.valueOf(2.0000));
		workEstimateItemLBD.setLbdDepth(BigDecimal.valueOf(2.0000));
		workEstimateItemLBD.setLbdNos(BigDecimal.valueOf(1.0000));
		workEstimateItemLBD.setLbdQuantity(BigDecimal.valueOf(1.0000));
		workEstimateItemLBD.setLbdTotal(BigDecimal.valueOf(8.0000));
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());

		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);

		int databaseSizeBeforeDelete = workEstimateItemRepository.findAll().size();

		// Delete the workEstimateItem
		restWorkEstimateItemMockMvc
				.perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// Validate the database contains one less item
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeDelete - 1);

		log.info("Test - deleteWorkEstimateNonSORItemLBDPerformed - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void deleteWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateNotFound - Start");

		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(delete(ENTITY_API_URL_ID, WORKESTIMATEID, subEstimate.getId(), workEstimateItem.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - deleteWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void deleteSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - deleteSubEstimateNotFound - Start");

		// Initialize the database
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), SUBESTIMATEID, workEstimateItem.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - deleteSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void deleteWorkEstimateItemNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateItemNotFound - Start");

		// setting wrong id here
		final Long ID = Long.MAX_VALUE;

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc.perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), ID))
				.andExpect(status().isNotFound());

		log.info("Test - deleteWorkEstimateItemNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void uploadNonSORItems() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - uploadNonSORItems - Start");

		File workEstimateFile = new File("../estimate-service/src/test/resources/templates/NonSORItems.xls");
		FileInputStream fileInputStream = new FileInputStream(workEstimateFile);

		MockMultipartFile fileInput = new MockMultipartFile("file", workEstimateFile.getName(), "multipart/form-data",
				fileInputStream);

		restWorkEstimateItemMockMvc
				.perform(MockMvcRequestBuilders.multipart(UPLOAD_FILE_URL, workEstimate.getId(), subEstimate.getId())
						.file(fileInput).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isCreated());

		log.info("Test - uploadNonSORItems - Start");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void uploadNonSORItemsWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - uploadNonSORItemsWorkEstimateNotFound - Start");

		File workEstimateFile = new File("../estimate-service/src/test/resources/templates/NonSORItems.xls");
		FileInputStream fileInputStream = new FileInputStream(workEstimateFile);

		MockMultipartFile fileInput = new MockMultipartFile("file", workEstimateFile.getName(), "multipart/form-data",
				fileInputStream);

		restWorkEstimateItemMockMvc
				.perform(MockMvcRequestBuilders.multipart(UPLOAD_FILE_URL, Long.MAX_VALUE, subEstimate.getId())
						.file(fileInput).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isNotFound());

		log.info("Test - uploadNonSORItemsWorkEstimateNotFound - Start");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void uploadNonSORItemsInvalidStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - uploadNonSORItemsInvalidStatus - Start");

		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(workEstimate);

		File workEstimateFile = new File("../estimate-service/src/test/resources/templates/NonSORItems.xls");
		FileInputStream fileInputStream = new FileInputStream(workEstimateFile);

		MockMultipartFile fileInput = new MockMultipartFile("file", workEstimateFile.getName(), "multipart/form-data",
				fileInputStream);

		restWorkEstimateItemMockMvc
				.perform(MockMvcRequestBuilders.multipart(UPLOAD_FILE_URL, workEstimate.getId(), subEstimate.getId())
						.file(fileInput).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - uploadNonSORItemsInvalidStatus - Start");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void uploadNonSORItemsSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - uploadNonSORItemsSubEstimateNotFound - Start");

		File workEstimateFile = new File("../estimate-service/src/test/resources/templates/NonSORItems.xls");
		FileInputStream fileInputStream = new FileInputStream(workEstimateFile);

		MockMultipartFile fileInput = new MockMultipartFile("file", workEstimateFile.getName(), "multipart/form-data",
				fileInputStream);

		restWorkEstimateItemMockMvc
				.perform(MockMvcRequestBuilders.multipart(UPLOAD_FILE_URL, workEstimate.getId(), Long.MAX_VALUE)
						.file(fileInput).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isNotFound());

		log.info("Test - uploadNonSORItemsSubEstimateNotFound - Start");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void uploadEmptyNonSORItemsFile() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - uploadEmptyNonSORItemsFile - Start");

		File workEstimateFile = new File("../estimate-service/src/test/resources/templates/BlankNonSORItems.xls");
		FileInputStream fileInputStream = new FileInputStream(workEstimateFile);

		MockMultipartFile fileInput = new MockMultipartFile("file", workEstimateFile.getName(), "multipart/form-data",
				fileInputStream);

		restWorkEstimateItemMockMvc
				.perform(MockMvcRequestBuilders.multipart(UPLOAD_FILE_URL, workEstimate.getId(), subEstimate.getId())
						.file(fileInput).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isNotFound());

		log.info("Test - uploadEmptyNonSORItemsFile - Start");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void uploadInvalidNonSORItemsFile() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - uploadInvalidNonSORItemsFile - Start");

		File workEstimateFile = new File("../estimate-service/src/test/resources/templates/InvalidNonSORItems.xls");
		FileInputStream fileInputStream = new FileInputStream(workEstimateFile);

		MockMultipartFile fileInput = new MockMultipartFile("file", workEstimateFile.getName(), "multipart/form-data",
				fileInputStream);

		restWorkEstimateItemMockMvc
				.perform(MockMvcRequestBuilders.multipart(UPLOAD_FILE_URL, workEstimate.getId(), subEstimate.getId())
						.file(fileInput).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());

		log.info("Test - uploadInvalidNonSORItemsFile - Start");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void uploadNonSORItemsFileWithFieldError() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - uploadNonSORItemsFileWithFieldError - Start");

		File workEstimateFile = new File(
				"../estimate-service/src/test/resources/templates/IncorrectFieldInNonSORItems.xls");
		FileInputStream fileInputStream = new FileInputStream(workEstimateFile);

		MockMultipartFile fileInput = new MockMultipartFile("file", workEstimateFile.getName(), "multipart/form-data",
				fileInputStream);

		restWorkEstimateItemMockMvc
				.perform(MockMvcRequestBuilders.multipart(UPLOAD_FILE_URL, workEstimate.getId(), subEstimate.getId())
						.file(fileInput).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());

		log.info("Test - uploadNonSORItemsFileWithFieldError - Start");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void saveUploadedNonSORItems() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - saveUploadedNonSORItems - Start");

		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();
		// Create the WorkEstimateItem
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		workEstimateItemDTOList.add(workEstimateItemDTO);

		WorkEstimateItem workEstimateItem2 = createEntity(em);
		WorkEstimateItemDTO workEstimateItemDTO2 = workEstimateItemMapper.toDto(workEstimateItem2);
		workEstimateItemDTO2.setFloorYn(true);
		workEstimateItemDTOList.add(workEstimateItemDTO2);

		restWorkEstimateItemMockMvc
				.perform(post(SAVE_UPLOADED_FILE_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
				.andExpect(status().isCreated());

		Integer maxEntry = workEstimateItemRepository.findMaxSOREntryOrderBySubEstimateId(subEstimate.getId());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate + 2);
		WorkEstimateItem testWorkEstimateItem = workEstimateItemList.get(workEstimateItemList.size() - 1);
		// assertThat(testWorkEstimateItem.getSubEstimateId()).isEqualTo(DEFAULT_SUB_ESTIMATE_ID);
		assertThat(testWorkEstimateItem.getEntryOrder()).isEqualTo(maxEntry);
		assertThat(testWorkEstimateItem.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
		assertThat(testWorkEstimateItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testWorkEstimateItem.getUomId()).isEqualTo(DEFAULT_UOM_ID);
		assertThat(testWorkEstimateItem.getBaseRate()).isEqualByComparingTo(DEFAULT_BASE_RATE);
		assertThat(testWorkEstimateItem.getFinalRate()).isEqualByComparingTo(DEFAULT_FINAL_RATE);
		assertThat(testWorkEstimateItem.getFloorNumber()).isEqualTo(DEFAULT_FLOOR_NUMBER);
		assertThat(testWorkEstimateItem.getLabourRate()).isEqualByComparingTo(DEFAULT_LABOUR_RATE);
		assertThat(testWorkEstimateItem.getLbdPerformedYn()).isEqualTo(DEFAULT_LBD_PERFORMED_YN);
		assertThat(testWorkEstimateItem.getRaPerformedYn()).isEqualTo(DEFAULT_RA_PERFORMED_YN);

		log.info("Test - saveUploadedNonSORItems - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void saveFileWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - saveFileWorkEstimateNotFound - Start");

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();
		workEstimateItemDTOList.add(workEstimateItemDTO);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(post(SAVE_UPLOADED_FILE_URL, WORKESTIMATEID, subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - saveFileWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void saveFileSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - saveFileSubEstimateNotFound - Start");

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();
		workEstimateItemDTOList.add(workEstimateItemDTO);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(post(SAVE_UPLOADED_FILE_URL, workEstimate.getId(), SUBESTIMATEID)
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - saveFileSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void saveFileInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - saveFileInvalidWorkEstimateStatus - Start");

		WorkEstimate tempWorkEstimate = workEstimateRepository.findById(workEstimate.getId()).get();

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		tempWorkEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimate = workEstimateRepository.saveAndFlush(tempWorkEstimate);

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();
		workEstimateItemDTOList.add(workEstimateItemDTO);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		restWorkEstimateItemMockMvc
				.perform(post(SAVE_UPLOADED_FILE_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - saveFileInvalidWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void saveFileNoInputsPassed() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - saveFileNoInputsPassed - Start");

		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		restWorkEstimateItemMockMvc
				.perform(post(SAVE_UPLOADED_FILE_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - saveFileNoInputsPassed - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void saveUploadedNonSORItemsValidation() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - saveUploadedNonSORItemsValidation - Start");

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		// Setting invalid inputs
		workEstimateItemDTO.setCatWorkSorItemId(null);
		workEstimateItemDTO.setCategoryId(null);
		workEstimateItemDTO.setBaseRate(null);

		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();
		workEstimateItemDTOList.add(workEstimateItemDTO);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		restWorkEstimateItemMockMvc
				.perform(post(SAVE_UPLOADED_FILE_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - saveUploadedNonSORItemsValidation - End");
		log.info("==========================================================================");
	}
}
