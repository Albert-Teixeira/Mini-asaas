package com.asaas.mini

import grails.gorm.services.Service

@Service(Payer)
interface PayerService {

    Payer get(Serializable id)

    List<Payer> list(Map args)

    Long count()

    void delete(Serializable id)

    Payer save(Payer payer)

}