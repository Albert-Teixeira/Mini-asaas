<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'customer.label', default: 'Customer')}" />
        <title>
            <g:message code="default.show.label" args="[entityName]" />
        </title>
    </head>
    <body>
        <a href="#show-customer" class="skip" tabindex="-1">
            <g:message code="default.link.skip.label" default="Pular para o conteúdo"/>
        </a>
        <div class="nav" role="navigation">
            <ul>
                <li>
                    <a class="home" href="${createLink(uri: '/')}">
                        <g:message code="default.home.label"/>
                    </a>
                </li>
                <li>
                    <g:link class="list" controller="authentication" action="manage">
                        Gerenciamento de usuários
                    </g:link>
                </li>
            </ul>
        </div>
        <div id="show-customer" class="content scaffold-show" role="main">
            <h1>
                <g:message code="default.show.label" args="[entityName]" />
            </h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="customer" />
            <fieldset class="buttons">
                <g:link class="edit" action="edit">
                    <g:message code="default.button.edit.label" default="Edit" />
                </g:link>
            </fieldset>
        </div>
    </body>
</html>
