package projectx.online

class Admin {

	transient springSecurityService

	String username
	String password
	String email
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		username blank: false, unique: true
		password blank: false
		email email:true, uniques:true, blank:false
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		AdminRole.findAllByAdmin(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
