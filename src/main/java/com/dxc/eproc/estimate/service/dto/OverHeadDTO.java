package com.dxc.eproc.estimate.service.dto;

import com.dxc.eproc.estimate.enumeration.OverHeadType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.domain.OverHead} entity.
 */
public class OverHeadDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The over head type. */
	@NotNull
	private OverHeadType overHeadType;

	/** The over head name. */
	@NotNull
	private String overHeadName;

	/** The active yn. */
	private Boolean activeYn;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the over head type.
	 *
	 * @return the over head type
	 */
	public OverHeadType getOverHeadType() {
		return overHeadType;
	}

	/**
	 * Sets the over head type.
	 *
	 * @param overHeadType the new over head type
	 */
	public void setOverHeadType(OverHeadType overHeadType) {
		this.overHeadType = overHeadType;
	}

	/**
	 * Gets the over head name.
	 *
	 * @return the over head name
	 */
	public String getOverHeadName() {
		return overHeadName;
	}

	/**
	 * Sets the over head name.
	 *
	 * @param overHeadName the new over head name
	 */
	public void setOverHeadName(String overHeadName) {
		this.overHeadName = overHeadName;
	}

	/**
	 * Gets the active yn.
	 *
	 * @return the active yn
	 */
	public Boolean getActiveYn() {
		return activeYn;
	}

	/**
	 * Sets the active yn.
	 *
	 * @param activeYn the new active yn
	 */
	public void setActiveYn(Boolean activeYn) {
		this.activeYn = activeYn;
	}

	/**
	 * Equals.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OverHeadDTO)) {
			return false;
		}

		OverHeadDTO overHeadDTO = (OverHeadDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, overHeadDTO.id);
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	// prettier-ignore
	@Override
	public String toString() {
		return "OverHeadDTO{" + "id=" + getId() + ", overHeadType='" + getOverHeadType() + "'" + ", overHeadName='"
				+ getOverHeadName() + "'" + ", activeYn='" + getActiveYn() + "'" + "}";
	}
}
