package com.asaas.mini
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER','ROLE_OWNER'])
class CustomerController {

    CustomerService customerService

    static allowedMethods = [update: "PUT"]

    def index() {
        User user = getAuthenticatedUser()
        Customer customer = user.customer
        respond customer ?: notFound()
    }

    def edit() {
        User user = getAuthenticatedUser()
        Customer customer = user.customer

        respond customer
    }

    def update() {
        
        User user = getAuthenticatedUser()
        Customer customer = user.customer

        try{
            customer = customerService.update(customer, params)

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'customer.label', default: 'Customer'), customer.id])
                    redirect(view: "index")
                }
            }
        } catch (ValidationException e) {
            flash.message = "Erro de validação: ${e.message}"
            respond customer, view:'edit'
            return
        } catch (Exception e) {
            flash.message = "Erro ao atualizar cliente: ${e.message}"
            respond customer, view:'edit'
            return
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
