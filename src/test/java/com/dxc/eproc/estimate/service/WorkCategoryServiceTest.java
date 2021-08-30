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

import com.dxc.eproc.estimate.model.WorkCategory;
import com.dxc.eproc.estimate.repository.WorkCategoryRepository;
import com.dxc.eproc.estimate.service.dto.WorkCategoryDTO;
import com.dxc.eproc.estimate.service.impl.WorkCategoryServiceImpl;
import com.dxc.eproc.estimate.service.mapper.WorkCategoryMapperImpl;

public class WorkCategoryServiceTest extends PowerMockTestCase {
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WorkCategoryServiceTest.class);

	/** The supplier info service. */
	private WorkCategoryService workCategoryService;

	/** The supplier info mapper. */
	@InjectMocks
	private WorkCategoryMapperImpl workCategoryMapper;

	/** The supplier info repository. */
	@Mock
	private WorkCategoryRepository workCategoryRepository;

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

		workCategoryService = new WorkCategoryServiceImpl(workCategoryRepository, workCategoryMapper);
	}

	@Test
	public void saveWorkCategoryTest() {
		log.info("==========================================================================");
		log.info("Test - saveWorkCategoryTest - Start");

		WorkCategory workCategory = new WorkCategory();
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);
		workCategoryDTO.setActiveYn(true);
		workCategoryDTO.setCategoryCode("001");
		workCategoryDTO.setCategoryName("CategoryName");
		PowerMockito.when(workCategoryRepository.save(Mockito.any())).thenReturn(workCategory);
		try {
			workCategoryDTO = workCategoryService.save(workCategoryDTO);
			log.info("Response saveWorkCategory: " + workCategoryDTO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception occured in saveTest!");
		}
		Assert.assertEquals(workCategoryDTO.getId(), workCategory.getId());
		Assert.assertEquals(workCategoryDTO.getActiveYn(), workCategory.getActiveYn());
		Assert.assertEquals(workCategoryDTO.getCategoryCode(), workCategory.getCategoryCode());
		Assert.assertEquals(workCategoryDTO.getCategoryName(), workCategory.getCategoryName());
		log.info("==========================================================================");
		log.info("Test - saveWorkCategoryAttributeTest - ends");
	}

	/**
	 * Save existing work estimate item test.
	 */
	@Test(priority = 2)
	public void saveExistingWorkCategoryTest() {
		log.info("==========================================================================");
		log.info("Test - saveExistingWorkCategoryTest - Start");

		final Long ID = 101L;

		WorkCategory workCategory = new WorkCategory();
		workCategory.setId(ID);
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);

		PowerMockito.when(workCategoryRepository.findById(workCategoryDTO.getId()))
				.thenReturn(Optional.of(workCategory));
		PowerMockito.when(workCategoryRepository.save(Mockito.any())).thenReturn(workCategory);

		try {
			workCategoryDTO = workCategoryService.save(workCategoryDTO);
			log.info("Response data:saveExistingWorkCategoryAttribute: " + workCategoryDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in saveExistingWorkCategoryAttributeTestt!");
		}
		Assert.assertEquals(workCategoryDTO.getId(), workCategory.getId());
		Assert.assertEquals(workCategoryDTO.getActiveYn(), workCategory.getActiveYn());
		Assert.assertEquals(workCategoryDTO.getCategoryCode(), workCategory.getCategoryCode());
		Assert.assertEquals(workCategoryDTO.getCategoryName(), workCategory.getCategoryName());
		log.info("Test - saveExistingWorkCategoryTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Partial update test.
	 */
	@Test(priority = 3)
	public void partialUpdateTest() {
		log.info("==========================================================================");
		log.info("Test - partialUpdateTest - Start");

		final Long ID = 101L;
		final String UPDATEDCATEGORYNAME = "Updated Category name";

		WorkCategory workCategory = createWorkCategory();
		workCategory.setId(ID);
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);
		workCategoryDTO.setId(101L);
		workCategoryDTO.setActiveYn(true);
		workCategoryDTO.setCategoryCode("001L");
		workCategoryDTO.setCategoryName(UPDATEDCATEGORYNAME);
		Optional<WorkCategoryDTO> workCategoryDTOOpt = Optional.of(workCategoryDTO);

		PowerMockito.when(workCategoryRepository.findById(workCategoryDTO.getId()))
				.thenReturn(Optional.of(workCategory));
		PowerMockito.when(workCategoryRepository.save(Mockito.any())).thenReturn(workCategory);

		try {
			workCategoryDTOOpt = workCategoryService.partialUpdate(workCategoryDTO);
			log.info("Response data:partialUpdateTest: " + workCategoryDTOOpt);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in partialUpdateTest!");
		}

		if (workCategoryDTOOpt.isPresent()) {
			workCategoryDTO = workCategoryDTOOpt.get();
		}
		Assert.assertEquals(workCategoryDTO.getId(), workCategory.getId());
		Assert.assertEquals(workCategoryDTO.getActiveYn(), workCategory.getActiveYn());
		Assert.assertEquals(workCategoryDTO.getCategoryCode(), workCategory.getCategoryCode());
		Assert.assertEquals(workCategoryDTO.getCategoryName(), workCategory.getCategoryName());

		log.info("Test - partialUpdateTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Create the work estimate item.
	 *
	 * @return the work estimate item
	 */
	private WorkCategory createWorkCategory() {

		WorkCategory workCategory = new WorkCategory().activeYn(true).categoryCode("00L").categoryName("CategroyName");
		return workCategory;
	}

	/**
	 * Find all test.
	 */
	@Test(priority = 4)
	public void findAllTest() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		Page<WorkCategoryDTO> responseDTO = null;
		WorkCategoryDTO workCategoryDTO = null;

		final Long ID = 101L;
		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<WorkCategory> workCategoryList = createWorkCategoryList();
		Page<WorkCategory> workCategoryPage = new PageImpl<>(workCategoryList);

		PowerMockito.when(workCategoryRepository.findAll(pageable)).thenReturn(workCategoryPage);

		try {
			responseDTO = workCategoryService.findAll(pageable);
			log.info("Response data:findAllTest: " + responseDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findAllTest!");
		}

		workCategoryDTO = responseDTO.get().findFirst().get();
		WorkCategory workCategory = workCategoryList.get(0);

		Assert.assertEquals(workCategoryDTO.getId(), ID);
		Assert.assertEquals(workCategoryDTO.getActiveYn(), workCategory.getActiveYn());
		Assert.assertEquals(workCategoryDTO.getCategoryCode(), workCategory.getCategoryCode());
		Assert.assertEquals(workCategoryDTO.getCategoryName(), workCategory.getCategoryName());

		log.info("Test - findAllTest - End");
		log.info("==========================================================================");

	}

	/**
	 * Create the work estimate item list.
	 *
	 * @return the work estimate item list
	 */
	public List<WorkCategory> createWorkCategoryList() {
		List<WorkCategory> workCategoryList = new ArrayList<>();

		WorkCategory firsWorkCategory = new WorkCategory().id(101L).activeYn(true).categoryCode("00L")
				.categoryName("CategroyName");
		WorkCategory secondWorkCategory = new WorkCategory().id(102L).activeYn(true).categoryCode("00L")
				.categoryName("CategroyName");

		workCategoryList.add(firsWorkCategory);
		workCategoryList.add(secondWorkCategory);

		return workCategoryList;
	}

	/**
	 * Find one test.
	 */
	@Test(priority = 5)
	public void findOneTest() {
		log.info("==========================================================================");
		log.info("Test - findOneTest - Start");

		final Long ID = 101L;

		Optional<WorkCategoryDTO> workCategoryOpt = null;

		WorkCategory workCategory = createWorkCategory();
		workCategory.setId(ID);
		WorkCategoryDTO workCategoryDTO = workCategoryMapper.toDto(workCategory);

		PowerMockito.when(workCategoryRepository.findById(workCategoryDTO.getId()))
				.thenReturn(Optional.of(workCategory));

		try {
			workCategoryOpt = workCategoryService.findOne(ID);
			log.info("Response data:findOneTest: " + workCategoryOpt);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findOneTest!");
		}

		if (workCategoryOpt.isPresent()) {
			workCategoryDTO = workCategoryOpt.get();
		}
		Assert.assertEquals(workCategoryDTO.getId(), workCategory.getId());
		Assert.assertEquals(workCategoryDTO.getActiveYn(), workCategory.getActiveYn());
		Assert.assertEquals(workCategoryDTO.getCategoryCode(), workCategory.getCategoryCode());
		Assert.assertEquals(workCategoryDTO.getCategoryName(), workCategory.getCategoryName());

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

		Page<WorkCategoryDTO> responseDTO = null;
		WorkCategoryDTO workCategoryDTO = null;

		final Long ID = 101L;
		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<WorkCategory> workCategoryList = createWorkCategoryList();
		Page<WorkCategory> workCategoryPage = new PageImpl<>(workCategoryList);

		PowerMockito.when(workCategoryRepository.findAllByActiveYn(true, pageable)).thenReturn(workCategoryPage);

		try {
			responseDTO = workCategoryService.findAllActive(pageable);
			log.info("Response data:findAllActiveTest: " + responseDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findAllActiveTest!");
		}

		workCategoryDTO = responseDTO.get().findFirst().get();
		WorkCategory workCategory = workCategoryList.get(0);

		Assert.assertEquals(workCategoryDTO.getId(), ID);
		Assert.assertEquals(workCategoryDTO.getActiveYn(), workCategory.getActiveYn());
		Assert.assertEquals(workCategoryDTO.getCategoryCode(), workCategory.getCategoryCode());
		Assert.assertEquals(workCategoryDTO.getCategoryName(), workCategory.getCategoryName());

		log.info("Test - findAllActiveTest - End");
		log.info("==========================================================================");

	}

	/**
	 * Find all active test.
	 */
	@Test(priority = 6)
	public void findAllActiveListTest() {
		log.info("==========================================================================");
		log.info("Test - findAllActivePageableTest - Start");

		List<WorkCategoryDTO> responseDTO = null;
		WorkCategoryDTO workCategoryDTO = null;

		final Long ID = 101L;

		List<WorkCategory> workCategoryList = createWorkCategoryList();

		PowerMockito.when(workCategoryRepository.findAllByActiveYn(true)).thenReturn(workCategoryList);

		try {
			responseDTO = workCategoryService.findAllActive();
			log.info("Response data:findAllActiveTest: " + responseDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findAllActiveTest!");
		}

		workCategoryDTO = responseDTO.get(0);
		WorkCategory workCategory = workCategoryList.get(0);

		Assert.assertEquals(workCategoryDTO.getId(), ID);
		Assert.assertEquals(workCategoryDTO.getActiveYn(), workCategory.getActiveYn());
		Assert.assertEquals(workCategoryDTO.getCategoryCode(), workCategory.getCategoryCode());
		Assert.assertEquals(workCategoryDTO.getCategoryName(), workCategory.getCategoryName());

		log.info("Test - findAllActivePageableTest - End");
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

		WorkCategory workCategory = new WorkCategory();
		workCategory.setActiveYn(true);
		workCategory.setCategoryCode("001");
		workCategory.setCategoryName("CategoryName");
		workCategory.setId(ID);
		PowerMockito.when(workCategoryRepository.findById(workCategory.getId())).thenReturn(Optional.of(workCategory));
		PowerMockito.when(workCategoryRepository.save(Mockito.any())).thenReturn(workCategory);

		try {
			workCategoryService.delete(ID);
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
