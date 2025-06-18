package com.asaas.mini

import grails.gorm.transactions.Transactional

@Transactional
class RegisterService {

    User registerUser(String username, String password) {
        Role userRole = Role.get(1)
        User user = new User(username: username, password: password, customer: null).save()
        UserRole.create user, userRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        return user
    }
}
