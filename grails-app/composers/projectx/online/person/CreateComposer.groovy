package projectx.online.person

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import projectx.online.Person

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def personInstance = new Person(self.params)
        if (!personInstance.save(flush: true) && personInstance.hasErrors()) {
            log.error personInstance.errors
            self.renderErrors(bean: personInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'person.label', default: 'Person'), personInstance.id])
            redirect(controller: "person", action: "list")
        }
    }
}