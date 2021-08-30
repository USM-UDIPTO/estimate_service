package com.dxc.eproc.estimate.integration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.client.sor.dto.AreaWeightageMasterDTO;
import com.dxc.eproc.client.sor.dto.FinancialYearsDTO;
import com.dxc.eproc.client.sor.dto.MaterialChargesDTO;
import com.dxc.eproc.client.sor.dto.MaterialMasterDetailsDTO;
import com.dxc.eproc.client.sor.dto.MaterialMasterLoadUnloadDetailsDTO;
import com.dxc.eproc.client.sor.dto.MaterialMasterRoyaltyChargesDetailsDTO;
import com.dxc.eproc.client.sor.dto.NotesMasterDTO;
import com.dxc.eproc.client.sor.dto.SorCategoryDTO;
import com.dxc.eproc.client.sor.dto.WorkItemDTO;
import com.dxc.eproc.client.user.dto.DepartmentDTO;
import com.dxc.eproc.client.user.dto.ResponseDTODepartmentDTO;
import com.dxc.eproc.estimate.IntegrationTest;
import com.dxc.eproc.estimate.config.WireMockConfig;
import com.dxc.eproc.estimate.enumeration.RaChargeType;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.enumeration.WorkType;
import com.dxc.eproc.estimate.mocks.SorServiceMock;
import com.dxc.eproc.estimate.mocks.UserServiceMock;
import com.dxc.eproc.estimate.repository.WorkEstimateAdditionalChargesRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateLeadChargesRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateLiftChargesRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateLoadUnloadChargesRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateMarketRateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateOtherAddnLiftChargesRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRateAnalysisRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRoyaltyChargesRepository;
import com.dxc.eproc.estimate.service.RaConfigurationService;
import com.dxc.eproc.estimate.service.RaFormulaService;
import com.dxc.eproc.estimate.service.RaParametersService;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateAdditionalChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateItemService;
import com.dxc.eproc.estimate.service.WorkEstimateLeadChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateLiftChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateLoadUnloadChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateMarketRateService;
import com.dxc.eproc.estimate.service.WorkEstimateOtherAddnLiftChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateRateAnalysisService;
import com.dxc.eproc.estimate.service.WorkEstimateRoyaltyChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.WorkTypeService;
import com.dxc.eproc.estimate.service.dto.RaConfigurationDTO;
import com.dxc.eproc.estimate.service.dto.RaFormulaDTO;
import com.dxc.eproc.estimate.service.dto.RaParametersDTO;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateAdditionalChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLeadChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLiftChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLoadUnloadChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateMarketRateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOtherAddnLiftChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRateAnalysisDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRoyaltyChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkTypeDTO;
import com.github.tomakehurst.wiremock.WireMockServer;

// TODO: Auto-generated Javadoc
/**
 * @author Udipta USM
 *
 *         The Class RateAnalysisControllerIT.
 */
@IntegrationTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@ContextConfiguration(classes = { WireMockConfig.class })
public class RateAnalysisControllerIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupOverheadResourceIT.class);

	private final static Long CIRCLE_ID = 1L;

	// RaParams starts
	private static Long BASIC_RATE;
	private static Long AREA_WEIGHTAGE;
	private static Long LIFT_RATE;
	private static Long MATERIAL_LEVEL_LEAD_RATE;
	private static Long MATERIAL_LEVEL_ROYALTY_RATE;
	private static Long MATERIAL_LEVEL_LOADING_CHARGES;
	private static Long MATERIAL_RATE;
	private static Long MATERIAL_QUANTITY;
	private static Long ADDITIONAL_LIFT_RATE;
	private static Long ADDITIONAL_LEAD_RATE;
	private static Long MATERIAL_LEVEL_UNLOADING_CHARGES;
	private static Long ADDITIONAL_LIFT_PERCENTAGE_RATE;
	private static Long OTHER_CHARGES;
	private static Long CONTRACTOR_PROFIT;
	private static Long OVERHEAD_PERCENTAGE;
	private static Long TAX;
	private static Long ITEM_LEVEL_ROYALTY_RATE;
	private static Long ADDITIONAL_CHARGES;
	private static Long ITEM_LEVEL_LEAD_RATE;
	private static Long ITEM_LEVEL_LOADING_CHARGES;
	private static Long ITEM_LEVEL_UNLOADING_CHARGES;
	private static Long LOCALITY_ALLOWANCE;
	private static Long EMPLOYEES_COST;
	private static Long CONTINGENCIES;
	private static Long TRANSPORTATION_COST;
	private static Long SERVICE_TAX;
	private static Long PROVIDENT_FUND_CHARGES;
	private static Long ESI_CHARGES;
	private static Long IDC_CHARGES;
	private static Long WATCH_WARD_COST;
	private static Long INSURANCE_COST;
	private static Long STATUTORY_CHARGES;
	private static Long COMPENSATION_CHARGES;
	private static Long LABOUR_CHARGES;
	// RaParams ends

	/** The work estimate DTO. */
	private static WorkEstimateDTO workEstimateDTO;

	/** The sub estimate DTO. */
	private static SubEstimateDTO subEstimateDTO;

	/** The work estimate item DTO. */
	private static WorkEstimateItemDTO workEstimateItemDTO;

	/** The work type DTO. */
	private static WorkTypeDTO workTypeDTO;

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/** The sub estimate service. */
	@Autowired
	private SubEstimateService subEstimateService;

	/** The work estimate item service. */
	@Autowired
	private WorkEstimateItemService workEstimateItemService;

	/** The work type service. */
	@Autowired
	private WorkTypeService workTypeService;

	@Autowired
	private RaFormulaService raFormulaService;

	@Autowired
	private RaConfigurationService raConfigurationService;

	@Autowired
	private RaParametersService raParametersService;

	// WorkEstimate Ras starts
	@Autowired
	private WorkEstimateMarketRateService workEstimateMarketRateService;
	@Autowired
	private WorkEstimateMarketRateRepository workEstimateMarketRateRepository;

	@Autowired
	private WorkEstimateLeadChargesService workEstimateLeadChargesService;
	@Autowired
	private WorkEstimateLeadChargesRepository workEstimateLeadChargesRepository;

	@Autowired
	private WorkEstimateLiftChargesService workEstimateLiftChargesService;
	@Autowired
	private WorkEstimateLiftChargesRepository workEstimateLiftChargesRepository;

	@Autowired
	private WorkEstimateLoadUnloadChargesService workEstimateLoadUnloadChargesService;
	@Autowired
	private WorkEstimateLoadUnloadChargesRepository workEstimateLoadUnloadChargesRepository;

	@Autowired
	private WorkEstimateRoyaltyChargesService workEstimateRoyaltyChargesService;
	@Autowired
	private WorkEstimateRoyaltyChargesRepository workEstimateRoyaltyChargesRepository;

	@Autowired
	private WorkEstimateRateAnalysisService workEstimateRateAnalysisService;
	@Autowired
	private WorkEstimateRateAnalysisRepository workEstimateRateAnalysisRepository;

	@Autowired
	private WorkEstimateAdditionalChargesService workEstimateAdditionalChargesService;
	@Autowired
	private WorkEstimateAdditionalChargesRepository workEstimateAdditionalChargesRepository;

	@Autowired
	private WorkEstimateOtherAddnLiftChargesService workEstimateOtherAddnLiftChargesService;
	@Autowired
	private WorkEstimateOtherAddnLiftChargesRepository workEstimateOtherAddnLiftChargesRepository;
	// WorkEstimate Ras ends

	/** The wire mock server. */
	@Autowired
	private WireMockServer wireMockServer;

	/** The mvc. */
	@Autowired
	private MockMvc mvc;

	/**
	 * Inits the.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public void init() throws Exception {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");

		System.setProperty("spring.profiles.active", "test");

		workEstimateDTO = getWorkEstimate();
		workEstimateDTO = workEstimateService.save(workEstimateDTO);

		subEstimateDTO = new SubEstimateDTO();
		subEstimateDTO.setEstimateTotal(new BigDecimal(200));
		subEstimateDTO.setSubEstimateName("testN o");
		subEstimateDTO.setWorkEstimateId(workEstimateDTO.getId());
		subEstimateDTO = subEstimateService.save(subEstimateDTO);

		workEstimateItemDTO = getWorkEstimateItem();
		workEstimateItemDTO = workEstimateItemService.save(workEstimateItemDTO);

		workTypeDTO = getWorkType();
		workTypeDTO = workTypeService.save(workTypeDTO);

		workEstimateDTO = new WorkEstimateDTO();
		workEstimateDTO.setId(subEstimateDTO.getWorkEstimateId());
		workEstimateDTO.setWorkTypeId(workTypeDTO.getId());
		workEstimateDTO = workEstimateService.partialUpdate(workEstimateDTO).get();

		fetchRaParameterTable();

		fetchRaFormula();
	}

	private void fetchRaFormula() {
		RaFormulaDTO raFormulaDTO = getRaFormulaDTO();
		raFormulaService.save(raFormulaDTO);
		raFormulaDTO = getRaFormulaDTO();
		raFormulaDTO.setWorkType(WorkType.LABOURCONTRACTWORKS);
		raFormulaService.save(raFormulaDTO);
		raFormulaDTO = getRaFormulaDTO();
		raFormulaDTO.setWorkType(WorkType.TURNKEYWORKS);
		raFormulaService.save(raFormulaDTO);
	}

	private RaFormulaDTO getRaFormulaDTO() {
		RaFormulaDTO raFormulaDTO = new RaFormulaDTO();
		raFormulaDTO.setDeptId(CIRCLE_ID);
		raFormulaDTO.setFormula(
				"areaweightage + basicrate + differenceinmaterialcost + additionalliftcharges + othercharges + leadrate + liftrate + loadingunloadingcharges + royaltycharges + localityallowance + contractorsprofit + overheadcharges + employeescost + contingencies + transportationcost + servicecost + idccharges + esicharges + watchwardcost + insurancecost + providentfundcharges + statutorycost + compensationcharges + additionalcharges");
		raFormulaDTO.setAwFormula(
				"basicrate + materialrate + differenceinmaterialcost + additionalliftcharges + othercharges + weightage");
		raFormulaDTO.setRoyaltyFormula("prevailingroyaltyrate + quantity + densityfactor + srroyaltyrate");
		return raFormulaDTO;
	}

	private void fetchRaParameterTable() {
		BASIC_RATE = raParametersService
				.save(new RaParametersDTO("basic rate", "CatSorItemRateContract", "CatSorItemRateContract")).getId();
		AREA_WEIGHTAGE = raParametersService
				.save(new RaParametersDTO("area weightage", "AreaWeightageMaster", "AreaWeightageMaster")).getId();
		LIFT_RATE = raParametersService.save(new RaParametersDTO("lift rate", "LiftRateMaster", "LiftRateMaster"))
				.getId();
		MATERIAL_LEVEL_LEAD_RATE = raParametersService
				.save(new RaParametersDTO("material level lead rate", "LeadRateMaster", "LeadRateMaster")).getId();
		MATERIAL_LEVEL_ROYALTY_RATE = raParametersService
				.save(new RaParametersDTO("material level royalty rate", "RoylatyRateMaster", "RoylatyRateMaster"))
				.getId();
		MATERIAL_LEVEL_LOADING_CHARGES = raParametersService.save(
				new RaParametersDTO("material level loading charges", "LoadUnloadRateMaster", "LoadUnloadRateMaster"))
				.getId();
		MATERIAL_RATE = raParametersService
				.save(new RaParametersDTO("material rate", "MaterialMaster", "MaterialMaster")).getId();
		MATERIAL_QUANTITY = raParametersService
				.save(new RaParametersDTO("material quantity", "MaterialReqtMaterialMap", "MaterialReqtMaterialMap"))
				.getId();
		ADDITIONAL_LIFT_RATE = raParametersService
				.save(new RaParametersDTO("additional lift rate", "NotesMaster", "NotesMaster")).getId();
		ADDITIONAL_LEAD_RATE = raParametersService
				.save(new RaParametersDTO("additional lead rate", "NhwyLeadRateMaster", "NhwyLeadRateMaster")).getId();
		MATERIAL_LEVEL_UNLOADING_CHARGES = raParametersService.save(
				new RaParametersDTO("material level unloading charges", "LoadUnloadRateMaster", "LoadUnloadRateMaster"))
				.getId();
		ADDITIONAL_LIFT_PERCENTAGE_RATE = raParametersService
				.save(new RaParametersDTO("additional lift percentage rate", "NotesMaster", "NotesMaster")).getId();
		OTHER_CHARGES = raParametersService.save(new RaParametersDTO("other charges", "NotesMaster", "NotesMaster"))
				.getId();
		CONTRACTOR_PROFIT = raParametersService.save(new RaParametersDTO("contractor profit", "", "")).getId();
		OVERHEAD_PERCENTAGE = raParametersService.save(new RaParametersDTO("overhead percentage", "", "")).getId();
		TAX = raParametersService.save(new RaParametersDTO("tax", "", "")).getId();
		ITEM_LEVEL_ROYALTY_RATE = raParametersService.save(new RaParametersDTO("item level royalty rate", "", ""))
				.getId();
		ADDITIONAL_CHARGES = raParametersService.save(new RaParametersDTO("additional charges", "", "")).getId();
		ITEM_LEVEL_LEAD_RATE = raParametersService.save(new RaParametersDTO("item level lead rate", "", "")).getId();
		ITEM_LEVEL_LOADING_CHARGES = raParametersService.save(new RaParametersDTO("item level loading charges", "", ""))
				.getId();
		ITEM_LEVEL_UNLOADING_CHARGES = raParametersService
				.save(new RaParametersDTO("item level unloading charges", "", "")).getId();
		LOCALITY_ALLOWANCE = raParametersService.save(new RaParametersDTO("locality allowance", "", "")).getId();
		EMPLOYEES_COST = raParametersService.save(new RaParametersDTO("employees cost", "", "")).getId();
		CONTINGENCIES = raParametersService.save(new RaParametersDTO("contingencies", "", "")).getId();
		TRANSPORTATION_COST = raParametersService.save(new RaParametersDTO("transportation cost", "", "")).getId();
		SERVICE_TAX = raParametersService.save(new RaParametersDTO("service tax", "", "")).getId();
		PROVIDENT_FUND_CHARGES = raParametersService.save(new RaParametersDTO("provident fund charges", "", ""))
				.getId();
		ESI_CHARGES = raParametersService.save(new RaParametersDTO("esi charges", "", "")).getId();
		IDC_CHARGES = raParametersService.save(new RaParametersDTO("idc charges", "", "")).getId();
		WATCH_WARD_COST = raParametersService.save(new RaParametersDTO("watch ward cost", "", "")).getId();
		INSURANCE_COST = raParametersService.save(new RaParametersDTO("insurance cost", "", "")).getId();
		STATUTORY_CHARGES = raParametersService.save(new RaParametersDTO("statutory charges", "", "")).getId();
		COMPENSATION_CHARGES = raParametersService.save(new RaParametersDTO("compensation charges", "", "")).getId();
		LABOUR_CHARGES = raParametersService.save(new RaParametersDTO("labour charges", "", "")).getId();
	}

	/**
	 * Gets the work estimate.
	 *
	 * @return the work estimate
	 */
	private WorkEstimateDTO getWorkEstimate() {
		WorkEstimateDTO workEstimateDTO = new WorkEstimateDTO();
		workEstimateDTO.setWorkEstimateNumber("testEN o");
		workEstimateDTO.setStatus(WorkEstimateStatus.INITIAL);
		workEstimateDTO.setDeptId(1L);
		workEstimateDTO.setLocationId(1L);
		workEstimateDTO.setFileNumber("testFN o");
		workEstimateDTO.setName("testN o");
		workEstimateDTO.setEstimateTypeId(1L);
		workEstimateDTO.setWorkTypeId(1L);
		workEstimateDTO.setWorkCategoryId(1L);
		workEstimateDTO.setApprovedBudgetYn(true);
		return workEstimateDTO;
	}

	/**
	 * Gets the work estimate item.
	 *
	 * @return the work estimate item
	 */
	private WorkEstimateItemDTO getWorkEstimateItem() {
		WorkEstimateItemDTO workEstimateItemDTO = new WorkEstimateItemDTO();
		workEstimateItemDTO.setBaseRate(BigDecimal.TEN);
		workEstimateItemDTO.setCatWorkSorItemId(1L);
		workEstimateItemDTO.setSubEstimateId(subEstimateDTO.getId());
		workEstimateItemDTO.setUomId(1L);
		workEstimateItemDTO.setUomName("uom_test");
		workEstimateItemDTO.setItemCode("item_code_test");
		workEstimateItemDTO.setDescription("desc_test");
		workEstimateItemDTO.setFloorNumber(1);
		return workEstimateItemDTO;
	}

	/**
	 * Gets the work type.
	 *
	 * @return the work type
	 */
	private WorkTypeDTO getWorkType() {
		WorkTypeDTO workTypeDTO = new WorkTypeDTO();
		workTypeDTO.setValueType(WorkType.LABOURCONTRACTWORKS.toString());
		workTypeDTO.setWorkTypeName("name_test");
		workTypeDTO.setWorkTypeValue("value_Test");
		workTypeDTO.setActiveYn(true);
		return workTypeDTO;
	}

	private MaterialMasterDetailsDTO getMaterialMasterDetailsDTO() {
		MaterialMasterDetailsDTO materialDTO = new MaterialMasterDetailsDTO();
		materialDTO.setMaterialId(1L);
		materialDTO.setMaterialName("mat_name_test");
		materialDTO.setQuantity(BigDecimal.TEN);
		materialDTO.setRate(BigDecimal.TEN);
		materialDTO.setUomId(1L);
		materialDTO.setUomName("uom_name_test");
		return materialDTO;
	}

	private AreaWeightageMasterDTO getAreaWeightageMasterDTO() {
		AreaWeightageMasterDTO areaWeightageMasterDTO = new AreaWeightageMasterDTO();
		areaWeightageMasterDTO.setId(1L);
		areaWeightageMasterDTO.setWeightageValue(BigDecimal.TEN);
		return areaWeightageMasterDTO;
	}

	private MaterialMasterLoadUnloadDetailsDTO getMaterialMasterLoadUnloadDetailsDTO() {
		MaterialMasterLoadUnloadDetailsDTO ldUdDetailsDTO = new MaterialMasterLoadUnloadDetailsDTO();
		ldUdDetailsDTO.setMaterialId(1L);
		ldUdDetailsDTO.setMaterialName("mat_name_test");
		ldUdDetailsDTO.setQuantity(BigDecimal.TEN);
		ldUdDetailsDTO.setLoadingCharges(BigDecimal.ONE);
		ldUdDetailsDTO.setUnLoadingCharges(BigDecimal.ONE);
		return ldUdDetailsDTO;
	}

	private MaterialMasterRoyaltyChargesDetailsDTO getMaterialMasterRoyaltyChargesDetailsDTO() {
		MaterialMasterRoyaltyChargesDetailsDTO royaltyDetailsDTO = new MaterialMasterRoyaltyChargesDetailsDTO();
		royaltyDetailsDTO.setMaterialId(1L);
		royaltyDetailsDTO.setMaterialName("mat_name_test");
		royaltyDetailsDTO.setQuantity(BigDecimal.TEN);
		royaltyDetailsDTO.setUomId(1L);
		royaltyDetailsDTO.setUomName("uom_name_test");
		royaltyDetailsDTO.setRoyaltyCharges(BigDecimal.TEN);
		royaltyDetailsDTO.setDensityFactor(BigDecimal.ONE);
		return royaltyDetailsDTO;
	}

	private NotesMasterDTO getNotesMasterDTO() {
		NotesMasterDTO notesMasterDTO = new NotesMasterDTO();
		notesMasterDTO.setId(1L);
		notesMasterDTO.setRate(BigDecimal.valueOf(5));
		notesMasterDTO.setPercentageRate(BigDecimal.ONE);
		return notesMasterDTO;
	}

	private DepartmentDTO getDepartmentDTO() {
		DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDeptCode("MI");
		departmentDTO.setDeptName("DeptName_Test");
		departmentDTO.setParentId(CIRCLE_ID.intValue());
		return departmentDTO;
	}

	/**
	 * Sets the mocks.
	 *
	 * @throws Exception the exception
	 */
	private void setMocks() throws Exception {
		WorkItemDTO workItemDTO = new WorkItemDTO();
		workItemDTO.setInitialLead(BigDecimal.ONE);
		workItemDTO.setSorCategoryId(1L);
		SorServiceMock.setUpMockGetWorkItemUsingGET(wireMockServer, 1L, workItemDTO);

		SorCategoryDTO sorCategoryDTO = new SorCategoryDTO();
		sorCategoryDTO.setParentId(1L);
		sorCategoryDTO.setCircleId(CIRCLE_ID);
		SorServiceMock.setUpMockGetSorCategoryUsingGET(wireMockServer, workItemDTO.getSorCategoryId(), sorCategoryDTO);

		FinancialYearsDTO financialYearsDTO = new FinancialYearsDTO();
		financialYearsDTO.setYear("2021");
		SorServiceMock.setUpMockGetFinancialYearByCategoryUsingGET(wireMockServer, sorCategoryDTO.getParentId(),
				financialYearsDTO);

		MaterialMasterDetailsDTO materialMasterDetailsDTO = getMaterialMasterDetailsDTO();
		List<MaterialMasterDetailsDTO> materialMasterDetailsList = new ArrayList<>();
		materialMasterDetailsList.add(materialMasterDetailsDTO);
		SorServiceMock.setUpMockGetMaterialDetailsByWorkItemUsingGET(wireMockServer, 1L, materialMasterDetailsList);

		AreaWeightageMasterDTO areaWeightageMasterDTO = getAreaWeightageMasterDTO();
		SorServiceMock.setUpMockGetAreaWeightageMasterUsingGET(wireMockServer, areaWeightageMasterDTO.getId(),
				areaWeightageMasterDTO);

		List<AreaWeightageMasterDTO> areaWeightageMasterList = new ArrayList<>();
		areaWeightageMasterList.add(areaWeightageMasterDTO);
		SorServiceMock.setUpMockGetAreaWeightageMastersforCategoryUsingGET(wireMockServer, sorCategoryDTO.getParentId(),
				areaWeightageMasterList);

		SorServiceMock.setUpMockGetLeadOrLiftChargesForWorkItemUsingGET(wireMockServer, 1L,
				RaChargeType.LEAD_CHARGE.toString(), materialMasterDetailsList);
		SorServiceMock.setUpMockGetLeadOrLiftChargesForWorkItemUsingGET(wireMockServer, 1L,
				RaChargeType.LIFT_CHARGE.toString(), materialMasterDetailsList);

		MaterialChargesDTO materialChargesDTO = new MaterialChargesDTO();
		materialChargesDTO.setMaterialCharges(BigDecimal.ONE);
		SorServiceMock.setUpMockGetLeadChargesForMaterialUsingPOST(wireMockServer, 1L, materialChargesDTO);

		SorServiceMock.setUpMockGetLiftChargesForMaterialUsingGET(wireMockServer,
				materialMasterDetailsDTO.getMaterialId(), BigDecimal.ONE.setScale(2), materialChargesDTO);

		MaterialMasterLoadUnloadDetailsDTO materialMasterLoadUnloadDetailsDTO = getMaterialMasterLoadUnloadDetailsDTO();
		List<MaterialMasterLoadUnloadDetailsDTO> materialMasterLdUdDetailsDTOList = new ArrayList<>();
		materialMasterLdUdDetailsDTOList.add(materialMasterLoadUnloadDetailsDTO);
		SorServiceMock.setUpMockGetLoadUnloadforMaterialDetailsByWorkItemUsingGET(wireMockServer, 1L,
				materialMasterLdUdDetailsDTOList);

		MaterialMasterRoyaltyChargesDetailsDTO materialMasterRoyaltyChargesDetailsDTO = getMaterialMasterRoyaltyChargesDetailsDTO();
		List<MaterialMasterRoyaltyChargesDetailsDTO> materialMasterRoyaltyChargesDetailsList = new ArrayList<>();
		materialMasterRoyaltyChargesDetailsList.add(materialMasterRoyaltyChargesDetailsDTO);
		SorServiceMock.setUpMockGetRoyaltyforMaterialDetailsByWorkItemUsingGET(wireMockServer, 1L,
				materialMasterRoyaltyChargesDetailsList);

		NotesMasterDTO notesMasterDTO = getNotesMasterDTO();
		List<NotesMasterDTO> notesMasterDTOList = new ArrayList<>();
		notesMasterDTOList.add(notesMasterDTO);
		SorServiceMock.setUpMockGetNotesMasterByItemAndChargeTypeUsingGET(wireMockServer, 1L,
				RaChargeType.LIFT_CHARGES.toString(), notesMasterDTOList);
		SorServiceMock.setUpMockGetNotesMasterByItemAndChargeTypeUsingGET(wireMockServer, 1L,
				RaChargeType.OTHER_CHARGES.toString(), notesMasterDTOList);

		DepartmentDTO deptDTO = getDepartmentDTO();
		ResponseDTODepartmentDTO response = new ResponseDTODepartmentDTO();
		response.setResponseObject(deptDTO);
		UserServiceMock.setUpMockGetDepartmentUsingGET(wireMockServer, CIRCLE_ID.intValue(), response);
	}

	private Set<Long> saveRaConfiguration(List<Long> paramIds) {
		Set<Long> raConfigIds = paramIds.stream().map(p -> {
			RaConfigurationDTO raConfigurationDTO = new RaConfigurationDTO();
			raConfigurationDTO.setDeptId(CIRCLE_ID);
			raConfigurationDTO.setRaParamId(p);
			Long id = raConfigurationService.save(raConfigurationDTO).getId();
			return id;
		}).collect(Collectors.toSet());
		return raConfigIds;
	}

	@Test
	public void viewRateAnalysis_SuccessTest() throws Exception {

		setMocks();

		String uri = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/rate-analysis/view";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), subEstimateDTO.getId(),
				workEstimateItemDTO.getId())).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("viewRateAnalysis_SuccessTest successful!!");
	}

	@Test
	public void viewRateAnalysis_Success_LABOURCONTRACTWORKS_Test() throws Exception {

		setMocks();

		List<Long> paramIds = Arrays.asList(MATERIAL_LEVEL_LEAD_RATE, LIFT_RATE, LABOUR_CHARGES,
				MATERIAL_LEVEL_LOADING_CHARGES, MATERIAL_LEVEL_UNLOADING_CHARGES, MATERIAL_LEVEL_ROYALTY_RATE,
				ADDITIONAL_LIFT_RATE, OTHER_CHARGES, TAX, CONTRACTOR_PROFIT, OVERHEAD_PERCENTAGE, ADDITIONAL_CHARGES,
				LOCALITY_ALLOWANCE, EMPLOYEES_COST, CONTINGENCIES, TRANSPORTATION_COST, SERVICE_TAX,
				PROVIDENT_FUND_CHARGES, ESI_CHARGES);

		Set<Long> raConfigIds = saveRaConfiguration(paramIds);

		String uri = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/rate-analysis/view";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), subEstimateDTO.getId(),
				workEstimateItemDTO.getId())).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("viewRateAnalysis_SuccessTest successful!!");
		raConfigIds.forEach(raConfigurationService::delete);
	}

	@Test
	public void viewRateAnalysis_Success_TURNKEYWORKS_Test() throws Exception {
		WorkTypeDTO tempWorkTypeDTO = new WorkTypeDTO();
		tempWorkTypeDTO.setId(workTypeDTO.getId());
		tempWorkTypeDTO.setValueType(WorkType.TURNKEYWORKS.toString());
		workTypeService.partialUpdate(tempWorkTypeDTO);

		setMocks();

		List<Long> paramIds = Arrays.asList(LOCALITY_ALLOWANCE, IDC_CHARGES, WATCH_WARD_COST, INSURANCE_COST,
				STATUTORY_CHARGES, COMPENSATION_CHARGES);

		Set<Long> raConfigIds = saveRaConfiguration(paramIds);

		String uri = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/rate-analysis/view";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), subEstimateDTO.getId(),
				workEstimateItemDTO.getId())).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("viewRateAnalysis_SuccessTest successful!!");
		raConfigIds.forEach(raConfigurationService::delete);
		tempWorkTypeDTO.setValueType(WorkType.LABOURCONTRACTWORKS.toString());
		workTypeService.partialUpdate(tempWorkTypeDTO);
	}

	@Test
	public void viewRateAnalysis_Success_All_Saved_Without_AWFormula_Test() throws Exception {

		setMocks();

		WorkEstimateItemDTO workEstimateItemDTO = getWorkEstimateItem();
		workEstimateItemDTO.setLabourRate(BigDecimal.ONE);
		workEstimateItemDTO = workEstimateItemService.save(workEstimateItemDTO);

		saveAllRaEntities(workEstimateItemDTO.getId());

		List<Long> paramIds = Arrays.asList(AREA_WEIGHTAGE, MATERIAL_LEVEL_LEAD_RATE, LIFT_RATE, LABOUR_CHARGES,
				MATERIAL_LEVEL_LOADING_CHARGES, MATERIAL_LEVEL_UNLOADING_CHARGES, ITEM_LEVEL_ROYALTY_RATE,
				ADDITIONAL_LIFT_RATE, OTHER_CHARGES, TAX, CONTRACTOR_PROFIT, OVERHEAD_PERCENTAGE, ADDITIONAL_CHARGES,
				LOCALITY_ALLOWANCE, EMPLOYEES_COST, CONTINGENCIES, TRANSPORTATION_COST, SERVICE_TAX,
				PROVIDENT_FUND_CHARGES, ESI_CHARGES, IDC_CHARGES, WATCH_WARD_COST, INSURANCE_COST, STATUTORY_CHARGES,
				COMPENSATION_CHARGES);

		Set<Long> raConfigIds = saveRaConfiguration(paramIds);

		String uri = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/rate-analysis/view";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), subEstimateDTO.getId(),
				workEstimateItemDTO.getId())).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("viewRateAnalysis_SuccessTest successful!!");
		raConfigIds.forEach(raConfigurationService::delete);
		deleteAllRaEntities();
	}

	@Test
	public void viewRateAnalysis_Success_All_Saved_Test() throws Exception {

		saveAllRaEntities(workEstimateItemDTO.getId());
		setMocks();

		List<Long> paramIds = Arrays.asList(AREA_WEIGHTAGE, MATERIAL_LEVEL_LEAD_RATE, LIFT_RATE, LABOUR_CHARGES,
				MATERIAL_LEVEL_LOADING_CHARGES, MATERIAL_LEVEL_UNLOADING_CHARGES, ITEM_LEVEL_ROYALTY_RATE,
				ADDITIONAL_LIFT_RATE, OTHER_CHARGES, TAX, CONTRACTOR_PROFIT, OVERHEAD_PERCENTAGE, ADDITIONAL_CHARGES,
				LOCALITY_ALLOWANCE, EMPLOYEES_COST, CONTINGENCIES, TRANSPORTATION_COST, SERVICE_TAX,
				PROVIDENT_FUND_CHARGES, ESI_CHARGES, IDC_CHARGES, WATCH_WARD_COST, INSURANCE_COST, STATUTORY_CHARGES,
				COMPENSATION_CHARGES);

		Set<Long> raConfigIds = saveRaConfiguration(paramIds);

		String uri = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/rate-analysis/view";
		mvc.perform(MockMvcRequestBuilders.get(uri, workEstimateDTO.getId(), subEstimateDTO.getId(),
				workEstimateItemDTO.getId())).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("viewRateAnalysis_SuccessTest successful!!");
		raConfigIds.forEach(raConfigurationService::delete);
		deleteAllRaEntities();
	}

	private void deleteAllRaEntities() {
		workEstimateMarketRateRepository.deleteAll();
		workEstimateLeadChargesRepository.deleteAll();
		workEstimateLiftChargesRepository.deleteAll();
		workEstimateLoadUnloadChargesRepository.deleteAll();
		workEstimateRoyaltyChargesRepository.deleteAll();
		workEstimateRateAnalysisRepository.deleteAll();
		workEstimateAdditionalChargesRepository.deleteAll();
		workEstimateOtherAddnLiftChargesRepository.deleteAll();
	}

	private void saveAllRaEntities(Long workEstimateItemId) {
		workEstimateMarketRateService.save(getWeMarketRate(workEstimateItemId));
		workEstimateLeadChargesService.save(getWeLeadChargesDTO(workEstimateItemId));
		workEstimateLiftChargesService.save(getWeLiftChargesDTO(workEstimateItemId));
		workEstimateLoadUnloadChargesService.save(getWeLoadUnloadChargeDTO(workEstimateItemId));
		workEstimateRoyaltyChargesService.save(getRoyaltyChargesDTO(workEstimateItemId));
		workEstimateRateAnalysisService.save(getWeRateAnalysisDTO(workEstimateItemId));
		workEstimateAdditionalChargesService.save(getWeAdditionalCharges(workEstimateItemId));
		workEstimateOtherAddnLiftChargesService.save(getOtherAddnLiftCharges(workEstimateItemId));
		WorkEstimateOtherAddnLiftChargesDTO otherAddnLiftCharges = getOtherAddnLiftCharges(workEstimateItemId);
		otherAddnLiftCharges.setType(RaChargeType.LIFT_CHARGES);
		workEstimateOtherAddnLiftChargesService.save(otherAddnLiftCharges);
	}

	private WorkEstimateMarketRateDTO getWeMarketRate(Long workEstimateItemId) {
		MaterialMasterDetailsDTO material = getMaterialMasterDetailsDTO();
		WorkEstimateMarketRateDTO weMarketRateDTO = new WorkEstimateMarketRateDTO();
		weMarketRateDTO.setMaterialMasterId(material.getMaterialId());
		weMarketRateDTO.setMaterialName(material.getMaterialName());
		weMarketRateDTO.setQuantity(material.getQuantity());
		weMarketRateDTO.setRate(material.getRate());
		weMarketRateDTO.setPrevailingMarketRate(weMarketRateDTO.getRate().add(BigDecimal.TEN));
		weMarketRateDTO.setUomId(material.getUomId());
		weMarketRateDTO.setUomName(material.getUomName());
		weMarketRateDTO.setDifference(BigDecimal.TEN);
		weMarketRateDTO.setSubEstimateId(subEstimateDTO.getId());
		weMarketRateDTO.setWorkEstimateId(workEstimateDTO.getId());
		weMarketRateDTO.setWorkEstimateItemId(workEstimateItemId);
		return weMarketRateDTO;
	}

	private WorkEstimateLeadChargesDTO getWeLeadChargesDTO(Long workEstimateItemId) {
		MaterialMasterDetailsDTO material = getMaterialMasterDetailsDTO();
		WorkEstimateLeadChargesDTO weLeadChargesDTO = new WorkEstimateLeadChargesDTO();
		weLeadChargesDTO.setMaterialMasterId(material.getMaterialId());
		weLeadChargesDTO.setMaterialName(material.getMaterialName());
		weLeadChargesDTO.setQuantity(material.getQuantity());
		weLeadChargesDTO.setLeadInKm(BigDecimal.ONE);
		weLeadChargesDTO.setLeadInM(BigDecimal.ONE);
		weLeadChargesDTO.setCatWorkSorItemId(workEstimateItemDTO.getCatWorkSorItemId());
		weLeadChargesDTO.setSubEstimateId(subEstimateDTO.getId());
		weLeadChargesDTO.setWorkEstimateId(workEstimateDTO.getId());
		weLeadChargesDTO.setWorkEstimateItemId(workEstimateItemId);
		return weLeadChargesDTO;
	}

	private WorkEstimateLiftChargesDTO getWeLiftChargesDTO(Long workEstimateItemId) {
		MaterialMasterDetailsDTO material = getMaterialMasterDetailsDTO();
		WorkEstimateLiftChargesDTO liftChargeDTO = new WorkEstimateLiftChargesDTO();
		liftChargeDTO.setMaterialMasterId(material.getMaterialId());
		liftChargeDTO.setMaterialName(material.getMaterialName());
		liftChargeDTO.setUomId(material.getUomId());
		liftChargeDTO.setUomName(material.getUomName());
		liftChargeDTO.setQuantity(material.getQuantity());
		liftChargeDTO.setLiftCharges(BigDecimal.TEN);
		liftChargeDTO.setLiftDistance(BigDecimal.ONE);
		liftChargeDTO.setWorkEstimateItemId(workEstimateItemId);
		return liftChargeDTO;
	}

	private WorkEstimateLoadUnloadChargesDTO getWeLoadUnloadChargeDTO(Long workEstimateItemId) {
		MaterialMasterLoadUnloadDetailsDTO ldUdDetails = getMaterialMasterLoadUnloadDetailsDTO();
		WorkEstimateLoadUnloadChargesDTO weLoadUnloadChargeDTO = new WorkEstimateLoadUnloadChargesDTO();
		weLoadUnloadChargeDTO.setMaterialMasterId(ldUdDetails.getMaterialId());
		weLoadUnloadChargeDTO.setMaterialName(ldUdDetails.getMaterialName());
		weLoadUnloadChargeDTO.setQuantity(ldUdDetails.getQuantity());
		weLoadUnloadChargeDTO.setLoadingCharges(ldUdDetails.getLoadingCharges());
		weLoadUnloadChargeDTO.setSelectedLoadCharges(true);
		weLoadUnloadChargeDTO.setUnloadingCharges(ldUdDetails.getUnLoadingCharges());
		weLoadUnloadChargeDTO.setSelectedUnloadCharges(true);
		weLoadUnloadChargeDTO.setTotal(BigDecimal.ZERO);
		weLoadUnloadChargeDTO.setCatWorkSorItemId(workEstimateItemDTO.getCatWorkSorItemId());
		weLoadUnloadChargeDTO.setSubEstimateId(subEstimateDTO.getId());
		weLoadUnloadChargeDTO.setWorkEstimateId(workEstimateDTO.getId());
		weLoadUnloadChargeDTO.setWorkEstimateItemId(workEstimateItemId);
		return weLoadUnloadChargeDTO;
	}

	private WorkEstimateRoyaltyChargesDTO getRoyaltyChargesDTO(Long workEstimateItemId) {
		MaterialMasterRoyaltyChargesDetailsDTO royaltyDetails = getMaterialMasterRoyaltyChargesDetailsDTO();
		WorkEstimateRoyaltyChargesDTO weRoyaltyDTO = new WorkEstimateRoyaltyChargesDTO();
		weRoyaltyDTO.setMaterialMasterId(royaltyDetails.getMaterialId());
		weRoyaltyDTO.setMaterialName(royaltyDetails.getMaterialName());
		weRoyaltyDTO.setQuantity(royaltyDetails.getQuantity());
		weRoyaltyDTO.setUomId(royaltyDetails.getUomId());
		weRoyaltyDTO.setUomName(royaltyDetails.getUomName());
		weRoyaltyDTO.setDensityFactor(royaltyDetails.getDensityFactor());
		weRoyaltyDTO.setPrevailingRoyaltyCharges(royaltyDetails.getRoyaltyCharges().add(BigDecimal.TEN));
		weRoyaltyDTO.setSrRoyaltyCharges(royaltyDetails.getRoyaltyCharges());
		weRoyaltyDTO.setDifference(BigDecimal.TEN);
		weRoyaltyDTO.setCatWorkSorItemId(workEstimateItemDTO.getCatWorkSorItemId());
		weRoyaltyDTO.setWorkEstimateId(workEstimateDTO.getId());
		weRoyaltyDTO.setSubEstimateId(subEstimateDTO.getId());
		weRoyaltyDTO.setWorkEstimateItemId(workEstimateItemId);
		return weRoyaltyDTO;
	}

	private WorkEstimateRateAnalysisDTO getWeRateAnalysisDTO(Long workEstimateItemId) {
		WorkEstimateRateAnalysisDTO weRateAnalysis = new WorkEstimateRateAnalysisDTO();
		weRateAnalysis.setAreaWeightageCircleId(CIRCLE_ID);
		weRateAnalysis.setAreaWeightageMasterId(1L);
		weRateAnalysis.setAreaWeightagePercentage(BigDecimal.ONE);
		weRateAnalysis.setBasicRate(BigDecimal.TEN);
		weRateAnalysis.setCompensationCost(BigDecimal.ONE);
		weRateAnalysis.setContingencies(BigDecimal.ONE);
		weRateAnalysis.setContractorProfitPercentage(BigDecimal.ONE);
		weRateAnalysis.setEmployeesCost(BigDecimal.ONE);
		weRateAnalysis.setEsiCharges(BigDecimal.ONE);
		weRateAnalysis.setFloorNo(Long.valueOf(workEstimateItemDTO.getFloorNumber()));
		weRateAnalysis.setIdcCharges(BigDecimal.ONE);
		weRateAnalysis.setInsuranceCost(BigDecimal.ONE);
		weRateAnalysis.setLocalityAllowance(BigDecimal.ONE);
		weRateAnalysis.setNetRate(BigDecimal.ZERO);
		weRateAnalysis.setOverheadPercentage(BigDecimal.ONE);
		weRateAnalysis.setProvidentFundCharges(BigDecimal.ONE);
		weRateAnalysis.setRaCompletedYn(true);
		weRateAnalysis.setServiceTax(BigDecimal.ONE);
		weRateAnalysis.setSorFinancialYear("2021");
		weRateAnalysis.setStatutoryCharges(BigDecimal.ONE);
		weRateAnalysis.setTaxPercentage(BigDecimal.ONE);
		weRateAnalysis.setTransportationCost(BigDecimal.ONE);
		weRateAnalysis.setWatchAndWardCost(BigDecimal.ONE);
		weRateAnalysis.setWorkEstimateId(workEstimateDTO.getId());
		weRateAnalysis.setWorkEstimateItemId(workEstimateItemId);
		return weRateAnalysis;
	}

	private WorkEstimateAdditionalChargesDTO getWeAdditionalCharges(Long workEstimateItemId) {
		WorkEstimateAdditionalChargesDTO weAdditionalChargesDTO = new WorkEstimateAdditionalChargesDTO();
		weAdditionalChargesDTO.setAdditionalChargesDesc("additional charges");
		weAdditionalChargesDTO.setAdditionalChargesRate(BigDecimal.TEN);
		weAdditionalChargesDTO.setWorkEstimateItemId(workEstimateItemId);
		return weAdditionalChargesDTO;
	}

	private WorkEstimateOtherAddnLiftChargesDTO getOtherAddnLiftCharges(Long workEstimateItemId) {
		WorkEstimateOtherAddnLiftChargesDTO weOtherAddnLiftCharges = new WorkEstimateOtherAddnLiftChargesDTO();
		weOtherAddnLiftCharges.setAddnCharges(BigDecimal.ONE);
		weOtherAddnLiftCharges.setNotesMasterId(1L);
		weOtherAddnLiftCharges.setSelected(true);
		weOtherAddnLiftCharges.setType(RaChargeType.OTHER_CHARGES);
		weOtherAddnLiftCharges.setWorkEstimateItemId(workEstimateItemId);
		return weOtherAddnLiftCharges;
	}
}
