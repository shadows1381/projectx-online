<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>

<body>
<z:window style="padding:5px" apply="projectx.online.person.CreateComposer">
    <z:grid>
        <z:columns sizable="true">
            <z:column label="" width="100px"/>
            <z:column label=""/>
            <z:column label="" width="100px"/>
            <z:column label=""/>
        </z:columns>
        <tmpl:form/>
    </z:grid>
    <z:hlayout>
        <z:button id="saveButton" label="${message(code: 'default.button.create.label', default: 'Create')}"/>
        <z:button href="${createLink(action:'list')}" label="${message(code: 'default.list.label', args:[entityName])}"/>
    </z:hlayout>
</z:window>
</body>
</html>