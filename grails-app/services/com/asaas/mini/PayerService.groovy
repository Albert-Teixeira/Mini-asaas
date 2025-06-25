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
            throw IllegalArgumentException("Pagador com id ${id} não encontrado")
        }
    }

    Payer save(Map params) {
        Payer payer = new Payer()

        List<String> errors = validatePayerParams(params)
        if (errors) {
            throw new ValidationException("Dados de pagador inválidos: ${errors.join(', ')}", payer.errors)
        }

        payer.customer = Customer.get(params.customer.id)
        payer.name = params.name
        payer.email = params.email
        payer.phoneNumber = params.phoneNumber
        payer.cpfCnpj = params.cpfCnpj
        payer.state = params.state
        payer.city = params.city
        payer.street = params.street
        payer.addressNumber = params.addressNumber ? params.addressNumber.toInteger() : null
        payer.postalCode = params.postalCode

        if (!payer.validate()) {
            throw new ValidationException("Dados de pagador inválidos: ${payer.errors}")
        }
        payer.save(flush: true)
        return payer
    }

    Payer update(Payer payer, Map params) {
        if (payer == null) {
            throw new IllegalArgumentException("Pagador não pode ser nulo para atualizá-lo")
        }

        List<String> errors = validatePayerParams(params)
        if (errors) {
            throw new ValidationException("Dados de pagador inválidos: ${errors.join(', ')}", payer.errors)
        }

        payer.customer = Customer.get(params.customer.id)
        payer.name = params.name
        payer.email = params.email
        payer.phoneNumber = params.phoneNumber
        payer.cpfCnpj = params.cpfCnpj
        payer.state = params.state
        payer.city = params.city
        payer.street = params.street
        payer.addressNumber = params.addressNumber ? params.addressNumber.toInteger() : null
        payer.postalCode = params.postalCode

        if (!payer.validate()) {
            throw new ValidationException("Dados de pagador inválidos: ${payer.errors}")
        }

        payer.save(flush: true, failOnError: true)

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

    private List<String> validatePayerParams(Map params) {
        List<String> errors = []
        if (!params.name) {
            errors << "Nome é obrigatório"
        }
        if (!params.email || !params.email.contains('@')) {
            errors << "Email inválido"
        }
        if (!params.phoneNumber || !(params.phoneNumber ==~/\(?\d{2}\)?\s?\d{4,5}-?\d{4}/)) {
            errors << "Número de telefone inválido: formato esperado: (XX) XXXX-XXXX ou (XX) XXXXX-XXXX"
        }
        if (!params.cpfCnpj || !(params.cpfCnpj ==~/\d{11}|\d{14}/)) {
            errors << "CPF/CNPJ inválido: deve ser 11 dígitos (CPF) ou 14 dígitos (CNPJ)"
        }
        if (!params.state || !(params.state ==~/(?i)[a-z]{2}/)) {
            errors << "Estado inválido: deve ser uma sigla de dois caracteres em maiúsculas"
        }
        if (!params.city) {
            errors << "Cidade é obrigatória"
        }
        if (!params.street) {
            errors << "Rua é obrigatória"
        }
        if (!params.addressNumber || params.addressNumber.trim().isEmpty() || params.addressNumber < 1 || params.addressNumber > 99999) {
            errors << "Número do endereço deve ser um inteiro entre 1 e 99999"
        }
        if (!params.postalCode || !(params.postalCode ==~/\d{5}-?\d{3}/)) {
            errors << "CEP inválido: formato esperado: XXXXX-XXX"
        }
        return errors
    }
}