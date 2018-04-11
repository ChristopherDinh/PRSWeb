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

import com.prs.business.vendor.Vendor;
import com.prs.business.vendor.VendorRepository;
import com.prs.util.PRSMaintenanceReturn;


@Controller   
@RequestMapping(path="/Vendors")
public class VendorController {
	@Autowired 
	private VendorRepository vendorRepository;

	@GetMapping(path="/List")
	public @ResponseBody Iterable<Vendor> getAllVendor() {
		return vendorRepository.findAll();
	}
	@GetMapping(path="/Get")
	public @ResponseBody Vendor getVendor(@RequestParam int id) {
		Optional<Vendor> v= vendorRepository.findById(id);
		if (v.isPresent())
			return v.get();
		else
			return null;
	}
	@PostMapping(path="/Add")
	public @ResponseBody PRSMaintenanceReturn addNewVendor (@RequestBody Vendor vendor) {
		try {
			vendorRepository.save(vendor);
		}
		catch (Exception e) {
			vendor = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(vendor);
	}
	@GetMapping(path="/Remove")
	public @ResponseBody PRSMaintenanceReturn deleteVendor (@RequestParam int id) {
		Optional<Vendor> vendor = vendorRepository.findById(id);
		try {
			vendorRepository.delete(vendor.get());
		}
		catch (Exception e) {
			vendor = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(vendor.get());
	}
	@PostMapping(path="/Change")
	public @ResponseBody PRSMaintenanceReturn updateVendor (@RequestBody Vendor vendor) {
		try {
			vendorRepository.save(vendor);
		}
		catch (Exception e) {
			vendor = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(vendor);
	}
}