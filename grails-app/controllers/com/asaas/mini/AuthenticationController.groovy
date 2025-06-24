package com.asaas.mini
import grails.plugin.springsecurity.annotation.Secured

class AuthenticationController {

    static allowedMethods = [index: "GET", save: "POST", manage: "GET", invite: "POST", invitation: "GET", saveInvitedUser: "POST"]

    AuthenticationService authenticationService

    def index() {
        redirect(action: "register")
    }

    def register() {
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
            addressNumber: addressNumber,
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

        Invitation invitation = new Invitation(email=params.email,
            customer=customer,
            expired=false)
        
        //send email with invitation object to email service and in email service send email to user with link /authentication/invitation

        render "convite enviado"
    }

    def invitation() {
        if(!params.id){
            render "Convite inválido"
            request.status = 404
            return
        }

        Integer id = Integer.parseInt(params.id)

        Invitation invitation = Invitation.get(id)

        if(!invitation){
            render "Convite inválido"
            request.status = 404
            return
        }

        if(invitation.expired){
            render "Convite inválido"
            request.status = 404
            return
        }

        render(view: "invitation", model: [id: id])
    }

    def saveInvitedUser() {
        if(!params.id){
            render "Convite inválido"
            request.status = 404
            return
        }

        Integer id = Integer.parseInt(params.id)
        String password = params.password
        String password2 = params.password2

        Invitation invitation = Invitation.get(id)

        if(!invitation){
            render "Convite inválido"
            request.status = 404
            return
        }

        if(invitation.expired){
            render "Convite inválido"
            request.status = 404
            return
        }

        if(password != password2){
            render "As senhas devem coincidir"
            request.status = 400
            return
        }

        String email = invitation.email
        Customer customer = invitation.customer
        
        User user

        try {
            user = authenticationService.registerUserAndCustomer(email, password, customer)
        } catch (Exception e) {
            println(e.getMessage())
            request.status = 500
            render("Ocorreu um erro ao cadastrar o usuário")
            return
        }

        invitation.expired = true
        invitation.save()

        render "Parabéns '${user.username}', agora você possui acesso à conta"
    }

    def remove() {
        if(!params.id){
            render "Falta o parâmetro id"
            request.status = 404
            return
        }

        Integer id = Integer.parseInt(params.id)

        try {
            authenticationService.deleteUser(User.get(id))
        } catch (Exception e) {
            println(e.getMessage())
            request.status = 500
            return
        }

        render("Usuário removido com sucesso")
    }
}
