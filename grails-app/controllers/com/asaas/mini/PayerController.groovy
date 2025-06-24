package com.asaas.mini

import grails.validation.ValidationException
import grails.web.http.HttpHeaders
import groovy.lang.MissingMethodException
import static org.springframework.http.HttpStatus.*

class PayerController {

    PayerService payerService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond payerService.list(params), model:[payerCount: payerService.count()]
    }

    def show(Long id) {
        Payer payer = Payer.get(id)
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
