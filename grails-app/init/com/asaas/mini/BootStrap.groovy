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
            houseNumber: 123,
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
    }
    def destroy = {
    }
}
