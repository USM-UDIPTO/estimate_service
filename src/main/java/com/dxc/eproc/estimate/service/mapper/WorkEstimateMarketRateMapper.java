package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.WorkEstimateMarketRate;
import com.dxc.eproc.estimate.service.dto.WorkEstimateMarketRateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkEstimateMarketRate} and its DTO {@link WorkEstimateMarketRateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkEstimateMarketRateMapper extends EntityMapper<WorkEstimateMarketRateDTO, WorkEstimateMarketRate> {}
