package com.asaas.mini

import grails.converters.JSON

class PaymentController {

    PaymentService paymentService

    static allowedMethods = [index: "GET",
        show: "GET",
        create: "GET",
        edit: "GET",
        save: "POST",
        update: "POST",
        remove: "GET",
        restore: "GET",
        confirm: "GET"]

    def index() {
        def paymentList = paymentService.getPayments(params.deleted)

        render(view: "index", model: [paymentList: paymentList, statusType: StatusType, deleted: params.deleted])
    }

    def show() {
        if(!params.id){
            response.status = 400
            render([erro: "O parâmetro id está faltando"] as JSON)
            return
        }

        def payment = paymentService.getPaymentById(params.id)

        if(!payment){
            response.status = 404
            render([erro: "Esse pagamento não foi encontrado"] as JSON)
            return
        }

        render(view: "show", model: [payment: payment, statusType: StatusType])
    }

    def create() {
        render(view: "create")
    }

    def edit() {
        def payment = Payment.get(params.id)

        render(view: "edit", model: [payment: payment])
    }

    def save() {
        def customerId = params.customer_id
        def payerId = params.payer_id
        def paymentType = params.payment_type
        def value = params.value
        def dueDate = params.due_date

        if(!customerId || !payerId || !paymentType || !value || !dueDate){
            def errorMessage = "Faltam os parâmetros:"

            if(!customerId) errorMessage += " customer_id"
            if(!payerId) errorMessage += " payer_id"
            if(!paymentType) errorMessage += " payment_type"
            if(!value) errorMessage += " value"
            if(!dueDate) errorMessage += " due_date"

            response.status = 400
            render([erro: errorMessage] as JSON)
        }

        def payment = paymentService.createPayment(customerId, payerId, paymentType, value, dueDate)

        if(!payment){
            response.status = 400
            render([erro: "Não foi possível criar o pagamento"] as JSON)
        }

        response.status = 201
        redirect(action: "show", id: payment.id)
    }

    def update() {
        if(!params.id){
            response.status = 400
            render([erro: "O parâmetro id está faltando"] as JSON)
        }

        Payment payment = Payment.get(params.id)
      
        payment = paymentService.editPayment(payment, params.value, params.due_date)

        if(!payment){
            response.status = 400
            render([erro: "O pagamento não pôde ser editado"] as JSON)
        }

        redirect(action: "show", id: payment.id)
    }

    def remove() {
        if(!params.id){
            response.status = 400
            render([erro: "O parâmetro id está faltando"] as JSON)
        }

        Payment payment = Payment.get(params.id)

        Boolean deleted = paymentService.deletePayment(payment)

        if(!deleted){
            response.status = 400
            render([erro: "O pagamento não pôde ser deletado"] as JSON)
        }
        
        redirect(action: "index")
    }

    def restore() {
        if(!params.id){
            response.status = 400
            render([erro: "O parâmetro id está faltando"] as JSON)
        }

        Payment payment = Payment.get(params.id)

        if(!params.due_date){
            payment = paymentService.restorePayment(payment)
        }
        else{
            payment = paymentService.restorePayment(payment,params.due_date)
        }

        if(!payment){
            response.status = 400
            render([erro: "O pagamento não pôde ser restaurado"] as JSON)
        }
        
        render(view: "show", model: [payment: payment, statusType: StatusType])
    }

    def confirm() {
        if(!params.id){
            response.status = 400
            render([erro: "O parâmetro id está faltando"] as JSON)
        }

        Payment payment = Payment.get(params.id)

        Boolean confirmed = paymentService.confirmPayment(payment)

        if(!confirmed){
            response.status = 400
            render([erro: "O pagamento não pôde ser confirmado"] as JSON)
        }
        
        redirect(action: "show", id: params.id)
    }
}