package com.dxc.eproc.estimate.service.mapper;

import org.mapstruct.Mapper;

import com.dxc.eproc.estimate.model.WorkEstimateLeadCharges;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLeadChargesDTO;

/**
 * Mapper for the entity {@link WorkEstimateLeadCharges} and its DTO {@link WorkEstimateLeadChargesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkEstimateLeadChargesMapper extends EntityMapper<WorkEstimateLeadChargesDTO, WorkEstimateLeadCharges> {}
