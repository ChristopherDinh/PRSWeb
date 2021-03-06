package com.prs.business.purchaseRequest;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PurchaseRequestRepository extends CrudRepository<PurchaseRequest, Integer> {
	Iterable<PurchaseRequest> findAllByUserIdNot(int id);

}