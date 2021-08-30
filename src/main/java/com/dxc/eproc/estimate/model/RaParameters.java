package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RaParameters.
 */
@Entity
@Table(name = "ra_parameters")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RaParameters extends EProcModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(name = "param_name", length = 50)
    private String paramName;

    @Size(max = 50)
    @Column(name = "param_table", length = 50)
    private String paramTable;

    @Size(max = 50)
    @Column(name = "param_field", length = 50)
    private String paramField;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RaParameters id(Long id) {
        this.id = id;
        return this;
    }

    public String getParamName() {
        return this.paramName;
    }

    public RaParameters paramName(String paramName) {
        this.paramName = paramName;
        return this;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamTable() {
        return this.paramTable;
    }

    public RaParameters paramTable(String paramTable) {
        this.paramTable = paramTable;
        return this;
    }

    public void setParamTable(String paramTable) {
        this.paramTable = paramTable;
    }

    public String getParamField() {
        return this.paramField;
    }

    public RaParameters paramField(String paramField) {
        this.paramField = paramField;
        return this;
    }

    public void setParamField(String paramField) {
        this.paramField = paramField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RaParameters)) {
            return false;
        }
        return id != null && id.equals(((RaParameters) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RaParameters{" +
            "id=" + getId() +
            ", paramName='" + getParamName() + "'" +
            ", paramTable='" + getParamTable() + "'" +
            ", paramField='" + getParamField() + "'" +
            "}";
    }
}
