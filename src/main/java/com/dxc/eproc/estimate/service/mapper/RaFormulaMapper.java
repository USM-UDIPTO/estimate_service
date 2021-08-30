package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.RaFormula;
import com.dxc.eproc.estimate.service.dto.RaFormulaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RaFormula} and its DTO {@link RaFormulaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RaFormulaMapper extends EntityMapper<RaFormulaDTO, RaFormula> {}
