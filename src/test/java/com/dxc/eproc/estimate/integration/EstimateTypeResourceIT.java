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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.model.EstimateType;
import com.dxc.eproc.estimate.repository.EstimateTypeRepository;
import com.dxc.eproc.estimate.service.dto.EstimateTypeDTO;
import com.dxc.eproc.estimate.service.mapper.EstimateTypeMapper;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class EstimateTypeResourceIT extends AbstractTestNGSpringContextTests {

	private final static Logger log = LoggerFactory.getLogger(EstimateTypeResourceIT.class);

	private static final String DEFAULT_ESTIMATE_TYPE_VALUE = "AAAAAAAAAA";
	private static final String UPDATED_ESTIMATE_TYPE_VALUE = "BBBBBBBBBB";

	private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
	private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

	private static final Boolean DEFAULT_ACTIVE_YN = true;
	private static final Boolean UPDATED_ACTIVE_YN = true;

	private static final String ENTITY_API_URL = "/v1/api/estimate-types";
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

	private static Random random = new Random();
	private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

	@Autowired
	private EstimateTypeRepository estimateTypeRepository;

	@Autowired
	private EstimateTypeMapper estimateTypeMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restEstimateTypeMockMvc;

	private EstimateType estimateType;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static EstimateType createEntity(EntityManager em) {
		EstimateType estimateType = new EstimateType().estimateTypeValue(DEFAULT_ESTIMATE_TYPE_VALUE)
				.valueType(DEFAULT_VALUE_TYPE).activeYn(DEFAULT_ACTIVE_YN);
		return estimateType;
	}

	@BeforeMethod
	public void initTest() {
		log.info("==================================================================================");
		log.info("This is executed before each Test - initTest");

		estimateType = createEntity(em);
	}

	@Test
	void createEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createEstimateType - Start");

		int databaseSizeBeforeCreate = estimateTypeRepository.findAll().size();
		// Create the EstimateType
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);
		restEstimateTypeMockMvc.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(estimateTypeDTO))).andExpect(status().isCreated());

		// Validate the EstimateType in the database
		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeCreate + 1);
		EstimateType testEstimateType = estimateTypeList.get(estimateTypeList.size() - 1);
		assertThat(testEstimateType.getEstimateTypeValue()).isEqualTo(DEFAULT_ESTIMATE_TYPE_VALUE);
		assertThat(testEstimateType.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
		assertThat(testEstimateType.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);

		log.info("==========================================================================");
		log.info("Test - createEstimateType - End");
	}

	@Test
	void createEstimateTypeWithExistingId() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createEstimateTypeWithExistingId - Start");

		// Create the EstimateType with an existing ID
		estimateType.setId(1L);
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);

		int databaseSizeBeforeCreate = estimateTypeRepository.findAll().size();

		// An entity with an existing ID cannot be created, so this API call must fail
		restEstimateTypeMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(estimateTypeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the EstimateType in the database
		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeCreate);

		log.info("==========================================================================");
		log.info("Test - createEstimateTypeWithExistingId - End");
	}

	@Test
	void checkEstimateTypeValueIsRequired() throws Exception {
		log.info("==========================================================================");
		log.info("Test -checkEstimateTypeValueIsRequired - Start");

		int databaseSizeBeforeTest = estimateTypeRepository.findAll().size();
		// set the field null
		estimateType.setEstimateTypeValue(null);

		// Create the EstimateType, which fails.
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);

		restEstimateTypeMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(estimateTypeDTO)))
				.andExpect(status().isBadRequest());

		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeTest);

		log.info("==========================================================================");
		log.info("Test - checkEstimateTypeValueIsRequired - End");
	}

	@Test
	void checkValueTypeIsRequired() throws Exception {
		log.info("==========================================================================");
		log.info("Test - checkValueTypeIsRequired - Start");

		int databaseSizeBeforeTest = estimateTypeRepository.findAll().size();
		// set the field null
		estimateType.setValueType(null);

		// Create the EstimateType, which fails.
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);

		restEstimateTypeMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(estimateTypeDTO)))
				.andExpect(status().isBadRequest());

		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeTest);

		log.info("==========================================================================");
		log.info("Test -checkValueTypeIsRequired - End");
	}

	@Test
	void getAllEstimateTypes() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllEstimateTypes - Start");

		// Initialize the database
		estimateTypeRepository.saveAndFlush(estimateType);

		// Get all the estimateTypeList
		restEstimateTypeMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(estimateType.getId().intValue())))
				.andExpect(jsonPath("$.[*].estimateTypeValue").value(hasItem(DEFAULT_ESTIMATE_TYPE_VALUE)))
				.andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
				.andExpect(jsonPath("$.[*].activeYn").value(hasItem(DEFAULT_ACTIVE_YN.booleanValue())));

		log.info("==========================================================================");
		log.info("Test - getAllEstimateTypes - End");
	}

	@Test
	void getAllActiveEstimateTypes() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllActiveEstimateTypes - Start");

		// Initialize the database
		estimateTypeRepository.saveAndFlush(estimateType);

		// Get all the estimateTypeList
		restEstimateTypeMockMvc.perform(get("/v1/api/estimate-types-active")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(estimateType.getId().intValue())))
				.andExpect(jsonPath("$.[*].estimateTypeValue").value(hasItem(DEFAULT_ESTIMATE_TYPE_VALUE)))
				.andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
				.andExpect(jsonPath("$.[*].activeYn").value(hasItem(DEFAULT_ACTIVE_YN.booleanValue())));

		log.info("==========================================================================");
		log.info("Test - getAllActiveEstimateTypes - End");
	}

	@Test
	void getEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getEstimateType - Start");

		// Initialize the database
		estimateTypeRepository.saveAndFlush(estimateType);

		// Get the estimateType
		restEstimateTypeMockMvc.perform(get(ENTITY_API_URL_ID, estimateType.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(estimateType.getId().intValue()))
				.andExpect(jsonPath("$.estimateTypeValue").value(DEFAULT_ESTIMATE_TYPE_VALUE))
				.andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
				.andExpect(jsonPath("$.activeYn").value(DEFAULT_ACTIVE_YN.booleanValue()));

		log.info("==========================================================================");
		log.info("Test - getEstimateType - End");
	}

	@Test
	void getNonExistingEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getNonExistingEstimateType - Start");
		// Get the estimateType
		restEstimateTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());

		log.info("==========================================================================");
		log.info("Test - getNonExistingEstimateType - End");
	}

	@Test
	void putNewEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putNewEstimateType - Start");

		// Initialize the database
		estimateTypeRepository.saveAndFlush(estimateType);

		int databaseSizeBeforeUpdate = estimateTypeRepository.findAll().size();

		// Update the estimateType
		EstimateType updatedEstimateType = estimateTypeRepository.findById(estimateType.getId()).get();
		// Disconnect from session so that the updates on updatedEstimateType are not
		// directly saved in db
		em.detach(updatedEstimateType);
		updatedEstimateType.estimateTypeValue(UPDATED_ESTIMATE_TYPE_VALUE).valueType(UPDATED_VALUE_TYPE)
				.activeYn(UPDATED_ACTIVE_YN);
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(updatedEstimateType);

		restEstimateTypeMockMvc.perform(put(ENTITY_API_URL_ID, estimateTypeDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estimateTypeDTO)))
				.andExpect(status().isOk());

		// Validate the EstimateType in the database
		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeUpdate);
		EstimateType testEstimateType = estimateTypeList.get(estimateTypeList.size() - 1);
		assertThat(testEstimateType.getEstimateTypeValue()).isEqualTo(UPDATED_ESTIMATE_TYPE_VALUE);
		assertThat(testEstimateType.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
		assertThat(testEstimateType.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);

		log.info("==========================================================================");
		log.info("Test - putNewEstimateType - End");
	}

	@Test
	void putNonExistingEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putNonExistingEstimateType - Start");

		int databaseSizeBeforeUpdate = estimateTypeRepository.findAll().size();
		estimateType.setId(count.incrementAndGet());

		// Create the EstimateType
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restEstimateTypeMockMvc
				.perform(put(ENTITY_API_URL_ID, estimateTypeDTO.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(estimateTypeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the EstimateType in the database
		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test - putNonExistingEstimateType - End");
	}

	@Test
	void putWithIdMismatchEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putWithIdMismatchEstimateType - Start");

		int databaseSizeBeforeUpdate = estimateTypeRepository.findAll().size();
		estimateType.setId(count.incrementAndGet());

		// Create the EstimateType
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restEstimateTypeMockMvc
				.perform(put(ENTITY_API_URL_ID, count.incrementAndGet()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(estimateTypeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the EstimateType in the database
		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test - putWithIdMismatchEstimateType - End");
	}

	@Test
	void putWithMissingIdPathParamEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putWithMissingIdPathParamEstimateType - Start");

		int databaseSizeBeforeUpdate = estimateTypeRepository.findAll().size();
		estimateType.setId(count.incrementAndGet());

		// Create the EstimateType
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restEstimateTypeMockMvc
				.perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(estimateTypeDTO)))
				.andExpect(status().isMethodNotAllowed());

		// Validate the EstimateType in the database
		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test - putWithMissingIdPathParamEstimateType - End");
	}

	@Test
	void partialUpdateEstimateTypeWithPatch() throws Exception {
		log.info("==========================================================================");
		log.info("Test - partialUpdateEstimateTypeWithPatch - Start");

		// Initialize the database
		estimateTypeRepository.saveAndFlush(estimateType);

		int databaseSizeBeforeUpdate = estimateTypeRepository.findAll().size();

		// Update the estimateType using partial update
		EstimateType partialUpdatedEstimateType = new EstimateType();
		partialUpdatedEstimateType.setId(estimateType.getId());

		partialUpdatedEstimateType.estimateTypeValue(UPDATED_ESTIMATE_TYPE_VALUE).valueType(UPDATED_VALUE_TYPE)
				.activeYn(UPDATED_ACTIVE_YN);

		restEstimateTypeMockMvc
				.perform(patch(ENTITY_API_URL_ID, partialUpdatedEstimateType.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstimateType)))
				.andExpect(status().isOk());

		// Validate the EstimateType in the database
		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeUpdate);
		EstimateType testEstimateType = estimateTypeList.get(estimateTypeList.size() - 1);
		assertThat(testEstimateType.getEstimateTypeValue()).isEqualTo(UPDATED_ESTIMATE_TYPE_VALUE);
		assertThat(testEstimateType.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
		assertThat(testEstimateType.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);

		log.info("==========================================================================");
		log.info("Test - partialUpdateEstimateTypeWithPatch - End");
	}

	@Test
	void fullUpdateEstimateTypeWithPatch() throws Exception {
		log.info("==========================================================================");
		log.info("Test - fullUpdateEstimateTypeWithPatch - Start");

		// Initialize the database
		estimateTypeRepository.saveAndFlush(estimateType);

		int databaseSizeBeforeUpdate = estimateTypeRepository.findAll().size();

		// Update the estimateType using partial update
		EstimateType partialUpdatedEstimateType = new EstimateType();
		partialUpdatedEstimateType.setId(estimateType.getId());

		partialUpdatedEstimateType.estimateTypeValue(UPDATED_ESTIMATE_TYPE_VALUE).valueType(UPDATED_VALUE_TYPE)
				.activeYn(UPDATED_ACTIVE_YN);

		restEstimateTypeMockMvc
				.perform(patch(ENTITY_API_URL_ID, partialUpdatedEstimateType.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstimateType)))
				.andExpect(status().isOk());

		// Validate the EstimateType in the database
		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeUpdate);
		EstimateType testEstimateType = estimateTypeList.get(estimateTypeList.size() - 1);
		assertThat(testEstimateType.getEstimateTypeValue()).isEqualTo(UPDATED_ESTIMATE_TYPE_VALUE);
		assertThat(testEstimateType.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
		assertThat(testEstimateType.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);

		log.info("==========================================================================");
		log.info("Test - fullUpdateEstimateTypeWithPatch - End");
	}

	@Test
	void patchNonExistingEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - patchNonExistingEstimateType - Start");

		int databaseSizeBeforeUpdate = estimateTypeRepository.findAll().size();
		estimateType.setId(count.incrementAndGet());

		// Create the EstimateType
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restEstimateTypeMockMvc
				.perform(patch(ENTITY_API_URL_ID, estimateTypeDTO.getId()).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(estimateTypeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the EstimateType in the database
		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test - patchNonExistingEstimateType - End");
	}

	@Test
	void patchWithIdMismatchEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - patchWithIdMismatchEstimateType - Start");

		int databaseSizeBeforeUpdate = estimateTypeRepository.findAll().size();
		estimateType.setId(count.incrementAndGet());

		// Create the EstimateType
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restEstimateTypeMockMvc
				.perform(patch(ENTITY_API_URL_ID, count.incrementAndGet()).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(estimateTypeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the EstimateType in the database
		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test - patchWithIdMismatchEstimateType - End");
	}

	@Test
	void patchWithMissingIdPathParamEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - patchWithMissingIdPathParamEstimateType - Start");

		int databaseSizeBeforeUpdate = estimateTypeRepository.findAll().size();
		estimateType.setId(count.incrementAndGet());

		// Create the EstimateType
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restEstimateTypeMockMvc
				.perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(estimateTypeDTO)))
				.andExpect(status().isMethodNotAllowed());

		// Validate the EstimateType in the database
		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test - patchWithMissingIdPathParamEstimateType - End");
	}

	@Test
	void deleteNonExistingEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteNonExistingEstimateType - Start");

		int databaseSizeBeforeUpdate = estimateTypeRepository.findAll().size();
		estimateType.setId(count.incrementAndGet());

		// Create the EstimateType
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restEstimateTypeMockMvc
				.perform(delete(ENTITY_API_URL_ID, estimateTypeDTO.getId()).accept(MediaType.APPLICATION_JSON))

				.andExpect(status().isBadRequest());

		// Validate the EstimateType in the database
		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test - deleteNonExistingEstimateType - End");
	}

	@Test
	void deleteEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteEstimateType - Start");

		// Initialize the database
		estimateTypeRepository.saveAndFlush(estimateType);

		int databaseSizeBeforeDelete = estimateTypeRepository.findAll().size();

		// Delete the estimateType
		restEstimateTypeMockMvc
				.perform(delete(ENTITY_API_URL_ID, estimateType.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<EstimateType> estimateTypeList = estimateTypeRepository.findAll();
		assertThat(estimateTypeList).hasSize(databaseSizeBeforeDelete);

		log.info("==========================================================================");
		log.info("Test - deleteEstimateType - End");
	}
}
