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
import com.dxc.eproc.estimate.model.OverHead;
import com.dxc.eproc.estimate.repository.OverHeadRepository;
import com.dxc.eproc.estimate.service.dto.OverHeadDTO;
import com.dxc.eproc.estimate.service.impl.OverHeadServiceImpl;
import com.dxc.eproc.estimate.service.mapper.OverHeadMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class OverHeadServiceTest.
 */
public class OverHeadServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(OverHeadServiceTest.class);

	/** The over head. */
	private static OverHead overHead;
	
	/** The over head DTO. */
	private static OverHeadDTO overHeadDTO;
	
	/** The over head page. */
	private static Page<OverHead> overHeadPage;

	/** The over head service. */
	@InjectMocks
	private OverHeadServiceImpl overHeadService;

	/** The over head repository. */
	@Mock
	private OverHeadRepository overHeadRepository;

	/** The over head mapper. */
	@Mock
	private OverHeadMapper overHeadMapper;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {

		overHead = new OverHead();
		overHead.setId(1L);
		overHead.setOverHeadName("Environment Impacts");
		overHead.setOverHeadType(OverHeadType.NORMAL);
		overHead.activeYn(true);

		overHeadDTO = new OverHeadDTO();
		BeanUtils.copyProperties(overHead, overHeadDTO);
		List<OverHead> overHeadList = new ArrayList<>();
		overHeadList.add(overHead);
		overHeadPage = new PageImpl<>(overHeadList);

	}

	/**
	 * Common mocking.
	 */
	@BeforeMethod
	public void commonMocking() {
		PowerMockito.when(overHeadMapper.toEntity(overHeadDTO))
				.thenReturn(overHead);
		PowerMockito.when(overHeadMapper.toDto(overHead)).thenReturn(overHeadDTO);

	}

	/**
	 * Save test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveTest() throws Exception {

		PowerMockito.when(overHeadRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(overHead));

		PowerMockito.when(overHeadRepository.save(Mockito.any())).thenReturn(overHead);

		OverHeadDTO result = overHeadService.save(overHeadDTO);
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
		PowerMockito.when(overHeadRepository.save(overHead)).thenReturn(overHead);

		PowerMockito.when(overHeadRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(overHead));

		Optional<OverHeadDTO> result = overHeadService.partialUpdate(overHeadDTO);
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
		PowerMockito.when(overHeadRepository.findAll(PageRequest.of(0, 5)))
				.thenReturn(overHeadPage);

		Page<OverHeadDTO> result = overHeadService.findAll(PageRequest.of(0, 5));
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
		PowerMockito.when(overHeadRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(overHead));

		Optional<OverHeadDTO> result = overHeadService.findOne(Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findOneTest successful!");
	}

	/**
	 * Find all by active yn test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void findAllByActiveYnTest() throws Exception {
		
		PowerMockito.when(overHeadRepository.findAllByActiveYn(true,PageRequest.of(0, 5)))
		.thenReturn(overHeadPage);

        Page<OverHeadDTO> result = overHeadService.findAllActive(PageRequest.of(0, 5));
        Assert.assertFalse(result.isEmpty());
		log.info("findAllByActiveYnTest successful!");
	}

	/**
	 * Save null test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveNullTest() throws Exception {

		OverHeadDTO overHeadDTO2 = new OverHeadDTO();
		BeanUtils.copyProperties(overHeadDTO, overHeadDTO2);
		overHeadDTO2.setId(null);
		PowerMockito.when(overHeadMapper.toEntity(overHeadDTO2))
				.thenReturn(overHead);
		PowerMockito.when(overHeadRepository.save(overHead)).thenReturn(overHead);

		OverHeadDTO result = overHeadService.save(overHeadDTO2);
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
		overHeadService.delete(Mockito.anyLong());
		log.info("deleteTest successful!");
	}

	/**
	 * Tear down.
	 */
	@AfterClass
	public void tearDown() {
		log.info("==========================================================================");
		log.info("This is executed after once Per Test Class - OverHeadServiceTest: tearDown");
	}	
	
}
