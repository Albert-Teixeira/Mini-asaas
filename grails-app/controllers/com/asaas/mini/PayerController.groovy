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
        println "Loaded from DB for show: ${payer?.properties}"
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
            flash.message = "Validation Error: ${e.message}"
            respond payer, view:'create'
            return
        } catch (Exception e) {
            flash.message = "Error saving payer: ${e.message}"
            respond payer, view:'create'
            return
        }
    }

    def edit(Long id) {
        respond payerService.reload(id)
    }

    def update() {

        println "PayerController.update() - Params: ${params}"

        try {
            Payer payer = payerService.update(Payer.get(params.id), params)

            // Debug
            println "PayerController.update() - Payer updated successfully: ${payer.properties}"
            println "PayerController.update() - Has errors: ${payer.hasErrors()}"

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'payer.label', default: 'Payer'), payer.id])
                    redirect payer
                }
                '*'{ respond payer, [status: OK] }
            }
        } catch (ValidationException e) {
            flash.message = "Validation Error: ${e.message}"
            respond payer, view:'edit'
            return
        } catch (Exception e) {
            flash.message = "Error updating payer: ${e.message}"
            respond payer, view:'edit'
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
            flash.message = "Error deleting payer: ${e.message}"
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
