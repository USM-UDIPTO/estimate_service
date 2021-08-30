package com.dxc.eproc.estimate.integration;

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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.IntegrationTest;
import com.dxc.eproc.estimate.model.WorkType;
import com.dxc.eproc.estimate.repository.WorkTypeRepository;
import com.dxc.eproc.estimate.service.dto.WorkTypeDTO;
import com.dxc.eproc.estimate.service.mapper.WorkTypeMapper;

// TODO: Auto-generated Javadoc
/**
 * Integration tests for the {@link WorkTypeResource} REST controller.
 */
@IntegrationTest
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class WorkTypeResourceIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(WorkTypeResourceIT.class);

	/** The work type repository. */
	@Autowired
	private WorkTypeRepository workTypeRepository;

	/** The work type mapper. */
	@Autowired
	private WorkTypeMapper workTypeMapper;

	/** The mvc. */
	@Autowired
	private MockMvc mvc;

	/** The work type. */
	private WorkType workType;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {
		log.info("==========================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
		workType = new WorkType();

	}

	/**
	 * Gets the work type DTO.
	 *
	 * @return the work type DTO
	 * @throws Exception the exception
	 */
	public WorkTypeDTO getworkTypeDTO() throws Exception {
		WorkTypeDTO workTypeDTO = new WorkTypeDTO();
		workType.setId(1L);
		workTypeDTO.setWorkTypeName("TestName");
		workTypeDTO.setWorkTypeValue("TestTypeValue");
		workTypeDTO.setValueType("TestValue");
		workTypeDTO.setActiveYn(true);
		return workTypeDTO;

	}

	/**
	 * Creates the entity.
	 *
	 * @return the work type
	 */
	public static WorkType createEntity() {
		WorkType workType = new WorkType();
		workType.setId(1L);
		workType.setWorkTypeName("Test");
		workType.setWorkTypeValue("Test");
		workType.setValueType("Test");
		workType.setActiveYn(true);
		return workType;

	}

	/**
	 * Creates the work type success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkType_SuccessTest() throws Exception {
		WorkTypeDTO workTypeDTO = getworkTypeDTO();
		workTypeDTO.setActiveYn(true);
		String uri = "/v1/api/work-types";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workTypeDTO.getId())
				.content(TestUtil.convertObjectToJsonBytes(workTypeDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated());
		log.info("createWorkType_SuccessTest() successful!!");
	}

	/**
	 * Creates the work type id exists.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createWorkType_IdExists() throws Exception {
		WorkTypeDTO workTypeDTO = getworkTypeDTO();
		workTypeDTO.setId(1L);
		String uri = "/v1/api/work-types";
		RequestBuilder req = MockMvcRequestBuilders.post(uri, workTypeDTO.getId()).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(workTypeDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("createWorkType_IdExists() successful!!");
	}

	/**
	 * Update work type success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkType_SuccessTest() throws Exception {
		WorkType workType = createEntity();
		workTypeRepository.save(workType);

		WorkTypeDTO workTypeDTO = workTypeMapper.toDto(workType);
		workTypeDTO.setActiveYn(true);

		String uri = "/v1/api/work-types/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, workTypeDTO.getId()).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(workTypeDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("updateWorkType_SuccessTest() successful!!");
	}

	/**
	 * Update work type id exists test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateWorkType_IdExistsTest() throws Exception {
		WorkTypeDTO workTypeDTO = getworkTypeDTO();
		workTypeDTO.setId(Long.MAX_VALUE);

		String uri = "/v1/api/work-types/{id}";
		RequestBuilder req = MockMvcRequestBuilders.put(uri, workTypeDTO.getId()).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(workTypeDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateWorkType_IdExistsTest successful");
	}

	/**
	 * Partial update work type success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void partialUpdateWorkType_SuccessTest() throws Exception {
		WorkType workType = createEntity();
		workTypeRepository.save(workType);

		WorkTypeDTO workTypeDTO = workTypeMapper.toDto(workType);
		workTypeDTO.setActiveYn(true);

		String uri = "/v1/api/work-types/{id}";

		RequestBuilder req1 = MockMvcRequestBuilders.patch(uri, workTypeDTO.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(workType));
		mvc.perform(req1).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("partialUpdateWorkType_SuccessTest()!!");
	}

	/**
	 * Partial update work type id exists test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void partialUpdateWorkType_IdExistsTest() throws Exception {
		WorkTypeDTO workTypeDTO = getworkTypeDTO();
		workTypeDTO.setId(Long.MAX_VALUE);

		String uri = "/v1/api/work-types/{id}";
		RequestBuilder req1 = MockMvcRequestBuilders.patch(uri, workTypeDTO.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(workType));
		mvc.perform(req1).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("partialUpdateWorkType_SuccessTest() successful!!");
	}

	/**
	 * Gets the all work types success test.
	 *
	 * @return the all work types success test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllWorkTypes_successTest() throws Exception {
		WorkTypeDTO workTypeDTO = getworkTypeDTO();
		String uri = "/v1/api/work-types";
		mvc.perform(MockMvcRequestBuilders.get(uri, workTypeDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());

		log.info("getAllWorkTypes_successTest successful");
	}

	/**
	 * Gets the all active work types success test.
	 *
	 * @return the all active work types success test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllActiveWorkTypes_successTest() throws Exception {
		WorkTypeDTO workTypeDTO = getworkTypeDTO();

		String uri = "/v1/api/work-types-active";
		mvc.perform(MockMvcRequestBuilders.get(uri, workTypeDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());

		log.info("getAllActiveWorkTypes_successTest successful");
	}

	/**
	 * Gets the all active estimate work types success test.
	 *
	 * @return the all active estimate work types success test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllActiveEstimateWorkTypes_successTest() throws Exception {
		WorkTypeDTO workTypeDTO = getworkTypeDTO();
		String uri = "/v1/api/active-work-types";
		mvc.perform(MockMvcRequestBuilders.get(uri, workTypeDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());

		log.info(" getAllActiveEstimateWorkTypes_successTest successful");
	}

	/**
	 * Gets the work type success test.
	 *
	 * @return the work type success test
	 * @throws Exception the exception
	 */
	@Test
	public void getWorkType_successTest() throws Exception {
		WorkType workType = createEntity();
		workTypeRepository.save(workType);
		WorkTypeDTO workTypeDTO = workTypeMapper.toDto(workType);
		String uri = "/v1/api/work-types/{id}";
		mvc.perform(MockMvcRequestBuilders.get(uri, workTypeDTO.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getWorkType_successTest Successful!!");
	}

	/**
	 * Delete work type success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkType_SuccessTest() throws Exception {
		WorkType workType = createEntity();
		workTypeRepository.save(workType);
		WorkTypeDTO workTypeDTO = workTypeMapper.toDto(workType);
		String uri = "/v1/api/work-types/{id}";
		RequestBuilder req = MockMvcRequestBuilders.delete(uri, workTypeDTO.getId()).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(workTypeDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNoContent());
		log.info("deleteWorkType_SuccessTest() successful!!");
	}

	/**
	 * Delete work type id exists test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteWorkType_IdExistsTest() throws Exception {
		WorkTypeDTO workTypeDTO = getworkTypeDTO();
		workTypeDTO.setId(Long.MAX_VALUE);

		String uri = "/v1/api/work-types/{id}";
		RequestBuilder req = MockMvcRequestBuilders.delete(uri, workTypeDTO.getId()).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(workTypeDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info(" deleteWorkType_IdExistsTest successful");
	}
}
