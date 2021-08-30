package com.dxc.eproc.estimate.document;

import java.time.Instant;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateDocumentMetaData.
 */
public class WorkEstimateDocumentMetaData {

	/** The object key. */
	private UUID objectKey;

	/** The file name. */
	private String fileName;

	/** The file desc. */
	private String fileDesc;

	/** The mime type. */
	private String mimeType;

	/** The document name. */
	private String documentName;

	/** The document type. */
	private String documentType;

	/** The reference type. */
	private ReferenceTypes referenceType;

	/** The reference id. */
	private Long referenceId;

	/** The created ts. */
	private Instant createdTs;

	/** The created by. */
	private String createdBy;

	/**
	 * Gets the document name.
	 *
	 * @return the document name
	 */
	public String getDocumentName() {
		return documentName;
	}

	/**
	 * Sets the document name.
	 *
	 * @param documentName the new document name
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * Gets the object key.
	 *
	 * @return the object key
	 */
	public UUID getObjectKey() {
		return objectKey;
	}

	/**
	 * Sets the object key.
	 *
	 * @param objectKey the new object key
	 */
	public void setObjectKey(UUID objectKey) {
		this.objectKey = objectKey;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the mime type.
	 *
	 * @return the mime type
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Sets the mime type.
	 *
	 * @param mimeType the new mime type
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * Gets the document type.
	 *
	 * @return the document type
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * Sets the document type.
	 *
	 * @param documentType the new document type
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * Gets the reference type.
	 *
	 * @return the reference type
	 */
	public ReferenceTypes getReferenceType() {
		return referenceType;
	}

	/**
	 * Sets the reference type.
	 *
	 * @param referenceType the new reference type
	 */
	public void setReferenceType(ReferenceTypes referenceType) {
		this.referenceType = referenceType;
	}

	/**
	 * Gets the reference id.
	 *
	 * @return the reference id
	 */
	public Long getReferenceId() {
		return referenceId;
	}

	/**
	 * Sets the reference id.
	 *
	 * @param referenceId the new reference id
	 */
	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * Gets the created ts.
	 *
	 * @return the created ts
	 */
	public Instant getCreatedTs() {
		return createdTs;
	}

	/**
	 * Sets the created ts.
	 *
	 * @param createdTs the new created ts
	 */
	public void setCreatedTs(Instant createdTs) {
		this.createdTs = createdTs;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the file desc.
	 *
	 * @return the file desc
	 */
	public String getFileDesc() {
		return fileDesc;
	}

	/**
	 * Sets the file desc.
	 *
	 * @param fileDesc the new file desc
	 */
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

}
