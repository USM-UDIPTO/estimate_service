package com.dxc.eproc.estimate.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.dxc.eproc.document.space.AbstractDocumentStore;
import com.dxc.eproc.document.space.DocumentMetaData;
import com.dxc.eproc.document.space.DocumentStoreException;
import com.dxc.eproc.estimate.model.ObjectStore;
import com.dxc.eproc.estimate.repository.ObjectStoreRepository;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.DateUtil;
import com.dxc.eproc.utils.Utility;

// TODO: Auto-generated Javadoc
/**
 * The Class SupplierSpace.
 */
@Component
public class WorkEstimateSpace extends AbstractDocumentStore {

	/** The Constant Estimate Space. */
	private static final String ESTIMATESPACE = "Work-Estimate-Space";

	/** The Constant Entity Name. */
	private static final String ENTITY_NAME = "WorkEstimate";

	/** The Constant Estimate space prefix. */
	private static final String ESTIMATE_SPACE_PREFIX = "workestimate";

	/** The Constant Bucket Name. */
	private static final String BUCKET_NAME = "workestimate";

	/** The object store repository. */
	@Autowired
	private ObjectStoreRepository objectStoreRepository;

	/**
	 * Instantiates a new Work estimate space.
	 *
	 * @throws DocumentStoreException the document store exception
	 */
	public WorkEstimateSpace() throws DocumentStoreException {
		setBucketName(BUCKET_NAME);
	}

	/**
	 * Gets the document.
	 *
	 * @param bucketName the bucket name
	 * @param objectKey  the object key
	 * @return the document
	 * @throws DocumentStoreException the document store exception
	 */
	@Override
	public byte[] getDocument(String bucketName, String objectKey) throws DocumentStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Save Estimate sanction document.
	 *
	 * @param documentMetaData the document meta data
	 * @return the work estimate document meta data
	 * @throws DocumentStoreException the document store exception
	 */
	public WorkEstimateDocumentMetaData saveEstimateDocument(DocumentMetaData documentMetaData)
			throws DocumentStoreException {

		ObjectStore objectStore = new ObjectStore();
		WorkEstimateDocumentMetaData workEstimateDocumentMetaData = new WorkEstimateDocumentMetaData();
		Map<String, String> fileMetadata = documentMetaData.getFileMetadata();
		if (fileMetadata == null) {
			fileMetadata = new HashMap<>();
		}
		fileMetadata.put(ESTIMATESPACE, "Estimate-Space");
		fileMetadata.put(FILE_NAME, documentMetaData.getFileName());
		fileMetadata.put(CONTENT_TYPE, documentMetaData.getMimeType());
		fileMetadata.put(CREATED_DATE, DateUtil.getCurrentDateTime());
		fileMetadata.put(LAST_MODIFIED_DATE, DateUtil.getCurrentDateTime());
		fileMetadata.put(REFERENCE_ID, String.valueOf(documentMetaData.getReferenceId()));
		fileMetadata.put(REFERENCE_TYPE, documentMetaData.getReferenceType());
		if (Utility.isValidString(documentMetaData.getDocumentType())) {
			fileMetadata.put(DOCUMENT_TYPE, documentMetaData.getDocumentType());
		}
		if (Utility.isValidString(documentMetaData.getDocumentName())) {
			fileMetadata.put(DOCUMENT_NAME, documentMetaData.getDocumentName());
		}
		if (Utility.isValidString(documentMetaData.getFileDesc())) {
			fileMetadata.put(FILE_DESC, documentMetaData.getFileDesc());
		}

		objectStore.setReferenceId(documentMetaData.getReferenceId());
		objectStore.setReferenceType(ReferenceTypes.valueOf(documentMetaData.getReferenceType()));
		objectStore.setActiveYn(true);
		objectStore.setWorkEstimateId(documentMetaData.getReferenceId());

		objectStore = objectStoreRepository.save(objectStore);

		String key = generateKey(ReferenceTypes.valueOf(documentMetaData.getReferenceType()),
				documentMetaData.getReferenceId(), objectStore.getId());
		fileMetadata.put(OBJECT_KEY, key);

		documentMetaData.setFileMetadata(fileMetadata);
		documentMetaData.setBucketName(BUCKET_NAME);
		documentMetaData.setObjectKey(key);
		try {
			saveDocument(documentMetaData);
		} catch (DocumentStoreException e) {
			e.printStackTrace();
			throw new DocumentStoreException(e.getMessage());
		}
		workEstimateDocumentMetaData.setObjectKey(objectStore.getId());
		workEstimateDocumentMetaData.setReferenceId(objectStore.getReferenceId());
		workEstimateDocumentMetaData.setReferenceType(objectStore.getReferenceType());
		workEstimateDocumentMetaData.setFileName(documentMetaData.getFileName());
		workEstimateDocumentMetaData.setMimeType(documentMetaData.getMimeType());
		workEstimateDocumentMetaData.setCreatedTs(objectStore.getCreatedTs());
		workEstimateDocumentMetaData.setCreatedBy(documentMetaData.getCreatedBy());
		workEstimateDocumentMetaData.setFileDesc(documentMetaData.getFileDesc());
		if (objectStore.getReferenceType() == ReferenceTypes.INDENT_WORKS) {
			workEstimateDocumentMetaData.setDocumentType(documentMetaData.getDocumentType());
			workEstimateDocumentMetaData.setDocumentName(documentMetaData.getDocumentName());
		} else {
			workEstimateDocumentMetaData.setDocumentType(objectStore.getReferenceType().getValue());
		}
		return workEstimateDocumentMetaData;
	}

	/**
	 * get the sanction document.
	 *
	 * @param workEstimateId the work estimate id
	 * @param referenceType  the reference type
	 * @return the list
	 * @throws DocumentStoreException the document store exception
	 */
	public List<WorkEstimateDocumentMetaData> getSanctionDocument(long workEstimateId, ReferenceTypes referenceType)
			throws DocumentStoreException {
		List<ObjectStore> estimateSanctionObjectDetailsList = objectStoreRepository
				.findAllByWorkEstimateIdAndReferenceType(workEstimateId, referenceType);
		return formulateToEstimateDocumentMetaData(estimateSanctionObjectDetailsList);

	}

	/**
	 * generate the key.
	 *
	 * @param referenceType the reference type
	 * @param referenceId   the reference id
	 * @param uuid          the uuid
	 * @return key
	 */
	private String generateKey(ReferenceTypes referenceType, Long referenceId, UUID uuid) {
		String key = null;
		key = ESTIMATE_SPACE_PREFIX + "/" + referenceType + "/" + ENTITY_NAME + "/" + referenceId + "/" + uuid;

		return key;
	}

	/**
	 * Formulate to estimate document meta data.
	 *
	 * @param objectStoreList the object store list
	 * @return the list
	 * @throws DocumentStoreException the document store exception
	 */
	private List<WorkEstimateDocumentMetaData> formulateToEstimateDocumentMetaData(List<ObjectStore> objectStoreList)
			throws DocumentStoreException {

		List<WorkEstimateDocumentMetaData> workEstimateDocumentMetaDataList = new ArrayList<>();

		for (ObjectStore objectStore : objectStoreList) {
			String key = generateKey(objectStore.getReferenceType(), objectStore.getReferenceId(), objectStore.getId());
			Map<String, String> objectMetaData = getDocumentMetaData(key);

			String fileName = objectMetaData.get(FILE_NAME);
			if (!Utility.isValidString(fileName)) {
				fileName = objectMetaData.get("x-amz-meta-filename");
			}
			String contentType = objectMetaData.get(CONTENT_TYPE);
			if (!Utility.isValidString(fileName)) {
				contentType = objectMetaData.get("x-amz-meta-contenttype");
			}
			String fileDesc = objectMetaData.get(FILE_DESC);
			if (!Utility.isValidString(fileDesc)) {
				fileDesc = objectMetaData.get("x-amz-meta-filedesc");
			}
			String documentType = objectMetaData.get(DOCUMENT_TYPE);
			if (!Utility.isValidString(documentType)) {
				documentType = objectMetaData.get("x-amz-meta-documenttype");
			}
			String documentName = objectMetaData.get(DOCUMENT_NAME);
			if (!Utility.isValidString(documentName)) {
				documentName = objectMetaData.get("x-amz-meta-documentname");
			}

			WorkEstimateDocumentMetaData workEstimateDocumentMetaData = new WorkEstimateDocumentMetaData();
			workEstimateDocumentMetaData.setObjectKey(objectStore.getId());
			workEstimateDocumentMetaData.setReferenceId(objectStore.getReferenceId());
			workEstimateDocumentMetaData.setReferenceType(objectStore.getReferenceType());
			workEstimateDocumentMetaData.setFileName(fileName);
			workEstimateDocumentMetaData.setMimeType(contentType);
			workEstimateDocumentMetaData.setCreatedTs(objectStore.getCreatedTs());
			workEstimateDocumentMetaData.setCreatedBy(objectStore.getCreatedBy());
			workEstimateDocumentMetaData.setFileDesc(fileDesc);
			workEstimateDocumentMetaData.setDocumentType(documentType);
			workEstimateDocumentMetaData.setDocumentName(documentName);
			workEstimateDocumentMetaDataList.add(workEstimateDocumentMetaData);
		}
		return workEstimateDocumentMetaDataList;
	}

	/**
	 * save Work Estimate Preparation Document.
	 *
	 * @param documentMetaDataList the document meta data list
	 * @return estimateDocumentMetaDataList
	 * @throws DocumentStoreException the document store exception
	 */
	public List<WorkEstimateDocumentMetaData> saveWorkEstimatePreparationDocument(
			List<DocumentMetaData> documentMetaDataList) throws DocumentStoreException {
		List<WorkEstimateDocumentMetaData> estimateDocumentMetaDataList = new ArrayList<>();

		for (DocumentMetaData documentMetaData : documentMetaDataList) {
			estimateDocumentMetaDataList.add(saveEstimateDocument(documentMetaData));
		}
		return estimateDocumentMetaDataList;
	}

	/**
	 * download the Object.
	 *
	 * @param workEstimateId the work estimate id
	 * @param uuid           the uuid
	 * @return the response entity
	 * @throws DocumentStoreException the document store exception
	 */
	public ResponseEntity<ByteArrayResource> downloadObject(long workEstimateId, UUID uuid)
			throws DocumentStoreException {
		byte[] object = null;
		String fileName = null;
		ByteArrayResource resource = null;
		int contentLength = 0;
		List<ObjectStore> objectStoreList = objectStoreRepository.findByWorkEstimateIdAndIdAndActiveYn(workEstimateId,
				uuid, true);

		if (objectStoreList.isEmpty()) {
			throw new RecordNotFoundException("ObjectStore is not avaliable - " + uuid, "ObjectStore");
		}
		for (ObjectStore objectStore : objectStoreList) {
			String objectKey = generateKey(objectStore.getReferenceType(), objectStore.getReferenceId(),
					objectStore.getId());

			Map<String, String> objectMetaData = getDocumentMetaData(objectKey);
			object = getDocument(objectKey);
			contentLength = object.length;
			fileName = objectMetaData.get(FILE_NAME);
			if (!Utility.isValidString(fileName)) {
				fileName = objectMetaData.get("x-amz-meta-filename");
			}
			resource = new ByteArrayResource(object);

		}
		return ResponseEntity.ok().contentLength(contentLength).header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + fileName + "\"").body(resource);

	}

	/**
	 * delete the Document.
	 *
	 * @param workEstimateId the work estimate id
	 * @param uuid           the uuid
	 * @throws DocumentStoreException the document store exception
	 */
	public void deleteDocument(long workEstimateId, UUID uuid) throws DocumentStoreException {

		List<ObjectStore> objectStoreList = objectStoreRepository.findByWorkEstimateIdAndIdAndActiveYn(workEstimateId,
				uuid, true);
		if (objectStoreList.isEmpty()) {
			throw new RecordNotFoundException("ObjectStore is not avaliable - " + uuid, "ObjectStore");
		}
		for (ObjectStore objectStore : objectStoreList) {
			String objectKey = generateKey(objectStore.getReferenceType(), objectStore.getReferenceId(),
					objectStore.getId());
			removeDocument(objectKey);
			objectStore.setActiveYn(false);
			objectStoreRepository.save(objectStore);
		}
	}
}
