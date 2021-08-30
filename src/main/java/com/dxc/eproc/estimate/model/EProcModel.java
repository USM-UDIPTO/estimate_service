package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

// TODO: Auto-generated Javadoc
/**
 * Base abstract class for entities which will hold definitions for created,
 * last modified, created by, last modified by attributes.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The created ts. */
	@JsonIgnore
	@CreatedDate
	@Column(name = "created_ts", nullable = false, updatable = false)
	protected Instant createdTs = Instant.now();

	/** The created by. */
	@JsonIgnore
	@CreatedBy
	@Column(name = "created_by", nullable = false, updatable = false)
	protected String createdBy;

	/** The last modified ts. */
	@JsonIgnore
	@LastModifiedDate
	@Column(name = "last_modified_ts")
	protected Instant lastModifiedTs = Instant.now();

	/** The last modified by. */
	@JsonIgnore
	@LastModifiedBy
	@Column(name = "last_modified_by")
	protected String lastModifiedBy;

	/** The version. */
	@Version
	@Column(name = "version")
	protected Long version;

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
	 * Gets the last modified by.
	 *
	 * @return the last modified by
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * Sets the last modified by.
	 *
	 * @param lastModifiedBy the new last modified by
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * Gets the last modified ts.
	 *
	 * @return the last modified ts
	 */
	public Instant getLastModifiedTs() {
		return lastModifiedTs;
	}

	/**
	 * Sets the last modified ts.
	 *
	 * @param lastModifiedTs the new last modified ts
	 */
	public void setLastModifiedTs(Instant lastModifiedTs) {
		this.lastModifiedTs = lastModifiedTs;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	@JsonIgnore
	public Long getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
}
