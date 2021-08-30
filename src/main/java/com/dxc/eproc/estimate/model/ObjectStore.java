package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.dxc.eproc.estimate.document.ReferenceTypes;

// TODO: Auto-generated Javadoc
/**
 * The Class ObjectStore.
 */
@Entity
@Table(name = "object_store")
public class ObjectStore extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private UUID id;

	/** The reference type. */
	private ReferenceTypes referenceType;

	/** The reference id. */
	private Long referenceId;

	/** The active yn. */
	private boolean activeYn;

	/** The work estimate id. */
	private Long workEstimateId;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Type(type = "uuid-char")
	@Column(columnDefinition = "CHAR(36)")
	public UUID getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * Gets the reference type.
	 *
	 * @return the reference type
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "reference_type", length = 50)
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
	@Column(name = "reference_id")
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
	 * Checks if is active yn.
	 *
	 * @return true, if is active yn
	 */
	public boolean isActiveYn() {
		return activeYn;
	}

	/**
	 * Sets the active yn.
	 *
	 * @param activeYn the new active yn
	 */
	public void setActiveYn(boolean activeYn) {
		this.activeYn = activeYn;
	}

	/**
	 * Gets the work estimate id.
	 *
	 * @return the work estimate id
	 */
	public Long getWorkEstimateId() {
		return workEstimateId;
	}

	/**
	 * Sets the work estimate id.
	 *
	 * @param workEstimateId the new work estimate id
	 */
	public void setWorkEstimateId(Long workEstimateId) {
		this.workEstimateId = workEstimateId;
	}

}
