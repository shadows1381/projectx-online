package projectx.online.admin

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import projectx.online.Admin

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def adminInstance = new Admin(self.params)
        if (!adminInstance.save(flush: true) && adminInstance.hasErrors()) {
            log.error adminInstance.errors
            self.renderErrors(bean: adminInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'admin.label', default: 'Admin'), adminInstance.id])
            redirect(controller: "admin", action: "list")
        }
    }
}