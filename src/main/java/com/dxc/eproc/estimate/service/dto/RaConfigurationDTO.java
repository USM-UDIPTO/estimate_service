package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dxc.eproc.estimate.domain.RaConfiguration} entity.
 */
public class RaConfigurationDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private Long deptId;

    @NotNull
    private Long raParamId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getRaParamId() {
        return raParamId;
    }

    public void setRaParamId(Long raParamId) {
        this.raParamId = raParamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RaConfigurationDTO)) {
            return false;
        }

        RaConfigurationDTO raConfigurationDTO = (RaConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, raConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RaConfigurationDTO{" +
            "id=" + getId() +
            ", deptId=" + getDeptId() +
            ", raParamId=" + getRaParamId() +
            "}";
    }
}
