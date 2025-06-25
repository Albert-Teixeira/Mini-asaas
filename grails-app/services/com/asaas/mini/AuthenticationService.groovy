package com.asaas.mini

import grails.gorm.transactions.Transactional

@Transactional
class AuthenticationService {

    User registerUserAndCustomer(String username, String password, Customer customer, Role userRole) {
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

    void expireInvitation(Invitation invitation){
        invitation.expired=true
        println('aqu1')
        invitation.save(failOnError: true)
        println('aqu2')
    }

    void deleteUser(User user){
        if(user.getAuthorities()[0].authority == "ROLE_OWNER"){
            throw new IllegalArgumentException("Não pode deletar o dono")
        }

        Role userRole = Role.get(2)

        UserRole.remove user, userRole
        user.delete()

        UserRole.withSession {
            it.flush()
            it.clear()
        }
    }

    List<User> getUsersByCustomerAccount(Customer customer){
        List<User> userList = User.findAll {
            customer == customer
        }

        return userList
    }
}
