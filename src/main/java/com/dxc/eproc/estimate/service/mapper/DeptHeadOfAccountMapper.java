package com.dxc.eproc.estimate.service.mapper;

import com.dxc.eproc.estimate.model.*;
import com.dxc.eproc.estimate.service.dto.DeptHeadOfAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeptHeadOfAccount} and its DTO
 * {@link DeptHeadOfAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeptHeadOfAccountMapper extends EntityMapper<DeptHeadOfAccountDTO, DeptHeadOfAccount> {
}
