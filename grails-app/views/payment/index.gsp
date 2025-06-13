<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>
    <body>
        <g:each var="payment" in="${ payments }">
            <div>
                <p>Cliente: ${payment.customer.name}</p>
                <p>Pagador: ${payment.payer.name}</p>
                <p>Tipo de pagamento: ${payment.paymentType}</p>
                <p>Valor: ${payment.value}</p>
                <p>Status: ${payment.status}</p>
                <p>Data de vencimento: ${payment.dueDate}</p>
                <p>Date de recebimento: ${payment.dateReceived}</p>
                <a href="${createLink(action:"show",id:"${payment.id}")}">Acessar pagamento</a>
                <a href="${createLink(action:"edit",id:"${payment.id}")}">Editar pagamento</a>
                <a href="${createLink(action:"remove",id:"${payment.id}")}">Deletar Pagamento</a>
                <p>-----------------------------------------------------------</p>
            </div>
        </g:each>
        <a href="${createLink(action:"create")}">Criar novo pagamento</a>
    </body>
</html>