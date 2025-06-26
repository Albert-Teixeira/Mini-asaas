<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Cobrança</title>
</head>
<body>
    <g:form resource="${this.payment}" method="PUT">
            <fieldset class="form">
                <p>Valor: ${payment.value}</p>
                <p>Tipo de Cobrança: ${payment.paymentType}</p>
                <p>Quem vai pagar: ${payment.payer.name}</p>
                <p>Nova data de vencimento:
                    <g:datePicker name="myDate" value="${payment.dueDate}" noSelection="['':'-Choose-']"/>
                </p>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="create" class="save" value="Confirmar" />
            </fieldset>
        </g:form>
        <a href="${createLink(action:"index")}">Voltar</a>
</body>
</html>