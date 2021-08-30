package com.dxc.eproc.estimate.service.mapper;

import org.mapstruct.Mapper;

import com.dxc.eproc.estimate.model.WorkEstimateRoyaltyCharges;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRoyaltyChargesDTO;

/**
 * Mapper for the entity {@link WorkEstimateRoyaltyCharges} and its DTO {@link WorkEstimateRoyaltyChargesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkEstimateRoyaltyChargesMapper extends EntityMapper<WorkEstimateRoyaltyChargesDTO, WorkEstimateRoyaltyCharges> {}
