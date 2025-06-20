package com.asaas.mini

class ExpirePaymentJob {

  PaymentService paymentService

    static triggers = {
      simple repeatInterval: 1000
    }

    def execute() {
      paymentService.checkExpiredPayments()
    }
}
