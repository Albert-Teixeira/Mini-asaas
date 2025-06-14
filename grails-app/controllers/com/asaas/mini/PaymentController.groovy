package com.asaas.mini

import grails.converters.JSON

import java.text.SimpleDateFormat

class PaymentController {

    PaymentService paymentService

    def index() {
        Boolean deleted = (params.id == "1")
        List<Payment> payments = paymentService.getPayments(deleted)

        response.status = 200
        render(view: "index", model: [payments: payments, statusType: StatusType, deleted: deleted])
    }

    def show() {
        if(!params.id){
            response.status = 400
            render([error: "missing parameter id"] as JSON)
            return
        }

        int id = Integer.parseInt(params.id)

        Payment payment = paymentService.getPaymentById(id)

        if(!payment){
            response.status = 404
            render([error: "not found"] as JSON)
            return
        }

        response.status = 200
        render(view: "show", model: [payment: payment, statusType: StatusType])
    }

    def create() {

        if(request.method == "GET"){
            render(view: "create")
            response.status = 200
            return
        }
        
        if(request.method != "POST"){
            response.status = 405
            render([error: "405 Method Not Allowed"] as JSON)
            return
        }

        int customerId = Integer.parseInt(params.customer_id)
        int payerId = Integer.parseInt(params.payer_id)
        PaymentType paymentType = PaymentType.valueOf(params.payment_type)
        Double value = Double.parseDouble(params.value)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date dueDate = format.parse(params.due_date);

        if(!customerId || !payerId || !paymentType || !value || !dueDate){
            String errorMessage = "missing parameters: "

            if(!customerId) errorMessage += "customer_id "
            if(!payerId) errorMessage += "payer_id "
            if(!paymentType) errorMessage += "payment_type "
            if(!value) errorMessage += "value "
            if(!dueDate) errorMessage += "due_date"

            response.status = 400
            render([error: errorMessage] as JSON)
        }

        Payment payment = paymentService.createPayment(customerId, payerId, paymentType, value, dueDate)

        if(!payment){
            response.status = 400
            render([error: "payment can't be created"] as JSON)
        }

        response.status = 201
        redirect(action: "show", id: payment.id)
    }

    //Isso aqui vai virar Receber pagamento, Mudar valor do pagamento, 
    def edit() {

        if(request.method != "GET" && request.method != "POST"){
            response.status = 405
            render([error: "405 Method Not Allowed"] as JSON)
            return
        }

        if(!params.id){
            response.status = 400
            render([error: "missing parameter id"] as JSON)
        }

        if(request.method == "GET"){
            Payment payment = Payment.get(params.id)
            response.status=200
            render(view: "edit", model: [payment: payment])
            return
        }

        int id = Integer.parseInt(params.id)
        Double value = Double.parseDouble(params.value)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        Date dueDate = format.parse(params.due_date)
        
        Payment payment = paymentService.editPayment(id, value, dueDate)

        if(!payment){
            response.status = 400
            render([error: "payment can't be edited"] as JSON)
        }
        
        redirect(view: "show", model: [payment: payment])
    }

    def remove() {
        if(!params.id){
            response.status = 400
            render([error: "missing parameter id"] as JSON)
        }

        int id = Integer.parseInt(params.id)

        Boolean deleted = paymentService.deletePayment(id)

        if(!deleted){
            response.status = 400
            render([error: "payment can't be deleted"] as JSON)
        }
        
        response.status = 200
        //colocar flash aqui
        redirect(action: "index")
    }

    def restore() {
        if(!params.id){
            response.status = 400
            render([error: "missing parameter id"] as JSON)
        }

        Payment payment

        int id = Integer.parseInt(params.id)

        if(!params.due_date){
            payment = paymentService.restorePayment(id)
        }
        else{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date dueDate = format.parse(params.due_date);
            payment = paymentService.restorePayment(id,dueDate) //Caso o pagamento excluido ja tenha vencido e precise de uma renovação
        }

        if(!payment){
            response.status = 400
            render([error: "payment can't be restored"] as JSON)
        }
        
        response.status = 200
        //flash aqui
        render(view: "show", model: [payment: payment, statusType: StatusType])
    }

    def confirm() {
        if(!params.id){
            response.status = 400
            render([error: "missing parameter id"] as JSON)
        }

        int id = Integer.parseInt(id)

        Boolean confirmed = paymentService.confirmPayment(id)

        if(!confirmed){
            response.status = 400
            render([error: "payment can't be confirmed"] as JSON)
        }
        
        response.status = 200
        redirect(action: "show", id: params.id)
    }

    def generateReceipt() {
        //To do
    }

}