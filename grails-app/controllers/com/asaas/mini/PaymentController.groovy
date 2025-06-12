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
        def customerId = params.customer_id
        def payerId = params.payer_id
        def paymentType = params.payment_type
        def value = params.value
        def dueDate = params.due_date

        if(!customerId || !payerId || !paymentType || !value || !dueDate){
            def errorMessage = "missing parameters: "

            if(!customerId) errorMessage += "customer_id "
            if(!payerId) errorMessage += "payer_id "
            if(!paymentType) errorMessage += "payment_type "
            if(!value) errorMessage += "value "
            if(!dueDate) errorMessage += "due_date"

            response.status = 400
            render([error: errorMessage] as JSON)
        }

        def payment = paymentService.createPayment(customerId, payerId, paymentType, value, dueDate)

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

    def restore() {
        if(!params.id){
            response.status = 400
            render([error: "missing parameter id"] as JSON)
        }

        Boolean restored

        if(!due_date){
            restored = paymentService.restorePayment(params.id)
        }
        else{
            restored = paymentService.restorePayment(params.id,params.due_date)
        }


        if(!restored){
            response.status = 400
            render([error: "payment can't be restored"] as JSON)
        }
        
        response.status = 200
        render([status: "payment sucessful restored"] as JSON)
    }

    def confirm() {
        if(!params.id){
            response.status = 400
            render([error: "missing parameter id"] as JSON)
        }

        Boolean confirmed = paymentService.confirmPayment(params.id)
    }

    def generateReceipt() {
        //To do
    }

}
