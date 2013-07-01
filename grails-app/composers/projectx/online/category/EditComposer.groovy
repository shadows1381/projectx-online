package projectx.online.category

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zk.ui.event.ForwardEvent
import org.zkoss.zul.*
import projectx.online.*

class EditComposer {
    Window self
	Window editWindow
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
	Longbox categoryIdBox
	
	
    def afterCompose = {Component comp ->
        //todo initialize components here
		Category categoryInstance = Category.get(categoryIdBox.value)
		adminIdBox.value=categoryInstance.admin.id
		adminFld.value=categoryInstance.admin.username
		
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
		if(userRole!=null){
			adminFld.disabled = false
			editWindow.visible=true
		}else{
			if(userName!=adminFld.value){
				redirect(controller:"login", action:"denied")
			}else{
				editWindow.visible=true
			}
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
        def params=self.params
		
		def admin = Admin.get(adminIdBox.value)
        def categoryInstance = Category.get(params.id)
		categoryInstance.admin = admin
        if (categoryInstance) {
            if (params.version != null) {
                def version = params.version
                if (categoryInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'category.label', default: 'Category')],default:"Another user has updated this ${categoryInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            categoryInstance.properties = params
            if (!categoryInstance.hasErrors() && categoryInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'category.label', default: 'Category'), categoryInstance.id])
                redirect(controller: "category", action: "edit", id: categoryInstance.id)
            }else {
                log.error categoryInstance.errors
                self.renderErrors(bean: categoryInstance)
				
				if (adminFld.value.isEmpty()) {
					adminFld.errorMessage = g.message(code: 'projectx.online.admin.blank')
				}
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'category.label', default: 'Category'), params.id])
            redirect(controller: "category",action: "list")
        }

    }
}