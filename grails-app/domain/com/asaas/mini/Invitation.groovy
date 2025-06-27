package com.asaas.mini

import com.asaas.mini.utils.BaseEntity

class Invitation extends BaseEntity{

    String email
    Customer customer
    Boolean expired

    static constraints = {
    }
}
