package projectx.online.layouts

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import projectx.online.*

class MenuComposer {
	Menubar menubar
	def springSecurityService
	def afterCompose  = {Component comp ->
		if(springSecurityService.currentUser==null){
			menubar.visible=false
		}
	}
}