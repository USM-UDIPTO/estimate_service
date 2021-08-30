package com.dxc.eproc.estimate.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.eproc.config.FrameworkComponent;
import com.dxc.eproc.estimate.EstimateServiceConstants;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupLumpsumService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupLumpsumDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.CustomClientInputValidators;
import com.dxc.eproc.exceptionhandling.CustomMethodArgumentNotValidException;
import com.dxc.eproc.exceptionhandling.CustomValidatorConstants;
import com.dxc.eproc.exceptionhandling.CustomValidatorVM;
import com.dxc.eproc.exceptionhandling.FieldErrorVM;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.utils.PaginationUtil;
import com.dxc.eproc.utils.Utility;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.WorkSubEstimateGroupLumpsum}.
 */
@RestController
@RequestMapping("/v1/api")
@Transactional
public class WorkSubEstimateGroupLumpsumController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workSubEstimateGroupLumpsum";

	/** The Constant APPROX_RATE. */
	private static final String APPROX_RATE = "ApproxRate";

	/** The Constant LUMPSUM_NAME. */
	private static final String LUMPSUM_NAME = "name";

	/** The Constant CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION. */
	private static final String CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "fieldError.customMethodArgumentNotValidException";
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupLumpsumController.class);

	/** The framework component. */
	@Autowired
	private FrameworkComponent frameworkComponent;

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/** The work sub estimate group service. */
	@Autowired
	private WorkSubEstimateGroupService workSubEstimateGroupService;

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;
	/** The work sub estimate group lumpsum service. */
	@Autowired
	private WorkSubEstimateGroupLumpsumService workSubEstimateGroupLumpsumService;

	/** The custom client input validators. */
	@Autowired
	private CustomClientInputValidators customClientInputValidators;

	/**
	 * {@code POST  /work-sub-estimate-group-lumpsums} : Create a new
	 * workSubEstimateGroupLumpsum.
	 *
	 * @param workSubEstimateGroupLumpsumDTO the workSubEstimateGroupLumpsumDTO to
	 *                                       create.
	 * @param workEstimateId                 the work estimate id
	 * @param groupId                        the group id
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workSubEstimateGroupLumpsumDTO, or with status
	 *         {@code 400 (Bad Request)} if the workSubEstimateGroupLumpsum has
	 *         already an ID.
	 * @throws Exception the exception
	 */

	@PostMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum")
	public ResponseEntity<WorkSubEstimateGroupLumpsumDTO> createWorkSubEstimateGroupLumpsum(
			@Valid @RequestBody WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "groupId", required = true) Long groupId) throws Exception {
		log.debug("REST request to save WorkSubEstimateGroupLumpsum : {}", workSubEstimateGroupLumpsumDTO);
		String errorM = "workSubEstimateGroupLumpsumDTO.createWorkSubEstimateGroupLumpsum.";

		workSubEstimateGroupLumpsumDTO.setId(null);
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"),
						ENTITY_NAME));

		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"Have permission");
		}

		validateApproxRate(workSubEstimateGroupLumpsumDTO);

		workSubEstimateGroupService.findByWorkEstimateIdAndId(workEstimateId, groupId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidGroupId"),
						ENTITY_NAME));

		workSubEstimateGroupLumpsumDTO.setWorkSubEstimateGroupId(groupId);

		workSubEstimateGroupLumpsumDTO = workSubEstimateGroupLumpsumService.save(workSubEstimateGroupLumpsumDTO);

		workSubEstimateGroupService.recalculateAndUpdateWRTLumpsum(workEstimateId, groupId);

		return ResponseEntity
				.created(new URI("/api/work-sub-estimate-group-lumpsums/" + workSubEstimateGroupLumpsumDTO.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						workSubEstimateGroupLumpsumDTO.getId().toString()))
				.body(workSubEstimateGroupLumpsumDTO);
	}

	/**
	 * Validate approx rate.
	 *
	 * @param workSubEstimateGroupLumpsumDTO the work sub estimate group lumpsum DTO
	 */
	private void validateApproxRate(WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO) {
		List<FieldErrorVM> fieldErrorVMList = new ArrayList<>();
		List<CustomValidatorVM> customValidatorVMList = new ArrayList<>();

		customValidatorVMList.add(new CustomValidatorVM(APPROX_RATE, workSubEstimateGroupLumpsumDTO.getApproxRate(),
				Arrays.asList(CustomValidatorConstants.PATTERN), workSubEstimateGroupLumpsumDTO.getClass().getName(),
				EstimateServiceConstants.EPROC_MIN_DECIMAL_PATTERN, null,
				frameworkComponent.resolveI18n("workSubEstimateGroupLumpsum.approxRate.pattern")));

		fieldErrorVMList = customClientInputValidators.checkValidations(customValidatorVMList);

		if (Utility.isValidCollection(fieldErrorVMList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorVMList);
		}
	}

	/**
	 * Validate lumpsum name.
	 *
	 * @param workSubEstimateGroupLumpsumDTO the work sub estimate group lumpsum DTO
	 */
	private void validateLumpsumName(WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO) {
		List<FieldErrorVM> fieldErrorVMList = new ArrayList<>();
		List<CustomValidatorVM> customValidatorVMList = new ArrayList<>();

		customValidatorVMList.add(new CustomValidatorVM(LUMPSUM_NAME, workSubEstimateGroupLumpsumDTO.getName(),
				Arrays.asList(CustomValidatorConstants.PATTERN), workSubEstimateGroupLumpsumDTO.getClass().getName(),
				EstimateServiceConstants.LUMPSUM_NAME_PATTERN, null,
				frameworkComponent.resolveI18n("workSubEstimateGroupLumpsum.name.pattern")));

		fieldErrorVMList = customClientInputValidators.checkValidations(customValidatorVMList);

		if (Utility.isValidCollection(fieldErrorVMList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorVMList);
		}
	}

	/**
	 * {@code PATCH  /work-sub-estimate-group-lumpsums/:id} : Partial updates given
	 * fields of an existing workSubEstimateGroupLumpsum, field will ignore if it is
	 * null.
	 *
	 * @param workSubEstimateGroupLumpsumDTO the workSubEstimateGroupLumpsumDTO to
	 *                                       update.
	 * @param workEstimateId                 the work estimate id
	 * @param groupId                        the group id
	 * @param id                             the id of the
	 *                                       workSubEstimateGroupLumpsumDTO to save.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workSubEstimateGroupLumpsumDTO, or with status
	 *         {@code 400 (Bad Request)} if the workSubEstimateGroupLumpsumDTO is
	 *         not valid, or with status {@code 404 (Not Found)} if the
	 *         workSubEstimateGroupLumpsumDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the
	 *         workSubEstimateGroupLumpsumDTO couldn't be updated.
	 * @throws Exception the exception
	 */
	@PutMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}")
	public ResponseEntity<WorkSubEstimateGroupLumpsumDTO> updateWorkSubEstimateGroupLumpsum(
			@RequestBody WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "groupId", required = true) Long groupId,
			@PathVariable(value = "id", required = true) Long id) throws Exception {
		log.debug("REST request to partial update WorkSubEstimateGroupLumpsum partially : {}, {}", id,
				workSubEstimateGroupLumpsumDTO);

		String errorM = "workSubEstimateGroupLumpsumDTO.updateWorkSubEstimateGroupLumpsum.";

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"),
						ENTITY_NAME));

		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"Have permission");
		}

		workSubEstimateGroupService.findByWorkEstimateIdAndId(workEstimateId, groupId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidGroupId"),
						ENTITY_NAME));

		workSubEstimateGroupLumpsumService.findByWorkSubEstimateGroupIdAndId(groupId, id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidLumpsumId"),
						ENTITY_NAME));

		WorkSubEstimateGroupLumpsumDTO finalLumpsumDTO = new WorkSubEstimateGroupLumpsumDTO();
		finalLumpsumDTO.setId(id);
		if (StringUtils.hasText(workSubEstimateGroupLumpsumDTO.getName())) {
			validateLumpsumName(workSubEstimateGroupLumpsumDTO);
			finalLumpsumDTO.setName(workSubEstimateGroupLumpsumDTO.getName());
		}
		if (workSubEstimateGroupLumpsumDTO.getApproxRate() != null) {
			validateApproxRate(workSubEstimateGroupLumpsumDTO);
			finalLumpsumDTO.setApproxRate(workSubEstimateGroupLumpsumDTO.getApproxRate());
		}
		finalLumpsumDTO = workSubEstimateGroupLumpsumService.partialUpdate(finalLumpsumDTO).get();

		workSubEstimateGroupService.recalculateAndUpdateWRTLumpsum(workEstimateId, groupId);

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				finalLumpsumDTO.getId().toString())).body(finalLumpsumDTO);
	}

	/**
	 * {@code GET  /work-sub-estimate-group-lumpsums} : get all the
	 * workSubEstimateGroupLumpsums.
	 *
	 * @param pageable       the pagination information.
	 * @param workEstimateId the work estimate id
	 * @param groupId        the group id
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workSubEstimateGroupLumpsums in body.
	 * @throws Exception the exception
	 */
	@GetMapping("work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum")
	public ResponseEntity<List<WorkSubEstimateGroupLumpsumDTO>> getAllWorkSubEstimateGroupLumpsums(Pageable pageable,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "groupId", required = true) Long groupId) throws Exception {

		String errorM = "workSubEstimateGroupLumpsumDTO.getAllWorkSubEstimateGroupLumpsum.";
		log.debug("REST request to get a page of WorkSubEstimateGroupLumpsums");

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"), ENTITY_NAME));

		workSubEstimateGroupService.findByWorkEstimateIdAndId(workEstimateId, groupId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidGroupId"),
						ENTITY_NAME));

		Page<WorkSubEstimateGroupLumpsumDTO> pageLumpsumDTO = workSubEstimateGroupLumpsumService
				.findByWorkSubEstimateGroupId(groupId, pageable);

		if (pageLumpsumDTO.isEmpty()) {
			throw new RecordNotFoundException(errorM + "noLumpsumPresent", ENTITY_NAME);
		}

		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), pageLumpsumDTO);
		return ResponseEntity.ok().headers(headers).body(pageLumpsumDTO.getContent());
	}

	/**
	 * {@code GET  /work-sub-estimate-group-lumpsums/:id} : get the "id"
	 * workSubEstimateGroupLumpsum.
	 *
	 * @param workEstimateId the work estimate id
	 * @param groupId        the group id
	 * @param id             the id of the workSubEstimateGroupLumpsumDTO to
	 *                       retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workSubEstimateGroupLumpsumDTO, or with status
	 *         {@code 404 (Not Found)}.
	 * @throws Exception the exception
	 */
	@GetMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}")
	public ResponseEntity<WorkSubEstimateGroupLumpsumDTO> getWorkSubEstimateGroupLumpsum(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "groupId", required = true) Long groupId,
			@PathVariable(value = "id", required = true) Long id) throws Exception {
		log.debug("REST request to get WorkSubEstimateGroupLumpsum :  workEstimateId : {}, groupId: {}, id: {}",
				workEstimateId, groupId, id);

		String errorM = "workSubEstimateGroupLumpsumDTO.getWorkSubEstimateGroupLumpsum.";
		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"), ENTITY_NAME));

		workSubEstimateGroupService.findByWorkEstimateIdAndId(workEstimateId, groupId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidGroupId"),
						ENTITY_NAME));

		WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO = workSubEstimateGroupLumpsumService
				.findByWorkSubEstimateGroupIdAndId(groupId, id).orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n(errorM + "invalidLumpsumId"), ENTITY_NAME));

		return ResponseUtil.wrapOrNotFound(Optional.of(workSubEstimateGroupLumpsumDTO));
	}

	/**
	 * {@code DELETE  /work-sub-estimate-group-lumpsums/:id} : delete the "id"
	 * workSubEstimateGroupLumpsum.
	 *
	 * @param workEstimateId the work estimate id
	 * @param groupId        the group id
	 * @param id             the id of the workSubEstimateGroupLumpsumDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 * @throws Exception the exception
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/lumpsum/{id}")
	public ResponseEntity<Void> deleteWorkSubEstimateGroupLumpsum(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "groupId", required = true) Long groupId,
			@PathVariable(value = "id", required = true) Long id) throws Exception {
		log.debug("REST request to delete WorkSubEstimateGroupLumpsum : {}", id);
		String errorM = "workSubEstimateGroupLumpsumDTO.deleteWorkSubEstimateGroupLumpsum.";
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"),
						ENTITY_NAME));

		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"Have permission");
		}

		workSubEstimateGroupService.findByWorkEstimateIdAndId(workEstimateId, groupId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidGroupId"),
						ENTITY_NAME));

		workSubEstimateGroupLumpsumService.findByWorkSubEstimateGroupIdAndId(groupId, id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidLumpsumId"),
						ENTITY_NAME));

		workSubEstimateGroupLumpsumService.delete(id);

		workSubEstimateGroupService.recalculateAndUpdateWRTLumpsum(workEstimateId, groupId);

		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
