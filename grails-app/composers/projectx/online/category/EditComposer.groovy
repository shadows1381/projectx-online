package projectx.online.category

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import projectx.online.Category

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def categoryInstance = Category.get(params.id)
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
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'category.label', default: 'Category'), params.id])
            redirect(controller: "category",action: "list")
        }

    }
}