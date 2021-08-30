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

import com.dxc.eproc.estimate.model.WorkType;
import com.dxc.eproc.estimate.repository.WorkTypeRepository;
import com.dxc.eproc.estimate.service.dto.WorkTypeDTO;
import com.dxc.eproc.estimate.service.impl.WorkTypeServiceImpl;
import com.dxc.eproc.estimate.service.mapper.WorkTypeMapperImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkTypeServiceTest.
 */

public class WorkTypeServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(WorkTypeServiceTest.class);

	/** The work type service. */
	private WorkTypeService workTypeService;

	/** The work type mapper. */
	@InjectMocks
	private WorkTypeMapperImpl workTypeMapper;

	/** The work type repository. */
	@Mock
	private WorkTypeRepository workTypeRepository;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {
		log.info("==========================================================================");
		log.info("This is executed before once Per Test Class - WorkTypeServiceTest: init");

	}

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		log.info("==========================================================================");
		log.info("This is executed before each Test - WorkTypeServiceTest: setUp");

		workTypeService = new WorkTypeServiceImpl(workTypeRepository, workTypeMapper);

	}

	/**
	 * Creates the work type.
	 *
	 * @return the work type
	 */
	private WorkType createWorkType() {

		WorkType workType = new WorkType().workTypeName("workType").workTypeValue("workType").valueType("valueType")
				.activeYn(true);

		return workType;
	}

	/**
	 * Creates the work type list.
	 *
	 * @return the list
	 */
	public List<WorkType> createWorkTypeList() {
		List<WorkType> workTypeList = new ArrayList<>();

		WorkType firstWorkType = new WorkType().id(1L).workTypeName("workType").workTypeValue("workType")
				.valueType("valueType").activeYn(true);
		WorkType secondWorkType = new WorkType().id(2L).workTypeName("workType").workTypeValue("workType")
				.valueType("valueType").activeYn(true);

		workTypeList.add(firstWorkType);
		workTypeList.add(secondWorkType);

		return workTypeList;
	}

	/**
	 * Save test.
	 */
	@Test
	public void saveTest() {
		log.info("==========================================================================");
		log.info("Test - saveTest - Start");

		WorkType workType = createWorkType();
		WorkTypeDTO workTypeDTO = workTypeMapper.toDto(createWorkType());
		PowerMockito.when(workTypeRepository.save(Mockito.any())).thenReturn(createWorkType());

		workTypeDTO = workTypeService.save(workTypeDTO);
		log.info("Response data:saveTest: " + workTypeDTO);

		Assert.assertEquals(workTypeDTO.getId(), workType.getId());
		Assert.assertEquals(workTypeDTO.getWorkTypeName(), workType.getWorkTypeName());
		Assert.assertEquals(workTypeDTO.getWorkTypeValue(), workType.getWorkTypeValue());
		Assert.assertEquals(workTypeDTO.getValueType(), workType.getValueType());
		Assert.assertEquals(workTypeDTO.getActiveYn(), workType.getActiveYn());

		log.info("Test - saveTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Save existing work type test.
	 */
	@Test
	public void saveExistingWorkTypeTest() {
		log.info("==========================================================================");
		log.info("Test - saveExistingWorkTypeTest - Start");

		final Long ID = 1L;
		WorkType workType = createWorkType();
		workType.setId(ID);
		WorkTypeDTO workTypeDTO = workTypeMapper.toDto(workType);

		PowerMockito.when(workTypeRepository.findById(workTypeDTO.getId())).thenReturn(Optional.of(workType));
		PowerMockito.when(workTypeRepository.save(Mockito.any())).thenReturn(workType);

		workTypeDTO = workTypeService.save(workTypeDTO);
		log.info("Response data:saveExistingWorkType : " + workTypeDTO);

		Assert.assertEquals(workTypeDTO.getId(), workType.getId());
		Assert.assertEquals(workTypeDTO.getWorkTypeName(), workType.getWorkTypeName());
		Assert.assertEquals(workTypeDTO.getWorkTypeValue(), workType.getWorkTypeValue());
		Assert.assertEquals(workTypeDTO.getValueType(), workType.getValueType());
		Assert.assertEquals(workTypeDTO.getActiveYn(), workType.getActiveYn());

		log.info("Test - saveExistingWorkTypeTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Partial update test.
	 */
	@Test
	public void partialUpdateTest() {
		log.info("==========================================================================");
		log.info("Test - partialUpdateTest - Start");

		final Long ID = 1L;
		final String UPDATEDVALUETYPE = "value";

		WorkType workType = createWorkType();
		workType.setId(ID);
		WorkTypeDTO workTypeDTO = workTypeMapper.toDto(workType);
		workTypeDTO.setWorkTypeValue(null);
		workTypeDTO.setValueType(UPDATEDVALUETYPE);
		Optional<WorkTypeDTO> workTypeDTOOpt = Optional.of(workTypeDTO);

		PowerMockito.when(workTypeRepository.findById(workTypeDTO.getId())).thenReturn(Optional.of(workType));
		PowerMockito.when(workTypeRepository.save(Mockito.any())).thenReturn(workType);

		workTypeDTOOpt = workTypeService.partialUpdate(workTypeDTO);
		log.info("Response data:partialUpdateTest: " + workTypeDTOOpt);

		if (workTypeDTOOpt.isPresent()) {
			workTypeDTO = workTypeDTOOpt.get();
		}
		Assert.assertEquals(workTypeDTO.getId(), workType.getId());
		Assert.assertEquals(workTypeDTO.getWorkTypeName(), workType.getWorkTypeName());
		Assert.assertEquals(workTypeDTO.getWorkTypeValue(), workType.getWorkTypeValue());
		Assert.assertEquals(workTypeDTO.getValueType(), workType.getValueType());
		Assert.assertEquals(workTypeDTO.getActiveYn(), workType.getActiveYn());

		log.info("Test - partialUpdateTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find all test.
	 */
	@Test
	public void findAllTest() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		Page<WorkTypeDTO> responseDTO = null;
		WorkTypeDTO workTypeDTO = null;

		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<WorkType> workTypeList = createWorkTypeList();
		Page<WorkType> workTypePage = new PageImpl<>(workTypeList);

		PowerMockito.when(workTypeRepository.findAll(pageable)).thenReturn(workTypePage);

		responseDTO = workTypeService.findAll(pageable);
		log.info("Response data:findAllTest: " + responseDTO);

		workTypeDTO = responseDTO.get().findFirst().get();
		WorkType workType = workTypeList.get(0);

		Assert.assertEquals(workTypeDTO.getId(), workTypeDTO.getId());
		Assert.assertEquals(workTypeDTO.getWorkTypeName(), workType.getWorkTypeName());
		Assert.assertEquals(workTypeDTO.getWorkTypeValue(), workType.getWorkTypeValue());
		Assert.assertEquals(workTypeDTO.getValueType(), workType.getValueType());

		log.info("Test - findAllTest - End");
		log.info("==========================================================================");

	}

	/**
	 * Find all active test.
	 */
	@Test
	public void findAllActiveTest() {
		log.info("==========================================================================");
		log.info("Test - findAllActiveTest - Start");

		Page<WorkTypeDTO> responseDTO = null;
		WorkTypeDTO workTypeDTO = null;

		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<WorkType> workTypeList = createWorkTypeList();
		Page<WorkType> workTypePage = new PageImpl<>(workTypeList);

		PowerMockito.when(workTypeRepository.findAllByActiveYn(true, pageable)).thenReturn(workTypePage);

		responseDTO = workTypeService.findAllActive(pageable);
		log.info("Response data:findAllTest: " + responseDTO);

		workTypeDTO = responseDTO.get().findFirst().get();
		WorkType workType = workTypeList.get(0);

		Assert.assertEquals(workTypeDTO.getId(), workType.getId());
		Assert.assertEquals(workTypeDTO.getWorkTypeName(), workType.getWorkTypeName());
		Assert.assertEquals(workTypeDTO.getWorkTypeValue(), workType.getWorkTypeValue());
		Assert.assertEquals(workTypeDTO.getValueType(), workType.getValueType());
		Assert.assertEquals(workTypeDTO.getActiveYn(), workType.getActiveYn());

		log.info("Test - findAllActiveTest - End");
		log.info("==========================================================================");

	}

	/**
	 * Find all active estimate work types test.
	 */
	@Test
	public void findAllActiveEstimateWorkTypesTest() {
		log.info("==========================================================================");
		log.info("Test - findAllActiveEstimateWorkTypesTest - Start");

		List<WorkType> workTypeList = createWorkTypeList();

		PowerMockito.when(workTypeRepository.findAllByActiveYn(true)).thenReturn(workTypeList);

		workTypeService.findAllActiveEstimateWorkTypes();

		log.info("Test - findAllActiveEstimateWorkTypesTest - End");
		log.info("==========================================================================");

	}

	/**
	 * Find one test.
	 */
	@Test
	public void findOneTest() {
		log.info("==========================================================================");
		log.info("Test - findOneTest - Start");

		final Long ID = 1L;
		Optional<WorkTypeDTO> workTypeOpt = null;

		WorkType workType = createWorkType();
		workType.setId(ID);
		WorkTypeDTO workTypeDTO = workTypeMapper.toDto(workType);

		PowerMockito.when(workTypeRepository.findById(workTypeDTO.getId())).thenReturn(Optional.of(workType));

		workTypeOpt = workTypeService.findOne(ID);
		log.info("Response data:findOneTest: " + workTypeOpt);

		if (workTypeOpt.isPresent()) {
			workTypeDTO = workTypeOpt.get();
		}
		Assert.assertEquals(workTypeDTO.getId(), workType.getId());
		Assert.assertEquals(workTypeDTO.getWorkTypeName(), workType.getWorkTypeName());
		Assert.assertEquals(workTypeDTO.getWorkTypeValue(), workType.getWorkTypeValue());
		Assert.assertEquals(workTypeDTO.getValueType(), workType.getValueType());

		log.info("Test - findOneTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Delete.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void delete() throws Exception {
		WorkType workType = createWorkType();
		PowerMockito.when(workTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(workType));
		workTypeService.delete(Mockito.anyLong());
		log.info("deleteTest successful!");
	}

	@AfterClass
	public static void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - WorkTypeServiceTest");
	}

}
