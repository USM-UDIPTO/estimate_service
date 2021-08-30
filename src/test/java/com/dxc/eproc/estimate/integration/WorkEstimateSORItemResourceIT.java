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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.EstimateServiceApplication;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.model.WorkEstimateCategory;
import com.dxc.eproc.estimate.model.WorkEstimateItem;
import com.dxc.eproc.estimate.repository.SubEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateCategoryRepository;
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
class WorkEstimateSORItemResourceIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(WorkEstimateSORItemResourceIT.class);

	private static final Long DEFAULT_SUB_ESTIMATE_ID = 1L;
	private static final Long UPDATED_SUB_ESTIMATE_ID = 2L;

	private static final Long DEFAULT_CAT_WORK_SOR_ITEM_ID = 1L;
	private static final Long UPDATED_CAT_WORK_SOR_ITEM_ID = 2L;

	private static final Long DEFAULT_WORK_CATEGORY_ID = 1L;
	private static final Long UPDATED_WORK_CATEGORY_ID = 2L;
	
	private static final String DEFAULT_CATEGORY_NAME = "category1";
	private static final String UPDATED_CATEGORY_NAME = "category2";
	
	private static final String DEFAULT_UOM_NAME = "uom_name";
	private static final String UPDATED_UOM_NAME = "uom_name_updated";

	private static final Integer DEFAULT_ENTRY_ORDER = 1;
	private static final Integer UPDATED_ENTRY_ORDER = 2;

	private static final String DEFAULT_ITEM_CODE = "AAAAAAAAAA";
	private static final String UPDATED_ITEM_CODE = "BBBBBBBBBB";

	private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
	private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

	private static final Long DEFAULT_UOM_ID = 1L;
	private static final Long UPDATED_UOM_ID = 2L;

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

	private static final String ENTITY_API_URL = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items";
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

	private static Random random = new Random();
	private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

	@Autowired
	private WorkEstimateItemRepository workEstimateItemRepository;

	@Autowired
	private WorkEstimateRepository workEstimateRepository;

	@Autowired
	private SubEstimateRepository subEstimateRepository;
	
	@Autowired
	private WorkEstimateCategoryRepository workEstimateCategoryRepository;

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
	
	private WorkEstimateCategory workEstimateCategory;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static WorkEstimateItem createEntity(EntityManager em) {
		WorkEstimateItem workEstimateItem = new WorkEstimateItem().subEstimateId(DEFAULT_SUB_ESTIMATE_ID)
				.catWorkSorItemId(DEFAULT_CAT_WORK_SOR_ITEM_ID).categoryId(DEFAULT_WORK_CATEGORY_ID)
				.categoryName(DEFAULT_CATEGORY_NAME).uomName(DEFAULT_UOM_NAME)
				.entryOrder(DEFAULT_ENTRY_ORDER).itemCode(DEFAULT_ITEM_CODE).description(DEFAULT_DESCRIPTION)
				.uomId(DEFAULT_UOM_ID).baseRate(DEFAULT_BASE_RATE).finalRate(DEFAULT_FINAL_RATE)
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
				.categoryName(UPDATED_CATEGORY_NAME).uomName(UPDATED_UOM_NAME)
				.entryOrder(UPDATED_ENTRY_ORDER).itemCode(UPDATED_ITEM_CODE).description(UPDATED_DESCRIPTION)
				.uomId(UPDATED_UOM_ID).baseRate(UPDATED_BASE_RATE).finalRate(UPDATED_FINAL_RATE)
				.quantity(UPDATED_QUANTITY).floorNumber(UPDATED_FLOOR_NUMBER).labourRate(UPDATED_LABOUR_RATE)
				.lbdPerformedYn(UPDATED_LBD_PERFORMED_YN).raPerformedYn(UPDATED_RA_PERFORMED_YN);
		return workEstimateItem;
	}

	private WorkEstimate createWorkEstimateEntity() {
		WorkEstimate workEstimate = new WorkEstimate().workEstimateNumber("7777").status(WorkEstimateStatus.DRAFT)
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

	private WorkEstimateCategory createWorkEstimateCategory() {
		WorkEstimateCategory workEstimateCategory = new WorkEstimateCategory();
		workEstimateCategory.setCategoryName("test_category");
		workEstimateCategory.setParentId(null);
		workEstimateCategory.setReferenceId(1L);
		workEstimateCategory.setCategoryCode("tc");
		workEstimateCategory.setItemYn(false);
		
		return workEstimateCategory;
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
		
		workEstimateCategory = createWorkEstimateCategory();
//		workEstimateCategory.setSubEstimateId(subEstimate.getId());
//		workEstimateCategoryRepository.saveAndFlush(workEstimateCategory);
	}

	@BeforeMethod
	public void initTest() {
		log.info("==================================================================================");
		log.info("This is executed before each Test - initTest");

		workEstimateItem = createEntity(em);
	}

	@Test(priority = 1)
	@Transactional
	void createWorkEstimateItem() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkEstimateItem - Start");

		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();
		
		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();
		// Create the WorkEstimateItem
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		
		WorkEstimateItem workEstimateItem = createEntity(em);
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		workEstimateItemDTO.setFloorYn(true);
		workEstimateItemDTOList.add(workEstimateItemDTO);

		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.queryParam("sorId", workEstimateCategory.getReferenceId().toString())
						.queryParam("sorName", workEstimateCategory.getCategoryName())
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
				.andExpect(status().isCreated());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate + 1);
		WorkEstimateItem testWorkEstimateItem = workEstimateItemList.get(workEstimateItemList.size() - 1);
		// assertThat(testWorkEstimateItem.getSubEstimateId()).isEqualTo(DEFAULT_SUB_ESTIMATE_ID);
		assertThat(testWorkEstimateItem.getCatWorkSorItemId()).isEqualTo(DEFAULT_CAT_WORK_SOR_ITEM_ID);
		assertThat(testWorkEstimateItem.getCategoryId()).isEqualTo(DEFAULT_WORK_CATEGORY_ID);
		assertThat(testWorkEstimateItem.getEntryOrder()).isEqualTo(DEFAULT_ENTRY_ORDER);
		assertThat(testWorkEstimateItem.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
		assertThat(testWorkEstimateItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testWorkEstimateItem.getUomId()).isEqualTo(DEFAULT_UOM_ID);
		assertThat(testWorkEstimateItem.getBaseRate()).isEqualByComparingTo(DEFAULT_BASE_RATE);
		assertThat(testWorkEstimateItem.getFinalRate()).isEqualByComparingTo(DEFAULT_FINAL_RATE);
		assertThat(testWorkEstimateItem.getFloorNumber()).isEqualTo(DEFAULT_FLOOR_NUMBER);
		assertThat(testWorkEstimateItem.getLabourRate()).isEqualByComparingTo(DEFAULT_LABOUR_RATE);
		assertThat(testWorkEstimateItem.getLbdPerformedYn()).isEqualTo(DEFAULT_LBD_PERFORMED_YN);
		assertThat(testWorkEstimateItem.getRaPerformedYn()).isEqualTo(DEFAULT_RA_PERFORMED_YN);

		log.info("Test - createWorkEstimateItem - End");
		log.info("==========================================================================");
	}
	
	@Test(priority = 2)
	@Transactional
	void createWorkEstimateItemWithNoFloor() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkEstimateItemWithNoFloor - Start");

		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();
		
		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();
		// Create the WorkEstimateItem
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		
		WorkEstimateItem workEstimateItem = createEntity(em);
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		workEstimateItemDTO.setFloorYn(false);
		workEstimateItemDTOList.add(workEstimateItemDTO);

		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.queryParam("sorId", workEstimateCategory.getReferenceId().toString())
						.queryParam("sorName", workEstimateCategory.getCategoryName())
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
				.andExpect(status().isCreated());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate + 1);
		WorkEstimateItem testWorkEstimateItem = workEstimateItemList.get(workEstimateItemList.size() - 1);
		assertThat(testWorkEstimateItem.getCatWorkSorItemId()).isEqualTo(DEFAULT_CAT_WORK_SOR_ITEM_ID);
		assertThat(testWorkEstimateItem.getCategoryId()).isEqualTo(DEFAULT_WORK_CATEGORY_ID);
		assertThat(testWorkEstimateItem.getEntryOrder()).isEqualTo(UPDATED_ENTRY_ORDER);
		assertThat(testWorkEstimateItem.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
		assertThat(testWorkEstimateItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testWorkEstimateItem.getUomId()).isEqualTo(DEFAULT_UOM_ID);
		assertThat(testWorkEstimateItem.getBaseRate()).isEqualByComparingTo(DEFAULT_BASE_RATE);
		assertThat(testWorkEstimateItem.getFinalRate()).isEqualByComparingTo(DEFAULT_FINAL_RATE);
		assertThat(testWorkEstimateItem.getLabourRate()).isEqualByComparingTo(DEFAULT_LABOUR_RATE);
		assertThat(testWorkEstimateItem.getLbdPerformedYn()).isEqualTo(DEFAULT_LBD_PERFORMED_YN);
		assertThat(testWorkEstimateItem.getRaPerformedYn()).isEqualTo(DEFAULT_RA_PERFORMED_YN);

		log.info("Test - createWorkEstimateItemWithNoFloor - End");
		log.info("==========================================================================");
	}

	@Test(priority = 2)
	@Transactional
	void createWorkEstimateItemWithExistingId() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkEstimateItemWithExistingId - Start");

		// Create the WorkEstimateItem with an existing ID
		workEstimateItem.setId(1L);
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();
		workEstimateItemDTOList.add(workEstimateItemDTO);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// An entity with an existing ID cannot be created, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - createWorkEstimateItemWithExistingId - End");
		log.info("==========================================================================");
	}

	@Test(priority = 3)
	@Transactional
	void postWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postWorkEstimateNotFound - Start");

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();
		workEstimateItemDTOList.add(workEstimateItemDTO);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, WORKESTIMATEID, subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.queryParam("sorId", workEstimateCategory.getReferenceId().toString())
						.queryParam("sorName", workEstimateCategory.getCategoryName())
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test(priority = 4)
	@Transactional
	void postSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postSubEstimateNotFound - Start");

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();
		workEstimateItemDTOList.add(workEstimateItemDTO);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), SUBESTIMATEID)
						.contentType(MediaType.APPLICATION_JSON)
						.queryParam("sorId", workEstimateCategory.getReferenceId().toString())
						.queryParam("sorName", workEstimateCategory.getCategoryName())
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test(priority = 5)
	@Transactional
	void postInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postInvalidWorkEstimateStatus - Start");

		WorkEstimate tempWorkEstimate = workEstimateRepository.findById(workEstimate.getId()).get();

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		tempWorkEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimate = workEstimateRepository.saveAndFlush(tempWorkEstimate);

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();
		workEstimateItemDTOList.add(workEstimateItemDTO);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.queryParam("sorId", workEstimateCategory.getReferenceId().toString())
						.queryParam("sorName", workEstimateCategory.getCategoryName())
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
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

	@Test(priority = 6)
	@Transactional
	void postNoInputsPassed() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postNoInputsPassed - Start");

		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.queryParam("sorId", workEstimateCategory.getReferenceId().toString())
						.queryParam("sorName", workEstimateCategory.getCategoryName())
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postNoInputsPassed - End");
		log.info("==========================================================================");
	}

	@Test(priority = 7)
	@Transactional
	void postValidateWorkEstimateSORItem() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postValidateWorkEstimateSORItem - Start");

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		// Setting invalid inputs
		workEstimateItemDTO.setCatWorkSorItemId(null);
		workEstimateItemDTO.setCategoryId(null);
		workEstimateItemDTO.setBaseRate(null);

		List<WorkEstimateItemDTO> workEstimateItemDTOList = new ArrayList<>();
		workEstimateItemDTOList.add(workEstimateItemDTO);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.queryParam("sorId", workEstimateCategory.getReferenceId().toString())
						.queryParam("sorName", workEstimateCategory.getCategoryName())
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOList)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postValidateWorkEstimateSORItem - End");
		log.info("==========================================================================");
	}

//	@Test(priority = 8)
	@Transactional
	void checkSubEstimateIdIsRequired() throws Exception {
		log.info("==========================================================================");
		log.info("Test - checkSubEstimateIdIsRequired - Start");

		int databaseSizeBeforeTest = workEstimateItemRepository.findAll().size();
		// set the field null
		workEstimateItem.setSubEstimateId(null);

		// Create the WorkEstimateItem, which fails.
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.queryParam("sorId", workEstimateCategory.getReferenceId().toString())
						.queryParam("sorName", workEstimateCategory.getCategoryName())
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeTest);

		log.info("Test - checkSubEstimateIdIsRequired - End");
		log.info("==========================================================================");
	}

//	@Test(priority = 9)
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
						.queryParam("sorId", workEstimateCategory.getReferenceId().toString())
						.queryParam("sorName", workEstimateCategory.getCategoryName())
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeTest);

		log.info("Test - checkItemCodeIsRequired - End");
		log.info("==========================================================================");
	}

//	@Test(priority = 10)
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
						.queryParam("sorId", workEstimateCategory.getReferenceId().toString())
						.queryParam("sorName", workEstimateCategory.getCategoryName())
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeTest);

		log.info("Test - checkDescriptionIsRequired - End");
		log.info("==========================================================================");
	}

//	@Test(priority = 11)
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
						.queryParam("sorId", workEstimateCategory.getReferenceId().toString())
						.queryParam("sorName", workEstimateCategory.getCategoryName())
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeTest);

		log.info("Test - checkUomIdIsRequired - End");
		log.info("==========================================================================");
	}

//	@Test(priority = 12)
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
						.queryParam("sorId", workEstimateCategory.getReferenceId().toString())
						.queryParam("sorName", workEstimateCategory.getCategoryName())
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeTest);

		log.info("Test - checkBaseRateIsRequired - End");
		log.info("==========================================================================");
	}
	
//	@Test(priority = 13)
	@Transactional
	void invalidSOR() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - invalidSOR - Start");

		int databaseSizeBeforeTest = workEstimateItemRepository.findAll().size();

		//setting wrong SOR name
		final String sorName = "null";
		
		// Create the WorkEstimateItem, which fails.
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		restWorkEstimateItemMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.queryParam("sorId", workEstimateCategory.getReferenceId().toString())
						.queryParam("sorName", workEstimateCategory.getCategoryName())
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeTest);
		
		log.info("Test - invalidSOR - End");
		log.info("==========================================================================");
	}
	
//	@Test(priority = 13)
	@Transactional
	void invalidSelectSOR() {
		log.info("==========================================================================");
		log.info("Test - invalidSelectSOR - Start");

		log.info("Test - invalidSelectSOR - End");
		log.info("==========================================================================");
	}

	@Test(priority = 13)
	@Transactional
	void putNewWorkEstimateItem() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putNewWorkEstimateItem - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		int databaseSizeBeforeUpdate = workEstimateItemRepository.findAll().size();

		// Update the workEstimateItem
		WorkEstimateItem updatedWorkEstimateItem = workEstimateItemRepository.findById(workEstimateItem.getId()).get();
		// Disconnect from session so that the updates on updatedWorkEstimateItem are
		// not directly saved in db
		em.detach(updatedWorkEstimateItem);
		updatedWorkEstimateItem.subEstimateId(UPDATED_SUB_ESTIMATE_ID).catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID)
				.categoryId(UPDATED_WORK_CATEGORY_ID).entryOrder(UPDATED_ENTRY_ORDER).itemCode(UPDATED_ITEM_CODE)
				.description(UPDATED_DESCRIPTION).uomId(UPDATED_UOM_ID).baseRate(UPDATED_BASE_RATE)
				.finalRate(UPDATED_FINAL_RATE).quantity(UPDATED_QUANTITY).floorNumber(UPDATED_FLOOR_NUMBER)
				.labourRate(UPDATED_LABOUR_RATE).lbdPerformedYn(UPDATED_LBD_PERFORMED_YN)
				.raPerformedYn(UPDATED_RA_PERFORMED_YN);
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

		// assertThat(testWorkEstimateItem.getSubEstimateId()).isEqualTo(DEFAULT_SUB_ESTIMATE_ID);
		assertThat(testWorkEstimateItem.getCatWorkSorItemId()).isEqualTo(DEFAULT_CAT_WORK_SOR_ITEM_ID);
		assertThat(testWorkEstimateItem.getCategoryId()).isEqualTo(DEFAULT_WORK_CATEGORY_ID);
		assertThat(testWorkEstimateItem.getEntryOrder()).isEqualTo(UPDATED_ENTRY_ORDER);
		assertThat(testWorkEstimateItem.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
		assertThat(testWorkEstimateItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
		assertThat(testWorkEstimateItem.getUomId()).isEqualTo(DEFAULT_UOM_ID);
		assertThat(testWorkEstimateItem.getBaseRate()).isEqualTo(String.format("%.4f", DEFAULT_BASE_RATE));
		assertThat(testWorkEstimateItem.getFinalRate()).isEqualTo(String.format("%.4f", DEFAULT_FINAL_RATE));
		assertThat(testWorkEstimateItem.getQuantity()).isEqualTo(String.format("%.4f", DEFAULT_QUANTITY));
		assertThat(testWorkEstimateItem.getFloorNumber()).isEqualTo(DEFAULT_FLOOR_NUMBER);
		assertThat(testWorkEstimateItem.getLabourRate()).isEqualTo(String.format("%.4f", DEFAULT_LABOUR_RATE));
		assertThat(testWorkEstimateItem.getLbdPerformedYn()).isEqualTo(DEFAULT_LBD_PERFORMED_YN);
		assertThat(testWorkEstimateItem.getRaPerformedYn()).isEqualTo(DEFAULT_RA_PERFORMED_YN);

		log.info("Test - putNewWorkEstimateItem - End");
		log.info("==========================================================================");
	}

	@Test(priority = 14)
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

	@Test(priority = 15)
	@Transactional
	void putWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putWorkEstimateNotFound - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
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

	@Test(priority = 16)
	@Transactional
	void putSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putSubEstimateNotFound - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
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

	@Test(priority = 17)
	@Transactional
	void putInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putInvalidWorkEstimateStatus - Start");

		WorkEstimate tempWorkEstimate = workEstimateRepository.findById(workEstimate.getId()).get();

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		tempWorkEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimate = workEstimateRepository.saveAndFlush(tempWorkEstimate);

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
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

	@Test(priority = 18)
	@Transactional
	void putContinueWorkEstimateSORItems() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putContinueWorkEstimateSORItems - Start");

		// remove all record from database
		workEstimateItemRepository.deleteAll();

		WorkEstimateItem workEstimateItem1 = createEntity(em);
		WorkEstimateItem workEstimateItem2 = createEntity(em);

		// Initialize the database
		workEstimateItem1.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem1);
		workEstimateItem2.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem2);

		WorkEstimateItemDTO workEstimateItemDTO1 = workEstimateItemMapper.toDto(workEstimateItem1);
		WorkEstimateItemDTO workEstimateItemDTO2 = workEstimateItemMapper.toDto(workEstimateItem2);

		// updating entry order
		workEstimateItemDTO1.setEntryOrder(UPDATED_ENTRY_ORDER);
		workEstimateItemDTO2.setEntryOrder(UPDATED_ENTRY_ORDER);

		List<WorkEstimateItemDTO> workEstimateItemDTOs = new ArrayList<>();
		workEstimateItemDTOs.add(workEstimateItemDTO1);
		workEstimateItemDTOs.add(workEstimateItemDTO2);

		restWorkEstimateItemMockMvc.perform(put(ENTITY_API_URL + "-continue", workEstimate.getId(), subEstimate.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOs))).andExpect(status().isOk());

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		WorkEstimateItem testWorkEstimateItem = workEstimateItemList.get(workEstimateItemList.size() - 1);

		Assert.assertEquals(testWorkEstimateItem.getId(), workEstimateItem2.getId());
		// assertThat(testWorkEstimateItem.getSubEstimateId()).isEqualTo(DEFAULT_SUB_ESTIMATE_ID);
		assertThat(testWorkEstimateItem.getCatWorkSorItemId()).isEqualTo(DEFAULT_CAT_WORK_SOR_ITEM_ID);
		assertThat(testWorkEstimateItem.getCategoryId()).isEqualTo(DEFAULT_WORK_CATEGORY_ID);
		assertThat(testWorkEstimateItem.getEntryOrder()).isEqualTo(UPDATED_ENTRY_ORDER);
		assertThat(testWorkEstimateItem.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
		assertThat(testWorkEstimateItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testWorkEstimateItem.getUomId()).isEqualTo(DEFAULT_UOM_ID);
		assertThat(testWorkEstimateItem.getBaseRate()).isEqualTo(String.format("%.4f", DEFAULT_BASE_RATE));
		assertThat(testWorkEstimateItem.getFinalRate()).isEqualTo(String.format("%.4f", DEFAULT_FINAL_RATE));
		assertThat(testWorkEstimateItem.getQuantity()).isEqualTo(String.format("%.4f", DEFAULT_QUANTITY));
		assertThat(testWorkEstimateItem.getFloorNumber()).isEqualTo(DEFAULT_FLOOR_NUMBER);
		assertThat(testWorkEstimateItem.getLabourRate()).isEqualTo(String.format("%.4f", DEFAULT_LABOUR_RATE));
		assertThat(testWorkEstimateItem.getLbdPerformedYn()).isEqualTo(DEFAULT_LBD_PERFORMED_YN);
		assertThat(testWorkEstimateItem.getRaPerformedYn()).isEqualTo(DEFAULT_RA_PERFORMED_YN);

		log.info("Test - putContinueWorkEstimateSORItems - End");
		log.info("==========================================================================");
	}

	@Test(priority = 19)
	@Transactional
	void putContinueWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putContinueWorkEstimateNotFound - Start");

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItem workEstimateItem1 = createEntity(em);
		WorkEstimateItem workEstimateItem2 = createEntity(em);

		// Initialize the database
		workEstimateItem1.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem1);
		workEstimateItem2.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem2);

		WorkEstimateItemDTO workEstimateItemDTO1 = workEstimateItemMapper.toDto(workEstimateItem1);
		WorkEstimateItemDTO workEstimateItemDTO2 = workEstimateItemMapper.toDto(workEstimateItem2);

		List<WorkEstimateItemDTO> workEstimateItemDTOs = new ArrayList<>();
		workEstimateItemDTOs.add(workEstimateItemDTO1);
		workEstimateItemDTOs.add(workEstimateItemDTO2);

		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL + "-continue", WORKESTIMATEID, subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOs)))
				.andExpect(status().isNotFound());

		log.info("Test - putContinueWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test(priority = 20)
	@Transactional
	void putContinueSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putContinueSubEstimateNotFound - Start");

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItem workEstimateItem1 = createEntity(em);
		WorkEstimateItem workEstimateItem2 = createEntity(em);

		// Initialize the database
		workEstimateItem1.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem1);
		workEstimateItem2.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem2);

		WorkEstimateItemDTO workEstimateItemDTO1 = workEstimateItemMapper.toDto(workEstimateItem1);
		WorkEstimateItemDTO workEstimateItemDTO2 = workEstimateItemMapper.toDto(workEstimateItem2);

		List<WorkEstimateItemDTO> workEstimateItemDTOs = new ArrayList<>();
		workEstimateItemDTOs.add(workEstimateItemDTO1);
		workEstimateItemDTOs.add(workEstimateItemDTO2);

		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL + "-continue", workEstimate.getId(), SUBESTIMATEID)
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOs)))
				.andExpect(status().isNotFound());

		log.info("Test - putContinueSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test(priority = 21)
	@Transactional
	void putContinueInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putContinueInvalidWorkEstimateStatus - Start");

		WorkEstimate tempWorkEstimate = workEstimateRepository.findById(workEstimate.getId()).get();

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		tempWorkEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimate = workEstimateRepository.saveAndFlush(tempWorkEstimate);

		WorkEstimateItem workEstimateItem1 = createEntity(em);
		WorkEstimateItem workEstimateItem2 = createEntity(em);

		// Initialize the database
		workEstimateItem1.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem1);
		workEstimateItem2.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem2);

		WorkEstimateItemDTO workEstimateItemDTO1 = workEstimateItemMapper.toDto(workEstimateItem1);
		WorkEstimateItemDTO workEstimateItemDTO2 = workEstimateItemMapper.toDto(workEstimateItem2);

		List<WorkEstimateItemDTO> workEstimateItemDTOs = new ArrayList<>();
		workEstimateItemDTOs.add(workEstimateItemDTO1);
		workEstimateItemDTOs.add(workEstimateItemDTO2);

		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL + "-continue", workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOs)))
				.andExpect(status().isBadRequest());

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - putContinueInvalidWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

	@Test(priority = 22)
	@Transactional
	void putContinueWorkEstimateItemNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putContinueWorkEstimateItemNotFound - Start");

		WorkEstimateItem workEstimateItem1 = createEntity(em);
		WorkEstimateItem workEstimateItem2 = createEntity(em);

		WorkEstimateItemDTO workEstimateItemDTO1 = workEstimateItemMapper.toDto(workEstimateItem1);
		WorkEstimateItemDTO workEstimateItemDTO2 = workEstimateItemMapper.toDto(workEstimateItem2);

		List<WorkEstimateItemDTO> workEstimateItemDTOs = new ArrayList<>();
		workEstimateItemDTOs.add(workEstimateItemDTO1);
		workEstimateItemDTOs.add(workEstimateItemDTO2);

		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL + "-continue", workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOs)))
				.andExpect(status().isBadRequest());

		log.info("Test - putContinueWorkEstimateItemNotFound - End");
		log.info("==========================================================================");
	}

	@Test(priority = 23)
	@Transactional
	void putContinueEntryOrderIsNull() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putContinueEntryOrderIsNull - Start");

		WorkEstimateItem workEstimateItem1 = createEntity(em);
		WorkEstimateItem workEstimateItem2 = createEntity(em);

		workEstimateItem1.setEntryOrder(null);
		workEstimateItem2.setEntryOrder(null);

		WorkEstimateItemDTO workEstimateItemDTO1 = workEstimateItemMapper.toDto(workEstimateItem1);
		WorkEstimateItemDTO workEstimateItemDTO2 = workEstimateItemMapper.toDto(workEstimateItem2);

		List<WorkEstimateItemDTO> workEstimateItemDTOs = new ArrayList<>();
		workEstimateItemDTOs.add(workEstimateItemDTO1);
		workEstimateItemDTOs.add(workEstimateItemDTO2);

		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL + "-continue", workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOs)))
				.andExpect(status().isBadRequest());

		log.info("Test - putContinueEntryOrderIsNull - End");
		log.info("==========================================================================");
	}

	@Test(priority = 24)
	@Transactional
	void putContinueInvalidSOREstimateItems() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putContinueInvalidSOREstimateItems - End");

		// creating three workEstimateItems here
		WorkEstimateItem workEstimateItem1 = createEntity(em);
		WorkEstimateItem workEstimateItem2 = createEntity(em);
		WorkEstimateItem workEstimateItem3 = createEntity(em);

		// Initialize the database
		workEstimateItem1.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem1);
		workEstimateItem2.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem2);
		workEstimateItem3.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem3);

		WorkEstimateItemDTO workEstimateItemDTO1 = workEstimateItemMapper.toDto(workEstimateItem1);
		WorkEstimateItemDTO workEstimateItemDTO2 = workEstimateItemMapper.toDto(workEstimateItem2);

		// Here adding only two workEstimateItems but created three
		List<WorkEstimateItemDTO> workEstimateItemDTOs = new ArrayList<>();
		workEstimateItemDTOs.add(workEstimateItemDTO1);
		workEstimateItemDTOs.add(workEstimateItemDTO2);

		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL + "-continue", workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOs)))
				.andExpect(status().isBadRequest());

		log.info("Test - putContinueInvalidSOREstimateItems - End");
		log.info("==========================================================================");
	}

	@Test(priority = 25)
	@Transactional
	void putContinueNoSORItemsSaved() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putContinueNoSORItemsSaved - Start");

		List<WorkEstimateItemDTO> workEstimateItemDTOs = new ArrayList<>();

		restWorkEstimateItemMockMvc.perform(put(ENTITY_API_URL + "-continue", workEstimate.getId(), subEstimate.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTOs))).andExpect(status().isOk());

		log.info("Test - putContinueNoSORItemsSaved - End");
		log.info("==========================================================================");
	}

	@Test(priority = 26)
	@Transactional
	void putModifyQuantityWorkEstimateSORItems() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putModifyQuantityWorkEstimateSORItems - Start");

		WorkEstimateItem workEstimateItem = createEntity(em);

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL_ID + "/modify-quantity", workEstimate.getId(), subEstimate.getId(),
						workEstimateItemDTO.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isOk());

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		WorkEstimateItem testWorkEstimateItem = workEstimateItemList.get(workEstimateItemList.size() - 1);

		Assert.assertEquals(testWorkEstimateItem.getId(), workEstimateItemDTO.getId());
		// assertThat(testWorkEstimateItem.getSubEstimateId()).isEqualTo(DEFAULT_SUB_ESTIMATE_ID);
		assertThat(testWorkEstimateItem.getCatWorkSorItemId()).isEqualTo(DEFAULT_CAT_WORK_SOR_ITEM_ID);
		assertThat(testWorkEstimateItem.getCategoryId()).isEqualTo(DEFAULT_WORK_CATEGORY_ID);
		assertThat(testWorkEstimateItem.getEntryOrder()).isEqualTo(DEFAULT_ENTRY_ORDER);
		assertThat(testWorkEstimateItem.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
		assertThat(testWorkEstimateItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testWorkEstimateItem.getUomId()).isEqualTo(DEFAULT_UOM_ID);
		assertThat(testWorkEstimateItem.getBaseRate()).isEqualTo(String.format("%.4f", DEFAULT_BASE_RATE));
		assertThat(testWorkEstimateItem.getFinalRate()).isEqualTo(String.format("%.4f", DEFAULT_FINAL_RATE));
		assertThat(testWorkEstimateItem.getQuantity()).isEqualTo(String.format("%.4f", DEFAULT_QUANTITY));
		assertThat(testWorkEstimateItem.getFloorNumber()).isEqualTo(DEFAULT_FLOOR_NUMBER);
		assertThat(testWorkEstimateItem.getLabourRate()).isEqualTo(String.format("%.4f", DEFAULT_LABOUR_RATE));
		assertThat(testWorkEstimateItem.getLbdPerformedYn()).isEqualTo(DEFAULT_LBD_PERFORMED_YN);
		assertThat(testWorkEstimateItem.getRaPerformedYn()).isEqualTo(DEFAULT_RA_PERFORMED_YN);

		log.info("Test - putModifyQuantityWorkEstimateSORItems - End");
		log.info("==========================================================================");
	}

	@Test(priority = 27)
	@Transactional
	void putModifyWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putModifyWorkEstimateNotFound - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL_ID + "/modify-quantity", WORKESTIMATEID, subEstimate.getId(),
						workEstimateItemDTO.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - putModifyWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test(priority = 28)
	@Transactional
	void putModifySubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putModifySubEstimateNotFound - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL_ID + "/modify-quantity", workEstimate.getId(), SUBESTIMATEID,
						workEstimateItemDTO.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - putModifySubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test(priority = 29)
	@Transactional
	void putModifyInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putModifyInvalidWorkEstimateStatus - Start");

		WorkEstimate tempWorkEstimate = workEstimateRepository.findById(workEstimate.getId()).get();

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		tempWorkEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimate = workEstimateRepository.saveAndFlush(tempWorkEstimate);

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL_ID + "/modify-quantity", workEstimate.getId(), subEstimate.getId(),
						workEstimateItemDTO.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - putModifyInvalidWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

	@Test(priority = 30)
	@Transactional
	void putModifyWorkEstimateItemNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putModifyWorkEstimateItemNotFound - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// setting wrong id here
		final Long WORKESTIMATEITEMID = Long.MAX_VALUE;

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL_ID + "/modify-quantity", workEstimate.getId(), subEstimate.getId(),
						WORKESTIMATEITEMID).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - putModifyWorkEstimateItemNotFound - End");
		log.info("==========================================================================");
	}

	@Test(priority = 31)
	@Transactional
	void putModifyWorkEstimateItemValidationError() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putModifyWorkEstimateItemValidationError - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		workEstimateItemDTO.setQuantity(null);

		int databaseSizeBeforeCreate = workEstimateItemRepository.findAll().size();

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(put(ENTITY_API_URL_ID + "/modify-quantity", workEstimate.getId(), subEstimate.getId(),
						workEstimateItemDTO.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkEstimateItem in the database
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - putModifyWorkEstimateItemValidationError - End");
		log.info("==========================================================================");
	}

	@Test(priority = 32)
	@Transactional
	void partialUpdateWorkEstimateItemWithPatch() throws Exception {
		log.info("==========================================================================");
		log.info("Test - partialUpdateWorkEstimateItemWithPatch - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
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
		// assertThat(testWorkEstimateItem.getSubEstimateId()).isEqualTo(UPDATED_SUB_ESTIMATE_ID);
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

	@Test(priority = 33)
	@Transactional
	void fullUpdateWorkEstimateItemWithPatch() throws Exception {
		log.info("==========================================================================");
		log.info("Test - fullUpdateWorkEstimateItemWithPatch - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
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
		// assertThat(testWorkEstimateItem.getSubEstimateId()).isEqualTo(UPDATED_SUB_ESTIMATE_ID);
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

	@Test(priority = 34)
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

	@Test(priority = 35)
	@Transactional
	void patchWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - patchWorkEstimateNotFound - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
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

	@Test(priority = 36)
	@Transactional
	void patchSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - patchSubEstimateNotFound - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
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

	@Test(priority = 37)
	@Transactional
	void patchInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - patchInvalidWorkEstimateStatus - Start");

		WorkEstimate tempWorkEstimate = workEstimateRepository.findById(workEstimate.getId()).get();

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		tempWorkEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimate = workEstimateRepository.saveAndFlush(tempWorkEstimate);

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
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

	@Test(priority = 38)
	@Transactional
	void getAllWorkEstimateSORItems() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkEstimateSORItems - Start");

		// deleting all data from database
		workEstimateItemRepository.deleteAll();

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// Get all the workEstimateItemList
		MvcResult mvcResult = restWorkEstimateItemMockMvc
				.perform(get(ENTITY_API_URL + "-with-pagination", workEstimate.getId(), subEstimate.getId()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andReturn();

		SubEstimateResponseDTO subEstimateResponseDTO = objectMapper
				.readValue(mvcResult.getResponse().getContentAsString(), SubEstimateResponseDTO.class);

		List<WorkEstimateItemDTO> sorItems = subEstimateResponseDTO.getItems();
		WorkEstimateItemDTO workEstimateItemDTO = sorItems.get(0);

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		WorkEstimateItem workEstimateItem = workEstimateItemList.get(0);

		Assert.assertEquals(workEstimateItemDTO.getId(), workEstimateItem.getId());
		Assert.assertEquals(workEstimateItemDTO.getSubEstimateId(), workEstimateItem.getSubEstimateId());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getId(), workEstimateItem.getId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate().toString(),
				String.format("%.4f", workEstimateItem.getBaseRate()));
		Assert.assertEquals(workEstimateItemDTO.getFinalRate().toString(),
				String.format("%.4f", workEstimateItem.getFinalRate()));
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getLabourRate().toString(),
				String.format("%.4f", workEstimateItem.getLabourRate()));
		Assert.assertEquals(workEstimateItemDTO.getLbdPerformedYn(), workEstimateItem.getLbdPerformedYn());
		Assert.assertEquals(workEstimateItemDTO.getRaPerformedYn(), workEstimateItem.getRaPerformedYn());

		log.info("Test - getAllWorkEstimateSORItems - End");
		log.info("==========================================================================");
	}

	@Test(priority = 39)
	@Transactional
	void getAllSORWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getAllSORWorkEstimateNotFound - Start");

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(get(ENTITY_API_URL + "-with-pagination", WORKESTIMATEID, subEstimate.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - getAllSORWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test(priority = 40)
	@Transactional
	void getAllSORSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getAllSORSubEstimateNotFound - Start");

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkEstimateItemMockMvc
				.perform(get(ENTITY_API_URL + "-with-pagination", workEstimate.getId(), SUBESTIMATEID))
				.andExpect(status().isNotFound());

		log.info("Test - getAllSORSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test(priority = 41)
	@Transactional
	void getAllWorkEstimateItems() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkEstimateItems - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// Get all the workEstimateItemList
		MvcResult mvcResult = restWorkEstimateItemMockMvc
				.perform(get(ENTITY_API_URL + "?sort=id,desc", workEstimate.getId(), subEstimate.getId()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andReturn();

		SubEstimateResponseDTO workEstimateResponseDTO = objectMapper
				.readValue(mvcResult.getResponse().getContentAsString(), SubEstimateResponseDTO.class);

		List<WorkEstimateItemDTO> sorItems = workEstimateResponseDTO.getItems();
		WorkEstimateItemDTO workEstimateItemDTO = sorItems.get(0);

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		WorkEstimateItem workEstimateItem = workEstimateItemList.get(0);

		Assert.assertEquals(workEstimateItemDTO.getId(), workEstimateItem.getId());
		Assert.assertEquals(workEstimateItemDTO.getSubEstimateId(), workEstimateItem.getSubEstimateId());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getId(), workEstimateItem.getId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate().toString(),
				String.format("%.4f", workEstimateItem.getBaseRate()));
		Assert.assertEquals(workEstimateItemDTO.getFinalRate().toString(),
				String.format("%.4f", workEstimateItem.getFinalRate()));
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getLabourRate().toString(),
				String.format("%.4f", workEstimateItem.getLabourRate()));
		Assert.assertEquals(workEstimateItemDTO.getLbdPerformedYn(), workEstimateItem.getLbdPerformedYn());
		Assert.assertEquals(workEstimateItemDTO.getRaPerformedYn(), workEstimateItem.getRaPerformedYn());

		log.info("Test - getAllWorkEstimateItems - End");
		log.info("==========================================================================");
	}

	@Test(priority = 42)
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

	@Test(priority = 43)
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

	@Test(priority = 44)
	@Transactional
	void getWorkEstimateItem() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkEstimateItem - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem);

		// Get the workEstimateItem
		restWorkEstimateItemMockMvc
				.perform(get(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(workEstimateItem.getId().intValue()))
				// .andExpect(jsonPath("$.subEstimateId").value(DEFAULT_SUB_ESTIMATE_ID.intValue()))
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

	@Test(priority = 45)
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

	@Test(priority = 46)
	@Transactional
	void getWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkEstimateNotFound - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
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

	@Test(priority = 47)
	@Transactional
	void getSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getSubEstimateNotFound - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
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

	@Test(priority = 48)
	@Transactional
	void deleteWorkEstimateItem() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateItem - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
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

	@Test(priority = 49)
	@Transactional
	void deleteWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateNotFound - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
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

	@Test(priority = 50)
	@Transactional
	void deleteSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - deleteSubEstimateNotFound - Start");

		// Initialize the database
		workEstimateItem.setSubEstimateId(subEstimate.getId());
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

	@Test(priority = 51)
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

}
