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
           <a href="${createLink(action:"index",params:[deleted:1])}">Ver cobranças removidas</a>
        </g:else>
        <g:each var="payment" in="${ paymentList }">
            <div>
                <p>Cliente: ${payment.customer.name}</p>
                <p>Pagador: ${payment.payer.name}</p>
                <p>Tipo de pagamento: ${payment.paymentType}</p>
                <p>Valor: ${payment.value}</p>
                <p>Status: ${payment.status}</p>
                <p>Data de vencimento: ${payment.dueDate}</p>
                <p>Data de recebimento: ${payment.dateReceived}</p>
                <p>Deleted: ${payment.deleted}</p>

                <g:if test="${ payment.status == statusType.RECEBIDA }">
                    <a href="${createLink(action:"show",id:"${payment.id}")}">Acessar pagamento</a>
                </g:if>
                <g:elseif test="${ payment.deleted }">
                    <a href="${createLink(action:"show",id:"${payment.id}")}">Acessar pagamento</a>
                    <a href="${createLink(action:"restore",id:"${payment.id}")}">Restaurar pagamento</a>
                </g:elseif>
                <g:else>
                    <a href="${createLink(action:"show",id:"${payment.id}")}">Acessar pagamento</a>
                    <a href="${createLink(action:"edit",id:"${payment.id}")}">Editar pagamento</a>
                    <a href="${createLink(action:"remove",id:"${payment.id}")}">Deletar Pagamento</a>
                    <a href="${createLink(action:"confirm",id:"${payment.id}")}">Receber Pagamento</a>
                </g:else>
                <p>-----------------------------------------------------------</p>
            </div>
        </g:each>
        <g:if test="${ !deleted }">
            <a href="${createLink(action:"create")}">Criar novo pagamento</a>
        </g:if>
    </body>
</html>