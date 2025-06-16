package com.asaas.mini


import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class CustomerController {

    CustomerService customerService

    static allowedMethods = [save: "POST", update: "POST", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond customerService.list(params), model:[customerCount: customerService.count()]
    }

    def show(Long id) {
        Customer customer = customerService.get(id)
        println "Loaded from DB for show: ${customer?.properties}"
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
            flash.message = "Validation Error: ${e.message}"
            respond customer, view:'create'
            return
        } catch (Exception e) {
            flash.message = "Error saving customer: ${e.message}"
            respond customer, view:'create'
            return
        }
    }

    def edit(Long id) {
        respond customerService.get(id)
    }

    def update() {

        println "Params for update: ${params}"

        try{
            Customer customer = customerService.update(Customer.get(params.id), params)

            println "CustomerController.update() - Customer updated successfully: ${customer?.properties}"
            println "CustomerController.update() - Has errors: ${customer.hasErrors()}"

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'customer.label', default: 'Customer'), customer.id])
                    redirect customer
                }
                '*'{ respond customer, [status: OK] }
            }
        } catch (ValidationException e) {
            flash.message = "Validation Error: ${e.message}"
            respond customer, view:'edit'
            return
        } catch (Exception e) {
            flash.message = "Error updating customer: ${e.message}"
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
