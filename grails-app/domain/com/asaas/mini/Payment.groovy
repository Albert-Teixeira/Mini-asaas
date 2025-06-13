package com.asaas.mini

import com.asaas.mini.utils.BaseEntity
import com.asaas.mini.PaymentType
import com.asaas.mini.StatusType

class Payment extends BaseEntity{

    Customer customer
    Payer payer
    PaymentType paymentType
    Double value
    StatusType statusType
    Date dueDate
    Date dateReceived

    static constraints = {
        value min: 0.0D
        dateReceived nullable: true
    }

    static mapping = {
        paymentType enumType: 'string'
        statusType enumType: 'string'
    }
}
