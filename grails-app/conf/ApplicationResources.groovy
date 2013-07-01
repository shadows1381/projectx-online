modules = {
    application {
        resource url:'js/application.js'
    }
	
	loginPage {
		dependsOn 'application'
		
		resource url: 'js/login.js'
		resource url: 'css/login.css'
	}
}