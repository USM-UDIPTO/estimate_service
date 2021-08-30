package com.dxc.eproc.estimate.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.enumeration.ValueType;
import com.dxc.eproc.estimate.model.WorkSubEstimateGroupOverhead;

/**
 * The Class WorkSubEstimateGroupOverheadRepositoryTest.
 */
@SpringBootTest
@ActiveProfiles("test")
public class WorkSubEstimateGroupOverheadRepositoryTest extends AbstractTestNGSpringContextTests {

	private static final Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupOverheadRepositoryTest.class);

	private static WorkSubEstimateGroupOverhead workSubEstimateGroupOverhead;

	@Autowired
	private WorkSubEstimateGroupOverheadRepository workSubEstimateGroupOverheadRepository;

	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");

		workSubEstimateGroupOverhead = new WorkSubEstimateGroupOverhead();
		workSubEstimateGroupOverhead.setCode("A");
		workSubEstimateGroupOverhead.setDescription("testD_o");
		workSubEstimateGroupOverhead.setConstruct("(Fixed)");
		workSubEstimateGroupOverhead.setEnteredValue(new BigDecimal(1.00));
		workSubEstimateGroupOverhead.setFinalYn(true);
		workSubEstimateGroupOverhead.setOverheadValue(new BigDecimal(1.55));
		workSubEstimateGroupOverhead.setValueFixedYn(true);
		workSubEstimateGroupOverhead.setValueType(ValueType.ADDED);
		workSubEstimateGroupOverhead.setWorkSubEstimateGroupId(1L);
		workSubEstimateGroupOverhead = workSubEstimateGroupOverheadRepository.save(workSubEstimateGroupOverhead);
	}

	@Test
	public void findByWorkSubEstimateGroupIdAndIdInTest() {
		List<Long> ids = new ArrayList<>();
		ids.add(workSubEstimateGroupOverhead.getId());
		List<WorkSubEstimateGroupOverhead> result = workSubEstimateGroupOverheadRepository
				.findByWorkSubEstimateGroupIdAndIdIn(1L, ids);
		Assert.assertFalse(result.isEmpty());
		log.info("findByWorkSubEstimateGroupIdAndIdInTest successful");
	}

	@Test
	public void sumOverheadValueByGroupIdAndIdInTest() {
		List<Long> ids = new ArrayList<>();
		ids.add(workSubEstimateGroupOverhead.getId());
		BigDecimal result = workSubEstimateGroupOverheadRepository.sumOverheadValueByGroupIdAndIdIn(1L, ids);
		Assert.assertEquals(result.intValue(), workSubEstimateGroupOverhead.getOverheadValue().intValue());
		log.info("sumOverheadValueByGroupIdAndIdInTest successful");
	}

	@Test
	public void countByWorkSubEstimateGroupIdTest() {
		int result = workSubEstimateGroupOverheadRepository.countByWorkSubEstimateGroupId(1L);
		Assert.assertNotEquals(result, 0);
		log.info("countByWorkSubEstimateGroupIdTest successful");
	}

	@Test
	public void sumAddedOverheadValueByGroupIdAndFinalYnTrueTest() {
		BigDecimal result = workSubEstimateGroupOverheadRepository.sumAddedOverheadValueByGroupIdAndFinalYnTrue(1L);
		Assert.assertEquals(result.intValue(), workSubEstimateGroupOverhead.getOverheadValue().intValue());
		log.info("sumAddedOverheadValueByGroupIdAndFinalYnTrueTest successful");
	}

	@Test
	public void sumOtherOverheadValueByGroupIdTest() {
		BigDecimal result = workSubEstimateGroupOverheadRepository.sumOtherOverheadValueByGroupId(1L);
		Assert.assertEquals(result, null);
		log.info("sumOtherOverheadValueByGroupIdTest successful");
	}

	@Test
	public void findByWorkSubEstimateGroupIdAndIdAndValueTypeTest() {
		Optional<WorkSubEstimateGroupOverhead> result = workSubEstimateGroupOverheadRepository
				.findByWorkSubEstimateGroupIdAndIdAndValueType(1L, workSubEstimateGroupOverhead.getId(),
						ValueType.ADDED);
		Assert.assertTrue(result.isPresent());
		log.info("findByWorkSubEstimateGroupIdAndIdAndValueTypeTest successful");
	}

	@Test
	public void findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalseTest() {
		List<WorkSubEstimateGroupOverhead> result = workSubEstimateGroupOverheadRepository
				.findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalse(1L, ValueType.ADDED);
		Assert.assertTrue(result.isEmpty());
		log.info("findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalseTest successful");
	}

	@Test
	public void findByWorkSubEstimateGroupIdTest() {
		Page<WorkSubEstimateGroupOverhead> result = workSubEstimateGroupOverheadRepository
				.findByWorkSubEstimateGroupId(1L, PageRequest.of(0, 5));
		Assert.assertTrue(result.hasContent());
		log.info("findByWorkSubEstimateGroupIdTest successful");
	}

	@Test
	public void findByWorkSubEstimateGroupIdAndIdTest() {
		Optional<WorkSubEstimateGroupOverhead> result = workSubEstimateGroupOverheadRepository
				.findByWorkSubEstimateGroupIdAndId(1L, workSubEstimateGroupOverhead.getId());
		Assert.assertTrue(result.isPresent());
		log.info("findByWorkSubEstimateGroupIdAndIdTest successful");
	}

	@Test
	public void findByWorkSubEstimateGroupIdAndValueTypeTest() {
		List<WorkSubEstimateGroupOverhead> result = workSubEstimateGroupOverheadRepository
				.findByWorkSubEstimateGroupIdAndValueType(1L, ValueType.ADDED);
		Assert.assertFalse(result.isEmpty());
		log.info("findByWorkSubEstimateGroupIdAndValueTypeTest successful");
	}
}
