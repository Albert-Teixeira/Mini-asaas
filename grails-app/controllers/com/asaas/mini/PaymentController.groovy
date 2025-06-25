package com.asaas.mini

import grails.converters.JSON

import java.text.SimpleDateFormat

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER','ROLE_OWNER'])
class PaymentController {

    PaymentService paymentService

    static allowedMethods = [
        index: "GET",
        show: "GET",
        create: "GET",
        edit: "GET",
        save: "POST",
        update: "POST",
        remove: "GET",
        restore: "GET",
        confirm: "GET"]

    def index() {
        Boolean deleted = (params.deleted == "1")
        List<Payment> paymentList = paymentService.getPayments(deleted)

        render(view: "index", model: [paymentList: paymentList, statusType: StatusType, deleted: deleted])
    }

    def show() {
        if(!params.id){
            response.status = 400
            render([erro: "O parâmetro id está faltando"] as JSON)
            return
        }

        Integer id = Integer.parseInt(params.id)

        Payment payment = Payment.get(params.id)

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

        Integer payerId = Integer.parseInt(params.payer_id)
        PaymentType paymentType = PaymentType.valueOf(params.payment_type)
        Double value = Double.parseDouble(params.value)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date dueDate = format.parse(params.due_date);

        if(!payerId || !paymentType || !value || !dueDate){
            String errorMessage = "Faltam os parâmetros:"
            if(!payerId) errorMessage += " payer_id"
            if(!paymentType) errorMessage += " payment_type"
            if(!value) errorMessage += " value"
            if(!dueDate) errorMessage += " due_date"

            response.status = 400
            render([erro: errorMessage] as JSON)
        }

        User user = getAuthenticatedUser()
        Customer customer = user.customer

        Payer payer = Payer.get(payerId)

        Payment payment = paymentService.createPayment(customer, payer, paymentType, value, dueDate)

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

        Integer id = Integer.parseInt(params.id)
        Double value = Double.parseDouble(params.value)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        Date dueDate = format.parse(params.due_date)
        
        Payment payment = Payment.get(params.id)
        payment = paymentService.editPayment(payment, value, dueDate)

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

        Integer id = Integer.parseInt(params.id)
        Payment payment = Payment.get(id)

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

        Integer id = Integer.parseInt(params.id)
        Payment payment = Payment.get(id)

        if(!params.due_date){
            payment = paymentService.restorePayment(payment)
        }
        else{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date dueDate = format.parse(params.due_date);
            payment = paymentService.restorePayment(payment,dueDate)
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

        Integer id = Integer.parseInt(params.id)
        Payment payment = Payment.get(id)

        Boolean confirmed = paymentService.confirmPayment(payment)

        if(!confirmed){
            response.status = 400
            render([erro: "O pagamento não pôde ser confirmado"] as JSON)
        }
        
        redirect(action: "show", id: params.id)
    }
}