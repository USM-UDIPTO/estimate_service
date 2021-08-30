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

import com.dxc.eproc.estimate.model.DeptEstimateType;
import com.dxc.eproc.estimate.repository.DeptEstimateTypeRepository;
import com.dxc.eproc.estimate.service.dto.DeptEstimateTypeDTO;
import com.dxc.eproc.estimate.service.mapper.DeptEstimateTypeMapper;

/**
 * Integration tests for the {@link DeptEstimateTypeResource} REST controller.
 */

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class DeptEstimateTypeResourceIT extends AbstractTestNGSpringContextTests {

	private final static Logger log = LoggerFactory.getLogger(DeptEstimateTypeResourceIT.class);

	private static final Long DEFAULT_DEPT_ID = 1L;
	private static final Long UPDATED_DEPT_ID = 2L;

	private static final Long DEFAULT_ESTIMATE_TYPE_ID = 1L;
	private static final Long UPDATED_ESTIMATE_TYPE_ID = 2L;

	private static final String DEFAULT_FLOW_TYPE = "AAAAAAAAAA";
	private static final String UPDATED_FLOW_TYPE = "BBBBBBBBBB";

	private static final Boolean DEFAULT_ACTIVE_YN = true;
	private static final Boolean UPDATED_ACTIVE_YN = true;

	private static final String ENTITY_API_URL = "/v1/api/dept-estimate-types";
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

	private static Random random = new Random();
	private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

	@Autowired
	private DeptEstimateTypeRepository deptEstimateTypeRepository;

	@Autowired
	private DeptEstimateTypeMapper deptEstimateTypeMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restDeptEstimateTypeMockMvc;

	private DeptEstimateType deptEstimateType;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static DeptEstimateType createEntity(EntityManager em) {
		DeptEstimateType deptEstimateType = new DeptEstimateType().deptId(DEFAULT_DEPT_ID)
				.estimateTypeId(DEFAULT_ESTIMATE_TYPE_ID).flowType(DEFAULT_FLOW_TYPE).activeYn(DEFAULT_ACTIVE_YN);
		return deptEstimateType;
	}

	@BeforeMethod
	public void initTest() {
		log.info("==================================================================================");
		log.info("This is executed before each Test - initTest");

		deptEstimateType = createEntity(em);
	}

	@Test
	void createDeptEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createDeptEstimateType - Start");

		int databaseSizeBeforeCreate = deptEstimateTypeRepository.findAll().size();
		// Create the DeptEstimateType
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);
		restDeptEstimateTypeMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(deptEstimateTypeDTO)))
				.andExpect(status().isCreated());

		// Validate the DeptEstimateType in the database
		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeCreate + 1);
		DeptEstimateType testDeptEstimateType = deptEstimateTypeList.get(deptEstimateTypeList.size() - 1);
		assertThat(testDeptEstimateType.getDeptId()).isEqualTo(DEFAULT_DEPT_ID);
		assertThat(testDeptEstimateType.getEstimateTypeId()).isEqualTo(DEFAULT_ESTIMATE_TYPE_ID);
		assertThat(testDeptEstimateType.getFlowType()).isEqualTo(DEFAULT_FLOW_TYPE);

		log.info("==========================================================================");
		log.info("Test - createDeptEstimateType - Start");
	}

	@Test
	void createDeptEstimateTypeWithExistingId() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createDeptEstimateTypeWithExistingId - Start");

		// Create the DeptEstimateType with an existing ID
		deptEstimateType.setId(1L);
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);

		int databaseSizeBeforeCreate = deptEstimateTypeRepository.findAll().size();

		// An entity with an existing ID cannot be created, so this API call must fail
		restDeptEstimateTypeMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(deptEstimateTypeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the DeptEstimateType in the database
		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeCreate);

		log.info("==========================================================================");
		log.info("Test - createDeptEstimateTypeWithExistingId - End");
	}

	@Test
	void checkDeptIdIsRequired() throws Exception {
		log.info("==========================================================================");
		log.info("Test - checkDeptIdIsRequired - Start");

		int databaseSizeBeforeTest = deptEstimateTypeRepository.findAll().size();
		// set the field null
		deptEstimateType.setDeptId(null);

		// Create the DeptEstimateType, which fails.
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);

		restDeptEstimateTypeMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(deptEstimateTypeDTO)))
				.andExpect(status().isBadRequest());

		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeTest);

		log.info("==========================================================================");
		log.info("Test - checkDeptIdIsRequired - End");
	}

	@Test
	void checkEstimateTypeIdIsRequired() throws Exception {
		log.info("==========================================================================");
		log.info("Test - checkEstimateTypeIdIsRequired - Start");

		int databaseSizeBeforeTest = deptEstimateTypeRepository.findAll().size();
		// set the field null
		deptEstimateType.setEstimateTypeId(null);

		// Create the DeptEstimateType, which fails.
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);

		restDeptEstimateTypeMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(deptEstimateTypeDTO)))
				.andExpect(status().isBadRequest());

		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeTest);

		log.info("==========================================================================");
		log.info("Test - checkEstimateTypeIdIsRequired - End");
	}

	@Test
	void getAllDeptEstimateTypes() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllDeptEstimateTypes - Start");

		// Initialize the database
		deptEstimateTypeRepository.saveAndFlush(deptEstimateType);

		// Get all the deptEstimateTypeList
		restDeptEstimateTypeMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(deptEstimateType.getId().intValue())))
				.andExpect(jsonPath("$.[*].deptId").value(hasItem(DEFAULT_DEPT_ID.intValue())))
				.andExpect(jsonPath("$.[*].estimateTypeId").value(hasItem(DEFAULT_ESTIMATE_TYPE_ID.intValue())))
				.andExpect(jsonPath("$.[*].flowType").value(hasItem(DEFAULT_FLOW_TYPE)))
				.andExpect(jsonPath("$.[*].activeYn").value(hasItem(DEFAULT_ACTIVE_YN.booleanValue())));

		log.info("==========================================================================");
		log.info("Test - getAllDeptEstimateTypes - End");
	}

	@Test
	void getAllActiveDeptEstimateTypes() throws Exception {
		log.info("==========================================================================");
		log.info("Test -getAllActiveDeptEstimateTypes - Start");

		// Initialize the database
		deptEstimateTypeRepository.saveAndFlush(deptEstimateType);

		// Get all the deptEstimateTypeList
		restDeptEstimateTypeMockMvc.perform(get("/v1/api/dept-estimate-types-active")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(deptEstimateType.getId().intValue())))
				.andExpect(jsonPath("$.[*].deptId").value(hasItem(DEFAULT_DEPT_ID.intValue())))
				.andExpect(jsonPath("$.[*].estimateTypeId").value(hasItem(DEFAULT_ESTIMATE_TYPE_ID.intValue())))
				.andExpect(jsonPath("$.[*].flowType").value(hasItem(DEFAULT_FLOW_TYPE)))
				.andExpect(jsonPath("$.[*].activeYn").value(hasItem(DEFAULT_ACTIVE_YN.booleanValue())));

		log.info("==========================================================================");
		log.info("Test - getAllActiveDeptEstimateTypes - End");
	}

	@Test
	void getAllActiveDeptEstimateTypesForWorkEstimate() throws Exception {
		log.info("==========================================================================");
		log.info("Test -getAllActiveDeptEstimateTypesForWorkEstimate- Start");

		// Initialize the database
		deptEstimateTypeRepository.saveAndFlush(deptEstimateType);

		// Get all the deptEstimateTypeList
		restDeptEstimateTypeMockMvc
				.perform(get("/v1/api/dept-master/{deptId}/active-estimate-types", deptEstimateType.getDeptId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(deptEstimateType.getId().intValue())))
				.andExpect(jsonPath("$.[*].deptId").value(hasItem(DEFAULT_DEPT_ID.intValue())))
				.andExpect(jsonPath("$.[*].estimateTypeId").value(hasItem(DEFAULT_ESTIMATE_TYPE_ID.intValue())))
				.andExpect(jsonPath("$.[*].flowType").value(hasItem(DEFAULT_FLOW_TYPE)))
				.andExpect(jsonPath("$.[*].activeYn").value(hasItem(DEFAULT_ACTIVE_YN.booleanValue())));

		log.info("==========================================================================");
		log.info("Test - getAllActiveDeptEstimateTypesForWorkEstimate - End");
	}

	@Test
	void getDeptEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getDeptEstimateType - Start");

		// Initialize the database
		deptEstimateTypeRepository.saveAndFlush(deptEstimateType);

		// Get the deptEstimateType
		restDeptEstimateTypeMockMvc.perform(get(ENTITY_API_URL_ID, deptEstimateType.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(deptEstimateType.getId().intValue()))
				.andExpect(jsonPath("$.deptId").value(DEFAULT_DEPT_ID.intValue()))
				.andExpect(jsonPath("$.estimateTypeId").value(DEFAULT_ESTIMATE_TYPE_ID.intValue()))
				.andExpect(jsonPath("$.flowType").value(DEFAULT_FLOW_TYPE))
				.andExpect(jsonPath("$.activeYn").value(DEFAULT_ACTIVE_YN.booleanValue()));

		log.info("==========================================================================");
		log.info("Test - getDeptEstimateType - End");
	}

	@Test
	void getNonExistingDeptEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getNonExistingDeptEstimateType - Start");

		// Get the deptEstimateType
		restDeptEstimateTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());

		log.info("==========================================================================");
		log.info("Test - getNonExistingDeptEstimateType- End");
	}

	@Test
	void putNewDeptEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putNewDeptEstimateType - Start");

		// Initialize the database
		deptEstimateTypeRepository.saveAndFlush(deptEstimateType);

		int databaseSizeBeforeUpdate = deptEstimateTypeRepository.findAll().size();

		// Update the deptEstimateType
		DeptEstimateType updatedDeptEstimateType = deptEstimateTypeRepository.findById(deptEstimateType.getId()).get();
		// Disconnect from session so that the updates on updatedDeptEstimateType are
		// not directly saved in db
		em.detach(updatedDeptEstimateType);
		updatedDeptEstimateType.deptId(UPDATED_DEPT_ID).estimateTypeId(UPDATED_ESTIMATE_TYPE_ID)
				.flowType(UPDATED_FLOW_TYPE).activeYn(UPDATED_ACTIVE_YN);
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(updatedDeptEstimateType);

		restDeptEstimateTypeMockMvc
				.perform(put(ENTITY_API_URL_ID, deptEstimateTypeDTO.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(deptEstimateTypeDTO)))
				.andExpect(status().isOk());

		// Validate the DeptEstimateType in the database
		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeUpdate);
		DeptEstimateType testDeptEstimateType = deptEstimateTypeList.get(deptEstimateTypeList.size() - 1);
		assertThat(testDeptEstimateType.getDeptId()).isEqualTo(UPDATED_DEPT_ID);
		assertThat(testDeptEstimateType.getEstimateTypeId()).isEqualTo(UPDATED_ESTIMATE_TYPE_ID);
		assertThat(testDeptEstimateType.getFlowType()).isEqualTo(UPDATED_FLOW_TYPE);
		assertThat(testDeptEstimateType.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);

		log.info("==========================================================================");
		log.info("Test - putNewDeptEstimateType - End");
	}

	@Test
	void putNonExistingDeptEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putNonExistingDeptEstimateType - Start");

		int databaseSizeBeforeUpdate = deptEstimateTypeRepository.findAll().size();
		deptEstimateType.setId(count.incrementAndGet());

		// Create the DeptEstimateType
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restDeptEstimateTypeMockMvc
				.perform(put(ENTITY_API_URL_ID, deptEstimateTypeDTO.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(deptEstimateTypeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the DeptEstimateType in the database
		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test - putNonExistingDeptEstimateType - End");
	}

	@Test
	void putWithIdMismatchDeptEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putWithIdMismatchDeptEstimateType - Start");

		int databaseSizeBeforeUpdate = deptEstimateTypeRepository.findAll().size();
		deptEstimateType.setId(count.incrementAndGet());

		// Create the DeptEstimateType
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restDeptEstimateTypeMockMvc
				.perform(put(ENTITY_API_URL_ID, count.incrementAndGet()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(deptEstimateTypeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the DeptEstimateType in the database
		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test - putWithIdMismatchDeptEstimateType - End");
	}

	@Test
	void putWithMissingIdPathParamDeptEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putWithMissingIdPathParamDeptEstimateType - Start");

		int databaseSizeBeforeUpdate = deptEstimateTypeRepository.findAll().size();
		deptEstimateType.setId(count.incrementAndGet());

		// Create the DeptEstimateType
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restDeptEstimateTypeMockMvc
				.perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(deptEstimateTypeDTO)))
				.andExpect(status().isMethodNotAllowed());

		// Validate the DeptEstimateType in the database
		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test -putWithMissingIdPathParamDeptEstimateType - End");
	}

	@Test
	void partialUpdateDeptEstimateTypeWithPatch() throws Exception {
		log.info("==========================================================================");
		log.info("Test - partialUpdateDeptEstimateTypeWithPatch - Start");

		// Initialize the database
		deptEstimateTypeRepository.saveAndFlush(deptEstimateType);

		int databaseSizeBeforeUpdate = deptEstimateTypeRepository.findAll().size();

		// Update the deptEstimateType using partial update
		DeptEstimateType partialUpdatedDeptEstimateType = new DeptEstimateType();
		partialUpdatedDeptEstimateType.setId(deptEstimateType.getId());

		partialUpdatedDeptEstimateType.flowType(UPDATED_FLOW_TYPE);

		restDeptEstimateTypeMockMvc
				.perform(patch(ENTITY_API_URL_ID, partialUpdatedDeptEstimateType.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeptEstimateType)))
				.andExpect(status().isOk());

		// Validate the DeptEstimateType in the database
		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeUpdate);
		DeptEstimateType testDeptEstimateType = deptEstimateTypeList.get(deptEstimateTypeList.size() - 1);
		assertThat(testDeptEstimateType.getDeptId()).isEqualTo(DEFAULT_DEPT_ID);
		assertThat(testDeptEstimateType.getEstimateTypeId()).isEqualTo(DEFAULT_ESTIMATE_TYPE_ID);
		assertThat(testDeptEstimateType.getFlowType()).isEqualTo(UPDATED_FLOW_TYPE);
		assertThat(testDeptEstimateType.getActiveYn()).isEqualTo(DEFAULT_ACTIVE_YN);

		log.info("==========================================================================");
		log.info("Test - partialUpdateDeptEstimateTypeWithPatch - End");
	}

	@Test
	void fullUpdateDeptEstimateTypeWithPatch() throws Exception {
		log.info("==========================================================================");
		log.info("Test -fullUpdateDeptEstimateTypeWithPatch- Start");

		// Initialize the database
		deptEstimateTypeRepository.saveAndFlush(deptEstimateType);

		int databaseSizeBeforeUpdate = deptEstimateTypeRepository.findAll().size();

		// Update the deptEstimateType using partial update
		DeptEstimateType partialUpdatedDeptEstimateType = new DeptEstimateType();
		partialUpdatedDeptEstimateType.setId(deptEstimateType.getId());

		partialUpdatedDeptEstimateType.deptId(UPDATED_DEPT_ID).estimateTypeId(UPDATED_ESTIMATE_TYPE_ID)
				.flowType(UPDATED_FLOW_TYPE).activeYn(UPDATED_ACTIVE_YN);

		restDeptEstimateTypeMockMvc
				.perform(patch(ENTITY_API_URL_ID, partialUpdatedDeptEstimateType.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeptEstimateType)))
				.andExpect(status().isOk());

		// Validate the DeptEstimateType in the database
		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeUpdate);
		DeptEstimateType testDeptEstimateType = deptEstimateTypeList.get(deptEstimateTypeList.size() - 1);
		assertThat(testDeptEstimateType.getDeptId()).isEqualTo(UPDATED_DEPT_ID);
		assertThat(testDeptEstimateType.getEstimateTypeId()).isEqualTo(UPDATED_ESTIMATE_TYPE_ID);
		assertThat(testDeptEstimateType.getFlowType()).isEqualTo(UPDATED_FLOW_TYPE);
		assertThat(testDeptEstimateType.getActiveYn()).isEqualTo(UPDATED_ACTIVE_YN);

		log.info("==========================================================================");
		log.info("Test - fullUpdateDeptEstimateTypeWithPatch - End");
	}

	@Test
	void patchNonExistingDeptEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - patchNonExistingDeptEstimateType - Start");

		int databaseSizeBeforeUpdate = deptEstimateTypeRepository.findAll().size();
		deptEstimateType.setId(count.incrementAndGet());

		// Create the DeptEstimateType
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restDeptEstimateTypeMockMvc
				.perform(patch(ENTITY_API_URL_ID, deptEstimateTypeDTO.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(deptEstimateTypeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the DeptEstimateType in the database
		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test - patchNonExistingDeptEstimateType - End");
	}

	@Test
	void patchWithIdMismatchDeptEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - patchWithIdMismatchDeptEstimateType - Start");

		int databaseSizeBeforeUpdate = deptEstimateTypeRepository.findAll().size();
		deptEstimateType.setId(count.incrementAndGet());

		// Create the DeptEstimateType
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restDeptEstimateTypeMockMvc
				.perform(patch(ENTITY_API_URL_ID, count.incrementAndGet()).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(deptEstimateTypeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the DeptEstimateType in the database
		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test -patchWithIdMismatchDeptEstimateType- End");
	}

	@Test
	void patchWithMissingIdPathParamDeptEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - patchWithMissingIdPathParamDeptEstimateType - Start");

		int databaseSizeBeforeUpdate = deptEstimateTypeRepository.findAll().size();
		deptEstimateType.setId(count.incrementAndGet());

		// Create the DeptEstimateType
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restDeptEstimateTypeMockMvc
				.perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(deptEstimateTypeDTO)))
				.andExpect(status().isMethodNotAllowed());

		// Validate the DeptEstimateType in the database
		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test - patchWithMissingIdPathParamDeptEstimateType - End");
	}

	@Test
	void deleteNonExistingDeptEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteNonExistingDeptEstimateType - Start");

		int databaseSizeBeforeUpdate = deptEstimateTypeRepository.findAll().size();
		deptEstimateType.setId(count.incrementAndGet());

		// Create the EstimateType
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restDeptEstimateTypeMockMvc
				.perform(delete(ENTITY_API_URL_ID, deptEstimateTypeDTO.getId()).accept(MediaType.APPLICATION_JSON))

				.andExpect(status().isBadRequest());

		// Validate the EstimateType in the database
		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeUpdate);

		log.info("==========================================================================");
		log.info("Test - deleteNonExistingDeptEstimateType - End");
	}

	@Test
	void deleteDeptEstimateType() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteDeptEstimateType - Start");

		// Initialize the database
		deptEstimateTypeRepository.saveAndFlush(deptEstimateType);

		int databaseSizeBeforeDelete = deptEstimateTypeRepository.findAll().size();

		// Delete the deptEstimateType
		restDeptEstimateTypeMockMvc
				.perform(delete(ENTITY_API_URL_ID, deptEstimateType.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<DeptEstimateType> deptEstimateTypeList = deptEstimateTypeRepository.findAll();
		assertThat(deptEstimateTypeList).hasSize(databaseSizeBeforeDelete);

		log.info("==========================================================================");
		log.info("Test - deleteDeptEstimateType - End");
	}
}
