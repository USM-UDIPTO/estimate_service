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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.EstimateServiceApplication;
import com.dxc.eproc.estimate.controller.OverHeadController;
import com.dxc.eproc.estimate.enumeration.OverHeadType;
import com.dxc.eproc.estimate.model.OverHead;
import com.dxc.eproc.estimate.repository.OverHeadRepository;
import com.dxc.eproc.estimate.service.dto.OverHeadDTO;
import com.dxc.eproc.estimate.service.mapper.OverHeadMapper;

/**
 * Integration tests for the {@link OverHeadController} REST controller.
 */
@SpringBootTest(classes = EstimateServiceApplication.class)
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class OverHeadResourceIT extends AbstractTestNGSpringContextTests {

    private static final OverHeadType DEFAULT_OVER_HEAD_TYPE = OverHeadType.NORMAL;
    private static final OverHeadType UPDATED_OVER_HEAD_TYPE = OverHeadType.ADDITIONAL;

    private static final String DEFAULT_OVER_HEAD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OVER_HEAD_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE_YN = false;
    private static final Boolean UPDATED_ACTIVE_YN = true;

    private static final String ENTITY_API_URL = "/v1/api/overhead";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OverHeadRepository overHeadRepository;

    @Autowired
    private OverHeadMapper overHeadMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOverHeadMockMvc;

    private OverHead overHead;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OverHead createEntity(EntityManager em) {
        OverHead overHead = new OverHead()
            .overHeadType(DEFAULT_OVER_HEAD_TYPE)
            .overHeadName(DEFAULT_OVER_HEAD_NAME)
            .activeYn(DEFAULT_ACTIVE_YN);
        return overHead;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OverHead createUpdatedEntity(EntityManager em) {
        OverHead overHead = new OverHead()
            .overHeadType(UPDATED_OVER_HEAD_TYPE)
            .overHeadName(UPDATED_OVER_HEAD_NAME)
            .activeYn(UPDATED_ACTIVE_YN);
        return overHead;
    }

    @BeforeMethod
    public void initTest() {
        overHead = createEntity(em);
    }

    @Test
    @Transactional
    void createOverHead() throws Exception {
        int databaseSizeBeforeCreate = overHeadRepository.findAll().size();
        // Create the OverHead
        OverHeadDTO overHeadDTO = overHeadMapper.toDto(overHead);
        restOverHeadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(overHeadDTO)))
            .andExpect(status().isCreated());

        // Validate the OverHead in the database
        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeCreate + 1);
        OverHead testOverHead = overHeadList.get(overHeadList.size() - 1);
        assertThat(testOverHead.getOverHeadType()).isEqualTo(DEFAULT_OVER_HEAD_TYPE);
        assertThat(testOverHead.getOverHeadName()).isEqualTo(DEFAULT_OVER_HEAD_NAME);
        assertThat(testOverHead.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);
    }

    @Test
    @Transactional
    void createOverHeadWithExistingId() throws Exception {
        // Create the OverHead with an existing ID
        overHead.setId(1L);
        OverHeadDTO overHeadDTO = overHeadMapper.toDto(overHead);

        int databaseSizeBeforeCreate = overHeadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOverHeadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(overHeadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OverHead in the database
        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOverHeadTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = overHeadRepository.findAll().size();
        // set the field null
        overHead.setOverHeadType(null);

        // Create the OverHead, which fails.
        OverHeadDTO overHeadDTO = overHeadMapper.toDto(overHead);

        restOverHeadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(overHeadDTO)))
            .andExpect(status().isBadRequest());

        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOverHeadNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = overHeadRepository.findAll().size();
        // set the field null
        overHead.setOverHeadName(null);

        // Create the OverHead, which fails.
        OverHeadDTO overHeadDTO = overHeadMapper.toDto(overHead);

        restOverHeadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(overHeadDTO)))
            .andExpect(status().isBadRequest());

        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOverHeads() throws Exception {
        // Initialize the database
        overHeadRepository.saveAndFlush(overHead);

        // Get all the overHeadList
        restOverHeadMockMvc
            .perform(get("/v1/api/overheads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overHead.getId().intValue())))
            .andExpect(jsonPath("$.[*].overHeadType").value(hasItem(DEFAULT_OVER_HEAD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].overHeadName").value(hasItem(DEFAULT_OVER_HEAD_NAME)))
            .andExpect(jsonPath("$.[*].activeYn").value(hasItem(DEFAULT_ACTIVE_YN.booleanValue())));
    }

    @Test
    @Transactional
    void getOverHead() throws Exception {
        // Initialize the database
        overHeadRepository.saveAndFlush(overHead);

        // Get the overHead
        restOverHeadMockMvc
            .perform(get(ENTITY_API_URL_ID, overHead.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(overHead.getId().intValue()))
            .andExpect(jsonPath("$.overHeadType").value(DEFAULT_OVER_HEAD_TYPE.toString()))
            .andExpect(jsonPath("$.overHeadName").value(DEFAULT_OVER_HEAD_NAME))
            .andExpect(jsonPath("$.activeYn").value(DEFAULT_ACTIVE_YN.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingOverHead() throws Exception {
        // Get the overHead
        restOverHeadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOverHead() throws Exception {
        // Initialize the database
        overHeadRepository.saveAndFlush(overHead);

        int databaseSizeBeforeUpdate = overHeadRepository.findAll().size();

        // Update the overHead
        OverHead updatedOverHead = overHeadRepository.findById(overHead.getId()).get();
        // Disconnect from session so that the updates on updatedOverHead are not directly saved in db
        em.detach(updatedOverHead);
        updatedOverHead.overHeadType(UPDATED_OVER_HEAD_TYPE).overHeadName(UPDATED_OVER_HEAD_NAME).activeYn(UPDATED_ACTIVE_YN);
        OverHeadDTO overHeadDTO = overHeadMapper.toDto(updatedOverHead);

        restOverHeadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overHeadDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(overHeadDTO))
            )
            .andExpect(status().isOk());

        // Validate the OverHead in the database
        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeUpdate);
        OverHead testOverHead = overHeadList.get(overHeadList.size() - 1);
        assertThat(testOverHead.getOverHeadType()).isEqualTo(UPDATED_OVER_HEAD_TYPE);
        assertThat(testOverHead.getOverHeadName()).isEqualTo(UPDATED_OVER_HEAD_NAME);
        assertThat(testOverHead.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);
    }

    @Test
    @Transactional
    void putNonExistingOverHead() throws Exception {
        int databaseSizeBeforeUpdate = overHeadRepository.findAll().size();
        overHead.setId(count.incrementAndGet());

        // Create the OverHead
        OverHeadDTO overHeadDTO = overHeadMapper.toDto(overHead);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverHeadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overHeadDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(overHeadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OverHead in the database
        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOverHead() throws Exception {
        int databaseSizeBeforeUpdate = overHeadRepository.findAll().size();
        overHead.setId(count.incrementAndGet());

        // Create the OverHead
        OverHeadDTO overHeadDTO = overHeadMapper.toDto(overHead);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverHeadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(overHeadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OverHead in the database
        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOverHead() throws Exception {
        int databaseSizeBeforeUpdate = overHeadRepository.findAll().size();
        overHead.setId(count.incrementAndGet());

        // Create the OverHead
        OverHeadDTO overHeadDTO = overHeadMapper.toDto(overHead);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverHeadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(overHeadDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OverHead in the database
        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOverHeadWithPatch() throws Exception {
        // Initialize the database
        overHeadRepository.saveAndFlush(overHead);

        int databaseSizeBeforeUpdate = overHeadRepository.findAll().size();

        // Update the overHead using partial update
        OverHead partialUpdatedOverHead = new OverHead();
        partialUpdatedOverHead.setId(overHead.getId());

        partialUpdatedOverHead.overHeadType(UPDATED_OVER_HEAD_TYPE).activeYn(UPDATED_ACTIVE_YN);

        restOverHeadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverHead.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOverHead))
            )
            .andExpect(status().isOk());

        // Validate the OverHead in the database
        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeUpdate);
        OverHead testOverHead = overHeadList.get(overHeadList.size() - 1);
        assertThat(testOverHead.getOverHeadType()).isEqualTo(UPDATED_OVER_HEAD_TYPE);
        assertThat(testOverHead.getOverHeadName()).isEqualTo(DEFAULT_OVER_HEAD_NAME);
        assertThat(testOverHead.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);
    }

    @Test
    @Transactional
    void fullUpdateOverHeadWithPatch() throws Exception {
        // Initialize the database
        overHeadRepository.saveAndFlush(overHead);

        int databaseSizeBeforeUpdate = overHeadRepository.findAll().size();

        // Update the overHead using partial update
        OverHead partialUpdatedOverHead = new OverHead();
        partialUpdatedOverHead.setId(overHead.getId());

        partialUpdatedOverHead.overHeadType(UPDATED_OVER_HEAD_TYPE).overHeadName(UPDATED_OVER_HEAD_NAME).activeYn(UPDATED_ACTIVE_YN);

        restOverHeadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverHead.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOverHead))
            )
            .andExpect(status().isOk());

        // Validate the OverHead in the database
        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeUpdate);
        OverHead testOverHead = overHeadList.get(overHeadList.size() - 1);
        assertThat(testOverHead.getOverHeadType()).isEqualTo(UPDATED_OVER_HEAD_TYPE);
        assertThat(testOverHead.getOverHeadName()).isEqualTo(UPDATED_OVER_HEAD_NAME);
        assertThat(testOverHead.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);
    }

    @Test
    @Transactional
    void patchNonExistingOverHead() throws Exception {
        int databaseSizeBeforeUpdate = overHeadRepository.findAll().size();
        overHead.setId(count.incrementAndGet());

        // Create the OverHead
        OverHeadDTO overHeadDTO = overHeadMapper.toDto(overHead);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverHeadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, overHeadDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(overHeadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OverHead in the database
        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOverHead() throws Exception {
        int databaseSizeBeforeUpdate = overHeadRepository.findAll().size();
        overHead.setId(count.incrementAndGet());

        // Create the OverHead
        OverHeadDTO overHeadDTO = overHeadMapper.toDto(overHead);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverHeadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(overHeadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OverHead in the database
        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOverHead() throws Exception {
        int databaseSizeBeforeUpdate = overHeadRepository.findAll().size();
        overHead.setId(count.incrementAndGet());

        // Create the OverHead
        OverHeadDTO overHeadDTO = overHeadMapper.toDto(overHead);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverHeadMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(overHeadDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OverHead in the database
        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOverHead() throws Exception {
        // Initialize the database
        overHeadRepository.saveAndFlush(overHead);

        int databaseSizeBeforeDelete = overHeadRepository.findAll().size();

        // Delete the overHead
        restOverHeadMockMvc
            .perform(delete(ENTITY_API_URL_ID, overHead.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OverHead> overHeadList = overHeadRepository.findAll();
        assertThat(overHeadList).hasSize(databaseSizeBeforeDelete);
    }
}
