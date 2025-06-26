<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Criar Cobrança</title>
    </head>
    <body>
        <g:form resource="${this.payment}" method="POST">
            <fieldset class="form">
                <p>Valor: </p>
                <g:field type="number" name="value" min="0" required="true"/>
                <p>Tipo de Cobrança: </p>
                <g:select name="paymentType" from="${paymentTypeList}" />
                <p>Quem vai pagar: </p>
                <g:select optionKey="id" optionValue="name" name="payer" from="${payerList}" />
                <p>Data de vencimento: </p>
                <g:datePicker name="myDate" value="${new Date()}" noSelection="['':'-Choose-']"/>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Criar')}" />
            </fieldset>
        </g:form>
        <a href="${createLink(action:"index")}">Voltar</a>
    </body>
</html>