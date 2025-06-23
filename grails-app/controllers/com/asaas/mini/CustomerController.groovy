package com.asaas.mini

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER','ROLE_OWNER'])
class CustomerController {

    def index() {
        User user = getAuthenticatedUser()

        if(!user){
            request.status = 401
            render "Precisa de autenticação"
            return
        }

        render(view: "index", model: [customer: user.customer])
    }
}
