package com.dxc.eproc.estimate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.testng.PowerMockTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.model.DeptEstimateType;
import com.dxc.eproc.estimate.repository.DeptEstimateTypeRepository;
import com.dxc.eproc.estimate.service.dto.DeptEstimateTypeDTO;
import com.dxc.eproc.estimate.service.impl.DeptEstimateTypeServiceImpl;
import com.dxc.eproc.estimate.service.mapper.DeptEstimateTypeMapperImpl;

public class DeptEstimateTypeServiceTest extends PowerMockTestCase {

	private final static Logger log = LoggerFactory.getLogger(DeptEstimateTypeServiceTest.class);

	private DeptEstimateTypeService deptEstimateTypeService;

	@InjectMocks
	private DeptEstimateTypeMapperImpl deptEstimateTypeMapper;

	@Mock
	private DeptEstimateTypeRepository deptEstimateTypeRepository;

	@BeforeClass
	public void init() {
		log.info("==========================================================================");
		log.info("This is executed before once Per Test Class - DeptEstimateTypeServiceTest: init");

	}

	@BeforeMethod
	public void setUp() {
		log.info("==========================================================================");
		log.info("This is executed before each Test - DeptEstimateTypeServiceTest: setUp");

		deptEstimateTypeService = new DeptEstimateTypeServiceImpl(deptEstimateTypeRepository, deptEstimateTypeMapper);

	}

	private DeptEstimateType createDeptEstimateType() {

		DeptEstimateType deptEstimateType = new DeptEstimateType().flowType("flowType").deptId(1L).estimateTypeId(1L)
				.activeYn(true);

		return deptEstimateType;
	}

	public List<DeptEstimateType> createDeptEstimateTypeList() {
		List<DeptEstimateType> deptEstimateTypeList = new ArrayList<>();

		DeptEstimateType firstDeptEstimateType = new DeptEstimateType().id(1L).flowType("flowType").deptId(1L)
				.estimateTypeId(1L).activeYn(true);
		DeptEstimateType secondDeptEstimateType = new DeptEstimateType().id(2L).flowType("flowType").deptId(1L)
				.estimateTypeId(1L).activeYn(true);

		deptEstimateTypeList.add(firstDeptEstimateType);
		deptEstimateTypeList.add(secondDeptEstimateType);

		return deptEstimateTypeList;
	}

	@Test(priority = 1)
	public void saveTest() {
		log.info("==========================================================================");
		log.info("Test - saveTest - Start");

		DeptEstimateType deptEstimateType = createDeptEstimateType();
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);
		PowerMockito.when(deptEstimateTypeRepository.save(Mockito.any())).thenReturn(deptEstimateType);

		deptEstimateTypeDTO = deptEstimateTypeService.save(deptEstimateTypeDTO);
		log.info("Response data:saveTest: " + deptEstimateTypeDTO);

		Assert.assertEquals(deptEstimateTypeDTO.getId(), deptEstimateType.getId());
		Assert.assertEquals(deptEstimateTypeDTO.getFlowType(), deptEstimateType.getFlowType());
		Assert.assertEquals(deptEstimateTypeDTO.getDeptId(), deptEstimateType.getDeptId());
		Assert.assertEquals(deptEstimateTypeDTO.getEstimateTypeId(), deptEstimateType.getEstimateTypeId());
		Assert.assertEquals(deptEstimateTypeDTO.getActiveYn(), deptEstimateType.getActiveYn());

		log.info("Test - saveTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 2)
	public void saveExistingDeptEstimateTypeTest() {
		log.info("==========================================================================");
		log.info("Test - saveExistingDeptEstimateTypeTest - Start");

		final Long ID = 1L;
		DeptEstimateType deptEstimateType = createDeptEstimateType();
		deptEstimateType.setId(ID);
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);

		PowerMockito.when(deptEstimateTypeRepository.findById(deptEstimateTypeDTO.getId()))
		.thenReturn(Optional.of(deptEstimateType));
		PowerMockito.when(deptEstimateTypeRepository.save(Mockito.any())).thenReturn(deptEstimateType);

		deptEstimateTypeDTO = deptEstimateTypeService.save(deptEstimateTypeDTO);
		log.info("Response data:saveExistingWorkLocation : " + deptEstimateTypeDTO);

		Assert.assertEquals(deptEstimateTypeDTO.getId(), deptEstimateType.getId());
		Assert.assertEquals(deptEstimateTypeDTO.getFlowType(), deptEstimateType.getFlowType());
		Assert.assertEquals(deptEstimateTypeDTO.getDeptId(), deptEstimateType.getDeptId());
		Assert.assertEquals(deptEstimateTypeDTO.getEstimateTypeId(), deptEstimateType.getEstimateTypeId());
		Assert.assertEquals(deptEstimateTypeDTO.getActiveYn(), deptEstimateType.getActiveYn());

		log.info("Test - saveExistingDeptEstimateTypeTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 3)
	public void partialUpdateTest() {
		log.info("==========================================================================");
		log.info("Test - partialUpdateTest - Start");

		final Long ID = 1L;
		final String UPDATEDFLOWTYPE = "flowtype1";

		DeptEstimateType deptEstimateType = createDeptEstimateType();
		deptEstimateType.setId(ID);
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);
		deptEstimateTypeDTO.setEstimateTypeId(null);
		deptEstimateTypeDTO.setDeptId(null);
		deptEstimateTypeDTO.setFlowType(UPDATEDFLOWTYPE);
		Optional<DeptEstimateTypeDTO> deptEstimateTypeDTOOpt = Optional.of(deptEstimateTypeDTO);

		PowerMockito.when(deptEstimateTypeRepository.findById(deptEstimateTypeDTO.getId()))
		.thenReturn(Optional.of(deptEstimateType));
		PowerMockito.when(deptEstimateTypeRepository.save(Mockito.any())).thenReturn(deptEstimateType);

		deptEstimateTypeDTOOpt = deptEstimateTypeService.partialUpdate(deptEstimateTypeDTO);
		log.info("Response data:partialUpdateTest: " + deptEstimateTypeDTOOpt);

		if (deptEstimateTypeDTOOpt.isPresent()) {
			deptEstimateTypeDTO = deptEstimateTypeDTOOpt.get();
		}

		Assert.assertEquals(deptEstimateTypeDTO.getId(), deptEstimateType.getId());
		Assert.assertEquals(deptEstimateTypeDTO.getFlowType(), deptEstimateType.getFlowType());
		Assert.assertEquals(deptEstimateTypeDTO.getDeptId(), deptEstimateType.getDeptId());
		Assert.assertEquals(deptEstimateTypeDTO.getEstimateTypeId(), deptEstimateType.getEstimateTypeId());
		Assert.assertEquals(deptEstimateTypeDTO.getActiveYn(), deptEstimateType.getActiveYn());

		log.info("Test - partialUpdateTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 4)
	public void findAllTest() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		Page<DeptEstimateTypeDTO> responseDTO = null;
		DeptEstimateTypeDTO deptEstimateTypeDTO = null;

		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<DeptEstimateType> deptEstimateTypeList = createDeptEstimateTypeList();
		Page<DeptEstimateType> deptEstimateTypePage = new PageImpl<>(deptEstimateTypeList);

		PowerMockito.when(deptEstimateTypeRepository.findAll(pageable)).thenReturn(deptEstimateTypePage);

		responseDTO = deptEstimateTypeService.findAll(pageable);
		log.info("Response data:findAllTest: " + responseDTO);

		deptEstimateTypeDTO = responseDTO.get().findFirst().get();
		DeptEstimateType deptEstimateType = deptEstimateTypeList.get(0);

		Assert.assertEquals(deptEstimateTypeDTO.getId(), deptEstimateType.getId());
		Assert.assertEquals(deptEstimateTypeDTO.getFlowType(), deptEstimateType.getFlowType());
		Assert.assertEquals(deptEstimateTypeDTO.getDeptId(), deptEstimateType.getDeptId());
		Assert.assertEquals(deptEstimateTypeDTO.getEstimateTypeId(), deptEstimateType.getEstimateTypeId());
		Assert.assertEquals(deptEstimateTypeDTO.getActiveYn(), deptEstimateType.getActiveYn());

		log.info("Test - findAllTest - End");
		log.info("==========================================================================");

	}

	@Test(priority = 4)
	public void findAllActiveTest() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		Page<DeptEstimateTypeDTO> responseDTO = null;
		DeptEstimateTypeDTO deptEstimateTypeDTO = null;

		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<DeptEstimateType> deptEstimateTypeList = createDeptEstimateTypeList();
		Page<DeptEstimateType> deptEstimateTypePage = new PageImpl<>(deptEstimateTypeList);

		PowerMockito.when(deptEstimateTypeRepository.findAllByActiveYn(true, pageable))
		.thenReturn(deptEstimateTypePage);

		responseDTO = deptEstimateTypeService.findAllActive(pageable);
		log.info("Response data:findAllTest: " + responseDTO);

		deptEstimateTypeDTO = responseDTO.get().findFirst().get();
		DeptEstimateType deptEstimateType = deptEstimateTypeList.get(0);

		Assert.assertEquals(deptEstimateTypeDTO.getId(), deptEstimateType.getId());
		Assert.assertEquals(deptEstimateTypeDTO.getFlowType(), deptEstimateType.getFlowType());
		Assert.assertEquals(deptEstimateTypeDTO.getDeptId(), deptEstimateType.getDeptId());
		Assert.assertEquals(deptEstimateTypeDTO.getEstimateTypeId(), deptEstimateType.getEstimateTypeId());
		Assert.assertEquals(deptEstimateTypeDTO.getActiveYn(), deptEstimateType.getActiveYn());

		log.info("Test - findAllActiveTest - End");
		log.info("==========================================================================");

	}

	@Test(priority = 6)
	public void findOneTest() {
		log.info("==========================================================================");
		log.info("Test - findOneTest - Start");

		final Long ID = 1L;
		Optional<DeptEstimateTypeDTO> deptEstimateTypeOpt = null;

		DeptEstimateType deptEstimateType = createDeptEstimateType();
		deptEstimateType.setId(ID);
		DeptEstimateTypeDTO deptEstimateTypeDTO = deptEstimateTypeMapper.toDto(deptEstimateType);

		PowerMockito.when(deptEstimateTypeRepository.findById(deptEstimateTypeDTO.getId()))
		.thenReturn(Optional.of(deptEstimateType));

		deptEstimateTypeOpt = deptEstimateTypeService.findOne(ID);
		log.info("Response data:findOneTest: " + deptEstimateTypeOpt);

		if (deptEstimateTypeOpt.isPresent()) {
			deptEstimateTypeDTO = deptEstimateTypeOpt.get();
		}

		Assert.assertEquals(deptEstimateTypeDTO.getId(), deptEstimateType.getId());
		Assert.assertEquals(deptEstimateTypeDTO.getFlowType(), deptEstimateType.getFlowType());
		Assert.assertEquals(deptEstimateTypeDTO.getDeptId(), deptEstimateType.getDeptId());
		Assert.assertEquals(deptEstimateTypeDTO.getEstimateTypeId(), deptEstimateType.getEstimateTypeId());
		Assert.assertEquals(deptEstimateTypeDTO.getActiveYn(), deptEstimateType.getActiveYn());

		log.info("Test - findOneTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 7)
	public void getAllActiveDeptEstimateTypesForWorkEstimateTest() {
		log.info("==========================================================================");
		log.info("Test - getAllActiveDeptEstimateTypesForWorkEstimateTest - Start");

		final Long DEPTID = 1L;

		DeptEstimateTypeDTO deptEstimateTypeDTO = null;
		List<DeptEstimateTypeDTO> deptEstimateTypeDTOList = null;

		List<DeptEstimateType> deptEstimateTypeList = createDeptEstimateTypeList();

		PowerMockito.when(deptEstimateTypeRepository.findAllActiveDeptEstimateTypesForWorkEstimate(DEPTID))
		.thenReturn(deptEstimateTypeList);

		deptEstimateTypeDTOList = deptEstimateTypeService.getAllActiveDeptEstimateTypesForWorkEstimate(DEPTID);
		log.info("Response data:getAllActiveDeptEstimateTypesForWorkEstimateTest: " + deptEstimateTypeList);

		deptEstimateTypeDTO = deptEstimateTypeDTOList.get(0);
		DeptEstimateType deptEstimateType = deptEstimateTypeList.get(0);

		Assert.assertEquals(deptEstimateTypeDTO.getId(), deptEstimateType.getId());
		Assert.assertEquals(deptEstimateTypeDTO.getFlowType(), deptEstimateType.getFlowType());
		Assert.assertEquals(deptEstimateTypeDTO.getDeptId(), deptEstimateType.getDeptId());
		Assert.assertEquals(deptEstimateTypeDTO.getEstimateTypeId(), deptEstimateType.getEstimateTypeId());
		Assert.assertEquals(deptEstimateTypeDTO.getActiveYn(), deptEstimateType.getActiveYn());

		log.info("Test - getAllActiveDeptEstimateTypesForWorkEstimateTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 8)
	public void delete() throws Exception {
		DeptEstimateType deptEstimateType = createDeptEstimateType();
		PowerMockito.when(deptEstimateTypeRepository.findById(Mockito.anyLong()))
		.thenReturn(Optional.of(deptEstimateType));
		deptEstimateTypeService.delete(Mockito.anyLong());
		log.info("deleteTest successful!");
	}

	@AfterClass
	public static void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - EstimateTypeServiceTest");
	}

}
