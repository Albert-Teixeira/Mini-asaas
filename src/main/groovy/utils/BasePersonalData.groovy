package com.asaas.mini.utils

abstract class BasePersonalData extends BaseEntity {

    String name
    String email
    String phoneNumber
    String cpfCnpj
    String state
    String city
    String street
    Integer houseNumber
    String postalCode

    static mapping = {
        tablePerHierarchy false
    }
}