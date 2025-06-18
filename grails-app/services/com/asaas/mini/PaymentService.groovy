package com.asaas.mini

import grails.gorm.transactions.Transactional
import java.text.SimpleDateFormat

@Transactional
class PaymentService {

    def createPayment(customerId, payerId, paymentType, value, dueDate) {

        def customer = Customer.get(customerId)

        def payer = Payer.get(payerId)

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date formatedDueDate = format.parse(dueDate);

        Date today = new Date()
        if(formatedDueDate.before(today)){
            return null
        }

        def payment = new Payment(
            customer: customer,
            payer: payer,
            paymentType: paymentType,
            value: value,
            status: StatusType.PENDENTE,
            dueDate: formatedDueDate,
            dateReceived: null)

        try {
            payment.save(failOnError: true)
        } catch (Exception e) {
            println(e.getMessage())
            return null
        }
        
        return payment
    }

    def getPayments(Boolean deleted) {
        if(deleted){
            def payments = Payment.findAllByDeleted(true)
            return payments
        }

        def payments = Payment.findAllByDeleted(false)

        return payments
    }

    def getPaymentsByCustomer(Customer customer) {
        def payments = Payment.findAll {
            customer == customer
        }

        return payments
    }

    def getPaymentsByPayer(Payer payer) {

        def payments = Payment.findAll {
            payer == payer
        }

        return payments
    }

    def getPaymentsByCustomerAndPayer(Customer customer, Payer payer) {
        def payments = Payment.findAll {
            customer == customer
            payer == payer
        }

        return payments
    }

    def editPayment(payment, value, dueDate) {

        def sanitizedValue = Double.parseDouble(value)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date formatedDueDate = format.parse(dueDate);

        Date today = new Date()
        if(formatedDueDate.before(today)){
            return null
        }

        try {
            payment.value = sanitizedValue
            payment.dueDate = formatedDueDate
            payment.save(failOnError: true)
        } catch (Exception e) {
            println(e.getMessage())
            return null
        }

        return payment
    }

    def deletePayment(payment) {
        if(payment.deleted == true){
            return false
        }

        try {
            payment.deleted = true
            payment.save(flush: true, failOnError: true)
        } catch (Exception e) {
            println(e.getMessage())
            return false
        }

        return true
    }

    def restorePayment(payment,dueDate = null) {
        if(payment.deleted == false){
            return null
        }

        if(payment.status == StatusType.RECEBIDA){
            return null
        }

        if(payment.status == StatusType.VENCIDA && !dueDate){
            return null
        }

        Date today = new Date()
        if(dueDate && dueDate.before(today)){
            return null
        }

        try {
            if(payment.status != StatusType.RECEBIDA) {
                payment.status = StatusType.PENDENTE
            }
            if(dueDate){
                payment.dueDate = dueDate
            }
            payment.deleted = false
        } catch(Exception e){
            println(e.getMessage())
            return null
        }

        return payment
    }

    def confirmPayment(payment){
        if(payment.deleted){
            return false
        }

        if(payment.status != StatusType.PENDENTE){
            return false
        }

        try {
            payment.status = StatusType.RECEBIDA
            payment.dateReceived = new Date()
        } catch(Exception e) {
            println(e.getMessage())
            return false
        }

        return true
    }
}
