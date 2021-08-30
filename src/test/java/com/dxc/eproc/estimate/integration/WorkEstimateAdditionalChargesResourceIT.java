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
//import com.dxc.eproc.estimate.controller.WorkEstimateAdditionalChargesController;
//import com.dxc.eproc.estimate.model.WorkEstimateAdditionalCharges;
//import com.dxc.eproc.estimate.repository.WorkEstimateAdditionalChargesRepository;
//import com.dxc.eproc.estimate.service.dto.WorkEstimateAdditionalChargesDTO;
//import com.dxc.eproc.estimate.service.mapper.WorkEstimateAdditionalChargesMapper;
//
///**
// * Integration tests for the {@link WorkEstimateAdditionalChargesController} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class WorkEstimateAdditionalChargesResourceIT {
//
//    private static final Long DEFAULT_WORK_ESTIMATE_ITEM_ID = 1L;
//    private static final Long UPDATED_WORK_ESTIMATE_ITEM_ID = 2L;
//
//    private static final String DEFAULT_ADDITIONAL_CHARGES_DESC = "AAAAAAAAAA";
//    private static final String UPDATED_ADDITIONAL_CHARGES_DESC = "BBBBBBBBBB";
//
//    private static final BigDecimal DEFAULT_ADDITIONAL_CHARGES_RATE = new BigDecimal(1);
//    private static final BigDecimal UPDATED_ADDITIONAL_CHARGES_RATE = new BigDecimal(2);
//
//    private static final String ENTITY_API_URL = "/api/work-estimate-additional-charges";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private WorkEstimateAdditionalChargesRepository workEstimateAdditionalChargesRepository;
//
//    @Autowired
//    private WorkEstimateAdditionalChargesMapper workEstimateAdditionalChargesMapper;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restWorkEstimateAdditionalChargesMockMvc;
//
//    private WorkEstimateAdditionalCharges workEstimateAdditionalCharges;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static WorkEstimateAdditionalCharges createEntity(EntityManager em) {
//        WorkEstimateAdditionalCharges workEstimateAdditionalCharges = new WorkEstimateAdditionalCharges()
//            .workEstimateItemId(DEFAULT_WORK_ESTIMATE_ITEM_ID)
//            .additionalChargesDesc(DEFAULT_ADDITIONAL_CHARGES_DESC)
//            .additionalChargesRate(DEFAULT_ADDITIONAL_CHARGES_RATE);
//        return workEstimateAdditionalCharges;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static WorkEstimateAdditionalCharges createUpdatedEntity(EntityManager em) {
//        WorkEstimateAdditionalCharges workEstimateAdditionalCharges = new WorkEstimateAdditionalCharges()
//            .workEstimateItemId(UPDATED_WORK_ESTIMATE_ITEM_ID)
//            .additionalChargesDesc(UPDATED_ADDITIONAL_CHARGES_DESC)
//            .additionalChargesRate(UPDATED_ADDITIONAL_CHARGES_RATE);
//        return workEstimateAdditionalCharges;
//    }
//
//    @BeforeMethod
//    public void initTest() {
//        workEstimateAdditionalCharges = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createWorkEstimateAdditionalCharges() throws Exception {
//        int databaseSizeBeforeCreate = workEstimateAdditionalChargesRepository.findAll().size();
//        // Create the WorkEstimateAdditionalCharges
//        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = workEstimateAdditionalChargesMapper.toDto(
//            workEstimateAdditionalCharges
//        );
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateAdditionalChargesDTO))
//            )
//            .andExpect(status().isCreated());
//
//        // Validate the WorkEstimateAdditionalCharges in the database
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeCreate + 1);
//        WorkEstimateAdditionalCharges testWorkEstimateAdditionalCharges = workEstimateAdditionalChargesList.get(
//            workEstimateAdditionalChargesList.size() - 1
//        );
//        assertThat(testWorkEstimateAdditionalCharges.getWorkEstimateItemId()).isEqualTo(DEFAULT_WORK_ESTIMATE_ITEM_ID);
//        assertThat(testWorkEstimateAdditionalCharges.getAdditionalChargesDesc()).isEqualTo(DEFAULT_ADDITIONAL_CHARGES_DESC);
//        assertThat(testWorkEstimateAdditionalCharges.getAdditionalChargesRate()).isEqualByComparingTo(DEFAULT_ADDITIONAL_CHARGES_RATE);
//    }
//
//    @Test
//    @Transactional
//    void createWorkEstimateAdditionalChargesWithExistingId() throws Exception {
//        // Create the WorkEstimateAdditionalCharges with an existing ID
//        workEstimateAdditionalCharges.setId(1L);
//        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = workEstimateAdditionalChargesMapper.toDto(
//            workEstimateAdditionalCharges
//        );
//
//        int databaseSizeBeforeCreate = workEstimateAdditionalChargesRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateAdditionalChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateAdditionalCharges in the database
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkWorkEstimateItemIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateAdditionalChargesRepository.findAll().size();
//        // set the field null
//        workEstimateAdditionalCharges.setWorkEstimateItemId(null);
//
//        // Create the WorkEstimateAdditionalCharges, which fails.
//        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = workEstimateAdditionalChargesMapper.toDto(
//            workEstimateAdditionalCharges
//        );
//
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateAdditionalChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkAdditionalChargesDescIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateAdditionalChargesRepository.findAll().size();
//        // set the field null
//        workEstimateAdditionalCharges.setAdditionalChargesDesc(null);
//
//        // Create the WorkEstimateAdditionalCharges, which fails.
//        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = workEstimateAdditionalChargesMapper.toDto(
//            workEstimateAdditionalCharges
//        );
//
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateAdditionalChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkAdditionalChargesRateIsRequired() throws Exception {
//        int databaseSizeBeforeTest = workEstimateAdditionalChargesRepository.findAll().size();
//        // set the field null
//        workEstimateAdditionalCharges.setAdditionalChargesRate(null);
//
//        // Create the WorkEstimateAdditionalCharges, which fails.
//        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = workEstimateAdditionalChargesMapper.toDto(
//            workEstimateAdditionalCharges
//        );
//
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateAdditionalChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllWorkEstimateAdditionalCharges() throws Exception {
//        // Initialize the database
//        workEstimateAdditionalChargesRepository.saveAndFlush(workEstimateAdditionalCharges);
//
//        // Get all the workEstimateAdditionalChargesList
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(workEstimateAdditionalCharges.getId().intValue())))
//            .andExpect(jsonPath("$.[*].workEstimateItemId").value(hasItem(DEFAULT_WORK_ESTIMATE_ITEM_ID.intValue())))
//            .andExpect(jsonPath("$.[*].additionalChargesDesc").value(hasItem(DEFAULT_ADDITIONAL_CHARGES_DESC)))
//            .andExpect(jsonPath("$.[*].additionalChargesRate").value(hasItem(TestUtil.sameNumber(DEFAULT_ADDITIONAL_CHARGES_RATE))));
//    }
//
//    @Test
//    @Transactional
//    void getWorkEstimateAdditionalCharges() throws Exception {
//        // Initialize the database
//        workEstimateAdditionalChargesRepository.saveAndFlush(workEstimateAdditionalCharges);
//
//        // Get the workEstimateAdditionalCharges
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(get(ENTITY_API_URL_ID, workEstimateAdditionalCharges.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(workEstimateAdditionalCharges.getId().intValue()))
//            .andExpect(jsonPath("$.workEstimateItemId").value(DEFAULT_WORK_ESTIMATE_ITEM_ID.intValue()))
//            .andExpect(jsonPath("$.additionalChargesDesc").value(DEFAULT_ADDITIONAL_CHARGES_DESC))
//            .andExpect(jsonPath("$.additionalChargesRate").value(TestUtil.sameNumber(DEFAULT_ADDITIONAL_CHARGES_RATE)));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingWorkEstimateAdditionalCharges() throws Exception {
//        // Get the workEstimateAdditionalCharges
//        restWorkEstimateAdditionalChargesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putNewWorkEstimateAdditionalCharges() throws Exception {
//        // Initialize the database
//        workEstimateAdditionalChargesRepository.saveAndFlush(workEstimateAdditionalCharges);
//
//        int databaseSizeBeforeUpdate = workEstimateAdditionalChargesRepository.findAll().size();
//
//        // Update the workEstimateAdditionalCharges
//        WorkEstimateAdditionalCharges updatedWorkEstimateAdditionalCharges = workEstimateAdditionalChargesRepository
//            .findById(workEstimateAdditionalCharges.getId())
//            .get();
//        // Disconnect from session so that the updates on updatedWorkEstimateAdditionalCharges are not directly saved in db
//        em.detach(updatedWorkEstimateAdditionalCharges);
//        updatedWorkEstimateAdditionalCharges
//            .workEstimateItemId(UPDATED_WORK_ESTIMATE_ITEM_ID)
//            .additionalChargesDesc(UPDATED_ADDITIONAL_CHARGES_DESC)
//            .additionalChargesRate(UPDATED_ADDITIONAL_CHARGES_RATE);
//        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = workEstimateAdditionalChargesMapper.toDto(
//            updatedWorkEstimateAdditionalCharges
//        );
//
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, workEstimateAdditionalChargesDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateAdditionalChargesDTO))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WorkEstimateAdditionalCharges in the database
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeUpdate);
//        WorkEstimateAdditionalCharges testWorkEstimateAdditionalCharges = workEstimateAdditionalChargesList.get(
//            workEstimateAdditionalChargesList.size() - 1
//        );
//        assertThat(testWorkEstimateAdditionalCharges.getWorkEstimateItemId()).isEqualTo(UPDATED_WORK_ESTIMATE_ITEM_ID);
//        assertThat(testWorkEstimateAdditionalCharges.getAdditionalChargesDesc()).isEqualTo(UPDATED_ADDITIONAL_CHARGES_DESC);
//        assertThat(testWorkEstimateAdditionalCharges.getAdditionalChargesRate()).isEqualTo(UPDATED_ADDITIONAL_CHARGES_RATE);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingWorkEstimateAdditionalCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateAdditionalChargesRepository.findAll().size();
//        workEstimateAdditionalCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateAdditionalCharges
//        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = workEstimateAdditionalChargesMapper.toDto(
//            workEstimateAdditionalCharges
//        );
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, workEstimateAdditionalChargesDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateAdditionalChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateAdditionalCharges in the database
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchWorkEstimateAdditionalCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateAdditionalChargesRepository.findAll().size();
//        workEstimateAdditionalCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateAdditionalCharges
//        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = workEstimateAdditionalChargesMapper.toDto(
//            workEstimateAdditionalCharges
//        );
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateAdditionalChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateAdditionalCharges in the database
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamWorkEstimateAdditionalCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateAdditionalChargesRepository.findAll().size();
//        workEstimateAdditionalCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateAdditionalCharges
//        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = workEstimateAdditionalChargesMapper.toDto(
//            workEstimateAdditionalCharges
//        );
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                put(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateAdditionalChargesDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the WorkEstimateAdditionalCharges in the database
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateWorkEstimateAdditionalChargesWithPatch() throws Exception {
//        // Initialize the database
//        workEstimateAdditionalChargesRepository.saveAndFlush(workEstimateAdditionalCharges);
//
//        int databaseSizeBeforeUpdate = workEstimateAdditionalChargesRepository.findAll().size();
//
//        // Update the workEstimateAdditionalCharges using partial update
//        WorkEstimateAdditionalCharges partialUpdatedWorkEstimateAdditionalCharges = new WorkEstimateAdditionalCharges();
//        partialUpdatedWorkEstimateAdditionalCharges.setId(workEstimateAdditionalCharges.getId());
//
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedWorkEstimateAdditionalCharges.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimateAdditionalCharges))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WorkEstimateAdditionalCharges in the database
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeUpdate);
//        WorkEstimateAdditionalCharges testWorkEstimateAdditionalCharges = workEstimateAdditionalChargesList.get(
//            workEstimateAdditionalChargesList.size() - 1
//        );
//        assertThat(testWorkEstimateAdditionalCharges.getWorkEstimateItemId()).isEqualTo(DEFAULT_WORK_ESTIMATE_ITEM_ID);
//        assertThat(testWorkEstimateAdditionalCharges.getAdditionalChargesDesc()).isEqualTo(DEFAULT_ADDITIONAL_CHARGES_DESC);
//        assertThat(testWorkEstimateAdditionalCharges.getAdditionalChargesRate()).isEqualByComparingTo(DEFAULT_ADDITIONAL_CHARGES_RATE);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateWorkEstimateAdditionalChargesWithPatch() throws Exception {
//        // Initialize the database
//        workEstimateAdditionalChargesRepository.saveAndFlush(workEstimateAdditionalCharges);
//
//        int databaseSizeBeforeUpdate = workEstimateAdditionalChargesRepository.findAll().size();
//
//        // Update the workEstimateAdditionalCharges using partial update
//        WorkEstimateAdditionalCharges partialUpdatedWorkEstimateAdditionalCharges = new WorkEstimateAdditionalCharges();
//        partialUpdatedWorkEstimateAdditionalCharges.setId(workEstimateAdditionalCharges.getId());
//
//        partialUpdatedWorkEstimateAdditionalCharges
//            .workEstimateItemId(UPDATED_WORK_ESTIMATE_ITEM_ID)
//            .additionalChargesDesc(UPDATED_ADDITIONAL_CHARGES_DESC)
//            .additionalChargesRate(UPDATED_ADDITIONAL_CHARGES_RATE);
//
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedWorkEstimateAdditionalCharges.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimateAdditionalCharges))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WorkEstimateAdditionalCharges in the database
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeUpdate);
//        WorkEstimateAdditionalCharges testWorkEstimateAdditionalCharges = workEstimateAdditionalChargesList.get(
//            workEstimateAdditionalChargesList.size() - 1
//        );
//        assertThat(testWorkEstimateAdditionalCharges.getWorkEstimateItemId()).isEqualTo(UPDATED_WORK_ESTIMATE_ITEM_ID);
//        assertThat(testWorkEstimateAdditionalCharges.getAdditionalChargesDesc()).isEqualTo(UPDATED_ADDITIONAL_CHARGES_DESC);
//        assertThat(testWorkEstimateAdditionalCharges.getAdditionalChargesRate()).isEqualByComparingTo(UPDATED_ADDITIONAL_CHARGES_RATE);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingWorkEstimateAdditionalCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateAdditionalChargesRepository.findAll().size();
//        workEstimateAdditionalCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateAdditionalCharges
//        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = workEstimateAdditionalChargesMapper.toDto(
//            workEstimateAdditionalCharges
//        );
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, workEstimateAdditionalChargesDTO.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateAdditionalChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateAdditionalCharges in the database
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchWorkEstimateAdditionalCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateAdditionalChargesRepository.findAll().size();
//        workEstimateAdditionalCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateAdditionalCharges
//        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = workEstimateAdditionalChargesMapper.toDto(
//            workEstimateAdditionalCharges
//        );
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateAdditionalChargesDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WorkEstimateAdditionalCharges in the database
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamWorkEstimateAdditionalCharges() throws Exception {
//        int databaseSizeBeforeUpdate = workEstimateAdditionalChargesRepository.findAll().size();
//        workEstimateAdditionalCharges.setId(count.incrementAndGet());
//
//        // Create the WorkEstimateAdditionalCharges
//        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = workEstimateAdditionalChargesMapper.toDto(
//            workEstimateAdditionalCharges
//        );
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(
//                patch(ENTITY_API_URL)
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(workEstimateAdditionalChargesDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the WorkEstimateAdditionalCharges in the database
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteWorkEstimateAdditionalCharges() throws Exception {
//        // Initialize the database
//        workEstimateAdditionalChargesRepository.saveAndFlush(workEstimateAdditionalCharges);
//
//        int databaseSizeBeforeDelete = workEstimateAdditionalChargesRepository.findAll().size();
//
//        // Delete the workEstimateAdditionalCharges
//        restWorkEstimateAdditionalChargesMockMvc
//            .perform(delete(ENTITY_API_URL_ID, workEstimateAdditionalCharges.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<WorkEstimateAdditionalCharges> workEstimateAdditionalChargesList = workEstimateAdditionalChargesRepository.findAll();
//        assertThat(workEstimateAdditionalChargesList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
