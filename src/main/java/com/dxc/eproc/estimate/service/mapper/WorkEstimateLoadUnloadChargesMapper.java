package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.WorkEstimateLoadUnloadCharges;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLoadUnloadChargesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkEstimateLoadUnloadCharges} and its DTO {@link WorkEstimateLoadUnloadChargesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkEstimateLoadUnloadChargesMapper
    extends EntityMapper<WorkEstimateLoadUnloadChargesDTO, WorkEstimateLoadUnloadCharges> {}
