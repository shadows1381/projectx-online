package projectx.online.category

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*
import projectx.online.*

class ListComposer {
    Grid grid
    ListModelList listModel = new ListModelList()
    Paging paging
    Longbox idLongbox
	def springSecurityService
	
    def afterCompose = {Component comp ->
        grid.setRowRenderer(rowRenderer as RowRenderer)
        grid.setModel(listModel)
        redraw()
    }

    void onClick_searchButton(Event e) {
        redraw()
    }

    void onPaging_paging(ForwardEvent fe) {
        def event = fe.origin
        redraw(event.activePage)
    }
	
    private redraw(int activePage = 0) {
        int offset = activePage * paging.pageSize
        int max = paging.pageSize
        def categoryInstanceList = Category.createCriteria().list(offset: offset, max: max) {
            order('id','desc')
            if (idLongbox.value) {
                eq('id', idLongbox.value)
            }
			
			String userName = springSecurityService.currentUser.username
			
			def userLoggedIn = Admin.findByUsername(userName)
			def adminRole = Role.findByAuthority("ROLE_ADMIN")
			def userRole = AdminRole.findByAdminAndRole(userLoggedIn, adminRole)
			
			if(userRole==null){
				eq('admin',  springSecurityService.currentUser)
			}
			
        }
        paging.totalSize = categoryInstanceList.totalCount
        listModel.clear()
        listModel.addAll(categoryInstanceList.id)
    }

    private rowRenderer = {Row row, Object id, int index ->
        def categoryInstance = Category.get(id)
        row << {
                label(value: categoryInstance.name)
                label(value: categoryInstance.admin.username)
                label(value: categoryInstance.created)
                hlayout{
                    toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "category", action: 'edit', id: id))
                    toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
                        Category.get(id).delete(flush: true)
                        listModel.remove(id)
                    })
                }
        }
    }
}