package com.asaas.mini.utils

abstract class BaseEntity {

    Date dateCreated
    Date lastUpdated
    Boolean deleted = false

    static mapping = {
        tablePerHierarchy false
    }
}
