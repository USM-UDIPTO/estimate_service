package com.dxc.eproc.estimate.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.IntegrationTest;
import com.dxc.eproc.estimate.controller.WorkEstimateOverheadController;
import com.dxc.eproc.estimate.enumeration.OverHeadType;
import com.dxc.eproc.estimate.model.WorkEstimateOverhead;
import com.dxc.eproc.estimate.repository.WorkEstimateOverheadRepository;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOverheadDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateOverheadMapper;

/**
 * Integration tests for the {@link WorkEstimateOverheadController} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkEstimateOverheadResourceIT {

    private static final Long DEFAULT_WORK_ESTIMATE_ID = 1L;
    private static final Long UPDATED_WORK_ESTIMATE_ID = 2L;

    private static final OverHeadType DEFAULT_OVER_HEAD_TYPE = OverHeadType.NORMAL;
    private static final OverHeadType UPDATED_OVER_HEAD_TYPE = OverHeadType.ADDITIONAL;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_OVER_HEAD_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OVER_HEAD_VALUE = new BigDecimal(2);

    private static final Boolean DEFAULT_FIXED_YN = false;
    private static final Boolean UPDATED_FIXED_YN = true;

    private static final String ENTITY_API_URL = "/api/work-estimate-overheads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkEstimateOverheadRepository workEstimateOverheadRepository;

    @Autowired
    private WorkEstimateOverheadMapper workEstimateOverheadMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEstimateOverheadMockMvc;

    private WorkEstimateOverhead workEstimateOverhead;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEstimateOverhead createEntity(EntityManager em) {
        WorkEstimateOverhead workEstimateOverhead = new WorkEstimateOverhead()
            .workEstimateId(DEFAULT_WORK_ESTIMATE_ID)
            .overHeadType(DEFAULT_OVER_HEAD_TYPE)
            .name(DEFAULT_NAME)
            .overHeadValue(DEFAULT_OVER_HEAD_VALUE)
            .fixedYn(DEFAULT_FIXED_YN);
        return workEstimateOverhead;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEstimateOverhead createUpdatedEntity(EntityManager em) {
        WorkEstimateOverhead workEstimateOverhead = new WorkEstimateOverhead()
            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
            .overHeadType(UPDATED_OVER_HEAD_TYPE)
            .name(UPDATED_NAME)
            .overHeadValue(UPDATED_OVER_HEAD_VALUE)
            .fixedYn(UPDATED_FIXED_YN);
        return workEstimateOverhead;
    }

    @BeforeMethod
    public void initTest() {
        workEstimateOverhead = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkEstimateOverhead() throws Exception {
        int databaseSizeBeforeCreate = workEstimateOverheadRepository.findAll().size();
        // Create the WorkEstimateOverhead
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(workEstimateOverhead);
        restWorkEstimateOverheadMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkEstimateOverhead in the database
        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEstimateOverhead testWorkEstimateOverhead = workEstimateOverheadList.get(workEstimateOverheadList.size() - 1);
        assertThat(testWorkEstimateOverhead.getWorkEstimateId()).isEqualTo(DEFAULT_WORK_ESTIMATE_ID);
        assertThat(testWorkEstimateOverhead.getOverHeadType()).isEqualTo(DEFAULT_OVER_HEAD_TYPE);
        assertThat(testWorkEstimateOverhead.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkEstimateOverhead.getOverHeadValue()).isEqualByComparingTo(DEFAULT_OVER_HEAD_VALUE);
        assertThat(testWorkEstimateOverhead.getFixedYn()).isEqualTo(DEFAULT_FIXED_YN);
    }

    @Test
    @Transactional
    void createWorkEstimateOverheadWithExistingId() throws Exception {
        // Create the WorkEstimateOverhead with an existing ID
        workEstimateOverhead.setId(1L);
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(workEstimateOverhead);

        int databaseSizeBeforeCreate = workEstimateOverheadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkEstimateOverheadMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkEstimateOverhead in the database
        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkWorkEstimateIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateOverheadRepository.findAll().size();
        // set the field null
        workEstimateOverhead.setWorkEstimateId(null);

        // Create the WorkEstimateOverhead, which fails.
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(workEstimateOverhead);

        restWorkEstimateOverheadMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOverHeadTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateOverheadRepository.findAll().size();
        // set the field null
        workEstimateOverhead.setOverHeadType(null);

        // Create the WorkEstimateOverhead, which fails.
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(workEstimateOverhead);

        restWorkEstimateOverheadMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateOverheadRepository.findAll().size();
        // set the field null
        workEstimateOverhead.setName(null);

        // Create the WorkEstimateOverhead, which fails.
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(workEstimateOverhead);

        restWorkEstimateOverheadMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOverHeadValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateOverheadRepository.findAll().size();
        // set the field null
        workEstimateOverhead.setOverHeadValue(null);

        // Create the WorkEstimateOverhead, which fails.
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(workEstimateOverhead);

        restWorkEstimateOverheadMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFixedYnIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateOverheadRepository.findAll().size();
        // set the field null
        workEstimateOverhead.setFixedYn(null);

        // Create the WorkEstimateOverhead, which fails.
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(workEstimateOverhead);

        restWorkEstimateOverheadMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWorkEstimateOverheads() throws Exception {
        // Initialize the database
        workEstimateOverheadRepository.saveAndFlush(workEstimateOverhead);

        // Get all the workEstimateOverheadList
        restWorkEstimateOverheadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEstimateOverhead.getId().intValue())))
            .andExpect(jsonPath("$.[*].workEstimateId").value(hasItem(DEFAULT_WORK_ESTIMATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].overHeadType").value(hasItem(DEFAULT_OVER_HEAD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].overHeadValue").value(hasItem(TestUtil.sameNumber(DEFAULT_OVER_HEAD_VALUE))))
            .andExpect(jsonPath("$.[*].fixedYn").value(hasItem(DEFAULT_FIXED_YN.booleanValue())));
    }

    @Test
    @Transactional
    void getWorkEstimateOverhead() throws Exception {
        // Initialize the database
        workEstimateOverheadRepository.saveAndFlush(workEstimateOverhead);

        // Get the workEstimateOverhead
        restWorkEstimateOverheadMockMvc
            .perform(get(ENTITY_API_URL_ID, workEstimateOverhead.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEstimateOverhead.getId().intValue()))
            .andExpect(jsonPath("$.workEstimateId").value(DEFAULT_WORK_ESTIMATE_ID.intValue()))
            .andExpect(jsonPath("$.overHeadType").value(DEFAULT_OVER_HEAD_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.overHeadValue").value(TestUtil.sameNumber(DEFAULT_OVER_HEAD_VALUE)))
            .andExpect(jsonPath("$.fixedYn").value(DEFAULT_FIXED_YN.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingWorkEstimateOverhead() throws Exception {
        // Get the workEstimateOverhead
        restWorkEstimateOverheadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorkEstimateOverhead() throws Exception {
        // Initialize the database
        workEstimateOverheadRepository.saveAndFlush(workEstimateOverhead);

        int databaseSizeBeforeUpdate = workEstimateOverheadRepository.findAll().size();

        // Update the workEstimateOverhead
        WorkEstimateOverhead updatedWorkEstimateOverhead = workEstimateOverheadRepository.findById(workEstimateOverhead.getId()).get();
        // Disconnect from session so that the updates on updatedWorkEstimateOverhead are not directly saved in db
        em.detach(updatedWorkEstimateOverhead);
        updatedWorkEstimateOverhead
            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
            .overHeadType(UPDATED_OVER_HEAD_TYPE)
            .name(UPDATED_NAME)
            .overHeadValue(UPDATED_OVER_HEAD_VALUE)
            .fixedYn(UPDATED_FIXED_YN);
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(updatedWorkEstimateOverhead);

        restWorkEstimateOverheadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimateOverheadDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkEstimateOverhead in the database
        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeUpdate);
        WorkEstimateOverhead testWorkEstimateOverhead = workEstimateOverheadList.get(workEstimateOverheadList.size() - 1);
        assertThat(testWorkEstimateOverhead.getWorkEstimateId()).isEqualTo(UPDATED_WORK_ESTIMATE_ID);
        assertThat(testWorkEstimateOverhead.getOverHeadType()).isEqualTo(UPDATED_OVER_HEAD_TYPE);
        assertThat(testWorkEstimateOverhead.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkEstimateOverhead.getOverHeadValue()).isEqualTo(UPDATED_OVER_HEAD_VALUE);
        assertThat(testWorkEstimateOverhead.getFixedYn()).isEqualTo(UPDATED_FIXED_YN);
    }

    @Test
    @Transactional
    void putNonExistingWorkEstimateOverhead() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateOverheadRepository.findAll().size();
        workEstimateOverhead.setId(count.incrementAndGet());

        // Create the WorkEstimateOverhead
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(workEstimateOverhead);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEstimateOverheadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimateOverheadDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkEstimateOverhead in the database
        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkEstimateOverhead() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateOverheadRepository.findAll().size();
        workEstimateOverhead.setId(count.incrementAndGet());

        // Create the WorkEstimateOverhead
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(workEstimateOverhead);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkEstimateOverheadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkEstimateOverhead in the database
        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkEstimateOverhead() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateOverheadRepository.findAll().size();
        workEstimateOverhead.setId(count.incrementAndGet());

        // Create the WorkEstimateOverhead
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(workEstimateOverhead);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkEstimateOverheadMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkEstimateOverhead in the database
        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkEstimateOverheadWithPatch() throws Exception {
        // Initialize the database
        workEstimateOverheadRepository.saveAndFlush(workEstimateOverhead);

        int databaseSizeBeforeUpdate = workEstimateOverheadRepository.findAll().size();

        // Update the workEstimateOverhead using partial update
        WorkEstimateOverhead partialUpdatedWorkEstimateOverhead = new WorkEstimateOverhead();
        partialUpdatedWorkEstimateOverhead.setId(workEstimateOverhead.getId());

        partialUpdatedWorkEstimateOverhead
            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
            .overHeadType(UPDATED_OVER_HEAD_TYPE)
            .name(UPDATED_NAME)
            .overHeadValue(UPDATED_OVER_HEAD_VALUE);

        restWorkEstimateOverheadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkEstimateOverhead.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimateOverhead))
            )
            .andExpect(status().isOk());

        // Validate the WorkEstimateOverhead in the database
        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeUpdate);
        WorkEstimateOverhead testWorkEstimateOverhead = workEstimateOverheadList.get(workEstimateOverheadList.size() - 1);
        assertThat(testWorkEstimateOverhead.getWorkEstimateId()).isEqualTo(UPDATED_WORK_ESTIMATE_ID);
        assertThat(testWorkEstimateOverhead.getOverHeadType()).isEqualTo(UPDATED_OVER_HEAD_TYPE);
        assertThat(testWorkEstimateOverhead.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkEstimateOverhead.getOverHeadValue()).isEqualByComparingTo(UPDATED_OVER_HEAD_VALUE);
        assertThat(testWorkEstimateOverhead.getFixedYn()).isEqualTo(DEFAULT_FIXED_YN);
    }

    @Test
    @Transactional
    void fullUpdateWorkEstimateOverheadWithPatch() throws Exception {
        // Initialize the database
        workEstimateOverheadRepository.saveAndFlush(workEstimateOverhead);

        int databaseSizeBeforeUpdate = workEstimateOverheadRepository.findAll().size();

        // Update the workEstimateOverhead using partial update
        WorkEstimateOverhead partialUpdatedWorkEstimateOverhead = new WorkEstimateOverhead();
        partialUpdatedWorkEstimateOverhead.setId(workEstimateOverhead.getId());

        partialUpdatedWorkEstimateOverhead
            .workEstimateId(UPDATED_WORK_ESTIMATE_ID)
            .overHeadType(UPDATED_OVER_HEAD_TYPE)
            .name(UPDATED_NAME)
            .overHeadValue(UPDATED_OVER_HEAD_VALUE)
            .fixedYn(UPDATED_FIXED_YN);

        restWorkEstimateOverheadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkEstimateOverhead.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEstimateOverhead))
            )
            .andExpect(status().isOk());

        // Validate the WorkEstimateOverhead in the database
        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeUpdate);
        WorkEstimateOverhead testWorkEstimateOverhead = workEstimateOverheadList.get(workEstimateOverheadList.size() - 1);
        assertThat(testWorkEstimateOverhead.getWorkEstimateId()).isEqualTo(UPDATED_WORK_ESTIMATE_ID);
        assertThat(testWorkEstimateOverhead.getOverHeadType()).isEqualTo(UPDATED_OVER_HEAD_TYPE);
        assertThat(testWorkEstimateOverhead.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkEstimateOverhead.getOverHeadValue()).isEqualByComparingTo(UPDATED_OVER_HEAD_VALUE);
        assertThat(testWorkEstimateOverhead.getFixedYn()).isEqualTo(UPDATED_FIXED_YN);
    }

    @Test
    @Transactional
    void patchNonExistingWorkEstimateOverhead() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateOverheadRepository.findAll().size();
        workEstimateOverhead.setId(count.incrementAndGet());

        // Create the WorkEstimateOverhead
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(workEstimateOverhead);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEstimateOverheadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workEstimateOverheadDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkEstimateOverhead in the database
        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkEstimateOverhead() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateOverheadRepository.findAll().size();
        workEstimateOverhead.setId(count.incrementAndGet());

        // Create the WorkEstimateOverhead
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(workEstimateOverhead);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkEstimateOverheadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkEstimateOverhead in the database
        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkEstimateOverhead() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateOverheadRepository.findAll().size();
        workEstimateOverhead.setId(count.incrementAndGet());

        // Create the WorkEstimateOverhead
        WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadMapper.toDto(workEstimateOverhead);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkEstimateOverheadMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOverheadDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkEstimateOverhead in the database
        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkEstimateOverhead() throws Exception {
        // Initialize the database
        workEstimateOverheadRepository.saveAndFlush(workEstimateOverhead);

        int databaseSizeBeforeDelete = workEstimateOverheadRepository.findAll().size();

        // Delete the workEstimateOverhead
        restWorkEstimateOverheadMockMvc
            .perform(delete(ENTITY_API_URL_ID, workEstimateOverhead.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEstimateOverhead> workEstimateOverheadList = workEstimateOverheadRepository.findAll();
        assertThat(workEstimateOverheadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
