
<%@ page import="com.mycodesnippets.Planet" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'planet.label', default: 'Planet')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'planet.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'planet.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="water" title="${message(code: 'planet.water.label', default: 'Water')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${planetInstanceList}" status="i" var="planetInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${planetInstance.id}">${fieldValue(bean: planetInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: planetInstance, field: "name")}</td>
                        
                            <td><g:formatBoolean boolean="${planetInstance.water}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${planetInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
