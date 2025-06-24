package com.asaas.mini

class BootStrap {

    def init = { servletContext ->
        def customer = new Customer(
            name: "Carlos",
            email: "email@email.com",
            phoneNumber: "15999999999",
            cpfCnpj: "123.456.789-09",
            state: "SP",
            city: "Boituva",
            street: "Pq. Ecologico",
            addressNumber: 123,
            postalCode: "15123-456")
        
        customer.save()

        def payer = new Payer(
            customer: customer,
            name: "Rodrigo",
            email: "email2@email.com",
            phoneNumber: "15999999999",
            cpfCnpj: "123.456.789-09",
            state: "SP",
            city: "Boituva",
            street: "Pq. Ecologico",
            houseNumber: 123,
            postalCode: "15123-456")
        
        payer.save()
        
        // def payment = new Payment(
        //     customer: customer,
        //     payer: payer,
        //     paymentType: PaymentType.BANK_SLIP,
        //     value: 45.55,
        //     status: StatusType.PENDING,
        //     dueDate: new Date(),
        //     dateReceived: null)
        
        // payment.save()

        // payment = new Payment(
        //     customer: customer,
        //     payer: payer,
        //     paymentType: PaymentType.PIX,
        //     value: 250,
        //     status: StatusType.PENDING,
        //     dueDate: new Date(),
        //     dateReceived: null)
        
        // payment.save()
    }
    def destroy = {
    }
}
