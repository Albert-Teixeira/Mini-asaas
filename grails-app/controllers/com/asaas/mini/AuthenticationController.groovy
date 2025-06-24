package com.asaas.mini

class AuthenticationController {

    static allowedMethods = [index: "GET", save: "POST"]

    AuthenticationService authenticationService

    def index() {
        render(view: "register")
    }

    def save() {
        
        String name = params.name
        String email = params.email
        String phoneNumber = params.phoneNumber
        String cpfCnpj = params.cpfCnpj
        String state = params.state
        String city = params.city
        String street = params.street
        Integer addressNumber = Integer.parseInt(params.addressNumber)
        String postalCode = params.postalCode
        String password = params.password
        String password2 = params.password2

        if(password != password2){
            request.status = 400
            render("As senhas devem coincidir")
            return
        }

        User user
        Customer customer = new Customer(
            name: name,
            email: email,
            phoneNumber: phoneNumber,
            cpfCnpj: cpfCnpj,
            state: state,
            city: city,
            street: street,
            houseNumber: addressNumber,
            postalCode: postalCode)

        try {
            user = authenticationService.registerUserAndCustomer(email, password, customer)
        } catch (Exception e) {
            println(e.getMessage())
            request.status = 500
            render("Ocorreu um erro ao cadastrar o usuário")
            return
        }

        render "Usuário '${user.username}' cadastrado com sucesso"
    }
}
