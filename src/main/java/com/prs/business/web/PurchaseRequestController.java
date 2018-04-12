package com.prs.business.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.business.purchaseRequest.PurchaseRequest;
import com.prs.business.purchaseRequest.PurchaseRequestLineItemRepository;
import com.prs.business.purchaseRequest.PurchaseRequestRepository;
import com.prs.util.PRSMaintenanceReturn;


@Controller   
@RequestMapping(path="/PurchaseRequests")
public class PurchaseRequestController {
	@Autowired 
	private PurchaseRequestRepository purchaseRequestRepository;
	

	@GetMapping(path="/List")
	public @ResponseBody Iterable<PurchaseRequest> getAllPurchaseRequest() {
		return purchaseRequestRepository.findAll();
	}
	@GetMapping(path="/Get")
	public @ResponseBody PurchaseRequest getPurchaseRequest(@RequestParam int id) {
		Optional<PurchaseRequest> v= purchaseRequestRepository.findById(id);
		if (v.isPresent())
			return v.get();
		else
			return null;
	}
	@PostMapping(path="/Add")
	public @ResponseBody PRSMaintenanceReturn addNewPurchaseRequest (@RequestBody PurchaseRequest purchaseRequest) {
		try {
			purchaseRequestRepository.save(purchaseRequest);
			return PRSMaintenanceReturn.getMaintReturn(purchaseRequest);
		}
		catch (DataIntegrityViolationException dive) {
			return PRSMaintenanceReturn.getMaintReturnError(purchaseRequest, dive.getRootCause().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			return PRSMaintenanceReturn.getMaintReturnError(purchaseRequest, e.getMessage());
		}
	}
	@GetMapping(path="/Remove")
	public @ResponseBody PRSMaintenanceReturn deletePurchaseRequest (@RequestParam int id) {
		Optional<PurchaseRequest> purchaseRequest = purchaseRequestRepository.findById(id);
		try {
			purchaseRequestRepository.delete(purchaseRequest.get());
		}
		catch (Exception e) {
			purchaseRequest = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(purchaseRequest.get());
	}
	@PostMapping(path="/Change")
	public @ResponseBody PRSMaintenanceReturn updatePurchaseRequest (@RequestBody PurchaseRequest purchaseRequest) {
		try {
			purchaseRequestRepository.save(purchaseRequest);
		}
		catch (Exception e) {
			purchaseRequest = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(purchaseRequest);
	}
}