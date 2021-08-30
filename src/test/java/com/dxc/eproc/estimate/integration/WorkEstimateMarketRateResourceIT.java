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

import com.dxc.eproc.estimate.controller.WorkEstimateMarketRateController;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.model.WorkEstimateItem;
import com.dxc.eproc.estimate.model.WorkEstimateMarketRate;
import com.dxc.eproc.estimate.repository.SubEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateItemRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateMarketRateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.dxc.eproc.estimate.service.dto.WorkEstimateMarketRateDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateMarketRateMapper;

/**
 * Integration tests for the {@link WorkEstimateMarketRateController} REST controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class WorkEstimateMarketRateResourceIT extends AbstractTestNGSpringContextTests {

    private static final Long DEFAULT_WORK_ESTIMATE_ID = 1L;
    private static final Long UPDATED_WORK_ESTIMATE_ID = 2L;

    private static final Long DEFAULT_SUB_ESTIMATE_ID = 1L;
    private static final Long UPDATED_SUB_ESTIMATE_ID = 2L;

    private static final Long DEFAULT_MATERIAL_MASTER_ID = 1L;
    private static final Long UPDATED_MATERIAL_MASTER_ID = 2L;

    private static final BigDecimal DEFAULT_DIFFERENCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DIFFERENCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PREVAILING_MARKET_RATE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PREVAILING_MARKET_RATE = new BigDecimal(1);

    private static final String ENTITY_API_URL = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-market-rates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkEstimateMarketRateRepository workEstimateMarketRateRepository;

    @Autowired
    private WorkEstimateRepository workEstimateRepository;
    
    @Autowired
    private SubEstimateRepository subEstimateRepository;
    
    @Autowired
    private WorkEstimateItemRepository workEstimateItemRepository;

    @Autowired
    private WorkEstimateMarketRateMapper workEstimateMarketRateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEstimateMarketRateMockMvc;

    private WorkEstimateMarketRate workEstimateMarketRate;
    
	private WorkEstimate workEstimate;

	private SubEstimate subEstimate;
	
	private WorkEstimateItem workEstimateItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEstimateMarketRate createEntity(EntityManager em) {
        WorkEstimateMarketRate workEstimateMarketRate = new WorkEstimateMarketRate()
            .workEstimateId(DEFAULT_WORK_ESTIMATE_ID)
            .subEstimateId(DEFAULT_SUB_ESTIMATE_ID)
            .materialMasterId(DEFAULT_MATERIAL_MASTER_ID)
            .difference(DEFAULT_DIFFERENCE)
            .prevailingMarketRate(DEFAULT_PREVAILING_MARKET_RATE);
        return workEstimateMarketRate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEstimateMarketRate createUpdatedEntity(EntityManager em) {
        WorkEstimateMarketRate workEstimateMarketRate = new WorkEstimateMarketRate()
            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
            .subEstimateId(UPDATED_SUB_ESTIMATE_ID)
            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
            .difference(UPDATED_DIFFERENCE)
            .prevailingMarketRate(UPDATED_PREVAILING_MARKET_RATE);
        return workEstimateMarketRate;
    }
    
	private WorkEstimate createWorkEstimateEntity() {
		WorkEstimate workEstimate = new WorkEstimate().workEstimateNumber("1124").status(WorkEstimateStatus.DRAFT)
				.deptId(1L).locationId(1L).fileNumber("1247").name("Road Repair")
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
        workEstimateMarketRate = createEntity(em);
        workEstimateMarketRate.setWorkEstimateId(workEstimate.getId());
        workEstimateMarketRate.setSubEstimateId(subEstimate.getId());
        workEstimateMarketRate.setWorkEstimateItemId(workEstimateItem.getId());
    }

    @Test
    @Transactional
    void createWorkEstimateMarketRate() throws Exception {
        int databaseSizeBeforeCreate = workEstimateMarketRateRepository.findAll().size();
        // Create the WorkEstimateMarketRate
        WorkEstimateMarketRateDTO workEstimateMarketRateDTO = workEstimateMarketRateMapper.toDto(workEstimateMarketRate);
        restWorkEstimateMarketRateMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateMarketRateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkEstimateMarketRate in the database
        List<WorkEstimateMarketRate> workEstimateMarketRateList = workEstimateMarketRateRepository.findAll();
        assertThat(workEstimateMarketRateList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEstimateMarketRate testWorkEstimateMarketRate = workEstimateMarketRateList.get(workEstimateMarketRateList.size() - 1);
        assertThat(testWorkEstimateMarketRate.getWorkEstimateId()).isEqualTo(workEstimate.getId());
        assertThat(testWorkEstimateMarketRate.getSubEstimateId()).isEqualTo(subEstimate.getId());
        assertThat(testWorkEstimateMarketRate.getMaterialMasterId()).isEqualTo(DEFAULT_MATERIAL_MASTER_ID);
        assertThat(testWorkEstimateMarketRate.getDifference()).isEqualByComparingTo(DEFAULT_DIFFERENCE);
        assertThat(testWorkEstimateMarketRate.getPrevailingMarketRate()).isEqualByComparingTo(DEFAULT_PREVAILING_MARKET_RATE);
    }

    @Test
    @Transactional
    void checkMaterialMasterIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateMarketRateRepository.findAll().size();
        // set the field null
        workEstimateMarketRate.setMaterialMasterId(null);

        // Create the WorkEstimateMarketRate, which fails.
        WorkEstimateMarketRateDTO workEstimateMarketRateDTO = workEstimateMarketRateMapper.toDto(workEstimateMarketRate);

        restWorkEstimateMarketRateMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateMarketRateDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateMarketRate> workEstimateMarketRateList = workEstimateMarketRateRepository.findAll();
        assertThat(workEstimateMarketRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDifferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateMarketRateRepository.findAll().size();
        // set the field null
        workEstimateMarketRate.setDifference(null);

        // Create the WorkEstimateMarketRate, which fails.
        WorkEstimateMarketRateDTO workEstimateMarketRateDTO = workEstimateMarketRateMapper.toDto(workEstimateMarketRate);

        restWorkEstimateMarketRateMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateMarketRateDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateMarketRate> workEstimateMarketRateList = workEstimateMarketRateRepository.findAll();
        assertThat(workEstimateMarketRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrevailingMarketRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateMarketRateRepository.findAll().size();
        // set the field null
        workEstimateMarketRate.setPrevailingMarketRate(null);

        // Create the WorkEstimateMarketRate, which fails.
        WorkEstimateMarketRateDTO workEstimateMarketRateDTO = workEstimateMarketRateMapper.toDto(workEstimateMarketRate);

        restWorkEstimateMarketRateMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateMarketRateDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateMarketRate> workEstimateMarketRateList = workEstimateMarketRateRepository.findAll();
        assertThat(workEstimateMarketRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWorkEstimateMarketRates() throws Exception {
        // Initialize the database
        workEstimateMarketRateRepository.saveAndFlush(workEstimateMarketRate);

        // Get all the workEstimateMarketRateList
        restWorkEstimateMarketRateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc", workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEstimateMarketRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].workEstimateId").value(hasItem(workEstimate.getId().intValue())))
            .andExpect(jsonPath("$.[*].subEstimateId").value(hasItem(subEstimate.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialMasterId").value(hasItem(DEFAULT_MATERIAL_MASTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].difference").value(hasItem(TestUtil.sameNumber(DEFAULT_DIFFERENCE))))
            .andExpect(jsonPath("$.[*].prevailingMarketRate").value(hasItem(TestUtil.sameNumber(DEFAULT_PREVAILING_MARKET_RATE))));
    }

    @Test
    @Transactional
    void getWorkEstimateMarketRate() throws Exception {
        // Initialize the database
        workEstimateMarketRateRepository.saveAndFlush(workEstimateMarketRate);

        // Get the workEstimateMarketRate
        restWorkEstimateMarketRateMockMvc
            .perform(get(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateMarketRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEstimateMarketRate.getId().intValue()))
            .andExpect(jsonPath("$.workEstimateId").value(workEstimate.getId().intValue()))
            .andExpect(jsonPath("$.subEstimateId").value(subEstimate.getId().intValue()))
            .andExpect(jsonPath("$.materialMasterId").value(DEFAULT_MATERIAL_MASTER_ID.intValue()))
            .andExpect(jsonPath("$.difference").value(TestUtil.sameNumber(DEFAULT_DIFFERENCE)))
            .andExpect(jsonPath("$.prevailingMarketRate").value(TestUtil.sameNumber(DEFAULT_PREVAILING_MARKET_RATE)));
    }

    @Test
    @Transactional
    void getNonExistingWorkEstimateMarketRate() throws Exception {
        // Get the workEstimateMarketRate
        restWorkEstimateMarketRateMockMvc.perform(get(ENTITY_API_URL_ID, workEstimate.getId(), 
        		subEstimate.getId(), workEstimateItem.getId(), Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorkEstimateMarketRate() throws Exception {
        // Initialize the database
        workEstimateMarketRateRepository.saveAndFlush(workEstimateMarketRate);

        int databaseSizeBeforeUpdate = workEstimateMarketRateRepository.findAll().size();

        // Update the workEstimateMarketRate
        WorkEstimateMarketRate updatedWorkEstimateMarketRate = workEstimateMarketRateRepository
            .findById(workEstimateMarketRate.getId())
            .get();
        // Disconnect from session so that the updates on updatedWorkEstimateMarketRate are not directly saved in db
        em.detach(updatedWorkEstimateMarketRate);
        updatedWorkEstimateMarketRate
            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
            .subEstimateId(UPDATED_SUB_ESTIMATE_ID)
            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
            .difference(UPDATED_DIFFERENCE)
            .prevailingMarketRate(UPDATED_PREVAILING_MARKET_RATE);
        WorkEstimateMarketRateDTO workEstimateMarketRateDTO = workEstimateMarketRateMapper.toDto(updatedWorkEstimateMarketRate);

        restWorkEstimateMarketRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateMarketRateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateMarketRateDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkEstimateMarketRate in the database
        List<WorkEstimateMarketRate> workEstimateMarketRateList = workEstimateMarketRateRepository.findAll();
        assertThat(workEstimateMarketRateList).hasSize(databaseSizeBeforeUpdate);
        WorkEstimateMarketRate testWorkEstimateMarketRate = workEstimateMarketRateList.get(workEstimateMarketRateList.size() - 1);
        assertThat(testWorkEstimateMarketRate.getWorkEstimateId()).isEqualTo(workEstimate.getId());
        assertThat(testWorkEstimateMarketRate.getSubEstimateId()).isEqualTo(subEstimate.getId());
        assertThat(testWorkEstimateMarketRate.getMaterialMasterId()).isEqualTo(UPDATED_MATERIAL_MASTER_ID);
        assertThat(testWorkEstimateMarketRate.getDifference()).isEqualTo(String.format("%.4f", UPDATED_DIFFERENCE));
        assertThat(testWorkEstimateMarketRate.getPrevailingMarketRate()).isEqualTo(String.format("%.4f", UPDATED_PREVAILING_MARKET_RATE));
    }

    @Test
    @Transactional
    void putNonExistingWorkEstimateMarketRate() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateMarketRateRepository.findAll().size();
        workEstimateMarketRate.setId(count.incrementAndGet());

        // Create the WorkEstimateMarketRate
        WorkEstimateMarketRateDTO workEstimateMarketRateDTO = workEstimateMarketRateMapper.toDto(workEstimateMarketRate);

        // If the entity doesn't have an ID, it will throw RecordNotFoundException
        restWorkEstimateMarketRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateMarketRateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateMarketRateDTO))
            )
            .andExpect(status().isNotFound());

        // Validate the WorkEstimateMarketRate in the database
        List<WorkEstimateMarketRate> workEstimateMarketRateList = workEstimateMarketRateRepository.findAll();
        assertThat(workEstimateMarketRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkEstimateMarketRate() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateMarketRateRepository.findAll().size();
        workEstimateMarketRate.setId(count.incrementAndGet());

        // Create the WorkEstimateMarketRate
        WorkEstimateMarketRateDTO workEstimateMarketRateDTO = workEstimateMarketRateMapper.toDto(workEstimateMarketRate);

        // If url ID doesn't match entity ID, it will throw RecordNotFoundException
        restWorkEstimateMarketRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateMarketRateDTO))
            )
            .andExpect(status().isNotFound());

        // Validate the WorkEstimateMarketRate in the database
        List<WorkEstimateMarketRate> workEstimateMarketRateList = workEstimateMarketRateRepository.findAll();
        assertThat(workEstimateMarketRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkEstimateMarketRate() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateMarketRateRepository.findAll().size();
        workEstimateMarketRate.setId(count.incrementAndGet());

        // Create the WorkEstimateMarketRate
        WorkEstimateMarketRateDTO workEstimateMarketRateDTO = workEstimateMarketRateMapper.toDto(workEstimateMarketRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkEstimateMarketRateMockMvc
            .perform(
                put(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateMarketRateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkEstimateMarketRate in the database
        List<WorkEstimateMarketRate> workEstimateMarketRateList = workEstimateMarketRateRepository.findAll();
        assertThat(workEstimateMarketRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkEstimateMarketRate() throws Exception {
        // Initialize the database
        workEstimateMarketRateRepository.saveAndFlush(workEstimateMarketRate);

        int databaseSizeBeforeDelete = workEstimateMarketRateRepository.findAll().size();

        // Delete the workEstimateMarketRate
        restWorkEstimateMarketRateMockMvc
            .perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), 
            		workEstimateItem.getId(), workEstimateMarketRate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEstimateMarketRate> workEstimateMarketRateList = workEstimateMarketRateRepository.findAll();
        assertThat(workEstimateMarketRateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
