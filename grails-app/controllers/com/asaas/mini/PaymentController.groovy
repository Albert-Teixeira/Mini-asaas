package com.asaas.mini

class PaymentController {

    def index() {
        
        for (payment in Payment.getAll()) {
            render "payment: "
            
            render payment.customer
            render payment.payer
            render payment.paymentType
            render payment.value
            render payment.status
            render payment.dueDate
            render payment.dateReceived
        }
    }

    def show() {
        if(!params.id){
            render "missing parameter id"
            return
        }

        def payment = Payment.get(params.id)

        if(!payment){
            render "not found"
            return
        }

        render payment.customer
        render payment.payer
        render payment.paymentType
        render payment.value
        render payment.status
        render payment.dueDate
        render payment.dateReceived
    }

    def create() {
        // def customer = new Customer(
        //     name: "name",
        //     email: "email@email.com",
        //     phoneNumber: "15999999999",
        //     cpfCnpj: "123.456.789-09",
        //     state: "SP",
        //     city: "Boituva",
        //     street: "Pq. Ecologico",
        //     houseNumber: 123,
        //     postalCode: "15123-456")
        
        // if(!customer.save()){
        //     render "falha no customer "
        // }

        // def payer = new Payer(
        //     customer: customer,
        //     name: "name2",
        //     email: "email2@email.com",
        //     phoneNumber: "15999999999",
        //     cpfCnpj: "123.456.789-09",
        //     state: "SP",
        //     city: "Boituva",
        //     street: "Pq. Ecologico",
        //     houseNumber: 123,
        //     postalCode: "15123-456")
        
        // if(!payer.save()){
        //     render "falha no payer "
        // }
        
        // def payment = new Payment(
        //     customer: customer,
        //     payer: payer,
        //     paymentType: PaymentType.BOLETO,
        //     value: 45.55,
        //     status: StatusType.PENDENTE,
        //     dueDate: new Date(),
        //     dateReceived: new Date())
        
        // if(!payment.save()){
        //     render "falha no payment "
        // }
        // else{
        //     render "td certo :-) "
        // }

        // render payment

        // def p = new Payment()
        // if (!p.save()) {
        //     p.errors.allErrors.each {
        //         println it
        //     }
        // }
        // render p
    }

    def edit() {
        def payment = Payment.get(params.id)
        try {
            payment.properties = params
            payment.save()
        } catch (Exception e) {
            println(e.getMessage())
            render "nao foi"
            return false
        }
        render "foi!"
        return true
    }

    def remove() {
        def payment = Payment.get(params.id)
        try {
            payment.delete()
        } catch (Exception e) {
            println(e.getMessage())
            render "nao foi"
            return false
        }
        render "foi!"
        return true
    }

}
