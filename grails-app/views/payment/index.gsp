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

                <g:if test="${ payment.statusType == statusType.RECEIVED }">
                    <a href="${createLink(action:"show",id:"${payment.id}")}">Acessar pagamento</a>
                </g:if>
                <g:elseif test="${ payment.deleted }">
                    <a href="${createLink(action:"show",id:"${payment.id}")}">Acessar pagamento</a><br><br>

                    <g:form action="restore" method="PUT" id="${payment.id}">
                        <fieldset class="buttons">
                            <input class="edit" type="submit" value="Restaurar pagamento" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Tem certeza?')}');" />
                        </fieldset>
                    </g:form>
                </g:elseif>
                <g:else>
                    <a href="${createLink(action:"show",id:"${payment.id}")}">Acessar pagamento</a><br><br>
                    <a href="${createLink(action:"edit",id:"${payment.id}")}">Alterar data de vencimento</a>

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
                </g:else>
                <p>-----------------------------------------------------------</p>
            </div>
        </g:each>
        <g:if test="${ !deleted }">
            <a href="${createLink(action:"create")}">Criar novo pagamento</a>
        </g:if>
    </body>
</html>