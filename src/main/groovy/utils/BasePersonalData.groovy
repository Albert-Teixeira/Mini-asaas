package com.asaas.mini.utils
import grails.gorm.dirty.checking.*

@DirtyCheck
abstract class BasePersonalData extends BaseEntity {

    String name
    String email
    String phoneNumber
    String cpfCnpj
    String state
    String city
    String street
    Integer addressNumber
    String postalCode
    String complement

    static mapping = {
        tablePerHierarchy false
    }
}