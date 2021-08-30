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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.model.WorkLocation;
import com.dxc.eproc.estimate.repository.SubEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkLocationRepository;
import com.dxc.eproc.estimate.service.dto.WorkLocationDTO;
import com.dxc.eproc.estimate.service.mapper.WorkLocationMapper;

/**
 * Integration tests for the {@link WorkLocationResource} REST controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class WorkLocationResourceIT extends AbstractTestNGSpringContextTests {

	private final static Logger log = LoggerFactory.getLogger(WorkLocationResourceIT.class);

	private static final Long DEFAULT_SUB_ESTIMATE_ID = 1L;
	private static final Long UPDATED_SUB_ESTIMATE_ID = 2L;

	private static final Long DEFAULT_DISTRICT_ID = 1L;
	private static final Long UPDATED_DISTRICT_ID = 2L;

	private static final Long DEFAULT_TALUQ_ID = 1L;
	private static final Long UPDATED_TALUQ_ID = 2L;

	private static final Long DEFAULT_LOKSABHA_CONT_ID = 1L;
	private static final Long UPDATED_LOKSABHA_CONT_ID = 2L;

	private static final Long DEFAULT_ASSEMBLY_CONT_ID = 1L;
	private static final Long UPDATED_ASSEMBLY_CONT_ID = 2L;

	private static final String DEFAULT_LOCATION_DESCRIPTION = "AAAAAAAAAA";
	private static final String UPDATED_LOCATION_DESCRIPTION = "BBBBBBBBBB";

	private static final Integer DEFAULT_LATITUDE_DEGREES = 1;
	private static final Integer UPDATED_LATITUDE_DEGREES = 2;

	private static final Integer DEFAULT_LATITUDE_MINUTES = 1;
	private static final Integer UPDATED_LATITUDE_MINUTES = 2;

	private static final Integer DEFAULT_LATITUDE_SECONDS = 1;
	private static final Integer UPDATED_LATITUDE_SECONDS = 2;

	private static final Integer DEFAULT_LONGITUDE_DEGREES = 1;
	private static final Integer UPDATED_LONGITUDE_DEGREES = 2;

	private static final Integer DEFAULT_LONGITUDE_MINUTES = 1;
	private static final Integer UPDATED_LONGITUDE_MINUTES = 2;

	private static final Integer DEFAULT_LONGITUDE_SECONDS = 1;
	private static final Integer UPDATED_LONGITUDE_SECONDS = 2;

	private static final String ENTITY_API_URL = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-location";
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

	@Autowired
	private WorkLocationRepository workLocationRepository;

	@Autowired
	private WorkEstimateRepository workEstimateRepository;

	@Autowired
	private SubEstimateRepository subEstimateRepository;

	@Autowired
	private WorkLocationMapper workLocationMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restWorkLocationMockMvc;

	private WorkEstimate workEstimate;

	private SubEstimate subEstimate;

	private WorkLocation workLocation;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static WorkLocation createEntity(EntityManager em) {
		WorkLocation workLocation = new WorkLocation().subEstimateId(DEFAULT_SUB_ESTIMATE_ID)
				.districtId(DEFAULT_DISTRICT_ID).taluqId(DEFAULT_TALUQ_ID).loksabhaContId(DEFAULT_LOKSABHA_CONT_ID)
				.assemblyContId(DEFAULT_ASSEMBLY_CONT_ID).locationDescription(DEFAULT_LOCATION_DESCRIPTION)
				.latitudeDegrees(DEFAULT_LATITUDE_DEGREES).latitudeMinutes(DEFAULT_LATITUDE_MINUTES)
				.latitudeSeconds(DEFAULT_LATITUDE_SECONDS).longitudeDegrees(DEFAULT_LONGITUDE_DEGREES)
				.longitudeMinutes(DEFAULT_LONGITUDE_MINUTES).longitudeSeconds(DEFAULT_LONGITUDE_SECONDS);
		return workLocation;
	}

	private WorkEstimate createWorkEstimateEntity() {
		WorkEstimate workEstimate = new WorkEstimate().workEstimateNumber("1113").status(WorkEstimateStatus.DRAFT)
				.deptId(1L).locationId(1L).fileNumber("1236").name("Road Repair")
				.description("Road Repair work estimate.").estimateTypeId(1L).workTypeId(1L).workCategoryId(1L)
				.workCategoryAttribute(10L).workCategoryAttributeValue(BigDecimal.valueOf(20))
				.adminSanctionAccordedYn(false).techSanctionAccordedYn(false).approvedBudgetYn(false);

		return workEstimate;
	}

	private SubEstimate createSubEstimateEntity() {
		SubEstimate subEstimate = new SubEstimate().workEstimateId(1L).sorWorCategoryId(1L)
				.subEstimateName("Repair Work").estimateTotal(BigDecimal.valueOf(1000000.0000)).areaWeightageCircle(1L)
				.areaWeightageDescription("Area weightage Description.").completedYn(false);

		return subEstimate;
	}

	@BeforeClass
	public void setUp() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - setUp");

		workEstimate = createWorkEstimateEntity();
		subEstimate = createSubEstimateEntity();

		// Initializing work estimate.
		workEstimateRepository.saveAndFlush(workEstimate);

		// Initializing sub estimate.
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);
	}

	@BeforeMethod
	public void initTest() {
		log.info("==================================================================================");
		log.info("This is executed before each Test - initTest");

		workLocation = createEntity(em);
	}

	@Test
	@Transactional
	void createWorkLocation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkLocation - Start");

		int databaseSizeBeforeCreate = workLocationRepository.findAll().size();
		// Create the WorkLocation
		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);

		restWorkLocationMockMvc.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workLocationDTO)))
				.andExpect(status().isCreated());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();

		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate + 1);
		WorkLocation testWorkLocation = workLocationList.get(workLocationList.size() - 1);
		assertThat(testWorkLocation.getSubEstimateId()).isEqualTo(subEstimate.getId());
		assertThat(testWorkLocation.getDistrictId()).isEqualTo(DEFAULT_DISTRICT_ID);
		assertThat(testWorkLocation.getTaluqId()).isEqualTo(DEFAULT_TALUQ_ID);
		assertThat(testWorkLocation.getLoksabhaContId()).isEqualTo(DEFAULT_LOKSABHA_CONT_ID);
		assertThat(testWorkLocation.getAssemblyContId()).isEqualTo(DEFAULT_ASSEMBLY_CONT_ID);
		assertThat(testWorkLocation.getLocationDescription()).isEqualTo(DEFAULT_LOCATION_DESCRIPTION);
		assertThat(testWorkLocation.getLatitudeDegrees()).isEqualTo(DEFAULT_LATITUDE_DEGREES);
		assertThat(testWorkLocation.getLatitudeMinutes()).isEqualTo(DEFAULT_LATITUDE_MINUTES);
		assertThat(testWorkLocation.getLatitudeSeconds()).isEqualTo(DEFAULT_LATITUDE_SECONDS);
		assertThat(testWorkLocation.getLongitudeDegrees()).isEqualTo(DEFAULT_LONGITUDE_DEGREES);
		assertThat(testWorkLocation.getLongitudeMinutes()).isEqualTo(DEFAULT_LONGITUDE_MINUTES);
		assertThat(testWorkLocation.getLongitudeSeconds()).isEqualTo(DEFAULT_LONGITUDE_SECONDS);

		log.info("Test - createWorkLocation - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void postWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postWorkEstimateNotFound - Start");

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);

		int databaseSizeBeforeCreate = workLocationRepository.findAll().size();

		restWorkLocationMockMvc.perform(post(ENTITY_API_URL, WORKESTIMATEID, subEstimate.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workLocationDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();

		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void postSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postSubEstimateNotFound - Start");

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);
		List<WorkLocationDTO> workLocationDTOList = new ArrayList<>();
		workLocationDTOList.add(workLocationDTO);

		int databaseSizeBeforeCreate = workLocationRepository.findAll().size();

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail

		restWorkLocationMockMvc.perform(post(ENTITY_API_URL, workEstimate.getId(), SUBESTIMATEID)
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workLocation)))
				.andExpect(status().isNotFound());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void postInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postInvalidWorkEstimateStatus - Start");

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(workEstimate);

		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);
		/*
		 * List<WorkLocationDTO> workLocationDTOList = new ArrayList<>();
		 * workLocationDTOList.add(workLocationDTO);
		 */

		int databaseSizeBeforeCreate = workLocationRepository.findAll().size();

		restWorkLocationMockMvc.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workLocationDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - postInvalidWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void postNoInputsPassed() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postNoInputsPassed - Start");

		List<WorkLocationDTO> workLocationDTOList = new ArrayList<>();

		int databaseSizeBeforeCreate = workLocationRepository.findAll().size();

		restWorkLocationMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workLocationDTOList)))
				.andExpect(status().isBadRequest());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postNoInputsPassed - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void postValidateWorkLocation() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postValidateWorkLocation - Start");

		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);
		// Setting invalid inputs
		workLocationDTO.setAssemblyContId(null);
		workLocationDTO.setDistrictId(null);

		workLocationDTO.setLoksabhaContId(null);

		List<WorkLocationDTO> workLocationDTOList = new ArrayList<>();
		workLocationDTOList.add(workLocationDTO);

		int databaseSizeBeforeCreate = workLocationRepository.findAll().size();

		restWorkLocationMockMvc
				.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workLocationDTOList)))
				.andExpect(status().isBadRequest());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postValidateWorkLocation - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllWorkLocations() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkLocations - Start");

		// Initialize the database
		workLocation.setSubEstimateId(subEstimate.getId());
		workLocationRepository.saveAndFlush(workLocation);

		// Get all the workLocationList
		restWorkLocationMockMvc
				.perform(get(ENTITY_API_URL + "?sort=id,desc", workEstimate.getId(), subEstimate.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(workLocation.getId().intValue())))
				.andExpect(jsonPath("$.[*].subEstimateId").value(hasItem(subEstimate.getId().intValue())))
				.andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID.intValue())))
				.andExpect(jsonPath("$.[*].taluqId").value(hasItem(DEFAULT_TALUQ_ID.intValue())))
				.andExpect(jsonPath("$.[*].loksabhaContId").value(hasItem(DEFAULT_LOKSABHA_CONT_ID.intValue())))
				.andExpect(jsonPath("$.[*].assemblyContId").value(hasItem(DEFAULT_ASSEMBLY_CONT_ID.intValue())))
				.andExpect(jsonPath("$.[*].locationDescription").value(hasItem(DEFAULT_LOCATION_DESCRIPTION)))
				.andExpect(jsonPath("$.[*].latitudeDegrees").value(hasItem(DEFAULT_LATITUDE_DEGREES)))
				.andExpect(jsonPath("$.[*].latitudeMinutes").value(hasItem(DEFAULT_LATITUDE_MINUTES)))
				.andExpect(jsonPath("$.[*].latitudeSeconds").value(hasItem(DEFAULT_LATITUDE_SECONDS)))
				.andExpect(jsonPath("$.[*].longitudeDegrees").value(hasItem(DEFAULT_LONGITUDE_DEGREES)))
				.andExpect(jsonPath("$.[*].longitudeMinutes").value(hasItem(DEFAULT_LONGITUDE_MINUTES)))
				.andExpect(jsonPath("$.[*].longitudeSeconds").value(hasItem(DEFAULT_LONGITUDE_SECONDS)));

		log.info("Test - getAllWorkLocations - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkEstimateNotFound - Start");

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkLocationMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc", WORKESTIMATEID, subEstimate.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - getAllWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getAllSubEstimateNotFound - Start");

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkLocationMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc", workEstimate.getId(), SUBESTIMATEID))
				.andExpect(status().isNotFound());

		log.info("Test - getAllSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllWorkLocationsNoRecordsFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkLocationsNoRecordsFound - Start");

		workLocationRepository.deleteAll();

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkLocationMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc", workEstimate.getId(), subEstimate.getId())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

		log.info("Test - getAllWorkLocationsNoRecordsFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getWorkLocation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkLocation - Start");

		// Initialize the database
		workLocation.setSubEstimateId(subEstimate.getId());
		workLocationRepository.saveAndFlush(workLocation);

		// Get the workLocation
		restWorkLocationMockMvc
				.perform(get(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workLocation.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(workLocation.getId().intValue()))
				.andExpect(jsonPath("$.subEstimateId").value(subEstimate.getId().intValue()))
				.andExpect(jsonPath("$.districtId").value(DEFAULT_DISTRICT_ID.intValue()))
				.andExpect(jsonPath("$.taluqId").value(DEFAULT_TALUQ_ID.intValue()))
				.andExpect(jsonPath("$.loksabhaContId").value(DEFAULT_LOKSABHA_CONT_ID.intValue()))
				.andExpect(jsonPath("$.assemblyContId").value(DEFAULT_ASSEMBLY_CONT_ID.intValue()))
				.andExpect(jsonPath("$.locationDescription").value(DEFAULT_LOCATION_DESCRIPTION))
				.andExpect(jsonPath("$.latitudeDegrees").value(DEFAULT_LATITUDE_DEGREES))
				.andExpect(jsonPath("$.latitudeMinutes").value(DEFAULT_LATITUDE_MINUTES))
				.andExpect(jsonPath("$.latitudeSeconds").value(DEFAULT_LATITUDE_SECONDS))
				.andExpect(jsonPath("$.longitudeDegrees").value(DEFAULT_LONGITUDE_DEGREES))
				.andExpect(jsonPath("$.longitudeMinutes").value(DEFAULT_LONGITUDE_MINUTES))
				.andExpect(jsonPath("$.longitudeSeconds").value(DEFAULT_LONGITUDE_SECONDS));

		log.info("Test - getWorkLocation - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkEstimateNotFound - Start");

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkLocationMockMvc
				.perform(get(ENTITY_API_URL_ID, WORKESTIMATEID, subEstimate.getId(), workLocation.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - getWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getSubEstimateNotFound - Start");

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkLocationMockMvc
				.perform(get(ENTITY_API_URL_ID, workEstimate.getId(), SUBESTIMATEID, workLocation.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - getSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getWorkLocationNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkLocationNotFound - Start");

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		// setting wrong id here
		final Long ID = Long.MAX_VALUE;

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkLocationMockMvc.perform(get(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), ID))
				.andExpect(status().isNotFound());

		log.info("Test - getWorkLocationNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putNewWorkLocation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putNewWorkLocation - Start");

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		int databaseSizeBeforeUpdate = workLocationRepository.findAll().size();

		// Update the workLocation
		WorkLocation updatedWorkLocation = workLocationRepository.findById(workLocation.getId()).get();
		// Disconnect from session so that the updates on updatedWorkLocation are not
		// directly saved in db
		em.detach(updatedWorkLocation);
		updatedWorkLocation.subEstimateId(DEFAULT_SUB_ESTIMATE_ID).districtId(UPDATED_DISTRICT_ID)
				.taluqId(UPDATED_TALUQ_ID).loksabhaContId(UPDATED_LOKSABHA_CONT_ID)
				.assemblyContId(UPDATED_ASSEMBLY_CONT_ID).locationDescription(UPDATED_LOCATION_DESCRIPTION)
				.latitudeDegrees(UPDATED_LATITUDE_DEGREES).latitudeMinutes(UPDATED_LATITUDE_MINUTES)
				.latitudeSeconds(UPDATED_LATITUDE_SECONDS).longitudeDegrees(UPDATED_LONGITUDE_DEGREES)
				.longitudeMinutes(UPDATED_LONGITUDE_MINUTES).longitudeSeconds(UPDATED_LONGITUDE_SECONDS);
		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(updatedWorkLocation);

		restWorkLocationMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workLocationDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workLocationDTO)))
				.andExpect(status().isOk());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeUpdate);
		WorkLocation testWorkLocation = workLocationList.get(workLocationList.size() - 1);

		assertThat(testWorkLocation.getSubEstimateId()).isEqualTo(DEFAULT_SUB_ESTIMATE_ID);
		assertThat(testWorkLocation.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
		assertThat(testWorkLocation.getTaluqId()).isEqualTo(UPDATED_TALUQ_ID);
		assertThat(testWorkLocation.getLoksabhaContId()).isEqualTo(UPDATED_LOKSABHA_CONT_ID);
		assertThat(testWorkLocation.getAssemblyContId()).isEqualTo(UPDATED_ASSEMBLY_CONT_ID);
		assertThat(testWorkLocation.getLocationDescription()).isEqualTo(UPDATED_LOCATION_DESCRIPTION);
		assertThat(testWorkLocation.getLatitudeDegrees()).isEqualTo(UPDATED_LATITUDE_DEGREES);
		assertThat(testWorkLocation.getLatitudeMinutes()).isEqualTo(UPDATED_LATITUDE_MINUTES);
		assertThat(testWorkLocation.getLatitudeSeconds()).isEqualTo(UPDATED_LATITUDE_SECONDS);
		assertThat(testWorkLocation.getLongitudeDegrees()).isEqualTo(UPDATED_LONGITUDE_DEGREES);
		assertThat(testWorkLocation.getLongitudeMinutes()).isEqualTo(UPDATED_LONGITUDE_MINUTES);
		assertThat(testWorkLocation.getLongitudeSeconds()).isEqualTo(UPDATED_LONGITUDE_SECONDS);

		log.info("Test - putNewWorkLocation - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putWorkEstimateNotFound - Start");

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);

		int databaseSizeBeforeCreate = workLocationRepository.findAll().size();

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkLocationMockMvc
				.perform(put(ENTITY_API_URL_ID, WORKESTIMATEID, subEstimate.getId(), workLocationDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workLocationDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putSubEstimateNotFound - Start");

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);

		int databaseSizeBeforeCreate = workLocationRepository.findAll().size();

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkLocationMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimate.getId(), SUBESTIMATEID, workLocationDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workLocationDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - putSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putInvalidWorkEstimateStatus - Start");

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(workEstimate);

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);

		int databaseSizeBeforeCreate = workLocationRepository.findAll().size();

		restWorkLocationMockMvc
				.perform(put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workLocationDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(workLocationDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - putInvalidWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void partialUpdateWorkLocationWithPatch() throws Exception {
		log.info("==========================================================================");
		log.info("Test - partialUpdateWorkLocationWithPatch - Start");

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		int databaseSizeBeforeUpdate = workLocationRepository.findAll().size();

		// Update the workLocation using partial update
		WorkLocation partialUpdatedWorkLocation = new WorkLocation();
		partialUpdatedWorkLocation.setId(workLocation.getId());

		partialUpdatedWorkLocation.subEstimateId(UPDATED_SUB_ESTIMATE_ID).loksabhaContId(UPDATED_LOKSABHA_CONT_ID)
				.assemblyContId(UPDATED_ASSEMBLY_CONT_ID).locationDescription(UPDATED_LOCATION_DESCRIPTION);

		restWorkLocationMockMvc
				.perform(patch(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(),
						partialUpdatedWorkLocation.getId()).contentType("application/merge-patch+json")
								.content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkLocation)))
				.andExpect(status().isOk());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeUpdate);
		WorkLocation testWorkLocation = workLocationList.get(workLocationList.size() - 1);
		assertThat(testWorkLocation.getSubEstimateId()).isEqualTo(UPDATED_SUB_ESTIMATE_ID);
		assertThat(testWorkLocation.getDistrictId()).isEqualTo(DEFAULT_DISTRICT_ID);
		assertThat(testWorkLocation.getTaluqId()).isEqualTo(DEFAULT_TALUQ_ID);
		assertThat(testWorkLocation.getLoksabhaContId()).isEqualTo(UPDATED_LOKSABHA_CONT_ID);
		assertThat(testWorkLocation.getAssemblyContId()).isEqualTo(UPDATED_ASSEMBLY_CONT_ID);
		assertThat(testWorkLocation.getLocationDescription()).isEqualTo(UPDATED_LOCATION_DESCRIPTION);
		assertThat(testWorkLocation.getLatitudeDegrees()).isEqualTo(DEFAULT_LATITUDE_DEGREES);
		assertThat(testWorkLocation.getLatitudeMinutes()).isEqualTo(DEFAULT_LATITUDE_MINUTES);
		assertThat(testWorkLocation.getLatitudeSeconds()).isEqualTo(DEFAULT_LATITUDE_SECONDS);
		assertThat(testWorkLocation.getLongitudeDegrees()).isEqualTo(DEFAULT_LONGITUDE_DEGREES);
		assertThat(testWorkLocation.getLongitudeMinutes()).isEqualTo(DEFAULT_LONGITUDE_MINUTES);
		assertThat(testWorkLocation.getLongitudeSeconds()).isEqualTo(DEFAULT_LONGITUDE_SECONDS);

		log.info("Test - partialUpdateWorkLocationWithPatch - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void fullUpdateWorkLocationWithPatch() throws Exception {
		log.info("==========================================================================");
		log.info("Test - fullUpdateWorkLocationWithPatch - Start");

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		int databaseSizeBeforeUpdate = workLocationRepository.findAll().size();

		// Update the workLocation using partial update
		WorkLocation partialUpdatedWorkLocation = new WorkLocation();
		partialUpdatedWorkLocation.setId(workLocation.getId());

		partialUpdatedWorkLocation.subEstimateId(UPDATED_SUB_ESTIMATE_ID).districtId(UPDATED_DISTRICT_ID)
				.taluqId(UPDATED_TALUQ_ID).loksabhaContId(UPDATED_LOKSABHA_CONT_ID)
				.assemblyContId(UPDATED_ASSEMBLY_CONT_ID).locationDescription(UPDATED_LOCATION_DESCRIPTION)
				.latitudeDegrees(UPDATED_LATITUDE_DEGREES).latitudeMinutes(UPDATED_LATITUDE_MINUTES)
				.latitudeSeconds(UPDATED_LATITUDE_SECONDS).longitudeDegrees(UPDATED_LONGITUDE_DEGREES)
				.longitudeMinutes(UPDATED_LONGITUDE_MINUTES).longitudeSeconds(UPDATED_LONGITUDE_SECONDS);

		restWorkLocationMockMvc
				.perform(patch(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(),
						partialUpdatedWorkLocation.getId()).contentType("application/merge-patch+json")
								.content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkLocation)))
				.andExpect(status().isOk());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeUpdate);
		WorkLocation testWorkLocation = workLocationList.get(workLocationList.size() - 1);
		assertThat(testWorkLocation.getSubEstimateId()).isEqualTo(UPDATED_SUB_ESTIMATE_ID);
		assertThat(testWorkLocation.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
		assertThat(testWorkLocation.getTaluqId()).isEqualTo(UPDATED_TALUQ_ID);
		assertThat(testWorkLocation.getLoksabhaContId()).isEqualTo(UPDATED_LOKSABHA_CONT_ID);
		assertThat(testWorkLocation.getAssemblyContId()).isEqualTo(UPDATED_ASSEMBLY_CONT_ID);
		assertThat(testWorkLocation.getLocationDescription()).isEqualTo(UPDATED_LOCATION_DESCRIPTION);
		assertThat(testWorkLocation.getLatitudeDegrees()).isEqualTo(UPDATED_LATITUDE_DEGREES);
		assertThat(testWorkLocation.getLatitudeMinutes()).isEqualTo(UPDATED_LATITUDE_MINUTES);
		assertThat(testWorkLocation.getLatitudeSeconds()).isEqualTo(UPDATED_LATITUDE_SECONDS);
		assertThat(testWorkLocation.getLongitudeDegrees()).isEqualTo(UPDATED_LONGITUDE_DEGREES);
		assertThat(testWorkLocation.getLongitudeMinutes()).isEqualTo(UPDATED_LONGITUDE_MINUTES);
		assertThat(testWorkLocation.getLongitudeSeconds()).isEqualTo(UPDATED_LONGITUDE_SECONDS);

		log.info("Test - fullUpdateWorkLocationWithPatch - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void patchWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - patchWorkEstimateNotFound - Start");

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);

		int databaseSizeBeforeCreate = workLocationRepository.findAll().size();

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkLocationMockMvc
				.perform(patch(ENTITY_API_URL_ID, WORKESTIMATEID, subEstimate.getId(), workLocationDTO.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workLocationDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - patchWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void patchSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - patchSubEstimateNotFound - Start");

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);

		int databaseSizeBeforeCreate = workLocationRepository.findAll().size();

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkLocationMockMvc
				.perform(patch(ENTITY_API_URL_ID, workEstimate.getId(), SUBESTIMATEID, workLocationDTO.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workLocationDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - patchSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void patchInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - patchInvalidWorkEstimateStatus - Start");

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(workEstimate);

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);

		int databaseSizeBeforeCreate = workLocationRepository.findAll().size();

		restWorkLocationMockMvc
				.perform(patch(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workLocationDTO.getId())
						.contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(workLocationDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - patchInvalidWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void deleteWorkLocation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateItem - Start");

		// Initialize the database
		workLocation.setSubEstimateId(subEstimate.getId());
		workLocationRepository.saveAndFlush(workLocation);

		int databaseSizeBeforeDelete = workLocationRepository.findAll().size();

		// Delete the workLocation
		restWorkLocationMockMvc
				.perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workLocation.getId())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeDelete - 1);

		log.info("Test - deleteWorkEstimateItem - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void deleteWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateNotFound - Start");

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restWorkLocationMockMvc
				.perform(delete(ENTITY_API_URL_ID, WORKESTIMATEID, subEstimate.getId(), workLocation.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - deleteWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void deleteSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - deleteSubEstimateNotFound - Start");

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkLocationMockMvc
				.perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), SUBESTIMATEID, workLocation.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - deleteSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void deleteWorkLocationNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkLocationNotFound - Start");

		// setting wrong id here
		final Long ID = Long.MAX_VALUE;

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restWorkLocationMockMvc.perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), ID))
				.andExpect(status().isNotFound());

		log.info("Test - deleteWorkEstimateItemNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	public void deleteInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - deleteInvalidWorkEstimateStatus - Start");

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(workEstimate);

		// Initialize the database
		workLocationRepository.saveAndFlush(workLocation);

		WorkLocationDTO workLocationDTO = workLocationMapper.toDto(workLocation);

		int databaseSizeBeforeCreate = workLocationRepository.findAll().size();

		restWorkLocationMockMvc
				.perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workLocationDTO.getId()))
				.andExpect(status().isBadRequest());

		// Validate the WorkLocation in the database
		List<WorkLocation> workLocationList = workLocationRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - deleteInvalidWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

}
