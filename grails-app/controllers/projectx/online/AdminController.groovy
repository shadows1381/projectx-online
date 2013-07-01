package projectx.online

import org.springframework.security.access.annotation.Secured
import grails.plugins.springsecurity.Secured

class AdminController {
	@Secured(['IS_AUTHENTICATED_FULLY'])
    def index = {
        redirect(action: "list", params: params)
    }

	@Secured(["ROLE_ADMIN"])
    def list = {}

    def create = {
        def adminInstance = new Admin()
        adminInstance.properties = params
        return [adminInstance: adminInstance]
    }
	@Secured(['IS_AUTHENTICATED_FULLY'])
    def edit = {
        def adminInstance = Admin.get(params.id)
        if (!adminInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'admin.label', default: 'Admin'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [adminInstance: adminInstance]
        }
    }

}
