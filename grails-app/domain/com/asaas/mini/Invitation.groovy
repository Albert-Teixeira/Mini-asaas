package com.asaas.mini

class Invitation extends BaseEntity{

    String email
    Customer customer
    Boolean expired

    static constraints = {
    }
}
