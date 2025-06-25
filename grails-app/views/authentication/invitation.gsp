<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Administrar usuários</title>
    </head>
    <body>
        <g:form name="invitationForm" action="saveInvitedUser" controller="authentication" id="${id}">
            <label>Insira uma senha:</label>
            <g:field type="password" name="password"/><br>

            <label>Insira uma senha novamente:</label>
            <g:field type="password" name="password2"/><br>

            <g:actionSubmit value="submit" action="saveInvitedUser"/>
        </g:form>
    </body>
</html>