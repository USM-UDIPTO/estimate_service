package com.dxc.eproc.estimate.service;

import java.math.BigDecimal;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.model.WorkEstimateItem;
import com.dxc.eproc.estimate.repository.WorkEstimateItemRepository;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;
import com.dxc.eproc.estimate.service.impl.WorkEstimateItemServiceImpl;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateItemMapperImpl;

public class WorkEstimateItemServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WorkEstimateItemServiceTest.class);

	/** The work estimate item service. */
	private WorkEstimateItemService workEstimateItemService;

	/** The work estimate item mapper. */
	@InjectMocks
	private WorkEstimateItemMapperImpl workEstimateItemMapper;

	/** The work estimate item repository. */
	@Mock
	private WorkEstimateItemRepository workEstimateItemRepository;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {
		log.info("==========================================================================");
		log.info("This is executed before once Per Test Class - workEstimateItemServiceTest: init");
	}

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		log.info("==========================================================================");
		log.info("This is executed before each Test - workEstimateItemServiceTest: setUp");

		workEstimateItemService = new WorkEstimateItemServiceImpl(workEstimateItemRepository, workEstimateItemMapper);
	}

	/**
	 * Save test.
	 */
	@Test(priority = 1)
	public void saveTest() {
		log.info("==========================================================================");
		log.info("Test - saveTest - Start");

		final Long WORKESTIMATEITEMID = 101L;

		WorkEstimateItem workEstimateItem = createWorkEstimateItem();
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		workEstimateItem.setId(WORKESTIMATEITEMID);

		PowerMockito.when(workEstimateItemRepository.save(Mockito.any())).thenReturn(workEstimateItem);

		try {
			workEstimateItemDTO = workEstimateItemService.save(workEstimateItemDTO);
			log.info("Response data:saveTest: " + workEstimateItemDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in saveTest!");
		}

		Assert.assertEquals(workEstimateItemDTO.getId(), workEstimateItem.getId());
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(workEstimateItemDTO.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getQuantity(), workEstimateItem.getQuantity());

		log.info("Test - saveTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Save existing work estimate item test.
	 */
	@Test(priority = 2)
	public void saveExistingWorkEstimateItemTest() {
		log.info("==========================================================================");
		log.info("Test - saveExistingWorkEstimateItemTest - Start");

		final Long WORKESTIMATEITEMID = 101L;

		WorkEstimateItem workEstimateItem = createWorkEstimateItem();
		workEstimateItem.setId(WORKESTIMATEITEMID);
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		PowerMockito.when(workEstimateItemRepository.findById(workEstimateItemDTO.getId()))
				.thenReturn(Optional.of(workEstimateItem));
		PowerMockito.when(workEstimateItemRepository.save(Mockito.any())).thenReturn(workEstimateItem);

		try {
			workEstimateItemDTO = workEstimateItemService.save(workEstimateItemDTO);
			log.info("Response data:saveExistingWorkEstimateItem: " + workEstimateItemDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in saveExistingWorkEstimateItemTest!");
		}

		Assert.assertEquals(workEstimateItemDTO.getId(), workEstimateItem.getId());
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(workEstimateItemDTO.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getQuantity(), workEstimateItem.getQuantity());

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

		final Long WORKESTIMATEITEMID = 101L;
		final BigDecimal UPDATEDBASERATE = BigDecimal.valueOf(16.0000);

		WorkEstimateItem workEstimateItem = createWorkEstimateItem();
		workEstimateItem.setId(WORKESTIMATEITEMID);
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);
		workEstimateItemDTO.setEntryOrder(null);
		workEstimateItemDTO.setCatWorkSorItemId(null);
		workEstimateItemDTO.setDescription(null);
		workEstimateItemDTO.setBaseRate(UPDATEDBASERATE);

		Optional<WorkEstimateItemDTO> workEstimateItemDTOOpt = Optional.of(workEstimateItemDTO);

		PowerMockito.when(workEstimateItemRepository.findById(workEstimateItemDTO.getId()))
				.thenReturn(Optional.of(workEstimateItem));
		PowerMockito.when(workEstimateItemRepository.save(Mockito.any())).thenReturn(workEstimateItem);

		try {
			workEstimateItemDTOOpt = workEstimateItemService.partialUpdate(workEstimateItemDTO);
			log.info("Response data:partialUpdateTest: " + workEstimateItemDTOOpt);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in partialUpdateTest!");
		}

		if (workEstimateItemDTOOpt.isPresent()) {
			workEstimateItemDTO = workEstimateItemDTOOpt.get();
		}
		Assert.assertEquals(workEstimateItemDTO.getId(), workEstimateItem.getId());
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(workEstimateItemDTO.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getQuantity(), workEstimateItem.getQuantity());

		log.info("Test - partialUpdateTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Create the work estimate item.
	 *
	 * @return the work estimate item
	 */
	private WorkEstimateItem createWorkEstimateItem() {

		WorkEstimateItem workEstimateItem = new WorkEstimateItem().entryOrder(1).catWorkSorItemId(1L).categoryId(1L)
				.uomId(1L).itemCode("item-code").description("This is item description")
				.baseRate(BigDecimal.valueOf(12.0000)).finalRate(BigDecimal.valueOf(12.0000)).floorNumber(12)
				.quantity(BigDecimal.valueOf(1.0000));

		return workEstimateItem;
	}

	/**
	 * Find all test.
	 */
	@Test(priority = 4)
	public void findAllTest() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		Page<WorkEstimateItemDTO> responseDTO = null;
		WorkEstimateItemDTO workEstimateItemDTO = null;

		final Long WORKESTIMATEITEMID = 1L;
		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<WorkEstimateItem> workEstimateItemList = createWorkEstimateItemList();
		Page<WorkEstimateItem> workEstimateItemPage = new PageImpl<>(workEstimateItemList);

		PowerMockito.when(workEstimateItemRepository.findAll(pageable)).thenReturn(workEstimateItemPage);

		try {
			responseDTO = workEstimateItemService.findAll(pageable);
			log.info("Response data:findAllTest: " + responseDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findAllTest!");
		}

		workEstimateItemDTO = responseDTO.get().findFirst().get();
		WorkEstimateItem workEstimateItem = workEstimateItemList.get(0);

		Assert.assertEquals(workEstimateItemDTO.getId(), WORKESTIMATEITEMID);
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(workEstimateItemDTO.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getQuantity(), workEstimateItem.getQuantity());

		log.info("Test - findAllTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find all items test.
	 */
	@Test(priority = 5)
	public void findAllItemsTest() {
		log.info("==========================================================================");
		log.info("Test - findAllItemsTest - Start");

		final Long SUBESTIMATEID = 1L;
		final Long WORKESTIMATEITEMID = 1L;

		List<WorkEstimateItem> workEstimateItemList = createWorkEstimateItemList();
		List<WorkEstimateItemDTO> workEstimateItemDTOs = workEstimateItemMapper.toDto(workEstimateItemList);

		PowerMockito.when(workEstimateItemRepository.findAllBySubEstimateIdOrderByEntryOrderAsc(SUBESTIMATEID))
				.thenReturn(workEstimateItemList);

		try {
			workEstimateItemDTOs = workEstimateItemService.findAllItems(SUBESTIMATEID);
			log.info("Response data:findAllItemsTest: " + workEstimateItemDTOs);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findAllItemsTest!");
		}

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemDTOs.get(0);
		WorkEstimateItem workEstimateItem = workEstimateItemList.get(0);

		Assert.assertEquals(workEstimateItemDTO.getId(), WORKESTIMATEITEMID);
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(workEstimateItemDTO.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getQuantity(), workEstimateItem.getQuantity());

		log.info("Test - findAllItemsTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find all SOR items test.
	 */
	@Test(priority = 6)
	public void findAllSORItemsTest() {
		log.info("==========================================================================");
		log.info("Test - findAllSORItemsTest - Start");

		final Long SUBESTIMATEID = 1L;
		final Long WORKESTIMATEITEMID = 1L;

		List<WorkEstimateItem> workEstimateItemList = createWorkEstimateItemList();
		List<WorkEstimateItemDTO> workEstimateItemDTOs = workEstimateItemMapper.toDto(workEstimateItemList);

		PowerMockito
				.when(workEstimateItemRepository
						.findAllBySubEstimateIdAndCatWorkSorItemIdNotNullOrderByEntryOrderAsc(SUBESTIMATEID))
				.thenReturn(workEstimateItemList);

		try {
			workEstimateItemDTOs = workEstimateItemService.findAllSORItems(SUBESTIMATEID);
			log.info("Response data:findAllSORItemsTest: " + workEstimateItemDTOs);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findAllSORItemsTest!");
		}

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemDTOs.get(0);
		WorkEstimateItem workEstimateItem = workEstimateItemList.get(0);

		Assert.assertEquals(workEstimateItemDTO.getId(), WORKESTIMATEITEMID);
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(workEstimateItemDTO.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getQuantity(), workEstimateItem.getQuantity());

		log.info("Test - findAllSORItemsTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find all SOR items pageable test.
	 */
	@Test(priority = 7)
	public void findAllSORItemsPageableTest() {
		log.info("==========================================================================");
		log.info("Test - findAllSORItemsPageableTest - Start");

		Page<WorkEstimateItemDTO> responseDTO = null;
		WorkEstimateItemDTO workEstimateItemDTO = null;

		final Long WORKESTIMATEITEMID = 1L;
		final Long SUBESTIMATEID = 1L;
		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<WorkEstimateItem> workEstimateItemList = createWorkEstimateItemList();
		Page<WorkEstimateItem> workEstimateItemPage = new PageImpl<>(workEstimateItemList);

		PowerMockito
				.when(workEstimateItemRepository
						.findAllBySubEstimateIdAndCatWorkSorItemIdNotNullOrderByEntryOrderAsc(SUBESTIMATEID, pageable))
				.thenReturn(workEstimateItemPage);

		try {
			responseDTO = workEstimateItemService.findAllSORItems(SUBESTIMATEID, pageable);
			log.info("Response data:findAllSORItemsPageableTest: " + responseDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findAllSORItemsPageableTest!");
		}

		workEstimateItemDTO = responseDTO.get().findFirst().get();
		WorkEstimateItem workEstimateItem = workEstimateItemList.get(0);

		Assert.assertEquals(workEstimateItemDTO.getId(), WORKESTIMATEITEMID);
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(workEstimateItemDTO.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getQuantity(), workEstimateItem.getQuantity());

		log.info("Test - findAllSORItemsPageableTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find all non SOR items test.
	 */
	@Test(priority = 8)
	public void findAllNonSORItemsTest() {
		log.info("==========================================================================");
		log.info("Test - findAllNonSORItemsTest - Start");

		final Long SUBESTIMATEID = 1L;
		final Long WORKESTIMATEITEMID = 1L;

		List<WorkEstimateItem> workEstimateItemList = createWorkEstimateItemList();
		List<WorkEstimateItemDTO> workEstimateItemDTOs = workEstimateItemMapper.toDto(workEstimateItemList);

		PowerMockito
				.when(workEstimateItemRepository
						.findAllBySubEstimateIdAndCatWorkSorItemIdNullOrderByEntryOrderAsc(SUBESTIMATEID))
				.thenReturn(workEstimateItemList);

		try {
			workEstimateItemDTOs = workEstimateItemService.findAllNonSORItems(SUBESTIMATEID);
			log.info("Response data:findAllNonSORItemsTest: " + workEstimateItemDTOs);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findAllNonSORItemsTest!");
		}

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemDTOs.get(0);
		WorkEstimateItem workEstimateItem = workEstimateItemList.get(0);

		Assert.assertEquals(workEstimateItemDTO.getId(), WORKESTIMATEITEMID);
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(workEstimateItemDTO.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getQuantity(), workEstimateItem.getQuantity());

		log.info("Test - findAllNonSORItemsTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find all non SOR items pageable test.
	 */
	@Test(priority = 9)
	public void findAllNonSORItemsPageableTest() {
		log.info("==========================================================================");
		log.info("Test - findAllNonSORItemsPageableTest - Start");

		Page<WorkEstimateItemDTO> responseDTO = null;
		WorkEstimateItemDTO workEstimateItemDTO = null;

		final Long WORKESTIMATEITEMID = 1L;
		final Long SUBESTIMATEID = 1L;
		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<WorkEstimateItem> workEstimateItemList = createWorkEstimateItemList();
		Page<WorkEstimateItem> workEstimateItemPage = new PageImpl<>(workEstimateItemList);

		PowerMockito
				.when(workEstimateItemRepository
						.findAllBySubEstimateIdAndCatWorkSorItemIdNullOrderByEntryOrderAsc(SUBESTIMATEID, pageable))
				.thenReturn(workEstimateItemPage);

		try {
			responseDTO = workEstimateItemService.findAllNonSORItems(SUBESTIMATEID, pageable);
			log.info("Response data:findAllNonSORItemsPageableTest: " + responseDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findAllNonSORItemsPageableTest!");
		}

		workEstimateItemDTO = responseDTO.get().findFirst().get();
		WorkEstimateItem workEstimateItem = workEstimateItemList.get(0);

		Assert.assertEquals(workEstimateItemDTO.getId(), WORKESTIMATEITEMID);
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(workEstimateItemDTO.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getQuantity(), workEstimateItem.getQuantity());

		log.info("Test - findAllNonSORItemsPageableTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find all SOR item by subEstimateId and id in test.
	 */
	@Test(priority = 10)
	public void findAllSORItemBySubEstimateIdAndIdInTest() {
		log.info("==========================================================================");
		log.info("Test - findAllSORItemBySubEstimateIdAndIdInTest - Start");

		final Long SUBESTIMATEID = 1L;
		final Long WORKESTIMATEITEMID = 1L;
		final List<Long> WORKESTIMATEITEMWITHIDS = new ArrayList<>();
		WORKESTIMATEITEMWITHIDS.add(1L);
		WORKESTIMATEITEMWITHIDS.add(2L);

		List<WorkEstimateItem> workEstimateItemList = createWorkEstimateItemList();
		List<WorkEstimateItemDTO> workEstimateItemDTOs = workEstimateItemMapper.toDto(workEstimateItemList);

		PowerMockito.when(workEstimateItemRepository.findBySubEstimateIdAndCatWorkSorItemIdNotNullAndIdIn(SUBESTIMATEID,
				WORKESTIMATEITEMWITHIDS)).thenReturn(workEstimateItemList);

		try {
			workEstimateItemDTOs = workEstimateItemService.findAllSORItemBySubEstimateIdAndIdIn(SUBESTIMATEID,
					WORKESTIMATEITEMWITHIDS);
			log.info("Response data:findAllSORItemBySubEstimateIdAndIdInTest: " + workEstimateItemDTOs);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findAllSORItemBySubEstimateIdAndIdInTest!");
		}

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemDTOs.get(0);
		WorkEstimateItem workEstimateItem = workEstimateItemList.get(0);

		Assert.assertEquals(workEstimateItemDTO.getId(), WORKESTIMATEITEMID);
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(workEstimateItemDTO.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getQuantity(), workEstimateItem.getQuantity());

		log.info("Test - findAllSORItemBySubEstimateIdAndIdInTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Create the work estimate item list.
	 *
	 * @return the work estimate item list
	 */
	public List<WorkEstimateItem> createWorkEstimateItemList() {
		List<WorkEstimateItem> workEstimateItemList = new ArrayList<>();

		WorkEstimateItem firstWorkEstimateItem = new WorkEstimateItem().id(1L).entryOrder(1).catWorkSorItemId(1L)
				.categoryId(1L).uomId(1L).itemCode("item-code1").description("This is item description")
				.baseRate(BigDecimal.valueOf(12.0000)).finalRate(BigDecimal.valueOf(12.0000)).floorNumber(12)
				.quantity(BigDecimal.valueOf(1.0000));

		WorkEstimateItem secondWorkEstimateItem = new WorkEstimateItem().id(2L).entryOrder(1).catWorkSorItemId(1L)
				.categoryId(1L).uomId(1L).itemCode("item-code2").description("This is item description")
				.baseRate(BigDecimal.valueOf(12.0000)).finalRate(BigDecimal.valueOf(12.0000)).floorNumber(12)
				.quantity(BigDecimal.valueOf(1.0000));

		workEstimateItemList.add(firstWorkEstimateItem);
		workEstimateItemList.add(secondWorkEstimateItem);

		return workEstimateItemList;
	}

	/**
	 * Find one test.
	 */
	@Test(priority = 11)
	public void findOneTest() {
		log.info("==========================================================================");
		log.info("Test - findOneTest - Start");

		final Long WORKESTIMATEITEMID = 101L;

		Optional<WorkEstimateItemDTO> workEstimateItemOpt = null;

		WorkEstimateItem workEstimateItem = createWorkEstimateItem();
		workEstimateItem.setId(WORKESTIMATEITEMID);
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemMapper.toDto(workEstimateItem);

		PowerMockito.when(workEstimateItemRepository.findById(workEstimateItemDTO.getId()))
				.thenReturn(Optional.of(workEstimateItem));

		try {
			workEstimateItemOpt = workEstimateItemService.findOne(WORKESTIMATEITEMID);
			log.info("Response data:findOneTest: " + workEstimateItemOpt);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findOneTest!");
		}

		if (workEstimateItemOpt.isPresent()) {
			workEstimateItemDTO = workEstimateItemOpt.get();
		}
		Assert.assertEquals(workEstimateItemDTO.getId(), WORKESTIMATEITEMID);
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(workEstimateItemDTO.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getQuantity(), workEstimateItem.getQuantity());

		log.info("Test - findOneTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find by sub estimate id and id test.
	 */
	@Test(priority = 12)
	public void findBySubEstimateIdAndIdTest() {
		log.info("==========================================================================");
		log.info("Test - findBySubEstimateIdAndIdTest - Start");

		final Long SUBESTIMATEID = 1L;
		final Long ID = 1L;

		Optional<WorkEstimateItemDTO> workEstimateItemOpt = null;
		WorkEstimateItemDTO workEstimateItemDTO = null;

		WorkEstimateItem workEstimateItem = createWorkEstimateItem();
		workEstimateItem.setId(ID);

		PowerMockito.when(workEstimateItemRepository.findBySubEstimateIdAndId(SUBESTIMATEID, ID))
				.thenReturn(Optional.of(workEstimateItem));

		try {
			workEstimateItemOpt = workEstimateItemService.findBySubEstimateIdAndId(SUBESTIMATEID, ID);
			log.info("Response data:findBySubEstimateIdAndIdTest: " + workEstimateItemOpt);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findBySubEstimateIdAndIdTest!");
		}

		if (workEstimateItemOpt.isPresent()) {
			workEstimateItemDTO = workEstimateItemOpt.get();
		}
		Assert.assertEquals(workEstimateItemDTO.getId(), ID);
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(workEstimateItemDTO.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getQuantity(), workEstimateItem.getQuantity());

		log.info("Test - findBySubEstimateIdAndIdTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Delete test.
	 */
	@Test(priority = 13)
	public void deleteTest() {
		log.info("==========================================================================");
		log.info("Test - deleteTest - Start");

		final Long ID = 1L;

		PowerMockito.doNothing().when(workEstimateItemRepository).deleteById(ID);

		try {
			workEstimateItemService.delete(ID);
			log.info("Response: deleteTest has been passed!");
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in deleteTest!");
		}

		log.info("Test - deleteTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find by subEstimateId and itemCode test.
	 */
	@Test(priority = 14)
	public void findBySubEstimateIdAndItemCodeTest() {
		log.info("==========================================================================");
		log.info("Test - findBySubEstimateIdAndItemCodeTest - Start");

		final Long SUBESTIMATEID = 1L;
		final Long WORKESTIMATEITEMID = 1L;
		final String ITEMCODE = "ITEM_CODE";

		List<WorkEstimateItem> workEstimateItemList = createWorkEstimateItemList();
		List<WorkEstimateItemDTO> workEstimateItemDTOs = workEstimateItemMapper.toDto(workEstimateItemList);

		PowerMockito.when(workEstimateItemRepository.findBySubEstimateIdAndItemCode(SUBESTIMATEID, ITEMCODE))
				.thenReturn(workEstimateItemList);

		try {
			workEstimateItemDTOs = workEstimateItemService.findBySubEstimateIdAndItemCode(SUBESTIMATEID, ITEMCODE);
			log.info("Response data:findBySubEstimateIdAndItemCodeTest: " + workEstimateItemDTOs);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findBySubEstimateIdAndItemCodeTest!");
		}

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemDTOs.get(0);
		WorkEstimateItem workEstimateItem = workEstimateItemList.get(0);

		Assert.assertEquals(workEstimateItemDTO.getId(), WORKESTIMATEITEMID);
		Assert.assertEquals(workEstimateItemDTO.getEntryOrder(), workEstimateItem.getEntryOrder());
		Assert.assertEquals(workEstimateItemDTO.getCatWorkSorItemId(), workEstimateItem.getCatWorkSorItemId());
		Assert.assertEquals(workEstimateItemDTO.getCategoryId(), workEstimateItem.getCategoryId());
		Assert.assertEquals(workEstimateItemDTO.getUomId(), workEstimateItem.getUomId());
		Assert.assertEquals(workEstimateItemDTO.getItemCode(), workEstimateItem.getItemCode());
		Assert.assertEquals(workEstimateItemDTO.getDescription(), workEstimateItem.getDescription());
		Assert.assertEquals(workEstimateItemDTO.getBaseRate(), workEstimateItem.getBaseRate());
		Assert.assertEquals(workEstimateItemDTO.getFinalRate(), workEstimateItem.getFinalRate());
		Assert.assertEquals(workEstimateItemDTO.getFloorNumber(), workEstimateItem.getFloorNumber());
		Assert.assertEquals(workEstimateItemDTO.getQuantity(), workEstimateItem.getQuantity());

		log.info("Test - findBySubEstimateIdAndItemCodeTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Sum of work estimate item total test.
	 */
	@Test(priority = 15)
	public void sumOfWorkEstimateItemTotalTest() {
		log.info("==========================================================================");
		log.info("Test - sumOfWorkEstimateItemTotalTest - Start");

		final Long SUBESTIMATEID = 1L;

		final BigDecimal SUMOFWORKESTIMATEITEMTOTAL = BigDecimal.valueOf(20.0000);
		BigDecimal totalResponse = null;

		PowerMockito.when(workEstimateItemRepository.sumOfWorkEstimateItemTotal(SUBESTIMATEID))
				.thenReturn(SUMOFWORKESTIMATEITEMTOTAL);

		try {
			totalResponse = workEstimateItemService.sumOfWorkEstimateItemTotal(SUBESTIMATEID);
			log.info("Response data:sumOfWorkEstimateItemTotalTest: " + totalResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in sumOfWorkEstimateItemTotalTest!");
		}

		Assert.assertEquals(totalResponse, SUMOFWORKESTIMATEITEMTOTAL);

		log.info("Test - sumOfWorkEstimateItemTotalTest - Start");
		log.info("==========================================================================");
	}

	/**
	 * Sum of SOR work estimate item total test.
	 */
	@Test(priority = 16)
	public void sumOfSORWorkEstimateItemTotalTest() {
		log.info("==========================================================================");
		log.info("Test - sumOfSORWorkEstimateItemTotalTest - Start");

		final Long SUBESTIMATEID = 1L;

		final BigDecimal SUMOFSORWORKESTIMATEITEMTOTAL = BigDecimal.valueOf(20.0000);
		BigDecimal totalResponse = null;

		PowerMockito.when(workEstimateItemRepository.sumOfSORWorkEstimateItemTotal(SUBESTIMATEID))
				.thenReturn(SUMOFSORWORKESTIMATEITEMTOTAL);

		try {
			totalResponse = workEstimateItemService.sumOfSORWorkEstimateItemTotal(SUBESTIMATEID);
			log.info("Response data:sumOfSORWorkEstimateItemTotalTest: " + totalResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in sumOfSORWorkEstimateItemTotalTest!");
		}

		Assert.assertEquals(totalResponse, SUMOFSORWORKESTIMATEITEMTOTAL);

		log.info("Test - sumOfSORWorkEstimateItemTotalTest - Start");
		log.info("==========================================================================");
	}

	/**
	 * Sum of non SOR work estimate item total test.
	 */
	@Test(priority = 17)
	public void sumOfNonSORWorkEstimateItemTotalTest() {
		log.info("==========================================================================");
		log.info("Test - sumOfNonSORWorkEstimateItemTotalTest - Start");

		final Long SUBESTIMATEID = 1L;

		final BigDecimal SUMOFNONSORWORKESTIMATEITEMTOTAL = BigDecimal.valueOf(20.0000);
		BigDecimal totalResponse = null;

		PowerMockito.when(workEstimateItemRepository.sumOfNonSORWorkEstimateItemTotal(SUBESTIMATEID))
				.thenReturn(SUMOFNONSORWORKESTIMATEITEMTOTAL);

		try {
			totalResponse = workEstimateItemService.sumOfNonSORWorkEstimateItemTotal(SUBESTIMATEID);
			log.info("Response data:sumOfNonSORWorkEstimateItemTotalTest: " + totalResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in sumOfNonSORWorkEstimateItemTotalTest!");
		}

		Assert.assertEquals(totalResponse, SUMOFNONSORWORKESTIMATEITEMTOTAL);

		log.info("Test - sumOfNonSORWorkEstimateItemTotalTest - Start");
		log.info("==========================================================================");
	}

	/**
	 * Find max SOR entry order by subEstimateId test.
	 */
	@Test(priority = 18)
	public void findMaxSOREntryOrderBySubEstimateIdTest() {
		log.info("==========================================================================");
		log.info("Test - findMaxSOREntryOrderBySubEstimateIdTest - Start");

		final Long SUBESTIMATEID = 1L;

		final Integer MAXSORENTRYORDER = 10;
		Integer totalResponse = null;

		PowerMockito.when(workEstimateItemRepository.findMaxSOREntryOrderBySubEstimateId(SUBESTIMATEID))
				.thenReturn(MAXSORENTRYORDER);

		try {
			totalResponse = workEstimateItemService.findMaxSOREntryOrderBySubEstimateId(SUBESTIMATEID);
			log.info("Response data:findMaxSOREntryOrderBySubEstimateIdTest: " + totalResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findMaxSOREntryOrderBySubEstimateIdTest!");
		}

		Assert.assertEquals(totalResponse, MAXSORENTRYORDER);

		log.info("Test - findMaxSOREntryOrderBySubEstimateIdTest - Start");
		log.info("==========================================================================");
	}

	/**
	 * Find max floor by subEstimateId and catWorkSorItemId test.
	 */
	@Test(priority = 19)
	public void findMaxFloorBySubEstimateIdAndCatWorkSorItemIdTest() {
		log.info("==========================================================================");
		log.info("Test - findMaxFloorBySubEstimateIdAndCatWorkSorItemIdTest - Start");

		final Long SUBESTIMATEID = 1L;
		final Long CATWORKSORITEMID = 1L;

		final Integer MAXFLOOR = 10;
		Integer totalResponse = null;

		PowerMockito.when(workEstimateItemRepository.findMaxFloorBySubEstimateIdAndCatWorkSorItemId(SUBESTIMATEID,
				CATWORKSORITEMID)).thenReturn(MAXFLOOR);

		try {
			totalResponse = workEstimateItemService.findMaxFloorBySubEstimateIdAndCatWorkSorItemId(SUBESTIMATEID,
					CATWORKSORITEMID);
			log.info("Response data:findMaxFloorBySubEstimateIdAndCatWorkSorItemIdTest: " + totalResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured in findMaxFloorBySubEstimateIdAndCatWorkSorItemIdTest!");
		}

		Assert.assertEquals(totalResponse, MAXFLOOR);

		log.info("Test - findMaxFloorBySubEstimateIdAndCatWorkSorItemIdTest - Start");
		log.info("==========================================================================");
	}
}
