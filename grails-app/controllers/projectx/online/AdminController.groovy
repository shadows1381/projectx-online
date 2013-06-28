package projectx.online

import org.springframework.security.access.annotation.Secured
import grails.plugins.springsecurity.Secured
@Secured(['IS_AUTHENTICATED_FULLY'])
class AdminController {

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
