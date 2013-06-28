package projectx.online

import org.apache.commons.lang.builder.HashCodeBuilder

class AdminRole implements Serializable {

	Admin admin
	Role role

	boolean equals(other) {
		if (!(other instanceof AdminRole)) {
			return false
		}

		other.admin?.id == admin?.id &&
			other.role?.id == role?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (admin) builder.append(admin.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static AdminRole get(long adminId, long roleId) {
		find 'from AdminRole where admin.id=:adminId and role.id=:roleId',
			[adminId: adminId, roleId: roleId]
	}

	static AdminRole create(Admin admin, Role role, boolean flush = false) {
		new AdminRole(admin: admin, role: role).save(flush: flush, insert: true)
	}

	static boolean remove(Admin admin, Role role, boolean flush = false) {
		AdminRole instance = AdminRole.findByAdminAndRole(admin, role)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(Admin admin) {
		executeUpdate 'DELETE FROM AdminRole WHERE admin=:admin', [admin: admin]
	}

	static void removeAll(Role role) {
		executeUpdate 'DELETE FROM AdminRole WHERE role=:role', [role: role]
	}

	static mapping = {
		id composite: ['role', 'admin']
		version false
	}
}
