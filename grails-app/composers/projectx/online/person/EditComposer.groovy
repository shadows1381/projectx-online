package projectx.online.person

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import projectx.online.Person

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def personInstance = Person.get(params.id)
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
                redirect(controller: "person", action: "edit", id: personInstance.id)
            }else {
                log.error personInstance.errors
                self.renderErrors(bean: personInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'person.label', default: 'Person'), params.id])
            redirect(controller: "person",action: "list")
        }

    }
}