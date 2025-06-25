package com.asaas.mini

import com.asaas.mini.Customer
import groovy.lang.MissingMethodException
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class CustomerService {

    Customer save(Map params) {

        Customer customer = new Customer()

        customer.name = params.name
        customer.email = params.email
        customer.cpfCnpj = params.cpfCnpj
        customer.state = params.state
        customer.city = params.city
        customer.street = params.street
        customer.addressNumber = params.addressNumber as Integer
        customer.postalCode = params.postalCode
        customer.complement = params.complement ?: null

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

    Customer update(Customer customer, Map params) {
        if (customer == null) {
            throw new ValidationException("Customer not found")
        }

        customer.name = params.name
        customer.email = params.email
        customer.cpfCnpj = params.cpfCnpj
        customer.state = params.state
        customer.city = params.city
        customer.street = params.street
        customer.addressNumber = params.addressNumber as Integer
        customer.postalCode = params.postalCode
        customer.complement = params.complement ?: null
        
        if (!customer.validate()) {
            throw new ValidationException("Invalid customer data", customer.errors)
        }
        customer.save(flush: true, failOnError: true)
        return customer
    }
    
    void delete(Serializable id) {

        Customer customer = Customer.get(id)
        if (customer) {
            customer.delete(flush: true)
        } else {
            throw new ValidationException("Customer not found with ID: $id")
        }
    }

    Customer reload(Serializable id) {
        Customer customer = Customer.get(id)
        if (customer) {
            session.withSession { session ->
                session.refresh(customer)
            }
        } else {
            throw new ValidationException("Customer not found with ID: $id")
        }
        return customer
    }
}
