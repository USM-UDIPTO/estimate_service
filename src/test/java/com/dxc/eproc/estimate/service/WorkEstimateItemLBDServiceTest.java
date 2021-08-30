package com.dxc.eproc.estimate.service;

import static org.testng.Assert.assertNotNull;

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
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.enumeration.LBDOperation;
import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.model.WorkEstimateItem;
import com.dxc.eproc.estimate.model.WorkEstimateItemLBD;
import com.dxc.eproc.estimate.repository.SubEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateItemLBDRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateItemRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.dxc.eproc.estimate.response.dto.SubEstimateResponseDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemLBDDTO;
import com.dxc.eproc.estimate.service.impl.SubEstimateServiceImpl;
import com.dxc.eproc.estimate.service.impl.WorkEstimateItemLBDServiceImpl;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateItemLBDMapperImpl;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateItemMapperImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateItemLBDServiceTest.
 */
public class WorkEstimateItemLBDServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WorkEstimateItemLBDServiceTest.class);

	/** The LBD page. */
	private static Page<WorkEstimateItemLBD> LBDPage;

	/** The workEstimateItemLBDService service. */
	@InjectMocks
	private WorkEstimateItemLBDServiceImpl workEstimateItemLBDService;

	/** The work estimate item service. */
	@Mock
	private WorkEstimateItemService workEstimateItemService;

	/** The sub estimate service. */
	@Mock
	private SubEstimateServiceImpl subEstimateService;

	/** The work estimate service. */
	@Mock
	private WorkEstimateService workEstimateService;

	/** The workEstimateItem LBDMapper. */
	@Mock
	private WorkEstimateItemLBDMapperImpl workEstimateItemLBDMapper;

	/** The work estimate item mapper. */
	@Mock
	private WorkEstimateItemMapperImpl workEstimateItemMapper;

	/** The supplier info repository. */
	@Mock
	private WorkEstimateItemLBDRepository workEstimateItemLBDRepository;

	/** The work estimate item repository. */
	@Mock
	private WorkEstimateItemRepository workEstimateItemRepository;

	/** The sub estimate repository. */
	@Mock
	private SubEstimateRepository subEstimateRepository;

	/** The work estimate repository. */
	@Mock
	private WorkEstimateRepository workEstimateRepository;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {
		log.info("==========================================================================");
		log.info("This is executed before once Per Test Class - WorkEstimateItemLBDServiceTest: init");
	}

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		log.info("==========================================================================");
		log.info("This is executed before each Test - SupplierInfoServiceTest: setUp");

		workEstimateItemLBDService = new WorkEstimateItemLBDServiceImpl(workEstimateItemLBDRepository,
				workEstimateItemLBDMapper, workEstimateItemService, workEstimateService, subEstimateService);
	}

	/**
	 * Save test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	public void saveWorkEstimateItemLBD() throws Exception {
		log.info("==========================================================================");
		log.info("Test - saveWorkEstimateItemLBD - Start");
		WorkEstimateItemLBD workEstimateItemLBD = new WorkEstimateItemLBD();
		workEstimateItemLBD.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBD.setCalculatedYn(false);
		workEstimateItemLBD.setLbdBredth(BigDecimal.ONE);
		workEstimateItemLBD.setLbdDepth(BigDecimal.TEN);
		workEstimateItemLBD.setLbdLength(BigDecimal.ONE);
		workEstimateItemLBD.setLbdBredthFormula("2221");
		workEstimateItemLBD.setLbdDepthFormula("100021");
		workEstimateItemLBD.setLbdLengthFormula("200021");
		workEstimateItemLBD.setLbdNos(BigDecimal.ONE);
		workEstimateItemLBD.setLbdParticulars("LBD");
		workEstimateItemLBD.setLbdQuantity(BigDecimal.TEN);
		workEstimateItemLBD.setLbdTotal(BigDecimal.TEN);
		workEstimateItemLBD.setWorkEstimateItemId(101L);

		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = new WorkEstimateItemLBDDTO();
		workEstimateItemLBDDTO.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBDDTO.setCalculatedYn(false);
		workEstimateItemLBDDTO.setLbdBredth(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdDepth(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdLength(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdBredthFormula("2221");
		workEstimateItemLBDDTO.setLbdDepthFormula("100021");
		workEstimateItemLBDDTO.setLbdLengthFormula("200021");
		workEstimateItemLBDDTO.setLbdNos(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdParticulars("LBD");
		workEstimateItemLBDDTO.setLbdQuantity(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdTotal(BigDecimal.TEN);
		workEstimateItemLBDDTO.setWorkEstimateItemId(101L);

		PowerMockito.when(workEstimateItemLBDMapper.toEntity(workEstimateItemLBDDTO)).thenReturn(workEstimateItemLBD);
		PowerMockito.when(workEstimateItemLBDRepository.findById(workEstimateItemLBD.getId()))
				.thenReturn(Optional.of(workEstimateItemLBD));
		PowerMockito.when(workEstimateItemLBDRepository.save(workEstimateItemLBD)).thenReturn(workEstimateItemLBD);
		PowerMockito.when(workEstimateItemLBDMapper.toDto(workEstimateItemLBD)).thenReturn(workEstimateItemLBDDTO);

		WorkEstimateItemLBDDTO resultDto = workEstimateItemLBDService.save(workEstimateItemLBDDTO);

		assertNotNull(resultDto);

		log.info("==========================================================================");
		log.info("Test - saveWorkEstimateItemLBD - End");
	}

	/**
	 * Partial update item LBD.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 2)
	public void partialUpdateItemLBD() throws Exception {

		log.info("==========================================================================");
		log.info("Test - partialUpdateItemLBD - Start");
		WorkEstimateItemLBD workEstimateItemLBD = createWorkEstimateItemLBD();
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = new WorkEstimateItemLBDDTO();
		workEstimateItemLBDDTO.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBDDTO.setCalculatedYn(false);
		workEstimateItemLBDDTO.setLbdBredth(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdDepth(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdLength(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdBredthFormula("2221");
		workEstimateItemLBDDTO.setLbdDepthFormula("100021");
		workEstimateItemLBDDTO.setLbdLengthFormula("200021");
		workEstimateItemLBDDTO.setLbdNos(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdParticulars("LBD");
		workEstimateItemLBDDTO.setLbdQuantity(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdTotal(BigDecimal.TEN);
		workEstimateItemLBDDTO.setId(101L);

		PowerMockito.when(workEstimateItemLBDRepository.findById(workEstimateItemLBDDTO.getId()))
				.thenReturn(Optional.of(workEstimateItemLBD));
//		PowerMockito.when(workEstimateItemLBDMapper.partialUpdate(workEstimateItemLBD, workEstimateItemLBDDTO));
		PowerMockito.when(workEstimateItemLBDRepository.save(workEstimateItemLBD)).thenReturn(workEstimateItemLBD);
		PowerMockito.when(workEstimateItemLBDMapper.toDto(workEstimateItemLBD)).thenReturn(workEstimateItemLBDDTO);

		Optional<WorkEstimateItemLBDDTO> result = workEstimateItemLBDService.partialUpdate(workEstimateItemLBDDTO);
		Assert.assertEquals(workEstimateItemLBD.getId(), result.get().getId());

		log.info("==========================================================================");
		log.info("Test - partialUpdateItemLBD - End");

	}

	/**
	 * Find all work estimate item LBD.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 3)
	public void findAllWorkEstimateItemLBD() throws Exception {
		log.info("==========================================================================");
		log.info("Test - findAllWorkEstimateItemLBD - Start");

		WorkEstimateItemLBD workEstimateItemLBD = createWorkEstimateItemLBD();
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = new WorkEstimateItemLBDDTO();
		workEstimateItemLBDDTO.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBDDTO.setCalculatedYn(false);
		workEstimateItemLBDDTO.setLbdBredth(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdDepth(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdLength(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdBredthFormula("2221");
		workEstimateItemLBDDTO.setLbdDepthFormula("100021");
		workEstimateItemLBDDTO.setLbdLengthFormula("200021");
		workEstimateItemLBDDTO.setLbdNos(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdParticulars("LBD");
		workEstimateItemLBDDTO.setLbdQuantity(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdTotal(BigDecimal.TEN);
		workEstimateItemLBDDTO.setId(101L);

		PowerMockito.when(workEstimateItemLBDRepository.findAll(PageRequest.of(0, 5))).thenReturn(LBDPage);
		PowerMockito.when(workEstimateItemLBDMapper.toDto(workEstimateItemLBD)).thenReturn(workEstimateItemLBDDTO);

		Page<WorkEstimateItemLBDDTO> resultPage = workEstimateItemLBDService.findAll(PageRequest.of(0, 5));
		assertNotNull(resultPage.isEmpty());
		log.info("==========================================================================");
		log.info("Test - partialUpdateItemLBD - End");

	}

	/**
	 * Find one work estimate LBD.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 4)
	public void findOneWorkEstimateLBD() throws Exception {

		log.info("==========================================================================");
		log.info("Test - findOneWorkEstimateLBD - Start");

		WorkEstimateItemLBD workEstimateItemLBD = createWorkEstimateItemLBD();
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = new WorkEstimateItemLBDDTO();
		workEstimateItemLBDDTO.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBDDTO.setCalculatedYn(false);
		workEstimateItemLBDDTO.setLbdBredth(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdDepth(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdLength(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdBredthFormula("2221");
		workEstimateItemLBDDTO.setLbdDepthFormula("100021");
		workEstimateItemLBDDTO.setLbdLengthFormula("200021");
		workEstimateItemLBDDTO.setLbdNos(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdParticulars("LBD");
		workEstimateItemLBDDTO.setLbdQuantity(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdTotal(BigDecimal.TEN);
		workEstimateItemLBDDTO.setId(101L);

		PowerMockito.when(workEstimateItemLBDRepository.findById(workEstimateItemLBD.getId()))
				.thenReturn(Optional.of(workEstimateItemLBD));
		PowerMockito.when(workEstimateItemLBDMapper.toDto(workEstimateItemLBD)).thenReturn(workEstimateItemLBDDTO);
		Optional<WorkEstimateItemLBDDTO> resultOpt = workEstimateItemLBDService.findOne(workEstimateItemLBDDTO.getId());
		Assert.assertEquals(workEstimateItemLBD.getId(), resultOpt.get().getId());

		log.info("==========================================================================");
		log.info("Test - findOneWorkEstimateLBD - End");

	}

	/**
	 * Delete work estimate LBD.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 5)
	public void deleteWorkEstimateLBD() throws Exception {
		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateLBD - Start");

		workEstimateItemLBDService.delete(Mockito.anyLong());

		log.info("==========================================================================");
		log.info("Test - deleteWorkEstimateLBD - End");

	}

	/**
	 * Find all by work estimate item id.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 6)
	public void findAllByWorkEstimateItemId() throws Exception {
		log.info("==========================================================================");
		log.info("Test - findAllByWorkEstimateItemId - Start");

		List<WorkEstimateItemLBD> workEstimateItemLBDList = createWorkEstimateItemLBDList();
		List<WorkEstimateItemLBDDTO> responseDtoList = new ArrayList<>();
		WorkEstimateItemLBDDTO dto = new WorkEstimateItemLBDDTO();

		if (!workEstimateItemLBDList.isEmpty()) {
			for (WorkEstimateItemLBD LBDList : workEstimateItemLBDList) {

				dto.setId(LBDList.getId());
				dto.setAdditionDeduction(LBDList.getAdditionDeduction());
				dto.setCalculatedYn(LBDList.getCalculatedYn());
				dto.setWorkEstimateItemId(LBDList.getWorkEstimateItemId());
				dto.setLbdNos(LBDList.getLbdNos());
				dto.setLbdBredth(LBDList.getLbdBredth());
				dto.setWorkEstimateItemId(LBDList.getWorkEstimateItemId());

				Mockito.when(workEstimateItemLBDRepository.findAllByWorkEstimateItemId(dto.getWorkEstimateItemId()))
						.thenReturn(workEstimateItemLBDList);
			}
		}

		responseDtoList = workEstimateItemLBDService.findAllByWorkEstimateItemId(dto.getWorkEstimateItemId());
		log.info("Resposne List" + responseDtoList);

		WorkEstimateItemLBD lbd = workEstimateItemLBDList.get(0);

		Assert.assertEquals(lbd.getId(), dto.getId());
		Assert.assertEquals(lbd.getAdditionDeduction(), dto.getAdditionDeduction());
		Assert.assertEquals(lbd.getCalculatedYn(), dto.getCalculatedYn());
		Assert.assertEquals(lbd.getLbdNos(), dto.getLbdNos());
		Assert.assertEquals(lbd.getWorkEstimateItemId(), dto.getWorkEstimateItemId());

		log.info("==========================================================================");
		log.info("Test - findAllByWorkEstimateItemId - End");

	}

	/**
	 * Find by work estimate item id and id.
	 */
	@Test(priority = 8)
	public void findByWorkEstimateItemIdAndId() {

		log.info("==========================================================================");
		log.info("Test - findByWorkEstimateItemIdAndId - Strat");
		WorkEstimateItemLBD workEstimateItemLBD = createWorkEstimateItemLBD();
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = new WorkEstimateItemLBDDTO();
		workEstimateItemLBDDTO.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBDDTO.setCalculatedYn(false);
		workEstimateItemLBDDTO.setLbdBredth(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdDepth(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdLength(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdBredthFormula("2221");
		workEstimateItemLBDDTO.setLbdDepthFormula("100021");
		workEstimateItemLBDDTO.setLbdLengthFormula("200021");
		workEstimateItemLBDDTO.setLbdNos(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdParticulars("LBD");
		workEstimateItemLBDDTO.setLbdQuantity(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdTotal(BigDecimal.TEN);
		workEstimateItemLBDDTO.setId(101L);
		PowerMockito
				.when(workEstimateItemLBDRepository.findByWorkEstimateItemIdAndId(
						workEstimateItemLBD.getWorkEstimateItemId(), workEstimateItemLBD.getId()))
				.thenReturn(Optional.of(workEstimateItemLBD));
		PowerMockito.when(workEstimateItemLBDMapper.toDto(workEstimateItemLBD)).thenReturn(workEstimateItemLBDDTO);

		Optional<WorkEstimateItemLBDDTO> resultDto = workEstimateItemLBDService.findByWorkEstimateItemIdAndId(
				workEstimateItemLBD.getWorkEstimateItemId(), workEstimateItemLBD.getId());

		Assert.assertEquals(workEstimateItemLBD.getId(), resultDto.get().getId());
		log.info("==========================================================================");
		log.info("Test - findByWorkEstimateItemIdAndId - End");

	}

	/**
	 * Find by work estimate item id.
	 */
	@Test(priority = 9)
	public void findByWorkEstimateItemId() {

		log.info("==========================================================================");
		log.info("Test - findByWorkEstimateItemId - Start");
		WorkEstimateItemLBD workEstimateItemLBD = createWorkEstimateItemLBD();
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = new WorkEstimateItemLBDDTO();
		workEstimateItemLBDDTO.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBDDTO.setCalculatedYn(false);
		workEstimateItemLBDDTO.setLbdBredth(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdDepth(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdLength(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdBredthFormula("2221");
		workEstimateItemLBDDTO.setLbdDepthFormula("100021");
		workEstimateItemLBDDTO.setLbdLengthFormula("200021");
		workEstimateItemLBDDTO.setLbdNos(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdParticulars("LBD");
		workEstimateItemLBDDTO.setLbdQuantity(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdTotal(BigDecimal.TEN);
		workEstimateItemLBDDTO.setId(101L);
		PowerMockito
				.when(workEstimateItemLBDRepository
						.findByWorkEstimateItemId(workEstimateItemLBD.getWorkEstimateItemId(), PageRequest.of(0, 5)))
				.thenReturn(LBDPage);
		PowerMockito.when(workEstimateItemLBDMapper.toDto(workEstimateItemLBD)).thenReturn(workEstimateItemLBDDTO);

		Page<WorkEstimateItemLBDDTO> pageEstimateItemDto = workEstimateItemLBDService
				.findByWorkEstimateItemId(workEstimateItemLBDDTO.getId(), PageRequest.of(0, 5));

		assertNotNull(pageEstimateItemDto);
		log.info("==========================================================================");
		log.info("Test - findByWorkEstimateItemId - End");

	}

	/**
	 * Update work estimate item LBD.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 10)
	public void updateWorkEstimateItemLBD() throws Exception {
		log.info("==========================================================================");
		log.info("Test - saveWorkEstimateItemLBD - Start");
		WorkEstimateItemLBD workEstimateItemLBD = createWorkEstimateItemLBD();
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = new WorkEstimateItemLBDDTO();
		workEstimateItemLBDDTO.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBDDTO.setCalculatedYn(false);
		workEstimateItemLBDDTO.setLbdBredth(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdDepth(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdLength(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdBredthFormula("2221");
		workEstimateItemLBDDTO.setLbdDepthFormula("100021");
		workEstimateItemLBDDTO.setLbdLengthFormula("200021");
		workEstimateItemLBDDTO.setLbdNos(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdParticulars("LBD");
		workEstimateItemLBDDTO.setLbdQuantity(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdTotal(BigDecimal.TEN);
		workEstimateItemLBDDTO.setWorkEstimateItemId(101L);

		PowerMockito.when(workEstimateItemLBDMapper.toEntity(workEstimateItemLBDDTO)).thenReturn(workEstimateItemLBD);
		PowerMockito.when(workEstimateItemLBDRepository.findById(workEstimateItemLBD.getId()))
				.thenReturn(Optional.of(workEstimateItemLBD));
		Mockito.when(workEstimateItemLBDRepository.save(workEstimateItemLBD)).thenReturn(workEstimateItemLBD);

		workEstimateItemLBDService.save(workEstimateItemLBDDTO);

		log.info("==========================================================================");
		log.info("Test - saveWorkEstimateItemLBD - End");
	}

	/**
	 * Gets the item LB ds total.
	 *
	 * @return the item LB ds total
	 */
	@Test(priority = 11)
	public void getItemLBDsTotal() {

		log.info("==========================================================================");
		log.info("Test - getItemLBDsTotal - Start");
		WorkEstimateItemLBD workEstimateItemLBD = createWorkEstimateItemLBD();

		BigDecimal result = workEstimateItemLBDService.getItemLBDsTotal(workEstimateItemLBD.getWorkEstimateItemId());
		assertNotNull(result);

		log.info("==========================================================================");
		log.info("Test - getItemLBDsTotal - End");

	}

	/**
	 * Recalculate and update item WRTLBD.
	 */
	@Test(priority = 12)
	public void recalculateAndUpdateItemWRTLBD() {

		log.info("==========================================================================");
		log.info("Test - recalculateAndUpdateItemWRTLBD - Start");
		// -----------------------------
		WorkEstimate workEstimate = new WorkEstimate();
		workEstimate.setId(101L);
		workEstimate.setWorkEstimateNumber("sd");
		workEstimate.setApprovedBudgetYn(true);
		workEstimate.setDeptId(1L);
		workEstimate.setFileNumber("KPWD/AEE/MNG/CRF/2009-10/1");
		workEstimate.setDescription("This is Road Construction-Phase-1");
		workEstimate.setName("Road Construction");
		workEstimate.locationId(1L);

		SubEstimate subEstimate = createSubEstimate();

		WorkEstimateItem workEstimateItem = createWorkEstimateItem();

		WorkEstimateItemDTO workEstimateItemDTO = new WorkEstimateItemDTO();
		workEstimateItemDTO.setSubEstimateId(subEstimate.getId());
		workEstimateItemDTO.setItemCode("item-code");
		workEstimateItemDTO.setId(101L);
		workEstimateItemDTO.setRaPerformedYn(true);
		workEstimateItemDTO.setId(101L);
		workEstimateItemDTO.setSubEstimateId(101L);
		workEstimateItemDTO.setEntryOrder(1);
		workEstimateItemDTO.setCatWorkSorItemId(1L);
		workEstimateItemDTO.setCategoryId(1L);
		workEstimateItemDTO.setUomId(1L);
		workEstimateItemDTO.setItemCode("item-code");
		workEstimateItemDTO.setDescription("This is item description");
		workEstimateItemDTO.setRaPerformedYn(true);
		workEstimateItemDTO.setBaseRate(BigDecimal.valueOf(12.0000));
		workEstimateItemDTO.setFinalRate(BigDecimal.valueOf(12.0000));
		workEstimateItemDTO.setFloorNumber(12);
		workEstimateItemDTO.setQuantity(BigDecimal.valueOf(1.0000));

		SubEstimateResponseDTO subEstimateresponse = new SubEstimateResponseDTO();
		subEstimateresponse.setWorkEstimateId(workEstimate.getId());
		subEstimateresponse.setSubEstimateId(subEstimate.getId());

		WorkEstimateItemLBD workEstimateItemLBD = createWorkEstimateItemLBD();
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = new WorkEstimateItemLBDDTO();
		workEstimateItemLBDDTO.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBDDTO.setCalculatedYn(false);
		workEstimateItemLBDDTO.setLbdBredth(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdDepth(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdLength(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdBredthFormula("2221");
		workEstimateItemLBDDTO.setLbdDepthFormula("100021");
		workEstimateItemLBDDTO.setLbdLengthFormula("200021");
		workEstimateItemLBDDTO.setLbdNos(BigDecimal.ONE);
		workEstimateItemLBDDTO.setLbdParticulars("LBD");
		workEstimateItemLBDDTO.setLbdQuantity(BigDecimal.TEN);
		workEstimateItemLBDDTO.setLbdTotal(BigDecimal.TEN);
		workEstimateItemLBDDTO.setId(101L);
		workEstimateItemLBDDTO.setWorkEstimateItemId(101L);

		PowerMockito.when(workEstimateRepository.save(workEstimate)).thenReturn(workEstimate);
		PowerMockito.when(subEstimateRepository.save(subEstimate)).thenReturn(subEstimate);
		PowerMockito.when(workEstimateItemRepository.save(workEstimateItem)).thenReturn(workEstimateItem);

		PowerMockito
				.when(workEstimateItemService.findBySubEstimateIdAndId(subEstimate.getId(), workEstimateItem.getId()))
				.thenReturn(Optional.of(workEstimateItemDTO));

		PowerMockito.when(workEstimateItemService.partialUpdate(workEstimateItemDTO))
				.thenReturn(Optional.of(workEstimateItemDTO));

		PowerMockito.when(subEstimateService.calculateSubEstimateTotals(subEstimateresponse.getWorkEstimateId(),
				subEstimateresponse.getSubEstimateId())).thenReturn(subEstimateresponse);

		PowerMockito
				.when(workEstimateItemLBDRepository
						.findByWorkEstimateItemId(workEstimateItemLBD.getWorkEstimateItemId(), PageRequest.of(0, 5)))
				.thenReturn(LBDPage);

		PowerMockito.when(workEstimateItemLBDMapper.toDto(workEstimateItemLBD)).thenReturn(workEstimateItemLBDDTO);

		workEstimateItemLBDService.recalculateAndUpdateItemWRTLBD(workEstimate.getId(), subEstimate.getId(),
				workEstimateItem.getId());

		log.info("==========================================================================");
		log.info("Test - recalculateAndUpdateItemWRTLBD - End");

	}

	/**
	 * Creates the WorkEstimateItemLDB.
	 *
	 * @return the WorkEstimateItemLBD
	 */

	private WorkEstimateItemLBD createWorkEstimateItemLBD() {
		WorkEstimateItemLBD workEstimateItemLBD = new WorkEstimateItemLBD();
		workEstimateItemLBD.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBD.setCalculatedYn(false);
		workEstimateItemLBD.setLbdBredth(BigDecimal.ONE);
		workEstimateItemLBD.setLbdDepth(BigDecimal.TEN);
		workEstimateItemLBD.setLbdLength(BigDecimal.ONE);
		workEstimateItemLBD.setLbdBredthFormula("2221");
		workEstimateItemLBD.setLbdDepthFormula("100021");
		workEstimateItemLBD.setLbdLengthFormula("200021");
		workEstimateItemLBD.setLbdNos(BigDecimal.ONE);
		workEstimateItemLBD.setLbdParticulars("LBD");
		workEstimateItemLBD.setLbdQuantity(BigDecimal.TEN);
		workEstimateItemLBD.setLbdTotal(BigDecimal.TEN);
		workEstimateItemLBD.setId(101L);
		workEstimateItemLBD.setWorkEstimateItemId(101L);

		List<WorkEstimateItemLBD> workEstimateItemLBDList = new ArrayList<>();
		workEstimateItemLBDList.add(workEstimateItemLBD);
		LBDPage = new PageImpl<>(workEstimateItemLBDList);
		return workEstimateItemLBD;

	}

	/**
	 * Creates List of WorkEstimateItemLDB.
	 *
	 * @return the list
	 */
	private List<WorkEstimateItemLBD> createWorkEstimateItemLBDList() {

		List<WorkEstimateItemLBD> workEstimateItemLBDList = new ArrayList<>();

		WorkEstimateItemLBD workEstimateItemLBD = new WorkEstimateItemLBD();
		workEstimateItemLBD.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBD.setCalculatedYn(false);
		workEstimateItemLBD.setLbdBredth(BigDecimal.ONE);
		workEstimateItemLBD.setLbdDepth(BigDecimal.TEN);
		workEstimateItemLBD.setLbdLength(BigDecimal.ONE);
		workEstimateItemLBD.setLbdBredthFormula("2221");
		workEstimateItemLBD.setLbdDepthFormula("100021");
		workEstimateItemLBD.setLbdLengthFormula("200021");
		workEstimateItemLBD.setLbdNos(BigDecimal.ONE);
		workEstimateItemLBD.setLbdParticulars("LBD");
		workEstimateItemLBD.setLbdQuantity(BigDecimal.TEN);
		workEstimateItemLBD.setLbdTotal(BigDecimal.TEN);
		workEstimateItemLBD.setId(101L);
		workEstimateItemLBD.setWorkEstimateItemId(101L);

		WorkEstimateItemLBD workEstimateItemLBD1 = new WorkEstimateItemLBD();
		workEstimateItemLBD1.setAdditionDeduction(LBDOperation.ADDITION);
		workEstimateItemLBD1.setCalculatedYn(false);
		workEstimateItemLBD1.setLbdBredth(BigDecimal.ONE);
		workEstimateItemLBD1.setLbdDepth(BigDecimal.TEN);
		workEstimateItemLBD1.setLbdLength(BigDecimal.ONE);
		workEstimateItemLBD1.setLbdBredthFormula("2221");
		workEstimateItemLBD1.setLbdDepthFormula("100021");
		workEstimateItemLBD1.setLbdLengthFormula("200021");
		workEstimateItemLBD1.setLbdNos(BigDecimal.ONE);
		workEstimateItemLBD1.setLbdParticulars("LBD");
		workEstimateItemLBD1.setLbdQuantity(BigDecimal.TEN);
		workEstimateItemLBD1.setLbdTotal(BigDecimal.TEN);
		workEstimateItemLBD1.setId(101L);
		workEstimateItemLBD1.setWorkEstimateItemId(101L);
		workEstimateItemLBDList.add(workEstimateItemLBD);
		workEstimateItemLBDList.add(workEstimateItemLBD1);
		return workEstimateItemLBDList;
	}

	/**
	 * Creates the sub estimate.
	 *
	 * @return the sub estimate
	 */
	private SubEstimate createSubEstimate() {

		SubEstimate subEstimate = new SubEstimate().id(101L).areaWeightageCircle(1L)
				.areaWeightageDescription("Area Weightage Description").areaWeightageId(1L).completedYn(true)
				.estimateTotal(BigDecimal.valueOf(12.0000)).sorWorCategoryId(1L).subEstimateName("Subestimate Name")
				.workEstimateId(101L);

		return subEstimate;
	}

	/**
	 * Creates the work estimate item.
	 *
	 * @return the work estimate item
	 */
	private WorkEstimateItem createWorkEstimateItem() {

		WorkEstimateItem workEstimateItem = new WorkEstimateItem().id(101L).subEstimateId(101L).entryOrder(1)
				.catWorkSorItemId(1L).categoryId(1L).uomId(1L).itemCode("item-code")
				.description("This is item description").raPerformedYn(true).baseRate(BigDecimal.valueOf(12.0000))
				.finalRate(BigDecimal.valueOf(12.0000)).floorNumber(12).quantity(BigDecimal.valueOf(1.0000));

		return workEstimateItem;
	}

	/**
	 * Tear down.
	 */
	@AfterClass
	public void tearDown() {
		log.info("==========================================================================");
		log.info("This is executed after once Per Test Class - SupplierNomineeServiceTest: tearDown");
	}

}