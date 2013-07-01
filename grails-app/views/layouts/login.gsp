<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--[if lt IE 7 ]> <html xmlns="http://www.w3.org/1999/xhtml" lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html xmlns="http://www.w3.org/1999/xhtml" lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html xmlns="http://www.w3.org/1999/xhtml" lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html xmlns="http://www.w3.org/1999/xhtml" lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails" /></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<link rel="shortcut icon"
				href="${resource(dir: 'images', file: 'favicon.ico')}"
				type="image/x-icon">
				<link rel="apple-touch-icon"
					href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
					<link rel="apple-touch-icon" sizes="114x114"
						href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
						<z:resources />
						<g:layoutHead />
						<g:javascript library="loginPage" />
						<r:layoutResources />
						<ga:trackPageview />
</head>
<body>
	<g:layoutBody />
	<div id="spinner" class="spinner" style="display: none;">
		<g:message code="spinner.alt" default="Loading&hellip;" />
	</div>
	<g:javascript library="loginPage" />
	<r:layoutResources />
	<script type="text/javascript">
		$(document).ready(function() {
			getParams();
		});
	</script>
</body>
</html>