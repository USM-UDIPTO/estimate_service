package com.dxc.eproc.estimate.integration;

import static org.mockito.Mockito.doNothing;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.EstimateServiceApplication;
import com.dxc.eproc.estimate.document.ReferenceTypes;
import com.dxc.eproc.estimate.document.WorkEstimateDocumentMetaData;
import com.dxc.eproc.estimate.document.WorkEstimateSpace;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.ObjectStore;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.repository.ObjectStoreRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * Integration tests for the {@link WorkEstimateFileUpload} REST controller.
 */
@SpringBootTest(classes = EstimateServiceApplication.class)
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")

public class WorkEstimateFileUploadResourceIT extends AbstractTestNGSpringContextTests {

	/** The CONSTANT LOG. */
	private static final Logger log = LoggerFactory.getLogger(WorkEstimateFileUploadResourceIT.class);

	/** The DEFAULT WORK ESTIMATE NUMBER. */
	private static final String DEFAULT_WORK_ESTIMATE_NUMBER = "AAAAAAAAAA/1/1/WORK_ESTIMATE_";

	/** The DEFAULT_STATUS. */
	private static final WorkEstimateStatus DEFAULT_STATUS = WorkEstimateStatus.DRAFT;

	/** The DEFAULT_DEPT_ID. */
	private static final Long DEFAULT_DEPT_ID = 1L;

	/** The DEFAULT_LOCATION_ID. */
	private static final Long DEFAULT_LOCATION_ID = 1L;

	/** The DEFAULT_FILE_NUMBER. */
	private static final String DEFAULT_FILE_NUMBER = "AAAAAAAAAA";

	/** The DEFAULT_NAME. */
	private static final String DEFAULT_NAME = "AAAAAAAAAA";

	/** The DEFAULT_DESCRIPTION. */
	private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";

	/** The DEFAULT_ESTIMATE_TYPE_ID. */
	private static final Long DEFAULT_ESTIMATE_TYPE_ID = 1L;

	/** The DEFAULT_WOTK_TYPE_ID. */
	private static final Long DEFAULT_WOTK_TYPE_ID = 1L;

	/** The DEFAULT_WORK_CATEGORY_ID. */
	private static final Long DEFAULT_WORK_CATEGORY_ID = 1L;

	/** The DEFAULT_WORK_CATEGORY_ATTRIBUTE. */
	private static final Long DEFAULT_WORK_CATEGORY_ATTRIBUTE = 1L;

	/** The DEFAULT_WORK_CATEGORY_ATTRIBUTE_VALUE. */
	private static final BigDecimal DEFAULT_WORK_CATEGORY_ATTRIBUTE_VALUE = new BigDecimal(1);

	/** The DEFAULT_ADMIN_SANCTION_ACCORDED_YN. */

	private static final Boolean DEFAULT_ADMIN_SANCTION_ACCORDED_YN = true;

	/** The DEFAULT_TECH_SANCTION_ACCORDED_YN. */
	private static final Boolean DEFAULT_TECH_SANCTION_ACCORDED_YN = false;

	/** The DEFAULT_LINE_ESTIMATE_TOTAL. */
	private static final BigDecimal DEFAULT_LINE_ESTIMATE_TOTAL = new BigDecimal(1);

	/** The DEFAULT_ESTIMATE_TOTAL. */
	private static final BigDecimal DEFAULT_ESTIMATE_TOTAL = new BigDecimal(1);

	/** The DEFAULT_LUMPSUM_TOTAL. */
	private static final BigDecimal DEFAULT_LUMPSUM_TOTAL = new BigDecimal(1);

	/** The DEFAULT_GROUP_OVERHEAD_TOTAL. */
	private static final BigDecimal DEFAULT_GROUP_OVERHEAD_TOTAL = new BigDecimal(1);

	/** The DEFAULT_ADMIN_SANCTION_REF_NUMBER. */
	private static final String DEFAULT_ADMIN_SANCTION_REF_NUMBER = "AAAAAAAAAA";

	/** The DEFAULT_ADMIN_SANCTION_REF_DATE. */
	private static final ZonedDateTime DEFAULT_ADMIN_SANCTION_REF_DATE = ZonedDateTime
			.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);

	/** The DEFAULT_ADMIN_SANCTIONED_DATE. */
	private static final ZonedDateTime DEFAULT_ADMIN_SANCTIONED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L),
			ZoneOffset.UTC);

	/** The DEFAULT_APPROVED_BUDGET_YN. */
	private static final Boolean DEFAULT_APPROVED_BUDGET_YN = true;

	/** THE ENTITY_API_URL. */
	private static final String ENTITY_API_URL = "/v1/api/work-estimate";

	/** THE ENTITY_API_URL_ID. */
	private static final String ENTITY_API_URL_ADMIN_ID = ENTITY_API_URL
			+ "/{workEstimateId}/upload-administration-sanction";

	/** The Constant GET_ENTITY_API_URL_ADMIN_ID. */
	private static final String GET_ENTITY_API_URL_ADMIN_ID = ENTITY_API_URL
			+ "/{workEstimateId}/get-administration-sanction";

	/** The Constant DELETE_ENTITY_API_URL_ADMIN_ID. */
	private static final String DELETE_ENTITY_API_URL_ADMIN_ID = ENTITY_API_URL
			+ "/{workEstimateId}/delete-administration-sanction";

	/** THE ENTITY_API_URL_TECH_ID. */
	private static final String ENTITY_API_URL_TECH_ID = ENTITY_API_URL + "/{workEstimateId}/upload-technical-sanction";

	/** The Constant GET_ENTITY_API_URL_TECH_ID. */
	private static final String GET_ENTITY_API_URL_TECH_ID = ENTITY_API_URL
			+ "/{workEstimateId}/get-technical-sanction";

	/** The Constant DELETE_ENTITY_API_URL_TECH_ID. */
	private static final String DELETE_ENTITY_API_URL_TECH_ID = ENTITY_API_URL
			+ "/{workEstimateId}/delete-technical-sanction";

	/** THE ENTITY_API_URL_TECH_ID. */
	private static final String ENTITY_API_URL_WORKESTIMATEID = ENTITY_API_URL
			+ "/{workEstimateId}/upload-estimate-document-prepation";

	/** The Constant GET_ENTITY_API_URL_WORKESTIMATEID. */
	private static final String GET_ENTITY_API_URL_WORKESTIMATEID = ENTITY_API_URL
			+ "/{workEstimateId}/get-estimate-document-prepation";

	/** The Constant DELETE_ENTITY_API_URL_WORKESTIMATEID. */
	private static final String DELETE_ENTITY_API_URL_WORKESTIMATEID = ENTITY_API_URL
			+ "/{workEstimateId}/delete-estimate-docs";

	/** The workEstimate. */
	private WorkEstimate workEstimate;

	/** The workEstimate. */
	private WorkEstimateDocumentMetaData workEstimateDocumentMetaData;

	/** The ObjectStore. */
	private ObjectStore objectStore;

	/** The ObjectStoreRepository. */
	@Autowired
	private ObjectStoreRepository objectStoreRepository;

	/** The workEstimate Repository. */
	@Autowired
	private WorkEstimateRepository workEstimateRepository;

	/** The estimate Document mock mvc. */
	@Autowired
	private MockMvc estimateDocumentMockMvc;

	/** The work estimate space. */
	@Autowired
	private WorkEstimateSpace workEstimateSpace;

	/**
	 * init the class.
	 */
	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");

	}

	/**
	 * inits the Method.
	 */
	@BeforeMethod
	public void initMethod() {
		log.info("==================================================================================");
		log.info("This is executed before once Per method in Class - init");
		workEstimate = createEstimateEntity();
		objectStore = new ObjectStore();

	}

	/**
	 * creates the Estimate Entity.
	 *
	 * @return workestimate
	 */
	public WorkEstimate createEstimateEntity() {
		WorkEstimate workEstimate = new WorkEstimate().workEstimateNumber(DEFAULT_WORK_ESTIMATE_NUMBER)
				.status(DEFAULT_STATUS).deptId(DEFAULT_DEPT_ID).locationId(DEFAULT_LOCATION_ID)
				.fileNumber(DEFAULT_FILE_NUMBER).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION)
				.estimateTypeId(DEFAULT_ESTIMATE_TYPE_ID).workTypeId(DEFAULT_WOTK_TYPE_ID)
				.workCategoryId(DEFAULT_WORK_CATEGORY_ID).workCategoryAttribute(DEFAULT_WORK_CATEGORY_ATTRIBUTE)
				.workCategoryAttributeValue(DEFAULT_WORK_CATEGORY_ATTRIBUTE_VALUE)
				.adminSanctionAccordedYn(DEFAULT_ADMIN_SANCTION_ACCORDED_YN)
				.techSanctionAccordedYn(DEFAULT_TECH_SANCTION_ACCORDED_YN)
				.lineEstimateTotal(DEFAULT_LINE_ESTIMATE_TOTAL).estimateTotal(DEFAULT_ESTIMATE_TOTAL)
				.groupLumpsumTotal(DEFAULT_LUMPSUM_TOTAL).groupOverheadTotal(DEFAULT_GROUP_OVERHEAD_TOTAL)
				.adminSanctionRefNumber(DEFAULT_ADMIN_SANCTION_REF_NUMBER)
				.adminSanctionRefDate(DEFAULT_ADMIN_SANCTION_REF_DATE)
				.adminSanctionedDate(DEFAULT_ADMIN_SANCTIONED_DATE).approvedBudgetYn(DEFAULT_APPROVED_BUDGET_YN);

		return workEstimate;
	}

	/**
	 * create the Entity.
	 *
	 * @return workEstimateDocumentMetaData
	 */
	private WorkEstimateDocumentMetaData createEntity() {
		WorkEstimateDocumentMetaData workEstimateDocumentMetaData = new WorkEstimateDocumentMetaData();
		workEstimateDocumentMetaData.setDocumentName("Hello");
		workEstimateDocumentMetaData.setDocumentType("CheckList");
		workEstimateDocumentMetaData.setReferenceType(ReferenceTypes.ADMINSANCTION_PROCEEDINGS);
		return workEstimateDocumentMetaData;
	}

	/**
	 * upload Administrative Sanction Test.
	 *
	 * @throws Exception exception
	 */
	@Test(priority = 1)
	public void uploadAdministrativeSanctionTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - uploadAdministrativeSanctionTest Start");
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimate = workEstimateRepository.save(workEstimate);
		workEstimateDocumentMetaData = createEntity();
		MockMultipartFile fileInput = new MockMultipartFile("file", "hello.pdf", MediaType.APPLICATION_PDF_VALUE,
				"Welcome, Admin!".getBytes());
		Mockito.when(workEstimateSpace.saveEstimateDocument(Mockito.any())).thenReturn(workEstimateDocumentMetaData);

		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders.multipart(ENTITY_API_URL_ADMIN_ID, workEstimate.getId()).file(fileInput)
						.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

		workEstimate.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.save(workEstimate);

		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders.multipart(ENTITY_API_URL_ADMIN_ID, workEstimate.getId()).file(fileInput)
						.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
		log.info("==================================================================================");
		log.info("Test - uploadAdministrativeSanctionTest Ends");
	}

	/**
	 * estimateId NotFound For Administrative SanctionTest.
	 *
	 * @throws Exception exception
	 */
	@Test(priority = 2)
	public void estimateIdNotFoundForAdministrativeSanctionTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - estimateIdNotFoundForAdministrativeSanctionTest Start");
		long ESTIMATEID = 22L;
		MockMultipartFile fileInput = new MockMultipartFile("file", "hello.pdf", MediaType.APPLICATION_PDF_VALUE,
				"Welcome, Admin!".getBytes());
		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders.multipart(ENTITY_API_URL_ADMIN_ID, ESTIMATEID).file(fileInput)
						.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("==================================================================================");
		log.info("Test - estimateIdNotFoundForAdministrativeSanctionTest Ends");
	}

	/**
	 * get Admin Sanction Test.
	 *
	 * @return the admin sanction test
	 * @throws Exception exception
	 */
	@Test(priority = 4)
	public void getAdminSanctionTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getAdminSanctionTest Start");
		List<WorkEstimateDocumentMetaData> workEstimateSanctionList = new ArrayList<>();
		WorkEstimateDocumentMetaData estimateData = createEntity();
		workEstimateSanctionList.add(estimateData);
		workEstimate = workEstimateRepository.findAll().get(0);
		Mockito.when(
				workEstimateSpace.getSanctionDocument(workEstimate.getId(), ReferenceTypes.ADMINSANCTION_PROCEEDINGS))
				.thenReturn(workEstimateSanctionList);
		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders.get(GET_ENTITY_API_URL_ADMIN_ID, workEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		log.info("==================================================================================");
		log.info("Test - getAdminSanctionTest Ends");
	}

	/**
	 * get Admin Sanction not found Test.
	 *
	 * @return the admin sanction not found test
	 * @throws Exception exception
	 */
	@Test(priority = 5)
	public void getAdminSanctionNotFoundTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getAdminSanctionNotFoundTest Start");
		long WORKESTIMATEID = 34L;
		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders.get(GET_ENTITY_API_URL_ADMIN_ID, WORKESTIMATEID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());

		log.info("==================================================================================");
		log.info("Test - getAdminSanctionNotFoundTest Ends");
	}

	/**
	 * upload Technical Sanction Test.
	 *
	 * @throws Exception exception
	 */
	@Test(priority = 6)
	public void uploadTechnicalSanctionTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - uploadTechnicalSanctionTest Start");
		workEstimate = createEstimateEntity();
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimate.setWorkEstimateNumber("BBBBBBBBB/1/1/WORK_ESTIMATE_");
		workEstimate.setTechSanctionAccordedYn(true);
		workEstimate = workEstimateRepository.save(workEstimate);
		workEstimateDocumentMetaData = createEntity();
		workEstimateDocumentMetaData.setReferenceType(ReferenceTypes.INDENT_WORKS);
		MockMultipartFile fileInput = new MockMultipartFile("file", "helloTechnical.pdf",
				MediaType.APPLICATION_PDF_VALUE, "Welcome, Tech!".getBytes());
		Mockito.when(workEstimateSpace.saveEstimateDocument(Mockito.any())).thenReturn(workEstimateDocumentMetaData);

		estimateDocumentMockMvc
				.perform(
						MockMvcRequestBuilders
								.multipart("/v1/api/work-estimate/{workEstimateId}/upload-technical-sanction",
										workEstimate.getId())
								.file(fileInput).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

		workEstimate.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.save(workEstimate);

		estimateDocumentMockMvc
				.perform(
						MockMvcRequestBuilders
								.multipart("/v1/api/work-estimate/{workEstimateId}/upload-technical-sanction",
										workEstimate.getId())
								.file(fileInput).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
		log.info("==================================================================================");
		log.info("Test - uploadTechnicalSanctionTest Ends");
	}

	/**
	 * estimateId NotFound For Technical SanctionTest.
	 *
	 * @throws Exception exception
	 */
	@Test(priority = 7)
	public void estimateIdNotFoundForTechnicalSanctionTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - estimateIdNotFoundForTechnicalSanctionTest Start");
		long ESTIMATEID = 22L;
		MockMultipartFile fileInput = new MockMultipartFile("file", "hello.pdf", MediaType.APPLICATION_PDF_VALUE,
				"Welcome, Admin!".getBytes());
		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders.multipart(ENTITY_API_URL_TECH_ID, ESTIMATEID).file(fileInput)
						.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("==================================================================================");
		log.info("Test - estimateIdNotFoundForTechnicalSanctionTest Ends");
	}

	/**
	 * get Tech Sanction Test.
	 *
	 * @return the tech sanction test
	 * @throws Exception exception
	 */
	@Test(priority = 9)
	public void getTechSanctionTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getTechSanctionTest Start");
		List<WorkEstimateDocumentMetaData> workEstimateSanctionList = new ArrayList<>();
		WorkEstimateDocumentMetaData estimateData = createEntity();
		estimateData.setReferenceType(ReferenceTypes.TECHSANCTION_PROCEEDINGS);
		workEstimateSanctionList.add(estimateData);
		workEstimate = workEstimateRepository.findAll().get(0);
		Mockito.when(
				workEstimateSpace.getSanctionDocument(workEstimate.getId(), ReferenceTypes.TECHSANCTION_PROCEEDINGS))
				.thenReturn(workEstimateSanctionList);
		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders.get(GET_ENTITY_API_URL_TECH_ID, workEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		log.info("==================================================================================");
		log.info("Test - getTechSanctionTest Ends");
	}

	/**
	 * get Tech Sanction not found Test.
	 *
	 * @return the tech sanction not found test
	 * @throws Exception exception
	 */
	@Test(priority = 10)
	public void getTechSanctionNotFoundTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getTechSanctionNotFoundTest Start");
		long WORKESTIMATEID = 34L;
		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders.get(GET_ENTITY_API_URL_TECH_ID, WORKESTIMATEID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());

		log.info("==================================================================================");
		log.info("Test - getTechSanctionNotFoundTest Ends");
	}

	/**
	 * upload Work Estimate Document Preparation Test.
	 *
	 * @throws Exception exception
	 */
	@Test(priority = 11)
	public void uploadWorkEstimateDocumentPreparationTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - uploadWorkEstimateDocumentPreparation Start");
		workEstimate = createEstimateEntity();
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimate.setWorkEstimateNumber("ABBBBBBBBBBBB/1/1/WORK_ESTIMATE_");
		workEstimateRepository.save(workEstimate);
		workEstimateDocumentMetaData = createEntity();
		workEstimateDocumentMetaData.setReferenceId(workEstimate.getId());
		workEstimateDocumentMetaData.setReferenceType(ReferenceTypes.INDENT_WORKS);
		List<WorkEstimateDocumentMetaData> workEstimateMetaDataList = new ArrayList<>();
		workEstimateMetaDataList.add(workEstimateDocumentMetaData);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(workEstimateDocumentMetaData);
		MockMultipartFile fileInput = new MockMultipartFile("files", "estimate.pdf", MediaType.APPLICATION_PDF_VALUE,
				"Welcome, Admin!".getBytes());
		Mockito.when(workEstimateSpace.saveEstimateDocument(Mockito.any())).thenReturn(workEstimateDocumentMetaData);

		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders.multipart(ENTITY_API_URL_WORKESTIMATEID, workEstimate.getId())
						.file(fileInput).file(makeMultipart("jsonData", jsonString, "application/json"))
						.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

		workEstimate.setStatus(WorkEstimateStatus.DRAFT);
		workEstimateRepository.save(workEstimate);

		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders.multipart(ENTITY_API_URL_WORKESTIMATEID, workEstimate.getId())
						.file(fileInput).file(makeMultipart("jsonData", jsonString, "application/json"))
						.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
		log.info("==================================================================================");
		log.info("Test - uploadWorkEstimateDocumentPreparation Ends");
	}

	/**
	 * work Estimate Id Not Found Test.
	 *
	 * @throws Exception exception
	 */
	@Test(priority = 12)
	public void workEstimateIdNotFoundTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - workEstimateIdNotFoundTest Start");
		long ID = 25L;
		List<WorkEstimateDocumentMetaData> workEstimateMetaDataList = new ArrayList<>();
		workEstimateMetaDataList.add(workEstimateDocumentMetaData);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(workEstimateDocumentMetaData);
		MockMultipartFile fileInput = new MockMultipartFile("files", "estimate.pdf", MediaType.APPLICATION_PDF_VALUE,
				"Welcome, Admin!".getBytes());
		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders.multipart(ENTITY_API_URL_WORKESTIMATEID, ID).file(fileInput)
						.file(makeMultipart("jsonData", jsonString, "application/json"))
						.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
		log.info("==================================================================================");
		log.info("Test - workEstimateIdNotFoundTest Ends");
	}

	/**
	 * get WorkEstimate Preparation Document Test.
	 *
	 * @return the work estimate preparation document test
	 * @throws Exception exception
	 */
	@Test(priority = 13)
	public void getWorkEstimatePreparationDocumentTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - getWorkEstimatePreparationDocumentTest Start");
		List<WorkEstimateDocumentMetaData> workEstimateSanctionList = new ArrayList<>();
		WorkEstimateDocumentMetaData estimateData = createEntity();
		estimateData.setReferenceType(ReferenceTypes.INDENT_WORKS);
		workEstimateSanctionList.add(estimateData);
		workEstimate = workEstimateRepository.findAll().get(0);
		Mockito.when(workEstimateSpace.getSanctionDocument(workEstimate.getId(), ReferenceTypes.INDENT_WORKS))
				.thenReturn(workEstimateSanctionList);
		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders.get(GET_ENTITY_API_URL_WORKESTIMATEID, workEstimate.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		log.info("==================================================================================");
		log.info("Test - getWorkEstimatePreparationDocumentTest Ends");
	}

	/**
	 * get WorkEstimate Preparation Document not found Test.
	 *
	 * @throws Exception exception
	 */
	/*
	 * @Test(priority = 14) public void workEstimatePreprationIdNotFoundTest()
	 * throws Exception { log.info(
	 * "=================================================================================="
	 * ); log.info("Test - workEstimatePreprationIdNotFoundTest Start"); long ID =
	 * 34L; estimateDocumentMockMvc
	 * .perform(MockMvcRequestBuilders.get(ENTITY_API_URL_WORKESTIMATEID, ID)
	 * .contentType(MediaType.APPLICATION_JSON))
	 * .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	 * log.info(
	 * "=================================================================================="
	 * ); log.info("Test - workEstimatePreprationIdNotFoundTest Ends"); }
	 */

	/**
	 * download the Work Estimate Document.
	 *
	 * @throws Exception exception
	 */
	@Test(priority = 16)
	public void downloadWorkEstimateDocument() throws Exception {
		log.info("==================================================================================");
		log.info("Test - downloadWorkEstimateDocument Start");
		workEstimate = workEstimateRepository.findAll().get(0);
		objectStore.setReferenceId(workEstimate.getId());
		objectStore.setReferenceType(ReferenceTypes.INDENT_WORKS);
		objectStore.setActiveYn(true);
		objectStore.setWorkEstimateId(workEstimate.getId());
		objectStore = objectStoreRepository.save(objectStore);
		UUID id = objectStore.getId();
		String fileName = "xxx";
		ResponseEntity<ByteArrayResource> returnValue = ResponseEntity.ok().contentLength(1111)
				.header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
				.body(new ByteArrayResource("Content".getBytes(StandardCharsets.UTF_8)));

		PowerMockito.when(workEstimateSpace.downloadObject(Mockito.anyInt(), Mockito.any())).thenReturn(returnValue);

		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders
						.get(ENTITY_API_URL + "/{workEstimateId}/download-estimate-document/{uuid}",
								workEstimate.getId(), id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("==================================================================================");
		log.info("Test - downloadWorkEstimateDocument Ends");
	}

	/**
	 * delete the Work Estimate Document.
	 *
	 * @throws Exception exception
	 */
	@Test(priority = 15)
	public void deleteWorkEstimateDocument() throws Exception {
		log.info("==================================================================================");
		log.info("Test - deleteWorkEstimateDocument Start");

		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders
						.delete(DELETE_ENTITY_API_URL_WORKESTIMATEID + "/{uuid}", Long.MAX_VALUE, UUID.randomUUID())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

		workEstimate = createEstimateEntity();
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimate.setWorkEstimateNumber("ABBBCCCDDDDCBBBB/1/1/WORK_ESTIMATE_");
		workEstimate = workEstimateRepository.save(workEstimate);
		objectStore.setReferenceId(workEstimate.getId());
		objectStore.setReferenceType(ReferenceTypes.INDENT_WORKS);
		objectStore.setActiveYn(true);
		objectStore.setWorkEstimateId(workEstimate.getId());
		objectStore = objectStoreRepository.save(objectStore);
		UUID id = objectStore.getId();
		doNothing().when(workEstimateSpace).deleteDocument(Mockito.anyInt(), Mockito.any());

		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders
						.delete(DELETE_ENTITY_API_URL_WORKESTIMATEID + "/{uuid}", workEstimate.getId(), id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

		workEstimate.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.save(workEstimate);

		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders
						.delete(DELETE_ENTITY_API_URL_WORKESTIMATEID + "/{uuid}", workEstimate.getId(), id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
		log.info("==================================================================================");
		log.info("Test - deleteWorkEstimateDocument Ends");
	}

	/**
	 * Delete administractive document.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 16)
	public void deleteAdministractiveDocument() throws Exception {
		log.info("==================================================================================");
		log.info("Test - deleteAdministractiveDocument Start");

		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders
						.delete(DELETE_ENTITY_API_URL_ADMIN_ID + "/{uuid}", Long.MAX_VALUE, UUID.randomUUID())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

		workEstimate = createEstimateEntity();
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimate.setWorkEstimateNumber("ABBBCCCDDDDCBBBB/2/2/WORK_ESTIMATE_");
		workEstimate = workEstimateRepository.save(workEstimate);
		objectStore.setReferenceId(workEstimate.getId());
		objectStore.setReferenceType(ReferenceTypes.ADMINSANCTION_PROCEEDINGS);
		objectStore.setActiveYn(true);
		objectStore.setWorkEstimateId(workEstimate.getId());
		objectStore = objectStoreRepository.save(objectStore);
		UUID id = objectStore.getId();
		doNothing().when(workEstimateSpace).deleteDocument(Mockito.anyInt(), Mockito.any());

		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders
						.delete(DELETE_ENTITY_API_URL_ADMIN_ID + "/{uuid}", workEstimate.getId(), id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

		workEstimate.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.save(workEstimate);
		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders
						.delete(DELETE_ENTITY_API_URL_ADMIN_ID + "/{uuid}", workEstimate.getId(), id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
		log.info("==================================================================================");
		log.info("Test - deleteWorkEstimateDocument Ends");
	}

	/**
	 * Delete technical document.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 17)
	public void deleteTechnicalDocument() throws Exception {
		log.info("==================================================================================");
		log.info("Test - deleteTechnicalDocument Start");

		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders
						.delete(DELETE_ENTITY_API_URL_TECH_ID + "/{uuid}", Long.MAX_VALUE, UUID.randomUUID())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

		workEstimate = createEstimateEntity();
		workEstimate.setStatus(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
		workEstimate.setWorkEstimateNumber("ABBBCCCDDDDCBBBB/3/3/WORK_ESTIMATE_");
		workEstimate = workEstimateRepository.save(workEstimate);
		objectStore.setReferenceId(workEstimate.getId());
		objectStore.setReferenceType(ReferenceTypes.TECHSANCTION_PROCEEDINGS);
		objectStore.setActiveYn(true);
		objectStore.setWorkEstimateId(workEstimate.getId());
		objectStore = objectStoreRepository.save(objectStore);
		UUID id = objectStore.getId();
		doNothing().when(workEstimateSpace).deleteDocument(Mockito.anyInt(), Mockito.any());

		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders
						.delete(DELETE_ENTITY_API_URL_TECH_ID + "/{uuid}", workEstimate.getId(), id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

		workEstimate.setStatus(WorkEstimateStatus.DRAFT);
		workEstimate = workEstimateRepository.save(workEstimate);
		estimateDocumentMockMvc
				.perform(MockMvcRequestBuilders
						.delete(DELETE_ENTITY_API_URL_TECH_ID + "/{uuid}", workEstimate.getId(), id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
		log.info("==================================================================================");
		log.info("Test - deleteWorkEstimateDocument Ends");
	}

	/**
	 * makeMultipart.
	 *
	 * @param requestPartName the request part name
	 * @param value           the value
	 * @param contentType     the content type
	 * @return multipart file
	 * @throws Exception exception.
	 */
	private MockMultipartFile makeMultipart(String requestPartName, String value, String contentType) throws Exception {

		return new MockMultipartFile(requestPartName, "", contentType, value.getBytes(Charset.forName("UTF-8")));
	}

	/**
	 * tear Down.
	 *
	 */
	@AfterClass
	public void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - WorkEstimateFileUploadControllerTest");
	}

}