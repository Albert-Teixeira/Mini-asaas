package com.asaas.mini

import grails.validation.ValidationException
import grails.web.http.HttpHeaders
import groovy.lang.MissingMethodException
import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER','ROLE_OWNER'])
class PayerController {

    PayerService payerService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        User user = getAuthenticatedUser()
        Customer customer = user.customer

        respond payerService.list(customer)
    }

    def show(Long id) {
        User user = getAuthenticatedUser()
        Customer customer = user.customer

        Payer payer = Payer.find {
            id == id
            customer == customer
        }
        respond payer ?: notFound()
    }

    def create() {
        respond new Payer(params)
    }

    def save() {

        try {
            Payer payer = payerService.save(params)

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'payer.label', default: 'Payer'), payer.id])
                    redirect payer
                }
                '*' { respond payer, [status: CREATED] }
            }
        } catch (ValidationException e) {
            flash.message = "Erro de Validação: ${e.message}"
            respond new Payer(params), view:'create'
            return
        } catch (Exception e) {
            flash.message = "Erro ao salvar o pagador: ${e.message}"
            respond new Payer(params), view:'create'
            return
        }
    }

    def edit(Long id) {
        respond payerService.reload(id)
    }

    def update() {
        try {
            Payer payer = payerService.update(Payer.get(params.id), params)

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'payer.label', default: 'Payer'), payer.id])
                    redirect payer
                }
                '*'{ respond payer, [status: OK] }
            }
        } catch (ValidationException e) {
            flash.message = "Erro de Validação: ${e.message}"
            respond new Payer(params), view:'edit'
            return
        } catch (Exception e) {
            flash.message = "Erro ao atualizar o pagador: ${e.message}"
            respond new Payer(params), view:'edit'
            return
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        try {
            payerService.delete(id)

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'payer.label', default: 'Payer'), id])
                    redirect action:"index", method:"GET"
                }
                '*'{ render status: NO_CONTENT }
            }
        } catch (IllegalArgumentException e) {
            flash.message = e.message
            request.withFormat {
                form multipartForm {
                    redirect action: "index", method: "GET"
                }
                '*'{ render status: NOT_FOUND }
            }
        } catch (Exception e) {
            flash.message = "Erro ao deletar o pagador: ${e.message}"
            request.withFormat {
                form multipartForm {
                    redirect action: "index", method: "GET"
                }
                '*'{ render status: INTERNAL_SERVER_ERROR }
            }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'payer.label', default: 'Payer'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
