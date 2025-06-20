package com.asaas.mini

import grails.gorm.transactions.Transactional

@Transactional
class AuthenticationService {

    User registerUserAndCustomer(String username, String password, Customer customer) {
        Role userRole = Role.get(1)
        User user = new User(username: username, password: password, customer: customer).save()
        if(!user){
            throw new IllegalArgumentException("Este email já está em uso")
        }
        UserRole.create user, userRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        return user
    }

    List<User> getUsersByCustomerAccount(Customer customer){
        List<User> userList = User.findAll {
            customer == customer
        }

        return userList
    }
}
