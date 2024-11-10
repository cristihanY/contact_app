package com.example.dbapp.service;

import java.util.List;
import com.example.dbapp.core.AppDatabase;
import com.example.dbapp.model.entity.Customer;
import com.example.dbapp.repository.dao.CustomerDao;

public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(AppDatabase database) {
        this.customerDao = database.customerDao();
    }

    public Customer addCustomer(Customer customer) {
        return customerDao.persist(customer);
    }

    public void updateCustomer(long id, Customer data) {
        Customer customer = getCustomerById(id);
        if (customer != null) {
            customer.setName(data.getName());
            customer.setLastName(data.getLastName());
            customer.setEmail(data.getEmail());
            customer.setPhone(data.getPhone());
            customerDao.update(customer);
        }
    }


    public void removeCustomer(long id) {
        customerDao.deleteById(id);
    }

    public List<Customer> getAllCustomer() {
        return customerDao.getAllFirst();
    }

    public Customer getCustomerById(long id) {
        return customerDao.getById(id);
    }
}

