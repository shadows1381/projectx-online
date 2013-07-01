<html>
<head>
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'category.label', default: 'Category')}" />
<title><g:message code="default.create.label"
		args="[entityName]" /></title>
</head>

<body>
	<z:window style="padding:5px"
		apply="projectx.online.category.CreateComposer">
		<z:grid>
			<z:columns sizable="true">
				<z:column label="${message(code:'name',default:'Name')}"
					width="100px" />
				<z:column label="${message(code:'value',default:'Value')}" />
			</z:columns>
			<tmpl:form />
		</z:grid>
		<z:hlayout>
			<z:button id="saveButton"
				label="${message(code: 'default.button.create.label', default: 'Create')}" />
			<z:button href="${createLink(action:'list')}"
				label="${message(code: 'default.list.label', args:[entityName])}" />
				
			<z:longbox id="adminIdBox" name="adminIdBox" visible="false" />
		</z:hlayout>
	</z:window>
</body>
</html>