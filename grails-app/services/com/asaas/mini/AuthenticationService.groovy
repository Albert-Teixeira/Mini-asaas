package com.asaas.mini

import grails.gorm.transactions.Transactional

import java.util.stream.Collectors

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

    List<User> getUsersByCustomerAccount(Customer customer, Boolean excludeOwner=true){
        List<User> userList = User.findAll {
            customer == customer
        }

        if(excludeOwner){
            List<User> userListWithoutOwner = userList
                .stream()
                .filter(user -> user.getAuthorities()[0].authority != "ROLE_OWNER")
                .collect(Collectors.toList())

            return userListWithoutOwner
        }

        return userList
    }
}
