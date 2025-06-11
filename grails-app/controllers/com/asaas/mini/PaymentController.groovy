package com.asaas.mini

import grails.converters.JSON

class PaymentController {

    PaymentService paymentService

    def index() {
        def payments = paymentService.getPayments()

        render(payments as JSON)
    }

    def show() {
        if(!params.id){
            render([error: "missing parameter id"] as JSON)
            return
        }

        def payment = paymentService.getPaymentById(params.id)

        if(!payment){
            render([error: "not found"] as JSON)
            return
        }

        render(payment as JSON)
    }

    def create() {
        def customer = params.customer
        def payer = params.payer
        def paymentType = params.payment_type
        def value = params.value
        def dueDate = params.due_date

        if(!customer || !payer || !paymentType || !value || !dueDate){
            def errorMessage = "missing parameters: "
            if(!customer) errorMessage += "customer "
            if(!payer) errorMessage += "payer "
            if(!paymentType) errorMessage += "payment_type "
            if(!value) errorMessage += "value "
            if(!dueDate) errorMessage += "due_date"
            render([error: errorMessage] as JSON)
        }

        def payment = paymentService.createPayment(customer, payer, paymentType, value, dueDate)

        if(!payment){
            render([error: "payment can't be created"] as JSON)
        }

        render(payment as JSON)
    }

    def edit() {
        if(!params.id){
            render([error: "missing parameter id"] as JSON)
        }

        def payment = paymentService.editPayment(params)

        if(!payment){
            render([error: "payment can't be edited"] as JSON)
        }
        
        render(payment as JSON)
    }

    def remove() {

        if(!params.id){
            render([error: "missing parameter id"] as JSON)
        }

        Boolean deleted = paymentService.deletePayment(params.id)

        if(!deleted){
            render([error: "payment can't be deleted"] as JSON)
        }
        
        render([status: "payment sucessful deleted"] as JSON)
    }

}
