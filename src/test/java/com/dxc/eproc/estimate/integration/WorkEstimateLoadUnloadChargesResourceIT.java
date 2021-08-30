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
//import com.dxc.eproc.estimate.controller.WorkEstimateLoadUnloadChargesController;
//import com.dxc.eproc.estimate.model.WorkEstimateLoadUnloadCharges;
//import com.dxc.eproc.estimate.repository.WorkEstimateLoadUnloadChargesRepository;
//import com.dxc.eproc.estimate.service.dto.WorkEstimateLoadUnloadChargesDTO;
//import com.dxc.eproc.estimate.service.mapper.WorkEstimateLoadUnloadChargesMapper;
//
///**
// * Integration tests for the {@link WorkEstimateLoadUnloadChargesController} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class WorkEstimateLoadUnloadChargesResourceIT {
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
//    private static final Long DEFAULT_LOAD_UNLOAD_RATE_MASTER_ID = 1L;
//    private static final Long UPDATED_LOAD_UNLOAD_RATE_MASTER_ID = 2L;
//
//    private static final Long DEFAULT_SUB_ESTIMATE_ID = 1L;
//    private static final Long UPDATED_SUB_ESTIMATE_ID = 2L;
//
//    private static final Boolean DEFAULT_SELECTED_LOAD_CHARGES = false;
//    private static final Boolean UPDATED_SELECTED_LOAD_CHARGES = true;
//
//    private static final BigDecimal DEFAULT_LOADING_CHARGES = new BigDecimal(1);
//    private static final BigDecimal UPDATED_LOADING_CHARGES = new BigDecimal(2);
//
//    private static final Boolean DEFAULT_SELECTED_UNLOAD_CHARGES = false;
//    private static final Boolean UPDATED_SELECTED_UNLOAD_CHARGES = true;
//
//    private static final BigDecimal DEFAULT_UNLOADING_CHARGES = new BigDecimal(1);
//    private static final BigDecimal UPDATED_UNLOADING_CHARGES = new BigDecimal(2);
//
//    private static final String ENTITY_API_URL = "/api/work-estimate-load-unload-charges";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private WorkEstimateLoadUnloadChargesRepository workEstimateLoadUnloadChargesRepository;
//
//    @Autowired
//    private WorkEstimateLoadUnloadChargesMapper workEstimateLoadUnloadChargesMapper;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restWorkEstimateLoadUnloadChargesMockMvc;
//
//    private WorkEstimateLoadUnloadCharges workEstimateLoadUnloadCharges;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static WorkEstimateLoadUnloadCharges createEntity(EntityManager em) {
//        WorkEstimateLoadUnloadCharges workEstimateLoadUnloadCharges = new WorkEstimateLoadUnloadCharges()
//            .workEstimateId(DEFAULT_WORK_ESTIMATE_ID)
//            .catWorkSorItemId(DEFAULT_CAT_WORK_SOR_ITEM_ID)
//            .materialMasterId(DEFAULT_MATERIAL_MASTER_ID)
//            .loadUnloadRateMasterId(DEFAULT_LOAD_UNLOAD_RATE_MASTER_ID)
//            .subEstimateId(DEFAULT_SUB_ESTIMATE_ID)
//            .selectedLoadCharges(DEFAULT_SELECTED_LOAD_CHARGES)
//            .loadingCharges(DEFAULT_LOADING_CHARGES)
//            .selectedUnloadCharges(DEFAULT_SELECTED_UNLOAD_CHARGES)
//            .unloadingCharges(DEFAULT_UNLOADING_CHARGES);
//        return workEstimateLoadUnloadCharges;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static WorkEstimateLoadUnloadCharges createUpdatedEntity(EntityManager em) {
//        WorkEstimateLoadUnloadCharges workEstimateLoadUnloadCharges = new WorkEstimateLoadUnloadCharges()
//            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
//            .catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID)
//            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
//            .loadUnloadRateMasterId(UPDATED_LOAD_UNLOAD_RATE_MASTER_ID)
//            .subEstimateId(UPDATED_SUB_ESTIMATE_ID)
//            .selectedLoadCharges(UPDATED_SELECTED_LOAD_CHARGES)
//            .loadingCharges(UPDATED_LOADING_CHARGES)
//            .selectedUnloadCharges(UPDATED_SELECTED_UNLOAD_CHARGES)
//            .unloadingCharges(UPDATED_UNLOADING_CHARGES);
//        return workEstimateLoadUnloadCharges;
//    }
//
//    @BeforeMethod
//    public void initTest() {
//        workEstimateLoadUnloadCharges = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createWorkEstimateLoadUnloadCharges() throws Exception {
//        int databaseSizeBeforeCreate = workEstimateLoadUnloadChargesRepository.findAll().size();
//        // Create the WorkEstimateLoadUnloadCharges
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isCreated());
//
//        // Validate the WorkEstimateLoadUnloadCharges in the database
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeCreate + 1);
//        WorkEstimateLoadUnloadCharges testWorkEstimateLoadUnloadCharges = workEstimateLoadUnloadChargesList.get(
//            workEstimateLoadUnloadChargesList.size() - 1
//        );
//        assertThat(testWorkEstimateLoadUnloadCharges.getWorkEstimateId()).isEqualTo(DEFAULT_WORK_ESTIMATE_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getCatWorkSorItemId()).isEqualTo(DEFAULT_CAT_WORK_SOR_ITEM_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getMaterialMasterId()).isEqualTo(DEFAULT_MATERIAL_MASTER_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getLoadUnloadRateMasterId()).isEqualTo(DEFAULT_LOAD_UNLOAD_RATE_MASTER_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getSubEstimateId()).isEqualTo(DEFAULT_SUB_ESTIMATE_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getSelectedLoadCharges()).isEqualTo(DEFAULT_SELECTED_LOAD_CHARGES);
//        assertThat(testWorkEstimateLoadUnloadCharges.getLoadingCharges()).isEqualByComparingTo(DEFAULT_LOADING_CHARGES);
//        assertThat(testWorkEstimateLoadUnloadCharges.getSelectedUnloadCharges()).isEqualTo(DEFAULT_SELECTED_UNLOAD_CHARGES);
//        assertThat(testWorkEstimateLoadUnloadCharges.getUnloadingCharges()).isEqualByComparingTo(DEFAULT_UNLOADING_CHARGES);
//    }
//
//    @Test
//    @Transactional
//    void createWorkEstimateLoadUnloadChargesWithExistingId() throws Exception {
//        // Create the WorkEstimateLoadUnloadCharges with an existing ID
//        workEstimateLoadUnloadCharges.setId(1L);
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        int databaseSizeBeforeCreate = workEstimateLoadUnloadChargesRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLoadUnloadCharges in the database
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkWorkEstimateIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLoadUnloadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLoadUnloadCharges.setWorkEstimateId(null);
//
//        // Create the WorkEstimateLoadUnloadCharges, which fails.
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkCatWorkSorItemIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLoadUnloadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLoadUnloadCharges.setCatWorkSorItemId(null);
//
//        // Create the WorkEstimateLoadUnloadCharges, which fails.
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkMaterialMasterIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLoadUnloadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLoadUnloadCharges.setMaterialMasterId(null);
//
//        // Create the WorkEstimateLoadUnloadCharges, which fails.
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkLoadUnloadRateMasterIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLoadUnloadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLoadUnloadCharges.setLoadUnloadRateMasterId(null);
//
//        // Create the WorkEstimateLoadUnloadCharges, which fails.
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkSubEstimateIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLoadUnloadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLoadUnloadCharges.setSubEstimateId(null);
//
//        // Create the WorkEstimateLoadUnloadCharges, which fails.
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkSelectedLoadChargesIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLoadUnloadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLoadUnloadCharges.setSelectedLoadCharges(null);
//
//        // Create the WorkEstimateLoadUnloadCharges, which fails.
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkLoadingChargesIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLoadUnloadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLoadUnloadCharges.setLoadingCharges(null);
//
//        // Create the WorkEstimateLoadUnloadCharges, which fails.
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkSelectedUnloadChargesIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLoadUnloadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLoadUnloadCharges.setSelectedUnloadCharges(null);
//
//        // Create the WorkEstimateLoadUnloadCharges, which fails.
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkUnloadingChargesIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLoadUnloadChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLoadUnloadCharges.setUnloadingCharges(null);
//
//        // Create the WorkEstimateLoadUnloadCharges, which fails.
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllWorkEstimateLoadUnloadCharges() throws Exception {
//        // Initialize the database
//        workEstimateLoadUnloadChargesRepository.saveAndFlush(workEstimateLoadUnloadCharges);
//
//        // Get all the workEstimateLoadUnloadChargesList
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(workEstimateLoadUnloadCharges.getId().intValue())))
//            .andExpect(jsonPath("$.[*].workEstimateId").value(hasItem(DEFAULT_WORK_ESTIMATE_ID.intValue())))
//            .andExpect(jsonPath("$.[*].catWorkSorItemId").value(hasItem(DEFAULT_CAT_WORK_SOR_ITEM_ID.intValue())))
//            .andExpect(jsonPath("$.[*].materialMasterId").value(hasItem(DEFAULT_MATERIAL_MASTER_ID.intValue())))
//            .andExpect(jsonPath("$.[*].loadUnloadRateMasterId").value(hasItem(DEFAULT_LOAD_UNLOAD_RATE_MASTER_ID.intValue())))
//            .andExpect(jsonPath("$.[*].subEstimateId").value(hasItem(DEFAULT_SUB_ESTIMATE_ID.intValue())))
//            .andExpect(jsonPath("$.[*].selectedLoadCharges").value(hasItem(DEFAULT_SELECTED_LOAD_CHARGES.booleanValue())))
//            .andExpect(jsonPath("$.[*].loadingCharges").value(hasItem(TestUtil.sameNumber(DEFAULT_LOADING_CHARGES))))
//            .andExpect(jsonPath("$.[*].selectedUnloadCharges").value(hasItem(DEFAULT_SELECTED_UNLOAD_CHARGES.booleanValue())))
//            .andExpect(jsonPath("$.[*].unloadingCharges").value(hasItem(TestUtil.sameNumber(DEFAULT_UNLOADING_CHARGES))));
//    }
//
//    @Test
//    @Transactional
//    void getWorkEstimateLoadUnloadCharges() throws Exception {
//        // Initialize the database
//        workEstimateLoadUnloadChargesRepository.saveAndFlush(workEstimateLoadUnloadCharges);
//
//        // Get the workEstimateLoadUnloadCharges
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(get(ENTITY_API_URL_ID, workEstimateLoadUnloadCharges.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(workEstimateLoadUnloadCharges.getId().intValue()))
//            .andExpect(jsonPath("$.workEstimateId").value(DEFAULT_WORK_ESTIMATE_ID.intValue()))
//            .andExpect(jsonPath("$.catWorkSorItemId").value(DEFAULT_CAT_WORK_SOR_ITEM_ID.intValue()))
//            .andExpect(jsonPath("$.materialMasterId").value(DEFAULT_MATERIAL_MASTER_ID.intValue()))
//            .andExpect(jsonPath("$.loadUnloadRateMasterId").value(DEFAULT_LOAD_UNLOAD_RATE_MASTER_ID.intValue()))
//            .andExpect(jsonPath("$.subEstimateId").value(DEFAULT_SUB_ESTIMATE_ID.intValue()))
//            .andExpect(jsonPath("$.selectedLoadCharges").value(DEFAULT_SELECTED_LOAD_CHARGES.booleanValue()))
//            .andExpect(jsonPath("$.loadingCharges").value(TestUtil.sameNumber(DEFAULT_LOADING_CHARGES)))
//            .andExpect(jsonPath("$.selectedUnloadCharges").value(DEFAULT_SELECTED_UNLOAD_CHARGES.booleanValue()))
//            .andExpect(jsonPath("$.unloadingCharges").value(TestUtil.sameNumber(DEFAULT_UNLOADING_CHARGES)));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingWorkEstimateLoadUnloadCharges() throws Exception {
//        // Get the workEstimateLoadUnloadCharges
//        restWorkEstimateLoadUnloadChargesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putNewWorkEstimateLoadUnloadCharges() throws Exception {
//        // Initialize the database
//        workEstimateLoadUnloadChargesRepository.saveAndFlush(workEstimateLoadUnloadCharges);
//
//        int databaseSizeBeforeUpdate = workEstimateLoadUnloadChargesRepository.findAll().size();
//
//        // Update the workEstimateLoadUnloadCharges
//        WorkEstimateLoadUnloadCharges updatedWorkEstimateLoadUnloadCharges = workEstimateLoadUnloadChargesRepository
//            .findById(workEstimateLoadUnloadCharges.getId())
//            .get();
//        // Disconnect from session so that the updates on updatedWorkEstimateLoadUnloadCharges are not directly saved in db
//        em.detach(updatedWorkEstimateLoadUnloadCharges);
//        updatedWorkEstimateLoadUnloadCharges
//            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
//            .catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID)
//            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
//            .loadUnloadRateMasterId(UPDATED_LOAD_UNLOAD_RATE_MASTER_ID)
//            .subEstimateId(UPDATED_SUB_ESTIMATE_ID)
//            .selectedLoadCharges(UPDATED_SELECTED_LOAD_CHARGES)
//            .loadingCharges(UPDATED_LOADING_CHARGES)
//            .selectedUnloadCharges(UPDATED_SELECTED_UNLOAD_CHARGES)
//            .unloadingCharges(UPDATED_UNLOADING_CHARGES);
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            updatedWorkEstimateLoadUnloadCharges
//        );
//
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, workEstimateLoadUnloadChargesDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WorkEstimateLoadUnloadCharges in the database
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeUpdate);
//        WorkEstimateLoadUnloadCharges testWorkEstimateLoadUnloadCharges = workEstimateLoadUnloadChargesList.get(
//            workEstimateLoadUnloadChargesList.size() - 1
//        );
//        assertThat(testWorkEstimateLoadUnloadCharges.getWorkEstimateId()).isEqualTo(UPDATED_WORK_ESTIMATE_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getCatWorkSorItemId()).isEqualTo(UPDATED_CAT_WORK_SOR_ITEM_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getMaterialMasterId()).isEqualTo(UPDATED_MATERIAL_MASTER_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getLoadUnloadRateMasterId()).isEqualTo(UPDATED_LOAD_UNLOAD_RATE_MASTER_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getSubEstimateId()).isEqualTo(UPDATED_SUB_ESTIMATE_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getSelectedLoadCharges()).isEqualTo(UPDATED_SELECTED_LOAD_CHARGES);
//        assertThat(testWorkEstimateLoadUnloadCharges.getLoadingCharges()).isEqualTo(UPDATED_LOADING_CHARGES);
//        assertThat(testWorkEstimateLoadUnloadCharges.getSelectedUnloadCharges()).isEqualTo(UPDATED_SELECTED_UNLOAD_CHARGES);
//        assertThat(testWorkEstimateLoadUnloadCharges.getUnloadingCharges()).isEqualTo(UPDATED_UNLOADING_CHARGES);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingWorkEstimateLoadUnloadCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLoadUnloadChargesRepository.findAll().size();
//        workEstimateLoadUnloadCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLoadUnloadCharges
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, workEstimateLoadUnloadChargesDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLoadUnloadCharges in the database
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchWorkEstimateLoadUnloadCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLoadUnloadChargesRepository.findAll().size();
//        workEstimateLoadUnloadCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLoadUnloadCharges
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLoadUnloadCharges in the database
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamWorkEstimateLoadUnloadCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLoadUnloadChargesRepository.findAll().size();
//        workEstimateLoadUnloadCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLoadUnloadCharges
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the WorkEstimateLoadUnloadCharges in the database
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateWorkEstimateLoadUnloadChargesWithPatch() throws Exception {
//        // Initialize the database
//        workEstimateLoadUnloadChargesRepository.saveAndFlush(workEstimateLoadUnloadCharges);
//
//        int databaseSizeBeforeUpdate = workEstimateLoadUnloadChargesRepository.findAll().size();
//
//        // Update the workEstimateLoadUnloadCharges using partial update
//        WorkEstimateLoadUnloadCharges partialUpdatedWorkEstimateLoadUnloadCharges = new WorkEstimateLoadUnloadCharges();
//        partialUpdatedWorkEstimateLoadUnloadCharges.setId(workEstimateLoadUnloadCharges.getId());
//
//        partialUpdatedWorkEstimateLoadUnloadCharges
//            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
//            .selectedLoadCharges(UPDATED_SELECTED_LOAD_CHARGES)
//            .unloadingCharges(UPDATED_UNLOADING_CHARGES);
//
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedWorkEstimateLoadUnloadCharges.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimateLoadUnloadCharges))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WorkEstimateLoadUnloadCharges in the database
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeUpdate);
//        WorkEstimateLoadUnloadCharges testWorkEstimateLoadUnloadCharges = workEstimateLoadUnloadChargesList.get(
//            workEstimateLoadUnloadChargesList.size() - 1
//        );
//        assertThat(testWorkEstimateLoadUnloadCharges.getWorkEstimateId()).isEqualTo(UPDATED_WORK_ESTIMATE_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getCatWorkSorItemId()).isEqualTo(DEFAULT_CAT_WORK_SOR_ITEM_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getMaterialMasterId()).isEqualTo(DEFAULT_MATERIAL_MASTER_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getLoadUnloadRateMasterId()).isEqualTo(DEFAULT_LOAD_UNLOAD_RATE_MASTER_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getSubEstimateId()).isEqualTo(DEFAULT_SUB_ESTIMATE_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getSelectedLoadCharges()).isEqualTo(UPDATED_SELECTED_LOAD_CHARGES);
//        assertThat(testWorkEstimateLoadUnloadCharges.getLoadingCharges()).isEqualByComparingTo(DEFAULT_LOADING_CHARGES);
//        assertThat(testWorkEstimateLoadUnloadCharges.getSelectedUnloadCharges()).isEqualTo(DEFAULT_SELECTED_UNLOAD_CHARGES);
//        assertThat(testWorkEstimateLoadUnloadCharges.getUnloadingCharges()).isEqualByComparingTo(UPDATED_UNLOADING_CHARGES);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateWorkEstimateLoadUnloadChargesWithPatch() throws Exception {
//        // Initialize the database
//        workEstimateLoadUnloadChargesRepository.saveAndFlush(workEstimateLoadUnloadCharges);
//
//        int databaseSizeBeforeUpdate = workEstimateLoadUnloadChargesRepository.findAll().size();
//
//        // Update the workEstimateLoadUnloadCharges using partial update
//        WorkEstimateLoadUnloadCharges partialUpdatedWorkEstimateLoadUnloadCharges = new WorkEstimateLoadUnloadCharges();
//        partialUpdatedWorkEstimateLoadUnloadCharges.setId(workEstimateLoadUnloadCharges.getId());
//
//        partialUpdatedWorkEstimateLoadUnloadCharges
//            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
//            .catWorkSorItemId(UPDATED_CAT_WORK_SOR_ITEM_ID)
//            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
//            .loadUnloadRateMasterId(UPDATED_LOAD_UNLOAD_RATE_MASTER_ID)
//            .subEstimateId(UPDATED_SUB_ESTIMATE_ID)
//            .selectedLoadCharges(UPDATED_SELECTED_LOAD_CHARGES)
//            .loadingCharges(UPDATED_LOADING_CHARGES)
//            .selectedUnloadCharges(UPDATED_SELECTED_UNLOAD_CHARGES)
//            .unloadingCharges(UPDATED_UNLOADING_CHARGES);
//
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedWorkEstimateLoadUnloadCharges.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimateLoadUnloadCharges))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WorkEstimateLoadUnloadCharges in the database
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeUpdate);
//        WorkEstimateLoadUnloadCharges testWorkEstimateLoadUnloadCharges = workEstimateLoadUnloadChargesList.get(
//            workEstimateLoadUnloadChargesList.size() - 1
//        );
//        assertThat(testWorkEstimateLoadUnloadCharges.getWorkEstimateId()).isEqualTo(UPDATED_WORK_ESTIMATE_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getCatWorkSorItemId()).isEqualTo(UPDATED_CAT_WORK_SOR_ITEM_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getMaterialMasterId()).isEqualTo(UPDATED_MATERIAL_MASTER_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getLoadUnloadRateMasterId()).isEqualTo(UPDATED_LOAD_UNLOAD_RATE_MASTER_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getSubEstimateId()).isEqualTo(UPDATED_SUB_ESTIMATE_ID);
//        assertThat(testWorkEstimateLoadUnloadCharges.getSelectedLoadCharges()).isEqualTo(UPDATED_SELECTED_LOAD_CHARGES);
//        assertThat(testWorkEstimateLoadUnloadCharges.getLoadingCharges()).isEqualByComparingTo(UPDATED_LOADING_CHARGES);
//        assertThat(testWorkEstimateLoadUnloadCharges.getSelectedUnloadCharges()).isEqualTo(UPDATED_SELECTED_UNLOAD_CHARGES);
//        assertThat(testWorkEstimateLoadUnloadCharges.getUnloadingCharges()).isEqualByComparingTo(UPDATED_UNLOADING_CHARGES);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingWorkEstimateLoadUnloadCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLoadUnloadChargesRepository.findAll().size();
//        workEstimateLoadUnloadCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLoadUnloadCharges
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, workEstimateLoadUnloadChargesDTO.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLoadUnloadCharges in the database
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchWorkEstimateLoadUnloadCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLoadUnloadChargesRepository.findAll().size();
//        workEstimateLoadUnloadCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLoadUnloadCharges
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLoadUnloadCharges in the database
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamWorkEstimateLoadUnloadCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLoadUnloadChargesRepository.findAll().size();
//        workEstimateLoadUnloadCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLoadUnloadCharges
//        WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesMapper.toDto(
//            workEstimateLoadUnloadCharges
//        );
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL)
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLoadUnloadChargesDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the WorkEstimateLoadUnloadCharges in the database
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteWorkEstimateLoadUnloadCharges() throws Exception {
//        // Initialize the database
//        workEstimateLoadUnloadChargesRepository.saveAndFlush(workEstimateLoadUnloadCharges);
//
//        int databaseSizeBeforeDelete = workEstimateLoadUnloadChargesRepository.findAll().size();
//
//        // Delete the workEstimateLoadUnloadCharges
//        restWorkEstimateLoadUnloadChargesMockMvc
//            .perform(delete(ENTITY_API_URL_ID, workEstimateLoadUnloadCharges.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<WorkEstimateLoadUnloadCharges> workEstimateLoadUnloadChargesList = workEstimateLoadUnloadChargesRepository.findAll();
//        assertThat(workEstimateLoadUnloadChargesList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
