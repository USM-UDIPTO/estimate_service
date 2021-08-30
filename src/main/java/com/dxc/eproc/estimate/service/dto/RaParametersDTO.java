package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.RaParameters} entity.
 */
public class RaParametersDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@Size(max = 50)
	private String paramName;

	@Size(max = 50)
	private String paramTable;

	@Size(max = 50)
	private String paramField;

	public RaParametersDTO() {
	}

	public RaParametersDTO(String paramName, String paramTable, String paramField) {
		this.paramName = paramName;
		this.paramTable = paramTable;
		this.paramField = paramField;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamTable() {
		return paramTable;
	}

	public void setParamTable(String paramTable) {
		this.paramTable = paramTable;
	}

	public String getParamField() {
		return paramField;
	}

	public void setParamField(String paramField) {
		this.paramField = paramField;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof RaParametersDTO)) {
			return false;
		}

		RaParametersDTO raParametersDTO = (RaParametersDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, raParametersDTO.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "RaParametersDTO{" + "id=" + getId() + ", paramName='" + getParamName() + "'" + ", paramTable='"
				+ getParamTable() + "'" + ", paramField='" + getParamField() + "'" + "}";
	}
}
