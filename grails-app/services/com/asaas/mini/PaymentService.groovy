package com.asaas.mini

import grails.gorm.transactions.Transactional
import java.text.SimpleDateFormat

@Transactional
class PaymentService {

    Payment createPayment(String customerId, String payerId, String paymentType, String value, String dueDate) {

        Customer customer = Customer.get(customerId) //validar se achou depois

        Payer payer = Payer.get(payerId) //validar se achou depois

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date formatedDueDate = format.parse(dueDate);

        Payment payment = new Payment(
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

    List<Payment> getPayments(String deleted) {
        if(deleted == "1"){
            List<Payment> payments = Payment.findAllByDeleted(true)
            return payments
        }

        List<Payment> payments = Payment.findAllByDeleted(false)

        return payments
    }

    Payment getPaymentById(String id) {
        Payment payment = Payment.get(id)

        return payment
    }

    List<Payment> getPaymentsByCustomer(String customerId) {
        Customer customer = Customer.get(customerId)

        List<Payment> payments = Payment.findAll {
            customer == customer
        }

        return payments
    }

    List<Payment> getPaymentsByPayer(String payerId) {
        Payer payer = Payment.get(payerId)

        List<Payment> payments = Payment.findAll {
            payer == payer
        }

        return payments
    }

    List<Payment> getPaymentsByCustomerAndPayer(String customerId, String payerId) {
        Customer customer = Customer.get(customerId)
        
        Payer payer = Payment.get(payerId)

        List<Payment> payments = Payment.findAll {
            customer == customer
            payer == payer
        }

        return payments
    }

    Payment editPayment(String id, String value, String dueDate) {
        Payment payment = Payment.get(id)

        Double sanitizedValue = Double.parseDouble(value)

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

    Boolean deletePayment(String id) {
        Payment payment = Payment.get(id)

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

    Payment restorePayment(String id, String dueDate = null) {
        Payment payment = Payment.get(id)

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

    Boolean confirmPayment(String id){
        Payment payment = Payment.get(id)

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
