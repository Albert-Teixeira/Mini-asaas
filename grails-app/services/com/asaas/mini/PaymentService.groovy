package com.asaas.mini

import grails.gorm.transactions.Transactional

@Transactional
class PaymentService {

    def createPayment(customer, payer, paymentType, value, dueDate) {
        def payment = new Payment(
            customer: customer,
            payer: payer,
            paymentType: paymentType,
            value: value,
            status: StatusType.PENDENTE,
            dueDate: dueDate,
            dateReceived: null)

        try {
            payment.save(failOnError: true)
        } catch (Exception e) {
            println(e.getMessage())
            return null
        }
        
        return payment
    }

    def getPayments() {
        def payments = Payment.getAll()

        return payments
    }

    def getPaymentById(id) {
        def payment = Payment.get(id)

        return payment
    }

    def getPaymentsByCustomer(customer) {
        def payments = Payment.findAll {
            customer == customer
        }

        return payments
    }

    def getPaymentsByPayer(payer) {
        def payments = Payment.findAll {
            payer == payer
        }

        return payments
    }

    def getPaymentsByCustomerAndPayer(customer, payer) {
        def payments = Payment.findAll {
            customer == customer
            payer == payer
        }

        return payments
    }

    def editPayment(properties) {
        def payment = Payment.get(properties.id)

        try {
            payment.properties = properties

            payment.save(failOnError: true)
        } catch (Exception e) {
            println(e.getMessage())
            return null
        }

        return payment
    }

    def deletePayment(id) {
        def payment = Payment.get(id)

        try {
            payment.delete(failOnError: true)
        } catch (Exception e) {
            println(e.getMessage())
            return false
        }

        return true
    }
}
