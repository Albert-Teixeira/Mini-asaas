package com.asaas.mini

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = {
        addTestUser()
    }

    @Transactional
    void addTestUser() {
        def ownerRole = new Role(authority: 'ROLE_OWNER').save()
        def userRole = new Role(authority: 'ROLE_USER').save()

        def customer1 = new Customer(
            name: "Carlos",
            email: "email@email.com",
            phoneNumber: "15999999999",
            cpfCnpj: "123.456.789-09",
            state: "SP",
            city: "Boituva",
            street: "Pq. Ecologico",
            houseNumber: 123,
            postalCode: "15123-456").save()

        def customer2 = new Customer(
            name: "Rodrigo",
            email: "email2@email.com",
            phoneNumber: "15888888888",
            cpfCnpj: "555.555.555-55",
            state: "SP",
            city: "Sorocaba",
            street: "Rua legal",
            houseNumber: 333,
            postalCode: "15111-111").save()
        
        def customer3 = new Customer(
            name: "Dono",
            email: "email3@email.com",
            phoneNumber: "99999999999",
            cpfCnpj: "999.999.999-99",
            state: "SP",
            city: "São Carlos",
            street: "Rua",
            houseNumber: 999,
            postalCode: "15999-999").save()

        def user1 = new User(username: 'user1', password: 'password', customer: customer1).save()
        def user2 = new User(username: 'user2', password: 'password', customer: customer2).save()
        def owner = new User(username: 'owner', password: 'password', customer: customer3).save()

        UserRole.create user1, userRole
        UserRole.create user2, userRole
        UserRole.create owner, ownerRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        assert User.count() == 3
        assert Role.count() == 2
        assert UserRole.count() == 3
    }

    def destroy = {
    }
}
