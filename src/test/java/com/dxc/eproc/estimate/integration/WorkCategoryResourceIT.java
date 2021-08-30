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
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.IntegrationTest;
import com.dxc.eproc.estimate.model.WorkCategory;
import com.dxc.eproc.estimate.repository.WorkCategoryRepository;
import com.dxc.eproc.estimate.service.dto.WorkCategoryDTO;
import com.dxc.eproc.estimate.service.mapper.WorkCategoryMapper;

/**
 * Integration tests for the {@link WorkCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkCategoryResourceIT extends AbstractTestNGSpringContextTests {
	/** The CONSTANT LOG. */
	private static final Logger log = LoggerFactory.getLogger(WorkEstimateFileUploadResourceIT.class);

	private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
	private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

	private static final String DEFAULT_CATEGORY_CODE = "AAAAAAAAAA";
	private static final String UPDATED_CATEGORY_CODE = "BBBBBBBBBB";

	private static final Boolean DEFAULT_ACTIVE_YN = true;
	private static final Boolean UPDATED_ACTIVE_YN = false;

	private static final String ENTITY_API_URL = "/v1/api/work-categories";
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

	private static Random random = new Random();
	private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

	@Autowired
	private WorkCategoryRepository workCategoryRepository;

	@Autowired
	private WorkCategoryMapper workCategoryMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restWorkCategoryMockMvc;

	private WorkCategory workCategory;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static WorkCategory createEntity(EntityManager em) {
		WorkCategory workCategory = new WorkCategory().categoryName(DEFAULT_CATEGORY_NAME)
				.categoryCode(DEFAULT_CATEGORY_CODE).activeYn(DEFAULT_ACTIVE_YN);
		return workCategory;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static WorkCategory createUpdatedEntity(EntityManager em) {
		WorkCategory workCategory = new WorkCategory().categoryName(UPDATED_CATEGORY_NAME)
				.categoryCode(UPDATED_CATEGORY_CODE).activeYn(UPDATED_ACTIVE_YN);
		return workCategory;
	}

	@BeforeMethod
	public void initTest() {
		workCategory = createEntity(em);
	}

	@Test(priority = 1)
	@Transactional
	void createWorkCategory() throws Exception {
		log.info("==================================================================================");
		log.info("Test - createWorkCategory Start");
		int databaseSizeBeforeCreate = workCategoryRepository.findAll().size();
		// Create the WorkCategory
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);
		restWorkCategoryMockMvc.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(workCategoryDTO))).andExpect(status().isCreated());

		// Validate the WorkCategory in the database
		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeCreate + 1);
		WorkCategory testWorkCategory = workCategoryList.get(workCategoryList.size() - 1);
		assertThat(testWorkCategory.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
		assertThat(testWorkCategory.getCategoryCode()).isEqualTo(DEFAULT_CATEGORY_CODE);
		assertThat(testWorkCategory.getActiveYn()).isEqualTo(DEFAULT_ACTIVE_YN);
		log.info("==================================================================================");
		log.info("Test - createWorkCategory Ends");
	}

	@Test(priority = 2)
	@Transactional
	void createWorkCategoryWithExistingId() throws Exception {
		log.info("==================================================================================");
		log.info("Test - createWorkCategoryWithExistingId Start");
		// Create the WorkCategory with an existing ID
		workCategory.setId(1L);
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);

		int databaseSizeBeforeCreate = workCategoryRepository.findAll().size();

		// An entity with an existing ID cannot be created, so this API call must fail
		restWorkCategoryMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkCategory in the database
		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeCreate);
		log.info("==================================================================================");
		log.info("Test - createWorkCategoryWithExistingId Ends");
	}

	@Test(priority = 3)
	@Transactional
	void checkCategoryNameIsRequired() throws Exception {
		log.info("==================================================================================");
		log.info("Test - checkCategoryNameIsRequired Start");
		int databaseSizeBeforeTest = workCategoryRepository.findAll().size();
		// set the field null
		workCategory.setCategoryName(null);

		// Create the WorkCategory, which fails.
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);

		restWorkCategoryMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryDTO)))
				.andExpect(status().isBadRequest());

		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeTest);
		log.info("==================================================================================");
		log.info("Test - checkCategoryNameIsRequired Ends");
	}

	@Test(priority = 4)
	@Transactional
	void checkCategoryCodeIsRequired() throws Exception {
		log.info("==================================================================================");
		log.info("Test - checkCategoryCodeIsRequired Start");
		int databaseSizeBeforeTest = workCategoryRepository.findAll().size();
		// set the field null
		workCategory.setCategoryCode(null);

		// Create the WorkCategory, which fails.
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);

		restWorkCategoryMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryDTO)))
				.andExpect(status().isBadRequest());

		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeTest);
		log.info("==================================================================================");
		log.info("Test - checkCategoryCodeIsRequired Ends");
	}

	@Test(priority = 5)
	@Transactional
	void checkActiveYnIsRequired() throws Exception {
		log.info("==================================================================================");
		log.info("Test - checkActiveYnIsRequired Start");
		int databaseSizeBeforeTest = workCategoryRepository.findAll().size();
		// set the field null
		workCategory.setActiveYn(null);

		// Create the WorkCategory, which fails.
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);

		restWorkCategoryMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryDTO)))
				.andExpect(status().isBadRequest());

		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeTest);
		log.info("==================================================================================");
		log.info("Test - checkActiveYnIsRequired Ends");
	}

	@Test(priority = 6)
	@Transactional
	void getAllWorkCategories() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getAllWorkCategories Start");
		// Initialize the database
		workCategoryRepository.saveAndFlush(workCategory);

		// Get all the workCategoryList
		restWorkCategoryMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(workCategory.getId().intValue())))
				.andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
				.andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE)))
				.andExpect(jsonPath("$.[*].activeYn").value(hasItem(DEFAULT_ACTIVE_YN.booleanValue())));
		log.info("==================================================================================");
		log.info("Test - getAllWorkCategories Ends");
	}

	@Test(priority = 7)
	@Transactional
	void getWorkCategory() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getWorkCategory Start");
		// Initialize the database
		workCategoryRepository.saveAndFlush(workCategory);

		// Get the workCategory
		restWorkCategoryMockMvc.perform(get(ENTITY_API_URL_ID, workCategory.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(workCategory.getId().intValue()))
				.andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME))
				.andExpect(jsonPath("$.categoryCode").value(DEFAULT_CATEGORY_CODE))
				.andExpect(jsonPath("$.activeYn").value(DEFAULT_ACTIVE_YN.booleanValue()));
		log.info("==================================================================================");
		log.info("Test - getWorkCategory Ends");
	}

	@Test(priority = 8)
	@Transactional
	void getNonExistingWorkCategory() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getNonExistingWorkCategory Start");
		// Get the workCategory
		restWorkCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
		log.info("==================================================================================");
		log.info("Test - getNonExistingWorkCategory Ends");
	}

	@Test(priority = 9)
	@Transactional
	void putNewWorkCategory() throws Exception {
		log.info("==================================================================================");
		log.info("Test - putNewWorkCategory Start");
		// Initialize the database
		workCategoryRepository.saveAndFlush(workCategory);

		int databaseSizeBeforeUpdate = workCategoryRepository.findAll().size();

		// Update the workCategory
		WorkCategory updatedWorkCategory = workCategoryRepository.findById(workCategory.getId()).get();
		// Disconnect from session so that the updates on updatedWorkCategory are not
		// directly saved in db
		em.detach(updatedWorkCategory);
		updatedWorkCategory.categoryName(UPDATED_CATEGORY_NAME).categoryCode(UPDATED_CATEGORY_CODE)
				.activeYn(UPDATED_ACTIVE_YN);
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(updatedWorkCategory);

		restWorkCategoryMockMvc.perform(put(ENTITY_API_URL_ID, workCategoryDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workCategoryDTO)))
				.andExpect(status().isOk());

		// Validate the WorkCategory in the database
		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeUpdate);
		WorkCategory testWorkCategory = workCategoryList.get(workCategoryList.size() - 1);
		assertThat(testWorkCategory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
		assertThat(testWorkCategory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
		assertThat(testWorkCategory.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);
		log.info("==================================================================================");
		log.info("Test - putNewWorkCategory Ends");
	}

	@Test(priority = 10)
	@Transactional
	void putNonExistingWorkCategory() throws Exception {
		log.info("==================================================================================");
		log.info("Test - putNonExistingWorkCategory Start");
		int databaseSizeBeforeUpdate = workCategoryRepository.findAll().size();
		workCategory.setId(count.incrementAndGet());

		// Create the WorkCategory
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restWorkCategoryMockMvc
				.perform(put(ENTITY_API_URL_ID, workCategoryDTO.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkCategory in the database
		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeUpdate);
		log.info("==================================================================================");
		log.info("Test - putNonExistingWorkCategory Ends");
	}

	@Test(priority = 11)
	@Transactional
	void putWithIdMismatchWorkCategory() throws Exception {
		log.info("==================================================================================");
		log.info("Test - putWithIdMismatchWorkCategory Start");
		int databaseSizeBeforeUpdate = workCategoryRepository.findAll().size();
		workCategory.setId(count.incrementAndGet());

		// Create the WorkCategory
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restWorkCategoryMockMvc
				.perform(put(ENTITY_API_URL_ID, count.incrementAndGet()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkCategory in the database
		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeUpdate);
		log.info("==================================================================================");
		log.info("Test - putWithIdMismatchWorkCategory Ends");
	}

	@Test(priority = 12)
	@Transactional
	void putWithMissingIdPathParamWorkCategory() throws Exception {
		log.info("==================================================================================");
		log.info("Test - putWithMissingIdPathParamWorkCategory Start");
		int databaseSizeBeforeUpdate = workCategoryRepository.findAll().size();
		workCategory.setId(count.incrementAndGet());

		// Create the WorkCategory
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restWorkCategoryMockMvc
				.perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workCategoryDTO)))
				.andExpect(status().isMethodNotAllowed());

		// Validate the WorkCategory in the database
		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeUpdate);
		log.info("==================================================================================");
		log.info("Test - putWithMissingIdPathParamWorkCategory Ends");
	}

	@Test(priority = 13)
	@Transactional
	void partialUpdateWorkCategoryWithPatch() throws Exception {
		log.info("==================================================================================");
		log.info("Test - partialUpdateWorkCategoryWithPatch Start");
		// Initialize the database
		workCategoryRepository.saveAndFlush(workCategory);

		int databaseSizeBeforeUpdate = workCategoryRepository.findAll().size();

		// Update the workCategory using partial update
		WorkCategory partialUpdatedWorkCategory = new WorkCategory();
		partialUpdatedWorkCategory.setId(workCategory.getId());

		partialUpdatedWorkCategory.activeYn(UPDATED_ACTIVE_YN);

		restWorkCategoryMockMvc
				.perform(patch(ENTITY_API_URL_ID, partialUpdatedWorkCategory.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkCategory)))
				.andExpect(status().isOk());

		// Validate the WorkCategory in the database
		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeUpdate);
		WorkCategory testWorkCategory = workCategoryList.get(workCategoryList.size() - 1);
		assertThat(testWorkCategory.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
		assertThat(testWorkCategory.getCategoryCode()).isEqualTo(DEFAULT_CATEGORY_CODE);
		assertThat(testWorkCategory.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);
		log.info("==================================================================================");
		log.info("Test - partialUpdateWorkCategoryWithPatch Ends");
	}

	@Test(priority = 14)
	@Transactional
	void fullUpdateWorkCategoryWithPatch() throws Exception {
		log.info("==================================================================================");
		log.info("Test - fullUpdateWorkCategoryWithPatch Start");
		// Initialize the database
		workCategoryRepository.saveAndFlush(workCategory);

		int databaseSizeBeforeUpdate = workCategoryRepository.findAll().size();

		// Update the workCategory using partial update
		WorkCategory partialUpdatedWorkCategory = new WorkCategory();
		partialUpdatedWorkCategory.setId(workCategory.getId());

		partialUpdatedWorkCategory.categoryName(UPDATED_CATEGORY_NAME).categoryCode(UPDATED_CATEGORY_CODE)
				.activeYn(UPDATED_ACTIVE_YN);

		restWorkCategoryMockMvc
				.perform(patch(ENTITY_API_URL_ID, partialUpdatedWorkCategory.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkCategory)))
				.andExpect(status().isOk());

		// Validate the WorkCategory in the database
		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeUpdate);
		WorkCategory testWorkCategory = workCategoryList.get(workCategoryList.size() - 1);
		assertThat(testWorkCategory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
		assertThat(testWorkCategory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
		assertThat(testWorkCategory.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);
		log.info("==================================================================================");
		log.info("Test - fullUpdateWorkCategoryWithPatch Ends");
	}

	@Test(priority = 15)
	@Transactional
	void patchNonExistingWorkCategory() throws Exception {
		log.info("==================================================================================");
		log.info("Test - patchNonExistingWorkCategory Start");
		int databaseSizeBeforeUpdate = workCategoryRepository.findAll().size();
		workCategory.setId(count.incrementAndGet());

		// Create the WorkCategory
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restWorkCategoryMockMvc
				.perform(patch(ENTITY_API_URL_ID, workCategoryDTO.getId()).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workCategoryDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkCategory in the database
		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeUpdate);
		log.info("==================================================================================");
		log.info("Test - patchNonExistingWorkCategory Ends");
	}

	@Test(priority = 16)
	@Transactional
	void patchWithIdMismatchWorkCategory() throws Exception {
		log.info("==================================================================================");
		log.info("Test - patchWithIdMismatchWorkCategory Start");
		int databaseSizeBeforeUpdate = workCategoryRepository.findAll().size();
		workCategory.setId(count.incrementAndGet());

		// Create the WorkCategory
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restWorkCategoryMockMvc
				.perform(patch(ENTITY_API_URL_ID, count.incrementAndGet()).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workCategoryDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkCategory in the database
		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeUpdate);
		log.info("==================================================================================");
		log.info("Test - patchWithIdMismatchWorkCategory Ends");
	}

	@Test(priority = 17)
	@Transactional
	void patchWithMissingIdPathParamWorkCategory() throws Exception {
		log.info("==================================================================================");
		log.info("Test - patchWithMissingIdPathParamWorkCategory Start");
		int databaseSizeBeforeUpdate = workCategoryRepository.findAll().size();
		workCategory.setId(count.incrementAndGet());

		// Create the WorkCategory
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restWorkCategoryMockMvc
				.perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workCategoryDTO)))
				.andExpect(status().isMethodNotAllowed());

		// Validate the WorkCategory in the database
		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeUpdate);
		log.info("==================================================================================");
		log.info("Test - patchWithMissingIdPathParamWorkCategory Ends");
	}

	@Test(priority = 18)
	@Transactional
	void getAllActiveWorkCategories() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getAllActiveWorkCategories Start");
		// Initialize the database
		workCategoryRepository.saveAndFlush(workCategory);

		// Get all the workCategoryList
		restWorkCategoryMockMvc.perform(get("/v1/api/work-categories-active" + "?sort=id,desc"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(workCategory.getId().intValue())))
				.andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
				.andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE)))
				.andExpect(jsonPath("$.[*].activeYn").value(hasItem(DEFAULT_ACTIVE_YN.booleanValue())));
		log.info("==================================================================================");
		log.info("Test - getAllActiveWorkCategories Ends");
	}

	@Test(priority = 19)
	@Transactional
	void getAllActiveWorkCategoriesList() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getAllActiveWorkCategoriesList Start");
		// Initialize the database
		workCategoryRepository.saveAndFlush(workCategory);

		// Get all the workCategoryList
		restWorkCategoryMockMvc.perform(get("/v1/api/active-work-categories" + "?sort=id,desc"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(workCategory.getId().intValue())))
				.andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
				.andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE)))
				.andExpect(jsonPath("$.[*].activeYn").value(hasItem(DEFAULT_ACTIVE_YN.booleanValue())));
		log.info("==================================================================================");
		log.info("Test - getAllActiveWorkCategoriesList Ends");
	}

	@Test(priority = 20)
	@Transactional
	void deleteWorkCategory() throws Exception {
		log.info("==================================================================================");
		log.info("Test - deleteWorkCategory Start");
		// Initialize the database
		workCategoryRepository.saveAndFlush(workCategory);

		int databaseSizeBeforeDelete = workCategoryRepository.findAll().size();

		// Delete the workCategory
		restWorkCategoryMockMvc
				.perform(delete(ENTITY_API_URL_ID, workCategory.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<WorkCategory> workCategoryList = workCategoryRepository.findAll();
		assertThat(workCategoryList).hasSize(databaseSizeBeforeDelete);
		log.info("==================================================================================");
		log.info("Test - deleteWorkCategory Ends");
	}

	@Test(priority = 21)
	@Transactional
	void deleteWorkCategory_InvalidId() throws Exception {
		log.info("==================================================================================");
		log.info("Test - deleteWorkCategory_InvalidId Start");
		// Initialize the database
		workCategoryRepository.saveAndFlush(workCategory);

		final Long WORKESTIMATEID = Long.MAX_VALUE;
		// Delete the workCategory

		restWorkCategoryMockMvc.perform(delete(ENTITY_API_URL_ID, WORKESTIMATEID)).andExpect(status().isBadRequest());
		log.info("==================================================================================");
		log.info("Test - deleteWorkCategory_InvalidId Ends");
	}
}
