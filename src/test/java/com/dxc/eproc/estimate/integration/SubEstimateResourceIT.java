package com.dxc.eproc.estimate.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.IntegrationTest;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.model.WorkEstimateItem;
import com.dxc.eproc.estimate.model.WorkEstimateItemLBD;
import com.dxc.eproc.estimate.model.WorkLocation;
import com.dxc.eproc.estimate.repository.SubEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateItemLBDRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateItemRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkLocationRepository;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkLocationService;
import com.dxc.eproc.estimate.service.dto.SubEstimateAggregateDTO;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkLocationDTO;
import com.dxc.eproc.estimate.service.mapper.SubEstimateMapper;
import com.dxc.eproc.estimate.service.mapper.WorkLocationMapper;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class SubEstimateResourceIT extends AbstractTestNGSpringContextTests {

	private final static Logger log = LoggerFactory.getLogger(SubEstimateResourceIT.class);

	private static final Long DEFAULT_WORK_ESTIMATE_ID = 1L;

	private static final Long DEFAULT_SOR_WOR_CATEGORY_ID = 1L;

	private static final String DEFAULT_SUB_ESTIMATE_NAME = "AAAAAAAAAA";

	private static final BigDecimal DEFAULT_ESTIMATE_TOTAL = new BigDecimal(1);
	private static final BigDecimal UPDATED_ESTIMATE_TOTAL = new BigDecimal(2);

	private static final Long DEFAULT_AREA_WEIGHTAGE_ID = 1L;
	private static final Long UPDATED_AREA_WEIGHTAGE_ID = 2L;

	private static final Long DEFAULT_AREA_WEIGHTAGE_CIRCLE = 1L;
	private static final Long UPDATED_AREA_WEIGHTAGE_CIRCLE = 2L;

	private static final String DEFAULT_AREA_WEIGHTAGE_DESCRIPTION = "AAAAAAAAAA";
	private static final String UPDATED_AREA_WEIGHTAGE_DESCRIPTION = "BBBBBBBBBB";

	private static final Boolean DEFAULT_COMPLETED_YN = false;
	private static final Boolean UPDATED_COMPLETED_YN = true;

	private static final Long DEFAULT_WORK_SUBESTIMATE_GROUP_ID = 1L;

	private static final String ENTITY_API_URL = "/v1/api/work-estimate/{workEstimateId}/sub-estimate";
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

	private static final String ENTITY_API_URL1 = "/v1/api/work-estimate/{workEstimateId}/sub-estimate-aggregate";
	private static final String ENTITY_API_URL2 = "/v1/api/work-estimate/{workEstimateId}/sub-estimates-with-pagination";
	private static final String ENTITY_API_URL3 = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{id}/complete";

	@Autowired
	private SubEstimateRepository subEstimateRepository;

	@Autowired
	private WorkLocationRepository workLocationRepository;

	@Autowired
	private WorkEstimateItemRepository workEstimateItemRepository;

	@Autowired
	private WorkEstimateRepository workEstimateRepository;

	@Autowired
	private WorkEstimateItemLBDRepository workEstimateItemLBDRepository;

	@Autowired
	private WorkLocationService workLocationService;

	@Autowired
	private SubEstimateService subEstimateService;

	@Autowired
	private SubEstimateMapper subEstimateMapper;

	@Autowired
	private WorkLocationMapper workLocationMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restSubEstimateMockMvc;

	private SubEstimate subEstimate;

	private WorkEstimate workEstimate;

	private WorkLocationDTO workLocationDTO;

	private SubEstimateDTO subEstimateDTO;

	public static SubEstimate createEntity(EntityManager em) {
		SubEstimate subEstimate = new SubEstimate().workEstimateId(DEFAULT_WORK_ESTIMATE_ID)
				.sorWorCategoryId(DEFAULT_SOR_WOR_CATEGORY_ID).subEstimateName(DEFAULT_SUB_ESTIMATE_NAME)
				.estimateTotal(DEFAULT_ESTIMATE_TOTAL).areaWeightageId(DEFAULT_AREA_WEIGHTAGE_ID)
				.areaWeightageCircle(DEFAULT_AREA_WEIGHTAGE_CIRCLE)
				.areaWeightageDescription(DEFAULT_AREA_WEIGHTAGE_DESCRIPTION).completedYn(DEFAULT_COMPLETED_YN);
		subEstimate.setWorkSubEstimateGroupId(DEFAULT_WORK_SUBESTIMATE_GROUP_ID);

		return subEstimate;
	}

	private WorkLocationDTO createWorkLocationDTO() {
		WorkLocationDTO workLocationDTO = new WorkLocationDTO();
		workLocationDTO.setSubEstimateId(1L);
		workLocationDTO.setDistrictId(1L);
		workLocationDTO.setLoksabhaContId(1L);
		workLocationDTO.setTaluqId(1L);
		workLocationDTO.setAssemblyContId(1L);
		workLocationDTO.setLatitudeDegrees(1);
		workLocationDTO.setLatitudeMinutes(1);
		workLocationDTO.setLatitudeSeconds(1);
		workLocationDTO.setLongitudeDegrees(1);
		workLocationDTO.setLongitudeMinutes(1);
		workLocationDTO.setLongitudeSeconds(1);
		workLocationDTO.setLocationDescription("Test Description");
		return workLocationDTO;
	}

	private SubEstimateDTO createSubEstimateDTO() {
		SubEstimateDTO subEstimateDTO = new SubEstimateDTO();
		subEstimateDTO.setAreaWeightageCircle(DEFAULT_AREA_WEIGHTAGE_CIRCLE);
		subEstimateDTO.setAreaWeightageDescription(DEFAULT_AREA_WEIGHTAGE_DESCRIPTION);
		subEstimateDTO.setAreaWeightageId(DEFAULT_AREA_WEIGHTAGE_ID);
		subEstimateDTO.setCompletedYn(DEFAULT_COMPLETED_YN);
		subEstimateDTO.setEstimateTotal(DEFAULT_ESTIMATE_TOTAL);
		subEstimateDTO.setSorWorCategoryId(DEFAULT_SOR_WOR_CATEGORY_ID);
		subEstimateDTO.setSubEstimateName(DEFAULT_SUB_ESTIMATE_NAME);
		subEstimateDTO.setWorkEstimateId(DEFAULT_WORK_ESTIMATE_ID);
		subEstimateDTO.setWorkSubEstimateGroupId(DEFAULT_WORK_SUBESTIMATE_GROUP_ID);
		return subEstimateDTO;
	}

	private WorkEstimateItem createWorkEstimateItem() {
		WorkEstimateItem workEstimateItemDTO = new WorkEstimateItem();
		workEstimateItemDTO.setCategoryId(1L);
		workEstimateItemDTO.setCatWorkSorItemId(1L);
		workEstimateItemDTO.setFinalRate(BigDecimal.valueOf(20));
		workEstimateItemDTO.setFloorNumber(2);
		workEstimateItemDTO.setBaseRate(BigDecimal.valueOf(20));
		workEstimateItemDTO.setDescription("test description");
		workEstimateItemDTO.setItemCode("12A");
		workEstimateItemDTO.setLbdPerformedYn(true);
		workEstimateItemDTO.setRaPerformedYn(true);
		workEstimateItemDTO.setQuantity(BigDecimal.valueOf(20));
		workEstimateItemDTO.setUomId(1L);
		return workEstimateItemDTO;
	}

	private SubEstimateAggregateDTO createSubEstimateAggregateDTO() {
		SubEstimateAggregateDTO subEstimateAggregateDTO = new SubEstimateAggregateDTO();
		List<WorkLocationDTO> workLocationDTOList = new ArrayList<>();
		workLocationDTOList.add(workLocationDTO);
		List<Long> deletedWorkLocationId = new ArrayList<>();
		deletedWorkLocationId.add(workLocationDTO.getId());
		subEstimateAggregateDTO.setSubEstimate(subEstimateDTO);
		subEstimateAggregateDTO.setWorkLocations(workLocationDTOList);
		subEstimateAggregateDTO.setDeletedWorkLocationIds(deletedWorkLocationId);
		return subEstimateAggregateDTO;
	}

	private WorkEstimate createWorkEstimateEntity() {
		WorkEstimate workEstimate = new WorkEstimate().workEstimateNumber("11112222").status(WorkEstimateStatus.DRAFT)
				.deptId(1L).locationId(1L).fileNumber("1234").name("Road Repair")
				.description("Road Repair work estimate.").groupLumpsumTotal(BigDecimal.valueOf(20)).estimateTypeId(1L)
				.workTypeId(1L).workCategoryId(1L).workCategoryAttribute(10L)
				.workCategoryAttributeValue(BigDecimal.valueOf(20)).adminSanctionAccordedYn(false)
				.techSanctionAccordedYn(false).approvedBudgetYn(false).groupOverheadTotal(BigDecimal.valueOf(20));

		return workEstimate;
	}

	@BeforeClass
	public void setUp() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - setUp");

		workEstimate = createWorkEstimateEntity();

		workLocationDTO = createWorkLocationDTO();
		workLocationDTO = workLocationService.save(workLocationDTO);

		workEstimate = workEstimateRepository.saveAndFlush(workEstimate);
		subEstimateDTO = createSubEstimateDTO();
		subEstimateDTO.setWorkEstimateId(workEstimate.getId());
		subEstimateDTO = subEstimateService.save(subEstimateDTO);
	}

	@BeforeMethod
	public void initTest() {
		log.info("==================================================================================");
		log.info("This is executed before each Test - initTest");

		subEstimate = createEntity(em);
	}

	@Test
	@Transactional
	void createSubEstimate() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createSubEstimate - Start");

		int databaseSizeBeforeCreate = subEstimateRepository.findAll().size();

		// Create the WorkEstimateItem
		SubEstimateDTO subEstimateDTO = subEstimateMapper.toDto(subEstimate);
		List<SubEstimateDTO> subEstimateDTOList = new ArrayList<>();
		subEstimateDTOList.add(subEstimateDTO);
		subEstimateDTOList.add(subEstimateDTO);

		restSubEstimateMockMvc.perform(post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subEstimateDTO)))
				.andExpect(status().isCreated());

		// Validate the SubEstimate in the database
		List<SubEstimate> subEstimateList = subEstimateRepository.findAll();
		assertThat(subEstimateList).hasSize(databaseSizeBeforeCreate + 1);
		SubEstimate testSubEstimate = subEstimateList.get(subEstimateList.size() - 1);
		//assertThat(testSubEstimate.getWorkEstimateId()).isEqualTo(DEFAULT_WORK_ESTIMATE_ID);
		assertThat(testSubEstimate.getSorWorCategoryId()).isEqualTo(DEFAULT_SOR_WOR_CATEGORY_ID);
		assertThat(testSubEstimate.getSubEstimateName()).isEqualTo(DEFAULT_SUB_ESTIMATE_NAME);
		assertThat(testSubEstimate.getEstimateTotal()).isEqualByComparingTo(DEFAULT_ESTIMATE_TOTAL);
		assertThat(testSubEstimate.getAreaWeightageId()).isEqualTo(DEFAULT_AREA_WEIGHTAGE_ID);
		assertThat(testSubEstimate.getAreaWeightageCircle()).isEqualTo(DEFAULT_AREA_WEIGHTAGE_CIRCLE);
		assertThat(testSubEstimate.getAreaWeightageDescription()).isEqualTo(DEFAULT_AREA_WEIGHTAGE_DESCRIPTION);
		assertThat(testSubEstimate.getCompletedYn()).isEqualTo(DEFAULT_COMPLETED_YN);

		log.info("Test - createSubEstimate - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void postWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postWorkEstimateNotFound - Start");

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;
		SubEstimateDTO subEstimateDTO = subEstimateMapper.toDto(subEstimate);

		int databaseSizeBeforeCreate = subEstimateRepository.findAll().size();

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restSubEstimateMockMvc.perform(post(ENTITY_API_URL, WORKESTIMATEID).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(subEstimateDTO))).andExpect(status().isNotFound());

		// Validate the WorkLocation in the database
		List<SubEstimate> workLocationList = subEstimateRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - postWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void postInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postInvalidWorkEstimateStatus - Start");

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		WorkEstimate tempWorkEstimate = workEstimateRepository.findById(workEstimate.getId()).get();
		tempWorkEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(tempWorkEstimate);

		SubEstimateDTO subEstimateDTO = subEstimateMapper.toDto(subEstimate);

		int databaseSizeBeforeCreate = subEstimateRepository.findAll().size();

		restSubEstimateMockMvc.perform(post(ENTITY_API_URL, tempWorkEstimate.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subEstimateDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkLocation in the database
		List<SubEstimate> subEstimateList = subEstimateRepository.findAll();
		assertThat(subEstimateList).hasSize(databaseSizeBeforeCreate);

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - postInvalidWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void createSubEstimateAggregate() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createSubEstimateAggregate - Start");

		int databaseSizeBeforeCreate = subEstimateRepository.findAll().size();
		// Create the WorkEstimateItem

		SubEstimateAggregateDTO subEstimateAggregateDTO = new SubEstimateAggregateDTO();

		SubEstimateDTO subEstimateDTO = createSubEstimateDTO();
		WorkLocationDTO workLocationDTO = createWorkLocationDTO();
		workLocationDTO.setSubEstimateId(null);

		List<WorkLocationDTO> workLocationDTOList = new ArrayList<>();
		workLocationDTOList.add(workLocationDTO);
		subEstimateAggregateDTO.setSubEstimate(subEstimateDTO);
		subEstimateAggregateDTO.setWorkLocations(workLocationDTOList);

		restSubEstimateMockMvc
				.perform(post(ENTITY_API_URL1, workEstimate.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(subEstimateAggregateDTO)))
				.andExpect(status().isCreated());

		List<SubEstimate> subEstimateList = subEstimateRepository.findAll();
		assertThat(subEstimateList).hasSize(databaseSizeBeforeCreate + 1);

		SubEstimate testSubEstimate = subEstimateList.get(subEstimateList.size() - 1);
		//assertThat(testSubEstimate.getWorkEstimateId()).isEqualTo(DEFAULT_WORK_ESTIMATE_ID);
		assertThat(testSubEstimate.getSorWorCategoryId()).isEqualTo(DEFAULT_SOR_WOR_CATEGORY_ID);
		assertThat(testSubEstimate.getSubEstimateName()).isEqualTo(DEFAULT_SUB_ESTIMATE_NAME);
		assertThat(testSubEstimate.getEstimateTotal()).isEqualByComparingTo(DEFAULT_ESTIMATE_TOTAL);
		assertThat(testSubEstimate.getAreaWeightageId()).isEqualTo(DEFAULT_AREA_WEIGHTAGE_ID);
		assertThat(testSubEstimate.getAreaWeightageCircle()).isEqualTo(DEFAULT_AREA_WEIGHTAGE_CIRCLE);
		assertThat(testSubEstimate.getAreaWeightageDescription()).isEqualTo(DEFAULT_AREA_WEIGHTAGE_DESCRIPTION);
		assertThat(testSubEstimate.getCompletedYn()).isEqualTo(DEFAULT_COMPLETED_YN);

		log.info("Test - createSubEstimateAggregate - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void createSubEstimateAggregateDelete() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createSubEstimateAggregateDelete - Start");

		SubEstimateAggregateDTO subEstimateAggregateDTO = new SubEstimateAggregateDTO();

		WorkLocationDTO newWorkLocation = createWorkLocationDTO();
		WorkLocation workLocation1 = workLocationMapper.toEntity(newWorkLocation);
		WorkLocation workLocation2 = workLocationMapper.toEntity(newWorkLocation);

		workLocation1.setSubEstimateId(subEstimateDTO.getId());
		workLocation2.setSubEstimateId(subEstimateDTO.getId());
		workLocation1 = workLocationRepository.save(workLocation1);
		workLocation2 = workLocationRepository.save(workLocation2);

		WorkLocationDTO newWorkLocation1 = workLocationMapper.toDto(workLocation1);
		WorkLocationDTO newWorkLocation2 = workLocationMapper.toDto(workLocation2);

		List<WorkLocationDTO> workLocationDTOList = new ArrayList<>();
		workLocationDTOList.add(newWorkLocation1);

		List<Long> deletedWorkLocationIds = new ArrayList<>();
		deletedWorkLocationIds.add(newWorkLocation2.getId());

		subEstimateAggregateDTO.setSubEstimate(subEstimateDTO);
		subEstimateAggregateDTO.setWorkLocations(workLocationDTOList);
		subEstimateAggregateDTO.setDeletedWorkLocationIds(deletedWorkLocationIds);

		restSubEstimateMockMvc
				.perform(post(ENTITY_API_URL1, workEstimate.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(subEstimateAggregateDTO)))
				.andExpect(status().isCreated());

		log.info("Test - createSubEstimateAggregateDelete - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void createSubEstimateAggregateDeleteConflictIds() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createSubEstimateAggregateDelete - Start");

		SubEstimateAggregateDTO subEstimateAggregateDTO = new SubEstimateAggregateDTO();

		WorkLocationDTO newWorkLocation = createWorkLocationDTO();
		WorkLocation workLocation = workLocationMapper.toEntity(newWorkLocation);
		workLocation = workLocationRepository.save(workLocation);
		newWorkLocation = workLocationMapper.toDto(workLocation);

		List<WorkLocationDTO> workLocationDTOList = new ArrayList<>();
		workLocationDTOList.add(newWorkLocation);

		List<Long> deletedWorkLocationIds = new ArrayList<>();
		deletedWorkLocationIds.add(newWorkLocation.getId());

		subEstimateAggregateDTO.setSubEstimate(subEstimateDTO);
		subEstimateAggregateDTO.setWorkLocations(workLocationDTOList);
		subEstimateAggregateDTO.setDeletedWorkLocationIds(deletedWorkLocationIds);

		restSubEstimateMockMvc
				.perform(post(ENTITY_API_URL1, workEstimate.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(subEstimateAggregateDTO)))
				.andExpect(status().isBadRequest());

		log.info("Test - createSubEstimateAggregateDelete - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void postWorkEstimateAggregateNotFound() throws Exception {
		log.info("==========================================================================");
		log.info("Test - postWorkEstimateAggregateNotFound - Start");

		// Create the WorkEstimateItem
		SubEstimateAggregateDTO subEstimateAggregateDTO = createSubEstimateAggregateDTO();
		final Long WORKESTIMATEID = Long.MAX_VALUE;
		restSubEstimateMockMvc
				.perform(post(ENTITY_API_URL1, WORKESTIMATEID).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(subEstimateAggregateDTO)))
				.andExpect(status().isNotFound());

		log.info("Test - postWorkEstimateAggregateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void postInvalidAggregateWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - postInvalidAggregateWorkEstimateStatus - Start");

		WorkEstimate tempWorkEstimate = workEstimateRepository.findById(workEstimate.getId()).get();
		tempWorkEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(tempWorkEstimate);

		SubEstimateAggregateDTO subEstimateAggregateDTO = createSubEstimateAggregateDTO();
		restSubEstimateMockMvc

				.perform(post(ENTITY_API_URL1, tempWorkEstimate.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(subEstimateAggregateDTO)))
				.andExpect(status().isBadRequest());

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - postInvalidAggregateWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	public void createWorkSubEstimateAggregate_invalidWorkLocationTest() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkSubEstimateAggregate_invalidWorkLocationTest - Start");

		SubEstimateAggregateDTO subEstimateAggregateDTO = new SubEstimateAggregateDTO();

		WorkLocationDTO newWorkLocation = createWorkLocationDTO();

		List<WorkLocationDTO> workLocationDTOList = new ArrayList<>();
		workLocationDTOList.add(newWorkLocation);

		List<Long> deletedWorkLocationIds = new ArrayList<>();
		deletedWorkLocationIds.add(newWorkLocation.getId());

		subEstimateAggregateDTO.setSubEstimate(subEstimateDTO);
		subEstimateAggregateDTO.setWorkLocations(workLocationDTOList);
		subEstimateAggregateDTO.setDeletedWorkLocationIds(deletedWorkLocationIds);

		restSubEstimateMockMvc
				.perform(post(ENTITY_API_URL1, workEstimate.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(subEstimateAggregateDTO)))
				.andExpect(status().isBadRequest());

		log.info("Test - createWorkSubEstimateAggregate_invalidWorkLocationTest - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	public void createWorkSubEstimateAggregate_invalidWorkLocationWithIdTest() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkSubEstimateAggregate_invalidWorkLocationWithIdTest - Start");

		SubEstimateAggregateDTO subEstimateAggregateDTO = createSubEstimateAggregateDTO();
		List<WorkLocationDTO> workLocations = subEstimateAggregateDTO.getWorkLocations();
		WorkLocationDTO workLocationDTO = createWorkLocationDTO();
		workLocationDTO.setId(Long.MAX_VALUE);
		workLocations.add(workLocationDTO);
		subEstimateAggregateDTO.setWorkLocations(workLocations);

		restSubEstimateMockMvc
				.perform(post(ENTITY_API_URL1, workEstimate.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(subEstimateAggregateDTO)))
				.andExpect(status().isBadRequest());

		log.info("Test - createWorkSubEstimateAggregate_invalidWorkLocationWithIdTest - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void postAggregateSubEstimateDtoRecordNotFound() throws Exception {
		log.info("==========================================================================");
		log.info("Test - postAggregateSubEstimateDtoRecordNotFound - Start");

		SubEstimateAggregateDTO subEstimateAggregateDTO = createSubEstimateAggregateDTO();
		subEstimateDTO.setId(Long.MAX_VALUE);
		subEstimateAggregateDTO.setSubEstimate(subEstimateDTO);

		restSubEstimateMockMvc
				.perform(post(ENTITY_API_URL1, workEstimate.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(subEstimateAggregateDTO)))
				.andExpect(status().isNotFound());

		log.info("Test - postAggregateSubEstimateDtoRecordNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	public void createWorkSubEstimateAggregate_WorkLocationListTest() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkSubEstimateAggregate_WorkLocationListTest - Start");

		SubEstimateAggregateDTO subEstimateAggregateDTO = new SubEstimateAggregateDTO();

		SubEstimateDTO subEstimateDTO = createSubEstimateDTO();
		SubEstimate subEstimate = subEstimateMapper.toEntity(subEstimateDTO);
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		WorkLocationDTO workLocationDTO = createWorkLocationDTO();
		workLocationDTO.setSubEstimateId(subEstimate.getId());

		List<WorkLocationDTO> workLocationDTOList = new ArrayList<>();
		workLocationDTOList.add(workLocationDTO);
		subEstimateAggregateDTO.setSubEstimate(subEstimateMapper.toDto(subEstimate));
		subEstimateAggregateDTO.setWorkLocations(workLocationDTOList);

		restSubEstimateMockMvc
				.perform(post(ENTITY_API_URL1, workEstimate.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(subEstimateAggregateDTO)))
				.andExpect(status().isCreated());

		log.info("Test - createWorkSubEstimateAggregate_WorkLocationListTest - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllSubEstimates() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllSubEstimates - Start");

		// Get all the workLocationList
		restSubEstimateMockMvc.perform(get(ENTITY_API_URL, workEstimate.getId()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());

		log.info("Test - getAllSubEstimates - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getSubEstimateAggregate() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getSubEstimateAggregate - End");
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		// Get all the subEstimateList
		restSubEstimateMockMvc.perform(get(ENTITY_API_URL1 + "/{id}", workEstimate.getId(), subEstimate.getId()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());

		log.info("Test - getSubEstimateAggregate - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getSubEstimateAggregateWorkEstimateNotFound() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getSubEstimateAggregateWorkEstimateNotFound - End");
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		restSubEstimateMockMvc.perform(get(ENTITY_API_URL1 + "/{id}", Long.MAX_VALUE, subEstimate.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - getSubEstimateAggregateWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getSubEstimateAggregateSubEstimateNotFound() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getSubEstimateAggregateSubEstimateNotFound - End");
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		restSubEstimateMockMvc.perform(get(ENTITY_API_URL1 + "/{id}", workEstimate.getId(), Long.MAX_VALUE))
				.andExpect(status().isNotFound());

		log.info("Test - getSubEstimateAggregateSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllSubEstimatesWorkEstimateNotFound() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllSubEstimatesWorkEstimateNotFound - Start");

		// Get all the workLocationList
		restSubEstimateMockMvc.perform(get(ENTITY_API_URL, Long.MAX_VALUE)).andExpect(status().isNotFound());

		log.info("Test - getAllSubEstimatesWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getSubEstimate() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getSubEstimate - Start");

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		// Get the subEstimate
		restSubEstimateMockMvc.perform(get(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId()))
				.andExpect(status().isOk());

		log.info("Test - getSubEstimate - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkEstimateNotFound - Start");

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restSubEstimateMockMvc.perform(get(ENTITY_API_URL_ID, WORKESTIMATEID, subEstimate.getId()))
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
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restSubEstimateMockMvc.perform(get(ENTITY_API_URL_ID, workEstimate.getId(), SUBESTIMATEID))
				.andExpect(status().isNotFound());

		log.info("Test - getSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putNewSubEstimate() throws Exception {
		log.info("==========================================================================");
		log.info("Test - putNewSubEstimate - Start");

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		int databaseSizeBeforeUpdate = subEstimateRepository.findAll().size();

		// Update the subEstimate
		SubEstimate updatedSubEstimate = subEstimateRepository.findById(subEstimate.getId()).get();
		em.detach(updatedSubEstimate);
		updatedSubEstimate.workEstimateId(DEFAULT_WORK_ESTIMATE_ID).sorWorCategoryId(DEFAULT_SOR_WOR_CATEGORY_ID)
				.subEstimateName(DEFAULT_SUB_ESTIMATE_NAME).estimateTotal(UPDATED_ESTIMATE_TOTAL)
				.areaWeightageId(UPDATED_AREA_WEIGHTAGE_ID).areaWeightageCircle(UPDATED_AREA_WEIGHTAGE_CIRCLE)
				.areaWeightageDescription(UPDATED_AREA_WEIGHTAGE_DESCRIPTION).completedYn(UPDATED_COMPLETED_YN);
		SubEstimateDTO subEstimateDTO = subEstimateMapper.toDto(updatedSubEstimate);

		restSubEstimateMockMvc.perform(put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimateDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subEstimateDTO)))
				.andExpect(status().isOk());

		// Validate the SubEstimate in the database
		List<SubEstimate> subEstimateList = subEstimateRepository.findAll();
		assertThat(subEstimateList).hasSize(databaseSizeBeforeUpdate);

		log.info("Test - putNewSubEstimate - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putWorkEstimateNotFound - Start");

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		SubEstimateDTO subEstimateDTO = subEstimateMapper.toDto(subEstimate);

		int databaseSizeBeforeCreate = subEstimateRepository.findAll().size();

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restSubEstimateMockMvc.perform(put(ENTITY_API_URL_ID, WORKESTIMATEID, subEstimateDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subEstimateDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkLocation in the database
		List<SubEstimate> subEstimateList = subEstimateRepository.findAll();
		assertThat(subEstimateList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - putWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putSubEstimateNotFound - Start");

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		SubEstimateDTO subEstimateDTO = subEstimateMapper.toDto(subEstimate);

		int databaseSizeBeforeCreate = subEstimateRepository.findAll().size();

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restSubEstimateMockMvc.perform(put(ENTITY_API_URL_ID, workEstimate.getId(), SUBESTIMATEID)
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subEstimateDTO)))
				.andExpect(status().isNotFound());

		// Validate the WorkLocation in the database
		List<SubEstimate> subEstimateList = subEstimateRepository.findAll();
		assertThat(subEstimateList).hasSize(databaseSizeBeforeCreate);

		log.info("Test - putSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void putInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - putInvalidWorkEstimateStatus - Start");

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		WorkEstimate tempWorkEstimate = workEstimateRepository.findById(workEstimate.getId()).get();
		tempWorkEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(tempWorkEstimate);

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		SubEstimateDTO subEstimateDTO = subEstimateMapper.toDto(subEstimate);

		int databaseSizeBeforeCreate = subEstimateRepository.findAll().size();

		restSubEstimateMockMvc.perform(put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimateDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subEstimateDTO)))
				.andExpect(status().isBadRequest());

		// Validate the WorkLocation in the database
		List<SubEstimate> workLocationList = subEstimateRepository.findAll();
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
	void deleteSubEstimate() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteSubEstimate - Start");

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		int databaseSizeBeforeDelete = subEstimateRepository.findAll().size();

		// Delete the workEstimateItem
		restSubEstimateMockMvc.perform(
				delete(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		List<SubEstimate> subEstimateList = subEstimateRepository.findAll();
		assertThat(subEstimateList).hasSize(databaseSizeBeforeDelete - 1);

		log.info("Test - deleteSubEstimate - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void deleteWorkEstimateSORItemLBDPerformed() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateSORItemLBDPerformed - Start");
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		WorkEstimateItem workEstimateItem1 = createWorkEstimateItem();
		workEstimateItem1.setSubEstimateId(subEstimate.getId());
		workEstimateItem1.setEntryOrder(2);
		workEstimateItemRepository.saveAndFlush(workEstimateItem1);

		WorkEstimateItemLBD workEstimateItemLBD = new WorkEstimateItemLBD();
		workEstimateItemLBD.setLbdLength(BigDecimal.valueOf(2.0000));
		workEstimateItemLBD.setLbdBredth(BigDecimal.valueOf(2.0000));
		workEstimateItemLBD.setLbdDepth(BigDecimal.valueOf(2.0000));
		workEstimateItemLBD.setLbdNos(BigDecimal.valueOf(1.0000));
		workEstimateItemLBD.setLbdQuantity(BigDecimal.valueOf(1.0000));
		workEstimateItemLBD.setLbdTotal(BigDecimal.valueOf(8.0000));
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem1.getId());

		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);

		int databaseSizeBeforeDelete = workEstimateItemRepository.findAll().size();

		// Delete the workEstimateItem
		restSubEstimateMockMvc
				.perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem1.getId())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeDelete - 1);

		log.info("Test - deleteWorkEstimateSORItemLBDPerformed - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void deleteWorkEstimateNonSORItemLBDPerformed() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateSORItemLBDPerformed - Start");
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		WorkEstimateItem workEstimateItem1 = createWorkEstimateItem();
		workEstimateItem1.setSubEstimateId(subEstimate.getId());
		workEstimateItem1.setEntryOrder(2);
		workEstimateItem1.setCatWorkSorItemId(null);
		workEstimateItemRepository.saveAndFlush(workEstimateItem1);

		WorkEstimateItemLBD workEstimateItemLBD = new WorkEstimateItemLBD();
		workEstimateItemLBD.setLbdLength(BigDecimal.valueOf(2.0000));
		workEstimateItemLBD.setLbdBredth(BigDecimal.valueOf(2.0000));
		workEstimateItemLBD.setLbdDepth(BigDecimal.valueOf(2.0000));
		workEstimateItemLBD.setLbdNos(BigDecimal.valueOf(1.0000));
		workEstimateItemLBD.setLbdQuantity(BigDecimal.valueOf(1.0000));
		workEstimateItemLBD.setLbdTotal(BigDecimal.valueOf(8.0000));
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem1.getId());

		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);

		int databaseSizeBeforeDelete = workEstimateItemRepository.findAll().size();

		// Delete the workEstimateItem
		restSubEstimateMockMvc
				.perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem1.getId())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository.findAll();
		assertThat(workEstimateItemList).hasSize(databaseSizeBeforeDelete - 1);

		log.info("Test - deleteWorkEstimateSORItemLBDPerformed - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void deleteSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - deleteSubEstimateNotFound - Start");

		// setting wrong id here
		final Long ID = Long.MAX_VALUE;

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restSubEstimateMockMvc.perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), ID))
				.andExpect(status().isNotFound());

		log.info("Test - deleteSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void deleteWorkEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateNotFound - Start");

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restSubEstimateMockMvc.perform(delete(ENTITY_API_URL_ID, WORKESTIMATEID, subEstimate.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - deleteWorkEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	public void deleteInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - deleteInvalidWorkEstimateStatus - Start");

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		WorkEstimate tempWorkEstimate = workEstimateRepository.findById(workEstimate.getId()).get();
		tempWorkEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(tempWorkEstimate);

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		int databaseSizeBeforeCreate = subEstimateRepository.findAll().size();

		restSubEstimateMockMvc.perform(delete(ENTITY_API_URL_ID, tempWorkEstimate.getId(), subEstimate.getId()))
				.andExpect(status().isBadRequest());

		// Validate the WorkLocation in the database
		List<SubEstimate> subEstimateList = subEstimateRepository.findAll();
		assertThat(subEstimateList).hasSize(databaseSizeBeforeCreate);

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - deleteInvalidWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllSubEstimatesSuccess() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllSubEstimatesSuccess - Start");

		// Get all the workLocationList
		restSubEstimateMockMvc.perform(get(ENTITY_API_URL2, workEstimate.getId()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());

		log.info("Test - getAllSubEstimatesSuccess - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getAllSubEstimatesNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getAllSubEstimatesNotFound - Start");

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restSubEstimateMockMvc.perform(get(ENTITY_API_URL2, WORKESTIMATEID)).andExpect(status().isNotFound());

		log.info("Test - getAllSubEstimatesNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getcompleteSubEstimate() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getcompleteSubEstimate - Start");

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		WorkEstimateItem workEstimateItem1 = createWorkEstimateItem();
		workEstimateItem1.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem1);

		// Get the subEstimate
		restSubEstimateMockMvc.perform(get(ENTITY_API_URL3, workEstimate.getId(), subEstimate.getId()))
				.andExpect(status().isOk());

		log.info("Test - getcompleteSubEstimate - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getcompleteSubEstimateBadRequest() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getcompleteSubEstimateBadRequest - Start");

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		WorkEstimateItem workEstimateItem1 = createWorkEstimateItem();
		workEstimateItem1.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem1);
		workEstimateItemRepository.deleteAll();

		// Get the subEstimate
		restSubEstimateMockMvc.perform(get(ENTITY_API_URL3, workEstimate.getId(), subEstimate.getId()))
				.andExpect(status().isBadRequest());

		log.info("Test - getcompleteSubEstimateBadRequest - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getcompleteSubEstimateCustomValueNotValid() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getcompleteSubEstimateCustomValueNotValid - Start");

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		WorkEstimateItem workEstimateItem1 = createWorkEstimateItem();
		workEstimateItem1.setQuantity(null);
		workEstimateItem1.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem1);

		// Get the subEstimate
		restSubEstimateMockMvc.perform(get(ENTITY_API_URL3, workEstimate.getId(), subEstimate.getId()))
				.andExpect(status().isBadRequest());

		log.info("Test - getcompleteSubEstimateCustomValueNotValid - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getWorkEstimateForCompleteSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkEstimateForCompleteSubEstimateNotFound - Start");

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restSubEstimateMockMvc.perform(get(ENTITY_API_URL3, WORKESTIMATEID, subEstimate.getId()))
				.andExpect(status().isNotFound());

		log.info("Test - getWorkEstimateForCompleteSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getCompleteSubEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getCompleteSubEstimateNotFound - Start");

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restSubEstimateMockMvc.perform(get(ENTITY_API_URL3, workEstimate.getId(), SUBESTIMATEID))
				.andExpect(status().isNotFound());

		log.info("Test - getCompleteSubEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void getInvalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - getInvalidWorkEstimateStatus - Start");

		// Setting work estimate status as ADMIN_SANCTION_APPROVED.
		WorkEstimate tempWorkEstimate = workEstimateRepository.findById(workEstimate.getId()).get();
		tempWorkEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimateRepository.saveAndFlush(tempWorkEstimate);

		// Initialize the database
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);

		SubEstimateDTO subEstimateDTO = subEstimateMapper.toDto(subEstimate);
		int databaseSizeBeforeCreate = subEstimateRepository.findAll().size();

		restSubEstimateMockMvc.perform(get(ENTITY_API_URL3, tempWorkEstimate.getId(), subEstimateDTO.getId())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

		// Validate the WorkLocation in the database
		List<SubEstimate> workLocationList = subEstimateRepository.findAll();
		assertThat(workLocationList).hasSize(databaseSizeBeforeCreate);

		// Setting work estimate status to DRAFT.
		WorkEstimate WorkEstimateDB = workEstimateRepository.findById(workEstimate.getId()).get();
		WorkEstimateDB.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.saveAndFlush(WorkEstimateDB);

		log.info("Test - getInvalidWorkEstimateStatus - End");
		log.info("==========================================================================");

	}
}
