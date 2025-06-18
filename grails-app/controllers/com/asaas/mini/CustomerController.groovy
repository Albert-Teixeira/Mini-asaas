package com.asaas.mini
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class CustomerController {

    CustomerService customerService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond customerService.list(params), model:[customerCount: customerService.count()]
    }

    def show(Long id) {
        Customer customer = customerService.get(id)
        respond customer ?: notFound()
    }

    def create() {
        respond new Customer(params)
    }

    def save() {
        try{
            Customer customer = customerService.save(params)

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'customer.label', default: 'Customer'), customer.id])
                    redirect customer
                }
                '*' { respond customer, [status: CREATED] }
            }
        } catch (ValidationException e) {
            flash.message = "Erro de validação: ${e.message}"
            respond customer, view:'create'
            return
        } catch (Exception e) {
            flash.message = "Erro ao salvar cliente: ${e.message}"
            respond customer, view:'create'
            return
        }
    }

    def edit(Long id) {
        respond customerService.get(id)
    }

    def update() {

        try{
            Customer customer = customerService.update(Customer.get(params.id), params)

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'customer.label', default: 'Customer'), customer.id])
                    redirect customer
                }
                '*'{ respond customer, [status: OK] }
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

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        try{
            customerService.delete(id)

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'customer.label', default: 'Customer'), id])
                    redirect action:"index", method:"GET"
                }
                '*'{ render status: NO_CONTENT }
            }
        } catch (Exception e) {
            flash.message = e.message
            request.withFormat {
                form multipartForm {
                    redirect action: "index", method: "GET"
                }
                '*'{ render status: NOT_FOUND }
            }
        } catch (IllegalArgumentException e) {
            flash.message = "Invalid ID: ${e.message}"
            request.withFormat {
                form multipartForm {
                    redirect action: "index", method: "GET"
                }
                '*'{ render status: BAD_REQUEST }
            }
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
