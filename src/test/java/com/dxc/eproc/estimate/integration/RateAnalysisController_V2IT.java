package com.dxc.eproc.estimate.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.client.sor.dto.AreaWeightageMasterDTO;
import com.dxc.eproc.estimate.EstimateServiceApplication;
import com.dxc.eproc.estimate.enumeration.RaChargeType;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateItemService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.RateAnalysisDetailsDTO;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateAdditionalChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLeadChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLiftChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLoadUnloadChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateMarketRateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOtherAddnLiftChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRateAnalysisDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRoyaltyChargesDTO;

@SpringBootTest(classes = EstimateServiceApplication.class)
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
public class RateAnalysisController_V2IT extends AbstractTestNGSpringContextTests {

	private final static Logger log = LoggerFactory.getLogger(RateAnalysisController_V2IT.class);
	
	private static WorkEstimateDTO workEstimateDTO;
	
	private static SubEstimateDTO subEstimateDTO;

	private static WorkEstimateItemDTO workEstimateItemDTO;
	
	private static RateAnalysisDetailsDTO rateAnalysisDetailsDTO;
	
	private static final String SAVE_URL = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/rate-analysis/save";
	
	@Autowired
	private MockMvc restRateAnalysisMockMvc;
	
	@Autowired
	private WorkEstimateService workEstimateService;
	
	@Autowired
	private SubEstimateService subEstimateService;

	@Autowired
	private WorkEstimateItemService workEstimateItemService;

	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
		
		workEstimateDTO = getWorkEstimate();
		workEstimateDTO = workEstimateService.save(workEstimateDTO);

		subEstimateDTO = new SubEstimateDTO();
		subEstimateDTO.setEstimateTotal(new BigDecimal(200));
		subEstimateDTO.setSubEstimateName("testN o");
		subEstimateDTO.setWorkEstimateId(workEstimateDTO.getId());
		subEstimateDTO = subEstimateService.save(subEstimateDTO);

		workEstimateItemDTO = getWorkEstimateItem();
		workEstimateItemDTO = workEstimateItemService.save(workEstimateItemDTO);
		
		rateAnalysisDetailsDTO = createRateAnalysisDetails();
	}

	@BeforeMethod
	public void initTest() {
		log.info("==================================================================================");
		log.info("This is executed before each test - initTest");
	}
	
	private WorkEstimateDTO getWorkEstimate() {
		WorkEstimateDTO workEstimateDTO = new WorkEstimateDTO();
		workEstimateDTO.setWorkEstimateNumber("testEN o");
		workEstimateDTO.setStatus(WorkEstimateStatus.INITIAL);
		workEstimateDTO.setDeptId(1L);
		workEstimateDTO.setLocationId(1L);
		workEstimateDTO.setFileNumber("testFN o");
		workEstimateDTO.setName("testN o");
		workEstimateDTO.setEstimateTypeId(1L);
		workEstimateDTO.setWorkTypeId(1L);
		workEstimateDTO.setWorkCategoryId(1L);
		workEstimateDTO.setApprovedBudgetYn(true);
		return workEstimateDTO;
	}

	private WorkEstimateItemDTO getWorkEstimateItem() {
		WorkEstimateItemDTO workEstimateItemDTO = new WorkEstimateItemDTO();
		workEstimateItemDTO.setBaseRate(BigDecimal.TEN);
		workEstimateItemDTO.setCatWorkSorItemId(1L);
		workEstimateItemDTO.setSubEstimateId(subEstimateDTO.getId());
		workEstimateItemDTO.setUomId(1L);
		workEstimateItemDTO.setUomName("uom_test");
		workEstimateItemDTO.setItemCode("item_code_test");
		workEstimateItemDTO.setDescription("desc_test");
		return workEstimateItemDTO;
	}

	@Test
	@Transactional
	void saveRateAnalysis() throws Exception {
		log.info("==========================================================================");
		log.info("Test - saveRateAnalysis - Start");

		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isCreated());

		log.info("Test - saveRateAnalysis - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	void workEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - workEstimateNotFound - Start");

		// setting wrong id here
		final Long WORKESTIMATEID = Long.MAX_VALUE;

		// Work Estimate not exist with WORK_ESTIMATE_ID, so this API call must fail
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, WORKESTIMATEID, subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isNotFound());

		log.info("Test - workEstimateNotFound - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	void invalidWorkEstimateStatus() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - invalidWorkEstimateStatus - Start");

		WorkEstimateDTO workEstimateDTO = new WorkEstimateDTO();
		workEstimateDTO.setWorkEstimateNumber("testEN");
		workEstimateDTO.setStatus(WorkEstimateStatus.REJECTED);
		workEstimateDTO.setDeptId(1L);
		workEstimateDTO.setLocationId(1L);
		workEstimateDTO.setFileNumber("testFN");
		workEstimateDTO.setName("testN");
		workEstimateDTO.setEstimateTypeId(1L);
		workEstimateDTO.setWorkTypeId(1L);
		workEstimateDTO.setWorkCategoryId(1L);
		workEstimateDTO.setApprovedBudgetYn(true);
		
		workEstimateDTO = workEstimateService.save(workEstimateDTO);
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isBadRequest());

		log.info("Test - invalidWorkEstimateStatus - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void subEstimateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - subEstimateNotFound - Start");

		// setting wrong id here
		final Long SUBESTIMATEID = Long.MAX_VALUE;

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), SUBESTIMATEID, workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isNotFound());

		log.info("Test - subEstimateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void workEstimateItemNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - workEstimateItemNotFound - Start");

		// setting wrong id here
		final Long WORKESTIMATEITEMID = Long.MAX_VALUE;

		// Sub Estimate not exist with SUB_ESTIMATE_ID, so this API call must fail
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), WORKESTIMATEITEMID)
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isNotFound());

		log.info("Test - workEstimateItemNotFound - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	void savePrevailingMarketRate() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - savePrevailingMarketRate - Start");

		WorkEstimateMarketRateDTO weMarketRate = createWorkEstimateMarketRate();

		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setPrevailingRatesList(List.of(weMarketRate));
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isCreated());

		log.info("Test - savePrevailingMarketRate - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	void invalidPrevailingMarketRate() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - invalidPrevailingMarketRate - Start");

		/* Setting invalid prevailing market rate */
		WorkEstimateMarketRateDTO weMarketRate = createWorkEstimateMarketRate();
		weMarketRate.setWorkEstimateId(null);

		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setPrevailingRatesList(List.of(weMarketRate));
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isBadRequest());

		log.info("Test - invalidPrevailingMarketRate - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	void invalidQuarryInLeadCharges() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - invalidQuarryInLeadCharges - Start");

		/* Setting invalid quarry */
		WorkEstimateLeadChargesDTO workEstimateLeadCharges = createWorkEstimateLeadCharges();
		workEstimateLeadCharges.setQuarry(null);

		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setLeadChargesList(List.of(workEstimateLeadCharges));
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isBadRequest());

		log.info("Test - invalidQuarryInLeadCharges - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	void invalidLeadCharges() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - invalidLeadCharges - Start");

		/* Setting invalid lead charges data */
		WorkEstimateLeadChargesDTO workEstimateLeadCharges = createWorkEstimateLeadCharges();
		workEstimateLeadCharges.setWorkEstimateId(null);
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setLeadChargesList(List.of(workEstimateLeadCharges));
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isBadRequest());

		log.info("Test - invalidLeadCharges - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	void invalidLodadUnloadCharges() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - invalidLodadUnloadCharges - Start");

		WorkEstimateLoadUnloadChargesDTO weLoadUnloadCharges = createWorkEstimateLoadUnloadCharges();
		weLoadUnloadCharges.setWorkEstimateId(null);
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setLoadUnloadChargesList(List.of(weLoadUnloadCharges));
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isBadRequest());

		log.info("Test - invalidLodadUnloadCharges - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	void saveRoyaltyCharges() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - saveRoyaltyCharges - Start");

		WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyCharges = createWorkEstimateRoyaltyCharges();
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setRoyaltyChargesList(List.of(workEstimateRoyaltyCharges));
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isCreated());

		log.info("Test - saveRoyaltyCharges - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	void invalidRoyaltyCharges() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - invalidRoyaltyCharges - Start");

		WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyCharges = createWorkEstimateRoyaltyCharges();
		workEstimateRoyaltyCharges.setWorkEstimateId(null);
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setRoyaltyChargesList(List.of(workEstimateRoyaltyCharges));
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isBadRequest());

		log.info("Test - invalidRoyaltyCharges - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	void saveLiftCharges() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - saveLiftCharges - Start");

		WorkEstimateLiftChargesDTO workEstimateLiftCharges = createWorkEstimateLiftCharges();
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setLiftChargesList(List.of(workEstimateLiftCharges));
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isCreated());

		log.info("Test - saveLiftCharges - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	void invalidLiftCharges() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - invalidLiftCharges - Start");

		WorkEstimateLiftChargesDTO workEstimateLiftCharges = createWorkEstimateLiftCharges();
		workEstimateLiftCharges.setWorkEstimateItemId(null);
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setLiftChargesList(List.of(workEstimateLiftCharges));
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isBadRequest());

		log.info("Test - invalidLiftCharges - End");
		log.info("==========================================================================");
	}
	
	void invalidRateAnalysis() {
		
	}
	
	void invalidOtherAddLiftCharges() {
		
	}
	
	void invalidOtherCharges() {
		
	}
	
	void invalidAdditionalCharges() {
		
	}

	@Test
	@Transactional
	void rateAnalysisNotCompleted() throws Exception {
		log.info("==========================================================================");
		log.info("Test - rateAnalysisNotCompleted - Start");

		WorkEstimateRateAnalysisDTO workEstimateRateAnalysis = rateAnalysisDetailsDTO.getWorkEstimateRateAnalysis();
		workEstimateRateAnalysis.setRaCompletedYn(false);
		rateAnalysisDetailsDTO.setWorkEstimateRateAnalysis(workEstimateRateAnalysis);
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isBadRequest());

		log.info("Test - rateAnalysisNotCompleted - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	void invalidFinalRate() throws Exception {
		log.info("==========================================================================");
		log.info("Test - invalidFinalRate - Start");

		//setting wrong input
		WorkEstimateRateAnalysisDTO workEstimateRateAnalysis = rateAnalysisDetailsDTO.getWorkEstimateRateAnalysis();
		workEstimateRateAnalysis.setRaCompletedYn(true);
		workEstimateRateAnalysis.setNetRate(BigDecimal.valueOf(-10.0000));
		rateAnalysisDetailsDTO.setWorkEstimateRateAnalysis(workEstimateRateAnalysis);
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isBadRequest());

		log.info("Test - invalidFinalRate - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	void customMethodArgumentException() throws Exception {
		log.info("==========================================================================");
		log.info("Test - customMethodArgumentException - Start");

		//setting wrong input
		WorkEstimateRateAnalysisDTO workEstimateRateAnalysis = rateAnalysisDetailsDTO.getWorkEstimateRateAnalysis();
		workEstimateRateAnalysis.setNetRate(BigDecimal.valueOf(50.0000));
		workEstimateRateAnalysis.setBasicRate(null);
		rateAnalysisDetailsDTO.setWorkEstimateRateAnalysis(workEstimateRateAnalysis);
		
		restRateAnalysisMockMvc
				.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
				.andExpect(status().isBadRequest());

		log.info("Test - customMethodArgumentException - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	private void workEstimateMarketRateNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - workEstimateMarketRateNotFound - Start");
		
		//setting wrong market rate id
		WorkEstimateMarketRateDTO weMarketRate = createWorkEstimateMarketRate();
		weMarketRate.setId(Long.MAX_VALUE);
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setPrevailingRatesList(List.of(weMarketRate));

		restRateAnalysisMockMvc
			.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
			.andExpect(status().isNotFound());
		
		log.info("Test - workEstimateMarketRateNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	private void workEstimateLeadChargesNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - workEstimateLeadChargesNotFound - Start");

		//setting wrong id
		WorkEstimateLeadChargesDTO workEstimateLeadCharges = createWorkEstimateLeadCharges();
		workEstimateLeadCharges.setId(Long.MAX_VALUE);
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setLeadChargesList(List.of(workEstimateLeadCharges));
		
		restRateAnalysisMockMvc
			.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
			.andExpect(status().isNotFound());
		
		log.info("Test - workEstimateLeadChargesNotFound - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	private void workEstimateLoadUnloadChargesNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - workEstimateLoadUnloadChargesNotFound - Start");
		
		//setting wrong id
		WorkEstimateLoadUnloadChargesDTO loadUnloadCharges = createWorkEstimateLoadUnloadCharges();
		loadUnloadCharges.setId(Long.MAX_VALUE);
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setLoadUnloadChargesList(List.of(loadUnloadCharges));
		
		restRateAnalysisMockMvc
			.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
			.andExpect(status().isNotFound());

		log.info("Test - workEstimateLoadUnloadChargesNotFound - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	private void workEstimateRoyaltyChargesNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - workEstimateRoyaltyChargesNotFound - Start");

		//setting wrong id
		WorkEstimateRoyaltyChargesDTO royaltyCharges = createWorkEstimateRoyaltyCharges();
		royaltyCharges.setId(Long.MAX_VALUE);
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setRoyaltyChargesList(List.of(royaltyCharges));
		
		restRateAnalysisMockMvc
			.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
			.andExpect(status().isNotFound());

		log.info("Test - workEstimateRoyaltyChargesNotFound - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	private void workEstimateLiftChargesNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - workEstimateLiftChargesNotFound - Start");
		
		//setting wrong id
		WorkEstimateLiftChargesDTO liftCharges = createWorkEstimateLiftCharges();
		liftCharges.setId(Long.MAX_VALUE);

		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setLiftChargesList(List.of(liftCharges));
		
		restRateAnalysisMockMvc
			.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
			.andExpect(status().isNotFound());

		log.info("Test - workEstimateLiftChargesNotFound - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	private void workEstimateRateAnalysisNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - workEstimateRateAnalysisNotFound - Start");

		//setting wrong id
		WorkEstimateRateAnalysisDTO workEstimateRateAnalysis = createWorkEstimateRateAnalysis();
		workEstimateRateAnalysis.setId(Long.MAX_VALUE);
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setWorkEstimateRateAnalysis(workEstimateRateAnalysis);
		
		restRateAnalysisMockMvc
			.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
			.andExpect(status().isNotFound());

		log.info("Test - workEstimateRateAnalysisNotFound - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	private void workEstimateOtherAddnLiftChargesNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - workEstimateOtherAddnLiftChargesNotFound - Start");
		
		//setting wrong id
		WorkEstimateOtherAddnLiftChargesDTO otherAddnLiftCharges = createOtherAddnLiftCharges();
		otherAddnLiftCharges.setType(RaChargeType.LIFT_CHARGES);
		otherAddnLiftCharges.setId(Long.MAX_VALUE);
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setAddnLiftChargesList(List.of(otherAddnLiftCharges));

		restRateAnalysisMockMvc
			.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
			.andExpect(status().isNotFound());
		
		log.info("Test - workEstimateOtherAddnLiftChargesNotFound - End");
		log.info("==========================================================================");
	}

	@Test
	@Transactional
	private void workEstimateAdditionalChargesNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - workEstimateAdditionalChargesNotFound - Start");
		
		//setting wrong id
		WorkEstimateAdditionalChargesDTO weAdditionalChargesDTO = createWorkEstimateAdditionalCharges();
		weAdditionalChargesDTO.setId(Long.MAX_VALUE);
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setAdditionalCharges(weAdditionalChargesDTO);

		restRateAnalysisMockMvc
			.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
			.andExpect(status().isNotFound());
		
		log.info("Test - workEstimateAdditionalChargesNotFound - End");
		log.info("==========================================================================");
	}
	
	@Test
	@Transactional
	private void workEstimateOtherChargesNotFound() throws IOException, Exception {
		log.info("==========================================================================");
		log.info("Test - workEstimateOtherChargesNotFound - Start");
		
		//setting wrong id
		WorkEstimateOtherAddnLiftChargesDTO weOtherAddnLiftCharges = createOtherAddnLiftCharges();
		weOtherAddnLiftCharges.setId(Long.MAX_VALUE);
		
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = createRateAnalysisDetails();
		rateAnalysisDetailsDTO.setAddnOtherChargesList(List.of(weOtherAddnLiftCharges));

		restRateAnalysisMockMvc
			.perform(post(SAVE_URL, workEstimateDTO.getId(), subEstimateDTO.getId(), workEstimateItemDTO.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestUtil.convertObjectToJsonBytes(rateAnalysisDetailsDTO)))
			.andExpect(status().isNotFound());
		
		log.info("Test - workEstimateOtherChargesNotFound - End");
		log.info("==========================================================================");
	}
	
	private RateAnalysisDetailsDTO createRateAnalysisDetails() {
		RateAnalysisDetailsDTO rateAnalysisDetailsDTO = new RateAnalysisDetailsDTO();
		
		WorkEstimateAdditionalChargesDTO weAdditionalChargesDTO = createWorkEstimateAdditionalCharges();
		rateAnalysisDetailsDTO.setAdditionalCharges(weAdditionalChargesDTO);
		
		rateAnalysisDetailsDTO.setAdditionalChargesReq(true);
		rateAnalysisDetailsDTO.setAddlLiftChargesReq(true);
		
		WorkEstimateOtherAddnLiftChargesDTO weOtherAddnLiftCharges = createOtherAddnLiftCharges();
		weOtherAddnLiftCharges.setType(RaChargeType.OTHER_CHARGES);
		rateAnalysisDetailsDTO.setAddnLiftChargesList(List.of(weOtherAddnLiftCharges));
		
		rateAnalysisDetailsDTO.setAddnLiftChargesTotal(BigDecimal.valueOf(10.0000));
		
//		rateAnalysisDetailsDTO.setAddnOtherChargesList(List.of(weOtherAddnLiftCharges));
		rateAnalysisDetailsDTO.setAddnOtherChargesTotal(BigDecimal.valueOf(20.0000));
		rateAnalysisDetailsDTO.setAreaWeightageList(List.of(getAreaWeightageMasterDTO()));
		rateAnalysisDetailsDTO.setAreaWeightageReq(true);
		rateAnalysisDetailsDTO.setAreaWeightageValue(BigDecimal.valueOf(0.0000));
		rateAnalysisDetailsDTO.setCircleName("circle_name");
		rateAnalysisDetailsDTO.setCompensationCostReq(true);
		rateAnalysisDetailsDTO.setContingenciesReq(true);
		rateAnalysisDetailsDTO.setContractorProfitForDiffInMaterial(BigDecimal.valueOf(0.0000));
		rateAnalysisDetailsDTO.setContractorProfitReq(true);
		rateAnalysisDetailsDTO.setEmployeeCostReq(true);
		rateAnalysisDetailsDTO.setEsiChargesReq(true);
		rateAnalysisDetailsDTO.setGrandTotal(BigDecimal.valueOf(0.0000));
		rateAnalysisDetailsDTO.setIdcChargesReq(true);
		rateAnalysisDetailsDTO.setInitialLead(BigDecimal.valueOf(0.0000));
		rateAnalysisDetailsDTO.setInsuranceCostReq(true);
		rateAnalysisDetailsDTO.setItemCode("item_code");
		rateAnalysisDetailsDTO.setItemDescription("item description");
		rateAnalysisDetailsDTO.setLabourChargesReq(true);
		rateAnalysisDetailsDTO.setLoadingUnloadingChargesReq(true);
		rateAnalysisDetailsDTO.setRoyaltyChargesReq(true);
		rateAnalysisDetailsDTO.setOtherChargesReq(true);
		
		WorkEstimateLeadChargesDTO weLeadCharges = createWorkEstimateLeadCharges();
		rateAnalysisDetailsDTO.setLeadChargesList(List.of(weLeadCharges));
		
		rateAnalysisDetailsDTO.setLeadChargesReq(true);
		rateAnalysisDetailsDTO.setLeadChargesTotal(BigDecimal.valueOf(0.0000));
		
//		WorkEstimateLiftChargesDTO weLiftCharges = createWorkEstimateLiftCharges();
//		rateAnalysisDetailsDTO.setLiftChargesList(List.of(weLiftCharges));
		
		rateAnalysisDetailsDTO.setLiftChargesReq(true);
		
		WorkEstimateLoadUnloadChargesDTO weLoadUnloadCharges = createWorkEstimateLoadUnloadCharges();
		rateAnalysisDetailsDTO.setLoadUnloadChargesList(List.of(weLoadUnloadCharges));
		
//		WorkEstimateMarketRateDTO weMarketRate = createWorkEstimateMarketRate();
//		rateAnalysisDetailsDTO.setPrevailingRatesList(List.of(weMarketRate));
		
//		WorkEstimateRoyaltyChargesDTO weRoyaltyCharges = createWorkEstimateRoyaltyCharges();
//		rateAnalysisDetailsDTO.setRoyaltyChargesList(List.of(weRoyaltyCharges));
		
		WorkEstimateRateAnalysisDTO weRateAnalysis = createWorkEstimateRateAnalysis();
		rateAnalysisDetailsDTO.setWorkEstimateRateAnalysis(weRateAnalysis);
		
		return rateAnalysisDetailsDTO;
	}

	private WorkEstimateAdditionalChargesDTO createWorkEstimateAdditionalCharges() {
		WorkEstimateAdditionalChargesDTO weAdditionalChargesDTO = new WorkEstimateAdditionalChargesDTO();
		weAdditionalChargesDTO.setWorkEstimateItemId(workEstimateItemDTO.getId());
		weAdditionalChargesDTO.setAdditionalChargesDesc("additional charges");
		weAdditionalChargesDTO.setAdditionalChargesRate(BigDecimal.valueOf(10.0000));
		return weAdditionalChargesDTO;
	}

	private WorkEstimateRateAnalysisDTO createWorkEstimateRateAnalysis() {
		WorkEstimateRateAnalysisDTO weRateAnalysis = new WorkEstimateRateAnalysisDTO();
		weRateAnalysis.setAreaWeightageCircleId(1L);
		weRateAnalysis.setAreaWeightageMasterId(1L);
		weRateAnalysis.setAreaWeightagePercentage(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setBasicRate(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setCompensationCost(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setContingencies(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setContractorProfitPercentage(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setEmployeesCost(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setEsiCharges(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setFloorNo(4L);
		weRateAnalysis.setIdcCharges(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setInsuranceCost(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setLocalityAllowance(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setNetRate(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setOverheadPercentage(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setProvidentFundCharges(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setRaCompletedYn(true);
		weRateAnalysis.setServiceTax(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setSorFinancialYear("2021");
		weRateAnalysis.setStatutoryCharges(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setTaxPercentage(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setTransportationCost(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setWatchAndWardCost(BigDecimal.valueOf(0.0000));
		weRateAnalysis.setWorkEstimateId(1L);
		weRateAnalysis.setWorkEstimateItemId(1L);
		return weRateAnalysis;
	}

	private WorkEstimateRoyaltyChargesDTO createWorkEstimateRoyaltyCharges() {
		WorkEstimateRoyaltyChargesDTO weRoyaltyCharges = new WorkEstimateRoyaltyChargesDTO();
		weRoyaltyCharges.setWorkEstimateId(workEstimateDTO.getId());
		weRoyaltyCharges.setSubEstimateId(subEstimateDTO.getId());
		weRoyaltyCharges.setWorkEstimateItemId(workEstimateItemDTO.getId());
		weRoyaltyCharges.setCatWorkSorItemId(1L);
		weRoyaltyCharges.setDensityFactor(BigDecimal.valueOf(0.0000));
		weRoyaltyCharges.setDifference(BigDecimal.valueOf(1.0).setScale(4));
		weRoyaltyCharges.setMaterialMasterId(1L);
		weRoyaltyCharges.setMaterialName("material_name");
		weRoyaltyCharges.setPrevailingRoyaltyCharges(BigDecimal.valueOf(5.0).setScale(4));
		weRoyaltyCharges.setQuantity(BigDecimal.valueOf(0.0000));
		weRoyaltyCharges.setSrRoyaltyCharges(BigDecimal.valueOf(0.0000));
		weRoyaltyCharges.setSubEstimateId(1L);
		weRoyaltyCharges.setTotal(BigDecimal.valueOf(0.0000));
		weRoyaltyCharges.setUomId(1L);
		weRoyaltyCharges.setUomName("uom_name");
		return weRoyaltyCharges;
	}

	private WorkEstimateOtherAddnLiftChargesDTO createOtherAddnLiftCharges() {
		WorkEstimateOtherAddnLiftChargesDTO weOtherAddnLiftCharges = new WorkEstimateOtherAddnLiftChargesDTO();
		weOtherAddnLiftCharges.setWorkEstimateItemId(workEstimateItemDTO.getId());
		weOtherAddnLiftCharges.setAddnCharges(BigDecimal.valueOf(5.0000));
		weOtherAddnLiftCharges.setNotesMasterId(1L);
		weOtherAddnLiftCharges.setSelected(true);
//		weOtherAddnLiftCharges.setType(RaChargeType.LEAD_CHARGE);
		return weOtherAddnLiftCharges;
	}

	private WorkEstimateLiftChargesDTO createWorkEstimateLiftCharges() {
		WorkEstimateLiftChargesDTO weLiftCharges = new WorkEstimateLiftChargesDTO();
		weLiftCharges.setWorkEstimateItemId(workEstimateItemDTO.getId());
		weLiftCharges.setLiftCharges(BigDecimal.valueOf(5.0).setScale(4));
		weLiftCharges.setLiftDistance(BigDecimal.valueOf(0.0000));
		weLiftCharges.setMaterialMasterId(1L);
		weLiftCharges.setMaterialName("material_name");
		weLiftCharges.setQuantity(BigDecimal.valueOf(0.0000));
		weLiftCharges.setTotal(BigDecimal.valueOf(0.0000));
		weLiftCharges.setUomId(1L);
		weLiftCharges.setUomName("uom_name");
		return weLiftCharges;
	}

	private WorkEstimateLoadUnloadChargesDTO createWorkEstimateLoadUnloadCharges() {
		WorkEstimateLoadUnloadChargesDTO weLoadUnloadCharges = new WorkEstimateLoadUnloadChargesDTO();
		weLoadUnloadCharges.setWorkEstimateId(workEstimateDTO.getId());
		weLoadUnloadCharges.setSubEstimateId(subEstimateDTO.getId());
		weLoadUnloadCharges.setWorkEstimateItemId(workEstimateItemDTO.getId());
		weLoadUnloadCharges.setCatWorkSorItemId(1L);
		weLoadUnloadCharges.setLoadingCharges(BigDecimal.valueOf(0.0000));
		weLoadUnloadCharges.setMaterialMasterId(1L);
		weLoadUnloadCharges.setMaterialName("material_name");
		weLoadUnloadCharges.setQuantity(BigDecimal.valueOf(0.0000));
		weLoadUnloadCharges.setSelectedLoadCharges(true);
		weLoadUnloadCharges.setSelectedUnloadCharges(true);
		weLoadUnloadCharges.setTotal(BigDecimal.valueOf(0.0000));
		weLoadUnloadCharges.setUnloadingCharges(BigDecimal.valueOf(0.0000));
		return weLoadUnloadCharges;
	}

	private WorkEstimateMarketRateDTO createWorkEstimateMarketRate() {
		WorkEstimateMarketRateDTO weMarketRate = new WorkEstimateMarketRateDTO();
		weMarketRate.setWorkEstimateId(workEstimateDTO.getId());
		weMarketRate.setSubEstimateId(subEstimateDTO.getId());
		weMarketRate.setWorkEstimateItemId(workEstimateItemDTO.getId());
		weMarketRate.setDifference(BigDecimal.valueOf(1.0).setScale(4));
		weMarketRate.setMaterialMasterId(1L);
		weMarketRate.setMaterialName("material_name");
		weMarketRate.setPrevailingMarketRate(BigDecimal.valueOf(16.0000));
		weMarketRate.setQuantity(BigDecimal.valueOf(0.0000));
		weMarketRate.setRate(BigDecimal.valueOf(0.0000));
		weMarketRate.setTotal(BigDecimal.valueOf(20.0000));
		weMarketRate.setUomId(1L);
		weMarketRate.setUomName("uom_name");
		return weMarketRate;
	}

	private WorkEstimateLeadChargesDTO createWorkEstimateLeadCharges() {
		WorkEstimateLeadChargesDTO weLeadCharges = new WorkEstimateLeadChargesDTO();
		weLeadCharges.setWorkEstimateId(workEstimateDTO.getId());
		weLeadCharges.setSubEstimateId(subEstimateDTO.getId());
		weLeadCharges.setWorkEstimateItemId(workEstimateItemDTO.getId());
		weLeadCharges.setCatWorkSorItemId(1L);
		weLeadCharges.setInitialLeadRequiredYn(true);
		weLeadCharges.setLeadCharges(BigDecimal.valueOf(10.0000));
		weLeadCharges.setLeadInKm(BigDecimal.valueOf(0).setScale(4));
		weLeadCharges.setLeadInM(BigDecimal.valueOf(0).setScale(4));
		weLeadCharges.setMaterialMasterId(1L);
		weLeadCharges.setMaterialName("material_name");
		weLeadCharges.setQuantity(BigDecimal.valueOf(0.0000));
		weLeadCharges.setQuarry("quarry");
		weLeadCharges.setTotal(BigDecimal.valueOf(0.0000));
		return weLeadCharges;
	}
	
	private AreaWeightageMasterDTO getAreaWeightageMasterDTO() {
		AreaWeightageMasterDTO areaWeightageMasterDTO = new AreaWeightageMasterDTO();
		areaWeightageMasterDTO.setId(1L);
		areaWeightageMasterDTO.setWeightageValue(BigDecimal.TEN);
		return areaWeightageMasterDTO;
	}
}
