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
//import com.dxc.eproc.estimate.controller.WorkEstimateLiftChargesController;
//import com.dxc.eproc.estimate.model.WorkEstimateLiftCharges;
//import com.dxc.eproc.estimate.repository.WorkEstimateLiftChargesRepository;
//import com.dxc.eproc.estimate.service.dto.WorkEstimateLiftChargesDTO;
//import com.dxc.eproc.estimate.service.mapper.WorkEstimateLiftChargesMapper;
//
///**
// * Integration tests for the {@link WorkEstimateLiftChargesController} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class WorkEstimateLiftChargesResourceIT {
//
//    private static final Long DEFAULT_WORK_ESTIMATE_ITEM_ID = 1L;
//    private static final Long UPDATED_WORK_ESTIMATE_ITEM_ID = 2L;
//
//    private static final Long DEFAULT_MATERIAL_MASTER_ID = 1L;
//    private static final Long UPDATED_MATERIAL_MASTER_ID = 2L;
//
//    private static final BigDecimal DEFAULT_LIFT_DISTANCE = new BigDecimal(0);
//    private static final BigDecimal UPDATED_LIFT_DISTANCE = new BigDecimal(1);
//
//    private static final BigDecimal DEFAULT_LIFTCHARGES = new BigDecimal(1);
//    private static final BigDecimal UPDATED_LIFTCHARGES = new BigDecimal(2);
//
//    private static final String ENTITY_API_URL = "/api/work-estimate-lift-charges";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private WorkEstimateLiftChargesRepository workEstimateLiftChargesRepository;
//
//    @Autowired
//    private WorkEstimateLiftChargesMapper workEstimateLiftChargesMapper;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restWorkEstimateLiftChargesMockMvc;
//
//    private WorkEstimateLiftCharges workEstimateLiftCharges;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static WorkEstimateLiftCharges createEntity(EntityManager em) {
//        WorkEstimateLiftCharges workEstimateLiftCharges = new WorkEstimateLiftCharges()
//            .workEstimateItemId(DEFAULT_WORK_ESTIMATE_ITEM_ID)
//            .materialMasterId(DEFAULT_MATERIAL_MASTER_ID)
//            .liftDistance(DEFAULT_LIFT_DISTANCE)
//            .liftcharges(DEFAULT_LIFTCHARGES);
//        return workEstimateLiftCharges;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static WorkEstimateLiftCharges createUpdatedEntity(EntityManager em) {
//        WorkEstimateLiftCharges workEstimateLiftCharges = new WorkEstimateLiftCharges()
//            .workEstimateItemId(UPDATED_WORK_ESTIMATE_ITEM_ID)
//            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
//            .liftDistance(UPDATED_LIFT_DISTANCE)
//            .liftcharges(UPDATED_LIFTCHARGES);
//        return workEstimateLiftCharges;
//    }
//
//    @BeforeMethod
//    public void initTest() {
//        workEstimateLiftCharges = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createWorkEstimateLiftCharges() throws Exception {
//        int databaseSizeBeforeCreate = workEstimateLiftChargesRepository.findAll().size();
//        // Create the WorkEstimateLiftCharges
//        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesMapper.toDto(workEstimateLiftCharges);
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLiftChargesDTO))
//            )
//            .andExpect(status().isCreated());
//
//        // Validate the WorkEstimateLiftCharges in the database
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeCreate + 1);
//        WorkEstimateLiftCharges testWorkEstimateLiftCharges = workEstimateLiftChargesList.get(workEstimateLiftChargesList.size() - 1);
//        assertThat(testWorkEstimateLiftCharges.getWorkEstimateItemId()).isEqualTo(DEFAULT_WORK_ESTIMATE_ITEM_ID);
//        assertThat(testWorkEstimateLiftCharges.getMaterialMasterId()).isEqualTo(DEFAULT_MATERIAL_MASTER_ID);
//        assertThat(testWorkEstimateLiftCharges.getLiftDistance()).isEqualByComparingTo(DEFAULT_LIFT_DISTANCE);
//        assertThat(testWorkEstimateLiftCharges.getLiftcharges()).isEqualByComparingTo(DEFAULT_LIFTCHARGES);
//    }
//
//    @Test
//    @Transactional
//    void createWorkEstimateLiftChargesWithExistingId() throws Exception {
//        // Create the WorkEstimateLiftCharges with an existing ID
//        workEstimateLiftCharges.setId(1L);
//        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesMapper.toDto(workEstimateLiftCharges);
//
//        int databaseSizeBeforeCreate = workEstimateLiftChargesRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLiftChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLiftCharges in the database
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkWorkEstimateItemIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLiftChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLiftCharges.setWorkEstimateItemId(null);
//
//        // Create the WorkEstimateLiftCharges, which fails.
//        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesMapper.toDto(workEstimateLiftCharges);
//
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLiftChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkMaterialMasterIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLiftChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLiftCharges.setMaterialMasterId(null);
//
//        // Create the WorkEstimateLiftCharges, which fails.
//        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesMapper.toDto(workEstimateLiftCharges);
//
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLiftChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkLiftDistanceIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLiftChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLiftCharges.setLiftDistance(null);
//
//        // Create the WorkEstimateLiftCharges, which fails.
//        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesMapper.toDto(workEstimateLiftCharges);
//
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLiftChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkLiftchargesIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateLiftChargesRepository.findAll().size();
//        // set the field null
//        workEstimateLiftCharges.setLiftcharges(null);
//
//        // Create the WorkEstimateLiftCharges, which fails.
//        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesMapper.toDto(workEstimateLiftCharges);
//
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLiftChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllWorkEstimateLiftCharges() throws Exception {
//        // Initialize the database
//        workEstimateLiftChargesRepository.saveAndFlush(workEstimateLiftCharges);
//
//        // Get all the workEstimateLiftChargesList
//        restWorkEstimateLiftChargesMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(workEstimateLiftCharges.getId().intValue())))
//            .andExpect(jsonPath("$.[*].workEstimateItemId").value(hasItem(DEFAULT_WORK_ESTIMATE_ITEM_ID.intValue())))
//            .andExpect(jsonPath("$.[*].materialMasterId").value(hasItem(DEFAULT_MATERIAL_MASTER_ID.intValue())))
//            .andExpect(jsonPath("$.[*].liftDistance").value(hasItem(TestUtil.sameNumber(DEFAULT_LIFT_DISTANCE))))
//            .andExpect(jsonPath("$.[*].liftcharges").value(hasItem(TestUtil.sameNumber(DEFAULT_LIFTCHARGES))));
//    }
//
//    @Test
//    @Transactional
//    void getWorkEstimateLiftCharges() throws Exception {
//        // Initialize the database
//        workEstimateLiftChargesRepository.saveAndFlush(workEstimateLiftCharges);
//
//        // Get the workEstimateLiftCharges
//        restWorkEstimateLiftChargesMockMvc
//            .perform(get(ENTITY_API_URL_ID, workEstimateLiftCharges.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(workEstimateLiftCharges.getId().intValue()))
//            .andExpect(jsonPath("$.workEstimateItemId").value(DEFAULT_WORK_ESTIMATE_ITEM_ID.intValue()))
//            .andExpect(jsonPath("$.materialMasterId").value(DEFAULT_MATERIAL_MASTER_ID.intValue()))
//            .andExpect(jsonPath("$.liftDistance").value(TestUtil.sameNumber(DEFAULT_LIFT_DISTANCE)))
//            .andExpect(jsonPath("$.liftcharges").value(TestUtil.sameNumber(DEFAULT_LIFTCHARGES)));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingWorkEstimateLiftCharges() throws Exception {
//        // Get the workEstimateLiftCharges
//        restWorkEstimateLiftChargesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putNewWorkEstimateLiftCharges() throws Exception {
//        // Initialize the database
//        workEstimateLiftChargesRepository.saveAndFlush(workEstimateLiftCharges);
//
//        int databaseSizeBeforeUpdate = workEstimateLiftChargesRepository.findAll().size();
//
//        // Update the workEstimateLiftCharges
//        WorkEstimateLiftCharges updatedWorkEstimateLiftCharges = workEstimateLiftChargesRepository
//            .findById(workEstimateLiftCharges.getId())
//            .get();
//        // Disconnect from session so that the updates on updatedWorkEstimateLiftCharges are not directly saved in db
//        em.detach(updatedWorkEstimateLiftCharges);
//        updatedWorkEstimateLiftCharges
//            .workEstimateItemId(UPDATED_WORK_ESTIMATE_ITEM_ID)
//            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
//            .liftDistance(UPDATED_LIFT_DISTANCE)
//            .liftcharges(UPDATED_LIFTCHARGES);
//        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesMapper.toDto(updatedWorkEstimateLiftCharges);
//
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, workEstimateLiftChargesDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLiftChargesDTO))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WorkEstimateLiftCharges in the database
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeUpdate);
//        WorkEstimateLiftCharges testWorkEstimateLiftCharges = workEstimateLiftChargesList.get(workEstimateLiftChargesList.size() - 1);
//        assertThat(testWorkEstimateLiftCharges.getWorkEstimateItemId()).isEqualTo(UPDATED_WORK_ESTIMATE_ITEM_ID);
//        assertThat(testWorkEstimateLiftCharges.getMaterialMasterId()).isEqualTo(UPDATED_MATERIAL_MASTER_ID);
//        assertThat(testWorkEstimateLiftCharges.getLiftDistance()).isEqualTo(UPDATED_LIFT_DISTANCE);
//        assertThat(testWorkEstimateLiftCharges.getLiftcharges()).isEqualTo(UPDATED_LIFTCHARGES);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingWorkEstimateLiftCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLiftChargesRepository.findAll().size();
//        workEstimateLiftCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLiftCharges
//        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesMapper.toDto(workEstimateLiftCharges);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, workEstimateLiftChargesDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLiftChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLiftCharges in the database
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchWorkEstimateLiftCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLiftChargesRepository.findAll().size();
//        workEstimateLiftCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLiftCharges
//        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesMapper.toDto(workEstimateLiftCharges);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLiftChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLiftCharges in the database
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamWorkEstimateLiftCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLiftChargesRepository.findAll().size();
//        workEstimateLiftCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLiftCharges
//        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesMapper.toDto(workEstimateLiftCharges);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLiftChargesDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the WorkEstimateLiftCharges in the database
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateWorkEstimateLiftChargesWithPatch() throws Exception {
//        // Initialize the database
//        workEstimateLiftChargesRepository.saveAndFlush(workEstimateLiftCharges);
//
//        int databaseSizeBeforeUpdate = workEstimateLiftChargesRepository.findAll().size();
//
//        // Update the workEstimateLiftCharges using partial update
//        WorkEstimateLiftCharges partialUpdatedWorkEstimateLiftCharges = new WorkEstimateLiftCharges();
//        partialUpdatedWorkEstimateLiftCharges.setId(workEstimateLiftCharges.getId());
//
//        partialUpdatedWorkEstimateLiftCharges
//            .workEstimateItemId(UPDATED_WORK_ESTIMATE_ITEM_ID)
//            .materialMasterId(UPDATED_MATERIAL_MASTER_ID);
//
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedWorkEstimateLiftCharges.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimateLiftCharges))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WorkEstimateLiftCharges in the database
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeUpdate);
//        WorkEstimateLiftCharges testWorkEstimateLiftCharges = workEstimateLiftChargesList.get(workEstimateLiftChargesList.size() - 1);
//        assertThat(testWorkEstimateLiftCharges.getWorkEstimateItemId()).isEqualTo(UPDATED_WORK_ESTIMATE_ITEM_ID);
//        assertThat(testWorkEstimateLiftCharges.getMaterialMasterId()).isEqualTo(UPDATED_MATERIAL_MASTER_ID);
//        assertThat(testWorkEstimateLiftCharges.getLiftDistance()).isEqualByComparingTo(DEFAULT_LIFT_DISTANCE);
//        assertThat(testWorkEstimateLiftCharges.getLiftcharges()).isEqualByComparingTo(DEFAULT_LIFTCHARGES);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateWorkEstimateLiftChargesWithPatch() throws Exception {
//        // Initialize the database
//        workEstimateLiftChargesRepository.saveAndFlush(workEstimateLiftCharges);
//
//        int databaseSizeBeforeUpdate = workEstimateLiftChargesRepository.findAll().size();
//
//        // Update the workEstimateLiftCharges using partial update
//        WorkEstimateLiftCharges partialUpdatedWorkEstimateLiftCharges = new WorkEstimateLiftCharges();
//        partialUpdatedWorkEstimateLiftCharges.setId(workEstimateLiftCharges.getId());
//
//        partialUpdatedWorkEstimateLiftCharges
//            .workEstimateItemId(UPDATED_WORK_ESTIMATE_ITEM_ID)
//            .materialMasterId(UPDATED_MATERIAL_MASTER_ID)
//            .liftDistance(UPDATED_LIFT_DISTANCE)
//            .liftcharges(UPDATED_LIFTCHARGES);
//
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedWorkEstimateLiftCharges.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimateLiftCharges))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WorkEstimateLiftCharges in the database
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeUpdate);
//        WorkEstimateLiftCharges testWorkEstimateLiftCharges = workEstimateLiftChargesList.get(workEstimateLiftChargesList.size() - 1);
//        assertThat(testWorkEstimateLiftCharges.getWorkEstimateItemId()).isEqualTo(UPDATED_WORK_ESTIMATE_ITEM_ID);
//        assertThat(testWorkEstimateLiftCharges.getMaterialMasterId()).isEqualTo(UPDATED_MATERIAL_MASTER_ID);
//        assertThat(testWorkEstimateLiftCharges.getLiftDistance()).isEqualByComparingTo(UPDATED_LIFT_DISTANCE);
//        assertThat(testWorkEstimateLiftCharges.getLiftcharges()).isEqualByComparingTo(UPDATED_LIFTCHARGES);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingWorkEstimateLiftCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLiftChargesRepository.findAll().size();
//        workEstimateLiftCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLiftCharges
//        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesMapper.toDto(workEstimateLiftCharges);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, workEstimateLiftChargesDTO.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLiftChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLiftCharges in the database
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchWorkEstimateLiftCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLiftChargesRepository.findAll().size();
//        workEstimateLiftCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLiftCharges
//        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesMapper.toDto(workEstimateLiftCharges);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLiftChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateLiftCharges in the database
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamWorkEstimateLiftCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateLiftChargesRepository.findAll().size();
//        workEstimateLiftCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateLiftCharges
//        WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesMapper.toDto(workEstimateLiftCharges);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateLiftChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL)
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateLiftChargesDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the WorkEstimateLiftCharges in the database
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteWorkEstimateLiftCharges() throws Exception {
//        // Initialize the database
//        workEstimateLiftChargesRepository.saveAndFlush(workEstimateLiftCharges);
//
//        int databaseSizeBeforeDelete = workEstimateLiftChargesRepository.findAll().size();
//
//        // Delete the workEstimateLiftCharges
//        restWorkEstimateLiftChargesMockMvc
//            .perform(delete(ENTITY_API_URL_ID, workEstimateLiftCharges.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<WorkEstimateLiftCharges> workEstimateLiftChargesList = workEstimateLiftChargesRepository.findAll();
//        assertThat(workEstimateLiftChargesList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
