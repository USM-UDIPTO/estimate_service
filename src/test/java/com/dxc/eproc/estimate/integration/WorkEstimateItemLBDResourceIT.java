package com.dxc.eproc.estimate.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.IntegrationTest;
import com.dxc.eproc.estimate.enumeration.LBDOperation;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.model.WorkEstimateItem;
import com.dxc.eproc.estimate.model.WorkEstimateItemLBD;
import com.dxc.eproc.estimate.repository.SubEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateItemLBDRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateItemRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemLBDDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateItemLBDMapper;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateItemMapper;

// TODO: Auto-generated Javadoc
/**
 * Integration tests for the {@link WorkEstimateItemLBDResource} REST
 * controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
public class WorkEstimateItemLBDResourceIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(WorkEstimateItemLBDResourceIT.class);

	/** The Constant DEFAULT_WORK_ESTIMATE_ITEM_ID. */
	private static final Long DEFAULT_WORK_ESTIMATE_ITEM_ID = 1L;

	/** The Constant DEFAULT_LBD_PARTICULARS. */
	private static final String DEFAULT_LBD_PARTICULARS = "AAAAAAAAAA";

	/** The Constant DEFAULT_LBD_NOS. */
	private static final BigDecimal DEFAULT_LBD_NOS = new BigDecimal(1);

	/** The Constant DEFAULT_LBD_LENGTH. */
	private static final BigDecimal DEFAULT_LBD_LENGTH = new BigDecimal(1);

	/** The Constant DEFAULT_LBD_LENGTH_FORMULA. */
	private static final String DEFAULT_LBD_LENGTH_FORMULA = "AAAAAAAAAA";

	/** The Constant DEFAULT_LBD_BREDTH. */
	private static final BigDecimal DEFAULT_LBD_BREDTH = new BigDecimal(1);

	/** The Constant DEFAULT_LBD_BREDTH_FORMULA. */
	private static final String DEFAULT_LBD_BREDTH_FORMULA = "AAAAAAAAAA";

	/** The Constant DEFAULT_LBD_DEPTH. */
	private static final BigDecimal DEFAULT_LBD_DEPTH = new BigDecimal(1);

	/** The Constant DEFAULT_LBD_DEPTH_FORMULA. */
	private static final String DEFAULT_LBD_DEPTH_FORMULA = "AAAAAAAAAA";

	/** The Constant DEFAULT_LBD_QUANTITY. */
	private static final BigDecimal DEFAULT_LBD_QUANTITY = new BigDecimal(1);

	/** The Constant DEFAULT_LBD_TOTAL. */
	private static final BigDecimal DEFAULT_LBD_TOTAL = new BigDecimal(1);

	/** The Constant DEFAULT_ADDITION_DEDUCTION. */
	private static final LBDOperation DEFAULT_ADDITION_DEDUCTION = LBDOperation.ADDITION;

	/** The Constant DEFAULT_CALCULATED_YN. */
	private static final Boolean DEFAULT_CALCULATED_YN = false;

	/** The work estimate item LBD repository. */
	@Autowired
	private WorkEstimateItemLBDRepository workEstimateItemLBDRepository;

	/** The work estimate repository. */
	@Autowired
	private WorkEstimateRepository workEstimateRepository;

	/** The sub estimate repository. */
	@Autowired
	private SubEstimateRepository subEstimateRepository;

	/** The work estimate item repository. */
	@Autowired
	private WorkEstimateItemRepository workEstimateItemRepository;

	/** The work estimate item LBD mapper. */
	@Autowired
	private WorkEstimateItemLBDMapper workEstimateItemLBDMapper;

	/** The work estimate item mapper. */
	@Autowired
	private WorkEstimateItemMapper workEstimateItemMapper;

	/** The em. */
	@Autowired
	private EntityManager em;

	/** The rest work estimate item LBD mock mvc. */
	@Autowired
	private MockMvc restWorkEstimateItemLBDMockMvc;

	/** The work estimate. */
	private WorkEstimate workEstimate;

	/** The sub estimate. */
	private SubEstimate subEstimate;

	/** The work estimate item. */
	private WorkEstimateItem workEstimateItem;

	/** The work estimate item LBD. */
	private WorkEstimateItemLBD workEstimateItemLBD;

	/**
	 * Create an entity for Work Estimate. This is a static method, as tests for
	 * other entities might also need it, if they test an entity which requires the
	 * current entity.
	 *
	 * @param em the em
	 * @return the work estimate
	 */
	public static WorkEstimate createWorkEstimateEntity(EntityManager em) {
		System.err.println("=============createWorkEstimateEntity================");

		WorkEstimate workEstimate = new WorkEstimate().workEstimateNumber("AAAAAAAAAA").status(WorkEstimateStatus.DRAFT)
				.deptId(1L).locationId(1L).fileNumber("AAAAAAAAAA").name("AAAAAAAAAA").description("AAAAAAAAAA")
				.estimateTypeId(1L).workTypeId(1L).workCategoryId(1L).workCategoryAttribute(1L)
				.workCategoryAttributeValue(new BigDecimal(1)).adminSanctionAccordedYn(false)
				.techSanctionAccordedYn(false).lineEstimateTotal(new BigDecimal(1)).estimateTotal(new BigDecimal(1))
				.groupLumpsumTotal(new BigDecimal(1)).groupOverheadTotal(new BigDecimal(1))
				.adminSanctionRefNumber("AAAAAAAAAA")
				.adminSanctionRefDate(ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC))
				.adminSanctionedDate(ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC))
				.techSanctionRefNumber("AAAAAAAAAA")
				.techSanctionedDate(ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC))
				.approvedBy("AAAAAAAAAA").approvedTs(ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC))
				.hkrdbFundedYn(false).schemeId(1L).approvedBudgetYn(false).grantAllocatedAmount(new BigDecimal(1))
				.documentReference("AAAAAAAAAA").provisionalAmount(new BigDecimal(1)).headOfAccount("AAAAAAAAAA");
		return workEstimate;
	}

	/**
	 * Create an entity for SubEstimate. This is a static method, as tests for other
	 * entities might also need it, if they test an entity which requires the
	 * current entity.
	 *
	 * @param em the em
	 * @return the sub estimate
	 */
	public static SubEstimate createSubEstimateEntity(EntityManager em) {
		System.err.println("=============createSubEstimateEntity================");
		SubEstimate subEstimate = new SubEstimate().workEstimateId(1L).sorWorCategoryId(1L)
				.subEstimateName("AAAAAAAAAA").estimateTotal(new BigDecimal(1)).areaWeightageId(1L)
				.areaWeightageCircle(1L).areaWeightageDescription("AAAAAAAAAA").completedYn(false);
		return subEstimate;
	}

	/**
	 * Create an entity for this test. This is a static method, as tests for other
	 * entities might also need it, if they test an entity which requires the
	 * current entity.
	 *
	 * @param em the em
	 * @return the work estimate item LBD
	 */
	public static WorkEstimateItemLBD createEntity(EntityManager em) {
		WorkEstimateItemLBD workEstimateItemLBD = new WorkEstimateItemLBD()
				.workEstimateItemId(DEFAULT_WORK_ESTIMATE_ITEM_ID).lbdParticulars(DEFAULT_LBD_PARTICULARS)
				.lbdNos(DEFAULT_LBD_NOS).lbdLength(DEFAULT_LBD_LENGTH).lbdLengthFormula(DEFAULT_LBD_LENGTH_FORMULA)
				.lbdBredth(DEFAULT_LBD_BREDTH).lbdBredthFormula(DEFAULT_LBD_BREDTH_FORMULA).lbdDepth(DEFAULT_LBD_DEPTH)
				.lbdDepthFormula(DEFAULT_LBD_DEPTH_FORMULA).lbdQuantity(DEFAULT_LBD_QUANTITY)
				.lbdTotal(DEFAULT_LBD_TOTAL).additionDeduction(DEFAULT_ADDITION_DEDUCTION)
				.calculatedYn(DEFAULT_CALCULATED_YN);
		return workEstimateItemLBD;
	}

	/**
	 * Create an entity for WorkEstimateItem. This is a static method, as tests for
	 * other entities might also need it, if they test an entity which requires the
	 * current entity.
	 *
	 * @param em the em
	 * @return the work estimate item
	 */
	public static WorkEstimateItem createWorkEstimateItemEntity(EntityManager em) {

		System.err.println("=============createWorkEstimateItemEntity================");
		WorkEstimateItem workEstimateItem = new WorkEstimateItem().subEstimateId(1L).catWorkSorItemId(1L).entryOrder(1)
				.itemCode("AAAAAAAAAA").description("AAAAAAAAAA").uomId(1L).baseRate(new BigDecimal(1))
				.finalRate(new BigDecimal(1)).quantity(new BigDecimal(1)).floorNumber(1).labourRate(new BigDecimal(1))
				.lbdPerformedYn(false).raPerformedYn(false);
		return workEstimateItem;
	}

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
		workEstimate = createWorkEstimateEntity(em);
		subEstimate = createSubEstimateEntity(em);
		workEstimateItem = createWorkEstimateItemEntity(em);
		workEstimate = workEstimateRepository.save(workEstimate);
		subEstimate = subEstimateRepository.save(subEstimate);
		workEstimateItem = workEstimateItemRepository.save(workEstimateItem);

	}

	/**
	 * Init the test.
	 */
	@BeforeMethod
	public void initTest() {
		log.info("==================================================================================");
		log.info("This is executed before each test - initTest");
		workEstimateItemLBD = createEntity(em);

	}

	/**
	 * Upload LB D work estimate id validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	@Transactional
	public void uploadLBD_WorkEstimateId_Validation_Test() throws Exception {
		log.info("---uploadLBD_WorkEstimateId_Validation Start----");
		MockMultipartFile fileInput = new MockMultipartFile("file", "LBD.xls", MediaType.APPLICATION_PDF_VALUE,
				"Welcome, Admin!".getBytes());
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);
		Long workEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimateId, subEstimate.getId(), workEstimateItem.getId()).file(fileInput)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isNotFound());
		log.info("---uploadLBD_WorkEstimateId_Validation_Test End----");
	}

	/**
	 * Upload LB D sub estimate id validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 2)
	@Transactional
	public void uploadLBD_SubEstimateId_Validation_Test() throws Exception {
		log.info("---uploadLBD_SubEstimateId_Validation_Test Start----");
		MockMultipartFile fileInput = new MockMultipartFile("file", "LBD.xls", MediaType.APPLICATION_PDF_VALUE,
				"Welcome, Admin!".getBytes());
		Long subEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimateId, workEstimateItem.getId()).file(fileInput)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isNotFound());
		log.info("---uploadLBD_SubEstimateId_Validation_Test End----");
	}

	/**
	 * Upload LB D work item id validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 3)
	@Transactional
	public void uploadLBD_WorkItemId_Validation_Test() throws Exception {
		log.info("---uploadLBD_WorkItemId_Validation_Test Start----");
		workEstimate = workEstimateRepository.save(workEstimate);
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimate = subEstimateRepository.save(subEstimate);
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItem = workEstimateItemRepository.save(workEstimateItem);
		workEstimateItemLBD.setLbdParticulars("TT");
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);

		MockMultipartFile fileInput = new MockMultipartFile("file", "LBD.xls", MediaType.APPLICATION_PDF_VALUE,
				"Welcome, Admin!".getBytes());
		Long workEstimateItemId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItemId).file(fileInput)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isNotFound());
		log.info("---uploadLBD_WorkItemId_Validation_Test End----");
	}

	/**
	 * Upload LB D file test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 4)
	@Transactional
	public void uploadLBD_File_Test() throws Exception {
		log.info("---uploadLBD_File_Test Start----");
		workEstimate = workEstimateRepository.save(workEstimate);
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimate = subEstimateRepository.save(subEstimate);
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItem = workEstimateItemRepository.save(workEstimateItem);
		workEstimateItemLBD.setLbdParticulars("TT");
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
		log.info("---uploadLBD_File_Test End----");
	}

	/**
	 * Upload LB D file blabk validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 5)
	@Transactional
	public void uploadLBD_File_blabk_Validation_Test() throws Exception {
		log.info("---uploadLBD_File_blabk_Validation_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/BlankLBD.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isNotFound());
		log.info("---uploadLBD_File_blabk_Validation_Test End----");
	}

	/**
	 * Upload LB D file incorrect validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 6)
	@Transactional
	public void uploadLBD_File_incorrect_Validation_Test() throws Exception {
		log.info("---uploadLBD_File_incorrect_Validation_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/IncorrectLBD.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_incorrect_Validation_Test End----");
	}

	/**
	 * Upload LB D file header validation for particular test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 7)
	@Transactional
	public void uploadLBD_File_Header_Validation_for_particular_Test() throws Exception {
		log.info("---uploadLBD_File_Header_Validation_for_particular_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_particular.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Header_Validation_for_particular_Test End----");
	}

	/**
	 * Upload LB D file header validation for no test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 8)
	@Transactional
	public void uploadLBD_File_Header_Validation_for_no_Test() throws Exception {
		log.info("---uploadLBD_File_Header_Validation_for_no_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_No.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Header_Validation_for_no_Test End----");
	}

	/**
	 * Upload LB D file header validation for value test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 9)
	@Transactional
	public void uploadLBD_File_Header_Validation_for_value_Test() throws Exception {
		log.info("---uploadLBD_File_Header_Validation_for_value_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_value.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Header_Validation_for_value_Test End----");
	}

	/**
	 * Upload LB D file header validation for lbd test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 10)
	@Transactional
	public void uploadLBD_File_Header_Validation_for_lbd_Test() throws Exception {
		log.info("---uploadLBD_File_Header_Validation_for_lbd_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_LBD.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Header_Validation_for_lbd_Test End----");
	}

	/**
	 * Upload LB D file header validation for lenght test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 11)
	@Transactional
	public void uploadLBD_File_Header_Validation_for_lenght_Test() throws Exception {
		log.info("---uploadLBD_File_Header_Validation_for_lenght_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_lenght.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Header_Validation_for_lenght_Test End----");
	}

	/**
	 * Upload LB D file header validation for breath test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 12)
	@Transactional
	public void uploadLBD_File_Header_Validation_for_breath_Test() throws Exception {
		log.info("---uploadLBD_File_Header_Validation_for_breath_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_breath.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Header_Validation_for_breath_Test End----");
	}

	/**
	 * Upload LB D file header validation for depth test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 13)
	@Transactional
	public void uploadLBD_File_Header_Validation_for_depth_Test() throws Exception {
		log.info("---uploadLBD_File_Header_Validation_for_depth_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_depth.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Header_Validation_for_depth_Test End----");
	}

	/**
	 * Upload LB D file header validation for addition test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 14)
	@Transactional
	public void uploadLBD_File_Header_Validation_for_addition_Test() throws Exception {
		log.info("---uploadLBD_File_Header_Validation_for_addition_Test Start----");
		File f = new File(
				"../estimate-service/src/test/resources/templates/LBD_Template_incorrect_additiondeduction.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Header_Validation_for_addition_Test End----");
	}

	/**
	 * Upload LB D file validation for incorrect value name test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 15)
	@Transactional
	public void uploadLBD_File_Validation_for_incorrect_valueName_Test() throws Exception {
		log.info("---uploadLBD_File_Validation_for_incorrect_valueName_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_valueName.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Validation_for_incorrect_valueName_Test End----");
	}

	/**
	 * Upload LB D file validation for incorrect addition name test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 16)
	@Transactional
	public void uploadLBD_File_Validation_for_incorrect_additionName_Test() throws Exception {
		log.info("---uploadLBD_File_Validation_for_incorrect_additionName_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_additionName.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Validation_for_incorrect_additionName_Test End----");
	}

	/**
	 * Upload LB D file validation for incorrect value null test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 17)
	@Transactional
	public void uploadLBD_File_Validation_for_incorrect_valueNull_Test() throws Exception {
		log.info("---uploadLBD_File_Validation_for_incorrect_valueNull_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_valueNull.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Validation_for_incorrect_valueNull_Test End----");
	}

	/**
	 * Upload LB D file validation for incorrect numeric value test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 18)
	@Transactional
	public void uploadLBD_File_Validation_for_incorrect_NumericValue_Test() throws Exception {
		log.info("---uploadLBD_File_Validation_for_incorrect_NumericValue_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_NumericNo.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Validation_for_incorrect_NumericValue_Test End----");
	}

	/**
	 * Upload LB D file validation for incorrect calculated null test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 19)
	@Transactional
	public void uploadLBD_File_Validation_for_incorrect_calculatedNull_Test() throws Exception {
		log.info("---uploadLBD_File_Validation_for_incorrect_calculatedNull_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_calculatedNull.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Validation_for_incorrect_calculatedNull_Test End----");
	}

	/**
	 * Upload LB D file validation for incorrect direct null test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 20)
	@Transactional
	public void uploadLBD_File_Validation_for_incorrect_directNull_Test() throws Exception {
		log.info("---uploadLBD_File_Validation_for_incorrect_directNull_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_directNull.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Validation_for_incorrect_directNull_Test End----");
	}

	/**
	 * Upload LB D file validation for incorrect direct numeric test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 21)
	@Transactional
	public void uploadLBD_File_Validation_for_incorrect_directNumeric_Test() throws Exception {
		log.info("---uploadLBD_File_Validation_for_incorrect_directNumeric_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_directNumeric.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Validation_for_incorrect_directNumeric_Test End----");
	}

	/**
	 * Upload LB D file validation for incorrect addition null test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 22)
	@Transactional
	public void uploadLBD_File_Validation_for_incorrect_additionNull_Test() throws Exception {
		log.info("---uploadLBD_File_Validation_for_incorrect_additionNull_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/LBD_Template_incorrect_additionNull.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Validation_for_incorrect_additionNull_Test End----");
	}

	/**
	 * Upload LB D file blabk validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 23)
	@Transactional
	public void uploadLBD_File_Custom_Validation_Test() throws Exception {
		log.info("---uploadLBD_File_Custom_Validation_Test Start----");
		File f = new File("../estimate-service/src/test/resources/templates/CustomLBD.xls");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile file = new MockMultipartFile("file", f.getName(), "multipart/form-data", fi1);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(file)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_File_Custom_Validation_Test End----");
	}

	/**
	 * Save uploaded LB D test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 24)
	public void saveUploadedLBD_Test() throws Exception {
		log.info("---saveUploadedLBD_Test Start----");
		List<WorkEstimateItemLBDDTO> lbdDTOList = new ArrayList<>();
		workEstimate = workEstimateRepository.save(workEstimate);
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimate = subEstimateRepository.save(subEstimate);
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItem = workEstimateItemRepository.save(workEstimateItem);
		workEstimateItemLBD.setLbdParticulars("TT");
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);
		WorkEstimateItemLBDDTO dto = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		lbdDTOList.add(dto);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.post(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/save-uploaded-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lbdDTOList)))
				.andExpect(status().isCreated());
		log.info("---saveUploadedLBD_Test End----");
	}

	/**
	 * Save uploaded LB D LBD total validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 25)
	public void saveUploadedLBD_LBDTotal_validation_Test() throws Exception {
		log.info("---saveUploadedLBD_LBDTotal_validation_Test Start----");
		List<WorkEstimateItemLBDDTO> lbdDTOList = new ArrayList<>();

		WorkEstimate workEstimate2 = createWorkEstimateEntity(em);
		workEstimate2.setWorkEstimateNumber("QQ");
		workEstimate2 = workEstimateRepository.save(workEstimate2);

		SubEstimate subEstimate2 = createSubEstimateEntity(em);
		subEstimate2.setWorkEstimateId(workEstimate2.getId());
		subEstimate2 = subEstimateRepository.save(subEstimate2);

		WorkEstimateItem workEstimateItem2 = createWorkEstimateItemEntity(em);
		workEstimateItem2.setSubEstimateId(subEstimate2.getId());
		workEstimateItem2.setFinalRate(new BigDecimal(55555.5555));
		workEstimateItem2 = workEstimateItemRepository.save(workEstimateItem2);

		workEstimateItemLBD.setLbdParticulars("pp");
		workEstimateItemLBD.setAdditionDeduction(LBDOperation.DEDUCTION);
		workEstimateItemLBD.setLbdTotal(new BigDecimal(100));
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem2.getId());
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);

		WorkEstimateItemLBDDTO dto = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		lbdDTOList.add(dto);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.post(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/save-uploaded-lbds",
				workEstimate2.getId(), subEstimate2.getId(), workEstimateItem2.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lbdDTOList)))
				.andExpect(status().isBadRequest());
		log.info("---saveUploadedLBD_LBDTotal_validation_Test End----");
	}

	/**
	 * Save uploaded LB D LBD total 1 validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 26)
	public void saveUploadedLBD_LBDTotal_exceedsValue_validation_Test() throws Exception {
		log.info("---saveUploadedLBD_LBDTotal_exceedsValue_validation_Test Start----");
		List<WorkEstimateItemLBDDTO> lbdDTOList = new ArrayList<>();

		WorkEstimate workEstimate3 = createWorkEstimateEntity(em);
		workEstimate3.setWorkEstimateNumber("ww");
		workEstimate3 = workEstimateRepository.save(workEstimate3);

		SubEstimate subEstimate3 = createSubEstimateEntity(em);
		subEstimate3.setWorkEstimateId(workEstimate3.getId());
		subEstimate3 = subEstimateRepository.save(subEstimate3);

		WorkEstimateItem workEstimateItem3 = createWorkEstimateItemEntity(em);
		workEstimateItem3.setSubEstimateId(subEstimate3.getId());
		workEstimateItem3.setFinalRate(new BigDecimal(1234567890123.5555));
		workEstimateItem3 = workEstimateItemRepository.save(workEstimateItem3);

		workEstimateItemLBD.setLbdParticulars("pp");
		workEstimateItemLBD.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBD.setLbdTotal(new BigDecimal(5));
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem3.getId());
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);

		WorkEstimateItemLBDDTO dto = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		lbdDTOList.add(dto);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.post(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/save-uploaded-lbds",
				workEstimate3.getId(), subEstimate3.getId(), workEstimateItem3.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lbdDTOList)))
				.andExpect(status().isBadRequest());
		log.info("---saveUploadedLBD_LBDTotal_exceedsValue_validation_Test End----");
	}

	/**
	 * Save uploaded LB D LBD total exceeds quantity validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 27)
	public void saveUploadedLBD_LBDTotal_exceedsQuantity_validation_Test() throws Exception {
		log.info("---saveUploadedLBD_LBDTotal_exceedsQuantity_validation_Test Start----");
		List<WorkEstimateItemLBDDTO> lbdDTOList = new ArrayList<>();

		WorkEstimate workEstimate4 = createWorkEstimateEntity(em);
		workEstimate4.setWorkEstimateNumber("wrrw");
		workEstimate4 = workEstimateRepository.save(workEstimate4);

		SubEstimate subEstimate4 = createSubEstimateEntity(em);
		subEstimate4.setWorkEstimateId(workEstimate4.getId());
		subEstimate4 = subEstimateRepository.save(subEstimate4);

		WorkEstimateItem workEstimateItem4 = createWorkEstimateItemEntity(em);
		workEstimateItem4.setSubEstimateId(subEstimate4.getId());
		workEstimateItem4.setFinalRate(new BigDecimal(1234567890123.5555));
		workEstimateItem4 = workEstimateItemRepository.save(workEstimateItem4);

		workEstimateItemLBD.setLbdParticulars("pp");
		workEstimateItemLBD.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBD.setLbdTotal(new BigDecimal(999999999999.9999));
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem4.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);

		WorkEstimateItemLBDDTO dto = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		lbdDTOList.add(dto);
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.post(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/save-uploaded-lbds",
				workEstimate4.getId(), subEstimate4.getId(), workEstimateItem4.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lbdDTOList)))
				.andExpect(status().isBadRequest());
		log.info("---saveUploadedLBD_LBDTotal_exceedsQuantity_validation_Test End----");
	}

	/**
	 * Save uploaded LB D work estimate id validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 28)
	public void saveUploadedLBD_WorkEstimateId_Validation_Test() throws Exception {
		log.info("---saveUploadedLBD_WorkEstimateId_Validation_Test Start----");
		List<WorkEstimateItemLBDDTO> lbdDTOList = new ArrayList<>();
		Long workEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.post(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/save-uploaded-lbds",
				workEstimateId, subEstimate.getId(), workEstimateItem.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(lbdDTOList))).andExpect(status().isNotFound());
		log.info("---saveUploadedLBD_WorkEstimateId_Validation_Test End----");
	}

	/**
	 * Save uploaded LB D sub estimate id validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 29)
	@Transactional
	public void saveUploadedLBD_SubEstimateId_Validation_Test() throws Exception {
		log.info("---saveUploadedLBD_SubEstimateId_Validation_Test Start----");
		List<WorkEstimateItemLBDDTO> lbdDTOList = new ArrayList<>();
		Long subEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.post(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/save-uploaded-lbds",
				workEstimate.getId(), subEstimateId, workEstimateItem.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(lbdDTOList))).andExpect(status().isNotFound());
		log.info("---saveUploadedLBD_SubEstimateId_Validation_Test End----");
	}

	/**
	 * Save uploaded LB D work item id validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 30)
	@Transactional
	public void saveUploadedLBD_WorkItemId_Validation_Test() throws Exception {
		log.info("---saveUploadedLBD_WorkItemId_Validation_Test Start----");
		List<WorkEstimateItemLBDDTO> lbdDTOList = new ArrayList<>();
		Long itemId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.post(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/save-uploaded-lbds",
				workEstimate.getId(), subEstimate.getId(), itemId).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(lbdDTOList))).andExpect(status().isNotFound());
		log.info("---saveUploadedLBD_WorkItemId_Validation_Test End----");
	}

	/**
	 * Upload LB D status validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 31)
	@Transactional
	public void uploadLBD_Status_Validation_Test() throws Exception {
		log.info("---uploadLBD_Status_Validation_Test Start----");
		workEstimate = workEstimateRepository.findAll().get(0);
		workEstimate.setWorkEstimateNumber("AAA");
		workEstimate.setStatus(WorkEstimateStatus.GRANT_ALLOCATED);
		workEstimateRepository.saveAndFlush(workEstimate);
		MockMultipartFile fileInput = new MockMultipartFile("file", "LBD.xls", MediaType.APPLICATION_PDF_VALUE,
				"Welcome, Admin!".getBytes());
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.multipart(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()).file(fileInput)
				.queryParam("itemCode", workEstimateItem.getItemCode()).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isBadRequest());
		log.info("---uploadLBD_Status_Validation_Test End----");
	}

	/**
	 * Save uploaded LB D status validation test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 32)
	@Transactional
	public void saveUploadedLBD_Status_Validation_Test() throws Exception {
		log.info("---saveUploadedLBD_Status_Validation_Test Start----");
		workEstimate = workEstimateRepository.findAll().get(0);
		workEstimate.setWorkEstimateNumber("BBB");
		workEstimate.setStatus(WorkEstimateStatus.GRANT_ALLOCATED);
		workEstimateRepository.saveAndFlush(workEstimate);
		List<WorkEstimateItemLBDDTO> lbdDTOList = new ArrayList<>();
		restWorkEstimateItemLBDMockMvc.perform(MockMvcRequestBuilders.post(
				"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/save-uploaded-lbds",
				workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lbdDTOList)))
				.andExpect(status().isBadRequest());
		log.info("---saveUploadedLBD_Status_Validation_Test End----");
	}

	/**
	 * Creates the work estimate item LBD.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 33)
	@Transactional
	void createWorkEstimateItemLBD() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkEstimateItemLBD - Start");

		int databaseSizeBeforeCreate = workEstimateItemLBDRepository.findAll().size();

		WorkEstimate workEstimate1 = new WorkEstimate();
		workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("ede");
		workEstimate1 = workEstimateRepository.save(workEstimate1);

		SubEstimate subEstimate2 = new SubEstimate();
		subEstimate2 = createSubEstimateEntity(em);
		subEstimate2.setWorkEstimateId(workEstimate1.getId());
		subEstimate2 = subEstimateRepository.save(subEstimate2);

		WorkEstimateItem workEstimateItem2 = new WorkEstimateItem();
		workEstimateItem2 = createWorkEstimateItemEntity(em);
		workEstimateItem2.setSubEstimateId(subEstimate2.getId());
		workEstimateItem2 = workEstimateItemRepository.save(workEstimateItem2);

		// Create the WorkEstimateItemLBD
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem2.getId());
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc
				.perform(post("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd",
						workEstimate1.getId(), subEstimate2.getId(), workEstimateItem2.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isCreated());
		// Validate the WorkEstimateItemLBD in the database
		List<WorkEstimateItemLBD> workEstimateItemLBDList = workEstimateItemLBDRepository.findAll();
		assertThat(workEstimateItemLBDList).hasSize(databaseSizeBeforeCreate + 1);
		log.info("Test - createWorkEstimateItemLBD - Success !!");

	}

	/**
	 * Creates the work estimate item LB D LBD validation test.
	 *
	 * @throws Exception the exception
	 */
	// -------
	@Test(priority = 34)
	@Transactional
	void createWorkEstimateItemLBD_LBDValidation_Test() throws Exception {
		log.info("==========================================================================");
		log.info("Test - updateWorkEstimateItemLBD_LBDValidation_Test1 - Start");

		WorkEstimate workEstimate1 = new WorkEstimate();
		workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("TYU");
		workEstimate1 = workEstimateRepository.save(workEstimate1);

		SubEstimate subEstimate2 = new SubEstimate();
		subEstimate2 = createSubEstimateEntity(em);
		subEstimate2.setWorkEstimateId(workEstimate1.getId());
		subEstimate2 = subEstimateRepository.save(subEstimate2);

		WorkEstimateItem workEstimateItem2 = new WorkEstimateItem();
		workEstimateItem2 = createWorkEstimateItemEntity(em);
		workEstimateItem2.setSubEstimateId(subEstimate2.getId());
		workEstimateItem2 = workEstimateItemRepository.save(workEstimateItem2);

		// Create the WorkEstimateItemLBD
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem2.getId());
		workEstimateItemLBD.setLbdBredth(new BigDecimal(-1));
		workEstimateItemLBD.setLbdBredthFormula("*");
		workEstimateItemLBD.setLbdLengthFormula("*");
		workEstimateItemLBD.setLbdDepthFormula("*");
		workEstimateItemLBD.setCalculatedYn(true);
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc
				.perform(post("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd",
						workEstimate1.getId(), subEstimate2.getId(), workEstimateItem2.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isBadRequest());
		// Validate the WorkEstimateItemLBD in the database
		log.info("Test - updateWorkEstimateItemLBD_LBDValidation_Test1 - Success !!");

	}

	/**
	 * Creates the work estimate item LB D estimat id validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 35)
	@Transactional
	void createWorkEstimateItemLBD_EstimatId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkEstimateItemLBD_EstimatId_Validation - Start");
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long workEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(post("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd",
						workEstimateId, subEstimate.getId(), workEstimateItem.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - createWorkEstimateItemLBD_EstimatId_Validation - Success !!");
	}

	/**
	 * Creates the work estimate item LB D sub estimate id validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 36)
	@Transactional
	void createWorkEstimateItemLBD_SubEstimateId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkEstimateItemLBD_SubEstimateId_Validation - Start");
		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();
		workEstimate.setStatus(WorkEstimateStatus.DRAFT);
		workEstimateRepository.saveAndFlush(workEstimate);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long subEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(post("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd",
						workEstimate.getId(), subEstimateId, workEstimateItem.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - createWorkEstimateItemLBD_SubEstimateId_Validation - Success !!");
	}

	/**
	 * Creates the work estimate item LB D SOR item id validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 37)
	@Transactional
	void createWorkEstimateItemLBD_SORItemId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkEstimateItemLBD_SORItemId_Validation - Start");
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long workEstimateItemId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(post("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd",
						workEstimate.getId(), subEstimate.getId(), workEstimateItemId)
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - createWorkEstimateItemLBD_SORItemId_Validation - Success !!");
	}

	/**
	 * Update work estimate item LBD test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 39)
	@Transactional
	void updateWorkEstimateItemLBDTest() throws Exception {
		log.info("==========================================================================");
		log.info("Test - updateWorkEstimateItemLBDTest - Start");

		int databaseSizeBeforeCreate = workEstimateItemLBDRepository.findAll().size();

		WorkEstimate workEstimate1 = new WorkEstimate();
		workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("Abc");
		workEstimate1 = workEstimateRepository.save(workEstimate1);

		SubEstimate subEstimate2 = new SubEstimate();
		subEstimate2 = createSubEstimateEntity(em);
		subEstimate2.setWorkEstimateId(workEstimate1.getId());
		subEstimate2 = subEstimateRepository.save(subEstimate2);

		WorkEstimateItem workEstimateItem2 = new WorkEstimateItem();
		workEstimateItem2 = createWorkEstimateItemEntity(em);
		workEstimateItem2.setSubEstimateId(subEstimate2.getId());
		workEstimateItem2 = workEstimateItemRepository.save(workEstimateItem2);

		// Create the WorkEstimateItemLBD
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem2.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc
				.perform(put(
						"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate1.getId(), subEstimate2.getId(), workEstimateItem2.getId(),
						workEstimateItemLBD.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isOk());
		// Validate the WorkEstimateItemLBD in the database
		List<WorkEstimateItemLBD> workEstimateItemLBDList = workEstimateItemLBDRepository.findAll();
		assertThat(workEstimateItemLBDList).hasSize(databaseSizeBeforeCreate + 1);
		log.info("Test - updateWorkEstimateItemLBDTest - Success !!");

	}

	/**
	 * Update work estimate item LB D LBD validation test.
	 *
	 * @throws Exception the exception
	 */
//-----------------------
	@Test(priority = 39)
	@Transactional
	void updateWorkEstimateItemLBD_LBDValidation_Test() throws Exception {
		log.info("==========================================================================");
		log.info("Test - updateWorkEstimateItemLBD_LBDValidation_Test - Start");

		WorkEstimate workEstimate1 = new WorkEstimate();
		workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("BCD");
		workEstimate1 = workEstimateRepository.save(workEstimate1);

		SubEstimate subEstimate2 = new SubEstimate();
		subEstimate2 = createSubEstimateEntity(em);
		subEstimate2.setWorkEstimateId(workEstimate1.getId());
		subEstimate2 = subEstimateRepository.save(subEstimate2);

		WorkEstimateItem workEstimateItem2 = new WorkEstimateItem();
		workEstimateItem2 = createWorkEstimateItemEntity(em);
		workEstimateItem2.setSubEstimateId(subEstimate2.getId());
		workEstimateItem2 = workEstimateItemRepository.save(workEstimateItem2);

		// Create the WorkEstimateItemLBD
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem2.getId());
		workEstimateItemLBD.setLbdBredth(new BigDecimal(-1));
		workEstimateItemLBD.setLbdBredthFormula("0/0");
		workEstimateItemLBD.setLbdLengthFormula("0/0");
		workEstimateItemLBD.setLbdDepthFormula("0/0");
		workEstimateItemLBD.setCalculatedYn(true);
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc
				.perform(put(
						"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate1.getId(), subEstimate2.getId(), workEstimateItem2.getId(),
						workEstimateItemLBD.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isBadRequest());
		// Validate the WorkEstimateItemLBD in the database
		log.info("Test - updateWorkEstimateItemLBD_LBDValidation_Test - Success !!");

	}

	/**
	 * Update work estimate item LB D LBD validation test 1.
	 *
	 * @throws Exception the exception
	 */
	// ---------------------
	@Test(priority = 39)
	@Transactional
	void updateWorkEstimateItemLBD_LBDValidation_Test1() throws Exception {
		log.info("==========================================================================");
		log.info("Test - updateWorkEstimateItemLBD_LBDValidation_Test1 - Start");

		WorkEstimate workEstimate1 = new WorkEstimate();
		workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("SDf");
		workEstimate1 = workEstimateRepository.save(workEstimate1);

		SubEstimate subEstimate2 = new SubEstimate();
		subEstimate2 = createSubEstimateEntity(em);
		subEstimate2.setWorkEstimateId(workEstimate1.getId());
		subEstimate2 = subEstimateRepository.save(subEstimate2);

		WorkEstimateItem workEstimateItem2 = new WorkEstimateItem();
		workEstimateItem2 = createWorkEstimateItemEntity(em);
		workEstimateItem2.setSubEstimateId(subEstimate2.getId());
		workEstimateItem2 = workEstimateItemRepository.save(workEstimateItem2);

		// Create the WorkEstimateItemLBD
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem2.getId());
		workEstimateItemLBD.setLbdBredth(new BigDecimal(-1));
		workEstimateItemLBD.setLbdBredthFormula("*");
		workEstimateItemLBD.setLbdLengthFormula("*");
		workEstimateItemLBD.setLbdDepthFormula("*");
		workEstimateItemLBD.setCalculatedYn(true);
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc
				.perform(put(
						"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate1.getId(), subEstimate2.getId(), workEstimateItem2.getId(),
						workEstimateItemLBD.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isBadRequest());
		// Validate the WorkEstimateItemLBD in the database
		log.info("Test - updateWorkEstimateItemLBD_LBDValidation_Test1 - Success !!");

	}

	/**
	 * Update work estimate item LB D LBD validation test 2.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 39)
	@Transactional
	void updateWorkEstimateItemLBD_LBDValidation_Test2() throws Exception {
		log.info("==========================================================================");
		log.info("Test - updateWorkEstimateItemLBD_LBDValidation_Test2 - Start");

		WorkEstimate workEstimate1 = new WorkEstimate();
		workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("QWE");
		workEstimate1 = workEstimateRepository.save(workEstimate1);

		SubEstimate subEstimate2 = new SubEstimate();
		subEstimate2 = createSubEstimateEntity(em);
		subEstimate2.setWorkEstimateId(workEstimate1.getId());
		subEstimate2 = subEstimateRepository.save(subEstimate2);

		WorkEstimateItem workEstimateItem2 = new WorkEstimateItem();
		workEstimateItem2 = createWorkEstimateItemEntity(em);
		workEstimateItem2.setSubEstimateId(subEstimate2.getId());
		workEstimateItem2 = workEstimateItemRepository.save(workEstimateItem2);

		// Create the WorkEstimateItemLBD
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem2.getId());
		workEstimateItemLBD.setLbdBredth(new BigDecimal(-1));
		workEstimateItemLBD.setLbdBredthFormula(null);
		workEstimateItemLBD.setLbdLengthFormula(null);
		workEstimateItemLBD.setLbdDepthFormula(null);
		workEstimateItemLBD.setLbdBredth(null);
		workEstimateItemLBD.setLbdLength(null);
		workEstimateItemLBD.setLbdDepth(null);
		workEstimateItemLBD.setCalculatedYn(true);
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc
				.perform(put(
						"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate1.getId(), subEstimate2.getId(), workEstimateItem2.getId(),
						workEstimateItemLBD.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isBadRequest());
		// Validate the WorkEstimateItemLBD in the database
		log.info("Test - updateWorkEstimateItemLBD_LBDValidation_Test2 - Success !!");

	}

	/**
	 * Update work estimate item LB D estimat id validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 40)
	@Transactional
	void updateWorkEstimateItemLBD_EstimatId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - updateWorkEstimateItemLBD_EstimatId_Validation - Start");
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		Long workEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(put(
						"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimateId, subEstimate.getId(), workEstimateItem.getId(), workEstimateItemLBD.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - updateWorkEstimateItemLBD_EstimatId_Validation - Success !!");
	}

	/**
	 * Update work estimate item LB D sub estimate id validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 41)
	@Transactional
	void updateWorkEstimateItemLBD_SubEstimateId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - updateWorkEstimateItemLBD_SubEstimateId_Validation - Start");
		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();
		workEstimate.setStatus(WorkEstimateStatus.DRAFT);
		workEstimateRepository.saveAndFlush(workEstimate);
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long subEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(put(
						"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate.getId(), subEstimateId, workEstimateItem.getId(), workEstimateItemLBD.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - updateWorkEstimateItemLBD_SubEstimateId_Validation - Success !!");
	}

	/**
	 * Update work estimate item LB D SOR item id validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 42)
	@Transactional
	void updateWorkEstimateItemLBD_SORItemId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - updateWorkEstimateItemLBD_SORItemId_Validation - Start");
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long workEstimateItemId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(put(
						"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate.getId(), subEstimate.getId(), workEstimateItemId, workEstimateItemLBD.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - updateWorkEstimateItemLBD_SORItemId_Validation - Success !!");
	}

	/**
	 * Update work estimate item LB D work estimate item LBD id validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 43)
	@Transactional
	void updateWorkEstimateItemLBD_workEstimateItemLBDId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - updateWorkEstimateItemLBD_workEstimateItemLBDId_Validation - Start");
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long workEstimateItemLBDId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(put(
						"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateItemLBDId)
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - updateWorkEstimateItemLBD_workEstimateItemLBDId_Validation - Success !!");
	}

	/**
	 * Gets the all work estimate item LBD S test.
	 *
	 * @return the all work estimate item LBD S test
	 * @throws Exception the exception
	 */
	@Test(priority = 44)
	@Transactional
	public void getAllWorkEstimateItemLBDS_Test() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkEstimateItemLBDS_Test - Start");
		workEstimate = createWorkEstimateEntity(em);
		workEstimate.setWorkEstimateNumber("4444");
		workEstimate = workEstimateRepository.save(workEstimate);
		subEstimate = createSubEstimateEntity(em);
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimate = subEstimateRepository.save(subEstimate);
		workEstimateItem = createWorkEstimateItemEntity(em);
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItem = workEstimateItemRepository.save(workEstimateItem);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbds",
						workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isOk());
		log.info("Test - getAllWorkEstimateItemLBDS_Test - Success !!");
	}

	/**
	 * Gets the all work estimate item LBD S estimat id validation.
	 *
	 * @return the all work estimate item LBD S estimat id validation
	 * @throws Exception the exception
	 */
	@Test(priority = 45)
	@Transactional
	void getAllWorkEstimateItemLBDS_EstimatId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkEstimateItemLBDS_EstimatId_Validation - Start");
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		Long workEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbds",
						workEstimateId, subEstimate.getId(), workEstimateItem.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - getAllWorkEstimateItemLBDS_EstimatId_Validation - Success !!");
	}

	/**
	 * Gets the all work estimate item LBD S sub estimate id validation.
	 *
	 * @return the all work estimate item LBD S sub estimate id validation
	 * @throws Exception the exception
	 */
	@Test(priority = 46)
	@Transactional
	void getAllWorkEstimateItemLBDS_SubEstimateId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkEstimateItemLBDS_SubEstimateId_Validation - Start");
		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();
		workEstimate.setStatus(WorkEstimateStatus.DRAFT);
		workEstimateRepository.saveAndFlush(workEstimate);
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long subEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbds",
						workEstimate.getId(), subEstimateId, workEstimateItem.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - getAllWorkEstimateItemLBDS_SubEstimateId_Validation - Success !!");
	}

	/**
	 * Gets the all work estimate item LBD S SOR item id validation.
	 *
	 * @return the all work estimate item LBD S SOR item id validation
	 * @throws Exception the exception
	 */
	@Test(priority = 47)
	@Transactional
	void getAllWorkEstimateItemLBDS_SORItemId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkEstimateItemLBDS_SORItemId_Validation - Start");
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long workEstimateItemId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbds",
						workEstimate.getId(), subEstimate.getId(), workEstimateItemId)
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - getAllWorkEstimateItemLBDS_SORItemId_Validation - Success !!");
	}

	/**
	 * Gets the work estimate item LB D test.
	 *
	 * @return the work estimate item LB D test
	 * @throws Exception the exception
	 */
	@Test(priority = 48)
	@Transactional
	public void getWorkEstimateItemLBD_Test() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkEstimateItemLBD_Test - Start");
		workEstimate = createWorkEstimateEntity(em);
		workEstimate.setWorkEstimateNumber("44444");
		workEstimate = workEstimateRepository.save(workEstimate);
		subEstimate = createSubEstimateEntity(em);
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimate = subEstimateRepository.save(subEstimate);
		workEstimateItem = createWorkEstimateItemEntity(em);
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItem = workEstimateItemRepository.save(workEstimateItem);

		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc
				.perform(get(
						"/v1/api//work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(),
						workEstimateItemLBD.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isOk());
		log.info("Test - getWorkEstimateItemLBD_Test - Success !!");
	}

	/**
	 * Gets the work estimate item LB D estimat id validation.
	 *
	 * @return the work estimate item LB D estimat id validation
	 * @throws Exception the exception
	 */
	@Test(priority = 49)
	@Transactional
	void getWorkEstimateItemLBD_EstimatId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkEstimateItemLBD_EstimatId_Validation - Start");
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		Long workEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(get(
						"/v1/api//work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimateId, subEstimate.getId(), workEstimateItem.getId(), workEstimateItemLBD.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - getWorkEstimateItemLBD_EstimatId_Validation - Success !!");
	}

	/**
	 * Gets the work estimate item LB D sub estimate id validation.
	 *
	 * @return the work estimate item LB D sub estimate id validation
	 * @throws Exception the exception
	 */
	@Test(priority = 50)
	@Transactional
	void getWorkEstimateItemLBD_SubEstimateId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkEstimateItemLBD_SubEstimateId_Validation - Start");
		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();
		workEstimate.setStatus(WorkEstimateStatus.DRAFT);
		workEstimateRepository.saveAndFlush(workEstimate);
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long subEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(get(
						"/v1/api//work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate.getId(), subEstimateId, workEstimateItem.getId(), workEstimateItemLBD.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - getWorkEstimateItemLBD_SubEstimateId_Validation - Success !!");
	}

	/**
	 * Gets the work estimate item LB D SOR item id validation.
	 *
	 * @return the work estimate item LB D SOR item id validation
	 * @throws Exception the exception
	 */
	@Test(priority = 51)
	@Transactional
	void getWorkEstimateItemLBD_SORItemId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getAllWorkEstimateItemLBDS_SORItemId_Validation - Start");
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long workEstimateItemId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(get(
						"/v1/api//work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate.getId(), subEstimate.getId(), workEstimateItemId, workEstimateItemLBD.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - getAllWorkEstimateItemLBDS_SORItemId_Validation - Success !!");
	}

	/**
	 * Gets the work estimate item LB D work estimate item LBD id validation.
	 *
	 * @return the work estimate item LB D work estimate item LBD id validation
	 * @throws Exception the exception
	 */
	@Test(priority = 52)
	@Transactional
	void getWorkEstimateItemLBD_workEstimateItemLBDId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkEstimateItemLBD_workEstimateItemLBDId_Validation - Start");
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long workEstimateItemLBDId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(get(
						"/v1/api//work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateItemLBDId)
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - getWorkEstimateItemLBD_workEstimateItemLBDId_Validation - Success !!");
	}

	/**
	 * Delete work estimate item LB D test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 53)
	@Transactional
	public void deleteWorkEstimateItemLBD_Test() throws Exception {
		log.info("==========================================================================");
		log.info("Test - getWorkEstimateItemLBD_Test - Start");
		workEstimate = createWorkEstimateEntity(em);
		workEstimate.setWorkEstimateNumber("44445");
		workEstimate = workEstimateRepository.save(workEstimate);

		subEstimate = createSubEstimateEntity(em);
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimate = subEstimateRepository.save(subEstimate);

		workEstimateItem = createWorkEstimateItemEntity(em);
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItem = workEstimateItemRepository.save(workEstimateItem);

		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc
				.perform(delete(
						"/v1/api//work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(),
						workEstimateItemLBD.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().is2xxSuccessful());
		log.info("Test - deleteWorkEstimateItemLBD_Test - Success !!");
	}

	/**
	 * Delete work estimate item LB D estimat id validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 54)
	@Transactional
	void deleteWorkEstimateItemLBD_EstimatId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateItemLBD_EstimatId_Validation - Start");
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		Long workEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(delete(
						"/v1/api//work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimateId, subEstimate.getId(), workEstimateItem.getId(), workEstimateItemLBD.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - deleteWorkEstimateItemLBD_EstimatId_Validation - Success !!");
	}

	/**
	 * Delete work estimate item LB D sub estimate id validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 55)
	@Transactional
	void deleteWorkEstimateItemLBD_SubEstimateId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateItemLBD_SubEstimateId_Validation - Start");
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long subEstimateId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(delete(
						"/v1/api//work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate.getId(), subEstimateId, workEstimateItem.getId(), workEstimateItemLBD.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - deleteWorkEstimateItemLBD_SubEstimateId_Validation - Success !!");
	}

	/**
	 * Delete work estimate item LB D SOR item id validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 56)
	@Transactional
	void deleteWorkEstimateItemLBD_SORItemId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateItemLBD_SORItemId_Validation - Start");
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long workEstimateItemId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(delete(
						"/v1/api//work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate.getId(), subEstimate.getId(), workEstimateItemId, workEstimateItemLBD.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - deleteWorkEstimateItemLBD_SORItemId_Validation - Success !!");
	}

	/**
	 * Delete work estimate item LB D work estimate item LBD id validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 57)
	@Transactional
	void deleteWorkEstimateItemLBD_workEstimateItemLBDId_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateItemLBD_workEstimateItemLBDId_Validation - Start");
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		Long workEstimateItemLBDId = Long.MAX_VALUE;
		restWorkEstimateItemLBDMockMvc
				.perform(delete(
						"/v1/api//work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateItemLBDId)
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isNotFound());
		log.info("Test - deleteWorkEstimateItemLBD_workEstimateItemLBDId_Validation - Success !!");
	}

	// ----- Search & Copy

	/**
	 * Search item within sub estimate.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 60)
	@Transactional
	public void searchItemWithinSubEstimate() throws Exception {
		log.info("---searchItemWithinSubEstimate Start----");

		workEstimate = createWorkEstimateEntity(em);
		workEstimate.setWorkEstimateNumber("44446");
		workEstimate = workEstimateRepository.save(workEstimate);

		subEstimate = createSubEstimateEntity(em);
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimate = subEstimateRepository.save(subEstimate);

		workEstimateItem = createWorkEstimateItemEntity(em);
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItem = workEstimateItemRepository.save(workEstimateItem);

		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBD = workEstimateItemLBDRepository.save(workEstimateItemLBD);

		List<WorkEstimateItemDTO> itemList = new ArrayList<>();
		WorkEstimateItemDTO itemDto = workEstimateItemMapper.toDto(workEstimateItem);
		itemList.add(itemDto);

		restWorkEstimateItemLBDMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/item/{itemId}/search",
						workEstimate.getId(), subEstimate.getId(), itemDto.getId())
								.queryParam("item-code", itemDto.getItemCode()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		log.info("---searchItemWithinSubEstimate End----");
	}

	/**
	 * Searchinvalid work estimate id.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 61)
	@Transactional
	public void searchinvalidWorkEstimateId() throws Exception {
		log.info("---searchinvalidWorkEstimateId Start----");
		long workEstimateId = Long.MAX_VALUE;

		restWorkEstimateItemLBDMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/item/{itemId}/search",
						workEstimateId, subEstimate.getId(), workEstimateItem.getId()).queryParam("item-code", "Code")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		log.info("---searchinvalidWorkEstimateId End----");
	}

	/**
	 * Search invalid work sub estimate id.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 62)
	@Transactional
	public void searchInvalidWorkSubEstimateId() throws Exception {
		log.info("---searchInvalidWorkSubEstimateId Start----");
		Long subEstimateId = Long.MAX_VALUE;

		restWorkEstimateItemLBDMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/item/{itemId}/search",
						workEstimate.getId(), subEstimateId, workEstimateItem.getId())
								.queryParam("item-code", workEstimateItem.getItemCode())
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		log.info("---searchInvalidWorkSubEstimateId End----");
	}

	/**
	 * Search no item found.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 63)
	@Transactional
	public void searchNoItemFound() throws Exception {
		log.info("---searchNoItemFound Start----");
		String itemCode = null;

		WorkEstimate workEstimate4 = createWorkEstimateEntity(em);
		workEstimate4 = createWorkEstimateEntity(em);
		workEstimate4.setWorkEstimateNumber("FGH");
		workEstimate4 = workEstimateRepository.save(workEstimate4);

		SubEstimate subEstimate4 = createSubEstimateEntity(em);
		subEstimate4.setWorkEstimateId(workEstimate4.getId());
		subEstimate4 = subEstimateRepository.save(subEstimate4);

		WorkEstimateItem workEstimateItem4 = createWorkEstimateItemEntity(em);
		workEstimateItem4.setSubEstimateId(subEstimate.getId());
		workEstimateItem4 = workEstimateItemRepository.save(workEstimateItem4);

		restWorkEstimateItemLBDMockMvc
				.perform(get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/item/{itemId}/search",
						workEstimate4.getId(), subEstimate4.getId(), workEstimateItem4.getId())
								.queryParam("item-code", itemCode).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		log.info("---searchNoItemFound End----");
	}

	/**
	 * Copy item LBD within sub estimate.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 64)
	@Transactional
	public void copyItemLBDWithinSubEstimate() throws Exception {
		log.info("---copyItemLBDWithinSubEstimate Start----");
		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("AAAA6");
		workEstimate1 = workEstimateRepository.saveAndFlush(workEstimate1);

		SubEstimate subEstimate1 = createSubEstimateEntity(em);
		subEstimate1.setWorkEstimateId(workEstimate1.getId());
		subEstimate1 = subEstimateRepository.saveAndFlush(subEstimate1);

		WorkEstimateItem sourceItem = createWorkEstimateItemEntity(em);
		sourceItem.setSubEstimateId(subEstimate1.getId());
		sourceItem.setItemCode("aasdr");
		sourceItem = workEstimateItemRepository.save(sourceItem);

		WorkEstimateItemLBD sourceItemLBD = createEntity(em);
		sourceItemLBD.setWorkEstimateItemId(sourceItem.getId());
		sourceItemLBD = workEstimateItemLBDRepository.saveAndFlush(sourceItemLBD);

		WorkEstimateItem destinationItem = createWorkEstimateItemEntity(em);
		destinationItem.setSubEstimateId(subEstimate1.getId());
		destinationItem.setItemCode("aasdr");
		destinationItem = workEstimateItemRepository.save(destinationItem);

		restWorkEstimateItemLBDMockMvc.perform(
				get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/copy-lbds",
						workEstimate1.getId(), subEstimate1.getId(), destinationItem.getId())
								.queryParam("sourceItemId", String.valueOf(sourceItem.getId()))
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		List<WorkEstimateItem> workEstimateItemList = workEstimateItemRepository
				.findAllBySubEstimateIdAndCatWorkSorItemIdNotNullOrderByEntryOrderAsc(subEstimate.getId());

		Assert.assertEquals(workEstimateItemList.size(), 2L);

		List<WorkEstimateItemLBD> workEstimateItemLBDList = workEstimateItemLBDRepository
				.findAllByWorkEstimateItemId(destinationItem.getId());

		Assert.assertEquals(workEstimateItemLBDList.size(), 1L);
		log.info("---copyItemLBDWithinSubEstimate End----");
	}

	/**
	 * Copy invalid work estimate id item.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 65)
	@Transactional
	public void copyInvalidWorkEstimateIdItem() throws Exception {
		log.info("---copyInvalidWorkEstimateIdItem Start----");
		Long souceItemId = 1L;
		Long workEstimateId = Long.MAX_VALUE;
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc.perform(
				get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/copy-lbds",
						workEstimateId, subEstimate.getId(), workEstimateItem.getId())
								.queryParam("sourceItemId", String.valueOf(souceItemId))
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		log.info("---copyInvalidWorkEstimateIdItem End----");
	}

	/**
	 * Copy invalid work sub estimate id item.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 66)
	@Transactional
	public void copyInvalidWorkSubEstimateIdItem() throws Exception {
		log.info("---copyInvalidWorkSubEstimateIdItem Start----");
		Long souceItemId = 1L;
		Long subEstimateId = Long.MAX_VALUE;
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc.perform(
				get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/copy-lbds",
						workEstimate.getId(), subEstimateId, workEstimateItem.getId())
								.queryParam("sourceItemId", String.valueOf(souceItemId))
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		log.info("---copyInvalidWorkSubEstimateIdItem End----");
	}

	/**
	 * Copy invalid work estimate item id item.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 67)
	@Transactional
	public void copyInvalidWorkEstimateItemIdItem() throws Exception {
		log.info("---copyInvalidWorkEstimateItemIdItem Start----");
		Long souceItemId = 1L;
		Long workEstimateItemId = Long.MAX_VALUE;
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc.perform(
				get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/copy-lbds",
						workEstimate.getId(), subEstimate.getId(), workEstimateItemId)
								.queryParam("sourceItemId", String.valueOf(souceItemId))
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		log.info("---copyInvalidWorkEstimateItemIdItem End----");
	}

	/**
	 * Copy source item invalid.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 68)
	@Transactional
	public void copySourceItemInvalid() throws Exception {
		log.info("---copySourceItemInvalid Start----");

		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("CCCAAAD8");
		workEstimate1 = workEstimateRepository.saveAndFlush(workEstimate1);

		SubEstimate subEstimate1 = createSubEstimateEntity(em);
		subEstimate1.setWorkEstimateId(workEstimate1.getId());
		subEstimate1 = subEstimateRepository.saveAndFlush(subEstimate1);

		WorkEstimateItem source1 = createWorkEstimateItemEntity(em);
		source1.setSubEstimateId(subEstimate1.getId());
		source1.setItemCode("aasdr");
		source1 = workEstimateItemRepository.save(source1);

		WorkEstimateItem source2 = createWorkEstimateItemEntity(em);
		source2.setSubEstimateId(subEstimate1.getId());
		source2.setItemCode("aasdr");
		source2 = workEstimateItemRepository.save(source2);

//		Long souceItemId = 0l;
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc.perform(
				get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/copy-lbds",
						workEstimate1.getId(), subEstimate1.getId(), source1.getId())
								.queryParam("sourceItemId", String.valueOf(source2.getId()))
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		log.info("---copySourceItemInvalid End----");
	}

	/**
	 * Copy item not prepared.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 69)
	@Transactional
	public void copyItemNotPrepared() throws Exception {
		log.info("---copyItemNotPrepared Start----");

		WorkEstimate workEstimate1 = createWorkEstimateEntity(em);
		workEstimate1.setWorkEstimateNumber("CCCCC8");
		workEstimate1 = workEstimateRepository.saveAndFlush(workEstimate1);

		SubEstimate subEstimate1 = createSubEstimateEntity(em);
		subEstimate1.setWorkEstimateId(workEstimate1.getId());
		subEstimate1 = subEstimateRepository.saveAndFlush(subEstimate1);

		WorkEstimateItem wokrEstimateItem1 = createWorkEstimateItemEntity(em);
		wokrEstimateItem1.setSubEstimateId(subEstimate1.getId());
		wokrEstimateItem1.setItemCode("aasdr");
		wokrEstimateItem1 = workEstimateItemRepository.save(wokrEstimateItem1);
		Long souceItemId = 0L;
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc.perform(
				get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/copy-lbds",
						workEstimate1.getId(), subEstimate1.getId(), wokrEstimateItem1.getId())
								.queryParam("sourceItemId", String.valueOf(souceItemId))
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		log.info("---copyItemNotPrepared End----");
	}

	/**
	 * No permission copy item.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 70)
	@Transactional
	public void noPermissionCopyItem() throws Exception {
		log.info("---noPermissionCopyItem  Start----");
		Long souceItemId = 1L;
		workEstimate = workEstimateRepository.findAll().get(0);
		workEstimate.setWorkEstimateNumber("rrrrr");
		workEstimate.setStatus(WorkEstimateStatus.GRANT_ALLOCATED);
		workEstimateRepository.saveAndFlush(workEstimate);
		restWorkEstimateItemLBDMockMvc.perform(
				get("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/copy-lbds",
						workEstimate.getId(), subEstimate.getId(), workEstimateItemLBD.getWorkEstimateItemId())
								.queryParam("sourceItemId", String.valueOf(souceItemId))
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		log.info("---noPermissionCopyItem End----");
	}

	/**
	 * Delete work estimate item LB D status validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 71)
	@Transactional
	void deleteWorkEstimateItemLBD_Status_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - updateWorkEstimateItemLBD_Status_Validation - Start");
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();
		workEstimate.setStatus(WorkEstimateStatus.GRANT_ALLOCATED);
		workEstimateRepository.saveAndFlush(workEstimate);
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc
				.perform(delete(
						"/v1/api//work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(),
						workEstimateItemLBD.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isBadRequest());
		log.info("Test - updateWorkEstimateItemLBD_Status_Validation - Success !!");
	}

	/**
	 * Update work estimate item LB D status validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 72)
	@Transactional
	void updateWorkEstimateItemLBD_Status_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - updateWorkEstimateItemLBD_Status_Validation - Start");
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();
		workEstimate.setStatus(WorkEstimateStatus.GRANT_ALLOCATED);
		workEstimateRepository.saveAndFlush(workEstimate);
		workEstimateItemLBD.setWorkEstimateItemId(workEstimateItem.getId());
		workEstimateItemLBDRepository.save(workEstimateItemLBD);
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc
				.perform(put(
						"/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}",
						workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(),
						workEstimateItemLBD.getId()).contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isBadRequest());
		log.info("Test - updateWorkEstimateItemLBD_Status_Validation - Success !!");
	}

	/**
	 * Creates the work estimate item LB D status validation.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 73)
	@Transactional
	void createWorkEstimateItemLBD_Status_Validation() throws Exception {
		log.info("==========================================================================");
		log.info("Test - createWorkEstimateItemLBD_Status_Validation - Start");
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
		workEstimate = workEstimateRepository.findById(workEstimate.getId()).get();
		workEstimate.setStatus(WorkEstimateStatus.GRANT_ALLOCATED);
		workEstimateRepository.saveAndFlush(workEstimate);
		workEstimateItemLBDRepository.saveAndFlush(workEstimateItemLBD);
		restWorkEstimateItemLBDMockMvc
				.perform(post("/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd",
						workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestUtil.convertObjectToJsonBytes(workEstimateItemLBDDTO)))
				.andExpect(status().isBadRequest());
		log.info("Test - createWorkEstimateItemLBD_Status_Validation - Success !!");
	}

	/**
	 * Download object test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 74)
	@Transactional
	void downloadObject_Test() throws Exception {
		log.info("=================== downloadObject_Test Start !! =================");
		restWorkEstimateItemLBDMockMvc.perform(get("/v1/api/work-estimate/download-lbd-template", workEstimate.getId(),
				subEstimate.getId(), workEstimateItem.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		log.info("=================== downloadObject_Test Success !! =================");
	}

}
