package com.asaas.mini

import com.asaas.mini.utils.BaseEntity

class Customer extends BaseEntity {

    static constraints = {
        name blank: false, maxSize: 100
        email blank: false, email: true, maxSize: 100
        cpfCnpj blank: false, maxSize: 20
        state blank: false, maxSize: 50
        city blank: false, maxSize: 50
        street blank: false, maxSize: 100
        number min: 1, max: 99999
        postalCode blank: false, maxSize: 10
    }

    static mapping = {
        
    }
}
