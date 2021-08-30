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
//import com.dxc.eproc.estimate.controller.RaParametersController;
//import com.dxc.eproc.estimate.model.RaParameters;
//import com.dxc.eproc.estimate.repository.RaParametersRepository;
//import com.dxc.eproc.estimate.service.dto.RaParametersDTO;
//import com.dxc.eproc.estimate.service.mapper.RaParametersMapper;
//
///**
// * Integration tests for the {@link RaParametersController} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class RaParametersResourceIT {
//
//    private static final String DEFAULT_PARAM_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_PARAM_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_PARAM_TABLE = "AAAAAAAAAA";
//    private static final String UPDATED_PARAM_TABLE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_PARAM_FIELD = "AAAAAAAAAA";
//    private static final String UPDATED_PARAM_FIELD = "BBBBBBBBBB";
//
//    private static final String ENTITY_API_URL = "/api/ra-parameters";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private RaParametersRepository raParametersRepository;
//
//    @Autowired
//    private RaParametersMapper raParametersMapper;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restRaParametersMockMvc;
//
//    private RaParameters raParameters;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static RaParameters createEntity(EntityManager em) {
//        RaParameters raParameters = new RaParameters()
//            .paramName(DEFAULT_PARAM_NAME)
//            .paramTable(DEFAULT_PARAM_TABLE)
//            .paramField(DEFAULT_PARAM_FIELD);
//        return raParameters;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static RaParameters createUpdatedEntity(EntityManager em) {
//        RaParameters raParameters = new RaParameters()
//            .paramName(UPDATED_PARAM_NAME)
//            .paramTable(UPDATED_PARAM_TABLE)
//            .paramField(UPDATED_PARAM_FIELD);
//        return raParameters;
//    }
//
//    @BeforeMethod
//    public void initTest() {
//        raParameters = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createRaParameters() throws Exception {
//        int databaseSizeBeforeCreate = raParametersRepository.findAll().size();
//        // Create the RaParameters
//        RaParametersDTO raParametersDTO = raParametersMapper.toDto(raParameters);
//        restRaParametersMockMvc
//            .perform(
//                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raParametersDTO))
//            )
//            .andExpect(status().isCreated());
//
//        // Validate the RaParameters in the database
//        List<RaParameters> raParametersList = raParametersRepository.findAll();
//        assertThat(raParametersList).hasSize(databaseSizeBeforeCreate + 1);
//        RaParameters testRaParameters = raParametersList.get(raParametersList.size() - 1);
//        assertThat(testRaParameters.getParamName()).isEqualTo(DEFAULT_PARAM_NAME);
//        assertThat(testRaParameters.getParamTable()).isEqualTo(DEFAULT_PARAM_TABLE);
//        assertThat(testRaParameters.getParamField()).isEqualTo(DEFAULT_PARAM_FIELD);
//    }
//
//    @Test
//    @Transactional
//    void createRaParametersWithExistingId() throws Exception {
//        // Create the RaParameters with an existing ID
//        raParameters.setId(1L);
//        RaParametersDTO raParametersDTO = raParametersMapper.toDto(raParameters);
//
//        int databaseSizeBeforeCreate = raParametersRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restRaParametersMockMvc
//            .perform(
//                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raParametersDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the RaParameters in the database
//        List<RaParameters> raParametersList = raParametersRepository.findAll();
//        assertThat(raParametersList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void getAllRaParameters() throws Exception {
//        // Initialize the database
//        raParametersRepository.saveAndFlush(raParameters);
//
//        // Get all the raParametersList
//        restRaParametersMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(raParameters.getId().intValue())))
//            .andExpect(jsonPath("$.[*].paramName").value(hasItem(DEFAULT_PARAM_NAME)))
//            .andExpect(jsonPath("$.[*].paramTable").value(hasItem(DEFAULT_PARAM_TABLE)))
//            .andExpect(jsonPath("$.[*].paramField").value(hasItem(DEFAULT_PARAM_FIELD)));
//    }
//
//    @Test
//    @Transactional
//    void getRaParameters() throws Exception {
//        // Initialize the database
//        raParametersRepository.saveAndFlush(raParameters);
//
//        // Get the raParameters
//        restRaParametersMockMvc
//            .perform(get(ENTITY_API_URL_ID, raParameters.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(raParameters.getId().intValue()))
//            .andExpect(jsonPath("$.paramName").value(DEFAULT_PARAM_NAME))
//            .andExpect(jsonPath("$.paramTable").value(DEFAULT_PARAM_TABLE))
//            .andExpect(jsonPath("$.paramField").value(DEFAULT_PARAM_FIELD));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingRaParameters() throws Exception {
//        // Get the raParameters
//        restRaParametersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putNewRaParameters() throws Exception {
//        // Initialize the database
//        raParametersRepository.saveAndFlush(raParameters);
//
//        int databaseSizeBeforeUpdate = raParametersRepository.findAll().size();
//
//        // Update the raParameters
//        RaParameters updatedRaParameters = raParametersRepository.findById(raParameters.getId()).get();
//        // Disconnect from session so that the updates on updatedRaParameters are not directly saved in db
//        em.detach(updatedRaParameters);
//        updatedRaParameters.paramName(UPDATED_PARAM_NAME).paramTable(UPDATED_PARAM_TABLE).paramField(UPDATED_PARAM_FIELD);
//        RaParametersDTO raParametersDTO = raParametersMapper.toDto(updatedRaParameters);
//
//        restRaParametersMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, raParametersDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(raParametersDTO))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the RaParameters in the database
//        List<RaParameters> raParametersList = raParametersRepository.findAll();
//        assertThat(raParametersList).hasSize(databaseSizeBeforeUpdate);
//        RaParameters testRaParameters = raParametersList.get(raParametersList.size() - 1);
//        assertThat(testRaParameters.getParamName()).isEqualTo(UPDATED_PARAM_NAME);
//        assertThat(testRaParameters.getParamTable()).isEqualTo(UPDATED_PARAM_TABLE);
//        assertThat(testRaParameters.getParamField()).isEqualTo(UPDATED_PARAM_FIELD);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingRaParameters() throws Exception {
//        int databaseSizeBeforeUpdate = raParametersRepository.findAll().size();
//        raParameters.setId(count.incrementAndGet());
//
//        // Create the RaParameters
//        RaParametersDTO raParametersDTO = raParametersMapper.toDto(raParameters);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restRaParametersMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, raParametersDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(raParametersDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the RaParameters in the database
//        List<RaParameters> raParametersList = raParametersRepository.findAll();
//        assertThat(raParametersList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchRaParameters() throws Exception {
//        int databaseSizeBeforeUpdate = raParametersRepository.findAll().size();
//        raParameters.setId(count.incrementAndGet());
//
//        // Create the RaParameters
//        RaParametersDTO raParametersDTO = raParametersMapper.toDto(raParameters);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restRaParametersMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(raParametersDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the RaParameters in the database
//        List<RaParameters> raParametersList = raParametersRepository.findAll();
//        assertThat(raParametersList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamRaParameters() throws Exception {
//        int databaseSizeBeforeUpdate = raParametersRepository.findAll().size();
//        raParameters.setId(count.incrementAndGet());
//
//        // Create the RaParameters
//        RaParametersDTO raParametersDTO = raParametersMapper.toDto(raParameters);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restRaParametersMockMvc
//            .perform(
//                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raParametersDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the RaParameters in the database
//        List<RaParameters> raParametersList = raParametersRepository.findAll();
//        assertThat(raParametersList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateRaParametersWithPatch() throws Exception {
//        // Initialize the database
//        raParametersRepository.saveAndFlush(raParameters);
//
//        int databaseSizeBeforeUpdate = raParametersRepository.findAll().size();
//
//        // Update the raParameters using partial update
//        RaParameters partialUpdatedRaParameters = new RaParameters();
//        partialUpdatedRaParameters.setId(raParameters.getId());
//
//        partialUpdatedRaParameters.paramTable(UPDATED_PARAM_TABLE).paramField(UPDATED_PARAM_FIELD);
//
//        restRaParametersMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedRaParameters.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaParameters))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the RaParameters in the database
//        List<RaParameters> raParametersList = raParametersRepository.findAll();
//        assertThat(raParametersList).hasSize(databaseSizeBeforeUpdate);
//        RaParameters testRaParameters = raParametersList.get(raParametersList.size() - 1);
//        assertThat(testRaParameters.getParamName()).isEqualTo(DEFAULT_PARAM_NAME);
//        assertThat(testRaParameters.getParamTable()).isEqualTo(UPDATED_PARAM_TABLE);
//        assertThat(testRaParameters.getParamField()).isEqualTo(UPDATED_PARAM_FIELD);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateRaParametersWithPatch() throws Exception {
//        // Initialize the database
//        raParametersRepository.saveAndFlush(raParameters);
//
//        int databaseSizeBeforeUpdate = raParametersRepository.findAll().size();
//
//        // Update the raParameters using partial update
//        RaParameters partialUpdatedRaParameters = new RaParameters();
//        partialUpdatedRaParameters.setId(raParameters.getId());
//
//        partialUpdatedRaParameters.paramName(UPDATED_PARAM_NAME).paramTable(UPDATED_PARAM_TABLE).paramField(UPDATED_PARAM_FIELD);
//
//        restRaParametersMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedRaParameters.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaParameters))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the RaParameters in the database
//        List<RaParameters> raParametersList = raParametersRepository.findAll();
//        assertThat(raParametersList).hasSize(databaseSizeBeforeUpdate);
//        RaParameters testRaParameters = raParametersList.get(raParametersList.size() - 1);
//        assertThat(testRaParameters.getParamName()).isEqualTo(UPDATED_PARAM_NAME);
//        assertThat(testRaParameters.getParamTable()).isEqualTo(UPDATED_PARAM_TABLE);
//        assertThat(testRaParameters.getParamField()).isEqualTo(UPDATED_PARAM_FIELD);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingRaParameters() throws Exception {
//        int databaseSizeBeforeUpdate = raParametersRepository.findAll().size();
//        raParameters.setId(count.incrementAndGet());
//
//        // Create the RaParameters
//        RaParametersDTO raParametersDTO = raParametersMapper.toDto(raParameters);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restRaParametersMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, raParametersDTO.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(raParametersDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the RaParameters in the database
//        List<RaParameters> raParametersList = raParametersRepository.findAll();
//        assertThat(raParametersList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchRaParameters() throws Exception {
//        int databaseSizeBeforeUpdate = raParametersRepository.findAll().size();
//        raParameters.setId(count.incrementAndGet());
//
//        // Create the RaParameters
//        RaParametersDTO raParametersDTO = raParametersMapper.toDto(raParameters);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restRaParametersMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(raParametersDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the RaParameters in the database
//        List<RaParameters> raParametersList = raParametersRepository.findAll();
//        assertThat(raParametersList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamRaParameters() throws Exception {
//        int databaseSizeBeforeUpdate = raParametersRepository.findAll().size();
//        raParameters.setId(count.incrementAndGet());
//
//        // Create the RaParameters
//        RaParametersDTO raParametersDTO = raParametersMapper.toDto(raParameters);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restRaParametersMockMvc
//            .perform(
//                patch(ENTITY_API_URL)
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(raParametersDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the RaParameters in the database
//        List<RaParameters> raParametersList = raParametersRepository.findAll();
//        assertThat(raParametersList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteRaParameters() throws Exception {
//        // Initialize the database
//        raParametersRepository.saveAndFlush(raParameters);
//
//        int databaseSizeBeforeDelete = raParametersRepository.findAll().size();
//
//        // Delete the raParameters
//        restRaParametersMockMvc
//            .perform(delete(ENTITY_API_URL_ID, raParameters.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<RaParameters> raParametersList = raParametersRepository.findAll();
//        assertThat(raParametersList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
