

<%@ page import="com.mycodesnippets.Planet" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'planet.label', default: 'Planet')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${planetInstance}">
            <div class="errors">
                <g:renderErrors bean="${planetInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${planetInstance?.id}" />
                <g:hiddenField name="version" value="${planetInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="moons"><g:message code="planet.moons.label" default="Moons" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: planetInstance, field: 'moons', 'errors')}">
                                    <g:select name="moons" from="${com.mycodesnippets.Moon.list()}" multiple="yes" optionKey="id" size="5" value="${planetInstance?.moons*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="planet.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: planetInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${planetInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="water"><g:message code="planet.water.label" default="Water" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: planetInstance, field: 'water', 'errors')}">
                                    <g:checkBox name="water" value="${planetInstance?.water}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
