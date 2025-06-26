package com.asaas.mini

import com.asaas.mini.Customer
import groovy.lang.MissingMethodException
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class CustomerService {

    Customer save(Map params) {

        Customer customer = new Customer()

        List<String> errors = validateCustomerParams(params)
        if (errors) {
            throw new ValidationException("Dados de cliente inválidos: ${errors.join(', ')}", customer.errors)
        }

        customer.name = params.name
        customer.email = params.email
        customer.phoneNumber = params.phoneNumber
        customer.cpfCnpj = params.cpfCnpj
        customer.state = params.state
        customer.city = params.city
        customer.street = params.street
        customer.addressNumber = params.addressNumber?.toString()?.trim()?.toInteger()
        customer.postalCode = params.postalCode
        customer.complement = params.complement ?: null

        if (!customer.validate()) {
            throw new ValidationException("Dados de cliente inválidos: ${customer.errors}")
        }

        customer.save(flush: true)
        return customer
    }

    List<Customer> list(Map params) {
        Customer.list(params)
    }

    Long count() {
        Customer.count()
    }

    Customer get(Serializable id) {
        Customer.get(id)
    }

    Customer update(Customer customer, Map params) {
        if (customer == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo para atualizá-lo")
        }

        List<String> errors = validateCustomerParams(params)
        if (errors) {
            throw new ValidationException("Dados de cliente inválidos: ${errors.join(', ')}", customer.errors)
        }

        customer.name = params.name
        customer.email = params.email
        customer.phoneNumber = params.phoneNumber
        customer.cpfCnpj = params.cpfCnpj
        customer.state = params.state
        customer.city = params.city
        customer.street = params.street
        customer.addressNumber = params.addressNumber?.toString()?.trim()?.toInteger()
        customer.postalCode = params.postalCode
        customer.complement = params.complement ?: null

        if (!customer.validate()) {
            throw new ValidationException("Dados de cliente inválidos: ${customer.errors}")
        }

        customer.save(flush: true, failOnError: true)
        return customer
    }

    void delete(Serializable id) {
        Customer customer = Customer.get(id)
        if (customer) {
            customer.delete(flush: true)
        } else {
            throw new IllegalArgumentException("Cliente com id ${id} não encontrado")
        }
    }

    Customer reload(Serializable id) {
        Customer customer = Customer.get(id)
        if (customer) {
            session.withSession { session ->
                session.refresh(customer)
            }
        } else {
            throw new IllegalArgumentException("Cliente com id ${id} não encontrado")
        }
        return customer
    }

    private List<String> validateCustomerParams(Map params) {
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

        Integer numero
        try {
            numero = params.addressNumber?.toString()?.trim()?.toInteger()
        } catch (Exception e) {
            numero = null
        }

        if (numero == null || numero < 1 || numero > 99999) {
            errors << "Número do endereço deve ser um inteiro entre 1 e 99999"
        }

        if (!params.postalCode || !(params.postalCode ==~/\d{5}-?\d{3}/)) {
            errors << "CEP inválido: formato esperado: XXXXX-XXX"
        }

        return errors
    }
}
