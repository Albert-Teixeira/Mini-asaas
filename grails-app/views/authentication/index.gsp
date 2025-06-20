<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Minha conta</title>
    </head>
    <body>
        <g:form name="registerForm" url="[controller:'authentication',action:'save']">
            <label>Email:</label>
            <g:field type="email" name="email"/><br>

            <label>Nome:</label>
            <g:field type="text" name="name"/><br>

            <label>Telefone:</label>
            <g:field type="text" name="phoneNumber"/><br>

            <label>Cpf ou Cnpj:</label>
            <g:field type="text" name="cpfCnpj"/><br>

            <label>Estado:</label>
            <g:field type="text" name="state"/><br>

            <label>Cidade:</label>
            <g:field type="text" name="city"/><br>

            <label>Rua:</label>
            <g:field type="text" name="street"/><br>

            <label>Número da casa:</label>
            <g:field type="number" name="addressNumber"/><br>

            <label>CEP:</label>
            <g:field type="text" name="postalCode"/><br>

            <label>Insira uma senha:</label>
            <g:field type="password" name="password"/><br>

            <label>Insira a senha novamente:</label>
            <g:field type="password" name="password2"/><br>

            <g:actionSubmit value="submit" action="save"/>
        </g:form>
    </body>
</html>