package com.dxc.eproc.estimate.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;

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

import com.dxc.eproc.estimate.controller.WorkEstimateOtherAddnLiftChargesController;
import com.dxc.eproc.estimate.enumeration.RaChargeType;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.model.WorkEstimateItem;
import com.dxc.eproc.estimate.model.WorkEstimateOtherAddnLiftCharges;
import com.dxc.eproc.estimate.repository.SubEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateItemRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateOtherAddnLiftChargesRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOtherAddnLiftChargesDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateOtherAddnLiftChargesMapper;

/**
 * Integration tests for the {@link WorkEstimateOtherAddnLiftChargesController} REST controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class WorkEstimateOtherAddnLiftChargesResourceIT extends AbstractTestNGSpringContextTests {

    private static final Long DEFAULT_NOTES_MASTER_ID = 1L;
    private static final Long UPDATED_NOTES_MASTER_ID = 2L;

    private static final Boolean DEFAULT_SELECTED = false;
    private static final Boolean UPDATED_SELECTED = true;

    private static final BigDecimal DEFAULT_ADDN_CHARGES = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADDN_CHARGES = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/v1/api/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-other-additional-lift-charges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkEstimateOtherAddnLiftChargesRepository workEstimateOtherAddnLiftChargesRepository;
    
    @Autowired
    private WorkEstimateRepository workEstimateRepository;
    
    @Autowired
    private SubEstimateRepository subEstimateRepository;
    
    @Autowired
    private WorkEstimateItemRepository workEstimateItemRepository;

    @Autowired
    private WorkEstimateOtherAddnLiftChargesMapper workEstimateOtherAddnLiftChargesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEstimateOtherAddnLiftChargesMockMvc;

    private WorkEstimateOtherAddnLiftCharges workEstimateOtherAddnLiftCharges;
    
	private WorkEstimate workEstimate;

	private SubEstimate subEstimate;
	
	private WorkEstimateItem workEstimateItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEstimateOtherAddnLiftCharges createEntity(EntityManager em) {
        WorkEstimateOtherAddnLiftCharges workEstimateOtherAddnLiftCharges = new WorkEstimateOtherAddnLiftCharges()
            .notesMasterId(DEFAULT_NOTES_MASTER_ID)
            .selected(DEFAULT_SELECTED)
            .addnCharges(DEFAULT_ADDN_CHARGES)
            .type(RaChargeType.LIFT_CHARGES);
         
        return workEstimateOtherAddnLiftCharges;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEstimateOtherAddnLiftCharges createUpdatedEntity(EntityManager em) {
        WorkEstimateOtherAddnLiftCharges workEstimateOtherAddnLiftCharges = new WorkEstimateOtherAddnLiftCharges()
            .notesMasterId(UPDATED_NOTES_MASTER_ID)
            .selected(UPDATED_SELECTED)
            .addnCharges(UPDATED_ADDN_CHARGES);
        return workEstimateOtherAddnLiftCharges;
    }
    
	private WorkEstimate createWorkEstimateEntity() {
		WorkEstimate workEstimate = new WorkEstimate().workEstimateNumber("1112").status(WorkEstimateStatus.DRAFT)
				.deptId(1L).locationId(1L).fileNumber("1235").name("Road Repair")
				.description("Road Repair work estimate.").estimateTypeId(1L).workTypeId(1L).workCategoryId(1L)
				.workCategoryAttribute(10L).workCategoryAttributeValue(BigDecimal.valueOf(20))
				.adminSanctionAccordedYn(false).techSanctionAccordedYn(false).approvedBudgetYn(false);

		return workEstimate;
	}

	private SubEstimate createSubEstimateEntity() {
		SubEstimate subEstimate = new SubEstimate().workEstimateId(1L).sorWorCategoryId(1L)
				.subEstimateName("Repair Work").estimateTotal(BigDecimal.valueOf(1000000.0000)).areaWeightageCircle(1L)
				.areaWeightageDescription("Area weightage Description.").completedYn(false);

		return subEstimate;
	}
	
	private WorkEstimateItem createWorkEstimateItemEntity() {
		WorkEstimateItem workEstimateItem = new WorkEstimateItem();
		workEstimateItem.setBaseRate(BigDecimal.TEN);
		workEstimateItem.setCatWorkSorItemId(1L);
		workEstimateItem.setUomId(1L);
		workEstimateItem.setUomName("uom_test");
		workEstimateItem.setItemCode("item_code_test");
		workEstimateItem.setDescription("desc_test");
		return workEstimateItem;
	}
    
	@BeforeClass
	public void setUp() {
		workEstimate = createWorkEstimateEntity();
		subEstimate = createSubEstimateEntity();
		workEstimateItem = createWorkEstimateItemEntity();

		// Initializing work estimate.
		workEstimateRepository.saveAndFlush(workEstimate);

		// Initializing sub estimate.
		subEstimate.setWorkEstimateId(workEstimate.getId());
		subEstimateRepository.saveAndFlush(subEstimate);
		
		workEstimateItem.setSubEstimateId(subEstimate.getId());
		workEstimateItemRepository.saveAndFlush(workEstimateItem);
	}

    @BeforeMethod
    public void initTest() {
        workEstimateOtherAddnLiftCharges = createEntity(em);
        workEstimateOtherAddnLiftCharges.setWorkEstimateItemId(workEstimateItem.getId());
    }

    @Test
    @Transactional
    void createWorkEstimateOtherAddnLiftCharges() throws Exception {
        int databaseSizeBeforeCreate = workEstimateOtherAddnLiftChargesRepository.findAll().size();
        // Create the WorkEstimateOtherAddnLiftCharges
        WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO = workEstimateOtherAddnLiftChargesMapper.toDto(
            workEstimateOtherAddnLiftCharges
        );
        restWorkEstimateOtherAddnLiftChargesMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOtherAddnLiftChargesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkEstimateOtherAddnLiftCharges in the database
        List<WorkEstimateOtherAddnLiftCharges> workEstimateOtherAddnLiftChargesList = workEstimateOtherAddnLiftChargesRepository.findAll();
        assertThat(workEstimateOtherAddnLiftChargesList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEstimateOtherAddnLiftCharges testWorkEstimateOtherAddnLiftCharges = workEstimateOtherAddnLiftChargesList.get(
            workEstimateOtherAddnLiftChargesList.size() - 1
        );
        assertThat(testWorkEstimateOtherAddnLiftCharges.getNotesMasterId()).isEqualTo(DEFAULT_NOTES_MASTER_ID);
        assertThat(testWorkEstimateOtherAddnLiftCharges.getSelected()).isEqualTo(DEFAULT_SELECTED);
        assertThat(testWorkEstimateOtherAddnLiftCharges.getAddnCharges()).isEqualByComparingTo(DEFAULT_ADDN_CHARGES);
    }
 
    @Test
    @Transactional
    void checkNotesMasterIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateOtherAddnLiftChargesRepository.findAll().size();
        // set the field null
        workEstimateOtherAddnLiftCharges.setNotesMasterId(null);

        // Create the WorkEstimateOtherAddnLiftCharges, which fails.
        WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO = workEstimateOtherAddnLiftChargesMapper.toDto(
            workEstimateOtherAddnLiftCharges
        );

        restWorkEstimateOtherAddnLiftChargesMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOtherAddnLiftChargesDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateOtherAddnLiftCharges> workEstimateOtherAddnLiftChargesList = workEstimateOtherAddnLiftChargesRepository.findAll();
        assertThat(workEstimateOtherAddnLiftChargesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSelectedIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateOtherAddnLiftChargesRepository.findAll().size();
        // set the field null
        workEstimateOtherAddnLiftCharges.setSelected(null);

        // Create the WorkEstimateOtherAddnLiftCharges, which fails.
        WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO = workEstimateOtherAddnLiftChargesMapper.toDto(
            workEstimateOtherAddnLiftCharges
        );

        restWorkEstimateOtherAddnLiftChargesMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOtherAddnLiftChargesDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateOtherAddnLiftCharges> workEstimateOtherAddnLiftChargesList = workEstimateOtherAddnLiftChargesRepository.findAll();
        assertThat(workEstimateOtherAddnLiftChargesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddnChargesIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEstimateOtherAddnLiftChargesRepository.findAll().size();
        // set the field null
        workEstimateOtherAddnLiftCharges.setAddnCharges(null);

        // Create the WorkEstimateOtherAddnLiftCharges, which fails.
        WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO = workEstimateOtherAddnLiftChargesMapper.toDto(
            workEstimateOtherAddnLiftCharges
        );

        restWorkEstimateOtherAddnLiftChargesMockMvc
            .perform(
                post(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOtherAddnLiftChargesDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkEstimateOtherAddnLiftCharges> workEstimateOtherAddnLiftChargesList = workEstimateOtherAddnLiftChargesRepository.findAll();
        assertThat(workEstimateOtherAddnLiftChargesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWorkEstimateOtherAddnLiftCharges() throws Exception {
        // Initialize the database
        workEstimateOtherAddnLiftChargesRepository.saveAndFlush(workEstimateOtherAddnLiftCharges);

        // Get all the workEstimateOtherAddnLiftChargesList
        restWorkEstimateOtherAddnLiftChargesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc", workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEstimateOtherAddnLiftCharges.getId().intValue())))
            .andExpect(jsonPath("$.[*].notesMasterId").value(hasItem(DEFAULT_NOTES_MASTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].selected").value(hasItem(DEFAULT_SELECTED.booleanValue())))
            .andExpect(jsonPath("$.[*].addnCharges").value(hasItem(TestUtil.sameNumber(DEFAULT_ADDN_CHARGES))));
    }

    @Test
    @Transactional
    void getWorkEstimateOtherAddnLiftCharges() throws Exception {
        // Initialize the database
        workEstimateOtherAddnLiftChargesRepository.saveAndFlush(workEstimateOtherAddnLiftCharges);

        // Get the workEstimateOtherAddnLiftCharges
        restWorkEstimateOtherAddnLiftChargesMockMvc
            .perform(get(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateOtherAddnLiftCharges.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEstimateOtherAddnLiftCharges.getId().intValue()))
            .andExpect(jsonPath("$.notesMasterId").value(DEFAULT_NOTES_MASTER_ID.intValue()))
            .andExpect(jsonPath("$.selected").value(DEFAULT_SELECTED.booleanValue()))
            .andExpect(jsonPath("$.addnCharges").value(TestUtil.sameNumber(DEFAULT_ADDN_CHARGES)));
    }

    @Test
    @Transactional
    void getNonExistingWorkEstimateOtherAddnLiftCharges() throws Exception {
        // Get the workEstimateOtherAddnLiftCharges
        restWorkEstimateOtherAddnLiftChargesMockMvc.perform(get(ENTITY_API_URL_ID, 
        		workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), Long.MAX_VALUE))
        .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorkEstimateOtherAddnLiftCharges() throws Exception {
        // Initialize the database
        workEstimateOtherAddnLiftChargesRepository.saveAndFlush(workEstimateOtherAddnLiftCharges);

        int databaseSizeBeforeUpdate = workEstimateOtherAddnLiftChargesRepository.findAll().size();

        // Update the workEstimateOtherAddnLiftCharges
        WorkEstimateOtherAddnLiftCharges updatedWorkEstimateOtherAddnLiftCharges = workEstimateOtherAddnLiftChargesRepository
            .findById(workEstimateOtherAddnLiftCharges.getId())
            .get();
        // Disconnect from session so that the updates on updatedWorkEstimateOtherAddnLiftCharges are not directly saved in db
        em.detach(updatedWorkEstimateOtherAddnLiftCharges);
        updatedWorkEstimateOtherAddnLiftCharges
            .notesMasterId(UPDATED_NOTES_MASTER_ID)
            .selected(UPDATED_SELECTED)
            .addnCharges(UPDATED_ADDN_CHARGES);
        WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO = workEstimateOtherAddnLiftChargesMapper.toDto(
            updatedWorkEstimateOtherAddnLiftCharges
        );

        restWorkEstimateOtherAddnLiftChargesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateOtherAddnLiftChargesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOtherAddnLiftChargesDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkEstimateOtherAddnLiftCharges in the database
        List<WorkEstimateOtherAddnLiftCharges> workEstimateOtherAddnLiftChargesList = workEstimateOtherAddnLiftChargesRepository.findAll();
        assertThat(workEstimateOtherAddnLiftChargesList).hasSize(databaseSizeBeforeUpdate);
        WorkEstimateOtherAddnLiftCharges testWorkEstimateOtherAddnLiftCharges = workEstimateOtherAddnLiftChargesList.get(
            workEstimateOtherAddnLiftChargesList.size() - 1
        );
        assertThat(testWorkEstimateOtherAddnLiftCharges.getNotesMasterId()).isEqualTo(UPDATED_NOTES_MASTER_ID);
        assertThat(testWorkEstimateOtherAddnLiftCharges.getSelected()).isEqualTo(UPDATED_SELECTED);
        assertThat(testWorkEstimateOtherAddnLiftCharges.getAddnCharges()).isEqualTo(String.format("%.2f", UPDATED_ADDN_CHARGES));
    }

    @Test
    @Transactional
    void putNonExistingWorkEstimateOtherAddnLiftCharges() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateOtherAddnLiftChargesRepository.findAll().size();
        workEstimateOtherAddnLiftCharges.setId(count.incrementAndGet());

        // Create the WorkEstimateOtherAddnLiftCharges
        WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO = workEstimateOtherAddnLiftChargesMapper.toDto(
            workEstimateOtherAddnLiftCharges
        );

        // If the entity doesn't have an ID, it will throw RecordNotFoundException
        restWorkEstimateOtherAddnLiftChargesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), workEstimateOtherAddnLiftChargesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOtherAddnLiftChargesDTO))
            )
            .andExpect(status().isNotFound());

        // Validate the WorkEstimateOtherAddnLiftCharges in the database
        List<WorkEstimateOtherAddnLiftCharges> workEstimateOtherAddnLiftChargesList = workEstimateOtherAddnLiftChargesRepository.findAll();
        assertThat(workEstimateOtherAddnLiftChargesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkEstimateOtherAddnLiftCharges() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateOtherAddnLiftChargesRepository.findAll().size();
        workEstimateOtherAddnLiftCharges.setId(count.incrementAndGet());

        // Create the WorkEstimateOtherAddnLiftCharges
        WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO = workEstimateOtherAddnLiftChargesMapper.toDto(
            workEstimateOtherAddnLiftCharges
        );

        // If url ID doesn't match entity ID, it will throw RecordNotFoundException
        restWorkEstimateOtherAddnLiftChargesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId(), count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOtherAddnLiftChargesDTO))
            )
            .andExpect(status().isNotFound());

        // Validate the WorkEstimateOtherAddnLiftCharges in the database
        List<WorkEstimateOtherAddnLiftCharges> workEstimateOtherAddnLiftChargesList = workEstimateOtherAddnLiftChargesRepository.findAll();
        assertThat(workEstimateOtherAddnLiftChargesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkEstimateOtherAddnLiftCharges() throws Exception {
        int databaseSizeBeforeUpdate = workEstimateOtherAddnLiftChargesRepository.findAll().size();
        workEstimateOtherAddnLiftCharges.setId(count.incrementAndGet());

        // Create the WorkEstimateOtherAddnLiftCharges
        WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO = workEstimateOtherAddnLiftChargesMapper.toDto(
            workEstimateOtherAddnLiftCharges
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkEstimateOtherAddnLiftChargesMockMvc
            .perform(
                put(ENTITY_API_URL, workEstimate.getId(), subEstimate.getId(), workEstimateItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEstimateOtherAddnLiftChargesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkEstimateOtherAddnLiftCharges in the database
        List<WorkEstimateOtherAddnLiftCharges> workEstimateOtherAddnLiftChargesList = workEstimateOtherAddnLiftChargesRepository.findAll();
        assertThat(workEstimateOtherAddnLiftChargesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkEstimateOtherAddnLiftCharges() throws Exception {
        // Initialize the database
        workEstimateOtherAddnLiftChargesRepository.saveAndFlush(workEstimateOtherAddnLiftCharges);

        int databaseSizeBeforeDelete = workEstimateOtherAddnLiftChargesRepository.findAll().size();

        // Delete the workEstimateOtherAddnLiftCharges
        restWorkEstimateOtherAddnLiftChargesMockMvc
            .perform(delete(ENTITY_API_URL_ID, workEstimate.getId(), subEstimate.getId(), 
            		workEstimateItem.getId(), workEstimateOtherAddnLiftCharges.getId())
            		.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEstimateOtherAddnLiftCharges> workEstimateOtherAddnLiftChargesList = workEstimateOtherAddnLiftChargesRepository.findAll();
        assertThat(workEstimateOtherAddnLiftChargesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
