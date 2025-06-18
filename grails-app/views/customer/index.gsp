<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Minha conta</title>
    </head>
    <body>
        <g:if test="${ !customer }">
            <h1>Parece que você não tem uma conta associada ao seu usuário, gostaria de criar?</h1>
        </g:if>
        <g:else>
            <h1>Bem vindo ${customer.name} !</h1>
            <p>Cpf/Cnpj: ${customer.cpfCnpj}</p>
            <br>
            <p>Morador de ${customer.city} - ${customer.state}, ${customer.street}, Nº ${customer.houseNumber}, CEP: ${customer.postalCode}</p>
        </g:else>


        <g:link controller='logout'>Logout</g:link>
    </body>
</html>