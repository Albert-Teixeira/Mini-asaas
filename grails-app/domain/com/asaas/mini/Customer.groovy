package com.asaas.mini

import com.asaas.mini.utils.BasePersonalData

class Customer extends BasePersonalData {

    static constraints = {
        name blank: false, maxSize: 100
        email blank: false, email: true, maxSize: 100
        cpfCnpj blank: false, maxSize: 20
        state blank: false, maxSize: 50
        city blank: false, maxSize: 50
        street blank: false, maxSize: 100
        addressNumber min: 1, max: 99999
        postalCode blank: false, maxSize: 10
        complement nullable: true, maxSize: 100
    }

    static mapping = {
        
    }
}
