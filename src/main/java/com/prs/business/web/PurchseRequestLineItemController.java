package com.prs.business.web;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.business.purchaseRequest.PurchaseRequestLineItem;
import com.prs.business.purchaseRequest.PurchaseRequestLineItemRepository;
import com.prs.util.PRSMaintenanceReturn;


@Controller   
@RequestMapping(path="/PurchaseRequestLineItems")
public class PurchseRequestLineItemController extends BaseController{
	@Autowired 
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;

	@GetMapping(path="/List")
	public @ResponseBody Iterable<PurchaseRequestLineItem> getAllPurchaseRequestLineItem() {
		return purchaseRequestLineItemRepository.findAll();
	}
	@GetMapping(path="/Get")
	public @ResponseBody PurchaseRequestLineItem getPurchaseRequestLineItem(@RequestParam int id) {
		Optional<PurchaseRequestLineItem> v= purchaseRequestLineItemRepository.findById(id);
		if (v.isPresent())
			return v.get();
		else
			return null;
	}
	@PostMapping(path="/Add")
	public @ResponseBody PRSMaintenanceReturn addNewPurchaseRequestLineItem (@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		try {
			purchaseRequestLineItemRepository.save(purchaseRequestLineItem);
		}
		catch (Exception e) {
			purchaseRequestLineItem = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(purchaseRequestLineItem);
	}
	@GetMapping(path="/Remove")
	public @ResponseBody PRSMaintenanceReturn deletePurchaseRequestLineItem (@RequestParam int id) {
		Optional<PurchaseRequestLineItem> purchaseRequestLineItem = purchaseRequestLineItemRepository.findById(id);
		try {
			purchaseRequestLineItemRepository.delete(purchaseRequestLineItem.get());
		}
		catch (Exception e) {
			purchaseRequestLineItem = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(purchaseRequestLineItem.get());
	}
	@PostMapping(path="/Change")
	public @ResponseBody PRSMaintenanceReturn updatePurchaseRequestLineItem (@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
		try {
			purchaseRequestLineItemRepository.save(purchaseRequestLineItem);
		}
		catch (Exception e) {
			purchaseRequestLineItem = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(purchaseRequestLineItem);
	}
}