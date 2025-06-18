<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Minha conta</title>
    </head>
    <body>
        <g:form name="myForm" url="[controller:'register',action:'save']">
            <label>Insira um usuário:</label>
            <g:textField name="username"/><br>
            <label>Insira uma senha:</label>
            <g:passwordField name="password"/><br>
            <label>Insira sua senha novamente:</label>
            <g:passwordField name="password2"/><br>
            <g:actionSubmit value="submit" action="save"/>
        </g:form>
    </body>
</html>