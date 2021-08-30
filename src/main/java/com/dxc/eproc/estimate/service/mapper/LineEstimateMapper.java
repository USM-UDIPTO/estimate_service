package com.dxc.eproc.estimate.service.mapper;

import org.mapstruct.Mapper;

import com.dxc.eproc.estimate.model.LineEstimate;
import com.dxc.eproc.estimate.service.dto.LineEstimateDTO;

/**
 * Mapper for the entity {@link LineEstimate} and its DTO {@link LineEstimateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LineEstimateMapper extends EntityMapper<LineEstimateDTO, LineEstimate> {}
