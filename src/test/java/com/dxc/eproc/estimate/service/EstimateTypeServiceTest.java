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

import com.dxc.eproc.estimate.model.EstimateType;
import com.dxc.eproc.estimate.repository.EstimateTypeRepository;
import com.dxc.eproc.estimate.service.dto.EstimateTypeDTO;
import com.dxc.eproc.estimate.service.impl.EstimateTypeServiceImpl;
import com.dxc.eproc.estimate.service.mapper.EstimateTypeMapperImpl;

public class EstimateTypeServiceTest extends PowerMockTestCase {

	private final static Logger log = LoggerFactory.getLogger(EstimateTypeServiceTest.class);

	private EstimateTypeService estimateTypeService;

	@InjectMocks
	private EstimateTypeMapperImpl estimateTypeMapper;

	@Mock
	private EstimateTypeRepository estimateTypeRepository;

	@BeforeClass
	public void init() {
		log.info("==========================================================================");
		log.info("This is executed before once Per Test Class - EstimateTypeServiceTest: init");

	}

	@BeforeMethod
	public void setUp() {
		log.info("==========================================================================");
		log.info("This is executed before each Test - EstimateTypeServiceTest: setUp");

		estimateTypeService = new EstimateTypeServiceImpl(estimateTypeRepository, estimateTypeMapper);

	}

	private EstimateType createEstimateType() {

		EstimateType estimateType = new EstimateType().estimateTypeValue("estimateType").valueType("valueType")
				.activeYn(true);

		return estimateType;
	}

	public List<EstimateType> createEstimateTypeList() {
		List<EstimateType> estimateTypeList = new ArrayList<>();

		EstimateType firstEstimateType = new EstimateType().id(1L).estimateTypeValue("estimateType")
				.valueType("valueType").activeYn(true);
		EstimateType secondEstimateType = new EstimateType().id(2L).estimateTypeValue("estimateType")
				.valueType("valueType").activeYn(true);

		estimateTypeList.add(firstEstimateType);
		estimateTypeList.add(secondEstimateType);

		return estimateTypeList;
	}

	@Test(priority = 1)
	public void saveTest() {
		log.info("==========================================================================");
		log.info("Test - saveTest - Start");

		EstimateType estimateType = createEstimateType();
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);
		PowerMockito.when(estimateTypeRepository.save(Mockito.any())).thenReturn(estimateType);

		estimateTypeDTO = estimateTypeService.save(estimateTypeDTO);
		log.info("Response data:saveTest: " + estimateTypeDTO);

		Assert.assertEquals(estimateTypeDTO.getId(), estimateType.getId());
		Assert.assertEquals(estimateTypeDTO.getEstimateTypeValue(), estimateType.getEstimateTypeValue());
		Assert.assertEquals(estimateTypeDTO.getValueType(), estimateType.getValueType());
		Assert.assertEquals(estimateTypeDTO.getActiveYn(), estimateType.getActiveYn());

		log.info("Test - saveTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 2)
	public void saveExistingEstimateTypeTest() {
		log.info("==========================================================================");
		log.info("Test - saveExistingEstimateTypeTest - Start");

		final Long ID = 1L;
		EstimateType estimateType = createEstimateType();
		estimateType.setId(ID);
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);

		PowerMockito.when(estimateTypeRepository.findById(estimateTypeDTO.getId()))
				.thenReturn(Optional.of(estimateType));
		PowerMockito.when(estimateTypeRepository.save(Mockito.any())).thenReturn(estimateType);

		estimateTypeDTO = estimateTypeService.save(estimateTypeDTO);
		log.info("Response data:saveExistingWorkLocation : " + estimateTypeDTO);

		Assert.assertEquals(estimateTypeDTO.getId(), estimateType.getId());
		Assert.assertEquals(estimateTypeDTO.getEstimateTypeValue(), estimateType.getEstimateTypeValue());
		Assert.assertEquals(estimateTypeDTO.getValueType(), estimateType.getValueType());
		Assert.assertEquals(estimateTypeDTO.getActiveYn(), estimateType.getActiveYn());

		log.info("Test - saveExistingEstimateTypeTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 3)
	public void partialUpdateTest() {
		log.info("==========================================================================");
		log.info("Test - partialUpdateTest - Start");

		final Long ID = 1L;
		final String UPDATEDVALUETYPE = "value";

		EstimateType estimateType = createEstimateType();
		estimateType.setId(ID);
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);
		estimateTypeDTO.setEstimateTypeValue(null);
		estimateTypeDTO.setValueType(UPDATEDVALUETYPE);
		Optional<EstimateTypeDTO> estimateTypeDTOOpt = Optional.of(estimateTypeDTO);

		PowerMockito.when(estimateTypeRepository.findById(estimateTypeDTO.getId()))
				.thenReturn(Optional.of(estimateType));
		PowerMockito.when(estimateTypeRepository.save(Mockito.any())).thenReturn(estimateType);

		estimateTypeDTOOpt = estimateTypeService.partialUpdate(estimateTypeDTO);
		log.info("Response data:partialUpdateTest: " + estimateTypeDTOOpt);

		if (estimateTypeDTOOpt.isPresent()) {
			estimateTypeDTO = estimateTypeDTOOpt.get();
		}
		Assert.assertEquals(estimateTypeDTO.getId(), estimateType.getId());
		Assert.assertEquals(estimateTypeDTO.getEstimateTypeValue(), estimateType.getEstimateTypeValue());
		Assert.assertEquals(estimateTypeDTO.getValueType(), estimateType.getValueType());
		Assert.assertEquals(estimateTypeDTO.getActiveYn(), estimateType.getActiveYn());

		log.info("Test - partialUpdateTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 4)
	public void findAllTest() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		Page<EstimateTypeDTO> responseDTO = null;
		EstimateTypeDTO estimateTypeDTO = null;

		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<EstimateType> estimateTypeList = createEstimateTypeList();
		Page<EstimateType> estimateTypePage = new PageImpl<>(estimateTypeList);

		PowerMockito.when(estimateTypeRepository.findAll(pageable)).thenReturn(estimateTypePage);

		responseDTO = estimateTypeService.findAll(pageable);
		log.info("Response data:findAllTest: " + responseDTO);

		estimateTypeDTO = responseDTO.get().findFirst().get();
		EstimateType estimateType = estimateTypeList.get(0);

		Assert.assertEquals(estimateTypeDTO.getId(), estimateType.getId());
		Assert.assertEquals(estimateTypeDTO.getEstimateTypeValue(), estimateType.getEstimateTypeValue());
		Assert.assertEquals(estimateTypeDTO.getValueType(), estimateType.getValueType());

		log.info("Test - findAllTest - End");
		log.info("==========================================================================");

	}

	@Test(priority = 4)
	public void findAllActiveWithPageTest() {
		log.info("==========================================================================");
		log.info("Test - findAllWithPageTest - Start");

		Page<EstimateTypeDTO> responseDTO = null;
		EstimateTypeDTO estimateTypeDTO = null;

		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<EstimateType> estimateTypeList = createEstimateTypeList();
		Page<EstimateType> estimateTypePage = new PageImpl<>(estimateTypeList);

		PowerMockito.when(estimateTypeRepository.findAllByActiveYn(true, pageable)).thenReturn(estimateTypePage);

		responseDTO = estimateTypeService.findAllActive(pageable);
		log.info("Response data:findAllActiveWithPageTest: " + responseDTO);

		estimateTypeDTO = responseDTO.get().findFirst().get();
		EstimateType estimateType = estimateTypeList.get(0);

		Assert.assertEquals(estimateTypeDTO.getId(), estimateType.getId());
		Assert.assertEquals(estimateTypeDTO.getEstimateTypeValue(), estimateType.getEstimateTypeValue());
		Assert.assertEquals(estimateTypeDTO.getValueType(), estimateType.getValueType());

		log.info("Test - findAllActiveWithPageTest - End");
		log.info("==========================================================================");

	}

	@Test(priority = 5)
	public void findAllActiveTest() {
		log.info("==========================================================================");
		log.info("Test - findAllActiveTest - Start");

		EstimateTypeDTO estimateTypeDTO = null;
		List<EstimateTypeDTO> estimateTypeDTOList = null;

		List<EstimateType> estimateTypeList = createEstimateTypeList();

		PowerMockito.when(estimateTypeRepository.findAllByActiveYn(true)).thenReturn(estimateTypeList);

		estimateTypeDTOList = estimateTypeService.findAllActive();
		log.info("Response data:findAllActiveTest: " + estimateTypeDTOList);

		estimateTypeDTO = estimateTypeDTOList.get(0);
		EstimateType estimateType = estimateTypeList.get(0);

		Assert.assertEquals(estimateTypeDTO.getId(), estimateType.getId());
		Assert.assertEquals(estimateTypeDTO.getEstimateTypeValue(), estimateType.getEstimateTypeValue());
		Assert.assertEquals(estimateTypeDTO.getValueType(), estimateType.getValueType());

		log.info("Test - findAllActiveTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 6)
	public void findOneTest() {
		log.info("==========================================================================");
		log.info("Test - findOneTest - Start");

		final Long ID = 1L;
		Optional<EstimateTypeDTO> estimateTypeOpt = null;

		EstimateType estimateType = createEstimateType();
		estimateType.setId(ID);
		EstimateTypeDTO estimateTypeDTO = estimateTypeMapper.toDto(estimateType);

		PowerMockito.when(estimateTypeRepository.findById(estimateTypeDTO.getId()))
				.thenReturn(Optional.of(estimateType));

		estimateTypeOpt = estimateTypeService.findOne(ID);
		log.info("Response data:findOneTest: " + estimateTypeOpt);

		if (estimateTypeOpt.isPresent()) {
			estimateTypeDTO = estimateTypeOpt.get();
		}
		Assert.assertEquals(estimateTypeDTO.getId(), estimateType.getId());
		Assert.assertEquals(estimateTypeDTO.getEstimateTypeValue(), estimateType.getEstimateTypeValue());
		Assert.assertEquals(estimateTypeDTO.getValueType(), estimateType.getValueType());

		log.info("Test - findOneTest - End");
		log.info("==========================================================================");
	}

	@Test(priority = 7)
	public void delete() throws Exception {
		EstimateType estimateType = createEstimateType();
		PowerMockito.when(estimateTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(estimateType));
		estimateTypeService.delete(Mockito.anyLong());
		log.info("deleteTest successful!");
	}

	@AfterClass
	public static void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - EstimateTypeServiceTest");
	}

}
