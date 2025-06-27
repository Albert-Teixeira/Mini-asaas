package com.asaas.mini.utils
import grails.gorm.dirty.checking.*

@DirtyCheck
abstract class BaseEntity {

    Date dateCreated
    Date lastUpdated
    Boolean deleted = false

    static constraints = {
        deleted nullable: false
    }

    static mapping = {
        tablePerHierarchy false
    }
}
