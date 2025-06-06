package com.asaas.mini

class Payment {

    Customer customer
    Payer payer
    String paymentType
    Double value
    String status
    Date dueDate
    Date dateReceived

    static constraints = {
        paymentType blank: false
        value blank: false, min: 0F
        status blank: false
        dateReceived nullable: true
    }

    static mapping = {
        
    }
}
