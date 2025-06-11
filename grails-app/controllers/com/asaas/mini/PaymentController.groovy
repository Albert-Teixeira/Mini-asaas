package com.asaas.mini

import grails.converters.JSON

class PaymentController {

    PaymentService paymentService

    def index() {
        def payments = paymentService.getPayments()

        response.status = 200
        render(payments as JSON)
    }

    def show() {
        if(!params.id){
            response.status = 400
            render([error: "missing parameter id"] as JSON)
            return
        }

        def payment = paymentService.getPaymentById(params.id)

        if(!payment){
            response.status = 404
            render([error: "not found"] as JSON)
            return
        }

        response.status = 200
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
            response.status = 400
            render([error: errorMessage] as JSON)
        }

        def payment = paymentService.createPayment(customer, payer, paymentType, value, dueDate)

        if(!payment){
            response.status = 400
            render([error: "payment can't be created"] as JSON)
        }

        response.status = 201
        render(payment as JSON)
    }

    def edit() {
        if(!params.id){
            response.status = 400
            render([error: "missing parameter id"] as JSON)
        }

        def payment = paymentService.editPayment(params)

        if(!payment){
            response.status = 400
            render([error: "payment can't be edited"] as JSON)
        }
        
        render(payment as JSON)
    }

    def remove() {

        if(!params.id){
            response.status = 400
            render([error: "missing parameter id"] as JSON)
        }

        Boolean deleted = paymentService.deletePayment(params.id)

        if(!deleted){
            response.status = 400
            render([error: "payment can't be deleted"] as JSON)
        }
        
        response.status = 200
        render([status: "payment sucessful deleted"] as JSON)
    }

}
