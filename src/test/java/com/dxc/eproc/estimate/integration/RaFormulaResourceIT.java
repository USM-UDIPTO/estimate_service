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

import com.dxc.eproc.estimate.controller.RaFormulaController;
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
import com.dxc.eproc.estimate.enumeration.WorkType;
import com.dxc.eproc.estimate.model.RaFormula;
import com.dxc.eproc.estimate.repository.RaFormulaRepository;
import com.dxc.eproc.estimate.service.dto.RaFormulaDTO;
import com.dxc.eproc.estimate.service.mapper.RaFormulaMapper;

/**
 * Integration tests for the {@link RaFormulaController} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RaFormulaResourceIT {

    private static final Long DEFAULT_DEPT_ID = 1L;
    private static final Long UPDATED_DEPT_ID = 2L;

    private static final WorkType DEFAULT_WORK_TYPE = WorkType.CLOSUREPERIODWORKS;
    private static final WorkType UPDATED_WORK_TYPE = WorkType.IMPROVEMENT;

    private static final String DEFAULT_FORMULA = "AAAAAAAAAA";
    private static final String UPDATED_FORMULA = "BBBBBBBBBB";

    private static final String DEFAULT_AW_FORMULA = "AAAAAAAAAA";
    private static final String UPDATED_AW_FORMULA = "BBBBBBBBBB";

    private static final String DEFAULT_ROYALTY_FORMULA = "AAAAAAAAAA";
    private static final String UPDATED_ROYALTY_FORMULA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ra-formulas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RaFormulaRepository raFormulaRepository;

    @Autowired
    private RaFormulaMapper raFormulaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRaFormulaMockMvc;

    private RaFormula raFormula;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RaFormula createEntity(EntityManager em) {
        RaFormula raFormula = new RaFormula()
            .deptId(DEFAULT_DEPT_ID)
            .workType(DEFAULT_WORK_TYPE)
            .formula(DEFAULT_FORMULA)
            .awFormula(DEFAULT_AW_FORMULA)
            .royaltyFormula(DEFAULT_ROYALTY_FORMULA);
        return raFormula;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RaFormula createUpdatedEntity(EntityManager em) {
        RaFormula raFormula = new RaFormula()
            .deptId(UPDATED_DEPT_ID)
            .workType(UPDATED_WORK_TYPE)
            .formula(UPDATED_FORMULA)
            .awFormula(UPDATED_AW_FORMULA)
            .royaltyFormula(UPDATED_ROYALTY_FORMULA);
        return raFormula;
    }

    @BeforeMethod
    public void initTest() {
        raFormula = createEntity(em);
    }

    @Test
    @Transactional
    void createRaFormula() throws Exception {
        int databaseSizeBeforeCreate = raFormulaRepository.findAll().size();
        // Create the RaFormula
        RaFormulaDTO raFormulaDTO = raFormulaMapper.toDto(raFormula);
        restRaFormulaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raFormulaDTO)))
            .andExpect(status().isCreated());

        // Validate the RaFormula in the database
        List<RaFormula> raFormulaList = raFormulaRepository.findAll();
        assertThat(raFormulaList).hasSize(databaseSizeBeforeCreate + 1);
        RaFormula testRaFormula = raFormulaList.get(raFormulaList.size() - 1);
        assertThat(testRaFormula.getDeptId()).isEqualTo(DEFAULT_DEPT_ID);
        assertThat(testRaFormula.getWorkType()).isEqualTo(DEFAULT_WORK_TYPE);
        assertThat(testRaFormula.getFormula()).isEqualTo(DEFAULT_FORMULA);
        assertThat(testRaFormula.getAwFormula()).isEqualTo(DEFAULT_AW_FORMULA);
        assertThat(testRaFormula.getRoyaltyFormula()).isEqualTo(DEFAULT_ROYALTY_FORMULA);
    }

    @Test
    @Transactional
    void createRaFormulaWithExistingId() throws Exception {
        // Create the RaFormula with an existing ID
        raFormula.setId(1L);
        RaFormulaDTO raFormulaDTO = raFormulaMapper.toDto(raFormula);

        int databaseSizeBeforeCreate = raFormulaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaFormulaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raFormulaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RaFormula in the database
        List<RaFormula> raFormulaList = raFormulaRepository.findAll();
        assertThat(raFormulaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDeptIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = raFormulaRepository.findAll().size();
        // set the field null
        raFormula.setDeptId(null);

        // Create the RaFormula, which fails.
        RaFormulaDTO raFormulaDTO = raFormulaMapper.toDto(raFormula);

        restRaFormulaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raFormulaDTO)))
            .andExpect(status().isBadRequest());

        List<RaFormula> raFormulaList = raFormulaRepository.findAll();
        assertThat(raFormulaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRaFormulas() throws Exception {
        // Initialize the database
        raFormulaRepository.saveAndFlush(raFormula);

        // Get all the raFormulaList
        restRaFormulaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raFormula.getId().intValue())))
            .andExpect(jsonPath("$.[*].deptId").value(hasItem(DEFAULT_DEPT_ID.intValue())))
            .andExpect(jsonPath("$.[*].workType").value(hasItem(DEFAULT_WORK_TYPE)))
            .andExpect(jsonPath("$.[*].formula").value(hasItem(DEFAULT_FORMULA)))
            .andExpect(jsonPath("$.[*].awFormula").value(hasItem(DEFAULT_AW_FORMULA)))
            .andExpect(jsonPath("$.[*].royaltyFormula").value(hasItem(DEFAULT_ROYALTY_FORMULA)));
    }

    @Test
    @Transactional
    void getRaFormula() throws Exception {
        // Initialize the database
        raFormulaRepository.saveAndFlush(raFormula);

        // Get the raFormula
        restRaFormulaMockMvc
            .perform(get(ENTITY_API_URL_ID, raFormula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(raFormula.getId().intValue()))
            .andExpect(jsonPath("$.deptId").value(DEFAULT_DEPT_ID.intValue()))
            .andExpect(jsonPath("$.workType").value(DEFAULT_WORK_TYPE))
            .andExpect(jsonPath("$.formula").value(DEFAULT_FORMULA))
            .andExpect(jsonPath("$.awFormula").value(DEFAULT_AW_FORMULA))
            .andExpect(jsonPath("$.royaltyFormula").value(DEFAULT_ROYALTY_FORMULA));
    }

    @Test
    @Transactional
    void getNonExistingRaFormula() throws Exception {
        // Get the raFormula
        restRaFormulaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRaFormula() throws Exception {
        // Initialize the database
        raFormulaRepository.saveAndFlush(raFormula);

        int databaseSizeBeforeUpdate = raFormulaRepository.findAll().size();

        // Update the raFormula
        RaFormula updatedRaFormula = raFormulaRepository.findById(raFormula.getId()).get();
        // Disconnect from session so that the updates on updatedRaFormula are not directly saved in db
        em.detach(updatedRaFormula);
        updatedRaFormula
            .deptId(UPDATED_DEPT_ID)
            .workType(UPDATED_WORK_TYPE)
            .formula(UPDATED_FORMULA)
            .awFormula(UPDATED_AW_FORMULA)
            .royaltyFormula(UPDATED_ROYALTY_FORMULA);
        RaFormulaDTO raFormulaDTO = raFormulaMapper.toDto(updatedRaFormula);

        restRaFormulaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, raFormulaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raFormulaDTO))
            )
            .andExpect(status().isOk());

        // Validate the RaFormula in the database
        List<RaFormula> raFormulaList = raFormulaRepository.findAll();
        assertThat(raFormulaList).hasSize(databaseSizeBeforeUpdate);
        RaFormula testRaFormula = raFormulaList.get(raFormulaList.size() - 1);
        assertThat(testRaFormula.getDeptId()).isEqualTo(UPDATED_DEPT_ID);
        assertThat(testRaFormula.getWorkType()).isEqualTo(UPDATED_WORK_TYPE);
        assertThat(testRaFormula.getFormula()).isEqualTo(UPDATED_FORMULA);
        assertThat(testRaFormula.getAwFormula()).isEqualTo(UPDATED_AW_FORMULA);
        assertThat(testRaFormula.getRoyaltyFormula()).isEqualTo(UPDATED_ROYALTY_FORMULA);
    }

    @Test
    @Transactional
    void putNonExistingRaFormula() throws Exception {
        int databaseSizeBeforeUpdate = raFormulaRepository.findAll().size();
        raFormula.setId(count.incrementAndGet());

        // Create the RaFormula
        RaFormulaDTO raFormulaDTO = raFormulaMapper.toDto(raFormula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaFormulaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, raFormulaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raFormulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaFormula in the database
        List<RaFormula> raFormulaList = raFormulaRepository.findAll();
        assertThat(raFormulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRaFormula() throws Exception {
        int databaseSizeBeforeUpdate = raFormulaRepository.findAll().size();
        raFormula.setId(count.incrementAndGet());

        // Create the RaFormula
        RaFormulaDTO raFormulaDTO = raFormulaMapper.toDto(raFormula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaFormulaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raFormulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaFormula in the database
        List<RaFormula> raFormulaList = raFormulaRepository.findAll();
        assertThat(raFormulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRaFormula() throws Exception {
        int databaseSizeBeforeUpdate = raFormulaRepository.findAll().size();
        raFormula.setId(count.incrementAndGet());

        // Create the RaFormula
        RaFormulaDTO raFormulaDTO = raFormulaMapper.toDto(raFormula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaFormulaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raFormulaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RaFormula in the database
        List<RaFormula> raFormulaList = raFormulaRepository.findAll();
        assertThat(raFormulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRaFormulaWithPatch() throws Exception {
        // Initialize the database
        raFormulaRepository.saveAndFlush(raFormula);

        int databaseSizeBeforeUpdate = raFormulaRepository.findAll().size();

        // Update the raFormula using partial update
        RaFormula partialUpdatedRaFormula = new RaFormula();
        partialUpdatedRaFormula.setId(raFormula.getId());

        partialUpdatedRaFormula.workType(UPDATED_WORK_TYPE).formula(UPDATED_FORMULA).awFormula(UPDATED_AW_FORMULA);

        restRaFormulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaFormula.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaFormula))
            )
            .andExpect(status().isOk());

        // Validate the RaFormula in the database
        List<RaFormula> raFormulaList = raFormulaRepository.findAll();
        assertThat(raFormulaList).hasSize(databaseSizeBeforeUpdate);
        RaFormula testRaFormula = raFormulaList.get(raFormulaList.size() - 1);
        assertThat(testRaFormula.getDeptId()).isEqualTo(DEFAULT_DEPT_ID);
        assertThat(testRaFormula.getWorkType()).isEqualTo(UPDATED_WORK_TYPE);
        assertThat(testRaFormula.getFormula()).isEqualTo(UPDATED_FORMULA);
        assertThat(testRaFormula.getAwFormula()).isEqualTo(UPDATED_AW_FORMULA);
        assertThat(testRaFormula.getRoyaltyFormula()).isEqualTo(DEFAULT_ROYALTY_FORMULA);
    }

    @Test
    @Transactional
    void fullUpdateRaFormulaWithPatch() throws Exception {
        // Initialize the database
        raFormulaRepository.saveAndFlush(raFormula);

        int databaseSizeBeforeUpdate = raFormulaRepository.findAll().size();

        // Update the raFormula using partial update
        RaFormula partialUpdatedRaFormula = new RaFormula();
        partialUpdatedRaFormula.setId(raFormula.getId());

        partialUpdatedRaFormula
            .deptId(UPDATED_DEPT_ID)
            .workType(UPDATED_WORK_TYPE)
            .formula(UPDATED_FORMULA)
            .awFormula(UPDATED_AW_FORMULA)
            .royaltyFormula(UPDATED_ROYALTY_FORMULA);

        restRaFormulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaFormula.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaFormula))
            )
            .andExpect(status().isOk());

        // Validate the RaFormula in the database
        List<RaFormula> raFormulaList = raFormulaRepository.findAll();
        assertThat(raFormulaList).hasSize(databaseSizeBeforeUpdate);
        RaFormula testRaFormula = raFormulaList.get(raFormulaList.size() - 1);
        assertThat(testRaFormula.getDeptId()).isEqualTo(UPDATED_DEPT_ID);
        assertThat(testRaFormula.getWorkType()).isEqualTo(UPDATED_WORK_TYPE);
        assertThat(testRaFormula.getFormula()).isEqualTo(UPDATED_FORMULA);
        assertThat(testRaFormula.getAwFormula()).isEqualTo(UPDATED_AW_FORMULA);
        assertThat(testRaFormula.getRoyaltyFormula()).isEqualTo(UPDATED_ROYALTY_FORMULA);
    }

    @Test
    @Transactional
    void patchNonExistingRaFormula() throws Exception {
        int databaseSizeBeforeUpdate = raFormulaRepository.findAll().size();
        raFormula.setId(count.incrementAndGet());

        // Create the RaFormula
        RaFormulaDTO raFormulaDTO = raFormulaMapper.toDto(raFormula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaFormulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, raFormulaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(raFormulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaFormula in the database
        List<RaFormula> raFormulaList = raFormulaRepository.findAll();
        assertThat(raFormulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRaFormula() throws Exception {
        int databaseSizeBeforeUpdate = raFormulaRepository.findAll().size();
        raFormula.setId(count.incrementAndGet());

        // Create the RaFormula
        RaFormulaDTO raFormulaDTO = raFormulaMapper.toDto(raFormula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaFormulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(raFormulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaFormula in the database
        List<RaFormula> raFormulaList = raFormulaRepository.findAll();
        assertThat(raFormulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRaFormula() throws Exception {
        int databaseSizeBeforeUpdate = raFormulaRepository.findAll().size();
        raFormula.setId(count.incrementAndGet());

        // Create the RaFormula
        RaFormulaDTO raFormulaDTO = raFormulaMapper.toDto(raFormula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaFormulaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(raFormulaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RaFormula in the database
        List<RaFormula> raFormulaList = raFormulaRepository.findAll();
        assertThat(raFormulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRaFormula() throws Exception {
        // Initialize the database
        raFormulaRepository.saveAndFlush(raFormula);

        int databaseSizeBeforeDelete = raFormulaRepository.findAll().size();

        // Delete the raFormula
        restRaFormulaMockMvc
            .perform(delete(ENTITY_API_URL_ID, raFormula.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RaFormula> raFormulaList = raFormulaRepository.findAll();
        assertThat(raFormulaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
