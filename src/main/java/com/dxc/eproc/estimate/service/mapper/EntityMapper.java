package com.dxc.eproc.estimate.service.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

// TODO: Auto-generated Javadoc
/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

public interface EntityMapper<D, E> {

	/**
	 * To entity.
	 *
	 * @param dto the dto
	 * @return the e
	 */
	E toEntity(D dto);

	/**
	 * To dto.
	 *
	 * @param entity the entity
	 * @return the d
	 */
	D toDto(E entity);

	/**
	 * To entity.
	 *
	 * @param dtoList the dto list
	 * @return the list
	 */
	List<E> toEntity(List<D> dtoList);

	/**
	 * To dto.
	 *
	 * @param entityList the entity list
	 * @return the list
	 */
	List<D> toDto(List<E> entityList);

	/**
	 * Partial update.
	 *
	 * @param entity the entity
	 * @param dto    the dto
	 */
	@Named("partialUpdate")
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void partialUpdate(@MappingTarget E entity, D dto);
}
