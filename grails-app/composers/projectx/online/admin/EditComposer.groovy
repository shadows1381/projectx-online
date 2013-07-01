package projectx.online.admin

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import projectx.online.*

class EditComposer {
    Window self
	Textbox usernameBox
	def springSecurityService
	Window editWindow
	Button adminListButton
	Checkbox acountExpiredCheck
	Checkbox enabledCheckBox
	Textbox passwordBox
	Textbox passwordReBox
	
    def afterCompose = {Component comp ->
        //todo initialize components here
		checkLoggedInUser()
    }

	void checkLoggedInUser(){
		String userName = springSecurityService.currentUser.username
		
		def userLoggedIn = Admin.findByUsername(userName)
		def adminRole = Role.findByAuthority("ROLE_ADMIN")
		def userRole = AdminRole.findByAdminAndRole(userLoggedIn, adminRole)
		
		if(userRole==null && usernameBox.value!=userName){
			redirect(controller: "login", action:"denied")
		}else if(userRole!=null && usernameBox.value!=userName){
			editWindow.visible = true
			adminListButton.visible=true
			acountExpiredCheck.disabled=false
			enabledCheckBox.disabled=false
		}else if(userRole!=null){
			adminListButton.visible=true
			editWindow.visible = true
		}else{
			editWindow.visible = true
		}
	}
	
    void onClick_saveButton(Event e) {
		if(passwordBox.value!=passwordReBox.value){
			passwordReBox.errorMessage = g.message(code: 'projectx.online.passwords.matches')
			passwordBox.errorMessage = g.message(code: 'projectx.online.passwords.matches')
			return
		}
        def params=self.params
        def adminInstance = Admin.get(params.id)
        if (adminInstance) {
            if (params.version != null) {
                def version = params.version
                if (adminInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'admin.label', default: 'Admin')],default:"Another user has updated this ${adminInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            adminInstance.properties = params
            if (!adminInstance.hasErrors() && adminInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'admin.label', default: 'Admin'), adminInstance.id])
                redirect(controller: "admin", action: "edit", id: adminInstance.id)
            }else {
                log.error adminInstance.errors
                self.renderErrors(bean: adminInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'admin.label', default: 'Admin'), params.id])
            redirect(controller: "admin",action: "list")
        }

    }
}