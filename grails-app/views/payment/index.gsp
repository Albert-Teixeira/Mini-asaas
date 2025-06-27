<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Listar Cobranças</title>
    </head>
    <body>
        <g:if test="${ deleted }">
           <a href="${createLink(action:"index")}">Voltar</a>
        </g:if>
        <g:else>
           <a href="${createLink(action:"index",params:[deleted:"1"])}">Ver cobranças removidas</a>
        </g:else>
        <g:each var="payment" in="${ paymentList }">
            <div>
                <p>Pagador: ${payment.payer.name}</p>
                <p>Tipo de pagamento: ${payment.paymentType}</p>
                <p>Valor: ${payment.value}</p>
                <p>Status: ${payment.statusType}</p>
                <g:if test="${ payment.statusType == statusType.RECEIVED }"> <p>Data de recebimento: ${payment.dateReceived}</p> </g:if>
                <p>Data de vencimento: ${payment.dueDate}</p>

                <a href="${createLink(action:"show",id:"${payment.id}")}">Acessar pagamento</a><br>

                <p>-----------------------------------------------------------</p>
            </div>
        </g:each>
        <g:if test="${ !deleted }">
            <a href="${createLink(action:"create")}">Criar novo pagamento</a>
        </g:if>
    </body>
</html>