package com.prs.business.web;

import java.sql.Timestamp;
import java.util.List;
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
public class PurchaseRequestController extends BaseController{
	@Autowired 
	private PurchaseRequestRepository purchaseRequestRepository;
	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;
	

	@GetMapping(path="/List")
	public @ResponseBody Iterable<PurchaseRequest> getAllPurchaseRequests() {
		return purchaseRequestRepository.findAll();
	}
	@GetMapping(path="/Get")
	public @ResponseBody List<PurchaseRequest> getPurchaseRequest(@RequestParam int id) {
		Optional<PurchaseRequest> v= purchaseRequestRepository.findById(id);
		return getReturnArray(v.get());
	}
	@PostMapping(path="/Add")
	public @ResponseBody PRSMaintenanceReturn addNewPurchaseRequest (@RequestBody PurchaseRequest purchaseRequest) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		purchaseRequest.setSubmittedDate(ts);
		purchaseRequest.setStatus(PurchaseRequest.STATUS_NEW);
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
	@PostMapping(path="/SubmitForReview") 
	public @ResponseBody PRSMaintenanceReturn submitForReview (@RequestBody PurchaseRequest pr) {
		Optional<PurchaseRequest> prOpt = purchaseRequestRepository.findById(pr.getId());
		Timestamp ts = new Timestamp(System.currentTimeMillis());
        pr = prOpt.get(); 
		if(pr.getTotal() < 50.0) {
             pr.setStatus(PurchaseRequest.STATUS_APPROVED);
         }else {
             pr.setStatus(PurchaseRequest.STATUS_REVIEW);
         }
         pr.setSubmittedDate(ts);
		try {
			pr.setStatus(PurchaseRequest.STATUS_REVIEW);
			pr.setSubmittedDate(new Timestamp(System.currentTimeMillis()));
			purchaseRequestRepository.save(pr);
		}
		catch (Exception e) {
			e.printStackTrace();
			pr = null;
		}
		
		return PRSMaintenanceReturn.getMaintReturn(pr);
	}
	@GetMapping(path = "/GetRequestReview")
	public @ResponseBody Iterable<PurchaseRequest> getRequestReview(@RequestParam int id) {
		return purchaseRequestRepository.findAllByUserIdNot(id);
	}
	}
