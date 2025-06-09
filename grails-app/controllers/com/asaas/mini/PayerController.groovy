package com.asaas.mini

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class PayerController {

    PayerService payerService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond payerService.list(params), model:[payerCount: payerService.count()]
    }

    def show(Long id) {
        respond payerService.get(id)
    }

    def create() {
        respond new Payer(params)
    }

    def save(Payer payer) {
        if (payer == null) {
            notFound()
            return
        }

        try {
            payerService.save(payer)
        } catch (ValidationException e) {
            respond payer.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'payer.label', default: 'Payer'), payer.id])
                redirect payer
            }
            '*' { respond payer, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond payerService.get(id)
    }

    def update(Payer payer) {
        if (payer == null) {
            notFound()
            return
        }

        try {
            payerService.save(payer)
        } catch (ValidationException e) {
            respond payer.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'payer.label', default: 'Payer'), payer.id])
                redirect payer
            }
            '*'{ respond payer, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        payerService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'payer.label', default: 'Payer'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
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
