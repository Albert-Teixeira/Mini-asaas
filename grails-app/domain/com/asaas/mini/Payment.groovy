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
        paymentType blank: false
        value blank: false, min: 0F
        status blank: false
        dateReceived nullable: true
    }

    static mapping = {
        paymentType enumType: 'string'
        status enumType: 'string'
    }
}
