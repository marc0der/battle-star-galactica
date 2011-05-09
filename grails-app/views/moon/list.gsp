
<%@ page import="com.mycodesnippets.Moon" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'moon.label', default: 'Moon')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'moon.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'moon.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="water" title="${message(code: 'moon.water.label', default: 'Water')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${moonInstanceList}" status="i" var="moonInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${moonInstance.id}">${fieldValue(bean: moonInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: moonInstance, field: "name")}</td>
                        
                            <td><g:formatBoolean boolean="${moonInstance.water}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${moonInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
