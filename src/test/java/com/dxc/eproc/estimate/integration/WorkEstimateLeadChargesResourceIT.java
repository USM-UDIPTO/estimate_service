//package com.dxc.eproc.estimate.integration;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//
//import javax.persistence.EntityManager;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import com.dxc.eproc.estimate.IntegrationTest;
//import com.dxc.eproc.estimate.controller.WorkEstimateLeadChargesResource;
//import com.dxc.eproc.estimate.model.WorkEstimateLeadCharges;
//import com.dxc.eproc.estimate.repository.WorkEstimateLeadChargesRepository;
//import com.dxc.eproc.estimate.service.dto.WorkEstimateLeadChargesDTO;
//import com.dxc.eproc.estimate.service.mapper.WorkEstimateLeadChargesMapper;
//
///**
// * Integration tests for the {@link WorkEstimateLeadChargesResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class WorkEstimateLeadChargesResourceIT {
//
//    private static final Long DEFAULT_WORK_ESTIMATE_ID = 1L;
//    private static final Long UPDATED_WORK_ESTIMATE_ID = 2L;
//
//    private static final Long DEFAULT_CAT_WORK_SOR_ITEM_ID = 1L;
//    private static final Long UPDATED_CAT_WORK_SOR_ITEM_ID = 2L;
//
//    private static final Long DEFAULT_MATERIAL_MASTER_ID = 1L;
//    private static final Long UPDATED_MATERIAL_MASTER_ID = 2L;
//
//    private static final Long DEFAULT_NHWY_ROAD_TYPE_MASTER_ID = 1L;
//    private static final Long UPDATED_NHWY_ROAD_TYPE_MASTER_ID = 2L;
//
//    private static final Long DEFAULT_SUB_ESTIMATE_ID = 1L;
//    private static final Long UPDATED_SUB_ESTIMATE_ID = 2L;
//
//    private static final String DEFAULT_QUARRY = "AAAAAAAAAA";
//    private static final String UPDATED_QUARRY = "BBBBBBBBBB";
//
//    private static final BigDecimal DEFAULT_LEAD_IN_M = new BigDecimal(0);
//    private static final BigDecimal UPDATED_LEAD_IN_M = new BigDecimal(1);
//
//    private static final BigDecimal DEFAULT_LEAD_IN_KM = new BigDecimal(0);
//    private static final BigDecimal UPDATED_LEAD_IN_KM = new BigDecimal(1);
//
//    private static final BigDecimal DEFAULT_LEAD_CHARGES_M = new BigDecimal(1);
//    private static final BigDecimal UPDATED_LEAD_CHARGES_M = new BigDecimal(2);
//
//    private static final BigDecimal DEFAULT_LEAD_CHARGES_KM = new BigDecimal(1);
//    private static final BigDecimal UPDATED_LEAD_CHARGES_KM = new BigDecimal(2);
//
//    private static final Boolean DEFAULT_INITIAL_LEAD_REQUIRED_YN = false;
//    private static final Boolean UPDATED_INITIAL_LEAD_REQUIRED_YN = true;
//
//    private static final String ENTITY_API_URL = "/api/work-estimate-lead-charges";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private WorkEstimateLeadChargesRepository workEstimateLeadChargesRepository;
//
//    @Autowired
//    private WorkEstimateLeadChargesMapper workEstimateLeadChargesMapper;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restWorkEstimateLeadChargesMockMvc;
//
//    private WorkEstimateLeadCharges workEstimateLeadCharges;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static WorkEstimateLeadCharges createEntity(EntityManager em) {
//        WorkEstimateLeadCharges workEstimateLeadCharges = new WorkEstimateLeadCharges()
//            .workEstimateId(DEFAULT_WORK_ESTIMATE_ID)
//            .catWorkSorItemId(DEFAULT_CAT_WORK_SOR_ITEM_ID)
//            .materialMasterId(DEFAULT_MATERIAL_MASTER_ID)
//            .nhwyRoadTypeMasterId(DEFAULT_NHWY_ROAD_TYPE_MASTER_ID)
//            .subEstimateId(DEFAULT_SUB_ESTIMATE_ID)
//            .quarry(DEFAULT_QUARRY)
//            .leadInM(DEFAULT_LEAD_IN_M)
//            .leadInKm(DEFAULT_LEAD_IN_KM)
//            .leadChargesM(DEFAULT_LEAD_CHARGES_M)
//            .leadChargesKm(DEFAULT_LEAD_CHARGES_KM)
//            .initialLeadRequiredYn(DEFAULT_INITIAL_LEAD_REQUIRED_YN);
//        return workEstimateLeadCharges;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static WorkEstimateLeadCharges createUpdatedEntity(EntityManager em) {
//        WorkEstimateLeadCharges workEstimateLeadCharges = new WorkEstimateLeadCharges()
//            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
//            .catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID)
//            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
//            .nhwyRoadTypeMasterId(UPDATED_NHWY_ROAD_TYPE_MASTER_ID)
//            .subEstimateId(UPDATED_SUB_ESTIMATE_ID)
//            .quarry(UPDATED_QUARRY)
//            .leadInM(UPDATED_LEAD_IN_M)
//            .leadInKm(UPDATED_LEAD_IN_KM)
//            .leadChargesM(UPDATED_LEAD_CHARGES_M)
//            .leadChargesKm(UPDATED_LEAD_CHARGES_KM)
//            .initialLeadRequiredYn(UPDATED_INITIAL_LEAD_REQUIRED_YN);
//        return workEstimateLeadCharges;
//    }
//
//    @BeforeMethod
//    public void initTest() {
//        workEstimateLeadCharges = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createWorkEstimateLeadCharges() throws Exception {
//        int databaseSizeBeforeCreate = workEstimateLeadChargesRepository.findAll().size();
//        // Create the WorkEstimateLeadCharges
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isCreated());
//
//        // Validate the WorkEstimateLeadCharges in the database
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeCreate + 1);
//        WorkEstimateLeadCharges testWorkEstimateLeadCharges = workEstimateLeadChargesList.get(workEstimateLeadChargesList.size() - 1);
//        assertThat(testWorkEstimateLeadCharges.getWorkEstimateId()).isEqualTo(DEFAULT_WORK_ESTIMATE_ID);
//        assertThat(testWorkEstimateLeadCharges.getCatWorkSorItemId()).isEqualTo(DEFAULT_CAT_WORK_SOR_ITEM_ID);
//        assertThat(testWorkEstimateLeadCharges.getMaterialMasterId()).isEqualTo(DEFAULT_MATERIAL_MASTER_ID);
//        assertThat(testWorkEstimateLeadCharges.getNhwyRoadTypeMasterId()).isEqualTo(DEFAULT_NHWY_ROAD_TYPE_MASTER_ID);
//        assertThat(testWorkEstimateLeadCharges.getSubEstimateId()).isEqualTo(DEFAULT_SUB_ESTIMATE_ID);
//        assertThat(testWorkEstimateLeadCharges.getQuarry()).isEqualTo(DEFAULT_QUARRY);
//        assertThat(testWorkEstimateLeadCharges.getLeadInM()).isEqualByComparingTo(DEFAULT_LEAD_IN_M);
//        assertThat(testWorkEstimateLeadCharges.getLeadInKm()).isEqualByComparingTo(DEFAULT_LEAD_IN_KM);
//        assertThat(testWorkEstimateLeadCharges.getLeadChargesM()).isEqualByComparingTo(DEFAULT_LEAD_CHARGES_M);
//        assertThat(testWorkEstimateLeadCharges.getLeadChargesKm()).isEqualByComparingTo(DEFAULT_LEAD_CHARGES_KM);
//        assertThat(testWorkEstimateLeadCharges.getInitialLeadRequiredYn()).isEqualTo(DEFAULT_INITIAL_LEAD_REQUIRED_YN);
//    }
//
//    @Test
//    @Transactional
//    void createWorkEstimateLeadChargesWithExistingId() throws Exception {
//        // Create the WorkEstimateLeadCharges with an existing ID
//        workEstimateLeadCharges.setId(1L);
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
//
//        int databaseSizeBeforeCreate = workEstimateLeadChargesRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLeadCharges in the database
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkWorkEstimateIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLeadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLeadCharges.setWorkEstimateId(null);
//
//        // Create the WorkEstimateLeadCharges, which fails.
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
//
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkCatWorkSorItemIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLeadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLeadCharges.setCatWorkSorItemId(null);
//
//        // Create the WorkEstimateLeadCharges, which fails.
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
//
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkMaterialMasterIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLeadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLeadCharges.setMaterialMasterId(null);
//
//        // Create the WorkEstimateLeadCharges, which fails.
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
//
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkSubEstimateIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLeadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLeadCharges.setSubEstimateId(null);
//
//        // Create the WorkEstimateLeadCharges, which fails.
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
//
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkInitialLeadRequiredYnIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLeadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLeadCharges.setInitialLeadRequiredYn(null);
//
//        // Create the WorkEstimateLeadCharges, which fails.
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
//
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllWorkEstimateLeadCharges() throws Exception {
//        // Initialize the database
//        workEstimateLeadChargesRepository.saveAndFlush(workEstimateLeadCharges);
//
//        // Get all the workEstimateLeadChargesList
//        restWorkEstimateLeadChargesMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(workEstimateLeadCharges.getId().intValue())))
//            .andExpect(jsonPath("$.[*].workEstimateId").value(hasItem(DEFAULT_WORK_ESTIMATE_ID.intValue())))
//            .andExpect(jsonPath("$.[*].catWorkSorItemId").value(hasItem(DEFAULT_CAT_WORK_SOR_ITEM_ID.intValue())))
//            .andExpect(jsonPath("$.[*].materialMasterId").value(hasItem(DEFAULT_MATERIAL_MASTER_ID.intValue())))
//            .andExpect(jsonPath("$.[*].nhwyRoadTypeMasterId").value(hasItem(DEFAULT_NHWY_ROAD_TYPE_MASTER_ID.intValue())))
//            .andExpect(jsonPath("$.[*].subEstimateId").value(hasItem(DEFAULT_SUB_ESTIMATE_ID.intValue())))
//            .andExpect(jsonPath("$.[*].quarry").value(hasItem(DEFAULT_QUARRY)))
//            .andExpect(jsonPath("$.[*].leadInM").value(hasItem(TestUtil.sameNumber(DEFAULT_LEAD_IN_M))))
//            .andExpect(jsonPath("$.[*].leadInKm").value(hasItem(TestUtil.sameNumber(DEFAULT_LEAD_IN_KM))))
//            .andExpect(jsonPath("$.[*].leadChargesM").value(hasItem(TestUtil.sameNumber(DEFAULT_LEAD_CHARGES_M))))
//            .andExpect(jsonPath("$.[*].leadChargesKm").value(hasItem(TestUtil.sameNumber(DEFAULT_LEAD_CHARGES_KM))))
//            .andExpect(jsonPath("$.[*].initialLeadRequiredYn").value(hasItem(DEFAULT_INITIAL_LEAD_REQUIRED_YN.booleanValue())));
//    }
//
//    @Test
//    @Transactional
//    void getWorkEstimateLeadCharges() throws Exception {
//        // Initialize the database
//        workEstimateLeadChargesRepository.saveAndFlush(workEstimateLeadCharges);
//
//        // Get the workEstimateLeadCharges
//        restWorkEstimateLeadChargesMockMvc
//            .perform(get(ENTITY_API_URL_ID, workEstimateLeadCharges.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(workEstimateLeadCharges.getId().intValue()))
//            .andExpect(jsonPath("$.workEstimateId").value(DEFAULT_WORK_ESTIMATE_ID.intValue()))
//            .andExpect(jsonPath("$.catWorkSorItemId").value(DEFAULT_CAT_WORK_SOR_ITEM_ID.intValue()))
//            .andExpect(jsonPath("$.materialMasterId").value(DEFAULT_MATERIAL_MASTER_ID.intValue()))
//            .andExpect(jsonPath("$.nhwyRoadTypeMasterId").value(DEFAULT_NHWY_ROAD_TYPE_MASTER_ID.intValue()))
//            .andExpect(jsonPath("$.subEstimateId").value(DEFAULT_SUB_ESTIMATE_ID.intValue()))
//            .andExpect(jsonPath("$.quarry").value(DEFAULT_QUARRY))
//            .andExpect(jsonPath("$.leadInM").value(TestUtil.sameNumber(DEFAULT_LEAD_IN_M)))
//            .andExpect(jsonPath("$.leadInKm").value(TestUtil.sameNumber(DEFAULT_LEAD_IN_KM)))
//            .andExpect(jsonPath("$.leadChargesM").value(TestUtil.sameNumber(DEFAULT_LEAD_CHARGES_M)))
//            .andExpect(jsonPath("$.leadChargesKm").value(TestUtil.sameNumber(DEFAULT_LEAD_CHARGES_KM)))
//            .andExpect(jsonPath("$.initialLeadRequiredYn").value(DEFAULT_INITIAL_LEAD_REQUIRED_YN.booleanValue()));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingWorkEstimateLeadCharges() throws Exception {
//        // Get the workEstimateLeadCharges
//        restWorkEstimateLeadChargesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putNewWorkEstimateLeadCharges() throws Exception {
//        // Initialize the database
//        workEstimateLeadChargesRepository.saveAndFlush(workEstimateLeadCharges);
//
//        int databaseSizeBeforeUpdate = workEstimateLeadChargesRepository.findAll().size();
//
//        // Update the workEstimateLeadCharges
//        WorkEstimateLeadCharges updatedWorkEstimateLeadCharges = workEstimateLeadChargesRepository
//            .findById(workEstimateLeadCharges.getId())
//            .get();
//        // Disconnect from session so that the updates on updatedWorkEstimateLeadCharges are not directly saved in db
//        em.detach(updatedWorkEstimateLeadCharges);
//        updatedWorkEstimateLeadCharges
//            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
//            .catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID)
//            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
//            .nhwyRoadTypeMasterId(UPDATED_NHWY_ROAD_TYPE_MASTER_ID)
//            .subEstimateId(UPDATED_SUB_ESTIMATE_ID)
//            .quarry(UPDATED_QUARRY)
//            .leadInM(UPDATED_LEAD_IN_M)
//            .leadInKm(UPDATED_LEAD_IN_KM)
//            .leadChargesM(UPDATED_LEAD_CHARGES_M)
//            .leadChargesKm(UPDATED_LEAD_CHARGES_KM)
//            .initialLeadRequiredYn(UPDATED_INITIAL_LEAD_REQUIRED_YN);
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(updatedWorkEstimateLeadCharges);
//
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, workEstimateLeadChargesDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WorkEstimateLeadCharges in the database
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeUpdate);
//        WorkEstimateLeadCharges testWorkEstimateLeadCharges = workEstimateLeadChargesList.get(workEstimateLeadChargesList.size() - 1);
//        assertThat(testWorkEstimateLeadCharges.getWorkEstimateId()).isEqualTo(UPDATED_WORK_ESTIMATE_ID);
//        assertThat(testWorkEstimateLeadCharges.getCatWorkSorItemId()).isEqualTo(UPDATED_CAT_WORK_SOR_ITEM_ID);
//        assertThat(testWorkEstimateLeadCharges.getMaterialMasterId()).isEqualTo(UPDATED_MATERIAL_MASTER_ID);
//        assertThat(testWorkEstimateLeadCharges.getNhwyRoadTypeMasterId()).isEqualTo(UPDATED_NHWY_ROAD_TYPE_MASTER_ID);
//        assertThat(testWorkEstimateLeadCharges.getSubEstimateId()).isEqualTo(UPDATED_SUB_ESTIMATE_ID);
//        assertThat(testWorkEstimateLeadCharges.getQuarry()).isEqualTo(UPDATED_QUARRY);
//        assertThat(testWorkEstimateLeadCharges.getLeadInM()).isEqualTo(UPDATED_LEAD_IN_M);
//        assertThat(testWorkEstimateLeadCharges.getLeadInKm()).isEqualTo(UPDATED_LEAD_IN_KM);
//        assertThat(testWorkEstimateLeadCharges.getLeadChargesM()).isEqualTo(UPDATED_LEAD_CHARGES_M);
//        assertThat(testWorkEstimateLeadCharges.getLeadChargesKm()).isEqualTo(UPDATED_LEAD_CHARGES_KM);
//        assertThat(testWorkEstimateLeadCharges.getInitialLeadRequiredYn()).isEqualTo(UPDATED_INITIAL_LEAD_REQUIRED_YN);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingWorkEstimateLeadCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLeadChargesRepository.findAll().size();
//        workEstimateLeadCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLeadCharges
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, workEstimateLeadChargesDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLeadCharges in the database
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchWorkEstimateLeadCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLeadChargesRepository.findAll().size();
//        workEstimateLeadCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLeadCharges
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLeadCharges in the database
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamWorkEstimateLeadCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLeadChargesRepository.findAll().size();
//        workEstimateLeadCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLeadCharges
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the WorkEstimateLeadCharges in the database
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateWorkEstimateLeadChargesWithPatch() throws Exception {
//        // Initialize the database
//        workEstimateLeadChargesRepository.saveAndFlush(workEstimateLeadCharges);
//
//        int databaseSizeBeforeUpdate = workEstimateLeadChargesRepository.findAll().size();
//
//        // Update the workEstimateLeadCharges using partial update
//        WorkEstimateLeadCharges partialUpdatedWorkEstimateLeadCharges = new WorkEstimateLeadCharges();
//        partialUpdatedWorkEstimateLeadCharges.setId(workEstimateLeadCharges.getId());
//
//        partialUpdatedWorkEstimateLeadCharges
//            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
//            .catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID)
//            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
//            .quarry(UPDATED_QUARRY)
//            .leadInM(UPDATED_LEAD_IN_M)
//            .leadInKm(UPDATED_LEAD_IN_KM)
//            .leadChargesKm(UPDATED_LEAD_CHARGES_KM);
//
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedWorkEstimateLeadCharges.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimateLeadCharges))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WorkEstimateLeadCharges in the database
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeUpdate);
//        WorkEstimateLeadCharges testWorkEstimateLeadCharges = workEstimateLeadChargesList.get(workEstimateLeadChargesList.size() - 1);
//        assertThat(testWorkEstimateLeadCharges.getWorkEstimateId()).isEqualTo(UPDATED_WORK_ESTIMATE_ID);
//        assertThat(testWorkEstimateLeadCharges.getCatWorkSorItemId()).isEqualTo(UPDATED_CAT_WORK_SOR_ITEM_ID);
//        assertThat(testWorkEstimateLeadCharges.getMaterialMasterId()).isEqualTo(UPDATED_MATERIAL_MASTER_ID);
//        assertThat(testWorkEstimateLeadCharges.getNhwyRoadTypeMasterId()).isEqualTo(DEFAULT_NHWY_ROAD_TYPE_MASTER_ID);
//        assertThat(testWorkEstimateLeadCharges.getSubEstimateId()).isEqualTo(DEFAULT_SUB_ESTIMATE_ID);
//        assertThat(testWorkEstimateLeadCharges.getQuarry()).isEqualTo(UPDATED_QUARRY);
//        assertThat(testWorkEstimateLeadCharges.getLeadInM()).isEqualByComparingTo(UPDATED_LEAD_IN_M);
//        assertThat(testWorkEstimateLeadCharges.getLeadInKm()).isEqualByComparingTo(UPDATED_LEAD_IN_KM);
//        assertThat(testWorkEstimateLeadCharges.getLeadChargesM()).isEqualByComparingTo(DEFAULT_LEAD_CHARGES_M);
//        assertThat(testWorkEstimateLeadCharges.getLeadChargesKm()).isEqualByComparingTo(UPDATED_LEAD_CHARGES_KM);
//        assertThat(testWorkEstimateLeadCharges.getInitialLeadRequiredYn()).isEqualTo(DEFAULT_INITIAL_LEAD_REQUIRED_YN);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateWorkEstimateLeadChargesWithPatch() throws Exception {
//        // Initialize the database
//        workEstimateLeadChargesRepository.saveAndFlush(workEstimateLeadCharges);
//
//        int databaseSizeBeforeUpdate = workEstimateLeadChargesRepository.findAll().size();
//
//        // Update the workEstimateLeadCharges using partial update
//        WorkEstimateLeadCharges partialUpdatedWorkEstimateLeadCharges = new WorkEstimateLeadCharges();
//        partialUpdatedWorkEstimateLeadCharges.setId(workEstimateLeadCharges.getId());
//
//        partialUpdatedWorkEstimateLeadCharges
//            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
//            .catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID)
//            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
//            .nhwyRoadTypeMasterId(UPDATED_NHWY_ROAD_TYPE_MASTER_ID)
//            .subEstimateId(UPDATED_SUB_ESTIMATE_ID)
//            .quarry(UPDATED_QUARRY)
//            .leadInM(UPDATED_LEAD_IN_M)
//            .leadInKm(UPDATED_LEAD_IN_KM)
//            .leadChargesM(UPDATED_LEAD_CHARGES_M)
//            .leadChargesKm(UPDATED_LEAD_CHARGES_KM)
//            .initialLeadRequiredYn(UPDATED_INITIAL_LEAD_REQUIRED_YN);
//
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedWorkEstimateLeadCharges.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimateLeadCharges))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WorkEstimateLeadCharges in the database
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeUpdate);
//        WorkEstimateLeadCharges testWorkEstimateLeadCharges = workEstimateLeadChargesList.get(workEstimateLeadChargesList.size() - 1);
//        assertThat(testWorkEstimateLeadCharges.getWorkEstimateId()).isEqualTo(UPDATED_WORK_ESTIMATE_ID);
//        assertThat(testWorkEstimateLeadCharges.getCatWorkSorItemId()).isEqualTo(UPDATED_CAT_WORK_SOR_ITEM_ID);
//        assertThat(testWorkEstimateLeadCharges.getMaterialMasterId()).isEqualTo(UPDATED_MATERIAL_MASTER_ID);
//        assertThat(testWorkEstimateLeadCharges.getNhwyRoadTypeMasterId()).isEqualTo(UPDATED_NHWY_ROAD_TYPE_MASTER_ID);
//        assertThat(testWorkEstimateLeadCharges.getSubEstimateId()).isEqualTo(UPDATED_SUB_ESTIMATE_ID);
//        assertThat(testWorkEstimateLeadCharges.getQuarry()).isEqualTo(UPDATED_QUARRY);
//        assertThat(testWorkEstimateLeadCharges.getLeadInM()).isEqualByComparingTo(UPDATED_LEAD_IN_M);
//        assertThat(testWorkEstimateLeadCharges.getLeadInKm()).isEqualByComparingTo(UPDATED_LEAD_IN_KM);
//        assertThat(testWorkEstimateLeadCharges.getLeadChargesM()).isEqualByComparingTo(UPDATED_LEAD_CHARGES_M);
//        assertThat(testWorkEstimateLeadCharges.getLeadChargesKm()).isEqualByComparingTo(UPDATED_LEAD_CHARGES_KM);
//        assertThat(testWorkEstimateLeadCharges.getInitialLeadRequiredYn()).isEqualTo(UPDATED_INITIAL_LEAD_REQUIRED_YN);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingWorkEstimateLeadCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLeadChargesRepository.findAll().size();
//        workEstimateLeadCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLeadCharges
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, workEstimateLeadChargesDTO.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLeadCharges in the database
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchWorkEstimateLeadCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLeadChargesRepository.findAll().size();
//        workEstimateLeadCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLeadCharges
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLeadCharges in the database
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamWorkEstimateLeadCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLeadChargesRepository.findAll().size();
//        workEstimateLeadCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLeadCharges
//        WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateLeadChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL)
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLeadChargesDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the WorkEstimateLeadCharges in the database
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteWorkEstimateLeadCharges() throws Exception {
//        // Initialize the database
//        workEstimateLeadChargesRepository.saveAndFlush(workEstimateLeadCharges);
//
//        int databaseSizeBeforeDelete = workEstimateLeadChargesRepository.findAll().size();
//
//        // Delete the workEstimateLeadCharges
//        restWorkEstimateLeadChargesMockMvc
//            .perform(delete(ENTITY_API_URL_ID, workEstimateLeadCharges.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<WorkEstimateLeadCharges> workEstimateLeadChargesList = workEstimateLeadChargesRepository.findAll();
//        assertThat(workEstimateLeadChargesList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
