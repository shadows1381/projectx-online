package projectx.online.person

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*
import projectx.online.*
import java.text.*

class ListComposer {
	Window self
	
    Grid gridNotAttending
	Grid gridAttending
    ListModelList listModel = new ListModelList()
	ListModelList listModelAttend = new ListModelList()
    Paging paging
	Paging pagingAttending
	Textbox searchNameBox
	Textbox barcodeEnterBox
	Button submitButton
	
	Longbox idBox
	Longbox versionBox
	Button createButton
	Button updateButton
	Button cancelButton
	Button newButton
	Panel editPanel
	Panel listPanel
	
	Textbox nameBox
	Textbox surnameBox
	Datebox dateBox
	Textbox ageBox
	Textbox gradeBox
	Combobox genderBox
	Textbox cellBox
	Textbox addressBox
	Textbox mNameBox
	Textbox fNameBox
	Textbox mCellBox
	Textbox fCellBox
	Datebox firstDateBox
	Datebox lastDateBox
	Checkbox attendingBox
	Textbox barcodeBox
	Checkbox maleBox
	Textbox photoBox
	Checkbox imageChangeBox
	def sessionFactory
	def springSecurityService
	
	Paging adminPaging
	Button clearListBoxSearch
	Textbox adminSearch
	Bandbox adminFld
	Listbox adminListBox
	ListModelList adminListBoxModel = new ListModelList()
	String searchListBoxStr = ""
	Longbox adminIdBox
	
	
    def afterCompose = {Component comp ->
        gridNotAttending.setRowRenderer(rowRenderer as RowRenderer)
        gridNotAttending.setModel(listModel)
		gridAttending.setRowRenderer(rowRenderer as RowRenderer)
		gridAttending.setModel(listModelAttend)
		redraw()
		redrawAttending()
		
		adminListBox.setItemRenderer(adminListBoxRowRenderer as ListitemRenderer)
		adminListBox.setModel(adminListBoxModel)
		redrawAdminListBox()
		checkLoggedInUser()
    }
	
	void clearInput(){
		nameBox.value = ""
		surnameBox.value = ""
		dateBox.value = null
		ageBox.value = "0"
		gradeBox.value = ""
		genderBox.value = ""
		cellBox.value = ""
		addressBox.value = ""
		mNameBox.value = ""
		fNameBox.value = ""
		mCellBox.value = ""
		fCellBox.value = ""
		firstDateBox.value = new Date()
		lastDateBox.value = new Date()
		attendingBox.checked = true
		barcodeBox.value = ""
		maleBox.checked = false
		photoBox.value = ""
		imageChangeBox.checked = false
		updateButton.visible= false
		createButton.visible=true
		editPanel.visible=false
		listPanel.visible=true
		
		adminIdBox.value = null
		adminFld.value=null
		
		barcodeEnterBox.value=null
	}

	void onClick_createButton(Event e) {
		barcodeBox.value = getBarCode()
		ageBox.value = getAge(dateBox.value)
		def admin = Admin.get(adminIdBox.value)
		
		def personInstance = new Person(self.params)
		personInstance.admin = admin
		
		if (!personInstance.save(flush: true) && personInstance.hasErrors()) {
			log.error personInstance.errors
			self.renderErrors(bean: personInstance)
		} else {
			flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'person.label', default: 'Person'), personInstance.id])
			redraw()
			redrawAttending()
			clearInput()
		}
	}
	
    void onClick_searchButton(Event e) {
        redraw()
		redrawAttending()
    }

    void onPaging_paging(ForwardEvent fe) {
        def event = fe.origin
        redraw(event.activePage)
		
    }

	void onPaging_pagingAttending(ForwardEvent fe) {
		def event = fe.origin
		redrawAttending(event.activePage)
		
	}
	
    private redraw(int activePage = 0) {
        int offset = activePage * paging.pageSize
        int max = paging.pageSize
		
		def session = sessionFactory.currentSession
		def person
		def personCnt
		
		
		String userName = springSecurityService.currentUser.username
		
		def userLoggedIn = Admin.findByUsername(userName)
		def adminRole = Role.findByAuthority("ROLE_ADMIN")
		def userRole = AdminRole.findByAdminAndRole(userLoggedIn, adminRole)
		
		if(userRole!=null){
			person = session.createSQLQuery(
			"""
					select * from person
					where person.attending = false 
					LIMIT $max OFFSET $offset
				"""
			)
			personCnt = session.createSQLQuery(
			"""
					select count(person.id) from person
					where person.attending = false 
				"""
			)
		}else{
			person = session.createSQLQuery(
				"""
					select * from person 
					where person.admin_id = """ + springSecurityService.currentUser.id +"""
					and person.attending = false 
					LIMIT $max OFFSET $offset
				"""
			)
			personCnt = session.createSQLQuery(
				"""
					select count(person.id) from person
					where person.admin_id = """ + springSecurityService.currentUser.id +"""
					and person.attending = false 
				"""
			)
		}
		
		
		person = person.addEntity(Person.class)
		person = person.list()

		
        paging.totalSize = personCnt.list().first()
        listModel.clear()
        listModel.addAll(person.id)
    }
	
	private redrawAttending(int activePage = 0) {
		int offset = activePage * pagingAttending.pageSize
		int max = pagingAttending.pageSize
		
		def session = sessionFactory.currentSession
		def person
		def personCnt
		
		
		String userName = springSecurityService.currentUser.username
		
		def userLoggedIn = Admin.findByUsername(userName)
		def adminRole = Role.findByAuthority("ROLE_ADMIN")
		def userRole = AdminRole.findByAdminAndRole(userLoggedIn, adminRole)
		
		if(userRole!=null){
			person = session.createSQLQuery(
			"""
					select * from person
					where person.attending = true 
					LIMIT $max OFFSET $offset
				"""
			)
			personCnt = session.createSQLQuery(
			"""
					select count(person.id) from person
					where person.attending = true 
				"""
			)
		}else{
			person = session.createSQLQuery(
				"""
					select * from person 
					where person.admin_id = """ + springSecurityService.currentUser.id +"""
					and person.attending = true 
					LIMIT $max OFFSET $offset
				"""
			)
			personCnt = session.createSQLQuery(
				"""
					select count(person.id) from person
					where person.admin_id = """ + springSecurityService.currentUser.id +"""
					and person.attending = true 
				"""
			)
		}
		
		
		person = person.addEntity(Person.class)
		person = person.list()

		
		pagingAttending.totalSize = personCnt.list().first()
		listModelAttend.clear()
		listModelAttend.addAll(person.id)
	}

    private rowRenderer = {Row row, Object id, int index ->
        def personInstance = Person.get(id)
        row << {
                label(value: personInstance.barcode)
                label(value: personInstance.name)
                label(value: personInstance.surname)
                label(value: personInstance.dob.format("dd MMMM yyyy"))
                label(value: personInstance.age)
                hlayout{
                    toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png', onClick:{
						idBox.value = personInstance.id
						versionBox.value = personInstance.version
						
						
						
						
						nameBox.value = personInstance.name
						surnameBox.value = personInstance.surname
						dateBox.value = personInstance.dob
						
						ageBox.value = personInstance.age
						
						gradeBox.value = personInstance.grade
						genderBox.value = personInstance.gender
						cellBox.value = personInstance.cellPhone
						addressBox.value = personInstance.address
						mNameBox.value = personInstance.momName
						fNameBox.value = personInstance.dadName
						mCellBox.value = personInstance.momCell
						fCellBox.value = personInstance.dadCell
						firstDateBox.value = personInstance.firstDate
						lastDateBox.value = personInstance.lastDateAttended
						attendingBox.checked = personInstance.attending
						barcodeBox.value = personInstance.barcode
						maleBox.checked = personInstance.male
						photoBox.value = personInstance.photo
						imageChangeBox.checked = personInstance.imagechange
						adminIdBox.value=personInstance.admin.id
						adminFld.value=personInstance.admin.username
						
						
						updateButton.visible = true
						createButton.visible = false
						editPanel.visible=true
						listPanel.visible=false
						
						
						
                    })
                    toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
                        Person.get(id).delete(flush: true)
                        listModel.remove(id)
                    })
                }
        }
    }
	
	void onClick_updateButton(Event e) {
		ageBox.value = getAge(dateBox.value)
		
		def params=self.params
		def admin = Admin.get(adminIdBox.value)
		
		
		
		def personInstance = Person.get(params.id)
		personInstance.admin = admin
		if (personInstance) {
			if (params.version != null) {
				def version = params.version
				if (personInstance.version > version) {
					String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'person.label', default: 'Person')],default:"Another user has updated this ${personInstance} while you were editing")
					Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
					return
				}
			}
			personInstance.properties = params
			if (!personInstance.hasErrors() && personInstance.save(flush: true)) {
				flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'person.label', default: 'Person'), personInstance.id])
				redraw()
				redrawAttending()
				clearInput()
			}else {
				log.error personInstance.errors
				self.renderErrors(bean: personInstance)
			}
		}
		else {
			flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'person.label', default: 'Person'), params.id])
			redraw()
			redrawAttending()
			clearInput()
		}

	}
	
	void onClick_newButton(Event e){
		clearInput()
		checkLoggedInUser()
		createButton.visible = true
		editPanel.visible=true
		listPanel.visible=false
	}
	
	void onClick_cancelButton(Event e){
		clearInput()
	}
	
	void onChange_genderBox(Event e){
		if(genderBox.value=="male"){
			maleBox.checked=true
		}else{
			maleBox.checked=false
		}
	}
	
	
	public String getBarCode() { //generates a barcode
		String barC = null
		try {
			boolean test = false
			while (!test) {
				long bar = (long) (Math.random() * 99999999) + 100000000
				barC = Long.toString(bar) + "12"
				def personLookup = Person.findByBarcode(barC)
				
				if(personLookup==null){
					test = true
				}
				
			}
		} catch (Exception ex) {
		}
		return barC
	}
	
	void checkLoggedInUser(){
		String userName = springSecurityService.currentUser.username
		
		def userLoggedIn = Admin.findByUsername(userName)
		def adminRole = Role.findByAuthority("ROLE_ADMIN")
		def userRole = AdminRole.findByAdminAndRole(userLoggedIn, adminRole)
		if(userRole==null){
			adminIdBox.value = springSecurityService.currentUser.id
			adminFld.value=userName
		}else{
			adminFld.disabled = false
		}
	}

	private redrawAdminListBox(int activePage = 0) {
		
				int offset = activePage * adminPaging.pageSize
				int max = adminPaging.pageSize
		
				def session = sessionFactory.currentSession
				def admin
				def adminCnt
				
				if (searchListBoxStr.isEmpty()) {
					admin = session.createSQLQuery(
						"""
					select * from admin 
					LIMIT $max OFFSET $offset
				""")
					
					adminCnt = session.createSQLQuery(
						"""
					select count(admin.id) from admin
				""")
					
				} else {
					admin = session.createSQLQuery(
							"""
						select * from admin 
						where admin.username like :adminsSearch 
						LIMIT $max OFFSET $offset
					""")
					admin.setString("adminsSearch", "%${searchListBoxStr}%")
					
					adminCnt = session.createSQLQuery(
						"""
						select count(admin.id) from admin 
						where admin.username like :tagsSearch 
					""")
					adminCnt.setString("adminsSearch", "%${searchListBoxStr}%")
				
				}
		
				admin = admin.addEntity(Admin.class)
				admin = admin.list()
		
				adminPaging.totalSize = adminCnt.list().first()
		
				adminListBoxModel.clear()
				adminListBoxModel.addAll(admin)
		
			}
	
	private adminListBoxRowRenderer = {Listitem listitem, Admin adminInstance, int index ->
		listitem << { listcell(label: adminInstance.username) }
		listitem.setValue(adminInstance.id)
	}
	
	void onPaging_adminPaging(ForwardEvent fe) {
		def event = fe.origin
		redrawAdminListBox(event.activePage)
	}
	
	void onSelect_adminListBox(Event e){
		long id = Long.parseLong(adminListBox.selectedItem.value.toString())
		adminIdBox.value = id
		adminFld.value=adminListBox.selectedItem.label
		adminFld.close()
	}
	
	int getAge(Date dobTemp) {
		//TAKE SUBSTRINGS OF THE DOB SO SPLIT OUT YEAR, MONTH AND DAY
		//INTO SEPERATE VARIABLES
		Date DOBDate = dobTemp
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy")
		int yearDOB = Integer.parseInt(dateFormat.format(DOBDate))
		
		dateFormat = new SimpleDateFormat("MM")
		int monthDOB = Integer.parseInt(dateFormat.format(DOBDate))
		
		dateFormat = new SimpleDateFormat("dd")
		int dayDOB = Integer.parseInt(dateFormat.format(DOBDate))

		//CALCULATE THE CURRENT YEAR, MONTH AND DAY
		//INTO SEPERATE VARIABLES
		dateFormat = new SimpleDateFormat("yyyy");
		Date date = new java.util.Date();
		int thisYear = Integer.parseInt(dateFormat.format(date));

		dateFormat = new SimpleDateFormat("MM");
		date = new java.util.Date();
		int thisMonth = Integer.parseInt(dateFormat.format(date));

		dateFormat = new SimpleDateFormat("dd");
		date = new java.util.Date();
		int thisDay = Integer.parseInt(dateFormat.format(date));

		//CREATE AN AGE VARIABLE TO HOLD THE CALCULATED AGE
		//TO START WILL – SET THE AGE EQUEL TO THE CURRENT YEAR MINUS THE YEAR
		//OF THE DOB
		int age = thisYear - yearDOB;

		//IF THE CURRENT MONTH IS LESS THAN THE DOB MONTH
		//THEN REDUCE THE DOB BY 1 AS THEY HAVE NOT HAD THEIR
		//BIRTHDAY YET THIS YEAR
		if (thisMonth < monthDOB) {
			age = age - 1;
		}

		//IF THE MONTH IN THE DOB IS EQUEL TO THE CURRENT MONTH
		//THEN CHECK THE DAY TO FIND OUT IF THEY HAVE HAD THEIR
		//BIRTHDAY YET. IF THE CURRENT DAY IS LESS THAN THE DAY OF THE DOB
		//THEN REDUCE THE DOB BY 1 AS THEY HAVE NOT HAD THEIR
		//BIRTHDAY YET THIS YEAR
		if (thisMonth == monthDOB && thisDay < dayDOB) {
			age = age - 1;
		}

		//THE AGE VARIBALE WILL NOW CONTAIN THE CORRECT AGE
		//DERIVED FROMTHE GIVEN DOB
		return age;
	}
	
	void onBlur_dateBox(Event e){
		ageBox.value = getAge(dateBox.value)
	}
	
	void onOK(){
		String barcodeSubmitted = barcodeEnterBox.value
		if(barcodeSubmitted.length()>=11){
			barcodeSubmitted = barcodeSubmitted.substring(0, 11)
			def person = Person.findByBarcode(barcodeSubmitted)
			if(person!=null){
				boolean attending = person.attending
				if(attending){
					person.attending=false
				}else{
					person.attending=true
					person.lastDateAttended = new Date()
				}
				person.save(flush: true)
				redraw()
				redrawAttending()
			}
		}
		clearInput()
	}
	
	void onClick_submitButton(Event e){
		String barcodeSubmitted = barcodeEnterBox.value
		if(barcodeSubmitted.length()>=11){
			barcodeSubmitted = barcodeSubmitted.substring(0, 11)
			def person = Person.findByBarcode(barcodeSubmitted)
			if(person!=null){
				boolean attending = person.attending
				if(attending){
					person.attending=false
				}else{
					person.attending=true
					person.lastDateAttended = new Date()
				}
				person.save(flush: true)
				redraw()
				redrawAttending()
			}
		}
		clearInput()
	}
	
	void onChanging_searchNameBox(Event e){
		redraw()
		redrawAttending()
	}
}