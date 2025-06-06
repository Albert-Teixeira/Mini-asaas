package com.asaas.mini

import com.asaas.mini.utils.BaseEntity

class Payer extends BaseEntity {

    Customer customer
    String name
    String email
    String phone
    String cpfCnpj
    String state
    String city
    String street
    Integer number
    String postalCode

    static constraints = {
        customer nullable: false
        name blank: false, maxSize: 100
        email blank: false, email: true, maxSize: 100
        phone blank: false, maxSize: 20
        cpfCnpj blank: false, maxSize: 20
        state blank: false, maxSize: 50
        city blank: false, maxSize: 50
        street blank: false, maxSize: 100
        number min: 1, max: 99999
        postalCode blank: false, maxSize: 10
    }

    static mapping = {
        customer column: 'customer_id'
    }
}