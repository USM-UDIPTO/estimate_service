package com.dxc.eproc.estimate.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dxc.eproc.config.FrameworkComponent;
import com.dxc.eproc.document.space.DocumentMetaData;
import com.dxc.eproc.document.space.DocumentStoreException;
import com.dxc.eproc.estimate.document.ReferenceTypes;
import com.dxc.eproc.estimate.document.WorkEstimateDocumentMetaData;
import com.dxc.eproc.estimate.document.WorkEstimateSpace;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.WorkEstimate}.
 */
@RestController
@RequestMapping("v1/api")
public class WorkEstimateFileUploadController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workEstimate";

	/** The Constant PERMISSION. */
	private static final String PERMISSION = "authorization.permision";

	/** The Constant ESTIMATE_BASE. */
	private static final String ESTIMATE_BASE = "estimate";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkEstimateFileUploadController.class);

	/** The work estimate service. */
	@Autowired
	/** The work estimate service. */
	private WorkEstimateService workEstimateService;

	/** The work estimate space. */
	@Autowired
	/** The Work Estimate Space. */
	private WorkEstimateSpace workEstimateSpace;

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The frame work component. */
	@Autowired
	private FrameworkComponent frameWorkComponent;

	/**
	 * upload Administrative Sanction.
	 *
	 * @param workEstimateId the work estimate id
	 * @param file           the file
	 * @return the response entity
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws URISyntaxException     the URI syntax exception
	 * @throws DocumentStoreException the document store exception
	 */
	@PostMapping("/work-estimate/{workEstimateId}/upload-administration-sanction")
	public ResponseEntity<WorkEstimateDocumentMetaData> uploadAdministrativeSanction(
			@PathVariable("workEstimateId") @Valid long workEstimateId, @RequestParam("file") MultipartFile file)
			throws IOException, URISyntaxException, DocumentStoreException {
		log.debug("REST request to upload AdministrativeSanction : ");

		DocumentMetaData documentMetaData = new DocumentMetaData();
		WorkEstimateDocumentMetaData estimateDocumentMetaData;

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}
		Path path = new File(file.getOriginalFilename()).toPath();
		String mimeType = Files.probeContentType(path);

		documentMetaData.setFileName(file.getOriginalFilename());
		documentMetaData.setFileData(file.getInputStream());
		documentMetaData.setMimeType(mimeType);
		documentMetaData.setFileDesc("AdminSanctionDocument");
		documentMetaData.setReferenceId(workEstimateDTO.getId());
		documentMetaData.setReferenceType(ReferenceTypes.ADMINSANCTION_PROCEEDINGS.toString());
		estimateDocumentMetaData = workEstimateSpace.saveEstimateDocument(documentMetaData);

		WorkEstimateDTO workEstimatePartialDTO = new WorkEstimateDTO();
		workEstimatePartialDTO.setId(workEstimateDTO.getId());
		workEstimatePartialDTO.setAdminSanctionAccordedYn(true);
		workEstimateService.partialUpdate(workEstimatePartialDTO);

		return ResponseEntity
				.created(new URI("/v1/api/work-estimate/upload-administration-sanction" + workEstimateDTO.getId()))
				.body(estimateDocumentMetaData);

	}

	/**
	 * get Administrative Sanction.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the administrative sanction
	 * @throws DocumentStoreException the document store exception
	 */
	@GetMapping("/work-estimate/{workEstimateId}/get-administration-sanction")
	public ResponseEntity<List<WorkEstimateDocumentMetaData>> getAdministrativeSanction(
			@PathVariable("workEstimateId") long workEstimateId) throws DocumentStoreException {
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));
		List<WorkEstimateDocumentMetaData> workEstimateSanctionList = workEstimateSpace
				.getSanctionDocument(workEstimateDTO.getId(), ReferenceTypes.ADMINSANCTION_PROCEEDINGS);

		return ResponseEntity.ok().body(workEstimateSanctionList);
	}

	/**
	 * Delete administrative sanction.
	 *
	 * @param workEstimateId the work estimate id
	 * @param uuid           the uuid
	 * @return the response entity
	 * @throws DocumentStoreException the document store exception
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/delete-administration-sanction/{uuid}")
	public ResponseEntity<Void> deleteAdministrativeSanction(@PathVariable("workEstimateId") long workEstimateId,
			@PathVariable("uuid") UUID uuid) throws DocumentStoreException {
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}

		WorkEstimateDTO workEstimatePartialDTO = new WorkEstimateDTO();
		workEstimatePartialDTO.setId(workEstimateDTO.getId());
		workEstimatePartialDTO.setAdminSanctionAccordedYn(false);
		workEstimateService.partialUpdate(workEstimatePartialDTO);

		workEstimateSpace.deleteDocument(workEstimateId, uuid);
		return ResponseEntity.noContent().build();

	}

	/**
	 * Upload objects.
	 *
	 * @param workEstimateId the work estimate id
	 * @param file           the file
	 * @return the response entity
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws URISyntaxException     the URI syntax exception
	 * @throws DocumentStoreException the document store exception
	 */
	@PostMapping("/work-estimate/{workEstimateId}/upload-technical-sanction")
	public ResponseEntity<WorkEstimateDocumentMetaData> uploadTechnicalSanction(
			@PathVariable("workEstimateId") @Valid long workEstimateId, @RequestParam("file") MultipartFile file)
			throws IOException, URISyntaxException, DocumentStoreException {
		log.debug("REST request to upload TechnicalSanction :");

		DocumentMetaData documentMetaData = new DocumentMetaData();
		WorkEstimateDocumentMetaData estimateDocumentMetaData;
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}

		Path path = new File(file.getOriginalFilename()).toPath();
		String mimeType = Files.probeContentType(path);

		documentMetaData.setFileName(file.getOriginalFilename());
		documentMetaData.setFileData(file.getInputStream());
		documentMetaData.setMimeType(mimeType);
		documentMetaData.setFileDesc("TechSanctionDocument");
		documentMetaData.setReferenceId(workEstimateDTO.getId());
		documentMetaData.setReferenceType(ReferenceTypes.TECHSANCTION_PROCEEDINGS.toString());
		estimateDocumentMetaData = workEstimateSpace.saveEstimateDocument(documentMetaData);
		WorkEstimateDTO workEstimatePartialDTO = new WorkEstimateDTO();
		workEstimatePartialDTO.setId(workEstimateDTO.getId());
		workEstimatePartialDTO.setTechSanctionAccordedYn(true);
		workEstimateService.partialUpdate(workEstimatePartialDTO);
		return ResponseEntity
				.created(new URI("/v1/api/work-estimate/upload-technical-sanction" + workEstimateDTO.getId()))
				.body(estimateDocumentMetaData);

	}

	/**
	 * get Technical Sanction.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the technical sanction
	 * @throws DocumentStoreException the document store exception
	 */
	@GetMapping("/work-estimate/{workEstimateId}/get-technical-sanction")
	public ResponseEntity<List<WorkEstimateDocumentMetaData>> getTechnicalSanction(
			@PathVariable("workEstimateId") long workEstimateId) throws DocumentStoreException {
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));
		List<WorkEstimateDocumentMetaData> workEstimateSanctionList = workEstimateSpace
				.getSanctionDocument(workEstimateDTO.getId(), ReferenceTypes.TECHSANCTION_PROCEEDINGS);

		return ResponseEntity.ok().body(workEstimateSanctionList);
	}

	/**
	 * Delete technical sanction.
	 *
	 * @param workEstimateId the work estimate id
	 * @param uuid           the uuid
	 * @return the response entity
	 * @throws DocumentStoreException the document store exception
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/delete-technical-sanction/{uuid}")
	public ResponseEntity<Void> deleteTechnicalSanction(@PathVariable("workEstimateId") long workEstimateId,
			@PathVariable("uuid") UUID uuid) throws DocumentStoreException {
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}

		WorkEstimateDTO workEstimatePartialDTO = new WorkEstimateDTO();
		workEstimatePartialDTO.setId(workEstimateDTO.getId());
		workEstimatePartialDTO.setTechSanctionAccordedYn(false);
		workEstimateService.partialUpdate(workEstimatePartialDTO);

		workEstimateSpace.deleteDocument(workEstimateId, uuid);
		return ResponseEntity.noContent().build();

	}

	/**
	 * uploadWork Estimate Document Preparation.
	 *
	 * @param workEstimateId               the work estimate id
	 * @param workEstimateDocumentMetaData the work estimate document meta data
	 * @param files                        the files
	 * @return the response entity
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws URISyntaxException     the URI syntax exception
	 * @throws DocumentStoreException the document store exception
	 */
	@PostMapping("/work-estimate/{workEstimateId}/upload-estimate-document-prepation")
	public ResponseEntity<List<WorkEstimateDocumentMetaData>> uploadWorkEstimateDocumentPreparation(
			@PathVariable("workEstimateId") @Valid long workEstimateId,
			@RequestPart(name = "jsonData", required = true) @Valid WorkEstimateDocumentMetaData workEstimateDocumentMetaData,
			@RequestPart(name = "files", required = true) MultipartFile[] files)
			throws IOException, URISyntaxException, DocumentStoreException {
		log.debug("REST request to upload WorkEstimateDocumentPreparation : ");
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}

		DocumentMetaData documentMetaData = new DocumentMetaData();
		List<DocumentMetaData> documentMetaDataList = new ArrayList<>();

		Arrays.asList(files).stream().forEach(file -> {
			Path path = new File(file.getOriginalFilename()).toPath();
			String mimeType = null;
			try {
				mimeType = Files.probeContentType(path);
			} catch (IOException e) {
				e.printStackTrace();
			}

			documentMetaData.setFileName(file.getOriginalFilename());
			try {
				documentMetaData.setFileData(file.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			documentMetaData.setMimeType(mimeType);
			documentMetaData.setFileDesc("WorkEstimatePreparationDocument");
			documentMetaData.setReferenceId(workEstimateDTO.getId());
			documentMetaData.setReferenceType(ReferenceTypes.INDENT_WORKS.toString());
			documentMetaData.setDocumentName(workEstimateDocumentMetaData.getDocumentName());
			documentMetaData.setDocumentType(workEstimateDocumentMetaData.getDocumentType());
			documentMetaDataList.add(documentMetaData);
		});
		List<WorkEstimateDocumentMetaData> workEstimateDocumentMetaDataList = workEstimateSpace
				.saveWorkEstimatePreparationDocument(documentMetaDataList);

		return ResponseEntity
				.created(new URI("/v1/api/work-estimate/upload-estimate-document-prepation" + workEstimateDTO.getId()))
				.body(workEstimateDocumentMetaDataList);
	}

	/**
	 * get Technical Sanction.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the work estimate preparation documents
	 * @throws DocumentStoreException the document store exception
	 */
	@GetMapping("/work-estimate/{workEstimateId}/get-estimate-document-prepation")
	public ResponseEntity<List<WorkEstimateDocumentMetaData>> getWorkEstimatePreparationDocuments(
			@PathVariable("workEstimateId") long workEstimateId) throws DocumentStoreException {

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));
		List<WorkEstimateDocumentMetaData> workEstimateList = workEstimateSpace
				.getSanctionDocument(workEstimateDTO.getId(), ReferenceTypes.INDENT_WORKS);
		return ResponseEntity.ok().body(workEstimateList);
	}

	/**
	 * deleteEstimateDocument.
	 *
	 * @param workEstimateId the work estimate id
	 * @param uuid           the uuid
	 * @return the response entity
	 * @throws DocumentStoreException the document store exception
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/delete-estimate-docs/{uuid}")
	public ResponseEntity<Void> deleteEstimateDocument(@PathVariable("workEstimateId") long workEstimateId,
			@PathVariable("uuid") UUID uuid) throws DocumentStoreException {
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}
		workEstimateSpace.deleteDocument(workEstimateId, uuid);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Download object.
	 *
	 * @param workEstimateId the work estimate id
	 * @param uuid           the uuid
	 * @return the response entity
	 * @throws DocumentStoreException the document store exception
	 */
	@GetMapping("/work-estimate/{workEstimateId}/download-estimate-document/{uuid}")
	public ResponseEntity<ByteArrayResource> downloadObject(@PathVariable("workEstimateId") long workEstimateId,
			@PathVariable("uuid") UUID uuid) throws DocumentStoreException {
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));
		return workEstimateSpace.downloadObject(workEstimateDTO.getId(), uuid);

	}

}
