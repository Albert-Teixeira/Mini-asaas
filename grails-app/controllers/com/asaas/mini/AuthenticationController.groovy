package com.asaas.mini
import grails.plugin.springsecurity.annotation.Secured

class AuthenticationController {

    static allowedMethods = [index: "GET", save: "POST", manage: "GET", invite: "POST"]

    AuthenticationService authenticationService

    def index() {
        render(view: "index")
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

    @Secured('ROLE_OWNER')
    def manage() {

        User owner = getAuthenticatedUser()
        Customer accountOwner = owner.customer

        List<User> accountUserList = authenticationService.getUsersByCustomerAccount(accountOwner)

        render(view: "manage", model: [accountUserList: accountUserList])
    }

    @Secured('ROLE_OWNER')
    def invite() {
        if(!params.email){
            render "Falta o parâmetro email"
            request.status = 400
            return
        }

        User owner = getAuthenticatedUser()
        Customer accountOwner = owner.customer

        Invitation invitation = new Invitation(customer=customer,
            expired=false)
        
        //send email with invitation object to email service and in email service send email to user with link /authentication/invitation

        render "convite enviado"
    }

    def invitation() {
        if(!params.id){
            render "Convite inválido"
            request.status = 400
            return
        }

        Integer id = Integer.parseInt(params.id)

        User owner = getAuthenticatedUser()
        Customer accountOwner = owner.customer

        render "convite enviado"
    }
}
