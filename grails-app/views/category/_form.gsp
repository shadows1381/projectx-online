
<z:rows>

    <z:row>
        <z:label value="${message(code:'category.name.label',default:'Name')}"/>
        <z:textbox name="name" value="${categoryInstance?.name}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'category.admin.label',default:'Admin')}"/>
        
        <z:bandbox readonly="true" width="405px" id="adminFld" autodrop="true" onClick="adminSearch.focus();" disabled="true">
	        <z:bandpopup>
	            <z:vbox>
	                <z:hbox>
	                    <z:label value="Search"/>
	                    <z:textbox id="adminSearch"/>
	                    <z:space />
	                   
						<z:button id="clearListBoxSearch" label="Clear"/>
	                </z:hbox>
	                <z:listbox id="adminListBox" width="650px" vflex="min">
	                </z:listbox>
	                <z:paging id="adminPaging" pageSize="5"/>
	            </z:vbox>
	        </z:bandpopup>
	    </z:bandbox>
    </z:row>

    <z:row>
        <z:label value="${message(code:'category.created.label',default:'Created')}"/>
        <z:datebox name="created" value="${categoryInstance?.created}"/>
    </z:row>

</z:rows>