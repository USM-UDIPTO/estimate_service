package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.WorkEstimateLiftCharges;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLiftChargesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkEstimateLiftCharges} and its DTO {@link WorkEstimateLiftChargesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkEstimateLiftChargesMapper extends EntityMapper<WorkEstimateLiftChargesDTO, WorkEstimateLiftCharges> {}
