<html>
<head>
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'person.label', default: 'Person')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	<z:window style="padding:5px"
		apply="projectx.online.person.ListComposer">
		<z:panel id="listPanel">
			<z:panelchildren>
				<z:hlayout>
					<z:toolbarbutton image="/images/skin/database_add.png"
						id="newButton"
						label="${message(code:'default.new.label',args:[entityName])}" />
					<z:space />
					<z:label value="Barcode" />
					<z:textbox id="barcodeEnterBox" />
					<z:button id="submitButton" label="${message(code:'Submit')}" />
					<z:space />
					<z:textbox id="searchNameBox" />
					<z:button id="searchButton" label="${message(code:'search')}" />
				</z:hlayout>
				<g:if test="${flash.message}">
					<z:window mode="popup" border="normal">
						<z:hlayout>
							<z:image src="/images/skin/information.png" />
							<z:div>
								${flash.message}
							</z:div>
						</z:hlayout>
					</z:window>
				</g:if>
				<z:hbox>
					<z:vlayout>
						<z:label value="Not Attending" />
						<z:grid id="gridNotAttending"
							emptyMessage="${message(code:'emptyMessage',default:'No Record')}"
							width="100%">
							<z:columns sizable="true">
								<z:column
									label="${message(code: 'person.barcode.label', default: 'Barcode')}" />
								<z:column
									label="${message(code: 'person.name.label', default: 'Name')}" />
								<z:column
									label="${message(code: 'person.surname.label', default: 'Surname')}" />
								<z:column
									label="${message(code: 'person.dob.label', default: 'Birthday')}" />
								<z:column
									label="${message(code: 'person.age.label', default: 'Age')}" />
								<z:column width="150px" />
							</z:columns>
						</z:grid>
						<z:paging autohide="true" id="paging" pageSize="15" />
					</z:vlayout>
					<z:vlayout>
						<z:label value="Attending" />
						<z:grid id="gridAttending" width="100%"
							emptyMessage="${message(code:'emptyMessage',default:'No Record')}">
							<z:columns sizable="true">
								<z:column
									label="${message(code: 'person.barcode.label', default: 'Barcode')}" />
								<z:column
									label="${message(code: 'person.name.label', default: 'Name')}" />
								<z:column
									label="${message(code: 'person.surname.label', default: 'Surname')}" />
								<z:column
									label="${message(code: 'person.dob.label', default: 'Birthday')}" />
								<z:column
									label="${message(code: 'person.age.label', default: 'Age')}" />
								<z:column width="150px" />
							</z:columns>
						</z:grid>
						<z:paging autohide="true" id="pagingAttending" pageSize="15" />
					</z:vlayout>
				</z:hbox>
			</z:panelchildren>
		</z:panel>

		<z:panel id="editPanel" visible="false">
			<z:panelchildren>
				<z:grid>
					<z:columns sizable="true">
						<z:column label="" width="100px" />
						<z:column label="" />
						<z:column label="" width="100px" />
						<z:column label="" />
					</z:columns>
					<tmpl:form />
				</z:grid>
				<z:hlayout>
					<z:button id="updateButton"
						label="${message(code: 'default.button.update.label', default: 'Update')}"
						visible="false" />
					<z:button id="createButton"
						label="${message(code: 'default.button.create.label', default: 'Create')}"
						visible="false" />
					<z:button id="cancelButton"
						label="${message(code: 'default.button.cancel.label', default: 'Cancel')}" />
				</z:hlayout>

				<z:longbox name="id" value="${personInstance.id}" visible="false"
					id="idBox" />
				<z:longbox name="version" value="${personInstance.version}"
					id="versionBox" visible="false" />
				<z:longbox id="adminIdBox" name="adminIdBox" visible="false" />

			</z:panelchildren>
		</z:panel>
	</z:window>
</body>
</html>