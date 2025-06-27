package com.asaas.mini

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import java.text.SimpleDateFormat

@Secured(['ROLE_USER','ROLE_OWNER'])
class PaymentController {

    PaymentService paymentService

    static allowedMethods = [
        index: "GET",
        show: "GET",
        create: "GET",
        edit: "GET",
        save: "POST",
        update: "PUT",
        remove: "DELETE",
        restore: "PUT",
        confirm: "PUT"]

    //foi
    def index() {
        User user = getAuthenticatedUser()
        Customer customer = user.customer

        Boolean deleted = (params.deleted == "1")
        List<Payment> paymentList = paymentService.getPaymentsByCustomer(customer,deleted)

        render(view: "index", model: [paymentList: paymentList, statusType: StatusType, deleted: deleted])
    }

    //foi
    def show() {
        if(!params.id){
            response.status = 400
            render([erro: "O parâmetro id está faltando"] as JSON)
            return
        }

        User user = getAuthenticatedUser()
        Customer customer = user.customer

        Integer id = Integer.parseInt(params.id)

        Payment payment = Payment.find {
            id == id
            customer == customer
        }

        if(!payment){
            response.status = 404
            render([erro: "Esse pagamento não foi encontrado"] as JSON)
            return
        }

        render(view: "show", model: [payment: payment, statusType: StatusType])
    }

    //foi
    def create() {
        User user = getAuthenticatedUser()
        Customer customer = user.customer
        List<Payer> payerList = Payer.findAll {
            customer == customer
        }
        
        render(view: "create", model: [payment: new Payment(), payerList: payerList, paymentTypeList: PaymentType.values()])
    }

    //foi
    def edit() {
        if(!params.id){
            response.status = 400
            render([erro: "O parâmetro id está faltando"] as JSON)
            return
        }
        
        User user = getAuthenticatedUser()
        Customer customer = user.customer

        Integer id = Integer.parseInt(params.id)

        Payment payment = Payment.find {
            id == id
            customer == customer
        }

        if(!payment){
            redirect(view: "index")
            return
        }

        render(view: "edit", model: [payment: payment])
    }

    //foi
    def save() {
        Integer payerId = Integer.parseInt(params.payer)
        PaymentType paymentType = PaymentType.valueOf(params.paymentType)
        Double value = Double.parseDouble(params.value)
        Calendar cal = Calendar.getInstance()

        cal.set(
            params.myDate_year as Integer,
            params.myDate_month as Integer - 1,
            params.myDate_day as Integer,
            params.myDate_hour as Integer,
            params.myDate_minute as Integer,
            0)

        Date dueDate = cal.getTime();

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

        Payer payer = Payer.find {
            id == payerId
            customer == customer
        }

        if(!payer){
            response.status = 400
            render([erro: "Não foi possível criar o pagamento"] as JSON)
        }

        Payment payment = paymentService.createPayment(customer, payer, paymentType, value, dueDate)

        if(!payment){
            response.status = 400
            render([erro: "Não foi possível criar o pagamento"] as JSON)
        }

        response.status = 201
        redirect(action: "show", id: payment.id)
    }

    //foi
    def update() {
        if(!params.id){
            response.status = 400
            render([erro: "O parâmetro id está faltando"] as JSON)
        }

        Integer id = Integer.parseInt(params.id)

        Calendar cal = Calendar.getInstance()

        cal.set(
            params.myDate_year as Integer,
            params.myDate_month as Integer - 1,
            params.myDate_day as Integer,
            params.myDate_hour as Integer,
            params.myDate_minute as Integer,
            0)

        Date dueDate = cal.getTime();
        
        User user = getAuthenticatedUser()
        Customer customer = user.customer

        Payment payment = Payment.find {
            id == id
            customer == customer
        }

        if(!payment){
            render([erro: "O pagamento não pôde ser editado"] as JSON)
            return
        }

        payment = paymentService.editPayment(payment, dueDate)

        if(!payment){
            response.status = 400
            render([erro: "O pagamento não pôde ser editado"] as JSON)
        }

        redirect(action: "show", id: payment.id)
    }

    //foi
    def remove() {
        if(!params.id){
            response.status = 400
            render([erro: "O parâmetro id está faltando"] as JSON)
        }

        User user = getAuthenticatedUser()
        Customer customer = user.customer

        Integer id = Integer.parseInt(params.id)
        Payment payment = Payment.find{
            id == id
            customer == customer
        }

        if(!payment){
            render([erro: "O pagamento não pôde ser deletado"] as JSON)
            return
        }

        Boolean deleted = paymentService.deletePayment(payment)

        if(!deleted){
            response.status = 400
            render([erro: "O pagamento não pôde ser deletado"] as JSON)
        }
        
        redirect(action: "index")
    }

    //foi
    def restore() {
        if(!params.id){
            response.status = 400
            render([erro: "O parâmetro id está faltando"] as JSON)
        }

        User user = getAuthenticatedUser()
        Customer customer = user.customer

        Integer id = Integer.parseInt(params.id)
        Payment payment = Payment.find {
            id == id
            customer == customer
        }

        if(!payment){
            render([erro: "O pagamento não pôde ser restaurado"] as JSON)
            return
        }

        payment = paymentService.restorePayment(payment)

        if(!payment){
            response.status = 400
            render([erro: "O pagamento não pôde ser restaurado"] as JSON)
        }
        
        render(view: "show", model: [payment: payment, statusType: StatusType])
    }

    //falta editar view com método g:form
    def confirm() {
        if(!params.id){
            response.status = 400
            render([erro: "O parâmetro id está faltando"] as JSON)
        }

        User user = getAuthenticatedUser()
        Customer customer = user.customer

        Integer id = Integer.parseInt(params.id)
        Payment payment = Payment.find {
            id == id
            customer == customer
        }

        if(!payment){
            render([erro: "O pagamento não pôde ser confirmado"] as JSON)
            return
        }

        Boolean confirmed = paymentService.confirmPayment(payment)

        if(!confirmed){
            response.status = 400
            render([erro: "O pagamento não pôde ser confirmado"] as JSON)
        }
        
        redirect(action: "show", id: params.id)
    }
}