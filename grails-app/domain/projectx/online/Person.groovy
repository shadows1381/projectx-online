package projectx.online

class Person {

	String barcode
	String name 
	String surname
	Date dob
	int age
	String grade
	boolean male
	String gender
	Date firstDate = new Date()
	String cellPhone
	String address
	String momName
	String momCell
	String dadName
	String dadCell
	boolean attending
	Date lastDateAttended = new Date()
	String photo
	boolean imagechange
	
	Admin admin
	static belongsTo = [admin : Admin]
	
    static constraints = {
		barcode unique:true, nullable:false,blank:false
		name blank:false
		surname blank:false
		dob blank:false, nullable:false
		age nullable:false, blank:false
		grade nullable:true, blank:true
		male nullable:true
		gender blank:false, inList:["male", "female"]
		firstDate blank:false
		cellPhone blank:false, matches:"[0-9]{10}"
		momName nullable:true, blank:true
		momCell nullable:true, blank:true, matches:"[0-9]{10}"
		dadName nullalbe:true, blank:true
		dadCell nullable:true, blank:true, matches:"[0-9]{10}"
		attending nullable:true
		lastDateAttended blank:false
		photo blank:true, nullable:true
		imagechange nullable:true
    }
}
