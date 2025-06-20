<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Administrar usuários</title>
    </head>
    <body>
        <g:each var="user" in="${ accountUserList }">
            <p>${user.username}</p>
            <button type="button">Revogar acesso deste usuário</button>
            <p>-----------------------</p>
        </g:each>
        <button type="button">Adicione um usuário para acessar a conta</button>
        <g:form name="inviteForm" url="[controller:'authentication',action:'invite']">
            <label>Email:</label>
            <g:field type="email" name="email"/><br>

            <g:actionSubmit value="submit" action="invite"/>
        </g:form>
    </body>
</html>