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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.IntegrationTest;
import com.dxc.eproc.estimate.model.WorkCategoryAttribute;
import com.dxc.eproc.estimate.repository.WorkCategoryAttributeRepository;
import com.dxc.eproc.estimate.service.dto.WorkCategoryAttributeDTO;
import com.dxc.eproc.estimate.service.mapper.WorkCategoryAttributeMapper;

/**
 * Integration tests for the {@link WorkCategoryAttributeResource} REST
 * controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class WorkCategoryAttributeResourceIT extends AbstractTestNGSpringContextTests {
	/** The CONSTANT LOG. */
	private static final Logger log = LoggerFactory.getLogger(WorkEstimateFileUploadResourceIT.class);

	private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
	private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

	private static final Boolean DEFAULT_ACTIVE_YN = true;
	private static final Boolean UPDATED_ACTIVE_YN = false;

	private static final Long DEFAULT_WORK_CATEGORY_ID = 1L;
	private static final Long UPDATED_WORK_CATEGORY_ID = 2L;

	private static final Long DEFAULT_WORK_TYPE_ID = 1L;
	private static final Long UPDATED_WORK_TYPE_ID = 2L;

	private static final String ENTITY_API_URL = "/v1/api/work-category-attributes";
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

	private static Random random = new Random();
	private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

	@Autowired
	private WorkCategoryAttributeRepository workCategoryAttributeRepository;

	@Autowired
	private WorkCategoryAttributeMapper workCategoryAttributeMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restWorkCategoryAttributeMockMvc;

	private WorkCategoryAttribute workCategoryAttribute;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static WorkCategoryAttribute createEntity(EntityManager em) {
		WorkCategoryAttribute workCategoryAttribute = new WorkCategoryAttribute().attributeName(DEFAULT_ATTRIBUTE_NAME)
				.activeYn(DEFAULT_ACTIVE_YN).workCategoryId(DEFAULT_WORK_CATEGORY_ID).workTypeId(DEFAULT_WORK_TYPE_ID);
		return workCategoryAttribute;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static WorkCategoryAttribute createUpdatedEntity(EntityManager em) {
		WorkCategoryAttribute workCategoryAttribute = new WorkCategoryAttribute().attributeName(UPDATED_ATTRIBUTE_NAME)
				.activeYn(UPDATED_ACTIVE_YN).workCategoryId(UPDATED_WORK_CATEGORY_ID).workTypeId(UPDATED_WORK_TYPE_ID);
		return workCategoryAttribute;
	}

	@BeforeMethod
	public void initTest() {
		workCategoryAttribute = createEntity(em);
	}

	@Test(priority = 1)
	@Transactional
	void createWorkCategoryAttribute() throws Exception {
		log.info("==================================================================================");
		log.info("Test - createWorkCategoryAttributeTest Start");
		int databaseSizeBeforeCreate = workCategoryAttributeRepository.findAll().size();
		// Create the WorkCategoryAttribute
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);
		restWorkCategoryAttributeMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryAttributeDTO)))
				.andExpect(status().isCreated());

		// Validate the WorkCategoryAttribute in the database
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeCreate + 1);
		WorkCategoryAttribute testWorkCategoryAttribute = workCategoryAttributeList
				.get(workCategoryAttributeList.size() - 1);
		assertThat(testWorkCategoryAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
		// assertThat(testWorkCategoryAttribute.getActiveYn()).isEqualTo(DEFAULT_ACTIVE_YN);
		assertThat(testWorkCategoryAttribute.getWorkCategoryId()).isEqualTo(DEFAULT_WORK_CATEGORY_ID);
		assertThat(testWorkCategoryAttribute.getWorkTypeId()).isEqualTo(DEFAULT_WORK_TYPE_ID);
		log.info("==================================================================================");
		log.info("Test - createWorkCategoryAttributeTest Ends");
	}

	@Test(priority = 2)
	@Transactional
	void createWorkCategoryAttributeWithExistingId() throws Exception {
		log.info("==================================================================================");
		log.info("Test - createWorkCategoryAttributeWithExistingId Start");
		// Create the WorkCategoryAttribute with an existing ID
		workCategoryAttribute.setId(1L);
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);

		int databaseSizeBeforeCreate = workCategoryAttributeRepository.findAll().size();

		// An entity with an existing ID cannot be created, so this API call must fail
		restWorkCategoryAttributeMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryAttributeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkCategoryAttribute in the database
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeCreate);
		log.info("==================================================================================");
		log.info("Test - createWorkCategoryAttributeWithExistingId Ends");
	}

	@Test(priority = 3)
	@Transactional
	void checkAttributeNameIsRequired() throws Exception {
		log.info("==================================================================================");
		log.info("Test - checkAttributeNameIsRequired Start");
		int databaseSizeBeforeTest = workCategoryAttributeRepository.findAll().size();
		// set the field null
		workCategoryAttribute.setAttributeName(null);

		// Create the WorkCategoryAttribute, which fails.
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);

		restWorkCategoryAttributeMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryAttributeDTO)))
				.andExpect(status().isBadRequest());

		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeTest);
		log.info("==================================================================================");
		log.info("Test - checkAttributeNameIsRequired Ends");
	}

	@Test(priority = 4)
	@Transactional
	void checkActiveYnIsRequired() throws Exception {
		log.info("==================================================================================");
		log.info("Test - checkActiveYnIsRequired Start");
		int databaseSizeBeforeTest = workCategoryAttributeRepository.findAll().size();
		// set the field null
		workCategoryAttribute.setActiveYn(null);

		// Create the WorkCategoryAttribute, which fails.
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);

		restWorkCategoryAttributeMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryAttributeDTO)))
				.andExpect(status().isBadRequest());

		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeTest);
		log.info("==================================================================================");
		log.info("Test - checkActiveYnIsRequired Ends");
	}

	@Test(priority = 5)
	@Transactional
	void checkWorkCategoryIdIsRequired() throws Exception {
		log.info("==================================================================================");
		log.info("Test - checkWorkCategoryIdIsRequired Start");
		int databaseSizeBeforeTest = workCategoryAttributeRepository.findAll().size();
		// set the field null
		workCategoryAttribute.setWorkCategoryId(null);

		// Create the WorkCategoryAttribute, which fails.
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);

		restWorkCategoryAttributeMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryAttributeDTO)))
				.andExpect(status().isBadRequest());

		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeTest);
		log.info("==================================================================================");
		log.info("Test - checkWorkCategoryIdIsRequired Ends");
	}

	@Test(priority = 6)
	@Transactional
	void checkWorkTypeIdIsRequired() throws Exception {
		log.info("==================================================================================");
		log.info("Test - checkWorkTypeIdIsRequired Start");
		int databaseSizeBeforeTest = workCategoryAttributeRepository.findAll().size();
		// set the field null
		workCategoryAttribute.setWorkTypeId(null);

		// Create the WorkCategoryAttribute, which fails.
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);

		restWorkCategoryAttributeMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryAttributeDTO)))
				.andExpect(status().isBadRequest());

		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeTest);
		log.info("==================================================================================");
		log.info("Test - checkWorkTypeIdIsRequired Ends");
	}

	@Test(priority = 7)
	@Transactional
	void getAllWorkCategoryAttributes() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getAllWorkCategoryAttributes Start");
		// Initialize the database
		workCategoryAttributeRepository.saveAndFlush(workCategoryAttribute);

		// Get all the workCategoryAttributeList
		restWorkCategoryAttributeMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(workCategoryAttribute.getId().intValue())))
				.andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
				.andExpect(jsonPath("$.[*].activeYn").value(hasItem(DEFAULT_ACTIVE_YN.booleanValue())))
				.andExpect(jsonPath("$.[*].workCategoryId").value(hasItem(DEFAULT_WORK_CATEGORY_ID.intValue())))
				.andExpect(jsonPath("$.[*].workTypeId").value(hasItem(DEFAULT_WORK_TYPE_ID.intValue())));
		log.info("==================================================================================");
		log.info("Test - getAllWorkCategoryAttributes Ends");
	}

	@Test(priority = 8)
	@Transactional
	void getWorkCategoryAttribute() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getWorkCategoryAttribute Start");
		// Initialize the database
		workCategoryAttributeRepository.saveAndFlush(workCategoryAttribute);

		// Get the workCategoryAttribute
		restWorkCategoryAttributeMockMvc.perform(get(ENTITY_API_URL_ID, workCategoryAttribute.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(workCategoryAttribute.getId().intValue()))
				.andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
				.andExpect(jsonPath("$.activeYn").value(DEFAULT_ACTIVE_YN.booleanValue()))
				.andExpect(jsonPath("$.workCategoryId").value(DEFAULT_WORK_CATEGORY_ID.intValue()))
				.andExpect(jsonPath("$.workTypeId").value(DEFAULT_WORK_TYPE_ID.intValue()));
		log.info("==================================================================================");
		log.info("Test - getWorkCategoryAttribute Ends");
	}

	@Test(priority = 9)
	@Transactional
	void getNonExistingWorkCategoryAttribute() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getNonExistingWorkCategoryAttribute Start");
		// Get the workCategoryAttribute
		restWorkCategoryAttributeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
				.andExpect(status().isNotFound());
		log.info("==================================================================================");
		log.info("Test - getNonExistingWorkCategoryAttribute Ends");
	}

	@Test(priority = 10)
	@Transactional
	void putNewWorkCategoryAttribute() throws Exception {
		log.info("==================================================================================");
		log.info("Test - putNewWorkCategoryAttribute Start");
		// Initialize the database
		workCategoryAttributeRepository.saveAndFlush(workCategoryAttribute);

		int databaseSizeBeforeUpdate = workCategoryAttributeRepository.findAll().size();

		// Update the workCategoryAttribute
		WorkCategoryAttribute updatedWorkCategoryAttribute = workCategoryAttributeRepository
				.findById(workCategoryAttribute.getId()).get();
		// Disconnect from session so that the updates on updatedWorkCategoryAttribute
		// are not directly saved in db
		em.detach(updatedWorkCategoryAttribute);
		updatedWorkCategoryAttribute.attributeName(UPDATED_ATTRIBUTE_NAME).activeYn(UPDATED_ACTIVE_YN)
				.workCategoryId(UPDATED_WORK_CATEGORY_ID).workTypeId(UPDATED_WORK_TYPE_ID);
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper
				.toDto(updatedWorkCategoryAttribute);

		restWorkCategoryAttributeMockMvc
				.perform(
						put(ENTITY_API_URL_ID, workCategoryAttributeDTO.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workCategoryAttributeDTO)))
				.andExpect(status().isOk());

		// Validate the WorkCategoryAttribute in the database
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeUpdate);
		WorkCategoryAttribute testWorkCategoryAttribute = workCategoryAttributeList
				.get(workCategoryAttributeList.size() - 1);
		assertThat(testWorkCategoryAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
		assertThat(testWorkCategoryAttribute.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);
		assertThat(testWorkCategoryAttribute.getWorkCategoryId()).isEqualTo(UPDATED_WORK_CATEGORY_ID);
		assertThat(testWorkCategoryAttribute.getWorkTypeId()).isEqualTo(UPDATED_WORK_TYPE_ID);
		log.info("==================================================================================");
		log.info("Test - putNewWorkCategoryAttribute Ends");
	}

	@Test(priority = 11)
	@Transactional
	void putNonExistingWorkCategoryAttribute() throws Exception {
		log.info("==================================================================================");
		log.info("Test - putNonExistingWorkCategoryAttribute Start");
		int databaseSizeBeforeUpdate = workCategoryAttributeRepository.findAll().size();
		workCategoryAttribute.setId(count.incrementAndGet());

		// Create the WorkCategoryAttribute
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restWorkCategoryAttributeMockMvc
				.perform(
						put(ENTITY_API_URL_ID, workCategoryAttributeDTO.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workCategoryAttributeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkCategoryAttribute in the database
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeUpdate);
		log.info("==================================================================================");
		log.info("Test - putNonExistingWorkCategoryAttribute Ends");
	}

	@Test(priority = 12)
	@Transactional
	void putWithIdMismatchWorkCategoryAttribute() throws Exception {
		log.info("==================================================================================");
		log.info("Test - putWithIdMismatchWorkCategoryAttribute Start");
		int databaseSizeBeforeUpdate = workCategoryAttributeRepository.findAll().size();
		workCategoryAttribute.setId(count.incrementAndGet());

		// Create the WorkCategoryAttribute
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restWorkCategoryAttributeMockMvc
				.perform(put(ENTITY_API_URL_ID, count.incrementAndGet()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryAttributeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkCategoryAttribute in the database
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeUpdate);
		log.info("==================================================================================");
		log.info("Test - putWithIdMismatchWorkCategoryAttribute Ends");
	}

	@Test(priority = 13)
	@Transactional
	void putWithMissingIdPathParamWorkCategoryAttribute() throws Exception {
		log.info("==================================================================================");
		log.info("Test - putWithMissingIdPathParamWorkCategoryAttribute Start");
		int databaseSizeBeforeUpdate = workCategoryAttributeRepository.findAll().size();
		workCategoryAttribute.setId(count.incrementAndGet());

		// Create the WorkCategoryAttribute
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restWorkCategoryAttributeMockMvc
				.perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryAttributeDTO)))
				.andExpect(status().isMethodNotAllowed());

		// Validate the WorkCategoryAttribute in the database
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeUpdate);
		log.info("==================================================================================");
		log.info("Test - putWithMissingIdPathParamWorkCategoryAttribute Ends");
	}

	@Test(priority = 14)
	@Transactional
	void partialUpdateWorkCategoryAttributeWithPatch() throws Exception {
		log.info("==================================================================================");
		log.info("Test - partialUpdateWorkCategoryAttributeWithPatch Start");
		// Initialize the database
		workCategoryAttributeRepository.saveAndFlush(workCategoryAttribute);

		int databaseSizeBeforeUpdate = workCategoryAttributeRepository.findAll().size();

		// Update the workCategoryAttribute using partial update
		WorkCategoryAttribute partialUpdatedWorkCategoryAttribute = new WorkCategoryAttribute();
		partialUpdatedWorkCategoryAttribute.setId(workCategoryAttribute.getId());

		partialUpdatedWorkCategoryAttribute.activeYn(UPDATED_ACTIVE_YN);

		restWorkCategoryAttributeMockMvc
				.perform(patch(ENTITY_API_URL_ID, partialUpdatedWorkCategoryAttribute.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkCategoryAttribute)))
				.andExpect(status().isOk());

		// Validate the WorkCategoryAttribute in the database
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeUpdate);
		WorkCategoryAttribute testWorkCategoryAttribute = workCategoryAttributeList
				.get(workCategoryAttributeList.size() - 1);
		assertThat(testWorkCategoryAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
		assertThat(testWorkCategoryAttribute.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);
		assertThat(testWorkCategoryAttribute.getWorkCategoryId()).isEqualTo(DEFAULT_WORK_CATEGORY_ID);
		assertThat(testWorkCategoryAttribute.getWorkTypeId()).isEqualTo(DEFAULT_WORK_TYPE_ID);
		log.info("==================================================================================");
		log.info("Test - partialUpdateWorkCategoryAttributeWithPatch Ends");
	}

	@Test(priority = 15)
	@Transactional
	void fullUpdateWorkCategoryAttributeWithPatch() throws Exception {
		log.info("==================================================================================");
		log.info("Test - fullUpdateWorkCategoryAttributeWithPatch Start");
		// Initialize the database
		workCategoryAttributeRepository.saveAndFlush(workCategoryAttribute);

		int databaseSizeBeforeUpdate = workCategoryAttributeRepository.findAll().size();

		// Update the workCategoryAttribute using partial update
		WorkCategoryAttribute partialUpdatedWorkCategoryAttribute = new WorkCategoryAttribute();
		partialUpdatedWorkCategoryAttribute.setId(workCategoryAttribute.getId());

		partialUpdatedWorkCategoryAttribute.attributeName(UPDATED_ATTRIBUTE_NAME).activeYn(UPDATED_ACTIVE_YN)
				.workCategoryId(UPDATED_WORK_CATEGORY_ID).workTypeId(UPDATED_WORK_TYPE_ID);

		restWorkCategoryAttributeMockMvc
				.perform(patch(ENTITY_API_URL_ID, partialUpdatedWorkCategoryAttribute.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkCategoryAttribute)))
				.andExpect(status().isOk());

		// Validate the WorkCategoryAttribute in the database
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeUpdate);
		WorkCategoryAttribute testWorkCategoryAttribute = workCategoryAttributeList
				.get(workCategoryAttributeList.size() - 1);
		assertThat(testWorkCategoryAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
		assertThat(testWorkCategoryAttribute.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);
		assertThat(testWorkCategoryAttribute.getWorkCategoryId()).isEqualTo(UPDATED_WORK_CATEGORY_ID);
		assertThat(testWorkCategoryAttribute.getWorkTypeId()).isEqualTo(UPDATED_WORK_TYPE_ID);
		log.info("==================================================================================");
		log.info("Test - fullUpdateWorkCategoryAttributeWithPatch Ends");
	}

	@Test(priority = 16)
	@Transactional
	void patchNonExistingWorkCategoryAttribute() throws Exception {
		log.info("==================================================================================");
		log.info("Test - patchNonExistingWorkCategoryAttribute Start");
		int databaseSizeBeforeUpdate = workCategoryAttributeRepository.findAll().size();
		workCategoryAttribute.setId(count.incrementAndGet());

		// Create the WorkCategoryAttribute
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restWorkCategoryAttributeMockMvc
				.perform(patch(ENTITY_API_URL_ID, workCategoryAttributeDTO.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workCategoryAttributeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkCategoryAttribute in the database
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeUpdate);
		log.info("==================================================================================");
		log.info("Test - patchNonExistingWorkCategoryAttribute Ends");
	}

	@Test(priority = 17)
	@Transactional
	void patchWithIdMismatchWorkCategoryAttribute() throws Exception {
		log.info("==================================================================================");
		log.info("Test - patchWithIdMismatchWorkCategoryAttribute Start");
		int databaseSizeBeforeUpdate = workCategoryAttributeRepository.findAll().size();
		workCategoryAttribute.setId(count.incrementAndGet());

		// Create the WorkCategoryAttribute
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restWorkCategoryAttributeMockMvc
				.perform(patch(ENTITY_API_URL_ID, count.incrementAndGet()).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workCategoryAttributeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkCategoryAttribute in the database
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeUpdate);
		log.info("==================================================================================");
		log.info("Test - patchWithIdMismatchWorkCategoryAttribute Ends");
	}

	@Test(priority = 17)
	@Transactional
	void patchWithMissingIdPathParamWorkCategoryAttribute() throws Exception {
		log.info("==================================================================================");
		log.info("Test - patchWithMissingIdPathParamWorkCategoryAttribute Start");
		int databaseSizeBeforeUpdate = workCategoryAttributeRepository.findAll().size();
		workCategoryAttribute.setId(count.incrementAndGet());

		// Create the WorkCategoryAttribute
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restWorkCategoryAttributeMockMvc
				.perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workCategoryAttributeDTO)))
				.andExpect(status().isMethodNotAllowed());

		// Validate the WorkCategoryAttribute in the database
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeUpdate);
		log.info("==================================================================================");
		log.info("Test - patchWithMissingIdPathParamWorkCategoryAttribute Ends");
	}

	@Test(priority = 18)
	@Transactional
	void getAllActiveWorkCategoryAttributes() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getAllActiveWorkCategoryAttributes Start");
		// Initialize the database
		workCategoryAttributeRepository.saveAndFlush(workCategoryAttribute);

		// Get all the workCategoryAttributeList
		restWorkCategoryAttributeMockMvc.perform(get("/v1/api/work-category-attributes-active" + "?sort=id,desc"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(workCategoryAttribute.getId().intValue())))
				.andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
				.andExpect(jsonPath("$.[*].activeYn").value(hasItem(DEFAULT_ACTIVE_YN.booleanValue())))
				.andExpect(jsonPath("$.[*].workCategoryId").value(hasItem(DEFAULT_WORK_CATEGORY_ID.intValue())))
				.andExpect(jsonPath("$.[*].workTypeId").value(hasItem(DEFAULT_WORK_TYPE_ID.intValue())));
		log.info("==================================================================================");
		log.info("Test - getAllActiveWorkCategoryAttributes Ends");
	}

	@Test(priority = 19)
	@Transactional
	void getAllActiveWorkCategoryAttributesList() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getAllActiveWorkCategoryAttributesList Start");
		// Initialize the database
		workCategoryAttributeRepository.saveAndFlush(workCategoryAttribute);

		// Get all the workCategoryAttributeList
		restWorkCategoryAttributeMockMvc
				.perform(get("/v1/api/estimate-work-type/{workTypeId}/work-category/{workCategoryId}/active-attributes",
						workCategoryAttribute.getWorkTypeId(), workCategoryAttribute.getWorkCategoryId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(workCategoryAttribute.getId().intValue())))
				.andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
				.andExpect(jsonPath("$.[*].activeYn").value(hasItem(DEFAULT_ACTIVE_YN.booleanValue())))
				.andExpect(jsonPath("$.[*].workCategoryId").value(hasItem(DEFAULT_WORK_CATEGORY_ID.intValue())))
				.andExpect(jsonPath("$.[*].workTypeId").value(hasItem(DEFAULT_WORK_TYPE_ID.intValue())));
		log.info("==================================================================================");
		log.info("Test - getAllActiveWorkCategoryAttributesList Ends");
	}

	@Test(priority = 19)
	@Transactional
	void deleteWorkCategoryAttribute() throws Exception {
		log.info("==================================================================================");
		log.info("Test - deleteWorkCategoryAttribute Start");
		// Initialize the database
		workCategoryAttributeRepository.saveAndFlush(workCategoryAttribute);

		int databaseSizeBeforeDelete = workCategoryAttributeRepository.findAll().size();

		// Delete the workCategoryAttribute
		restWorkCategoryAttributeMockMvc
				.perform(delete(ENTITY_API_URL_ID, workCategoryAttribute.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<WorkCategoryAttribute> workCategoryAttributeList = workCategoryAttributeRepository.findAll();
		assertThat(workCategoryAttributeList).hasSize(databaseSizeBeforeDelete);
		log.info("==================================================================================");
		log.info("Test - deleteWorkCategoryAttribute Ends");
	}

	@Test(priority = 20)
	@Transactional
	void deleteWorkCategoryAttributeInvalidId() throws Exception {
		log.info("==================================================================================");
		log.info("Test - deleteWorkCategoryAttributeInvalidId Start");
		// Initialize the database
		workCategoryAttributeRepository.saveAndFlush(workCategoryAttribute);

		final Long WORKESTIMATEID = Long.MAX_VALUE;
		// Delete the workCategoryAttribute
		restWorkCategoryAttributeMockMvc
				.perform(delete(ENTITY_API_URL_ID, WORKESTIMATEID).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

		log.info("==================================================================================");
		log.info("Test - deleteWorkCategoryAttributeInvalidId Ends");
	}
}
