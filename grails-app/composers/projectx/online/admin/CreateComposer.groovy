package projectx.online.admin

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import projectx.online.*

class CreateComposer {
    Window self
	Checkbox enabledCheckBox
	Button backButton
	
	Textbox passwordBox
	Textbox passwordReBox
	
	def springSecurityService
	
    def afterCompose = {Component comp ->
        //todo initialize components here
		enabledCheckBox.checked = true
    }

    void onClick_saveButton(Event e) {
		if(passwordBox.value!=passwordReBox.value){
			String failureMessage = "Passwords do not match"
			Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
			return
		}
        def adminInstance = new Admin(self.params)
        if (!adminInstance.save(flush: true) && adminInstance.hasErrors()) {
            log.error adminInstance.errors
            self.renderErrors(bean: adminInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'admin.label', default: 'User'), adminInstance.username])
			if(springSecurityService.currentUser==null){
				redirect(controller: "login", action: "auth")
			}else{
				redirect(controller: "admin", action: "list")
			}
        }
    }
	
	void onClick_backButton(Event e){
		if(springSecurityService.currentUser==null){
			redirect(controller: "login", action: "auth")
		}else{
			redirect(controller: "admin", action: "list")
		}
	}
}