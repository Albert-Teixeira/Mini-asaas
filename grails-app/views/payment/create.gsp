<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Criar Cobrança</title>
    </head>
    <body>
        <form action="${createLink(action: "save")}" method="post">
            <label for="customer_id">Id do cliente:</label><br>
            <input type="text" id="customer_id" name="customer_id"><br>

            <label for="payer_id">Id do pagador:</label><br>
            <input type="text" id="payer_id" name="payer_id"><br>

            <label for="payment_type">Selecione o tipo de pagamento:</label><br>
            <select id="payment_type" name="payment_type">
                <option value="BOLETO">BOLETO</option>
                <option value="CARTAO">CARTAO</option>
                <option value="PIX">PIX</option>
                <option value="DINHEIRO">DINHEIRO</option>
            </select><br>

            <label for="value">Valor:</label><br>
            <input type="text" id="value" name="value"><br>

            <label for="due_date">Data de vencimento:</label><br>
            <input type="datetime-local" id="due_date" name="due_date"/>

            <input type="submit" value="Submit">
        </form>
        <a href="${createLink(action:"index")}">Voltar</a>
    </body>
</html>