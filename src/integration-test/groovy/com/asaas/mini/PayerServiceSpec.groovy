package com.asaas.mini

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class PayerServiceSpec extends Specification {

    PayerService payerService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Payer(...).save(flush: true, failOnError: true)
        //new Payer(...).save(flush: true, failOnError: true)
        //Payer payer = new Payer(...).save(flush: true, failOnError: true)
        //new Payer(...).save(flush: true, failOnError: true)
        //new Payer(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //payer.id
    }

    void "test get"() {
        setupData()

        expect:
        payerService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Payer> payerList = payerService.list(max: 2, offset: 2)

        then:
        payerList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        payerService.count() == 5
    }

    void "test delete"() {
        Long payerId = setupData()

        expect:
        payerService.count() == 5

        when:
        payerService.delete(payerId)
        sessionFactory.currentSession.flush()

        then:
        payerService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Payer payer = new Payer()
        payerService.save(payer)

        then:
        payer.id != null
    }
}
