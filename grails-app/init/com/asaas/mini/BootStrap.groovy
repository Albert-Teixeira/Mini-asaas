package com.asaas.mini

class BootStrap {

    def init = { servletContext ->

        //Hardcodando alguns dados para posteriores usos

        def customer = new Customer(
            name: "name",
            email: "email@email.com",
            phoneNumber: "15999999999",
            cpfCnpj: "123.456.789-09",
            state: "SP",
            city: "Boituva",
            street: "Pq. Ecologico",
            houseNumber: 123,
            postalCode: "15123-456")
        
        customer.save()

        def payer = new Payer(
            customer: customer,
            name: "name2",
            email: "email2@email.com",
            phoneNumber: "15999999999",
            cpfCnpj: "123.456.789-09",
            state: "SP",
            city: "Boituva",
            street: "Pq. Ecologico",
            houseNumber: 123,
            postalCode: "15123-456")
        
        payer.save()
        
        def payment = new Payment(
            customer: customer,
            payer: payer,
            paymentType: PaymentType.BOLETO,
            value: 45.55,
            status: StatusType.PENDENTE,
            dueDate: new Date(),
            dateReceived: new Date())
        
        payment.save()
    }
    def destroy = {
    }
}
