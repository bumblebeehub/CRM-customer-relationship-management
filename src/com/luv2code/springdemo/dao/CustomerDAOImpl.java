package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {

		//get the current hibernate session
		Session session = sessionFactory.getCurrentSession();
		
		//create a query sort by last name
		Query<Customer> theQuery = session.createQuery("from Customer order by lastName", Customer.class);
		
		//execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		//return the results
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {

		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomers(int theId) {

		Session session = sessionFactory.getCurrentSession();
		Customer customer = session.get(Customer.class, theId);
		
		return customer;
	}

	@Override
	public void deleteCustomer(int theId) {
		Session session = sessionFactory.getCurrentSession();
//		Customer customer = session.get(Customer.class, theId);
//		session.delete(customer);
		
		Query theQuery = session.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		
		Session theSession = sessionFactory.getCurrentSession();
		
		Query theQuery = null;
		
		//only search the customer when the search name is not empty or "   "
		if(theSearchName != null && theSearchName.trim().length() > 0)
		{
			theQuery = theSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", 
					Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
			
		}
		else {
			theQuery = theSession.createQuery("from Customer", Customer.class);
		}
		List<Customer> customers = theQuery.getResultList();
		return customers;
	}

}
