package com.dxc.eproc.estimate.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.document.ReferenceTypes;
import com.dxc.eproc.estimate.model.ObjectStore;

// TODO: Auto-generated Javadoc
/**
 * The Interface ObjectStoreRepository.
 */
@Repository
public interface ObjectStoreRepository extends JpaRepository<ObjectStore, UUID> {

	/**
	 * Find by reference id and reference type.
	 *
	 * @param referenceId   the reference id
	 * @param referenceType the reference type
	 * @return the list
	 */
	@Query("select os FROM ObjectStore os WHERE os.referenceId=:referenceId and os.referenceType=:referenceType")
	List<ObjectStore> findByReferenceIdAndReferenceType(@Param("referenceId") Long referenceId,
			@Param("referenceType") ReferenceTypes referenceType);

	/**
	 * Find by reference id and reference type and active yn.
	 *
	 * @param referenceId   the reference id
	 * @param referenceType the reference type
	 * @param b             the b
	 * @return the list
	 */
	List<ObjectStore> findByReferenceIdAndReferenceTypeAndActiveYn(Long referenceId, ReferenceTypes referenceType,
			boolean b);

	/**
	 * Find by reference id and reference type in and active yn.
	 *
	 * @param referenceId    the reference id
	 * @param referenceTypes the reference types
	 * @param b              the b
	 * @return the list
	 */
	List<ObjectStore> findByReferenceIdAndReferenceTypeInAndActiveYn(Long referenceId,
			List<ReferenceTypes> referenceTypes, boolean b);

	/**
	 * Find by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the list
	 */
	List<ObjectStore> findByWorkEstimateId(Long workEstimateId);

	/**
	 * Find By Work Estimate Id And Id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id
	 * @return the list
	 */
	List<ObjectStore> findByWorkEstimateIdAndId(Long workEstimateId, UUID id);

	/**
	 * findAllByWorkEstimateIdAndReferenceType.
	 *
	 * @param workEstimateId the workEstimateId
	 * @param referenceType  the reference type
	 * @return the objectStoreList
	 */
	List<ObjectStore> findAllByWorkEstimateIdAndReferenceType(Long workEstimateId,
			@Param("referenceType") ReferenceTypes referenceType);

	/**
	 * Find by work estimate id and id and active yn.
	 *
	 * @param workEstimateId the work estimate id
	 * @param uuid           the uuid
	 * @param b              the b
	 * @return the list
	 */
	List<ObjectStore> findByWorkEstimateIdAndIdAndActiveYn(long workEstimateId, UUID uuid, boolean b);

}
