package com.asaas.mini

import com.asaas.mini.Payer
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import groovy.lang.MissingMethodException

@Transactional
class PayerService {

    Payer get(Serializable id) {
        Payer.get(id)
    }

    List<Payer> list(Map params) {
        Payer.list(params)
    }

    Long count(){
        Payer.count()
    }

    void delete(Serializable id) {
        Payer payer = Payer.get(id)
        if (payer) {
            payer.delete(flush: true)
        } else {
            throw IllegalArgumentException("Payer with id ${id} not found")
        }
    }

    Payer save(Map params) {
        Payer payer = new Payer()

        payer.name = params.name
        payer.email = params.email
        payer.phoneNumber = params.phoneNumber
        payer.cpfCnpj = params.cpfCnpj
        payer.state = params.state
        payer.city = params.city
        payer.street = params.street
        payer.houseNumber = params.houseNumber ? params.houseNumber.toInteger() : null
        payer.postalCode = params.postalCode

        if (!payer.validate()) {
            throw new ValidationException("Invalid Payer data: ${payer.errors}")
        }
        payer.save(flush: true)
        return payer
    }

    Payer update(Payer payer, Map params) {
        if (payer == null) {
            throw new IllegalArgumentException("Payer cannot be null for update")
        }

        payer.name = params.name
        payer.email = params.email
        payer.phoneNumber = params.phoneNumber
        payer.cpfCnpj = params.cpfCnpj
        payer.state = params.state
        payer.city = params.city
        payer.street = params.street
        payer.houseNumber = params.houseNumber ? params.houseNumber.toInteger() : null
        payer.postalCode = params.postalCode

        if (!payer.validate()) {
            throw new ValidationException("Invalid Payer data: ${payer.errors}")
        }

        payer.save(flush: true, failOnError: true)
        println "Payer updated successfully"

        return payer
    }

    Payer reload(Serializable id) {
        Payer payer = Payer.get(id)
        if (payer) {
            Payer.withSession { session ->
                session.refresh(payer)
            }
        }
        return payer
    }
}