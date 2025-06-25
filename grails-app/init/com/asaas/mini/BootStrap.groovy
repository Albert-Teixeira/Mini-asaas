package com.asaas.mini

class BootStrap {

    def init = { servletContext ->
        def customer = new Customer(
            name: "Carlos",
            email: "email@email.com",
            cpfCnpj: "123.456.789-09",
            state: "SP",
            city: "Boituva",
            street: "Pq. Ecologico",
            addressNumber: 123,
            postalCode: "15123-456")
        
        customer.save()
    }
    def destroy = {
    }
}
