package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.WorkEstimateRateAnalysis;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRateAnalysisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkEstimateRateAnalysis} and its DTO {@link WorkEstimateRateAnalysisDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkEstimateRateAnalysisMapper extends EntityMapper<WorkEstimateRateAnalysisDTO, WorkEstimateRateAnalysis> {}
