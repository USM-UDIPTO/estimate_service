package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.WorkEstimateAdditionalCharges;
import com.dxc.eproc.estimate.service.dto.WorkEstimateAdditionalChargesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkEstimateAdditionalCharges} and its DTO {@link WorkEstimateAdditionalChargesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkEstimateAdditionalChargesMapper
    extends EntityMapper<WorkEstimateAdditionalChargesDTO, WorkEstimateAdditionalCharges> {}
