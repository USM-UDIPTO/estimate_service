package com.dxc.eproc.estimate.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.controller.WorkEstimateRateAnalysisController;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.model.WorkEstimateItem;
import com.dxc.eproc.estimate.model.WorkEstimateRateAnalysis;
import com.dxc.eproc.estimate.repository.SubEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateItemRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRateAnalysisRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRateAnalysisDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateRateAnalysisMapper;

/**
 * Integration tests for the {@link WorkEstimateRateAnalysisController} REST controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class WorkEstimateRateAnalysisResourceIT extends AbstractTestNGSpringContextTests {

    private static final Long DEFAULT_WORK_ESTIMATE_ID = 1L;
    private static final Long UPDATED_WORK_ESTIMATE_ID = 2L;

    private static final Long DEFAULT_AREA_WEIGHTAGE_MASTER_ID = 1L;
    private static final Long UPDATED_AREA_WEIGHTAGE_MASTER_ID = 2L;

    private static final Long DEFAULT_AREA_WEIGHTAGE_CIRCLE_ID = 1L;
    private static final Long UPDATED_AREA_WEIGHTAGE_CIRCLE_ID = 2L;

    private static final BigDecimal DEFAULT_AREA_WEIGHTAGE_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AREA_WEIGHTAGE_PERCENTAGE = new BigDecimal(2);

    private static final String DEFAULT_SOR_FINANCIAL_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_SOR_FINANCIAL_YEAR = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BASIC_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASIC_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NET_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_NET_RATE = new BigDecimal(2);

    private static final Long DEFAULT_FLOOR_NO = 1L;
    private static final Long UPDATED_FLOOR_NO = 2L;

    private static final BigDecimal DEFAULT_CONTRACTOR_PROFIT_PERCENTAGE = new BigDecimal(0);
    private static final BigDecimal UPDATED_CONTRACTOR_PROFIT_PERCENTAGE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_OVERHEAD_PERCENTAGE = new BigDecimal(0);
    private static final BigDecimal UPDATED_OVERHEAD_PERCENTAGE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TAX_PERCENTAGE = new BigDecimal(0);
    private static final BigDecimal UPDATED_TAX_PERCENTAGE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_LOCALITY_ALLOWANCE = new BigDecimal(0);
    private static final BigDecimal UPDATED_LOCALITY_ALLOWANCE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_EMPLOYEES_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_EMPLOYEES_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CONTINGENCIES = new BigDecimal(0);
    private static final BigDecimal UPDATED_CONTINGENCIES = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TRANSPORTATION_COST = new BigDecimal(0);
    private static final BigDecimal UPDATED_TRANSPORTATION_COST = new BigDecimal(1);

    private static final BigDecimal DEFAULT_SERVICE_TAX = new BigDecimal(0);
    private static final BigDecimal UPDATED_SERVICE_TAX = new BigDecimal(1);

    private static final BigDecimal DEFAULT_PROVIDENT_FUND_CHARGES = new BigDecimal(0);
    private static final BigDecimal UPDATED_PROVIDENT_FUND_CHARGES = new BigDecimal(1);

    private static final BigDecimal DEFAULT_ESI_CHARGES = new BigDecimal(0);
    private static final BigDecimal UPDATED_ESI_CHARGES = new BigDecimal(1);

    private static final BigDecimal DEFAULT_IDC_CHARGES = new BigDecimal(0);
    private static final BigDecimal UPDATED_IDC_CHARGES = new BigDecimal(1);

    private static final BigDecimal DEFAULT_WATCH_AND_WARD_COST = new BigDecimal(0);
    private static final BigDecimal UPDATED_WATCH_AND_WARD_COST = new BigDecimal(1);

    private static final BigDecimal DEFAULT_INSURANCE_COST = new BigDecimal(0);
    private static final BigDecimal UPDATED_INSURANCE_COST = new BigDecimal(1);

    private static final BigDecimal DEFAULT_STATUTORY_CHARGES = new BigDecimal(1);
    private static final BigDecimal UPDATED_STATUTORY_CHARGES = new BigDecimal(2);

    private static final BigDecimal DEFAULT_COMPENSATION_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COMPENSATION_COST = new BigDecimal(2);

    private static final Boolean DEFAULT_RA_COMPLETED_YN = false;
    private static final Boolean UPDATED_RA_COMPLETED_YN = true;

    private static final String ENTITY_API_URL = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-rate-analyses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkEstimateRateAnalysisRepository workEstimateRateAnalysisRepository;
    
    @Autowired
    private WorkEstimateRepository workEstimateRepository;
    
    @Autowired
    private SubEstimateRepository subEstimateRepository;
    
    @Autowired
    private WorkEstimateItemRepository workEstimateItemRepository;

    @Autowired
    private WorkEstimateRateAnalysisMapper workEstimateRateAnalysisMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEstimateRateAnalysisMockMvc;

    private WorkEstimateRateAnalysis workEstimateRateAnalysis;

	private WorkEstimate workEstimate;

	private SubEstimate subEstimate;
	
	private WorkEstimateItem workEstimateItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEstimateRateAnalysis createEntity(EntityManager em) {
        WorkEstimateRateAnalysis workEstimateRateAnalysis = new WorkEstimateRateAnalysis()
            .workEstimateId(DEFAULT_WORK_ESTIMATE_ID)
            .areaWeightageMasterId(DEFAULT_AREA_WEIGHTAGE_MASTER_ID)
            .areaWeightageCircleId(DEFAULT_AREA_WEIGHTAGE_CIRCLE_ID)
            .areaWeightagePercentage(DEFAULT_AREA_WEIGHTAGE_PERCENTAGE)
            .sorFinancialYear(DEFAULT_SOR_FINANCIAL_YEAR)
            .basicRate(DEFAULT_BASIC_RATE)
            .netRate(DEFAULT_NET_RATE)
            .floorNo(DEFAULT_FLOOR_NO)
            .contractorProfitPercentage(DEFAULT_CONTRACTOR_PROFIT_PERCENTAGE)
            .overheadPercentage(DEFAULT_OVERHEAD_PERCENTAGE)
            .taxPercentage(DEFAULT_TAX_PERCENTAGE)
            .localityAllowance(DEFAULT_LOCALITY_ALLOWANCE)
            .employeesCost(DEFAULT_EMPLOYEES_COST)
            .contingencies(DEFAULT_CONTINGENCIES)
            .transportationCost(DEFAULT_TRANSPORTATION_COST)
            .serviceTax(DEFAULT_SERVICE_TAX)
            .providentFundCharges(DEFAULT_PROVIDENT_FUND_CHARGES)
            .esiCharges(DEFAULT_ESI_CHARGES)
            .idcCharges(DEFAULT_IDC_CHARGES)
            .watchAndWardCost(DEFAULT_WATCH_AND_WARD_COST)
            .insuranceCost(DEFAULT_INSURANCE_COST)
            .statutoryCharges(DEFAULT_STATUTORY_CHARGES)
            .compensationCost(DEFAULT_COMPENSATION_COST)
            .raCompletedYn(DEFAULT_RA_COMPLETED_YN);
        return workEstimateRateAnalysis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEstimateRateAnalysis createUpdatedEntity(EntityManager em) {
        WorkEstimateRateAnalysis workEstimateRateAnalysis = new WorkEstimateRateAnalysis()
            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
            .areaWeightageMasterId(UPDATED_AREA_WEIGHTAGE_MASTER_ID)
            .areaWeightageCircleId(UPDATED_AREA_WEIGHTAGE_CIRCLE_ID)
            .areaWeightagePercentage(UPDATED_AREA_WEIGHTAGE_PERCENTAGE)
            .sorFinancialYear(UPDATED_SOR_FINANCIAL_YEAR)
            .basicRate(UPDATED_BASIC_RATE)
            .netRate(UPDATED_NET_RATE)
            .floorNo(UPDATED_FLOOR_NO)
            .contractorProfitPercentage(UPDATED_CONTRACTOR_PROFIT_PERCENTAGE)
            .overheadPercentage(UPDATED_OVERHEAD_PERCENTAGE)
            .taxPercentage(UPDATED_TAX_PERCENTAGE)
            .localityAllowance(UPDATED_LOCALITY_ALLOWANCE)
            .employeesCost(UPDATED_EMPLOYEES_COST)
            .contingencies(UPDATED_CONTINGENCIES)
            .transportationCost(UPDATED_TRANSPORTATION_COST)
            .serviceTax(UPDATED_SERVICE_TAX)
            .providentFundCharges(UPDATED_PROVIDENT_FUND_CHARGES)
            .esiCharges(UPDATED_ESI_CHARGES)
            .idcCharges(UPDATED_IDC_CHARGES)
            .watchAndWardCost(UPDATED_WATCH_AND_WARD_COST)
            .insuranceCost(UPDATED_INSURANCE_COST)
            .statutoryCharges(UPDATED_STATUTORY_CHARGES)
            .compensationCost(UPDATED_COMPENSATION_COST)
            .raCompletedYn(UPDATED_RA_COMPLETED_YN);
        return workEstimateRateAnalysis;
    }
    
	private WorkEstimate createWorkEstimateEntity() {
		WorkEstimate workEstimate = new WorkEstimate().workEstimateNumber("1111").status(WorkEstimateStatus.DRAFT)
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
	
	private WorkEstimateItem createWorkEstimateItemEntity() {
		WorkEstimateItem workEstimateItem = new WorkEstimateItem();
		workEstimateItem.setBaseRate(BigDecimal.TEN);
		workEstimateItem.setCatWorkSorItemId(1L);
		workEstimateItem.setUomId(1L);
		workEstimateItem.setUomName("uom_test");
		workEstimateItem.setItemCode("item_code_test");
		workEstimateItem.setDescription("desc_test");
		return workEstimateItem;
	}
    
	@BeforeClass
	public void setUp() {
		workEstimate = createWorkEstimateEntity();
		subEstimate = createSubEstimateEntity();
		workEstimateItem = createWorkEstimateItemEntity();

		// Initializing work estimate.
		workEstimateRepository.saveAndFlush(workEstimate);

		// Initializing sub estimate.
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);
		
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem);
	}

    @BeforeMethod
    public void initTest() {
        workEstimateRateAnalysis = createEntity(em);
        workEstimateRateAnalysis.setWorkEstimateItemId(workEstimateItem.getId());
    }

    @Test
    @Transactional
    void createWorkEstimateRateAnalysis() throws Exception {
        int databaseSizeBeforeCreate = workEstimateRateAnalysisRepository.findAll().size();
        // Create the WorkEstimateRateAnalysis
        WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO = workEstimateRateAnalysisMapper.toDto(workEstimateRateAnalysis);
        restWorkEstimateRateAnalysisMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRateAnalysisDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkEstimateRateAnalysis in the database
        List<WorkEstimateRateAnalysis> workEstimateRateAnalysisList = workEstimateRateAnalysisRepository.findAll();
        assertThat(workEstimateRateAnalysisList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEstimateRateAnalysis testWorkEstimateRateAnalysis = workEstimateRateAnalysisList.get(workEstimateRateAnalysisList.size() - 1);
        assertThat(testWorkEstimateRateAnalysis.getWorkEstimateId()).isEqualTo(DEFAULT_WORK_ESTIMATE_ID);
        assertThat(testWorkEstimateRateAnalysis.getAreaWeightageMasterId()).isEqualTo(DEFAULT_AREA_WEIGHTAGE_MASTER_ID);
        assertThat(testWorkEstimateRateAnalysis.getAreaWeightageCircleId()).isEqualTo(DEFAULT_AREA_WEIGHTAGE_CIRCLE_ID);
        assertThat(testWorkEstimateRateAnalysis.getAreaWeightagePercentage()).isEqualByComparingTo(DEFAULT_AREA_WEIGHTAGE_PERCENTAGE);
        assertThat(testWorkEstimateRateAnalysis.getSorFinancialYear()).isEqualTo(DEFAULT_SOR_FINANCIAL_YEAR);
        assertThat(testWorkEstimateRateAnalysis.getBasicRate()).isEqualByComparingTo(DEFAULT_BASIC_RATE);
        assertThat(testWorkEstimateRateAnalysis.getNetRate()).isEqualByComparingTo(DEFAULT_NET_RATE);
        assertThat(testWorkEstimateRateAnalysis.getFloorNo()).isEqualTo(DEFAULT_FLOOR_NO);
        assertThat(testWorkEstimateRateAnalysis.getContractorProfitPercentage()).isEqualByComparingTo(DEFAULT_CONTRACTOR_PROFIT_PERCENTAGE);
        assertThat(testWorkEstimateRateAnalysis.getOverheadPercentage()).isEqualByComparingTo(DEFAULT_OVERHEAD_PERCENTAGE);
        assertThat(testWorkEstimateRateAnalysis.getTaxPercentage()).isEqualByComparingTo(DEFAULT_TAX_PERCENTAGE);
        assertThat(testWorkEstimateRateAnalysis.getLocalityAllowance()).isEqualByComparingTo(DEFAULT_LOCALITY_ALLOWANCE);
        assertThat(testWorkEstimateRateAnalysis.getEmployeesCost()).isEqualByComparingTo(DEFAULT_EMPLOYEES_COST);
        assertThat(testWorkEstimateRateAnalysis.getContingencies()).isEqualByComparingTo(DEFAULT_CONTINGENCIES);
        assertThat(testWorkEstimateRateAnalysis.getTransportationCost()).isEqualByComparingTo(DEFAULT_TRANSPORTATION_COST);
        assertThat(testWorkEstimateRateAnalysis.getServiceTax()).isEqualByComparingTo(DEFAULT_SERVICE_TAX);
        assertThat(testWorkEstimateRateAnalysis.getProvidentFundCharges()).isEqualByComparingTo(DEFAULT_PROVIDENT_FUND_CHARGES);
        assertThat(testWorkEstimateRateAnalysis.getEsiCharges()).isEqualByComparingTo(DEFAULT_ESI_CHARGES);
        assertThat(testWorkEstimateRateAnalysis.getIdcCharges()).isEqualByComparingTo(DEFAULT_IDC_CHARGES);
        assertThat(testWorkEstimateRateAnalysis.getWatchAndWardCost()).isEqualByComparingTo(DEFAULT_WATCH_AND_WARD_COST);
        assertThat(testWorkEstimateRateAnalysis.getInsuranceCost()).isEqualByComparingTo(DEFAULT_INSURANCE_COST);
        assertThat(testWorkEstimateRateAnalysis.getStatutoryCharges()).isEqualByComparingTo(DEFAULT_STATUTORY_CHARGES);
        assertThat(testWorkEstimateRateAnalysis.getCompensationCost()).isEqualByComparingTo(DEFAULT_COMPENSATION_COST);
        assertThat(testWorkEstimateRateAnalysis.getRaCompletedYn()).isEqualTo(DEFAULT_RA_COMPLETED_YN);
    }

    @Test
    @Transactional
    void checkBasicRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateRateAnalysisRepository.findAll().size();
        // set the field null
        workEstimateRateAnalysis.setBasicRate(null);

        // Create the WorkEstimateRateAnalysis, which fails.
        WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO = workEstimateRateAnalysisMapper.toDto(workEstimateRateAnalysis);

        restWorkEstimateRateAnalysisMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRateAnalysisDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateRateAnalysis> workEstimateRateAnalysisList = workEstimateRateAnalysisRepository.findAll();
        assertThat(workEstimateRateAnalysisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNetRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateRateAnalysisRepository.findAll().size();
        // set the field null
        workEstimateRateAnalysis.setNetRate(null);

        // Create the WorkEstimateRateAnalysis, which fails.
        WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO = workEstimateRateAnalysisMapper.toDto(workEstimateRateAnalysis);

        restWorkEstimateRateAnalysisMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRateAnalysisDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateRateAnalysis> workEstimateRateAnalysisList = workEstimateRateAnalysisRepository.findAll();
        assertThat(workEstimateRateAnalysisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRaCompletedYnIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateRateAnalysisRepository.findAll().size();
        // set the field null
        workEstimateRateAnalysis.setRaCompletedYn(null);

        // Create the WorkEstimateRateAnalysis, which fails.
        WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO = workEstimateRateAnalysisMapper.toDto(workEstimateRateAnalysis);

        restWorkEstimateRateAnalysisMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRateAnalysisDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateRateAnalysis> workEstimateRateAnalysisList = workEstimateRateAnalysisRepository.findAll();
        assertThat(workEstimateRateAnalysisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWorkEstimateRateAnalyses() throws Exception {
        // Initialize the database
        workEstimateRateAnalysisRepository.saveAndFlush(workEstimateRateAnalysis);

        // Get all the workEstimateRateAnalysisList
        restWorkEstimateRateAnalysisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc", workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEstimateRateAnalysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].workEstimateId").value(hasItem(DEFAULT_WORK_ESTIMATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].areaWeightageMasterId").value(hasItem(DEFAULT_AREA_WEIGHTAGE_MASTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].areaWeightageCircleId").value(hasItem(DEFAULT_AREA_WEIGHTAGE_CIRCLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].areaWeightagePercentage").value(hasItem(TestUtil.sameNumber(DEFAULT_AREA_WEIGHTAGE_PERCENTAGE))))
            .andExpect(jsonPath("$.[*].sorFinancialYear").value(hasItem(DEFAULT_SOR_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].basicRate").value(hasItem(TestUtil.sameNumber(DEFAULT_BASIC_RATE))))
            .andExpect(jsonPath("$.[*].netRate").value(hasItem(TestUtil.sameNumber(DEFAULT_NET_RATE))))
            .andExpect(jsonPath("$.[*].floorNo").value(hasItem(DEFAULT_FLOOR_NO.intValue())))
            .andExpect(jsonPath("$.[*].contractorProfitPercentage").value(hasItem(TestUtil.sameNumber(DEFAULT_CONTRACTOR_PROFIT_PERCENTAGE))))
            .andExpect(jsonPath("$.[*].overheadPercentage").value(hasItem(TestUtil.sameNumber(DEFAULT_OVERHEAD_PERCENTAGE))))
            .andExpect(jsonPath("$.[*].taxPercentage").value(hasItem(TestUtil.sameNumber(DEFAULT_TAX_PERCENTAGE))))
            .andExpect(jsonPath("$.[*].localityAllowance").value(hasItem(TestUtil.sameNumber(DEFAULT_LOCALITY_ALLOWANCE))))
            .andExpect(jsonPath("$.[*].employeesCost").value(hasItem(TestUtil.sameNumber(DEFAULT_EMPLOYEES_COST))))
            .andExpect(jsonPath("$.[*].contingencies").value(hasItem(TestUtil.sameNumber(DEFAULT_CONTINGENCIES))))
            .andExpect(jsonPath("$.[*].transportationCost").value(hasItem(TestUtil.sameNumber(DEFAULT_TRANSPORTATION_COST))))
            .andExpect(jsonPath("$.[*].serviceTax").value(hasItem(TestUtil.sameNumber(DEFAULT_SERVICE_TAX))))
            .andExpect(jsonPath("$.[*].providentFundCharges").value(hasItem(TestUtil.sameNumber(DEFAULT_PROVIDENT_FUND_CHARGES))))
            .andExpect(jsonPath("$.[*].esiCharges").value(hasItem(TestUtil.sameNumber(DEFAULT_ESI_CHARGES))))
            .andExpect(jsonPath("$.[*].idcCharges").value(hasItem(TestUtil.sameNumber(DEFAULT_IDC_CHARGES))))
            .andExpect(jsonPath("$.[*].watchAndWardCost").value(hasItem(TestUtil.sameNumber(DEFAULT_WATCH_AND_WARD_COST))))
            .andExpect(jsonPath("$.[*].insuranceCost").value(hasItem(TestUtil.sameNumber(DEFAULT_INSURANCE_COST))))
            .andExpect(jsonPath("$.[*].statutoryCharges").value(hasItem(TestUtil.sameNumber(DEFAULT_STATUTORY_CHARGES))))
            .andExpect(jsonPath("$.[*].compensationCost").value(hasItem(TestUtil.sameNumber(DEFAULT_COMPENSATION_COST))))
            .andExpect(jsonPath("$.[*].raCompletedYn").value(hasItem(DEFAULT_RA_COMPLETED_YN.booleanValue())));
    }

    @Test
    @Transactional
    void getWorkEstimateRateAnalysis() throws Exception {
        // Initialize the database
        workEstimateRateAnalysisRepository.saveAndFlush(workEstimateRateAnalysis);

        // Get the workEstimateRateAnalysis
        restWorkEstimateRateAnalysisMockMvc
            .perform(get(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateRateAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEstimateRateAnalysis.getId().intValue()))
            .andExpect(jsonPath("$.workEstimateId").value(DEFAULT_WORK_ESTIMATE_ID.intValue()))
            .andExpect(jsonPath("$.areaWeightageMasterId").value(DEFAULT_AREA_WEIGHTAGE_MASTER_ID.intValue()))
            .andExpect(jsonPath("$.areaWeightageCircleId").value(DEFAULT_AREA_WEIGHTAGE_CIRCLE_ID.intValue()))
            .andExpect(jsonPath("$.areaWeightagePercentage").value(TestUtil.sameNumber(DEFAULT_AREA_WEIGHTAGE_PERCENTAGE)))
            .andExpect(jsonPath("$.sorFinancialYear").value(DEFAULT_SOR_FINANCIAL_YEAR))
            .andExpect(jsonPath("$.basicRate").value(TestUtil.sameNumber(DEFAULT_BASIC_RATE)))
            .andExpect(jsonPath("$.netRate").value(TestUtil.sameNumber(DEFAULT_NET_RATE)))
            .andExpect(jsonPath("$.floorNo").value(DEFAULT_FLOOR_NO.intValue()))
            .andExpect(jsonPath("$.contractorProfitPercentage").value(TestUtil.sameNumber(DEFAULT_CONTRACTOR_PROFIT_PERCENTAGE)))
            .andExpect(jsonPath("$.overheadPercentage").value(TestUtil.sameNumber(DEFAULT_OVERHEAD_PERCENTAGE)))
            .andExpect(jsonPath("$.taxPercentage").value(TestUtil.sameNumber(DEFAULT_TAX_PERCENTAGE)))
            .andExpect(jsonPath("$.localityAllowance").value(TestUtil.sameNumber(DEFAULT_LOCALITY_ALLOWANCE)))
            .andExpect(jsonPath("$.employeesCost").value(TestUtil.sameNumber(DEFAULT_EMPLOYEES_COST)))
            .andExpect(jsonPath("$.contingencies").value(TestUtil.sameNumber(DEFAULT_CONTINGENCIES)))
            .andExpect(jsonPath("$.transportationCost").value(TestUtil.sameNumber(DEFAULT_TRANSPORTATION_COST)))
            .andExpect(jsonPath("$.serviceTax").value(TestUtil.sameNumber(DEFAULT_SERVICE_TAX)))
            .andExpect(jsonPath("$.providentFundCharges").value(TestUtil.sameNumber(DEFAULT_PROVIDENT_FUND_CHARGES)))
            .andExpect(jsonPath("$.esiCharges").value(TestUtil.sameNumber(DEFAULT_ESI_CHARGES)))
            .andExpect(jsonPath("$.idcCharges").value(TestUtil.sameNumber(DEFAULT_IDC_CHARGES)))
            .andExpect(jsonPath("$.watchAndWardCost").value(TestUtil.sameNumber(DEFAULT_WATCH_AND_WARD_COST)))
            .andExpect(jsonPath("$.insuranceCost").value(TestUtil.sameNumber(DEFAULT_INSURANCE_COST)))
            .andExpect(jsonPath("$.statutoryCharges").value(TestUtil.sameNumber(DEFAULT_STATUTORY_CHARGES)))
            .andExpect(jsonPath("$.compensationCost").value(TestUtil.sameNumber(DEFAULT_COMPENSATION_COST)))
            .andExpect(jsonPath("$.raCompletedYn").value(DEFAULT_RA_COMPLETED_YN.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingWorkEstimateRateAnalysis() throws Exception {
        // Get the workEstimateRateAnalysis
        restWorkEstimateRateAnalysisMockMvc.perform(get(ENTITY_API_URL_ID, workEstimate.getId(), 
        		subEstimate.getId(), workEstimateItem.getId(), Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorkEstimateRateAnalysis() throws Exception {
        // Initialize the database
        workEstimateRateAnalysisRepository.saveAndFlush(workEstimateRateAnalysis);

        int databaseSizeBeforeUpdate = workEstimateRateAnalysisRepository.findAll().size();

        // Update the workEstimateRateAnalysis
        WorkEstimateRateAnalysis updatedWorkEstimateRateAnalysis = workEstimateRateAnalysisRepository
            .findById(workEstimateRateAnalysis.getId())
            .get();
        // Disconnect from session so that the updates on updatedWorkEstimateRateAnalysis are not directly saved in db
        em.detach(updatedWorkEstimateRateAnalysis);
        updatedWorkEstimateRateAnalysis
            .areaWeightageMasterId(UPDATED_AREA_WEIGHTAGE_MASTER_ID)
            .areaWeightageCircleId(UPDATED_AREA_WEIGHTAGE_CIRCLE_ID)
            .areaWeightagePercentage(UPDATED_AREA_WEIGHTAGE_PERCENTAGE)
            .sorFinancialYear(UPDATED_SOR_FINANCIAL_YEAR)
            .basicRate(UPDATED_BASIC_RATE)
            .netRate(UPDATED_NET_RATE)
            .floorNo(UPDATED_FLOOR_NO)
            .contractorProfitPercentage(UPDATED_CONTRACTOR_PROFIT_PERCENTAGE)
            .overheadPercentage(UPDATED_OVERHEAD_PERCENTAGE)
            .taxPercentage(UPDATED_TAX_PERCENTAGE)
            .localityAllowance(UPDATED_LOCALITY_ALLOWANCE)
            .employeesCost(UPDATED_EMPLOYEES_COST)
            .contingencies(UPDATED_CONTINGENCIES)
            .transportationCost(UPDATED_TRANSPORTATION_COST)
            .serviceTax(UPDATED_SERVICE_TAX)
            .providentFundCharges(UPDATED_PROVIDENT_FUND_CHARGES)
            .esiCharges(UPDATED_ESI_CHARGES)
            .idcCharges(UPDATED_IDC_CHARGES)
            .watchAndWardCost(UPDATED_WATCH_AND_WARD_COST)
            .insuranceCost(UPDATED_INSURANCE_COST)
            .statutoryCharges(UPDATED_STATUTORY_CHARGES)
            .compensationCost(UPDATED_COMPENSATION_COST)
            .raCompletedYn(UPDATED_RA_COMPLETED_YN);
        WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO = workEstimateRateAnalysisMapper.toDto(updatedWorkEstimateRateAnalysis);

        restWorkEstimateRateAnalysisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateRateAnalysisDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRateAnalysisDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkEstimateRateAnalysis in the database
        List<WorkEstimateRateAnalysis> workEstimateRateAnalysisList = workEstimateRateAnalysisRepository.findAll();
        assertThat(workEstimateRateAnalysisList).hasSize(databaseSizeBeforeUpdate);
        WorkEstimateRateAnalysis testWorkEstimateRateAnalysis = workEstimateRateAnalysisList.get(workEstimateRateAnalysisList.size() - 1);
        assertThat(testWorkEstimateRateAnalysis.getWorkEstimateId()).isEqualTo(DEFAULT_WORK_ESTIMATE_ID);
        assertThat(testWorkEstimateRateAnalysis.getAreaWeightageMasterId()).isEqualTo(UPDATED_AREA_WEIGHTAGE_MASTER_ID);
        assertThat(testWorkEstimateRateAnalysis.getAreaWeightageCircleId()).isEqualTo(UPDATED_AREA_WEIGHTAGE_CIRCLE_ID);
        assertThat(testWorkEstimateRateAnalysis.getAreaWeightagePercentage()).isEqualTo(String.format("%.2f", UPDATED_AREA_WEIGHTAGE_PERCENTAGE));
        assertThat(testWorkEstimateRateAnalysis.getSorFinancialYear()).isEqualTo(UPDATED_SOR_FINANCIAL_YEAR);
        assertThat(testWorkEstimateRateAnalysis.getBasicRate()).isEqualTo(String.format("%.4f", UPDATED_BASIC_RATE));
        assertThat(testWorkEstimateRateAnalysis.getNetRate()).isEqualTo(String.format("%.4f", UPDATED_NET_RATE));
        assertThat(testWorkEstimateRateAnalysis.getFloorNo()).isEqualTo(UPDATED_FLOOR_NO);
        assertThat(testWorkEstimateRateAnalysis.getContractorProfitPercentage()).isEqualTo(String.format("%.2f", UPDATED_CONTRACTOR_PROFIT_PERCENTAGE));
        assertThat(testWorkEstimateRateAnalysis.getOverheadPercentage()).isEqualTo(String.format("%.4f", UPDATED_OVERHEAD_PERCENTAGE));
        assertThat(testWorkEstimateRateAnalysis.getTaxPercentage()).isEqualTo(String.format("%.4f", UPDATED_TAX_PERCENTAGE));
        assertThat(testWorkEstimateRateAnalysis.getLocalityAllowance()).isEqualTo(String.format("%.4f", UPDATED_LOCALITY_ALLOWANCE));
        assertThat(testWorkEstimateRateAnalysis.getEmployeesCost()).isEqualTo(String.format("%.4f", UPDATED_EMPLOYEES_COST));
        assertThat(testWorkEstimateRateAnalysis.getContingencies()).isEqualTo(String.format("%.4f", UPDATED_CONTINGENCIES));
        assertThat(testWorkEstimateRateAnalysis.getTransportationCost()).isEqualTo(String.format("%.4f", UPDATED_TRANSPORTATION_COST));
        assertThat(testWorkEstimateRateAnalysis.getServiceTax()).isEqualTo(String.format("%.4f", UPDATED_SERVICE_TAX));
        assertThat(testWorkEstimateRateAnalysis.getProvidentFundCharges()).isEqualTo(String.format("%.4f", UPDATED_PROVIDENT_FUND_CHARGES));
        assertThat(testWorkEstimateRateAnalysis.getEsiCharges()).isEqualTo(String.format("%.4f", UPDATED_ESI_CHARGES));
        assertThat(testWorkEstimateRateAnalysis.getIdcCharges()).isEqualTo(String.format("%.4f", UPDATED_IDC_CHARGES));
        assertThat(testWorkEstimateRateAnalysis.getWatchAndWardCost()).isEqualTo(String.format("%.4f", UPDATED_WATCH_AND_WARD_COST));
        assertThat(testWorkEstimateRateAnalysis.getInsuranceCost()).isEqualTo(String.format("%.4f", UPDATED_INSURANCE_COST));
        assertThat(testWorkEstimateRateAnalysis.getStatutoryCharges()).isEqualTo(String.format("%.4f", UPDATED_STATUTORY_CHARGES));
        assertThat(testWorkEstimateRateAnalysis.getCompensationCost()).isEqualTo(String.format("%.4f", UPDATED_COMPENSATION_COST));
        assertThat(testWorkEstimateRateAnalysis.getRaCompletedYn()).isEqualTo(UPDATED_RA_COMPLETED_YN);
    }

    @Test
    @Transactional
    void putNonExistingWorkEstimateRateAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateRateAnalysisRepository.findAll().size();
        workEstimateRateAnalysis.setId(count.incrementAndGet());

        // Create the WorkEstimateRateAnalysis
        WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO = workEstimateRateAnalysisMapper.toDto(workEstimateRateAnalysis);

        // If the entity doesn't have an ID, it will throw RecordNotFoundException
        restWorkEstimateRateAnalysisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateRateAnalysisDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRateAnalysisDTO))
            )
            .andExpect(status().isNotFound());

        // Validate the WorkEstimateRateAnalysis in the database
        List<WorkEstimateRateAnalysis> workEstimateRateAnalysisList = workEstimateRateAnalysisRepository.findAll();
        assertThat(workEstimateRateAnalysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkEstimateRateAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateRateAnalysisRepository.findAll().size();
        workEstimateRateAnalysis.setId(count.incrementAndGet());

        // Create the WorkEstimateRateAnalysis
        WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO = workEstimateRateAnalysisMapper.toDto(workEstimateRateAnalysis);

        // If url ID doesn't match entity ID, it will throw RecordNotFoundException
        restWorkEstimateRateAnalysisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRateAnalysisDTO))
            )
            .andExpect(status().isNotFound());

        // Validate the WorkEstimateRateAnalysis in the database
        List<WorkEstimateRateAnalysis> workEstimateRateAnalysisList = workEstimateRateAnalysisRepository.findAll();
        assertThat(workEstimateRateAnalysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkEstimateRateAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateRateAnalysisRepository.findAll().size();
        workEstimateRateAnalysis.setId(count.incrementAndGet());

        // Create the WorkEstimateRateAnalysis
        WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO = workEstimateRateAnalysisMapper.toDto(workEstimateRateAnalysis);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkEstimateRateAnalysisMockMvc
            .perform(
                put(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRateAnalysisDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkEstimateRateAnalysis in the database
        List<WorkEstimateRateAnalysis> workEstimateRateAnalysisList = workEstimateRateAnalysisRepository.findAll();
        assertThat(workEstimateRateAnalysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkEstimateRateAnalysis() throws Exception {
        // Initialize the database
        workEstimateRateAnalysisRepository.saveAndFlush(workEstimateRateAnalysis);

        int databaseSizeBeforeDelete = workEstimateRateAnalysisRepository.findAll().size();

        // Delete the workEstimateRateAnalysis
        restWorkEstimateRateAnalysisMockMvc
            .perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), 
            		workEstimateItem.getId(), workEstimateRateAnalysis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEstimateRateAnalysis> workEstimateRateAnalysisList = workEstimateRateAnalysisRepository.findAll();
        assertThat(workEstimateRateAnalysisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
