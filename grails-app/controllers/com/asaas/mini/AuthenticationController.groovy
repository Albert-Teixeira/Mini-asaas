package com.asaas.mini
import grails.plugin.springsecurity.annotation.Secured

class AuthenticationController {

    static allowedMethods = [index: "GET", save: "POST", manage: "GET", invite: "POST", invitation: "GET", saveInvitedUser: "POST"]


    AuthenticationService authenticationService
    InvitationService invitationService
    CustomerService customerService

    def index() {
        redirect(action: "register")
    }

    def register() {
        render(view: "register")
    }

    def mail() {
        
        render "email enviado com sucesso"
    }

    def save() {
        String email = params.email
        String password = params.password
        String password2 = params.password2

        if(password != password2){
            request.status = 400
            render("As senhas devem coincidir")
            return
        }

        User user
        try {
            Customer customer = customerService.save(params)
            user = authenticationService.registerUserAndCustomer(email, password, customer, Role.get(1))
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

        Invitation invitation = new Invitation(
            email: params.email,
            customer: accountOwner,
            expired: false)
        
        invitation.save(failOnError: true)

        sendMail {
            to params.email
            subject "Hello John"
            html view: 'invitemail', model: [invitation: invitation]
        }

        redirect(action: "manage")
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
            authenticationService.expireInvitation(invitation)
            user = authenticationService.registerUserAndCustomer(email, password, customer, Role.get(2))
        } catch (Exception e) {
            println(e.getMessage())
            request.status = 500
            render("Ocorreu um erro ao cadastrar o usuário")
            return
        }

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
            render("Erro ao remover usuário")
            return
        }

        redirect(action: "manage")
    }
}
