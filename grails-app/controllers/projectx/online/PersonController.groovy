package projectx.online

import org.springframework.security.access.annotation.Secured
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class PersonController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
		def personInstance = new Person()
		personInstance.properties = params
		return [personInstance: personInstance]
	}

    def create = {
        def personInstance = new Person()
        personInstance.properties = params
        return [personInstance: personInstance]
    }

    def edit = {
        def personInstance = Person.get(params.id)
        if (!personInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [personInstance: personInstance]
        }
    }

}
