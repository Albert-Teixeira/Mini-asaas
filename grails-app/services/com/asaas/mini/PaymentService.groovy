package com.asaas.mini

import grails.gorm.transactions.Transactional
import java.text.SimpleDateFormat

@Transactional
class PaymentService {

    def createPayment(customerId, payerId, paymentType, value, dueDate) {

        def customer = Customer.get(customerId) //validar se achou depois

        def payer = Payer.get(payerId) //validar se achou depois

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date formatedDueDate = format.parse(dueDate);

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

        //To do: notificar cliente
        
        return payment
    }

    def getPayments(deleted) {
        if(deleted == "1"){
            def payments = Payment.findAllByDeleted(true)
            return payments
        }

        def payments = Payment.findAllByDeleted(false)

        return payments
    }

    def getPaymentById(id) {
        def payment = Payment.get(id)

        return payment
    }

    def getPaymentsByCustomer(customerId) {
        def customer = Customer.get(customerId)

        def payments = Payment.findAll {
            customer == customer
        }

        return payments
    }

    def getPaymentsByPayer(payerId) {
        def payer = Payment.get(payerId)

        def payments = Payment.findAll {
            payer == payer
        }

        return payments
    }

    def getPaymentsByCustomerAndPayer(customerId, payerId) {
        def customer = Customer.get(customerId)
        
        def payer = Payment.get(payerId)

        def payments = Payment.findAll {
            customer == customer
            payer == payer
        }

        return payments
    }

    def editPayment(id, value, dueDate) {
        def payment = Payment.get(id)

        def sanitizedValue = Double.parseDouble(value)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date formatedDueDate = format.parse(dueDate);

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

    def deletePayment(id) {
        def payment = Payment.get(id)

        if(payment.deleted == true){
            return false //Cobrança já deletada
        }

        try {
            if(payment.status == StatusType.VENCIDA || payment.status == StatusType.RECEBIDA){
                payment.deleted = true
            }
            else{
                payment.deleted = true
                payment.status = StatusType.ARQUIVADA
            }
            payment.save(failOnError: true)
        } catch (Exception e) {
            println(e.getMessage())
            return false
        }

        //To do: notificar cliente

        return true
    }

    def restorePayment(id,dueDate = null) {
        def payment = Payment.get(id)
        println(payment.status)

        if(payment.deleted == false){
            return null //Cobrança não foi deletada
        }

        if(payment.status == StatusType.RECEBIDA){
            return null
        }

        if(payment.status == StatusType.VENCIDA && !dueDate){
            return null //Cobrança vencida e não foi apresentado uma nova data de vencimento
        }

        if(dueDate){
            if(dueDate < Date()){
                return null //Nova data de cobrança menor que a data atual
            }
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

        //To do: Notificar cliente

        return payment
    }

    def confirmPayment(id){
        def payment = Payment.get(id)

        if(payment.deleted){
            return false //Cobrança deletada
        }

        if(payment.status != StatusType.PENDENTE){
            return false //Cobrança deve estar pendente
        }

        try {
            payment.status = StatusType.RECEBIDA //To do: Add saldo para o cliente depois?
            payment.dateReceived = new Date()
        } catch(Exception e) {
            println(e.getMessage())
            return false
        }

        //To do: Notificar cliente

        return true
    }
}
