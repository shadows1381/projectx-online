package projectx.online

class Category {

	String name
	Date created = new Date()
	Admin admin
	static belongsTo = [admin : Admin]
	
    static constraints = {
		name blank:false, nullable: false
		admin blank:false, nullable:false
		created blank:false
    }
}
