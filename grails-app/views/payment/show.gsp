<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
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
        <p>-----------------------------------------------------------</p>
        <a href="${createLink(action:"index")}">Voltar</a>
        <a href="${createLink(action:"remove",id:"${payment.id}")}">Deletar Pagamento</a>
    </div>
</body>
</html>