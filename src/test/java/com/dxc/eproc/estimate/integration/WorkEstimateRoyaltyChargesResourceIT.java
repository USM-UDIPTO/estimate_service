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

import com.dxc.eproc.estimate.controller.WorkEstimateRoyaltyChargesController;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.model.WorkEstimateItem;
import com.dxc.eproc.estimate.model.WorkEstimateRoyaltyCharges;
import com.dxc.eproc.estimate.repository.SubEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateItemRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRoyaltyChargesRepository;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRoyaltyChargesDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateRoyaltyChargesMapper;

/**
 * Integration tests for the {@link WorkEstimateRoyaltyChargesController} REST controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class WorkEstimateRoyaltyChargesResourceIT extends AbstractTestNGSpringContextTests {

    private static final Long DEFAULT_WORK_ESTIMATE_ID = 1L;
    private static final Long UPDATED_WORK_ESTIMATE_ID = 2L;

    private static final Long DEFAULT_CAT_WORK_SOR_ITEM_ID = 1L;
    private static final Long UPDATED_CAT_WORK_SOR_ITEM_ID = 2L;

    private static final Long DEFAULT_MATERIAL_MASTER_ID = 1L;
    private static final Long UPDATED_MATERIAL_MASTER_ID = 2L;

    private static final Long DEFAULT_SUB_ESTIMATE_ID = 1L;
    private static final Long UPDATED_SUB_ESTIMATE_ID = 2L;

    private static final BigDecimal DEFAULT_SR_ROYALTY_CHARGES = new BigDecimal(1);
    private static final BigDecimal UPDATED_SR_ROYALTY_CHARGES = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PREVAILING_ROYALTY_CHARGES = new BigDecimal(0);
    private static final BigDecimal UPDATED_PREVAILING_ROYALTY_CHARGES = new BigDecimal(1);

    private static final BigDecimal DEFAULT_DENSITY_FACTOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_DENSITY_FACTOR = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DIFFERENCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DIFFERENCE = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-royalty-charges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkEstimateRoyaltyChargesRepository workEstimateRoyaltyChargesRepository;
    
    @Autowired
    private WorkEstimateRepository workEstimateRepository;
    
    @Autowired
    private SubEstimateRepository subEstimateRepository;
    
    @Autowired
    private WorkEstimateItemRepository workEstimateItemRepository;

    @Autowired
    private WorkEstimateRoyaltyChargesMapper workEstimateRoyaltyChargesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEstimateRoyaltyChargesMockMvc;

    private WorkEstimateRoyaltyCharges workEstimateRoyaltyCharges;
    
	private WorkEstimate workEstimate;

	private SubEstimate subEstimate;
	
	private WorkEstimateItem workEstimateItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEstimateRoyaltyCharges createEntity(EntityManager em) {
        WorkEstimateRoyaltyCharges workEstimateRoyaltyCharges = new WorkEstimateRoyaltyCharges()
            .workEstimateId(DEFAULT_WORK_ESTIMATE_ID)
            .catWorkSorItemId(DEFAULT_CAT_WORK_SOR_ITEM_ID)
            .materialMasterId(DEFAULT_MATERIAL_MASTER_ID)
            .subEstimateId(DEFAULT_SUB_ESTIMATE_ID)
            .srRoyaltyCharges(DEFAULT_SR_ROYALTY_CHARGES)
            .prevailingRoyaltyCharges(DEFAULT_PREVAILING_ROYALTY_CHARGES)
            .densityFactor(DEFAULT_DENSITY_FACTOR)
            .difference(DEFAULT_DIFFERENCE);
        return workEstimateRoyaltyCharges;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEstimateRoyaltyCharges createUpdatedEntity(EntityManager em) {
        WorkEstimateRoyaltyCharges workEstimateRoyaltyCharges = new WorkEstimateRoyaltyCharges()
            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
            .catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID)
            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
            .subEstimateId(UPDATED_SUB_ESTIMATE_ID)
            .srRoyaltyCharges(UPDATED_SR_ROYALTY_CHARGES)
            .prevailingRoyaltyCharges(UPDATED_PREVAILING_ROYALTY_CHARGES)
            .densityFactor(UPDATED_DENSITY_FACTOR)
            .difference(UPDATED_DIFFERENCE);
        return workEstimateRoyaltyCharges;
    }
    
	private WorkEstimate createWorkEstimateEntity() {
		WorkEstimate workEstimate = new WorkEstimate().workEstimateNumber("1122").status(WorkEstimateStatus.DRAFT)
				.deptId(1L).locationId(1L).fileNumber("1245").name("Road Repair")
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
        workEstimateRoyaltyCharges = createEntity(em);
        workEstimateRoyaltyCharges.setWorkEstimateId(workEstimate.getId());
        workEstimateRoyaltyCharges.setSubEstimateId(subEstimate.getId());
        workEstimateRoyaltyCharges.setWorkEstimateItemId(workEstimateItem.getId());
    }

    @Test
    @Transactional
    void createWorkEstimateRoyaltyCharges() throws Exception {
        int databaseSizeBeforeCreate = workEstimateRoyaltyChargesRepository.findAll().size();
        // Create the WorkEstimateRoyaltyCharges
        WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO = workEstimateRoyaltyChargesMapper.toDto(workEstimateRoyaltyCharges);
        restWorkEstimateRoyaltyChargesMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRoyaltyChargesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkEstimateRoyaltyCharges in the database
        List<WorkEstimateRoyaltyCharges> workEstimateRoyaltyChargesList = workEstimateRoyaltyChargesRepository.findAll();
        assertThat(workEstimateRoyaltyChargesList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEstimateRoyaltyCharges testWorkEstimateRoyaltyCharges = workEstimateRoyaltyChargesList.get(
            workEstimateRoyaltyChargesList.size() - 1
        );
        assertThat(testWorkEstimateRoyaltyCharges.getWorkEstimateId()).isEqualTo(workEstimate.getId());
        assertThat(testWorkEstimateRoyaltyCharges.getCatWorkSorItemId()).isEqualTo(DEFAULT_CAT_WORK_SOR_ITEM_ID);
        assertThat(testWorkEstimateRoyaltyCharges.getMaterialMasterId()).isEqualTo(DEFAULT_MATERIAL_MASTER_ID);
        assertThat(testWorkEstimateRoyaltyCharges.getSubEstimateId()).isEqualTo(subEstimate.getId());
        assertThat(testWorkEstimateRoyaltyCharges.getSrRoyaltyCharges()).isEqualByComparingTo(DEFAULT_SR_ROYALTY_CHARGES);
        assertThat(testWorkEstimateRoyaltyCharges.getPrevailingRoyaltyCharges()).isEqualByComparingTo(DEFAULT_PREVAILING_ROYALTY_CHARGES);
        assertThat(testWorkEstimateRoyaltyCharges.getDensityFactor()).isEqualByComparingTo(DEFAULT_DENSITY_FACTOR);
        assertThat(testWorkEstimateRoyaltyCharges.getDifference()).isEqualByComparingTo(DEFAULT_DIFFERENCE);
    }

    @Test
    @Transactional
    void checkCatWorkSorItemIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateRoyaltyChargesRepository.findAll().size();
        // set the field null
        workEstimateRoyaltyCharges.setCatWorkSorItemId(null);

        // Create the WorkEstimateRoyaltyCharges, which fails.
        WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO = workEstimateRoyaltyChargesMapper.toDto(workEstimateRoyaltyCharges);

        restWorkEstimateRoyaltyChargesMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRoyaltyChargesDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateRoyaltyCharges> workEstimateRoyaltyChargesList = workEstimateRoyaltyChargesRepository.findAll();
        assertThat(workEstimateRoyaltyChargesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaterialMasterIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateRoyaltyChargesRepository.findAll().size();
        // set the field null
        workEstimateRoyaltyCharges.setMaterialMasterId(null);

        // Create the WorkEstimateRoyaltyCharges, which fails.
        WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO = workEstimateRoyaltyChargesMapper.toDto(workEstimateRoyaltyCharges);

        restWorkEstimateRoyaltyChargesMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRoyaltyChargesDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateRoyaltyCharges> workEstimateRoyaltyChargesList = workEstimateRoyaltyChargesRepository.findAll();
        assertThat(workEstimateRoyaltyChargesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSrRoyaltyChargesIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateRoyaltyChargesRepository.findAll().size();
        // set the field null
        workEstimateRoyaltyCharges.setSrRoyaltyCharges(null);

        // Create the WorkEstimateRoyaltyCharges, which fails.
        WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO = workEstimateRoyaltyChargesMapper.toDto(workEstimateRoyaltyCharges);

        restWorkEstimateRoyaltyChargesMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRoyaltyChargesDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateRoyaltyCharges> workEstimateRoyaltyChargesList = workEstimateRoyaltyChargesRepository.findAll();
        assertThat(workEstimateRoyaltyChargesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrevailingRoyaltyChargesIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateRoyaltyChargesRepository.findAll().size();
        // set the field null
        workEstimateRoyaltyCharges.setPrevailingRoyaltyCharges(null);

        // Create the WorkEstimateRoyaltyCharges, which fails.
        WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO = workEstimateRoyaltyChargesMapper.toDto(workEstimateRoyaltyCharges);

        restWorkEstimateRoyaltyChargesMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRoyaltyChargesDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateRoyaltyCharges> workEstimateRoyaltyChargesList = workEstimateRoyaltyChargesRepository.findAll();
        assertThat(workEstimateRoyaltyChargesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDensityFactorIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateRoyaltyChargesRepository.findAll().size();
        // set the field null
        workEstimateRoyaltyCharges.setDensityFactor(null);

        // Create the WorkEstimateRoyaltyCharges, which fails.
        WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO = workEstimateRoyaltyChargesMapper.toDto(workEstimateRoyaltyCharges);

        restWorkEstimateRoyaltyChargesMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRoyaltyChargesDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateRoyaltyCharges> workEstimateRoyaltyChargesList = workEstimateRoyaltyChargesRepository.findAll();
        assertThat(workEstimateRoyaltyChargesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWorkEstimateRoyaltyCharges() throws Exception {
        // Initialize the database
        workEstimateRoyaltyChargesRepository.saveAndFlush(workEstimateRoyaltyCharges);

        // Get all the workEstimateRoyaltyChargesList
        restWorkEstimateRoyaltyChargesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc", workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEstimateRoyaltyCharges.getId().intValue())))
            .andExpect(jsonPath("$.[*].workEstimateId").value(hasItem(workEstimate.getId().intValue())))
            .andExpect(jsonPath("$.[*].catWorkSorItemId").value(hasItem(DEFAULT_CAT_WORK_SOR_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].materialMasterId").value(hasItem(DEFAULT_MATERIAL_MASTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].subEstimateId").value(hasItem(subEstimate.getId().intValue())))
            .andExpect(jsonPath("$.[*].srRoyaltyCharges").value(hasItem(TestUtil.sameNumber(DEFAULT_SR_ROYALTY_CHARGES))))
            .andExpect(jsonPath("$.[*].prevailingRoyaltyCharges").value(hasItem(TestUtil.sameNumber(DEFAULT_PREVAILING_ROYALTY_CHARGES))))
            .andExpect(jsonPath("$.[*].densityFactor").value(hasItem(TestUtil.sameNumber(DEFAULT_DENSITY_FACTOR))))
            .andExpect(jsonPath("$.[*].difference").value(hasItem(TestUtil.sameNumber(DEFAULT_DIFFERENCE))));
    }

    @Test
    @Transactional
    void getWorkEstimateRoyaltyCharges() throws Exception {
        // Initialize the database
        workEstimateRoyaltyChargesRepository.saveAndFlush(workEstimateRoyaltyCharges);

        // Get the workEstimateRoyaltyCharges
        restWorkEstimateRoyaltyChargesMockMvc
            .perform(get(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateRoyaltyCharges.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEstimateRoyaltyCharges.getId().intValue()))
            .andExpect(jsonPath("$.workEstimateId").value(workEstimate.getId().intValue()))
            .andExpect(jsonPath("$.catWorkSorItemId").value(DEFAULT_CAT_WORK_SOR_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.materialMasterId").value(DEFAULT_MATERIAL_MASTER_ID.intValue()))
            .andExpect(jsonPath("$.subEstimateId").value(subEstimate.getId().intValue()))
            .andExpect(jsonPath("$.srRoyaltyCharges").value(TestUtil.sameNumber(DEFAULT_SR_ROYALTY_CHARGES)))
            .andExpect(jsonPath("$.prevailingRoyaltyCharges").value(TestUtil.sameNumber(DEFAULT_PREVAILING_ROYALTY_CHARGES)))
            .andExpect(jsonPath("$.densityFactor").value(TestUtil.sameNumber(DEFAULT_DENSITY_FACTOR)))
            .andExpect(jsonPath("$.difference").value(TestUtil.sameNumber(DEFAULT_DIFFERENCE)));
    }

    @Test
    @Transactional
    void getNonExistingWorkEstimateRoyaltyCharges() throws Exception {
        // Get the workEstimateRoyaltyCharges
        restWorkEstimateRoyaltyChargesMockMvc.perform(get(ENTITY_API_URL_ID, workEstimate.getId(), 
        		subEstimate.getId(), workEstimateItem.getId(), Long.MAX_VALUE))
        .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorkEstimateRoyaltyCharges() throws Exception {
        // Initialize the database
        workEstimateRoyaltyChargesRepository.saveAndFlush(workEstimateRoyaltyCharges);

        int databaseSizeBeforeUpdate = workEstimateRoyaltyChargesRepository.findAll().size();

        // Update the workEstimateRoyaltyCharges
        WorkEstimateRoyaltyCharges updatedWorkEstimateRoyaltyCharges = workEstimateRoyaltyChargesRepository
            .findById(workEstimateRoyaltyCharges.getId())
            .get();
        // Disconnect from session so that the updates on updatedWorkEstimateRoyaltyCharges are not directly saved in db
        em.detach(updatedWorkEstimateRoyaltyCharges);
        updatedWorkEstimateRoyaltyCharges
            .catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID)
            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
            .subEstimateId(UPDATED_SUB_ESTIMATE_ID)
            .srRoyaltyCharges(UPDATED_SR_ROYALTY_CHARGES)
            .prevailingRoyaltyCharges(UPDATED_PREVAILING_ROYALTY_CHARGES)
            .densityFactor(UPDATED_DENSITY_FACTOR)
            .difference(UPDATED_DIFFERENCE);
        WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO = workEstimateRoyaltyChargesMapper.toDto(
            updatedWorkEstimateRoyaltyCharges
        );

        restWorkEstimateRoyaltyChargesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateRoyaltyChargesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRoyaltyChargesDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkEstimateRoyaltyCharges in the database
        List<WorkEstimateRoyaltyCharges> workEstimateRoyaltyChargesList = workEstimateRoyaltyChargesRepository.findAll();
        assertThat(workEstimateRoyaltyChargesList).hasSize(databaseSizeBeforeUpdate);
        WorkEstimateRoyaltyCharges testWorkEstimateRoyaltyCharges = workEstimateRoyaltyChargesList.get(
            workEstimateRoyaltyChargesList.size() - 1
        );
        assertThat(testWorkEstimateRoyaltyCharges.getWorkEstimateId()).isEqualTo(workEstimate.getId());
        assertThat(testWorkEstimateRoyaltyCharges.getCatWorkSorItemId()).isEqualTo(UPDATED_CAT_WORK_SOR_ITEM_ID);
        assertThat(testWorkEstimateRoyaltyCharges.getMaterialMasterId()).isEqualTo(UPDATED_MATERIAL_MASTER_ID);
        assertThat(testWorkEstimateRoyaltyCharges.getSubEstimateId()).isEqualTo(UPDATED_SUB_ESTIMATE_ID);
        assertThat(testWorkEstimateRoyaltyCharges.getSrRoyaltyCharges()).isEqualTo(String.format("%.2f", UPDATED_SR_ROYALTY_CHARGES));
        assertThat(testWorkEstimateRoyaltyCharges.getPrevailingRoyaltyCharges()).isEqualTo(String.format("%.2f", UPDATED_PREVAILING_ROYALTY_CHARGES));
        assertThat(testWorkEstimateRoyaltyCharges.getDensityFactor()).isEqualTo(String.format("%.2f", UPDATED_DENSITY_FACTOR));
        assertThat(testWorkEstimateRoyaltyCharges.getDifference()).isEqualTo(String.format("%.2f", UPDATED_DIFFERENCE));
    }

    @Test
    @Transactional
    void putNonExistingWorkEstimateRoyaltyCharges() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateRoyaltyChargesRepository.findAll().size();
        workEstimateRoyaltyCharges.setId(count.incrementAndGet());

        // Create the WorkEstimateRoyaltyCharges
        WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO = workEstimateRoyaltyChargesMapper.toDto(workEstimateRoyaltyCharges);

        // If the entity doesn't have an ID, it will throw RecordNotFoundException
        restWorkEstimateRoyaltyChargesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateRoyaltyChargesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRoyaltyChargesDTO))
            )
            .andExpect(status().isNotFound());

        // Validate the WorkEstimateRoyaltyCharges in the database
        List<WorkEstimateRoyaltyCharges> workEstimateRoyaltyChargesList = workEstimateRoyaltyChargesRepository.findAll();
        assertThat(workEstimateRoyaltyChargesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkEstimateRoyaltyCharges() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateRoyaltyChargesRepository.findAll().size();
        workEstimateRoyaltyCharges.setId(count.incrementAndGet());

        // Create the WorkEstimateRoyaltyCharges
        WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO = workEstimateRoyaltyChargesMapper.toDto(workEstimateRoyaltyCharges);

        // If url ID doesn't match entity ID, it will throw RecordNotFoundException
        restWorkEstimateRoyaltyChargesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRoyaltyChargesDTO))
            )
            .andExpect(status().isNotFound());

        // Validate the WorkEstimateRoyaltyCharges in the database
        List<WorkEstimateRoyaltyCharges> workEstimateRoyaltyChargesList = workEstimateRoyaltyChargesRepository.findAll();
        assertThat(workEstimateRoyaltyChargesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkEstimateRoyaltyCharges() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateRoyaltyChargesRepository.findAll().size();
        workEstimateRoyaltyCharges.setId(count.incrementAndGet());

        // Create the WorkEstimateRoyaltyCharges
        WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO = workEstimateRoyaltyChargesMapper.toDto(workEstimateRoyaltyCharges);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkEstimateRoyaltyChargesMockMvc
            .perform(
                put(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateRoyaltyChargesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkEstimateRoyaltyCharges in the database
        List<WorkEstimateRoyaltyCharges> workEstimateRoyaltyChargesList = workEstimateRoyaltyChargesRepository.findAll();
        assertThat(workEstimateRoyaltyChargesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkEstimateRoyaltyCharges() throws Exception {
        // Initialize the database
        workEstimateRoyaltyChargesRepository.saveAndFlush(workEstimateRoyaltyCharges);

        int databaseSizeBeforeDelete = workEstimateRoyaltyChargesRepository.findAll().size();

        // Delete the workEstimateRoyaltyCharges
        restWorkEstimateRoyaltyChargesMockMvc
            .perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateRoyaltyCharges.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEstimateRoyaltyCharges> workEstimateRoyaltyChargesList = workEstimateRoyaltyChargesRepository.findAll();
        assertThat(workEstimateRoyaltyChargesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
