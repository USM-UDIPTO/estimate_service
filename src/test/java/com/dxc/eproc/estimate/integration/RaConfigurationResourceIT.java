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
//import com.dxc.eproc.estimate.controller.RaConfigurationController;
//import com.dxc.eproc.estimate.model.RaConfiguration;
//import com.dxc.eproc.estimate.repository.RaConfigurationRepository;
//import com.dxc.eproc.estimate.service.dto.RaConfigurationDTO;
//import com.dxc.eproc.estimate.service.mapper.RaConfigurationMapper;
//
///**
// * Integration tests for the {@link RaConfigurationController} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class RaConfigurationResourceIT {
//
//    private static final Long DEFAULT_DEPT_ID = 1L;
//    private static final Long UPDATED_DEPT_ID = 2L;
//
//    private static final Long DEFAULT_RA_PARAM_ID = 1L;
//    private static final Long UPDATED_RA_PARAM_ID = 2L;
//
//    private static final String ENTITY_API_URL = "/api/ra-configurations";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private RaConfigurationRepository raConfigurationRepository;
//
//    @Autowired
//    private RaConfigurationMapper raConfigurationMapper;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restRaConfigurationMockMvc;
//
//    private RaConfiguration raConfiguration;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static RaConfiguration createEntity(EntityManager em) {
//        RaConfiguration raConfiguration = new RaConfiguration().deptId(DEFAULT_DEPT_ID).raParamId(DEFAULT_RA_PARAM_ID);
//        return raConfiguration;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static RaConfiguration createUpdatedEntity(EntityManager em) {
//        RaConfiguration raConfiguration = new RaConfiguration().deptId(UPDATED_DEPT_ID).raParamId(UPDATED_RA_PARAM_ID);
//        return raConfiguration;
//    }
//
//    @BeforeMethod
//    public void initTest() {
//        raConfiguration = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createRaConfiguration() throws Exception {
//        int databaseSizeBeforeCreate = raConfigurationRepository.findAll().size();
//        // Create the RaConfiguration
//        RaConfigurationDTO raConfigurationDTO = raConfigurationMapper.toDto(raConfiguration);
//        restRaConfigurationMockMvc
//            .perform(
//                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raConfigurationDTO))
//            )
//            .andExpect(status().isCreated());
//
//        // Validate the RaConfiguration in the database
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
//        RaConfiguration testRaConfiguration = raConfigurationList.get(raConfigurationList.size() - 1);
//        assertThat(testRaConfiguration.getDeptId()).isEqualTo(DEFAULT_DEPT_ID);
//        assertThat(testRaConfiguration.getRaParamId()).isEqualTo(DEFAULT_RA_PARAM_ID);
//    }
//
//    @Test
//    @Transactional
//    void createRaConfigurationWithExistingId() throws Exception {
//        // Create the RaConfiguration with an existing ID
//        raConfiguration.setId(1L);
//        RaConfigurationDTO raConfigurationDTO = raConfigurationMapper.toDto(raConfiguration);
//
//        int databaseSizeBeforeCreate = raConfigurationRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restRaConfigurationMockMvc
//            .perform(
//                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raConfigurationDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the RaConfiguration in the database
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkDeptIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = raConfigurationRepository.findAll().size();
//        // set the field null
//        raConfiguration.setDeptId(null);
//
//        // Create the RaConfiguration, which fails.
//        RaConfigurationDTO raConfigurationDTO = raConfigurationMapper.toDto(raConfiguration);
//
//        restRaConfigurationMockMvc
//            .perform(
//                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raConfigurationDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkRaParamIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = raConfigurationRepository.findAll().size();
//        // set the field null
//        raConfiguration.setRaParamId(null);
//
//        // Create the RaConfiguration, which fails.
//        RaConfigurationDTO raConfigurationDTO = raConfigurationMapper.toDto(raConfiguration);
//
//        restRaConfigurationMockMvc
//            .perform(
//                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raConfigurationDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllRaConfigurations() throws Exception {
//        // Initialize the database
//        raConfigurationRepository.saveAndFlush(raConfiguration);
//
//        // Get all the raConfigurationList
//        restRaConfigurationMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(raConfiguration.getId().intValue())))
//            .andExpect(jsonPath("$.[*].deptId").value(hasItem(DEFAULT_DEPT_ID.intValue())))
//            .andExpect(jsonPath("$.[*].raParamId").value(hasItem(DEFAULT_RA_PARAM_ID.intValue())));
//    }
//
//    @Test
//    @Transactional
//    void getRaConfiguration() throws Exception {
//        // Initialize the database
//        raConfigurationRepository.saveAndFlush(raConfiguration);
//
//        // Get the raConfiguration
//        restRaConfigurationMockMvc
//            .perform(get(ENTITY_API_URL_ID, raConfiguration.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(raConfiguration.getId().intValue()))
//            .andExpect(jsonPath("$.deptId").value(DEFAULT_DEPT_ID.intValue()))
//            .andExpect(jsonPath("$.raParamId").value(DEFAULT_RA_PARAM_ID.intValue()));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingRaConfiguration() throws Exception {
//        // Get the raConfiguration
//        restRaConfigurationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putNewRaConfiguration() throws Exception {
//        // Initialize the database
//        raConfigurationRepository.saveAndFlush(raConfiguration);
//
//        int databaseSizeBeforeUpdate = raConfigurationRepository.findAll().size();
//
//        // Update the raConfiguration
//        RaConfiguration updatedRaConfiguration = raConfigurationRepository.findById(raConfiguration.getId()).get();
//        // Disconnect from session so that the updates on updatedRaConfiguration are not directly saved in db
//        em.detach(updatedRaConfiguration);
//        updatedRaConfiguration.deptId(UPDATED_DEPT_ID).raParamId(UPDATED_RA_PARAM_ID);
//        RaConfigurationDTO raConfigurationDTO = raConfigurationMapper.toDto(updatedRaConfiguration);
//
//        restRaConfigurationMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, raConfigurationDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(raConfigurationDTO))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the RaConfiguration in the database
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeUpdate);
//        RaConfiguration testRaConfiguration = raConfigurationList.get(raConfigurationList.size() - 1);
//        assertThat(testRaConfiguration.getDeptId()).isEqualTo(UPDATED_DEPT_ID);
//        assertThat(testRaConfiguration.getRaParamId()).isEqualTo(UPDATED_RA_PARAM_ID);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingRaConfiguration() throws Exception {
//        int databaseSizeBeforeUpdate = raConfigurationRepository.findAll().size();
//        raConfiguration.setId(count.incrementAndGet());
//
//        // Create the RaConfiguration
//        RaConfigurationDTO raConfigurationDTO = raConfigurationMapper.toDto(raConfiguration);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restRaConfigurationMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, raConfigurationDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(raConfigurationDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the RaConfiguration in the database
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchRaConfiguration() throws Exception {
//        int databaseSizeBeforeUpdate = raConfigurationRepository.findAll().size();
//        raConfiguration.setId(count.incrementAndGet());
//
//        // Create the RaConfiguration
//        RaConfigurationDTO raConfigurationDTO = raConfigurationMapper.toDto(raConfiguration);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restRaConfigurationMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(raConfigurationDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the RaConfiguration in the database
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamRaConfiguration() throws Exception {
//        int databaseSizeBeforeUpdate = raConfigurationRepository.findAll().size();
//        raConfiguration.setId(count.incrementAndGet());
//
//        // Create the RaConfiguration
//        RaConfigurationDTO raConfigurationDTO = raConfigurationMapper.toDto(raConfiguration);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restRaConfigurationMockMvc
//            .perform(
//                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raConfigurationDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the RaConfiguration in the database
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateRaConfigurationWithPatch() throws Exception {
//        // Initialize the database
//        raConfigurationRepository.saveAndFlush(raConfiguration);
//
//        int databaseSizeBeforeUpdate = raConfigurationRepository.findAll().size();
//
//        // Update the raConfiguration using partial update
//        RaConfiguration partialUpdatedRaConfiguration = new RaConfiguration();
//        partialUpdatedRaConfiguration.setId(raConfiguration.getId());
//
//        partialUpdatedRaConfiguration.raParamId(UPDATED_RA_PARAM_ID);
//
//        restRaConfigurationMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedRaConfiguration.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaConfiguration))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the RaConfiguration in the database
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeUpdate);
//        RaConfiguration testRaConfiguration = raConfigurationList.get(raConfigurationList.size() - 1);
//        assertThat(testRaConfiguration.getDeptId()).isEqualTo(DEFAULT_DEPT_ID);
//        assertThat(testRaConfiguration.getRaParamId()).isEqualTo(UPDATED_RA_PARAM_ID);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateRaConfigurationWithPatch() throws Exception {
//        // Initialize the database
//        raConfigurationRepository.saveAndFlush(raConfiguration);
//
//        int databaseSizeBeforeUpdate = raConfigurationRepository.findAll().size();
//
//        // Update the raConfiguration using partial update
//        RaConfiguration partialUpdatedRaConfiguration = new RaConfiguration();
//        partialUpdatedRaConfiguration.setId(raConfiguration.getId());
//
//        partialUpdatedRaConfiguration.deptId(UPDATED_DEPT_ID).raParamId(UPDATED_RA_PARAM_ID);
//
//        restRaConfigurationMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedRaConfiguration.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaConfiguration))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the RaConfiguration in the database
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeUpdate);
//        RaConfiguration testRaConfiguration = raConfigurationList.get(raConfigurationList.size() - 1);
//        assertThat(testRaConfiguration.getDeptId()).isEqualTo(UPDATED_DEPT_ID);
//        assertThat(testRaConfiguration.getRaParamId()).isEqualTo(UPDATED_RA_PARAM_ID);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingRaConfiguration() throws Exception {
//        int databaseSizeBeforeUpdate = raConfigurationRepository.findAll().size();
//        raConfiguration.setId(count.incrementAndGet());
//
//        // Create the RaConfiguration
//        RaConfigurationDTO raConfigurationDTO = raConfigurationMapper.toDto(raConfiguration);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restRaConfigurationMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, raConfigurationDTO.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(raConfigurationDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the RaConfiguration in the database
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchRaConfiguration() throws Exception {
//        int databaseSizeBeforeUpdate = raConfigurationRepository.findAll().size();
//        raConfiguration.setId(count.incrementAndGet());
//
//        // Create the RaConfiguration
//        RaConfigurationDTO raConfigurationDTO = raConfigurationMapper.toDto(raConfiguration);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restRaConfigurationMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(raConfigurationDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the RaConfiguration in the database
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamRaConfiguration() throws Exception {
//        int databaseSizeBeforeUpdate = raConfigurationRepository.findAll().size();
//        raConfiguration.setId(count.incrementAndGet());
//
//        // Create the RaConfiguration
//        RaConfigurationDTO raConfigurationDTO = raConfigurationMapper.toDto(raConfiguration);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restRaConfigurationMockMvc
//            .perform(
//                patch(ENTITY_API_URL)
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(raConfigurationDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the RaConfiguration in the database
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteRaConfiguration() throws Exception {
//        // Initialize the database
//        raConfigurationRepository.saveAndFlush(raConfiguration);
//
//        int databaseSizeBeforeDelete = raConfigurationRepository.findAll().size();
//
//        // Delete the raConfiguration
//        restRaConfigurationMockMvc
//            .perform(delete(ENTITY_API_URL_ID, raConfiguration.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<RaConfiguration> raConfigurationList = raConfigurationRepository.findAll();
//        assertThat(raConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
