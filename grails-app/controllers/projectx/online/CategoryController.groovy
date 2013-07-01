package projectx.online
import org.springframework.security.access.annotation.Secured
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class CategoryController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def categoryInstance = new Category()
        categoryInstance.properties = params
        return [categoryInstance: categoryInstance]
    }

    def edit = {
        def categoryInstance = Category.get(params.id)
        if (!categoryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [categoryInstance: categoryInstance]
        }
    }

}
