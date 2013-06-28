import projectx.online.*

class BootStrap {

    def init = { servletContext ->
		
		def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
		def userRole = new Role(authority: 'ROLE_USER').save(flush: true)
		def adminUser = new Admin(username: 'admin', enabled: true, password: 'Arthur43', email: 'lourens@al.co.za').save(flush: true)
		//UserRole.create testUser, adminRole, true
		AdminRole.create adminUser, adminRole
		
    }
    def destroy = {
    }
}
