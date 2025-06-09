package com.asaas.mini

import grails.gorm.services.Service

@Service(Customer)
interface CustomerService {

    Customer get(Serializable id)

    List<Customer> list(Map args)

    Long count()

    void delete(Serializable id)

    Customer save(Customer customer)

}