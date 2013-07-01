<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
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
<link rel="stylesheet" href="${resource(dir: 'css', file: 'admin.css')}"
	type="text/css">
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
<g:layoutHead />
<r:layoutResources />
<z:resources />
</head>
<body>
	<div class="header_back_ground">
		<z:menubar id="menubar" autodrop="true"
			sclass="z-menubar-hor-main-menu" apply="projectx.online.layouts.MenuComposer">
			<z:menuitem label="Home" height="40px;" top="40px;"
				href="${createLink(uri: '/')}" image="${fam.icon(name: 'house')}" />
			<sec:ifAnyGranted roles="ROLE_ADMIN">
				<z:menuitem label="Admin List" height="40px;" top="40px;"
					href="${createLink(uri: '/admin')}"
					image="${fam.icon(name: 'application_side_list')}" />
			</sec:ifAnyGranted>
			<z:menu label="${sec.loggedInUserInfo(field: "username")}"
				image="${fam.icon(name: 'user_suit')}">
				<z:menupopup>
					<z:menuitem label="Edit"
						href="/admin/edit/${sec.loggedInUserInfo(field: "id") }"
						image="${fam.icon(name: 'user_edit')}" />
					<z:menuitem label="Log out" href="/logout"
						image="${fam.icon(name: 'disconnect')}" />
				</z:menupopup>
			</z:menu>
		</z:menubar>
	</div>
	<g:layoutBody />
	<div id="spinner" class="spinner" style="display: none;">
		<g:message code="spinner.alt" default="Loading&hellip;" />
	</div>
	<g:javascript library="application" />
	<r:layoutResources />
</body>
</html>
