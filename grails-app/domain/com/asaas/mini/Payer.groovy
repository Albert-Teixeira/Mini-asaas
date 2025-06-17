package com.asaas.mini

import com.asaas.mini.utils.BaseEntity

class Payer extends BaseEntity {

    String name
    String email
    String phoneNumber
    String cpfCnpj
    String state
    String city
    String street
    Integer houseNumber
    String postalCode
    
    //Customer customer

    static constraints = {
    //    customer nullable: true

        name blank: false, maxSize: 100;
        email blank: false, email: true, maxSize: 100;
        phoneNumber blank: false, maxSize: 20;
        cpfCnpj blank: false, maxSize: 20;
        state blank: false, maxSize: 50;
        city blank: false, maxSize: 50;
        street blank: false, maxSize: 100;
        houseNumber min: 1, max: 99999;
        postalCode blank: false, maxSize: 10;
    }
    
    static mapping = {
        table 'payer'
    }

/*
    static mapping = {
        customer column: 'customer_id'
    }*/
}