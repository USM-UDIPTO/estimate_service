package com.dxc.eproc.estimate.repository;

import java.util.List;

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

import com.dxc.eproc.estimate.model.SchemeCode;

@SpringBootTest
@ActiveProfiles("test")
public class SchemeCodeRepositoryTest extends AbstractTestNGSpringContextTests {
	private static final Logger log = LoggerFactory.getLogger(SchemeCodeRepositoryTest.class);

	private static SchemeCode schemeCode;

	@Autowired
	SchemeCodeRepository schemeCodeRepository;

	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
		schemeCode = new SchemeCode();
	}

	@Test
	public void findAllByActiveYnTest() {
		Page<SchemeCode> result = schemeCodeRepository.findAllByActiveYn(false, PageRequest.of(0, 5));
		Assert.assertTrue(result.isEmpty());
	}

	@Test
	public void findAllByLocationIdAndSchemeTypeAndSchemeCode() {
		List<SchemeCode> result = schemeCodeRepository.findAllByLocationIdAndSchemeTypeAndSchemeCode(1L,
				schemeCode.getSchemeType(), "Test");
		Assert.assertTrue(result.isEmpty());
	}
}
