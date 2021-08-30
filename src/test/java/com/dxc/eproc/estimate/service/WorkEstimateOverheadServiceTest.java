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
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.enumeration.OverHeadType;
import com.dxc.eproc.estimate.model.WorkEstimateOverhead;
import com.dxc.eproc.estimate.repository.WorkEstimateOverheadRepository;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOverheadDTO;
import com.dxc.eproc.estimate.service.impl.WorkEstimateOverheadServiceImpl;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateOverheadMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateOverheadServiceTest.
 */
public class WorkEstimateOverheadServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WorkEstimateOverheadServiceTest.class);

	/** The work estimate overhead. */
	private static WorkEstimateOverhead workEstimateOverhead;

	/** The work estimate overhead DTO. */
	private static WorkEstimateOverheadDTO workEstimateOverheadDTO;

	/** The work estimate overhead page. */
	private static Page<WorkEstimateOverhead> workEstimateOverheadPage;

	/** The work estimate overhead service. */
	@InjectMocks
	private WorkEstimateOverheadServiceImpl workEstimateOverheadService;

	/** The work estimate overhead repository. */
	@Mock
	private WorkEstimateOverheadRepository workEstimateOverheadRepository;

	/** The work estimate overhead mapper. */
	@Mock
	private WorkEstimateOverheadMapper workEstimateOverheadMapper;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {

		workEstimateOverhead = new WorkEstimateOverhead();
		workEstimateOverhead.setId(1L);
		workEstimateOverhead.setWorkEstimateId(1L);
		workEstimateOverhead.setName("Environment Impacts");
		workEstimateOverhead.overHeadType(OverHeadType.ADDITIONAL);
		workEstimateOverhead.overHeadValue(BigDecimal.TEN);
		workEstimateOverhead.fixedYn(true);

		workEstimateOverheadDTO = new WorkEstimateOverheadDTO();
		BeanUtils.copyProperties(workEstimateOverhead, workEstimateOverheadDTO);
		List<WorkEstimateOverhead> workEstimateOverheadList = new ArrayList<>();
		workEstimateOverheadList.add(workEstimateOverhead);
		workEstimateOverheadPage = new PageImpl<>(workEstimateOverheadList);

	}

	/**
	 * Common mocking.
	 */
	@BeforeMethod
	public void commonMocking() {
		PowerMockito.when(workEstimateOverheadMapper.toEntity(workEstimateOverheadDTO))
				.thenReturn(workEstimateOverhead);
		PowerMockito.when(workEstimateOverheadMapper.toDto(workEstimateOverhead)).thenReturn(workEstimateOverheadDTO);

	}

	/**
	 * Save test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveTest() throws Exception {

		PowerMockito.when(workEstimateOverheadRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(workEstimateOverhead));

		PowerMockito.when(workEstimateOverheadRepository.save(Mockito.any())).thenReturn(workEstimateOverhead);

		WorkEstimateOverheadDTO result = workEstimateOverheadService.save(workEstimateOverheadDTO);
		Assert.assertNotNull(result.getId());
		log.info("saveTest successful!");
	}

	/**
	 * Partial update test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void partialUpdateTest() throws Exception {
		PowerMockito.when(workEstimateOverheadRepository.save(workEstimateOverhead)).thenReturn(workEstimateOverhead);

		PowerMockito.when(workEstimateOverheadRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(workEstimateOverhead));

		Optional<WorkEstimateOverheadDTO> result = workEstimateOverheadService.partialUpdate(workEstimateOverheadDTO);
		Assert.assertTrue(result.isPresent());
		log.info("partialUpdateTest successful!");
	}

	/**
	 * Find all test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void findAllTest() throws Exception {
		PowerMockito.when(workEstimateOverheadRepository.findAll(PageRequest.of(0, 5)))
				.thenReturn(workEstimateOverheadPage);

		Page<WorkEstimateOverheadDTO> result = workEstimateOverheadService.findAll(PageRequest.of(0, 5));
		Assert.assertFalse(result.isEmpty());
		log.info("findAllTest successful!");
	}

	/**
	 * Find one test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void findOneTest() throws Exception {
		PowerMockito.when(workEstimateOverheadRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(workEstimateOverhead));

		Optional<WorkEstimateOverheadDTO> result = workEstimateOverheadService.findOne(Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findOneTest successful!");
	}

	/**
	 * Find by work estimate id and id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void findByWorkEstimateIdAndIdTest() throws Exception {
		PowerMockito
				.when(workEstimateOverheadRepository.findByWorkEstimateIdAndId(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(Optional.of(workEstimateOverhead));

		Optional<WorkEstimateOverheadDTO> result = workEstimateOverheadService
				.findByWorkEstimateIdAndId(Mockito.anyLong(), Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findByWorkEstimateIdAndIdTest successful!");
	}

	/**
	 * Find all by work estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void findAllByWorkEstimateIdTest() throws Exception {

		List<WorkEstimateOverhead> workEstimateOverheadlist = new ArrayList<>();
		PowerMockito.when(workEstimateOverheadRepository.findAllByWorkEstimateId(Mockito.anyLong()))
				.thenReturn(workEstimateOverheadlist);

		List<WorkEstimateOverheadDTO> result = workEstimateOverheadService.findAllByWorkEstimateId(Mockito.anyLong());
		Assert.assertTrue(result.isEmpty());
		log.info("findAllByWorkEstimateIdTest successful!");
	}

	/**
	 * Gets the all work estimate overheads by work estimate id test.
	 *
	 * @return the all work estimate overheads by work estimate id test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkEstimateOverheadsByWorkEstimateIdTest() throws Exception {

		PowerMockito.when(
				workEstimateOverheadRepository.findByWorkEstimateIdOrderByLastModifiedTsDesc(1L, PageRequest.of(0, 5)))
				.thenReturn(workEstimateOverheadPage);

		Page<WorkEstimateOverheadDTO> result = workEstimateOverheadService
				.getAllWorkEstimateOverheadsByWorkEstimateId(1L, PageRequest.of(0, 5));
		Assert.assertFalse(result.isEmpty());

		log.info("getAllWorkEstimateOverheadsByWorkEstimateIdTest successful!");
	}

	/**
	 * Sum normal over head by work estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void sumNormalOverHeadByWorkEstimateIdTest() throws Exception {

		PowerMockito.when(workEstimateOverheadRepository.sumNormalOverHeadByWorkEstimateId(Mockito.anyLong()))
				.thenReturn(BigDecimal.TEN);
		BigDecimal result = workEstimateOverheadService.sumNormalOverHeadByWorkEstimateId(Mockito.anyLong());
		Assert.assertEquals(result, BigDecimal.TEN);
		log.info("sumNormalOverHeadByWorkEstimateIdTest successful!");
	}

	/**
	 * Sum additional over head by work estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void sumAdditionalOverHeadByWorkEstimateIdTest() throws Exception {

		PowerMockito.when(workEstimateOverheadRepository.sumAdditionalOverHeadByWorkEstimateId(Mockito.anyLong()))
				.thenReturn(null);
		BigDecimal result = workEstimateOverheadService.sumAdditionalOverHeadByWorkEstimateId(Mockito.anyLong());
		Assert.assertEquals(result, null);
		log.info("sumAdditionalOverHeadByWorkEstimateIdTest successful!");
	}

	/**
	 * Save null test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveNullTest() throws Exception {

		WorkEstimateOverheadDTO workEstimateOverheadDTO2 = new WorkEstimateOverheadDTO();
		BeanUtils.copyProperties(workEstimateOverheadDTO, workEstimateOverheadDTO2);
		workEstimateOverheadDTO2.setId(null);
		PowerMockito.when(workEstimateOverheadMapper.toEntity(workEstimateOverheadDTO2))
				.thenReturn(workEstimateOverhead);
		PowerMockito.when(workEstimateOverheadRepository.save(workEstimateOverhead)).thenReturn(workEstimateOverhead);

		WorkEstimateOverheadDTO result = workEstimateOverheadService.save(workEstimateOverheadDTO2);
		Assert.assertNotNull(result);
		log.info("saveTest successful!");
	}

	/**
	 * Delete test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteTest() throws Exception {
		workEstimateOverheadService.delete(Mockito.anyLong());
		log.info("deleteTest successful!");
	}

	/**
	 * Tear down.
	 */
	@AfterClass
	public void tearDown() {
		log.info("==========================================================================");
		log.info("This is executed after once Per Test Class - WorkEstimateServiceTest: tearDown");
	}
}
