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
            <g:form name="removeUserForm" action="remove" controller="authentication" id="${user.id}">
                <g:actionSubmit value="remover usuário" action="remove"/>
            </g:form>
            <p>-----------------------</p>
        </g:each>
        <g:form name="inviteForm" url="[controller:'authentication',action:'invite']">
            <p>Adicionar usuário</p>
            <label>Email:</label>
            <g:field type="email" name="email"/><br>

            <g:actionSubmit value="Adicionar usuário" action="invite"/>
        </g:form>
    </body>
</html>