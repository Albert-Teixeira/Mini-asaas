package com.asaas.mini

import grails.gorm.transactions.Transactional

@Transactional
class PaymentService {

    Payment createPayment(Customer customer, Payer payer, PaymentType paymentType, Double value, Date dueDate) {
        Payment payment = new Payment(
            customer: customer,
            payer: payer,
            paymentType: paymentType,
            value: value,
            statusType: StatusType.PENDING,
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

    // List<Payment> getPayments(Boolean deleted) {
    //     if(deleted){
    //         List<Payment> payments = Payment.findAllByDeleted(true)
    //         return payments
    //     }

    //     List<Payment> payments = Payment.findAllByDeleted(false)

    //     return payments
    // }

    List<Payment> getPaymentsByCustomer(Customer customer, Boolean deleted=false) {
        List<Payment> payments = Payment.findAll {
            deleted == deleted
            customer == customer
        }

        return payments
    }

    List<Payment> getPaymentsByCustomerAndPayer(Customer customer, Payer payer) {
        List<Payment> payments = Payment.findAll {
            customer == customer
            payer == payer
        }

        return payments
    }

    Payment editPayment(Payment payment, Date dueDate) {

        Date today = new Date()
        if(dueDate.before(today)){
            return null
        }

        try {
            payment.dueDate = dueDate
            payment.save(failOnError: true)
        } catch (Exception e) {
            println(e.getMessage())
            return null
        }

        return payment
    }

    Boolean deletePayment(Payment payment){
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

    Payment restorePayment(Payment payment) {
        if(payment.deleted == false){
            return null
        }

        try {
            payment.deleted = false
        } catch(Exception e){
            println(e.getMessage())
            return null
        }

        return payment
    }

    Boolean confirmPayment(Payment payment){
        if(payment.deleted){
            return false
        }

        if(payment.statusType != StatusType.PENDING){
            return false
        }

        try {
            payment.statusType = StatusType.RECEIVED
            payment.dateReceived = new Date()
        } catch(Exception e) {
            println(e.getMessage())
            return false
        }

        return true
    }

    void expirePayment(Payment payment){
        payment.statusType = StatusType.OVERDUE
    }

    void checkExpiredPayments(){
        Date today = new Date()

        List<Payment> paymentList = Payment.createCriteria().list {
            le("dueDate", today)
            and {
                like("statusType", StatusType.PENDING)
            }
        }

        for (Payment payment in paymentList) {
            expirePayment(payment)
        }
    }
}
