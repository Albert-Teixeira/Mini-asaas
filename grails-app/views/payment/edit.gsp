<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Cobrança</title>
</head>
<body>
    <div>
        <form action="${createLink(action: "edit", id: payment.id)}" method="post">
            <p>Cliente: ${payment.customer.name}</p>
            <p>Pagador: ${payment.payer.name}</p>
            <p>Tipo de pagamento: ${payment.paymentType}</p>
            <p>Valor: <input type="text" id="value" name="value" value="${payment.value}"></p>
            <p>Data de vencimento: <input type="datetime-local" id="due_date" name="due_date" value="${payment.dueDate}"/></p>
            <input type="submit" value="Editar Pagamento">
        </form>
        <p>-----------------------------------------------------------</p>
        <a href="${createLink(action:"index")}">Voltar</a>
    </div>
</body>
</html>