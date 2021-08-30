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

import com.dxc.eproc.estimate.model.WorkCategoryAttribute;
import com.dxc.eproc.estimate.repository.WorkCategoryAttributeRepository;
import com.dxc.eproc.estimate.service.dto.WorkCategoryAttributeDTO;
import com.dxc.eproc.estimate.service.impl.WorkCategoryAttributeServiceImpl;
import com.dxc.eproc.estimate.service.mapper.WorkCategoryAttributeMapperImpl;

public class WorkCategoryAttributeServiceTest extends PowerMockTestCase {
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WorkCategoryAttributeServiceTest.class);

	/** The supplier info service. */
	private WorkCategoryAttributeService workCategoryAttributeService;

	/** The supplier info mapper. */
	@InjectMocks
	private WorkCategoryAttributeMapperImpl workCategoryAttributeMapper;

	/** The supplier info repository. */
	@Mock
	private WorkCategoryAttributeRepository workCategoryAttributeRepository;

	@BeforeClass
	public void init() {
		log.info("==========================================================================");
		log.info("This is executed before once Per Test Class - WorkCategoryAttributeServiceTest: init");
	}

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		log.info("==========================================================================");
		log.info("This is executed before each Test - WorkCategoryAttributeServiceTest: setUp");

		workCategoryAttributeService = new WorkCategoryAttributeServiceImpl(workCategoryAttributeRepository,
				workCategoryAttributeMapper);
	}

	@Test
	public void saveWorkCategoryAttributeTest() {
		log.info("==========================================================================");
		log.info("Test - saveWorkCategoryAttributeTest - Start");

		WorkCategoryAttribute workCategoryAttribute = new WorkCategoryAttribute();
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);
		workCategoryAttributeDTO.setActiveYn(true);
		workCategoryAttributeDTO.setAttributeName("attribute name");
		workCategoryAttributeDTO.setWorkCategoryId(101L);
		workCategoryAttributeDTO.setWorkTypeId(101L);
		PowerMockito.when(workCategoryAttributeRepository.save(Mockito.any())).thenReturn(workCategoryAttribute);
		try {
			workCategoryAttributeDTO = workCategoryAttributeService.save(workCategoryAttributeDTO);
			log.info("Response saveWorkCategoryAttribute: " + workCategoryAttributeDTO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception occured in saveTest!");
		}
		Assert.assertEquals(workCategoryAttributeDTO.getId(), workCategoryAttribute.getId());
		Assert.assertEquals(workCategoryAttributeDTO.getActiveYn(), workCategoryAttribute.getActiveYn());
		Assert.assertEquals(workCategoryAttributeDTO.getAttributeName(), workCategoryAttribute.getAttributeName());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkCategoryId(), workCategoryAttribute.getWorkCategoryId());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkTypeId(), workCategoryAttribute.getWorkTypeId());
		log.info("==========================================================================");
		log.info("Test - saveWorkCategoryAttributeTest - ends");
	}

	/**
	 * Save existing work estimate item test.
	 */
	@Test(priority = 2)
	public void saveExistingWorkCategoryAttributeTest() {
		log.info("==========================================================================");
		log.info("Test - saveExistingWorkEstimateItemTest - Start");

		final Long WORKCATEGORYID = 101L;

		WorkCategoryAttribute workCategoryAttribute = new WorkCategoryAttribute();
		workCategoryAttribute.setId(WORKCATEGORYID);
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);

		PowerMockito.when(workCategoryAttributeRepository.findById(workCategoryAttributeDTO.getId()))
				.thenReturn(Optional.of(workCategoryAttribute));
		PowerMockito.when(workCategoryAttributeRepository.save(Mockito.any())).thenReturn(workCategoryAttribute);

		try {
			workCategoryAttributeDTO = workCategoryAttributeService.save(workCategoryAttributeDTO);
			log.info("Response data:saveExistingWorkCategoryAttribute: " + workCategoryAttributeDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in saveExistingWorkCategoryAttributeTestt!");
		}
		Assert.assertEquals(workCategoryAttributeDTO.getId(), workCategoryAttribute.getId());
		Assert.assertEquals(workCategoryAttributeDTO.getActiveYn(), workCategoryAttribute.getActiveYn());
		Assert.assertEquals(workCategoryAttributeDTO.getAttributeName(), workCategoryAttribute.getAttributeName());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkCategoryId(), workCategoryAttribute.getWorkCategoryId());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkTypeId(), workCategoryAttribute.getWorkTypeId());

		log.info("Test - saveExistingWorkEstimateItemTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Partial update test.
	 */
	@Test(priority = 3)
	public void partialUpdateTest() {
		log.info("==========================================================================");
		log.info("Test - partialUpdateTest - Start");

		final Long WORKCATEGORYID = 101L;
		final String UPDATEDATTRIBUTENAME = "Updated Attribute name";

		WorkCategoryAttribute workCategoryAttribute = createWorkCategoryAttribute();
		workCategoryAttribute.setId(WORKCATEGORYID);
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);
		workCategoryAttributeDTO.setId(101L);
		workCategoryAttributeDTO.setActiveYn(true);
		workCategoryAttributeDTO.setAttributeName(UPDATEDATTRIBUTENAME);
		workCategoryAttributeDTO.setWorkCategoryId(101L);
		workCategoryAttributeDTO.setWorkTypeId(101L);
		Optional<WorkCategoryAttributeDTO> workCategoryAttributeDTOOpt = Optional.of(workCategoryAttributeDTO);

		PowerMockito.when(workCategoryAttributeRepository.findById(workCategoryAttributeDTO.getId()))
				.thenReturn(Optional.of(workCategoryAttribute));
		PowerMockito.when(workCategoryAttributeRepository.save(Mockito.any())).thenReturn(workCategoryAttribute);

		try {
			workCategoryAttributeDTOOpt = workCategoryAttributeService.partialUpdate(workCategoryAttributeDTO);
			log.info("Response data:partialUpdateTest: " + workCategoryAttributeDTOOpt);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in partialUpdateTest!");
		}

		if (workCategoryAttributeDTOOpt.isPresent()) {
			workCategoryAttributeDTO = workCategoryAttributeDTOOpt.get();
		}
		Assert.assertEquals(workCategoryAttributeDTO.getId(), workCategoryAttribute.getId());
		Assert.assertEquals(workCategoryAttributeDTO.getActiveYn(), workCategoryAttribute.getActiveYn());
		Assert.assertEquals(workCategoryAttributeDTO.getAttributeName(), workCategoryAttribute.getAttributeName());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkCategoryId(), workCategoryAttribute.getWorkCategoryId());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkTypeId(), workCategoryAttribute.getWorkTypeId());

		log.info("Test - partialUpdateTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Create the work estimate item.
	 *
	 * @return the work estimate item
	 */
	private WorkCategoryAttribute createWorkCategoryAttribute() {

		WorkCategoryAttribute workCategoryAttribute = new WorkCategoryAttribute().activeYn(true)
				.attributeName("attribute name").workCategoryId(101L).workTypeId(101L);

		return workCategoryAttribute;
	}

	/**
	 * Find all test.
	 */
	@Test(priority = 4)
	public void findAllTest() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		Page<WorkCategoryAttributeDTO> responseDTO = null;
		WorkCategoryAttributeDTO workCategoryAttributeDTO = null;

		final Long WORKCATEGORYID = 101L;
		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<WorkCategoryAttribute> workCategoryAttributeList = createWorkCategoryAttributeList();
		Page<WorkCategoryAttribute> workCategoryAttributePage = new PageImpl<>(workCategoryAttributeList);

		PowerMockito.when(workCategoryAttributeRepository.findAll(pageable)).thenReturn(workCategoryAttributePage);

		try {
			responseDTO = workCategoryAttributeService.findAll(pageable);
			log.info("Response data:findAllTest: " + responseDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findAllTest!");
		}

		workCategoryAttributeDTO = responseDTO.get().findFirst().get();
		WorkCategoryAttribute workCategoryAttribute = workCategoryAttributeList.get(0);

		Assert.assertEquals(workCategoryAttributeDTO.getId(), WORKCATEGORYID);
		Assert.assertEquals(workCategoryAttributeDTO.getActiveYn(), workCategoryAttribute.getActiveYn());
		Assert.assertEquals(workCategoryAttributeDTO.getAttributeName(), workCategoryAttribute.getAttributeName());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkCategoryId(), workCategoryAttribute.getWorkCategoryId());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkTypeId(), workCategoryAttribute.getWorkTypeId());

		log.info("Test - findAllTest - End");
		log.info("==========================================================================");

	}

	/**
	 * Create the work estimate item list.
	 *
	 * @return the work estimate item list
	 */
	public List<WorkCategoryAttribute> createWorkCategoryAttributeList() {
		List<WorkCategoryAttribute> workCategoryAttributeList = new ArrayList<>();

		WorkCategoryAttribute firsWorkCategoryAttribute = new WorkCategoryAttribute().id(101L).activeYn(true)
				.attributeName("attribute name").workCategoryId(101L).workTypeId(101L);
		WorkCategoryAttribute secondWorkCategoryAttribute = new WorkCategoryAttribute().id(102L).activeYn(true)
				.attributeName("new attribute name").workCategoryId(102L).workTypeId(102L);

		workCategoryAttributeList.add(firsWorkCategoryAttribute);
		workCategoryAttributeList.add(secondWorkCategoryAttribute);

		return workCategoryAttributeList;
	}

	/**
	 * Find one test.
	 */
	@Test(priority = 5)
	public void findOneTest() {
		log.info("==========================================================================");
		log.info("Test - findOneTest - Start");

		final Long WORKCATEGORYID = 101L;

		Optional<WorkCategoryAttributeDTO> workCategoryAttributeOpt = null;

		WorkCategoryAttribute workCategoryAttribute = createWorkCategoryAttribute();
		workCategoryAttribute.setId(WORKCATEGORYID);
		WorkCategoryAttributeDTO workCategoryAttributeDTO = workCategoryAttributeMapper.toDto(workCategoryAttribute);

		PowerMockito.when(workCategoryAttributeRepository.findById(workCategoryAttributeDTO.getId()))
				.thenReturn(Optional.of(workCategoryAttribute));

		try {
			workCategoryAttributeOpt = workCategoryAttributeService.findOne(WORKCATEGORYID);
			log.info("Response data:findOneTest: " + workCategoryAttributeOpt);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findOneTest!");
		}

		if (workCategoryAttributeOpt.isPresent()) {
			workCategoryAttributeDTO = workCategoryAttributeOpt.get();
		}
		Assert.assertEquals(workCategoryAttributeDTO.getId(), workCategoryAttribute.getId());
		Assert.assertEquals(workCategoryAttributeDTO.getActiveYn(), workCategoryAttribute.getActiveYn());
		Assert.assertEquals(workCategoryAttributeDTO.getAttributeName(), workCategoryAttribute.getAttributeName());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkCategoryId(), workCategoryAttribute.getWorkCategoryId());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkTypeId(), workCategoryAttribute.getWorkTypeId());

		log.info("Test - findOneTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find all active test.
	 */
	@Test(priority = 6)
	public void findAllActiveTest() {
		log.info("==========================================================================");
		log.info("Test - findAllActiveTest - Start");

		Page<WorkCategoryAttributeDTO> responseDTO = null;
		WorkCategoryAttributeDTO workCategoryAttributeDTO = null;

		final Long WORKCATEGORYID = 101L;
		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<WorkCategoryAttribute> workCategoryAttributeList = createWorkCategoryAttributeList();
		Page<WorkCategoryAttribute> workCategoryAttributePage = new PageImpl<>(workCategoryAttributeList);

		PowerMockito.when(workCategoryAttributeRepository.findAllByActiveYn(true, pageable))
				.thenReturn(workCategoryAttributePage);

		try {
			responseDTO = workCategoryAttributeService.findAllActive(pageable);
			log.info("Response data:findAllActiveTest: " + responseDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findAllActiveTest!");
		}

		workCategoryAttributeDTO = responseDTO.get().findFirst().get();
		WorkCategoryAttribute workCategoryAttribute = workCategoryAttributeList.get(0);

		Assert.assertEquals(workCategoryAttributeDTO.getId(), WORKCATEGORYID);
		Assert.assertEquals(workCategoryAttributeDTO.getActiveYn(), workCategoryAttribute.getActiveYn());
		Assert.assertEquals(workCategoryAttributeDTO.getAttributeName(), workCategoryAttribute.getAttributeName());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkCategoryId(), workCategoryAttribute.getWorkCategoryId());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkTypeId(), workCategoryAttribute.getWorkTypeId());

		log.info("Test - findAllActiveTest - End");
		log.info("==========================================================================");

	}

	/**
	 * Find all by worktypeId and workCategoryId and activeYn test.
	 */
	@Test(priority = 7)
	public void findAllByWorkTypeIdAndWorkCategoryIdAndActiveYnTest() {
		log.info("==========================================================================");
		log.info("Test - findAllByWorkTypeIdAndWorkCategoryIdAndActiveYnTest - Start");

		WorkCategoryAttributeDTO workCategoryAttributeDTO = null;
		List<WorkCategoryAttributeDTO> responseDTO = null;
		final Long WORKCATEGORYID = 101L;
		final Long WORKTYPEID = 101L;

		List<WorkCategoryAttribute> workCategoryAttributeList = createWorkCategoryAttributeList();

		PowerMockito.when(workCategoryAttributeRepository.findAllByWorkTypeIdAndWorkCategoryIdAndActiveYn(WORKTYPEID,
				WORKCATEGORYID, true)).thenReturn(workCategoryAttributeList);

		try {
			responseDTO = workCategoryAttributeService.findAllByWorkTypeIdAndWorkCategoryIdAndActiveYn(WORKTYPEID,
					WORKCATEGORYID);
			log.info("Response data:findAllByWorkTypeIdAndWorkCategoryIdAndActiveYn: " + responseDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findAllByWorkTypeIdAndWorkCategoryIdAndActiveYnTest!");
		}

		workCategoryAttributeDTO = responseDTO.get(0);
		WorkCategoryAttribute workCategoryAttribute = workCategoryAttributeList.get(0);

		Assert.assertEquals(workCategoryAttributeDTO.getId(), WORKCATEGORYID);
		Assert.assertEquals(workCategoryAttributeDTO.getActiveYn(), workCategoryAttribute.getActiveYn());
		Assert.assertEquals(workCategoryAttributeDTO.getAttributeName(), workCategoryAttribute.getAttributeName());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkCategoryId(), workCategoryAttribute.getWorkCategoryId());
		Assert.assertEquals(workCategoryAttributeDTO.getWorkTypeId(), workCategoryAttribute.getWorkTypeId());

		log.info("Test - findAllByWorkTypeIdAndWorkCategoryIdAndActiveYnTest - End");
		log.info("==========================================================================");

	}

	/**
	 * Delete test.
	 */
	@Test(priority = 8)
	public void deleteTest() {
		log.info("==========================================================================");
		log.info("Test - deleteTest - Start");

		final Long ID = 101L;

		WorkCategoryAttribute workCategoryAttribute = new WorkCategoryAttribute();
		workCategoryAttribute.setId(ID);
		workCategoryAttribute.setActiveYn(false);
		workCategoryAttribute.setAttributeName("attribute name");
		workCategoryAttribute.setWorkCategoryId(101L);
		workCategoryAttribute.setWorkTypeId(101L);
		PowerMockito.when(workCategoryAttributeRepository.findById(workCategoryAttribute.getId()))
				.thenReturn(Optional.of(workCategoryAttribute));
		PowerMockito.when(workCategoryAttributeRepository.save(Mockito.any())).thenReturn(workCategoryAttribute);

		try {
			workCategoryAttributeService.delete(ID);
			log.info("Response: deleteTest has been passed!");
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in deleteTest!");
		}

		log.info("Test - deleteTest - End");
		log.info("==========================================================================");
	}

	/**
	 * tearDown.
	 */
	@AfterClass
	public void tearDown() {
		log.info("==========================================================================");
		log.info("This is executed after once Per Test Class - WorkCategoryAttributeServiceTest: tearDown");
	}

}
