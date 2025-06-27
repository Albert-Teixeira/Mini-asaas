package com.asaas.mini

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = {
        initializeRoles()
    }

    @Transactional
    void initializeRoles() {
        if(Role.getAll().size() != 2){
            Role ownerRole = new Role(authority: 'ROLE_OWNER').save(failOnError: true)
            Role userRole = new Role(authority: 'ROLE_USER').save(failOnError: true)
        }
    }

    def destroy = {
    }
}
