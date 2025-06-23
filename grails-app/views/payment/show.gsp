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
        <p>Status: ${payment.status}</p>
        <p>Data de vencimento: ${payment.dueDate}</p>
        <p>Date de recebimento: ${payment.dateReceived}</p>
        <p>Deleted: ${payment.deleted}</p>
        <p>-----------------------------------------------------------</p>
        <a href="${createLink(action:"index")}">Voltar</a>
        
        <g:if test="${ payment.deleted }">
           <a href="${createLink(action:"restore",id:"${payment.id}")}">Restaurar pagamento</a>
        </g:if>
        <g:elseif test="${payment.status == statusType.PENDENTE || payment.status == statusType.VENCIDA}">
            <a href="${createLink(action:"edit",id:"${payment.id}")}">Editar pagamento</a>
            <a href="${createLink(action:"remove",id:"${payment.id}")}">Deletar Pagamento</a>
            <a href="${createLink(action:"confirm",id:"${payment.id}")}">Receber Pagamento</a>
        </g:elseif>
    </div>
</body>
</html>