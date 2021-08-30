package com.dxc.eproc.estimate.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.enumeration.SchemeType;
import com.dxc.eproc.estimate.model.SchemeCode;
import com.dxc.eproc.estimate.repository.SchemeCodeRepository;
import com.dxc.eproc.estimate.service.SchemeCodeService;
import com.dxc.eproc.estimate.service.dto.SchemeCodeDTO;
import com.dxc.eproc.estimate.service.mapper.SchemeCodeMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link SchemeCode}.
 */
@Service
@Transactional
public class SchemeCodeServiceImpl implements SchemeCodeService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SchemeCodeServiceImpl.class);

	/** The scheme code repository. */
	private final SchemeCodeRepository schemeCodeRepository;

	/** The scheme code mapper. */
	private final SchemeCodeMapper schemeCodeMapper;

	/**
	 * Instantiates a new scheme code service impl.
	 *
	 * @param schemeCodeRepository the scheme code repository
	 * @param schemeCodeMapper     the scheme code mapper
	 */
	public SchemeCodeServiceImpl(SchemeCodeRepository schemeCodeRepository, SchemeCodeMapper schemeCodeMapper) {
		this.schemeCodeRepository = schemeCodeRepository;
		this.schemeCodeMapper = schemeCodeMapper;
	}

	/**
	 * Save.
	 *
	 * @param schemeCodeDTO the scheme code DTO
	 * @return the scheme code DTO
	 */
	@Override
	public SchemeCodeDTO save(SchemeCodeDTO schemeCodeDTO) {
		SchemeCode schemeCode = null;
		if (schemeCodeDTO.getId() == null) {
			log.debug("Request to save SchemeCode");
			schemeCode = schemeCodeMapper.toEntity(schemeCodeDTO);
		} else {
			log.debug("Request to update SchemeCode - Id : {}", schemeCodeDTO.getId());

			Optional<SchemeCode> schemeCodeOptional = schemeCodeRepository.findById(schemeCodeDTO.getId());
			if (schemeCodeOptional.isPresent()) {
				schemeCode = schemeCodeOptional.get();
				schemeCode.activeYn(schemeCodeDTO.getActiveYn()).locationId(schemeCodeDTO.getLocationId())
						.schemeCode(schemeCodeDTO.getSchemeCode()).schemeStatus(schemeCodeDTO.getSchemeStatus())
						.schemeType(schemeCodeDTO.getSchemeType()).workName(schemeCodeDTO.getWorkName())
						.workVal(schemeCodeDTO.getWorkVal());
			}
		}
		schemeCode = schemeCodeRepository.save(schemeCode);
		return schemeCodeMapper.toDto(schemeCode);
	}

	/**
	 * Partial update.
	 *
	 * @param schemeCodeDTO the scheme code DTO
	 * @return the optional
	 */
	@Override
	public Optional<SchemeCodeDTO> partialUpdate(SchemeCodeDTO schemeCodeDTO) {
		log.debug("Request to partially update SchemeCode : {}", schemeCodeDTO);

		return schemeCodeRepository.findById(schemeCodeDTO.getId()).map(existingSchemeCode -> {
			schemeCodeMapper.partialUpdate(existingSchemeCode, schemeCodeDTO);
			return existingSchemeCode;
		}).map(schemeCodeRepository::save).map(schemeCodeMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<SchemeCodeDTO> findAll(Pageable pageable) {
		log.debug("Request to get all SchemeCodes");
		return schemeCodeRepository.findAll(pageable).map(schemeCodeMapper::toDto);
	}

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<SchemeCodeDTO> findAllActive(Pageable pageable) {
		log.debug("Request to get all active SchemeCodes");
		return schemeCodeRepository.findAllByActiveYn(true, pageable).map(schemeCodeMapper::toDto);
	}

	/**
	 * Find all by location id and scheme type and scheme code.
	 *
	 * @param locationId the location id
	 * @param schemeType the scheme type
	 * @param schemeCode the scheme code
	 * @return the list
	 */
	@Override
	public List<SchemeCodeDTO> findAllByLocationIdAndSchemeTypeAndSchemeCode(Long locationId, SchemeType schemeType,
			String schemeCode) {
		log.debug("Request to get all active By SchemeType and SchemeCode");
		return schemeCodeMapper.toDto(
				schemeCodeRepository.findAllByLocationIdAndSchemeTypeAndSchemeCode(locationId, schemeType, schemeCode));
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<SchemeCodeDTO> findOne(Long id) {
		log.debug("Request to get SchemeCode : {}", id);
		return schemeCodeRepository.findById(id).map(schemeCodeMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete SchemeCode : {}", id);
		Optional<SchemeCode> schemeCodeOptional = schemeCodeRepository.findById(id);
		if (schemeCodeOptional.isPresent()) {
			SchemeCode schemeCode = schemeCodeOptional.get();
			schemeCode.setActiveYn(false);
			schemeCodeRepository.save(schemeCode);
		}
	}
}
