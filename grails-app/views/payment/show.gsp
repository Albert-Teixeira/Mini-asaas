<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cobrança</title>
</head>
<body>
    <div>
        <p>Cliente: ${payment.customer.name}</p>
        <p>Pagador: ${payment.payer.name}</p>
        <p>Tipo de pagamento: ${payment.paymentType}</p>
        <p>Valor: ${payment.value}</p>
        <p>Status: ${payment.statusType}</p>
        <p>Data de vencimento: ${payment.dueDate}</p>
        <p>Date de recebimento: ${payment.dateReceived}</p>
        <p>Deleted: ${payment.deleted}</p>
        <p>-----------------------------------------------------------</p>
        <a href="${createLink(action:"index")}">Voltar</a>
        
        <g:if test="${ payment.deleted }">
           <g:form action="restore" method="PUT" id="${payment.id}">
                <fieldset class="buttons">
                    <input class="edit" type="submit" value="Restaurar pagamento" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Tem certeza?')}');" />
                </fieldset>
            </g:form>
        </g:if>
        <g:elseif test="${payment.statusType == statusType.PENDING || payment.statusType == statusType.OVERDUE}">
            <a href="${createLink(action:"edit",id:"${payment.id}")}">Editar pagamento</a>

            <g:form action="remove" method="DELETE" id="${payment.id}">
                <fieldset class="buttons">
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Remover')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Tem certeza?')}');" />
                </fieldset>
            </g:form>

            <g:form action="confirm" method="PUT" id="${payment.id}">
                <fieldset class="buttons">
                    <input class="edit" type="submit" value="Receber cobrança" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Tem certeza?')}');" />
                </fieldset>
            </g:form>
        </g:elseif>
    </div>
</body>
</html>