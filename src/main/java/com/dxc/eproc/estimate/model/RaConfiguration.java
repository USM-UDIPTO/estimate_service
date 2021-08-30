package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RaConfiguration.
 */
@Entity
@Table(name = "ra_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RaConfiguration extends EProcModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dept_id", nullable = false)
    private Long deptId;

    @NotNull
    @Column(name = "ra_param_id", nullable = false)
    private Long raParamId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RaConfiguration id(Long id) {
        this.id = id;
        return this;
    }

    public Long getDeptId() {
        return this.deptId;
    }

    public RaConfiguration deptId(Long deptId) {
        this.deptId = deptId;
        return this;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getRaParamId() {
        return this.raParamId;
    }

    public RaConfiguration raParamId(Long raParamId) {
        this.raParamId = raParamId;
        return this;
    }

    public void setRaParamId(Long raParamId) {
        this.raParamId = raParamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RaConfiguration)) {
            return false;
        }
        return id != null && id.equals(((RaConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RaConfiguration{" +
            "id=" + getId() +
            ", deptId=" + getDeptId() +
            ", raParamId=" + getRaParamId() +
            "}";
    }
}
