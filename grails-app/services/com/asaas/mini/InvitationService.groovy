package com.asaas.mini

import grails.gorm.transactions.Transactional

@Transactional
class InvitationService {

    Invitation createInvitation(String email, Customer customer) {
        Invitation invitation = new Invitation(
            email: email,
            customer: customer,
            expired: false)
        
        invitation.save(failOnError: true)

        return invitation
    }

    void expireInvitation(Invitation invitation){
        invitation.expired=true
        invitation.save(failOnError: true)
    }
}
