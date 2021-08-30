package com.dxc.eproc.estimate.model;

import com.dxc.eproc.estimate.enumeration.OverHeadType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OverHead.
 */
@Entity
@Table(name = "over_head")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OverHead extends EProcModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "over_head_type", nullable = false)
    private OverHeadType overHeadType;

    @NotNull
    @Column(name = "over_head_name", nullable = false)
    private String overHeadName;

    @NotNull
    @Column(name = "active_yn", nullable = false)
    private Boolean activeYn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OverHead id(Long id) {
        this.id = id;
        return this;
    }

    public OverHeadType getOverHeadType() {
        return this.overHeadType;
    }

    public OverHead overHeadType(OverHeadType overHeadType) {
        this.overHeadType = overHeadType;
        return this;
    }

    public void setOverHeadType(OverHeadType overHeadType) {
        this.overHeadType = overHeadType;
    }

    public String getOverHeadName() {
        return this.overHeadName;
    }

    public OverHead overHeadName(String overHeadName) {
        this.overHeadName = overHeadName;
        return this;
    }

    public void setOverHeadName(String overHeadName) {
        this.overHeadName = overHeadName;
    }

    public Boolean getActiveYn() {
        return this.activeYn;
    }

    public OverHead activeYn(Boolean activeYn) {
        this.activeYn = activeYn;
        return this;
    }

    public void setActiveYn(Boolean activeYn) {
        this.activeYn = activeYn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OverHead)) {
            return false;
        }
        return id != null && id.equals(((OverHead) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OverHead{" +
            "id=" + getId() +
            ", overHeadType='" + getOverHeadType() + "'" +
            ", overHeadName='" + getOverHeadName() + "'" +
            ", activeYn='" + getActiveYn() + "'" +
            "}";
    }
}
