package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.WorkEstimateOtherAddnLiftCharges;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOtherAddnLiftChargesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkEstimateOtherAddnLiftCharges} and its DTO {@link WorkEstimateOtherAddnLiftChargesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkEstimateOtherAddnLiftChargesMapper
    extends EntityMapper<WorkEstimateOtherAddnLiftChargesDTO, WorkEstimateOtherAddnLiftCharges> {}
