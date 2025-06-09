package com.asaas.mini

class Payment {

    Customer customer
    Payer payer
    PaymentType paymentType
    Double value
    StatusType status
    Date dueDate
    Date dateReceived

    static constraints = {
        value min: 0.0D
        dateReceived nullable: true
    }

    static mapping = {
        paymentType enumType: 'string'
        status enumType: 'string'
    }
}
