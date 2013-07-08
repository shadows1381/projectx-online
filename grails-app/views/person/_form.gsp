
<z:rows>



	<z:row>
		<z:label value="${message(code:'person.name.label',default:'Name')}" />
		<z:textbox name="name" value="${personInstance?.name}" id="nameBox" />
		<z:label
			value="${message(code:'person.surname.label',default:'Surname')}" />
		<z:textbox name="surname" value="${personInstance?.surname}"
			id="surnameBox" />
	</z:row>


	<z:row>
		<z:label value="${message(code:'person.dob.label',default:'Dob')}" />
		<z:datebox name="dob" value="${personInstance?.dob}" id="dateBox" />
		<z:label value="${message(code:'person.age.label',default:'Age')}" />
		<z:textbox name="age"
			value="${fieldValue(bean: personInstance, field: 'age')}" id="ageBox"
			disabled="true" />
	</z:row>

	<z:row>
		<z:label value="${message(code:'person.grade.label',default:'Grade')}" />
		<z:textbox name="grade" value="${personInstance?.grade}" id="gradeBox" />
		<z:label
			value="${message(code:'person.gender.label',default:'Gender')}" />
		<zkui:select name="gender" id="genderBox"
			from="${personInstance.constraints.gender.inList}"
			value="${personInstance?.gender}" valueMessagePrefix="person.gender" />
	</z:row>
	<z:row>
		<z:label
			value="${message(code:'person.cellPhone.label',default:'Cellphone')}" />
		<z:textbox name="cellPhone" value="${personInstance?.cellPhone}"
			id="cellBox" />
		<z:label
			value="${message(code:'person.address.label',default:'Address')}" />
		<z:textbox name="address" value="${personInstance?.address}"
			id="addressBox" />
	</z:row>

	<z:row>
		<z:label
			value="${message(code:'person.momName.label',default:'Mother Name')}" />
		<z:textbox name="momName" value="${personInstance?.momName}"
			id="mNameBox" />
		<z:label
			value="${message(code:'person.dadName.label',default:'Father Name')}" />
		<z:textbox name="dadName" value="${personInstance?.dadName}"
			id="fNameBox" />
	</z:row>

	<z:row>
		<z:label
			value="${message(code:'person.momCell.label',default:'Mother Cell')}" />
		<z:textbox name="momCell" value="${personInstance?.momCell}"
			id="mCellBox" />
		<z:label
			value="${message(code:'person.dadCell.label',default:'Father Cell')}" />
		<z:textbox name="dadCell" value="${personInstance?.dadCell}"
			id="fCellBox" />
	</z:row>





	<z:row>
		<z:label
			value="${message(code:'person.firstDate.label',default:'First Date')}" />
		<z:datebox name="firstDate" value="${personInstance?.firstDate}"
			id="firstDateBox" disabled="true" />
		<z:label
			value="${message(code:'person.lastDateAttended.label',default:'Last Date Attended')}" />
		<z:datebox name="lastDateAttended"
			value="${personInstance?.lastDateAttended}" disabled="true"
			id="lastDateBox" />
	</z:row>
	<z:row spans="4">
		<z:label value="Admin section:" />
	</z:row>
	<z:row>
		<z:label
			value="${message(code:'person.attending.label',default:'Attending')}" />
		<z:checkbox name="attending" checked="${personInstance?.attending}"
			id="attendingBox" disabled="true" />

	</z:row>
	<z:row>
		<z:label
			value="${message(code:'category.admin.label',default:'Admin')}" />

		<z:bandbox readonly="true" width="405px" id="adminFld" autodrop="true"
			onClick="adminSearch.focus();" disabled="true">
			<z:bandpopup>
				<z:vbox>
					<z:hbox>
						<z:label value="Search" />
						<z:textbox id="adminSearch" />
						<z:space />

						<z:button id="clearListBoxSearch" label="Clear" />
					</z:hbox>
					<z:listbox id="adminListBox" width="650px" vflex="min">
					</z:listbox>
					<z:paging id="adminPaging" pageSize="5" />
				</z:vbox>
			</z:bandpopup>
		</z:bandbox>
	</z:row>

	<z:row>
		<z:label
			value="${message(code:'person.barcode.label',default:'Barcode')}" />
		<z:textbox name="barcode" value="${personInstance?.barcode}"
			id="barcodeBox" disabled="true" />
		<z:label value="${message(code:'person.male.label',default:'Male')}"
			visible="false" />
		<z:checkbox name="male" checked="${personInstance?.male}" id="maleBox"
			visible="false" disabled="true" />
	</z:row>

	<z:row visible="false">
		<z:label value="${message(code:'person.photo.label',default:'Photo')}" />
		<z:textbox name="photo" value="${personInstance?.photo}" id="photoBox" />
		<z:label
			value="${message(code:'person.imagechange.label',default:'Imagechange')}" />
		<z:checkbox name="imagechange"
			checked="${personInstance?.imagechange}" id="imageChangeBox" />
	</z:row>
</z:rows>