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

import com.dxc.eproc.estimate.model.LineEstimate;
import com.dxc.eproc.estimate.repository.LineEstimateRepository;
import com.dxc.eproc.estimate.service.dto.LineEstimateDTO;
import com.dxc.eproc.estimate.service.impl.LineEstimateServiceImpl;
import com.dxc.eproc.estimate.service.mapper.LineEstimateMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class LineEstimateServiceTest.
 */
public class LineEstimateServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(LineEstimateServiceTest.class);

	/** The line estimate. */
	private static LineEstimate lineEstimate;

	/** The line estimate DTO. */
	private static LineEstimateDTO lineEstimateDTO;

	/** The line estimate page. */
	private static Page<LineEstimate> lineEstimatePage;

	/** The line estimate service. */
	@InjectMocks
	private LineEstimateServiceImpl lineEstimateService;

	/** The line estimate repository. */
	@Mock
	private LineEstimateRepository lineEstimateRepository;

	/** The line estimate mapper. */
	@Mock
	private LineEstimateMapper lineEstimateMapper;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {

		lineEstimate = new LineEstimate();
		lineEstimate.setId(1L);
		lineEstimate.name("Flyover Maintainience");
		lineEstimate.approxRate(BigDecimal.valueOf(30.0000));

		lineEstimateDTO = new LineEstimateDTO();
		BeanUtils.copyProperties(lineEstimate, lineEstimateDTO);
		List<LineEstimate> lineEstimateList = new ArrayList<>();
		lineEstimateList.add(lineEstimate);
		lineEstimatePage = new PageImpl<>(lineEstimateList);

	}

	/**
	 * Common mocking.
	 */
	@BeforeMethod
	public void commonMocking() {
		PowerMockito.when(lineEstimateMapper.toEntity(lineEstimateDTO)).thenReturn(lineEstimate);
		PowerMockito.when(lineEstimateMapper.toDto(lineEstimate)).thenReturn(lineEstimateDTO);

	}

	/**
	 * Save test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveTest() throws Exception {

		PowerMockito.when(lineEstimateRepository.findById(lineEstimateDTO.getId()))
				.thenReturn(Optional.of(lineEstimate));

		PowerMockito.when(lineEstimateRepository.save(lineEstimate)).thenReturn(lineEstimate);

		LineEstimateDTO result = lineEstimateService.save(lineEstimateDTO);
		Assert.assertNotNull(result.getId());
		log.info("saveTest successful!");
	}

	/**
	 * Save null test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveNullTest() throws Exception {

		LineEstimateDTO lineEstimateDTO2 = new LineEstimateDTO();
		BeanUtils.copyProperties(lineEstimateDTO, lineEstimateDTO2);
		lineEstimateDTO2.setId(null);
		PowerMockito.when(lineEstimateMapper.toEntity(lineEstimateDTO2)).thenReturn(lineEstimate);
		PowerMockito.when(lineEstimateRepository.save(lineEstimate)).thenReturn(lineEstimate);

		LineEstimateDTO result = lineEstimateService.save(lineEstimateDTO2);
		Assert.assertNotNull(result);
		log.info("saveTest successful!");
	}

	/**
	 * Partial update test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void partialUpdateTest() throws Exception {
		PowerMockito.when(lineEstimateRepository.save(lineEstimate)).thenReturn(lineEstimate);

		PowerMockito.when(lineEstimateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(lineEstimate));

		Optional<LineEstimateDTO> result = lineEstimateService.partialUpdate(lineEstimateDTO);
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
		PowerMockito.when(lineEstimateRepository.findAll(PageRequest.of(0, 5))).thenReturn(lineEstimatePage);

		Page<LineEstimateDTO> result = lineEstimateService.findAll(PageRequest.of(0, 5));
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
		PowerMockito.when(lineEstimateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(lineEstimate));

		Optional<LineEstimateDTO> result = lineEstimateService.findOne(Mockito.anyLong());
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
		PowerMockito.when(lineEstimateRepository.findByWorkEstimateIdAndId(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(Optional.of(lineEstimate));

		Optional<LineEstimateDTO> result = lineEstimateService.findByWorkEstimateIdAndId(Mockito.anyLong(),
				Mockito.anyLong());
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

		List<LineEstimate> lineEstimatelist = new ArrayList<>();
		PowerMockito.when(lineEstimateRepository.findAllByWorkEstimateId(Mockito.anyLong()))
				.thenReturn(lineEstimatelist);

		List<LineEstimateDTO> result = lineEstimateService.findAllByWorkEstimateId(Mockito.anyLong());
		Assert.assertTrue(result.isEmpty());
		log.info("findAllByWorkEstimateIdTest successful!");
	}

	/**
	 * Gets the all line estimates by work estimate id test.
	 *
	 * @return the all line estimates by work estimate id test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllLineEstimatesByWorkEstimateIdTest() throws Exception {

		PowerMockito
				.when(lineEstimateRepository.findByWorkEstimateIdOrderByLastModifiedTsDesc(1L, PageRequest.of(0, 5)))
				.thenReturn(lineEstimatePage);

		Page<LineEstimateDTO> result = lineEstimateService.getAllLineEstimatesByWorkEstimateId(1L,
				PageRequest.of(0, 5));
		Assert.assertFalse(result.isEmpty());

		log.info("getAllLineEstimatesByWorkEstimateIdTest successful!");
	}

	/**
	 * Delete test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteTest() throws Exception {
		lineEstimateService.delete(Mockito.anyLong());
		log.info("deleteTest successful!");
	}

	/**
	 * Sum approximate value by line estimate id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void sumApproximateValueByLineEstimateIdTest() throws Exception {

		PowerMockito.when(lineEstimateRepository.sumApproximateValueByLineEstimateId(Mockito.anyLong()))
				.thenReturn(BigDecimal.TEN);
		BigDecimal result = lineEstimateService.sumApproximateValueByLineEstimateId(Mockito.anyLong());
		Assert.assertEquals(result, BigDecimal.TEN);
		log.info("sumApproximateValueByLineEstimateIdTest successful!");
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
