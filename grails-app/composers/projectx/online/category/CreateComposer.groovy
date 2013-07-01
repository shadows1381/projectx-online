package projectx.online.category

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.*

import projectx.online.*

class CreateComposer {
    Window self
	
	Paging adminPaging
	Button clearListBoxSearch
	Textbox adminSearch
	Bandbox adminFld
	Listbox adminListBox
	ListModelList adminListBoxModel = new ListModelList()
	String searchListBoxStr = ""
	Longbox adminIdBox
	def sessionFactory
	def springSecurityService
	
    def afterCompose = {Component comp ->
        //todo initialize components here
		adminListBox.setItemRenderer(adminListBoxRowRenderer as ListitemRenderer)
		adminListBox.setModel(adminListBoxModel)
		redrawAdminListBox()
		checkLoggedInUser()
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
	
    void onClick_saveButton(Event e) {
		
		def admin = Admin.get(adminIdBox.value)
		
        def categoryInstance = new Category(self.params)
		categoryInstance.admin = admin
		
		
        if (!categoryInstance.save(flush: true) && categoryInstance.hasErrors()) {
            log.error categoryInstance.errors
            self.renderErrors(bean: categoryInstance)
			
			if (adminFld.value.isEmpty()) {
				adminFld.errorMessage = g.message(code: 'projectx.online.admin.blank')
			}
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'category.label', default: 'Category'), categoryInstance.id])
            redirect(controller: "category", action: "list")
        }
    }
}