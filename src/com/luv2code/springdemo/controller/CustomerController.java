package com.luv2code.springdemo.controller;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.dao.CustomerDAO;
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

import sun.print.resources.serviceui;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	//need to inject the customer service into this controller
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomer(Model theModel)
	{
		//get customer from the dao
		List<Customer> theCustomers = customerService.getCustomers();
		
		//add the customer to the model
		theModel.addAttribute("customers", theCustomers);
		
		
		return "list-customer";
	}
	
	@GetMapping("/showFormForAdd")
	public String addCustomer(Model theModel)
	{
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer",theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer)
	{
		
		//save the customer using service
		customerService.saveCustomer(theCustomer);
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String updateCustomer(@RequestParam("customerId") int theId, 
			Model theModel)
	{
		//get customer from database
		Customer customer = customerService.getCustomers(theId);
		
		//set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", customer);
		
		return "customer-form"; 
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId)
	{
		
		customerService.deleteCustomer(theId);
		return "redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String searchCustomer(@RequestParam("theSearchName") String theSearchName, Model theModel)
	{
		//search the customer from the service
		List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
		
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customer";
		
	}
	
}
