package com.asaas.mini

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional

class CustomerService {

    Customer save(Customer customer) {
        if (customer == null) {
            throw new ValidationException("Customer cannot be null")
        }
        if (!customer.validate()) {
            throw new ValidationException("Invalid customer data", customer.errors)
        }
        customer.save(flush: true)
    }

    List<Customer> list(Map params) {
        Customer.list(params)
    }

    Long count() {
        Customer.count()
    }

    Customer get(Serializable id) {
        Customer.get(id)
    }

    Customer update(Customer customer) {
        if (customer == null) {
            throw new ValidationException("Customer cannot be null")
        }
        if (!customer.validate()) {
            throw new ValidationException("Invalid customer data", customer.errors)
        }
        customer.update(flush: true)
    }
    
    void delete(Serializable id) {
        if (id == null) {
            throw new ValidationException("ID cannot be null")
        }
        Customer customer = get(id)
        if (customer) {
            customer.delete(flush: true)
        } else {
            throw new ValidationException("Customer not found with ID: $id")
        }
    }
}
